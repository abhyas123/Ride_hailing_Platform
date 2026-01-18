package com.program.controller;

import com.program.dto.request.CreateDriverRequest;
import com.program.exception.DriverNotFoundException;
import com.program.service.DriverApprovalService;
import com.program.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/drivers/internal")
public class InternalDriverController {

    private final DriverService driverService;
    private final DriverApprovalService driverApprovalService;

    public InternalDriverController(DriverService driverService,
                                    DriverApprovalService driverApprovalService) {
        this.driverService = driverService;
        this.driverApprovalService = driverApprovalService;
    }

    // ===== CREATE DRIVER =====
    @PostMapping("/create")
    public ResponseEntity<Void> createDriver(
            @Valid @RequestBody CreateDriverRequest request) {

        driverService.createDriver(request);
        return ResponseEntity.ok().build();
    }

    // ===== APPROVE DRIVER =====
    @PutMapping("/{driverId}/approve")
    public ResponseEntity<String> approveDriver(
            @PathVariable String driverId) {

        UUID id = parseUUID(driverId);
        driverApprovalService.approveDriver(id);
        return ResponseEntity.ok("Driver approved successfully");
    }

    // ===== REJECT DRIVER =====
    @PutMapping("/{driverId}/reject")
    public ResponseEntity<String> rejectDriver(
            @PathVariable String driverId,
            @RequestParam String reason) {

        UUID id = parseUUID(driverId);
        driverApprovalService.rejectDriver(id, reason);
        return ResponseEntity.ok("Driver rejected");
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new DriverNotFoundException("Invalid driver id format");
        }
    }
}
