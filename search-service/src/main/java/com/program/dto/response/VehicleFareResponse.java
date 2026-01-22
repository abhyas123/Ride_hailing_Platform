package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleFareResponse {

    private String vehicleType;     // BIKE, AUTO, CAR
    private Double estimatedFare;   // ₹ amount
    private Integer estimatedTime;  // minutes
}
