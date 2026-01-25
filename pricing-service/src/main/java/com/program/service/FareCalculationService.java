package com.program.service;

import com.program.entity.PricingRule;
import com.program.repository.PricingRuleRepository;
import com.program.util.FareConstants;
import com.program.util.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FareCalculationService {

    private final PricingRuleRepository pricingRuleRepository;

    public double baseFare(VehicleType vehicleType) {
        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType.name())
                .orElseThrow(() -> new IllegalStateException("Pricing rule not found"));

        return rule.getBaseFare();
    }

    public double distanceFare(VehicleType vehicleType, double distanceKm) {
        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType.name())
                .orElseThrow(() -> new IllegalStateException("Pricing rule not found"));

        return distanceKm * rule.getPerKmRate();
    }

    public double timeFare(VehicleType vehicleType, int timeInMinutes) {
        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType.name())
                .orElseThrow(() -> new IllegalStateException("Pricing rule not found"));

        return timeInMinutes * rule.getPerMinuteRate();
    }

    public double nightCharge() {
        return FareConstants.isNightTime()
                ? FareConstants.NIGHT_CHARGE
                : 0.0;
    }
}
