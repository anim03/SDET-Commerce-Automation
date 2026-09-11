# SDET Commerce Automation — Complete Learning Notes

> These notes document the complete journey of building the SDET Commerce Automation project from scratch.
>
> Goal: Understand not only **what was implemented**, but also **why it was implemented, how the code flows, how it is tested, and how to explain it in an interview**.

---

# PART 1 — PROJECT FOUNDATION

## 1. What Are We Building?

We are building a realistic e-commerce application together with a complete Quality Engineering automation ecosystem.

This is NOT only an automation repository.

The project contains a real backend application, database, authentication, authorization, business workflows, API automation, database testing, reporting and API documentation.

Current high-level architecture:

```text
                     SDET COMMERCE PLATFORM

                  ┌─────────────────────┐
                  │   React Frontend    │
                  │      PLANNED        │
                  └──────────┬──────────┘
                             │
                             │ HTTP / REST
                             ▼
                  ┌─────────────────────┐
                  │ Spring Boot Backend │
                  │      Java 17        │
                  └──────────┬──────────┘
                             │
                             │ JPA / Hibernate
                             ▼
                  ┌─────────────────────┐
                  │     PostgreSQL      │
                  └─────────────────────┘


                     TESTING LAYER

             ┌─────────────────────────┐
             │ REST Assured + TestNG   │
             │     API Automation      │
             └────────────┬────────────┘
                          │
                ┌─────────┼─────────┐
                │         │         │
                ▼         ▼         ▼
              REST      JSON     PostgreSQL
              APIs     Schema       JDBC
                                  Validation
```

Later:

```text
React + TypeScript
        ↓
Playwright + TypeScript
        ↓
UI + API Hybrid Testing
        ↓
GitHub Actions
        ↓
k6 Performance Testing
        ↓
AWS Deployment
```

---

# 2. Why Did We Build This Project?

As an SDET, knowing Selenium, Playwright or REST Assured alone is not enough.

A senior SDET should understand:

- How an application is structured
- How frontend communicates with backend
- How REST APIs work
- How authentication works
- How authorization works
- How data is stored
- How APIs interact with databases
- How business workflows are tested
- How test automation frameworks are designed
- How tests run against multiple environments
- How secrets are protected
- How reports are generated
- How automation fits into CI/CD
- How an application eventually runs in cloud infrastructure

Therefore, this project is designed as an **end-to-end Quality Engineering project**.

---

# 3. Current Project Status

At the current milestone:

```text
Spring Boot Backend             DONE
PostgreSQL                      DONE
Docker PostgreSQL               DONE
User Registration               DONE
User Login                      DONE
JWT Authentication              DONE
Spring Security                 DONE
ROLE_USER / ROLE_ADMIN          DONE
RBAC                            DONE

Product APIs                    DONE
Cart APIs                       DONE
Order APIs                      DONE
Mock Payment APIs               DONE

REST Assured Framework          DONE
TestNG                          DONE
API Client Layer                DONE
Functional Testing              DONE
Negative Testing                DONE
Validation Testing              DONE
Security Testing                DONE
RBAC Testing                    DONE
JSON Schema Validation          DONE
JDBC Database Validation        DONE
Test Data Cleanup               DONE

LOCAL / QA / STAGE Config       DONE
.env Secret Management          DONE

Allure Reporting                DONE
Request/Response Attachments    DONE
Sensitive Data Redaction        DONE
Environment Metadata            DONE

Swagger / OpenAPI               DONE
Swagger JWT Authorize           DONE

Current API Regression:
Tests Run: 48
Failures : 0
Errors   : 0

React Frontend                  PLANNED
Playwright UI Automation        PLANNED
GitHub Actions                  PLANNED
k6 Performance                  PLANNED
AWS                             PLANNED
```

---

# 4. Technology Stack

## Backend

```text
Java 17
Spring Boot 4.1.1
Spring Web MVC
Spring Data JPA
Hibernate
Spring Security
JWT
Maven
```

## Database

```text
PostgreSQL 16
Docker
Docker Compose
JDBC
```

## API Automation

```text
Java 17
REST Assured
TestNG
JSON Schema Validator
JDBC
Allure
Maven
```

## API Documentation

```text
OpenAPI
Swagger UI
```

## Future

```text
React
TypeScript
Vite
Playwright
GitHub Actions
k6
AWS
```

---

# 5. Repository Structure

Our root repository is:

```text
SDET-Commerce-Automation/
```

Conceptually:

```text
SDET-Commerce-Automation/
│
├── backend/
│
├── api-automation/
│
├── ui-automation/
│
├── database/
│
├── docker/
│
├── docs/
│   └── learning-notes/
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

Important point:

The backend and automation framework are separate.

```text
backend/
```

contains the actual application.

```text
api-automation/
```

contains the test automation framework.

This separation is intentional.

---

# 6. Why Is API Automation Separate From Backend?

A beginner might put tests directly inside the backend project.

That is possible for unit/integration tests, but our goal is to create a realistic independent automation framework.

Think about a real company.

Developers may own:

```text
commerce-backend
```

while QA/SDET may maintain:

```text
commerce-api-automation
```

The automation framework should be capable of testing:

```text
LOCAL
QA
STAGE
```

without depending on the backend source code.

For example:

```text
API Automation
     │
     ├── LOCAL → http://localhost:8080
     │
     ├── QA    → https://qa.example.com
     │
     └── STAGE → https://stage.example.com
```

Same test code.

Different environment.

This is one of the reasons we created an independent Maven module.

### Interview Answer

> "I kept the API automation framework independent from the application backend so that it behaves like a black-box test framework. The same automation suite can target local, QA or staging environments using external configuration without depending on backend source code."

---

# PART 2 — DEVELOPMENT ENVIRONMENT

# 7. Tools Installed

Our local development environment contains:

```text
macOS / Apple Silicon
Java 17
Maven
Docker Desktop
Docker Compose
VS Code
Git
Allure CLI
```

---

# 8. Java

Java is the primary programming language for:

```text
Spring Boot Backend
REST Assured Automation
TestNG Tests
JDBC Database Validation
```

Check Java:

```bash
java -version
```

Java compiler:

```bash
javac -version
```

Why Java 17?

Java 17 is an LTS — Long-Term Support — release and is widely used in enterprise applications.

---

# 9. What Is Maven?

Maven is a Java build and dependency management tool.

Our backend and API automation are Maven projects.

A Maven project contains:

```text
pom.xml
```

POM means:

```text
Project Object Model
```

The `pom.xml` defines things such as:

```text
Java version
Dependencies
Plugins
Build configuration
Test configuration
```

Example dependency idea:

```xml
<dependency>
    ...
</dependency>
```

Instead of manually downloading library JAR files, Maven downloads and manages dependencies.

---

# 10. Important Maven Commands

Compile/build/test:

```bash
mvn clean test
```

or when Maven Wrapper exists:

```bash
./mvnw clean test
```

### What does `clean` mean?

It removes the previous build output.

Usually:

```text
target/
```

gets recreated.

### What does `test` mean?

Maven:

```text
compiles application
        ↓
compiles tests
        ↓
runs tests
        ↓
generates test results
```

### Why Maven Wrapper?

Our backend contains:

```text
mvnw
```

The Maven Wrapper helps a project use a predictable Maven setup instead of relying only on the globally installed Maven.

---

# PART 3 — SPRING BOOT

# 11. What Is Spring Boot?

Spring Boot is a Java framework used to create production-style applications and REST APIs.

Without Spring Boot, a lot of configuration would have to be done manually.

Spring Boot gives us:

```text
Embedded web server
Dependency injection
REST controller support
Database integration
Security integration
Configuration management
Validation
Exception handling
```

Our application entry point is conceptually:

```text
BackendApplication.java
```

with:

```java
@SpringBootApplication
```

and:

```java
SpringApplication.run(...)
```

---

# 12. What Happens When Spring Boot Starts?

Simplified flow:

```text
BackendApplication
        ↓
SpringApplication.run()
        ↓
Spring Boot starts
        ↓
Application Context created
        ↓
Spring scans application packages
        ↓
Controllers discovered
Services discovered
Repositories discovered
Configuration discovered
        ↓
Database connection initialized
        ↓
Embedded server starts
        ↓
Application listens on port 8080
```

Our local backend:

```text
http://localhost:8080
```

---

# 13. What Is localhost?

`localhost` means:

```text
this computer
```

Usually:

```text
localhost = 127.0.0.1
```

So:

```text
http://localhost:8080
```

means:

```text
HTTP
 ↓
my computer
 ↓
port 8080
```

Our Spring Boot application listens there locally.

---

# 14. What Is a Port?

A computer can run many network applications simultaneously.

Ports help identify which application should receive a request.

Example:

```text
localhost:8080 → Spring Boot
localhost:5432 → PostgreSQL
```

So:

```text
8080 != server machine
```

8080 is a network port on the machine.

---

# PART 4 — SPRING BOOT LAYERED ARCHITECTURE

# 15. Main Application Layers

Our backend follows a layered architecture.

Typical flow:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

Response returns in reverse:

```text
Database
   ↓
Repository
   ↓
Service
   ↓
Controller
   ↓
HTTP Response
```

---

# 16. Controller Layer

The controller handles HTTP requests.

Examples:

```text
POST /api/users/login
GET /api/products
POST /api/orders
POST /api/payments
```

Controller responsibilities should mainly include:

```text
Receive HTTP request
Map URL
Read request body/path parameters
Call service
Return HTTP response
```

Controller should NOT contain large amounts of business logic.

Think:

```text
Controller = Entry door of backend
```

---

# 17. Service Layer

The service contains business logic.

Example:

When creating an order:

```text
Does user have cart items?
        ↓
Does sufficient stock exist?
        ↓
Create order
        ↓
Create order items
        ↓
Reduce product stock
        ↓
Clear cart
        ↓
Return order
```

This belongs in the Service layer because these are business rules.

Think:

```text
Service = Brain of application
```

---

# 18. Repository Layer

Repository communicates with the database.

Spring Data JPA allows us to create repository interfaces instead of writing SQL for every operation.

Conceptually:

```java
public interface ProductRepository
        extends JpaRepository<Product, Long> {
}
```

This gives common database operations such as:

```text
save()
findById()
findAll()
delete()
existsById()
```

Think:

```text
Repository = Database communication layer
```

---

# 19. Entity Layer

An Entity represents persistent database data.

Example Product:

```text
Product
│
├── id
├── name
├── description
├── price
└── stock
```

This maps to a database table conceptually like:

```text
products
```

Another example:

```text
User entity
        ↓
users table
```

Entity:

```text
Java Object
```

Database:

```text
Table Row
```

JPA/Hibernate connects them.

---

# 20. DTO — Data Transfer Object

DTO means:

```text
Data Transfer Object
```

We should not always expose database entities directly through an API.

Instead, APIs can use objects such as:

```text
LoginRequest
PaymentRequest
PaymentResponse
```

Example:

```text
Client sends:

{
  "email": "...",
  "password": "..."
}
```

Spring converts it into:

```text
LoginRequest
```

DTOs help separate:

```text
API contract
```

from:

```text
database model
```

---

# 21. Dependency Injection

Spring manages many application objects for us.

These managed objects are called:

```text
Beans
```

Instead of manually doing:

```java
ProductService service = new ProductService(...);
```

Spring creates and injects required dependencies.

Conceptually:

```text
ProductController
       │
       needs
       ▼
ProductService
       │
       needs
       ▼
ProductRepository
```

Spring wires these objects together.

This is Dependency Injection.

### Why is it useful?

It improves:

```text
Loose coupling
Testability
Maintainability
Object lifecycle management
```

---

# PART 5 — DATABASE

# 22. Why PostgreSQL?

Our application needs persistent data.

Examples:

```text
Users
Products
Cart Items
Orders
Order Items
Payments
```

We use:

```text
PostgreSQL
```

PostgreSQL is a relational database.

Data is organized into tables.

---

# 23. Important Tables

Conceptually our database contains:

```text
users
products
cart_items
orders
order_items
payments
```

Relationships matter.

Example:

```text
User
 │
 ├── Cart Items
 │
 └── Orders
       │
       ├── Order Items
       │
       └── Payment
```

---

# 24. Primary Key

A primary key uniquely identifies a row.

Example:

```text
products

id | name
---+----------------
1  | Laptop
2  | Keyboard
```

Here:

```text
id
```

is the primary key.

---

# 25. Foreign Key

A foreign key creates a relationship between tables.

Example:

```text
orders
id = 10

payments
order_id = 10
```

The payment belongs to order 10.

Therefore:

```text
payments.order_id
```

can reference:

```text
orders.id
```

---

# 26. Why Foreign Keys Matter in Testing

Foreign keys protect data integrity.

For example, we cannot always delete an order while a payment still references it.

This became important in our test cleanup.

Correct cleanup order can be:

```text
Payment
   ↓
Order Items
   ↓
Order
```

rather than deleting the parent record first.

This is an important real-world database testing lesson.

---

# PART 6 — DOCKER

# 27. What Is Docker?

Docker allows applications/services to run inside isolated containers.

For our current project, PostgreSQL runs through Docker.

Instead of manually installing/configuring PostgreSQL differently on every machine:

```text
Developer A
Developer B
QA Machine
CI Machine
```

we can define the required database environment in code/configuration.

---

# 28. What Is a Docker Image?

Think:

```text
Image = Blueprint
Container = Running instance
```

We use:

```text
postgres:16
```

as the PostgreSQL image.

Docker uses this image to create the PostgreSQL container.

---

# 29. What Is a Container?

A container is a running isolated instance created from an image.

Our PostgreSQL container is:

```text
sdet-commerce-postgres
```

Relationship:

```text
postgres:16 image
        ↓
Docker creates
        ↓
sdet-commerce-postgres container
        ↓
PostgreSQL server running
```

---

# 30. Docker Compose

Docker Compose allows us to define services in:

```text
docker-compose.yml
```

Our PostgreSQL configuration conceptually contains:

```yaml
services:
  postgres:
    image: postgres:16
    container_name: sdet-commerce-postgres
```

Environment values configure:

```text
Database name
Username
Password
```

Port mapping:

```text
5432:5432
```

means:

```text
Host Port 5432
       ↓
Container Port 5432
```

Therefore Spring Boot can connect through:

```text
localhost:5432
```

---

# 31. Docker Volume

Containers can be removed.

We don't want database data to disappear every time the container stops.

Therefore we use a Docker volume:

```text
postgres_data
```

Concept:

```text
PostgreSQL Container
       │
       ▼
Docker Volume
       │
       ▼
Persistent database data
```

Container lifecycle and data lifecycle are therefore not necessarily the same.

---

# 32. Important Docker Commands

Start services:

```bash
docker compose up -d
```

`-d` means detached mode.

The terminal is returned while containers continue running.

Check running containers:

```bash
docker ps
```

Check all containers:

```bash
docker ps -a
```

Stop Compose services:

```bash
docker compose down
```

View logs:

```bash
docker logs sdet-commerce-postgres
```

Enter PostgreSQL:

```bash
docker exec -it sdet-commerce-postgres \
psql -U sdetuser -d sdetcommerce
```

---

# 33. What Does docker exec Mean?

Example:

```bash
docker exec -it sdet-commerce-postgres \
psql -U sdetuser -d sdetcommerce
```

Breakdown:

```text
docker exec
→ execute a command inside an existing container

-it
→ interactive terminal

sdet-commerce-postgres
→ container name

psql
→ PostgreSQL command-line client

-U sdetuser
→ database user

-d sdetcommerce
→ database name
```

---

# PART 7 — SPRING BOOT TO POSTGRESQL CONNECTION

# 34. How Does Backend Connect to Database?

Conceptually:

```text
Spring Boot
    │
    │ JDBC
    ▼
localhost:5432
    │
    ▼
Docker Port Mapping
    │
    ▼
PostgreSQL Container
    │
    ▼
sdetcommerce Database
```

Connection URL:

```text
jdbc:postgresql://localhost:5432/sdetcommerce
```

Break it down:

```text
jdbc
→ Java Database Connectivity

postgresql
→ database driver/type

localhost
→ database host

5432
→ PostgreSQL port

sdetcommerce
→ database name
```

---

# 35. application.properties

Spring Boot configuration is stored in:

```text
backend/src/main/resources/application.properties
```

Our important configuration conceptually looks like:

```properties
spring.application.name=backend

spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/sdetcommerce}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

app.jwt.secret=${JWT_SECRET}
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:3600000}
```

---

# 36. Understanding ${ENVIRONMENT_VARIABLE}

Example:

```properties
spring.datasource.username=${DB_USERNAME}
```

This tells Spring:

```text
Read DB_USERNAME from the environment.
```

We intentionally do NOT hardcode:

```properties
spring.datasource.username=sdetuser
spring.datasource.password=sdetpassword
```

in source-controlled application configuration.

Why?

Because credentials should not be committed to Git.

---

# 37. Default Environment Values

Example:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/sdetcommerce}
```

This means:

```text
If DB_URL exists
        ↓
use DB_URL

otherwise
        ↓
use jdbc:postgresql://localhost:5432/sdetcommerce
```

The value after `:` is the default.

Same idea:

```properties
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:3600000}
```

If `JWT_EXPIRATION_MS` is absent:

```text
3600000 milliseconds
```

is used.

---

# PART 8 — .env AND LOCAL CONFIGURATION

# 38. Why Did We Create .env?

We need local values such as:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
Test credentials
```

But these should not be committed to Git.

Therefore local values are stored in:

```text
.env
```

and `.env` is ignored by Git.

---

# 39. Important Security Rule

Never commit:

```text
Passwords
JWT secrets
API keys
Access tokens
Private credentials
```

to Git.

Bad:

```text
DB_PASSWORD=my-real-password
```

inside a committed source file.

Better:

```text
DB_PASSWORD=${environment value}
```

and load the real value from local/CI secret configuration.

---

# 40. .env vs .env.example

This distinction is extremely important.

```text
.env
```

contains real local values.

It should NOT be committed.

```text
.env.example
```

contains placeholders/instructions.

It SHOULD be committed.

Example:

```env
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=replace_with_a_secure_jwt_secret_minimum_32_bytes
```

This tells another developer what configuration is required without exposing our real credentials.

---

# 41. Backend run-local.sh

We created a helper script so that local environment variables are loaded before Spring Boot starts.

Conceptually:

```bash
#!/bin/bash

set -a
source "$(dirname "$0")/.env"
set +a

cd "$(dirname "$0")"

./mvnw spring-boot:run
```

---

# 42. Understanding set -a

Command:

```bash
set -a
```

means variables subsequently created/read by the shell are automatically marked for export.

Then:

```bash
source .env
```

loads variables from `.env`.

Then:

```bash
set +a
```

turns automatic exporting back off.

Flow:

```text
.env
 ↓
source
 ↓
Shell Variables
 ↓
Exported Environment Variables
 ↓
Java Process
 ↓
Spring Boot
 ↓
application.properties
```

---

# 43. Why Did ./mvnw clean test Initially Need Environment Loading?

A `.env` file is NOT automatically understood by Maven or Java.

Just because this file exists:

```text
backend/.env
```

does not mean:

```bash
./mvnw clean test
```

automatically reads it.

Therefore we used:

```bash
set -a
source .env
set +a
./mvnw clean test
```

Now environment variables are available to the Maven/Java process.

This is an important concept.

```text
.env file exists
```

does NOT automatically mean:

```text
environment variables are loaded
```

Something must load/export them.

---

# 44. Backend Local Start Command

From repository root:

```bash
./backend/run-local.sh
```

Or:

```bash
cd backend
./run-local.sh
```

Flow:

```text
run-local.sh
      ↓
loads backend/.env
      ↓
exports environment variables
      ↓
starts Maven
      ↓
Spring Boot starts
      ↓
Spring connects to PostgreSQL
      ↓
API becomes available on port 8080
```

---

# PART 9 — JPA AND HIBERNATE

# 45. What Is JPA?

JPA means:

```text
Java Persistence API
```

JPA defines a standard way for Java applications to work with relational databases through objects.

Instead of thinking only in SQL:

```sql
INSERT INTO products ...
```

Java code can work with:

```java
Product product = new Product();
productRepository.save(product);
```

---

# 46. What Is Hibernate?

JPA is a specification.

Hibernate is a popular implementation of that specification.

Easy memory:

```text
JPA       = Rules / Specification
Hibernate = Implementation
```

Spring Data JPA makes working with JPA/Hibernate easier.

---

# 47. ORM

ORM means:

```text
Object Relational Mapping
```

It maps:

```text
Java Object
     ↕
Database Row
```

Example:

```text
Product.java
     ↕
products table
```

Fields:

```text
Java                Database

id          ↔       id
name        ↔       name
price       ↔       price
stock       ↔       stock
```

---

# 48. ddl-auto=update

We currently use:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This allows Hibernate to update database schema based on entity definitions.

Useful for:

```text
Local development
Learning
POC projects
```

But in mature production systems, database schema changes are commonly managed using migration tools such as:

```text
Flyway
Liquibase
```

because schema changes should be controlled and versioned.

### Interview Point

Do not say:

> "ddl-auto=update is how production databases should always be managed."

Better:

> "For this portfolio project's local development I used Hibernate schema update. In a production system I would prefer controlled versioned migrations using tools such as Flyway or Liquibase."

---

# PART 10 — COMPLETE FOUNDATION FLOW

# 49. What Happens When We Start Everything?

First:

```bash
docker compose up -d
```

Flow:

```text
Docker Compose
      ↓
Reads docker-compose.yml
      ↓
Starts postgres:16
      ↓
Creates/uses PostgreSQL container
      ↓
Mounts postgres_data volume
      ↓
PostgreSQL listens on 5432
```

Then:

```bash
./backend/run-local.sh
```

Flow:

```text
run-local.sh
      ↓
Loads .env
      ↓
Exports DB/JWT configuration
      ↓
Maven starts Spring Boot
      ↓
Spring creates ApplicationContext
      ↓
JPA initializes
      ↓
Datasource connects to PostgreSQL
      ↓
Hibernate maps entities/tables
      ↓
Spring Security initializes
      ↓
Controllers initialized
      ↓
Server listens on localhost:8080
```

Now:

```text
Client / Test
      ↓
http://localhost:8080/api/...
      ↓
Spring Boot
      ↓
Controller
      ↓
Service
      ↓
Repository
      ↓
PostgreSQL
```

---

# 50. One Diagram To Remember Foundation

```text
                    OUR LOCAL MACHINE

┌─────────────────────────────────────────────────────────┐
│                                                         │
│   REST Client / Automation                              │
│              │                                          │
│              │ HTTP :8080                               │
│              ▼                                          │
│   ┌─────────────────────────────┐                       │
│   │      Spring Boot App        │                       │
│   │                             │                       │
│   │ Controller                  │                       │
│   │     ↓                       │                       │
│   │ Service                     │                       │
│   │     ↓                       │                       │
│   │ Repository                  │                       │
│   └─────────────┬───────────────┘                       │
│                 │                                       │
│                 │ JDBC :5432                            │
│                 ▼                                       │
│   ┌─────────────────────────────┐                       │
│   │ Docker                      │                       │
│   │ ┌─────────────────────────┐ │                       │
│   │ │ PostgreSQL 16           │ │                       │
│   │ │ sdetcommerce database   │ │                       │
│   │ └───────────┬─────────────┘ │                       │
│   └─────────────┼───────────────┘                       │
│                 │                                       │
│                 ▼                                       │
│          postgres_data volume                           │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

# 51. Foundation Interview Questions

## Q1. What architecture did you use?

**Answer:**

I built the backend using a layered Spring Boot architecture. HTTP requests enter through controllers, business logic is handled by services, repositories manage persistence through Spring Data JPA/Hibernate, and PostgreSQL is used as the relational database.

---

## Q2. Why did you use Docker?

**Answer:**

I used Docker to provide a reproducible PostgreSQL environment. The database configuration is defined through Docker Compose, which reduces machine-specific setup and will also make future CI/CD execution easier.

---

## Q3. What is the difference between Docker image and container?

**Answer:**

An image is the reusable blueprint containing the software and required filesystem, while a container is a running instance created from that image.

Easy memory:

```text
Image = Class
Container = Object
```

This analogy is not technically exact, but it is useful for remembering the relationship.

---

## Q4. Why PostgreSQL?

**Answer:**

The commerce domain contains relational data such as users, products, cart items, orders, order items and payments. PostgreSQL provides relational integrity, constraints and transactional capabilities suitable for these workflows.

---

## Q5. What is JPA vs Hibernate?

**Answer:**

JPA is the Java persistence specification, while Hibernate is an implementation of JPA. Spring Data JPA provides an additional abstraction that simplifies repository-based database access.

---

## Q6. What is Dependency Injection?

**Answer:**

Dependency Injection means dependencies are provided to a class instead of the class manually creating them. Spring's IoC container manages application beans and injects required dependencies, which reduces coupling and improves maintainability and testability.

---

## Q7. Why separate backend and API automation?

**Answer:**

The API automation framework should behave independently of application source code. Keeping it separate allows the same test framework to execute against local, QA and staging deployments through environment configuration.

---

# 52. Foundation Cheat Sheet

Remember this:

```text
Spring Boot
= Backend application framework

Controller
= Receives HTTP requests

Service
= Business logic

Repository
= Database access

Entity
= Java representation of persistent data

DTO
= API data transfer model

JPA
= Persistence specification

Hibernate
= JPA implementation

PostgreSQL
= Relational database

Docker
= Container platform

Docker Image
= Blueprint

Docker Container
= Running image instance

Docker Compose
= Multi-service configuration/orchestration for local setup

Maven
= Build + dependency management

pom.xml
= Maven project configuration

.env
= Real local configuration/secrets — do NOT commit

.env.example
= Safe configuration template — commit

localhost:8080
= Spring Boot

localhost:5432
= PostgreSQL
```

---

# 53. Where We Are In The Learning Journey

We now understand the foundation:

```text
Project Goal
    ↓
Repository Structure
    ↓
Java + Maven
    ↓
Spring Boot
    ↓
Layered Architecture
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
PostgreSQL
    ↓
Docker
    ↓
Environment Configuration
```

The next part explains the first real business flow:

```text
USER REGISTRATION
        ↓
LOGIN
        ↓
PASSWORD SECURITY
        ↓
JWT GENERATION
        ↓
JWT VALIDATION
        ↓
SPRING SECURITY
        ↓
PROTECTED APIs
```

This is where authentication and application security begin.

---

# PART 11 — USER REGISTRATION AND AUTHENTICATION

# 54. What Is Authentication?

Authentication means:

```text
Who are you?
```

Example:

```text
Email + Password
        ↓
Backend verifies credentials
        ↓
User identity confirmed
```

Authentication does NOT mean the user is allowed to do everything.

It only proves identity.

Authorization comes later.

Easy memory:

```text
Authentication = Who are you?
Authorization  = What are you allowed to do?
```

---

# 55. Why Do We Need Authentication?

Our commerce APIs include operations that should not be available anonymously.

Examples:

```text
Cart
Orders
Payments
Admin Product Management
```

The backend must know:

```text
Which user is calling?
Is the user logged in?
What role does the user have?
```

Therefore we implemented authentication using:

```text
Spring Security
JWT
Password hashing
```

---

# 56. User Registration Flow

Registration conceptually works like this:

```text
Client
  ↓
POST /api/users/register
  ↓
UserController
  ↓
UserService
  ↓
Validate request
  ↓
Check whether email already exists
  ↓
Hash password
  ↓
Assign ROLE_USER
  ↓
Save user
  ↓
PostgreSQL
  ↓
Return response
```

A new user does NOT automatically become admin.

Default role:

```text
ROLE_USER
```

---

# 57. Why Default ROLE_USER?

Security principle:

```text
Least Privilege
```

A newly registered user should receive only the minimum permissions required.

Bad design:

```text
Every new user → ADMIN
```

Good design:

```text
Every new user → ROLE_USER
```

Admin privileges should be deliberately assigned.

---

# 58. User Entity Role

Our User entity includes a role.

Conceptually:

```java
@Enumerated(EnumType.STRING)
private Role role;
```

The enum contains:

```java
ROLE_USER,
ROLE_ADMIN
```

Why store enum as STRING?

Because database values remain readable:

```text
ROLE_USER
ROLE_ADMIN
```

instead of ordinal numbers such as:

```text
0
1
```

Ordinal storage is risky because enum ordering may change.

---

# 59. Role Enum

Conceptually:

```java
public enum Role {
    ROLE_USER,
    ROLE_ADMIN
}
```

Using an enum prevents random role values.

Instead of:

```text
"admin"
"ADMIN"
"Admin"
"superuser"
```

the application works with controlled values.

---

# PART 12 — PASSWORD SECURITY

# 60. Never Store Plaintext Passwords

A password should NOT be stored like:

```text
email                    password
user@test.com            Test@123
```

If the database is compromised, all passwords become immediately exposed.

Instead, passwords should be stored as cryptographic hashes.

---

# 61. What Is Password Hashing?

Hashing transforms a password into a non-reversible representation.

Concept:

```text
Test@123
   ↓
Password Encoder
   ↓
$2a$10$....
```

The database stores the hash.

The original password should not be recoverable from it.

---

# 62. Hashing vs Encryption

Important difference:

```text
Encryption
→ Designed to be reversible with a key

Hashing
→ Designed to be one-way
```

Passwords should generally be hashed, not reversibly encrypted.

---

# 63. Password Validation During Login

Suppose database contains:

```text
Stored Hash
```

and user sends:

```text
Test@123
```

Backend does NOT decrypt the stored password.

Instead:

```text
Raw Password
      ↓
Password Encoder comparison
      ↓
Does it match stored hash?
      │
      ├── YES → valid credentials
      └── NO  → invalid credentials
```

---

# 64. Why Password Hashes Look Different

Secure password hashing algorithms commonly include a salt.

This means the same plaintext password can produce different stored hashes.

That is good.

We should validate using the encoder's comparison method, not by hashing manually and comparing strings.

---

# 65. Password Security Interview Answer

> "I never store plaintext passwords. During registration the password is encoded before persistence. During login, the provided raw password is verified against the stored encoded password using the configured password encoder."

---

# PART 13 — LOGIN FLOW

# 66. Login Request

The login API conceptually accepts:

```json
{
  "email": "user@test.com",
  "password": "Test@123"
}
```

Flow:

```text
POST /api/users/login
        ↓
Controller
        ↓
UserService
        ↓
Find user by email
        ↓
Verify password
        ↓
Credentials valid?
        │
        ├── NO → login failure
        │
        └── YES
             ↓
          JwtService
             ↓
          Generate JWT
             ↓
          Return token
```

---

# 67. Why Return a Token?

HTTP requests are independent.

After login, the backend needs a way to recognize the user on later requests.

Example:

```text
Request 1:
POST /login
```

Then later:

```text
Request 2:
GET /api/products
```

Then:

```text
Request 3:
POST /api/orders
```

JWT allows the client to prove authentication on each protected request.

---

# PART 14 — JWT FROM ZERO

# 68. What Is JWT?

JWT means:

```text
JSON Web Token
```

It is a compact token format often used for stateless authentication.

A JWT commonly looks like:

```text
xxxxx.yyyyy.zzzzz
```

It has three main parts:

```text
Header.Payload.Signature
```

---

# 69. JWT Structure

Concept:

```text
HEADER
   .
PAYLOAD
   .
SIGNATURE
```

Example shape:

```text
eyJ...
.
eyJ...
.
abc...
```

---

# 70. JWT Header

Header usually describes things such as:

```text
Token type
Signing algorithm
```

Conceptually:

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

---

# 71. JWT Payload

Payload contains claims.

Claims are pieces of information.

Examples:

```text
subject
role
issued-at
expiration
```

In our application, important information includes:

```text
User identity
Role
Expiration
```

---

# 72. JWT Signature

The signature protects token integrity.

Concept:

```text
Header + Payload
      +
Secret
      ↓
Signature
```

If someone modifies the payload without possessing the signing secret, signature verification should fail.

---

# 73. JWT Is Usually Signed, Not Secret

Very important:

JWT payload is NOT automatically encrypted.

Therefore do NOT put sensitive secrets inside JWT payload.

Bad examples:

```text
Password
Database password
Private API key
Credit card CVV
```

JWT signing provides integrity/authenticity, not automatic confidentiality.

---

# 74. JWT Generation Flow

Our conceptual flow:

```text
Successful Login
      ↓
User loaded
      ↓
Role available
      ↓
JwtService
      ↓
Create claims
      ↓
Set subject
      ↓
Set issued time
      ↓
Set expiration
      ↓
Sign token using JWT secret
      ↓
Return token
```

---

# 75. JWT Expiration

Our configuration includes:

```properties
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:3600000}
```

Default:

```text
3600000 ms
```

which is:

```text
60 minutes
```

Why expiration?

A token should not remain valid forever.

If a token gets leaked, expiration limits the time it can be abused.

---

# 76. JWT Secret

Configuration concept:

```properties
app.jwt.secret=${JWT_SECRET}
```

Real secret comes from environment configuration.

It must NOT be hardcoded and committed.

Bad:

```java
String secret = "my-secret";
```

Better:

```text
Environment variable
        ↓
Spring configuration
        ↓
JwtService
```

---

# 77. JWT Role Claim

Our token includes user authorization information.

Concept:

```text
JWT
│
├── subject = user identity
├── role = ROLE_USER / ROLE_ADMIN
└── expiration
```

This helps reconstruct the authenticated user's authorities.

---

# PART 15 — STATELESS AUTHENTICATION

# 78. What Does Stateless Mean?

Traditional session flow may work like:

```text
Login
  ↓
Server creates session
  ↓
Server stores session state
  ↓
Client sends session ID
```

JWT-based stateless flow:

```text
Login
  ↓
Server generates token
  ↓
Client stores token
  ↓
Client sends token on every request
  ↓
Server validates token
```

The backend does not need a traditional per-user HTTP session for our API authentication flow.

---

# 79. SessionCreationPolicy.STATELESS

Our Spring Security configuration uses stateless security.

Conceptually:

```java
SessionCreationPolicy.STATELESS
```

Meaning:

```text
Do not depend on server-side HTTP sessions
for authentication state.
```

Each request must carry its own authentication information.

---

# 80. Authorization Header

Protected API calls send:

```http
Authorization: Bearer <JWT>
```

Example:

```text
Authorization
     ↓
Bearer
     ↓
JWT token
```

`Bearer` means whoever presents the token is treated as its bearer, subject to successful validation.

Therefore token protection is critical.

---

# PART 16 — SPRING SECURITY

# 81. What Is Spring Security?

Spring Security provides authentication and authorization infrastructure for Spring applications.

It helps us implement:

```text
Protected endpoints
JWT authentication
User authorities
Role checks
401 handling
403 handling
Security filters
```

---

# 82. Security Filter Chain

Before a protected request reaches our controller, Spring Security processes it through filters.

Simplified:

```text
HTTP Request
      ↓
Spring Security Filter Chain
      ↓
JwtAuthenticationFilter
      ↓
Authentication established?
      ↓
Authorization rule checked
      ↓
Controller
```

This is very important.

A protected request normally does not go straight to the controller.

---

# 83. SecurityConfig

Our SecurityConfig defines rules such as:

```text
Register/Login → Public

Swagger → Public

GET Products → Authenticated

POST Product → ADMIN only

PUT Product → ADMIN only

DELETE Product → ADMIN only

Other application endpoints → Authenticated
```

