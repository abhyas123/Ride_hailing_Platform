package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverKycResponse {

    private String aadhaarNumber;
    private String panNumber;
    private String drivingLicenseNumber;
    private String kycStatus; // PENDING, VERIFIED, REJECTED
}
