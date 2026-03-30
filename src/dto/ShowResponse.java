package dto;

import java.time.LocalDateTime;

public class ShowResponse {
    private final String showId;
    private final String theatreName;
    private final String screenName;
    private final LocalDateTime startTime;

    public ShowResponse(String showId, String theatreName, String screenName, LocalDateTime startTime) {
        this.showId = showId;
        this.theatreName = theatreName;
        this.screenName = screenName;
        this.startTime = startTime;
    }

    public String getShowId() {
        return showId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getScreenName() {
        return screenName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}