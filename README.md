# SDET Commerce Automation

## Author
**Animesh Pandey**

Senior SDET / Automation Lead portfolio project focused on modern Quality Engineering, automation architecture, API testing, UI automation, CI/CD, and cloud-ready testing practices.

An end-to-end **Quality Engineering and SDET portfolio project** built to demonstrate backend development, API automation, database validation, security testing, test reporting, and scalable automation framework design.

The project currently consists of a **Spring Boot REST API**, **PostgreSQL database**, and an independent **REST Assured + TestNG API automation framework**.

The long-term goal is to evolve this repository into a complete full-stack quality engineering platform including React, Playwright, Docker, CI/CD, performance testing, and AWS deployment.

---

## Current Project Status

### Completed

- Spring Boot REST API
- PostgreSQL database integration
- User registration and login
- JWT authentication
- Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`)
- Product management
- Shopping cart
- Order management
- Mock payment processing
- REST Assured API automation framework
- TestNG test execution
- API functional testing
- Negative and validation testing
- Security and authorization testing
- RBAC testing
- Database validation using JDBC
- JSON schema validation
- Environment-based test configuration
- Allure reporting
- API request/response evidence
- Sensitive-data redaction in reports
- Swagger / OpenAPI documentation
- Swagger JWT authorization

### Planned

- React + TypeScript frontend
- Playwright + TypeScript UI automation
- UI + API hybrid automation
- Full application Dockerization
- GitHub Actions CI/CD
- k6 performance testing
- AWS deployment
- Extended observability and reporting

---

## Architecture

```text
                         SDET Commerce Platform

                         ┌─────────────────────┐
                         │   React Frontend    │
                         │    (Planned)        │
                         └──────────┬──────────┘
                                    │
                                    │ HTTP / REST
                                    ▼
                         ┌─────────────────────┐
                         │ Spring Boot REST API│
                         │                     │
                         │ JWT Authentication  │
                         │ RBAC Authorization  │
                         │ Business Services   │
                         └──────────┬──────────┘
                                    │
                                    │ JPA / Hibernate
                                    ▼
                         ┌─────────────────────┐
                         │     PostgreSQL      │
                         └─────────────────────┘


              API Automation Architecture

┌───────────────────────┐
│ REST Assured + TestNG │
└───────────┬───────────┘
            │
            ├──────────────► Spring Boot REST API
            │
            ├──────────────► JSON Schema Validation
            │
            ├──────────────► PostgreSQL DB Validation
            │
            └──────────────► Allure Reporting
```

---

## Technology Stack

| Area | Technology |
|---|---|
| Backend | Java 17 |
| Framework | Spring Boot |
| REST API | Spring Web MVC |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Authentication | JWT |
| Authorization | Spring Security / RBAC |
| API Automation | REST Assured |
| Test Framework | TestNG |
| Database Testing | JDBC / SQL |
| Schema Validation | REST Assured JSON Schema Validator |
| Reporting | Allure |
| API Documentation | Swagger / OpenAPI |
| Build Tool | Maven |
| Database Container | Docker / Docker Compose |
| Version Control | Git / GitHub |

---

## Application Features

### User Management

The application supports:

- User registration
- User login
- JWT token generation
- Secure password handling
- User roles

Supported roles:

```text
ROLE_USER
ROLE_ADMIN
```

---

### Product Management

Supported operations include:

```text
GET    /api/products
GET    /api/products/{id}
POST   /api/products
PUT    /api/products/{id}
DELETE /api/products/{id}
```

Product functionality includes:

- Product creation
- Product retrieval
- Product search
- Product updates
- Product deletion
- Input validation
- Stock management

Product write operations are restricted to administrators.

---

### Shopping Cart

The cart module supports:

- Add product to cart
- Retrieve cart
- Update quantity
- Remove cart item
- Clear cart
- Stock validation
- Duplicate product handling

---

### Orders

The order workflow supports:

- Create order from cart
- Retrieve orders
- Retrieve individual order
- Cancel order
- Order-item snapshots
- Stock deduction during order creation
- Stock restoration after cancellation
- Empty-cart validation
- Ownership validation

---

### Mock Payment

The payment module provides a simulated payment workflow for automation purposes.

Supported functionality includes:

- Create payment for an order
- Retrieve payment by order
- Generate transaction ID
- Payment status management
- Duplicate-payment prevention
- Cancelled-order validation
- Order ownership validation

Current payment statuses:

```text
SUCCESS
FAILED
```

---

## Security

Security is implemented using **Spring Security + JWT**.

Authentication flow:

```text
User Login
    ↓
