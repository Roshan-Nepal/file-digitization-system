package com.roshan.filedigitizationsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoSuchUserExistsException extends RuntimeException {
    public NoSuchUserExistsException() {
        super("No such user exists.");
    }
    public NoSuchUserExistsException(String message) {
        super(message);
    }
}
