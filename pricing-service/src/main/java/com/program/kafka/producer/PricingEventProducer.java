package com.program.kafka.producer;

import lombok.Getter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PricingEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PricingEventProducer(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // ===============================
    // FARE FINALIZED EVENT
    // ===============================
    public void publishFareFinalized(
            String rideId,
            Double distanceInKm,
            Integer durationInMinutes
    ) {

        PricingEvent event = new PricingEvent(
                rideId,
                distanceInKm,
                durationInMinutes
        );

        kafkaTemplate.send("pricing-finalized-events", rideId, event);
    }

    // ===============================
    // EVENT DTO
    // ===============================
    @Getter
    public static class PricingEvent {

        private String rideId;
        private Double distanceInKm;
        private Integer durationInMinutes;

        public PricingEvent(
                String rideId,
                Double distanceInKm,
                Integer durationInMinutes
        ) {
            this.rideId = rideId;
            this.distanceInKm = distanceInKm;
            this.durationInMinutes = durationInMinutes;
        }
    }
}