Conceptually:

```text
URL + HTTP method
        ↓
Security rule
        ↓
Allowed or rejected
```

---

# 84. Public Endpoints

Some endpoints must remain accessible without JWT.

Examples:

```text
/api/users/register
/api/users/login
```

Otherwise a user would need to be logged in before being able to log in.

Swagger documentation endpoints are also permitted in our current portfolio configuration.

---

# PART 17 — JWT AUTHENTICATION FILTER

# 85. Why Do We Need JwtAuthenticationFilter?

Spring receives:

```http
Authorization: Bearer <token>
```

Something must:

```text
Read the header
Extract token
Validate token
Read identity
Read role
Create Authentication object
Put it into SecurityContext
```

That is the responsibility of our JWT authentication filter.

---

# 86. JwtAuthenticationFilter Flow

Complete simplified flow:

```text
Incoming HTTP Request
        ↓
Read Authorization header
        ↓
Does it start with "Bearer "?
        │
        ├── NO → continue without JWT authentication
        │
        └── YES
              ↓
         Extract token
              ↓
         JwtService validates token
              ↓
         Token valid?
              │
              ├── NO → request remains unauthenticated
              │
              └── YES
                    ↓
              Extract user identity
                    ↓
              Extract role
                    ↓
              Build GrantedAuthority
                    ↓
              Create Authentication
                    ↓
              Store in SecurityContext
                    ↓
              Continue filter chain
```

---

# 87. Why Filter Before Controller?

Security should be applied consistently.

Without a centralized filter, every controller might need code like:

```text
Read JWT
Validate JWT
Check user
Check role
```

again and again.

That would produce:

```text
Duplication
Security mistakes
Hard-to-maintain code
```

Using the filter chain centralizes authentication.

---

# PART 18 — SECURITY CONTEXT

# 88. What Is SecurityContext?

After JWT validation, Spring needs to know:

```text
This request belongs to authenticated user X
with authorities Y
```

That information is stored in:

```text
SecurityContext
```

Conceptually:

```text
JWT
 ↓
JwtAuthenticationFilter
 ↓
Authentication Object
 ↓
SecurityContext
 ↓
Spring Security authorization checks
```

---

# 89. Authentication Object

The Authentication object conceptually contains:

```text
Principal / user identity
Credentials or token context
Authorities
Authentication status
```

The important thing for us is:

```text
Identity + Authorities
```

---

# 90. GrantedAuthority

Spring Security represents permissions/roles using authorities.

Example:

```text
ROLE_USER
ROLE_ADMIN
```

When JWT contains:

```text
ROLE_ADMIN
```

our filter reconstructs an authority so Spring Security understands that the current request belongs to an admin.

---

# PART 19 — AUTHENTICATION VS AUTHORIZATION

# 91. Authentication

Question:

```text
Who are you?
```

Example:

```text
JWT valid
→ user authenticated
```

---

# 92. Authorization

Question:

```text
Are you allowed to perform this action?
```

Example:

```text
Authenticated ROLE_USER
        ↓
POST /api/products
        ↓
Requires ADMIN
        ↓
Rejected
```

---

# 93. Full Example

User logs in:

```text
user@test.com
        ↓
Valid credentials
        ↓
JWT with ROLE_USER
```

Then calls:

```text
GET /api/products
```

Rule:

```text
authenticated
```

Result:

```text
200 OK
```

Now same user calls:

```text
POST /api/products
```

Rule:

```text
ADMIN required
```

Result:

```text
403 Forbidden
```

---

# PART 20 — RBAC

# 94. What Is RBAC?

RBAC means:

```text
Role-Based Access Control
```

Permissions are assigned based on roles.

Our roles:

```text
ROLE_USER
ROLE_ADMIN
```

---

# 95. Why RBAC?

Without RBAC, every authenticated user might be able to:

```text
Create products
Update products
Delete products
```

That would be a serious authorization issue.

We therefore restrict product management.

---

# 96. Current Product Access Rules

```text
Operation              USER        ADMIN

GET Products            YES         YES
GET Product Details     YES         YES
POST Product             NO         YES
PUT Product              NO         YES
DELETE Product           NO         YES
```

This is an example of privilege separation.

---

# 97. hasRole("ADMIN")

Spring Security commonly uses:

```java
hasRole("ADMIN")
```

while the underlying authority is:

```text
ROLE_ADMIN
```

Spring's role convention automatically works with the `ROLE_` prefix.

Important:

```text
hasRole("ADMIN")
```

maps conceptually to:

```text
ROLE_ADMIN
```

---

# 98. RBAC Request Flow

```text
POST /api/products
        ↓
Authorization: Bearer JWT
        ↓
JwtAuthenticationFilter
        ↓
JWT valid
        ↓
Extract role
        ↓
SecurityContext
        ↓
SecurityConfig
        ↓
Does user have ADMIN role?
        │
        ├── YES
        │     ↓
        │  ProductController
        │     ↓
        │  ProductService
        │     ↓
        │  ProductRepository
        │     ↓
        │  PostgreSQL
        │
        └── NO
              ↓
         403 Forbidden
```

---

# PART 21 — 401 VS 403

# 99. 401 Unauthorized

Despite the wording "Unauthorized", 401 generally means authentication is missing or invalid.

Easy meaning:

```text
"I cannot establish who you are."
```

Examples:

```text
No JWT
Invalid JWT
Expired JWT
Malformed authentication
```

Typical result:

```text
401 Unauthorized
```

---

# 100. 403 Forbidden

403 means the server recognizes/authenticates the user but does not allow the requested operation.

Easy meaning:

```text
"I know who you are,
but you cannot do this."
```

Example:

```text
ROLE_USER
    ↓
POST /api/products
    ↓
ADMIN required
    ↓
403
```

---

# 101. The Best Memory Trick

```text
401 = Who are you?

403 = I know who you are,
      but you don't have permission.
```

---

# 102. Decision Flow

```text
Protected Request
       ↓
Valid authentication?
       │
       ├── NO
       │     ↓
       │   401
       │
       └── YES
             ↓
       Required permission?
             │
             ├── YES → API executes
             │
             └── NO  → 403
```

---

# PART 22 — THE 401/403 ISSUE WE FACED

# 103. What Problem Did We See?

During RBAC testing, access-denied behavior was not always returning the expected status.

A user with insufficient role should receive:

```text
403
```

while an unauthenticated request should receive:

```text
401
```

We needed these two cases to remain clearly separated.

---

# 104. Why This Matters

If authentication and authorization errors are mixed together:

```text
Tests become misleading
API behavior becomes confusing
Clients cannot distinguish problems
Security behavior is harder to diagnose
```

A security-focused automation suite should explicitly test both.

---

# 105. How We Fixed It

Our Spring Security handlers were configured to set HTTP status codes directly.

Conceptually:

```java
response.setStatus(401);
```

for unauthenticated access.

And:

```java
response.setStatus(403);
```

for access denied.

This avoided unwanted error redispatch behavior interfering with the intended response.

---

# 106. Why /error Was Relevant

Spring Boot has error handling infrastructure around:

```text
/error
```

When certain response methods trigger error dispatching, the request may enter additional error-processing behavior.

In our security configuration we avoided that path for these security responses by setting the response status directly.

---

# 107. Real Test We Performed

We verified:

```text
ROLE_USER token
        ↓
POST /api/products
        ↓
403 Forbidden
```

and:

```text
Authenticated user
        ↓
GET /api/products
        ↓
200 OK
```

This proved that authentication and role authorization were working separately.

---

# PART 23 — USER TOKEN VS ADMIN TOKEN

# 108. Why Did Automation Need Two Tokens?

One token is not enough for proper RBAC testing.

We need:

```text
Normal User
Admin User
```

Therefore automation obtains:

```text
token
```

for normal-user workflows.

And:

```text
adminToken
```

for admin workflows.

---

# 109. BaseTest Authentication Setup

Conceptually:

```text
@BeforeClass
     ↓
Login standard test user
     ↓
Store USER token
     ↓
Login admin user
     ↓
Store ADMIN token
     ↓
Initialize API clients
```

Result:

```text
token      → ROLE_USER
adminToken → ROLE_ADMIN
```

---

# 110. Why This Design Is Useful

Tests can clearly express intent.

Example:

```text
Product read test
→ token
```

Product admin operation:

```text
Create/update/delete
→ adminToken
```

RBAC negative test:

```text
Try admin action with token
→ expect 403
```

This is much better than using an admin account for every test.

If every test used admin credentials, authorization defects could remain hidden.

---

# PART 24 — COMPLETE SECURITY FLOW

# 111. Registration to Protected API

```text
1. User registers
        ↓
2. Password is encoded
        ↓
3. ROLE_USER assigned
        ↓
4. User saved in PostgreSQL
        ↓
5. User logs in
        ↓
6. Email/password validated
        ↓
7. JWT generated
        ↓
8. Client receives JWT
        ↓
9. Client calls protected API
        ↓
10. Authorization: Bearer <JWT>
        ↓
11. JwtAuthenticationFilter extracts token
        ↓
12. JwtService validates token
        ↓
13. Identity + role extracted
        ↓
14. Authentication object created
        ↓
15. SecurityContext populated
        ↓
16. SecurityConfig checks permissions
        ↓
17A. Allowed → Controller
17B. Not authenticated → 401
17C. Wrong role → 403
```

---

# 112. Security Architecture Diagram

```text
                   LOGIN FLOW

Email + Password
       │
       ▼
┌─────────────────┐
│ UserController  │
└────────┬────────┘
         ▼
┌─────────────────┐
│  UserService    │
└────────┬────────┘
         │
         ├── Find User
         ├── Verify Password
         │
         ▼
┌─────────────────┐
│   JwtService    │
└────────┬────────┘
         │
         ▼
       JWT


               PROTECTED REQUEST FLOW

Authorization: Bearer JWT
            │
            ▼
┌──────────────────────────┐
│ JwtAuthenticationFilter  │
└─────────────┬────────────┘
              │
              ▼
       Validate Token
              │
              ▼
     Extract User + Role
              │
              ▼
┌──────────────────────────┐
│     SecurityContext      │
└─────────────┬────────────┘
              │
              ▼
┌──────────────────────────┐
│      SecurityConfig      │
│   Authorization Rules    │
└─────────────┬────────────┘
              │
       ┌──────┴──────┐
       │             │
       ▼             ▼
    Allowed       Rejected
       │             │
       ▼         401 / 403
 Controller
```

---

# PART 25 — SECURITY TESTING MINDSET

# 113. Do Not Test Only Happy Paths

A weak automation suite may only test:

```text
Valid token
Valid request
Expected 200
```

A stronger suite also tests:

```text
No token
Invalid token
Wrong role
Invalid credentials
Duplicate users
Invalid input
Unauthorized resource access
```

Security testing starts with asking:

```text
What should this user NOT be able to do?
```

---

# 114. Important Security Scenarios

Examples we care about:

```text
Anonymous user accesses protected endpoint
→ 401

ROLE_USER attempts admin product creation
→ 403

ROLE_ADMIN creates product
→ 201

Valid user accesses products
→ 200

Invalid login credentials
→ authentication failure
```

Later domain-specific tests also validate ownership for:

```text
Cart
Orders
Payments
```

---

# 115. Why Ownership Validation Matters

Authentication alone does not guarantee authorization.

Suppose:

```text
User A is authenticated
```

That should NOT automatically allow User A to access:

```text
User B's order
User B's cart
User B's payment
```

So application security has multiple layers:

```text
Authenticated?
     ↓
Correct Role?
     ↓
Correct Resource Owner?
```

This is a very important API security concept.

---

# PART 26 — JWT SECURITY NOTES

# 116. JWT Is Like a Credential

If a valid JWT is stolen, an attacker may be able to use it until it expires or becomes invalid.

Therefore:

```text
Do not log real JWTs
Do not commit JWTs
Do not expose JWTs in reports
Use HTTPS in real deployed environments
Use expiration
Protect signing secret
```

This is why later we implemented sanitized Allure reporting.

---

# 117. Why We Redact Authorization Headers

Our automation reports attach HTTP request details.

Originally that could expose:

```text
Authorization: Bearer eyJ....
```

This is dangerous because test artifacts may be:

```text
Shared
Uploaded to CI
Downloaded
Archived
Viewed by multiple people
```

Therefore we later built:

```text
SanitizedAllureFilter
```

which keeps useful request evidence while replacing secrets with:

```text
[REDACTED]
```

We will study this deeply in the Allure section.

---

# PART 27 — SECURITY INTERVIEW QUESTIONS

# 118. Q: Explain Authentication vs Authorization.

**Answer:**

Authentication establishes the identity of a user, while authorization determines what that authenticated user is permitted to do. In my project, JWT establishes the authenticated identity and Spring Security RBAC rules enforce authorization.

---

# 119. Q: How Did You Implement JWT Authentication?

**Answer:**

I implemented stateless JWT authentication using Spring Security. After successful login, the backend generates a signed JWT containing user identity and role information. On subsequent requests, a custom JWT filter extracts and validates the token, creates the appropriate Spring Authentication object and populates the SecurityContext. Endpoint authorization is then enforced through SecurityConfig.

---

# 120. Q: What Happens When a User Calls a Protected API?

**Answer:**

The request enters the Spring Security filter chain before reaching the controller. My JWT authentication filter reads the Bearer token, validates it, extracts the user identity and role, and populates the SecurityContext. Spring Security then evaluates the authorization rules. If the user is allowed, the request reaches the controller; otherwise it returns 401 or 403 depending on the failure type.

---

# 121. Q: Difference Between 401 and 403?

**Answer:**

401 means authentication is missing or invalid. 403 means the caller is authenticated but does not have sufficient permission for the requested operation.

---

# 122. Q: Why Is JWT Called Stateless?

**Answer:**

The backend does not rely on a traditional server-side HTTP session to remember the logged-in user. Each protected request carries the JWT, and the server validates that token independently.

---

# 123. Q: Is JWT Encrypted?

**Answer:**

Not necessarily. A standard signed JWT protects integrity but its payload can usually be decoded. Therefore sensitive secrets should never be stored in the JWT payload.

---

# 124. Q: Why Did You Add Role Into JWT?

**Answer:**

The role allows the application to reconstruct user authorities during JWT authentication. The custom filter converts the role information into Spring Security authorities, which are then used by RBAC rules.

---

# 125. Q: Why Use Separate User and Admin Accounts in Automation?

**Answer:**

Using separate accounts allows the automation suite to validate both positive and negative authorization behavior. For example, an admin product creation test should pass with the admin token, while the same operation with a normal-user token should return 403.

---

# 126. Q: How Do You Protect Passwords?

**Answer:**

Passwords are encoded before persistence and the raw password is never stored in the database. During login, the submitted password is checked against the stored encoded password using the password encoder.

---

# PART 28 — SECURITY CHEAT SHEET

```text
AUTHENTICATION
= Who are you?

AUTHORIZATION
= What are you allowed to do?

JWT
= JSON Web Token

JWT Parts
= Header.Payload.Signature

Bearer Token
= Token sent in Authorization header

JwtService
= Generates / parses / validates JWT

JwtAuthenticationFilter
= Reads incoming JWT and establishes Authentication

SecurityContext
= Stores authenticated identity for current request

GrantedAuthority
= Permission/role recognized by Spring Security

RBAC
= Role-Based Access Control

ROLE_USER
= Standard user

ROLE_ADMIN
= Administrative user

401
= Missing/invalid authentication

403
= Authenticated but insufficient permission

STATELESS
= No traditional server-side login session dependency

Password
= Store hash, never plaintext

JWT Payload
= Do not store secrets

JWT Secret
= Never commit to Git
```

---

# 127. One Security Flow To Memorize

```text
REGISTER
   ↓
Hash Password
   ↓
ROLE_USER
   ↓
Database

LOGIN
   ↓
Verify Password
   ↓
Generate JWT

PROTECTED REQUEST
   ↓
Bearer JWT
   ↓
JwtAuthenticationFilter
   ↓
JwtService
   ↓
Validate JWT
   ↓
Extract User + Role
   ↓
SecurityContext
   ↓
SecurityConfig
   ↓
Permission Check
   │
   ├── Authentication missing → 401
   ├── Wrong permission       → 403
   └── Allowed                → Controller
```

---

# 128. Where We Are Now

At this point we understand:

```text
Application Foundation
        ↓
Spring Boot
        ↓
PostgreSQL
        ↓
Docker
        ↓
User Registration
        ↓
Password Security
        ↓
Login
        ↓
JWT
        ↓
Spring Security
        ↓
JwtAuthenticationFilter
        ↓
SecurityContext
        ↓
RBAC
        ↓
401 vs 403
```

Next we move into the actual commerce business modules:

```text
PRODUCTS
   ↓
CART
   ↓
ORDERS
   ↓
PAYMENTS
```

For each module we will understand:

```text
Entity
Repository
Service
Controller
Business Rules
Database Relationships
API Flow
Validation
Security
Testing Strategy
Interview Explanation
```
---

# PART 29 — PRODUCT MODULE

# 129. Why Product Module First?

Commerce application ka core object hota hai:

```text
Product
```

Baaki flows mostly product ke around build hote hain:

```text
Product
   ↓
Cart
   ↓
Order
   ↓
Payment
```

Agar product hi nahi hai, to cart/order/payment ka realistic flow possible nahi hota.

---

# 130. Product Entity

Our Product entity conceptually contains:

```text
Product
│
├── id
├── name
├── description
├── price
└── stock
```

Important points:

```text
id
→ unique database identifier

name
→ product name

description
→ product details

price
→ monetary value

stock
→ available quantity
```

---

# 131. Why BigDecimal for Price?

Money ko floating-point types se handle karna risky hota hai because floating-point precision issues ho sakte hain.

Example:

```text
0.1 + 0.2
```

binary floating-point mein exactly expected decimal result na de.

Therefore monetary values ke liye commonly:

```java
BigDecimal
```

use kiya jata hai.

### Interview Answer

> "For monetary values like product prices and order totals, I used BigDecimal instead of floating-point types to avoid precision issues."

---

# 132. Product Repository

Repository database access handle karta hai.

Basic operations:

```text
save
findById
findAll
delete
```

Search functionality ke liye custom query method bhi ho sakta hai.

Concept:

```text
ProductService
      ↓
ProductRepository
      ↓
products table
```

---

# 133. Product Service

ProductService business logic handle karta hai.

Typical operations:

```text
Create Product
Get All Products
Get Product By ID
Search Product
Update Product
Delete Product
```

Service layer ka kaam sirf repository call karna nahi hai.

Yahan validations aur business rules bhi ho sakte hain.

---

# 134. Product Controller

Controller HTTP endpoints expose karta hai.

Current important endpoints:

```text
GET    /api/products
GET    /api/products/{id}
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
```

Security:

```text
GET
→ authenticated user

POST / PUT / DELETE
→ ADMIN only
```

---

# 135. Product Request Flow

Example:

```text
GET /api/products/10
        ↓
Spring Security
        ↓
ProductController
        ↓
ProductService
        ↓
ProductRepository
        ↓
PostgreSQL
        ↓
Product found?
        │
        ├── YES → 200
        └── NO  → error response
```

---

# 136. Product Creation Flow

```text
POST /api/products
        ↓
JWT Authentication
        ↓
RBAC Check
        ↓
ADMIN?
        │
        ├── NO → 403
        │
        └── YES
              ↓
        Validate request
              ↓
        ProductService
              ↓
        ProductRepository.save()
              ↓
        PostgreSQL
              ↓
        201 Created
```

---

# 137. Product Search

Search functionality important hai because real applications mein sirf full list retrieve karna enough nahi hota.

Possible flow:

```text
Search Term
     ↓
GET /api/products?...search...
     ↓
ProductController
     ↓
ProductService
     ↓
ProductRepository
     ↓
Matching records
```

Testing mein:

```text
Exact match
Partial match
No match
Case behavior
Empty search
```

jaise scenarios useful hote hain.

---

# 138. Product Validation

Typical invalid inputs:

```text
Blank name
Invalid price
Negative price
Negative stock
Missing required fields
```

Validation ka goal:

```text
Bad data DB tak na pahuche.
```

---

# 139. Product DB Validation

API test sirf response verify nahi karta.

Example:

```text
Create Product API
       ↓
201 response
       ↓
Capture Product ID
       ↓
Run SQL query
       ↓
Verify:
name
price
stock
```

This is API-to-database validation.

---

# 140. Product Security Tests

Important cases:

```text
No token → 401

USER GET products → 200

USER POST product → 403

ADMIN POST product → 201
```

Yeh RBAC ka real proof hai.

---

# PART 30 — CART MODULE

# 141. What Is Cart?

Cart temporary shopping state represent karta hai.

User product ko directly order nahi karta.

Typical journey:

```text
Browse Product
    ↓
Add to Cart
    ↓
Update Quantity
    ↓
Review Cart
    ↓
Create Order
```

---

# 142. CartItem Entity

Conceptually:

```text
CartItem
│
├── id
├── user
├── product
└── quantity
```

This means:

```text
Which user?
Which product?
How many?
```

---

# 143. Cart Relationship

Conceptually:

```text
User
  │
  └── Cart Items
         │
         └── Product
```

Example:

```text
User A
│
├── Product 10 × 2
└── Product 15 × 1
```

---

# 144. Unique User + Product Constraint

Important design:

```text
(user_id, product_id)
```

unique hona chahiye.

Why?

Without this, same user ke liye same product ki multiple duplicate rows create ho sakti hain.

Bad:

```text
user 5, product 10, qty 1
user 5, product 10, qty 2
```

Better:

```text
user 5, product 10, qty 3
```

So cart logically cleaner rehta hai.

---

# 145. Add to Cart Flow

```text
POST Add-to-Cart
      ↓
Authenticate user
      ↓
Find product
      ↓
Product exists?
      │
      ├── NO → error
      └── YES
            ↓
      Validate quantity
            ↓
      Check stock
            ↓
      Enough stock?
            │
            ├── NO → error
            └── YES
                  ↓
          Existing cart item?
            │
            ├── YES → update quantity
            └── NO  → create cart item
                  ↓
             Save to DB
```

---

# 146. Cart Stock Validation

Suppose:

```text
Product stock = 5
```

User tries:

```text
quantity = 10
```

Cart should not accept unrealistic quantity.

This prevents later invalid order creation.

Still, order creation must validate stock again because stock may change after product was added to cart.

Very important:

```text
Cart validation alone is not enough.
```

---

# 147. Why Validate Stock Again During Order?

Timeline:

```text
10:00
User adds 5 units to cart
Stock = 5

10:05
Another customer purchases 3
Stock = 2

10:10
First user creates order
```

If we trust old cart state blindly:

```text
Order quantity = 5
Stock now = 2
```

invalid order create ho jayega.

Therefore order creation should re-check current stock.

---

# 148. Cart Operations

Our cart supports:

```text
Add item
Get cart
Update quantity
Remove item
Clear cart
```

Each operation authenticated user ke context mein hona chahiye.

---

# 149. Cart Ownership

User A should never manipulate User B's cart.

Correct concept:

```text
JWT identity
     ↓
Backend identifies current user
     ↓
Only current user's cart items
```

Client-provided arbitrary user ID par trust karna risky ho sakta hai.

---

# 150. Cart Automation Coverage

We created tests around:

```text
CRUD
Validation
Security
Database
```

Meaning:

```text
Add
Read
Update
Remove
Clear
Invalid quantity
Insufficient stock
Missing token
DB persistence
```

---

# PART 31 — ORDER MODULE

# 151. What Is an Order?

Cart represents shopping intent.

Order represents a committed purchase transaction/workflow.

Flow:

```text
Cart
 ↓
Create Order
 ↓
Order persisted
 ↓
Stock reduced
 ↓
Cart cleared
```

---

# 152. Order Entity

Conceptually:

```text
Order
│
├── id
├── user
├── status
├── total
└── orderItems
```

Current statuses include:

```text
CREATED
CANCELLED
```

---

# 153. OrderItem Entity

OrderItem stores product data related to the order.

Conceptually:

```text
OrderItem
│
├── id
├── order
├── product
├── quantity
├── price snapshot
└── other snapshot data
```

---

# 154. Why OrderItem Snapshot Is Important

Suppose user buys:

```text
Product: Keyboard
Price: 2000
```

Later admin changes product price to:

```text
2500
```

Historical order should still reflect:

```text
2000
```

at the time of purchase.

This is why order data should capture a snapshot.

Very important principle:

```text
Historical transaction data should not depend only on mutable current product data.
```

---

# 155. Create Order Flow

Complete simplified flow:

```text
POST /api/orders
        ↓
Authenticate user
        ↓
Load user's cart
        ↓
Cart empty?
        │
        ├── YES → error
        └── NO
              ↓
       For every cart item
              ↓
       Load current product
              ↓
       Validate stock
              ↓
       Any insufficient stock?
              │
              ├── YES → fail order
              └── NO
                    ↓
              Create Order
                    ↓
              Create OrderItems
                    ↓
              Snapshot price/data
                    ↓
              Reduce stock
                    ↓
              Save order
                    ↓
              Clear cart
                    ↓
              Return CREATED order
```

---

# 156. Stock Deduction Example

Before order:

```text
Product Stock = 10
Cart Quantity = 3
```

After successful order:

```text
Product Stock = 7
```

Formula:

```text
newStock = currentStock - orderedQuantity
```

---

# 157. Why Order Creation Is More Than One DB Operation

Order creation affects multiple things:

```text
orders
order_items
products stock
cart_items
```

That means business workflow spans multiple database writes.

Conceptually this should behave atomically.

Meaning:

```text
Either all important changes succeed
or operation should roll back.
```

Spring transaction management is commonly used for such workflows.

---

# 158. Why Transaction Is Important

Imagine:

```text
Order saved
Stock deducted
BUT cart clear fails
```

Now system becomes inconsistent.

Or:

```text
Order saved
BUT stock update fails
```

Again inconsistent.

Transactional business flow helps avoid partial updates.

### Interview Answer

> "Order creation touches multiple tables and business states, so it should be transactional. The operation should either complete as a unit or roll back to preserve consistency."

---

# 159. Order Retrieval

Users should retrieve only their own orders.

Concept:

```text
Authenticated User
      ↓
GET orders
      ↓
Filter by current user
```

Not:

```text
Return every customer's order.
```

---

# 160. Order Cancellation

Cancellation is not just:

```text
status = CANCELLED
```

There is business logic.

Our flow:

```text
Find order
    ↓
Validate ownership
    ↓
Already cancelled?
    ↓
Restore stock
    ↓
Set status CANCELLED
    ↓
Save
```

---

# 161. Stock Restoration

Example:

Before original order:

```text
Stock = 10
```

Order quantity:

```text
3
```

After order:

```text
Stock = 7
```

After cancellation:

```text
Stock = 10
```

Formula:

```text
restoredStock = currentStock + cancelledQuantity
```

---

# 162. Why We Test Stock Restoration

If cancellation only updates order status but does not restore stock:

```text
Inventory becomes wrong.
```

So test should verify both:

```text
Order status
AND
Product stock
```

This is business-level validation.

---

# 163. Order Database Validation

Example test:

```text
Create cart
    ↓
Create order
    ↓
API says CREATED
    ↓
Query orders table
    ↓
Query order_items
    ↓
Query products stock
    ↓
Verify DB state
```

This validates full integration.

---

# 164. Order Security

Important:

```text
User A
```

must not be able to manipulate:

```text
User B's order
```

Therefore order service must enforce ownership.

Authentication alone is not enough.

---

# PART 32 — MOCK PAYMENT MODULE

# 165. Why Did We Build Mock Payment?

Real payment gateway integration would introduce:

```text
External provider
Credentials
Webhooks
Real financial risk
Complex setup
```

For portfolio automation, we want realistic payment business logic without real money movement.

So we built:

```text
Mock Payment
```

---

# 166. Payment Entity

Conceptually:

```text
Payment
│
├── id
├── order
├── amount
├── paymentMethod
├── status
├── transactionId
└── createdAt
```

---

# 167. Payment Status

Current enum:

```text
SUCCESS
FAILED
```

In our current mock flow, successful valid payment processing returns:

```text
SUCCESS
```

---

# 168. One Payment Per Order

Important relationship:

```text
Order
  │
  └── Payment
```

Current design enforces one payment per order.

That prevents:

```text
Same order paid twice
```

unless business explicitly supports retries/multiple payment attempts.

---

# 169. Payment Creation Flow

```text
POST /api/payments
       ↓
Authenticate user
       ↓
Read orderId
       ↓
Find order
       ↓
Order exists?
       │
       ├── NO → error
       └── YES
             ↓
       Verify ownership
             ↓
       Cancelled?
             │
             ├── YES → reject
             └── NO
                   ↓
          Payment already exists?
             │
             ├── YES → reject duplicate
             └── NO
                   ↓
          Use order amount
                   ↓
          Generate transaction ID
                   ↓
          Set SUCCESS
                   ↓
          Save payment
                   ↓
          Return 201
```

---

# 170. Why Payment Amount Comes From Order

Very important security/business rule:

Client should not be trusted to choose arbitrary final payment amount.

Bad request design:

```json
{
  "orderId": 100,
  "amount": 1
}
```

If backend trusts this:

```text
Order total = 5000
Client pays = 1
```

serious flaw.

Better:

```text
Client sends order reference
Backend calculates/reads trusted amount from order
```

Our payment service uses order amount.

This is a strong interview point.

---

# 171. Duplicate Payment Protection

Scenario:

```text
Order 100 already has SUCCESS payment
```

Second payment request for same order should not silently create another payment.

We validate:

```text
existsByOrderId
```

and reject duplicate payment.

Current expected conflict behavior:

```text
409 Conflict
```

---

# 172. Cancelled Order Payment Validation

Scenario:

```text
Order status = CANCELLED
```

Payment should not be created.

Expected:

```text
409 Conflict
```

Why?

Because business state does not allow payment anymore.

---

# 173. Payment Ownership

User A should not pay for or retrieve arbitrary User B order/payment data.

Flow:

```text
JWT User
   ↓
Load Order
   ↓
Check order owner
   ↓
Allow / reject
```

Again:

```text
Authentication != complete authorization
```

---

# 174. Transaction ID

Each payment gets a transaction identifier.

We generate it using a UUID-based approach.

Concept:

```text
Payment
   ↓
Unique transaction ID
```

Useful for:

```text
Tracing
Debugging
Audit
Support
Test assertions
```

---

# 175. Payment Database Validation

Automation validates:

```text
API response
      ↓
payment created
      ↓
Query DB
      ↓
Verify:
order ID
amount
payment method
status
transaction ID
```

This proves persistence, not just controller response.

---

# PART 33 — BUSINESS FLOW END TO END

# 176. Complete Commerce Journey

```text
REGISTER
   ↓
LOGIN
   ↓
JWT
   ↓
GET PRODUCTS
   ↓
ADD PRODUCT TO CART
   ↓
UPDATE CART
   ↓
CREATE ORDER
   ↓
STOCK DECREASES
   ↓
CART CLEARS
   ↓
CREATE PAYMENT
   ↓
PAYMENT SUCCESS
```

---

# 177. Cancellation Journey

```text
Create Product
   ↓
Stock = 10
   ↓
Add 3 to Cart
   ↓
Create Order
   ↓
Stock = 7
   ↓
Cancel Order
   ↓
Order = CANCELLED
   ↓
Stock = 10
```

This is one of our strongest end-to-end business validations.

---

# 178. Database Relationships

Conceptually:

```text
USERS
  │
  ├──────────────┐
  │              │
  ▼              ▼
CART_ITEMS      ORDERS
  │              │
  ▼              ▼
PRODUCTS       ORDER_ITEMS
                 │
                 ▼
              PRODUCTS

ORDERS
  │
  ▼
PAYMENTS
```

Another simplified view:

```text
User
 ├── CartItem ── Product
 └── Order
      ├── OrderItem ── Product
      └── Payment
```

---

# PART 34 — WHY DATA CLEANUP MATTERS

# 179. Test Data Can Break Other Tests

Suppose one test creates:

```text
Product
Cart Item
Order
Payment
```

and leaves everything behind.

Next test may fail because:

```text
Duplicate data
Existing payment
Existing cart
Wrong stock
Unexpected order state
```

Therefore automation needs controlled cleanup.

---

# 180. Parent/Child Cleanup Order

Because of foreign keys, deletion order matters.

Example:

```text
Payment references Order
OrderItem references Order
Order references User
```

So cleanup cannot always start with parent.

Safer order:

```text
Payment
   ↓
Order Items
   ↓
Order
```

Then related cart/product cleanup as required.

---

# 181. Why Payment Must Be Deleted Before Order

If:

```text
payment.order_id
```

references:

```text
orders.id
```

then deleting order first may violate foreign-key constraints.

Database protects us from inconsistent references.

This was a practical lesson from our cleanup logic.

---

# PART 35 — TESTING BUSINESS RULES

# 182. Test APIs, Not Only Status Codes

A weak test:

```text
POST /orders
Expect 201
```

A better test:

```text
POST /orders
Expect 201

Verify:
order status = CREATED
correct item count
correct quantity
correct price
cart cleared
stock reduced
database row created
```

This validates actual business behavior.

---

# 183. Positive Testing

Examples:

```text
Create valid product
Add available product to cart
Create order with valid cart
Cancel valid order
Create payment for active unpaid order
```

---

# 184. Negative Testing

Examples:

```text
Blank product name
Negative price
Invalid quantity
Order with empty cart
Quantity > available stock
Payment for missing order
Duplicate payment
Payment for cancelled order
```

Negative tests are essential because production defects often happen around invalid inputs and edge states.

---

# 185. Security Testing

Examples:

```text
No token
Invalid token
Wrong role
Wrong resource owner
```

---

# 186. Database Testing

Examples:

```text
Product persisted correctly
Cart quantity persisted
Order created
Order items saved
Stock deducted
Stock restored
Payment persisted
```

---

# 187. Business Testing Pyramid

For these workflows:

```text
HTTP Validation
       ↓
Business Rule Validation
       ↓
Database Validation
       ↓
Security Validation
```

Together they provide stronger confidence than only asserting response status.

---

# PART 36 — PRODUCT INTERVIEW QUESTIONS

# 188. Q: How Did You Test Product APIs?

**Answer:**

I covered product CRUD, search, input validation, role-based security and direct database validation. Read operations are available to authenticated users, while create, update and delete operations require the admin role.

---

# 189. Q: Why Use BigDecimal for Price?

**Answer:**

Monetary calculations require reliable decimal precision. BigDecimal avoids the floating-point precision issues that can occur with float or double.

---

# PART 37 — CART INTERVIEW QUESTIONS

# 190. Q: How Did You Handle Duplicate Cart Items?

**Answer:**

The cart design uses a unique user-product relationship so the same product is not stored as uncontrolled duplicate rows for the same user. Existing cart state can be updated instead.

---

# 191. Q: Why Check Stock in Both Cart and Order?

**Answer:**

Cart stock validation improves user experience, but it cannot guarantee stock will remain unchanged. Therefore order creation revalidates current stock before committing the transaction.

---

# 192. Q: How Do You Secure Cart Data?

**Answer:**

Cart operations are tied to the authenticated user's identity. The backend determines the current user from the authentication context rather than trusting arbitrary client ownership.

---

# PART 38 — ORDER INTERVIEW QUESTIONS

# 193. Q: Explain Your Order Creation Logic.

**Answer:**

