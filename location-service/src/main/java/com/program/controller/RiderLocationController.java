package com.program.controller;

import com.program.dto.response.NearbyDriverResponse;
import com.program.dto.response.RideLocationUpdateResponse;
import com.program.service.DriverLocationService;
import com.program.service.RiderTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location/rider")
public class RiderLocationController {

    private final DriverLocationService driverLocationService;
    private final RiderTrackingService riderTrackingService;

    public RiderLocationController(
            DriverLocationService driverLocationService,
            RiderTrackingService riderTrackingService
    ) {
        this.driverLocationService = driverLocationService;
        this.riderTrackingService = riderTrackingService;
    }

    // ============================
    // FIND NEARBY DRIVERS
    // ============================
    @GetMapping("/nearby-drivers")
    public ResponseEntity<List<NearbyDriverResponse>> findNearbyDrivers(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radiusKm
    ) {

        List<NearbyDriverResponse> drivers =
                driverLocationService.findNearbyDrivers(latitude, longitude, radiusKm);

        return ResponseEntity.ok(drivers);
    }

    // ============================
    // GET DRIVER LIVE LOCATION
    // ============================
    @GetMapping("/driver-location/{driverId}")
    public ResponseEntity<RideLocationUpdateResponse> getDriverLocation(
            @PathVariable String driverId,
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {

        RideLocationUpdateResponse response =
                riderTrackingService.getDriverLiveLocation(driverId, latitude, longitude);

        return ResponseEntity.ok(response);
    }
}
