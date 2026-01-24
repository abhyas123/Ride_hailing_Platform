package com.program.dto.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingEstimateResponse {

    private List<VehicleFareResponse> vehicleFares;
}
