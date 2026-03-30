package repositories.pricingrule;

import models.PricingRule;

import java.util.List;

public interface PricingRuleRepository {
    void save(PricingRule rule);

    List<PricingRule> findActiveRules();
}