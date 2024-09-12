package com.cdl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for PricingRuleFactory implementations.
 * Covers various scenarios and edge cases.
 */
class PricingRuleFactoryTest {

    private PricingRuleFactory factory;

    @BeforeEach
    void setUp() {
        factory = new PricingRuleFactory();
    }

    /**
     * Test adding multiple pricing rules and retrieving them.
     */
    @Test
    void testAddMultiplePricingRules() {
        PricingRule rule1 = new MockPricingRule();
        PricingRule rule2 = new MockPricingRule();
        factory.addPricingRule("A", rule1);
        factory.addPricingRule("B", rule2);
        assertEquals(rule1, factory.getPricingRule("A"));
        assertEquals(rule2, factory.getPricingRule("B"));
    }

    /**
     * Test checking that the class properly handles invalid inputs (null and empty strings) for all methods.
     */
    @Test
    void testInvalidInputs() {
        PricingRule rule = new MockPricingRule();

        assertThrows(NullPointerException.class, () -> factory.addPricingRule(null, rule));
        assertThrows(IllegalArgumentException.class, () -> factory.addPricingRule("", rule));
        assertThrows(NullPointerException.class, () -> factory.addPricingRule("A", null));

        assertThrows(NullPointerException.class, () -> factory.getPricingRule(null));
        assertThrows(NullPointerException.class, () -> factory.hasRule(null));
    }

    /**
     * Test checking if a rule exists for a SKU.
     */
    @Test
    void testHasRule() {
        factory.addPricingRule("A", new MockPricingRule());
        assertTrue(factory.hasRule("A"));
        assertFalse(factory.hasRule("B"));
    }

    /**
     * Mock implementation of PricingRule for testing purposes.
     */
    private static class MockPricingRule implements PricingRule {
        @Override
        public BigDecimal calculatePrice(int quantity) {
            // Simple implementation for testing
            return BigDecimal.valueOf(quantity);
        }
    }
}