package com.internhub.backend.errors.exceptions;

public class ChangePasswordAccessDeniedException extends RuntimeException {

    public ChangePasswordAccessDeniedException() {
        super("Current password was entered incorrectly");
    }
}
