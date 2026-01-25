package com.program.util;

import java.util.List;

public enum VehicleType {

    BIKE,
    AUTO,
    CAR;

    public static VehicleType fromString(String value) {
        try {
            return VehicleType.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid vehicle type: " + value);
        }
    }


    public static List<VehicleType> supportedTypes() {
        return List.of(BIKE, AUTO, CAR);
    }
}
