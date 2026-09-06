Haan bhai, ab **ek hi single block** de raha hoon. Isko ek baar mein copy karke apne notes file mein paste kar do.

```markdown
# SDET END-TO-END PROJECT — PERSONAL LEARNING NOTES

## 1. Project Objective

### Goal
Build an end-to-end SDET project to understand how a real application is developed, tested, containerized, integrated with CI/CD, and deployed to cloud.

### Technologies I Will Learn

- Java 17
- Spring Boot
- REST APIs
- PostgreSQL
- SQL
- REST Assured
- TestNG
- JavaScript
- TypeScript
- Playwright
- Docker
- Git
- GitHub
- GitHub Actions
- k6
- AWS
- Allure

### Overall Architecture

Playwright UI Tests
       ↓
Web Application
       ↓
Spring Boot REST API
       ↓
PostgreSQL Database

REST Assured ──────→ REST API
                         ↑
k6 Performance Tests ────┘

GitHub
   ↓
GitHub Actions
   ↓
Docker
   ↓
AWS

### Important Understanding

I am not learning Spring Boot to become a backend developer.

I am learning enough backend development to understand the system I am testing as an SDET.

---

## 2. Homebrew

### What is Homebrew?

Homebrew is a package manager for macOS.

It allows developers to install tools and software directly from Terminal.

Instead of downloading tools manually, I can use commands such as:

brew install maven

### Check Homebrew

brew --version

My version:

Homebrew 6.0.12

### Why Are We Using Homebrew?

We are using Homebrew to install developer tools such as:

- Java
- Maven
- Git
- Docker
- Other command-line utilities

### Interview Takeaway

Homebrew itself is not an important SDET interview topic.

It is simply a convenient package manager for macOS development environments.

---

## 3. Java 17

### What is Java?

Java is a programming language.

In this project, Java will be used for:

- Spring Boot backend
- REST Assured API automation
- TestNG
- JDBC database validation

### What is JDK?

JDK stands for:

Java Development Kit

The JDK contains the tools required to develop and run Java applications.

Conceptually:

JDK
 ├── Java Compiler
 ├── Java Runtime
 └── Development Tools

### Why Java 17?

We selected Java 17 because it is an LTS release and has strong compatibility with Spring Boot and modern Java testing frameworks.

LTS means:

Long-Term Support

### Install Java 17

brew install openjdk@17

### Find Installation Path

brew --prefix openjdk@17

My result:

/opt/homebrew/opt/openjdk@17

---

## 4. Java Runtime Detection Issue

After installing Java, I initially received:

The operation couldn’t be completed.
Unable to locate a Java Runtime.

### Why Did This Happen?

Java was installed by Homebrew, but macOS was not detecting the Java installation through its standard Java Virtual Machines location.

This means:

Java was installed
      ≠
macOS automatically knew where Java was

### Fix

I registered the Homebrew Java installation with macOS using:

sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

### What Does This Command Do?

ln creates a link.

The command creates a symbolic link from the Homebrew Java installation to the standard Java Virtual Machines directory used by macOS.

### Verify Java Installations

/usr/libexec/java_home -V

My output showed:

17.0.20.1

with a Java 17 installation path.

### Learning

Installing software and configuring the environment are two different things.

This becomes important when working with:

- Local environments
- Jenkins
- CI/CD
- Docker
- Cloud servers
- Build agents

---

## 5. JAVA_HOME

### What is JAVA_HOME?

JAVA_HOME is an environment variable that points to the Java Development Kit that should be used.

Example:

JAVA_HOME
     ↓
Java 17 installation directory

Many tools depend on JAVA_HOME.

Examples:

- Maven
- Gradle
- Jenkins
- Spring Boot
- Automation frameworks

### Set JAVA_HOME Temporarily

export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"

### What Does export Mean?

export creates or updates an environment variable for the current shell session.

### Verify Java

java -version

Expected:

openjdk version "17..."

---

## 6. PATH Environment Variable

### What is PATH?

PATH tells the operating system where to search for executable commands.

For example, when I type:

java

the shell searches directories listed in the PATH variable.

### Why Add JAVA_HOME to PATH?

We used:

export PATH="$JAVA_HOME/bin:$PATH"

This places the Java 17 executable directory before other possible Java versions.

This helps ensure that:

java

uses Java 17.

### Important Learning

A system can have multiple Java versions installed.

The version that gets executed depends on environment configuration.

---

## 7. .zshrc

### What is .zshrc?

.zshrc is a configuration file used by the Zsh shell.

macOS uses Zsh as its default shell.

The file is usually located at:

~/.zshrc

### Why Did We Modify .zshrc?

When we use:

export JAVA_HOME=...

the setting only applies to the current terminal session.

If Terminal is closed, the setting may be lost.

To make Java 17 the default permanently, we added:

echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.zshrc

### Reload Configuration

source ~/.zshrc

### What Does source Do?

source reloads the shell configuration file without closing Terminal.

### Interview Takeaway

Environment variables are extremely important in CI/CD.

Common examples:

JAVA_HOME
BASE_URL
DB_URL
USERNAME
PASSWORD
ENV
TOKEN
API_KEY

---

## 8. Maven

### What is Maven?

Maven is a Java build and dependency management tool.

It helps:

- Download Java libraries
- Compile code
- Run tests
- Package applications
- Manage project dependencies
- Execute build lifecycle commands

### Example Libraries Maven Can Manage

- Spring Boot
- REST Assured
- TestNG
- PostgreSQL JDBC Driver
- Allure
- Jackson

### Maven Configuration File

Maven projects mainly use:

pom.xml

pom.xml stands for:

Project Object Model

### Install Maven

brew install maven

### Verify Maven

mvn -version

My Maven version:

Apache Maven 3.9.16

---

## 9. Maven Using Wrong Java Version

After installing Maven, I found that Maven was using:

Java version: 26.0.2.1

instead of Java 17.

### Why Is This Important?

Our project is standardized on Java 17.

Using different Java versions across tools can create compatibility problems.

Example:

Terminal Java = 17
Maven Java    = 26
Jenkins Java  = 21

This can cause unexpected build failures.

### Fix

I set Java 17 explicitly:

export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"

Then verified:

mvn -version

Final result:

Java version: 17.0.20.1

### Important Learning

Installed Java version is not necessarily the Java version being used by a tool.

Useful debugging commands:

java -version
mvn -version
echo $JAVA_HOME

These are useful when debugging CI/CD and build environment issues.

---

## 10. Git

### What is Git?

Git is a distributed version-control system.

It tracks changes made to source code.

Git allows developers to:

- Track history
- Create branches
- Commit changes
- Revert changes
- Collaborate with teams
- Push code to GitHub

### Basic Git Workflow

Modify Code
    ↓
git add
    ↓
git commit
    ↓
git push
    ↓
GitHub

### Check Git Installation

git --version

My Git version:

git version 2.50.1

### Common Commands

git status
git add .
git commit -m "message"
git branch
git checkout
git push
git pull

---

## 11. Node.js

### What is Node.js?

Node.js is a JavaScript runtime.

It allows JavaScript to run outside the browser.

We need Node.js mainly for:

- Playwright
- TypeScript
- JavaScript automation
- npm packages
- Tooling

### Check Node.js

node -v

My version:

v24.15.0

---

## 12. npm

### What is npm?

npm stands for:

Node Package Manager

It manages JavaScript and TypeScript project dependencies.

Examples:

- Playwright
- TypeScript
- dotenv
- reporting libraries
- utility libraries

### Check npm

npm -v

My version:

11.12.1

### Useful Comparison

Java                 JavaScript / TypeScript

Maven       <---->   npm
pom.xml     <---->   package.json

This is an important mental model.

---

## 13. Docker

### What is Docker?

Docker is a platform used to build and run applications inside containers.

A container packages:

- Application
- Runtime
- Dependencies
- Configuration

This makes the application more portable and reproducible.

### Why Does an SDET Need Docker?

SDETs often work with:

- Containerized applications
- CI/CD environments
- Test environments
- Database containers
- Microservices
- Cloud environments

Docker helps create consistent testing environments.

### Our Project Usage

Eventually our project will use Docker for:

Spring Boot Application
PostgreSQL Database
Automation Environment

---

## 14. Install Docker Desktop

Install Docker Desktop:

brew install --cask docker

Open Docker:

open -a Docker

Docker Desktop must be running before executing Docker commands.

---

## 15. Docker Hello World Test

We tested Docker using:

docker run hello-world

The output showed:

Hello from Docker!

### What Actually Happened?

When I ran:

docker run hello-world

the following flow occurred:

Docker CLI
    ↓
Docker Daemon
    ↓
Check local image
    ↓
Image not found locally
    ↓
Pull hello-world image from Docker Hub
    ↓
Create container
    ↓
Run container
    ↓
Display output

### What Did This Test Confirm?

It confirmed that:

- Docker CLI is installed
- Docker daemon is running
- Docker can communicate with Docker Hub
- Docker can pull images
- Docker can create containers
- Docker can run containers

### Docker Terms

#### Image

An image is a packaged template.

Examples:

PostgreSQL Image
Spring Boot Image
Ubuntu Image

#### Container

A container is a running instance of an image.

Conceptually:

Image
  ↓
Container

---

## 16. Docker CLI and Docker Daemon

### Docker CLI

Docker CLI is the command-line interface.

Example:

docker run hello-world

### Docker Daemon

The Docker daemon is the background service that actually manages:

- Images
- Containers
- Networks
- Volumes

Conceptually:

User
 ↓
Docker CLI
 ↓
Docker Daemon
 ↓
Container

---

## 17. Project Folder Creation

We created the main project directory:

mkdir SDET-Commerce-Automation

Moved inside the directory:

cd SDET-Commerce-Automation

---

## 18. Git Repository Initialization

Inside the project folder, we initialized Git:

git init

### What Does git init Do?

It turns a normal directory into a Git repository.

Internally Git creates:

.git

folder.

This folder stores:

- Commit history
- Branch information
- Repository metadata
- Configuration

After git init, Git can track changes inside the project.

---

## 19. Initial Project Folder Structure

We created:

mkdir backend api-automation ui-automation database docs

Initial structure:

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
└── docs/

---

## 20. Why Did We Create Separate Folders?

### backend

Contains the application backend.

Planned technologies:

Java
Spring Boot
REST API
Business Logic
Database Integration

### api-automation

Contains the independent API automation framework.

Planned technologies:

Java
REST Assured
TestNG
Maven
Allure

### ui-automation

Contains UI automation.

Planned technologies:

Playwright
TypeScript
Node.js
npm

### database

Contains database-related files.

Examples:

schema.sql
seed-data.sql
queries

### docs

Contains documentation.

Examples:

Architecture
Test Strategy
Framework Design
Learning Notes
Diagrams

---

## 21. Why Keep Application and Automation Separate?

We intentionally separated:

backend

from:

api-automation

because automation should be independently executable.

Example:

API Automation
      ↓
DEV Environment

API Automation
      ↓
QA Environment

API Automation
      ↓
STAGE Environment

The automation framework should not depend on rebuilding the backend code every time.

### Senior SDET Interview Point

A good explanation is:

"I intentionally decoupled the automation framework from the application code so that the same automation suite can run independently against multiple environments."

---

## 22. Planned Final Project Structure

SDET-Commerce-Automation/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   └── pom.xml
│
├── api-automation/
│   ├── src/
│   │   └── test/
│   │       ├── java/
│   │       │   ├── tests/
│   │       │   ├── clients/
│   │       │   ├── models/
│   │       │   ├── utils/
│   │       │   └── config/
│   │       └── resources/
│   └── pom.xml
│
├── ui-automation/
│   ├── tests/
│   ├── pages/
│   ├── fixtures/
│   ├── utils/
│   ├── test-data/
│   ├── playwright.config.ts
│   └── package.json
│
├── database/
│   ├── schema.sql
│   └── seed-data.sql
│
├── docs/
│   ├── architecture/
│   ├── test-strategy/
│   └── learning-notes/
│
├── .github/
│   └── workflows/
│
├── docker-compose.yml
├── .gitignore
└── README.md

---

## 23. Final Technology Stack

Backend Language: Java 17
Backend Framework: Spring Boot
API Type: REST
Database: PostgreSQL
ORM: Spring Data JPA / Hibernate
API Automation: REST Assured
API Test Runner: TestNG
UI Automation: Playwright
UI Language: TypeScript
JavaScript Runtime: Node.js
Package Manager: npm
Database Validation: SQL / JDBC
Performance Testing: k6
Performance Language: JavaScript
Containerization: Docker
CI/CD: GitHub Actions
Cloud: AWS
Reporting: Allure
Version Control: Git / GitHub

---

## 24. Planned Application Features

### User

- Register
- Login
- Profile

### Products

- Create Product
- Get Product
- Search Product
- Update Product
- Delete Product

### Cart

- Add Product
- Update Quantity
- Remove Product

### Orders

- Create Order
- Get Order
- Cancel Order

### Payment

- Mock Payment

### Admin

- Add Product
- Update Product
- Delete Product

---

## 25. Planned End-to-End Flow

Register User
    ↓
Login
    ↓
Search Product
    ↓
View Product
    ↓
Add Product to Cart
    ↓
Checkout
    ↓
Create Order
    ↓
Mock Payment
    ↓
Validate API Response
    ↓
Validate Database
    ↓
Validate UI

---

## 26. Testing Layers

The project will use multiple testing layers.

          UI Tests
             ↓
          API Tests
             ↓
     Integration Tests
             ↓
        Unit Tests

The principle is:

More API / Integration Tests
Less UI Tests

because UI tests are generally slower and more fragile.

---

## 27. API Automation Plan

REST Assured tests will not be written as large duplicated blocks.

Instead, the framework will follow a reusable structure:

Test
 ↓
Client / Service Layer
 ↓
Request Specification
 ↓
API
 ↓
Response Model

Example concept:

ProductClient productClient = new ProductClient();

ProductResponse product =
        productClient.getProduct(101);

Assert.assertEquals(product.getName(), "MacBook");

This improves:

- Maintainability
- Reusability
- Readability
- Scalability

---

## 28. UI Automation Plan

Playwright automation will use:

- Page Objects
- Fixtures
- Utilities
- Test Data
- Environment configuration
- Reusable API helpers

Example concept:

test('user can place an order', async ({ page }) => {

    await loginPage.login(user);

    await productPage.searchProduct('Laptop');

    await productPage.addToCart();

    await cartPage.checkout();

    await expect(orderPage.successMessage).toBeVisible();
});

---

## 29. Database Validation Plan

After API execution, we will validate backend data using SQL.

Example API:

POST /orders

Response:

{
  "orderId": 1001,
  "status": "CREATED"
}

Then database validation:

SELECT status
FROM orders
WHERE order_id = 1001;

Expected:

API Status = CREATED
DB Status  = CREATED

If both match:

PASS

---

## 30. Docker Plan

Eventually we want a command like:

docker compose up

to start:

Spring Boot Application
PostgreSQL Database

This creates a reproducible local environment.

Instead of manually installing and configuring PostgreSQL every time, Docker will manage the database container.

---

## 31. CI/CD Plan

Our planned GitHub Actions flow:

Developer Push / Pull Request
        ↓
GitHub Actions
        ↓
Build Spring Boot Application
        ↓
Start PostgreSQL
        ↓
Start Application
        ↓
Run API Automation
        ↓
Run UI Automation
        ↓
Run Database Validation
        ↓
Generate Reports
        ↓
Pipeline PASS / FAIL

### Why CI/CD Matters for an SDET

Automation becomes more valuable when tests run automatically.

Instead of a tester manually executing tests:

Code Change
   ↓
Automated Pipeline
   ↓
Automated Tests
   ↓
Immediate Feedback

---

## 32. Performance Testing Plan

We will use:

k6

with JavaScript.

Example scenarios:

50 users → Get Products
100 users → Search Products
50 users → Login
20 users → Checkout

Important metrics:

- Response Time
- Throughput
- Error Rate
- P95
- P99

Example:

import http from 'k6/http';
import { check } from 'k6';

export default function () {

    const response = http.get(
        'http://localhost:8080/api/products'
    );

    check(response, {
        'status is 200': (r) => r.status === 200
    });
}

---

## 33. AWS Plan

Eventually the project will be deployed to AWS.

Planned AWS services:

### EC2

Used to run the application/server.

### RDS

Used for managed PostgreSQL database.

### S3

Used for storing reports or build artifacts.

### IAM

Used for access control and permissions.

### CloudWatch

Used for:

- Logs
- Monitoring
- Metrics

### Final Cloud Flow

Playwright / REST Assured
            ↓
        AWS Application
            ↓
       Spring Boot API
            ↓
       AWS RDS PostgreSQL

---

## 34. Why Learn AWS as an SDET?

An SDET does not need to become a cloud architect.

However, modern applications often run in the cloud.

An SDET should understand:

- Where the application is deployed
- How environments work
- How logs are accessed
- How databases are hosted
- How CI/CD connects to environments
- How test automation runs against cloud systems

---

## 35. Important Learning So Far

The environment setup flow was:

Homebrew
    ↓
Java 17
    ↓
JAVA_HOME
    ↓
PATH
    ↓
Maven
    ↓
Git
    ↓
Node.js
    ↓
npm
    ↓
Docker
    ↓
Project Folder
    ↓
Git Repository
    ↓
Project Structure

---

## 36. Problems Faced and Fixes

### Problem 1

Java was installed but macOS could not detect it.

Error:

Unable to locate a Java Runtime

Fix:

sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

### Problem 2

Maven was using Java 26 instead of Java 17.

Detected using:

mvn -version

Fix:

export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"

Then persisted configuration in:

~/.zshrc

### Problem 3

Docker Compose command was not initially detected as expected.

However, Docker itself was verified successfully using:

docker run hello-world

The hello-world test confirmed the Docker engine was working.

Docker Compose setup will be validated again when we start containerizing PostgreSQL and Spring Boot.

---

## 37. Useful Verification Commands

### Java

java -version

### Java Home

echo $JAVA_HOME

### Installed Java Versions

/usr/libexec/java_home -V

### Maven

mvn -version

### Git

git --version

### Node

node -v

### npm

npm -v

### Docker

docker --version

### Docker Test

docker run hello-world

### Homebrew

brew --version

---

## 38. Key Interview Lessons From Setup

### Environment Configuration

I learned that installing a tool is not enough.

The environment must also know which version to use.

### JAVA_HOME

JAVA_HOME points Java-based tools to the selected JDK.

### PATH

PATH decides which executable runs when a command is entered.

### Maven

Maven manages Java dependencies and build lifecycle.

### Git

Git tracks source-code changes and supports collaboration.

### Node.js

Node.js allows JavaScript to run outside the browser.

### npm

npm manages JavaScript and TypeScript dependencies.

### Docker

Docker creates reproducible application environments using containers.

### CI/CD

Automation provides maximum value when integrated with continuous pipelines.

---

## 39. SDET Mindset

A strong SDET should understand more than test scripts.

An SDET should understand:

Application
    ↓
API
    ↓
Database
    ↓
Automation
    ↓
Build
    ↓
CI/CD
    ↓
Containers
    ↓
Cloud

The goal is not only to verify software.

The goal is to understand how quality fits into the complete software engineering lifecycle.

---

## 40. Current Project Status

- [x] Homebrew installed
- [x] Java 17 installed
- [x] Java registered with macOS
- [x] JAVA_HOME configured
- [x] PATH configured
- [x] Maven installed
- [x] Maven configured with Java 17
- [x] Git verified
- [x] Node.js verified
- [x] npm verified
- [x] Docker installed
- [x] Docker hello-world verified
- [x] Project folder created
- [x] Git repository initialized
- [x] Base project structure created
- [ ] GitHub remote repository
- [ ] Spring Boot project
- [ ] REST API development
- [ ] PostgreSQL integration
- [ ] REST Assured framework
- [ ] Database automation
- [ ] Playwright framework
- [ ] End-to-End tests
- [ ] Docker Compose
- [ ] GitHub Actions
- [ ] Allure reporting
- [ ] k6 performance tests
- [ ] AWS deployment
- [ ] Cloud-based automation execution

---

## 41. Next Step

Next we will start the actual backend development.

The first major topic will be:

Spring Boot

We will learn:

- What Spring Boot is
- Why Spring Boot is used
- Spring vs Spring Boot
- What Maven does inside a Spring Boot project
- What pom.xml is
- What annotations are
- Controller
- Service
- Repository
- Entity
- DTO
- Dependency Injection
- REST APIs
- HTTP methods
- Request and response
- JSON
- PostgreSQL integration

Then we will create our first API:

POST /api/users/register

This will be the beginning of the actual SDET Commerce Platform.
```
Bilkul bhai. Ye **previous notes ke continuation mein paste karne ke liye single copy block** hai. Isko Section 42 onward add kar do.

