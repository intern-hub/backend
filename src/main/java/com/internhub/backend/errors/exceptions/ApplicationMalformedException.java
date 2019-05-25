package com.internhub.backend.errors.exceptions;

public class ApplicationMalformedException extends RuntimeException {

    public ApplicationMalformedException() {
        super("Required fields: position");
    }
}
