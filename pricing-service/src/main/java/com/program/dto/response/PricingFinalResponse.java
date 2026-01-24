package com.program.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingFinalResponse {

    private UUID rideId;
    private Double totalFare;
    private FareBreakdownResponse breakdown;
}
