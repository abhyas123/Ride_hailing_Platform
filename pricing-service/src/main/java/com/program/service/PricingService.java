package com.program.service;

import com.program.dto.request.PricingEstimateRequest;
import com.program.dto.request.PricingFinalRequest;
import com.program.dto.response.PricingEstimateResponse;
import com.program.dto.response.PricingFinalResponse;
import com.program.dto.response.VehicleFareResponse;
import com.program.entity.FareAudit;
import com.program.util.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final FareCalculationService fareCalculationService;
    private final SurgePricingService surgePricingService;
    private final PricingRuleService pricingRuleService;
    private final PricingAuditService pricingAuditService;

    // ===============================
    // ESTIMATE FARE (BEFORE RIDE)
    // ===============================
    @Transactional(readOnly = true)
    public PricingEstimateResponse estimateFare(PricingEstimateRequest request) {

        pricingRuleService.loadRules(); // ensure rules cached

        List<VehicleFareResponse> fares =
                VehicleType.supportedTypes().stream()
                        .map(vehicleType -> {

                            double baseFare =
                                    fareCalculationService.baseFare(vehicleType);

                            double distanceFare =
                                    fareCalculationService.distanceFare(
                                            vehicleType,
                                            request.getDistanceKm()
                                    );

                            double timeFare =
                                    fareCalculationService.timeFare(
                                            vehicleType,
                                            request.getEstimatedDurationMinutes().intValue()

                                    );

                            double nightCharge =
                                    fareCalculationService.nightCharge();

                            double total =
                                    baseFare + distanceFare + timeFare + nightCharge;

                            total =
                                    surgePricingService.applySurge(
                                            vehicleType,
                                            total
                                    );

                            return VehicleFareResponse.builder()
                                    .vehicleType(vehicleType)
                                    .fare(total)
                                    .build();
                        })
                        .toList();

        return new PricingEstimateResponse(
                request.getDistanceKm(),
                request.getEstimatedDurationMinutes().intValue(),
                fares
        );
    }

    // ===============================
    // FINAL FARE (AFTER RIDE)
    // ===============================
    @Transactional
    public PricingFinalResponse calculateFinalFare(PricingFinalRequest request) {

        double baseFare =
                fareCalculationService.baseFare(request.getVehicleType());

        double distanceFare =
                fareCalculationService.distanceFare(
                        request.getVehicleType(),
                        request.getActualDistanceKm()
                );

        double timeFare =
                fareCalculationService.timeFare(
                        request.getVehicleType(),
                        request.getActualDurationMinutes().intValue()
                );

        double nightCharge =
                fareCalculationService.nightCharge();

        double total =
                baseFare + distanceFare + timeFare + nightCharge;

        total =
                surgePricingService.applySurge(
                        request.getVehicleType(),
                        total
                );

        FareAudit audit =
                pricingAuditService.logFareBreakdown(
                        request,
                        baseFare,
                        distanceFare,
                        timeFare,
                        nightCharge,
                        total
                );

        return new PricingFinalResponse(
                audit.getRideId(),
                total,
                null
        );
    }
}
