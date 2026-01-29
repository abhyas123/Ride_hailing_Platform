package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "driver-service",
        path = "/drivers/internal"
)
public interface DriverServiceClient {

    // ===== APPROVE DRIVER =====
    @PutMapping("/{driverId}/approve")
    void approveDriver(
            @PathVariable("driverId") String driverId
    );

    // ===== REJECT DRIVER =====
    @PutMapping("/{driverId}/reject")
    void rejectDriver(
            @PathVariable("driverId") String driverId,
            @RequestParam("reason") String reason
    );
}
