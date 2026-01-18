package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVehicleRequest {

    @NotNull(message = "Vehicle type is required")
    private String vehicleType; // BIKE, AUTO, CAR

    @NotBlank(message = "Vehicle number is required")
    private String vehicleNumber;

    @NotBlank(message = "RC document URL is required")
    private String rcDocumentUrl;

    @NotBlank(message = "Insurance document URL is required")
    private String insuranceDocumentUrl;
}
