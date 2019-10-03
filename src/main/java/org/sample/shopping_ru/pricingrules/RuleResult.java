package org.sample.shopping_ru.pricingrules;

import java.math.BigDecimal;

public class RuleResult {

	private BigDecimal skuCost;
	
	private int quantity;

	public BigDecimal getSkuCost() {
		return skuCost;
	}

	public void setSkuCost(BigDecimal skuCost) {
		this.skuCost = skuCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
