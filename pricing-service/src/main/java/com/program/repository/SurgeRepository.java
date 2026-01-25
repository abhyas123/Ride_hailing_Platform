package com.program.repository;

import com.program.entity.SurgeMultiplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SurgeRepository
        extends JpaRepository<SurgeMultiplier, UUID> {

    Optional<SurgeMultiplier> findByCityAndActiveTrue(String city);
}
