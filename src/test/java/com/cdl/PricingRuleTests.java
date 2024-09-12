package com.cdl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for PricingRule implementations.
 * Covers RegularPricingRule and SpecialPricingRule with various scenarios and edge cases.
 */
class PricingRuleTests {

    @Nested
    class RegularPricingRuleTest {

        @ParameterizedTest
        @CsvSource({
                "0.50, 1, 0.50",
                "0.50, 2, 1.00",
                "0.50, 5, 2.50",
                "0.50, 10, 5.00",
                "0.33, 3, 0.99",  // Tests rounding
                "0.10, 100, 10.00"
        })
        void testRegularPricingCalculation(BigDecimal unitPrice, int quantity, BigDecimal expectedTotal) {
            PricingRule rule = new RegularPricingRule(unitPrice);
            assertEquals(expectedTotal, rule.calculatePrice(quantity));
        }

        @Test
        void testRegularPricingEdgeCases() {
            PricingRule rule = new RegularPricingRule(new BigDecimal("0.50"));
            assertEquals(new BigDecimal("0.00"), rule.calculatePrice(0));
            assertEquals(new BigDecimal("50000.00").setScale(2), rule.calculatePrice(100000));
        }

        @Test
        void testRegularPricingInvalidInputs() {
            assertThrows(IllegalArgumentException.class, () -> new RegularPricingRule(new BigDecimal("-0.01")));
            PricingRule rule = new RegularPricingRule(BigDecimal.ONE);
            assertThrows(IllegalArgumentException.class, () -> rule.calculatePrice(-1));
        }
    }

    @Nested
    class SpecialPricingRuleTest {

        @ParameterizedTest
        @CsvSource({
                "0.50, 3, 1.30, 1, 0.50",
                "0.50, 3, 1.30, 2, 1.00",
                "0.50, 3, 1.30, 3, 1.30",
                "0.50, 3, 1.30, 4, 1.80",
                "0.50, 3, 1.30, 6, 2.60",
                "0.50, 3, 1.30, 7, 3.10"
        })
        void testSpecialPricingCalculation(BigDecimal unitPrice, int specialQuantity, BigDecimal specialPrice,
                                           int quantity, BigDecimal expectedTotal) {
            PricingRule rule = new SpecialPricingRule(unitPrice, specialQuantity, specialPrice);
            assertEquals(expectedTotal, rule.calculatePrice(quantity));
        }

        @Test
        void testSpecialPricingEdgeCases() {
            PricingRule rule = new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("1.30"));
            assertEquals(new BigDecimal("0.00"), rule.calculatePrice(0));
            assertEquals(new BigDecimal("43333.40"), rule.calculatePrice(100000));  // 33333 * 1.30 + 1 * 0.50
        }

        @Test
        void testSpecialPricingInvalidInputs() {
            assertThrows(IllegalArgumentException.class,
                    () -> new SpecialPricingRule(new BigDecimal("-0.01"), 3, new BigDecimal("1.30")));
            assertThrows(IllegalArgumentException.class,
                    () -> new SpecialPricingRule(new BigDecimal("0.50"), 0, new BigDecimal("1.30")));
            assertThrows(IllegalArgumentException.class,
                    () -> new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("-0.01")));

            PricingRule rule = new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("1.30"));
            assertThrows(IllegalArgumentException.class, () -> rule.calculatePrice(-1));
        }

        @Test
        void testSpecialPricingNonDiscountCases() {
            PricingRule higherSpecialPrice = new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("2.00"));
            assertEquals(new BigDecimal("2.00"), higherSpecialPrice.calculatePrice(3));

            PricingRule sameAsRegularPrice = new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("1.50"));
            assertEquals(new BigDecimal("1.50"), sameAsRegularPrice.calculatePrice(3));
        }
    }
}