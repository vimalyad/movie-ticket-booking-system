package models;

import java.util.*;

public class Theatre {

    private final String id;
    private final String name;
    private final String location;
    private final List<Screen> screens;

    public Theatre(String name, String location) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(location, "address must not be null");

        if (name.isBlank())
            throw new IllegalArgumentException("theatre name must not be blank");

        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
        this.location = location;
        this.screens = new ArrayList<>();
    }

    public void addScreen(Screen screen) {
        Objects.requireNonNull(screen, "screen must not be null");

        boolean duplicateNumber = screens.stream()
                .anyMatch(s -> s.getScreenNumber() == screen.getScreenNumber());

        if (duplicateNumber) {
            throw new IllegalArgumentException(
                    "Screen number " + screen.getScreenNumber()
                            + " already exists in theatre: " + name
            );
        }

        screens.add(screen);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<Screen> getScreens() {
        return Collections.unmodifiableList(screens);
    }

    public int getTotalCapacity() {
        return screens.stream()
                .mapToInt(Screen::getTotalCapacity)
                .sum();
    }

    public Screen getScreenById(String screenId) {
        Objects.requireNonNull(screenId, "screenId must not be null");
        return screens.stream()
                .filter(s -> s.getId().equals(screenId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Screen not found in theatre " + name + ": " + screenId));
    }

    public Screen getScreenByNumber(int screenNumber) {
        return screens.stream()
                .filter(s -> s.getScreenNumber() == screenNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Screen number " + screenNumber + " not found in theatre: " + name));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theatre other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Theatre[" + name + ", " + location
                + ", screens=" + screens.size() + "]";
    }
}
