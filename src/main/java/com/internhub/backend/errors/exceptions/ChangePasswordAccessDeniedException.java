package com.internhub.backend.errors.exceptions;

public class ChangePasswordAccessDeniedException extends RuntimeException {

    public ChangePasswordAccessDeniedException() {
        super("Old password was entered incorrectly");
    }
}
