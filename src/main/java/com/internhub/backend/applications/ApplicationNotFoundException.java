package com.internhub.backend.applications;

public class ApplicationNotFoundException extends RuntimeException {

    public ApplicationNotFoundException(long id) {
        super(String.format("Application with ID %d does not exist", id));
    }
}
