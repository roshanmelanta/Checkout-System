package com.cdl;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a regular pricing rule where the price is directly proportional to the quantity.
 */
public class RegularPricingRule implements PricingRule{
    private final BigDecimal unitPrice;

    /**
     * Constructs a new RegularPricingRule with the specified unit price.
     *
     * @param unitPrice The price for a single unit of the item.
     * @throws IllegalArgumentException if the unitPrice is negative.
     */
    public RegularPricingRule(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        this.unitPrice = unitPrice;
    }

    /**
     * Calculates the total price for a given quantity of items.
     *
     * @param quantity The number of items to calculate the price for.
     * @return The total price for the given quantity.
     * @throws IllegalArgumentException if the quantity is negative.
     */
    @Override
    public BigDecimal calculatePrice(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
