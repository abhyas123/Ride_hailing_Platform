package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "driver-service",
        path = "/drivers/internal"
)
public interface DriverServiceClient {

    @PutMapping("/{driverId}/status")
    void updateDriverStatus(
            @PathVariable("driverId") UUID driverId,
            @RequestParam("status") String status
    );

    @GetMapping("/{driverId}/approved")
    Boolean isDriverApproved(
            @PathVariable("driverId") UUID driverId
    );
}
