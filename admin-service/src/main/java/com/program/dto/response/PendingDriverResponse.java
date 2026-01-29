package com.program.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingDriverResponse {

    private String driverId;
    private String phone;
    private String name;
    private String vehicleType;
    private String kycStatus;   // PENDING / VERIFIED / REJECTED
}
