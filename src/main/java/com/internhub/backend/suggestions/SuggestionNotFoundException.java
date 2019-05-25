package com.internhub.backend.suggestions;

public class SuggestionNotFoundException extends RuntimeException {

    public SuggestionNotFoundException(long id) {
        super(String.format("Suggestion with ID %d does not exist", id));
    }
}
