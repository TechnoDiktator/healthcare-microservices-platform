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

| Service | Primary responsibility | Data store | Communication style |
| --- | --- | --- | --- |
| API Gateway | Central entry point for incoming requests | None | REST routing |
| Auth Service | Authentication, authorization, and token validation | PostgreSQL | REST |
| Patient Service | Patient CRUD and patient lifecycle workflows | PostgreSQL | REST, gRPC, Kafka |
| Doctor Service | Doctor management and doctor-related workflows | MongoDB | REST, gRPC, Kafka |
| Billing Service | Billing generation and billing records | PostgreSQL | REST, gRPC, Kafka |
| Analytics Service | Consumes events and builds analytics/read models | MongoDB | Kafka |

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

The Analytics Service listens to business events published by the other services so it can maintain reporting data without tight coupling.

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

The project uses Docker Compose to start the core infrastructure:

- PostgreSQL databases for auth, patient, and billing
- MongoDB for doctor and analytics data
- Kafka for event streaming
- All Spring Boot services as containerized apps

A typical startup flow is:

1. Start the infrastructure with Docker Compose.
2. Start the microservices.
3. Route requests through the API Gateway.
4. Let the services publish events to Kafka for analytics consumption.

---

## 🚀 Running the Project

From the repository root:

```bash
docker compose up --build
```

Then access the services through the gateway and service-specific ports defined in the Docker Compose setup.

Example entry points:

- API Gateway: http://localhost:8080
- Auth Service: http://localhost:8081
- Patient Service: http://localhost:4000
- Doctor Service: http://localhost:8082
- Billing Service: http://localhost:8083

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

## 🎯 What this project demonstrates

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
