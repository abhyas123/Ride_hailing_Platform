package com.program.service;

import com.program.dto.response.RideLocationUpdateResponse;
import com.program.repository.DriverLocationRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RiderTrackingService {

    private final DriverLocationRedisRepository locationRepository;

    /**
     * NOTE:
     * In real systems this is triggered via WebSocket subscription.
     * Here service is used by controller / socket handler.
     */
    public RideLocationUpdateResponse getDriverLiveLocation(
            String driverId,
            double latitude,
            double longitude
    ) {

        return new RideLocationUpdateResponse(
                null,
                driverId,
                latitude,
                longitude,
                LocalDateTime.now()
        );
    }
}
