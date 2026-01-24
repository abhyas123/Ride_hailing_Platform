package com.program.dto.response;

import com.program.util.VehicleType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleFareResponse {

    private VehicleType vehicleType;
    private Double estimatedFare;
    private Double surgeMultiplier;
}