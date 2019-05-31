package com.internhub.backend.errors.exceptions;

public class ResetPasswordMalformedException extends RuntimeException {
    public ResetPasswordMalformedException() {
        super("Required fields: token, newPassword");
    }
}
