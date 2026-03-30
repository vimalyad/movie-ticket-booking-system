package services.payment;

import models.Payment;
import repositories.payment.PaymentRepository;

public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void process(Payment payment, String paymentMethod, String token) {
        boolean success = validateToken(token);
        if (success) {
            payment.markAsSuccess();
        } else {
            payment.markAsFailed();
        }
        paymentRepository.save(payment);
    }

    public void refund(Payment payment) {
        payment.markAsRefunded();
        paymentRepository.save(payment);
    }

    private boolean validateToken(String token) {
        return token != null && !token.isBlank();
    }
}