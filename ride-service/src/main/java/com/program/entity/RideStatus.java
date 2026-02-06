package com.program.entity;

public enum RideStatus {
    CREATED,           // Ride request created, searching for driver
    DRIVER_ASSIGNED,   // Driver has accepted the ride
    ONGOING,           // Ride is in progress
    COMPLETED,         // Ride finished successfully
    CANCELLED          // Cancelled by user or driver
}
