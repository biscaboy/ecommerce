package com.davidjdickinson.udacity.ecommerce.security;

public class SecurityConstants {

    public static final String SECRET = "PGog6uBN0mC5kcY9VhF3";
    public static final long EXPIRATION_TIME = (10 * 24 * 60 * 60 * 1000); // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}