Credentials Validated
    ↓
JWT Generated
    ↓
JWT Sent in Authorization Header
    ↓
JwtAuthenticationFilter
    ↓
Authenticated Security Context
    ↓
Endpoint Authorization
```

Authorization header:

```text
Authorization: Bearer <JWT>
```

---

## Role-Based Access Control

The project distinguishes between standard users and administrators.

| Operation | ROLE_USER | ROLE_ADMIN |
|---|:---:|:---:|
| Login/Register | ✅ | ✅ |
| Read Products | ✅ | ✅ |
| Create Product | ❌ | ✅ |
| Update Product | ❌ | ✅ |
| Delete Product | ❌ | ✅ |
| Cart Operations | ✅ | ✅ |
| Order Operations | ✅ | ✅ |
| Payment Operations | ✅ | ✅ |

The API also differentiates correctly between:

```text
401 Unauthorized
→ Missing or invalid authentication

403 Forbidden
→ Authenticated user does not have sufficient permission
```

---

# API Automation Framework

The API automation framework is intentionally maintained as an **independent Maven module**.

```text
api-automation/
```

This separation allows the automation suite to target different deployed environments without being coupled to backend source code.

---

## Framework Design

```text
Tests
  ↓
API Clients
  ↓
RequestSpecFactory
  ↓
REST Assured
  ↓
Spring Boot APIs
```

Supporting components include:

```text
ApiConfig
AuthHelper
TestDataFactory
TestDataCleanup
DatabaseHelper
SanitizedAllureFilter
AllureEnvironmentListener
```

---

## Automated Test Coverage

The current API regression suite contains **48 automated tests** covering multiple layers of the application.

Coverage includes:

### Product

- CRUD testing
- Search testing
- Validation testing
- Security testing
- RBAC testing
- Database validation

### Cart

- CRUD testing
- Validation testing
- Security testing
- Database validation

### Orders

- Order creation
- Order retrieval
- Order cancellation
- Validation testing
- Security testing
- Database validation

### Payments

- Payment flow
- Duplicate-payment validation
- Security testing
- Order/payment validation
- Database validation

### Authentication

- Login validation
- JWT-based authentication

Current regression status:

```text
Tests Run : 48
Failures  : 0
Errors    : 0
```

---

## Database Validation

API automation does not validate only HTTP responses.

Selected scenarios also validate persisted data directly in PostgreSQL using JDBC.

Example flow:

```text
REST API Request
       ↓
HTTP Response Validation
       ↓
Database Query
       ↓
Database Record Validation
```

This provides API-to-database integration coverage.

---

## JSON Schema Validation

Response structures are validated using JSON schemas stored under:

```text
api-automation/src/test/resources/schemas/
```

Schemas currently cover:

```text
Product
Cart Item
Order
Payment
```

---

# Environment Management

The API framework supports environment-based execution.

Supported environment model:

```text
LOCAL
QA
STAGE
```

Environment selection is externalized rather than hardcoded inside test classes.

Example:

```text
TEST_ENV=local
BASE_URL=http://localhost:8080
```

Environment-specific values and credentials are loaded through environment variables.

Real `.env` files are excluded from Git.

Templates are provided using:

```text
backend/.env.example
api-automation/.env.example
```

---

# Allure Reporting

The automation framework integrates **Allure** for test reporting.

Reports provide:

- Test execution status
- Test suites
- Execution duration
- Failure information
- API request evidence
- API response evidence
- Environment metadata

Generate test results:

```bash
./run-tests-local.sh
```

Open the report:

```bash
allure serve target/allure-results
```

---

## Secure Test Reporting

Sensitive information is sanitized before being attached to Allure reports.

For example:

```text
Authorization: [REDACTED]
```

The custom reporting filter protects values such as:

- JWT authorization headers
- Passwords
- Authentication tokens
- Cookies
- API keys

The real credentials are still transmitted to the application but are not exposed in generated test reports.

---

# Swagger / OpenAPI

Interactive API documentation is available through Swagger UI.

After starting the backend:

```text
http://localhost:8080/swagger-ui/index.html
```

Raw OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

Swagger supports JWT Bearer authentication through the **Authorize** option.

Typical flow:

```text
Login
  ↓
