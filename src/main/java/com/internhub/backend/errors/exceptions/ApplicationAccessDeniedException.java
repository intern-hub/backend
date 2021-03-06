package com.internhub.backend.errors.exceptions;

public class ApplicationAccessDeniedException extends RuntimeException {

    public ApplicationAccessDeniedException(long id) {
        super(String.format("Access denied to application with ID %d", id));
    }
}
