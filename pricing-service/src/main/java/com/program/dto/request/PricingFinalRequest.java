package com.program.dto.request;

import com.program.util.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingFinalRequest {

    @NotNull
    private UUID rideId;

    @NotNull
    private UUID driverId;

    @NotNull
    private VehicleType vehicleType;

    @NotNull
    @Min(1)
    private Double actualDistanceKm;

    @NotNull
    @Min(1)
    private Long actualDurationMinutes;
}
