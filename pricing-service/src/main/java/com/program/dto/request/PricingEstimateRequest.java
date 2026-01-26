package com.program.dto.request;

import com.program.util.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingEstimateRequest {

//    @NotNull
//    private VehicleType vehicleType;

    @NotNull
    @Min(1)
    private Double distanceKm;

    @NotNull
    @Min(1)
    private Long estimatedDurationMinutes;
}
