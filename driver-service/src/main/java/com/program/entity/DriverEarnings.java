package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "driver_earnings",
        indexes = {
                @Index(name = "idx_earnings_driver_id", columnList = "driver_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverEarnings {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false, unique = true)
    private Driver driver;

    @Column(nullable = false)
    private Double totalEarnings;

    @Column(nullable = false)
    private Double availableBalance;

    @Version
    private Long version;
}
