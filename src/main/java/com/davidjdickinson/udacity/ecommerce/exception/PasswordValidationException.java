package com.davidjdickinson.udacity.ecommerce.exception;

public class PasswordValidationException extends RuntimeException {

    public PasswordValidationException(String exception) {
        super(exception);
    }
}
