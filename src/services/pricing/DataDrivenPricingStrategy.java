package services.pricing;

import enums.DayType;
import enums.ShowType;
import models.Money;
import models.PricingRule;
import models.Show;
import models.ShowSeat;

public class DataDrivenPricingStrategy implements PricingStrategy {
    private final PricingRule rule;

    public DataDrivenPricingStrategy(PricingRule rule) {
        this.rule = rule;
    }

    @Override
    public Money calculateExtraCharge(ShowSeat showSeat, Show show) {
        if (!matches(showSeat, show)) {
            return Money.zero(rule.getSurcharge().getCurrency());
        }
        return rule.getSurcharge();
    }

    private boolean matches(ShowSeat showSeat, Show show) {
        if (rule.getSeatTypeFilter() != null && rule.getSeatTypeFilter() != showSeat.getSeat().getSeatType()) {
            return false;
        }
        if (rule.getShowTypeFilter() != null && rule.getShowTypeFilter() != ShowType.from(show.getStartTime().toLocalTime())) {
            return false;
        }
        if (rule.getDayTypeFilter() != null && rule.getDayTypeFilter() != DayType.from(show.getStartTime().toLocalDate())) {
            return false;
        }
        return true;
    }
}