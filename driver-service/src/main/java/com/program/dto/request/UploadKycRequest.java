package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadKycRequest {

    @NotBlank(message = "Aadhaar number is required")
    private String aadhaarNumber;

    @NotBlank(message = "PAN number is required")
    private String panNumber;

    @NotBlank(message = "Driving license number is required")
    private String drivingLicenseNumber;
}
