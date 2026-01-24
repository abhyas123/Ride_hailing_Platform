package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "ride-service",
        configuration = com.program.config.FeignConfig.class
)
public interface RideServiceClient {

    @PostMapping("/rides/internal/pricing-updated")
    void notifyPricingUpdated(
            @RequestParam UUID rideId,
            @RequestParam Double finalFare
    );
}
