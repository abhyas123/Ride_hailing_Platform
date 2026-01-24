package com.program.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareBreakdownResponse {

    private Double baseFare;
    private Double distanceFare;
    private Double timeFare;
    private Double surgeFare;
    private Double platformFee;
    private Double driverEarning;
}
