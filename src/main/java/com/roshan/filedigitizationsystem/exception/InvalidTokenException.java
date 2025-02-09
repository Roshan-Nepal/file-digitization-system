package com.roshan.filedigitizationsystem.exception;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException() {
        super("Invalid Token");
    }

    public TokenValidationException(String message) {
        super(message);
    }
}
