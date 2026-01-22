package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteSearchRequest {

    @NotBlank(message = "Pickup location name is required")
    private String pickupLocation;

    @NotBlank(message = "Drop location name is required")
    private String dropLocation;
}
