package dto;

import enums.BookingStatus;
import models.Money;

import java.time.Instant;
import java.util.List;

public class BookingResponse {
    private final String bookingId;
    private final Money totalAmount;
    private final BookingStatus status;
    private final List<String> seatLabels;
    private final Instant expiresAt;

    public BookingResponse(String bookingId, Money totalAmount, BookingStatus status, List<String> seatLabels, Instant expiresAt) {
        this.bookingId = bookingId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.seatLabels = seatLabels;
        this.expiresAt = expiresAt;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public List<String> getSeatLabels() {
        return seatLabels;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}