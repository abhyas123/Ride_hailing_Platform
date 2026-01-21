package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeRideLocationRequest {

    @NotBlank(message = "Ride ID is required")
    private String rideId;
}
