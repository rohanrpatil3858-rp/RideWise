# RideWise - Object Relationships Document

## Overview
This document details all object relationships in RideWise, including types, cardinalities, and design implications.

---

## Relationship Types Summary

| From | To | Type | Cardinality | Ownership | Law of Demeter |
|------|-----|------|-------------|-----------|-----------------|
| Rider | Ride | Association | 1 : N | Ride owns reference | ✅ Direct |
| Driver | Ride | Association | 1 : N | Ride owns reference | ✅ Direct |
| Ride | FareReceipt | Composition | 1 : 1 | RideService owns | ✅ Direct |
| RideService | RideMatchingStrategy | Composition | 1 : 1 | RideService owns | ✅ Direct |
| RideService | FareStrategy | Composition | 1 : 1 | RideService owns | ✅ Direct |

---

## Detailed Relationship Analysis

### 1. Rider → Ride (Association)

#### Definition
A Rider can have multiple Rides, but each Ride belongs to exactly one Rider.

#### Type: Association (Uni-directional)
```java
public class Ride {
    private Rider rider;  // Reference to rider
    
    public Ride(String id, Rider rider, String pickup, String drop, double distance) {
        this.rider = rider;  // Store reference
    }
    
    public Rider getRider() {
        return rider;  // Access rider through this reference
    }
}
```

#### Cardinality
- **Rider → Ride**: 1 to Many (1 rider can request many rides)
- **Ride → Rider**: Many to 1 (each ride belongs to one rider)
- **Notation**: Rider "1" ─── "N" Ride

#### Code Example
```java
// Rider requests ride
Rider rajesh = riderService.registerRider("Rajesh", "Downtown");

// Rajesh can have multiple rides
Ride ride1 = rideService.requestRide(rajesh.getId(), "A", "B", 10);
Ride ride2 = rideService.requestRide(rajesh.getId(), "C", "D", 15);

// Each ride knows its rider
System.out.println(ride1.getRider().getName());  // "Rajesh"
System.out.println(ride2.getRider().getName());  // "Rajesh"
```

#### Object Lifetime
- Rider lifetime: ≥ Ride lifetime (Rider persists even after ride completes)
- Ride can exist without active driver, but not without rider
- When accessing ride's rider, information is always available

#### Design Considerations
✅ **Law of Demeter Compliance**: Direct communication  
✅ **Loose Coupling**: Ride just holds reference to Rider  
✅ **No Back Reference**: Rider doesn't know about rides  
⚠️ **One-way Flow**: Only Ride → Rider direction

---

### 2. Driver → Ride (Association)

#### Definition
A Driver can have multiple Rides, but each Ride is assigned to at most one Driver.

#### Type: Association (Uni-directional)
```java
public class Ride {
    private Driver driver;  // Reference to driver (nullable initially)
    
    public void setDriver(Driver driver) {
        this.driver = driver;  // Assigned when match found
    }
    
    public Driver getDriver() {
        return driver;  // Null before assignment
    }
}
```

#### Cardinality
- **Driver → Ride**: 1 to Many (1 driver can complete many rides)
- **Ride → Driver**: Many to 1 or None (ride has 1 or 0 drivers)
- **Notation**: Driver "1" ─── "N" Ride (optional on ride side)

#### Code Example
```java
// Driver registration
Driver amit = driverService.registerDriver("Amit", "Market Sq", VehicleType.CAR);

// Multiple rides can be assigned to one driver
Ride ride1 = rideService.requestRide(...);
Ride ride2 = rideService.requestRide(...);

rideService.assignDriver(ride1.getId());  // amit gets ride1
rideService.assignDriver(ride2.getId());  // amit gets ride2

// Track driver's rides
System.out.println(amit.getRidesCompleted());  // Initially 0

// Complete rides
rideService.completeRide(ride1.getId());
rideService.completeRide(ride2.getId());

System.out.println(amit.getRidesCompleted());  // Now 2
```

