package com.cdl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the checkout process and calculates totals.
 * This class is responsible for scanning items, maintaining a cart,
 * and calculating the total price based on pricing rules.
 */
public class Checkout {
    private final Map<String, Integer> cart = new HashMap<>();

    private PricingRuleFactory pricingRuleFactory;

    public Checkout(PricingRuleFactory pricingRuleFactory) {
        this.pricingRuleFactory = pricingRuleFactory;
    }

    /**
     * Scans an item and adds it to the cart.
     *
     * @param sku The stock keeping unit (SKU) of the item to scan.
     * @throws NullPointerException if the SKU is null.
     * @throws IllegalArgumentException if the SKU is empty or invalid.
     */
    public void scan(String sku) {
        if (sku == null) {
            throw new NullPointerException("SKU cannot be null");
        }
        if (sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be empty");
        }
        if (!pricingRuleFactory.hasRule(sku)) {
            throw new IllegalArgumentException("Invalid SKU: " + sku);
        }
        // Add the item to the cart or increment its quantity if already present
        cart.put(sku, cart.getOrDefault(sku, 0) + 1);
    }

    /**
     * Calculates the total price of all items in the cart.
     *
     * @return The total price as a BigDecimal, rounded to 2 decimal places.
     */
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String sku = entry.getKey();
            int quantity = entry.getValue();
            PricingRule rule = pricingRuleFactory.getPricingRule(sku);
            total = total.add(rule.calculatePrice(quantity));
        }
        // Round to 2 decimal places
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public void clear() {
        cart.clear();
    }

    // For testing purpose
    int getQuantity(String sku) {
        return cart.getOrDefault(sku, 0);
    }
}