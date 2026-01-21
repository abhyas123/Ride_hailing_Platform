package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationResponse {

    private String driverId;
    private Double latitude;
    private Double longitude;
    private boolean online;
}
