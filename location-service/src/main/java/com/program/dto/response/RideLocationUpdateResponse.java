package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideLocationUpdateResponse {

    private String rideId;
    private String driverId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime updatedAt;
}
