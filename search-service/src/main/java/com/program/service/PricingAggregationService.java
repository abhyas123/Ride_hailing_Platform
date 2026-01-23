package com.program.service;

import com.program.client.PricingServiceClient;
import com.program.dto.request.PricingEstimateRequest;
import com.program.dto.response.VehicleFareResponse;
import com.program.exception.SearchException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingAggregationService {

    private final PricingServiceClient pricingServiceClient;

    public PricingAggregationService(PricingServiceClient pricingServiceClient) {
        this.pricingServiceClient = pricingServiceClient;
    }

    /**
     * Calls pricing-service to get vehicle-wise fare
     */
    public List<VehicleFareResponse> getFareEstimates(
            PricingEstimateRequest request) {

        try {
            return pricingServiceClient.getFareEstimate(request);
        } catch (Exception ex) {
            throw new SearchException("Pricing service unavailable", ex);
        }
    }
}
