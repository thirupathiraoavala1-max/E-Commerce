# 🛒 E-Commerce Microservices

A production-style **Spring Boot Microservices** application built using **Spring Boot 3**, **Spring Cloud**, **MongoDB**, **PostgreSQL/MySQL**, **Spring Cloud Gateway**, **Eureka Service Discovery**, and **Docker Testcontainers**.

This project demonstrates a real-world microservices architecture where each service owns its own database and communicates using REST APIs.

---

## 📌 Architecture

```text
                        Client
                           │
                           ▼
                 ┌──────────────────┐
                 │   API Gateway    │
                 └──────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
        ▼                  ▼                  ▼
 ┌──────────────┐   ┌──────────────┐   ┌──────────────┐
 │Product Service│   │Order Service │   │Inventory Svc │
 └──────────────┘   └──────────────┘   └──────────────┘
        │                  │                  │
        │                  └──────────────┐   │
        │                                 ▼   │
        │                        WebClient Call│
        │                                     │
        ▼                                     ▼
    MongoDB                           PostgreSQL/MySQL

                    ▲
                    │
          Eureka Service Discovery
```

---

# 🚀 Features

- Spring Boot 3
- Spring Cloud
- Microservices Architecture
- Spring Cloud Gateway
- Eureka Service Discovery
- MongoDB
- PostgreSQL/MySQL
- Spring Data JPA
- Spring Data MongoDB
- REST APIs
- WebClient Communication
- DTO Pattern
- Repository Pattern
- Layered Architecture
- Docker Testcontainers
- Lombok
- Maven Multi Module Project

---

# 📂 Project Structure

```
microservices-parent
│
├── api-gateway
├── discovery-server
├── inventory-service
├── order-service
├── product-service
└── pom.xml
```

---

# 🛍 Product Service

The Product Service manages the product catalog using MongoDB.

### Responsibilities

- Create Product
- Retrieve Products
- Store Product Information

### Tech Stack

- Spring Boot
- Spring Data MongoDB
- MongoDB
- Lombok

### Product Model

| Field | Type |
|-------|------|
| id | String |
| name | String |
| description | String |
| price | BigDecimal |

### REST APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/product` | Create Product |
| GET | `/api/product` | Get All Products |

### Sample Request

```json
POST /api/product

{
    "name":"iPhone 13",
    "description":"Apple iPhone 13",
    "price":1200
}
```

---

# 📦 Inventory Service

The Inventory Service checks product stock availability.

### Responsibilities

- Maintain Inventory
- Check Stock
- Support Multiple SKU Lookup

### Model

| Field | Type |
|-------|------|
| id | Long |
| skuCode | String |
| quantity | Integer |

### REST API

| Method | Endpoint |
|---------|----------|
| GET | `/api/inventory?skuCode=iphone13&skuCode=iphone14` |

### Example Response

```json
[
  {
    "skuCode":"iphone13",
    "inStock":true
  },
  {
    "skuCode":"iphone14",
    "inStock":false
  }
]
```

---

# 🛒 Order Service

The Order Service creates customer orders after verifying inventory.

### Responsibilities

- Place Order
- Generate Order Number
- Verify Inventory
- Save Order

### REST API

| Method | Endpoint |
|---------|----------|
| POST | `/api/order` |

### Flow

```
Client

↓

Order Service

↓

Inventory Service

↓

Inventory Available?

↓

YES → Save Order

NO → Reject Order
```

---

# 🔄 Service Communication

The Order Service communicates with the Inventory Service using **Spring WebClient**.

### Why WebClient?

- Reactive HTTP Client
- Non-blocking
- Better Performance
- Modern replacement for RestTemplate

---

# 🌐 Eureka Service Discovery

Every microservice registers with Eureka.

Instead of hardcoding URLs:

```
http://localhost:8082
```

services communicate using

```
http://inventory-service
```

Eureka automatically resolves the service instance.

### Benefits

- Dynamic Service Discovery
- Load Balancing
- Fault Tolerance
- Multiple Service Instances

---

# 🚪 API Gateway

Spring Cloud Gateway acts as the single entry point for all client requests.

Example Route

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/api/product/**
```

### Responsibilities

- Request Routing
- Authentication
- Load Balancing
- SSL Termination
- Centralized API Access

---

# 🧪 Testing

The Product Service includes Integration Testing using **Testcontainers**.

### Technologies

- JUnit 5
- MockMvc
- MongoDB Testcontainer
- ObjectMapper

Benefits

- Real MongoDB Container
- Automated Integration Testing
- No Manual Database Setup

---

# 🛠 Technologies Used

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Cloud | Microservices |
| Spring Data JPA | Database Access |
| Spring Data MongoDB | MongoDB Integration |
| MongoDB | Product Database |
| PostgreSQL / MySQL | Order & Inventory Database |
| Spring Cloud Gateway | API Gateway |
| Eureka Server | Service Discovery |
| WebClient | Service Communication |
| Docker | Containers |
| Testcontainers | Integration Testing |
| Maven | Build Tool |
| Lombok | Boilerplate Reduction |

---

# ▶️ Running the Project

## Clone Repository

```bash
git clone https://github.com/<your-username>/E-Commerce.git
cd E-Commerce
```

## Start Services

Run the services in the following order:

1. Discovery Server
2. API Gateway
3. Inventory Service
4. Product Service
5. Order Service

---

# 📌 API Summary

| Service | Endpoint |
|----------|----------|
| Product | `POST /api/product` |
| Product | `GET /api/product` |
| Inventory | `GET /api/inventory` |
| Order | `POST /api/order` |

---

# 📖 Concepts Covered

- Microservices Architecture
- Layered Architecture
- REST APIs
- Spring Boot
- Spring Cloud
- Eureka Service Discovery
- Spring Cloud Gateway
- DTO Pattern
- Repository Pattern
- Builder Pattern
- Dependency Injection
- WebClient
- MongoDB
- PostgreSQL
- Integration Testing
- Docker Testcontainers

---

# 🚀 Future Enhancements

- Kafka Event-Driven Communication
- JWT Authentication
- Keycloak Integration
- Config Server
- Circuit Breaker (Resilience4j)
- Distributed Tracing (Zipkin)
- Prometheus & Grafana
- Docker Compose
- Kubernetes Deployment
- Jenkins CI/CD
- AWS Deployment

---

## 👨‍💻 Author

**Thirupathi Rao Avala**

Java Backend Developer

**Tech Stack:** Java • Spring Boot • Spring Cloud • Microservices • Kafka • MongoDB • PostgreSQL • Docker • AWS
