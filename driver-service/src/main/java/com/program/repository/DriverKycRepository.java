package com.program.repository;

import com.program.entity.DriverKyc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DriverKycRepository extends JpaRepository<DriverKyc, UUID> {

    Optional<DriverKyc> findByDriverId(UUID driverId);
}
