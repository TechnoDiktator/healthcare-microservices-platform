# 🏥 Healthcare Microservices Platform

> A production-inspired distributed healthcare backend built with **Spring Boot**, **gRPC**, **Apache Kafka**, **Protocol Buffers**, **PostgreSQL**, **MongoDB**, and **Docker**.

---

# 📖 Overview

Healthcare systems typically consist of multiple independent domains such as patient management, doctor management, billing, prescriptions, and analytics. Rather than implementing these as a single monolithic application, this project demonstrates how they can be designed as independently deployable microservices communicating through synchronous and asynchronous channels.

The project emphasizes **distributed systems concepts**, **event-driven architecture**, and **production-oriented backend engineering** instead of simple CRUD operations.

---

# ✨ Features

* Microservice Architecture
* RESTful APIs
* gRPC-based inter-service communication
* Apache Kafka event streaming
* Protocol Buffers for serialization
* Event-driven Analytics Service
* PostgreSQL & MongoDB (Polyglot Persistence)
* Dockerized Services
* Domain-driven service separation
* Structured exception handling
* Production-style logging

---

# 🏗️ System Architecture

```text
                           Client
                              │
                              ▼
                     REST API Requests
                              │
        ┌─────────────────────────────────────┐
        │                                     │
        ▼                                     ▼
 Patient Service                      Doctor Service
        │                                     │
        └──────────────┐──────────────────────┘
                       ▼
               Prescription Service
                       │
                gRPC Billing Request
                       ▼
                 Billing Service
                       │
                Publish Kafka Events
                       ▼
                 Apache Kafka Broker
                       ▼
               Analytics Service
                       │
                 MongoDB Analytics
```

---

# 🧩 Microservices

## 👤 Patient Service

Responsibilities

* Register patients
* Update patient information
* Delete patients
* Publish patient lifecycle events

Database

* PostgreSQL

Published Events

* PATIENT_CREATED
* PATIENT_UPDATED
* PATIENT_DELETED

---

## 👨‍⚕️ Doctor Service

Responsibilities

* Register doctors
* Manage doctor profiles
* Validate specialization
* Publish doctor events

Database

* MongoDB

Published Events

* DOCTOR_CREATED
* DOCTOR_UPDATED
* DOCTOR_DELETED

---

## 💳 Billing Service

Responsibilities

* Generate consultation bills
* Calculate billing information
* Maintain billing records
* Publish billing events

Database

* PostgreSQL

Published Events

* BILL_CREATED
* BILL_UPDATED
* BILL_DELETED

---

## 📋 Prescription Service

Responsibilities

* Validate patients
* Validate doctors
* Verify specialization
* Generate bills through gRPC
* Create prescriptions
* Publish prescription events

Database

* MongoDB

Published Events

* PRESCRIPTION_CREATED
* PRESCRIPTION_DELETED

---

## 📊 Analytics Service

Responsibilities

* Consume Kafka events
* Maintain analytics read models
* Store denormalized reporting data
* Serve analytics endpoints

Database

* MongoDB

Consumes

* patient-events
* doctor-events
* billing-events
* prescription-events

---

# 🔄 Communication Patterns

## REST

Used for client-to-service communication.

Examples

* Create Patient
* Create Doctor
* Create Prescription

---

## gRPC

Used for synchronous communication between internal services.

Examples

* Validate Patient
* Validate Doctor
* Generate Bill

Benefits

* High performance
* Contract-first APIs
* Efficient binary serialization

---

## Apache Kafka

Used for asynchronous communication.

Each service publishes domain events after successful business operations.

Topics

* patient-events
* doctor-events
* billing-events
* prescription-events

The Analytics Service consumes these events to maintain reporting data without tightly coupling itself to the producer services.

---

# 📦 Technology Stack

| Category          | Technology          |
| ----------------- | ------------------- |
| Language          | Java 21             |
| Framework         | Spring Boot         |
| Communication     | REST, gRPC          |
| Messaging         | Apache Kafka        |
| Serialization     | Protocol Buffers    |
| Databases         | PostgreSQL, MongoDB |
| Build Tool        | Maven               |
| Containerization  | Docker              |
| API Documentation | Swagger / OpenAPI   |

---

# 📁 Project Structure

```text
healthcare-microservices-platform
│
├── auth-service
├── patient-service
├── doctor-service
├── prescription-service
├── billing-service
├── analytics-service
│
├── docker-compose.yml
│
└── README.md
```

---

# 🔁 Event Flow

## Creating a Prescription

```text
Client

↓

Prescription Service

↓

Validate Patient (gRPC)

↓

Validate Doctor (gRPC)

↓

Generate Bill (gRPC)

↓

Save Prescription

↓

Publish PRESCRIPTION_CREATED Event

↓

Apache Kafka

↓

Analytics Service

↓

Update Analytics Database
```

---

# 🚀 Running the Project

## Clone Repository

```bash
git clone https://github.com/<your-username>/healthcare-microservices-platform.git
```

---

## Start Infrastructure

```bash
docker compose up
```

---

## Start Services

Run each Spring Boot service.

* Auth Service
* Patient Service
* Doctor Service
* Billing Service
* Prescription Service
* Analytics Service

---

## Access Swagger

Each microservice exposes Swagger/OpenAPI documentation.

Example

```
http://localhost:<port>/swagger-ui/index.html
```

---

# 📈 Future Enhancements

* Distributed Tracing using OpenTelemetry
* Jaeger Integration
* Prometheus Metrics
* Grafana Dashboards
* Spring Boot Actuator
* Correlation IDs
* Resilience4j (Retry & Circuit Breaker)
* Kubernetes Deployment
* GitHub Actions CI/CD

---

# 🎯 Learning Objectives

This project was built to explore modern backend engineering concepts commonly used in distributed production systems, including:

* Service decomposition
* Event-driven architecture
* Asynchronous messaging
* High-performance service-to-service communication
* Polyglot persistence
* Domain isolation
* Scalable backend design

---

# 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

Feel free to open an issue or submit a pull request.

---

# 📄 License

This project is intended for educational and portfolio purposes.
