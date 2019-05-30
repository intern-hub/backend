package com.internhub.backend.errors.exceptions;

public class ResetPasswordAccessDeniedException extends RuntimeException {
    public ResetPasswordAccessDeniedException() {
        super("Invalid token provided");
    }
}
