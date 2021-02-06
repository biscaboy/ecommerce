package com.davidjdickinson.udacity.ecommerce.exception;

public class InvalidUserIdException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Invalid User Id.";

    public InvalidUserIdException() {
        super(EXCEPTION_MESSAGE);
    }
}