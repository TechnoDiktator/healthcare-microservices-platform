# 🏥 Healthcare Microservices Platform

This repository contains a distributed healthcare backend built with Spring Boot, gRPC, Kafka, PostgreSQL, MongoDB, and Docker. The services are organized around separate business domains and communicate through REST APIs, synchronous gRPC calls, and asynchronous Kafka events.

---

## 📖 Overview

The project demonstrates a realistic microservices setup for:

- authentication and authorization
- patient management
- doctor management
- billing operations
- analytics and event-driven reporting

The system is designed so each service can evolve independently while still cooperating through clear integration boundaries.

---

## 🚀 Why You Should Hire Me

This project demonstrates my ability to design and deliver an enterprise-grade distributed system with strong operational quality.

### What I built

- Designed and implemented a microservices architecture with Spring Boot, REST, gRPC, Kafka, PostgreSQL, MongoDB, and Docker.
- Built a centralized API Gateway with JWT-based routing and authentication enforcement.
- Implemented event-driven analytics using Kafka topics and MongoDB projection storage for reporting.
- Developed polyglot persistence models: relational storage for auth/patient/billing and document storage for doctor/analytics data.
- Added repeatable seeded test data across services for demo-ready environments and integration testing.
- Included containerized deployment, health checks, logging guidance, and operational commands for service lifecycle management.

### Why this matters

- Demonstrates end-to-end system design for scalable distributed services.
- Shows practical experience with asynchronous event processing and read-model projection.
- Highlights cross-service communication, fault isolation, and independent service evolution.
- Validates strong infrastructure automation and developer-friendly startup workflows.

---

## API Testing

The complete Postman collection is available here:

📁 [Postman Collection](api-requests/postman_collection/SpBootGrpcProject.postman_collection.json)

Import the collection into Postman and update the JWT token variables after logging in.

## 🏗️ Architecture

The system is organized as a layered microservices architecture with a central gateway, domain services, event streaming, and separate data stores.

### High-level architecture

```mermaid
flowchart TD
    Client[Client / Postman / Web App]
    Gateway[API Gateway<br/>Spring Cloud Gateway]
    Auth[Auth Service<br/>JWT + Auth]
    Patient[Patient Service<br/>Patient CRUD + workflows]
    Doctor[Doctor Service<br/>Doctor CRUD + workflows]
    Billing[Billing Service<br/>Billing records + invoices]
    Analytics[Analytics Service<br/>Event-driven reporting]

    DBAuth[(PostgreSQL<br/>auth_db)]
    DBPatient[(PostgreSQL<br/>patient_db)]
    DBDoc[(MongoDB<br/>doctor_db)]
    DBBilling[(PostgreSQL<br/>billing_db)]
    DBAnalytics[(MongoDB<br/>analytics data)]

    Kafka[Kafka Broker<br/>Event Streaming]

    Client --> Gateway
    Gateway --> Auth
    Gateway --> Patient
    Gateway --> Doctor

    Patient --> Doctor
    Patient --> Billing
    Doctor --> Billing
    Billing --> Kafka
    Patient --> Kafka
    Doctor --> Kafka

    Auth --> DBAuth
    Patient --> DBPatient
    Doctor --> DBDoc
    Billing --> DBBilling
    Analytics --> DBAnalytics

    Kafka --> Analytics
```

### Service interaction flow

```mermaid
sequenceDiagram
    actor User
    participant Gateway as API Gateway
    participant Auth as Auth Service
    participant Patient as Patient Service
    participant Doctor as Doctor Service
    participant Billing as Billing Service
    participant Analytics as Analytics Service
    participant Kafka as Kafka Broker

    User->>Gateway: REST request
    Gateway->>Auth: Validate token / auth check
    Auth-->>Gateway: Auth success
    Gateway->>Patient: Forward patient request
    Patient->>Doctor: gRPC doctor validation
    Patient->>Billing: gRPC billing request
    Patient->>Kafka: Publish patient event
    Billing->>Kafka: Publish billing event
    Doctor->>Kafka: Publish doctor event
    Kafka-->>Analytics: Consume domain events
    Analytics-->>Analytics: Update reporting model
    Patient-->>Gateway: Response
    Gateway-->>User: Final response
```

