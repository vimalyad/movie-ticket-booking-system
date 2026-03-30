package models;

import enums.SeatType;

import java.util.UUID;

public class Seat {
    private final String id;
    private final String row;
    private final int number;
    private final SeatType seatType;

    public Seat(String row, int number, SeatType seatType) {
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
}