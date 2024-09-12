package com.cdl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the checkout process and calculates totals.
 */
public class Checkout {
    private final Map<String, Integer> cart = new HashMap<>();
    private PricingRuleFactory pricingRuleFactory;

    public Checkout(PricingRuleFactory pricingRuleFactory) {
        this.pricingRuleFactory = pricingRuleFactory;
    }

    public void scan(String sku) {
        // TODO: Implement scan method
    }

    public BigDecimal calculateTotal() {
        // TODO: Implement calculateTotal method
        return BigDecimal.ZERO;
    }

    // Method for testing purposes
    int getQuantity(String sku) {
        return cart.getOrDefault(sku, 0);
    }
}