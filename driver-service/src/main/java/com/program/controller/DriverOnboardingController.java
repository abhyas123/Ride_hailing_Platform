package com.program.controller;

import com.program.dto.request.AddVehicleRequest;
import com.program.dto.request.UpdateDriverProfileRequest;
import com.program.dto.request.UploadKycRequest;
import com.program.service.DriverOnboardingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers/onboarding")
public class DriverOnboardingController {

    private final DriverOnboardingService onboardingService;

    public DriverOnboardingController(DriverOnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @RequestHeader("X-Driver-Id") String driverId,
            @Valid @RequestBody UpdateDriverProfileRequest request) {

        onboardingService.updateProfile(driverId, request);
        return ResponseEntity.ok("Driver profile updated successfully");
    }

    @PostMapping("/kyc")
    public ResponseEntity<String> uploadKyc(
            @RequestHeader("X-Driver-Id") String driverId,
            @Valid @RequestBody UploadKycRequest request) {

        onboardingService.uploadKyc(driverId, request);
        return ResponseEntity.ok("KYC submitted for verification");
    }

    @PostMapping("/vehicle")
    public ResponseEntity<String> addVehicle(
            @RequestHeader("X-Driver-Id") String driverId,
            @Valid @RequestBody AddVehicleRequest request) {

        onboardingService.addVehicle(driverId, request);
        return ResponseEntity.ok("Vehicle details added successfully");
    }
}
