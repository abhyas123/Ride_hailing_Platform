package com.program.service;

import com.program.dto.request.WithdrawRequest;
import com.program.dto.response.WithdrawalResponse;
import com.program.entity.Driver;
import com.program.entity.DriverEarnings;
import com.program.entity.DriverWithdrawal;
import com.program.exception.DriverNotFoundException;
import com.program.repository.DriverEarningsRepository;
import com.program.repository.DriverRepository;
import com.program.repository.DriverWithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverWithdrawalService {

    private final DriverRepository driverRepository;
    private final DriverEarningsRepository earningsRepository;
    private final DriverWithdrawalRepository withdrawalRepository;

    /**
     * Driver requests withdrawal
     */
    public WithdrawalResponse withdraw(String driverId, WithdrawRequest request) {

        UUID id;
        try {
            id = UUID.fromString(driverId);
        } catch (IllegalArgumentException e) {
            throw new DriverNotFoundException("Invalid driver id format");
        }

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));

        DriverEarnings earnings = earningsRepository.findByDriverId(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver earnings not found"));

        double amount = request.getAmount();

        if (amount <= 0) {
            throw new IllegalStateException("Withdrawal amount must be greater than zero");
        }

        if (earnings.getAvailableBalance() < amount) {
            throw new IllegalStateException("Insufficient balance");
        }

        // deduct balance
        earnings.setAvailableBalance(
                earnings.getAvailableBalance() - amount
        );
        earningsRepository.save(earnings);

        // create withdrawal record
        DriverWithdrawal withdrawal = DriverWithdrawal.builder()
                .driver(driver)
                .amount(amount)
                .status("PENDING")
                .requestedAt(LocalDateTime.now())
                .build();

        withdrawalRepository.save(withdrawal);

        // response
        return new WithdrawalResponse(
                withdrawal.getId().toString(),
                driver.getId().toString(),
                withdrawal.getAmount(),
                withdrawal.getStatus(),
                withdrawal.getRequestedAt()
        );
    }
}
