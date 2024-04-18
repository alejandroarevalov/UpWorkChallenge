package com.upwork.challenge.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UpworkStudentsExceptionHandler {

    @ExceptionHandler(UpWorkStudentsAuthenticationException.class)
    public ResponseEntity<Object> handleNonExistingUserException(UpWorkStudentsAuthenticationException ex) {
        return createErrorMessage(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UpWorkStudentsResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(UpWorkStudentsResourceNotFoundException ex) {
        return createErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> createErrorMessage(String message, HttpStatus statusCode) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);

        return new ResponseEntity<>(body, statusCode);
    }
}
