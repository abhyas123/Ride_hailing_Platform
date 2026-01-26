package com.program.service;

import com.program.entity.SurgeMultiplier;
import com.program.exception.SurgeNotAvailableException;
import com.program.redis.SurgeRedisRepository;
import com.program.repository.SurgeRepository;
import com.program.util.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SurgePricingService {

    private final SurgeRedisRepository surgeRedisRepository;
    private final SurgeRepository surgeRepository;

    public double applySurge(
            VehicleType vehicleType,
            double fare
    ) {

        Double multiplier =
                surgeRedisRepository
                        .getSurgeMultiplier(vehicleType.name())
                        .orElse(null);

        if (multiplier == null) {
            SurgeMultiplier surge =
                    surgeRepository.findByVehicleTypeAndActiveTrue(vehicleType)
                            .orElse(null);
            
            if (surge != null && surge.getMultiplier() != null) {
                multiplier = surge.getMultiplier();
                surgeRedisRepository.saveSurgeMultiplier(
                        vehicleType.name(),
                        multiplier,
                        Duration.ofMinutes(30)
                );
            } else {
                // Use default multiplier if surge not available
                multiplier = 1.0;
            }
        }

        return fare * multiplier;
    }
}
