package enums;

import java.time.LocalTime;

public enum ShowType {

    MORNING(LocalTime.of(6, 0), LocalTime.of(11, 59)),
    AFTERNOON(LocalTime.of(12, 0), LocalTime.of(16, 59)),
    EVENING(LocalTime.of(17, 0), LocalTime.of(20, 59)),
    NIGHT(LocalTime.of(21, 0), LocalTime.of(23, 59));

    private final LocalTime from;
    private final LocalTime to;

    ShowType(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public static ShowType from(LocalTime time) {
        for (ShowType slot : values()) {
            if (!time.isBefore(slot.from) && !time.isAfter(slot.to)) {
                return slot;
            }
        }
        return NIGHT;
    }

    public String label() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
