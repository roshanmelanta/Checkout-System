package com.cdl;

import java.math.BigDecimal;
import java.util.Scanner;

public class CheckoutSystem {
    private final Scanner scanner;
    private final Checkout checkout;

    public CheckoutSystem(PricingRuleFactory pricingRuleFactory) {
        this.scanner = new Scanner(System.in);
        this.checkout = new Checkout(pricingRuleFactory);
    }

    public void start() {
        System.out.println("Welcome to the CDL Checkout System");
        System.out.println("Enter items (A, B, C, D) one by one. Type 'done' to finish or 'quit' to exit:");

        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("QUIT")) {
                System.out.println("Thank you for using CDL Checkout System. Goodbye!");
                break;
            } else if (input.equals("DONE")) {
                displayTotal();
                checkout.clear();
                System.out.println("Starting a new checkout. Enter items or 'quit' to exit:");
            } else {
                try {
                    checkout.scan(input);
                    displayRunningTotal();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage() + " Please try again.");
                }
            }
        }
    }

    private void displayRunningTotal() {
        System.out.printf("Running total: £%.2f%n", checkout.calculateTotal());
    }

    private void displayTotal() {
        System.out.printf("Final total: £%.2f%n", checkout.calculateTotal());
    }

    public static void main(String[] args) {
        PricingRuleFactory pricingRuleFactory = initializePricingRules();
        CheckoutSystem checkoutSystem = new CheckoutSystem(pricingRuleFactory);
        checkoutSystem.start();
    }

    private static PricingRuleFactory initializePricingRules() {
        PricingRuleFactory factory = new PricingRuleFactory();
        factory.addPricingRule("A", new SpecialPricingRule(new BigDecimal("0.50"), 3, new BigDecimal("1.30")));
        factory.addPricingRule("B", new SpecialPricingRule(new BigDecimal("0.30"), 2, new BigDecimal("0.45")));
        factory.addPricingRule("C", new RegularPricingRule(new BigDecimal("0.20")));
        factory.addPricingRule("D", new RegularPricingRule(new BigDecimal("0.15")));
        return factory;
    }
}