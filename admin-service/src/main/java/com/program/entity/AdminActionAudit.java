package com.program.entity;

import com.program.util.AdminActionType;
import com.program.util.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "admin_action_audit",
        indexes = {
                @Index(name = "idx_admin_driver_id", columnList = "driver_id"),
                @Index(name = "idx_admin_action_type", columnList = "action_type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminActionAudit {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "driver_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID driverId;

    @Column(name = "admin_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID adminId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 30)
    private AdminActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 20)
    private ApprovalStatus approvalStatus;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime actionAt;

    @Version
    private Long version;
}
