package com.program.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SurgeRedisRepository {

    private static final String KEY_PREFIX = "surge:";

    private final StringRedisTemplate redisTemplate;

    public void saveSurgeMultiplier(
            String city,
            Double multiplier,
            Duration ttl
    ) {
        redisTemplate.opsForValue()
                .set(KEY_PREFIX + city, multiplier.toString(), ttl);
    }

    public Optional<Double> getSurgeMultiplier(String city) {

        String value =
                redisTemplate.opsForValue().get(KEY_PREFIX + city);

        return value == null
                ? Optional.empty()
                : Optional.of(Double.parseDouble(value));
    }

    public void deleteSurge(String city) {
        redisTemplate.delete(KEY_PREFIX + city);
    }
}
