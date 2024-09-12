package com.cdl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Comprehensive test suite for PricingRuleFactory implementations.
 * Covers various scenarios and edge cases.
 */
class PricingRuleFactoryTest {

    private PricingRuleFactory pricingRuleFactory;

    @Mock
    private PricingRule regularPricingRule;

    @Mock
    private PricingRule specialPricingRule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pricingRuleFactory = new PricingRuleFactory();

        // Set up regular pricing rule
        when(regularPricingRule.calculatePrice(anyInt())).thenAnswer(invocation -> {
            int quantity = invocation.getArgument(0);
            return BigDecimal.valueOf(0.50).multiply(BigDecimal.valueOf(quantity));
        });

        // Set up special pricing rule (2 for 0.45)
        when(specialPricingRule.calculatePrice(anyInt())).thenAnswer(invocation -> {
            int quantity = invocation.getArgument(0);
            int specialDeals = quantity / 2;
            int remainder = quantity % 2;
            return BigDecimal.valueOf(0.45).multiply(BigDecimal.valueOf(specialDeals))
                    .add(BigDecimal.valueOf(0.50).multiply(BigDecimal.valueOf(remainder)));
        });
    }

    @Test
    void testAddPricingRule_RegularRule() {
        pricingRuleFactory.addPricingRule("A", regularPricingRule);
        assertEquals(regularPricingRule, pricingRuleFactory.getPricingRule("A"));
    }

    @Test
    void testAddPricingRule_SpecialRule() {
        pricingRuleFactory.addPricingRule("B", specialPricingRule);
        assertEquals(specialPricingRule, pricingRuleFactory.getPricingRule("B"));
    }

    @Test
    void testAddPricingRule_MultipleRules() {
        pricingRuleFactory.addPricingRule("A", regularPricingRule);
        pricingRuleFactory.addPricingRule("B", specialPricingRule);

        assertTrue(pricingRuleFactory.hasRule("A"));
        assertTrue(pricingRuleFactory.hasRule("B"));
    }

    @Test
    void testGetPricingRule_ExistingRules() {
        pricingRuleFactory.addPricingRule("A", regularPricingRule);
        pricingRuleFactory.addPricingRule("B", specialPricingRule);

        assertEquals(regularPricingRule, pricingRuleFactory.getPricingRule("A"));
        assertEquals(specialPricingRule, pricingRuleFactory.getPricingRule("B"));
    }

    @Test
    void testGetPricingRule_NonExistentRule() {
        assertNull(pricingRuleFactory.getPricingRule("C"));
    }

    @Test
    void testAddPricingRule_NullSku() {
        assertThrows(NullPointerException.class, () -> pricingRuleFactory.addPricingRule(null, regularPricingRule));
    }

    @Test
    void testAddPricingRule_EmptySku() {
        assertThrows(IllegalArgumentException.class, () -> pricingRuleFactory.addPricingRule("", regularPricingRule));
    }

    @Test
    void testAddPricingRule_NullRule() {
        assertThrows(NullPointerException.class, () -> pricingRuleFactory.addPricingRule("A", null));
    }

    @Test
    void testGetPricingRule_NullSku() {
        assertThrows(NullPointerException.class, () -> pricingRuleFactory.getPricingRule(null));
    }

    @Test
    void testHasRule() {
        pricingRuleFactory.addPricingRule("A", regularPricingRule);
        assertTrue(pricingRuleFactory.hasRule("A"));
        assertFalse(pricingRuleFactory.hasRule("B"));
    }

    @Test
    void testHasRule_NullSku() {
        assertThrows(NullPointerException.class, () -> pricingRuleFactory.hasRule(null));
    }
}