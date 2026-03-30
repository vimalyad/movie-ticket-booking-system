package enums;

public enum SeatType {

    REGULAR,
    PREMIUM,
    RECLINER,
    VIP;

    public String label() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}