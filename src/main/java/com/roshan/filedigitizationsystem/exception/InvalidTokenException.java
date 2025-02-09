package com.roshan.filedigitizationsystem.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid or Expired Token");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