When an authenticated user creates an order, the service loads the current cart, validates that it is not empty, rechecks current product stock, creates the order and order-item snapshots, reduces inventory, persists the order and clears the cart. The workflow should be transactional because multiple database states are updated together.

---

# 194. Q: Why Snapshot Order Item Data?

**Answer:**

Product data can change after purchase. Order-item snapshots preserve historical transaction information such as the price used when the order was created.

---

# 195. Q: What Happens on Cancellation?

**Answer:**

The service validates ownership and order state, restores the inventory quantity for each order item, and changes the order status to CANCELLED.

---

# PART 39 — PAYMENT INTERVIEW QUESTIONS

# 196. Q: Why Mock Payment?

**Answer:**

The goal is to test realistic payment-related business workflows without integrating a real payment provider or moving real money. The mock module still validates ownership, duplicate-payment prevention, order state, amount calculation and persistence.

---

# 197. Q: Why Should the Client Not Send the Final Payment Amount?

**Answer:**

The client is not a trusted source for financial calculations. The backend should derive the payable amount from trusted server-side order data to prevent tampering.

---

# 198. Q: How Did You Prevent Duplicate Payment?

**Answer:**

Before creating a payment, the service checks whether a payment already exists for the order. If it does, the request is rejected with a conflict response.

---

# PART 40 — COMMERCE BUSINESS LOGIC CHEAT SHEET

```text
PRODUCT
= Item available for purchase

CART
= User's temporary selection

ORDER
= Committed purchase record

ORDER ITEM
= Historical item snapshot inside order

PAYMENT
= Transaction associated with order

STOCK
= Current inventory quantity

ADD TO CART
= Validate product + quantity

CREATE ORDER
= Validate cart + recheck stock + snapshot + deduct stock + clear cart

CANCEL ORDER
= Change status + restore stock

CREATE PAYMENT
= Validate order + owner + state + duplicate + use trusted order amount

409
= Request conflicts with current resource/business state

Foreign Key
= Relationship enforcing referential integrity

Transaction
= Multiple changes treated as one logical unit
```

---

# 199. One Complete Business Diagram To Remember

```text
                 AUTHENTICATED USER
                        │
                        ▼
                   GET PRODUCTS
                        │
                        ▼
                  ADD TO CART
                        │
              Validate stock/quantity
                        │
                        ▼
                   CART ITEMS
                        │
                        ▼
                  CREATE ORDER
                        │
          ┌─────────────┼─────────────┐
          │             │             │
          ▼             ▼             ▼
     Order Row     Order Items    Deduct Stock
          │                           │
          └─────────────┬─────────────┘
                        ▼
                    Clear Cart
                        │
                        ▼
                   ORDER CREATED
                        │
                        ▼
                  CREATE PAYMENT
                        │
            Validate owner/state
                        │
             Duplicate payment?
                        │
                        ▼
                  Trusted amount
                        │
                        ▼
                  Transaction ID
                        │
                        ▼
                     SUCCESS
```

---

# 200. Current Learning Position

We now understand the complete backend business journey:

```text
User
 ↓
Authentication
 ↓
Authorization
 ↓
Products
 ↓
Cart
 ↓
Orders
 ↓
Inventory
 ↓
Cancellation
 ↓
Payments
```

The next major section is our SDET side:

```text
API AUTOMATION FRAMEWORK
        ↓
REST Assured
        ↓
TestNG
        ↓
RequestSpecFactory
        ↓
Client Layer
        ↓
BaseTest
        ↓
AuthHelper
        ↓
Test Data
        ↓
Cleanup
        ↓
Functional / Validation / Security Tests
```

This is where we move from:

```text
"How the application works"
```

to:

```text
"How a Senior SDET designs automation around the application."
```
---

# PART 41 — API AUTOMATION FRAMEWORK

# 201. Why Did We Build a Separate API Automation Framework?

Backend application ek system hai.

Automation framework doosra system hai jo backend ko externally test karta hai.

Architecture:

```text
SDET-Commerce-Automation
│
├── backend/
│      ↓
│   Actual application
│
└── api-automation/
       ↓
    Independent test framework
```

Our automation behaves like a real API consumer.

It sends HTTP requests to the running backend and validates responses.

This is important because we are testing the application from outside rather than directly calling Java service methods.

---

# 202. Black-Box Testing Idea

Our API automation primarily treats backend APIs as externally accessible interfaces.

Test knows:

```text
Endpoint
HTTP Method
Request
Authentication
Expected Response
```

It does not need to directly call:

```text
ProductService
OrderService
PaymentService
```

Example:

```text
REST Assured
     ↓
POST /api/orders
     ↓
Running Spring Boot Application
```

This makes the framework more realistic and portable.

---

# 203. Why REST Assured?

REST Assured is a Java library designed for REST API testing.

It helps us:

```text
Send HTTP requests
Add headers
Add JWT
Send JSON body
Read JSON response
Validate status codes
Validate response body
Apply reusable request specifications
Attach filters
Validate JSON schema
```

Basic conceptual example:

```java
given()
    .baseUri("http://localhost:8080")
    .contentType("application/json")
.when()
    .get("/api/products")
.then()
    .statusCode(200);
```

---

# 204. REST Assured Mental Model

Remember:

```text
GIVEN
= request setup

WHEN
= perform action

THEN
= validate response
```

Example:

```text
GIVEN
JWT + request body

WHEN
POST /api/products

THEN
status = 201
```

---

# 205. Why Not Repeat given() Everywhere?

Beginner framework:

```java
given()
 .baseUri(...)
 .contentType(...)
 .header(...)
```

inside every test.

Then again:

```java
given()
 .baseUri(...)
 .contentType(...)
 .header(...)
```

in another test.

Problems:

```text
Duplication
Hardcoded configuration
Difficult maintenance
Inconsistent reporting
```

Therefore we introduced reusable framework components.

---

# PART 42 — TESTNG

# 206. What Is TestNG?

TestNG is our Java test execution framework.

REST Assured sends API requests.

TestNG manages the tests.

Easy distinction:

```text
REST Assured
= API communication/testing library

TestNG
= Test runner + lifecycle framework
```

---

# 207. What Does TestNG Give Us?

TestNG provides:

```text
@Test
@BeforeClass
@AfterClass
Assertions
Test grouping
Test lifecycle
Test execution
Reporting integration
```

Example:

```java
@Test
public void createProductTest() {
    ...
}
```

---

# 208. @BeforeClass

`@BeforeClass` runs setup before test methods in that test class.

Our BaseTest uses it to prepare common dependencies.

Concept:

```text
Test Class Starts
       ↓
@BeforeClass
       ↓
Login
       ↓
Get tokens
       ↓
Initialize clients
       ↓
@Test
       ↓
@Test
       ↓
...
```

This avoids repeating login/client initialization inside every test.

---

# 209. Maven + TestNG Flow

When we run:

```bash
mvn clean test
```

conceptually:

```text
Maven
  ↓
Surefire Plugin
  ↓
TestNG
  ↓
@BeforeClass
  ↓
@Test methods
  ↓
Listeners / Allure
  ↓
Results
```

Maven is not itself our test framework.

Maven starts the test execution process.

TestNG executes the test classes.

---

# PART 43 — FRAMEWORK PACKAGE STRUCTURE

# 210. API Automation Structure

Conceptually:

```text
api-automation/
│
├── src/test/java/com/sdetcommerce/api/
│   │
│   ├── client/
│   ├── config/
│   ├── database/
│   ├── filters/
│   ├── listeners/
│   ├── model/
│   ├── tests/
│   └── utils/
│
├── src/test/resources/
│   └── schemas/
│
├── .env
├── .env.example
├── pom.xml
└── run-tests-local.sh
```

Each package has a responsibility.

---

# 211. client/

Contains API-specific communication classes.

Examples:

```text
ProductClient
CartClient
OrderClient
PaymentClient
```

Their job:

```text
Build/send API request
Return response
```

They should not contain test assertions for every scenario.

---

# 212. config/

Contains framework configuration.

Important classes:

```text
ApiConfig
RequestSpecFactory
```

Responsibilities:

```text
Environment
Base URL
Credentials
Reusable REST Assured request setup
```

---

# 213. database/

Contains database validation helpers and records.

Examples conceptually:

```text
Product DB helper
Payment DB helper
ProductDbRecord
PaymentDbRecord
```

Purpose:

```text
API action
   ↓
SQL query
   ↓
Map DB result
   ↓
Test assertion
```

---

# 214. filters/

Contains REST Assured filters.

Important:

```text
SanitizedAllureFilter
```

This intercepts API requests/responses for secure Allure evidence.

We will study this separately.

---

# 215. listeners/

Contains TestNG listeners.

Important:

```text
AllureEnvironmentListener
```

It creates environment metadata for reports.

---

# 216. model/

Contains request/response models or POJOs used by automation.

Example:

```text
PaymentRequest
```

Instead of constructing every request as raw JSON strings, models provide structured Java objects.

---

# 217. tests/

Contains actual TestNG test classes.

Examples:

```text
LoginTests
ProductCrudTests
ProductValidationTests
ProductSecurityTests
ProductRbacTests

CartCrudTests
CartValidationTests
CartSecurityTests
CartDatabaseTests

OrderFlowTests
OrderCancellationTests
OrderValidationTests
OrderSecurityTests
OrderDatabaseTests

PaymentFlowTests
PaymentValidationTests
PaymentSecurityTests
PaymentDatabaseTests
```

---

# 218. utils/

Contains reusable supporting utilities.

Examples:

```text
AuthHelper
TestDataFactory
TestDataCleanup
```

Purpose:

```text
Avoid repeating supporting logic across tests.
```

---

# PART 44 — ApiConfig

# 219. Why Do We Need ApiConfig?

Bad framework:

```java
String baseUrl = "http://localhost:8080";
String email = "user@test.com";
String password = "...";
```

inside multiple classes.

Problems:

```text
Hardcoding
Duplication
Security risk
Environment switching difficult
```

Instead:

```text
Environment Variables
        ↓
ApiConfig
        ↓
Framework Components
```

---

# 220. ApiConfig Responsibilities

Our ApiConfig handles values such as:

```text
TEST_ENV
BASE_URL

QA_BASE_URL
STAGE_BASE_URL

TEST_EMAIL
TEST_PASSWORD

ADMIN_EMAIL
ADMIN_PASSWORD
```

It provides centralized getters.

Conceptually:

```java
ApiConfig.getBaseUrl()
ApiConfig.getTestEmail()
ApiConfig.getTestPassword()
ApiConfig.getAdminEmail()
ApiConfig.getAdminPassword()
```

---

# 221. Environment Resolution

Current environment model:

```text
TEST_ENV=local
```

Possible environments:

```text
local
qa
stage
```

Conceptual logic:

```text
Is BASE_URL explicitly provided?
        │
        ├── YES
        │     ↓
        │  Use BASE_URL
        │
        └── NO
              ↓
          Read TEST_ENV
              ↓
      ┌───────┼────────┐
      │       │        │
    local     qa      stage
      │       │        │
      ▼       ▼        ▼
 localhost  QA URL   STAGE URL
```

---

# 222. Why Explicit BASE_URL Override?

Sometimes we may want to test a specific temporary deployment.

Example:

```text
TEST_ENV=qa
```

but explicitly provide:

```text
BASE_URL=https://feature-123.example.com
```

Explicit override gives flexibility.

---

# 223. Required Environment Validation

If:

```text
TEST_ENV=qa
```

but:

```text
QA_BASE_URL
```

does not exist, silently falling back can be dangerous.

A test might accidentally execute against the wrong environment.

Better:

```text
Missing required configuration
        ↓
Fail fast
```

This is why required environment values are validated.

---

# 224. Why Fail Fast?

Imagine intending to test QA but configuration is missing.

Bad framework:

```text
QA missing
   ↓
silently uses localhost
   ↓
tests pass
   ↓
team thinks QA is healthy
```

Very dangerous.

Better:

```text
QA missing
   ↓
framework fails immediately
   ↓
configuration problem is obvious
```

---

# PART 45 — RequestSpecFactory

# 225. What Is RequestSpecification?

REST Assured requests often share common configuration:

```text
Base URI
Content-Type
Headers
Filters
```

A `RequestSpecification` packages reusable request configuration.

---

# 226. Why RequestSpecFactory?

Instead of this everywhere:

```java
given()
    .baseUri(ApiConfig.getBaseUrl())
    .contentType(ContentType.JSON)
```

we centralize it.

Our factory provides:

```text
getBaseSpec()
getAuthenticatedSpec(token)
```

---

# 227. getBaseSpec()

Used for requests that do not require JWT.

Example:

```text
Login
```

Conceptually:

```java
new RequestSpecBuilder()
    .setBaseUri(ApiConfig.getBaseUrl())
    .setContentType(ContentType.JSON)
    .addFilter(reportingFilter)
    .build();
```

---

# 228. getAuthenticatedSpec(token)

Used for protected APIs.

Adds:

```http
Authorization: Bearer <token>
```

Conceptually:

```text
Base URL
+
Content-Type JSON
+
Authorization Header
+
Reporting Filter
```

---

# 229. RequestSpecFactory Flow

```text
Test
 ↓
Client
 ↓
RequestSpecFactory
 ↓
ApiConfig.getBaseUrl()
 ↓
Add headers
 ↓
Add SanitizedAllureFilter
 ↓
REST Assured Request
```

This centralization is extremely useful.

If tomorrow we need:

```text
New common header
Different reporting filter
Different authentication behavior
```

we can update one place.

---

# 230. Factory Design Benefit

Without factory:

```text
100 tests
×
same configuration
```

With factory:

```text
100 tests
      ↓
1 RequestSpecFactory
```

This follows:

```text
DRY
```

DRY means:

```text
Don't Repeat Yourself
```

---

# PART 46 — AUTH HELPER

# 231. Why AuthHelper?

Protected API tests require JWT.

We could manually login in every test:

```text
POST login
Extract token
Use token
```

But that creates duplication.

Therefore:

```text
AuthHelper
```

centralizes authentication support.

---

# 232. AuthHelper Flow

Normal user:

```text
AuthHelper.getAuthToken()
        ↓
Read TEST_EMAIL
        ↓
Read TEST_PASSWORD
        ↓
POST /api/users/login
        ↓
Extract token
        ↓
Return JWT
```

Admin:

```text
AuthHelper.getAdminAuthToken()
        ↓
Read ADMIN_EMAIL
        ↓
Read ADMIN_PASSWORD
        ↓
POST /api/users/login
        ↓
Extract admin JWT
```

---

# 233. Why AuthHelper Uses getBaseSpec()

Login is public.

Therefore login should NOT require an existing JWT.

Flow:

```text
AuthHelper
    ↓
RequestSpecFactory.getBaseSpec()
    ↓
POST login
```

Then protected requests use:

```text
getAuthenticatedSpec(token)
```

---

# PART 47 — BASE TEST

# 234. Why BaseTest?

Many test classes need:

```text
USER token
ADMIN token
ProductClient
CartClient
OrderClient
PaymentClient
```

Instead of repeating setup, common initialization lives in:

```text
BaseTest
```

---

# 235. Current BaseTest Concept

```java
public class BaseTest {

    protected String token;
    protected String adminToken;

    protected ProductClient productClient;
    protected CartClient cartClient;
    protected OrderClient orderClient;
    protected PaymentClient paymentClient;

    @BeforeClass
    public void setUp() {
        token = AuthHelper.getAuthToken();
        adminToken = AuthHelper.getAdminAuthToken();

        productClient = new ProductClient();
        cartClient = new CartClient();
        orderClient = new OrderClient();
        paymentClient = new PaymentClient();
    }
}
```

---

# 236. Test Inheritance

Test classes can extend:

```java
BaseTest
```

Concept:

```text
BaseTest
  │
  ├── ProductCrudTests
  ├── ProductRbacTests
  ├── CartCrudTests
  ├── OrderFlowTests
  └── PaymentFlowTests
```

They inherit common protected fields.

---

# 237. Why Not Put All Tests in BaseTest?

BaseTest should provide common setup.

It should NOT become:

```text
One giant class containing all tests.
```

Test classes should remain domain-focused.

Good:

```text
ProductCrudTests
ProductSecurityTests
CartDatabaseTests
OrderValidationTests
```

This improves:

```text
Readability
Maintenance
Failure diagnosis
Ownership
Reporting
```

---

# PART 48 — API CLIENT LAYER

# 238. Why API Client Classes?

A test should express business intent.

Better:

```text
productClient.createProduct(...)
```

than repeating full REST Assured request construction in every test.

Client classes hide low-level HTTP plumbing.

---

# 239. ProductClient

Conceptually supports:

```text
Get products
Get product
Search products
Create product
Update product
Delete product
```

Test:

```text
ProductCrudTests
      ↓
ProductClient
      ↓
REST Assured
      ↓
Product API
```

---

# 240. CartClient

Supports:

```text
Add item
Get cart
Update quantity
Remove item
Clear cart
```

---

# 241. OrderClient

Supports:

```text
Create order
Get order
List orders
Cancel order
```

---

# 242. PaymentClient

Supports:

```text
Create payment
Get payment by order
```

---

# 243. Why Clients Should Return Response

A useful client design returns REST Assured:

```java
Response
```

to the test.

Then test decides what to assert.

Concept:

```text
Client
= How to call API

Test
= What behavior to verify
```

This separation is important.

---

# 244. Example Separation

Client:

```text
createProduct(...)
    ↓
POST request
    ↓
return Response
```

Test:

```text
Response response =
    productClient.createProduct(...);

Assert status
Assert body
Assert DB
```

The client should not decide every expected result because same API can intentionally produce:

```text
201
400
401
403
409
```

depending on scenario.

---

# PART 49 — MODELS / POJOs

# 245. Why Request Models?

Instead of raw JSON everywhere:

```java
String body = "{ ... }";
```

we can use Java objects.

Example concept:

```java
PaymentRequest request =
    new PaymentRequest(orderId, "CARD");
```

REST Assured/Jackson can serialize it into JSON.

---

# 246. Serialization

Serialization means:

```text
Java Object
      ↓
JSON
```

Example:

```text
PaymentRequest Java object
      ↓
Jackson
      ↓
{
  "orderId": 10,
  "paymentMethod": "CARD"
}
```

---

# 247. Deserialization

Deserialization is the reverse:

```text
JSON
 ↓
Java Object
```

Useful when we want strongly typed response models.

---

# 248. Why Models Are Better Than Raw Strings

Benefits:

```text
Compile-time structure
Cleaner tests
Reusable data
Less JSON syntax duplication
Easier refactoring
```

But raw JSON can still be useful for specific malformed-payload negative tests.

---

# PART 50 — TEST DATA FACTORY

# 249. Why TestDataFactory?

Automation repeatedly creates data.

If every test uses:

```text
Product name = Test Product
```

parallel/repeated runs may conflict.

Instead generate unique test data.

Example:

```text
Test Product 1723456789
```

---

# 250. Unique Product Names

Our TestDataFactory provides unique product names.

Concept:

```text
Base Name
   +
Unique suffix
   ↓
Unique Product Name
```

This reduces collisions between test executions.

---

# 251. Why Unique Test Data Matters?

Suppose test expects product creation to succeed.

Previous run left:

```text
Automation Product
```

and application has uniqueness constraint.

Next run:

```text
Create same product
        ↓
Unexpected failure
```

Unique data makes tests more independent.

---

# PART 51 — TEST DATA CLEANUP

# 252. Why Cleanup?

Automated tests create data:

```text
Products
Cart items
Orders
Order items
Payments
```

If not cleaned:

```text
Database grows
Tests influence each other
Duplicate conflicts happen
Stock changes remain
Debugging becomes difficult
```

---

# 253. TestDataCleanup

We created cleanup utilities for domain data.

Concept:

```text
Test creates data
      ↓
Assertions
      ↓
Cleanup
      ↓
Environment returned to predictable state
```

---

# 254. Cleanup Must Respect Foreign Keys

Remember:

```text
Payment → Order
OrderItem → Order
```

Therefore:

```text
delete payment
      ↓
delete order items
      ↓
delete order
```

may be required.

Trying parent first may produce:

```text
Foreign Key Constraint Violation
```

---

# 255. Cleanup vs Test Independence

Good automation should minimize dependency such as:

```text
Test B requires Test A to run first.
```

Better:

```text
Test A creates what it needs
Test A validates
Test A cleans

Test B creates what it needs
Test B validates
Test B cleans
```

This improves:

```text
Independent execution
Parallel readiness
Failure diagnosis
Repeatability
```

---

# PART 52 — API TEST DESIGN

# 256. Arrange — Act — Assert

A useful test structure:

```text
ARRANGE
Prepare test data

ACT
Call API

ASSERT
Validate result
```

Example:

```text
ARRANGE
Create admin token/product payload

ACT
POST /api/products

ASSERT
201
response body
database state
```

---

# 257. Test Name Should Explain Behavior

Weak:

```text
test1()
testProduct()
```

Better:

```text
shouldCreateProductAsAdmin()
shouldRejectProductCreationForNormalUser()
shouldRejectOrderWhenCartIsEmpty()
```

Even if exact naming differs, principle remains:

```text
Test name should communicate behavior.
```

---

# PART 53 — OUR TEST SUITE ORGANIZATION

# 258. Login

```text
LoginTests
```

Focus:

```text
Authentication behavior
```

---

# 259. Product Tests

```text
ProductCrudTests
ProductDatabaseTests
ProductSearchTests
ProductSecurityTests
ProductValidationTests
ProductRbacTests
```

This separation tells us immediately what type of failure occurred.

---

# 260. Cart Tests

```text
CartCrudTests
CartDatabaseTests
CartSecurityTests
CartValidationTests
```

---

# 261. Order Tests

```text
OrderFlowTests
OrderCancellationTests
OrderDatabaseTests
OrderSecurityTests
OrderValidationTests
```

---

# 262. Payment Tests

```text
PaymentFlowTests
PaymentDatabaseTests
PaymentSecurityTests
PaymentValidationTests
```

---

# 263. Why Separate Tests By Concern?

Imagine report says:

```text
ProductSecurityTests failed
```

Immediately we know:

```text
Likely security/authorization issue
```

Instead of searching through one massive:

```text
ProductTests.java
```

This improves maintainability and reporting.

---

# PART 54 — COMPLETE AUTOMATION REQUEST FLOW

# 264. Example: Admin Creates Product

```text
ProductRbacTests
       ↓
adminToken from BaseTest
       ↓
ProductClient
       ↓
RequestSpecFactory.getAuthenticatedSpec(adminToken)
       ↓
ApiConfig.getBaseUrl()
       ↓
REST Assured
       ↓
Authorization: Bearer <ADMIN JWT>
       ↓
POST /api/products
       ↓
Spring Boot
       ↓
JwtAuthenticationFilter
       ↓
ROLE_ADMIN
       ↓
SecurityConfig
       ↓
ProductController
       ↓
ProductService
       ↓
ProductRepository
       ↓
PostgreSQL
       ↓
HTTP 201
       ↓
REST Assured Response
       ↓
Test Assertions
```

This diagram connects our backend and automation architecture.

---

# 265. Example: USER Attempts Product Creation

```text
ProductRbacTests
       ↓
token
       ↓
ProductClient
       ↓
Authenticated Request Spec
       ↓
POST /api/products
       ↓
Spring Security
       ↓
JWT valid
       ↓
ROLE_USER
       ↓
Endpoint requires ADMIN
       ↓
403 Forbidden
       ↓
REST Assured
       ↓
Assert 403
```

Notice:

```text
Controller may never execute.
```

Security rejects request before business controller processing.

---

# PART 55 — run-tests-local.sh

# 266. Why Did We Create a Test Runner Script?

Instead of manually exporting every variable each time:

```bash
export TEST_ENV=...
export TEST_EMAIL=...
...
mvn clean test
```

we created:

```text
run-tests-local.sh
```

---

# 267. Current Concept

```bash
#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

set -a
source "$SCRIPT_DIR/.env"
set +a

cd "$SCRIPT_DIR"

mvn clean test
```

---

# 268. What Does set -e Mean?

```bash
set -e
```

tells the shell to stop when an unhandled command fails.

This helps prevent continuing execution after important setup failures.

---

# 269. Why SCRIPT_DIR?

If script assumes current terminal directory, it may fail when called from somewhere else.

Using script directory makes it more robust.

Concept:

```text
Wherever user runs script from
        ↓
Determine script's own location
        ↓
Load .env from correct module
        ↓
Run Maven from correct module
```

---

# 270. Local Test Command

From:

```text
api-automation/
```

run:

```bash
./run-tests-local.sh
```

Flow:

```text
Shell Script
    ↓
Load .env
    ↓
Export variables
    ↓
Maven
    ↓
Surefire
    ↓
TestNG
    ↓
BaseTest
    ↓
AuthHelper
    ↓
Clients
    ↓
REST Assured
    ↓
Backend
```

---

# PART 56 — AUTOMATION FRAMEWORK INTERVIEW QUESTIONS

# 271. Q: Explain Your API Automation Framework.

**Answer:**

I built an independent Java API automation framework using REST Assured and TestNG. Configuration is externalized through environment variables and centralized through ApiConfig. RequestSpecFactory manages reusable REST Assured specifications, AuthHelper handles user and admin authentication, and domain-specific client classes encapsulate API communication. Tests are separated by functional, validation, security and database concerns. The framework also supports JDBC validation, JSON schema validation and Allure reporting.

---

# 272. Q: Why Did You Use RequestSpecFactory?

**Answer:**

It centralizes common REST Assured configuration such as the base URI, content type, authentication headers and reporting filters. This avoids duplication and allows common request behavior to be changed in one place.

---

# 273. Q: Why Client Classes?

**Answer:**

Client classes separate HTTP communication from test assertions. The client knows how to call an endpoint, while the test decides what behavior and response should be validated.

---

# 274. Q: What Is BaseTest Used For?

**Answer:**

BaseTest provides common TestNG setup such as retrieving normal-user and admin tokens and initializing reusable API clients. Domain test classes inherit this setup instead of duplicating it.

---

# 275. Q: How Do You Handle Test Data?

**Answer:**

I generate unique data where collisions are possible and use dedicated cleanup utilities to remove test-created data. Cleanup also respects foreign-key relationships, especially for orders, order items and payments.

---

# 276. Q: Why TestNG?

**Answer:**

TestNG provides the test lifecycle, annotations, assertions and suite execution capabilities, while REST Assured handles HTTP API interaction. Maven Surefire integrates the TestNG suite into the build.

---

# 277. Q: How Does Your Framework Support Different Environments?

**Answer:**

The environment is externalized through variables such as TEST_ENV and environment-specific base URLs. ApiConfig resolves the correct target at runtime, while RequestSpecFactory consumes that configuration. The tests themselves do not hardcode environment URLs.

---

# PART 57 — FRAMEWORK CHEAT SHEET

```text
REST Assured
= Sends/tests HTTP APIs

TestNG
= Executes and manages tests

Maven
= Build/dependency tool

Surefire
= Maven test execution plugin

ApiConfig
= Environment/configuration source

RequestSpecFactory
= Reusable REST Assured request configuration

AuthHelper
= Logs in and provides JWT

BaseTest
= Common test setup

ProductClient
= Product API communication

CartClient
= Cart API communication

OrderClient
= Order API communication

PaymentClient
= Payment API communication

Model / POJO
= Structured request/response data

TestDataFactory
= Generates reusable/unique test data

TestDataCleanup
= Removes test-created state

@BeforeClass
= Setup before test methods in class

@Test
= Test method

DRY
= Don't Repeat Yourself

Serialization
= Java Object → JSON

Deserialization
= JSON → Java Object
```

---

# 278. Framework Architecture To Memorize

```text
                    TEST CLASS
                        │
                        ▼
                    BaseTest
                   /        \
                  /          \
             USER JWT      ADMIN JWT
                  \          /
                   \        /
                    ▼      ▼
                    API Client
                        │
                        ▼
                RequestSpecFactory
                    /         \
                   /           \
              ApiConfig    Auth Header
                   \           /
                    \         /
                     ▼       ▼
                    REST Assured
                         │
                         ▼
                 Spring Boot API
                         │
                         ▼
                    PostgreSQL
                         │
                         ▼
                    API Response
                         │
                         ▼
                  Test Assertions
```

---

# 279. Where We Are Now

We now understand:

```text
Application Architecture
        ↓
Security
        ↓
Commerce Business Logic
        ↓
API Automation Architecture
        ↓
REST Assured
        ↓
TestNG
        ↓
Configuration
        ↓
Request Specifications
        ↓
Authentication Helper
        ↓
API Clients
        ↓
BaseTest
        ↓
Test Data
        ↓
Cleanup
```

Next we will go deeper into the testing layers that make the framework stronger than basic API automation:

```text
POSITIVE TESTING
        ↓
NEGATIVE TESTING
        ↓
VALIDATION TESTING
        ↓
SECURITY TESTING
        ↓
RBAC TESTING
        ↓
JSON SCHEMA VALIDATION
        ↓
JDBC DATABASE VALIDATION
```

The key question changes from:

```text
"Can my framework call the API?"
```

to:

```text
"How do I prove the complete system behaves correctly?"
```
---

# PART 58 — API TESTING LAYERS

# 280. API Testing Is More Than Status Code Validation

A basic API test may do only this:

```text
Send Request
    ↓
Check Status Code
```

Example:

```text
POST /api/products
        ↓
201 Created
```

But 201 alone does NOT prove everything is correct.

A stronger test asks:

```text
Was correct HTTP status returned?
Was response body correct?
Were business rules followed?
Was response contract correct?
Was data actually persisted?
Was access control enforced?
Were related database records updated correctly?
```

Therefore our framework uses multiple validation layers.

---

# 281. Our Validation Layers

```text
                  API REQUEST
                       │
                       ▼
              ┌─────────────────┐
              │ Status Code     │
              └────────┬────────┘
                       ▼
              ┌─────────────────┐
              │ Response Body   │
              └────────┬────────┘
                       ▼
              ┌─────────────────┐
              │ Business Rules  │
              └────────┬────────┘
                       ▼
              ┌─────────────────┐
              │ JSON Schema     │
              └────────┬────────┘
                       ▼
              ┌─────────────────┐
              │ Security / RBAC │
              └────────┬────────┘
                       ▼
              ┌─────────────────┐
              │ Database        │
              └─────────────────┘
```

Each layer answers a different question.

---

# PART 59 — POSITIVE TESTING

# 282. What Is Positive Testing?

Positive testing verifies that the system works correctly when valid input and valid conditions are provided.

Simple meaning:

```text
Valid Input
+
Valid User
+
Valid Business State
        ↓
Expected Success
```

---

# 283. Product Positive Example

```text
ADMIN token
+
Valid Product
        ↓
POST /api/products
        ↓
201 Created
```

Then validate:

```text
Product ID generated
Name correct
Price correct
Stock correct
Product persisted
```

---

# 284. Cart Positive Example

```text
Authenticated User
        ↓
Valid Product
        ↓
Available Stock
        ↓
Valid Quantity
        ↓
Add To Cart
        ↓
Success
```

---

# 285. Order Positive Example

```text
Authenticated User
        ↓
Non-empty Cart
        ↓
Sufficient Stock
        ↓
Create Order
        ↓
Order CREATED
        ↓
Stock Reduced
        ↓
Cart Cleared
```

---

# 286. Payment Positive Example

```text
Valid User
        ↓
Own Order
        ↓
Order Not Cancelled
        ↓
No Existing Payment
        ↓
Create Payment
        ↓
201 Created
        ↓
SUCCESS
```

---

# PART 60 — NEGATIVE TESTING

# 287. What Is Negative Testing?

Negative testing checks how the system behaves with invalid input, invalid state or forbidden operations.

Simple idea:

```text
What happens when something is wrong?
```

Examples:

```text
Missing field
Invalid quantity
Empty cart
Insufficient stock
Duplicate payment
Cancelled order
Missing authentication
Wrong role
```

---

# 288. Why Negative Testing Is Important

Production users do not always follow the perfect happy path.

Requests may contain:

```text
Wrong data
Missing data
Unexpected state
Tampered data
Unauthorized operations
```

A reliable API should reject invalid operations safely and predictably.

---

# 289. Negative Test Example

Suppose:

```text
Available stock = 5
```

Request:

```text
Quantity = 10
```

Expected:

```text
Request rejected
Stock unchanged
No invalid order created
```

Notice that we should test both:

```text
Response
AND
System state
```

---

# PART 61 — VALIDATION TESTING

# 290. What Is Input Validation?

Input validation ensures incoming data follows expected rules before business processing or persistence.

Examples:

```text
Required field
Non-blank value
Positive quantity
Valid price
Valid email
Valid payment method
```

---

# 291. Why Backend Validation Matters

Never assume frontend validation is enough.

Frontend may prevent:

```text
quantity = -10
```

but API can still be called directly using:

```text
REST Assured
Postman
curl
another service
malicious client
```

Therefore backend must independently validate requests.

---

# 292. Frontend Validation vs Backend Validation

```text
Frontend Validation
= Better user experience

Backend Validation
= Actual system protection
```

Frontend can be bypassed.

Backend cannot trust the client.

---

# 293. Validation Test Pattern

```text
Create invalid request
        ↓
Send request
        ↓
Verify error status
        ↓
Verify error response
        ↓
Verify invalid data was not persisted
```

---

# PART 62 — SECURITY TESTING

# 294. Security Testing In Our Framework

Our API security tests validate authentication and authorization boundaries.

Important categories:

```text
No authentication
Invalid authentication
Wrong role
Wrong resource ownership
```

---

# 295. Missing Token

Example:

```text
GET protected endpoint
```

without:

```text
Authorization
```

Expected:

```text
401
```

Meaning:

```text
Authentication could not be established.
```

---

# 296. Wrong Role

Example:

```text
ROLE_USER
        ↓
POST /api/products
        ↓
ADMIN required
        ↓
403
```

This validates authorization.

---

# 297. Resource Ownership

More subtle security problem:

```text
User A
```

is authenticated.

But User A tries to access:

```text
User B's order
```

Authentication succeeds.

Role may also be valid.

But resource ownership is wrong.

Therefore:

```text
Authenticated
≠
Allowed to access every resource
```

---

# 298. Three Security Questions

For a protected business request, think:

```text
1. Is the caller authenticated?

2. Does the caller have the required role?

3. Does the caller own/have permission for this resource?
```

These are different checks.

---

# PART 63 — RBAC TESTING

# 299. RBAC Test Matrix

Product example:

```text
                         USER       ADMIN

GET products              PASS       PASS
POST product              403        PASS
PUT product               403        PASS
DELETE product            403        PASS
```

A good RBAC test suite checks both sides.

Not only:

```text
ADMIN succeeds
```

but also:

```text
USER is rejected
```

---

# 300. Why Positive + Negative RBAC?

Suppose ADMIN creation works.

That proves:

```text
Admin can create product.
```

It does NOT prove:

```text
Normal user cannot create product.
```

Both tests are required.

---

# 301. ProductRbacTests

Our framework contains:

```text
ProductRbacTests
```

An important pair of scenarios:

```text
USER token
+
POST Product
        ↓
403
```

and:

```text
ADMIN token
+
POST Product
        ↓
201
```

This proves privilege separation.

---

# PART 64 — RESPONSE VALIDATION

# 302. What Should We Validate In API Response?

Depending on scenario:

```text
HTTP Status
Headers
Content Type
Response Fields
Field Values
Field Types
Required Fields
Error Message
Business Status
Identifiers
```

