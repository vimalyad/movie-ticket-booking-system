package repositories.pricingrule;

import models.PricingRule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryPricingRuleRepository implements PricingRuleRepository {
    private final Map<String, PricingRule> database = new ConcurrentHashMap<>();

    @Override
    public void save(PricingRule rule) {
        database.put(rule.getId(), rule);
    }

    @Override
    public List<PricingRule> findActiveRules() {
        return database.values().stream()
                .filter(PricingRule::isActive)
                .collect(Collectors.toList());
    }
}