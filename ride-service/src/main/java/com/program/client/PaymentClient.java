package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    /**
     * Process payment for a completed ride
     * @param paymentRequest Contains rideId, amount, driverId, userId
     * @return Payment processing status
     */
    @PostMapping("/payments/process")
    Map<String, Object> processRidePayment(@RequestBody Map<String, Object> paymentRequest);
}
