package com.program.service;

import com.program.dto.request.AddVehicleRequest;
import com.program.dto.request.UpdateDriverProfileRequest;
import com.program.dto.request.UploadKycRequest;
import com.program.entity.Driver;
import com.program.entity.DriverKyc;
import com.program.entity.DriverVehicle;
import com.program.exception.DriverNotFoundException;
import com.program.repository.DriverKycRepository;
import com.program.repository.DriverRepository;
import com.program.repository.DriverVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverOnboardingService {

    private final DriverRepository driverRepository;
    private final DriverKycRepository driverKycRepository;
    private final DriverVehicleRepository driverVehicleRepository;

    /* ================= PROFILE ================= */

    public void updateProfile(String driverId, UpdateDriverProfileRequest request) {

        UUID id = parseDriverId(driverId);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        driver.setName(request.getName());
        driver.setProfilePhotoUrl(request.getProfilePhotoUrl());

        driverRepository.save(driver);
    }

    /* ================= KYC ================= */

    public void uploadKyc(String driverId, UploadKycRequest request) {

        UUID id = parseDriverId(driverId);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverKyc kyc = DriverKyc.builder()
                .driver(driver)
                .aadhaarNumber(request.getAadhaarNumber())
                .panNumber(request.getPanNumber())
                .drivingLicenseNumber(request.getDrivingLicenseNumber())
                .status("PENDING")
                .build();

        driverKycRepository.save(kyc);
    }

    /* ================= VEHICLE ================= */

    public void addVehicle(String driverId, AddVehicleRequest request) {

        UUID id = parseDriverId(driverId);

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverVehicle vehicle = DriverVehicle.builder()
                .driver(driver)
                .vehicleType(request.getVehicleType())
                .vehicleNumber(request.getVehicleNumber())
                .rcDocumentUrl(request.getRcDocumentUrl())
                .insuranceDocumentUrl(request.getInsuranceDocumentUrl())
                .active(true)
                .build();

        driverVehicleRepository.save(vehicle);
    }

    /* ================= UTIL ================= */

    private UUID parseDriverId(String driverId) {
        try {
            return UUID.fromString(driverId);
        } catch (IllegalArgumentException e) {
            throw new DriverNotFoundException("Invalid driver id format");
        }
    }
}
