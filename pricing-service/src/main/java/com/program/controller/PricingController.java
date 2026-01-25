package com.program.controller;

import com.program.dto.request.PricingEstimateRequest;
import com.program.dto.request.PricingFinalRequest;
import com.program.dto.response.PricingEstimateResponse;
import com.program.dto.response.PricingFinalResponse;
import com.program.service.PricingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    // =====================================
    // INITIAL ESTIMATE (SEARCH PHASE)
    // =====================================
    @PostMapping("/estimate")
    public ResponseEntity<PricingEstimateResponse> estimateFare(
            @Valid @RequestBody PricingEstimateRequest request
    ) {
        PricingEstimateResponse response =
                pricingService.estimateFare(request);

        return ResponseEntity.ok(response);
    }

    // =====================================
    // FINAL FARE (AFTER RIDE END)
    // =====================================
    @PostMapping("/final")
    public ResponseEntity<PricingFinalResponse> calculateFinalFare(
            @Valid @RequestBody PricingFinalRequest request
    ) {
        PricingFinalResponse response =
                pricingService.calculateFinalFare(request);

        return ResponseEntity.ok(response);
    }
}
