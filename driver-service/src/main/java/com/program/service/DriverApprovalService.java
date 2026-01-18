package com.program.service;

import com.program.entity.Driver;
import com.program.entity.DriverKyc;
import com.program.exception.DriverNotFoundException;
import com.program.repository.DriverKycRepository;
import com.program.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverApprovalService {

    private final DriverRepository driverRepository;
    private final DriverKycRepository kycRepository;

    public void approveDriver(UUID driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverKyc kyc = kycRepository.findByDriverId(driverId)
                .orElseThrow(() -> new IllegalStateException("KYC not submitted"));

        kyc.setStatus("VERIFIED");
        driver.setApproved(true);
        driver.setStatus("OFFLINE");

        kycRepository.save(kyc);
        driverRepository.save(driver);
    }

    public void rejectDriver(UUID driverId, String reason) {

        DriverKyc kyc = kycRepository.findByDriverId(driverId)
                .orElseThrow(() -> new IllegalStateException("KYC not found"));

        kyc.setStatus("REJECTED");
        kyc.setRejectionReason(reason); // ✅ IMPORTANT

        kycRepository.save(kyc);
    }
}
