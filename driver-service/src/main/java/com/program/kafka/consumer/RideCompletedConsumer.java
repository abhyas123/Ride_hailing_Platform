package com.program.kafka.consumer;

import com.program.service.DriverStatusService;
import com.program.util.DriverStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RideCompletedConsumer {

    private final DriverStatusService driverStatusService;

    public RideCompletedConsumer(DriverStatusService driverStatusService) {
        this.driverStatusService = driverStatusService;
    }

    @KafkaListener(
            topics = "ride-completed-events",
            groupId = "driver-service"
    )
    public void consume(RideCompletedEvent event) {

        driverStatusService.updateStatus(
                event.getDriverId(),
                DriverStatus.ONLINE   // ✅ FIXED
        );
    }

    public static class RideCompletedEvent {
        private UUID driverId;
        private UUID rideId;

        public UUID getDriverId() {
            return driverId;
        }

        public UUID getRideId() {
            return rideId;
        }
    }
}
