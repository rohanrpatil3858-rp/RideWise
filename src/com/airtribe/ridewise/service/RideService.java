package com.airtribe.ridewise.service;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.RideStatus;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.util.IdGenerator;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service for managing Rides
 * Orchestrates ride creation, driver matching, and fare calculation
 * Demonstrates:
 * - SRP: Single responsibility for ride management
 * - DIP: Depends on RideMatchingStrategy and FareStrategy interfaces, not concrete classes
 * - OCP: New strategies can be added without modifying this class
 * - Composition: Uses injected strategies for extensibility
 */
public class RideService {
    
    private Map<String, Ride> rides = new HashMap<>();
    private Map<String, FareReceipt> fares = new HashMap<>();
    
    // Strategy injections (Dependency Inversion)
    private RideMatchingStrategy matchingStrategy;
    private FareStrategy fareStrategy;
    
    // Service dependencies
    private RiderService riderService;
    private DriverService driverService;
    
    /**
     * Constructor with strategy injection
     * Demonstrates Dependency Injection and composition over inheritance
     */
    public RideService(RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy,
                      RiderService riderService, DriverService driverService) {
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
        this.riderService = riderService;
        this.driverService = driverService;
    }
    
    /**
     * Request a ride for a rider
     * Creates a new ride in REQUESTED status
     */
    public Ride requestRide(String riderId, String pickupLocation, String dropLocation, double distance)
            throws IllegalArgumentException {
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            throw new IllegalArgumentException("Rider not found: " + riderId);
        }
        
        String rideId = IdGenerator.generateRideId();
        Ride ride = new Ride(rideId, rider, pickupLocation, dropLocation, distance);
        rides.put(rideId, ride);
        
        return ride;
    }
    
    /**
     * Assign a driver to a ride using the matching strategy
     * Updates ride status to ASSIGNED
     */
    public Ride assignDriver(String rideId) throws NoDriverAvailableException, IllegalStateException {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found: " + rideId);
        }
        
        if (ride.getDriver() != null) {
            throw new IllegalStateException("Driver already assigned to this ride");
        }
        
        // Get available drivers
        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        
        // Use strategy to find suitable driver
        Driver assignedDriver = matchingStrategy.findDriver(ride.getRider(), availableDrivers);
        
        if (assignedDriver == null) {
            throw new NoDriverAvailableException("No available driver found for this ride");
        }
        
        // Assign driver and update statuses
        ride.setDriver(assignedDriver);
        ride.setStatus(RideStatus.ASSIGNED);
        driverService.updateAvailability(assignedDriver.getId(), false);
        
        return ride;
    }
    
    /**
     * Calculate fare for a completed ride using the fare strategy
     */
    public FareReceipt calculateFare(String rideId) throws IllegalArgumentException {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found: " + rideId);
        }
        
        // Use strategy to calculate fare
        double amount = fareStrategy.calculateFare(ride);
        
        // Create fare receipt
        FareReceipt receipt = new FareReceipt(rideId, amount, LocalDateTime.now());
        fares.put(rideId, receipt);
        
        return receipt;
    }
    
    /**
     * Complete a ride
     * Updates ride status to COMPLETED and marks driver as available
     */
    public Ride completeRide(String rideId) throws IllegalArgumentException, IllegalStateException {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found: " + rideId);
        }
        
        if (ride.getDriver() == null) {
            throw new IllegalStateException("No driver assigned to this ride");
        }
        
        // Update ride status
        ride.setStatus(RideStatus.COMPLETED);
        
        // Make driver available again
        Driver driver = ride.getDriver();
        driver.incrementRidesCompleted();
        driverService.updateAvailability(driver.getId(), true);
        
        return ride;
    }
    
    /**
     * Cancel a ride
     */
    public Ride cancelRide(String rideId) throws IllegalArgumentException {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found: " + rideId);
        }
        
        ride.setStatus(RideStatus.CANCELLED);
        
        // If driver was assigned, make them available again
        if (ride.getDriver() != null) {
            driverService.updateAvailability(ride.getDriver().getId(), true);
        }
        
        return ride;
    }
    
    /**
     * Get ride by ID
     */
    public Ride getRideById(String rideId) {
        return rides.get(rideId);
    }
    
    /**
     * Get all rides
     */
    public Collection<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }
    
    /**
     * Get fare receipt for a ride
     */
    public FareReceipt getFareReceipt(String rideId) {
        return fares.get(rideId);
    }
    
    /**
     * Change the ride matching strategy at runtime
     * Demonstrates OCP compliance
     */
    public void setMatchingStrategy(RideMatchingStrategy strategy) {
        this.matchingStrategy = strategy;
    }
    
    /**
     * Change the fare calculation strategy at runtime
     * Demonstrates OCP compliance
     */
    public void setFareStrategy(FareStrategy strategy) {
        this.fareStrategy = strategy;
    }
}

