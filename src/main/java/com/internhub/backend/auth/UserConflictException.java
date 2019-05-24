package com.internhub.backend.auth;

public class UserConflictException extends RuntimeException {
    public UserConflictException(String username) {
        super(username + " is already a user");
    }
}
