package com.program.util;

public final class LocationConstants {

    private LocationConstants() {
        // prevent instantiation
    }

    /** Earth radius in kilometers */
    public static final double EARTH_RADIUS_KM = 6371.0;

    /** Default nearby driver search radius (km) */
    public static final double DEFAULT_SEARCH_RADIUS_KM = 3.0;

    /** Redis GEO key prefix */
    public static final String DRIVER_GEO_KEY = "drivers:geo";

    /** WebSocket topic prefix */
    public static final String RIDE_LOCATION_TOPIC_PREFIX = "/topic/rides/";

    /** WebSocket endpoint */
    public static final String WEBSOCKET_ENDPOINT = "/ws-location";

    /** Driver location update interval (seconds) */
    public static final int LOCATION_UPDATE_INTERVAL_SECONDS = 5;
}