---

# 303. Status Code Validation

Examples:

```text
200 OK
201 Created
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
```

Status code should match API behavior.

But status code alone is not enough.

---

# 304. Response Body Validation

Suppose payment returns:

```json
{
  "status": "SUCCESS",
  "transactionId": "..."
}
```

We should validate important values such as:

```text
status = SUCCESS
transactionId exists
correct order association
correct amount
```

---

# PART 65 — JSON SCHEMA VALIDATION

# 305. What Is JSON Schema?

JSON Schema defines the expected structure of a JSON document.

It can describe:

```text
Required fields
Field types
Nested objects
Arrays
Allowed values
Patterns
```

---

# 306. Why Schema Validation?

Suppose expected API response is:

```json
{
  "id": 10,
  "name": "Keyboard",
  "price": 2000
}
```

A normal assertion may only check:

```text
name = Keyboard
```

But API accidentally changes:

```text
"id": "10"
```

from number to string.

Business value assertion might still miss this.

Schema validation can detect contract changes.

---

# 307. Functional Validation vs Schema Validation

Very important difference:

```text
Functional Assertion
= Is the VALUE correct?

Schema Validation
= Is the STRUCTURE / TYPE correct?
```

Example:

```text
price = 2000
```

Functional test asks:

```text
Is price actually 2000?
```

Schema asks:

```text
Is price a valid expected data type?
```

We need both where appropriate.

---

# 308. Example Schema Concept

```json
{
  "type": "object",
  "required": ["id", "name", "price", "stock"],
  "properties": {
    "id": {
      "type": "integer"
    },
    "name": {
      "type": "string"
    },
    "price": {
      "type": "number"
    },
    "stock": {
      "type": "integer"
    }
  }
}
```

Meaning:

```text
Response must be object

id
→ integer

name
→ string

price
→ number

stock
→ integer
```

---

# 309. JSON Schema Validation Flow

```text
REST Assured Response
        ↓
JSON Schema Validator
        ↓
Load expected schema
        ↓
Compare response structure
        ↓
MATCH?
   │
   ├── YES → contract valid
   └── NO  → test fails
```

---

# 310. What Schema Validation Does NOT Prove

Schema can say:

```text
price must be a number
```

But:

```text
price = 1
```

may still satisfy schema even if expected business price was:

```text
5000
```

Therefore:

```text
Schema validation
≠
Business validation
```

This is a common interview question.

---

# PART 66 — DATABASE VALIDATION

# 311. Why Database Validation?

An API may return:

```text
201 Created
```

but persistence could still be wrong.

Example:

```text
Response says stock = 10
```

while DB accidentally stores:

```text
stock = 0
```

API response validation alone may not detect this immediately.

Database validation verifies actual persistence.

---

# 312. API vs DB Validation

Example:

```text
POST /api/products
        ↓
201 Created
        ↓
Response:
name = Keyboard
stock = 10
```

Then:

```sql
SELECT ...
FROM products
WHERE id = ?;
```

Verify:

```text
DB name = Keyboard
DB stock = 10
```

Now we have stronger confidence.

---

# PART 67 — JDBC

# 313. What Is JDBC?

JDBC means:

```text
Java Database Connectivity
```

It is the standard Java API for communicating with relational databases.

Our automation uses JDBC to directly query PostgreSQL for validation.

---

# 314. Automation DB Flow

```text
Test
  ↓
API Client
  ↓
Spring Boot API
  ↓
PostgreSQL
  ↓
API Response
  ↓
Test captures ID
  ↓
JDBC helper
  ↓
SQL query
  ↓
PostgreSQL
  ↓
DB record
  ↓
Test compares API and DB
```

---

# 315. JDBC Connection Concept

Conceptually:

```java
Connection connection =
    DriverManager.getConnection(
        databaseUrl,
        username,
        password
    );
```

Then:

```text
Connection
    ↓
PreparedStatement
    ↓
Execute SQL
    ↓
ResultSet
```

---

# 316. What Is Connection?

A JDBC:

```text
Connection
```

represents an active connection between our Java automation process and PostgreSQL.

---

# 317. What Is PreparedStatement?

PreparedStatement allows parameterized SQL.

Concept:

```sql
SELECT id, name, price, stock
FROM products
WHERE id = ?
```

Then:

```text
? = productId
```

This is better than blindly concatenating values into SQL strings.

---

# 318. Why PreparedStatement?

Benefits:

```text
Safer parameter handling
Cleaner SQL
Reduced SQL injection risk
Correct type binding
```

---

# 319. What Is ResultSet?

Database query returns rows.

JDBC represents query results using:

```text
ResultSet
```

Concept:

```text
SQL
 ↓
PostgreSQL
 ↓
Rows
 ↓
ResultSet
 ↓
Java reads columns
```

Example:

```text
id
name
price
stock
```

---

# PART 68 — DATABASE RECORD OBJECTS

# 320. Why ProductDbRecord?

Instead of returning random values or generic maps from database helpers, we use structured records/models.

Concept:

```text
SQL Result
   ↓
ProductDbRecord
   │
   ├── id
   ├── name
   ├── price
   └── stock
```

Then test can clearly access:

```text
record.id()
record.name()
record.price()
record.stock()
```

depending on implementation.

---

# 321. Why Better Than Map?

A generic map might look like:

```java
Map<String, Object>
```

Problems:

```text
Casting
Typos in keys
Less type safety
Less readable
```

Structured DB records make intent clearer.

---

# 322. PaymentDbRecord

Same concept for payment.

```text
payments table
      ↓
SQL
      ↓
PaymentDbRecord
      │
      ├── order information
      ├── amount
      ├── payment method
      ├── status
      └── transaction ID
```

This allows direct persistence validation.

---

# PART 69 — PRODUCT DATABASE TEST

# 323. Product DB Validation Flow

```text
Generate unique product
        ↓
ADMIN creates product
        ↓
API returns 201
        ↓
Capture product ID
        ↓
JDBC query products table
        ↓
ProductDbRecord
        ↓
Compare:
API name vs DB name
API price vs DB price
API stock vs DB stock
```

---

# 324. Why This Test Is Valuable

It validates integration between:

```text
Controller
Service
Repository
Hibernate/JPA
PostgreSQL
```

Even though automation accesses database through JDBC independently.

---

# PART 70 — CART DATABASE TEST

# 325. Cart DB Validation

Example:

```text
Add Product to Cart
        ↓
API Success
        ↓
Query cart_items
        ↓
Verify:
correct user
correct product
correct quantity
```

Then after update:

```text
Update Quantity
        ↓
Query DB again
        ↓
Verify new quantity
```

---

# PART 71 — ORDER DATABASE TEST

# 326. Order DB Validation Is More Powerful

Order affects multiple pieces of state.

A strong test can verify:

```text
Order row created
Order items created
Correct quantities
Correct snapshot values
Product stock reduced
Cart cleared
```

This is not just endpoint testing.

It is workflow validation.

---

# 327. Order Cancellation DB Validation

```text
Create Order
        ↓
Capture stock after order
        ↓
Cancel Order
        ↓
Query order
        ↓
status = CANCELLED
        ↓
Query product
        ↓
stock restored
```

This verifies business consistency.

---

# PART 72 — PAYMENT DATABASE TEST

# 328. Payment DB Validation

Flow:

```text
Create Order
      ↓
Create Payment
      ↓
201
      ↓
Capture payment/order data
      ↓
JDBC query payments
      ↓
PaymentDbRecord
      ↓
Verify:
order
amount
method
status
transaction ID
```

---

# 329. Why Payment Cleanup Happens Before Order Cleanup

Database relationship:

```text
Payment
   ↓
references
   ↓
Order
```

If we try:

```text
DELETE Order
```

while payment still references it, PostgreSQL may reject the operation.

Therefore:

```text
Delete Payment
      ↓
Delete Order Items
      ↓
Delete Order
```

This respects referential integrity.

---

# PART 73 — API RESPONSE VS SCHEMA VS DATABASE

# 330. The Most Important Difference

Suppose we create:

```text
Product = Keyboard
Price = 2000
Stock = 10
```

### Response Validation

Asks:

```text
Did API return:
name = Keyboard?
price = 2000?
stock = 10?
```

### Schema Validation

Asks:

```text
Is name a string?
Is price a number?
Is stock an integer?
Are required fields present?
```

### Database Validation

Asks:

```text
Was Keyboard actually persisted?
Was price actually stored as 2000?
Was stock actually stored as 10?
```

These are three different validation layers.

---

# 331. Easy Memory

```text
RESPONSE
= What did API tell me?

SCHEMA
= Is API contract shaped correctly?

DATABASE
= What did system actually persist?
```

---

# PART 74 — BUSINESS RULE VALIDATION

# 332. Fourth Layer: Business Behavior

There is another important layer.

Example order creation:

```text
Response correct
Schema correct
DB row exists
```

but stock was not reduced.

Still a defect.

Therefore we also validate:

```text
Business side effects
```

Examples:

```text
Order → stock decreases

Cancel order → stock restores

Create order → cart clears

Payment → amount comes from order

Duplicate payment → rejected
```

---

# 333. Complete Validation Model

```text
                    API TEST
                       │
       ┌───────────────┼────────────────┐
       │               │                │
       ▼               ▼                ▼
   RESPONSE          SCHEMA          DATABASE
       │               │                │
       └───────────────┼────────────────┘
                       ▼
                BUSINESS RULES
                       │
                       ▼
                 SECURITY/RBAC
```

A mature test suite combines the appropriate layers depending on risk.

---

# PART 75 — HTTP STATUS CODES

# 334. 200 OK

Typically:

```text
Successful GET
Successful operation returning data
```

Example:

```text
GET /api/products
→ 200
```

---

# 335. 201 Created

Used when a resource is successfully created.

Examples:

```text
Create Product
Create Payment
```

Expected:

```text
201
```

where implemented.

---

# 336. 400 Bad Request

Typically means request is invalid.

Examples:

```text
Missing required field
Invalid quantity
Invalid input format
Validation failure
```

Exact status depends on API contract.

---

# 337. 401 Unauthorized

Meaning:

```text
Authentication missing or invalid.
```

Easy:

```text
Who are you?
```

---

# 338. 403 Forbidden

Meaning:

```text
Authenticated but insufficient permission.
```

Easy:

```text
I know you,
but you cannot do this.
```

---

# 339. 404 Not Found

Resource does not exist.

Example:

```text
GET product ID that does not exist
```

---

# 340. 409 Conflict

Request conflicts with current resource/business state.

Our important examples:

```text
Duplicate payment
Payment against cancelled order
```

These are not necessarily malformed requests.

The request conflicts with current state.

---

# PART 76 — TEST ASSERTION STRATEGY

# 341. Avoid Over-Assertion

Not every test needs to verify every field.

Example:

```text
Security test
```

main purpose:

```text
USER cannot perform ADMIN operation.
```

The critical assertion is:

```text
403
```

We do not need to repeat every product schema assertion in every RBAC test.

---

# 342. Avoid Under-Assertion

Opposite problem:

```text
Create order
→ assert 201 only
```

This is too weak for an important business flow.

For high-risk workflows, verify meaningful side effects.

---

# 343. Risk-Based Assertions

Think:

```text
What could go wrong here?
```

Then choose assertions.

Product creation:

```text
status
important response fields
DB persistence
```

Order:

```text
status
order data
stock
cart
DB
```

RBAC:

```text
status
operation prevented
```

Payment:

```text
status
amount
transaction
duplicate prevention
DB
```

---

# PART 77 — TEST INDEPENDENCE

# 344. Why Tests Should Be Independent

Bad suite:

```text
Test 1 creates product
        ↓
Test 2 assumes product from Test 1
        ↓
Test 3 assumes order from Test 2
```

If Test 1 fails:

```text
Test 2 fails
Test 3 fails
```

Now one defect looks like three defects.

---

# 345. Better Pattern

```text
Each important test
        ↓
Creates/prepares required state
        ↓
Executes behavior
        ↓
Validates
        ↓
Cleans up
```

This improves:

```text
Repeatability
Debugging
Parallel readiness
CI reliability
```

---

# PART 78 — TESTING INTERVIEW QUESTIONS

# 346. Q: What Types of API Testing Did You Implement?

**Answer:**

I implemented functional, negative, validation, security, RBAC, JSON schema and database validation tests. For critical workflows such as orders and payments, I also validate business side effects such as inventory changes, cart cleanup, order state and persisted payment information.

---

# 347. Q: Why Do Database Validation If API Response Is Correct?

**Answer:**

An API response only confirms what the service returned to the caller. It does not independently prove that the correct state was persisted. JDBC validation allows the automation to verify actual database records and important side effects.

---

# 348. Q: What Is the Difference Between Schema and Functional Validation?

**Answer:**

Functional validation checks whether values and business behavior are correct, while schema validation verifies the structural contract such as required fields and data types. A response can satisfy its schema while still containing incorrect business values.

---

# 349. Q: How Do You Validate Security?

**Answer:**

I test authentication, role-based authorization and resource ownership separately. For example, missing authentication should return 401, an authenticated normal user attempting an admin operation should receive 403, and users should not be allowed to manipulate resources belonging to another user.

---

# 350. Q: How Do You Validate Database Data?

**Answer:**

The automation connects independently to PostgreSQL using JDBC. After an API operation, I query the relevant table using parameterized SQL, map the result into typed database records and compare persisted values against the expected API and business state.

---

# 351. Q: What Is PreparedStatement?

**Answer:**

PreparedStatement is a JDBC mechanism for executing parameterized SQL. It separates SQL structure from parameter values, improves type handling and reduces risks associated with manually concatenated SQL.

---

# 352. Q: Why Are Negative Tests Important?

**Answer:**

Negative tests verify that the application safely rejects invalid input, unauthorized operations and invalid business states. They are important because a reliable API must enforce rules, not only process valid happy paths.

---

# 353. Q: How Do You Decide What to Assert?

**Answer:**

I use risk-based assertions. For simple authorization tests the critical assertion may be the HTTP status, while business-critical workflows such as order creation require validation of response data, database persistence and side effects such as stock reduction and cart cleanup.

---

# PART 79 — TESTING CHEAT SHEET

```text
Positive Test
= Valid condition → expected success

Negative Test
= Invalid condition → expected rejection

Validation Test
= Input rules enforced

Security Test
= Authentication / authorization boundaries

RBAC Test
= Role permissions enforced

Response Assertion
= Validate returned API data

JSON Schema
= Validate API contract structure

JDBC
= Java Database Connectivity

PreparedStatement
= Parameterized SQL execution

ResultSet
= Database query result

DB Validation
= Verify persisted system state

Business Validation
= Verify side effects and rules

401
= Missing / invalid authentication

403
= Authenticated but not permitted

404
= Resource not found

409
= Conflict with current state
```

---

# 354. One Testing Diagram To Remember

```text
                     TEST SCENARIO
                          │
                          ▼
                     ARRANGE DATA
                          │
                          ▼
                     SEND REQUEST
                          │
                          ▼
                 ┌───────────────────┐
                 │ HTTP STATUS      │
                 └────────┬──────────┘
                          ▼
                 ┌───────────────────┐
                 │ RESPONSE BODY    │
                 └────────┬──────────┘
                          ▼
                 ┌───────────────────┐
                 │ JSON SCHEMA      │
                 └────────┬──────────┘
                          ▼
                 ┌───────────────────┐
                 │ BUSINESS RULES   │
                 └────────┬──────────┘
                          ▼
                 ┌───────────────────┐
                 │ DATABASE / JDBC  │
                 └────────┬──────────┘
                          ▼
                 ┌───────────────────┐
                 │ SECURITY / RBAC  │
                 └────────┬──────────┘
                          ▼
                       CLEANUP
```

Not every test needs every box.

Choose the layers based on the scenario and risk.

---

# 355. Where We Are Now

We now understand:

```text
REST Assured Framework
        ↓
Functional Testing
        ↓
Negative Testing
        ↓
Input Validation
        ↓
Security Testing
        ↓
RBAC Testing
        ↓
Response Validation
        ↓
JSON Schema Validation
        ↓
JDBC
        ↓
Database Validation
        ↓
Business Side-Effect Validation
```

Next we move into framework engineering:

```text
LOCAL / QA / STAGE
        ↓
Environment Switching
        ↓
.env
        ↓
Secrets Management
        ↓
Allure Reporting
        ↓
Request / Response Evidence
        ↓
Sensitive Data Exposure Problem
        ↓
SanitizedAllureFilter
        ↓
Environment Metadata
```

This section will explain how we turned a working test suite into a safer and more maintainable automation framework.

---

# PART 80 — FRAMEWORK ENGINEERING

# 356. From Working Tests to a Real Framework

At this stage our tests were already able to:

```text
Call APIs
Authenticate users
Test Products
Test Cart
Test Orders
Test Payments
Validate database
Validate security
```

But a professional automation framework needs more than passing tests.

We also need:

```text
Environment management
Secret management
Reusable configuration
Reporting
Debugging evidence
Security of reports
Execution metadata
```

This is the difference between:

```text
"Some automated API tests"
```

and:

```text
"A maintainable automation framework"
```

---

# PART 81 — ENVIRONMENT SWITCHING

# 357. Why Do We Need Multiple Environments?

Real applications usually have environments such as:

```text
LOCAL
DEV
QA
STAGE
PRODUCTION
```

Our framework currently supports the concept of:

```text
LOCAL
QA
STAGE
```

The same test code should be reusable.

Bad design:

```java
String baseUrl =
    "http://localhost:8080";
```

inside test classes.

Better:

```text
Test
 ↓
ApiConfig
 ↓
Environment Configuration
 ↓
Correct Base URL
```

---

# 358. The Goal

We want:

```text
Same Test Code
     │
     ├── LOCAL
     │
     ├── QA
     │
     └── STAGE
```

without changing Java test code.

This is called:

```text
Environment-agnostic test design
```

---

# 359. TEST_ENV

We use:

```text
TEST_ENV
```

to describe the target environment.

Example:

```env
TEST_ENV=local
```

Possible values:

```text
local
qa
stage
```

---

# 360. BASE_URL

We also support:

```text
BASE_URL
```

as an explicit override.

Example:

```env
BASE_URL=http://localhost:8080
```

This gives us two useful concepts:

```text
Environment selection
+
Explicit URL override
```

---

# 361. Base URL Resolution Priority

Our configuration conceptually follows:

```text
Is BASE_URL explicitly provided?
          │
     ┌────┴────┐
     │         │
    YES        NO
     │         │
     ▼         ▼
Use BASE_URL  Read TEST_ENV
                   │
             ┌─────┼──────┐
             │     │      │
           local   qa    stage
             │     │      │
             ▼     ▼      ▼
         localhost QA URL Stage URL
```

Explicit `BASE_URL` wins first.

---

# 362. Why BASE_URL Override Is Useful

Suppose QA normally runs at:

```text
https://qa.example.com
```

but a temporary feature environment exists:

```text
https://feature-123.example.com
```

We should not modify test source code.

We can provide:

```text
BASE_URL=https://feature-123.example.com
```

and run the same suite.

---

# 363. Fail-Fast Configuration

Suppose:

```text
TEST_ENV=qa
```

but:

```text
QA_BASE_URL
```

is missing.

Bad framework behavior:

```text
Silently use another environment.
```

Good framework behavior:

```text
Fail immediately with configuration error.
```

Why?

Because passing tests against the wrong environment are worse than a visible configuration failure.

---

# 364. ApiConfig Responsibility

`ApiConfig` acts as the central configuration layer.

Conceptually:

```text
Environment Variables
        ↓
ApiConfig
        ↓
Base URL
Test Credentials
Admin Credentials
        ↓
Framework
```

Test classes do not need to know how environment resolution works.

---

# PART 82 — LOCAL TEST ENVIRONMENT FILE

# 365. api-automation/.env

Our local automation environment file conceptually contains:

```env
TEST_ENV=local
BASE_URL=http://localhost:8080

TEST_EMAIL=<local-test-user-email>
TEST_PASSWORD=<local-test-user-password>

ADMIN_EMAIL=<local-admin-email>
ADMIN_PASSWORD=<local-admin-password>

DB_USERNAME=<local-database-user>
DB_PASSWORD=<local-database-password>
```

Important:

```text
These are local values.
```

They should NOT be committed.

---

# 366. Why Credentials Are Externalized

Bad:

```java
String password = "actual-password";
```

Better:

```java
System.getenv("TEST_PASSWORD");
```

Conceptually:

```text
Secret
 ↓
Environment
 ↓
ApiConfig
 ↓
AuthHelper
```

The test source code remains secret-free.

---

# PART 83 — .env.example

# 367. The Problem With Ignoring .env

If `.env` is ignored, another developer cloning the project may ask:

```text
Which variables do I need?
```

That is why we created:

```text
.env.example
```

---

# 368. Safe Template

Example:

```env
TEST_ENV=local
BASE_URL=http://localhost:8080

TEST_EMAIL=your_test_user_email
TEST_PASSWORD=your_test_user_password

ADMIN_EMAIL=your_admin_user_email
ADMIN_PASSWORD=your_admin_user_password

DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password

QA_BASE_URL=https://your-qa-environment.example.com
STAGE_BASE_URL=https://your-stage-environment.example.com
```

This file contains structure, not secrets.

---

# 369. Important Difference

```text
.env
│
├── Real values
├── Local
└── DO NOT COMMIT


.env.example
│
├── Placeholder values
├── Documentation
└── COMMIT
```

---

# PART 84 — LOCAL TEST RUNNER

# 370. run-tests-local.sh

Our API automation module contains:

```text
run-tests-local.sh
```

Its job is:

```text
Find script directory
        ↓
Load .env
        ↓
Export variables
        ↓
Run Maven tests
```

---

# 371. Conceptual Script

```bash
#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

set -a
source "$SCRIPT_DIR/.env"
set +a

cd "$SCRIPT_DIR"

mvn clean test
```

---

# 372. Why set -a?

When `.env` is sourced, variables initially belong to the shell context.

Java needs environment variables exported to its process.

Therefore:

```bash
set -a
```

enables automatic exporting.

Then:

```bash
source .env
```

loads values.

Then:

```bash
set +a
```

disables that behavior again.

---

# 373. Complete Execution Flow

```text
./run-tests-local.sh
        ↓
Read .env
        ↓
Export variables
        ↓
mvn clean test
        ↓
Maven Surefire
        ↓
TestNG
        ↓
ApiConfig
        ↓
Environment variables available
        ↓
Correct Base URL + credentials
        ↓
Tests execute
```

---

# PART 85 — SECRET MANAGEMENT

# 374. What Is a Secret?

Examples:

```text
Password
JWT signing secret
API key
Access token
Database password
Private certificate/key
```

These should not be exposed through:

```text
Git
README
Logs
Reports
Screenshots
Source code
```

---

# 375. Why Git Secrets Are Dangerous

Once a secret is committed, simply deleting the line later may not be enough.

It may still exist in:

```text
Git history
Remote repository history
Forks
CI logs
Developer clones
```

Therefore prevention is important.

---

# 376. Our Secret Strategy

Current local strategy:

```text
Real Secrets
     ↓
.env
     ↓
.gitignore
```

Repository provides:

```text
.env.example
```

with placeholders.

Later CI/CD should use:

```text
GitHub Secrets
```

or another secure secret store.

---

# PART 86 — ALLURE REPORTING

# 377. Why Do We Need Reporting?

Terminal output is useful during development.

But teams need a readable report showing:

```text
What ran?
What passed?
What failed?
Which environment?
What request was sent?
What response came back?
```

Therefore we integrated:

```text
Allure
```

---

# 378. What Is Allure?

Allure is a test reporting framework.

It converts test execution results into a readable interactive report.

Our flow:

```text
TestNG Tests
      ↓
Allure Integration
      ↓
target/allure-results
      ↓
Allure CLI
      ↓
HTML Report
```

---

# 379. Running Allure

After test execution:

```bash
allure serve target/allure-results
```

Allure reads result files and launches the report.

---

# 380. What Does target/allure-results Contain?

It contains generated reporting artifacts.

Conceptually:

```text
Test result data
Attachments
Environment metadata
Execution information
```

It is generated output.

It should not be treated as source code.

---

# PART 87 — WHY API EVIDENCE MATTERS

# 381. A Failed Test Without Evidence

Suppose CI says:

```text
PaymentFlowTests FAILED
Expected: 201
Actual: 409
```

First question:

```text
What request did we send?
```

Then:

```text
What response did backend return?
```

Without evidence, debugging becomes slower.

---

# 382. Useful API Evidence

For each relevant API interaction, report can show:

```text
HTTP Method
URL
Headers
Request Body
Response Status
Response Headers
Response Body
```

This makes test failure investigation much easier.

---

# 383. Why REST Assured Filters Are Useful

REST Assured filters can observe request and response processing.

Concept:

```text
Test
 ↓
REST Assured Request
 ↓
FILTER
 ↓
Backend
 ↓
Response
 ↓
FILTER
 ↓
Test
```

A filter is therefore a good place to capture reusable API evidence.

---

# PART 88 — INITIAL ALLURE APPROACH

# 384. Initial Reporting Integration

Initially we used standard REST Assured Allure integration.

This successfully attached request/response information.

That solved:

```text
Debugging visibility
```

but introduced another issue:

```text
Sensitive information visibility
```

---

# 385. The Security Problem We Found

The report could include:

```text
Authorization: Bearer <real JWT>
```

and generated request information such as curl could also expose the real token.

This is unsafe.

The report is an artifact that may later be:

```text
Shared
Uploaded
Archived
Downloaded
Stored in CI
```

---

# 386. Important Engineering Lesson

A reporting tool should never become a credential leak.

Therefore:

```text
More logging
```

is NOT automatically:

```text
Better framework.
```

We need:

```text
Useful evidence
+
Safe evidence
```

---

# PART 89 — WHY JWT EXPOSURE MATTERS

# 387. JWT Is a Bearer Credential

If someone gets a valid bearer token, they may be able to call protected APIs as that user while the token remains usable.

Therefore:

```text
Authorization: Bearer <JWT>
```

must be treated as sensitive.

---

# 388. Other Sensitive Fields

We also considered fields such as:

```text
Authorization
Cookie
Set-Cookie
X-API-Key
API-Key

password
token
accessToken
refreshToken
secret
```

These should not appear unmasked in test artifacts.

---

# PART 90 — SanitizedAllureFilter

# 389. Our Solution

Instead of relying on raw request attachment behavior, we implemented a custom REST Assured filter:

```text
SanitizedAllureFilter
```

Its job:

```text
Capture request
        ↓
Create safe representation
        ↓
Redact sensitive values
        ↓
Attach safe request to Allure
        ↓
Execute REAL request unchanged
        ↓
Capture response
        ↓
Redact sensitive values
        ↓
Attach safe response
```

---

# 390. Critical Design Principle

The filter must NOT replace the real JWT before the backend receives the request.

Wrong:

```text
Real JWT
   ↓
Replace with [REDACTED]
   ↓
Send [REDACTED] to backend
   ↓
Authentication fails
```

Correct:

```text
                   REAL REQUEST
                        │
                ┌───────┴────────┐
                │                │
                ▼                ▼
          Backend receives   Reporting copy
             real JWT             │
                                  ▼
                              Sanitize
                                  │
                                  ▼
                           Allure attachment
```

---

# 391. Why This Matters

Sanitization is a reporting concern.

It should not modify application behavior.

This separation gives us:

```text
Real test execution
+
Safe test evidence
```

---

# PART 91 — REQUEST SANITIZATION

# 392. Sensitive Header Example

Actual request:

```text
Authorization: Bearer eyJ...
```

Allure should display:

```text
Authorization: [REDACTED]
```

---

# 393. Sensitive Body Example

Actual request:

```json
{
  "email": "user@example.com",
  "password": "secret-value"
}
```

Safe report:

```json
{
  "email": "user@example.com",
  "password": "[REDACTED]"
}
```

---

# 394. Token Field Example

Actual:

```json
{
  "token": "eyJ..."
}
```

Report:

```json
{
  "token": "[REDACTED]"
}
```

---

# 395. Sanitized Curl

A useful report may show a curl-like reproduction.

But this must also be sanitized.

Unsafe:

```bash
curl ... \
-H "Authorization: Bearer eyJ..."
```

Safe:

```bash
curl ... \
-H "Authorization: [REDACTED]"
```

Otherwise header display may be safe while curl still leaks the credential.

---

# PART 92 — REQUEST SPEC + FILTER

# 396. RequestSpecFactory Integration

Our `RequestSpecFactory` adds the custom filter.

Conceptually:

```java
new RequestSpecBuilder()
    .setBaseUri(ApiConfig.getBaseUrl())
    .setContentType(ContentType.JSON)
    .addFilter(new SanitizedAllureFilter())
    .build();
```

For authenticated requests:

```text
Base URI
+
Content Type
+
Authorization Header
+
SanitizedAllureFilter
```

---

# 397. Why Put Filter in RequestSpecFactory?

If every test manually adds:

```text
SanitizedAllureFilter
```

someone may forget it.

By centralizing:

```text
Client
 ↓
RequestSpecFactory
 ↓
Reporting Filter
```

API calls consistently receive reporting behavior.

---

# PART 93 — REQUEST AND RESPONSE ATTACHMENTS

# 398. What We Attach

Our custom filter creates attachments such as:

```text
API Request
API Response
```

This allows a failed test to show what happened.

---

# 399. Why One Test Can Show Multiple Attachments

Suppose one test performs:

```text
Login
 ↓
Create Product
 ↓
Add To Cart
 ↓
Create Order
 ↓
Create Payment
```

The test may show several:

```text
API Request
API Response
```

attachments.

That is expected.

One TestNG test method can trigger multiple HTTP calls.

---

# 400. Why This Is Useful

If the final payment call fails, we can inspect earlier setup calls.

We can answer:

```text
Did login work?
Was product created?
Did cart setup work?
Was order created?
What exactly did payment return?
```

This significantly improves debugging.

---

# PART 94 — ALLURE ENVIRONMENT METADATA

# 401. Why Environment Metadata?

Imagine receiving an Allure report without knowing whether tests ran against:

```text
LOCAL
QA
STAGE
```

The report becomes less useful.

Therefore we add environment information.

---

# 402. AllureEnvironmentListener

We created:

```text
AllureEnvironmentListener
```

It writes:

```text
target/allure-results/environment.properties
```

with metadata such as:

```text
Environment
Base URL
Framework
Test Runner
Java Version
Operating System
```

---

# 403. Example Concept

```text
Environment = local
Base URL = http://localhost:8080
Framework = REST Assured
Test Runner = TestNG
Java Version = 17
Operating System = macOS
```

This helps anyone reading the report understand execution context.

---

# 404. Why Listener?

TestNG listeners allow us to hook into test execution lifecycle.

Instead of every test writing metadata, listener handles it centrally.

Concept:

```text
TestNG execution starts
       ↓
Listener
       ↓
Create environment.properties
       ↓
Tests execute
       ↓
Allure reads metadata
```

---

# PART 95 — ALLURE ARCHITECTURE

# 405. Complete Reporting Flow

```text
                  TESTNG TEST
                      │
                      ▼
                  API CLIENT
                      │
                      ▼
              RequestSpecFactory
                      │
                      ▼
             SanitizedAllureFilter
                 /            \
                /              \
               ▼                ▼
      Safe Request Copy     Real Request
               │                │
               ▼                ▼
          Allure Attach       Backend
                                │
                                ▼
                             Response
                                │
                                ▼
                      SanitizedAllureFilter
                                │
                         Safe Response Copy
                                │
                                ▼
                         Allure Attachment
```

Meanwhile:

```text
TestNG Listener
      ↓
environment.properties
      ↓
Allure Results
```

---

# PART 96 — REPORT SECURITY PRINCIPLE

# 406. Observability vs Confidentiality

A good automation framework needs observability:

```text
Enough information to debug.
```

But also confidentiality:

```text
Do not expose secrets.
```

Balance:

```text
Debuggable
+
Secure
=
Useful Reporting
```

---

# 407. What Should We Log?

Generally useful:

```text
HTTP method
Endpoint
Non-sensitive headers
Request structure
Response status
Response body when safe
Test environment
```

---

# 408. What Should We Protect?

Examples:

```text
Passwords
Bearer tokens
Refresh tokens
API keys
Cookies
JWT secrets
Database passwords
Private keys
```

---

# PART 97 — WHY CUSTOM FILTER WAS BETTER FOR OUR NEED

# 409. Standard Integration vs Custom Requirement

Standard reporting integration gave us quick visibility.

But our requirement became:

```text
Capture API evidence
WITHOUT exposing credentials.
```

Therefore custom filter gave us control over:

```text
Which headers appear
How bodies are sanitized
How curl is generated
How sensitive values are masked
```

---

# 410. Framework Engineering Mindset

This is an important senior-level thought process:

```text
Requirement:
Need API evidence

First Solution:
Use standard reporting integration

Observation:
Sensitive JWT visible

Risk:
Credential leakage

Improvement:
Custom sanitization filter

Validation:
Tests still pass
Backend still receives real JWT
Allure displays [REDACTED]
```

Engineering is often iterative like this.

---

# PART 98 — REGRESSION AFTER REPORTING CHANGE

# 411. Why Run Full Regression After Filter Change?

The filter touches every API request.

Even though its purpose is reporting, a bug could accidentally:

```text
Change headers
Change body
Consume response
Break authentication
Alter request behavior
```

Therefore after introducing sanitization, we reran the complete API suite.

Result:

```text
Tests Run : 48
Failures  : 0
```

This proved reporting changes did not break test execution.

---

# 412. What Else Did We Verify?

We manually inspected Allure and confirmed sensitive authorization information was displayed as:

```text
[REDACTED]
```

including the curl representation.

Therefore validation had two parts:

```text
Functional Regression
        +
Security Inspection
```

---

# PART 99 — ENVIRONMENT + REPORTING FLOW

# 413. Full Framework Startup

```text
api-automation/.env
        ↓
run-tests-local.sh
        ↓
Environment variables exported
        ↓
Maven
        ↓
TestNG
        ↓
AllureEnvironmentListener
        ↓
ApiConfig
        ↓
RequestSpecFactory
        ↓
SanitizedAllureFilter
        ↓
REST Assured
        ↓
Spring Boot
        ↓
Response
        ↓
Safe Allure Evidence
```

---

# PART 100 — FUTURE CI/CD SECRET MODEL

# 414. Local vs CI

Locally:

```text
.env
```

can provide configuration.

In CI/CD:

```text
Do NOT commit .env with secrets.
```

Instead:

```text
GitHub Secrets
        ↓
Workflow environment variables
        ↓
Maven/Test process
        ↓
ApiConfig
```

---

# 415. Future GitHub Actions Concept

Later:

```text
GitHub Push / Pull Request
          ↓
GitHub Actions
          ↓
Checkout Repository
          ↓
Set up Java
          ↓
Start required services
          ↓
Inject Secrets
          ↓
Run Tests
          ↓
Generate Allure Results
          ↓
Publish/Archive Results
```

This is planned, not yet implemented.

---

# PART 101 — FRAMEWORK ENGINEERING INTERVIEW QUESTIONS

# 416. Q: How Does Your Framework Support Multiple Environments?

**Answer:**

I externalized environment configuration from the test code. ApiConfig resolves the base URL using environment variables and supports local, QA and staging targets, with an explicit BASE_URL override when required. Missing mandatory configuration fails fast rather than silently executing against the wrong environment.