### Data and messaging view

```mermaid
flowchart LR
    subgraph DomainServices[Domain Services]
        P[Patient Service]
        D[Doctor Service]
        B[Billing Service]
    end

    subgraph Storage[Storage Layer]
        PG[(PostgreSQL)]
        MG[(MongoDB)]
    end

    subgraph Integration[Integration Layer]
        K[Kafka]
        A[Analytics Service]
    end

    P --> PG
    B --> PG
    D --> MG
    P --> K
    D --> K
    B --> K
    K --> A
    A --> MG
```

### How the services interact

- The API Gateway serves as the single entry point for all client requests.
- The Auth Service handles authentication and identity validation for protected routes.
- The Patient Service manages patient lifecycle operations and invokes downstream services over gRPC.
- The Doctor Service manages doctor-related domain operations and participates in cross-service validation flows.
- The Billing Service handles billing records and publishes domain events after business operations.
- The Analytics Service consumes Kafka events to maintain reporting and analytics data without direct coupling to the producing services.

---

## 🧩 Microservices

| Service | Primary responsibility | Primary data store | Communication style |
| --- | --- | --- | --- |
| API Gateway | Central entry point and request routing | None | REST routing, JWT filtering |
| Auth Service | Authentication, authorization, and token validation | PostgreSQL | REST |
| Patient Service | Patient CRUD, validation, and workflow coordination | PostgreSQL | REST, gRPC, Kafka |
| Doctor Service | Doctor profile management and prescription workflows | MongoDB | REST, gRPC, Kafka |
| Billing Service | Billing generation, invoices, and payment lifecycle | PostgreSQL | REST, gRPC, Kafka |
| Analytics Service | Event-driven projection and reporting read models | MongoDB | Kafka ingestion, REST read API |

---

## 🔄 Communication Patterns

### REST
Used for client-facing APIs and gateway routing.

### gRPC
Used for internal service-to-service calls such as:

- patient validation
- doctor validation
- billing creation

### Kafka
Used for asynchronous event propagation between services.

- The Analytics Service listens to Kafka topics published by the other services and persists projection documents into MongoDB.
- Patient events are published to `patient-events`, doctor events to `doctor-events`, billing events to `billing-events`, and prescription events to `prescription-events`.
- The Analytics Service deserializes protobuf event payloads and stores patient, doctor, billing, and prescription projections for reporting.

---

## 🗂️ Project Structure

```text
patient-management/
├── api-gateway/
├── auth-service/
├── patient-service/
├── doctor-service/
├── billing-service/
├── analytics-service/
├── Integration-tests/
├── docker-compose.yml
└── Readme.md
```

---

## 🐳 Infrastructure and Runtime

The project uses Docker Compose to start the core infrastructure and service containers.

- PostgreSQL databases for auth, patient, and billing services.
- MongoDB for doctor service data and analytics projections.
- Kafka broker for asynchronous event streaming.
- All Spring Boot services are containerized and joined on a Docker network.
- Each service exposes Spring Boot Actuator endpoints for health and operational metrics.

### Docker Compose dependency graph

```mermaid
flowchart TD
    AuthDB[auth-service-db]
    PatientDB[patient-service-db]
    BillingDB[billing-service-db]
    DoctorMongo[doctor-mongodb]
    AnalyticsMongo[analytics-mongo-db]
    Kafka[kafka]
    Auth[auth-service]
    Patient[patient-service]
    Billing[billing-service]
    Doctor[doctor-service]
    Analytics[analytics-service]
    Gateway[api-gateway]

    AuthDB --> Auth
    PatientDB --> Patient
    BillingDB --> Billing
    DoctorMongo --> Doctor
    AnalyticsMongo --> Analytics
    Kafka --> Billing
    Kafka --> Patient
    Kafka --> Doctor
    Kafka --> Analytics
    Auth --> Doctor
    Patient --> Doctor
    Billing --> Doctor
    Auth --> Gateway
```

