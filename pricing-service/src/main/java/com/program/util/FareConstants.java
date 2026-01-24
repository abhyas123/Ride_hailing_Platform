package com.program.util;

public final class FareConstants {

    private FareConstants() {
        // prevent instantiation
    }

    // ==============================
    // BASE FARE (IN INR)
    // ==============================
    public static final double BIKE_BASE_FARE = 20.0;
    public static final double AUTO_BASE_FARE = 30.0;
    public static final double CAR_BASE_FARE  = 50.0;

    // ==============================
    // PER KM RATE
    // ==============================
    public static final double BIKE_PER_KM_RATE = 8.0;
    public static final double AUTO_PER_KM_RATE = 12.0;
    public static final double CAR_PER_KM_RATE  = 15.0;

    // ==============================
    // PER MINUTE RATE
    // ==============================
    public static final double BIKE_PER_MIN_RATE = 1.0;
    public static final double AUTO_PER_MIN_RATE = 1.5;
    public static final double CAR_PER_MIN_RATE  = 2.0;

    // ==============================
    // PLATFORM COMMISSION (%)
    // ==============================
    public static final double PLATFORM_COMMISSION_PERCENT = 17.0;

    // ==============================
    // SURGE LIMITS
    // ==============================
    public static final double MIN_SURGE = 1.0;
    public static final double MAX_SURGE = 3.0;
}
