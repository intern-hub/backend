package com.internhub.backend.errors.exceptions;

public class ApplicationNotFoundException extends RuntimeException {

    public ApplicationNotFoundException(long id) {
        super(String.format("Application with ID %d does not exist", id));
    }
}
