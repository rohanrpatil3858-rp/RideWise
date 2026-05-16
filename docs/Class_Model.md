# RideWise - Class Model

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      MODEL LAYER                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐           ┌──────────────┐                  │
│  │   Rider      │           │   Driver     │                  │
│  ├──────────────┤           ├──────────────┤                  │
│  │ -id: String  │           │ -id: String  │                  │
│  │ -name        │           │ -name        │                  │
│  │ -location    │           │ -location    │                  │
│  └──────────────┘           │ -available   │                  │
│         ▲                    │ -vehicleType │                  │
│         │                    │ -rides       │                  │
│         │                    └──────────────┘                  │
│         │ (association)               ▲                        │
│         │                            │ (association)           │
│    ┌────┴───────────────────────────┘                          │
│    │                                                            │
│    │                  ┌──────────────┐                         │
│    ├─────────────────>│    Ride      │                         │
│    │                  ├──────────────┤                         │
│    │                  │ -id: String  │                         │
│    │                  │ -rider       │                         │
│    │                  │ -driver      │                         │
│    │                  │ -distance    │                         │
│    │                  │ -status      │                         │
│    │                  │ -pickup      │                         │
│    │                  │ -drop        │                         │
│    │                  └──────┬───────┘                         │
│    │                         │ (composition)                    │
│    │                         ▼                                  │
│    │                  ┌────────────────────┐                   │
│    └────────────────>│ FareReceipt        │                   │
│                      ├────────────────────┤                   │
│                      │ -rideId: String    │                   │
│                      │ -amount: double    │                   │
│                      │ -baseFare: double  │                   │
│                      │ -distanceFare      │                   │
│                      │ -surgePricing      │                   │
│                      │ -generatedAt       │                   │
│                      └────────────────────┘                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                     ENUM LAYER                                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────┐        ┌─────────────────┐             │
│  │  RideStatus      │        │  VehicleType    │             │
│  ├──────────────────┤        ├─────────────────┤             │
│  │ REQUESTED        │        │ BIKE            │             │
│  │ ASSIGNED         │        │ AUTO            │             │
│  │ COMPLETED        │        │ CAR             │             │
│  │ CANCELLED        │        └─────────────────┘             │
│  └──────────────────┘                                         │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                   STRATEGY LAYER                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────────────┐  ┌────────────────────────┐    │
│  │RideMatchingStrategy <<I>>│  │ FareStrategy <<I>>     │    │
│  ├──────────────────────────┤  ├────────────────────────┤    │
│  │findDriver()              │  │ calculateFare()        │    │
│  └──────┬───────────────────┘  └────┬───────────────────┘    │
│         │                            │                        │
│    ┌────┴──────────────┐        ┌────┴────────────────┐      │
│    │                   │        │                     │      │
│    ▼                   ▼        ▼                     ▼      │
│ ┌─────────────┐   ┌──────────┐ ┌───────────┐   ┌──────────┐│
│ │NearestDriver│   │LeastActive│ │DefaultFare│   │PeakHour ││
│ │Strategy     │   │ Strategy   │ │Strategy   │   │Strategy ││
│ └─────────────┘   └──────────┘ └───────────┘   └──────────┘│
│                                                                │
└─────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                                 │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐      │
│  │RiderService  │    │DriverService │    │ RideService  │      │
│  ├──────────────┤    ├──────────────┤    ├──────────────┤      │
│  │register()    │    │register()    │    │request()     │      │
│  │getRiderById()│    │getById()     │    │assignDriver()│      │
│  │getAll()      │    │getAvailable()│    │complete()    │      │
│  │exists()      │    │updateAvail() │    │cancel()      │      │
│  │              │    │updateLocation│    │calculateFare│      │
│  │              │    │getAll()      │    │getRideById() │      │
│  │              │    │exists()      │    │getAll()      │      │
│  │              │    │              │    │setStrategy() │      │
│  └──────────────┘    └──────────────┘    └──────────────┘      │
│                              ▲                                   │
│                              │ (uses)                            │
│                              │                                   │
│                    ┌─────────┴──────────┐                       │
│                    │                    │                       │
│                    ▼                    ▼                       │
│            RideMatchingStrategy    FareStrategy                 │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                   APPLICATION LAYER                              │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌────────────────────────────────────────────────────────┐    │
│  │              Main (Console Application)                │    │
│  │  ├─ Initialize Services                               │    │
│  │  ├─ Display Menu                                      │    │
│  │  ├─ Handle User Input                                 │    │
│  │  ├─ Call Service Methods                              │    │
│  │  └─ Display Results                                   │    │
│  └────────────────────────────────────────────────────────┘    │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                   UTILITY & EXCEPTION LAYER                      │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────┐    ┌──────────────────────────────┐      │
│  │  IdGenerator     │    │NoDriverAvailableException    │      │
│  ├──────────────────┤    ├──────────────────────────────┤      │
│  │generateRiderId() │    │Exception <<extends>>         │      │
│  │generateDriverId()│    └──────────────────────────────┘      │
│  │generateRideId()  │                                           │
│  │reset()           │                                           │
│  └──────────────────┘                                           │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

