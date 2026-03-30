package models;

import enums.SeatType;

import java.util.*;

public class Screen {

    private final String id;
    private final int screenNumber;
    private final String name;
    private final List<Seat> seats;

    public Screen(int screenNumber, String name) {
        if (screenNumber <= 0)
            throw new IllegalArgumentException("screenNumber must be positive, got: " + screenNumber);
        Objects.requireNonNull(name, "name must not be null");
        if (name.isBlank())
            throw new IllegalArgumentException("screen name must not be blank");

        this.id = UUID.randomUUID().toString();
        this.screenNumber = screenNumber;
        this.name = name.trim();
        this.seats = new ArrayList<>();
    }

    public void addRow(String rowLabel, int seatCount, SeatType seatType) {
        Objects.requireNonNull(rowLabel, "rowLabel must not be null");
        Objects.requireNonNull(seatType, "seatType must not be null");

        if (rowLabel.isBlank())
            throw new IllegalArgumentException("rowLabel must not be blank");
        if (seatCount <= 0)
            throw new IllegalArgumentException("seatCount must be positive, got: " + seatCount);

        for (int i = 1; i <= seatCount; i++) {
            seats.add(new Seat(rowLabel, i, seatType));
        }
    }


    public String getId() {
        return id;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public String getName() {
        return name;
    }

    public int getTotalCapacity() {
        return seats.size();
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public Seat getSeatById(String seatId) {
        Objects.requireNonNull(seatId, "seatId must not be null");
        return seats.stream()
                .filter(s -> s.getId().equals(seatId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Seat not found in screen " + name + ": " + seatId));
    }

    public List<Seat> getSeatsByType(SeatType seatType) {
        Objects.requireNonNull(seatType, "seatType must not be null");
        return seats.stream()
                .filter(s -> s.getSeatType() == seatType)
                .toList();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screen other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Screen[" + name + ", capacity=" + getTotalCapacity() + "]";
    }
}