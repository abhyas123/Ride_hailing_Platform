package com.program.repository;

import com.program.entity.PricingRule;
import com.program.util.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PricingRuleRepository
        extends JpaRepository<PricingRule, UUID> {

    Optional<PricingRule> findByVehicleType(VehicleType vehicleType);
}
