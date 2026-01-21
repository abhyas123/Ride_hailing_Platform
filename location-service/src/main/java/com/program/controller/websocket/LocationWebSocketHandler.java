package com.program.controller.websocket;

import com.program.service.LocationBroadcastService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketHandler {

    private final LocationBroadcastService broadcastService;

    public LocationWebSocketHandler(LocationBroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    // ============================
    // DRIVER SENDS LIVE LOCATION
    // ============================
    @MessageMapping("/location/update")
    public void handleLocationUpdate(@Payload LocationMessage message) {

        broadcastService.broadcastToRider(
                message.getRideId(),
                message.getDriverId(),
                message.getLatitude(),
                message.getLongitude()
        );

        broadcastService.broadcastToKafka(
                message.getRideId(),
                message.getDriverId(),
                message.getLatitude(),
                message.getLongitude()
        );
    }

    // ============================
    // SOCKET MESSAGE DTO
    // ============================
    public static class LocationMessage {

        private String rideId;
        private String driverId;
        private double latitude;
        private double longitude;

        public String getRideId() {
            return rideId;
        }

        public String getDriverId() {
            return driverId;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
