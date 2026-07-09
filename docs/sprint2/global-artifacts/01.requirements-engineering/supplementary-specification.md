# Supplementary Specification (FURPS+)

> **Scope:** Sprint 1 and Sprint 2 user stories only.  
> Sprint 1 UCs: US001–US003, US010–US011, US030–US033, US050–US060  
> Sprint 2 UCs: US030–US033, US041–US044, US050–US064, US070–US072, US080–US083, US085

---

## Functionality

- The system must enforce **authentication and authorisation** for all users and functionalities across all sprints (US030, NFR09). User sessions must be validated before any operation is performed.
- The system must support a **bootstrap process** to initialise base data (users, airports, air control areas, aircraft models, engine models, air transport companies) without manual UI interaction (US031, US050, US052, US055, US056, US060, US061).
- **Role-based access control** must be applied consistently: Admin, Backoffice Operator, Air Transport Company Collaborator (ATCC), Pilot, Weather Person, and Flight Control Operator each have distinct permissions (section 3.1.1).
- Users must have an **active security clearance** with an automatic expiry date, and a periodic skills assessment (every 5 years); Admins and Backoffice Operators can update this information (section 3.1.1).
- All **uniqueness constraints** on domain identifiers must be enforced by the system: IATA/ICAO airport codes (worldwide unique), airline IATA/ICAO codes, aircraft registration numbers, user email addresses, and route names (US052, US055, US056, US060, US073).
- The system must support **importing and validating flight plan files** described in the Core Flight DSL, performing lexical, syntactic, and semantic analysis before accepting any flight plan (US081, US083).
- **Bulk import of weather data** must support multiple external providers; a CSV format must be available initially, and the architecture must allow easy addition of new sources (US042).
- **Remote TCP access** must be available for Weather Person (US044), ATCC (US078), and Pilot (US086) roles. All corresponding user stories for each role must be fully accessible through the TCP client application; direct database interaction from the client is strictly forbidden.
- All **error messages** produced by the DSL parser must include error type, line number, and column number (US083, section 3.4.4).
- **Diagrams and figures** included in project documentation must be generated with PlantUML and stored in the repository as both PlantUML source files and PNG images (NFR02).

---

## Usability

- The backoffice **graphical interface** must be developed in JavaFX 11.
- The **TCP client applications** (Weather Person, ATCC, Pilot) must present clear and informative feedback for all operations, including error conditions.
- The **DSL parser** must produce meaningful, human-readable error messages — specifying error type, line, and column — so that users can quickly identify and correct invalid flight plan files (US081, US083).
- All **technical documentation** must be maintained in the project repository under the `docs` folder in Markdown format, following UML notation where applicable, and kept up to date throughout the project (NFR02).
- The system must provide **list and search functionality** for backoffice entities (users, collaborators, fleet, pilot roster) with filtering options as specified in individual user stories (US033, US062, US072a–US072d, US076).

---

## Reliability

- **Business rule validation** must be enforced at every create and update operation. Key rules include:
    - An aircraft model must always retain at least one certified engine model (US058).
    - An aircraft cannot be decommissioned if it has pending flights (US071).
    - A pilot cannot be deactivated if they have flight plans assigned (US077).
    - A flight route cannot be deleted if planned flights exist after the specified date (US074).
    - The same engine model cannot be added twice to the same aircraft model (US057).
    - Engine type compatibility must be verified when associating an engine model to an aircraft model (US057).
- The system must support **two persistence modes**, configurable without code changes: in-memory (for development and testing) and a remote relational database (for production deployment) (NFR08).
- **Any commit** to the repository must leave the system in a valid state — compiling successfully and passing all tests. Failure to comply results in a zero grade for that sprint in LAPR4 (NFR06).
- **Test coverage** of Java domain and controller packages must not fall below 90% at any point during development (NFR03).
- The **DSL semantic validation** must collect and report all errors in a single execution pass, rather than halting at the first error found (section 3.4.5).
- Only **valid flight plans** (passing lexical, syntactic, and semantic validation) may be imported and used by the system (US081).

---

## Performance

