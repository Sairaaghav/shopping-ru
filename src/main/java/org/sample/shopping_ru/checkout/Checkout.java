package org.sample.shopping_ru.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.sample.shopping_ru.catalogue.Catalogue;
import org.sample.shopping_ru.pricingrules.PricingRules;
import org.sample.shopping_ru.pricingrules.RuleResult;

public class Checkout {

	Catalogue catalogue;
	PricingRules pricingRules;
	Map<String, Integer> itemMap = new HashMap<String, Integer>();
	
	public Checkout(Catalogue catalogue, PricingRules pricingRules) {
		this.catalogue = catalogue;
		this.pricingRules = pricingRules;
	}
	
	public void scan(String sku) {
		if (itemMap.containsKey(sku)) {
			Integer count = itemMap.get(sku);
			itemMap.put(sku, count + 1);
		} else {
			itemMap.put(sku, new Integer(1));
		}
	}
	
	public BigDecimal calculateTotalCost() {
		BigDecimal totalCost = new BigDecimal("0");

		Iterator<Entry<String, Integer>> itemMapEntrySetIterator = itemMap.entrySet().iterator();
		while (itemMapEntrySetIterator.hasNext()) {
			Map.Entry<java.lang.String, java.lang.Integer> entry = itemMapEntrySetIterator.next();
			String item = entry.getKey();
			int quantity = entry.getValue();
			BigDecimal unitPrice = catalogue.getUnitPrice(item);
			
			while (quantity > 0) {
				RuleResult ruleResult = pricingRules.applyRule(item, quantity, unitPrice);
				quantity = ruleResult.getQuantity();
				totalCost = totalCost.add(ruleResult.getSkuCost());
			}
		}
		
		return totalCost;
	}

	public void clear() {
		itemMap.clear();
	}
}
