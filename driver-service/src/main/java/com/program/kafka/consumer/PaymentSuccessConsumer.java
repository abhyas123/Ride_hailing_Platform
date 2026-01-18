package com.program.kafka.consumer;

import com.program.service.DriverEarningsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentSuccessConsumer {

    private final DriverEarningsService earningsService;

    public PaymentSuccessConsumer(DriverEarningsService earningsService) {
        this.earningsService = earningsService;
    }

    @KafkaListener(
            topics = "payment-success-events",
            groupId = "driver-service"
    )
    public void consume(PaymentSuccessEvent event) {
        earningsService.addEarnings(
                event.getDriverId(),
                event.getDriverAmount()
        );
    }

    public static class PaymentSuccessEvent {
        private UUID driverId;
        private Double driverAmount;

        public UUID getDriverId() {
            return driverId;
        }

        public Double getDriverAmount() {
            return driverAmount;
        }
    }
}
