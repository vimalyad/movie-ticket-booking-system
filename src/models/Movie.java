package models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Movie {
    private final String id = UUID.randomUUID().toString();
    private final String title;
    private final List<String> languages;
    private final List<String> genres;
    private final int durationMinutes;
    private final LocalDate releaseDate;

    public Movie(String title, List<String> languages, List<String> genres, int durationMinutes, LocalDate releaseDate) {
        this.title = title;
        this.languages = languages;
        this.genres = genres;
        this.durationMinutes = durationMinutes;
        this.releaseDate = releaseDate;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}