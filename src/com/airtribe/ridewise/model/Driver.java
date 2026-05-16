package com.airtribe.ridewise.model;

/**
 * Domain model representing a Driver
 * Encapsulates driver-specific information and availability status
 */
public class Driver {
    private String id;
    private String name;
    private String currentLocation;
    private boolean available;
    private VehicleType vehicleType;
    private int ridesCompleted;

    public Driver(String id, String name, String currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true;
        this.ridesCompleted = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getRidesCompleted() {
        return ridesCompleted;
    }

    // Setters
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void incrementRidesCompleted() {
        this.ridesCompleted++;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", available=" + available +
                ", vehicleType=" + vehicleType +
                ", ridesCompleted=" + ridesCompleted +
                '}';
    }
}