```markdown
## 42. Starting the Actual Backend Development

After completing the development environment setup, the next phase of the project is:

SPRING BOOT BACKEND DEVELOPMENT

Until now, I was preparing the machine and project structure.

The actual application development starts from here.

Our backend will eventually handle:

User Registration
Login
Products
Cart
Orders
Payments
Admin Operations

The backend technology stack will be:

Java 17
   ↓
Spring Boot
   ↓
REST APIs
   ↓
Spring Data JPA / Hibernate
   ↓
PostgreSQL

---

## 43. What is Spring Boot?

Spring Boot is a Java framework used to build standalone, production-ready applications quickly.

It is built on top of the Spring Framework.

Instead of manually configuring many components required by a Java web application, Spring Boot provides sensible defaults and automatic configuration.

In simple words:

Traditional Java Application
        ↓
A lot of manual configuration
        ↓
Application

Spring Boot
        ↓
Automatic configuration
        ↓
Application

Spring Boot will allow us to create REST APIs such as:

POST /api/users/register

POST /api/users/login

GET /api/products

GET /api/products/{id}

POST /api/cart

POST /api/orders

DELETE /api/orders/{id}

---

## 44. Spring vs Spring Boot

Spring is the larger Java application framework.

Spring Boot is built on top of Spring and makes Spring applications easier to create and run.

Simple understanding:

Spring Framework
       ↓
Provides core capabilities
       ↓
Dependency Injection
Spring MVC
Database Integration
Security
etc.

Spring Boot
       ↓
Uses Spring
       +
Auto Configuration
Starter Dependencies
Embedded Web Server
Easy Application Startup

Therefore:

Spring Boot ≠ Completely different framework from Spring

Spring Boot = Easier and opinionated way of building Spring applications.

---

## 45. Why Are We Using Spring Boot in an SDET Project?

The primary goal is not to become a backend developer.

The goal is to understand how the application being tested actually works.

As an SDET, understanding backend development helps with:

API Testing
Database Testing
Integration Testing
Debugging
Log Analysis
Service Testing
Automation Framework Design
CI/CD
Microservices Testing
Performance Testing

Instead of testing somebody else's sample API, I will build my own application and then create automation against it.

This gives me understanding of both sides:

APPLICATION DEVELOPMENT
        +
TEST AUTOMATION

This is valuable for a Senior SDET role.

---

## 46. Spring Initializr

We used Spring Initializr to create the initial Spring Boot project.

Spring Initializr is an official project generator for Spring applications.

It generates the initial project structure and configuration based on selected technologies.

Instead of manually creating:

pom.xml
src/main/java
src/test/java
application.properties
Maven wrapper
Spring Boot starter configuration

Spring Initializr generates the initial structure automatically.

The website used was:

start.spring.io

---

## 47. Spring Initializr Configuration

The following configuration was selected:

Project:

Maven

Language:

Java

Spring Boot:

4.1.1

Group:

com.sdetcommerce

Artifact:

backend

Package Name:

com.sdetcommerce.backend

Packaging:

Jar

Configuration:

Properties

Java:

17

Dependencies:

Spring Web
Spring Data JPA
PostgreSQL Driver
Validation

---

## 48. Why Maven Was Selected

Spring Boot supports different build systems.

Common examples:

Maven
Gradle

For this project, Maven was selected because:

I already configured Maven locally.

REST Assured will also use Maven.

Maven is widely used in Java automation projects.

I want to understand pom.xml deeply.

It provides a straightforward dependency management model.

Our backend will therefore contain:

pom.xml

The API automation framework will later have its own:

pom.xml

This means:

backend/pom.xml
        ↓
Backend dependencies

api-automation/pom.xml
        ↓
Automation dependencies

The two projects remain independently manageable.

---

## 49. Why Java Was Selected

Java is being used for the backend because it is also one of the main languages in our SDET stack.

Java will eventually be used for:

Spring Boot Backend
REST Assured
TestNG
JDBC
Database Validation
Utility Classes
API Models

This gives us one strong Java-based engineering stack.

UI automation will separately use:

TypeScript + Playwright

Performance testing will use:

JavaScript + k6

Therefore, the overall language exposure becomes:

Java
JavaScript
TypeScript
SQL

---

## 50. Spring Boot Version Selection

Spring Initializr displayed Spring Boot 4.x versions.

The selected stable version was:

Spring Boot 4.1.1

The Initializr screen showed:

4.2.0 SNAPSHOT
4.2.0 M1
4.1.2 SNAPSHOT
4.1.1
4.0.9 SNAPSHOT
4.0.8

We selected:

4.1.1

because it was shown as a stable release rather than:

SNAPSHOT

or:

M1

---

## 51. What is a SNAPSHOT Version?

A SNAPSHOT version represents a development version that can still change.

Example:

4.2.0-SNAPSHOT

It is generally not the preferred choice for a stable learning or portfolio project.

Simple understanding:

Stable Release
      ↓
Released and intended for normal use

SNAPSHOT
      ↓
Development build
      ↓
May change

For this project, stable releases are preferred.

---

## 52. What is an M1 Version?

M1 means:

Milestone 1

A milestone release is an early pre-release version.

For example:

4.2.0 M1

It can be used to preview upcoming functionality but is not the preferred choice for our project.

For our project:

Stable Release > Milestone > Snapshot

when the objective is predictable project development.

---

## 53. Group in Spring Initializr

We selected:

com.sdetcommerce

as the Group.

The Group generally identifies the organization, company, or project namespace.

Examples from real applications could look like:

com.amazon

com.google

com.companyname

For our project:

com.sdetcommerce

The Group helps create a unique Java namespace.

---

## 54. Artifact in Spring Initializr

We selected:

backend

as the Artifact.

Artifact is essentially the project/module name produced by the Maven build.

Our Spring Boot backend module is therefore called:

backend

This matches our repository structure:

SDET-Commerce-Automation/
│
├── backend/
├── api-automation/
├── ui-automation/
├── database/
└── docs/

---

## 55. Package Name

We selected:

com.sdetcommerce.backend

This becomes the base Java package for our application.

Our Java classes will eventually look like:

com.sdetcommerce.backend.controller

com.sdetcommerce.backend.service

com.sdetcommerce.backend.repository

com.sdetcommerce.backend.entity

com.sdetcommerce.backend.dto

com.sdetcommerce.backend.exception

Conceptually:

com.sdetcommerce.backend
          │
          ├── controller
          ├── service
          ├── repository
          ├── entity
          ├── dto
          └── exception

This creates a clean layered structure.

---

## 56. Why Package Structure Matters

Java packages help organize code logically.

Instead of keeping every Java class in one location:

UserController
UserService
UserRepository
ProductController
OrderController
ProductService
etc.

We organize them by responsibility.

Example:

controller/
    UserController.java

service/
    UserService.java

repository/
    UserRepository.java

entity/
    User.java

dto/
    UserRequest.java
    UserResponse.java

This makes the project easier to:

Understand
Maintain
Scale
Debug
Test

---

## 57. Packaging: JAR

We selected:

Jar

instead of:

War

JAR means:

Java Archive

Our Spring Boot application will eventually be packaged into a JAR file.

Conceptually:

Java Source Code
       ↓
Maven Build
       ↓
JAR File
       ↓
Run Application

A typical command later could look like:

java -jar backend.jar

Spring Boot applications commonly use executable JAR packaging.

---

## 58. JAR vs WAR Basic Understanding

JAR:

Java Archive

WAR:

Web Application Archive

Traditional Java web applications were commonly packaged as WAR files and deployed to an external application server.

Spring Boot commonly uses executable JAR files because it can include an embedded web server.

Conceptually:

Traditional Web Application:

WAR
 ↓
External Tomcat
 ↓
Application

Spring Boot:

Executable JAR
     ↓
Embedded Server
     ↓
Application

This makes Spring Boot applications easier to run independently.

---

## 59. Configuration: Properties

We selected:

Properties

Spring Boot supports configuration files such as:

application.properties

and:

application.yml

For this project, we selected:

application.properties

Initially it will be located approximately at:

src/main/resources/application.properties

Later it can contain configuration such as:

Database URL
Database username
Database password
Server port
Logging settings
Application settings

Example concept:

server.port=8080

spring.datasource.url=...

spring.datasource.username=...

spring.datasource.password=...

IMPORTANT:

Real passwords and secrets should not be committed directly into Git.

Later we will learn environment variables and secret management.

---

## 60. Why Java 17 Was Selected

Our local environment was already configured with:

Java 17

JAVA_HOME also points to Java 17.

Maven was verified to use Java 17.

Therefore Spring Initializr was configured with:

Java 17

This keeps our development environment consistent.

Conceptually:

Local Java
    ↓
Java 17

Maven Java
    ↓
Java 17

Spring Boot Project
    ↓
Java 17

Consistency between environments helps reduce build problems.

---

## 61. Dependency 1 — Spring Web

We added:

Spring Web

Spring Web provides the functionality required to create web applications and REST APIs.

It allows us to create endpoints such as:

GET /api/products

POST /api/users/register

PUT /api/products/{id}

DELETE /api/products/{id}

Later we will use annotations such as:

@RestController

@RequestMapping

@GetMapping

@PostMapping

@PutMapping

@DeleteMapping

@RequestBody

@PathVariable

Spring Web also provides the web infrastructure required for handling HTTP requests and responses.

---

## 62. Embedded Web Server

Spring Boot can run with an embedded web server.

For servlet-based Spring Web applications, Apache Tomcat is commonly used as the default embedded server.

Conceptually:

Browser / Postman / REST Assured
              ↓
          HTTP Request
              ↓
        Embedded Tomcat
              ↓
         Spring Boot
              ↓
          Controller
              ↓
           Response

This means we do not need to manually install a separate Tomcat server for normal local development.

---

## 63. Dependency 2 — Spring Data JPA

We added:

Spring Data JPA

JPA stands for:

Java Persistence API

It provides a standard approach for working with relational database data using Java objects.

Instead of manually writing SQL for every operation, we can work with Java entities and repositories.

Example concept:

Java Object:

User
- id
- name
- email

Database Table:

users

id | name | email

Spring Data JPA helps connect these two worlds.

---

## 64. What is ORM?

ORM stands for:

Object Relational Mapping

It maps Java objects to relational database tables.

Example:

Java:

User user = new User();

Database:

users table

Conceptually:

Java Object
     ↓
ORM
     ↓
Database Row

Hibernate is commonly used as the JPA implementation in Spring applications.

Later we will learn the relationship between:

Spring Data JPA
JPA
Hibernate
PostgreSQL

---

## 65. Dependency 3 — PostgreSQL Driver

We added:

PostgreSQL Driver

Our backend is written in Java.

Our database will be:

PostgreSQL

Java needs a database driver to communicate with PostgreSQL.

Conceptually:

Spring Boot
     ↓
JDBC / PostgreSQL Driver
     ↓
PostgreSQL
     ↓
Tables

The driver understands how Java communicates with PostgreSQL.

Later we will configure the connection inside:

application.properties

---

## 66. What is JDBC?

JDBC stands for:

Java Database Connectivity

It is a Java API used to communicate with relational databases.

Conceptually:

Java Application
      ↓
JDBC
      ↓
Database Driver
      ↓
PostgreSQL

We will encounter JDBC in two places:

1. Backend database communication

2. SDET database validation

Later our automation can directly query the database using JDBC to validate data.

Example:

API creates an order.

POST /api/orders

API returns:

orderId = 1001

Automation then queries:

SELECT * FROM orders WHERE id = 1001;

This allows us to validate the complete backend flow.

---

## 67. Dependency 4 — Validation

We added:

Validation

Validation helps verify incoming API data before processing it.

For example, a registration request may contain:

name
email
password

We may want rules such as:

Name cannot be blank.

Email must be valid.

Password must meet minimum requirements.

Example concept:

POST /api/users/register

Request:

{
  "name": "",
  "email": "invalid-email"
}

Instead of storing invalid data, the API should reject the request.

Later we can use annotations such as:

@NotBlank

@NotNull

@Email

@Size

@Min

@Max

Validation is especially important for API negative testing.

---

## 68. Why Validation is Important for an SDET

Validation creates many important test scenarios.

For example:

Valid Email
Invalid Email
Blank Email
Null Email
Password Too Short
Missing Required Field
Invalid Product Price
Negative Quantity

This means application validation directly influences our API automation strategy.

We will eventually create both:

Positive Tests

and

Negative Tests

---

## 69. Current Backend Dependency Flow

Our selected backend technologies currently look like:

Spring Web
    ↓
REST API

Spring Data JPA
    ↓
Database Persistence

PostgreSQL Driver
    ↓
PostgreSQL Connection

Validation
    ↓
Request Validation

Combined:

Client
  ↓
Spring Web
  ↓
Controller
  ↓
Service
  ↓
Spring Data JPA
  ↓
PostgreSQL Driver
  ↓
PostgreSQL

Validation checks incoming requests before invalid data reaches deeper application layers.

---

## 70. Planned Layered Backend Architecture

Our backend will eventually follow approximately:

Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
Database

Where:

Controller
= Handles HTTP requests and responses

Service
= Contains business logic

Repository
= Handles database access

Entity
= Represents database data

DTO
= Represents API request/response data

Database
= PostgreSQL

Example:

POST /api/users/register
          ↓
UserController
          ↓
UserService
          ↓
UserRepository
          ↓
PostgreSQL

This layered architecture will also make testing easier.

---

## 71. Controller

A Controller receives HTTP requests.

Example:

POST /api/users/register

The controller receives the request and passes the required data to the service layer.

Conceptually:

HTTP Request
     ↓
Controller
     ↓
Service

We will create controllers later.

---

## 72. Service

The Service layer contains business logic.

Example registration logic:

Receive registration request
        ↓
Check whether email already exists
        ↓
Validate business rules
        ↓
Create user
        ↓
Save user

The controller should not contain all business logic.

Keeping business logic in the service layer improves maintainability.

---

## 73. Repository

Repository handles database interaction.

Example:

Find user by email

Save user

Find product by ID

Delete product

Conceptually:

Service
   ↓
Repository
   ↓
Database

Spring Data JPA will help us create repositories with significantly less boilerplate code.

---

## 74. Entity

An Entity represents data stored in the database.

Example User Entity:

User

id
name
email
password

could map to:

users table

id | name | email | password

Entities will later use annotations such as:

@Entity

@Table

@Id

@GeneratedValue

@Column

---

## 75. DTO

DTO stands for:

Data Transfer Object

DTOs are commonly used to transfer data between the API and application layers.

Example registration request:

{
  "name": "John",
  "email": "john@example.com",
  "password": "Password123"
}

Instead of exposing the database entity directly, we can create:

UserRegistrationRequest

Similarly, the response can use:

UserResponse

This allows API contracts and database entities to remain separated.

---

## 76. Why DTOs Matter

Suppose our database entity contains:

id
name
email
password
createdAt
internalStatus

We may not want the API response to expose:

password
internalStatus

Therefore:

Entity
    ↓
Internal database representation

DTO
    ↓
External API representation

This is cleaner and safer.

It also gives us clear API contracts to automate against.

---

## 77. Current Technology Architecture

At this point, the planned architecture is:

Playwright + TypeScript
        ↓
       UI
        ↓
Spring Boot REST API
        ↓
Spring Data JPA
        ↓
PostgreSQL

REST Assured + Java
        ↓
Spring Boot REST API

JDBC / SQL Validation
        ↓
PostgreSQL

k6 + JavaScript
        ↓
Spring Boot REST API

Later:

GitHub
   ↓
GitHub Actions
   ↓
Docker
   ↓
AWS

---

## 78. What We Have Completed in This Phase

Completed:

[x] Understood why Spring Boot is being used

[x] Opened Spring Initializr

[x] Selected Maven

[x] Selected Java

[x] Selected Java 17

[x] Selected Spring Boot stable version

[x] Configured Group

[x] Configured Artifact

[x] Configured Package Name

[x] Selected JAR packaging

[x] Selected Properties configuration

[x] Added Spring Web

[x] Added Spring Data JPA

[x] Added PostgreSQL Driver

[x] Added Validation

Not completed yet:

[ ] Generate Spring Boot project

[ ] Extract project

[ ] Move generated files into backend folder

[ ] Understand generated project structure

[ ] Understand pom.xml

[ ] Run Spring Boot application

[ ] Verify embedded server

[ ] Create first REST API

[ ] Connect PostgreSQL

---

## 79. Important Interview Takeaways So Far

1. Spring Boot simplifies building Spring applications through auto-configuration and starter dependencies.

2. Spring Web is used to build HTTP endpoints and REST APIs.

3. Spring Data JPA simplifies persistence and repository implementation.

4. JPA defines a persistence standard while Hibernate can act as its implementation.

5. PostgreSQL Driver allows Java applications to communicate with PostgreSQL.

6. JDBC is the Java API for database connectivity.

7. Validation protects APIs from invalid request data.

8. Controllers handle HTTP interactions.

9. Services contain business logic.

10. Repositories handle persistence/database access.

11. Entities represent persistent database data.

12. DTOs represent data transferred through application/API boundaries.

13. Executable JAR packaging allows Spring Boot applications to run with an embedded web server.

14. Application code and automation code are being kept as separate modules.

15. Understanding backend architecture helps an SDET design better API, integration, database, performance, and end-to-end tests.

---

## 80. Next Step

The Spring Initializr configuration is ready.

Next:

Generate the Spring Boot project.

Then:

Download backend.zip
        ↓
Inspect generated structure
        ↓
Move it into existing backend/
        ↓
Understand pom.xml
        ↓
Understand src/main/java
        ↓
Understand src/main/resources
        ↓
Understand application.properties
        ↓
Understand @SpringBootApplication
        ↓
Run the application
        ↓
Understand embedded Tomcat
        ↓
Verify localhost:8080
        ↓
Create first REST endpoint

The important rule for this project is:

DO NOT JUST COPY CODE.

For every major implementation I should understand:

WHAT it is

WHY it is needed

HOW it works

HOW to test it

WHAT can fail

HOW to debug it

HOW to explain it in an SDET interview
```


