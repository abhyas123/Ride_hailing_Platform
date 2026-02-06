package com.program.dto.response;

import com.program.entity.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideResponseDto {
    
    private UUID id;
    private UUID passengerId;
    private UUID driverId;
    private String sourceLocation;
    private String destinationLocation;
    private RideStatus status;
    private Double fare;
    private String otp;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;

    public static RideResponseDto fromEntity(com.program.entity.Ride ride) {
        if (ride == null) return null;
        
        return RideResponseDto.builder()
                .id(ride.getId())
                .passengerId(ride.getPassengerId())
                .driverId(ride.getDriverId())
                .sourceLocation(ride.getSourceLocation())
                .destinationLocation(ride.getDestinationLocation())
                .status(ride.getStatus())
                .fare(ride.getFare())
                .otp(ride.getPickupOtp())
                .startTime(ride.getStartTime())
                .endTime(ride.getEndTime())
                .createdAt(ride.getCreatedAt())
                .build();
    }
}
