package models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Show {

    private final String id;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Show(Movie movie, Screen screen, LocalDateTime startTime) {
        Objects.requireNonNull(movie, "movie must not be null");
        Objects.requireNonNull(screen, "screen must not be null");
        Objects.requireNonNull(startTime, "startTime must not be null");

        this.id = UUID.randomUUID().toString();
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movie.getDurationMinutes()).plusMinutes(15);
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Show other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Show[" + movie.getTitle() + " @ " + screen.getName() + ", start=" + startTime + "]";
    }
}