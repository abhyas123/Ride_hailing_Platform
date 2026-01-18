package com.program.repository;

import com.program.entity.DriverEarnings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DriverEarningsRepository extends JpaRepository<DriverEarnings, UUID> {

    Optional<DriverEarnings> findByDriverId(UUID driverId);
}
