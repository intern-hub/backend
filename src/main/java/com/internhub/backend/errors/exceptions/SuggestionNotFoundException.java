package com.internhub.backend.errors.exceptions;

public class SuggestionNotFoundException extends RuntimeException {

    public SuggestionNotFoundException(long id) {
        super(String.format("Suggestion with ID %d does not exist", id));
    }
}
