package com.program.entity;

import com.program.util.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fare_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareAudit {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private UUID rideId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Double baseFare;

    @Column(nullable = false)
    private Double distanceFare;

    @Column(nullable = false)
    private Double timeFare;

    @Column(nullable = false)
    private Double nightCharge;

    @Column(nullable = false)
    private Double totalFare;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
