package com.program.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverEarningsResponse {

    private String driverId;
    private Double totalEarnings;
    private Double availableBalance;
}
