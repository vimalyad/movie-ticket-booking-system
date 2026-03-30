package repositories.movie;

import models.Movie;

import java.util.List;

public interface MovieRepository {
    Movie findById(String id);

    List<Movie> findMoviesPlayingInCity(String city);
}
