package com.program.controller;

import com.program.service.DriverLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location/internal")
public class InternalLocationController {

    private final DriverLocationService driverLocationService;

    public InternalLocationController(DriverLocationService driverLocationService) {
        this.driverLocationService = driverLocationService;
    }

    // ============================
    // FORCE REMOVE DRIVER LOCATION
    // ============================
    @DeleteMapping("/driver/{driverId}")
    public ResponseEntity<String> removeDriver(@PathVariable String driverId) {
        driverLocationService.removeDriver(driverId);
        return ResponseEntity.ok("Driver removed from location service");
    }
}