---

# 417. Q: How Do You Manage Secrets?

**Answer:**

Local secrets are stored outside source code in ignored environment files, while safe `.env.example` templates document the required variables. Secrets are not committed to Git. In CI/CD, the same design can consume protected CI secrets as environment variables.

---

# 418. Q: How Did You Implement API Reporting?

**Answer:**

I integrated Allure with TestNG and added reusable API request and response evidence through a REST Assured filter. Environment metadata is also added to the report through a TestNG listener.

---

# 419. Q: Did You Face Any Reporting Security Issue?

**Answer:**

Yes. The initial REST Assured reporting approach exposed bearer tokens in request details. Since test reports may be shared or archived, I replaced that with a custom sanitization filter that redacts sensitive headers and fields before attaching evidence to Allure, while leaving the actual request unchanged.

---

# 420. Q: Why Must the Actual Request Remain Unchanged?

**Answer:**

The backend requires the real authentication token. Sanitization is only for the reporting copy. Modifying the actual Authorization header would cause authentication failures and would change the behavior being tested.

---

# 421. Q: What Data Do You Redact?

**Answer:**

The filter protects sensitive headers such as Authorization, cookies and API-key headers, and masks sensitive body fields such as passwords, access tokens, refresh tokens and secrets before attaching request or response evidence.

---

# 422. Q: Why Add Environment Metadata to Allure?

**Answer:**

A test result is more useful when its execution context is known. The report records information such as the target environment, base URL, framework, test runner, Java version and operating system, making failures easier to reproduce and diagnose.

---

# 423. Q: Why Did You Run Full Regression After Reporting Changes?

**Answer:**

The reporting filter participates in every REST Assured request. Even though its purpose is observability, an implementation error could alter authentication, headers or request processing. I therefore reran the full API regression and also manually verified that sensitive values were redacted.

---

# PART 102 — FRAMEWORK ENGINEERING CHEAT SHEET

```text
TEST_ENV
= Select target environment

BASE_URL
= Explicit endpoint override

ApiConfig
= Central configuration resolution

.env
= Local real values; do not commit

.env.example
= Safe configuration template

Fail Fast
= Stop immediately on invalid required configuration

Allure
= Test reporting framework

allure-results
= Generated raw Allure result artifacts

AllureEnvironmentListener
= Adds execution environment metadata

REST Assured Filter
= Intercepts request/response processing

SanitizedAllureFilter
= Creates safe API evidence

[REDACTED]
= Sensitive value intentionally hidden

Observability
= Ability to understand/debug system behavior

Secret
= Sensitive credential or cryptographic value
```

---

# 424. One Diagram To Memorize

```text
                     .env
                      │
                      ▼
              run-tests-local.sh
                      │
                      ▼
                  ApiConfig
                      │
                      ▼
                   BaseTest
                      │
                      ▼
                  API Client
                      │
                      ▼
             RequestSpecFactory
                      │
                      ▼
             REAL Authorization
                      │
                      ▼
            SanitizedAllureFilter
                /             \
               /               \
              ▼                 ▼
       Safe Report Copy      Real Request
              │                 │
              ▼                 ▼
           Allure           Spring Boot
                                │
                                ▼
                           PostgreSQL
                                │
                                ▼
                            Response
                                │
                                ▼
                    SanitizedAllureFilter
                                │
                                ▼
                       Safe Allure Response
```

---

# 425. Current Learning Position

At this stage we understand:

```text
Backend Architecture
        ↓
Authentication / JWT
        ↓
Spring Security / RBAC
        ↓
Products / Cart / Orders / Payment
        ↓
REST Assured Framework
        ↓
TestNG
        ↓
Positive / Negative Testing
        ↓
JSON Schema
        ↓
JDBC Validation
        ↓
Environment Switching
        ↓
Secret Management
        ↓
Allure Reporting
        ↓
API Evidence
        ↓
Sensitive Data Redaction
```

Next we will cover:

```text
OpenAPI
   ↓
Swagger
   ↓
Swagger UI
   ↓
JWT Authorize
   ↓
How Swagger differs from Postman and automation
   ↓
Git / .gitignore
   ↓
Secret Scanning
   ↓
48-Test Final Regression
   ↓
Pre-GitHub Project Hygiene
```

This takes the project from a locally working framework toward a repository that can be safely presented as a professional SDET portfolio project.

---

# PART 103 — API DOCUMENTATION

# 426. Why Does an API Need Documentation?

Backend mein bahut saare endpoints ho sakte hain:

```text
POST /api/users/register
POST /api/users/login

GET    /api/products
GET    /api/products/{id}
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}

Cart APIs
Order APIs
Payment APIs
```

Developers and testers need to understand:

```text
Which endpoint exists?
Which HTTP method is required?
What request body is expected?
What response is returned?
Which endpoints require authentication?
```

This is where OpenAPI and Swagger help.

---

# 427. What Is OpenAPI?

OpenAPI is a specification for describing REST APIs.

It can describe:

```text
Endpoints
HTTP Methods
Request Parameters
Request Bodies
Response Schemas
Authentication
API Metadata
```

Easy memory:

```text
OpenAPI
= API description standard
```

---

# 428. What Is Swagger?

Swagger is commonly used for tools around OpenAPI.

In our project the most visible tool is:

```text
Swagger UI
```

It gives us a browser interface where we can:

```text
Explore endpoints
See request schemas
Enter parameters
Authorize with JWT
Execute API calls
Inspect responses
```

---

# 429. OpenAPI vs Swagger

Easy distinction:

```text
OpenAPI
= Specification / API contract description

Swagger UI
= Visual interactive interface for that OpenAPI description
```

Do not simply say:

```text
Swagger and OpenAPI are exactly the same thing.
```

Better understanding:

```text
OpenAPI defines the specification.
Swagger tooling can consume/display it.
```

---

# PART 104 — SPRINGDOC OPENAPI

# 430. How Did We Add Swagger?

Our Spring Boot application uses:

```text
springdoc-openapi
```

with the Spring Web MVC UI starter.

This allows Spring Boot application metadata and controller mappings to be exposed as OpenAPI documentation.

---

# 431. Important Swagger URLs

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

Raw OpenAPI documentation:

```text
http://localhost:8080/v3/api-docs
```

---

# 432. What Is /v3/api-docs?

This endpoint exposes the machine-readable OpenAPI description.

Concept:

```text
Spring Controllers
        ↓
springdoc
        ↓
OpenAPI Description
        ↓
/v3/api-docs
        ↓
Swagger UI
```

Swagger UI uses API documentation information to build the interactive browser interface.

---

# 433. Why Raw OpenAPI Is Useful?

Humans usually prefer:

```text
Swagger UI
```

but machine-readable OpenAPI can later support:

```text
Documentation tooling
Contract analysis
Client generation
API governance
Other automation/tool integrations
```

---

# PART 105 — SWAGGER SECURITY CONFIGURATION

# 434. The Authentication Problem

Most of our business APIs are protected.

If Swagger sends:

```text
GET /api/products
```

without authentication:

```text
401
```

Therefore Swagger needs a way to send JWT.

---

# 435. OpenApiConfig

We created:

```text
OpenApiConfig.java
```

which defines an HTTP Bearer security scheme.

Conceptually:

```text
Security Scheme Name
      ↓
bearerAuth
      ↓
Type = HTTP
      ↓
Scheme = bearer
      ↓
Format = JWT
```

---

# 436. Bearer Authentication Scheme

Conceptually:

```java
new SecurityScheme()
    .type(SecurityScheme.Type.HTTP)
    .scheme("bearer")
    .bearerFormat("JWT");
```

This tells OpenAPI:

```text
Our protected APIs use HTTP Bearer authentication with JWT.
```

---

# 437. Swagger Authorize Button

After adding the security scheme, Swagger UI provides:

```text
Authorize
```

The user can provide a JWT.

Swagger then sends authenticated requests.

---

# 438. Important: Paste Raw JWT

In our Swagger configuration, paste:

```text
<JWT>
```

not:

```text
Bearer <JWT>
```

Swagger adds the Bearer prefix automatically.

Otherwise the header may effectively become malformed.

Easy memory:

```text
Swagger Authorize
→ paste raw JWT

REST Assured header
→ Authorization: Bearer <JWT>
```

---

# 439. Swagger JWT Flow

```text
Login
  ↓
Receive JWT
  ↓
Open Swagger
  ↓
Click Authorize
  ↓
Paste raw JWT
  ↓
Swagger stores authorization
  ↓
Execute protected endpoint
  ↓
Authorization: Bearer <JWT>
  ↓
Spring Security
  ↓
API Response
```

---

# PART 106 — SWAGGER AND SPRING SECURITY

# 440. Swagger Documentation Must Be Reachable

If Swagger documentation itself required JWT before the user could open it, local exploration would become inconvenient.

Therefore our current security configuration permits documentation endpoints such as:

```text
/swagger-ui/**
/swagger-ui.html
/v3/api-docs/**
```

---

# 441. Important Production Consideration

For our portfolio/local project:

```text
Swagger endpoints are publicly reachable within the running environment.
```

In a production system, organizations may choose to:

```text
Disable Swagger UI
Restrict it
Protect it behind authentication
Expose it only internally
```

depending on security requirements.

### Interview Answer

> "For the portfolio environment I permit the OpenAPI documentation endpoints so the API can be explored easily. In production I would follow the organization's security policy and potentially restrict or disable public Swagger access."

---

# PART 107 — SWAGGER VS POSTMAN VS AUTOMATION

# 442. Swagger UI

Best for:

```text
Discovering APIs
Reading documentation
Understanding request models
Quick manual API execution
```

Swagger is closely tied to the API contract/documentation.

---

# 443. Postman

Postman is useful for:

```text
Manual API exploration
Collections
Environment variables
Request experimentation
Debugging
Ad-hoc testing
```

It is especially convenient while investigating APIs manually.

---

# 444. REST Assured Automation

REST Assured framework is used for:

```text
Repeatable automated regression
Assertions
Security tests
Database validation
Schema validation
CI/CD execution
Reporting
```

---

# 445. Easy Comparison

```text
Swagger
= Understand and explore API contract

Postman
= Manual/ad-hoc API testing and exploration

REST Assured
= Repeatable automated API regression
```

These tools complement each other.

They do not necessarily replace each other.

---

# PART 108 — PRODUCT ID IN SWAGGER

# 446. Testing {id} Endpoints

Swagger may show:

```text
GET /api/products/{id}
```

`{id}` is a path parameter.

We need a real existing product ID.

One way to find products directly in PostgreSQL:

```bash
docker exec -it sdet-commerce-postgres \
psql -U <database-user> -d sdetcommerce \
-c "SELECT id, name, price, stock FROM products;"
```

Then use an existing ID in Swagger.

---

# 447. Path Parameter Example

Endpoint:

```text
/api/products/{id}
```

Suppose:

```text
id = 10
```

Actual request:

```text
/api/products/10
```

---

# PART 109 — GIT FOUNDATION

# 448. What Is Git?

Git is a distributed version control system.

It tracks source-code changes.

It allows us to:

```text
Track history
Create commits
Create branches
Compare changes
Collaborate
Revert changes
```

---

# 449. Git vs GitHub

Important distinction:

```text
Git
= Version control technology

GitHub
= Platform that hosts Git repositories and collaboration workflows
```

Git works locally without GitHub.

---

# 450. Working Directory, Staging and Commit

Conceptual Git flow:

```text
Working Directory
       ↓
git add
       ↓
Staging Area
       ↓
git commit
       ↓
Local Repository
       ↓
git push
       ↓
Remote Repository
```

---

# 451. git status

Important command:

```bash
git status
```

Short version:

```bash
git status --short
```

Examples:

```text
M  README.md
?? api-automation/...
```

Meaning:

```text
M
= tracked file modified

??
= new untracked file
```

---

# PART 110 — WHY WE DID NOT IMMEDIATELY RUN git add .

# 452. The Risk

Command:

```bash
git add .
```

can stage many files at once.

That is convenient but dangerous before reviewing the repository.

It could accidentally stage:

```text
Secrets
Generated reports
Temporary files
Backups
IDE files
Build output
```

Therefore before our first major portfolio commit, we inspect the repository carefully.

---

# 453. Professional Commit Mindset

Better flow:

```text
Clean repository
      ↓
Review .gitignore
      ↓
Check git status
      ↓
Scan secrets
      ↓
Review intended files
      ↓
Stage
      ↓
Review staged diff
      ↓
Commit
```

---

# PART 111 — .gitignore

# 454. What Is .gitignore?

`.gitignore` tells Git which untracked files/patterns should normally not be added.

Examples:

```text
.env
target/
allure-results/
node_modules/
.DS_Store
```

---

# 455. Why Ignore target/?

Maven generates:

```text
target/
```

It may contain:

```text
Compiled classes
Test output
Allure results
Build artifacts
```

These can be recreated.

They should generally not be source-controlled.

---

# 456. Why Ignore .env?

`.env` contains local configuration and potentially secrets.

Therefore:

```text
.env
.env.*
*.env
```

are ignored.

But safe templates need an exception:

```text
!.env.example
!**/.env.example
```

---

# 457. Why Ignore Allure Results?

Generated reporting output such as:

```text
allure-results/
allure-report/
```

can be recreated.

They are execution artifacts, not source code.

---

# 458. Why Ignore node_modules?

Future frontend/Playwright modules will use Node.js.

`node_modules/` contains downloaded dependencies.

It can be reconstructed using the dependency manifest/lock file.

Therefore it should not be committed.

---

# 459. Why Ignore .DS_Store?

macOS creates:

```text
.DS_Store
```

for Finder metadata.

It has nothing to do with project source code.

---

# PART 112 — .env.example AGAIN

# 460. Repository Usability

Ignoring `.env` protects secrets.

But someone cloning the project still needs to know:

```text
Which variables are required?
```

Therefore:

```text
.env.example
```

is committed.

This gives us:

```text
Security
+
Usability
```

---

# PART 113 — BACKUP FILE WARNING

# 461. Temporary Backup Files

During development we may create:

```text
*.backup
*.bak
```

These should not normally be committed.

Our ignore strategy includes temporary/backup patterns.

---

# 462. Learning Notes Backup

We created a backup before replacing these learning notes.

That backup is temporary safety material.

Before the final GitHub push, make sure an accidental backup file is not included unless intentionally wanted.

A public portfolio repository should contain the clean final documentation, not unnecessary temporary copies.

---

# PART 114 — SECRET SCANNING

# 463. Why Scan Before GitHub Push?

`.gitignore` helps prevent some files from being staged.

But secrets can still accidentally exist inside tracked source files.

Examples:

```text
README
Java file
Shell script
Markdown notes
Configuration file
```

Therefore we manually scan before first push.

---

# 464. Search Tracked Content

Conceptually:

```bash
git grep -n -E \
'<known-secret-pattern-1>|<known-secret-pattern-2>'
```

Ideal result:

```text
No output
```

Meaning known secret values were not found in tracked content.

---

# 465. JWT Pattern Scan

JWT often resembles:

```text
xxxxx.yyyyy.zzzzz
```

A defensive scan can look for token-like patterns.

Concept:

```bash
grep -RInE \
'eyJ[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+' \
. \
--exclude-dir=.git \
--exclude-dir=target \
--exclude='*.env'
```

Ideal:

```text
No output
```

---

# 466. Why Exclude .env?

Our local `.env` intentionally contains local values.

The purpose of the scan is to detect accidental copies elsewhere.

We already know `.env` itself contains local configuration and is ignored.

---

# 467. Check Whether .env Is Ignored

Command:

```bash
git check-ignore -v backend/.env api-automation/.env
```

This tells us which `.gitignore` rule is protecting the files.

---

# 468. Check Whether .env Appears in Git Status

Concept:

```bash
git status --short --untracked-files=all | grep -E '(^|/)\.env$'
```

Ideal:

```text
No output
```

because real `.env` files should not be candidates for commit.

---

# 469. Secret Scan Is Not Perfect

Important professional point:

A simple grep is useful, but it is not a complete enterprise secret-management solution.

Production repositories may additionally use tools such as:

```text
Secret scanning
Pre-commit hooks
Repository security scanners
CI security checks
Platform-native secret detection
```

Our manual scan is a valuable pre-push safety step.

---

# PART 115 — FINAL REGRESSION

# 470. Why Run Regression Before GitHub Milestone?

We changed multiple framework areas:

```text
RBAC
Environment management
Allure
Sanitization
Swagger
Configuration
Cleanup
```

Before declaring the milestone stable, we run the complete test suite.

---

# 471. Final API Regression Result

Our final current API automation result:

```text
Tests Run : 48
Failures  : 0
Errors    : 0
```

Meaning:

```text
48 / 48 passed
```

---

# 472. What Does 48/48 Actually Prove?

It proves the scenarios currently implemented in our automation suite passed against the tested environment at that execution time.

It does NOT mean:

```text
Application has zero bugs.
```

Important QA principle:

```text
Tests demonstrate confidence for covered scenarios.
They do not mathematically prove absence of defects.
```

---

# 473. Why Mention This in Interview?

Avoid saying:

> "All tests passed, therefore the application is bug-free."

Better:

> "The complete 48-test regression passed for the implemented coverage, giving confidence across the current functional, validation, security, RBAC and database scenarios."

---

# PART 116 — BACKEND BUILD VALIDATION

# 474. Backend and Automation Are Two Different Validations

We validate:

```text
Backend build/tests
```

and:

```text
Independent API automation regression
```

These are different.

Backend:

```text
Does application build/test successfully?
```

API automation:

```text
Does running application behave correctly from an external test perspective?
```

---

# 475. Backend Test With Environment

Because backend requires environment variables:

```bash
cd backend

set -a
source .env
set +a

./mvnw clean test
```

This ensures required configuration is available.

---

# 476. API Regression

From API automation module:

```bash
./run-tests-local.sh
```

which:

```text
Loads environment
        ↓
Runs Maven
        ↓
Executes TestNG
        ↓
Runs complete API suite
```

---

# PART 117 — PRE-GITHUB PROJECT HYGIENE

# 477. Why Repository Hygiene Matters?

A portfolio project is judged by more than code functionality.

A reviewer may notice:

```text
Repository structure
README quality
Secret handling
Generated files
Naming
Documentation
Commit history
Test organization
```

Clean repository structure communicates engineering discipline.

---

# 478. What Should Be Present?

Useful source-controlled content:

```text
Backend source
Automation source
pom.xml
docker-compose.yml
README.md
Learning notes
.env.example
.gitignore
Schemas
Scripts
Documentation
```

---

# 479. What Should NOT Be Present?

Usually avoid:

```text
Real .env
Passwords
JWTs
target/
Allure generated results
IDE metadata
.DS_Store
Temporary backup files
Random logs
```

---

# PART 118 — CURRENT MILESTONE CHECKLIST

# 480. Backend

```text
[✓] Spring Boot
[✓] PostgreSQL
[✓] Docker database
[✓] User Registration
[✓] Login
[✓] JWT
[✓] Spring Security
[✓] RBAC
[✓] Products
[✓] Cart
[✓] Orders
[✓] Mock Payment
```

---

# 481. API Automation

```text
[✓] REST Assured
[✓] TestNG
[✓] Client Layer
[✓] RequestSpecFactory
[✓] ApiConfig
[✓] AuthHelper
[✓] BaseTest
[✓] Test Data
[✓] Cleanup
[✓] Functional Tests
[✓] Negative Tests
[✓] Validation Tests
[✓] Security Tests
[✓] RBAC Tests
[✓] JSON Schema
[✓] JDBC DB Validation
```

---

# 482. Framework Engineering

```text
[✓] Environment Switching
[✓] LOCAL configuration
[✓] QA/STAGE architecture
[✓] .env
[✓] .env.example
[✓] Allure
[✓] Request Evidence
[✓] Response Evidence
[✓] Environment Metadata
[✓] Sensitive Data Redaction
[✓] Swagger/OpenAPI
[✓] JWT Authorize
```

---

# 483. Regression

```text
[✓] Backend validation passed
[✓] API regression passed
[✓] 48 / 48 API tests passed
```

---

# 484. Still Planned

```text
[ ] React + TypeScript Frontend
[ ] Playwright + TypeScript
[ ] UI + API Hybrid Testing
[ ] Full Application Dockerization
[ ] GitHub Actions
[ ] k6 Performance Testing
[ ] AWS Deployment
```

This distinction is important.

Never claim planned technologies as already implemented.

---

# PART 119 — FIRST GITHUB MILESTONE

# 485. Why Push Now?

At this point we have a meaningful standalone milestone:

```text
Real Backend
+
Real Database
+
Authentication
+
RBAC
+
Commerce Workflows
+
Independent API Automation
+
DB Validation
+
Secure Reporting
+
API Documentation
```

This is enough to create a strong first repository milestone before frontend development starts.

---

# 486. Why Not Wait Until Everything Is Finished?

Software projects evolve incrementally.

A clean milestone can show:

```text
v1
Backend + API automation

Later
Frontend + UI automation

Later
CI/CD + performance + cloud
```

This gives project history and demonstrates iterative engineering.

---

# PART 120 — GIT PRE-COMMIT CHECKLIST

# 487. Before Staging

Check:

```bash
git status --short --untracked-files=all
```

Review every unexpected file.

---

# 488. Check Ignored Secrets

```bash
git check-ignore -v backend/.env api-automation/.env
```

Both should be ignored.

---

# 489. Check JWT-Like Strings

```bash
grep -RInE \
'eyJ[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+' \
. \
--exclude-dir=.git \
--exclude-dir=target \
--exclude='*.env'
```

Review any output carefully.

---

# 490. Check Generated Files

Look for accidental:

```text
target/
allure-results/
allure-report/
.DS_Store
*.backup
*.bak
```

These should not be part of the clean milestone unless intentionally required.

---

# 491. Then Stage

Only after review:

```bash
git add .
```

Then immediately inspect:

```bash
git status
```

and ideally:

```bash
git diff --cached
```

This shows exactly what is about to be committed.

---

# 492. Important Git Rule

Think:

```text
git add
≠
git commit
```

`git add` prepares/stages changes.

`git commit` records the staged snapshot.

And:

```text
git commit
≠
git push
```

`git push` sends local commits to a remote repository.

---

# PART 121 — SWAGGER INTERVIEW QUESTIONS

# 493. Q: What Is OpenAPI?

**Answer:**

OpenAPI is a standard specification for describing REST APIs, including endpoints, request and response models, parameters and security schemes.

---

# 494. Q: What Is Swagger UI?

**Answer:**

Swagger UI is an interactive browser interface that renders the OpenAPI description and allows developers or testers to explore and execute documented API endpoints.

---

# 495. Q: How Did You Configure JWT in Swagger?

**Answer:**

I added an OpenAPI HTTP Bearer security scheme with JWT as the bearer format. Swagger UI then exposes an Authorize option, allowing a JWT to be supplied and automatically sent through the Authorization header for protected endpoints.

---

# 496. Q: Swagger vs REST Assured?

**Answer:**

Swagger UI is primarily useful for API discovery, documentation and quick manual interaction. REST Assured is used in my project for repeatable automated regression, assertions, security testing, database validation and reporting.

---

# PART 122 — GIT INTERVIEW QUESTIONS

# 497. Q: Why Do You Use .gitignore?

**Answer:**

I use `.gitignore` to prevent generated files, local environment configuration, IDE metadata and other non-source artifacts from being accidentally added to version control.

---

# 498. Q: How Do You Prevent Secrets From Entering Git?

**Answer:**

I externalize secrets through environment variables, keep real `.env` files ignored, commit only safe `.env.example` templates, inspect Git status before staging and perform pre-push scans for known credentials and token-like patterns.

---

# 499. Q: Difference Between Git and GitHub?

**Answer:**

Git is the distributed version control system used to track source history, while GitHub is a hosting and collaboration platform for Git repositories.

---

# PART 123 — REGRESSION INTERVIEW QUESTIONS

# 500. Q: What Is Your Current Automation Coverage?

**Answer:**

The current API suite contains 48 tests covering authentication, product operations, cart workflows, orders, cancellation, mock payment, validation, security, RBAC and database integration scenarios. The current full regression passes 48 out of 48 tests.

---

# 501. Q: Does 48/48 Mean the Application Has No Bugs?

**Answer:**

No. It means all currently implemented automated scenarios passed for that execution. Test results provide confidence in the covered behavior, but they do not prove the complete absence of defects.

---

# PART 124 — PORTFOLIO MILESTONE STORY

# 502. How To Explain What We Have Built So Far

Professional short version:

> "I built an end-to-end commerce quality engineering project consisting of a Java Spring Boot backend, PostgreSQL database, JWT authentication, Spring Security RBAC and product, cart, order and mock-payment workflows. I then built an independent REST Assured and TestNG automation framework with functional, negative, security, RBAC, JSON schema and JDBC database validation. The framework supports environment-based configuration, Allure reporting with sanitized API evidence and Swagger/OpenAPI documentation. The current API regression contains 48 passing tests."

---

# 503. What Makes This More Than a CRUD Project?

Because we implemented business behavior such as:

```text
Authentication
Authorization
RBAC
Ownership
Inventory validation
Stock deduction
Stock restoration
Cart clearing
Order snapshots
Duplicate payment protection
Cancelled-order rules
Database relationships
Secure reporting
Environment switching
```

These create realistic system behavior beyond simple CRUD endpoints.

---

# PART 125 — COMPLETE CURRENT ARCHITECTURE

# 504. Application + Automation

```text
                  API AUTOMATION

             TestNG Test Classes
                     │
                     ▼
                  BaseTest
                     │
                     ▼
                API Clients
                     │
                     ▼
             RequestSpecFactory
                /          \
               /            \
          ApiConfig    SanitizedAllureFilter
               \            /
                \          /
                 ▼        ▼
                 REST Assured
                      │
                      │ HTTP
                      ▼
            ┌────────────────────┐
            │ Spring Boot Backend│
            └─────────┬──────────┘
                      │
              Spring Security
                      │
           JwtAuthenticationFilter
                      │
                SecurityContext
                      │
                    RBAC
                      │
                  Controller
                      │
                   Service
                      │
                 Repository
                      │
                      ▼
                 PostgreSQL
                      ▲
                      │
                JDBC Validation


              SUPPORTING SYSTEMS

              Docker Compose
                    │
                    ▼
              PostgreSQL 16

              Swagger/OpenAPI
                    │
                    ▼
              API Documentation

              Allure
                    │
                    ▼
             Secure Test Report
```

---

# 505. Current Milestone In One Line

```text
Backend + Database + Security + Commerce Logic
+
Independent API Automation
+
Database Validation
+
Secure Reporting
+
API Documentation
=
Current SDET Commerce Milestone
```

---

# 506. Where We Go Next

After the repository is cleaned and the first GitHub milestone is created:

```text
React + TypeScript
        ↓
Real Browser UI
        ↓
Playwright + TypeScript
        ↓
UI Automation
        ↓
UI + API Hybrid Testing
        ↓
Full Dockerization
        ↓
GitHub Actions
        ↓
k6
        ↓
AWS
```

The next major learning phase will therefore move from:

```text
Backend + API Quality Engineering
```

to:

```text
Full-Stack SDET Quality Engineering
```

---

# PART 126 — PROBLEMS WE FACED AND HOW WE FIXED THEM

# 507. Why Document Problems?

A strong engineer should be able to explain:

```text
What was the problem?
How did I identify it?
What was the root cause?
What did I change?
How did I verify the fix?
```

Interviewers are often more interested in this than in hearing:

```text
"I created an automation framework."
```

Because debugging shows engineering maturity.

---

# 508. Problem-Solving Framework

For almost every technical issue, follow:

```text
1. Observe
2. Reproduce
3. Isolate
4. Identify root cause
5. Apply smallest correct fix
6. Re-test
7. Run regression
```

Easy memory:

```text
Reproduce → Isolate → Fix → Verify
```

---

# PART 127 — PROBLEM 1: BACKEND TESTS FAILED WITHOUT ENVIRONMENT VARIABLES

# 509. What Happened?

Running:

```bash
./mvnw clean test
```

directly could fail because application configuration expected values such as:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

but Maven did not automatically load our local `.env`.

---

# 510. Why Did This Happen?

Important concept:

```text
Having a .env file
≠
Operating system automatically exports those variables
```

Spring reads environment variables available to the Java process.

If shell never exported the values:

```text
Java process cannot see them.
```

---

# 511. Fix

Load `.env` before Maven:

```bash
set -a
source .env
set +a

./mvnw clean test
```

---

# 512. Why This Works

```text
set -a
    ↓
Automatically export variables

source .env
    ↓
Load local configuration

set +a
    ↓
Stop automatic exporting

Maven
    ↓
Java process inherits environment
```

---

# 513. Better Local Experience

For running the backend application, we created:

```text
run-local.sh
```

so setup is reusable.

From project root:

```bash
./backend/run-local.sh
```

---

# 514. Interview Answer

> "One issue I faced was that Spring configuration depended on environment variables, but Maven did not automatically load my local `.env`. I resolved it by explicitly exporting the variables before starting Maven and created a reusable shell script for local execution."

---

# PART 128 — PROBLEM 2: 401 VS 403 BEHAVIOR

# 515. Expected Behavior

We wanted:

```text
Missing/invalid authentication
→ 401

Authenticated USER performing ADMIN operation
→ 403
```

---

# 516. What Was Going Wrong?

Security error handling could enter additional Spring Boot error processing, which made the resulting status behavior confusing.

The issue was related to how the response was being sent and the `/error` dispatch path.

---

# 517. Why This Was Important

If USER calls:

```text
POST /api/products
```

with valid USER JWT:

```text
JWT is valid
User is authenticated
But role is insufficient
```

Therefore expected:

```text
403
```

Not:

```text
401
```

---

# 518. Fix

For authentication failure:

```java
response.setStatus(
    HttpServletResponse.SC_UNAUTHORIZED
);
```

For access denied:

```java
response.setStatus(
    HttpServletResponse.SC_FORBIDDEN
);
```

---

# 519. Why Direct setStatus Helped

Instead of triggering an unnecessary error redispatch, the security handler directly returned the intended HTTP status.

Result:

```text
Unauthenticated
→ 401

Authenticated but unauthorized
→ 403
```

---

# 520. How We Verified

Test:

```text
ROLE_USER
+
POST /api/products
        ↓
403
```

Then:

```text
ROLE_USER
+
GET /api/products
        ↓
200
```

This proved:

```text
Authentication works
+
Authorization works
```

---

# PART 129 — PROBLEM 3: ADMIN OPERATIONS WERE USING NORMAL USER TOKEN

# 521. What Was the Design Problem?

Initially a framework can easily end up with only:

```text
token
```

for every API.

But after RBAC:

```text
POST / PUT / DELETE products
```

require ADMIN privileges.

A normal USER token should fail.

---

# 522. Correct Framework Design

We introduced two authentication contexts:

```text
token
→ standard user

adminToken
→ admin user
```

---

# 523. BaseTest Setup

Concept:

```text
@BeforeClass
     ↓
AuthHelper.getAuthToken()
     ↓
token

AuthHelper.getAdminAuthToken()
     ↓
adminToken
```

---

# 524. Why This Was Better Than Making Every User Admin

Because real authorization testing requires different privilege levels.

If everything used admin:

```text
RBAC defects could be hidden.
```

We need:

```text
USER negative tests
+
ADMIN positive tests
```

---

# PART 130 — PROBLEM 4: API FRAMEWORK WAS LOCAL-URL DEPENDENT

# 525. Initial Limitation

Hardcoded:

```text
http://localhost:8080
```

works locally.

But it prevents clean reuse for:

```text
QA
Stage
Feature environment
CI deployment
```

---

# 526. Fix

We centralized target resolution using:

```text
ApiConfig
```

with:

```text
TEST_ENV
BASE_URL
QA_BASE_URL
STAGE_BASE_URL
```

---

# 527. Result

Now:

```text
Same Java tests
```

can target different environments through configuration.

No test-source changes required.

---

# 528. Additional Improvement: Fail Fast

If user selects:

```text
TEST_ENV=qa
```

but QA URL is missing:

```text
Framework stops.
```

It does not silently execute somewhere else.

---

# PART 131 — PROBLEM 5: JWT WAS VISIBLE IN ALLURE

# 529. Initial Requirement

We wanted API reports to include:

```text
Request
Response
Headers
Body
```

because they help debugging.

---

# 530. The Problem

The generated evidence showed:

```text
Authorization: Bearer <real JWT>
```

and the curl representation could also expose the token.

This created a security risk.

---

# 531. Why This Is Serious

A test report might later be:

```text
Uploaded to CI
Shared with team
Archived
Downloaded
Stored as build artifact
```

Therefore credentials should never be treated as harmless test data.

---

# 532. Wrong Fix

Do NOT change:

```text
Real Authorization header
```

to:

```text
[REDACTED]
```

before sending request.

That would make the actual API call fail.

---

# 533. Correct Fix

We created:

```text
SanitizedAllureFilter
```

Flow:

```text
Actual Request
     │
     ├── Real version → Backend
     │
     └── Copy → Sanitize → Allure
```

---

# 534. Values We Protect

Examples:

```text
Authorization
Cookie
Set-Cookie
X-API-Key
API-Key

password
token
accessToken
refreshToken
secret
```

---

# 535. How We Verified

We checked Allure manually.

Instead of:

```text
Bearer eyJ...
```

report showed:

```text
[REDACTED]
```

Curl was also sanitized.

Then full regression:

```text
48 / 48 passed
```

This proved:

```text
Reporting changed
Application behavior did not
```

---

# PART 132 — PROBLEM 6: TEST DATA COLLISIONS

# 536. The Problem

Repeated automation can create data like:

```text
Test Product
```

again and again.

This can cause:

```text
Duplicate conflicts
Unexpected search results
Dirty database state
Test dependency
```

---

# 537. Fix

Use:

```text
TestDataFactory
```

for unique values.

Example:

```text
Automation Product <unique suffix>
```

---

# 538. Cleanup

After test execution:

```text
TestDataCleanup
```

removes relevant test-created state.

This keeps runs repeatable.

---

# PART 133 — PROBLEM 7: FOREIGN KEY CLEANUP FAILURE

# 539. The Problem

Imagine:

```text
Payment
   ↓
references Order
```

If automation tries:

```text
DELETE Order
```

before:

```text
DELETE Payment
```

PostgreSQL can reject the operation.

---

# 540. Root Cause

Foreign keys maintain:

```text
Referential Integrity
```

Database refuses to leave a child row referencing a deleted parent.

---

# 541. Fix

Cleanup must respect dependency order.

Example:

```text
Payment
   ↓
Order Items
   ↓
Order
```

Then other dependent data as required.

---

# 542. Interview Answer

> "I faced cleanup failures caused by foreign-key relationships. Instead of disabling constraints, I corrected the cleanup sequence so child records such as payments and order items are removed before their parent order."

This is a much better solution than:

```text
Disable DB constraints.
```

---

# PART 134 — PROBLEM 8: CART STOCK CAN BECOME STALE

# 543. Scenario

At 10:00:

```text
Stock = 5
User adds quantity 5 to cart
```

Later:

```text
Another transaction reduces stock.
```

At checkout, cart still says:

```text
quantity = 5
```

but current stock may be:

```text
2
```

---

# 544. Wrong Design

Trust cart validation forever.

```text
If it entered cart successfully,
it must still be valid.
```

