package com.davidjdickinson.udacity.ecommerce.util;

public class PasswordValidatorFactory {
    public static PasswordValidator create() {
        return new PasswordValidator();
    }
}
