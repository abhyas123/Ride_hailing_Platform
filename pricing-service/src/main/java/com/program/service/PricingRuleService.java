package com.program.service;

import com.program.entity.PricingRule;
import com.program.repository.PricingRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingRuleService {

    private final PricingRuleRepository pricingRuleRepository;

    public List<PricingRule> loadRules() {
        return pricingRuleRepository.findAll();
    }
}