## Class Descriptions

### Model Classes

#### Rider
- **Package**: com.airtribe.ridewise.model
- **Responsibility**: Represent a rider
- **Key Methods**: Constructor, getters
- **Visibility**: All fields private, public getters

#### Driver
- **Package**: com.airtribe.ridewise.model
- **Responsibility**: Represent a driver with state
- **Key Methods**: Constructor, getters, setters, incrementRidesCompleted()
- **Visibility**: All fields private, public getters/setters

#### Ride
- **Package**: com.airtribe.ridewise.model
- **Responsibility**: Represent a ride with lifecycle
- **Key Methods**: Constructor, getters, setDriver(), setStatus()
- **Visibility**: Immutable except for driver and status

#### FareReceipt
- **Package**: com.airtribe.ridewise.model
- **Responsibility**: Represent fare calculation result
- **Key Methods**: Constructor overloads, getters
- **Visibility**: Read-only after creation

#### RideStatus (Enum)
- **Package**: com.airtribe.ridewise.model
- **Values**: REQUESTED, ASSIGNED, COMPLETED, CANCELLED

#### VehicleType (Enum)
- **Package**: com.airtribe.ridewise.model
- **Values**: BIKE, AUTO, CAR

### Strategy Interfaces

#### RideMatchingStrategy
- **Package**: com.airtribe.ridewise.strategy
- **Method**: `Driver findDriver(Rider rider, List<Driver> availableDrivers)`
- **Implementations**: NearestDriverStrategy, LeastActiveDriverStrategy

#### FareStrategy
- **Package**: com.airtribe.ridewise.strategy
- **Method**: `double calculateFare(Ride ride)`
- **Implementations**: DefaultFareStrategy, PeakHourFareStrategy

### Service Classes

#### RiderService
- **Package**: com.airtribe.ridewise.service
- **Responsibility**: Rider CRUD operations
- **Key Methods**: registerRider(), getRiderById(), getAllRiders(), riderExists()

#### DriverService
- **Package**: com.airtribe.ridewise.service
- **Responsibility**: Driver management with availability tracking
- **Key Methods**: registerDriver(), getDriverById(), getAvailableDrivers(), updateAvailability(), etc.

#### RideService
- **Package**: com.airtribe.ridewise.service
- **Responsibility**: Ride orchestration and lifecycle management
- **Dependencies**: RideMatchingStrategy, FareStrategy (injected)
- **Key Methods**: requestRide(), assignDriver(), completeRide(), calculateFare(), etc.

### Application & Utility

#### Main
- **Package**: com.airtribe.ridewise
- **Responsibility**: Console UI and menu handling
- **Key Methods**: main(), initializeServices(), loadSampleData(), menu options

#### IdGenerator
- **Package**: com.airtribe.ridewise.util
- **Responsibility**: Generate unique IDs for entities
- **Key Methods**: generateRiderId(), generateDriverId(), generateRideId(), reset()

#### NoDriverAvailableException
- **Package**: com.airtribe.ridewise.exception
- **Extends**: Exception
- **Usage**: Thrown when no suitable driver found for ride

## Package Structure

```
com.airtribe.ridewise/
├── Main.java
├── model/
│   ├── Rider.java
│   ├── Driver.java
│   ├── Ride.java
│   ├── FareReceipt.java
│   ├── RideStatus.java
│   └── VehicleType.java
├── strategy/
│   ├── RideMatchingStrategy.java
│   ├── NearestDriverStrategy.java
│   ├── LeastActiveDriverStrategy.java
│   ├── FareStrategy.java
│   ├── DefaultFareStrategy.java
│   └── PeakHourFareStrategy.java
├── service/
│   ├── RiderService.java
│   ├── DriverService.java
│   └── RideService.java
├── exception/
│   └── NoDriverAvailableException.java
└── util/
    └── IdGenerator.java
```

## Dependency Graph

```
Main
 ├─> RideService
 │    ├─> RiderService
 │    ├─> DriverService
 │    ├─> RideMatchingStrategy (interface)
 │    └─> FareStrategy (interface)
 ├─> RiderService
 ├─> DriverService
 └─> Strategy implementations
      ├─> NearestDriverStrategy
      ├─> LeastActiveDriverStrategy
      ├─> DefaultFareStrategy
      └─> PeakHourFareStrategy

Utilities:
 ├─> IdGenerator (static methods)
 └─> NoDriverAvailableException
```

## Design Patterns Applied

1. **Strategy Pattern**: RideMatchingStrategy, FareStrategy
2. **Dependency Injection**: Strategies injected into RideService
3. **Service Layer**: RiderService, DriverService, RideService
4. **Repository Pattern**: Services manage collections
5. **Factory Pattern**: IdGenerator for ID creation

## SOLID Principles Mapping

| Principle | Implementation |
|-----------|-----------------|
| SRP | Each service has one responsibility |
| OCP | Can add strategies without modifying RideService |
| LSP | All strategy implementations interchangeable |
| ISP | Small focused strategy interfaces |
| DIP | RideService depends on strategy interfaces |