Receive JWT
  ↓
Swagger Authorize
  ↓
Execute Protected APIs
```

---

# Running the Project Locally

## Prerequisites

Install:

- Java 17
- Maven
- Docker Desktop
- Docker Compose
- Allure CLI
- Git

---

## 1. Clone Repository

```bash
git clone <repository-url>
cd SDET-Commerce-Automation
```

---

## 2. Configure Backend Environment

Copy:

```bash
cp backend/.env.example backend/.env
```

Update the values in:

```text
backend/.env
```

---

## 3. Start PostgreSQL

From repository root:

```bash
docker compose up -d
```

Verify:

```bash
docker ps
```

---

## 4. Start Backend

```bash
cd backend
./run-local.sh
```

Backend runs at:

```text
http://localhost:8080
```

---

## 5. Configure API Automation

Open another terminal:

```bash
cd api-automation
cp .env.example .env
```

Configure test credentials and database credentials in:

```text
api-automation/.env
```

---

## 6. Run API Regression

```bash
./run-tests-local.sh
```

Expected successful execution:

```text
Tests run: 48
Failures: 0
Errors: 0
BUILD SUCCESS
```

---

## 7. View Allure Report

```bash
allure serve target/allure-results
```

---

# Project Structure

```text
SDET-Commerce-Automation/
│
├── backend/
│   ├── src/main/java/
│   │   └── com/sdetcommerce/backend/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── exception/
│   │       ├── repository/
│   │       ├── security/
│   │       └── service/
│   │
│   ├── src/main/resources/
│   ├── .env.example
│   ├── pom.xml
│   └── run-local.sh
│
├── api-automation/
│   ├── src/test/java/
│   │   └── com/sdetcommerce/api/
│   │       ├── client/
│   │       ├── config/
│   │       ├── database/
│   │       ├── filters/
│   │       ├── listeners/
│   │       ├── model/
│   │       ├── tests/
│   │       └── utils/
│   │
│   ├── src/test/resources/
│   │   └── schemas/
│   │
│   ├── .env.example
│   ├── pom.xml
│   └── run-tests-local.sh
│
├── database/
├── docs/
├── docker/
├── docker-compose.yml
├── .gitignore
└── README.md
```

---

# Test Strategy

The project follows a layered Quality Engineering approach.

```text
                    UI / E2E
                   Playwright
                       ▲
                      / \
                     /   \
                    /     \
             API / Integration
          REST Assured + TestNG
                     ▲
                    / \
                   /   \
                  /     \
            Service / Data Layer
        PostgreSQL / JDBC Validation

              Performance Layer
                     k6
```

The majority of automated functional coverage is intentionally maintained at the API and integration layers.

Playwright focuses on critical end-to-end user journeys, while API-assisted setup and cleanup are used to keep UI tests reliable and independent of static database state.

The current automated quality layers include:

- Backend unit/integration validation
- REST API automation
- Database validation
- Authentication and RBAC validation
- Playwright UI automation
- UI + API hybrid testing
- Docker build validation
- k6 performance testing
- GitHub Actions continuous quality gates

---

# Roadmap

## Completed Engineering Milestones

### Backend

Implemented using:

```text
Java 17
Spring Boot
PostgreSQL
JWT
RBAC
Swagger / OpenAPI
```

Core commerce capabilities include:

- User registration
- Login and JWT authentication
- User profile
- Product catalogue
- Product search
- Product details
- Cart management
- Checkout
- Order creation
- Order retrieval
- Order cancellation
- Mock payment
- Admin product CRUD
- Role-based authorization

### API Automation

Implemented a separate automation framework using:

```text
Java
REST Assured
TestNG
JDBC
Allure
```

The framework includes:

- Environment configuration
- Reusable request specifications
- Authentication helpers
- Dynamic test data
- API validation
- Database validation
- Security validation
- RBAC testing
- Sensitive-data sanitization
- Allure reporting

Current API regression:

```text
48 automated tests
```

### Frontend

Implemented the real application UI using:

```text
React
TypeScript
Vite
```

The frontend consumes the actual Spring Boot APIs rather than static mock data.

Implemented flows include:

- Login
- Product catalogue
- Product search
- Product details
- Shopping cart
- Checkout
- Orders
- Order details
- Mock payment
- Admin product management
- Role-based UI access

### UI Automation

Implemented a separate framework using:

```text
Playwright
TypeScript
```

The framework includes:

- Page Object Model
- Authentication storage state
- User and Admin fixtures
- Environment configuration
- Dynamic API-assisted test data
- API setup and cleanup
- UI + API hybrid testing
- Smoke and regression tagging
- HTML reports
- Screenshots
- Videos
- Playwright traces

Critical coverage includes:

- Authentication
- Product browsing
- Product search
- Product details
- Cart workflows
- Order creation
- Payment
- Admin RBAC
- Admin product management

### Docker

The complete application is Dockerized.

```text
PostgreSQL
    ↓
