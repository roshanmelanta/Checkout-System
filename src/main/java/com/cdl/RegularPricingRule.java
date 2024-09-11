package com.cdl;

import java.math.BigDecimal;

/**
 * Implementation of PricingRule for regular pricing.
 */
public class RegularPricingRule implements PricingRule{
    private final BigDecimal unitPrice;
    public RegularPricingRule(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public BigDecimal calculatePrice(int quantity) {
        return null;
    }
}
