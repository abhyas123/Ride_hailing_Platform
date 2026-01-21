package com.program.kafka.consumer;

import com.program.service.LocationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideCompletedConsumer {

    private final LocationService locationService;

    public RideCompletedConsumer(LocationService locationService) {
        this.locationService = locationService;
    }

    @KafkaListener(
            topics = "ride-completed-events",
            groupId = "location-service"
    )
    public void consume(RideCompletedEvent event) {

        locationService.markDriverOnline(
                event.getDriverId(),
                event.getLastLatitude(),
                event.getLastLongitude()
        );
    }

    // ---------- EVENT DTO ----------
    public static class RideCompletedEvent {

        private String rideId;
        private String driverId;
        private double lastLatitude;
        private double lastLongitude;

        public String getRideId() {
            return rideId;
        }

        public String getDriverId() {
            return driverId;
        }

        public double getLastLatitude() {
            return lastLatitude;
        }

        public double getLastLongitude() {
            return lastLongitude;
        }
    }
}
