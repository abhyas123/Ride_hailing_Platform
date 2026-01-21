package com.program.service;

import com.program.dto.response.NearbyDriverResponse;
import com.program.exception.DriverOfflineException;
import com.program.repository.DriverLocationRedisRepository;
import com.program.repository.DriverOnlineRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverLocationService {

    private final DriverLocationRedisRepository locationRepository;
    private final DriverOnlineRedisRepository onlineRepository;

    // ================================
    // UPDATE DRIVER LOCATION
    // ================================
    public void updateLocation(
            String driverId,
            double latitude,
            double longitude
    ) {

        if (!onlineRepository.isOnline(driverId)) {
            throw new DriverOfflineException("Driver must be online to update location");
        }

        locationRepository.saveLocation(driverId, latitude, longitude);
    }

    // ================================
    // REMOVE DRIVER LOCATION
    // ================================
    public void removeDriver(String driverId) {
        locationRepository.removeDriver(driverId);
        onlineRepository.markOffline(driverId);
    }

    // ================================
    // FIND NEARBY DRIVERS
    // ================================
    public List<NearbyDriverResponse> findNearbyDrivers(
            double latitude,
            double longitude,
            double radiusKm
    ) {

        List<String> driverIds =
                locationRepository.findNearbyDrivers(latitude, longitude, radiusKm);

        return driverIds.stream()
                .map(id -> new NearbyDriverResponse(id, null, null, null))
                .collect(Collectors.toList());
    }
}
