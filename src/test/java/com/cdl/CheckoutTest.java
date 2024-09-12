package com.cdl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutTest {
    private Checkout checkout;
    private PricingRuleFactory pricingRuleFactory;

    @BeforeEach
    void setUp() {
        pricingRuleFactory = new PricingRuleFactory();
        // Add some mock pricing rules
        pricingRuleFactory.addPricingRule("A", new MockPricingRule(BigDecimal.valueOf(50)));
        pricingRuleFactory.addPricingRule("B", new MockPricingRule(BigDecimal.valueOf(30)));
        checkout = new Checkout(pricingRuleFactory);
    }

    @Test
    void testScan_ValidSKU() {
        assertDoesNotThrow(() -> checkout.scan("A"));
        assertEquals(1, getCartQuantity("A"));
    }

    @Test
    void testCheckoutTotal_SingleItem() {
        checkout.scan("A");
        assertEquals(BigDecimal.valueOf(50), checkout.calculateTotal());
    }

    // Mock PricingRule for testing purposes
    private static class MockPricingRule implements PricingRule {
        private final BigDecimal price;

        MockPricingRule(BigDecimal price) {
            this.price = price;
        }

        @Override
        public BigDecimal calculatePrice(int quantity) {
            return price.multiply(BigDecimal.valueOf(quantity));
        }
    }

    // Helper method to get the quantity of an item in the cart
    private int getCartQuantity(String sku) {
        return checkout.getQuantity(sku);
    }
}