Spring Boot Backend
    ↓
React Frontend
```

Docker Compose provides a reproducible full-stack environment for local and CI execution.

### CI/CD

GitHub Actions currently executes six automated quality jobs:

```text
Backend Build & Test
        +
Frontend Lint & Build
        +
Docker Build Validation
        +
REST Assured API Automation
        +
Playwright UI Automation
        +
k6 Performance Smoke Test
```

The pipeline uses clean runner environments and disposable test data.

A CI-only Playwright failure exposed a hidden dependency on local product data.

The affected test was corrected to create and clean up its own dynamic product through the API.

This removed the static database dependency rather than masking the problem with retries.

### Performance Testing

Implemented using:

```text
k6
JavaScript
```

Performance coverage includes:

- Smoke testing
- Load testing
- Stress testing
- Functional checks
- HTTP failure-rate thresholds
- p95 response-time thresholds
- Virtual User load profiles
- CI performance quality gate
- JSON summary artifact generation

Local validation completed successfully for all three scenarios.

Smoke result:

```text
1 VU
5 iterations
22 / 22 checks passed
0% HTTP failures
p95 ≈ 78.98 ms
```

Load result:

```text
10 max VUs
331 iterations
663 HTTP requests
664 / 664 checks passed
0% HTTP failures
p95 ≈ 10.43 ms
```

Stress result:

```text
30 max VUs
1481 iterations
2963 HTTP requests
2964 / 2964 checks passed
0% HTTP failures
p95 ≈ 6.07 ms
```

These measurements represent the configured local test environment and workload; they are not claims about the application's absolute production capacity.

The lightweight k6 smoke scenario is integrated into GitHub Actions as a performance quality gate.

The CI execution automatically generates:

```text
smoke-summary.json
```

and publishes it as the GitHub Actions artifact:

```text
k6-performance-results
```

The artifact generation flow was independently verified by downloading and inspecting the generated JSON.

## Next Phase — AWS Cloud Deployment

The next major engineering phase is AWS deployment and cloud validation.

Planned services include:

```text
IAM
RDS
EC2
S3
CloudWatch
```

The objective is to evolve the project from:

```text
Local + Docker + CI
```

to:

```text
Cloud-Deployed Application
        ↓
Cloud Configuration
        ↓
Cloud Validation
        ↓
Observability
        ↓
Deployment Automation
```

The AWS phase will focus on understanding how the application is securely configured, deployed, tested and observed in a cloud environment.

---

# Engineering Goals

This project is designed to demonstrate more than test-script creation.

It focuses on:

- Test architecture
- Framework design
- API and integration testing
- Database validation
- Security testing
- RBAC validation
- Environment management
- Secure reporting
- CI/CD readiness
- Maintainable test data
- End-to-end quality engineering

---

## Project Evolution

```text
Spring Boot Backend
        ↓
PostgreSQL Database
        ↓
JWT Authentication + RBAC
        ↓
REST Assured + TestNG
        ↓
API + Database Validation
        ↓
Allure Reporting
        ↓
Swagger / OpenAPI
        ↓
React + TypeScript Frontend
        ↓
Playwright + TypeScript
        ↓
Dynamic API Test Data
        ↓
UI + API Hybrid Testing
        ↓
Dockerized Full Stack
        ↓
GitHub Actions CI
        ↓
REST Assured CI
        ↓
Playwright CI
        ↓
k6 Performance Testing
        ↓
Performance CI Quality Gate
        ↓
Six-Job Green CI Pipeline
        ↓
