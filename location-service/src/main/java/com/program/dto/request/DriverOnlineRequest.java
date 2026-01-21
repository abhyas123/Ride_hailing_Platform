package com.program.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverOnlineRequest {

    @NotNull(message = "Online status is required")
    private Boolean online;
}
