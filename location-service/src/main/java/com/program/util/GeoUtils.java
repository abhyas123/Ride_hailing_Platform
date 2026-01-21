package com.program.util;

public final class GeoUtils {

    private GeoUtils() {
        // utility class
    }

    /**
     * Validate latitude value
     */
    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
    }

    /**
     * Validate longitude value
     */
    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }

    /**
     * Validate both latitude and longitude
     */
    public static boolean isValidLocation(double latitude, double longitude) {
        return isValidLatitude(latitude) && isValidLongitude(longitude);
    }

    /**
     * Format location as Redis GEO string
     */
    public static String formatLocation(double latitude, double longitude) {
        return latitude + "," + longitude;
    }
}