#### Object Lifetime
- Driver lifetime: ≥ Ride lifetime
- Ride can exist without driver (REQUESTED, CANCELLED states)
- Driver is assigned during ASSIGNED state
- Driver reference persists even after ride completion

#### Null Handling
```java
public class Ride {
    private Driver driver;  // null initially
    
    public void setDriver(Driver driver) {
        this.driver = driver;  // Set during assignment
    }
    
    // Safe getter
    public Driver getDriver() {
        return driver;  // Can be null before assignment
    }
}

// Usage with null check
Ride ride = rideService.requestRide(...);
if (ride.getDriver() != null) {
    System.out.println("Driver: " + ride.getDriver().getName());
} else {
    System.out.println("No driver assigned yet");
}
```

#### Design Considerations
✅ **Law of Demeter Compliance**: Direct communication  
✅ **Loose Coupling**: Ride just holds reference to Driver  
✅ **Null Safety**: Driver is optional (nullable)  
✅ **State Management**: Driver availability tracked separately  
⚠️ **One-way Flow**: Only Ride → Driver direction

---

### 3. Ride → FareReceipt (Composition)

#### Definition
A Ride has exactly one FareReceipt after completion. FareReceipt cannot exist independently of a Ride.

#### Type: Composition (Strong Ownership)
```java
public class RideService {
    private Map<String, Ride> rides = new HashMap<>();
    private Map<String, FareReceipt> fares = new HashMap<>();  // Owned by service
    
    public FareReceipt calculateFare(String rideId) {
        Ride ride = rides.get(rideId);
        double amount = fareStrategy.calculateFare(ride);
        
        // Create and own the receipt
        FareReceipt receipt = new FareReceipt(rideId, amount, LocalDateTime.now());
        fares.put(rideId, receipt);  // Store with ride ID as key
        
        return receipt;
    }
}
```

#### Cardinality
- **Ride → FareReceipt**: 1 to 1 (each ride has at most 1 receipt)
- **FareReceipt → Ride**: Many to 1 (receipt belongs to exactly 1 ride)
- **Notation**: Ride ◆─── FareReceipt (diamond indicates composition)

#### Code Example
```java
// Request and complete ride
Ride ride = rideService.requestRide(...);
rideService.assignDriver(ride.getId());
rideService.completeRide(ride.getId());

// Calculate fare - creates FareReceipt
FareReceipt receipt = rideService.calculateFare(ride.getId());

System.out.println("Ride ID: " + receipt.getRideId());
System.out.println("Amount: ₹" + receipt.getAmount());
System.out.println("Time: " + receipt.getGeneratedAt());

// Receipt is keyed by ride ID - strong relationship
FareReceipt sameReceipt = rideService.getFareReceipt(ride.getId());
```

#### Lifecycle
- FareReceipt created: After ride completion
- FareReceipt lifetime: ≥ Ride lifetime
- FareReceipt cannot exist without Ride ID
- FareReceipt is owned by RideService, not Ride

#### Composition Indicators
✅ **Strong Ownership**: RideService manages FareReceipt storage  
✅ **Lifecycle Dependency**: FareReceipt depends on Ride  
✅ **ID Coupling**: FareReceipt stores rideId, references Ride via ID  
✅ **No Independent Existence**: Can't create receipt without ride ID  

#### Design Considerations
✅ **Law of Demeter Compliance**: Access via RideService  
✅ **Encapsulation**: FareReceipt managed by service  
✅ **One-to-One Mapping**: Each ride gets exactly one receipt  
✅ **Lazy Creation**: Created only when needed (after completion)

---

### 4. RideService → RideMatchingStrategy (Composition)

#### Definition
RideService owns and manages a RideMatchingStrategy instance. Strategy is injected and controlled by service.

