package com.internhub.backend.positions;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(long id) {
        super(String.format("Position with ID %d does not exist", id));
    }
}
