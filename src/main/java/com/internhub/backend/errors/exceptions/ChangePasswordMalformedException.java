package com.internhub.backend.errors.exceptions;

public class ChangePasswordMalformedException extends RuntimeException {
    public ChangePasswordMalformedException() {
        super("Required fields: oldPassword, newPassword");
    }
}