#### Type: Composition (Through Dependency Injection)
```java
public class RideService {
    private RideMatchingStrategy matchingStrategy;  // Owned/managed by service
    
    // Injected via constructor
    public RideService(RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy,
                      RiderService riderService, DriverService driverService) {
        this.matchingStrategy = matchingStrategy;  // Takes ownership
        this.fareStrategy = fareStrategy;
        // ...
    }
    
    public Ride assignDriver(String rideId) throws NoDriverAvailableException {
        Ride ride = rides.get(rideId);
        List<Driver> availableDrivers = driverService.getAvailableDrivers();
        
        // Use injected strategy
        Driver assignedDriver = matchingStrategy.findDriver(ride.getRider(), availableDrivers);
        
        if (assignedDriver == null) {
            throw new NoDriverAvailableException("No available driver found");
        }
        // ...
    }
    
    // Can change strategy at runtime
    public void setMatchingStrategy(RideMatchingStrategy strategy) {
        this.matchingStrategy = strategy;
    }
}
```

#### Cardinality
- **RideService → RideMatchingStrategy**: 1 to 1 (service has exactly one active strategy)
- **Strategy → RideService**: 1 to Many (strategy can be used by multiple services)
- **Notation**: RideService ◆─── RideMatchingStrategy

#### Code Example
```java
// Create strategy
RideMatchingStrategy strategy1 = new NearestDriverStrategy();

// Inject into service (ownership transfer)
RideService rideService = new RideService(
    strategy1,  // Service now owns this strategy
    new DefaultFareStrategy(),
    riderService,
    driverService
);

// Use strategy through service
Ride ride = rideService.requestRide(...);
rideService.assignDriver(ride.getId());  // Uses strategy1 internally

// Change strategy at runtime (another ownership)
RideMatchingStrategy strategy2 = new LeastActiveDriverStrategy();
rideService.setMatchingStrategy(strategy2);  // Ownership changes

Ride ride2 = rideService.requestRide(...);
rideService.assignDriver(ride2.getId());  // Uses strategy2 now
```

#### Dependency Injection Details

**Constructor Injection Pattern**:
```java
// Service receives strategy as dependency
public RideService(
    RideMatchingStrategy matchingStrategy,  // Injected
    FareStrategy fareStrategy,              // Injected
    RiderService riderService,              // Injected
    DriverService driverService             // Injected
) {
    this.matchingStrategy = matchingStrategy;
    this.fareStrategy = fareStrategy;
    this.riderService = riderService;
    this.driverService = driverService;
}
```

**Benefits of This Approach**:
- ✅ Loose coupling (depends on interface, not concrete class)
- ✅ Testable (can inject mock strategies)
- ✅ Flexible (can change strategies)
- ✅ Clear dependencies (visible in constructor)

#### Ownership Model
```
┌─────────────────┐
│  RideService    │◆───────┐
└─────────────────┘        │ owns
        │                  │
        └─────────────────────┐
                               ▼
                    ┌──────────────────────────┐
                    │RideMatchingStrategy      │◄─── Interface
                    │(Abstraction)             │
                    └──────┬──────────────────┘
                           │ implemented by
                    ┌──────┴──────────────────┐
                    │                         │
         ┌──────────────────┐    ┌──────────────────────┐
         │NearestDriver     │    │LeastActiveDriver     │
         │Strategy          │    │Strategy              │
         └──────────────────┘    └──────────────────────┘
```

#### Design Considerations
✅ **DIP Compliance**: Owns interface, not concrete class  
✅ **OCP Compliance**: Can add strategies without modification  
✅ **Composition Over Inheritance**: Uses composition, not subclassing  
✅ **Strategy Pattern**: Textbook implementation  
✅ **Runtime Flexibility**: Can swap strategies at runtime

---

### 5. RideService → FareStrategy (Composition)

#### Definition
RideService owns and manages a FareStrategy instance. Strategy is injected and controlled by service.

