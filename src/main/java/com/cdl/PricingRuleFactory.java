package com.cdl;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating and managing pricing rules.
 * This class provides methods to add, retrieve, and check for pricing rules associated with SKUs.
 */
public class PricingRuleFactory {
    private final Map<String, PricingRule> rules = new HashMap<>();

    /**
     * Adds a pricing rule for a specific SKU.
     *
     * @param sku  The Stock Keeping Unit (SKU) for the item.
     * @param rule The PricingRule to be associated with the SKU.
     */
    public void addPricingRule(String sku, PricingRule rule) {
        // TODO: Implement method
    }

    /**
     * Retrieves the pricing rule for a given SKU.
     *
     * @param sku The Stock Keeping Unit (SKU) for the item.
     * @return The PricingRule associated with the SKU, or null if not found.
     */
    public PricingRule getPricingRule(String sku) {
        // TODO: Implement method
        return null;
    }

    /**
     * Checks if a pricing rule exists for a given SKU.
     *
     * @param sku The Stock Keeping Unit (SKU) to check.
     * @return true if a pricing rule exists for the SKU, false otherwise.
     */
    public boolean hasRule(String sku) {
        // TODO: Implement method
        return false;
    }
}