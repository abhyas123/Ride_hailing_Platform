package com.program.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocationUpdateProducer {

    private final KafkaTemplate<String, LocationUpdateEvent> kafkaTemplate;

    public LocationUpdateProducer(
            KafkaTemplate<String, LocationUpdateEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishLocationUpdate(
            String rideId,
            String driverId,
            double latitude,
            double longitude
    ) {

        LocationUpdateEvent event = new LocationUpdateEvent(
                rideId,
                driverId,
                latitude,
                longitude,
                LocalDateTime.now()
        );

        kafkaTemplate.send(
                "location-update-events",
                driverId,
                event
        );
    }

    // ===============================
    // EVENT DTO
    // ===============================
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationUpdateEvent {
        private String rideId;
        private String driverId;
        private double latitude;
        private double longitude;
        private LocalDateTime timestamp;
    }
}
