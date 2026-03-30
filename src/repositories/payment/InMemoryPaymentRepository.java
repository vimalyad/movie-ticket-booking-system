package repositories.payment;

import models.Payment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPaymentRepository implements PaymentRepository {
    private final Map<String, Payment> database = new ConcurrentHashMap<>();

    @Override
    public void save(Payment payment) {
        database.put(payment.getId(), payment);
    }

    @Override
    public Payment findByBookingId(String bookingId) {
        return database.values().stream()
                .filter(payment -> payment.getBookingId().equals(bookingId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No payment found for booking id: " + bookingId));
    }
}
