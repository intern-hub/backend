package com.internhub.backend.errors;


import com.internhub.backend.errors.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            ApplicationMalformedException.class,
            SuggestionMalformedException.class,
            UserMalformedException.class,
            ForgotPasswordMalformedException.class,
            ChangePasswordMalformedException.class,
            ResetPasswordMalformedException.class
    })
    public ResponseEntity<CustomErrorResponse> handleBadRequestException(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ApplicationAccessDeniedException.class,
            SuggestionAccessDeniedException.class,
            ForgotPasswordAccessDeniedException.class,
            ChangePasswordAccessDeniedException.class,
            ResetPasswordAccessDeniedException.class
    })
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            ApplicationNotFoundException.class,
            SuggestionNotFoundException.class,
            CompanyNotFoundException.class,
            PositionNotFoundException.class
    })
    public ResponseEntity<CustomErrorResponse> handleNotFoundException(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            ApplicationConflictException.class,
            UserConflictException.class
    })
    public ResponseEntity<CustomErrorResponse> handleConflictException(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