- The system must be designed to support **scalability** for future simulation phases; the architecture established in Sprints 1 and 2 must not introduce bottlenecks that would prevent parallelisation in Sprint 3 (section 3, scalability requirement).
- **Remote TCP client operations** must not introduce unacceptable latency; all Weather Person, ATCC, and Pilot user stories must be responsive over the TCP connection (US044, US078, US086).

---

## Supportability

- The codebase must follow **Object-Oriented best practices** and Domain-Driven Design (DDD), using the aggregate model established in US010 and US011 as the foundation for all implementations.
- All Java code must include **Javadoc comments** for classes and public methods (NFR02).
- **Unit tests** must be implemented for all methods (excluding I/O methods) using the JUnit 5 framework, following the AAA (Arrange-Act-Assert) convention. Coverage reports must be generated with the JaCoCo plugin (NFR03).
- The project must use **Maven** as the build automation tool, with a clean build lifecycle applicable to all modules (NFR05).
- **Continuous integration** must be configured via GitHub Actions/Workflows, with automated nightly builds, test execution, and publication of results and metrics (US004, NFR05).
- The repository must include **scripts** for building, deploying, and running the solution on Unix/Linux systems, as well as a `README.md` at the root explaining all major tasks (US005, NFR07).
- The **DSL architecture** (ANTLR grammar, visitor/listener implementations, internal representation) must be designed to allow straightforward language extensions without breaking the core specification (section 3.4.7).
- Development process artefacts for each user story (analysis, design, tests, implementation notes) must be **documented in the repository** and kept current (NFR02).
- The **weather data import** architecture must be designed so that adding new data source formats requires minimal changes — only a new adapter/parser, not modifications to core logic (US042).

---

## Others +

### Design Constraints

- **Scrum** methodology must be used for project management, with a weekly Scrum meeting with the Scrum Master (LAPR4 PL teacher) (NFR01).
- The project must adopt **Domain-Driven Design (DDD)**, with a Domain Model (US010) and per-aggregate justification via sequence diagrams demonstrating invariant enforcement (US011).
- The development process must be **iterative and incremental**, delivering working software at each sprint boundary (Table 1, section 2.2).
- **PlantUML** must be used to generate all UML diagrams; both source files and PNG exports must be committed to the repository (NFR02).
- The **Core Flight DSL** must be processed using **ANTLR**, with a formal grammar defining lexer and parser rules, at least one visitor and one listener implementation, and production of an internal representation (AST or domain objects) (section 3.4.4).
- GitHub repository commits must be **regular and frequent** (ideally daily), as commit history and issue tracking will be used as evidence of continuous work in assessments (NFR04, section 2.1).
- Each **team member** must participate in tasks spanning knowledge from all enrolled course units; no member may restrict themselves to a single course unit's tasks (section 2.2).

---

### Implementation Constraints

- The primary implementation language is **Java** (NFR10).
- The **backoffice graphical interface** must be implemented in JavaFX 11.
- The **Core Flight DSL parser** must be implemented in Java using **ANTLR**, including at minimum one visitor and one listener, and must produce an internal representation (section 3.4.4).
- The **flight plan test/validation component** (US085) must include a part implemented in the **C language**.
- The build tool is **Maven** (NFR05).
- **Test coverage** of Java domain and controller packages must not fall below 90% (NFR03).
- Remote access for Weather Person, ATCC, and Pilot must be implemented via a dedicated **TCP client application**; direct database access from the client is not acceptable (US044, US078, US086).
- The system must support both **in-memory and relational database** persistence, switchable by configuration (NFR08).

---

### Interface Constraints

- The **TCP client applications** for remote access (US044, US078, US086) must communicate exclusively through the TCP connection to the embedded server; no direct database interaction is permitted.
- The **bulk weather data import** (US042) must support a CSV format as the baseline and be extensible to additional external provider formats without core system changes.
- The **Core Flight DSL** is defined and generated by an external specialised tool; the system's responsibility is solely to import, validate, and process the DSL files — not to generate them (section 3, vendor lock-in requirement).
- The **aircraft physics model** (lift, drag, thrust, fuel consumption) as defined in section 3.3 must be the basis for flight plan validation and testing (US085), ensuring consistency between the domain model and physics calculations.

---

### Physical Constraints

N/A — No specific hardware constraints apply to Sprint 1 or Sprint 2 deliverables.