Wrong.

---

# 545. Correct Design

Validate stock:

```text
When adding to cart
```

and again:

```text
When creating order
```

Because order creation is the point where inventory is committed.

---

# PART 135 — PROBLEM 9: ORDER CANCELLATION MUST RESTORE STOCK

# 546. Basic Cancellation Is Not Enough

Weak implementation:

```text
status = CANCELLED
```

only.

Problem:

```text
Inventory remains reduced.
```

---

# 547. Correct Business Logic

```text
Find order
     ↓
Validate owner/state
     ↓
For each order item
     ↓
Restore quantity to product stock
     ↓
Set CANCELLED
```

---

# 548. How We Test It

```text
Initial stock = 10
Order qty = 3
After order = 7
Cancel order
Final stock = 10
```

This validates real business behavior.

---

# PART 136 — PROBLEM 10: CLIENT-CONTROLLED PAYMENT AMOUNT

# 549. Security Risk

Suppose backend trusts this:

```json
{
  "orderId": 100,
  "amount": 1
}
```

but actual order amount is:

```text
5000
```

A malicious client could tamper with the request.

---

# 550. Correct Design

Client sends:

```text
orderId
paymentMethod
```

Backend obtains:

```text
Trusted order amount
```

from server-side order data.

---

# 551. Principle

```text
Never trust client-controlled financial values
when server already has authoritative data.
```

---

# PART 137 — PROBLEM 11: DUPLICATE PAYMENT

# 552. Scenario

Order already has payment.

User sends another:

```text
POST /api/payments
```

---

# 553. Risk

Without duplicate protection:

```text
Same order
→ multiple payments
```

This can create major business issues.

---

# 554. Fix

Check:

```text
Payment exists for order?
```

If yes:

```text
409 Conflict
```

---

# PART 138 — PROBLEM 12: SWAGGER PROTECTED ENDPOINTS RETURNED 401

# 555. Why?

Swagger can execute protected APIs.

But without Authorization header:

```text
Spring Security
      ↓
401
```

This is correct backend behavior.

---

# 556. Fix

Configure OpenAPI Bearer JWT security scheme.

Then:

```text
Swagger
→ Authorize
→ Paste raw JWT
→ Execute API
```

Swagger sends:

```text
Authorization: Bearer <JWT>
```

---

# 557. Common Mistake

Pasting:

```text
Bearer <JWT>
```

inside Swagger's Bearer authorization field.

In our setup, paste only:

```text
<JWT>
```

because Swagger adds the prefix.

---

# PART 139 — DEBUGGING API FAILURES

# 558. When a Test Fails, Do Not Immediately Change the Test

First determine:

```text
Is this:
Application defect?
Test defect?
Test data issue?
Environment issue?
Authentication issue?
Database issue?
Configuration issue?
```

---

# 559. Debugging Order

Useful sequence:

```text
1. Read failing test name
2. Read expected vs actual
3. Inspect Allure API request
4. Inspect Allure API response
5. Check HTTP status
6. Verify request payload
7. Verify authentication
8. Check backend logs
9. Query database if relevant
10. Reproduce manually if needed
```

---

# 560. Example: Expected 201, Received 403

Think:

```text
Is JWT present?
Is JWT valid?
Which role?
Does endpoint require ADMIN?
Did test use token instead of adminToken?
```

Do not randomly change expected result to:

```text
403
```

just to make test pass.

---

# 561. Example: Expected 201, Received 409

Think business state:

```text
Duplicate resource?
Payment already exists?
Order cancelled?
Invalid current state?
```

409 usually tells us:

```text
Request may be structurally valid,
but conflicts with current state.
```

---

# 562. Example: Expected 200, Received 401

Check:

```text
Authorization header missing?
Token expired?
Wrong token?
JWT validation failed?
Request spec correct?
```

---

# 563. Example: API Passes But DB Test Fails

Possible categories:

```text
Persistence bug
Wrong SQL query
Wrong test record ID
Transaction timing
Mapping issue
Incorrect DB environment
```

We should isolate whether:

```text
Application wrote wrong data
```

or:

```text
Automation queried wrong data.
```

---

# PART 140 — DEBUGGING DATABASE ISSUES

# 564. Connect to PostgreSQL Container

Conceptual command:

```bash
docker exec -it sdet-commerce-postgres \
psql -U <database-user> -d sdetcommerce
```

---

# 565. List Tables

Inside PostgreSQL:

```sql
\dt
```

---

# 566. Inspect Products

```sql
SELECT id, name, price, stock
FROM products;
```

---

# 567. Inspect Orders

Conceptually:

```sql
SELECT *
FROM orders;
```

---

# 568. Inspect Order Items

```sql
SELECT *
FROM order_items;
```

---

# 569. Inspect Payments

```sql
SELECT *
FROM payments;
```

Use database queries to verify actual state when troubleshooting workflows.

---

# PART 141 — DEBUGGING DOCKER

# 570. Check Running Containers

```bash
docker ps
```

We expect PostgreSQL container to be running.

---

# 571. Check All Containers

```bash
docker ps -a
```

Useful when container exited unexpectedly.

---

# 572. View Container Logs

```bash
docker logs sdet-commerce-postgres
```

Useful for:

```text
Startup issues
Authentication issues
Database initialization
Unexpected crashes
```

---

# 573. Docker Compose Status

From project root:

```bash
docker compose ps
```

---

# 574. Start Database

```bash
docker compose up -d
```

---

# 575. Stop Services

```bash
docker compose down
```

Be careful with volumes.

`down` does not necessarily mean database data is deleted.

---

# PART 142 — DEBUGGING SPRING BOOT

# 576. Start Backend

From repository root:

```bash
./backend/run-local.sh
```

---

# 577. What To Check During Startup

Look for:

```text
Application started
Port 8080
Database connection success
Hibernate activity
No configuration exceptions
```

---

# 578. Port Conflict

If backend cannot start because:

```text
Port 8080 already in use
```

find process:

```bash
lsof -i :8080
```

Then inspect before stopping anything.

---

# 579. Test Backend Availability

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

or call a known endpoint appropriately.

Remember:

```text
Protected endpoint without JWT may correctly return 401.
```

Do not mistake that for backend being down.

---

# PART 143 — DEBUGGING API AUTOMATION

# 580. Run Full Suite

```bash
cd ~/SDET-Commerce-Automation/api-automation
./run-tests-local.sh
```

---

# 581. Run Maven Directly

If environment already exported:

```bash
mvn clean test
```

---

# 582. Why Prefer Wrapper Script Locally?

Because it consistently loads:

```text
.env
```

and starts tests with expected configuration.

---

# 583. Generate Allure Report

After tests:

```bash
allure serve target/allure-results
```

Use it to inspect request/response evidence.

---

# PART 144 — DEBUGGING GIT

# 584. Repository Status

```bash
git status
```

Short:

```bash
git status --short --untracked-files=all
```

---

# 585. What Changed in Working Directory?

```bash
git diff
```

---

# 586. What Is Staged?

```bash
git diff --cached
```

Very useful before committing.

---

# 587. Check Whether File Is Ignored

Example:

```bash
git check-ignore -v backend/.env
```

---

# 588. Why .gitignore May Seem Not to Work

If a file was already tracked before being added to `.gitignore`:

```text
.gitignore does not automatically untrack it.
```

Important Git concept.

`.gitignore` mainly affects untracked files.

---

# PART 145 — COMMAND CHEAT SHEET

# 589. Go to Repository

```bash
cd ~/SDET-Commerce-Automation
```

---

# 590. Start PostgreSQL

```bash
docker compose up -d
```

---

# 591. Check PostgreSQL

```bash
docker ps
```

---

# 592. Start Backend

```bash
./backend/run-local.sh
```

---

# 593. Backend Build/Test

```bash
cd ~/SDET-Commerce-Automation/backend

set -a
source .env
set +a

./mvnw clean test
```

---

# 594. Run API Automation

```bash
cd ~/SDET-Commerce-Automation/api-automation
./run-tests-local.sh
```

---

# 595. Open Allure

```bash
cd ~/SDET-Commerce-Automation/api-automation
allure serve target/allure-results
```

---

# 596. Open Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 597. Open Raw OpenAPI Docs

```text
http://localhost:8080/v3/api-docs
```

---

# 598. Query Products

```bash
docker exec -it sdet-commerce-postgres \
psql -U <database-user> -d sdetcommerce \
-c "SELECT id, name, price, stock FROM products;"
```

---

# 599. Git Status

```bash
cd ~/SDET-Commerce-Automation
git status --short --untracked-files=all
```

---

# 600. Check Environment Files Are Ignored

```bash
git check-ignore -v backend/.env api-automation/.env
```

---

# 601. JWT Safety Scan

```bash
grep -RInE \
'eyJ[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+' \
. \
--exclude-dir=.git \
--exclude-dir=target \
--exclude='*.env'
```

---

# 602. Cleanup Temporary Files

```bash
find . -name "*.backup" -not -path "./.git/*" -delete
find . -name "*.bak" -not -path "./.git/*" -delete
find . -name ".DS_Store" -not -path "./.git/*" -delete
```

Do this only after confirming no backup is still needed.

---

# 603. Check Notes Length

```bash
wc -l docs/learning-notes/SDET-Learning-Notes.md
```

---

# 604. Check Notes End

```bash
tail -30 docs/learning-notes/SDET-Learning-Notes.md
```

---

# PART 146 — ROOT-CAUSE ANALYSIS MINDSET

# 605. Symptom Is Not Root Cause

Example:

```text
Test returned 403
```

That is a symptom.

Possible causes:

```text
Wrong token
Wrong role
Wrong endpoint rule
Authentication context missing
Expected behavior actually changed
```

We need root cause before changing code.

---

# 606. Five Whys Mindset

Example:

```text
Why did product creation fail?
→ 403

Why 403?
→ USER token used

Why USER token used?
→ Client call received token variable

Why was admin token not passed?
→ Test setup used generic token

Why was framework designed that way?
→ RBAC did not exist initially
```

Then fix architecture:

```text
Introduce adminToken
```

instead of patching random assertion.

---

# 607. Fix the Correct Layer

If problem is:

```text
Environment configuration
```

fix:

```text
ApiConfig / environment
```

not:

```text
test assertion
```

If problem is:

```text
HTTP communication duplication
```

fix:

```text
Client / RequestSpecFactory
```

If problem is:

```text
Business rule
```

fix:

```text
Service layer
```

If problem is:

```text
Authorization
```

fix:

```text
Spring Security / ownership logic
```

This is architectural debugging.

---

# PART 147 — SENIOR SDET DEBUGGING INTERVIEW QUESTIONS

# 608. Q: How Do You Debug a Failing API Test?

**Answer:**

I first reproduce the failure and determine whether it is an application, automation, data, environment or configuration issue. I inspect the request and response evidence, authentication context and expected business state. If persistence is involved, I query the database independently. I then fix the issue at the correct layer and rerun the affected tests followed by appropriate regression.

---

# 609. Q: Tell Me About a Security Issue You Found in Your Framework.

**Answer:**

While adding API evidence to Allure, I found that bearer tokens were visible in request headers and curl output. I treated the report as a potential credential-exposure surface and replaced the standard attachment approach with a custom REST Assured filter that sanitizes reporting copies while leaving the actual requests unchanged. I then reran the complete 48-test regression and manually verified token redaction.

---

# 610. Q: Tell Me About a Database Issue You Faced.

**Answer:**

During test-data cleanup I encountered foreign-key dependency issues around orders, order items and payments. I corrected the cleanup sequence so dependent child records are deleted before their parent records, preserving referential integrity rather than bypassing database constraints.

---

# 611. Q: Tell Me About an Authorization Issue You Solved.

**Answer:**

I needed the framework and backend to clearly distinguish authentication failure from insufficient permissions. Missing authentication should return 401, while a valid normal user attempting an admin operation should return 403. I corrected the Spring Security response handling and verified the behavior with dedicated RBAC tests.

---

# 612. Q: How Do You Know Whether a Test or Application Is Wrong?

**Answer:**

I compare the requirement and business rule with the actual request, response and system state. I reproduce outside the failing test when useful, inspect logs and database state, and isolate the failing layer before changing anything. I do not change expected assertions simply to make a failing test pass.

---

# PART 148 — DEBUGGING MEMORY MAP

# 613. One Diagram to Remember

```text
                    TEST FAILURE
                         │
                         ▼
                     REPRODUCE
                         │
                         ▼
                 READ REQUEST/RESPONSE
                         │
                         ▼
                CLASSIFY THE PROBLEM
             /      /      |      \
            /      /       |       \
        APP     TEST     DATA     ENV
         │        │        │        │
         └────────┴────┬───┴────────┘
                      ▼
                 ISOLATE LAYER
                      │
         ┌────────────┼─────────────┐
         │            │             │
         ▼            ▼             ▼
    SECURITY      BUSINESS        DATABASE
         │            │             │
         └────────────┼─────────────┘
                      ▼
                 ROOT CAUSE
                      │
                      ▼
                  FIX LAYER
                      │
                      ▼
                  RE-TEST
                      │
                      ▼
                  REGRESSION
```

---

# 614. Problems We Can Now Explain

We can now clearly explain practical problems around:

```text
Environment variables
Local shell execution
401 vs 403
User vs admin tokens
Environment switching
Sensitive Allure data
JWT redaction
Test data uniqueness
Database cleanup
Foreign keys
Stock revalidation
Order cancellation
Payment amount trust
Duplicate payment
Swagger authentication
Git hygiene
```

This is important because a portfolio project becomes much stronger when we can explain not only:

```text
"What I built"
```

but also:

```text
"What broke, why it broke, and how I fixed it."
```

---

# PART 149 — CURRENT LEARNING POSITION

# 615. We Now Understand

```text
Project Foundation
        ↓
Backend Architecture
        ↓
Database
        ↓
Docker
        ↓
Authentication
        ↓
JWT
        ↓
Spring Security
        ↓
RBAC
        ↓
Products
        ↓
Cart
        ↓
Orders
        ↓
Payment
        ↓
REST Assured
        ↓
TestNG
        ↓
Framework Architecture
        ↓
Validation
        ↓
Security Testing
        ↓
JSON Schema
        ↓
JDBC
        ↓
Environment Switching
        ↓
Secrets
        ↓
Allure
        ↓
Swagger
        ↓
Git Hygiene
        ↓
Debugging
        ↓
Root-Cause Analysis
```

Next we will convert all of this into an interview-ready story:

```text
Complete Request Flows
        ↓
End-to-End Architecture Explanation
        ↓
2-Minute Project Explanation
        ↓
30-Second Version
        ↓
Senior SDET Interview Questions
        ↓
"Why did you choose this architecture?"
        ↓
"How would you scale it?"
        ↓
"What would you improve next?"
```

The goal is that you should be able to explain the project confidently without opening the code.

---

# PART 150 — COMPLETE REQUEST FLOWS

# 616. Why Learn Complete Request Flows?

Individual classes yaad karna enough nahi hai.

Interview mein question aa sakta hai:

```text
"What happens when a user creates an order?"
```

Strong answer should connect:

```text
Client
Security
Controller
Service
Repository
Database
Response
Automation
```

So think in flows, not isolated files.

---

# 617. Registration Flow

```text
Client
  ↓
POST /api/users/register
  ↓
Spring Security
  ↓
Public endpoint → allowed
  ↓
UserController
  ↓
UserService
  ↓
Validate request
  ↓
Check existing user
  ↓
Encode password
  ↓
Assign ROLE_USER
  ↓
UserRepository
  ↓
PostgreSQL
  ↓
User created
  ↓
HTTP Response
```

Key points:

```text
Registration is public
Password is not stored as plaintext
New user receives ROLE_USER
Public registration should not grant ROLE_ADMIN
```

---

# 618. Login Flow

```text
Client
  ↓
POST /api/users/login
  ↓
Public endpoint
  ↓
UserController
  ↓
UserService
  ↓
Find user
  ↓
Verify password
  ↓
Credentials valid?
  │
  ├── NO → authentication failure
  │
  └── YES
        ↓
     JwtService
        ↓
     Generate signed JWT
        ↓
     Include identity + role + expiry
        ↓
     Return JWT
```

The JWT is then used for protected requests.

---

# 619. Protected API Flow

```text
Client
  ↓
Authorization: Bearer <JWT>
  ↓
Spring Security Filter Chain
  ↓
JwtAuthenticationFilter
  ↓
Extract JWT
  ↓
Validate token
  ↓
Extract identity + role
  ↓
Create Authentication
  ↓
SecurityContext
  ↓
Authorization rules
  ↓
Allowed?
  │
  ├── NO AUTH → 401
  ├── WRONG PERMISSION → 403
  └── YES
        ↓
     Controller
```

This flow is one of the most important flows to memorize.

---

# 620. Product Read Flow

```text
GET /api/products/{id}
        ↓
JWT Filter
        ↓
Authenticated?
        ↓
ProductController
        ↓
ProductService
        ↓
ProductRepository
        ↓
PostgreSQL
        ↓
Product
        ↓
HTTP Response
```

---

# 621. Admin Product Creation Flow

```text
POST /api/products
        ↓
JWT
        ↓
JwtAuthenticationFilter
        ↓
SecurityContext
        ↓
SecurityConfig
        ↓
ROLE_ADMIN required
        ↓
Admin?
   ┌────┴────┐
   │         │
  NO        YES
   │         │
  403        ▼
       ProductController
             ↓
       ProductService
             ↓
       ProductRepository
             ↓
        PostgreSQL
             ↓
         201 Created
```

---

# 622. Add-to-Cart Flow

```text
Authenticated User
        ↓
Add Product to Cart
        ↓
CartController
        ↓
CartService
        ↓
Find Product
        ↓
Validate quantity
        ↓
Check stock
        ↓
Existing cart item?
   ┌────────┴────────┐
   │                 │
  YES               NO
   │                 │
Update quantity   Create item
   │                 │
   └────────┬────────┘
            ↓
       Cart persistence
            ↓
        Response
```

---

# 623. Order Creation Flow

```text
Authenticated User
        ↓
POST Create Order
        ↓
OrderController
        ↓
OrderService
        ↓
Load user's cart
        ↓
Cart empty?
        │
        ├── YES → Reject
        │
        └── NO
              ↓
       Revalidate stock
              ↓
       Stock available?
        │
        ├── NO → Reject
        │
        └── YES
              ↓
          Create Order
              ↓
       Create OrderItems
              ↓
        Snapshot values
              ↓
         Deduct stock
              ↓
          Save order
              ↓
          Clear cart
              ↓
        Return CREATED
```

---

# 624. Why Order Flow Is Important

It touches multiple business states:

```text
Cart
Order
Order Items
Product Inventory
```

Therefore it is much more valuable than simple CRUD testing.

---

# 625. Order Cancellation Flow

```text
Authenticated User
        ↓
Cancel Order
        ↓
Load Order
        ↓
Validate ownership
        ↓
Validate current status
        ↓
For each OrderItem
        ↓
Restore product stock
        ↓
Set order = CANCELLED
        ↓
Persist changes
        ↓
Return response
```

---

# 626. Payment Flow

```text
Authenticated User
        ↓
POST /api/payments
        ↓
PaymentController
        ↓
PaymentService
        ↓
Find Order
        ↓
Validate ownership
        ↓
Order cancelled?
   ┌────┴────┐
   │         │
  YES        NO
   │         │
 409         ▼
       Existing Payment?
          ┌──┴──┐
          │     │
         YES    NO
          │     │
         409    ▼
            Read trusted
            order amount
                 ↓
         Generate transaction ID
                 ↓
             SUCCESS
                 ↓
          PaymentRepository
                 ↓
             PostgreSQL
                 ↓
             201 Created
```

---

# PART 151 — AUTOMATION REQUEST FLOW

# 627. Complete Automated Test Flow

Suppose automation tests admin product creation:

```text
TestNG
  ↓
ProductRbacTests
  ↓
BaseTest.adminToken
  ↓
ProductClient
  ↓
RequestSpecFactory
  ↓
ApiConfig
  ↓
SanitizedAllureFilter
  ↓
REST Assured
  ↓
HTTP Request
  ↓
Spring Boot
  ↓
Spring Security
  ↓
Product Business Logic
  ↓
PostgreSQL
  ↓
HTTP Response
  ↓
REST Assured
  ↓
Allure Attachment
  ↓
Assertions
  ↓
Optional JDBC Validation
  ↓
Cleanup
```

This is the complete connection between:

```text
Test Framework
Application
Database
Reporting
```

---

# 628. API + Database Validation Flow

```text
              TEST
               │
               ▼
          REST Assured
               │
               ▼
          Backend API
               │
               ▼
          PostgreSQL
               │
               ▼
         API Response
               │
               ▼
       Response Assertions
               │
               ▼
          JDBC Helper
               │
               ▼
          PostgreSQL
               │
               ▼
          DB Record
               │
               ▼
         DB Assertions
```

This provides independent persistence verification.

---

# PART 152 — COMPLETE PROJECT ARCHITECTURE

# 629. Current Architecture

```text
┌─────────────────────────────────────────────┐
│            API AUTOMATION FRAMEWORK         │
│                                             │
│ TestNG                                      │
│   ↓                                         │
│ BaseTest                                    │
│   ↓                                         │
│ API Clients                                 │
│   ↓                                         │
│ RequestSpecFactory                          │
│   ↓                                         │
│ REST Assured                                │
│   ↓                                         │
│ Sanitized Allure Evidence                   │
└──────────────────┬──────────────────────────┘
                   │
                   │ HTTP
                   ▼
┌─────────────────────────────────────────────┐
│             SPRING BOOT BACKEND             │
│                                             │
│ Spring Security                             │
│      ↓                                      │
│ JwtAuthenticationFilter                     │
│      ↓                                      │
│ SecurityContext / RBAC                      │
│      ↓                                      │
│ Controllers                                 │
│      ↓                                      │
│ Services                                    │
│      ↓                                      │
│ Repositories                                │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│                POSTGRESQL                   │
│                                             │
│ Users                                       │
│ Products                                    │
│ Cart Items                                  │
│ Orders                                      │
│ Order Items                                 │
│ Payments                                    │
└─────────────────────────────────────────────┘
                   ▲
                   │
                   │ JDBC
                   │
┌──────────────────┴──────────────────────────┐
│       AUTOMATION DATABASE VALIDATION        │
└─────────────────────────────────────────────┘
```

Supporting components:

```text
Docker Compose
→ PostgreSQL local infrastructure

Swagger/OpenAPI
→ API documentation and exploration

Allure
→ Test reporting

.env / ApiConfig
→ Configuration and secrets

Git
→ Version control
```

---

# PART 153 — WHY THIS ARCHITECTURE?

# 630. Why Separate Backend and Automation?

Because automation should test the application externally.

```text
backend/
```

contains application code.

```text
api-automation/
```

contains independent test code.

Benefits:

```text
Independent deployment targeting
Cleaner responsibilities
QA/STAGE support
Realistic API testing
Independent framework evolution
```

---

# 631. Why Layer the Backend?

```text
Controller
↓
Service
↓
Repository
```

because responsibilities differ.

Controller:

```text
HTTP boundary
```

Service:

```text
Business rules
```

Repository:

```text
Persistence
```

This improves maintainability and testability.

---

# 632. Why Client Layer in Automation?

Same principle applies to automation.

```text
Test
↓
Client
↓
HTTP
```

Test focuses on:

```text
Behavior and assertions
```

Client focuses on:

```text
How to communicate with endpoint
```

---

# 633. Why RequestSpecFactory?

To centralize:

```text
Base URI
Content Type
Authorization
Reporting Filter
```

This avoids duplicated HTTP configuration.

---

# 634. Why JDBC If We Already Have REST Assured?

Because they validate different layers.

```text
REST Assured
→ External API behavior

JDBC
→ Actual persistence
```

Combining them provides deeper integration confidence.

---

# 635. Why Allure?

Because automation is not useful only when it passes.

When it fails, we need:

```text
Execution context
Request evidence
Response evidence
Failure details
```

But reporting must also protect secrets.

---

# PART 154 — 30-SECOND PROJECT EXPLANATION

# 636. Interview Version

> "I built an end-to-end commerce quality engineering project with a Java Spring Boot backend and PostgreSQL. It supports JWT authentication, Spring Security RBAC, products, cart, orders, inventory handling and mock payments. On top of that, I built an independent REST Assured and TestNG framework covering functional, negative, security, schema and JDBC database validation, with environment switching, Swagger documentation and secure Allure reporting. The current API regression has 48 passing tests."

---

# PART 155 — 2-MINUTE PROJECT EXPLANATION

# 637. Interview Version

> "I wanted to build a project that demonstrates both application architecture understanding and Senior SDET-level automation, rather than only writing UI test scripts.
>
> I developed a commerce backend using Java, Spring Boot and PostgreSQL. The application supports user registration and login with JWT-based stateless authentication. Spring Security handles endpoint protection and role-based access control, with standard users and administrators having different permissions.
>
> On the business side, I implemented products, cart, orders and a mock-payment workflow. Order creation revalidates inventory, creates order-item snapshots, reduces stock and clears the cart. Cancellation restores inventory, while payment validates ownership, order state and duplicate-payment conditions and derives the payable amount from trusted server-side order data.
>
> I then built a separate Java automation framework using REST Assured and TestNG. It uses reusable request specifications, domain-specific API clients, authentication helpers, environment-based configuration, unique test data and cleanup utilities. The suite covers functional, negative, validation, security and RBAC scenarios. For critical workflows I also use JDBC to validate PostgreSQL state and JSON Schema to validate API contracts.
>
> I integrated Allure for reporting. During that work I identified that bearer tokens could appear in request evidence, so I implemented a custom sanitization filter that keeps the real request unchanged but redacts sensitive information from reports. I also added Swagger/OpenAPI with JWT authorization for API exploration.
>
> The current API milestone has a complete 48-test regression passing, and the next phase is React and TypeScript followed by Playwright UI automation, CI/CD, performance testing and cloud deployment."

---

# PART 156 — EXPLAIN THE FRAMEWORK IN 60 SECONDS

# 638. Framework Answer

> "My API framework is built with Java, REST Assured and TestNG. Configuration is centralized in ApiConfig and externalized through environment variables so the same tests can target different environments. RequestSpecFactory manages reusable REST Assured setup, while AuthHelper provides standard-user and admin JWTs. Domain clients such as ProductClient, CartClient, OrderClient and PaymentClient encapsulate HTTP communication, and BaseTest provides common setup.
>
> Test classes are separated by concerns such as CRUD, validation, security, RBAC and database testing. For persistence validation I use JDBC, and for contract validation I use JSON Schema. Allure provides reporting, and I added a custom REST Assured filter to redact sensitive credentials from request and response evidence."

---

# PART 157 — WHAT MAKES THIS SENIOR SDET LEVEL?

# 639. Not Just Test Scripts

The project includes:

```text
Framework Architecture
Environment Management
Authentication
Authorization Testing
RBAC
API Contract Validation
Database Validation
Business Rule Testing
Test Data Management
Cleanup Strategy
Secure Reporting
Debugging Evidence
Git Hygiene
```

This demonstrates thinking beyond:

```text
"Send request → assert 200"
```

---

# 640. Quality Engineering Mindset

Senior SDET thinking asks:

```text
What are the risks?
What layer should validate this?
What should happen on failure?
How do we debug failures?
How do we keep tests independent?
How do we protect secrets?
How can this run in CI?
How can the framework scale?
```

---

# PART 158 — WHY NOT AUTOMATE EVERYTHING THROUGH UI?

# 641. UI Tests Are Expensive

UI tests are useful, but typically:

```text
Slower
More brittle
More expensive to maintain
```

Many business rules can be validated more efficiently at API level.

---

# 642. Our Future Test Pyramid

Conceptually:

```text
            /\
           /  \
          / UI \
         /------\
        /  API   \
       /----------\
      / Unit/Comp. \
     /______________\
```

For our SDET framework:

```text
Most business/integration coverage
→ API

Critical user journeys
→ UI

Application internals
→ backend/unit/component tests
```

---

# 643. Example

We do not need 20 UI tests just to validate:

```text
Invalid product price
Wrong role
Duplicate payment
Empty cart
```

API tests are faster and more focused.

UI should validate important user journeys.

---

# PART 159 — FUTURE UI ARCHITECTURE

# 644. Next Phase

Planned:

```text
React
+
TypeScript
+
Vite
```

Frontend will call:

```text
Spring Boot REST API
```

---

# 645. Future Full Application

```text
               React + TypeScript
                       │
                       │ HTTP
                       ▼
                 Spring Boot
                       │
                       ▼
                  PostgreSQL
```

---

# 646. Future Automation

```text
                Playwright
                    │
                    ▼
              Browser / React
                    │
                    ▼
               Spring Boot
                    │
                    ▼
                Database
```

REST Assured remains:

```text
Direct API automation
```

So we will have:

```text
Playwright → UI Layer
REST Assured → API Layer
JDBC → DB Layer
```

---

# PART 160 — UI + API HYBRID TESTING

# 647. What Is Hybrid Testing?

Sometimes test setup through UI is unnecessarily slow.

Example:

```text
Need a product before UI test.
```

Instead of:

```text
Login UI
Open Admin
Fill Product Form
Submit
```

test can potentially:

```text
Create product through API
        ↓
Open browser
        ↓
Validate product through UI
```

This is hybrid automation.

---

# 648. Why Useful?

```text
Fast setup
More stable tests
Less unnecessary UI dependency
Better test isolation
```

UI should be used when UI behavior itself is what we want to validate.

---

# PART 161 — HOW WOULD YOU SCALE THE FRAMEWORK?

# 649. First: CI/CD

Add:

```text
GitHub Actions
```

to automatically run checks on:

```text
Pull Requests
Pushes
Scheduled regression
```

---

# 650. Second: Parallel Execution

As suite grows:

```text
Split tests
Run independent suites concurrently
```

But only after ensuring:

```text
Test data isolation
Thread safety
No shared mutable state
```

---

# 651. Third: Better Environment Provisioning

Future:

```text
Containerized backend
Containerized database
```

so CI can create a predictable test environment.

---

# 652. Fourth: Observability

For larger systems:

```text
Application logs
Correlation IDs
Centralized logs
Metrics
Tracing
```

can improve debugging.

---

# 653. Fifth: Cloud

Later architecture can deploy to AWS.

Potential services:

```text
EC2
RDS
S3
IAM
CloudWatch
```

These are planned learning areas, not current implementation.

---

# PART 162 — WHY DOCKER?

# 654. Interview Answer

> "I currently use Docker to run PostgreSQL consistently in the local environment. It reduces machine-specific database setup and gives the project a repeatable database service. A later phase will containerize the rest of the application as well."

Do not claim:

```text
Full application is Dockerized
```

yet.

Currently:

```text
PostgreSQL is containerized.
```

---

# PART 163 — WHY SPRING BOOT?

# 655. Interview Answer

> "I chose Spring Boot because it allowed me to build a realistic Java backend around the same ecosystem I use for automation. It helped me understand application layers, REST APIs, persistence, security and business logic from the developer side, which makes my SDET testing decisions stronger."

---

# PART 164 — WHY POSTGRESQL?

# 656. Interview Answer

> "I wanted a real relational database so I could model relationships such as users, products, orders, order items and payments and also validate persistence independently through JDBC. PostgreSQL gave me realistic SQL and relational-integrity scenarios for automation."

---

# PART 165 — WHY REST ASSURED?

# 657. Interview Answer

> "REST Assured integrates naturally with Java and TestNG and provides a readable API for HTTP requests, assertions, reusable specifications, filters and schema validation. It also fits well with JDBC-based database validation in the same Java framework."

---

# PART 166 — WHY TESTNG?

# 658. Interview Answer

> "TestNG provides the execution lifecycle, annotations and test organization, while REST Assured handles HTTP communication. I use common setup through BaseTest and TestNG lifecycle hooks, and Maven Surefire executes the suite."

---

# PART 167 — WHY PLAYWRIGHT NEXT?

# 659. Interview Answer

> "The backend and API layer are already strongly covered, so the next logical layer is browser-level validation. I plan to use Playwright with TypeScript for critical user journeys and UI behavior while keeping most business-rule validation at the API layer."

---

# PART 168 — WHAT WOULD YOU IMPROVE?

# 660. Strong Interview Answer

> "The current milestone is intentionally focused on backend and API quality engineering. My next improvements are a React and TypeScript frontend, Playwright browser automation, full application containerization and GitHub Actions. After that I plan to add performance testing with k6 and deploy the system to AWS so the same framework can run against a cloud-hosted environment."

---

# PART 169 — SENIOR SDET SCENARIO QUESTIONS

# 661. Q: API Test Is Failing in QA but Passing Locally. What Do You Do?

**Answer:**

> "I first confirm both executions are targeting the intended environments and compare configuration, test data and deployed application versions. I inspect request and response evidence, authentication and relevant backend logs. If persistence is involved, I compare database state. I isolate whether the difference comes from environment configuration, deployment, data or application behavior before changing the test."

---

# 662. Q: 50 Tests Fail After One Authentication Change. Would You Fix 50 Tests?

**Answer:**

> "Not immediately. If the failures share the same root cause, I would inspect the centralized authentication layer first, such as AuthHelper, RequestSpecFactory or the security contract. A framework should centralize cross-cutting concerns so one authentication change does not require repetitive changes across many tests."

---

# 663. Q: How Would You Reduce Flaky Tests?

**Answer:**

> "I would identify the actual source of nondeterminism rather than adding retries blindly. Common causes include shared test data, asynchronous behavior, unstable environments and timing assumptions. I prefer isolated test data, deterministic setup, condition-based waits where necessary and clear cleanup. Retries should not hide genuine defects."

---

# 664. Q: Would You Run All 48 Tests on Every Commit?

**Answer:**

> "It depends on execution time and CI strategy. If the suite remains fast, running it on pull requests can be valuable. As it grows, I would separate fast smoke or critical checks from broader regression and use scheduled or deployment-triggered regression where appropriate."

---

# 665. Q: What If Database Validation Makes Tests Too Tightly Coupled?

**Answer:**

> "I use direct database validation selectively for high-value persistence and integration scenarios rather than every API test. Most tests should validate through the public contract, while targeted JDBC checks provide additional confidence for critical database side effects."

---

# 666. Q: Would You Test Production Database Directly?

**Answer:**

> "No. Direct database validation should be restricted to controlled non-production environments and follow organizational security and access policies. Production testing should avoid unsafe direct data manipulation."

---

# 667. Q: Why Not Put Assertions Inside API Clients?

**Answer:**

> "Because the same API method may legitimately return different responses for positive, negative, security and validation scenarios. The client should focus on making the request, while the test owns the expected behavior and assertions."

---

# 668. Q: How Would You Test an API With No UI?

**Answer:**

> "I can test it directly using API automation. UI is not required for REST API validation. I would validate the contract, status codes, response data, negative cases, security, business rules and selected persistence behavior directly through the API."

---

# 669. Q: What Would You Automate First?

**Answer:**

> "I prioritize stable, repeatable and business-critical scenarios. I start with core happy paths and high-risk API business rules, then add negative, security and integration coverage. UI automation is focused on critical user journeys rather than duplicating every lower-level test."

---

# 670. Q: What Makes a Good Automation Framework?

**Answer:**

> "A good framework should be maintainable, readable, reusable, environment-independent, secure and easy to debug. Tests should clearly express business intent, cross-cutting configuration should be centralized, test data should be controlled and failures should provide enough evidence for efficient diagnosis."

