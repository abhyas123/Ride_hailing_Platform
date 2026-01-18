package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalResponse {

    private String withdrawalId;
    private String driverId;
    private Double amount;
    private String status; // PENDING, SUCCESS, FAILED
    private LocalDateTime requestedAt;
}
