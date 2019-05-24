package com.internhub.backend.errors;


import com.internhub.backend.companies.CompanyNotFoundException;
import com.internhub.backend.positions.PositionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class NotFoundGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            CompanyNotFoundException.class,
            PositionNotFoundException.class
    })
    public ResponseEntity<NotFoundErrorResponse> handleNotFoundException(Exception ex, WebRequest request) {
        NotFoundErrorResponse errors = new NotFoundErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