---

# PART 170 — PROJECT CHALLENGE STORY

# 671. STAR-Style Example: Secure Reporting

### Situation

We wanted detailed API request and response evidence in Allure.

### Task

Provide useful debugging information without exposing authentication credentials.

### Action

We initially integrated API evidence and discovered bearer JWTs were visible in headers and curl output.

Instead of removing useful reporting, we implemented:

```text
SanitizedAllureFilter
```

which creates a sanitized reporting representation while preserving the real request sent to the backend.

Sensitive values such as:

```text
Authorization
Tokens
Passwords
Cookies
API keys
Secrets
```

are redacted.

### Result

```text
Detailed API evidence remained available
JWT exposure was removed
48/48 regression still passed
```

This is a strong interview example because it combines:

```text
Automation
Security
Framework design
Debugging
Regression
```

---

# PART 171 — PROJECT CHALLENGE STORY 2

# 672. STAR-Style Example: RBAC

### Situation

Product management needed different permissions for normal users and administrators.

### Task

Implement and validate proper role-based access control.

### Action

We introduced:

```text
ROLE_USER
ROLE_ADMIN
```

JWT contains role information.

The JWT filter converts it into Spring Security authority.

Security rules allow authenticated product reads but restrict product mutations to admins.

Automation maintains:

```text
token
adminToken
```

and tests both positive and negative authorization.

### Result

We verified:

```text
USER GET product
→ 200

USER POST product
→ 403

ADMIN POST product
→ 201
```

This demonstrated real privilege separation.

---

# PART 172 — PROJECT CHALLENGE STORY 3

# 673. STAR-Style Example: Order Consistency

### Situation

Order creation changes several pieces of application state.

### Task

Ensure order creation and cancellation preserve correct inventory behavior.

### Action

Order creation:

```text
Revalidates stock
Creates order
Creates item snapshots
Reduces inventory
Clears cart
```

Cancellation:

```text
Validates order
Restores stock
Sets CANCELLED
```

Automation validates both API behavior and database/business side effects.

### Result

The workflow provides stronger confidence than simply checking that an order endpoint returns success.

---

# PART 173 — IF INTERVIEWER ASKS "DID YOU BUILD THIS YOURSELF?"

# 674. Strong Answer

> "Yes. I built it as a learning and portfolio project specifically to deepen my SDET understanding across application architecture, API development, security, database integration and automation. I worked through each module incrementally, debugged issues as they appeared and documented the architecture and decisions so I can explain how the complete system works."

Important:

Do not pretend it is:

```text
Production system used by millions of users.
```

Present it accurately as:

```text
Personal end-to-end engineering / portfolio project.
```

That is completely valid.

---

# PART 174 — IF INTERVIEWER OPENS THE CODE

# 675. Be Ready to Navigate

You should know these areas conceptually:

```text
BACKEND

config/
→ Spring Security
→ OpenAPI

entity/
→ User
→ Product
→ CartItem
→ Order
→ OrderItem
→ Payment
→ Role/status enums

repository/
→ Database access

service/
→ Business logic

controller/
→ REST endpoints
```

Automation:

```text
config/
→ ApiConfig
→ RequestSpecFactory

client/
→ ProductClient
→ CartClient
→ OrderClient
→ PaymentClient

utils/
→ AuthHelper
→ TestDataFactory
→ TestDataCleanup

database/
→ JDBC helpers / records

filters/
→ SanitizedAllureFilter

listeners/
→ AllureEnvironmentListener

tests/
→ Functional
→ Validation
→ Security
→ Database
→ RBAC
```

---

# PART 175 — DO NOT MEMORIZE CODE LINE BY LINE

# 676. What Should You Memorize?

Do not memorize:

```text
Every import
Every annotation
Every exact method line
```

Understand:

```text
Why class exists
What responsibility it owns
What calls it
What it calls
What problem it solves
```

Example:

```text
RequestSpecFactory
```

You should immediately know:

```text
Central REST Assured request configuration
Base URL
Content type
JWT header
Sanitized reporting filter
```

That is more important than memorizing syntax.

---

# PART 176 — FIVE QUESTIONS FOR EVERY CLASS

# 677. Use This Learning Technique

Whenever reviewing code, ask:

```text
1. Why does this class exist?

2. Who calls it?

3. What does it call?

4. What data enters it?

5. What does it return/change?
```

Example:

```text
PaymentService
```

Ask:

```text
Why?
→ Payment business rules

Who calls it?
→ PaymentController

What does it call?
→ Order/Payment repositories

Input?
→ Authenticated user + payment request

What changes?
→ Creates validated payment record
```

Now you understand architecture.

---

# PART 177 — COMPLETE PROJECT MEMORY MAP

# 678. Remember This Hierarchy

```text
SDET COMMERCE AUTOMATION
│
├── APPLICATION
│   │
│   ├── Spring Boot
│   ├── PostgreSQL
│   ├── Security
│   │   ├── JWT
│   │   └── RBAC
│   │
│   └── Business
│       ├── Users
│       ├── Products
│       ├── Cart
│       ├── Orders
│       └── Payment
│
├── API AUTOMATION
│   │
│   ├── REST Assured
│   ├── TestNG
│   ├── ApiConfig
│   ├── RequestSpecFactory
│   ├── AuthHelper
│   ├── API Clients
│   ├── Test Data
│   └── Cleanup
│
├── VALIDATION
│   │
│   ├── Functional
│   ├── Negative
│   ├── Validation
│   ├── Security
│   ├── RBAC
│   ├── JSON Schema
│   └── JDBC
│
├── ENGINEERING
│   │
│   ├── Environment Switching
│   ├── Secret Management
│   ├── Allure
│   ├── Sanitization
│   ├── Swagger
│   ├── Docker
│   └── Git
│
└── NEXT
    │
    ├── React + TypeScript
    ├── Playwright
    ├── Hybrid UI/API
    ├── Full Dockerization
    ├── GitHub Actions
    ├── k6
    └── AWS
```

---

# PART 178 — THE MOST IMPORTANT INTERVIEW MESSAGE

# 679. What This Project Demonstrates

The main value of this project is not:

```text
"I know REST Assured syntax."
```

The stronger message is:

```text
I understand how a system works
        ↓
I identify risks at different layers
        ↓
I choose the right testing layer
        ↓
I design reusable automation
        ↓
I validate business behavior
        ↓
I validate security
        ↓
I validate persistence
        ↓
I make failures diagnosable
        ↓
I protect sensitive data
        ↓
I can evolve the framework toward CI/CD and UI automation
```

That is the Quality Engineering mindset we want to demonstrate.

---

# PART 179 — CURRENT PROJECT EXPLANATION CHECKPOINT

# 680. You Should Now Be Able to Explain Without Code

You should be able to answer:

```text
What are we building?

Why Spring Boot?

Why PostgreSQL?

Why Docker?

How does registration work?

How does login work?

How does JWT work?

What does JwtAuthenticationFilter do?

What is SecurityContext?

401 vs 403?

What is RBAC?

How do Products work?

How does Cart work?

How does Order creation work?

Why revalidate stock?

Why snapshot OrderItems?

How does cancellation restore stock?

How does Payment work?

Why derive amount from Order?

Why prevent duplicate payments?

Why separate API automation?

Why REST Assured?

Why TestNG?

Why RequestSpecFactory?

Why API clients?

Why BaseTest?

Why USER and ADMIN tokens?

How do we manage test data?

How do we clean DB data?

Why JSON Schema?

Why JDBC?

Response vs Schema vs DB validation?

How do environments switch?

Why .env and .env.example?

How does Allure work?

Why SanitizedAllureFilter?

How did JWT exposure happen?

How did we fix it?

What is Swagger/OpenAPI?

How does Swagger JWT authorization work?

How do we protect Git from secrets?

What problems did we face?

How do we debug failures?

What is next?
```

If you can explain these concepts, you understand the current milestone instead of merely having code that works.

---

# PART 180 — NEXT PHASE

# 681. Current Milestone Boundary

Current implemented milestone:

```text
Spring Boot Backend
+
PostgreSQL
+
JWT / RBAC
+
Commerce Business Logic
+
REST Assured API Automation
+
48-Test Regression
+
JDBC
+
JSON Schema
+
Environment Switching
+
Secure Allure Reporting
+
Swagger/OpenAPI
```

Next implemented phase will be:

```text
React + TypeScript Frontend
        ↓
Playwright + TypeScript
        ↓
UI + API Hybrid Testing
```

After that:

```text
Full Dockerization
GitHub Actions
k6
AWS
```

We should update these notes as those capabilities are actually implemented rather than describing planned work as completed.

---

# PART 181 — MASTER REVISION GUIDE

# 682. How To Use This Final Section

This section is not meant to replace the detailed notes.

Use it when:

```text
Interview is tomorrow
Interview is in a few hours
You want a quick project revision
You forgot one concept
You want rapid-fire practice
```

Detailed understanding:

```text
Parts 1–9
```

Fast revision:

```text
Part 10
```

---

# PART 182 — PROJECT IN ONE SENTENCE

# 683. One-Line Project Summary

> "SDET Commerce Automation is an end-to-end quality engineering portfolio project combining a Spring Boot commerce backend, PostgreSQL, JWT/RBAC security and an independent REST Assured/TestNG automation framework with API, security, schema and JDBC database validation."

---

# PART 183 — PROJECT IN 10 POINTS

# 684. Ten Things To Remember

```text
1. Backend
   → Java 17 + Spring Boot

2. Database
   → PostgreSQL

3. Local Infrastructure
   → PostgreSQL through Docker Compose

4. Authentication
   → JWT

5. Authorization
   → Spring Security + RBAC

6. Business Modules
   → User + Product + Cart + Order + Payment

7. API Automation
   → REST Assured + TestNG

8. Deep Validation
   → JSON Schema + JDBC

9. Reporting
   → Allure + SanitizedAllureFilter

10. API Documentation
    → OpenAPI + Swagger UI
```

---

# PART 184 — APPLICATION MEMORY MAP

# 685. Backend Request Flow

Memorize:

```text
HTTP Request
     ↓
Spring Security
     ↓
JWT Filter
     ↓
SecurityContext
     ↓
Authorization
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
PostgreSQL
     ↓
Response
```

---

# 686. Responsibility Map

```text
Controller
= HTTP handling

Service
= Business logic

Repository
= Database access

Entity
= Persistence model

DTO / Request / Response Model
= API data transfer

SecurityConfig
= Security rules

JwtAuthenticationFilter
= JWT authentication processing
```

---

# PART 185 — AUTHENTICATION RAPID REVISION

# 687. Registration

```text
Request
 ↓
Validate
 ↓
Hash password
 ↓
ROLE_USER
 ↓
Persist User
```

---

# 688. Login

```text
Email + Password
        ↓
Verify credentials
        ↓
Generate JWT
        ↓
Return JWT
```

---

# 689. JWT

JWT conceptually contains:

```text
Header
Payload
Signature
```

Our authentication flow uses claims such as:

```text
User identity
Role
Expiration
```

---

# 690. Why JWT?

```text
Stateless authentication
```

Server can validate the signed token on protected requests without requiring a traditional server-side login session for every user.

---

# 691. Bearer Header

```text
Authorization: Bearer <JWT>
```

---

# PART 186 — SECURITY RAPID REVISION

# 692. Authentication vs Authorization

```text
Authentication
= Who are you?

Authorization
= What are you allowed to do?
```

---

# 693. 401 vs 403

```text
401
= Authentication missing/invalid

403
= Authenticated but not permitted
```

Memory:

```text
401 → Who are you?

403 → I know you, but NO.
```

---

# 694. RBAC

```text
ROLE_USER
ROLE_ADMIN
```

Example:

```text
USER GET products
→ allowed

USER POST product
→ 403

ADMIN POST product
→ allowed
```

---

# 695. Three Security Checks

```text
Authenticated?

Correct role?

Correct resource owner?
```

Do not confuse these.

---

# PART 187 — PRODUCT RAPID REVISION

# 696. Product Responsibilities

```text
Create
Read
Update
Delete
Search
Validation
Stock
```

Product mutation is admin-controlled.

---

# 697. Why BigDecimal For Price?

Financial values should avoid floating-point precision problems associated with binary floating-point types.

Use:

```text
BigDecimal
```

for monetary values in Java business applications.

---

# PART 188 — CART RAPID REVISION

# 698. Cart Important Rules

```text
Authenticated user
Valid product
Valid quantity
Stock available
User owns cart
```

Unique concept:

```text
(user_id, product_id)
```

prevents duplicate logical cart rows for the same product/user combination.

---

# 699. Why Validate Stock in Cart AND Order?

Because stock can change after the item was added to the cart.

```text
Cart validation
= early validation

Order validation
= final inventory commitment check
```

---

# PART 189 — ORDER RAPID REVISION

# 700. Order Creation

```text
Get Cart
 ↓
Validate Non-Empty
 ↓
Revalidate Stock
 ↓
Create Order
 ↓
Create OrderItems
 ↓
Snapshot Data
 ↓
Reduce Stock
 ↓
Clear Cart
```

---

# 701. Why OrderItem Snapshot?

Product data can change later.

Historical order should still preserve what was purchased at that time.

Concept:

```text
Current Product
≠
Historical Order Snapshot
```

---

# 702. Cancellation

```text
Find Order
 ↓
Validate Owner
 ↓
Validate State
 ↓
Restore Stock
 ↓
CANCELLED
```

---

# PART 190 — PAYMENT RAPID REVISION

# 703. Payment Rules

```text
Authenticated user
Own order
Order not cancelled
No existing payment
Amount derived from order
Generate transaction ID
Mock SUCCESS
```

---

# 704. Why Amount Comes From Order?

Never trust a client to decide an authoritative financial amount when the server already owns the correct order total.

```text
Client
→ orderId + paymentMethod

Server
→ trusted amount
```

---

# 705. Why Duplicate Payment Returns Conflict?

The request may be structurally valid, but it conflicts with current business state.

Therefore:

```text
409 Conflict
```

is appropriate for our duplicate-payment scenario.

---

# PART 191 — API AUTOMATION RAPID REVISION

# 706. Framework Flow

```text
TestNG Test
     ↓
BaseTest
     ↓
API Client
     ↓
RequestSpecFactory
     ↓
REST Assured
     ↓
Backend
     ↓
Response
     ↓
Assertions
```

Supporting:

```text
ApiConfig
AuthHelper
TestDataFactory
TestDataCleanup
JDBC Helpers
Allure
```

---

# 707. Why Separate Automation Project?

```text
Application
≠
Test framework
```

Automation should be capable of targeting a deployed environment externally.

Benefits:

```text
Independent maintenance
Environment switching
Cleaner architecture
CI readiness
```

---

# 708. ApiConfig

Responsibility:

```text
Resolve environment configuration
```

Supports conceptually:

```text
local
qa
stage
```

plus:

```text
BASE_URL override
```

---

# 709. RequestSpecFactory

Centralizes:

```text
Base URI
Content type
Authentication
Reporting filter
```

---

# 710. AuthHelper

Provides authentication tokens.

```text
Normal User
→ token

Admin
→ adminToken
```

---

# 711. BaseTest

Provides common test setup such as:

```text
token
adminToken
ProductClient
CartClient
OrderClient
PaymentClient
```

---

# 712. Client Layer

Examples:

```text
ProductClient
CartClient
OrderClient
PaymentClient
```

Responsibility:

```text
HTTP communication
```

Tests own:

```text
Assertions
```

---

# PART 192 — TEST TYPES RAPID REVISION

# 713. Functional

```text
Does valid behavior work?
```

---

# 714. Negative

```text
Does invalid behavior get rejected?
```

---

# 715. Validation

```text
Are input rules enforced?
```

---

# 716. Security

```text
Are authentication and access boundaries enforced?
```

---

# 717. RBAC

```text
Are role permissions correct?
```

---

# 718. Schema

```text
Is response contract structurally correct?
```

---

# 719. Database

```text
Was expected state actually persisted?
```

---

# PART 193 — THREE VALIDATION LAYERS

# 720. Memorize This

```text
Response
= What did API return?

Schema
= Is contract structure correct?

Database
= What was actually persisted?
```

Plus:

```text
Business Rules
= Were correct side effects produced?
```

---

# PART 194 — JDBC RAPID REVISION

# 721. JDBC

```text
Java Database Connectivity
```

Flow:

```text
Connection
 ↓
PreparedStatement
 ↓
SQL
 ↓
ResultSet
 ↓
Typed DB Record
 ↓
Assertion
```

---

# 722. Why PreparedStatement?

```text
Parameterized SQL
Cleaner value binding
Better type handling
Reduced injection risk
```

---

# 723. Why Not DB Validate Every Test?

Because database validation increases implementation coupling.

Use it selectively for important:

```text
Persistence
Integration
Business side effects
```

---

# PART 195 — ENVIRONMENT RAPID REVISION

# 724. Configuration

```text
.env
→ real local configuration
→ ignored

.env.example
→ placeholders
→ committed
```

---

# 725. Local API Test Runner

```text
run-tests-local.sh
     ↓
source .env
     ↓
export variables
     ↓
mvn clean test
```

---

# 726. Fail Fast

If:

```text
TEST_ENV=qa
```

but required QA configuration is missing:

```text
Fail immediately
```

Do not silently run against another environment.

---

# PART 196 — ALLURE RAPID REVISION

# 727. Allure Purpose

```text
Readable test reporting
Execution context
Failure evidence
Request/response attachments
```

---

# 728. Security Problem

Initial API evidence exposed:

```text
Bearer JWT
```

This was unacceptable.

---

# 729. Solution

```text
SanitizedAllureFilter
```

Architecture:

```text
Real Request
 ├── Backend gets real credentials
 └── Allure gets sanitized copy
```

---

# 730. What Is Redacted?

Examples:

```text
Authorization
Cookies
API keys
Passwords
Tokens
Secrets
```

---

# 731. Why Full Regression After Filter Change?

Because filter participates in HTTP processing.

We needed to prove it did not change actual API behavior.

Result:

```text
48 / 48
```

---

# PART 197 — SWAGGER RAPID REVISION

# 732. OpenAPI

```text
REST API description specification
```

---

# 733. Swagger UI

```text
Interactive interface for exploring documented APIs
```

---

# 734. Swagger JWT

```text
Login
 ↓
Get JWT
 ↓
Swagger Authorize
 ↓
Paste raw JWT
 ↓
Execute protected API
```

Swagger adds:

```text
Bearer
```

for our configured bearer scheme.

---

# 735. Swagger vs Postman vs REST Assured

```text
Swagger
→ Documentation + exploration

Postman
→ Manual/ad-hoc API testing

REST Assured
→ Automated regression
```

---

# PART 198 — DOCKER RAPID REVISION

# 736. Current Docker Usage

Currently:

```text
PostgreSQL
```

runs through Docker.

Do NOT claim:

```text
Complete application is Dockerized.
```

That is future work.

---

# 737. Important Commands

```bash
docker compose up -d
docker compose ps
docker ps
docker compose down
```

---

# PART 199 — GIT RAPID REVISION

# 738. Git Flow

```text
Working Directory
 ↓
git add
 ↓
Staging
 ↓
git commit
 ↓
Local Repository
 ↓
git push
 ↓
Remote Repository
```

---

# 739. Before Commit

```text
Review status
Check .gitignore
Check .env
Scan secrets
Review staged files
Review staged diff
Commit
```

---

# PART 200 — DEBUGGING RAPID REVISION

# 740. Debugging Formula

```text
Reproduce
 ↓
Inspect Evidence
 ↓
Classify
 ↓
Isolate
 ↓
Find Root Cause
 ↓
Fix Correct Layer
 ↓
Re-test
 ↓
Regression
```

---

# 741. Never Do This

```text
Test failed
 ↓
Change assertion until test passes
```

Correct:

```text
Understand why actual != expected.
```

---

# PART 201 — TOP 20 RAPID-FIRE INTERVIEW QUESTIONS

# 742. What Is REST Assured?

> A Java library used to test REST APIs through readable HTTP request construction and response validation.

---

# 743. What Is TestNG?

> A Java testing framework that provides test execution, annotations, lifecycle management and organization.

---

# 744. What Is JWT?

> A signed token format commonly used to carry authentication claims between parties. In this project it supports stateless API authentication.

---

# 745. Authentication vs Authorization?

> Authentication establishes identity; authorization determines what that identity is allowed to do.

---

# 746. 401 vs 403?

> 401 indicates missing or invalid authentication, while 403 indicates an authenticated caller does not have sufficient permission.

---

# 747. What Is RBAC?

> Role-Based Access Control assigns permissions according to roles such as ROLE_USER and ROLE_ADMIN.

---

# 748. Why RequestSpecFactory?

> To centralize reusable REST Assured configuration such as base URI, content type, authentication and reporting filters.

---

# 749. Why API Client Classes?

> To separate endpoint communication from test assertions and keep tests focused on business behavior.

---

# 750. Why BaseTest?

> To provide reusable setup such as authentication tokens and API clients across test classes.

---

# 751. Why JSON Schema?

> To validate the structural API contract, including expected fields and data types.

---

# 752. Why JDBC?

> To independently verify persistence and critical database side effects.

---

# 753. Why Docker?

> To provide a consistent local PostgreSQL environment without requiring machine-specific database installation and configuration.

---

# 754. Why Allure?

> To provide readable execution reports and useful debugging evidence.

---

# 755. Why Custom Allure Filter?

> To retain API request and response evidence while preventing sensitive credentials from being exposed in test reports.

---

# 756. Why Environment Variables?

> To keep environment-specific configuration and secrets outside the test source code.

---

# 757. Why .env.example?

> To document required environment variables without committing real credentials.

---

# 758. Why Swagger?

> To expose interactive API documentation based on the OpenAPI specification.

---

# 759. Why Database Cleanup?

> To keep tests repeatable and prevent test-created data from affecting later executions.

---

# 760. Why Unique Test Data?

> To avoid collisions between repeated or potentially parallel executions.

---

# 761. Why API Before UI?

> API automation provides faster and more stable coverage for business logic, while UI automation should focus on important browser journeys and presentation behavior.

---

# PART 202 — TOP 10 SENIOR SDET QUESTIONS

# 762. How Do You Design an Automation Framework?

> "I first identify the application architecture, risks, environments and test layers. I separate test intent from technical communication, centralize configuration and authentication, define reusable domain clients, create controlled test data and cleanup, and ensure failures produce useful evidence. I also design the framework so it can run outside a developer machine and evolve toward CI/CD."

---

# 763. How Do You Decide What to Automate?

> "I prioritize repeatable, stable and business-critical scenarios with high regression value. I automate business rules at the lowest practical layer and reserve UI automation for workflows where browser behavior itself matters."

---

# 764. How Do You Handle Flaky Tests?

> "I identify the source of nondeterminism instead of masking failures with retries. I look for shared state, timing assumptions, unstable dependencies, test-data collisions and environment issues, then correct the root cause."

---

# 765. How Do You Make Tests Maintainable?

> "I keep responsibilities separated, centralize cross-cutting configuration, use domain clients, avoid duplicated setup, generate isolated test data and keep assertions focused on business intent."

---

# 766. How Do You Handle Secrets?

> "I externalize secrets, keep real environment files out of Git, use safe templates, sanitize reporting artifacts and plan to use protected CI secret storage for pipeline execution."

---

# 767. How Do You Debug Failures?

> "I reproduce the failure, inspect request and response evidence, classify whether it is an application, automation, data, environment or configuration issue, inspect database or logs where needed, isolate the failing layer and then apply the fix at the correct layer."

---

# 768. How Do You Test Security?

> "I separate authentication, role authorization and resource ownership. I validate missing authentication, insufficient privileges and attempts to access or modify resources outside the caller's permitted scope."

---

# 769. How Do You Validate End-to-End Business Logic?

> "For important workflows I validate the API response, business side effects and selected persisted state. For example, order creation should create the order, snapshot items, reduce inventory and clear the cart."

---

# 770. How Would You Run This in CI?

> "I would provision the required services, inject configuration through protected CI secrets, build the application, run the appropriate automated suites and publish test results. GitHub Actions is the planned CI platform for this project."

---

# 771. How Would You Scale This Project?

> "I would add the React frontend and Playwright coverage first, then containerize the complete application, integrate GitHub Actions, introduce performance testing with k6 and deploy to AWS. As the suite grows I would also consider parallel execution, test tagging and stronger observability."

---

# PART 203 — INTERVIEW DAY REVISION PLAN

# 772. If You Have 60 Minutes

Use:

```text
10 min
→ Architecture + request flow

10 min
→ JWT + Spring Security + RBAC

10 min
→ Product + Cart + Order + Payment

10 min
→ REST Assured framework

10 min
→ JDBC + Schema + Allure

10 min
→ Problems + interview answers
```

---

# 773. If You Have 30 Minutes

Review:

```text
Project architecture
JWT flow
401 vs 403
Order flow
Payment flow
Framework architecture
JDBC
Allure security story
30-sec explanation
2-min explanation
```

---

# 774. If You Have 10 Minutes

Memorize:

```text
Project purpose

Architecture:
Test → API → Backend → DB

Security:
JWT + RBAC + 401/403

Business:
Product → Cart → Order → Payment

Automation:
REST Assured + TestNG + JDBC + Schema

Engineering:
Environment + Allure + Swagger

Strong challenge:
JWT redaction in Allure

Current result:
48/48 API regression
```

---

# PART 204 — FIVE STORIES TO PREPARE

# 775. Story 1 — Framework Architecture

Be ready to explain:

```text
Why automation is separate
Why client layer
Why RequestSpecFactory
Why BaseTest
Why ApiConfig
```

---

# 776. Story 2 — Security

Explain:

```text
JWT
ROLE_USER
ROLE_ADMIN
401
403
Resource ownership
```

---

# 777. Story 3 — Business Workflow

Use:

```text
Order creation
```

because it demonstrates:

```text
Cart
Inventory
Order
OrderItems
Database
Business side effects
```

---

# 778. Story 4 — Framework Problem

Use:

```text
JWT exposed in Allure
        ↓
Identified security risk
        ↓
SanitizedAllureFilter
        ↓
48/48 regression
```

---

# 779. Story 5 — Database Problem

Use:

```text
Foreign-key cleanup issue
        ↓
Understand parent/child dependency
        ↓
Correct deletion order
```

---

# PART 205 — QUESTIONS YOU SHOULD ASK YOURSELF

# 780. Self-Test Without Looking At Notes

Try answering aloud:

```text
Can I draw the architecture?

Can I explain JWT request flow?

Can I explain 401 vs 403?

Can I explain why USER cannot create product?

Can I explain why stock is checked twice?

Can I explain order snapshots?

Can I explain cancellation stock restoration?

Can I explain payment amount security?

Can I explain duplicate payment handling?

Can I explain RequestSpecFactory?

Can I explain why clients return Response?

Can I explain JSON Schema vs DB validation?

Can I explain JDBC?

Can I explain environment switching?

Can I explain .env vs .env.example?

Can I explain the Allure JWT problem?

Can I explain SanitizedAllureFilter?

Can I explain Swagger JWT authorization?

Can I explain one real debugging story?

Can I explain what we are building next?
```

If any answer is weak:

```text
Do not memorize the answer.

Go back to that detailed section
and understand the flow.
```

---

# PART 206 — FINAL PROJECT MEMORY DIAGRAM

# 781. Everything In One Diagram

```text
                        USER / CLIENT
                             │
                             ▼
                    SPRING BOOT REST API
                             │
                    ┌────────┴────────┐
                    │                 │
                    ▼                 ▼
             SPRING SECURITY      CONTROLLERS
                    │                 │
                    ▼                 ▼
                  JWT              SERVICES
                    │                 │
                    ▼                 ▼
                  RBAC           REPOSITORIES
                                      │
                                      ▼
                                  POSTGRESQL


                     API AUTOMATION
                           │
                           ▼
                        TESTNG
                           │
                           ▼
                       BASETEST
                           │
                           ▼
                     API CLIENTS
                           │
                           ▼
                 REQUESTSPECFACTORY
                    /             \
                   ▼               ▼
              APICONFIG     SANITIZED ALLURE
                   \               /
                    \             /
                     ▼           ▼
                      REST ASSURED
                           │
                           ▼
                    SPRING BOOT API
                           │
                           ▼
                      POSTGRESQL
                           ▲
                           │
                          JDBC


                    SUPPORTING TOOLS

             Docker → PostgreSQL runtime

             Swagger → API documentation

             Allure → secure reporting

             Git → source control

             .env → local configuration
```

---

# PART 207 — CURRENT VS FUTURE

# 782. CURRENTLY IMPLEMENTED

```text
Java 17
Spring Boot
PostgreSQL
Docker PostgreSQL
JWT
Spring Security
RBAC
Product
Cart
Order
Mock Payment
REST Assured
TestNG
JSON Schema
JDBC
Environment Switching
Allure
Secure Request/Response Evidence
Swagger/OpenAPI
48-Test Regression
```

---

# 783. NEXT

```text
React
TypeScript
Vite
Playwright
UI + API Hybrid Testing
Full Application Dockerization
GitHub Actions
k6
AWS
```

Do not mix:

```text
Current
```

with:

```text
Planned
```

during interviews.

---

# PART 208 — FINAL 30-SECOND ANSWER

# 784. Memorize the Structure, Not Every Word

> "I built a commerce-based end-to-end SDET portfolio project using Java, Spring Boot and PostgreSQL with JWT authentication and role-based security. The application covers products, cart, orders, inventory and mock payments. I also built a separate REST Assured and TestNG automation framework covering functional, negative, security, schema and JDBC database validation, with environment configuration, secure Allure reporting and Swagger documentation. The current API suite has 48 passing regression tests, and the next phase is React and Playwright."

---

# PART 209 — FINAL 2-MINUTE STRUCTURE

# 785. Remember These Six Blocks

Do not memorize a paragraph.

Remember:

```text
1. WHY
   → End-to-end Senior SDET portfolio

2. APPLICATION
   → Spring Boot + PostgreSQL

3. SECURITY
   → JWT + RBAC

4. BUSINESS
   → Product + Cart + Order + Payment

5. AUTOMATION
   → REST Assured + TestNG + Schema + JDBC + Allure

6. NEXT
   → React + Playwright + CI/CD + Performance + AWS
```

If you remember these six blocks, you can naturally explain the project.

---

# PART 210 — FINAL PRINCIPLE

# 786. What Should This Project Teach You?

The objective is not:

```text
Memorize Java code.
```

The objective is:

```text
Understand the system
        ↓
Understand the risk
        ↓
Choose the right test layer
        ↓
Design maintainable automation
        ↓
Validate business behavior
        ↓
Debug using evidence
        ↓
Protect sensitive information
        ↓
Deliver confidence
```

That is the mindset of a strong SDET and Quality Engineer.

---

# END OF CURRENT BACKEND + API AUTOMATION LEARNING NOTES

These notes represent the project through the current:

```text
Backend
+
API Automation
+
Security
+
Database Validation
+
Reporting
+
Swagger
```

milestone.

Future sections should be added only after we actually implement:

```text
Frontend
Playwright
CI/CD
Performance
Cloud
```

so that the documentation always reflects what we can genuinely explain and demonstrate.

---

# PART 211 — FIRST GITHUB MILESTONE

## 1. Why This Milestone Matters

Until this point, the project was mainly running locally.

Now the project has reached its first professional source-control milestone:

```text
Local Project
     ↓
Git Repository
     ↓
Security Review
     ↓
Professional Commit
     ↓
GitHub Remote
     ↓
Public GitHub Repository
```

Repository:

```text
SDET-Commerce-Automation
```

GitHub user:

```text
anim03
```

Repository visibility:

```text
PUBLIC
```

This means the project is now becoming:

```text
Learning Project
      ↓
Engineering Project
      ↓
Portfolio Project
```

---

# PART 212 — STAGING AND SECURITY VERIFICATION

## 1. Why Security Review Was Important

Before publishing the repository, we did not blindly run:

```bash
git add .
git commit
git push
```

First we checked that sensitive and generated files would not accidentally reach GitHub.

Important categories:

```text
.env files
credentials
JWT secrets
database passwords
tokens
build output
IDE files
temporary files
reports
backup files
```

---

## 2. `.gitignore`

The root `.gitignore` protects categories such as:

```text
environment files
IDE metadata
OS-generated files
build directories
logs
reports
temporary files
Node dependencies
frontend build output
coverage
```

At the same time:

```text
.env.example
```

is intentionally allowed.

Why?

Because:

```text
.env
→ real local configuration

.env.example
→ safe configuration template
```

---

## 3. Safe Environment Templates

The repository contains safe example files:

```text
backend/.env.example
api-automation/.env.example
```

Real local files such as:

```text
backend/.env
api-automation/.env
```

must remain outside Git.

Rule:

```text
Commit configuration structure.
Never commit actual secrets.
```

---

## 4. Verify Ignored Files

Useful command:

```bash
git check-ignore -v backend/.env
```

and:

```bash
git check-ignore -v api-automation/.env
```

This helps verify:

```text
which .gitignore rule
is protecting the file
```

---

## 5. Review Repository Before Commit

Important commands:

```bash
git status
```

```bash
git diff
```

```bash
git diff --cached
```

Meaning:

```text
git status
→ what changed?

git diff
→ what is modified but unstaged?

git diff --cached
→ what exactly will be committed?
```

Senior SDET mindset:

```text
Do not trust the command.

Inspect the evidence.
```

---

## 6. Cleanup Before Publishing

Temporary development files were cleaned before the milestone.

Example cleanup commands:

```bash
find . -name "*.backup" -not -path "./.git/*" -delete
```

```bash
find . -name "*.bak" -not -path "./.git/*" -delete
```

```bash
find . -name ".DS_Store" -not -path "./.git/*" -delete
```

Why?

Because a professional repository should not contain:

```text
editor backups
OS metadata
temporary copies
accidental generated files
```

---

## 7. Secret Scanning Mindset

Before public push, inspect for patterns such as:

```text
password
secret
token
Authorization
private key
API key
```

But remember:

```text
Keyword found
≠
Secret definitely found
```

For example:

```text
password
```

can legitimately appear in:

```text
documentation
test descriptions
placeholder configuration
security code
```

Therefore:

```text
Search
→ Inspect
→ Classify
→ Fix if required
```

---

## 8. Important Security Principle

If a real secret is ever committed and pushed:

```text
Deleting the line later is not sufficient.
```

Correct response:

```text
1. Rotate/revoke the credential
2. Remove it from current source
3. Clean history if required
4. Verify repository again
5. Add preventive controls
```

Treat exposed credentials as compromised.

---

# PART 213 — FIRST PROFESSIONAL COMMIT

## 1. Commit Created

After the repository review, the first major professional milestone commit was created.

Commit:

```text
cf23ac6
```

Message:

```text
feat: complete backend and API automation milestone
```

---

## 2. Why This Commit Message Is Good

Structure:

```text
feat:
```

indicates a feature/milestone-oriented change.

Message:

```text
complete backend and API automation milestone
```

describes the logical state being captured.

Better than messages like:

```text
changes
updated files
final
code
my project
```

---

## 3. Commit Size

This was intentionally a large initial milestone commit because the project had already been developed substantially before GitHub publication.

Recorded change:

```text
108 files changed
26150 insertions
2026 deletions
```

For future development:

```text
prefer smaller logical commits
```

because they are easier to:

