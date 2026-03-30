package models.users;

import enums.UserRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Customer extends User {
    private final List<Booking> bookingList;

    public Customer(String name, String email, String phone) {
        super(name, email, phone, UserRole.CUSTOMER);
        this.bookingList = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        Objects.requireNonNull(booking, "booking must not be null");
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }


}
