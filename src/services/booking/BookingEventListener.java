package services.booking;

import models.Booking;

// it just mocks what we will be doing after changes in a booking
// Observer Pattern
public interface BookingEventListener {
    void onBookingConfirmed(Booking booking);

    void onBookingCancelled(Booking booking);

    void onBookingExpired(Booking booking);
}
