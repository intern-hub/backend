package com.internhub.backend.auth;

public class UserMalformedException extends RuntimeException {
    public UserMalformedException() {
        super("User object is missing fields");
    }
}
