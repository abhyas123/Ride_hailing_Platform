package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "location-service",
        configuration = com.program.config.FeignConfig.class
)
public interface LocationServiceClient {

    @GetMapping("/location/internal/distance")
    Double calculateDistanceKm(
            @RequestParam Double startLat,
            @RequestParam Double startLng,
            @RequestParam Double endLat,
            @RequestParam Double endLng
    );
}
