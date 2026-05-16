package com.airtribe.ridewise.model;

/**
 * Domain model representing a Ride
 * Encapsulates ride information and its current state
 */
public class Ride {
    private String id;
    private Rider rider;
    private Driver driver;
    private double distance;
    private RideStatus status;
    private String pickupLocation;
    private String dropLocation;
    private long createdAt;
    private long completedAt;

    public Ride(String id, Rider rider, String pickupLocation, String dropLocation, double distance) {
        this.id = id;
        this.rider = rider;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
        this.createdAt = System.currentTimeMillis();
        this.completedAt = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public double getDistance() {
        return distance;
    }

    public RideStatus getStatus() {
        return status;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getCompletedAt() {
        return completedAt;
    }

    // Setters
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
        if (status == RideStatus.COMPLETED) {
            this.completedAt = System.currentTimeMillis();
        }
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", rider=" + rider.getName() +
                ", driver=" + (driver != null ? driver.getName() : "Not Assigned") +
                ", distance=" + distance +
                ", status=" + status +
                ", pickupLocation='" + pickupLocation + '\'' +
                ", dropLocation='" + dropLocation + '\'' +
                '}';
    }
}

