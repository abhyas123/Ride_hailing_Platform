package com.program.controller;

import com.program.dto.request.UpdateDriverLocationRequest;
import com.program.service.DriverLocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location/driver")
public class DriverLocationController {

    private final DriverLocationService driverLocationService;

    public DriverLocationController(DriverLocationService driverLocationService) {
        this.driverLocationService = driverLocationService;
    }

    // ============================
    // DRIVER UPDATES LOCATION
    // ============================
    @PutMapping("/update")
    public ResponseEntity<String> updateLocation(
            @RequestHeader("X-Driver-Id") String driverId,
            @Valid @RequestBody UpdateDriverLocationRequest request
    ) {

        driverLocationService.updateLocation(
                driverId,
                request.getLatitude(),
                request.getLongitude()
        );

        return ResponseEntity.ok("Driver location updated");
    }

    // ============================
    // DRIVER GOES OFFLINE
    // ============================
    @DeleteMapping("/offline")
    public ResponseEntity<String> goOffline(
            @RequestHeader("X-Driver-Id") String driverId
    ) {
        driverLocationService.removeDriver(driverId);
        return ResponseEntity.ok("Driver is offline");
    }
}
