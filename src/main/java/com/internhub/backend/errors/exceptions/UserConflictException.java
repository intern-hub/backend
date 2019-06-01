package com.internhub.backend.errors.exceptions;

public class UserConflictException extends RuntimeException {
    public UserConflictException() {
        super("Username has already been taken");
    }
}
