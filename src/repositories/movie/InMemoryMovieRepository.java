package repositories.movie;

import models.Movie;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryMovieRepository implements MovieRepository {
    private final Map<String, Movie> database = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> cityToMovieIds = new ConcurrentHashMap<>();

    public void save(Movie movie) {
        database.put(movie.getId(), movie);
    }

    public void mapMovieToCity(String movieId, String city) {
        cityToMovieIds.computeIfAbsent(city.toLowerCase(), k -> ConcurrentHashMap.newKeySet()).add(movieId);
    }

    @Override
    public Movie findById(String id) {
        Movie movie = database.get(id);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found with id: " + id);
        }
        return movie;
    }

    @Override
    public List<Movie> findMoviesPlayingInCity(String city) {
        Set<String> movieIds = cityToMovieIds.getOrDefault(city.toLowerCase(), Set.of());
        return movieIds.stream()
                .map(database::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}