package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "ride-service",
        path = "/rides/internal"
)
public interface RideServiceClient {

    @PostMapping("/{rideId}/driver-assigned")
    void notifyDriverAssigned(
            @PathVariable("rideId") UUID rideId,
            @RequestParam("driverId") UUID driverId
    );

    @PostMapping("/{rideId}/started")
    void notifyRideStarted(
            @PathVariable("rideId") UUID rideId
    );

    @PostMapping("/{rideId}/completed")
    void notifyRideCompleted(
            @PathVariable("rideId") UUID rideId
    );
}
