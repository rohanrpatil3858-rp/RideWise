# RideWise - Modular Ride-Sharing System

A **low-level design (LLD)** implementation of a ride-sharing platform using **OOP & SOLID principles**. This project demonstrates best practices in software architecture with a focus on modularity, extensibility, and maintainability.

---

## 📋 Project Overview

RideWise is a console-based ride-sharing application that showcases:
- **Domain-driven design** with clear entity models
- **Strategy pattern** for flexible algorithm switching
- **Dependency injection** for loose coupling
- **Service-oriented architecture** following SOLID principles
- **Clean separation of concerns** across layers

---

## 🎯 SOLID Principles Implementation

### 1. **Single Responsibility Principle (SRP)**
Each class has a single, well-defined responsibility:
- `RiderService` → Manages rider registration & retrieval
- `DriverService` → Manages driver registration & availability
- `RideService` → Orchestrates ride lifecycle
- `NearestDriverStrategy` → Finds nearest driver
- `DefaultFareStrategy` → Calculates standard fares

### 2. **Open/Closed Principle (OCP)**
The system is **open for extension, closed for modification**:
- New strategies can be added without modifying existing code
- Example: Add `AirportSpecialStrategy` without touching `RideService`

### 3. **Liskov Substitution Principle (LSP)**
All strategy implementations are interchangeable:
- `NearestDriverStrategy` can replace `LeastActiveDriverStrategy`
- `DefaultFareStrategy` can replace `PeakHourFareStrategy`
- No method overrides violate the contract

### 4. **Interface Segregation Principle (ISP)**
Small, focused interfaces:
- `RideMatchingStrategy` → Only one responsibility (findDriver)
- `FareStrategy` → Only one responsibility (calculateFare)
- Services depend only on interfaces they need

### 5. **Dependency Inversion Principle (DIP)**
High-level modules depend on abstractions:
```java
public RideService(RideMatchingStrategy matchingStrategy, 
                   FareStrategy fareStrategy, ...) {
    // RideService depends on interfaces, not concrete classes
}
```

---

## 🏗️ Architecture & Design Patterns

### **Strategy Pattern**
Allows runtime switching of algorithms:
```
RideMatchingStrategy (Interface)
├── NearestDriverStrategy
└── LeastActiveDriverStrategy

FareStrategy (Interface)
├── DefaultFareStrategy
└── PeakHourFareStrategy
```

### **Dependency Injection**
Services receive dependencies through constructors:
```java
RideService service = new RideService(
    new NearestDriverStrategy(),    // Injected
    new DefaultFareStrategy(),      // Injected
    riderService,                   // Injected
    driverService                   // Injected
);
```

### **Service Layer Architecture**
```
┌─────────────────────────────┐
│      Main (Console UI)      │
├─────────────────────────────┤
│   Service Layer             │
│  ├─ RideService             │
│  ├─ RiderService            │
│  └─ DriverService           │
├─────────────────────────────┤
│   Strategy Layer            │
│  ├─ RideMatchingStrategy    │
│  └─ FareStrategy            │
├─────────────────────────────┤
│   Domain Models             │
│  ├─ Rider, Driver, Ride     │
│  └─ FareReceipt             │
└─────────────────────────────┘
```

---

## 📦 Project Structure

```
Ridewise/
├── src/
│   ├── Main.java                      # Console application
│   ├── RideStatus.java                # Enum
│   ├── VehicleType.java               # Enum
│   ├── Rider.java                     # Domain model
│   ├── Driver.java                    # Domain model
│   ├── Ride.java                      # Domain model
│   ├── FareReceipt.java               # Domain model
│   ├── RideMatchingStrategy.java      # Strategy interface
│   ├── FareStrategy.java              # Strategy interface
│   ├── NearestDriverStrategy.java     # Strategy implementation
│   ├── LeastActiveDriverStrategy.java # Strategy implementation
│   ├── DefaultFareStrategy.java       # Strategy implementation
│   ├── PeakHourFareStrategy.java      # Strategy implementation
│   ├── RiderService.java              # Service layer
│   ├── DriverService.java             # Service layer
│   └── RideService.java               # Service layer
└── README.md                          # This file
```

