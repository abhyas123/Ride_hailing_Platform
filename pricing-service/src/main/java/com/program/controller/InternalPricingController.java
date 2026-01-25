package com.program.controller;

import com.program.dto.request.PricingFinalRequest;
import com.program.dto.response.PricingFinalResponse;
import com.program.service.PricingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing/internal")
@RequiredArgsConstructor
public class InternalPricingController {

    private final PricingService pricingService;

    // =====================================
    // INTERNAL RE-CALCULATION
    // ONLY RIDE SERVICE SHOULD CALL THIS
    // =====================================
    @PostMapping("/recalculate")
    public ResponseEntity<PricingFinalResponse> recalculateFare(
            @Valid @RequestBody PricingFinalRequest request
    ) {
        PricingFinalResponse response =
                pricingService.calculateFinalFare(request);

        return ResponseEntity.ok(response);
    }
}
