package models;

import enums.SeatAvailabilityStatus;

import java.util.UUID;

public class ShowSeat {
    private final String id = UUID.randomUUID().toString();
    private final Seat seat;
    private final String showId;
    private SeatAvailabilityStatus status = SeatAvailabilityStatus.AVAILABLE;

    public ShowSeat(Seat seat, String showId) {
        this.seat = seat;
        this.showId = showId;
    }

    public void lock() {
        if (this.status != SeatAvailabilityStatus.AVAILABLE) {
            throw new IllegalStateException("Seat not available.");
        }
        this.status = SeatAvailabilityStatus.LOCKED;
    }

    public void book() {
        if (this.status != SeatAvailabilityStatus.LOCKED) {
            throw new IllegalStateException("Seat must be locked first.");
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
}