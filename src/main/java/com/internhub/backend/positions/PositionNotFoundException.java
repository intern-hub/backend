package com.internhub.backend.positions;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(Long id) {
        super("Could not find position " + id);
    }
}
