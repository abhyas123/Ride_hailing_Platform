package com.program.service;

import com.program.exception.DriverOfflineException;
import com.program.repository.DriverOnlineRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverOnlineStatusService {

    private final DriverOnlineRedisRepository onlineRepository;

    public void goOnline(String driverId) {
        onlineRepository.markOnline(driverId);
    }

    public void goOffline(String driverId) {
        onlineRepository.markOffline(driverId);
    }

    public boolean isDriverOnline(String driverId) {
        return onlineRepository.isOnline(driverId);
    }

    public void validateDriverOnline(String driverId) {
        if (!onlineRepository.isOnline(driverId)) {
            throw new DriverOfflineException("Driver is offline");
        }
    }
}
