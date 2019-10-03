package org.sample.shopping_ru.pricingrules;

import java.util.ArrayList;
import java.util.List;

public class Rule {

	int orderedQuantity;
	String condition;
	List<Result> results = new ArrayList<Result>();
	
	public int getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public List<Result> getResults() {
		return results;
	}
	public void setResults(List<Result> results) {
		this.results = results;
	}
}
