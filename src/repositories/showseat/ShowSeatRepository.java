package repositories.showseat;

import models.ShowSeat;

import java.util.List;

public interface ShowSeatRepository {
    List<ShowSeat> findAllById(List<String> ids);

    List<ShowSeat> findByShowId(String showId);

    void saveAll(List<ShowSeat> seats);
}