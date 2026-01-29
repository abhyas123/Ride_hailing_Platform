package com.program.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDetailsResponse {

    private String driverId;

    // BASIC INFO
    private String phone;
    private String name;
    private String profilePhotoUrl;

    // KYC
    private String aadhaarNumber;
    private String panNumber;
    private String drivingLicenseNumber;
    private String kycStatus;

    // VEHICLE
    private String vehicleType;
    private String vehicleNumber;
    private String rcDocumentUrl;
    private String insuranceDocumentUrl;

    // STATUS
    private boolean approved;
}
