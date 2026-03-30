package repositories.booking;

import enums.BookingStatus;
import models.Booking;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBookingRepository implements BookingRepository {
    private final Map<String, Booking> database = new ConcurrentHashMap<>();

    @Override
    public void save(Booking booking) {
        database.put(booking.getId(), booking);
    }

    @Override
    public Booking findById(String id) {
        Booking booking = database.get(id);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found with id: " + id);
        }
        return booking;
    }

    @Override
    public List<Booking> findByStatus(BookingStatus status) {
        return database.values().stream()
                .filter(booking -> booking.getStatus() == status)
                .collect(Collectors.toList());
    }
}
