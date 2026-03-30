package dto;

import enums.DayType;
import enums.SeatType;
import enums.ShowType;

public class AddPricingRuleRequest {
    private String name;
    private double surchargeAmount;
    private String currency;
    private SeatType seatType;
    private ShowType showType;
    private DayType dayType;

    public AddPricingRuleRequest(String name, double surchargeAmount, String currency, SeatType seatType, ShowType showType, DayType dayType) {
        this.name = name;
        this.surchargeAmount = surchargeAmount;
        this.currency = currency;
        this.seatType = seatType;
        this.showType = showType;
        this.dayType = dayType;
    }

    public String getName() {
        return name;
    }

    public double getSurchargeAmount() {
        return surchargeAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public ShowType getShowType() {
        return showType;
    }

    public DayType getDayType() {
        return dayType;
    }
}