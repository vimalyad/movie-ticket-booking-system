package dto;

import java.util.List;

public class BookingRequest {
    private String customerId;
    private String showId;
    private List<String> showSeatIds;

    public BookingRequest(String customerId, String showId, List<String> showSeatIds) {
        this.customerId = customerId;
        this.showId = showId;
        this.showSeatIds = showSeatIds;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getShowId() {
        return showId;
    }

    public List<String> getShowSeatIds() {
        return showSeatIds;
    }
}