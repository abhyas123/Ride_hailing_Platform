package com.program.client;

import com.program.dto.request.PricingEstimateRequest;
import com.program.dto.response.VehicleFareResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "pricing-service"
)
public interface PricingServiceClient {

    @PostMapping("/pricing/estimate")
    List<VehicleFareResponse> getFareEstimate(
            @RequestBody PricingEstimateRequest request
    );
}
