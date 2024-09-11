package com.cdl;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class PricingRuleTests {
    @Test
    void testRegularPricingRule() {
        PricingRule rule = new RegularPricingRule(new BigDecimal("0.50"));

        assertEquals(new BigDecimal("0.50"), rule.calculatePrice(1));
        assertEquals(new BigDecimal("1.00"), rule.calculatePrice(2));
        assertEquals(new BigDecimal("2.50"), rule.calculatePrice(5));
        assertEquals(BigDecimal.ZERO, rule.calculatePrice(0));
    }

    @Test
    void testSpecialPricingRule() {
        PricingRule rule = new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("1.30"));

        assertEquals(new BigDecimal("0.50"), rule.calculatePrice(1));
        assertEquals(new BigDecimal("1.00"), rule.calculatePrice(2));
        assertEquals(new BigDecimal("1.30"), rule.calculatePrice(3));
        assertEquals(new BigDecimal("1.80"), rule.calculatePrice(4));
        assertEquals(new BigDecimal("2.60"), rule.calculatePrice(6));
        assertEquals(BigDecimal.ZERO, rule.calculatePrice(0));
    }
}
