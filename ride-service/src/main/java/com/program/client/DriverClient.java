package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "driver-service")
public interface DriverClient {

    /**
     * Notify drivers about a new ride request
     * @param notificationRequest Contains rideId and list of driverIds
     * @return Notification status
     */
    @PostMapping("/drivers/internal/notify-ride")
    Map<String, Object> notifyDriversAboutRide(@RequestBody Map<String, Object> notificationRequest);
}
