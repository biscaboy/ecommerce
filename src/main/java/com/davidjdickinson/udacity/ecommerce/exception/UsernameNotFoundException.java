package com.davidjdickinson.udacity.ecommerce.exception;

public class UsernameNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Username not found.";

    public UsernameNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
