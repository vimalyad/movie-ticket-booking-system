package models;

import enums.PaymentStatus;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Payment {

    private final String id;
    private final String bookingId;
    private final Money amount;
    private final Instant createdAt;

    private PaymentStatus status;
    private String transactionReference;

    public Payment(String bookingId, Money amount) {
        Objects.requireNonNull(bookingId, "bookingId must not be null");
        Objects.requireNonNull(amount, "amount must not be null");

        this.id = UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.amount = amount;
        this.createdAt = Instant.now();
        this.status = PaymentStatus.PENDING;
    }

    public void markAsSuccess(String transactionReference) {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Only PENDING payments can succeed.");
        }
        this.status = PaymentStatus.SUCCESS;
        this.transactionReference = transactionReference;
    }

    public void markAsFailed(String errorReference) {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Only PENDING payments can fail.");
        }
        this.status = PaymentStatus.FAILED;
        this.transactionReference = errorReference;
    }

    public void processRefund(String refundReference) {
        if (this.status != PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Only SUCCESSFUL payments can be refunded.");
        }
        this.status = PaymentStatus.REFUNDED;
        this.transactionReference = refundReference;
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

    public String getTransactionReference() {
        return transactionReference;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}