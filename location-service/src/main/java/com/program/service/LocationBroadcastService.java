package com.program.service;

import com.program.kafka.producer.LocationUpdateProducer;
import com.program.dto.response.RideLocationUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationBroadcastService {

    private final SimpMessagingTemplate messagingTemplate;
    private final LocationUpdateProducer locationUpdateProducer;

    // ================================
    // BROADCAST TO RIDER (WebSocket)
    // ================================
    public void broadcastToRider(
            String rideId,
            String driverId,
            double latitude,
            double longitude
    ) {

        RideLocationUpdateResponse response =
                new RideLocationUpdateResponse(
                        rideId,
                        driverId,
                        latitude,
                        longitude,
                        LocalDateTime.now()
                );

        messagingTemplate.convertAndSend(
                "/topic/ride/" + rideId + "/location",
                response
        );
    }

    // ================================
    // BROADCAST TO SYSTEM (Kafka)
    // ================================
    public void broadcastToKafka(
            String rideId,
            String driverId,
            double latitude,
            double longitude
    ) {

        locationUpdateProducer.publishLocationUpdate(
                rideId,
                driverId,
                latitude,
                longitude
        );
    }
}
