package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID passengerId;

    private UUID driverId;

    // We store locations as simple Strings for now (e.g., "Lat,Lng") or address names
    // For V2, we can use Point types with Hibernate Spatial
    @Column(nullable = false)
    private String sourceLocation;

    @Column(nullable = false)
    private String destinationLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status; // REQUESTED, ACCEPTED, IN_PROGRESS, COMPLETED, CANCELLED

    private Double fare;
    
    // OTP fields for pickup verification
    @Column(length = 6)
    private String pickupOtp;
    
    private LocalDateTime otpGeneratedAt;
    
    private LocalDateTime otpVerifiedAt;
    
    @Column(nullable = false)
    private Boolean isOtpVerified = false;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
