package com.program.service;

import com.program.dto.response.DriverEarningsResponse;
import com.program.entity.Driver;
import com.program.entity.DriverEarnings;
import com.program.exception.DriverNotFoundException;
import com.program.repository.DriverEarningsRepository;
import com.program.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverEarningsService {

    private final DriverRepository driverRepository;
    private final DriverEarningsRepository earningsRepository;

    public DriverEarningsResponse getEarnings(String driverId) {

        UUID id;
        try {
            id = UUID.fromString(driverId);
        } catch (IllegalArgumentException e) {
            throw new DriverNotFoundException("Invalid driver id format");
        }

        DriverEarnings earnings = earningsRepository.findByDriverId(id)
                .orElseThrow(() -> new DriverNotFoundException("Earnings not found"));

        return new DriverEarningsResponse(
                earnings.getDriver().getId().toString(),
                earnings.getTotalEarnings(),
                earnings.getAvailableBalance()
        );
    }


    public void addEarnings(UUID driverId, double amount) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverEarnings earnings = earningsRepository.findByDriverId(driverId)
                .orElse(
                        DriverEarnings.builder()
                                .driver(driver)
                                .totalEarnings(0.0)
                                .availableBalance(0.0)
                                .build()
                );

        earnings.setTotalEarnings(earnings.getTotalEarnings() + amount);
        earnings.setAvailableBalance(earnings.getAvailableBalance() + amount);

        earningsRepository.save(earnings);
    }
}
