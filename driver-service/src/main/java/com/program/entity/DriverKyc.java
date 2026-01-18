package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "driver_kyc",
        indexes = {
                @Index(name = "idx_kyc_driver_id", columnList = "driver_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverKyc {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false, unique = true)
    private Driver driver;

    @Column(nullable = false)
    private String aadhaarNumber;

    @Column(nullable = false)
    private String panNumber;

    @Column(nullable = false)
    private String drivingLicenseNumber;

    @Column(nullable = false)
    private String status; // PENDING, VERIFIED, REJECTED

    @Column(length = 255)
    private String rejectionReason;   // ✅ FIX

    @Version
    private Long version;
}
