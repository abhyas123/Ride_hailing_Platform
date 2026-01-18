package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "driver_withdrawals",
        indexes = {
                @Index(name = "idx_withdrawal_driver_id", columnList = "driver_id"),
                @Index(name = "idx_withdrawal_status", columnList = "status")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverWithdrawal {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status; // PENDING, SUCCESS, FAILED

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    @Version
    private Long version;
}
