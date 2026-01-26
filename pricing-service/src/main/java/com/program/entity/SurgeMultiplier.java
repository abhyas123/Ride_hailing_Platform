package com.program.entity;

import com.program.util.VehicleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "surge_multipliers",
        indexes = {
                @Index(name = "idx_surge_active", columnList = "active"),
                @Index(name = "idx_surge_time", columnList = "start_time,end_time")
        }
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurgeMultiplier {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Double multiplier;

    @Column(nullable = false)
    private boolean active;
}
