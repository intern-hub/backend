package com.internhub.backend.errors.exceptions;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(long id) {
        super(String.format("Company with ID %d does not exist", id));
    }
}
