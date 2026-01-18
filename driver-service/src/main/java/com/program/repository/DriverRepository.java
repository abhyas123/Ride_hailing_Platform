package com.program.repository;

import com.program.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {

    Optional<Driver> findByPhone(String phone);

    boolean existsByPhone(String phone);
}
