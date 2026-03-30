package repositories.theatre;

import models.Theatre;

import java.util.List;

public interface TheatreRepository {
    Theatre findById(String id);

    List<Theatre> findByCity(String city);

    void save(Theatre theatre);
}