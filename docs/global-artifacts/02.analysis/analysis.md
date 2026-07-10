# Domain Model

## Context

AISafe is a flight control management system. Given the complexity of the domain — involving flights,
simulations, weather, aircraft, and multiple actors — **Domain-Driven Design (DDD)** was adopted as
the primary design framework.

DDD allows us to:
- Organise the domain into cohesive, bounded contexts (aggregates)
- Enforce business rules at the model level through invariants
- Decouple aggregates so they can evolve independently

---

## Domain Model Diagrams

### Simplified Overview

![AISafe Domain Model](AISafe_DomainModel.png)

### Complete Domain Model

![AISafe Complete Domain Model](AISafe_CompleteDomainModel.png)

Individual aggregate diagrams are available in the [`Aggregates/`](Aggregates/) folder.

---

## Aggregates

The system is composed of **14 aggregates**:

| Aggregate | Aggregate Root | Identifier | Key Invariant |
|---|---|---|---|
| User | `User` | `UserID` | A disabled user cannot authenticate or perform any operation; security clearance must not be expired |
| Maker | `Maker` | `MakerID` | Must have at least one `MakerType` (`AIRCRAFT` or `ENGINE`) |
| AirControlArea | `AirControlArea` | `AirControlAreaCode` | Geographic boundary must be valid and non-empty |
| Airport | `Airport` | `IataCode` / `IcaoCode` | IATA and ICAO codes must be globally unique; must belong to exactly one `AirControlArea` |
| EngineModel | `EngineModel` | `EngineModelID` | Must reference a valid `Maker` of type `ENGINE` |
| AircraftModel | `AircraftModel` | `AircraftModelID` | Must have at least one certified engine model; `mtow` must exceed `emptyWeight`; name and manufacturer combination must be unique |
| AirTransportCompany | `AirTransportCompany` | `IataCode` / `IcaoCode` | IATA and ICAO codes must be unique across all companies |
| Aircraft | `Aircraft` | `AircraftRegistration` | Total seats in `CabinConfiguration` cannot exceed the model's `maxPassengerCapacity`; a decommissioned aircraft cannot be assigned to new flight plans |
| FlightRoute | `FlightRoute` | `RouteCode` | Departure and arrival airports must be different; an inactive route cannot have new flights created |
| Flight | `Flight` | `FlightID` | Must reference an active `FlightRoute` via `routeCode`; must have at least one `FlightPlan` |
| FlightSimulation | `FlightSimulation` | `FlightSimulationID` | Must include at least one `FlightExecution`; `SimulationReport` is only generated after simulation reaches `COMPLETED` status |
| WeatherData | `WeatherData` | `WeatherDataID` | Must be associated with a valid `AirControlArea` via `airControlAreaCode` |
| Pilot | `Pilot` | `PilotID` | An inactive pilot cannot be assigned to flight plans; must have at least one valid `Certification` |
| ATCC | `ATCC` | `ATCCID` | Must reference a valid `User` and a valid `AirTransportCompany` |

---

## Shared Kernel

The following types are shared across multiple aggregates and defined in the **Shared Kernel** to avoid
duplication and ensure consistent validation:

| Type | Description |
|---|---|
| `Coordinates` | Latitude and longitude pair — used in `WeatherData` and `SafetyViolationEvent` |
| `Country` | Identified by an `IsoCode` and a name — used in `Maker`, `Airport`, `Aircraft` |
| `IsoCode` | ISO 3166-1 alpha-2 country code (e.g. `PT`, `US`) |
| `IataCode` | 2–3 character IATA code — used by airports, airlines, and routes |
| `IcaoCode` | 4 character ICAO code — used by airports and airlines |

---

## Key Design Decisions

### 1. References between aggregates are by ID, never by direct object

Each aggregate is an independent consistency boundary. Direct object references between aggregates
would break isolation and create uncontrolled coupling. Every cross-aggregate reference is represented
by a typed Value Object ID (e.g. `AircraftRegistration`, `IataCode`, `PilotID`).

### 2. All identifiers are typed Value Objects

Instead of raw `String` or `UUID` fields, every identifier is wrapped in a dedicated Value Object
(e.g. `UserID`, `MakerID`, `EngineModelID`). This prevents accidental ID confusion across aggregates
and makes the model more expressive and type-safe at compile time.

### 3. `Maker` was unified into a single aggregate

