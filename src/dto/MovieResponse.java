package dto;

import java.util.List;

public class MovieResponse {
    private final String id;
    private final String title;
    private final List<String> languages;
    private final List<String> genres;

    public MovieResponse(String id, String title, List<String> languages, List<String> genres) {
        this.id = id;
        this.title = title;
        this.languages = languages;
        this.genres = genres;
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
}
