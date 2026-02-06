package com.program.repository;

import com.program.entity.Ride;
import com.program.entity.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    
    // Find active rides for a passenger
    List<Ride> findByPassengerIdAndStatusIn(UUID passengerId, List<RideStatus> statuses);

    // Find active rides for a driver
    List<Ride> findByDriverIdAndStatusIn(UUID driverId, List<RideStatus> statuses);
    
    // Find all rides by status (admin use or background jobs)
    List<Ride> findByStatus(RideStatus status);
}