AWS Deployment
```

Current completed state:

```text
Backend
+
Frontend
+
Database
+
API Automation
+
UI Automation
+
Docker
+
CI
+
Performance Testing
```

Next:

```text
AWS Cloud Deployment + Validation
```

---

## Author

**Animesh Pandey**

Senior SDET / Automation Lead portfolio project focused on modern Quality Engineering, automation architecture, API testing, UI automation, CI/CD, and cloud-ready testing practices.


---

## Live Deployment

The SDET Commerce Automation platform has been deployed as a publicly accessible full-stack portfolio application.

### Live Application

| Component | URL |
|---|---|
| Frontend | https://sdet-commerce-automation.vercel.app |
| Backend API | https://sdet-commerce-automation.onrender.com |
| Swagger UI | https://sdet-commerce-automation.onrender.com/swagger-ui/index.html |

### Cloud Deployment Architecture

    GitHub
       |
       +--------------------+
       |                    |
       v                    v
    Vercel               Render
    React +              Spring Boot
    TypeScript           REST API
                             |
                             v
                         Supabase
                         PostgreSQL

### Deployment Stack

- Frontend Hosting: Vercel
- Backend Hosting: Render
- Database: Supabase PostgreSQL
- Backend: Java + Spring Boot
- Frontend: React + TypeScript + Vite
- Containerization: Docker
- CI: GitHub Actions
- API Documentation: Swagger / OpenAPI
- Authentication: JWT
- API Automation: REST Assured + TestNG
- UI Automation: Playwright
- Performance Testing: k6

### Environment-Based Configuration

Production configuration is supplied using environment variables instead of hardcoded credentials.

Backend variables:

    DB_URL
    DB_USERNAME
    DB_PASSWORD
    JWT_SECRET
    JWT_EXPIRATION_MS
    FRONTEND_URL
    PORT

Frontend variable:

    VITE_API_BASE_URL

Secrets and production database credentials are not committed to GitHub.

### Runtime Port Configuration

The Spring Boot application supports both local and hosted runtime ports:

    server.port=${PORT:8080}

Local development therefore continues to use port 8080 while the hosting platform can provide its own runtime port.

### Cloud-Aware CORS

The backend CORS configuration uses the FRONTEND_URL environment variable.

Local development defaults to:

    http://localhost:5173

The hosted environment uses:

    https://sdet-commerce-automation.vercel.app

This allows the deployed Vercel frontend to communicate with the Render backend.

### Cloud Database

The deployed backend uses Supabase PostgreSQL.

The application connects using environment-based database configuration:

    DB_URL
    DB_USERNAME
    DB_PASSWORD

The Supabase Session Pooler is used for hosted database connectivity.

Hibernate schema management creates and updates the application schema using:

    spring.jpa.hibernate.ddl-auto=update

Application tables include:

    users
    products
    cart_items
    orders
    order_items
    payments

Demo product records were seeded into the cloud database for live end-to-end validation.

### SPA Routing

Vercel SPA routing is configured through:

    frontend/vercel.json

This allows React Router routes such as:

    /login
    /products
    /cart
    /orders

to be opened directly without returning a hosting-platform 404.

### Live End-to-End Validation

The deployed environment was validated through the complete commerce workflow:

    Login
      ↓
    Products
      ↓
    Product Details
      ↓
    Add to Cart
      ↓
    Cart
      ↓
    Checkout
      ↓
    Order Creation
      ↓
    Payment
      ↓
    Orders
      ↓
    Order Details

The validated live integration chain is:

    Browser
       ↓
    Vercel
    React + TypeScript
       ↓
    HTTPS REST API
       ↓
    Render
    Spring Boot
       ↓
    Supabase
    PostgreSQL

### CI and Deployment

GitHub Actions provides the project CI quality gates:

- Backend Build & Test
- Frontend Lint & Build
- Docker Build Validation
- REST Assured API Automation
- Playwright UI Automation
- k6 Performance Smoke Test

Application hosting is provided through Vercel, Render, and Supabase.

### Free-Tier Deployment Note

This environment is intended for portfolio and demonstration purposes.

The deployment currently uses free-tier services. Free services may have limitations such as cold starts, inactivity spin-down, usage limits, or project pausing.

No paid AWS infrastructure is required for the current portfolio deployment.

