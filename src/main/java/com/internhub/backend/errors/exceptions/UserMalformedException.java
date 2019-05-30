package com.internhub.backend.errors.exceptions;

public class UserMalformedException extends RuntimeException {
    public UserMalformedException() {
        super("Required fields: username, email, and password");
    }
}
