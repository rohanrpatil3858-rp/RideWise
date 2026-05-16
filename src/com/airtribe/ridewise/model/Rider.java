package com.airtribe.ridewise.model;

/**
 * Domain model representing a Rider
 * Encapsulates rider-specific information
 */
public class Rider {
    private String id;
    private String name;
    private String location;

    public Rider(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Rider{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

