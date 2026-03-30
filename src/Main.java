import controllers.AdminController;
import controllers.BookingController;
import controllers.SearchController;
import dto.*;
import enums.SeatType;
import repositories.booking.InMemoryBookingRepository;
import repositories.movie.InMemoryMovieRepository;
import repositories.payment.InMemoryPaymentRepository;
import repositories.pricingrule.InMemoryPricingRuleRepository;
import repositories.show.InMemoryShowRepository;
import repositories.showseat.InMemoryShowSeatRepository;
import repositories.theatre.InMemoryTheatreRepository;
import seeder.DemoDataSeeder;
import services.booking.BookingEventListener;
import services.booking.BookingExpiryScheduler;
import services.booking.BookingFacade;
import services.booking.SeatLockProvider;
import services.payment.PaymentService;
import services.pricing.PricingEngine;
import services.search.SearchService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("🚀 Starting Movie Ticket Booking System...\n");

        InMemoryTheatreRepository theatreRepo = new InMemoryTheatreRepository();
        InMemoryMovieRepository movieRepo = new InMemoryMovieRepository();
        InMemoryShowRepository showRepo = new InMemoryShowRepository();
        InMemoryShowSeatRepository showSeatRepo = new InMemoryShowSeatRepository();
        InMemoryBookingRepository bookingRepo = new InMemoryBookingRepository();
        InMemoryPaymentRepository paymentRepo = new InMemoryPaymentRepository();
        InMemoryPricingRuleRepository pricingRuleRepo = new InMemoryPricingRuleRepository();

        SeatLockProvider lockProvider = new SeatLockProvider();
        PricingEngine pricingEngine = new PricingEngine(pricingRuleRepo);
        PaymentService paymentService = new PaymentService(paymentRepo);
        SearchService searchService = new SearchService(theatreRepo, movieRepo, showRepo, showSeatRepo);

        List<BookingEventListener> listeners = new ArrayList<>();
        listeners.add(new BookingEventListener() {
            @Override
            public void onBookingConfirmed(models.Booking booking) {
                System.out.println("📧 [EMAIL SERVICE] Tickets Confirmed! Sending PDF to Customer " + booking.getCustomerId());
            }

            @Override
            public void onBookingCancelled(models.Booking booking) {
                System.out.println("📧 [EMAIL SERVICE] Booking Cancelled for Customer " + booking.getCustomerId());
            }

            @Override
            public void onBookingExpired(models.Booking booking) {
                System.out.println("📧 [EMAIL SERVICE] Booking cart expired. Seats released.");
            }
        });

        BookingExpiryScheduler expiryScheduler = new BookingExpiryScheduler(bookingRepo, lockProvider, listeners);
        expiryScheduler.start();
        System.out.println("⏳ Background Expiry Scheduler started.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Shutting down background tasks...");
            expiryScheduler.stop();
        }));

        BookingFacade bookingFacade = new BookingFacade(
                bookingRepo, showSeatRepo, showRepo, lockProvider,
                pricingEngine, paymentService, paymentRepo, listeners
        );

        AdminController adminController = new AdminController(pricingRuleRepo);
        SearchController searchController = new SearchController(searchService);
        BookingController bookingController = new BookingController(bookingFacade);

        DemoDataSeeder.SeederContext context = DemoDataSeeder.seed(theatreRepo, movieRepo, showRepo, showSeatRepo);

        System.out.println("\n⚙️ [ADMIN] Setting up Dynamic Pricing Rules...");
        adminController.addPricingRule(new AddPricingRuleRequest(
                "Recliner Premium Surge", 150.0, "INR", SeatType.RECLINER, null, null
        ));
        System.out.println("✅ [ADMIN] Added +150 INR surcharge for RECLINER seats.");

        System.out.println("\n---------------------------------------------------");
        System.out.println("👤 [CUSTOMER 1] User opens the App...");
        System.out.println("---------------------------------------------------\n");

        String userCity = context.city;

        System.out.println("🔍 Searching for theatres in: " + userCity);
        List<models.Theatre> theatres = searchController.getTheatresByCity(userCity);
        theatres.forEach(t -> System.out.println("   🏢 Found Theatre: " + t.getName()));

        System.out.println("\n🔍 Searching for movies in: " + userCity);
        List<MovieResponse> movies = searchController.getMoviesByCity(userCity);
        movies.forEach(m -> System.out.println("   🎬 Found Movie: " + m.getTitle()));

        System.out.println("\n🔍 Checking seat availability for the show...");
        List<SeatAvailabilityResponse> seats = searchController.getSeatAvailability(context.showId);
        System.out.println("   🪑 Total seats available: " + seats.size());

        List<String> selectedSeats = new ArrayList<>();
        selectedSeats.add(context.regularSeatIds.get(0));
        selectedSeats.add(context.reclinerSeatIds.get(0));

        String customerId = "CUST-999";
        System.out.println("\n🛒 User 1 selects 2 seats and clicks 'Book Tickets'...");

        BookingRequest bookingRequest = new BookingRequest(customerId, context.showId, selectedSeats);
        BookingResponse pendingBooking;
        try {
            pendingBooking = bookingController.createBooking(bookingRequest);
            System.out.println("✅ Booking Initiated Successfully for User 1!");
            System.out.println("   -> Booking ID: " + pendingBooking.getBookingId());
            System.out.println("   -> Status: " + pendingBooking.getStatus());
            System.out.println("   -> Total Amount Due: " + pendingBooking.getTotalAmount().getAmount() + " " + pendingBooking.getTotalAmount().getCurrency());
            System.out.println("   -> Seats Held: " + pendingBooking.getSeatLabels());
            System.out.println("   ⏳ Please complete payment before: " + pendingBooking.getExpiresAt());
        } catch (Exception e) {
            System.out.println("❌ Failed to initiate booking: " + e.getMessage());
            return;
        }

        System.out.println("\n🚨 [CONCURRENCY TEST] Customer 2 tries to book the exact SAME seats...");
        String secondCustomerId = "CUST-888";
        BookingRequest competingRequest = new BookingRequest(secondCustomerId, context.showId, selectedSeats);

        try {
            bookingController.createBooking(competingRequest);
            System.out.println("❌ FATAL FLAW: System allowed double booking!");
        } catch (IllegalStateException e) {
            System.out.println("🛡️ SYSTEM BLOCKED DOUBLE-BOOKING: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("🛡️ SYSTEM BLOCKED DOUBLE-BOOKING: " + e.getMessage());
        }

        System.out.println("\n💳 User 1 is entering Credit Card details...");
        PaymentRequest paymentRequest = new PaymentRequest("CREDIT_CARD", "tok_visa_12345");

        try {
            String result = bookingController.confirmBooking(pendingBooking.getBookingId(), paymentRequest);
            System.out.println("🎉 " + result);
        } catch (Exception e) {
            System.out.println("\n❌ Payment Error: " + e.getMessage());
        }

        System.out.println("\n🔍 Checking seat availability again to verify booking...");
        List<SeatAvailabilityResponse> updatedSeats = searchController.getSeatAvailability(context.showId);
        long bookedCount = updatedSeats.stream()
                .filter(s -> s.getStatus() == enums.SeatAvailabilityStatus.BOOKED)
                .count();
        System.out.println("   🪑 Total seats officially BOOKED in database: " + bookedCount);

        System.out.println("\n🚫 User 1 decides to cancel their booking...");
        try {
            String cancelResult = bookingController.cancelBooking(pendingBooking.getBookingId(), customerId);
            System.out.println("✅ " + cancelResult);
        } catch (Exception e) {
            System.out.println("❌ Cancellation Error: " + e.getMessage());
        }

        System.out.println("\n🔍 Checking seat availability after cancellation...");
        List<SeatAvailabilityResponse> finalSeats = searchController.getSeatAvailability(context.showId);
        long availableCount = finalSeats.stream()
                .filter(s -> s.getStatus() == enums.SeatAvailabilityStatus.AVAILABLE)
                .count();
        long finalBookedCount = finalSeats.stream()
                .filter(s -> s.getStatus() == enums.SeatAvailabilityStatus.BOOKED)
                .count();

        System.out.println("   🪑 Total seats officially AVAILABLE in database: " + availableCount);
        System.out.println("   🪑 Total seats officially BOOKED in database: " + finalBookedCount);

        System.out.println("\n🏁 Demo Completed Successfully.");
        System.exit(0);
    }
}