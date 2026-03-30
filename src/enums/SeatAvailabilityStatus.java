package enums;

public enum SeatAvailabilityStatus {
    AVAILABLE,
    LOCKED,
    BOOKED;

    public boolean isSelectable() {
        return this == AVAILABLE;
    }

    public boolean isHeld() {
        return this == LOCKED || this == BOOKED;
    }
}
