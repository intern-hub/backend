package com.internhub.backend.errors.exceptions;

public class SuggestionAccessDeniedException extends RuntimeException {

    public SuggestionAccessDeniedException(long id) {
        super(String.format("Access denied to suggestion with ID %d", id));
    }
}
