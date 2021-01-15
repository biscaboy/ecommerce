package com.example.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Password Validation
 *
 *  Complexity requirements:
 *
 1. The password may not contain the account name or variations on the account name.

 (Must have all of the following)
 2. It must contain characters from three of the following five groups (quoted from the Microsoft document):
 3. Uppercase letters of European languages (A through Z, with diacritical marks, Greek and Cyrillic characters)
 4. Lowercase letters of European languages (A through Z, sharp S, with diacritical marks, Greek and Cyrillic characters)
 5. Base 10 digits (0 through 9);
 6. non-alphanumeric characters (special characters): (~!@#$%^&*_-+=`|\(){}[]:;"'<>,.?/)
 */

public class PasswordValidator {

    private String reason;

    private static final int REQUIRED_LENGTH = 10;
    private static final String REQUIRED_SPECIAL_CHARS = "@#!$%^&*+=()_~-";

    // Derived from https://regexr.com/3bfsi
    // Minimum 10 Character Password with one of each of lowercase, uppercase letters, digits, and special characters
    private static final Pattern REGEX_ALL_RULES = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&*+=()_~-]).*(?=.*[a-zA-Z]).{10,}$");
    private static final Pattern REGEX_MIN_DIGITS = Pattern.compile("^(?=.*\\d).*");
    private static final Pattern REGEX_MIN_LOWERCASE = Pattern.compile("^(?=.*[a-z]).*$");
    private static final Pattern REGEX_MIN_UPPERCASE = Pattern.compile("^(?=.*[A-Z]).*$");
    private static final Pattern REGEX_MIN_LENGTH = Pattern.compile("^.{" + REQUIRED_LENGTH + ",}.*$");
    private static final Pattern REGEX_MIN_SPECIAL_CHARS = Pattern.compile("^(?=.*[@#!$%^&*+=()_~-]).*$");
    //private static final String REGEX_MIN_TWO_CAPS = "/^(?=.*[A-Z].*[A-Z]).*";

    public static final String REASON_NO_MATCH = "The password and confirm password must match. ";
    public static final String REASON_MIN_LOWERCASE = "At least one lower letter is required. ";
    public static final String REASON_MIN_UPPERCASE = "At least one uppercase letter is required. ";
    public static final String REASON_MIN_DIGITS = "At least one digit [0-9] is required. ";
    public static final String REASON_MIN_LENGTH = "Minimum length is " + REQUIRED_LENGTH + " characters. ";
    public static final String REASON_MIN_SPECIAL_CHARS = "At least one special character [" + REQUIRED_SPECIAL_CHARS + "] is required.  ";

    public boolean validate(String password, String confirmPassword) {
        if ( password.equals(confirmPassword) && isValid(password, REGEX_ALL_RULES) ) {
            return true;
        } else {
            String reason = "";
            // determine all reasons the validation failed.
            if (!password.equals(confirmPassword)) {
                reason += REASON_NO_MATCH;
            }
            if (!isValid(password, REGEX_MIN_DIGITS)) {
                reason += REASON_MIN_DIGITS;
            }
            if (!isValid(password, REGEX_MIN_UPPERCASE)){
                reason += REASON_MIN_UPPERCASE;
            }
            if (!isValid(password, REGEX_MIN_LOWERCASE)){
                reason += REASON_MIN_LOWERCASE;
            }
            if (!isValid(password, REGEX_MIN_LENGTH)){
                reason += REASON_MIN_LENGTH;
            }
            if (!isValid(password, REGEX_MIN_SPECIAL_CHARS)){
                reason += REASON_MIN_SPECIAL_CHARS;
            }
            this.reason = reason;
            return false;
        }
    }

    public String getReasonMessage() {
        return this.reason;
    }

    private boolean isValid(String password, Pattern pattern) {
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}