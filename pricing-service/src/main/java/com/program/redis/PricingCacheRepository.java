package com.program.redis;

import com.program.dto.response.PricingEstimateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PricingCacheRepository {

    private static final String KEY_PREFIX = "pricing:";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void cacheEstimate(
            String cacheKey,
            PricingEstimateResponse response,
            Duration ttl
    ) {
        try {
            redisTemplate.opsForValue().set(
                    KEY_PREFIX + cacheKey,
                    objectMapper.writeValueAsString(response),
                    ttl
            );
        } catch (Exception ignored) {
        }
    }

    public Optional<PricingEstimateResponse> getCachedEstimate(
            String cacheKey
    ) {
        try {
            String value =
                    redisTemplate.opsForValue()
                            .get(KEY_PREFIX + cacheKey);

            if (value == null) {
                return Optional.empty();
            }

            return Optional.of(
                    objectMapper.readValue(
                            value,
                            PricingEstimateResponse.class
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void evict(String cacheKey) {
        redisTemplate.delete(KEY_PREFIX + cacheKey);
    }
}
