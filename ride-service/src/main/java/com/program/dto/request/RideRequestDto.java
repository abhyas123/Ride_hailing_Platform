package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RideRequestDto {
    
    @NotNull(message = "Source location is required")
    @NotBlank(message = "Source location cannot be blank")
    private String sourceLocation;
    
    @NotNull(message = "Destination location is required")
    @NotBlank(message = "Destination location cannot be blank")
    private String destinationLocation;
    
    private Double sourceLatitude;
    private Double sourceLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
}
