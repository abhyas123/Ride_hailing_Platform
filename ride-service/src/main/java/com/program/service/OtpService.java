package com.program.service;

import com.program.entity.Ride;
import com.program.entity.RideOtp;
import com.program.exception.OtpExpiredException;
import com.program.exception.OtpVerificationFailedException;
import com.program.repository.RideOtpRepository;
import com.program.repository.RideRepository;
import com.program.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for OTP generation and verification
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {

    private final RideOtpRepository otpRepository;
    private final RideRepository rideRepository;
    private final OtpGenerator otpGenerator;

    @Value("${ride.otp-expiry-minutes:10}")
    private int otpExpiryMinutes;

    /**
     * Generate OTP for a ride
     */
    @Transactional
    public String generateOtpForRide(UUID rideId) {
        log.info("Generating OTP for ride: {}", rideId);

        // Check if valid OTP already exists
        otpRepository.findValidOtpByRideId(rideId, LocalDateTime.now())
                .ifPresent(existingOtp -> {
                    log.info("Valid OTP already exists for ride: {}", rideId);
                    otpRepository.delete(existingOtp);
                });

        // Generate new OTP
        String otpCode = otpGenerator.generateOtp();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plusMinutes(otpExpiryMinutes);

        RideOtp rideOtp = RideOtp.builder()
                .rideId(rideId)
                .otp(otpCode)
                .generatedAt(now)
                .expiresAt(expiryTime)
                .verified(false)
                .build();

        otpRepository.save(rideOtp);

        // Update ride entity
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setPickupOtp(otpCode);
        ride.setOtpGeneratedAt(now);
        ride.setIsOtpVerified(false);
        rideRepository.save(ride);

        log.info("✅ OTP generated successfully for ride: {}", rideId);
        return otpCode;
    }

    /**
     * Verify OTP for a ride
     */
    @Transactional
    public boolean verifyOtp(UUID rideId, String otpCode) {
        log.info("Verifying OTP for ride: {}", rideId);

        // Validate OTP format
        if (!otpGenerator.isValidFormat(otpCode)) {
            log.warn("Invalid OTP format: {}", otpCode);
            throw new OtpVerificationFailedException("Invalid OTP format");
        }

        // Find OTP record
        RideOtp rideOtp = otpRepository.findByRideId(rideId)
                .orElseThrow(() -> new OtpVerificationFailedException("No OTP found for this ride"));

        // Check if already verified
        if (rideOtp.getVerified()) {
            log.warn("OTP already verified for ride: {}", rideId);
            throw new OtpVerificationFailedException("OTP already verified");
        }

        // Check expiration
        if (rideOtp.isExpired()) {
            log.warn("OTP expired for ride: {}", rideId);
            throw new OtpExpiredException("OTP has expired. Please request a new one.");
        }

        // Verify OTP code
        if (!rideOtp.getOtp().equals(otpCode)) {
            log.warn("Invalid OTP provided for ride: {}", rideId);
            throw new OtpVerificationFailedException("Invalid OTP");
        }

        // Mark as verified
        LocalDateTime now = LocalDateTime.now();
        rideOtp.setVerified(true);
        rideOtp.setVerifiedAt(now);
        otpRepository.save(rideOtp);

        // Update ride entity
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        ride.setIsOtpVerified(true);
        ride.setOtpVerifiedAt(now);
        rideRepository.save(ride);

        log.info("✅ OTP verified successfully for ride: {}", rideId);
        return true;
    }

    /**
     * Get OTP for a ride (for testing/debugging)
     */
    public String getOtpForRide(UUID rideId) {
        return otpRepository.findValidOtpByRideId(rideId, LocalDateTime.now())
                .map(RideOtp::getOtp)
                .orElse(null);
    }

    /**
     * Check if OTP is verified for a ride
     */
    public boolean isOtpVerified(UUID rideId) {
        return otpRepository.findByRideId(rideId)
                .map(RideOtp::getVerified)
                .orElse(false);
    }

    /**
     * Clean up expired OTPs (scheduled task)
     */
    @Transactional
    public void cleanupExpiredOtps() {
        log.info("Cleaning up expired OTPs");
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
