package com.roshan.filedigitizationsystem.exception;

public class InvalidFileFormatException extends RuntimeException {
    public InvalidFileFormatException() {
        super("Invalid file type.");
    }
    public InvalidFileFormatException(String message) {
        super(message);
    }
}
