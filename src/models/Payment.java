package models;

import enums.PaymentStatus;

import java.util.UUID;

public class Payment {
    private final String id = UUID.randomUUID().toString();
    private final String bookingId;
    private final Money amount;
    private PaymentStatus status = PaymentStatus.PENDING;

    public Payment(String bookingId, Money amount) {
        this.bookingId = bookingId;
        this.amount = amount;
    }

    public void markAsSuccess() {
        this.status = PaymentStatus.SUCCESS;
    }

    public void markAsFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public void markAsRefunded() {
        this.status = PaymentStatus.REFUNDED;
    }

    public String getId() {
        return id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}