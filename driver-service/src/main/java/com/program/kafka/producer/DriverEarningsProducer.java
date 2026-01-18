package com.program.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DriverEarningsProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DriverEarningsProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEarnings(UUID driverId, Double amount) {
        DriverEarningsEvent event = new DriverEarningsEvent(driverId, amount);
        kafkaTemplate.send("driver-earnings-events", event);
    }

    public static class DriverEarningsEvent {
        private UUID driverId;
        private Double amount;

        public DriverEarningsEvent(UUID driverId, Double amount) {
            this.driverId = driverId;
            this.amount = amount;
        }

        public UUID getDriverId() {
            return driverId;
        }

        public Double getAmount() {
            return amount;
        }
    }
}
