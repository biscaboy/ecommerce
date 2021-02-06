package com.davidjdickinson.udacity.ecommerce.exception;

public class ItemNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Item not found.";

    public ItemNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}

