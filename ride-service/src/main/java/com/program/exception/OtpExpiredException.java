package com.program.exception;

/**
 * Exception thrown when OTP has expired
 */
public class OtpExpiredException extends RuntimeException {
    
    public OtpExpiredException(String message) {
        super(message);
    }
    
    public OtpExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
