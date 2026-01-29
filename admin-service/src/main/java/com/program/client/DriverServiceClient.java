package com.program.client;

import com.program.dto.response.DriverDetailsResponse;
import com.program.dto.response.PendingDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "driver-service",
        path = "/drivers/internal"
)
public interface DriverServiceClient {

    // ===============================
    // GET PENDING DRIVERS
    // ===============================
    @GetMapping("/pending")
    List<PendingDriverResponse> getPendingDrivers();

    // ===============================
    // GET DRIVER DETAILS
    // ===============================
    @GetMapping("/{driverId}/details")
    DriverDetailsResponse getDriverDetails(
            @PathVariable("driverId") String driverId
    );

    // ===============================
    // CHECK APPROVAL STATUS
    // ===============================
    @GetMapping("/{driverId}/approved")
    boolean isDriverApproved(
            @PathVariable("driverId") String driverId
    );

    // ===============================
    // APPROVE DRIVER
    // ===============================
    @PutMapping("/{driverId}/approve")
    void approveDriver(
            @PathVariable("driverId") String driverId
    );

    // ===============================
    // REJECT DRIVER
    // ===============================
    @PutMapping("/{driverId}/reject")
    void rejectDriver(
            @PathVariable("driverId") String driverId,
            @RequestParam("reason") String reason
    );
}
