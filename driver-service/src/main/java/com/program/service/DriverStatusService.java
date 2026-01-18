package com.program.service;

import com.program.dto.request.UpdateDriverStatusRequest;
import com.program.entity.Driver;
import com.program.exception.DriverNotApprovedException;
import com.program.exception.DriverNotFoundException;
import com.program.repository.DriverRepository;
import com.program.util.DriverStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverStatusService {

    private final DriverRepository driverRepository;

    /* =====================================================
       PUBLIC API (REST CONTROLLER)
       ===================================================== */

    public void updateStatus(String driverId, UpdateDriverStatusRequest request) {
        UUID id = parseDriverId(driverId);
        updateStatusInternal(id, DriverStatus.valueOf(request.getStatus()));
    }

    /* =====================================================
       INTERNAL API (KAFKA, SYSTEM EVENTS)
       ===================================================== */

    public void updateStatus(UUID driverId, DriverStatus status) {
        updateStatusInternal(driverId, status);
    }

    /* =====================================================
       CORE LOGIC (SINGLE SOURCE OF TRUTH)
       ===================================================== */

    private void updateStatusInternal(UUID driverId, DriverStatus status) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        if (!driver.isApproved()) {
            throw new DriverNotApprovedException("Driver not approved by admin");
        }

        driver.setStatus(status.name());
        driverRepository.save(driver);
    }

    /* =====================================================
       UTIL
       ===================================================== */

    private UUID parseDriverId(String driverId) {
        try {
            return UUID.fromString(driverId);
        } catch (IllegalArgumentException e) {
            throw new DriverNotFoundException("Invalid driver id format");
        }
    }
}
