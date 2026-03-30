package repositories.booking;

import enums.BookingStatus;
import models.Booking;

import java.util.List;

public interface BookingRepository {
    void save(Booking booking);

    Booking findById(String id);

    List<Booking> findByStatus(BookingStatus status);
}