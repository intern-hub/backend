package com.internhub.backend.errors.exceptions;

public class ForgotPasswordAccessDeniedException extends RuntimeException {
    public ForgotPasswordAccessDeniedException() {
        super("Invalid username or email entered");
    }
}
