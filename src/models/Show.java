package models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Show {
    private final String id = UUID.randomUUID().toString();
    private final String movieId;
    private final String screenId;
    private final LocalDateTime startTime;

    public Show(String movieId, String screenId, LocalDateTime startTime) {
        this.movieId = movieId;
        this.screenId = screenId;
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getScreenId() {
        return screenId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}