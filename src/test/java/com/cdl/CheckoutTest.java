package com.cdl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Checkout class.
 * Tests various scenarios including scanning items, calculating totals,
 * and handling special pricing rules.
 */
public class CheckoutTest {
    private Checkout checkout;
    private PricingRuleFactory pricingRuleFactory;

    @Mock
    private RegularPricingRule regularPricingRule;

    @Mock
    private SpecialPricingRule specialPricingRule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pricingRuleFactory = new PricingRuleFactory();

        // Set up regular pricing rule
        when(regularPricingRule.calculatePrice(anyInt())).thenAnswer(invocation -> {
            int quantity = invocation.getArgument(0);
            return BigDecimal.valueOf(0.50).multiply(BigDecimal.valueOf(quantity));
        });
        pricingRuleFactory.addPricingRule("A", regularPricingRule);

        // Set up special pricing rule (3 for 130)
        when(specialPricingRule.calculatePrice(anyInt())).thenAnswer(invocation -> {
            int quantity = invocation.getArgument(0);
            int specialDeals = quantity / 3;
            int remainder = quantity % 3;
            return BigDecimal.valueOf(1.30).multiply(BigDecimal.valueOf(specialDeals))
                    .add(BigDecimal.valueOf(0.50).multiply(BigDecimal.valueOf(remainder)));
        });
        pricingRuleFactory.addPricingRule("B", specialPricingRule);

        checkout = new Checkout(pricingRuleFactory);
    }

    @Test
    void testScan_ValidSKU() {
        assertDoesNotThrow(() -> checkout.scan("A"));
        assertEquals(1, getCartQuantity("A"));
    }

    @Test
    void testScan_InvalidSKU() {
        assertThrows(IllegalArgumentException.class, () -> checkout.scan("X"));
    }

    @Test
    void testScan_EmptySKU() {
        assertThrows(IllegalArgumentException.class, () -> checkout.scan(""));
    }

    @Test
    void testCheckoutTotal_EmptyCart() {
        assertEquals(BigDecimal.ZERO.setScale(2), checkout.calculateTotal());
    }

    @Test
    void testCheckoutTotal_MultipleRegularItems() {
        checkout.scan("A");
        checkout.scan("A");
        assertEquals(BigDecimal.valueOf(1.00).setScale(2), checkout.calculateTotal());
    }

    @Test
    void testCheckoutTotal_SpecialDeal() {
        checkout.scan("B");
        checkout.scan("B");
        checkout.scan("B");
        assertEquals(BigDecimal.valueOf(1.30).setScale(2), checkout.calculateTotal());
    }

    @Test
    void testCheckoutTotal_MixedItems() {
        checkout.scan("A");
        checkout.scan("B");
        checkout.scan("B");
        checkout.scan("A");
        checkout.scan("B");
        assertEquals(BigDecimal.valueOf(2.30).setScale(2), checkout.calculateTotal());
    }

    // Helper method to get the quantity of an item in the cart
    private int getCartQuantity(String sku) {
        return checkout.getQuantity(sku);
    }
}
