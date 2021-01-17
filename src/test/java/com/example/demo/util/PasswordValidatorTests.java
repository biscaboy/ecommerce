package com.example.demo.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidatorTests {

    private PasswordValidator passwordValidator;

    @BeforeEach
    public void beforeEach() {
        this.passwordValidator = PasswordValidatorFactory.create();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "12#Fourfiv3sixSEVEN",
            "aaaaaaa*E6",
            "AAAAAAAb!9",
            "8932984Bc(",
            "(*&$#I*&1Qw"
    })
    @DisplayName("Password is valid")
    public void password_is_valid(String password) {
        assertTrue(passwordValidator.validate(password, password),
                   passwordValidator.getReasonMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "asdf",
            "123123123123",
            "asdfasdfasdf",
            "PPOPOUEINDOOE",
            "*#*#(*$(*#($#"
    })
    @DisplayName("Password is not valid")
    public void password_is_not_valid(String password) {
        assertFalse(passwordValidator.validate(password, password),
                    passwordValidator.getReasonMessage());
    }

    @Test
    @DisplayName("Password is too short")
    public void password_is_too_short() {
        String password = "tOo*sh0t";
        assertFalse(passwordValidator.validate(password, password));
        assertEquals(passwordValidator.getReasonMessage(), PasswordValidator.REASON_MIN_LENGTH);
    }

    @Test
    @DisplayName("Password has no digits")
    public void password_has_no_digits() {
        String password = "EveryThing@#$%";
        assertFalse(passwordValidator.validate(password, password));
        assertEquals(passwordValidator.getReasonMessage(), PasswordValidator.REASON_MIN_DIGITS);
    }

    @Test
    @DisplayName("Password has no uppercase")
    public void password_has_no_uppercase() {
        String password = "thisisalllower*4";
        assertFalse(passwordValidator.validate(password, password));
        assertEquals(passwordValidator.getReasonMessage(), PasswordValidator.REASON_MIN_UPPERCASE);
    }

    @Test
    @DisplayName("Password has no lowercase")
    public void password_has_no_lowercase() {
        String password = "ASEVDIR3(A)32";
        assertFalse(passwordValidator.validate(password, password));
        assertEquals(passwordValidator.getReasonMessage(), PasswordValidator.REASON_MIN_LOWERCASE);
    }

    @Test
    @DisplayName("Password has no special character")
    public void password_has_no_special_character() {
        String password = "ASEVDIR3asA32";
        assertFalse(passwordValidator.validate(password, password));
        assertEquals(passwordValidator.getReasonMessage(), PasswordValidator.REASON_MIN_SPECIAL_CHARS);
    }

    @Test
    @DisplayName("Passwords do not match")
    public void passwords_do_not_match() {
        String password = "*AVeryFin3Password";
        String confirm = "thisisdi$4Bfferent";
        assertFalse(passwordValidator.validate(password, confirm));
        assertEquals(passwordValidator.getReasonMessage(), PasswordValidator.REASON_NO_MATCH);
    }



}
