package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.util.IdGenerator;
import java.util.*;

/**
 * Service for managing Drivers
 * Responsible for driver registration, availability, and retrieval
 * Follows SRP (Single Responsibility Principle)
 */
public class DriverService {
    
    private Map<String, Driver> drivers = new HashMap<>();
    
    /**
     * Register a new driver in the system
     */
    public Driver registerDriver(String name, String currentLocation, VehicleType vehicleType) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Driver name cannot be empty");
        }
        
        String driverId = IdGenerator.generateDriverId();
        Driver driver = new Driver(driverId, name, currentLocation, vehicleType);
        drivers.put(driverId, driver);
        
        return driver;
    }
    
    /**
     * Get a driver by ID
     */
    public Driver getDriverById(String driverId) {
        return drivers.get(driverId);
    }
    
    /**
     * Get all available drivers
     */
    public List<Driver> getAvailableDrivers() {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
    
    /**
     * Update driver availability
     */
    public void updateAvailability(String driverId, boolean available) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setAvailable(available);
        }
    }
    
    /**
     * Update driver location
     */
    public void updateDriverLocation(String driverId, String newLocation) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setCurrentLocation(newLocation);
        }
    }
    
    /**
     * Get all registered drivers
     */
    public Collection<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }
    
    /**
     * Check if driver exists
     */
    public boolean driverExists(String driverId) {
        return drivers.containsKey(driverId);
    }
}

