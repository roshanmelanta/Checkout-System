package com.cdl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PricingRuleFactoryTest {

    private PricingRuleFactory factory;

    @BeforeEach
    void setUp() {
        factory = new PricingRuleFactory();
    }

    /**
     * Test adding a pricing rule and then retrieving it.
     */
    @Test
    void testAddAndGetPricingRule() {
        PricingRule rule = new MockPricingRule();
        factory.addPricingRule("A", rule);
        assertEquals(rule, factory.getPricingRule("A"), "Should return the correct rule for SKU A");
    }

    /**
     * Test the hasRule method for both existing and non-existing SKUs.
     */
    @Test
    void testHasRule() {
        factory.addPricingRule("A", new MockPricingRule());
        assertTrue(factory.hasRule("A"), "Should return true for existing SKU");
        assertFalse(factory.hasRule("B"), "Should return false for non-existent SKU");
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