---

## 🚀 How to Run

### Prerequisites
- Java 8 or higher
- Any IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Compilation & Execution

**Using Command Line:**
```bash
# Navigate to project root
cd Ridewise

# Compile all Java files
javac -d bin src/*.java

# Run the application
java -cp bin Main
```

**Using IntelliJ IDEA:**
1. Open the project
2. Right-click `Main.java`
3. Select "Run 'Main'"

---

## 📱 Feature Walkthrough

### 1. **Register Rider**
```
Input: Name, Location
Output: Rider ID (R1, R2, ...)
Status: CREATED
```

### 2. **Register Driver**
```
Input: Name, Location, Vehicle Type (BIKE/AUTO/CAR)
Output: Driver ID (D1, D2, ...)
Status: AVAILABLE
```

### 3. **Request Ride**
```
Process:
1. Rider submits request (pickup, drop, distance)
2. System creates Ride (Status: REQUESTED)
3. Matching strategy finds suitable driver
4. Driver is assigned (Status: ASSIGNED)
5. Driver becomes unavailable
```

### 4. **Complete Ride**
```
Process:
1. Mark ride as COMPLETED
2. Calculate fare using fare strategy
3. Make driver available again
4. Increment driver's ride count
```

### 5. **Switch Strategies**
```
Available Strategies:
- Matching: Nearest Driver vs Least Active Driver
- Fare: Default (₹50 + ₹15/km) vs Peak Hour (1.5x multiplier)
```

---

## 💡 Domain Entities

### **Rider**
| Field | Type | Description |
|-------|------|-------------|
| id | String | Unique identifier (R1, R2, ...) |
| name | String | Rider's name |
| location | String | Current location |

### **Driver**
| Field | Type | Description |
|-------|------|-------------|
| id | String | Unique identifier (D1, D2, ...) |
| name | String | Driver's name |
| currentLocation | String | Current location |
| available | boolean | Availability status |
| vehicleType | VehicleType | BIKE, AUTO, or CAR |
| ridesCompleted | int | Total completed rides |

### **Ride**
| Field | Type | Description |
|-------|------|-------------|
| id | String | Unique identifier (RIDE1, ...) |
| rider | Rider | Associated rider |
| driver | Driver | Assigned driver (nullable) |
| distance | double | Distance in km |
| status | RideStatus | REQUESTED → ASSIGNED → COMPLETED |
| pickupLocation | String | Pickup point |
| dropLocation | String | Drop point |

### **FareReceipt**
| Field | Type | Description |
|-------|------|-------------|
| rideId | String | Associated ride ID |
| amount | double | Total fare amount |
| baseFare | double | Base fare component |
| distanceFare | double | Distance-based fare |
| surgePricing | double | Peak hour surcharge |

---

## 🔄 Ride Lifecycle

```
┌─────────────┐
│  REQUESTED  │  Rider requests a ride
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  ASSIGNED   │  Driver matched & assigned
└──────┬──────┘
       │
       ├──────────────────┐
       │                  │
       ▼                  ▼
┌─────────────┐    ┌──────────────┐
│  COMPLETED  │    │  CANCELLED   │
└─────────────┘    └──────────────┘
```

---

## 🛠️ Extensibility Examples

### **Add a New Matching Strategy**
```java
public class RandomDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> availableDrivers) {
        if (availableDrivers.isEmpty()) return null;
        int random = new Random().nextInt(availableDrivers.size());
        return availableDrivers.get(random);
    }
}

// Usage - NO CHANGES to RideService needed!
rideService.setMatchingStrategy(new RandomDriverStrategy());
```

### **Add a New Fare Strategy**
```java
public class DistanceBasedFareStrategy implements FareStrategy {
    @Override
    public double calculateFare(Ride ride) {
        // 1. Flat ₹20 + ₹20/km for CAR
        // 2. Flat ₹10 + ₹8/km for BIKE
        if (ride.getDriver().getVehicleType() == VehicleType.CAR) {
            return 20 + (ride.getDistance() * 20);
        }
        return 10 + (ride.getDistance() * 8);
    }
}

// Usage - NO CHANGES to RideService needed!
rideService.setFareStrategy(new DistanceBasedFareStrategy());
```

