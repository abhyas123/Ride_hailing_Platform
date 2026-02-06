package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for driver accepting a ride
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcceptRideRequest {

    @NotNull(message = "Ride ID is required")
    private UUID rideId;

    @NotNull(message = "Driver ID is required")
    private UUID driverId;

    @NotBlank(message = "Driver FCM token is required")
    private String driverFcmToken;

    private String estimatedArrivalMinutes;
}
