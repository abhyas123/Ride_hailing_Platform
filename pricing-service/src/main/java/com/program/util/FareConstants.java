package com.program.util;

import java.time.LocalTime;

public final class FareConstants {

    private FareConstants() {
    }

    // ===============================
    // SURGE LIMITS
    // ===============================
    public static final double MIN_SURGE = 1.0;   // No discount below base fare
    public static final double MAX_SURGE = 3.0;   // Max 3x surge

    // ===============================
    // NIGHT CHARGE
    // ===============================
    public static final double NIGHT_CHARGE = 20.0;

    private static final int NIGHT_START_HOUR = 23; // 11 PM
    private static final int NIGHT_END_HOUR = 5;    // 5 AM

    public static boolean isNightTime() {
        LocalTime now = LocalTime.now();

        return now.isAfter(LocalTime.of(NIGHT_START_HOUR, 0))
                || now.isBefore(LocalTime.of(NIGHT_END_HOUR, 0));
    }
}
