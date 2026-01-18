package com.program.controller;

import com.program.dto.response.DriverProfileResponse;
import com.program.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/me")
    public ResponseEntity<DriverProfileResponse> getMyProfile(
            @RequestHeader("X-Driver-Id") String driverId) {

        DriverProfileResponse response = driverService.getDriverProfile(driverId);
        return ResponseEntity.ok(response);
    }

}
