package com.cdl;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a special pricing rule where a discounted price is applied for a specific quantity of items.
 */
public class SpecialPricingRule implements PricingRule{
    private final BigDecimal unitPrice;
    private final int specialQuantity;
    private final BigDecimal specialPrice;

    /**
     * Constructs a new SpecialPricingRule with the specified unit price, special quantity, and special price.
     *
     * @param unitPrice The regular price for a single unit of the item.
     * @param specialQuantity The quantity of items required for the special price to apply.
     * @param specialPrice The discounted price for the special quantity of items.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public SpecialPricingRule(BigDecimal unitPrice, int specialQuantity, BigDecimal specialPrice) {
        if (unitPrice.compareTo(BigDecimal.ZERO) < 0 || specialQuantity <= 0 || specialPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid parameters for SpecialPricingRule");
        }
        this.unitPrice = unitPrice;
        this.specialQuantity = specialQuantity;
        this.specialPrice = specialPrice;
    }

    /**
     * Calculates the total price for a given quantity of items, applying the special price where applicable.
     *
     * @param quantity The number of items to calculate the price for.
     * @return The total price for the given quantity, with special pricing applied.
     * @throws IllegalArgumentException if the quantity is negative.
     */
    @Override
    public BigDecimal calculatePrice(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        int specialDeals = quantity / specialQuantity;
        int remainingItems = quantity % specialQuantity;

        BigDecimal totalPrice = specialPrice.multiply(BigDecimal.valueOf(specialDeals))
                .add(unitPrice.multiply(BigDecimal.valueOf(remainingItems)));

        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
