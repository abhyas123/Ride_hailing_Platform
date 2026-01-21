package com.program.util;

public final class DistanceCalculator {

    private DistanceCalculator() {
        // utility class
    }

    /**
     * Calculate distance between two geo points using Haversine formula
     *
     * @return distance in kilometers
     */
    public static double calculateDistanceKm(
            double lat1,
            double lon1,
            double lat2,
            double lon2
    ) {

        if (!GeoUtils.isValidLocation(lat1, lon1) ||
                !GeoUtils.isValidLocation(lat2, lon2)) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(Math.toRadians(lat1)) *
                                Math.cos(Math.toRadians(lat2)) *
                                Math.sin(dLon / 2) *
                                Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return LocationConstants.EARTH_RADIUS_KM * c;
    }

    /**
     * Estimate ETA in minutes
     *
     * @param distanceKm distance in km
     * @param avgSpeedKmph average speed
     */
    public static int estimateEtaMinutes(double distanceKm, double avgSpeedKmph) {
        if (avgSpeedKmph <= 0) {
            throw new IllegalArgumentException("Average speed must be greater than zero");
        }

        double hours = distanceKm / avgSpeedKmph;
        return (int) Math.ceil(hours * 60);
    }
}
