package com.cosmocats.intergalactic_market.service.exception;

public class ProductNotFoundException extends RuntimeException {
        private static final String PRODUCT_NOT_FOUND = "Sorry, product '%s' is not found.";

    public ProductNotFoundException(String name) {
        super(String.format(PRODUCT_NOT_FOUND, name));
    }
}
