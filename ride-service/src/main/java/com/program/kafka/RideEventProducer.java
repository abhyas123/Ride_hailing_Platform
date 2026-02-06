package com.program.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String RIDE_EVENTS_TOPIC = "ride-events";

    /**
     * Publish ride created event
     */
    public void publishRideCreated(UUID rideId, String source, String dest, Double fare) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "RIDE_CREATED");
        event.put("rideId", rideId.toString());
        event.put("source", source);
        event.put("destination", dest);
        event.put("estimatedFare", fare);
        event.put("timestamp", System.currentTimeMillis());

        kafkaTemplate.send(RIDE_EVENTS_TOPIC, rideId.toString(), event);
        log.info("Published RIDE_CREATED event for ride: {}", rideId);
    }

    /**
     * Publish driver assigned event
     */
    public void publishDriverAssigned(UUID rideId, UUID driverId, String otp) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "DRIVER_ASSIGNED");
        event.put("rideId", rideId.toString());
        event.put("driverId", driverId.toString());
        event.put("otp", otp);
        event.put("timestamp", System.currentTimeMillis());

        kafkaTemplate.send(RIDE_EVENTS_TOPIC, rideId.toString(), event);
        log.info("Published DRIVER_ASSIGNED event for ride: {}", rideId);
    }

    /**
     * Publish ride completed event
     */
    public void publishRideCompleted(UUID rideId, UUID driverId, Double finalFare) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "RIDE_COMPLETED");
        event.put("rideId", rideId.toString());
        event.put("driverId", driverId.toString());
        event.put("finalFare", finalFare);
        event.put("timestamp", System.currentTimeMillis());

        kafkaTemplate.send(RIDE_EVENTS_TOPIC, rideId.toString(), event);
        log.info("Published RIDE_COMPLETED event for ride: {}", rideId);
    }

    /**
     * Publish ride cancelled event
     */
    public void publishRideCancelled(UUID rideId, String reason) {
        Map<String, Object> event = new HashMap<>();
        event.put("eventType", "RIDE_CANCELLED");
        event.put("rideId", rideId.toString());
        event.put("reason", reason);
        event.put("timestamp", System.currentTimeMillis());

        kafkaTemplate.send(RIDE_EVENTS_TOPIC, rideId.toString(), event);
        log.info("Published RIDE_CANCELLED event for ride: {}", rideId);
    }
}
