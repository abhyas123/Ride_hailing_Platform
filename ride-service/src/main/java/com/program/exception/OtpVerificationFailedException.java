package com.program.exception;

/**
 * Exception thrown when OTP verification fails
 */
public class OtpVerificationFailedException extends RuntimeException {
    
    public OtpVerificationFailedException(String message) {
        super(message);
    }
    
    public OtpVerificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
