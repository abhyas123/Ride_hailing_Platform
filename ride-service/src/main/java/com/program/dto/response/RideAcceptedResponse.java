package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for ride acceptance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideAcceptedResponse {

    private UUID rideId;
    private UUID driverId;
    private String driverName;
    private String driverPhone;
    private String vehicleDetails;
    private String estimatedArrival;
    private boolean otpGenerated;
    private LocalDateTime acceptedAt;
}
