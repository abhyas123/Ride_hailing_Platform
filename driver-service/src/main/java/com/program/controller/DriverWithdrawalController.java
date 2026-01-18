package com.program.controller;

import com.program.dto.request.WithdrawRequest;
import com.program.dto.response.WithdrawalResponse;
import com.program.service.DriverWithdrawalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers/withdrawals")
public class DriverWithdrawalController {

    private final DriverWithdrawalService withdrawalService;

    public DriverWithdrawalController(DriverWithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping
    public ResponseEntity<WithdrawalResponse> withdraw(
            @RequestHeader("X-Driver-Id") String driverId,
            @Valid @RequestBody WithdrawRequest request) {

        WithdrawalResponse response =
                withdrawalService.withdraw(driverId, request);

        return ResponseEntity.ok(response);
    }
}
