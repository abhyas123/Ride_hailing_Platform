package com.program.service;

import com.program.dto.request.PricingFinalRequest;
import com.program.entity.FareAudit;
import com.program.repository.FareAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PricingAuditService {

    private final FareAuditRepository fareAuditRepository;

    public FareAudit logFareBreakdown(
            PricingFinalRequest request,
            double baseFare,
            double distanceFare,
            double timeFare,
            double nightCharge,
            double totalFare
    ) {

        FareAudit audit = FareAudit.builder()
                .rideId(request.getRideId())
                .vehicleType(request.getVehicleType())
                .baseFare(baseFare)
                .distanceFare(distanceFare)
                .timeFare(timeFare)
                .nightCharge(nightCharge)
                .totalFare(totalFare)
                .createdAt(LocalDateTime.now())
                .build();

        return fareAuditRepository.save(audit);
    }
}
