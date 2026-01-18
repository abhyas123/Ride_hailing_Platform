package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverProfileResponse {

    private String driverId;
    private String phone;
    private String name;
    private String profilePhotoUrl;
    private String status;
    private boolean approved;
}
