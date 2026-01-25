package com.program.util;

import java.time.LocalTime;

public class FareConstants {

    public static final double NIGHT_CHARGE = 20.0;

    public static boolean isNightTime() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(22, 0))
                || now.isBefore(LocalTime.of(6, 0));
    }
}
