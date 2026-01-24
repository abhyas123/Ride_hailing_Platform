package com.program.util;

import java.time.Duration;
import java.time.LocalDateTime;

public final class TimeUtils {

    private TimeUtils() {
    }

    /**
     * Calculates duration in minutes between two timestamps
     */
    public static long minutesBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return Duration.between(start, end).toMinutes();
    }

    /**
     * Converts seconds to rounded minutes
     */
    public static long secondsToMinutes(long seconds) {
        if (seconds <= 0) {
            return 0;
        }
        return Math.round(seconds / 60.0);
    }
}
