package com.program.service;

import com.program.client.DriverClient;
import com.program.client.LocationClient;
import com.program.client.PaymentClient;
import com.program.client.PricingClient;
import com.program.entity.Ride;
import com.program.entity.RideStatus;
import com.program.exception.InvalidRideStateException;
import com.program.exception.RideNotFoundException;
import com.program.kafka.RideEventProducer;
import com.program.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RideService {

    private final RideRepository rideRepository;
    private final RideEventProducer rideEventProducer;
    private final OtpService otpService;
    private final NotificationService notificationService;
    
    // Feign Clients
    private final PricingClient pricingClient;
    private final LocationClient locationClient;
    private final DriverClient driverClient;
    private final PaymentClient paymentClient;

    /**
     * Step 4: User Confirms Ride
     * Creates ride, gets fare estimate, finds nearby drivers, notifies them
     */
    public Ride requestRide(UUID passengerId, String source, String dest, 
                           Double sourceLat, Double sourceLng) {
        log.info("Processing ride request for passenger: {}", passengerId);
        
        // 1. Get fare estimate from Pricing Service
        Double estimatedFare = 100.0; // Default fallback
        try {
            Map<String, Object> fareEstimate = pricingClient.getFareEstimate(source, dest, 5.0);
            estimatedFare = (Double) fareEstimate.getOrDefault("fare", 100.0);
            log.info("Fare estimate received: {}", estimatedFare);
        } catch (Exception e) {
            log.warn("Failed to get fare estimate, using default: {}", e.getMessage());
        }

        // 2. Create ride with status CREATED
        Ride ride = Ride.builder()
                .passengerId(passengerId)
                .sourceLocation(source)
                .destinationLocation(dest)
                .status(RideStatus.CREATED)
                .fare(estimatedFare)
                .build();
        
        Ride savedRide = rideRepository.save(ride);
        log.info("Ride created with ID: {}", savedRide.getId());

        // 3. Find nearby drivers from Location Service
        try {
            List<Map<String, Object>> nearbyDrivers = locationClient.findNearbyDrivers(
                sourceLat, sourceLng, 5.0
            );
            
            List<String> driverIds = nearbyDrivers.stream()
                    .map(d -> (String) d.get("driverId"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            
            log.info("Found {} nearby drivers", driverIds.size());

            // 4. Notify drivers via Driver Service
            if (!driverIds.isEmpty()) {
                Map<String, Object> notificationRequest = new HashMap<>();
                notificationRequest.put("rideId", savedRide.getId().toString());
                notificationRequest.put("driverIds", driverIds);
                notificationRequest.put("source", source);
                notificationRequest.put("destination", dest);
                notificationRequest.put("fare", estimatedFare);
                
                driverClient.notifyDriversAboutRide(notificationRequest);
            }
        } catch (Exception e) {
            log.error("Failed to find/notify drivers: {}", e.getMessage());
        }

        // 5. Publish Kafka event for Notification Service
        rideEventProducer.publishRideCreated(
            savedRide.getId(), source, dest, estimatedFare
        );

        return savedRide;
    }

    /**
     * Get ride by ID
     */
    public Ride getRide(UUID rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RideNotFoundException("Ride not found: " + rideId));
    }

    /**
     * Step 5: Driver Accepts Ride
     * Assigns driver, generates OTP, updates status to DRIVER_ASSIGNED
     * Sends FCM notification to rider with driver details
     */
    public Ride acceptRide(UUID rideId, UUID driverId, String riderFcmToken, 
                          String driverName, String vehicleNumber, String estimatedArrival) {
        log.info("Driver {} accepting ride {}", driverId, rideId);
        
        Ride ride = getRide(rideId);
        
        if (ride.getStatus() != RideStatus.CREATED) {
            throw new InvalidRideStateException(
                "Ride cannot be accepted. Current status: " + ride.getStatus()
            );
        }
        
        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.DRIVER_ASSIGNED);
        
        Ride updatedRide = rideRepository.save(ride);
        
        // Generate OTP for pickup verification
        String otp = otpService.generateOtpForRide(rideId);
        log.info("OTP generated for ride {}: {}", rideId, otp);
        
        // Send FCM notification to rider
        try {
            notificationService.sendRideAcceptedNotification(
                riderFcmToken, driverName, vehicleNumber, estimatedArrival,
                rideId.toString(), otp
            );
            log.info("✅ Ride accepted notification sent to rider");
        } catch (Exception e) {
            log.error("❌ Failed to send ride accepted notification", e);
        }
        
        // Publish event for other services
        rideEventProducer.publishDriverAssigned(
            updatedRide.getId(), driverId, otp
        );
        
        log.info("Ride {} assigned to driver {} with OTP", rideId, driverId);
        return updatedRide;
    }
    
    /**
     * Step 6: Ride In Progress
     * Driver starts ride after OTP verification
     */
    public Ride startRide(UUID rideId, String otp, String riderFcmToken) {
        log.info("Starting ride {} with OTP verification", rideId);
        
        Ride ride = getRide(rideId);
        
        if (ride.getStatus() != RideStatus.DRIVER_ASSIGNED) {
            throw new InvalidRideStateException(
                "Ride cannot be started. Current status: " + ride.getStatus()
            );
        }
        
        // Verify OTP using OtpService
        boolean verified = otpService.verifyOtp(rideId, otp);
        if (!verified) {
            throw new InvalidRideStateException("OTP verification failed");
        }
        
        ride.setStatus(RideStatus.ONGOING);
        ride.setStartTime(LocalDateTime.now());
        
        Ride startedRide = rideRepository.save(ride);
        
        // Send ride started notification
        try {
            notificationService.sendRideStartedNotification(riderFcmToken, rideId.toString());
            log.info("✅ Ride started notification sent to rider");
        } catch (Exception e) {
            log.error("❌ Failed to send ride started notification", e);
        }
        
        log.info("Ride {} started at {}", rideId, ride.getStartTime());
        return startedRide;
    }

    /**
     * Step 7 & 8: Ride Completed
     * Ends ride, calculates final fare, triggers payment
     */
    public Ride endRide(UUID rideId) {
        log.info("Ending ride {}", rideId);
        
        Ride ride = getRide(rideId);
        
        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new InvalidRideStateException(
                "Ride cannot be ended. Current status: " + ride.getStatus()
            );
        }
        
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        
        // Calculate final fare from Pricing Service
        Double finalFare = ride.getFare(); // Default to estimated
        try {
            Map<String, Object> fareCalc = pricingClient.calculateFinalFare(
                rideId.toString(), 5.0, 20
            );
            finalFare = (Double) fareCalc.getOrDefault("finalFare", ride.getFare());
            ride.setFare(finalFare);
            log.info("Final fare calculated: {}", finalFare);
        } catch (Exception e) {
            log.warn("Failed to calculate final fare, using estimate: {}", e.getMessage());
        }
        
        Ride completedRide = rideRepository.save(ride);
        
        // Publish event for Payment Service
        rideEventProducer.publishRideCompleted(
            completedRide.getId(), 
            completedRide.getDriverId(), 
            finalFare
        );
        
        // Trigger payment processing
        try {
            Map<String, Object> paymentRequest = new HashMap<>();
            paymentRequest.put("rideId", rideId.toString());
            paymentRequest.put("amount", finalFare);
            paymentRequest.put("driverId", ride.getDriverId().toString());
            paymentRequest.put("userId", ride.getPassengerId().toString());
            
            paymentClient.processRidePayment(paymentRequest);
            log.info("Payment processing initiated for ride {}", rideId);
        } catch (Exception e) {
            log.error("Failed to initiate payment: {}", e.getMessage());
        }
        
        log.info("Ride {} completed successfully", rideId);
        return completedRide;
    }
    
    /**
     * Cancel ride
     */
    public Ride cancelRide(UUID rideId, String reason) {
        log.info("Cancelling ride {} with reason: {}", rideId, reason);
        
        Ride ride = getRide(rideId);
        
        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new InvalidRideStateException("Cannot cancel completed ride");
        }
        
        ride.setStatus(RideStatus.CANCELLED);
        Ride cancelledRide = rideRepository.save(ride);
        
        // Publish cancellation event
        rideEventProducer.publishRideCancelled(rideId, reason);
        
        return cancelledRide;
    }


}
