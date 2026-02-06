package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for OTP verification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpVerificationResponse {

    private boolean verified;
    private String message;
    private UUID rideId;
    private LocalDateTime verifiedAt;
}
