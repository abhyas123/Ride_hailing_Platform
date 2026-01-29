package com.program.controller;

import com.program.service.AdminAuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/internal")
public class InternalAdminController {

    private final AdminAuditService auditService;

    public InternalAdminController(AdminAuditService auditService) {
        this.auditService = auditService;
    }

    // ===============================
    // DRIVER ONBOARDING COMPLETE
    // ===============================
    @PostMapping("/drivers/onboarding-complete")
    public ResponseEntity<Void> onboardingComplete(
            @RequestParam String driverId) {

        auditService.logOnboardingCompleted(driverId);
        return ResponseEntity.ok().build();
    }

    // ===============================
    // DRIVER STATUS UPDATE
    // ===============================
    @PostMapping("/drivers/status-update")
    public ResponseEntity<Void> driverStatusUpdated(
            @RequestParam String driverId,
            @RequestParam String status) {

        auditService.logStatusChange(driverId, status);
        return ResponseEntity.ok().build();
    }
}