The original design had two separate aggregates — `AircraftMaker` and `EngineMaker`. These were
merged into a single `Maker` aggregate with a `Set<MakerType>` field (`AIRCRAFT`, `ENGINE`). This
reflects real-world cases (e.g. Rolls-Royce manufactures both engines and aircraft) and avoids
duplication of identity and logic.

### 4. `Pilot` and `ATCC` are independent aggregates

Initially considered as entities inside `AirTransportCompany`, both `Pilot` and `ATCC` were promoted
to independent aggregates because:
- They have their own lifecycle (can be activated/deactivated independently of the company)
- They reference `User` and `AirTransportCompany` by ID — they are not owned by either
- `Pilot` has its own business rules (certifications per aircraft model, active status validation)
- `ATCC` has its own status (`ACTIVE`/`DISABLED`) independently managed

### 5. `FlightPlan` is an Entity inside the `Flight` aggregate

`FlightPlan` was deliberately **not** made an Aggregate Root. A flight plan only exists in the context
of a flight — it has no independent lifecycle. A `Flight` may have multiple `FlightPlan`s (e.g. if a
plan is rejected and resubmitted), but only one can be validated at a time. The `Flight` aggregate
root controls this invariant.

This follows the clarification from the Product Owner:
> *"Route 1:N Flight 1:N FlightPlan"* — the flight plan belongs to the flight, not the other way around.

### 6. `Flight` has a `FlightType` (REGULAR or CHARTER)

The type of flight determines the structure of its schedule:
- `REGULAR` flights have a `RegularSchedule` with recurring days of the week and times
- `CHARTER` flights have a `CharterSchedule` with a specific departure date and time

This is modelled using a `Schedule` abstract Value Object with two concrete subtypes.

### 7. `FlightSimulation` owns `FlightExecution` as an internal entity

Each flight within a simulation is represented by a `FlightExecution` entity, which lives inside the
`FlightSimulation` aggregate. Safety violations during the simulation are captured as
`SafetyViolationEvent` value objects, and the overall outcome is captured in a `SimulationReport`
value object. The `FlightSimulation` aggregate root controls the consistency of all of these.

### 8. `WeatherData` stores both `coordinates` and `airControlAreaCode`

Weather conditions are not uniform within an air control area (confirmed by the Product Owner).
Therefore, `coordinates` are used for precise geolocation of each weather reading. However,
`airControlAreaCode` is also stored to satisfy the functional requirements of registering (US041)
and consulting (US043) weather data by area. Both fields serve distinct purposes.

### 9. `AircraftModel` holds a `Set<EngineModelID>` for certified engines

A given aircraft model (e.g. Airbus A320) can be certified to fly with more than one engine model
(e.g. CFM56 or IAE V2500). The `certifiedEngineModelIDs` field captures this, while `engineNumber`
specifies how many engines are installed. All engines on a given aircraft are of the same model,
as clarified in US055.

### 10. `IataCode` and `IcaoCode` are in the Shared Kernel

These codes are used across multiple aggregates (`Airport`, `AirTransportCompany`, `FlightRoute`,
`Aircraft`, `Pilot`, `ATCC`). Placing them in the Shared Kernel avoids duplication and ensures
consistent format validation across the entire system.

---

## Inter-Aggregate Relationships

The following table shows how aggregates relate to each other through ID references:

| From | To | Via |
|---|---|---|
| `Airport` | `AirControlArea` | `airControlAreaCode` |
| `EngineModel` | `Maker` | `makerID` |
| `AircraftModel` | `Maker` | `makerID` |
| `Aircraft` | `AircraftModel` | `aircraftModelID` |
| `Aircraft` | `AirTransportCompany` | `airTransportCompanyIataCode` |
| `FlightRoute` | `Airport` (×2) | `departureAirportIataCode`, `arrivalAirportIataCode` |
| `FlightRoute` | `AirTransportCompany` | `airTransportCompanyIataCode` |
| `Flight` | `FlightRoute` | `routeCode` |
| `FlightPlan` (entity) | `Aircraft` | `aircraftRegistration` |
| `FlightPlan` (entity) | `Pilot` | `pilotID` |
| `FlightExecution` | `FlightPlan` | `flightPlanID` |
| `FlightSimulation` | `AirControlArea` | `airControlAreaCode` |
| `WeatherData` | `AirControlArea` | `airControlAreaCode` |
| `Pilot` | `User` | `userID` |
| `Pilot` | `AirTransportCompany` | `currentCompanyIataCode` |
| `ATCC` | `User` | `userID` |
| `ATCC` | `AirTransportCompany` | `currentCompanyIataCode` |