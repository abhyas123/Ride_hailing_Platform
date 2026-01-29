package com.program.service;

import com.program.entity.AdminActionAudit;
import com.program.repository.AdminActionAuditRepository;
import com.program.util.AdminActionType;
import com.program.util.ApprovalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAuditService {

    private final AdminActionAuditRepository auditRepository;

    // ===============================
    // GENERIC AUDIT
    // ===============================
    public void logAction(
            String driverId,
            AdminActionType actionType,
            String reason
    ) {

        AdminActionAudit audit = AdminActionAudit.builder()
                .driverId(UUID.fromString(driverId))   // ✅ FIX
                .adminId(getSystemAdminId())            // ✅ FIX
                .actionType(actionType)                 // ✅ FIX
                .approvalStatus(
                        actionType == AdminActionType.APPROVE_DRIVER
                                ? ApprovalStatus.APPROVED
                                : ApprovalStatus.REJECTED
                )
                .reason(reason)
                .actionAt(LocalDateTime.now())          // ✅ FIX
                .build();

        auditRepository.save(audit);
    }


    // ===============================
    // LOG ONBOARDING COMPLETED
    // ===============================
    public void logOnboardingCompleted(String driverId) {
        // Uses the existing logAction method with a specific type
        logAction(driverId, AdminActionType.APPROVE_DRIVER, "Onboarding process completed internally");
    }

    // ===============================
    // LOG STATUS CHANGE
    // ===============================
    public void logStatusChange(String driverId, String status) {
        // Determine action type based on incoming status string
        AdminActionType actionType = "APPROVED".equalsIgnoreCase(status)
                ? AdminActionType.APPROVE_DRIVER
                : AdminActionType.REJECT_DRIVER;

        logAction(driverId, actionType, "Internal status update: " + status);
    }

    // ===============================
    // TEMP SYSTEM ADMIN
    // ===============================
    private UUID getSystemAdminId() {
        // Later replace with authenticated admin id
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