#### Type: Composition (Through Dependency Injection)
```java
public class RideService {
    private FareStrategy fareStrategy;  // Owned/managed by service
    
    // Injected via constructor
    public RideService(RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy,
                      RiderService riderService, DriverService driverService) {
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;  // Takes ownership
        // ...
    }
    
    public FareReceipt calculateFare(String rideId) {
        Ride ride = rides.get(rideId);
        
        // Use injected strategy
        double amount = fareStrategy.calculateFare(ride);
        
        FareReceipt receipt = new FareReceipt(rideId, amount, LocalDateTime.now());
        fares.put(rideId, receipt);
        
        return receipt;
    }
    
    // Can change strategy at runtime
    public void setFareStrategy(FareStrategy strategy) {
        this.fareStrategy = strategy;
    }
}
```

#### Cardinality
- **RideService → FareStrategy**: 1 to 1 (service has exactly one active strategy)
- **Strategy → RideService**: 1 to Many (strategy can be used by multiple services)
- **Notation**: RideService ◆─── FareStrategy

#### Code Example
```java
// Create strategies
FareStrategy defaultFare = new DefaultFareStrategy();
FareStrategy peakHourFare = new PeakHourFareStrategy();

// Use default fare initially
RideService rideService = new RideService(
    new NearestDriverStrategy(),
    defaultFare,  // Service owns this
    riderService,
    driverService
);

Ride ride1 = rideService.requestRide(...);
rideService.assignDriver(ride1.getId());
rideService.completeRide(ride1.getId());

FareReceipt receipt1 = rideService.calculateFare(ride1.getId());
System.out.println("Regular fare: ₹" + receipt1.getAmount());

// Switch to peak hour fare
rideService.setFareStrategy(peakHourFare);

Ride ride2 = rideService.requestRide(...);
rideService.assignDriver(ride2.getId());
rideService.completeRide(ride2.getId());

FareReceipt receipt2 = rideService.calculateFare(ride2.getId());
System.out.println("Peak hour fare: ₹" + receipt2.getAmount());
// May be 1.5x if during peak hours
```

#### Ownership Model
```
┌─────────────────┐
│  RideService    │◆───────┐
└─────────────────┘        │ owns
        │                  │
        └─────────────────────┐
                               ▼
                    ┌──────────────────────┐
                    │FareStrategy          │◄─── Interface
                    │(Abstraction)         │
                    └──────┬───────────────┘
                           │ implemented by
                    ┌──────┴──────────────────┐
                    │                         │
         ┌──────────────────┐    ┌──────────────────────┐
         │DefaultFare       │    │PeakHourFare          │
         │Strategy          │    │Strategy              │
         └──────────────────┘    └──────────────────────┘
```

#### Design Considerations
✅ **DIP Compliance**: Owns interface, not concrete class  
✅ **OCP Compliance**: Can add strategies without modification  
✅ **Composition Over Inheritance**: Uses composition, not subclassing  
✅ **Strategy Pattern**: Textbook implementation  
✅ **Runtime Flexibility**: Can swap strategies at runtime

---

## Law of Demeter Analysis

The Law of Demeter states: "Only talk to your immediate friends."

### Compliance Check

| Relationship | Direct Access? | Demeter Compliant? |
|--------------|---------------|--------------------|
| RideService → RiderService | Yes (injected) | ✅ Yes |
| RideService → DriverService | Yes (injected) | ✅ Yes |
| RideService → RideMatchingStrategy | Yes (injected) | ✅ Yes |
| RideService → FareStrategy | Yes (injected) | ✅ Yes |
| Ride → Rider | Yes (reference) | ✅ Yes |
| Ride → Driver | Yes (reference) | ✅ Yes |

### Example: Correct Law of Demeter

```java
// GOOD: RideService only talks to immediate friends
public void completeRide(String rideId) {
    Ride ride = rides.get(rideId);                    // Get from own collection
    Driver driver = ride.getDriver();                 // Get from ride
    driver.incrementRidesCompleted();                 // Call on driver
    driverService.updateAvailability(driver.getId(), true);  // Call on service
}

// BAD: Would violate Law of Demeter
// ride.getDriver().getVehicleType().toString()...   // Chain of method calls
// ride.getRider().getLocation().getCoordinates()... // Too many hops
```

---

## Relationship Diagram

