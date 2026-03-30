package models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Movie {

    private final String id;
    private final String title;
    private final List<String> languages;
    private final List<String> genres;
    private final int durationMinutes;
    private final String description;
    private final LocalDate releaseDate;
    private final String rating;

    private Movie(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.title = builder.title;
        this.languages = builder.languages;
        this.genres = builder.genres;
        this.durationMinutes = builder.durationMinutes;
        this.description = builder.description;
        this.releaseDate = builder.releaseDate;
        this.rating = builder.rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public boolean isReleased() {
        return !LocalDate.now().isBefore(releaseDate);
    }

    public static class Builder {

        private final String title;
        private final List<String> languages;
        private final List<String> genres;
        private final int durationMinutes;
        private final LocalDate releaseDate;

        private String description = "";
        private String rating = "UA";

        public Builder(String title,
                       List<String> languages,
                       List<String> genres,
                       int durationMinutes,
                       LocalDate releaseDate) {

            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(languages, "languages must not be null");
            Objects.requireNonNull(genres, "genres must not be null");
            Objects.requireNonNull(releaseDate, "releaseDate must not be null");

            if (title.isBlank())
                throw new IllegalArgumentException("title must not be blank");
            if (languages.isEmpty())
                throw new IllegalArgumentException("at least one language required");
            if (genres.isEmpty())
                throw new IllegalArgumentException("at least one genre required");
            if (durationMinutes <= 0)
                throw new IllegalArgumentException("durationMinutes must be positive");

            this.title = title.trim();
            this.languages = List.copyOf(languages);
            this.genres = List.copyOf(genres);
            this.durationMinutes = durationMinutes;
            this.releaseDate = releaseDate;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder rating(String rating) {
            this.rating = rating;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    @Override
    public String toString() {
        return "Movie[" + title + ", " + languages + ", " + durationMinutes + "min]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}