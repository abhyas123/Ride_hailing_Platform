package com.program.kafka.consumer;

import com.program.service.LocationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DriverStatusConsumer {

    private final LocationService locationService;

    public DriverStatusConsumer(LocationService locationService) {
        this.locationService = locationService;
    }

    @KafkaListener(
            topics = "driver-status-events",
            groupId = "location-service"
    )
    public void consume(DriverStatusEvent event) {

        if ("ONLINE".equals(event.getStatus())) {
            locationService.markDriverOnline(
                    event.getDriverId(),
                    event.getLatitude(),
                    event.getLongitude()
            );
        } else {
            locationService.markDriverOffline(event.getDriverId());
        }
    }

    // ---------- EVENT DTO ----------
    public static class DriverStatusEvent {

        private String driverId;
        private String status;
        private double latitude;
        private double longitude;

        public String getDriverId() {
            return driverId;
        }

        public String getStatus() {
            return status;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