```
┌──────────────────────────────────────────────────┐
│              RideWise Relationships               │
├──────────────────────────────────────────────────┤
│                                                  │
│  ┌─────────────────┐                            │
│  │    Rider        │                            │
│  │  ┌───────────┐  │                            │
│  │  │  id       │  │                            │
│  │  │  name     │  │                            │
│  │  │  location │  │                            │
│  │  └───────────┘  │                            │
│  └────────┬────────┘                            │
│           │ (1 to N)                            │
│           │ Association                         │
│           ▼                                      │
│  ┌─────────────────────────────────────┐       │
│  │           Ride                      │       │
│  │  ┌────────────────────────────────┐ │       │
│  │  │  id                            │ │       │
│  │  │  rider────────────────────────>│ │       │
│  │  │  driver─────────────────────>  │ │       │
│  │  │  distance                      │ │       │
│  │  │  status                        │ │       │
│  │  │  pickup                        │ │       │
│  │  │  drop                          │ │       │
│  │  └────────────────────────────────┘ │       │
│  └────────────┬──────────────────────┬──┘       │
│               │                      │          │
│               │ (1 to N)        (1 to 1)        │
│               │ Association    Composition      │
│               │                      │          │
│  ┌────────────┴────────┐   ┌─────────▼───────┐ │
│  │     Driver          │   │  FareReceipt    │ │
│  │  ┌────────────────┐ │   │ ┌─────────────┐│ │
│  │  │ id             │ │   │ │ rideId      ││ │
│  │  │ name           │ │   │ │ amount      ││ │
│  │  │ location       │ │   │ │ baseFare    ││ │
│  │  │ available      │ │   │ │ distance    ││ │
│  │  │ vehicleType    │ │   │ │ surge       ││ │
│  │  │ ridesCompleted │ │   │ │ generatedAt ││ │
│  │  └────────────────┘ │   │ └─────────────┘│ │
│  └─────────────────────┘   └─────────────────┘ │
│           ▲                                     │
│           │ (1 to N)                           │
│           │ Association                        │
└───────────┼─────────────────────────────────────┘
            │
      (Owned by RideService)


┌──────────────────────────────────────────────────────┐
│            Strategy Relationships                     │
├──────────────────────────────────────────────────────┤
│                                                      │
│  ┌──────────────────────────────────┐              │
│  │      RideService                 │              │
│  │ ◆─────────────────────────────┐  │              │
│  │ │                             │  │              │
│  │ ▼ (Composition)          (Composition)          │
│  │  ┌──────────────────┐  ◆─────────────────┐    │
│  │  │RideMatching     │   │   FareStrategy  │    │
│  │  │Strategy         │   └────────────────┘    │
│  │  │(Interface)      │         │ Implemented by:│
│  │  └────────┬────────┘         │                │
│  │           │                  ├─ Default      │
│  │  Impl by: ├─ Nearest         ├─ PeakHour     │
│  │           ├─ LeastActive     │                │
│  │           │                  │                │
│  └───────────┼──────────────────┼────────────────┘
│              │                  │
│         (Strategy)         (Strategy)
│
└──────────────────────────────────────────────────────┘
```

---

## Composition vs Association Summary

| Aspect | Association | Composition |
|--------|------------|-----------|
| **Strength** | Weak | Strong |
| **Ownership** | Shared | Exclusive |
| **Lifetime** | Independent | Dependent |
| **Cardinality** | 1:N usually | 1:1 usually |
| **Example** | Rider-Ride, Driver-Ride | Ride-FareReceipt |
| **Symbol** | ─── | ◆─── |

---

## Conclusion

RideWise demonstrates:
- ✅ **Clear Relationships**: Well-defined associations and compositions
- ✅ **Law of Demeter**: No excessive method chaining or deep coupling
- ✅ **Proper Ownership**: Clear ownership model through dependency injection
- ✅ **Appropriate Cardinality**: Correct one-to-one, one-to-many relationships
- ✅ **Flexible Design**: Strategies can be swapped without affecting relationships

