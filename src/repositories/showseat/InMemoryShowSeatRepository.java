package repositories.showseat;

import models.ShowSeat;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryShowSeatRepository implements ShowSeatRepository {
    private final Map<String, ShowSeat> database = new ConcurrentHashMap<>();

    @Override
    public List<ShowSeat> findAllById(List<String> ids) {
        return ids.stream()
                .map(database::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowSeat> findByShowId(String showId) {
        return database.values().stream()
                .filter(seat -> seat.getShowId().equals(showId))
                .collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            database.put(seat.getId(), seat);
        }
    }
}