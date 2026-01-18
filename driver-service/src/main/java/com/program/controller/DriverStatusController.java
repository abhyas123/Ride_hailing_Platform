package com.program.controller;

import com.program.dto.request.UpdateDriverStatusRequest;
import com.program.service.DriverStatusService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers/status")
public class DriverStatusController {

    private final DriverStatusService statusService;

    public DriverStatusController(DriverStatusService statusService) {
        this.statusService = statusService;
    }

    @PutMapping
    public ResponseEntity<String> updateStatus(
            @RequestHeader("X-Driver-Id") String driverId,
            @Valid @RequestBody UpdateDriverStatusRequest request) {

        statusService.updateStatus(driverId, request);
        return ResponseEntity.ok("Driver status updated successfully");
    }
}
