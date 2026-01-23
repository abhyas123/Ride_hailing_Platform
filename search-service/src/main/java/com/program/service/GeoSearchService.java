package com.program.service;

import com.program.dto.request.RouteSearchRequest;
import com.program.dto.request.PricingEstimateRequest;
import com.program.dto.response.RouteSearchResponse;
import com.program.dto.response.VehicleFareResponse;
import com.program.exception.SearchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeoSearchService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PricingAggregationService pricingAggregationService;

    @Value("${map.api.base-url}")
    private String mapBaseUrl;

    @Value("${map.api.key}")
    private String mapApiKey;

    public GeoSearchService(PricingAggregationService pricingAggregationService) {
        this.pricingAggregationService = pricingAggregationService;
    }

    /**
     * Main search flow:
     * - Geocode pickup & drop
     * - Calculate distance & ETA
     * - Call pricing service
     */
    public RouteSearchResponse searchRoute(RouteSearchRequest request) {

        try {
            // ===== MOCKED GEO RESULT (replace with real Map API call later) =====
            double pickupLat = 22.7196;
            double pickupLng = 75.8577;
            double dropLat = 22.6851;
            double dropLng = 75.8730;

            // Distance in KM (mock)
            double distanceKm = 8.5;
            int etaMinutes = 20;

            // ===== CALL PRICING SERVICE =====
            PricingEstimateRequest pricingRequest =
                    new PricingEstimateRequest(distanceKm, etaMinutes);

            List<VehicleFareResponse> fares =
                    pricingAggregationService.getFareEstimates(pricingRequest);

            return new RouteSearchResponse(
                    pickupLat,
                    pickupLng,
                    dropLat,
                    dropLng,
                    distanceKm,
                    etaMinutes,
                    fares
            );

        } catch (Exception ex) {
            throw new SearchException("Failed to search route", ex);
        }
    }
}
