package controllers;

import dto.AddPricingRuleRequest;
import models.Money;
import models.PricingRule;
import repositories.pricingrule.PricingRuleRepository;

public class AdminController {
    private final PricingRuleRepository pricingRuleRepository;

    public AdminController(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public void addPricingRule(AddPricingRuleRequest request) {
        PricingRule rule = new PricingRule(
                request.getName(),
                Money.of(request.getSurchargeAmount(), request.getCurrency()),
                request.getSeatType(),
                request.getShowType(),
                request.getDayType()
        );
        pricingRuleRepository.save(rule);
    }
}