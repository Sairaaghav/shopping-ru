package org.sample.shopping_ru.pricingrules;

import java.util.ArrayList;
import java.util.List;

public class PriceRule {
	
	String sku;
	List<Rule> rules = new ArrayList<Rule>();
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
}
