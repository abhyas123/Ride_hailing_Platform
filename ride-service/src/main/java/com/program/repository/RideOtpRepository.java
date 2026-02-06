package com.program.repository;

import com.program.entity.RideOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for RideOtp entity
 */
@Repository
public interface RideOtpRepository extends JpaRepository<RideOtp, UUID> {

    /**
     * Find OTP by ride ID
     */
    Optional<RideOtp> findByRideId(UUID rideId);

    /**
     * Find valid (non-expired, non-verified) OTP by ride ID
     */
    @Query("SELECT ro FROM RideOtp ro WHERE ro.rideId = :rideId " +
           "AND ro.verified = false AND ro.expiresAt > :currentTime")
    Optional<RideOtp> findValidOtpByRideId(UUID rideId, LocalDateTime currentTime);

    /**
     * Delete expired OTPs
     */
    void deleteByExpiresAtBefore(LocalDateTime dateTime);

    /**
     * Check if valid OTP exists for ride
     */
    @Query("SELECT CASE WHEN COUNT(ro) > 0 THEN true ELSE false END FROM RideOtp ro " +
           "WHERE ro.rideId = :rideId AND ro.verified = false AND ro.expiresAt > :currentTime")
    boolean existsValidOtpForRide(UUID rideId, LocalDateTime currentTime);
}
