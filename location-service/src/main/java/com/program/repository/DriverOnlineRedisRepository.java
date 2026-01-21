package com.program.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class DriverOnlineRedisRepository {

    private static final String ONLINE_DRIVERS_KEY = "driver:online";

    private final StringRedisTemplate redisTemplate;

    public DriverOnlineRedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ================================
    // MARK ONLINE
    // ================================
    public void markOnline(String driverId) {
        redisTemplate.opsForSet().add(ONLINE_DRIVERS_KEY, driverId);
    }

    // ================================
    // MARK OFFLINE
    // ================================
    public void markOffline(String driverId) {
        redisTemplate.opsForSet().remove(ONLINE_DRIVERS_KEY, driverId);
    }

    // ================================
    // CHECK ONLINE
    // ================================
    public boolean isOnline(String driverId) {
        Boolean member =
                redisTemplate.opsForSet().isMember(ONLINE_DRIVERS_KEY, driverId);
        return Boolean.TRUE.equals(member);
    }

    // ================================
    // GET ALL ONLINE DRIVERS
    // ================================
    public Set<String> getAllOnlineDrivers() {
        return redisTemplate.opsForSet().members(ONLINE_DRIVERS_KEY);
    }
}
