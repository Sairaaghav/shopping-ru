package org.sample.shopping_ru;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import org.sample.shopping_ru.catalogue.Catalogue;
import org.sample.shopping_ru.checkout.Checkout;
import org.sample.shopping_ru.pricingrules.PricingRules;
import org.sample.shopping_ru.util.ResourceHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Shopping RU.
 *
 */
public class ShoppingRuApplication {
	public static void main(String[] args) throws FileNotFoundException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		// prepare catalogue
		File catalogueFile = new ResourceHelper().getFileFromResources("catalogue.json");
		Catalogue catalogue = mapper.readValue(catalogueFile, Catalogue.class);

		// build pricing rules
		File pricingRulesFile = new ResourceHelper().getFileFromResources("pricing_rules.json");
		PricingRules pricingRules = mapper.readValue(pricingRulesFile, PricingRules.class);

		System.out.println("Shopping Ru Application.");
		System.out.println(
				"Please enter your items (SKU code), separated by comma, to place an Order and get its Total Cost. :: ");

		Checkout checkout = new Checkout(catalogue, pricingRules);
		Scanner scanner = new Scanner(System.in);
		String orderedItemsFromUser = scanner.nextLine().trim();
		if (orderedItemsFromUser.isEmpty()) {
			System.out.println("Total Cost : " + BigDecimal.ZERO);
		} else {
			String[] items = orderedItemsFromUser.toLowerCase().split(",");
			for (String item : items) {
				checkout.scan(item.trim());
			}
			System.out.println(checkout.calculateTotalCost());
		}
	}
}
