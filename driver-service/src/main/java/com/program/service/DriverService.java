package com.program.service;

import com.program.dto.request.CreateDriverRequest;
import com.program.dto.response.DriverProfileResponse;
import com.program.entity.Driver;
import com.program.exception.DriverNotFoundException;
import com.program.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DriverService {

    private final DriverRepository driverRepository;

    @Transactional
    public void createDriver(CreateDriverRequest request) {

        // prevent duplicate driver creation
        boolean exists = driverRepository.existsByPhone(request.getPhone());
        if (exists) {
            return; // idempotent behaviour (important)
        }

        Driver driver = Driver.builder()
                .phone(request.getPhone())
                .status("INACTIVE")   // cannot take rides yet
                .approved(false)
                .build();

        driverRepository.save(driver);
    }


    public Driver getDriver(UUID driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));
    }

    public DriverProfileResponse getDriverProfile(String driverId) {

        UUID id;
        try {
            id = UUID.fromString(driverId);
        } catch (IllegalArgumentException e) {
            throw new DriverNotFoundException("Invalid driver id format");
        }

        Driver driver = getDriver(id);

        return new DriverProfileResponse(
                driver.getId().toString(),
                driver.getPhone(),
                driver.getName(),
                driver.getProfilePhotoUrl(),
                driver.getStatus(), // ✅ FIX IS HERE
                driver.isApproved()
        );
    }



    @Transactional
    public Driver updateProfile(UUID driverId, String name, String profilePhotoUrl) {
        Driver driver = getDriver(driverId);
        driver.setName(name);
        driver.setProfilePhotoUrl(profilePhotoUrl);
        return driverRepository.save(driver);
    }
}
