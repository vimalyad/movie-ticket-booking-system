package services.pricing;

import models.Money;
import models.Show;
import models.ShowSeat;

import java.math.BigDecimal;

public class SeatTypePricingStrategy implements PricingStrategy {

    @Override
    public Money calculateExtraCharge(ShowSeat showSeat, Show show) {
        String currency = "INR";

        return switch (showSeat.getSeat().getSeatType()) {
            case RECLINER -> Money.of(BigDecimal.valueOf(150), currency);
            case PREMIUM -> Money.of(BigDecimal.valueOf(80), currency);
            case VIP -> Money.of(BigDecimal.valueOf(300), currency);
            case REGULAR -> Money.zero(currency);
        };
    }
}