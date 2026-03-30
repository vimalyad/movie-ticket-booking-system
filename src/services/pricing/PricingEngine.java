package services.pricing;

import models.Money;
import models.PricingRule;
import models.Show;
import models.ShowSeat;
import repositories.pricingrule.PricingRuleRepository;

import java.util.List;

public class PricingEngine {
    private final PricingRuleRepository ruleRepository;

    public PricingEngine(PricingRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public Money calculateFinalPrice(ShowSeat showSeat, Show show, Money basePrice) {
        Money total = basePrice;
        List<PricingRule> activeRules = ruleRepository.findActiveRules();

        for (PricingRule rule : activeRules) {
            PricingStrategy strategy = new DataDrivenPricingStrategy(rule);
            total = total.add(strategy.calculateExtraCharge(showSeat, show));
        }
        return total;
    }
}