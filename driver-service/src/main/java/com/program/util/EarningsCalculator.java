package com.program.util;

public final class EarningsCalculator {

    private static final double PLATFORM_COMMISSION_PERCENT = 15.0;

    private EarningsCalculator() {
        // Utility class - no instance
    }

    public static double calculateDriverEarning(double totalFare) {

        if (totalFare <= 0) {
            return 0.0;
        }

        double commission = (totalFare * PLATFORM_COMMISSION_PERCENT) / 100;
        return round(totalFare - commission);
    }

    public static double calculatePlatformEarning(double totalFare) {

        if (totalFare <= 0) {
            return 0.0;
        }

        double commission = (totalFare * PLATFORM_COMMISSION_PERCENT) / 100;
        return round(commission);
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
