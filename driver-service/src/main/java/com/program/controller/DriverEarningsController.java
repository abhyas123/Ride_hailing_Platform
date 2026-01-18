package com.program.controller;

import com.program.dto.response.DriverEarningsResponse;
import com.program.service.DriverEarningsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers/earnings")
public class DriverEarningsController {

    private final DriverEarningsService earningsService;

    public DriverEarningsController(DriverEarningsService earningsService) {
        this.earningsService = earningsService;
    }

    @GetMapping
    public ResponseEntity<DriverEarningsResponse> getEarnings(
            @RequestHeader("X-Driver-Id") String driverId) {

        DriverEarningsResponse response = earningsService.getEarnings(driverId);
        return ResponseEntity.ok(response);
    }
}
