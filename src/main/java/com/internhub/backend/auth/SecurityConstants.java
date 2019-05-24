package com.internhub.backend.auth;

public class SecurityConstants {
    public static final String SECRET = "this is truly an amazing secret string";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGNUP_URL = "/api/auth/signup";
    public static final String LOGIN_URL = "/api/auth/login";
}
