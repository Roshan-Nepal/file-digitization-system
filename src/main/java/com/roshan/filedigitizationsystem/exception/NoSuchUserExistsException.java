package com.roshan.filedigitizationsystem.exception;


public class NoSuchUserExistsException extends RuntimeException {
    public NoSuchUserExistsException() {
        super("No such user exists.");
    }
    public NoSuchUserExistsException(String message) {
        super(message);
    }
}
