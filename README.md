# SDET Commerce Automation

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

The project follows a layered quality engineering approach.

```text
                    UI Tests
                  (Playwright)
                     Planned
                       ▲
                      / \
                     /   \
                    /     \
             API / Integration
              REST Assured
              Current Focus
                  ▲
                 / \
                /   \
               /     \
          Service / Data Layer
           DB Validation
```

The majority of automated coverage is intentionally maintained at the API and integration layers, while future UI automation will focus on critical end-to-end user journeys.

---

# Roadmap

## Next Phase

### Frontend

Build the application UI using:

```text
React
TypeScript
Vite
```

Planned screens include:

- Login
- Registration
- Product catalogue
- Product search
- Product details
- Shopping cart
- Checkout
- Orders
- Payment
- Admin product management

### UI Automation

Build a separate automation framework using:

```text
Playwright
TypeScript
```

Coverage will include:

- Authentication
- Product browsing
- Cart workflows
- Order creation
- Payment workflow
- Admin RBAC
- UI + API hybrid scenarios

### CI/CD

Integrate:

```text
GitHub Actions
```

Planned pipeline stages:

```text
Build
  ↓
API Tests
  ↓
UI Tests
  ↓
Reports
  ↓
Quality Gate
```

### Performance Testing

Add:

```text
k6
JavaScript
```

for load and performance validation of critical APIs.

### Cloud

Deploy the application using selected AWS services such as:

```text
EC2
RDS
S3
IAM
CloudWatch
```

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
Backend + Database
        ↓
API Automation
        ↓
Security / RBAC
        ↓
Allure Reporting
        ↓
Swagger / OpenAPI
        ↓
React Frontend
        ↓
Playwright UI Automation
        ↓
Dockerized Full Stack
        ↓
GitHub Actions
        ↓
Performance Testing
        ↓
AWS Deployment
```

---

## Author

**Animesh Pandey**

Senior SDET / Automation Lead portfolio project focused on modern Quality Engineering, automation architecture, API testing, UI automation, CI/CD, and cloud-ready testing practices.