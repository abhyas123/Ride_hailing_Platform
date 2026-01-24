package com.program.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtils {

    private MathUtils() {
    }

    /**
     * Rounds amount to 2 decimal places
     */
    public static double roundMoney(double amount) {
        return BigDecimal.valueOf(amount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Applies surge with safety limits
     */
    public static double applySurge(double baseFare, double surgeMultiplier) {

        if (surgeMultiplier < FareConstants.MIN_SURGE) {
            surgeMultiplier = FareConstants.MIN_SURGE;
        }

        if (surgeMultiplier > FareConstants.MAX_SURGE) {
            surgeMultiplier = FareConstants.MAX_SURGE;
        }

        return roundMoney(baseFare * surgeMultiplier);
    }

    /**
     * Calculates percentage value
     */
    public static double percentage(double amount, double percent) {
        return roundMoney((amount * percent) / 100.0);
    }
}
