package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ride-service",
        path = "/rides/internal"
)
public interface RideServiceClient {

    @PostMapping("/driver/assigned")
    void notifyDriverAssigned(
            @RequestParam("rideId") String rideId,
            @RequestParam("driverId") String driverId
    );

    @PostMapping("/driver/status")
    void updateDriverStatus(
            @RequestParam("driverId") String driverId,
            @RequestParam("status") String status
    );
}
