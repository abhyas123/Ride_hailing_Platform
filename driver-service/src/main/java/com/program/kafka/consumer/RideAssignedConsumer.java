package com.program.kafka.consumer;

import com.program.service.DriverStatusService;
import com.program.util.DriverStatus;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RideAssignedConsumer {

    private final DriverStatusService driverStatusService;

    public RideAssignedConsumer(DriverStatusService driverStatusService) {
        this.driverStatusService = driverStatusService;
    }

    @KafkaListener(
            topics = "ride-assigned-events",
            groupId = "driver-service"
    )
    public void consume(RideAssignedEvent event) {
        driverStatusService.updateStatus(
                event.getDriverId(),
                DriverStatus.BUSY
        );
    }

    @Getter
    public static class RideAssignedEvent {
        private UUID driverId;
        private UUID rideId;

    }
}
