package services.booking;

import dto.BookingRequest;
import dto.BookingResponse;
import dto.PaymentRequest;
import enums.BookingStatus;
import enums.PaymentStatus;
import models.*;
import repositories.booking.BookingRepository;
import repositories.payment.PaymentRepository;
import repositories.show.ShowRepository;
import repositories.showseat.ShowSeatRepository;
import services.payment.PaymentService;
import services.pricing.PricingEngine;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

// Facade design pattern
// it handles all heavy complex tasks of booking
public class BookingFacade {
    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final SeatLockProvider seatLockProvider;
    private final PricingEngine pricingEngine;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final List<BookingEventListener> eventListeners;

    public BookingFacade(BookingRepository bookingRepository, ShowSeatRepository showSeatRepository, ShowRepository showRepository,
                         SeatLockProvider seatLockProvider, PricingEngine pricingEngine, PaymentService paymentService,
                         PaymentRepository paymentRepository, List<BookingEventListener> eventListeners) {
        this.bookingRepository = bookingRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.seatLockProvider = seatLockProvider;
        this.pricingEngine = pricingEngine;
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
        this.eventListeners = eventListeners;
    }

    public BookingResponse initiateBooking(BookingRequest request) {
        Show show = showRepository.findById(request.getShowId());
        List<ShowSeat> seats = showSeatRepository.findAllById(request.getShowSeatIds());

        boolean locked = seatLockProvider.lockSeats(
                seats.stream().map(ShowSeat::getId).toList(), request.getCustomerId(), seats
        );
        if (!locked) {
            throw new IllegalStateException("Seats are currently held by another user.");
        }

        Money basePrice = Money.of(BigDecimal.valueOf(200), "INR");
        Money total = Money.zero("INR");
        for (ShowSeat seat : seats) {
            total = total.add(pricingEngine.calculateFinalPrice(seat, show, basePrice));
        }

        Booking booking = new Booking(request.getCustomerId(), show.getId(), seats, total);
        bookingRepository.save(booking);
        showSeatRepository.saveAll(seats);

        return new BookingResponse(booking.getId(), total, BookingStatus.PENDING,
                seats.stream().map(s -> s.getSeat().getSeatLabel()).toList(), Instant.now().plusSeconds(600));
    }

    public String confirmBooking(String bookingId, PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findById(bookingId);
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Booking is not pending.");
        }

        Payment payment = new Payment(bookingId, booking.getTotalAmount());
        paymentService.process(payment, paymentRequest.getPaymentMethod(), paymentRequest.getPaymentToken());

        List<String> seatIds = booking.getSeats().stream().map(ShowSeat::getId).toList();

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            booking.confirm();
            seatLockProvider.unlockSeats(seatIds);
            bookingRepository.save(booking);
            showSeatRepository.saveAll(booking.getSeats());
            eventListeners.forEach(l -> l.onBookingConfirmed(booking));
            return "Booking confirmed! Reference: " + booking.getId();
        } else {
            booking.cancel();
            seatLockProvider.unlockSeats(seatIds);
            bookingRepository.save(booking);
            showSeatRepository.saveAll(booking.getSeats());
            return "Payment failed. Seats have been released.";
        }
    }

    public String cancelBooking(String bookingId, String customerId) {
        Booking booking = bookingRepository.findById(bookingId);
        if (!booking.getCustomerId().equals(customerId)) {
            throw new IllegalStateException("Unauthorized");
        }
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only confirmed bookings can be cancelled");
        }

        Payment originalPayment = paymentRepository.findByBookingId(bookingId);
        paymentService.refund(originalPayment);

        booking.cancel();
        seatLockProvider.unlockSeats(booking.getSeats().stream().map(ShowSeat::getId).toList());
        bookingRepository.save(booking);
        showSeatRepository.saveAll(booking.getSeats());
        eventListeners.forEach(l -> l.onBookingCancelled(booking));

        return "Cancelled successfully. Refund initiated.";
    }
}