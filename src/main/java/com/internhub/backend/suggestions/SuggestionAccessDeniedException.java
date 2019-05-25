package com.internhub.backend.suggestions;

public class SuggestionAccessDeniedException extends RuntimeException {

    public SuggestionAccessDeniedException(long id) {
        super(String.format("Access denied to suggestion with ID %d", id));
    }
}
