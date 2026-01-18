package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "payment-service",
        path = "/payments/internal"
)
public interface PaymentServiceClient {

    @PostMapping("/driver/credit")
    void creditDriverEarnings(
            @RequestParam("driverId") String driverId,
            @RequestParam("amount") double amount,
            @RequestParam("rideId") String rideId
    );
}
