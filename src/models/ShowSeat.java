package models;

import enums.SeatAvailabilityStatus;

import java.util.Objects;
import java.util.UUID;

public class ShowSeat {

    private final String id;
    private final Seat seat;
    private final String showId;
    private SeatAvailabilityStatus status;

    public ShowSeat(Seat seat, String showId) {
        Objects.requireNonNull(seat, "seat must not be null");
        Objects.requireNonNull(showId, "showId must not be null");
        if (showId.isBlank()) throw new IllegalArgumentException("showId must not be blank");

        this.id = UUID.randomUUID().toString();
        this.seat = seat;
        this.showId = showId;
        this.status = SeatAvailabilityStatus.AVAILABLE;
    }

    public void lock() {
        if (this.status != SeatAvailabilityStatus.AVAILABLE) {
            throw new IllegalStateException("Cannot lock seat. Current status: " + this.status);
        }
        this.status = SeatAvailabilityStatus.LOCKED;
    }

    public void book() {
        if (this.status != SeatAvailabilityStatus.LOCKED) {
            throw new IllegalStateException("Seat must be locked before booking. Current status: " + this.status);
        }
        this.status = SeatAvailabilityStatus.BOOKED;
    }

    public void release() {
        this.status = SeatAvailabilityStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public String getShowId() {
        return showId;
    }

    public SeatAvailabilityStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ShowSeat[" + seat.getSeatLabel() + ", " + status + "]";
    }
}