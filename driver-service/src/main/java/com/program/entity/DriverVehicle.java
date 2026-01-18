package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "driver_vehicle",
        indexes = {
                @Index(name = "idx_vehicle_driver_id", columnList = "driver_id"),
                @Index(name = "idx_vehicle_type", columnList = "vehicleType")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverVehicle {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private String vehicleType; // BIKE, AUTO, CAR

    @Column(nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String rcDocumentUrl;

    @Column(nullable = false)
    private String insuranceDocumentUrl;

    @Column(nullable = false)
    private boolean active;

    @Version
    private Long version;
}
