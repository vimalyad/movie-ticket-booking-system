package models;

import enums.DayType;
import enums.SeatType;
import enums.ShowType;

import java.util.UUID;

public class PricingRule {
    private final String id = UUID.randomUUID().toString();
    private final String name;
    private final Money surcharge;
    private final SeatType seatTypeFilter;
    private final ShowType showTypeFilter;
    private final DayType dayTypeFilter;
    private boolean isActive = true;

    public PricingRule(String name, Money surcharge, SeatType seatTypeFilter, ShowType showTypeFilter, DayType dayTypeFilter) {
        this.name = name;
        this.surcharge = surcharge;
        this.seatTypeFilter = seatTypeFilter;
        this.showTypeFilter = showTypeFilter;
        this.dayTypeFilter = dayTypeFilter;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getSurcharge() {
        return surcharge;
    }

    public SeatType getSeatTypeFilter() {
        return seatTypeFilter;
    }

    public ShowType getShowTypeFilter() {
        return showTypeFilter;
    }

    public DayType getDayTypeFilter() {
        return dayTypeFilter;
    }
}