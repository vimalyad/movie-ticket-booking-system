package models;

import enums.BookingStatus;
import models.users.Customer;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Booking {

    private final String id;
    private final Customer customer;
    private final Show show;
    private final List<ShowSeat> seats;
    private final Money totalAmount;
    private final Instant createdAt;

    private BookingStatus status;

    public Booking(Customer customer, Show show, List<ShowSeat> seats, Money totalAmount) {
        Objects.requireNonNull(customer, "customer must not be null");
        Objects.requireNonNull(show, "show must not be null");
        Objects.requireNonNull(seats, "seats must not be null");
        Objects.requireNonNull(totalAmount, "totalAmount must not be null");

        if (seats.isEmpty()) {
            throw new IllegalArgumentException("Booking must contain at least one seat");
        }

        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.show = show;
        this.seats = List.copyOf(seats);
        this.totalAmount = totalAmount;
        this.createdAt = Instant.now();
        this.status = BookingStatus.PENDING;
    }


    public void confirm() {
        if (this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be confirmed. Current: " + this.status);
        }
        this.status = BookingStatus.CONFIRMED;
        this.seats.forEach(ShowSeat::book);
    }

    public void cancel() {
        if (this.status == BookingStatus.CANCELLED || this.status == BookingStatus.EXPIRED) {
            throw new IllegalStateException("Booking is already inactive. Current: " + this.status);
        }
        this.status = BookingStatus.CANCELLED;
        this.seats.forEach(ShowSeat::release);
    }

    public void expire() {
        if (this.status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can expire. Current: " + this.status);
        }
        this.status = BookingStatus.EXPIRED;
        this.seats.forEach(ShowSeat::release);
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Show getShow() {
        return show;
    }

    public List<ShowSeat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}