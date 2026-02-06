package com.program.service;

import com.google.firebase.messaging.*;
import com.program.exception.NotificationSendException;
import com.program.util.NotificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service for sending FCM notifications
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final NotificationTemplate notificationTemplate;

    @Value("${notification.enabled:true}")
    private boolean notificationEnabled;

    @Value("${notification.retry-attempts:3}")
    private int retryAttempts;

    /**
     * Send notification to a specific device
     */
    public String sendNotification(String fcmToken, String title, String body, Map<String, String> data) {
        if (!notificationEnabled) {
            log.info("Notifications are disabled");
            return "DISABLED";
        }

        if (fcmToken == null || fcmToken.isEmpty()) {
            log.warn("FCM token is null or empty");
            throw new NotificationSendException("Invalid FCM token");
        }

        try {
            // Build notification
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Build message
            Message.Builder messageBuilder = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification);

            // Add custom data if provided
            if (data != null && !data.isEmpty()) {
                messageBuilder.putAllData(data);
            }

            // Configure Android-specific options
            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .setNotification(AndroidNotification.builder()
                            .setSound("default")
                            .setColor("#FF6B35")
                            .build())
                    .build();

            messageBuilder.setAndroidConfig(androidConfig);

            // Send message
            Message message = messageBuilder.build();
            String response = firebaseMessaging.send(message);

            log.info("✅ Notification sent successfully. Response: {}", response);
            return response;

        } catch (FirebaseMessagingException e) {
            log.error("❌ Failed to send notification to token: {}", fcmToken, e);
            throw new NotificationSendException("Failed to send notification: " + e.getMessage());
        }
    }

    /**
     * Send ride accepted notification to rider
     */
    public String sendRideAcceptedNotification(
            String riderFcmToken,
            String driverName,
            String vehicleNumber,
            String estimatedArrival,
            String rideId,
            String otp) {

        Map<String, String> notification = notificationTemplate.buildRideAcceptedNotification(
                driverName, vehicleNumber, estimatedArrival);

        Map<String, String> data = Map.of(
                "type", "RIDE_ACCEPTED",
                "rideId", rideId,
                "otp", otp,
                "driverName", driverName,
                "vehicleNumber", vehicleNumber
        );

        return sendNotification(riderFcmToken, notification.get("title"), notification.get("body"), data);
    }

    /**
     * Send driver nearby notification
     */
    public String sendDriverNearbyNotification(String riderFcmToken, String driverName, String rideId) {
        Map<String, String> notification = notificationTemplate.buildDriverNearbyNotification(driverName);

        Map<String, String> data = Map.of(
                "type", "DRIVER_NEARBY",
                "rideId", rideId
        );

        return sendNotification(riderFcmToken, notification.get("title"), notification.get("body"), data);
    }

    /**
     * Send OTP notification
     */
    public String sendOtpNotification(String riderFcmToken, String otp, String rideId) {
        Map<String, String> notification = notificationTemplate.buildOtpNotification(otp);

        Map<String, String> data = Map.of(
                "type", "OTP_GENERATED",
                "rideId", rideId,
                "otp", otp
        );

        return sendNotification(riderFcmToken, notification.get("title"), notification.get("body"), data);
    }

    /**
     * Send ride started notification
     */
    public String sendRideStartedNotification(String riderFcmToken, String rideId) {
        Map<String, String> notification = notificationTemplate.buildRideStartedNotification();

        Map<String, String> data = Map.of(
                "type", "RIDE_STARTED",
                "rideId", rideId
        );

        return sendNotification(riderFcmToken, notification.get("title"), notification.get("body"), data);
    }

    /**
     * Send ride completed notification
     */
    public String sendRideCompletedNotification(String riderFcmToken, String fare, String rideId) {
        Map<String, String> notification = notificationTemplate.buildRideCompletedNotification(fare);

        Map<String, String> data = Map.of(
                "type", "RIDE_COMPLETED",
                "rideId", rideId,
                "fare", fare
        );

        return sendNotification(riderFcmToken, notification.get("title"), notification.get("body"), data);
    }

    /**
     * Send notification with retry logic
     */
    public String sendNotificationWithRetry(String fcmToken, String title, String body, Map<String, String> data) {
        int attempts = 0;
        Exception lastException = null;

        while (attempts < retryAttempts) {
            try {
                return sendNotification(fcmToken, title, body, data);
            } catch (NotificationSendException e) {
                lastException = e;
                attempts++;
                log.warn("Retry attempt {} failed for FCM token: {}", attempts, fcmToken);
                
                if (attempts < retryAttempts) {
                    try {
                        Thread.sleep(1000 * attempts); // Exponential backoff
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        log.error("Failed to send notification after {} attempts", retryAttempts);
        throw new NotificationSendException("Failed after " + retryAttempts + " attempts", lastException);
    }
}
