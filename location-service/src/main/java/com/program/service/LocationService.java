package com.program.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final DriverOnlineStatusService onlineStatusService;
    private final DriverLocationService driverLocationService;

    // ================================
    // DRIVER COMES ONLINE
    // ================================
    public void markDriverOnline(
            String driverId,
            double latitude,
            double longitude
    ) {
        onlineStatusService.goOnline(driverId);
        driverLocationService.updateLocation(driverId, latitude, longitude);
    }

    // ================================
    // DRIVER GOES OFFLINE
    // ================================
    public void markDriverOffline(String driverId) {
        onlineStatusService.goOffline(driverId);
        driverLocationService.removeDriver(driverId);
    }

    // ================================
    // DRIVER BUSY (RIDE ASSIGNED)
    // ================================
    public void markDriverBusy(String driverId) {
        // Driver is busy → still online, just not available
        // We DO NOT remove location
        onlineStatusService.validateDriverOnline(driverId);
    }
}
