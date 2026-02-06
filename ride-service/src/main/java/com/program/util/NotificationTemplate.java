package com.program.util;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Notification message templates
 */
@Component
public class NotificationTemplate {

    /**
     * Build ride accepted notification
     */
    public Map<String, String> buildRideAcceptedNotification(
            String driverName, 
            String vehicleNumber, 
            String estimatedArrival) {
        
        String title = "Driver Assigned! 🚗";
        String body = String.format(
                "%s is on the way!\nVehicle: %s\nETA: %s minutes",
                driverName, vehicleNumber, estimatedArrival
        );
        
        return Map.of("title", title, "body", body);
    }

    /**
     * Build driver nearby notification
     */
    public Map<String, String> buildDriverNearbyNotification(String driverName) {
        String title = "Driver Nearby! 📍";
        String body = String.format("%s is approaching your pickup location", driverName);
        return Map.of("title", title, "body", body);
    }

    /**
     * Build OTP notification
     */
    public Map<String, String> buildOtpNotification(String otp) {
        String title = "Your Ride OTP 🔐";
        String body = String.format("Share this OTP with your driver: %s", otp);
        return Map.of("title", title, "body", body);
    }

    /**
     * Build ride started notification
     */
    public Map<String, String> buildRideStartedNotification() {
        String title = "Ride Started! 🚀";
        String body = "Your ride has begun. Have a safe journey!";
        return Map.of("title", title, "body", body);
    }

    /**
     * Build ride completed notification
     */
    public Map<String, String> buildRideCompletedNotification(String fare) {
        String title = "Ride Completed ✅";
        String body = String.format("Total fare: ₹%s. Thank you for riding with us!", fare);
        return Map.of("title", title, "body", body);
    }
}
