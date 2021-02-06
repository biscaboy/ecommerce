package com.davidjdickinson.udacity.ecommerce.exception;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(String exception) {
        super(exception);
    }
}