```text
review
debug
revert
understand
cherry-pick
```

---

## 4. Working Tree After Commit

After the milestone:

```text
working tree clean
```

means there were no remaining tracked/untracked changes requiring attention at that point.

Useful command:

```bash
git status
```

Expected clean-state idea:

```text
nothing to commit
working tree clean
```

---

# PART 214 — GITHUB REMOTE

## 1. Public Repository Created

Repository:

```text
SDET-Commerce-Automation
```

Remote repository:

```text
GitHub
```

Visibility:

```text
Public
```

It was intentionally created without generating another:

```text
README
.gitignore
license
```

because these already existed locally or were being managed from the local project.

---

## 2. Why Avoid Auto-Generated README?

If GitHub creates an initial commit while local Git already has unrelated history:

```text
Local History
      +
Remote Initial Commit
```

can create unnecessary history reconciliation.

Creating an empty remote allowed:

```text
existing local repository
→ push directly
```

---

## 3. Origin

The remote name used is:

```text
origin
```

Conceptually:

```text
origin
→ conventional alias for primary remote repository
```

It is not a Git keyword requiring GitHub specifically.

---

## 4. Verify Remote

Command:

```bash
git remote -v
```

This displays:

```text
fetch remote
push remote
```

Always verify before first push.

Why?

Because pushing to the wrong repository can expose:

```text
code
history
configuration
```

---

# PART 215 — GITHUB CLI AUTHENTICATION

## 1. Initial Push Authentication

The repository used:

```text
HTTPS
```

for GitHub remote communication.

Modern GitHub HTTPS authentication should not use the GitHub account password as the Git credential.

---

## 2. GitHub CLI

GitHub CLI was installed:

```bash
gh
```

Version at setup time:

```text
2.100.0
```

---

## 3. Authentication

Authentication was completed using GitHub CLI's browser-based login flow.

Conceptually:

```text
Terminal
   ↓
GitHub CLI
   ↓
Browser authorization
   ↓
GitHub account
   ↓
Authenticated Git operations
```

---

## 4. Verify Authentication

Useful command:

```bash
gh auth status
```

This can confirm:

```text
authenticated account
Git protocol
authentication status
```

Security rule:

```text
Never paste authentication tokens
into project notes, source code,
screenshots or GitHub issues.
```

---

## 5. HTTPS vs SSH

Both are valid GitHub authentication approaches.

HTTPS:

```text
https://...
```

SSH:

```text
git@github.com:...
```

Our repository currently uses:

```text
HTTPS
```

No need to change to SSH only for appearance.

---

# PART 216 — FIRST PUSH

## 1. Push Command

The local `main` branch was pushed to GitHub.

Result:

```text
main
→ origin/main
```

The branch was also configured to track the remote branch.

---

## 2. Upstream Tracking

Conceptually:

```text
local main
    │
    ▼
origin/main
```

Once upstream is configured, future pushes can generally use:

```bash
git push
```

instead of repeatedly specifying:

```text
remote + branch
```

---

## 3. First Push Result

The important result was:

```text
[new branch] main -> main
```

and:

```text
branch 'main' set up to track 'origin/main'
```

This confirmed:

```text
local repository
and
GitHub repository
```

were connected successfully.

---

## 4. GitHub Verification

After push, the public repository was checked for:

```text
README rendering
source structure
expected files
absence of real .env files
project milestone visibility
```

Publishing successfully is not enough.

Always verify what the public user can actually see.

---

# PART 217 — CURRENT PROJECT STATUS

## 1. Backend

Completed:

```text
Java 17
Spring Boot
REST APIs
PostgreSQL
JPA
JWT Authentication
RBAC
Swagger/OpenAPI
externalized configuration
```

---

## 2. Commerce Modules

Completed:

```text
User Registration
Login
Profile

Products
Search
Details
Admin CRUD

Cart
Add
Update
Remove
Clear

Orders
Create
Get
List
Cancel

Mock Payment

Admin / RBAC
```

---

## 3. API Automation

Completed:

```text
Java
REST Assured
TestNG
reusable request specification
authentication helper
environment switching
test-data utilities
cleanup
database validation
security testing
RBAC testing
Allure reporting
request/response evidence
secret sanitization
```

---

## 4. Regression Baseline

Current API regression:

```text
48 tests
48 passed
```

This baseline is important.

Future CI should not only ask:

```text
Did Maven return success?
```

It should also help confirm:

```text
Did the expected test suite actually execute?
```

---

## 5. Allure

Completed:

```text
TestNG integration
environment metadata
request attachment
response attachment
sensitive-data sanitization
```

Sensitive values are replaced in report evidence with:

```text
[REDACTED]
```

---

## 6. Swagger/OpenAPI

Completed:

```text
Swagger UI
OpenAPI document
Bearer JWT security scheme
Authorize functionality
```

This provides interactive API exploration alongside automated regression.

---

## 7. Database

Current:

```text
PostgreSQL 16
```

runs using:

```text
Docker Compose
```

with persistent storage.

---

## 8. Git/GitHub

Completed:

```text
Git repository
.gitignore
secret review
professional milestone commit
GitHub public repository
HTTPS authentication
first push
upstream tracking
```

---

## 9. Documentation

Current documentation includes:

```text
README.md

docs/learning-notes/
    SDET-Learning-Notes.md
    Linux-Git-Real-World-Notes.md
    SDET-Engineering-Tooling-Notes.md
```

Each file has a different purpose:

```text
SDET-Learning-Notes
→ project learning journey

Linux-Git-Real-World-Notes
→ Linux/Git/CI troubleshooting knowledge

SDET-Engineering-Tooling-Notes
→ Maven/Java/Spring/HTTP/Docker/Node/config/tooling
```

Do not merge everything into one document.

---

# PART 218 — NEXT PHASE: REACT + TYPESCRIPT

## 1. Why Frontend Is Next

Current system already has:

```text
Backend
API
Database
Security
API Automation
Reporting
Documentation
GitHub
```

But there is currently no real user-facing web application.

Therefore next:

```text
React + TypeScript frontend
```

---

## 2. Future Architecture

```text
               React + TypeScript
                       │
                       │ HTTP
                       ▼
                  Spring Boot
                       │
                       ▼
                  PostgreSQL
```

Testing:

```text
Playwright
    │
    ▼
React UI
    │
    ▼
Spring Boot API
    │
    ▼
PostgreSQL
```

Existing API automation remains:

```text
REST Assured
     │
     ▼
Spring Boot
```

---

## 3. Frontend Technology

Planned:

```text
React
TypeScript
Vite
React Router
Axios
simple CSS
```

Avoid adding unnecessary UI libraries initially.

Goal:

```text
functional
clean
testable
professional
```

not:

```text
complex visual design
```

---

## 4. Planned Screens

```text
Login

Register

Products

Product Search

Product Details

Cart

Checkout / Create Order

Orders

Payment

Admin Product Management
```

---

## 5. RBAC in Frontend

Normal user:

```text
shopping functionality
```

Admin:

```text
product management functionality
```

Important:

```text
Hiding an admin button in UI
is NOT authorization.
```

Backend RBAC remains the real security boundary.

UI controls only improve user experience.

---

## 6. Frontend Environment Configuration

Future example:

```text
VITE_API_BASE_URL
```

Local development value can point to:

```text
http://localhost:8080
```

Important:

```text
VITE_* values are client-side.
```

Therefore never put:

```text
JWT signing secret
database password
private API credential
```

inside frontend environment variables.

---

## 7. CORS

Future frontend development may run on:

```text
http://localhost:5173
```

while backend runs on:

```text
http://localhost:8080
```

These are different origins because:

```text
port differs
```

Therefore browser communication may require:

```text
CORS configuration
```

This will be implemented only when needed and verified against the actual frontend/backend behavior.

---

## 8. Authentication

Frontend will:

```text
Login
   ↓
Receive JWT
   ↓
Use token for protected API requests
```

For portfolio/demo implementation, token storage strategy will be chosen explicitly.

If localStorage is used:

```text
simple
but accessible to JavaScript
```

which creates an XSS-related security trade-off.

Production alternatives may include:

```text
HttpOnly
Secure
SameSite cookies
```

depending on architecture.

---

## 9. Why TypeScript?

TypeScript provides:

```text
static typing
better IDE support
safer refactoring
clear API models
```

It is especially useful when building:

```text
React frontend
+
Playwright automation
```

because the same ecosystem reinforces JavaScript/TypeScript knowledge.

---

## 10. Why React Before Playwright?

Do not create UI automation before having the actual UI.

Correct sequence:

```text
Backend
    ↓
API Stable
    ↓
Frontend
    ↓
Critical UI Flows
    ↓
Playwright
```

This prevents us from creating:

```text
fake UI automation
or
portfolio-only test scripts
```

without a real system under test.

---

## 11. Playwright Strategy Later

Playwright should primarily cover:

```text
Login
Registration
Product browsing
Search
Cart
Checkout
Order creation
Payment
Admin product flow
```

Business-rule-heavy validation should continue to stay mainly in:

```text
API tests
```

This maintains a healthy test pyramid.

---

## 12. UI + API Hybrid Testing

Later we can use:

```text
API
→ prepare state

UI
→ validate user experience

API/DB
→ verify resulting state
```

Example:

```text
API creates product
      ↓
Playwright searches product
      ↓
UI adds product to cart
      ↓
UI creates order
      ↓
API/DB verifies order
```

This is much stronger than writing only isolated UI scripts.

---

## 13. Frontend Must Use Real Backend

Important project rule:

```text
No fake static frontend data
for core commerce flows.
```

Frontend should consume the actual:

```text
Spring Boot APIs
```

we already built.

That makes the project truly:

```text
end-to-end
```

---

## 14. Before Creating Frontend

First verify installed runtime:

```bash
node --version
npm --version
```

Do not scaffold anything until these commands are checked.

Then choose a compatible Vite/React setup.

---

## 15. Next Immediate Practical Step

From project root:

```bash
cd ~/SDET-Commerce-Automation
```

Verify location:

```bash
pwd
```

Then:

```bash
node --version
npm --version
```

At this point:

```text
STOP.
```

Do not run Vite creation command yet.

First understand:

```text
which Node version
which npm version
```

are actually installed.

Then we will create:

```text
frontend/
```

cleanly without disturbing:

```text
backend/
api-automation/
docs/
```

---

# END OF PART 218

# GITHUB MILESTONE DOCUMENTATION COMPLETE
---

# PART 219 — FRONTEND TO CI/CD: COMPLETE END-TO-END SDET MILESTONE

This part documents the practical evolution of the project from a backend/API automation project into a complete full-stack Quality Engineering system.

The implemented architecture became:

React + TypeScript Frontend
            ↓
Spring Boot REST API
            ↓
PostgreSQL Database

REST Assured + TestNG
            ↓
API + DB Validation

Playwright + TypeScript
            ↓
Browser UI Validation

Docker Compose
            ↓
Frontend + Backend + PostgreSQL

GitHub Actions
            ↓
Continuous Quality Validation

Important learning:

A Senior SDET does not only write tests.

A Senior SDET understands how the application,
automation frameworks, infrastructure,
test data and CI pipeline work together.

---

# SECTION 2590 — REACT + TYPESCRIPT FRONTEND IMPLEMENTATION

## 2805. Why We Added a Frontend

Initially the project contained:

Spring Boot backend
REST APIs
PostgreSQL
REST Assured automation

That was strong for API engineering, but it was not yet a complete end-to-end commerce application.

We therefore added:

React
TypeScript
Vite

The frontend communicates with the real Spring Boot backend.

Important rule followed:

No fake static data for core commerce flows.

The UI consumes the actual APIs.

## 2806. Frontend Business Flows Implemented

The frontend now supports:

Login
Dashboard
Products
Product Search
Product Details
Cart
Checkout
Order Creation
Order Details
Mock Payment
Admin Product Management
Role-Based Admin Access

This means the same business system can now be tested at multiple layers:

UI
API
Database

## 2807. Authentication in the Frontend

The frontend authenticates against the Spring Boot login API.

High-level flow:

User enters credentials
        ↓
React sends login request
        ↓
Spring Boot validates user
        ↓
JWT returned
        ↓
Frontend stores authentication state
        ↓
Protected routes become accessible

The frontend also derives the user's role so that ADMIN-specific functionality can be protected.

## 2808. RBAC in the UI

The application contains two important roles:

ROLE_USER
ROLE_ADMIN

Normal users can use commerce functionality.

Admin users additionally receive access to product-management functionality.

But an important security principle is:

UI restriction is not security by itself.

Real authorization remains enforced by Spring Security on the backend.

For example:

USER
→ POST /api/products
→ 403 Forbidden

ADMIN
→ POST /api/products
→ allowed

The UI simply provides the correct user experience on top of backend authorization.

---

# SECTION 2591 — PLAYWRIGHT + TYPESCRIPT UI AUTOMATION

## 2809. Why Playwright Was Added

REST Assured validates backend behaviour.

Playwright validates:

what the actual user experiences in the browser

The purpose was not to duplicate every API test at UI level.

Instead:

API tests
→ broad business-rule coverage

UI tests
→ critical user journeys

This maintains the test pyramid.

## 2810. Playwright Framework Structure

The Playwright framework was created separately under:

ui-automation/

Important framework concepts include:

Page Object Model
Reusable fixtures
Environment configuration
Authentication setup projects
Storage State
Dynamic test data
API-assisted setup
Role-specific browser projects
Failure artifacts

## 2811. Page Object Model

Reusable page classes were created for pages such as:

LoginPage
DashboardPage
ProductsPage
ProductDetailsPage
CartPage
CheckoutPage
OrderDetailsPage
PaymentPage
AdminProductsPage

Tests therefore focus mainly on:

business intent

rather than repeatedly containing low-level locators.

Example concept:

Test:

open products
search product
open product
add to cart

Page Object:

contains the locators and UI operations

Benefits:

maintainability
readability
reuse
lower duplication

---

# SECTION 2592 — PLAYWRIGHT AUTHENTICATION ARCHITECTURE

## 2812. The Problem With Logging In Before Every Test

A naive UI framework might do:

open login page
enter credentials
login
run test

for every test.

This creates:

unnecessary execution time
duplicate login logic
more opportunities for flaky failures

We instead implemented Playwright:

storageState

## 2813. User and Admin Setup Projects

Two authentication setup projects were created:

setup-user
setup-admin

They authenticate once and save browser state into:

auth/user.json
auth/admin.json

Then the browser projects use those states:

chromium-user
chromium-admin

Conceptually:

setup-user
    ↓
auth/user.json
    ↓
chromium-user tests

setup-admin
    ↓
auth/admin.json
    ↓
chromium-admin tests

## 2814. Why Auth Files Must Not Be Committed

Storage-state files may contain authentication information.

Therefore:

auth/user.json
auth/admin.json

must not be committed to Git.

This is another example of:

test automation security hygiene

---

# SECTION 2593 — PLAYWRIGHT ENVIRONMENT SWITCHING

## 2815. Environment-Aware UI Automation

The Playwright framework supports environment configuration.

Conceptually:

TEST_ENV=local
TEST_ENV=qa
TEST_ENV=stage

Environment-specific configuration separates:

UI Base URL
API Base URL
credentials

This prevents environment URLs from being scattered across test files.

## 2816. Why UI and API URLs Are Both Needed

The Playwright framework does more than browser automation.

It also uses APIs for efficient test setup.

Therefore it needs:

UI URL

and

API URL

Example:

API
→ create test product

UI
→ verify product is visible

This is an example of:

UI + API hybrid automation

---

# SECTION 2594 — DYNAMIC TEST DATA

## 2817. Why Fixed Product Data Is Dangerous

Initially UI tests could depend on an already-existing product.

That can work locally because the developer database may contain data.

But:

local DB != clean CI DB

A reliable automation framework should not assume that arbitrary test data already exists.

## 2818. Dynamic Product Creation

A reusable helper was introduced for product test data.

Concept:

Playwright test
      ↓
Admin API authentication
      ↓
POST /api/products
      ↓
Create unique product
      ↓
Perform UI validation
      ↓
Delete test product

Product names are unique.

Conceptually:

PW Product + unique timestamp/value

This prevents tests from depending on manually prepared database records.

## 2819. Why API Setup Is Better Than UI Setup

Suppose the purpose of a test is:

Verify user can search for a product.

Creating the product through the Admin UI first would add unnecessary UI steps.

Better:

API creates prerequisite state
        ↓
UI validates search behaviour

This makes tests:

faster
more focused
less flaky
more independent

---

# SECTION 2595 — UI + API HYBRID TESTING

## 2820. Hybrid Testing Pattern

A powerful SDET pattern is:

API
→ arrange test state

UI
→ perform user behaviour

API / DB
→ validate resulting state when required

This combines the strengths of different testing layers.

## 2821. Example From This Project

For product-related flows:

Admin API
→ create unique product

Playwright
→ navigate to product

Playwright
→ interact with product/cart/order

Cleanup
→ remove temporary state when safe

The UI test does not waste browser time preparing everything.

---

# SECTION 2596 — PLAYWRIGHT COVERAGE

## 2822. Current UI Automation Coverage

The framework currently contains coverage for:

Authentication
Products
Product Search
Product Details
Cart
Clear Cart
Order + Payment
Admin RBAC
Admin Product Management

The suite contains:

setup-user
setup-admin
chromium-user tests
chromium-admin tests

At the completed CI milestone:

11 Playwright test/setup entries

were discovered by Playwright.

## 2823. Tags

Tests use tags such as:

@smoke
@regression
@products
@cart
@orders
@payment
@admin
@rbac
@auth

This allows targeted execution.

Examples:

npm run test:smoke

npx playwright test --grep "@regression"

---

# SECTION 2597 — PLAYWRIGHT FAILURE EVIDENCE

## 2824. Failure Diagnostics

The Playwright configuration captures useful debugging evidence.

On failure we can retain:

Screenshot
Video
Trace
HTML report
Error context

This is important in CI because:

you cannot physically watch
the GitHub runner browser

Evidence must therefore be collected automatically.

## 2825. CI Retry Strategy

In CI:

retries = 2

Locally:

retries = 0

Why?

CI environments may occasionally experience infrastructure timing variation.

But retries should never be used to hide a genuine defect.

Rule:

Retry helps diagnose instability.

Retry is not a substitute for fixing flaky tests.

## 2826. Worker Strategy

The framework currently uses:

1 worker

This is intentional while tests share some application/database/user state.

Running everything aggressively in parallel before proper isolation can create:

cart collisions
shared-user conflicts
data races
cleanup conflicts

Future optimization can increase parallelism after stronger test isolation.

---

# SECTION 2598 — FULL APPLICATION DOCKERIZATION

## 2827. Why Dockerize the Full Application

Before Dockerization, components were started independently.

For example:

PostgreSQL
Spring Boot
React development server

Docker Compose gives us a reproducible application stack.

Final concept:

Docker Compose
   |
   +-- PostgreSQL
   |
   +-- Spring Boot Backend
   |
   +-- React/Nginx Frontend

## 2828. Backend Docker Image

The backend uses a multi-stage Docker build.

Concept:

Stage 1
Java JDK
Maven build
create JAR

        ↓

Stage 2
Java runtime
copy JAR
run application

Why multi-stage?

Because the runtime image does not need the entire build environment.

Benefits:

cleaner runtime
smaller image
separation of build and execution

## 2829. Frontend Docker Image

The frontend also uses a multi-stage build.

Concept:

Node.js
   ↓
npm ci
   ↓
npm run build
   ↓
Vite dist/

        ↓

Nginx
   ↓
serve production frontend

Node is needed to build the React application.

Nginx serves the resulting static production files.

## 2830. SPA Routing With Nginx

React uses client-side routing.

Therefore Nginx needs SPA fallback behaviour.

Concept:

request /orders/123

if static file does not exist

→ return index.html

→ React Router handles route

Without this configuration, directly opening frontend routes can return:

404

---

# SECTION 2599 — DOCKER COMPOSE NETWORKING

## 2831. Host vs Container Networking

This was an important practical concept.

From the host machine:

PostgreSQL
→ localhost:5432

Backend
→ localhost:8080

Frontend
→ localhost:5173

But from the backend container:

localhost

means:

the backend container itself

It does NOT mean the PostgreSQL container.

Therefore backend connects using Docker service DNS:

postgres:5432

Concept:

Backend container
      ↓
postgres:5432
      ↓
PostgreSQL container

## 2832. Docker Service Names

Docker Compose provides internal DNS using service names.

Therefore:

postgres

can act as the hostname inside the Compose network.

This removes the need to know the database container's dynamic IP address.

---

# SECTION 2600 — DOCKER HEALTH CHECK

## 2833. Why PostgreSQL Health Check Matters

Container status:

running

does not necessarily mean:

application ready

PostgreSQL may require some time before accepting connections.

Therefore a health check uses:

pg_isready

Backend startup can then depend on:

PostgreSQL healthy

rather than merely:

PostgreSQL container started

---

# SECTION 2601 — DOCKER VOLUMES

## 2834. PostgreSQL Persistence

PostgreSQL uses a named Docker volume.

This means:

docker compose down

stops/removes containers but preserves the named database volume.

However:

docker compose down -v

also removes the named volume.

Therefore:

-v can delete local database data.

Use it intentionally.

---

# SECTION 2602 — VERIFIED DOCKERIZED APPLICATION

## 2835. Full Stack Validation

After resolving the local port conflict, all three services successfully ran:

PostgreSQL
→ healthy

Backend
→ port 8080

Frontend
→ port 5173

The browser application was then manually verified.

This completed:

Full Application Dockerization

---

# SECTION 2603 — GITHUB ACTIONS CI/CD

## 2836. What GitHub Actions Does

GitHub Actions allows automated workflows to run after repository events.

For this project:

push to main

or

pull request to main

triggers the CI workflow.

## 2837. Core GitHub Actions Concepts

Important terms:

Workflow
→ complete automation definition

Job
→ major independent unit of work

Step
→ command/action inside a job

Runner
→ machine executing the job

Service
→ supporting container such as PostgreSQL

Artifact
→ output retained after execution

---

# SECTION 2604 — CI PIPELINE EVOLUTION

## 2838. Phase 1

Initial CI validated:

Backend Build & Test

Frontend Lint & Build

Docker Build Validation

This established basic continuous integration.

## 2839. Phase 2

Next we added:

REST Assured API Automation

The API job required more than simply:

mvn test

because CI starts with a fresh PostgreSQL database.

---

# SECTION 2605 — EPHEMERAL CI DATABASE

## 2840. Local Database vs CI Database

Local development DB may contain:

users
products
historical orders
test data

A GitHub Actions PostgreSQL service starts fresh.

Therefore:

CI must create everything it requires.

This is one of the most important lessons from this milestone.

## 2841. CI User Bootstrap

The API framework expects:

normal user credentials

admin user credentials

The CI pipeline therefore:

starts PostgreSQL
        ↓
starts Spring Boot
        ↓
registers normal user
        ↓
registers second user
        ↓
promotes second user to ROLE_ADMIN
        ↓
runs REST Assured tests

## 2842. Why We Did Not Add Public Admin Registration

Normal registration creates:

ROLE_USER

Instead of exposing something like:

/register-admin

only for test automation, CI promotes the dedicated temporary user in the ephemeral database.

This preserves the production security model.

Important principle:

Do not weaken application security
just to make automation easier.

---

# SECTION 2606 — API AUTOMATION IN CI

## 2843. REST Assured Execution

Once the CI database and users are ready:

REST Assured
+
TestNG

run against the live Spring Boot application.

This includes:

API validation
security validation
RBAC validation
database validation
business-flow validation

## 2844. CI API Result

The API automation milestone successfully ran the existing suite in GitHub Actions.

Current API regression:

48 tests
48 passed

This proved that the framework works outside the developer laptop.

That distinction matters.

A framework that only works locally is incomplete.

---

# SECTION 2607 — ALLURE AND SUREFIRE CI ARTIFACTS

## 2845. Why Upload Test Artifacts

CI runners are temporary.

When the runner disappears, local files disappear too.

Therefore important results are uploaded as GitHub Actions artifacts.

For API automation:

Allure results
Surefire reports

are retained.

## 2846. Why if: always() Matters

Report upload should happen even if tests fail.

Concept:

tests fail
     ↓
still upload evidence

Otherwise the moment we most need debugging information is the moment it disappears.

---

# SECTION 2608 — PLAYWRIGHT CI INTEGRATION

## 2847. Full UI CI Architecture

The Playwright CI job uses the Dockerized application.

Flow:

GitHub Runner
      ↓
Docker Compose
      ↓
PostgreSQL
Spring Boot
React/Nginx
      ↓
wait for backend
      ↓
wait for frontend
      ↓
create USER
      ↓
create ADMIN
      ↓
promote ROLE_ADMIN
      ↓
install Playwright Chromium
      ↓
setup-user
setup-admin
      ↓
run browser tests
      ↓
upload reports/evidence

## 2848. Why Readiness Checks Matter

Starting Docker containers does not guarantee the application is immediately ready.

Therefore CI polls:

backend endpoint

frontend endpoint

before starting automation.

This avoids:

tests starting while application is still booting

which is a common source of false failures.

---

# SECTION 2609 — PLAYWRIGHT CI ARTIFACTS

## 2849. UI Failure Evidence

The CI pipeline uploads:

Playwright HTML report

test-results

Docker logs on failure

Playwright test-results may contain:

screenshots
videos
trace.zip
error context

This creates an evidence-driven debugging workflow.

---

# SECTION 2610 — REAL CI FAILURE FOUND

## 2850. The Failure

The first complete Playwright CI run produced:

10 passed
1 failed

The failed test was:

Products @smoke @products

authenticated user can view products

The failing assertion searched for:

View Product

button.

Playwright reported:

Expected: visible

Error:
element(s) not found

Retries also failed.

## 2851. Why This Was Interesting

Locally the test had worked.

CI failed.

The immediate temptation could have been:

increase timeout

But that would not solve the actual problem.

The element was not slow.

The element did not exist.

This distinction is critical.

---

# SECTION 2611 — ROOT CAUSE ANALYSIS

## 2852. Root Cause

The products smoke test assumed:

at least one product already exists

That assumption happened to be true in the local development database.

But GitHub Actions created a:

fresh PostgreSQL database

Therefore the Products page could load correctly while containing:

zero products

No product means:

no View Product button

Therefore the assertion failed.

## 2853. The Hidden Dependency

The real bug was not:

Playwright timeout

and not:

Docker

and not:

GitHub Actions

It was:

hidden dependency on pre-existing test data

This is exactly the type of issue CI is supposed to expose.

---

# SECTION 2612 — CORRECT FIX

## 2854. What We Did Not Do

We did NOT:

increase timeout blindly

skip the test

delete the assertion

hardcode a product into CI DB

manually seed random local data

Those approaches would hide the underlying test-design problem.

## 2855. What We Did

The smoke test was changed to:

create its own product dynamically
        ↓
open Products UI
        ↓
verify that exact product
        ↓
verify View Product action
        ↓
clean up product

This made the test:

self-contained
repeatable
environment-independent
CI-safe

---

# SECTION 2613 — FINAL CI RESULT

## 2856. Final Pipeline

After fixing the static-data dependency, GitHub Actions reran successfully.

Final result:

Backend Build & Test
        PASS

Frontend Lint & Build
        PASS

Docker Build Validation
        PASS

REST Assured API Automation
        PASS

Playwright UI Automation
        PASS

The complete workflow became green.

## 2857. What This Proves

The project can now automatically validate:

Backend compilation/tests
Frontend lint/build
Docker image construction
REST API behaviour
Database state
Authentication
Authorization / RBAC
Critical browser journeys
User role
Admin role
Full Dockerized application integration

on a clean GitHub-hosted runner.

---

# SECTION 2614 — CI FAILURE DEBUGGING METHOD

## 2858. Practical Debugging Sequence

When a CI UI test fails:

1. Identify failed job
2. Identify failed step
3. Identify exact test
4. Read assertion error
5. Check retries
6. Inspect screenshot
7. Inspect video
8. Inspect trace
9. Inspect application/container logs if required
10. Determine root cause
11. Fix cause, not symptom
12. Push and rerun CI

## 2859. Example From This Project

Observed:

Locator:
View Product button

Expected:
visible

Actual:
element not found

Question:

Is the element slow,
or does it not exist?

Investigation showed:

it did not exist

Why?

fresh DB had no product

Correct engineering response:

create deterministic test data

not:

increase timeout

---

# SECTION 2615 — IMPORTANT SENIOR SDET LESSON

## 2860.

A weak automation mindset asks:

How can I make this test green?

A stronger SDET mindset asks:

Why did this test fail only in this environment?

What dependency did the test assume?

Can the test prepare its own state?

Can the failure happen again?

What evidence proves the root cause?

That difference matters in senior-level automation work.

---

# SECTION 2616 — INTERVIEW STORY

## 2861. Question

Tell me about a CI-only automation failure you diagnosed.

Strong answer:

In my end-to-end commerce automation project,
a Playwright products smoke test passed locally
but failed consistently in GitHub Actions.

The locator failure initially looked like a UI
timing issue because the View Product button
was not found.

Instead of increasing the timeout, I checked
the CI execution context and realized GitHub
Actions was starting with a fresh PostgreSQL
database.

My local database already contained products,
so the test had an undocumented dependency
on existing data.

I changed the test to create a unique product
through the API before UI validation and clean
it up afterwards.

After the change, the complete pipeline passed.

The main lesson was that reliable automation
must own its prerequisite test data instead of
depending on persistent environment state.

---

# SECTION 2617 — WHY THIS PROJECT IS NOW END-TO-END

## 2862.

The project now connects:

Frontend Engineering
Backend Engineering
Database
API Testing
UI Testing
Security / RBAC
Test Data Management
Docker
CI/CD
Reporting
Failure Diagnostics

This is much closer to real Quality Engineering than simply maintaining isolated Selenium or API scripts.

---

# SECTION 2618 — CURRENT PROJECT ARCHITECTURE

## 2863.

                         GitHub
                            |
                            v
                    GitHub Actions CI
                            |
          +-----------------+------------------+
          |                 |                  |
          v                 v                  v
      Backend            Frontend           Docker
      Build/Test         Lint/Build         Validation
          |
          +------------------+
                             |
                             v
                      API Automation
                             |
                   REST Assured + TestNG
                             |
                    API + DB Validation
                             |
                             v
                      UI Automation
                             |
                    Playwright + TS
                             |
                             v
                 Critical Browser Flows


Application Runtime:

React + TypeScript
        |
        v
Spring Boot REST API
        |
        v
PostgreSQL

---

# SECTION 2619 — CURRENT TEST STRATEGY

## 2864.

The project follows this philosophy:

Many API/integration tests

        +

Focused critical UI tests

        +

Database validation where valuable

Not:

Automate every business rule through UI.

Reason:

API tests
→ faster and more focused

UI tests
→ slower but validate actual user experience

DB tests
→ validate persistence when business risk requires it

---

# SECTION 2620 — TEST DATA STRATEGY

## 2865.

Current principle:

Every important automated test should either:

1. create the state it requires

or

2. explicitly receive controlled test state

Avoid:

"I think this record already exists."

This principle becomes even more important with:

CI
parallel execution
ephemeral environments
cloud deployments

---

# SECTION 2621 — CI/CD VS CI

## 2866.

At this milestone, GitHub Actions primarily provides:

Continuous Integration

because it automatically:

builds
tests
validates
reports

on code changes.

A later AWS stage can introduce automated deployment behaviour.

Therefore in interviews, describe the current implementation precisely:

GitHub Actions CI pipeline

rather than claiming a production deployment pipeline that has not yet been implemented.

---

# SECTION 2622 — COMMANDS TO REMEMBER

## 2867. Docker

docker compose config --quiet
docker compose build
docker compose up -d
docker compose ps
docker compose logs backend
docker compose logs frontend
docker compose logs postgres
docker compose down

Destructive local cleanup:

docker compose down -v

Remember:

-v removes named volumes.

## 2868. Playwright

List tests:

npx playwright test --list

Run all:

npx playwright test

Run user project:

npx playwright test --project=chromium-user

Run admin project:

npx playwright test --project=chromium-admin

Run smoke:

npx playwright test --grep "@smoke"

Run regression:

npx playwright test --grep "@regression"

## 2869. GitHub Actions

Recent runs:

gh run list --limit 5

View run:

gh run view

View failed logs:

gh run view --log-failed

This is useful when debugging CI without relying entirely on the browser UI.

---

# SECTION 2623 — CURRENT COMPLETION STATUS

## 2870.

Completed:

Backend
        COMPLETE

PostgreSQL integration
        COMPLETE

JWT authentication
        COMPLETE

RBAC
        COMPLETE

Products
        COMPLETE

Cart
        COMPLETE

Orders
        COMPLETE

Mock Payment
        COMPLETE

REST Assured API Framework
        COMPLETE

48-Test API Regression
        COMPLETE

Database Validation
        COMPLETE

Allure
        COMPLETE

Swagger/OpenAPI
        COMPLETE

React + TypeScript Frontend
        COMPLETE

Playwright + TypeScript Framework
        COMPLETE

Dynamic UI Test Data
        COMPLETE

UI + API Hybrid Setup
        COMPLETE

Full Application Dockerization
        COMPLETE

GitHub Actions CI
        COMPLETE

API Automation in CI
        COMPLETE

Playwright Automation in CI
        COMPLETE

---

# SECTION 2624 — NEXT PROJECT PHASE

## 2871.

Next:

k6 Performance Testing

Planned progression:

Smoke Performance Test
        ↓
Load Test
        ↓
Stress Test
        ↓
Thresholds
        ↓
Response-Time Validation
        ↓
Error-Rate Validation
        ↓
CI Integration

After performance testing:

AWS Deployment / Cloud Integration

Then:

Final README
GitHub Portfolio
Recruiter-Facing Polish

---

# SECTION 2625 — MILESTONE SUMMARY

## 2872.

The most important evolution was:

Backend API project
        ↓
API automation framework
        ↓
React frontend
        ↓
Playwright framework
        ↓
Dynamic test data
        ↓
UI + API hybrid testing
        ↓
Full-stack Dockerization
        ↓
GitHub Actions
        ↓
API automation in CI
        ↓
UI automation in CI
        ↓
CI-only failure investigation
        ↓
test-data dependency removed
        ↓
complete green pipeline

---

# SECTION 2626 — FINAL INTERVIEW REVISION

## 2873.

If asked:

What did you build?

Answer:

I built an end-to-end commerce Quality Engineering
portfolio project consisting of a Spring Boot backend,
PostgreSQL database and React/TypeScript frontend.

For automation, I built a REST Assured/TestNG API
framework with API and database validation, and a
Playwright/TypeScript UI framework using Page Objects,
storage-state authentication and dynamic API-assisted
test data.

I Dockerized the complete application and integrated
the quality checks into GitHub Actions so backend,
frontend, Docker, API automation and Playwright
automation are continuously validated on clean runners.

If asked:

What was the most useful engineering lesson?

Answer:

Automation reliability depends heavily on controlled
state and reproducible environments.

CI exposed a hidden dependency in one of my Playwright
tests because it relied on a product that existed only
in my local database.

I fixed it by making the test create and clean up its
own dynamic data rather than masking the problem with
timeouts or retries.

---

# END OF PART 219

# FRONTEND + PLAYWRIGHT + DOCKER + GITHUB ACTIONS CI MILESTONE COMPLETE

