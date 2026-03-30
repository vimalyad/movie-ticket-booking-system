package models;

import enums.BookingStatus;

import java.util.List;
import java.util.UUID;

public class Booking {
    private final String id = UUID.randomUUID().toString();
    private final String customerId;
    private final String showId;
    private final List<ShowSeat> seats;
    private final Money totalAmount;
    private BookingStatus status = BookingStatus.PENDING;

    public Booking(String customerId, String showId, List<ShowSeat> seats, Money totalAmount) {
        this.customerId = customerId;
        this.showId = showId;
        this.seats = seats;
        this.totalAmount = totalAmount;
    }

    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
        this.seats.forEach(ShowSeat::book);
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        this.seats.forEach(ShowSeat::release);
    }

    public void expire() {
        this.status = BookingStatus.EXPIRED;
        this.seats.forEach(ShowSeat::release);
    }

    public String getId() {
        return id;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ShowSeat> getSeats() {
        return seats;
    }

    public String getShowId() {
        return showId;
    }
}