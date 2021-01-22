package com.davidjdickinson.udacity.ecommerce.util;

import com.davidjdickinson.udacity.ecommerce.model.requests.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogMFTests {

    @Test
    public void test_format(){
        String message = LogMF.format("myMethod", "My Message", "MyObject", "My Value");
        String expectedJson = "{ \"method\" : \"myMethod\", \"message\": \"My Message\", { \"java.lang.String\" : {\"MyObject\" : \"My Value\" } }";
        String expected = "method=[myMethod] message=[My Message] object=[java.lang.String] MyObject=[My Value]";

        assertEquals(expected, message);
    }

    @Test
    public void test_format_pojo(){
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("password");
        request.setConfirmPassword("confirm");
        String message = LogMF.format("myMethod", "My Message", request);
//        String expectedJson = "{ \"method\" : \"myMethod\", \"message\": \"My Message\", " +
//                "{ \"com.example.demo.model.requests.CreateUserRequest\" : " +
//                "{\"password\" : \"***** CONFIDENTIAL *****\", \"confirmPassword\" : \"***** CONFIDENTIAL *****\", \"username\" : \"test\" } }";
        String expected = "method=[myMethod] message=[My Message] " +
                "object=[com.davidjdickinson.udacity.ecommerce.model.requests.CreateUserRequest] " +
                "password=[***** CONFIDENTIAL *****] confirmPassword=[***** CONFIDENTIAL *****] username=[test]";
        assertEquals(expected, message);
    }

    @Test
    @DisplayName("Format a single message")
    public void test_format_single_message() {
        String message = LogMF.format("myMethod", "My Message");
//        String expectedJson = "{ \"method\" : \"myMethod\", \"message\": \"My Message\" }";
        String expected = "method=[myMethod] message=[My Message]";
        assertEquals(expected, message);
    }
}
