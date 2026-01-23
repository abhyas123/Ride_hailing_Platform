package com.program.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingEstimateRequest {

    @NotNull(message = "Distance is required")
    @Min(value = 1, message = "Distance must be greater than 0")
    private Double distanceInKm;

    @NotNull(message = "Estimated time is required")
    @Min(value = 1, message = "Estimated time must be greater than 0")
    private Integer estimatedTimeInMinutes;

    @NotNull(message = "Pickup latitude is required")
    private Double pickupLatitude;

    @NotNull(message = "Pickup longitude is required")
    private Double pickupLongitude;

    public PricingEstimateRequest(double distanceKm, int etaMinutes) {
        this.distanceInKm =distanceKm;
        this.estimatedTimeInMinutes = etaMinutes;
    }
}
