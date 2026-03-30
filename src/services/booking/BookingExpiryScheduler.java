package services.booking;

import enums.BookingStatus;
import models.Booking;
import models.ShowSeat;
import repositories.booking.BookingRepository;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookingExpiryScheduler {
    private final BookingRepository bookingRepository;
    private final SeatLockProvider seatLockProvider;
    private final List<BookingEventListener> eventListeners;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public BookingExpiryScheduler(BookingRepository bookingRepository, SeatLockProvider seatLockProvider, List<BookingEventListener> eventListeners) {
        this.bookingRepository = bookingRepository;
        this.seatLockProvider = seatLockProvider;
        this.eventListeners = eventListeners;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::sweepExpiredBookings, 1, 1, TimeUnit.MINUTES);
    }

    private void sweepExpiredBookings() {
        List<Booking> pending = bookingRepository.findByStatus(BookingStatus.PENDING);
        for (Booking booking : pending) {
            List<String> seatIds = booking.getSeats().stream().map(ShowSeat::getId).toList();
            boolean anyLocked = seatIds.stream().anyMatch(seatLockProvider::isSeatLocked);

            if (!anyLocked) {
                booking.expire();
                bookingRepository.save(booking);
                eventListeners.forEach(l -> l.onBookingExpired(booking));
            }
        }
    }

    public void stop() {
        scheduler.shutdown();
    }
}