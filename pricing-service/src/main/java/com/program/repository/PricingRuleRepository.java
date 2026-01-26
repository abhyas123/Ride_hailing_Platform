package com.program.repository;

import com.program.entity.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PricingRuleRepository
        extends JpaRepository<PricingRule, UUID> {

    Optional<PricingRule> findByVehicleType(String vehicleType);
}
