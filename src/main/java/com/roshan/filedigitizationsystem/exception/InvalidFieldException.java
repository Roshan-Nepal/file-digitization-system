package com.roshan.filedigitizationsystem.exception;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException() {
        super();
    }
    public InvalidFieldException(String message) {
        super(message);
    }
}
