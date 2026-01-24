package com.program.entity;

import com.program.util.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "fare_audit",
        indexes = {
                @Index(name = "idx_fare_ride_id", columnList = "ride_id", unique = true),
                @Index(name = "idx_fare_driver_id", columnList = "driver_id"),
                @Index(name = "idx_fare_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ride_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID rideId;

    @Column(name = "driver_id", columnDefinition = "BINARY(16)")
    private UUID driverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Double distanceKm;

    @Column(nullable = false)
    private Long durationMinutes;

    @Column(nullable = false)
    private Double surgeMultiplier;

    @Column(name = "total_fare", nullable = false)
    private Double totalFare;

    @Column(name = "platform_fee", nullable = false)
    private Double platformFee;

    @Column(name = "driver_earning", nullable = false)
    private Double driverEarning;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;
}