This dependency graph reflects the Docker Compose `depends_on` relationships and the service startup order:

- `auth-service` waits for `auth-service-db`.
- `patient-service` waits for `patient-service-db` and `kafka`.
- `billing-service` waits for `billing-service-db` and `kafka`.
- `doctor-service` waits for `doctor-mongodb`, `kafka`, `auth-service`, `patient-service`, and `billing-service`.
- `analytics-service` waits for `analytics-mongo-db` and `kafka`.
- `api-gateway` waits for `auth-service`.

The graph helps show which services depend on database or messaging infrastructure before becoming healthy.

The analytics stack now persists event projections into MongoDB, allowing the Analytics Service to provide read models for:

- patient activity and registration history
- doctor profiles and availability
- billing and payment summaries
- prescription details and medication records

The application also includes seeded test data across multiple services:

- `auth-service` seeds an admin user via `AdminSeeder.java` and adds sample doctor accounts via `auth-service/src/main/resources/data.sql`.
- `patient-service` inserts initial patient records from `patient-service/src/main/resources/data.sql`.
- `doctor-service` seeds doctor profiles in `DoctorDataSeeder.java` using Spring Boot `CommandLineRunner`.
- `billing-service` inserts billing records from `billing-service/src/main/resources/data.sql`.

A typical startup flow is:

1. Start the infrastructure with Docker Compose.
2. Start the microservices.
3. Route client requests through the API Gateway.
4. Let the services publish events to Kafka.
5. The Analytics Service consumes events and persists projection documents into MongoDB.

---

## 🚀 Running the Project

From the repository root, start the infrastructure and services with Docker Compose:

```bash
docker compose up --build
```

If you want to run it in detached mode:

```bash
docker compose up --build -d
```

To stop the application and remove containers:

```bash
docker compose down
```

To rebuild a single service after changes:

```bash
docker compose build <service-name>
```

To list the service containers started by Docker Compose:

```bash
docker compose ps
```

To view logs for all services:

```bash
docker compose logs -f
```

To view logs for a specific service:

```bash
docker compose logs -f <service-name>
```

To restart a specific service:

```bash
docker compose restart <service-name>
```

To remove stopped containers, networks, and volumes created by compose:

```bash
docker compose down --volumes
```

To inspect container health via Actuator endpoints from inside the container:

```bash
docker compose exec <service-name> sh
curl -f http://localhost:<service-port>/actuator/health
```

Each Spring Boot service exposes Actuator health and info endpoints, so you can verify runtime health directly from the container.

Then access the services through the gateway and service-specific ports defined in the Docker Compose setup.

Example entry points:

- API Gateway: http://localhost:4004
- Auth Service: http://localhost:4005
- Patient Service: http://localhost:4000
- Doctor Service: http://localhost:8080
- Billing Service: http://localhost:4001
- Analytics Service: http://localhost:4002

Use the gateway for most client requests so authentication, routing, and JWT handling happen consistently.

---

## 🛠️ Technology Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Cloud Gateway
- REST APIs
- gRPC
- Apache Kafka
- PostgreSQL
- MongoDB
- Docker / Docker Compose
- Maven

---

## REST API Reference

The gateway exposes the public entry points, and the individual services expose their own REST endpoints underneath.

### Auth Service

Base path: /auth (through gateway) or / on the service itself

| Method | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| POST | /auth/login | Authenticate a user and return a token | Public |
| GET | /auth/validate | Validate a bearer token | Required |
| POST | /auth/register | Register a new user account | Public |

### Internal Auth Management

| Method | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| POST | /internal/users | Create an internal user | Internal |
| PUT | /internal/users/{id} | Update an internal user | Internal |
| DELETE | /internal/users/{id} | Delete an internal user | Internal |

### Patient Service

Base path: /api/patients (through gateway) or /patients on the service

