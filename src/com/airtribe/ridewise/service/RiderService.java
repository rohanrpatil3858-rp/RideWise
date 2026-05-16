package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.util.IdGenerator;
import java.util.*;

/**
 * Service for managing Riders
 * Responsible for rider registration and retrieval
 * Follows SRP (Single Responsibility Principle)
 */
public class RiderService {
    
    private Map<String, Rider> riders = new HashMap<>();
    
    /**
     * Register a new rider in the system
     */
    public Rider registerRider(String name, String location) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Rider name cannot be empty");
        }
        
        String riderId = IdGenerator.generateRiderId();
        Rider rider = new Rider(riderId, name, location);
        riders.put(riderId, rider);
        
        return rider;
    }
    
    /**
     * Get a rider by ID
     */
    public Rider getRiderById(String riderId) {
        return riders.get(riderId);
    }
    
    /**
     * Get all registered riders
     */
    public Collection<Rider> getAllRiders() {
        return new ArrayList<>(riders.values());
    }
    
    /**
     * Check if rider exists
     */
    public boolean riderExists(String riderId) {
        return riders.containsKey(riderId);
    }
}

