package com.internhub.backend.errors.exceptions;

public class ForgotPasswordMalformedException extends RuntimeException {
    public ForgotPasswordMalformedException() {
        super("Required fields: username, email");
    }
}
