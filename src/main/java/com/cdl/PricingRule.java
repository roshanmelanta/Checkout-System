package com.cdl;

import java.math.BigDecimal;

/**
 * Interface for pricing rules.
 */
public interface PricingRule {
    BigDecimal calculatePrice(int quantity);
}
