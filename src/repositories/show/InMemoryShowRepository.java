package repositories.show;

import models.Show;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryShowRepository implements ShowRepository {
    private final Map<String, Show> database = new ConcurrentHashMap<>();

    public void save(Show show) {
        database.put(show.getId(), show);
    }

    @Override
    public Show findById(String id) {
        Show show = database.get(id);
        if (show == null) {
            throw new IllegalArgumentException("Show not found with id: " + id);
        }
        return show;
    }

    @Override
    public List<Show> findByMovieAndCity(String movieId, String city) {
        return database.values().stream()
                .filter(show -> show.getMovieId().equals(movieId))
                .collect(Collectors.toList());
    }
}
