package com.internhub.backend.errors.exceptions;

public class SuggestionMalformedException extends RuntimeException {

    public SuggestionMalformedException() {
        super("Required fields: content");
    }
}
