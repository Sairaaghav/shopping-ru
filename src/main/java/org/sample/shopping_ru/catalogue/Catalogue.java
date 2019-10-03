package org.sample.shopping_ru.catalogue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalogue {

	List<CatalogueItem> items = new ArrayList<CatalogueItem>();

	public List<CatalogueItem> getItems() {
		return items;
	}

	public void setItems(List<CatalogueItem> items) {
		this.items = items;
	}
	
	public BigDecimal getUnitPrice(String sku) {
		BigDecimal unitPrice = new BigDecimal("0");
		Iterator<CatalogueItem> catalogueItemIterator = items.iterator();
		while (catalogueItemIterator.hasNext()) {
			CatalogueItem catalogueItem = catalogueItemIterator.next();
			String skuInCatalogue = catalogueItem.getSku();
			if (sku.equals(skuInCatalogue)) {
				unitPrice = catalogueItem.getUnitPrice(); 
			}
		}
		return unitPrice;
	}
}
