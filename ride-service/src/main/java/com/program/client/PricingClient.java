package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "pricing-service")
public interface PricingClient {

    /**
     * Get fare estimate for a ride
     * @param source Source location
     * @param destination Destination location
     * @param distance Distance in km
     * @return Fare estimate details
     */
    @GetMapping("/pricing/estimate")
    Map<String, Object> getFareEstimate(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("distance") Double distance
    );

    /**
     * Calculate final fare after ride completion
     * @param rideId Ride ID
     * @param distance Actual distance traveled
     * @param duration Duration in minutes
     * @return Final fare details
     */
    @GetMapping("/pricing/calculate")
    Map<String, Object> calculateFinalFare(
            @RequestParam("rideId") String rideId,
            @RequestParam("distance") Double distance,
            @RequestParam("duration") Integer duration
    );
}
