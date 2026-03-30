package models;

import java.util.UUID;

public class Screen {
    private final String id = UUID.randomUUID().toString();
    private final String theatreId;
    private final String name;

    public Screen(String theatreId, String name) {
        this.theatreId = theatreId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public String getName() {
        return name;
    }
}