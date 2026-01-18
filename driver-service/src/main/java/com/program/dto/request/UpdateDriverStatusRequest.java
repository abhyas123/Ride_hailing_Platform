package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDriverStatusRequest {

    @NotBlank(message = "Status is required")
    private String status; // ONLINE, OFFLINE, BUSY
}
