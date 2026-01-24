package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "surge_multipliers",
        indexes = {
                @Index(name = "idx_surge_active", columnList = "active"),
                @Index(name = "idx_surge_time", columnList = "start_time,end_time")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurgeMultiplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double multiplier; // e.g. 1.2, 1.5, 2.0

    @Column(nullable = false)
    private boolean active;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Version
    private Long version;
}
