package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "location-service")
public interface LocationClient {

    /**
     * Find nearby drivers within a radius
     * @param latitude Pickup latitude
     * @param longitude Pickup longitude
     * @param radiusKm Search radius in kilometers
     * @return List of nearby driver details
     */
    @GetMapping("/location/rider/nearby-drivers")
    List<Map<String, Object>> findNearbyDrivers(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radiusKm") double radiusKm
    );
}
