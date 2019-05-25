package com.internhub.backend.errors.exceptions;

public class ApplicationConflictException extends RuntimeException {
    public ApplicationConflictException(String username, Long positionId) {
        super(String.format("%s's application for position %d already exists", username, positionId));
    }
}