---

## 📊 Sample Scenarios

### **Scenario 1: Basic Ride Request**
```
1. Rajesh (Rider) requests ride from Downtown → Airport
   Distance: 15 km
   
2. System finds nearest driver: Amit Patel (CAR)
   
3. Ride assigned & Amit gets the ride
   
4. Ride completed
   
5. Fare calculated: ₹50 (base) + ₹225 (15*15) = ₹275
```

### **Scenario 2: Peak Hour Surge Pricing**
```
1. Request at 8:30 AM (Peak hours: 8-10 AM)
   
2. Fare with DefaultFareStrategy: ₹275
   
3. Switch to PeakHourFareStrategy
   
4. Same ride now costs: ₹275 * 1.5 = ₹412.50
```

### **Scenario 3: Driver Load Balancing**
```
Current Drivers:
- Amit: 45 completed rides (busy)
- Suresh: 12 completed rides (new)
- Vikram: 23 completed rides (medium)

Matching Strategy:
- NearestDriver: Picks closest (may overload experienced drivers)
- LeastActiveDriver: Picks Suresh (distributes load evenly)
```

---

## 🎓 Key Learning Outcomes

After studying this implementation, you'll understand:

1. **OOP Principles**
   - Encapsulation (private fields, getters/setters)
   - Inheritance (strategy pattern)
   - Polymorphism (interface implementations)

2. **SOLID Principles**
   - How to write maintainable, extensible code
   - How to decouple components
   - How to design for change

3. **Design Patterns**
   - Strategy Pattern (algorithms as objects)
   - Dependency Injection (loose coupling)
   - Service Layer Pattern (separation of concerns)

4. **System Design**
   - Domain modeling
   - Service layer architecture
   - Extension points

---

## ❓ Common Questions

**Q: Why use interfaces for strategies?**
A: Allows changing algorithm at runtime without modifying RideService.

**Q: What if I want to persist data to database?**
A: Create a `DAO` layer between services and in-memory maps.

**Q: How do I add authentication?**
A: Create `AuthService` and inject into services that need it.

**Q: Can I add payments?**
A: Create `PaymentService` and call it when ride completes.

---

## 📝 Next Steps for Enhancement

- ✅ Add rating system for riders & drivers
- ✅ Add payment processing
- ✅ Add real GPS-based distance calculation
- ✅ Add database persistence (MySQL/PostgreSQL)
- ✅ Convert to Spring Boot web service
- ✅ Add unit tests with JUnit & Mockito
- ✅ Add REST API endpoints
- ✅ Add analytics & reporting

---

## 🎯 Assignment Requirements Checklist

- ✅ Register Riders
- ✅ Register Drivers
- ✅ Show available drivers
- ✅ Request a ride
- ✅ Match ride using strategy
- ✅ Calculate fare using strategy
- ✅ Track ride status (REQUESTED → ASSIGNED → COMPLETED → CANCELLED)
- ✅ RideMatchingStrategy interface & implementations
- ✅ FareStrategy interface & implementations
- ✅ RiderService, DriverService, RideService
- ✅ Console application with menu
- ✅ SOLID principles compliance
- ✅ Design patterns implementation
- ✅ Low coupling between services
- ✅ Easily extendable architecture

---

## 👨‍💻 Author Notes

This implementation focuses on **simplicity and clarity** without over-engineering. The code is:
- **Readable** → Easy to understand logic
- **Maintainable** → Clear separation of concerns
- **Extensible** → New features without modifying existing code
- **Testable** → Can be easily unit tested

Study each layer independently:
1. Start with domain models (Rider, Driver, Ride)
2. Understand strategies (how they work)
3. Analyze services (how they orchestrate)
4. Review Main (how it ties everything)

---

## 📚 References

- **SOLID Principles**: https://en.wikipedia.org/wiki/SOLID
- **Design Patterns**: Gang of Four patterns
- **Clean Code**: Robert C. Martin


