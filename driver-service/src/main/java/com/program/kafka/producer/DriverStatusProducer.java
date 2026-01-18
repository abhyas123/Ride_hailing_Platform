package com.program.kafka.producer;

import com.program.util.DriverStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DriverStatusProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DriverStatusProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStatus(UUID driverId, DriverStatus status) {
        DriverStatusEvent event = new DriverStatusEvent(driverId, status.name());
        kafkaTemplate.send("driver-status-events", event);
    }

    public static class DriverStatusEvent {
        private UUID driverId;
        private String status;

        public DriverStatusEvent(UUID driverId, String status) {
            this.driverId = driverId;
            this.status = status;
        }

        public UUID getDriverId() {
            return driverId;
        }

        public String getStatus() {
            return status;
        }
    }
}
