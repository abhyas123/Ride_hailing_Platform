package com.program.repository;

import com.program.entity.DriverVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriverVehicleRepository extends JpaRepository<DriverVehicle, UUID> {

    List<DriverVehicle> findByDriverId(UUID driverId);

    List<DriverVehicle> findByVehicleTypeAndActiveTrue(String vehicleType);
}
