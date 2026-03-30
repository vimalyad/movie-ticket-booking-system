package models;

import enums.SeatType;

import java.util.Objects;
import java.util.UUID;

public class Seat {

    private final String id;
    private final String row;
    private final int number;
    private final SeatType seatType;

    public Seat(String row, int number, SeatType seatType) {
        Objects.requireNonNull(row, "row must not be null");
        Objects.requireNonNull(seatType, "seatType must not be null");
        if (row.isBlank()) throw new IllegalArgumentException("row must not be blank");
        if (number <= 0) throw new IllegalArgumentException("seat number must be positive, got: " + number);
        this.id = UUID.randomUUID().toString();
        this.row = row.toUpperCase().trim();
        this.number = number;
        this.seatType = seatType;
    }

    public String getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public String getSeatLabel() {
        return row + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Seat[" + getSeatLabel() + ", " + seatType + "]";
    }
}
