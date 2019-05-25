package com.internhub.backend.errors.exceptions;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(long id) {
        super(String.format("Position with ID %d does not exist", id));
    }
}
