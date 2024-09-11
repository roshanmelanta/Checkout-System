package com.cdl;

/**
 * Represents an item in the checkout system.
 */
public class Item {
    private final String sku;

    public Item(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }
}
