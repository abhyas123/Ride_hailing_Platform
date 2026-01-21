package com.program.repository;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DriverLocationRedisRepository {

    private static final String GEO_KEY = "driver:locations";

    private final GeoOperations<String, String> geoOps;

    public DriverLocationRedisRepository(StringRedisTemplate redisTemplate) {
        this.geoOps = redisTemplate.opsForGeo();
    }

    // ================================
    // ADD / UPDATE DRIVER LOCATION
    // ================================
    public void saveLocation(String driverId, double latitude, double longitude) {
        geoOps.add(
                GEO_KEY,
                new Point(longitude, latitude),
                driverId
        );
    }

    // ================================
    // REMOVE DRIVER
    // ================================
    public void removeDriver(String driverId) {
        geoOps.remove(GEO_KEY, driverId);
    }

    // ================================
    // FIND NEARBY DRIVERS
    // ================================
    public List<String> findNearbyDrivers(
            double latitude,
            double longitude,
            double radiusKm
    ) {

        Circle searchArea = new Circle(
                new Point(longitude, latitude),
                new Distance(radiusKm, Metrics.KILOMETERS)
        );

        return geoOps.radius(GEO_KEY, searchArea)
                .getContent()
                .stream()
                .map(result -> result.getContent().getName())
                .collect(Collectors.toList());
    }
}
