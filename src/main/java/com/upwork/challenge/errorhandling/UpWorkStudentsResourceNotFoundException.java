package com.upwork.challenge.errorhandling;

public class UpWorkStudentsResourceNotFoundException extends RuntimeException {

    public UpWorkStudentsResourceNotFoundException(String message) {
        super(message);
    }

}
