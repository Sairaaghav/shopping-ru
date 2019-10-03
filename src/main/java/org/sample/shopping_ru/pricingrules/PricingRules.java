package org.sample.shopping_ru.pricingrules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PricingRules {

	List<PriceRule> itemPricingRules = new ArrayList<PriceRule>();

	public List<PriceRule> getItemPricingRules() {
		return itemPricingRules;
	}

	public void setItemPricingRules(List<PriceRule> itemPricingRules) {
		this.itemPricingRules = itemPricingRules;
	}

	public RuleResult applyRule(String item, int userRequestedQuantity, BigDecimal unitPrice) {
		RuleResult ruleResult = new RuleResult();
		BigDecimal skuCost = new BigDecimal("0");
		
		Iterator<PriceRule> priceRuleIterator = itemPricingRules.iterator();
		while (priceRuleIterator.hasNext()) {
			PriceRule priceRule = (PriceRule) priceRuleIterator.next();
			if (item.equals(priceRule.getSku())) {

				// apply all rules for the SKU/ITEM
				List<Rule> rules = priceRule.getRules();
				Iterator<Rule> rulesIterator = rules.iterator();
				while (rulesIterator.hasNext()) {
					Rule rule = rulesIterator.next();
					int orderQuantity = rule.getOrderedQuantity();
					String condition = rule.getCondition();
					
					if ("PIECE_DISCOUNT".equals(condition)) {
						if (userRequestedQuantity >= orderQuantity) {
							ruleResult = calculateCostWithQuantity(ruleResult, userRequestedQuantity, orderQuantity,
									rule, unitPrice);
						} else {
							skuCost = skuCost.add(unitPrice.multiply(new BigDecimal(userRequestedQuantity)));
							userRequestedQuantity = 0;
							ruleResult.setSkuCost(skuCost);
							ruleResult.setQuantity(userRequestedQuantity);
						}
					} else if ("BULK_DISCOUNT".equals(condition)) {
						if (userRequestedQuantity >= orderQuantity) {
							ruleResult = calculateCostWithQuantity(ruleResult, userRequestedQuantity, orderQuantity,
									rule, unitPrice);
						} else {
							skuCost = skuCost.add(unitPrice.multiply(new BigDecimal(userRequestedQuantity)));
							ruleResult.setSkuCost(skuCost);
						}
						userRequestedQuantity = 0;
						ruleResult.setQuantity(userRequestedQuantity);
					} else if ("NIL_DISCOUNT".equals(condition)) {
						skuCost = skuCost.add(unitPrice.multiply(new BigDecimal(userRequestedQuantity)));
						ruleResult.setSkuCost(skuCost);
						userRequestedQuantity = 0;
						ruleResult.setQuantity(userRequestedQuantity);
					}
					
				}
			}
		}
		 
		return ruleResult;
	}

	private RuleResult calculateCostWithQuantity(RuleResult ruleResult, int userRequestedQuantity, int orderQuantity,
			Rule rule, BigDecimal unitPrice) {
		BigDecimal skuCost = new BigDecimal("0");

		Iterator<Result> resultIterator = rule.getResults().iterator();
		while (resultIterator.hasNext()) {
			Result result = (Result) resultIterator.next();
			String value = result.getValue();
			double multiplier = result.getMultiplier();
			if ("UNIT_PRICE".equals(value)) {
				skuCost = unitPrice.multiply(new BigDecimal(multiplier));
			} else if ("QUANTITY".equals(value)) {
				skuCost = new BigDecimal(userRequestedQuantity * multiplier);
			}
		}

		// reduce the quanitity for further calculations
		userRequestedQuantity = userRequestedQuantity - orderQuantity;

		ruleResult.setQuantity(userRequestedQuantity);
		ruleResult.setSkuCost(skuCost);
		return ruleResult;
	}
}
