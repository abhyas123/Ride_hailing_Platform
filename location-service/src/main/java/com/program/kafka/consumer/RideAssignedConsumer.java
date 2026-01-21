package com.program.kafka.consumer;

import com.program.service.LocationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RideAssignedConsumer {

    private final LocationService locationService;

    public RideAssignedConsumer(LocationService locationService) {
        this.locationService = locationService;
    }

    @KafkaListener(
            topics = "ride-assigned-events",
            groupId = "location-service"
    )
    public void consume(RideAssignedEvent event) {
        locationService.markDriverBusy(event.getDriverId());
    }

    // ---------- EVENT DTO ----------
    public static class RideAssignedEvent {

        private String rideId;
        private String driverId;

        public String getRideId() {
            return rideId;
        }

        public String getDriverId() {
            return driverId;
        }
    }
}
