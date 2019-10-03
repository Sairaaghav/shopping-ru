package org.sample.shopping_ru;

import java.io.File;
import java.math.BigDecimal;

import org.sample.shopping_ru.catalogue.Catalogue;
import org.sample.shopping_ru.checkout.Checkout;
import org.sample.shopping_ru.pricingrules.PricingRules;
import org.sample.shopping_ru.util.ResourceHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ShoppingRuApplicationTest extends TestCase {
	
	ObjectMapper mapper = new ObjectMapper();
	Catalogue catalogue = null;
	PricingRules pricingRules = null;
	Checkout checkout = null;
	
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ShoppingRuApplicationTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ShoppingRuApplicationTest.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		// prepare catalogue
		File catalogueFile = new ResourceHelper().getFileFromResources("catalogue.json");
		catalogue = mapper.readValue(catalogueFile, Catalogue.class);

		// build pricing rules
		File pricingRulesFile = new ResourceHelper().getFileFromResources("pricing_rules.json");
		pricingRules = mapper.readValue(pricingRulesFile, PricingRules.class);
		
		// our Checkout system
		checkout = new Checkout(catalogue, pricingRules);
	}

	public void testWithThreeQuantityOneItem() {
		checkout.scan("atv");
		checkout.scan("atv");
		checkout.scan("atv");
		
		BigDecimal expectedResult = new BigDecimal("219.00");
		
		BigDecimal actualResult = checkout.calculateTotalCost();
		
		assertEquals(expectedResult, actualResult);
	}

	public void testWithThreeItemsTwoQuantities() {
		// atv, atv, atv, ipd, vga
		checkout.scan("atv");
		checkout.scan("atv");
		checkout.scan("atv");
		checkout.scan("ipd");
		checkout.scan("ipd");
		checkout.scan("vga");
		checkout.scan("vga");
		
		BigDecimal expectedResult = new BigDecimal("1378.98");
		
		BigDecimal actualResult = checkout.calculateTotalCost();
		
		assertEquals(expectedResult, actualResult);
	}

	public void testWithFiveSuperSuperIPad() {
		// atv, atv, atv, ipd, vga
		checkout.scan("ipd");
		checkout.scan("ipd");
		checkout.scan("ipd");
		checkout.scan("ipd");
		checkout.scan("ipd");
		
		// we should get $499.99 per item
		BigDecimal expectedResult = new BigDecimal("2499.95");
		
		BigDecimal actualResult = checkout.calculateTotalCost();
		
		assertEquals(expectedResult, actualResult);
	}
}
