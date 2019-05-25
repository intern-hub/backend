package com.internhub.backend.errors.exceptions;

public class UserConflictException extends RuntimeException {
    public UserConflictException(String username) {
        super(username + " is already a user");
    }
}
