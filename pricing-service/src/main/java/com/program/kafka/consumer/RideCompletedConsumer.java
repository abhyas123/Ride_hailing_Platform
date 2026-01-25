package com.program.kafka.consumer;

import com.program.kafka.producer.PricingEventProducer;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideCompletedConsumer {

    private final PricingEventProducer pricingEventProducer;

    public RideCompletedConsumer(PricingEventProducer pricingEventProducer) {
        this.pricingEventProducer = pricingEventProducer;
    }

    @KafkaListener(
            topics = "ride-completed-events",
            groupId = "pricing-service"
    )
    public void consume(RideCompletedEvent event) {

        // Emit pricing finalization event
        pricingEventProducer.publishFareFinalized(
                event.getRideId(),
                event.getDistanceInKm(),
                event.getDurationInMinutes()
        );
    }

    // ===============================
    // EVENT DTO (Consumed)
    // ===============================
    @Getter
    public static class RideCompletedEvent {

        private String rideId;
        private Double distanceInKm;
        private Integer durationInMinutes;

    }
}
