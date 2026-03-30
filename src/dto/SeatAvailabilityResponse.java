package dto;

import enums.SeatAvailabilityStatus;

public class SeatAvailabilityResponse {
    private final String showSeatId;
    private final String seatLabel;
    private final double basePrice;
    private final SeatAvailabilityStatus status;

    public SeatAvailabilityResponse(String showSeatId, String seatLabel, double basePrice, SeatAvailabilityStatus status) {
        this.showSeatId = showSeatId;
        this.seatLabel = seatLabel;
        this.basePrice = basePrice;
        this.status = status;
    }

    public String getShowSeatId() {
        return showSeatId;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public SeatAvailabilityStatus getStatus() {
        return status;
    }
}