package repositories.theatre;

import models.Theatre;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTheatreRepository implements TheatreRepository {
    private final Map<String, Theatre> database = new ConcurrentHashMap<>();

    @Override
    public void save(Theatre theatre) {
        database.put(theatre.getId(), theatre);
    }

    @Override
    public Theatre findById(String id) {
        return database.get(id);
    }

    @Override
    public List<Theatre> findByCity(String city) {
        return database.values().stream()
                .filter(theatre -> theatre.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}