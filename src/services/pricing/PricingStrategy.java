package services.pricing;

import models.Money;
import models.Show;
import models.ShowSeat;

public interface PricingStrategy {
    Money calculateExtraCharge(ShowSeat showSeat, Show show);
}