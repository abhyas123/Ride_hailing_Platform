package com.program.exception;

public class DriverAlreadyApprovedException extends RuntimeException {

    public DriverAlreadyApprovedException(String message) {
        super(message);
    }
}
