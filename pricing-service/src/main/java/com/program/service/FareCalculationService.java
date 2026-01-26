package com.program.service;

import com.program.entity.PricingRule;
import com.program.exception.PricingException;
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
                .findByVehicleType(vehicleType)
                .orElseThrow(() -> new PricingException(
                        "Pricing rule not found for vehicle type: " + vehicleType.name() + 
                        ". Please ensure pricing rules are initialized in the database."));

        return rule.getBaseFare();
    }

    public double distanceFare(VehicleType vehicleType, double distanceKm) {
        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType)
                .orElseThrow(() -> new PricingException(
                        "Pricing rule not found for vehicle type: " + vehicleType.name() + 
                        ". Please ensure pricing rules are initialized in the database."));

        return distanceKm * rule.getPerKmRate();
    }

    public double timeFare(VehicleType vehicleType, int timeInMinutes) {
        PricingRule rule = pricingRuleRepository
                .findByVehicleType(vehicleType)
                .orElseThrow(() -> new PricingException(
                        "Pricing rule not found for vehicle type: " + vehicleType.name() + 
                        ". Please ensure pricing rules are initialized in the database."));

        return timeInMinutes * rule.getPerMinuteRate();
    }

    public double nightCharge() {
        return FareConstants.isNightTime()
                ? FareConstants.NIGHT_CHARGE
                : 0.0;
    }
}
