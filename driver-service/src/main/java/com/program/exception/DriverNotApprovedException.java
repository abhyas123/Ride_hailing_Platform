package com.program.exception;

public class DriverNotApprovedException extends RuntimeException {

    public DriverNotApprovedException(String message) {
        super(message);
    }
}
