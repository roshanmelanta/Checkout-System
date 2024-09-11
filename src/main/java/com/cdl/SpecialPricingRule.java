package com.cdl;

import java.math.BigDecimal;

/**
 * Implementation of PricingRule for special bulk pricing.
 */
public class SpecialPricingRule implements PricingRule {
    private final BigDecimal unitPrice;
    private final int specialQuantity;
    private final BigDecimal specialPrice;

    public SpecialPricingRule(BigDecimal unitPrice, int specialQuantity, BigDecimal specialPrice) {
        this.unitPrice = unitPrice;
        this.specialQuantity = specialQuantity;
        this.specialPrice = specialPrice;
    }

    @Override
    public BigDecimal calculatePrice(int quantity) {
        return null;
    }
}
