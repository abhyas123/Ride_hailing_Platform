package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "admin-service",
        path = "/admin/internal"
)
public interface AdminServiceClient {

    @PostMapping("/drivers/onboarding-complete")
    void notifyOnboardingComplete(
            @RequestParam("driverId") String driverId
    );

    @PostMapping("/drivers/status-update")
    void notifyDriverStatusChange(
            @RequestParam("driverId") String driverId,
            @RequestParam("status") String status
    );
}
