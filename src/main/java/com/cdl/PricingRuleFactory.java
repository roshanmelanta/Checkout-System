package com.cdl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Factory for creating and managing pricing rules.
 * This class provides methods to add, retrieve, and check for pricing rules associated with SKUs.
 */
public class PricingRuleFactory {
    private final Map<String, PricingRule> rules = new HashMap<>();

    /**
     * Validates the SKU string.
     *
     * @param sku The Stock Keeping Unit (SKU) to validate.
     * @throws IllegalArgumentException if the SKU is empty.
     * @throws NullPointerException if the SKU is null.
     */
    private void validateSku(String sku) {
        Objects.requireNonNull(sku, "SKU cannot be null");
        if (sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be empty");
        }
    }

    /**
     * Adds a pricing rule for a specific SKU.
     *
     * @param sku  The Stock Keeping Unit (SKU) for the item.
     * @param rule The PricingRule to be associated with the SKU.
     * @throws NullPointerException if the rule is null.
     */
    public void addPricingRule(String sku, PricingRule rule) {
        validateSku(sku);
        Objects.requireNonNull(rule, "Pricing rule cannot be null");
        rules.put(sku.trim(), rule);
    }

    /**
     * Retrieves the pricing rule for a given SKU.
     *
     * @param sku The Stock Keeping Unit (SKU) for the item.
     * @return The PricingRule associated with the SKU, or null if not found.
     */
    public PricingRule getPricingRule(String sku) {
        validateSku(sku);
        return rules.get(sku.trim());
    }

    /**
     * Checks if a pricing rule exists for a given SKU.
     *
     * @param sku The Stock Keeping Unit (SKU) to check.
     * @return true if a pricing rule exists for the SKU, false otherwise.
     */
    public boolean hasRule(String sku) {
        validateSku(sku);
        return rules.containsKey(sku.trim());
    }
}