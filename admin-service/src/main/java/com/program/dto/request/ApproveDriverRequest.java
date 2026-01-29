package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveDriverRequest {

    @NotBlank(message = "Driver ID is required")
    private String driverId;
}
