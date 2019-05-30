package com.internhub.backend.errors.exceptions;

public class ForgotPasswordAccessDeniedException extends RuntimeException {
    public ForgotPasswordAccessDeniedException() {
        super("Invalid username-email combination entered");
    }
}
