package com.program.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===============================
    // INVALID PRICING REQUEST
    // ===============================
    @ExceptionHandler(InvalidPricingRequestException.class)
    public ResponseEntity<?> handleInvalidPricingRequest(
            InvalidPricingRequestException ex
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    // ===============================
    // SURGE NOT AVAILABLE
    // ===============================
    @ExceptionHandler(SurgeNotAvailableException.class)
    public ResponseEntity<?> handleSurgeNotAvailable(
            SurgeNotAvailableException ex
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    // ===============================
    // GENERIC PRICING ERROR
    // ===============================
    @ExceptionHandler(PricingException.class)
    public ResponseEntity<?> handlePricingException(
            PricingException ex
    ) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    // ===============================
    // VALIDATION ERROR (@Valid)
    // ===============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        String errorMessage =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .findFirst()
                        .map(err -> err.getField() + " " + err.getDefaultMessage())
                        .orElse("Invalid request");

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage
        );
    }

    // ===============================
    // ILLEGAL STATE (DATABASE ISSUES)
    // ===============================
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    // ===============================
    // FALLBACK (SAFETY NET)
    // ===============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        ex.printStackTrace(); // 🔥 IMPORTANT

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + ex.getMessage()
        );
    }

    // ===============================
    // COMMON RESPONSE FORMAT
    // ===============================
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String message
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}
