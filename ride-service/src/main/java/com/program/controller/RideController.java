package com.program.controller;

import com.program.dto.request.AcceptRideRequest;
import com.program.dto.request.RideRequestDto;
import com.program.dto.request.StartRideRequest;
import com.program.dto.request.VerifyOtpRequest;
import com.program.dto.response.OtpVerificationResponse;
import com.program.dto.response.RideAcceptedResponse;
import com.program.dto.response.RideResponseDto;
import com.program.entity.Ride;
import com.program.service.OtpService;
import com.program.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
@Slf4j
public class RideController {

    private final RideService rideService;
    private final OtpService otpService;

    /**
     * POST /rides/request
     * User requests a ride with source/destination coordinates
     */
    @PostMapping("/request")
    public ResponseEntity<RideResponseDto> requestRide(
            @RequestBody RideRequestDto request, 
            @RequestHeader("X-User-Id") String userId) {
        
        log.info("Ride request received from user: {}", userId);
        
        Ride ride = rideService.requestRide(
                UUID.fromString(userId), 
                request.getSourceLocation(),
                request.getDestinationLocation(),
                request.getSourceLatitude(),
                request.getSourceLongitude()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RideResponseDto.fromEntity(ride));
    }

    /**
     * GET /rides/{id}
     * Get ride details by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDto> getRide(@PathVariable UUID id) {
        Ride ride = rideService.getRide(id);
        return ResponseEntity.ok(RideResponseDto.fromEntity(ride));
    }

    /**
     * POST /rides/{id}/accept
     * Driver accepts a ride request
     * Generates OTP and sends FCM notification to rider
     */
    @PostMapping("/{id}/accept")
    public ResponseEntity<RideAcceptedResponse> acceptRide(
            @PathVariable UUID id, 
            @Valid @RequestBody AcceptRideRequest request) {
        
        log.info("Driver {} accepting ride {}", request.getDriverId(), id);
        
        Ride ride = rideService.acceptRide(
            id, 
            request.getDriverId(),
            request.getDriverFcmToken(),  // This should be riderFcmToken, need to fetch from user service
            "Driver Name",  // TODO: Fetch from driver service
            "Vehicle ABC-123",  // TODO: Fetch from driver service
            request.getEstimatedArrivalMinutes()
        );
        
        RideAcceptedResponse response = RideAcceptedResponse.builder()
                .rideId(ride.getId())
                .driverId(ride.getDriverId())
                .driverName("Driver Name")  // TODO: Fetch from driver service
                .driverPhone("+91-XXXXXXXXXX")  // TODO: Fetch from driver service
                .vehicleDetails("Vehicle ABC-123")  // TODO: Fetch from driver service
                .estimatedArrival(request.getEstimatedArrivalMinutes())
                .otpGenerated(true)
                .acceptedAt(java.time.LocalDateTime.now())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * POST /rides/{id}/verify-otp
     * Verify OTP for ride pickup
     */
    @PostMapping("/{id}/verify-otp")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(
            @PathVariable UUID id,
            @Valid @RequestBody VerifyOtpRequest request) {
        
        log.info("Verifying OTP for ride {}", id);
        
        try {
            boolean verified = otpService.verifyOtp(id, request.getOtp());
            
            OtpVerificationResponse response = OtpVerificationResponse.builder()
                    .verified(verified)
                    .message("OTP verified successfully")
                    .rideId(id)
                    .verifiedAt(java.time.LocalDateTime.now())
                    .build();
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            OtpVerificationResponse response = OtpVerificationResponse.builder()
                    .verified(false)
                    .message(e.getMessage())
                    .rideId(id)
                    .build();
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * POST /rides/{id}/start
     * Driver starts the ride after OTP verification
     */
    @PostMapping("/{id}/start")
    public ResponseEntity<RideResponseDto> startRide(
            @PathVariable UUID id, 
            @Valid @RequestBody StartRideRequest request) {
        
        log.info("Starting ride {} with OTP verification", id);
        
        Ride ride = rideService.startRide(
            request.getRideId(), 
            request.getOtp(),
            "riderFcmToken"  // TODO: Fetch from user service
        );
        return ResponseEntity.ok(RideResponseDto.fromEntity(ride));
    }

    /**
     * POST /rides/{id}/end
     * Driver ends the ride
     */
    @PostMapping("/{id}/end")
    public ResponseEntity<RideResponseDto> endRide(@PathVariable UUID id) {
        log.info("Ending ride {}", id);
        
        Ride ride = rideService.endRide(id);
        return ResponseEntity.ok(RideResponseDto.fromEntity(ride));
    }
    
    /**
     * POST /rides/{id}/cancel
     * Cancel a ride with reason
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<RideResponseDto> cancelRide(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request) {
        
        String reason = request.getOrDefault("reason", "No reason provided");
        log.info("Cancelling ride {} with reason: {}", id, reason);
        
        Ride ride = rideService.cancelRide(id, reason);
        return ResponseEntity.ok(RideResponseDto.fromEntity(ride));
    }
}
