package com.program.repository;

import com.program.entity.DriverWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriverWithdrawalRepository extends JpaRepository<DriverWithdrawal, UUID> {

    List<DriverWithdrawal> findByDriverIdOrderByRequestedAtDesc(UUID driverId);
}
