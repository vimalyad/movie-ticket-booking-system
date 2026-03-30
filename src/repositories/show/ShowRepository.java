package repositories.show;

import models.Show;

import java.util.List;

public interface ShowRepository {
    Show findById(String id);

    List<Show> findByMovieAndCity(String movieId, String city);
}