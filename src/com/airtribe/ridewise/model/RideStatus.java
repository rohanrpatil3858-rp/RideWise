package com.airtribe.ridewise.model;

/**
 * Enum representing the status of a ride throughout its lifecycle
 */
public enum RideStatus {
    REQUESTED,      // Initial state when rider requests a ride
    ASSIGNED,       // Ride has been matched with a driver
    COMPLETED,      // Ride has been successfully completed
    CANCELLED       // Ride has been cancelled
}

