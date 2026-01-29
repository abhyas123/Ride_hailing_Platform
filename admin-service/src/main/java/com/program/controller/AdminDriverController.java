package com.program.controller;

import com.program.dto.request.ApproveDriverRequest;
import com.program.dto.request.RejectDriverRequest;
import com.program.dto.response.DriverDetailsResponse;
import com.program.dto.response.PendingDriverResponse;
import com.program.service.AdminDriverApprovalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/drivers")
public class AdminDriverController {

    private final AdminDriverApprovalService approvalService;

    public AdminDriverController(AdminDriverApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // ===============================
    // GET ALL PENDING DRIVERS
    // ===============================
    @GetMapping("/pending")
    public ResponseEntity<List<PendingDriverResponse>> getPendingDrivers() {
        return ResponseEntity.ok(approvalService.getPendingDrivers());
    }

    // ===============================
    // GET DRIVER DETAILS
    // ===============================
    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDetailsResponse> getDriverDetails(
            @PathVariable String driverId) {

        return ResponseEntity.ok(
                approvalService.getDriverDetails(driverId)
        );
    }

    // ===============================
    // APPROVE DRIVER
    // ===============================
    @PostMapping("/approve")
    public ResponseEntity<String> approveDriver(
            @Valid @RequestBody ApproveDriverRequest request) {

        approvalService.approveDriver(request);
        return ResponseEntity.ok("Driver approved successfully");
    }

    // ===============================
    // REJECT DRIVER
    // ===============================
    @PostMapping("/reject")
    public ResponseEntity<String> rejectDriver(
            @Valid @RequestBody RejectDriverRequest request) {

        approvalService.rejectDriver(request);
        return ResponseEntity.ok("Driver rejected successfully");
    }
}
