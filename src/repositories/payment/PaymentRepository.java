package repositories.payment;

import models.Payment;

public interface PaymentRepository {
    void save(Payment payment);

    Payment findByBookingId(String bookingId);
}