| Method | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| GET | /api/patients | Get all patients | ADMIN, STAFF, DOCTOR |
| POST | /api/patients | Create a patient | ADMIN, STAFF, DOCTOR |
| PUT | /api/patients/{id} | Update a patient | ADMIN, STAFF, DOCTOR |
| DELETE | /api/patients/delete/{id} | Delete a patient | ADMIN, STAFF, DOCTOR |
| GET | /api/patients/{id}/recommended-doctors | Get doctors recommended for a patient condition | ADMIN, STAFF, DOCTOR |
| GET | /api/patients/random | Fetch a random patient | ADMIN, STAFF, DOCTOR |
| GET | /api/patients/{patientId}/bills/{billId} | Retrieve a patient bill | ADMIN, PATIENT |
| PUT | /api/patients/{patientId}/bills/{billId}/pay | Pay a patient bill | ADMIN, PATIENT |

### Doctor Service

Base path: /api/doctors (through gateway) or /doctors on the service

| Method | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| POST | /api/doctors | Create a doctor | ADMIN |
| GET | /api/doctors/{id} | Get a doctor by ID | Authenticated |
| GET | /api/doctors | Get all doctors | Authenticated |
| PUT | /api/doctors/{id} | Update a doctor | ADMIN |
| DELETE | /api/doctors/{id} | Delete a doctor | ADMIN |
| GET | /api/doctors/specialization/{specialization} | Get doctors by specialization | Authenticated |
| GET | /api/doctors/recommend | Recommend doctors by specialization | Authenticated |
| POST | /api/doctors/{doctorId}/prescriptions | Create a prescription for a doctor | Authenticated |
| GET | /api/doctors/prescriptions/{prescriptionId} | Get a prescription by ID | Authenticated |
| GET | /api/doctors/{doctorId}/prescriptions | Get prescriptions for a doctor | Authenticated |
| GET | /api/doctors/patients/{patientId}/prescriptions | Get prescriptions for a patient | Authenticated |
| DELETE | /api/doctors/prescriptions/{prescriptionId} | Delete a prescription | Authenticated |

---

## 🔧 gRPC API Reference

The services also communicate internally using gRPC contracts defined in the proto files.

### Billing Service gRPC

Service name: BillingService

| RPC | Request | Response | Purpose |
| --- | --- | --- | --- |
| GenerateBill | GenerateBillRequest | GenerateBillResponse | Create a billing record from prescription data |
| GetBill | GetBillRequest | BillResponse | Retrieve a bill by ID |
| PayBill | PayBillRequest | PaymentResponse | Mark a bill as paid |

#### Message shapes

- GenerateBillRequest: prescriptionId, patientId, doctorId, consultationFee, medicines[]
- GenerateBillResponse: billId, totalAmount, paymentStatus
- GetBillRequest: billId
- BillResponse: billId, prescriptionId, patientId, doctorId, consultationFee, medicineCost, totalAmount, paymentStatus
- PayBillRequest: billId
- PaymentResponse: billId, paymentStatus

### Doctor Service gRPC

Service name: DoctorServices

| RPC | Request | Response | Purpose |
| --- | --- | --- | --- |
| GetDoctorsBySpecialization | DoctorSpecializationRequest | DoctorListResponse | Return doctors matching a specialization |

#### Message shapes

- DoctorSpecializationRequest: specialization
- DoctorListResponse: doctors[]
- DoctorResponse: id, first_name, last_name, email, specialization, phone_number, qualification, experience

### Patient Service gRPC

Service name: PatientService

| RPC | Request | Response | Purpose |
| --- | --- | --- | --- |
| GetPatientById | GetPatientRequest | PatientResponse | Fetch a patient by ID |
| GetRandomPatient | Empty | PatientResponse | Return a random patient record |

#### Message shapes

- GetPatientRequest: patient_id
- PatientResponse: id, name, email, address, date_of_birth

---
##  What this project demonstrates

This repository is a good example of:

- service decomposition
- inter-service communication
- event-driven architecture
- polyglot persistence
- container-based deployment
- distributed backend design

---

## 🤝 Notes

The current implementation uses an API Gateway, authentication service, patient/doctor/billing services, and an analytics service. The repository also contains an Integration-tests module for end-to-end verification.
