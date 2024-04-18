package com.upwork.challenge.errorhandling;

public class UpWorkStudentsAuthenticationException extends RuntimeException {

    public UpWorkStudentsAuthenticationException(String message) {
        super(message);
    }
}
