package com.program.kafka.consumer;

import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideCancelledConsumer {

    @KafkaListener(
            topics = "ride-cancelled-events",
            groupId = "pricing-service"
    )
    public void consume(RideCancelledEvent event) {

        // Currently no pricing action required
        // To be Reserved for audit / refund logic
    }

    // ===============================
    // EVENT DTO (Consumed)
    // ===============================
    @Getter
    public static class RideCancelledEvent {

        private String rideId;
        private String reason;

    }
}
