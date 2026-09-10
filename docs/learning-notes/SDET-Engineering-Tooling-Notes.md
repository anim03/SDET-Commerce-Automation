# SDET ENGINEERING TOOLING NOTES

> Practical engineering tooling notes built around the SDET-Commerce-Automation project.

---

# PART 1 — MAVEN: ZERO TO ADVANCED FOR SDET

# SECTION 1 — WHY MAVEN MATTERS

## 1. What Problem Does Maven Solve?

A Java project normally needs to handle:

```text
Source Code
Dependencies
Compilation
Tests
Packaging
Plugins
Build Configuration
Reports
```

Managing all of these manually becomes difficult.

Maven provides a standard build and dependency-management system.

Our flow becomes:

```text
Java Source Code
      ↓
Maven
      ↓
Download Dependencies
      ↓
Compile
      ↓
Run Tests
      ↓
Package Application
      ↓
Build Artifact
```

---

# SECTION 2 — WHAT IS MAVEN?

## 2. Definition

**Apache Maven** is a build automation and dependency-management tool primarily used for Java projects.

Maven helps us:

```text
manage dependencies
compile Java
run tests
package applications
execute plugins
standardize project structure
support CI/CD builds
```

In our project Maven is used in both:

```text
backend/
api-automation/
```

But they are separate Maven projects with different responsibilities.

---

# SECTION 3 — BACKEND VS API AUTOMATION

## 3. Why Do Both Use Maven?

### Backend

```text
backend/
```

Maven handles:

```text
Spring Boot dependencies
Spring Security
JPA
PostgreSQL driver
JWT
Swagger/OpenAPI
compilation
backend tests
application packaging
```

### API Automation

```text
api-automation/
```

Maven handles:

```text
REST Assured
TestNG
JDBC
Allure
test compilation
test execution
reporting integration
```

Important:

```text
Same build tool
≠
Same application
```

The API automation project is intentionally independent from the backend.

That means later it can test:

```text
LOCAL
QA
STAGE
```

without becoming part of backend source code.

---

# SECTION 4 — pom.xml

## 4. What Is pom.xml?

The main Maven configuration file is:

```text
pom.xml
```

POM means:

```text
Project Object Model
```

It describes the Maven project.

Conceptually:

```text
pom.xml
│
├── Project Identity
├── Java Version
├── Dependencies
├── Plugins
├── Build Configuration
└── Other Maven Settings
```

---

# SECTION 5 — PROJECT COORDINATES

## 5. Maven Coordinates

A Maven project commonly has:

```xml
<groupId>...</groupId>
<artifactId>...</artifactId>
<version>...</version>
```

These identify an artifact.

Think:

```text
groupId
→ organization/package namespace

artifactId
→ project/library name

version
→ artifact version
```

Together:

```text
groupId : artifactId : version
```

form Maven coordinates.

---

# SECTION 6 — groupId

## 6. What Is groupId?

Example concept:

```text
com.sdetcommerce
```

`groupId` usually represents the organization or logical namespace.

It helps distinguish similarly named artifacts.

Example:

```text
com.company.payment
org.example.testing
```

---

# SECTION 7 — artifactId

## 7. What Is artifactId?

`artifactId` identifies the specific project/module.

For our backend:

```text
backend
```

Conceptually:

```text
groupId    = com.sdetcommerce
artifactId = backend
```

---

# SECTION 8 — VERSION

## 8. Project Version

A Maven project can have a version such as:

```text
1.0.0
```

or:

```text
1.0.0-SNAPSHOT
```

`SNAPSHOT` generally indicates:

```text
development version
```

rather than a fixed release.

---

# SECTION 9 — PACKAGING

## 9. Packaging Type

Common Maven packaging types include:

```text
jar
war
pom
```

Modern Spring Boot applications are commonly packaged as executable:

```text
JAR
```

A JAR means:

```text
Java ARchive
```

---

# SECTION 10 — STANDARD MAVEN DIRECTORY STRUCTURE

## 10. Convention Over Configuration

Maven expects a standard structure:

```text
project/
│
├── pom.xml
│
└── src/
    ├── main/
    │   ├── java/
    │   └── resources/
    │
    └── test/
        ├── java/
        └── resources/
```

Meaning:

```text
src/main/java
→ application source code

src/main/resources
→ application resources/config

src/test/java
→ test source code

src/test/resources
→ test resources
```

Maven understands these locations automatically.

---

# SECTION 11 — TARGET DIRECTORY

## 11. What Is target/?

When Maven builds a project, generated output normally goes into:

```text
target/
```

It can contain:

```text
compiled classes
test classes
test reports
generated files
JAR
Allure results
other build output
```

Because it is generated:

```text
target/
```

should normally not be committed to Git.

---

# SECTION 12 — MAVEN COMMAND STRUCTURE

## 12. Basic Command

Example:

```bash
mvn test
```

or using Maven Wrapper:

```bash
./mvnw test
```

General pattern:

```text
mvn <phase>
```

or:

```text
mvn <plugin>:<goal>
```

Example:

```bash
mvn dependency:tree
```

Here:

```text
dependency
→ plugin prefix

tree
→ goal
```

---

# SECTION 13 — MAVEN BUILD LIFECYCLE

## 13. What Is a Lifecycle?

Maven organizes build operations into lifecycles.

The most important lifecycle for us is:

```text
default lifecycle
```

Important phases include:

```text
validate
compile
test
package
verify
install
deploy
```

There is also a separate:

```text
clean lifecycle
```

---

# SECTION 14 — MAVEN PHASE ORDER

## 14. Mental Model

Think:

```text
validate
   ↓
compile
   ↓
test
   ↓
package
   ↓
verify
   ↓
install
   ↓
deploy
```

If you execute a later phase, Maven runs the required earlier phases in that lifecycle first.

Example:

```bash
mvn package
```

does not only package.

It also runs earlier phases such as:

```text
validate
compile
test
```

before reaching package.

---

# SECTION 15 — mvn clean

## 15. What Does clean Do?

```bash
mvn clean
```

removes Maven build output, normally:

```text
target/
```

Why?

Because an old build may contain stale generated artifacts.

Concept:

```text
Old target/
    ↓
mvn clean
    ↓
Fresh build state
```

---

# SECTION 16 — mvn compile

## 16. Compile

```bash
mvn compile
```

compiles application source code.

Concept:

```text
src/main/java
      ↓
Java compiler
      ↓
target/classes
```

It does not mean full regression execution.

---

# SECTION 17 — mvn test

## 17. Test

```bash
mvn test
```

runs tests associated with Maven's test phase.

Conceptually:

```text
compile application
      ↓
compile tests
      ↓
test execution
```

For our API automation project, this is especially important because TestNG automation executes through Maven.

---

# SECTION 18 — mvn package

## 18. Package

```bash
mvn package
```

runs the lifecycle through packaging.

For a JAR-based application:

```text
source
 ↓
compile
 ↓
test
 ↓
package
 ↓
JAR
```

The artifact normally appears under:

```text
target/
```

---

# SECTION 19 — mvn verify

## 19. Verify

```bash
mvn verify
```

runs through the `verify` phase.

It is intended to perform checks needed to verify that the package is valid and meets configured quality criteria.

What actually runs depends on the project's configured plugins.

Important:

```text
verify
≠ automatically every possible test
```

Plugin configuration determines behavior.

---

# SECTION 20 — mvn install

## 20. Install

```bash
mvn install
```

runs earlier lifecycle phases and then installs the built artifact into the local Maven repository.

Concept:

```text
Project
 ↓
Build
 ↓
Package
 ↓
Local Maven Repository
```

This allows other local Maven projects to depend on that artifact.

---

# SECTION 21 — mvn deploy

## 21. Deploy

```bash
mvn deploy
```

is intended to publish an artifact to a configured remote repository.

Examples conceptually:

```text
Nexus
Artifactory
other Maven repository
```

Important:

```text
mvn deploy
```

does NOT mean:

```text
deploy my Spring Boot application to production
```

It refers to Maven artifact deployment.

This is a common interview trap.

---

# SECTION 22 — CLEAN + TEST

## 22. Why Use This?

```bash
mvn clean test
```

Means:

```text
remove old build output
        ↓
create fresh compilation
        ↓
execute tests
```

For our API framework we use the Maven Wrapper through our local runner.

Conceptually:

```bash
./mvnw clean test
```

---

# SECTION 23 — MAVEN WRAPPER

## 23. What Is Maven Wrapper?

A project may contain:

```text
mvnw
mvnw.cmd
.mvn/
```

This is the Maven Wrapper.

Instead of relying entirely on:

```bash
mvn
```

we can use:

```bash
./mvnw
```

---

# SECTION 24 — mvn VS ./mvnw

## 24. Difference

### System Maven

```bash
mvn test
```

uses Maven installed on the machine.

### Maven Wrapper

```bash
./mvnw test
```

uses the Maven version/configuration associated with the project wrapper.

Benefits:

```text
more consistent builds
developer consistency
CI consistency
less dependence on global Maven installation
```

---

# SECTION 25 — CHECK MAVEN VERSION

## 25. Commands

System Maven:

```bash
mvn -version
```

Wrapper:

```bash
./mvnw -version
```

The output can show information such as:

```text
Maven version
Java version
Java home
OS
architecture
```

This is very useful during CI troubleshooting.

---

# SECTION 26 — DEPENDENCIES

## 26. What Is a Dependency?

A dependency is external code our project needs.

Instead of manually downloading libraries, Maven can resolve them.

Concept:

```text
pom.xml
   ↓
Dependency declaration
   ↓
Maven repository
   ↓
Download dependency
   ↓
Classpath
```

Examples from our overall project include libraries/frameworks for:

```text
Spring
PostgreSQL
JWT
Swagger/OpenAPI
REST Assured
TestNG
Allure
```

---

# SECTION 27 — DEPENDENCY DECLARATION

## 27. General Structure

```xml
<dependency>
    <groupId>...</groupId>
    <artifactId>...</artifactId>
    <version>...</version>
</dependency>
```

Maven uses these coordinates to resolve the artifact.

---

# SECTION 28 — WHERE MAVEN DOWNLOADS DEPENDENCIES

## 28. Local Repository

Maven normally maintains a local repository under:

```text
~/.m2/repository/
```

Concept:

```text
Maven Central / Remote Repository
              ↓
           Maven
              ↓
       ~/.m2/repository
              ↓
           Project
```

Once downloaded, dependencies can often be reused from the local repository.

---

# SECTION 29 — MAVEN CENTRAL

## 29. What Is Maven Central?

Maven Central is a widely used public repository containing Java/JVM artifacts.

When Maven needs a dependency that is not available locally, it can retrieve it from configured remote repositories such as Maven Central.

---

# SECTION 30 — CORPORATE REPOSITORIES

## 30. Nexus / Artifactory Concept

Companies may use repository managers such as:

```text
Sonatype Nexus Repository
JFrog Artifactory
```

These can provide:

```text
internal libraries
controlled external dependencies
artifact storage
release artifacts
repository proxying
```

Flow:

```text
Developer / CI
      ↓
Corporate Repository
      ↓
Internal + Approved External Artifacts
```

---

# SECTION 31 — TRANSITIVE DEPENDENCIES

## 31. What Are Transitive Dependencies?

Suppose:

```text
Our Project
   ↓
Library A
   ↓
Library B
```

We directly declare:

```text
Library A
```

but Maven may also bring:

```text
Library B
```

because A depends on B.

B is a:

```text
transitive dependency
```

---

# SECTION 32 — WHY TRANSITIVE DEPENDENCIES MATTER

## 32. Possible Problems

They can lead to:

```text
version conflicts
unexpected libraries
security vulnerabilities
classpath problems
larger dependency tree
```

Therefore we should know how to inspect them.

---

# SECTION 33 — dependency:tree

## 33. One of the Most Useful Maven Commands

```bash
mvn dependency:tree
```

or:

```bash
./mvnw dependency:tree
```

It displays the project's dependency hierarchy.

Useful when:

```text
two versions appear
class not found
method not found
unexpected dependency
security issue
```

---

# SECTION 34 — DEPENDENCY CONFLICT

## 34. Example Concept

Suppose:

```text
Library A
→ Utility 1.0

Library B
→ Utility 2.0
```

Now project has competing dependency paths.

Maven applies dependency mediation rules to determine which version is selected.

This can create runtime surprises if incompatible versions are involved.

---

# SECTION 35 — DEPENDENCY EXCLUSION

## 35. Concept

Sometimes a transitive dependency should not be included.

Maven supports:

```xml
<exclusions>
    ...
</exclusions>
```

Concept:

```text
Dependency A
   ↓
Unwanted Dependency B

Exclude B
```

Use exclusions intentionally rather than randomly when a build fails.

---

# SECTION 36 — DEPENDENCY SCOPES

## 36. Why Scope Exists

Not every dependency is required everywhere.

Maven dependency scopes control where a dependency participates.

Important scopes include:

```text
compile
provided
runtime
test
```

---

# SECTION 37 — compile SCOPE

## 37. Compile

`compile` is the default dependency scope.

Generally available for:

```text
compilation
testing
runtime
```

unless overridden by dependency relationships/configuration.

---

# SECTION 38 — test SCOPE

## 38. Test

Example:

```xml
<scope>test</scope>
```

means the dependency is intended for test compilation/execution rather than normal application runtime packaging.

Testing libraries are common candidates.

---

# SECTION 39 — runtime SCOPE

## 39. Runtime

A runtime dependency is needed when the application runs but not necessarily for compiling application source.

---

# SECTION 40 — provided SCOPE

## 40. Provided

`provided` means the dependency is required for compilation/testing but expected to be supplied by the runtime/container environment.

Exact use depends on application architecture.

---

# SECTION 41 — PLUGINS

## 41. Dependency vs Plugin

Very important:

```text
Dependency
→ code used by application/tests

Plugin
→ performs build work
```

Examples of build work:

```text
compile
run tests
package
generate reports
```

---

# SECTION 42 — MAVEN PLUGIN STRUCTURE

## 42. Concept

Plugins can be configured under:

```xml
<build>
    <plugins>
        ...
    </plugins>
</build>
```

A plugin can expose:

```text
goals
```

which can be connected to Maven lifecycle phases.

---

# SECTION 43 — SUREFIRE

## 43. Maven Surefire Plugin

The Maven Surefire Plugin is commonly used to execute tests during Maven's:

```text
test
```

phase.

Flow:

```text
mvn test
   ↓
test lifecycle phase
   ↓
Surefire
   ↓
test framework
   ↓
tests execute
```

In our automation world, understanding this connection is important.

---

# SECTION 44 — FAILING TESTS

## 44. Why Maven Returns Failure

Suppose automated tests fail.

Maven can return a non-zero exit code.

Concept:

```text
Test failure
    ↓
Maven build failure
    ↓
non-zero exit code
    ↓
CI job fails
```

This is exactly why Maven and CI/CD are connected.

---

# SECTION 45 — SKIPPING TESTS

## 45. Common Command

You may encounter:

```bash
mvn package -DskipTests
```

This typically skips running tests while still allowing test-source compilation behavior according to Maven/plugin configuration.

Another property commonly seen is:

```bash
-Dmaven.test.skip=true
```

which can skip test compilation as well.

### Engineering Principle

Do not skip tests merely to make a pipeline green.

Understand why they are being skipped.

---

# SECTION 46 — `-D` SYSTEM/USER PROPERTIES

## 46. Command-Line Properties

Maven accepts properties using:

```text
-Dname=value
```

Example:

```bash
mvn test -Dtest=LoginTests
```

or project-specific configuration might use:

```bash
mvn test -Denv=qa
```

Whether a property affects anything depends on how the project/plugins consume it.

---

# SECTION 47 — RUN SPECIFIC TEST

## 47. Common Surefire Pattern

Example:

```bash
mvn test -Dtest=LoginTests
```

A method can also be targeted in supported configurations, for example:

```bash
mvn test -Dtest=LoginTests#validLogin
```

Exact support depends on the test framework and Surefire configuration.

Useful during:

```text
debugging
local development
focused validation
```

Full regression should still be run at appropriate milestones.

---

# SECTION 48 — MAVEN PROFILES

## 48. What Is a Maven Profile?

Maven profiles allow build configuration to vary under different conditions.

Example concept:

```text
local
qa
stage
```

A profile can be activated using:

```bash
mvn test -Pqa
```

if the project defines a `qa` profile.

### Important for Our Project

Our current API framework environment switching is based on environment/runtime configuration.

We should NOT introduce Maven profiles just because they exist.

Use them only when they solve a real build-configuration problem.

---

# SECTION 49 — settings.xml

## 49. What Is Maven settings.xml?

Maven can use a settings file commonly located at:

```text
~/.m2/settings.xml
```

It may configure things such as:

```text
repository mirrors
authentication
proxies
profiles
servers
```

This is especially important in enterprise environments.

---

# SECTION 50 — CORPORATE PROXY SCENARIO

## 50. Example

Suppose:

```bash
mvn clean test
```

cannot download dependencies in a corporate network.

Possible reasons:

```text
proxy
repository mirror
certificate
authentication
network restrictions
```

Do not immediately change dependency versions.

Check Maven/network configuration first.

---

# SECTION 51 — OFFLINE BUILDS

## 51. Maven Offline Mode

Command:

```bash
mvn -o test
```

means:

```text
offline mode
```

Maven will rely on artifacts already available locally.

If a required dependency has never been downloaded, the build may fail.

---

# SECTION 52 — FORCE DEPENDENCY UPDATE

## 52. `-U`

You may encounter:

```bash
mvn -U clean test
```

`-U` tells Maven to force checks for updated releases/snapshots according to Maven repository behavior.

Do not use it as a universal fix for every Maven failure.

---

# SECTION 53 — CLEAN BUILD TROUBLESHOOTING

## 53. Recommended Thinking

If Maven build fails:

```text
Read actual error
     ↓
Which phase failed?
     ↓
Compilation?
Test?
Dependency resolution?
Plugin?
Packaging?
Configuration?
     ↓
Fix root cause
```

Do not start by deleting random directories.

---

# SECTION 54 — COMMON MAVEN FAILURE: COMPILATION

## 54. Example

```text
COMPILATION ERROR
cannot find symbol
```

Possible causes:

```text
wrong import
renamed method/class
missing dependency
source incompatibility
generated source missing
```

The key is:

```text
read first meaningful compiler error
```

not only:

```text
BUILD FAILURE
```

at the bottom.

---

# SECTION 55 — COMMON MAVEN FAILURE: DEPENDENCY RESOLUTION

## 55. Example

Maven cannot resolve an artifact.

Investigate:

```text
coordinates correct?
version exists?
internet/network?
corporate repository?
proxy?
authentication?
local cache issue?
```

Useful:

```bash
./mvnw dependency:tree
```

and Maven's detailed error output.

---

# SECTION 56 — COMMON MAVEN FAILURE: TEST FAILURE

## 56. Example

```text
Tests run: 48
Failures: 1
```

This is not automatically a Maven problem.

Maven may be correctly reporting:

```text
automation/product failure
```

Investigate the failing test and report.

---

# SECTION 57 — COMMON MAVEN FAILURE: JAVA VERSION

## 57. Scenario

Project requires Java 17 but Maven is running with another JDK.

Check:

```bash
java -version
```

and:

```bash
./mvnw -version
```

Why both?

Because the important question is:

```text
Which Java is Maven actually using?
```

---

# SECTION 58 — JAVA_HOME CONNECTION

## 58. Why JAVA_HOME Matters

`JAVA_HOME` identifies the JDK location for many Java tools.

Check:

```bash
echo "$JAVA_HOME"
```

Then:

```bash
./mvnw -version
```

If Java versions differ unexpectedly, investigate:

```text
JAVA_HOME
PATH
IDE configuration
CI runner configuration
```

---

# SECTION 59 — MAVEN DEBUG OUTPUT

## 59. `-X`

For deep troubleshooting:

```bash
mvn -X test
```

enables Maven debug output.

This can be extremely verbose.

Use when normal error output is insufficient.

Do not automatically dump huge debug logs into public tickets because they may contain environment information.

---

# SECTION 60 — MAVEN ERROR STACK TRACE

## 60. `-e`

Command:

```bash
mvn -e test
```

shows error stack traces.

Useful for deeper build troubleshooting.

---

# SECTION 61 — EFFECTIVE POM

## 61. Powerful Advanced Command

```bash
mvn help:effective-pom
```

Maven configuration may come from:

```text
project POM
parent POM
plugin defaults
dependency management
profiles
```

The effective POM shows the resulting combined Maven model.

Useful when asking:

```text
Where is this configuration coming from?
```

---

# SECTION 62 — EFFECTIVE SETTINGS

## 62. Command

```bash
mvn help:effective-settings
```

Useful for understanding effective Maven settings such as:

```text
mirrors
repositories
profiles
servers
```

Be careful sharing its output because enterprise settings can contain sensitive configuration.

---

# SECTION 63 — PARENT POM

## 63. What Is a Parent POM?

A Maven project can inherit configuration from a parent.

A parent may provide:

```text
dependency versions
plugin configuration
properties
build standards
```

Our Spring Boot backend uses Spring Boot's Maven parent approach.

Conceptually:

```text
Spring Boot Parent
       ↓
our pom.xml
       ↓
inherited defaults/version management
```

---

# SECTION 64 — DEPENDENCY MANAGEMENT

## 64. What Is dependencyManagement?

`dependencyManagement` can define dependency versions/configuration centrally without necessarily adding every dependency to the project classpath.

Child modules/dependencies can then reference managed versions.

This helps maintain version consistency.

---

# SECTION 65 — BOM

## 65. Bill of Materials

A BOM is a Maven mechanism commonly used to manage compatible dependency versions.

BOM means:

```text
Bill of Materials
```

Concept:

```text
BOM
 ↓
compatible dependency versions
 ↓
project dependencies
```

Frameworks use BOMs to reduce manual version management.

---

# SECTION 66 — WHY SPRING BOOT DOESN'T NEED EVERY VERSION

## 66. Version Management

In Spring Boot projects, many dependency versions are managed by Spring Boot dependency management.

Therefore developers often do NOT specify a version for every Spring dependency manually.

This reduces incompatible combinations.

---

# SECTION 67 — MULTI-MODULE MAVEN

## 67. Concept

Maven can manage multiple modules under a parent project.

Example:

```text
parent/
├── pom.xml
├── service-a/
├── service-b/
└── common/
```

However:

```text
backend/
api-automation/
```

in our current portfolio are intentionally separate projects.

We should not convert them into a Maven multi-module project unless we have a real architectural reason.

---

# SECTION 68 — MAVEN IN CI/CD

## 68. Typical CI Flow

```text
Checkout
   ↓
Setup Java
   ↓
Maven Wrapper
   ↓
clean
   ↓
compile
   ↓
test
   ↓
report
```

Possible command:

```bash
./mvnw clean test
```

For backend packaging:

```bash
./mvnw clean package
```

---

# SECTION 69 — MAVEN CACHE IN CI

## 69. Why Cache Dependencies?

Without caching:

```text
CI run
→ download dependencies

next CI run
→ download again
```

With an appropriate Maven cache:

```text
dependencies reused
→ faster build
```

But cache correctness still matters.

A stale/corrupt cache can occasionally contribute to build problems.

---

# SECTION 70 — MAVEN + TESTNG

## 70. Relationship

Important interview concept:

```text
Maven
→ build/execution orchestration

Surefire
→ test execution integration

TestNG
→ testing framework

REST Assured
→ API testing library

Allure
→ reporting
```

These tools solve different problems.

Do not say:

```text
Maven is my testing framework.
```

---

# SECTION 71 — MAVEN + REST ASSURED

## 71. Relationship

REST Assured is a Java library.

Maven:

```text
resolves REST Assured dependency
compiles test code
executes test lifecycle through plugins
```

REST Assured itself handles:

```text
HTTP API test interactions/assertions
```

---

# SECTION 72 — MAVEN + ALLURE

## 72. Relationship

Allure integration libraries/listeners produce reporting data during test execution.

Maven participates in:

```text
dependency management
test execution
build lifecycle
```

The Allure CLI/report tooling can then consume generated Allure results.

Again:

```text
Maven
≠ Allure
```

---

# SECTION 73 — MAVEN + SPRING BOOT

## 73. Running Backend

We use:

```bash
./mvnw spring-boot:run
```

This is different from:

```bash
./mvnw test
```

Here:

```text
spring-boot
→ plugin prefix

run
→ plugin goal
```

The Spring Boot Maven Plugin starts the application.

---

# SECTION 74 — PLUGIN GOAL VS LIFECYCLE PHASE

## 74. Important Difference

Example lifecycle phase:

```bash
./mvnw test
```

Example plugin goal:

```bash
./mvnw spring-boot:run
```

Example plugin goal:

```bash
./mvnw dependency:tree
```

So:

```text
test
→ lifecycle phase

spring-boot:run
→ plugin goal

dependency:tree
→ plugin goal
```

---

# SECTION 75 — BUILD SUCCESS

## 75. What Does BUILD SUCCESS Mean?

It means Maven successfully completed the requested build operation.

It does NOT automatically mean:

```text
application has no bugs
all environments work
all possible tests passed
production is healthy
```

It only means:

```text
requested Maven execution completed successfully
```

---

# SECTION 76 — BUILD FAILURE

## 76. What Does BUILD FAILURE Mean?

It means Maven could not successfully complete the requested operation.

Root cause could be:

```text
compilation
test failure
dependency resolution
plugin
configuration
environment
Java version
network
```

Always inspect the actual error above:

```text
BUILD FAILURE
```

---

# SECTION 77 — OUR BACKEND COMMAND

## 77. Local Backend

Our backend local startup is wrapped by:

```bash
./backend/run-local.sh
```

The script loads local environment configuration and then invokes:

```bash
./mvnw spring-boot:run
```

This gives us:

```text
Shell
 ↓
Environment Variables
 ↓
Maven Wrapper
 ↓
Spring Boot Plugin
 ↓
Backend
```

This is a great example of multiple engineering tools working together.

---

# SECTION 78 — OUR API AUTOMATION COMMAND

## 78. Local Tests

Our API automation local runner conceptually does:

```text
Load .env
    ↓
Maven Wrapper
    ↓
clean test
    ↓
TestNG
    ↓
REST Assured tests
    ↓
Allure results
```

This is why understanding Maven is important even though our main job is testing.

---

# SECTION 79 — INTERVIEW: WHY MAVEN?

## 79. Strong Answer

> "I use Maven for dependency management and build automation in Java-based test frameworks and backend projects. It gives us a standard project structure and lifecycle, resolves libraries from repositories, runs tests through build plugins and integrates cleanly with CI/CD. In my API framework I use the Maven Wrapper so local and CI execution can use a consistent Maven setup."

---

# SECTION 80 — INTERVIEW: MAVEN LIFECYCLE

## 80. Strong Answer

> "Maven organizes builds into lifecycle phases. In the default lifecycle, common phases include compile, test, package, verify, install and deploy. When a later phase is invoked, Maven runs the preceding phases required to reach it. The clean lifecycle is separate and is used to remove previous build output."

---

# SECTION 81 — INTERVIEW: mvn test VS package

## 81. Answer

```text
mvn test
→ runs lifecycle through test

mvn package
→ continues further and creates package/artifact
```

Because package comes later:

```text
package also reaches the test phase first
```

unless test execution has been explicitly skipped/configured differently.

---

# SECTION 82 — INTERVIEW: install VS deploy

## 82. Answer

```text
install
→ installs artifact into local Maven repository

deploy
→ publishes artifact to configured remote Maven repository
```

Neither automatically means application deployment to production.

---

# SECTION 83 — INTERVIEW: MAVEN WRAPPER

## 83. Answer

> "The Maven Wrapper lets the project define/use a Maven distribution without requiring every developer or CI agent to rely on the same globally installed Maven version. I prefer the wrapper in project commands because it improves build consistency."

---

# SECTION 84 — INTERVIEW: DEPENDENCY VS PLUGIN

## 84. Answer

```text
Dependency
→ code required by application/tests

Plugin
→ performs Maven build tasks
```

Example:

```text
REST Assured
→ dependency

Maven Surefire Plugin
→ plugin
```

---

# SECTION 85 — INTERVIEW: TRANSITIVE DEPENDENCY

## 85. Answer

> "A transitive dependency is a dependency brought indirectly through another dependency. Maven resolves these automatically, but they can create version conflicts or unexpected libraries, so I use `dependency:tree` when investigating dependency issues."

---

# SECTION 86 — INTERVIEW: LOCAL MAVEN REPOSITORY

## 86. Answer

Default location is commonly:

```text
~/.m2/repository
```

It stores downloaded and locally installed Maven artifacts.

---

# SECTION 87 — INTERVIEW: TESTS FAIL IN MAVEN

## 87. Answer

> "I first distinguish whether Maven itself failed or Maven correctly reported a test failure. I inspect the first meaningful error, test report and stack trace. If the issue is dependency or build related, I check the dependency tree, Java/Maven versions and environment configuration rather than changing the test blindly."

---

# SECTION 88 — INTERVIEW: WORKS LOCALLY, FAILS IN CI

## 88. Maven-Specific Checks

```text
Java version
Maven/Wrapper version
environment variables
repository access
settings.xml
proxy
filesystem permissions
working directory
dependency cache
```

---

# SECTION 89 — MAVEN TROUBLESHOOTING CHEAT SHEET

## 89. Version

```bash
./mvnw -version
```

## 90. Clean Test

```bash
./mvnw clean test
```

## 91. Package

```bash
./mvnw clean package
```

## 92. Dependency Tree

```bash
./mvnw dependency:tree
```

## 93. Error Stack Trace

```bash
./mvnw -e test
```

## 94. Debug

```bash
./mvnw -X test
```

## 95. Effective POM

```bash
./mvnw help:effective-pom
```

## 96. Effective Settings

```bash
./mvnw help:effective-settings
```

## 97. Force Update Check

```bash
./mvnw -U test
```

Use troubleshooting flags intentionally.

---

# SECTION 90 — MAVEN MENTAL MODEL

## 98. Complete Picture

```text
pom.xml
   ↓
Maven
   ↓
Repositories
   ↓
Dependencies
   ↓
Compile
   ↓
Tests
   ↓
Plugins
   ↓
Package
   ↓
Artifact
   ↓
CI/CD
```

---

# SECTION 91 — OUR PROJECT MENTAL MODEL

## 99. Backend

```text
backend/pom.xml
       ↓
Maven Wrapper
       ↓
Spring Boot
       ↓
Compile
       ↓
Tests
       ↓
JAR / Runtime
```

## 100. API Automation

```text
api-automation/pom.xml
        ↓
Maven Wrapper
        ↓
TestNG + REST Assured
        ↓
API Tests
        ↓
DB Validation
        ↓
Allure Results
```

---

# SECTION 92 — FINAL MAVEN RAPID REVISION

## 101. Remember These

```text
pom.xml
Maven coordinates
standard directory structure
target/
clean
compile
test
package
verify
install
deploy
Maven Wrapper
dependencies
transitive dependencies
scopes
plugins
Surefire
.m2
Maven Central
Nexus/Artifactory
settings.xml
dependency:tree
effective-pom
Java version
CI integration
```

---

# SECTION 93 — ONE-MINUTE MAVEN INTERVIEW ANSWER

## 102.

> "Maven is the build and dependency-management tool I use for Java-based automation and Spring Boot projects. I manage project dependencies through `pom.xml`, use the standard Maven lifecycle for compilation, testing and packaging, and prefer the Maven Wrapper for consistent execution. I understand dependency scopes, transitive dependencies, plugins such as Surefire, the local `.m2` repository and commands such as `dependency:tree` for troubleshooting. In CI/CD, Maven provides a predictable command such as `./mvnw clean test` whose exit status can directly contribute to the pipeline quality result."

---

# END OF PART 1 — MAVEN ZERO TO ADVANCED

Next:

**PART 2 — JDK, JRE, JVM, JAVA_HOME, PATH, Compilation, Bytecode, Classpath, JAR and the Complete Java Build/Runtime Flow**

---

# PART 2 — JDK, JRE, JVM & JAVA BUILD FUNDAMENTALS

# SECTION 94 — WHY THIS MATTERS FOR SDET

As an SDET working with Java-based automation and Spring Boot, it is not enough to know:

```bash
java -version
mvn test
```

We should also understand what is happening underneath.

Important flow:

```text
Java Source Code
      ↓
Compiler
      ↓
Bytecode
      ↓
JVM
      ↓
Running Application/Test
```

This knowledge becomes useful when we face:

```text
Java version mismatch
Maven compilation failure
ClassNotFoundException
NoClassDefFoundError
UnsupportedClassVersionError
CI-only failures
runtime dependency issues
```

---

# SECTION 95 — WHAT IS JAVA?

## 103. Java Overview

Java is:

```text
Programming Language
+
Platform Ecosystem
```

We write source code such as:

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

This source code is not directly executed by the CPU.

It first goes through Java compilation.

---

# SECTION 96 — SOURCE CODE TO EXECUTION

## 104. Complete Flow

```text
Hello.java
   ↓
javac
   ↓
Hello.class
   ↓
Bytecode
   ↓
JVM
   ↓
Machine-specific execution
```

This is one of the most important Java mental models.

---

# SECTION 97 — WHAT IS JDK?

## 105. JDK Definition

JDK means:

```text
Java Development Kit
```

It provides tools required to develop Java applications.

It includes tools such as:

```text
javac
java
jar
javadoc
other development tools
```

For development and Maven builds, we normally need a JDK.

---

# SECTION 98 — WHAT IS JRE?

## 106. JRE Definition

JRE means:

```text
Java Runtime Environment
```

Conceptually, it provides the runtime components required to execute Java applications.

Traditional mental model:

```text
JDK
├── Development Tools
└── Runtime Environment
    └── JVM
```

Modern Java distributions do not always ship a separate standalone JRE in the same way older Java distributions did.

For interview understanding:

```text
JDK
→ development + runtime capability

JRE
→ runtime concept

JVM
→ executes Java bytecode
```

---

# SECTION 99 — WHAT IS JVM?

## 107. JVM Definition

JVM means:

```text
Java Virtual Machine
```

Its job is to execute Java bytecode.

Concept:

```text
.class bytecode
      ↓
JVM
      ↓
Operating System / CPU
```

Different operating systems can have their own JVM implementations.

This contributes to Java's portability model.

---

# SECTION 100 — JDK VS JRE VS JVM

## 108. Simple Comparison

```text
JDK
→ Develop + Compile + Run

JRE
→ Runtime environment concept

JVM
→ Executes bytecode
```

Easy interview memory:

```text
JDK contains development tools
JVM runs bytecode
```

---

# SECTION 101 — WHY JAVA IS CALLED PLATFORM INDEPENDENT

## 109. Concept

Java source code is compiled into:

```text
bytecode
```

instead of directly into one operating system's native executable.

Then a compatible JVM executes that bytecode.

Concept:

```text
Java Source
    ↓
Bytecode
    ↓
Windows JVM
Linux JVM
macOS JVM
```

This leads to the famous idea:

```text
Write Once, Run Anywhere
```

But practical compatibility still depends on:

```text
Java version
native libraries
OS-specific behavior
filesystem
environment
architecture
```

So "platform independent" does not mean every Java application behaves identically everywhere.

---

# SECTION 102 — WHAT IS javac?

## 110. Java Compiler

`javac` is the Java compiler.

Example:

```bash
javac Hello.java
```

It converts:

```text
Hello.java
```

into:

```text
Hello.class
```

---

# SECTION 103 — WHAT IS java COMMAND?

## 111. Running Java

After compilation:

```bash
java Hello
```

runs the class.

Important:

```text
javac
→ compile

java
→ run
```

Common interview question.

---

# SECTION 104 — .java VS .class

## 112. Difference

### `.java`

Contains:

```text
human-readable Java source code
```

Example:

```text
User.java
```

### `.class`

Contains:

```text
compiled Java bytecode
```

Example:

```text
User.class
```

The JVM executes the bytecode.

---

# SECTION 105 — BYTECODE

## 113. What Is Bytecode?

Bytecode is an intermediate instruction format produced by the Java compiler.

Flow:

```text
Source Code
   ↓
javac
   ↓
Bytecode
   ↓
JVM
```

It is not the same as:

```text
Java source code
```

and not exactly the same as:

```text
native machine code
```

---

# SECTION 106 — JVM EXECUTION

## 114. What Happens Inside JVM?

High-level view:

```text
Load Classes
    ↓
Verify Bytecode
    ↓
Execute
```

The JVM may use techniques such as:

```text
interpretation
JIT compilation
```

to execute code efficiently.

---

# SECTION 107 — JIT COMPILER

## 115. Just-In-Time Compilation

JIT means:

```text
Just-In-Time
```

The JVM can compile frequently executed bytecode into native machine code during runtime.

Concept:

```text
Bytecode
   ↓
JVM observes execution
   ↓
Hot code identified
   ↓
JIT compilation
   ↓
Native machine code
```

This helps improve runtime performance.

---

# SECTION 108 — CHECK JAVA VERSION

## 116. Command

```bash
java -version
```

This tells us which Java runtime is being used by the current shell command resolution.

For development, also check:

```bash
javac -version
```

---

# SECTION 109 — WHY CHECK BOTH java AND javac?

## 117. Scenario

Imagine:

```text
java = 17
javac = another version
```

This can indicate an inconsistent environment.

Check:

```bash
java -version
javac -version
```

For Maven:

```bash
./mvnw -version
```

because Maven's Java runtime matters too.

---

# SECTION 110 — OUR PROJECT JAVA VERSION

## 118. Current Project

Our backend is built around:

```text
Java 17
```

Therefore the expected local development environment should use a compatible Java 17 JDK for the Maven build.

Useful checks:

```bash
java -version
javac -version
./mvnw -version
```

---

# SECTION 111 — JAVA_HOME

## 119. What Is JAVA_HOME?

`JAVA_HOME` is an environment variable pointing to a Java installation.

Example concept:

```text
JAVA_HOME
    ↓
/path/to/jdk-17
```

Check:

```bash
echo "$JAVA_HOME"
```

Tools such as Maven, Gradle and scripts may use it.

---

# SECTION 112 — PATH

## 120. What Is PATH?

`PATH` is an environment variable containing directories the shell searches for executable commands.

Example concept:

```text
PATH
→ /usr/bin
→ /usr/local/bin
→ Java bin directory
→ other tool directories
```

When we type:

```bash
java
```

the shell searches directories in `PATH`.

---

# SECTION 113 — JAVA_HOME VS PATH

## 121. Difference

```text
JAVA_HOME
→ points to Java/JDK installation

PATH
→ tells shell where executables can be found
```

Typical concept:

```text
JAVA_HOME=/path/to/jdk

PATH includes:
$JAVA_HOME/bin
```

Then commands such as:

```text
java
javac
```

become available.

---

# SECTION 114 — WHICH JAVA IS RUNNING?

## 122. Command

On Unix-like systems:

```bash
which java
```

This shows which executable is resolved from PATH.

Also:

```bash
which javac
```

Use together with:

```bash
java -version
javac -version
echo "$JAVA_HOME"
```

---

# SECTION 115 — JAVA VERSION MISMATCH SCENARIO

## 123. Example

Application expects:

```text
Java 17
```

but CI runner uses:

```text
Java 11
```

Possible result:

```text
compilation failure
runtime failure
plugin compatibility issue
```

Troubleshooting:

```bash
java -version
javac -version
./mvnw -version
echo "$JAVA_HOME"
```

Do not assume Maven and terminal are automatically using the same Java installation.

---

# SECTION 116 — CLASS VERSION COMPATIBILITY

## 124. Java Bytecode Version

Java class files contain version information.

If code is compiled with a newer JDK and executed using an older JVM, runtime may reject it.

Example mental model:

```text
Compile with newer Java
        ↓
newer class-file version
        ↓
Run on older JVM
        ↓
failure
```

---

# SECTION 117 — UnsupportedClassVersionError

## 125. What Does It Mean?

Typical meaning:

```text
class was compiled using a newer Java version
than the runtime supports
```

Example:

```text
Compile → Java 17
Run     → Java 11
```

Possible result:

```text
UnsupportedClassVersionError
```

### Troubleshooting

Check:

```bash
java -version
javac -version
./mvnw -version
```

Then compare compile and runtime versions.

---

# SECTION 118 — COMPILE-TIME VS RUNTIME

## 126. Compile-Time

Errors detected while compiling.

Examples:

```text
syntax error
cannot find symbol
wrong method signature
type mismatch
```

These stop code from compiling.

---

## 127. Runtime

Errors happen after the program has successfully compiled and begins execution.

Examples:

```text
NullPointerException
database unavailable
ClassNotFoundException
HTTP connection failure
```

---

# SECTION 119 — COMPILE-TIME EXAMPLE

## 128.

Suppose code says:

```java
String name = 10;
```

The compiler detects incompatible types.

This is a:

```text
compile-time error
```

The application never reaches normal execution.

---

# SECTION 120 — RUNTIME EXAMPLE

## 129.

This can compile:

```java
String name = null;
System.out.println(name.length());
```

But while running, it can produce:

```text
NullPointerException
```

This is runtime failure.

---

# SECTION 121 — WHAT IS CLASSPATH?

## 130. Classpath Definition

Classpath tells the Java runtime/compiler where classes and libraries can be found.

Concept:

```text
Application
   ↓
Needs Class X
   ↓
Classpath
   ↓
Find Class X
```

Classpath may include:

```text
compiled classes
JAR files
dependency libraries
```

---

# SECTION 122 — MAVEN AND CLASSPATH

## 131. Why We RareLY Build Classpath Manually

In a Maven project, Maven resolves dependencies and constructs appropriate classpaths for:

```text
compile
test
runtime
```

This is one major benefit of Maven.

Without Maven, we might need to manage many JAR locations manually.

---

# SECTION 123 — MAIN CLASSPATH VS TEST CLASSPATH

## 132. Concept

Application compile classpath may contain:

```text
main dependencies
```

Test classpath may additionally contain:

```text
TestNG
REST Assured
test utilities
```

This is related to Maven dependency scopes.

---

# SECTION 124 — WHAT IS A JAR?

## 133. JAR Definition

JAR means:

```text
Java ARchive
```

A JAR packages Java-related files into one archive.

It can contain:

```text
.class files
resources
metadata
```

Concept:

```text
compiled application
      ↓
package
      ↓
application.jar
```

---

# SECTION 125 — INSPECT A JAR

## 134. Command

A JAR is based on ZIP format.

You can inspect its contents using:

```bash
jar tf application.jar
```

This lists files inside the JAR.

---

# SECTION 126 — RUN A JAR

## 135. Command

For an executable JAR:

```bash
java -jar application.jar
```

Flow:

```text
JAR
 ↓
JVM
 ↓
Application starts
```

---

# SECTION 127 — WHAT MAKES A JAR EXECUTABLE?

## 136. Concept

An executable JAR includes metadata that identifies the application entry point and required packaging structure.

Traditional Java JAR metadata can include:

```text
Main-Class
```

Frameworks such as Spring Boot provide their own executable JAR packaging mechanism.

---

# SECTION 128 — SPRING BOOT EXECUTABLE JAR

## 137. Spring Boot Flow

For a Spring Boot application:

```text
Source Code
   ↓
Maven
   ↓
Spring Boot Maven Plugin
   ↓
Executable JAR
   ↓
java -jar
```

This allows the backend to run without separately deploying it into a traditional external application server.

---

# SECTION 129 — MAVEN PACKAGE TO JAR

## 138. Concept

When packaging the backend:

```bash
./mvnw clean package
```

conceptually:

```text
clean
 ↓
compile
 ↓
test
 ↓
package
 ↓
target/*.jar
```

Exact output filename depends on project coordinates and build configuration.

---

# SECTION 130 — SPRING-BOOT:RUN VS JAVA -JAR

## 139. Difference

During development:

```bash
./mvnw spring-boot:run
```

runs the application through the Spring Boot Maven Plugin.

After packaging:

```bash
java -jar target/<application>.jar
```

runs the packaged application.

Mental model:

```text
Development
→ spring-boot:run

Packaged Runtime
→ java -jar
```

---

# SECTION 131 — MAIN METHOD

## 140. Java Entry Point

Traditional Java entry point:

```java
public static void main(String[] args)
```

Our Spring Boot application also has a main application class.

Concept:

```text
JVM
 ↓
main()
 ↓
SpringApplication.run(...)
 ↓
Spring Boot starts
```

---

# SECTION 132 — SPRING BOOT STARTUP FLOW

## 141. High-Level View

```text
java / Maven
    ↓
Main Class
    ↓
Spring Boot
    ↓
Application Context
    ↓
Beans
    ↓
Configuration
    ↓
Embedded Web Server
    ↓
Application Ready
```

We will study Spring Boot in more detail in Part 3.

---

# SECTION 133 — CLASS LOADING

## 142. What Is Class Loading?

During runtime the JVM needs to load classes before using them.

Concept:

```text
Application references class
         ↓
Class loader searches
         ↓
Class definition loaded
         ↓
JVM can use class
```

If it cannot find required classes, class-loading errors may occur.

---

# SECTION 134 — ClassNotFoundException

## 143. Meaning

`ClassNotFoundException` generally occurs when code explicitly attempts to load a class by name and the class cannot be found at runtime.

Conceptual example:

```java
Class.forName("some.Driver");
```

but required class is unavailable.

Potential causes:

```text
dependency missing
incorrect classpath
wrong class name
runtime packaging issue
```

---

# SECTION 135 — NoClassDefFoundError

## 144. Meaning

`NoClassDefFoundError` means JVM expected a class definition to be available during runtime but it could not successfully load/use it.

Possible reasons:

```text
dependency missing at runtime
incorrect packaging
class initialization failure
classpath inconsistency
```

---

# SECTION 136 — ClassNotFoundException VS NoClassDefFoundError

## 145. Interview-Level Difference

Simplified:

```text
ClassNotFoundException
→ often explicit/dynamic class loading cannot find class

NoClassDefFoundError
→ JVM/runtime expected a class but definition is unavailable/unusable
```

Both usually lead us to inspect:

```text
classpath
dependencies
packaging
runtime environment
```

---

# SECTION 137 — NoSuchMethodError

## 146. Meaning

A `NoSuchMethodError` often indicates binary/runtime incompatibility.

Example concept:

```text
Code compiled expecting:
Library.methodA()

Runtime contains older/different library version
without methodA()
```

Possible cause:

```text
dependency version conflict
```

Very useful command:

```bash
./mvnw dependency:tree
```

---

# SECTION 138 — WHY NoSuchMethodError CAN COMPILE SUCCESSFULLY

## 147. Scenario

During compilation:

```text
Library version A
```

was available.

At runtime:

```text
Library version B
```

is loaded.

If B lacks the expected method:

```text
NoSuchMethodError
```

This shows why:

```text
BUILD SUCCESS
```

does not guarantee runtime dependency compatibility in every environment.

---

# SECTION 139 — NoSuchFieldError

## 148. Similar Concept

`NoSuchFieldError` can occur when compiled code expects a field that the runtime version of a class does not provide.

Again inspect:

```text
dependency versions
runtime classpath
dependency tree
```

---

# SECTION 140 — JAVA CLASS LOADER CONCEPT

## 149. High-Level Model

The JVM uses class loaders to load classes.

You do not need deep JVM internals for most SDET interviews.

Remember:

```text
Java does not load every class from source code directly.

It loads compiled classes available through runtime classpath/module mechanisms.
```

---

# SECTION 141 — JAR DEPENDENCY PROBLEM

## 150. Example

Suppose our automation requires:

```text
REST Assured
```

The source compiles because Maven knows the dependency.

But if runtime execution somehow lacks the required JARs:

```text
class-loading/runtime errors
```

can occur.

Maven normally manages this for test execution.

---

# SECTION 142 — FAT JAR / UBER JAR CONCEPT

## 151. What Is a Fat JAR?

A fat/uber JAR packages:

```text
application
+
dependencies
```

into a deployable structure/archive.

Spring Boot executable JARs provide a related packaging approach where application classes and dependencies are packaged so the Boot launcher can run them.

Important:

Do not assume every normal JAR automatically contains all dependencies.

---

# SECTION 143 — THIN JAR CONCEPT

## 152. Thin JAR

Conceptually:

```text
application classes
```

are packaged, but dependencies may need to be supplied separately.

Whether an application uses thin or bundled packaging depends on build strategy.

---

# SECTION 144 — WHAT IS MANIFEST.MF?

## 153. Manifest

A JAR can contain:

```text
META-INF/MANIFEST.MF
```

It stores metadata.

Traditional executable JARs may use:

```text
Main-Class
```

in the manifest.

---

# SECTION 145 — JAVA PACKAGE VS JAR

## 154. Do Not Confuse

Java package:

```java
package com.sdetcommerce.backend;
```

is a namespace/code organization mechanism.

JAR:

```text
backend.jar
```

is a packaged archive.

They are completely different concepts.

---

# SECTION 146 — PACKAGE NAME AND DIRECTORY STRUCTURE

## 155. Example

Java class:

```java
package com.sdetcommerce.backend;
```

usually lives under a matching source directory structure:

```text
src/main/java/com/sdetcommerce/backend/
```

Maven standard layout combines with Java package conventions.

---

# SECTION 147 — IMPORT

## 156. What Is Import?

Example:

```java
import java.util.List;
```

`import` lets source code refer to a type using a shorter name.

It does NOT:

```text
download a dependency
install a library
```

Maven manages external dependencies.

This is another useful interview distinction.

---

# SECTION 148 — PACKAGE VS IMPORT VS DEPENDENCY

## 157. Mental Model

```text
package
→ where our class logically belongs

import
→ how source references another type

dependency
→ external library made available to project
```

---

# SECTION 149 — JAVA PROCESS

## 158. When Spring Boot Runs

A running Spring Boot application normally exists as a Java process.

Useful commands:

```bash
ps aux | grep java
```

or:

```bash
pgrep -fl java
```

Then ports can be checked using:

```bash
lsof -i :8080
```

---

# SECTION 150 — JVM MEMORY BASICS

## 159. Why SDET Should Know This

You do not need to become a JVM performance engineer.

But basic JVM memory understanding helps when tests or services fail because of:

```text
OutOfMemoryError
large test suites
huge reports
memory leak
large payload processing
```

---

# SECTION 151 — HEAP

## 160. What Is Heap?

Heap is a major JVM memory area where objects are allocated.

Concept:

```java
new User()
```

creates an object whose runtime storage typically involves heap memory.

---

# SECTION 152 — STACK

## 161. Stack Concept

Each thread has stack memory used for execution frames such as:

```text
method calls
local variables
execution state
```

Simplified comparison:

```text
Heap
→ objects/shared runtime data

Stack
→ method execution/thread frames
```

---

# SECTION 153 — StackOverflowError

## 162. Typical Cause

A common cause is excessive recursion.

Example concept:

```text
method()
 ↓
method()
 ↓
method()
 ↓
...
```

until stack capacity is exhausted.

Result:

```text
StackOverflowError
```

---

# SECTION 154 — OutOfMemoryError

## 163. Meaning

The JVM cannot satisfy a memory allocation requirement.

Possible areas/reasons include:

```text
heap exhaustion
too many objects
memory leak
oversized workload
incorrect JVM memory configuration
```

Do not automatically increase memory without investigating root cause.

---

# SECTION 155 — JVM OPTIONS

## 164. Example Concepts

You may see options such as:

```text
-Xms
-Xmx
```

Simplified:

```text
-Xms
→ initial heap size

-Xmx
→ maximum heap size
```

Example:

```bash
java -Xmx1g -jar application.jar
```

Do not tune these randomly in production.

---

# SECTION 156 — GARBAGE COLLECTION

## 165. GC Concept

Java automatically manages much of object memory through garbage collection.

Concept:

```text
Object no longer reachable
        ↓
eligible for garbage collection
        ↓
memory may be reclaimed
```

GC behavior is JVM/runtime-managed.

---

# SECTION 157 — WHY GC MATTERS TO TESTING

## 166. Possible Symptoms

Very memory-heavy automation may experience:

```text
slow execution
high CPU
long pauses
OutOfMemoryError
```

But always collect evidence before blaming garbage collection.

---

# SECTION 158 — THREAD BASICS

## 167. Thread

A thread is a unit of execution within a process.

Java applications may have many threads.

Automation frameworks may also execute tests in parallel using multiple threads.

This connects directly to:

```text
parallel test execution
thread safety
shared test data
static state
```

---

# SECTION 159 — PROCESS VS THREAD

## 168. Difference

```text
Process
→ running program with its own process resources

Thread
→ execution path inside a process
```

Example:

```text
Spring Boot application
→ Java process
→ multiple internal threads
```

---

# SECTION 160 — JAVA AND PARALLEL TESTING

## 169. Why This Matters

If TestNG runs tests in parallel:

```text
one Java process
    ↓
multiple test threads
```

Shared mutable state can create:

```text
flaky tests
wrong token
data collision
race conditions
```

This is not automatically a JVM bug.

It may be test-framework design.

---

# SECTION 161 — JVM SYSTEM PROPERTIES

## 170. What Are System Properties?

Java supports runtime system properties.

Example:

```bash
java -Denv=qa ...
```

In code:

```java
System.getProperty("env");
```

Maven also commonly uses `-D` properties.

Context matters because:

```text
Maven property
Java system property
plugin property
```

may interact depending on configuration.

---

# SECTION 162 — ENVIRONMENT VARIABLES VS SYSTEM PROPERTIES

## 171. Difference

Environment variable:

```bash
export TEST_ENV=qa
```

Java access:

```java
System.getenv("TEST_ENV");
```

System property:

```bash
java -Dtest.env=qa ...
```

Java access:

```java
System.getProperty("test.env");
```

They are different configuration mechanisms.

---

# SECTION 163 — OUR ENVIRONMENT STRATEGY

## 172. Current Framework

Our API automation uses runtime/environment configuration to select environments such as:

```text
local
qa
stage
```

This allows the same test code to execute against different targets.

Important principle:

```text
configuration outside test logic
```

---

# SECTION 164 — COMPILE-TIME DEPENDENCY VS RUNTIME DEPENDENCY

## 173. Concept

A class may be available during compilation but absent or incompatible at runtime.

This can cause:

```text
ClassNotFoundException
NoClassDefFoundError
NoSuchMethodError
```

Therefore troubleshooting should ask:

```text
What was compile classpath?
What is runtime classpath?
Which version is actually loaded?
```

---

# SECTION 165 — SOURCE COMPATIBILITY

## 174. Concept

Build tools can configure which Java language level/source version is expected.

For example:

```text
Java 17 language features
```

require appropriate compiler configuration.

If compiler/source settings do not match the code:

```text
compilation error
```

can occur.

---

# SECTION 166 — MAVEN COMPILER CONNECTION

## 175. Maven + javac

Maven does not replace the Java compiler.

High-level flow:

```text
Maven lifecycle
     ↓
Compiler plugin
     ↓
Java compiler
     ↓
.class files
```

Maven orchestrates the build.

Java tools actually compile/run Java code.

---

# SECTION 167 — JAVA BUILD FLOW IN OUR BACKEND

## 176. Mental Model

```text
Backend Java source
       ↓
Maven Wrapper
       ↓
Java 17 JDK
       ↓
Compile
       ↓
Tests
       ↓
Spring Boot packaging
       ↓
JAR
       ↓
JVM
       ↓
Backend running on port 8080
```

This is the full story behind a simple command such as:

```bash
./mvnw spring-boot:run
```

---

# SECTION 168 — JAVA TEST FLOW IN API AUTOMATION

## 177. Mental Model

```text
REST Assured/TestNG Java source
          ↓
Maven Wrapper
          ↓
JDK
          ↓
Compile test classes
          ↓
Surefire/TestNG
          ↓
JVM executes tests
          ↓
HTTP requests
          ↓
Backend
          ↓
Results / Allure
```

---

# SECTION 169 — COMMON ERROR: JAVA COMMAND NOT FOUND

## 178. Symptom

```text
java: command not found
```

Possible reasons:

```text
JDK not installed
PATH does not contain Java bin
shell configuration not loaded
CI setup step missing
```

Check:

```bash
which java
echo "$PATH"
```

---

# SECTION 170 — COMMON ERROR: JAVA_HOME INVALID

## 179. Symptom

A build tool may report that:

```text
JAVA_HOME is not defined correctly
```

Check:

```bash
echo "$JAVA_HOME"
ls "$JAVA_HOME"
```

Then:

```bash
"$JAVA_HOME/bin/java" -version
```

if the path exists.

---

# SECTION 171 — COMMON ERROR: MAVEN USES WRONG JAVA

## 180. Troubleshooting

Run:

```bash
java -version
```

then:

```bash
./mvnw -version
```

Compare output.

The most relevant Java version for Maven build troubleshooting is the one Maven reports it is using.

---

# SECTION 172 — COMMON ERROR: CANNOT FIND SYMBOL

## 181. Meaning

Compiler cannot resolve a referenced symbol.

Possible causes:

```text
typo
missing import
method renamed
class renamed
dependency missing
generated source unavailable
wrong branch/code version
```

Read:

```text
first compiler error
file
line number
symbol
location
```

---

# SECTION 173 — COMMON ERROR: PACKAGE DOES NOT EXIST

## 182. Possible Reasons

```text
dependency unavailable
incorrect import
source path problem
module/build configuration
wrong version
```

Check:

```bash
./mvnw dependency:tree
```

if external dependencies are involved.

---

# SECTION 174 — COMMON ERROR: ClassNotFoundException IN CI ONLY

## 183. Check

```text
CI dependency resolution
packaging
test scope
runtime classpath
different Maven profile/config
different artifact
cache
Java version
```

Do not assume test code itself is automatically wrong.

---

# SECTION 175 — COMMON ERROR: NoSuchMethodError

## 184. Strong Troubleshooting Flow

```text
Read class + method name
       ↓
Identify owning dependency
       ↓
Run dependency tree
       ↓
Look for conflicting versions
       ↓
Check runtime environment
       ↓
Align compatible dependencies
```

Useful:

```bash
./mvnw dependency:tree
```

---

# SECTION 176 — LOCAL VS CI JAVA TROUBLESHOOTING

## 185. Compare

Local:

```bash
java -version
javac -version
./mvnw -version
```

CI should expose equivalent non-secret diagnostics.

Compare:

```text
OS
architecture
Java
Maven
working directory
environment
```

---

# SECTION 177 — APPLE SILICON / ARCHITECTURE

## 186. Why Architecture Matters

Different machines may use:

```text
arm64 / aarch64
x86_64
```

Most pure Java bytecode is portable across architectures when a compatible JVM exists.

But architecture matters when using:

```text
native libraries
browser binaries
Docker images
JNI
external tools
```

Check:

```bash
uname -m
```

---

# SECTION 178 — JAVA AND NATIVE CODE

## 187. Important Exception

Java applications can interact with native code through mechanisms such as JNI.

Then compatibility can depend on:

```text
OS
CPU architecture
native library
JVM
```

So Java portability has practical limits when native dependencies are involved.

---

# SECTION 179 — JAR VS WAR

## 188. Basic Difference

### JAR

```text
Java archive
```

Often used for:

```text
libraries
standalone applications
Spring Boot executable applications
```

### WAR

```text
Web Application Archive
```

Historically common for deploying Java web applications into external servlet/application containers.

For our project:

```text
Spring Boot executable JAR
```

is the relevant model.

---

# SECTION 180 — WHY SPRING BOOT JAR IS USEFUL

## 189. Benefits

Conceptually:

```text
Application
+
embedded server/runtime support
+
dependencies packaging structure
```

makes deployment simpler.

Instead of:

```text
install external application server
deploy WAR manually
```

we can often run:

```bash
java -jar application.jar
```

---

# SECTION 181 — JAVA VERSION NAMING

## 190. LTS Concept

Some Java releases are designated Long-Term Support releases by major vendors.

Java 17 is widely used as an LTS baseline in enterprise applications.

For our project:

```text
Java 17
```

is the chosen development/runtime baseline.

---

# SECTION 182 — SHOULD WE ALWAYS USE LATEST JAVA?

## 191. No

Enterprise projects choose Java versions based on:

```text
framework support
organization standard
LTS strategy
dependency compatibility
runtime support
cloud/container environment
upgrade planning
```

Using the newest version is not automatically the best engineering decision.

---

# SECTION 183 — JAVA VERSION UPGRADE TESTING

## 192. As an SDET

When Java runtime changes, validate:

```text
build
unit tests
API automation
integration tests
application startup
dependency compatibility
performance-sensitive areas
CI pipeline
Docker/runtime image
```

A runtime upgrade can affect more than compilation.

---

# SECTION 184 — BUILD VS RUN

## 193. Important Difference

Build:

```text
compile
test
package
```

Run:

```text
start and execute application
```

Example:

```bash
./mvnw clean package
```

builds/package.

```bash
java -jar target/<jar>.jar
```

runs packaged application.

---

# SECTION 185 — BUILD ARTIFACT

## 194. What Is an Artifact?

A build artifact is an output produced by the build.

Examples:

```text
JAR
WAR
test report
compiled package
Docker image
```

In Maven context, the primary project artifact may be:

```text
JAR
```

---

# SECTION 186 — JAVA COMPILATION TROUBLESHOOTING CHECKLIST

## 195.

If compilation fails:

```text
1. Read first meaningful compiler error
2. Check file + line
3. Check syntax/types
4. Check imports
5. Check dependency availability
6. Check Java version
7. Check Maven compiler/build configuration
8. Re-run focused build
```

---

# SECTION 187 — JAVA RUNTIME TROUBLESHOOTING CHECKLIST

## 196.

If application compiles but fails at runtime:

```text
1. Read exception type
2. Read root cause
3. Check runtime Java version
4. Check environment variables
5. Check dependency/classpath issues
6. Check DB/network/external service
7. Check logs
8. Check recent changes
```

---

# SECTION 188 — EXCEPTION VS ERROR

## 197. High-Level Difference

In Java:

```text
Exception
→ condition application may potentially handle

Error
→ serious JVM/system-level condition generally not intended for normal application recovery
```

Examples:

```text
NullPointerException
→ Exception hierarchy

OutOfMemoryError
→ Error hierarchy
```

Do not interpret every class ending in `Error` as a product defect without context.

---

# SECTION 189 — CHECKED VS UNCHECKED EXCEPTIONS

## 198. Checked Exception

Compiler requires handling or declaration.

Example concept:

```text
IOException
```

---

## 199. Unchecked Exception

Runtime exceptions do not require mandatory compile-time handling.

Example:

```text
NullPointerException
IllegalArgumentException
```

For SDET interviews, know the distinction at a high level.

---

# SECTION 190 — STACK TRACE

## 200. What Is a Stack Trace?

A stack trace shows the call path leading to an exception/error.

Example conceptual flow:

```text
Controller
 ↓
Service
 ↓
Repository
 ↓
Database call
 ↓
Exception
```

A strong tester reads:

```text
exception type
message
root cause
first relevant application frame
caused by chain
```

Do not only copy the last line.

---

# SECTION 191 — "CAUSED BY"

## 201. Why It Matters

Java exceptions can wrap other exceptions.

Example:

```text
High-level framework exception
    ↓
Caused by
    ↓
database exception
```

The root cause may be deeper in the stack trace.

---

# SECTION 192 — LOGGING JAVA EXCEPTIONS

## 202. SDET Approach

When API returns 500:

```text
response
+
backend logs
+
stack trace
+
request data
```

should be correlated.

Do not infer root cause from HTTP status alone.

---

# SECTION 193 — JVM PROCESS AND PORT DEBUGGING

## 203. Example Flow

Application expected on:

```text
8080
```

Check:

```bash
pgrep -fl java
```

then:

```bash
lsof -i :8080
```

then:

```bash
curl -i http://localhost:8080/v3/api-docs
```

This checks three different layers:

```text
process
port
HTTP
```

---

# SECTION 194 — STRONG TROUBLESHOOTING MENTAL MODEL

## 204.

```text
Can Java run?
   ↓
Can Maven build?
   ↓
Can application start?
   ↓
Is port listening?
   ↓
Can HTTP endpoint respond?
   ↓
Does authentication work?
   ↓
Does DB integration work?
```

This is much stronger than:

```text
test failed → rerun
```

---

# SECTION 195 — INTERVIEW: JDK VS JVM

## 205. Strong Answer

> "The JDK is the development kit containing tools required to build Java applications, such as the compiler, while the JVM is the runtime engine that executes compiled Java bytecode. In a Maven-based automation or Spring Boot project, the JDK is required for compiling the code, and the JVM executes the resulting classes and tests."

---

# SECTION 196 — INTERVIEW: JAVA_HOME VS PATH

## 206. Strong Answer

> "`JAVA_HOME` points to the Java installation, while `PATH` controls where the shell searches for executable commands. During build troubleshooting I check both, and I also verify `mvnw -version` because that tells me which Java Maven is actually using."

---

# SECTION 197 — INTERVIEW: WHY JAVA PORTABLE?

## 207. Strong Answer

> "Java source is compiled into bytecode that can be executed by a compatible JVM on different operating systems. However, practical portability can still be affected by Java versions, native libraries, filesystem behavior and environment-specific dependencies."

---

# SECTION 198 — INTERVIEW: JAR

## 208. Strong Answer

> "A JAR is a Java archive containing compiled classes, resources and metadata. In our Spring Boot backend, Maven and the Spring Boot plugin can package the application into an executable JAR that can be started using `java -jar`."

---

# SECTION 199 — INTERVIEW: CLASSPATH

## 209. Strong Answer

> "Classpath tells the Java compiler or runtime where required classes and libraries are available. Maven handles most classpath construction automatically based on declared dependencies and their scopes, which is why dependency configuration directly affects compilation and runtime behavior."

---

# SECTION 200 — INTERVIEW: UnsupportedClassVersionError

## 210. Strong Answer

> "It usually means the class was compiled using a newer Java class-file version than the runtime JVM supports. I would compare the build JDK and runtime JDK versions, including the Java version reported by Maven or the CI runner."

---

# SECTION 201 — INTERVIEW: NoSuchMethodError

## 211. Strong Answer

> "A `NoSuchMethodError` often points to binary incompatibility where the code was compiled against one library version but a different incompatible version is loaded at runtime. I would inspect the dependency tree and runtime classpath before changing application code."

---

# SECTION 202 — INTERVIEW: ClassNotFoundException

## 212. Strong Answer

> "It indicates that a class requested during runtime could not be found. I would inspect dependency availability, runtime classpath and packaging, and confirm that the expected dependency exists in the environment where the failure occurs."

---

# SECTION 203 — INTERVIEW: COMPILE VS RUNTIME

## 213. Strong Answer

> "Compile-time problems prevent source code from successfully becoming bytecode, while runtime failures happen after compilation when the application or tests execute. I use the error type to determine whether to investigate source/build configuration or runtime dependencies, data and infrastructure."

---

# SECTION 204 — INTERVIEW: JAVA PROCESS TROUBLESHOOTING

## 214. Strong Answer

> "If a Java service is reported as unavailable, I verify the layers independently. I check whether the Java process exists, whether the expected port is listening and whether the HTTP endpoint responds. This helps distinguish process failure, network/listener issues and application-level HTTP failures."

---

# SECTION 205 — RAPID-FIRE QUESTIONS

## 215. What compiles Java source?

```text
javac
```

## 216. What executes Java bytecode?

```text
JVM
```

## 217. Source extension?

```text
.java
```

## 218. Compiled bytecode extension?

```text
.class
```

## 219. Java archive?

```text
JAR
```

## 220. Run executable JAR?

```bash
java -jar <file>.jar
```

## 221. Check Java version?

```bash
java -version
```

## 222. Check compiler version?

```bash
javac -version
```

## 223. Check Maven's Java?

```bash
./mvnw -version
```

## 224. Show Java home?

```bash
echo "$JAVA_HOME"
```

## 225. Locate Java executable?

```bash
which java
```

## 226. Newer class on older JVM error?

```text
UnsupportedClassVersionError
```

## 227. Runtime library version mismatch may cause?

```text
NoSuchMethodError
```

## 228. Memory exhausted?

```text
OutOfMemoryError
```

## 229. Excessive recursion may cause?

```text
StackOverflowError
```

---

# SECTION 206 — COMMAND INTERPRETATION

## 230.

```bash
java -version
```

Means:

> Show information about the Java runtime resolved for the current shell.

---

## 231.

```bash
javac -version
```

Means:

> Show the Java compiler version.

---

## 232.

```bash
echo "$JAVA_HOME"
```

Means:

> Display the current `JAVA_HOME` environment variable.

---

## 233.

```bash
which java
```

Means:

> Show which Java executable will be resolved from PATH.

---

## 234.

```bash
./mvnw -version
```

Means:

> Show Maven Wrapper/Maven runtime details including which Java environment Maven is using.

---

## 235.

```bash
java -jar application.jar
```

Means:

> Start an executable Java archive using the JVM.

---

## 236.

```bash
jar tf application.jar
```

Means:

> List the contents of a JAR archive.

---

# SECTION 207 — JAVA + MAVEN + SPRING BOOT COMPLETE CONNECTION

## 237.

```text
Java Source
    ↓
pom.xml describes project/dependencies
    ↓
Maven Wrapper starts Maven
    ↓
Maven uses JDK
    ↓
Java compiler creates bytecode
    ↓
Tests execute in JVM
    ↓
Spring Boot plugin packages/runs app
    ↓
Application starts
    ↓
JVM process listens on port
    ↓
REST APIs become available
```

This is the complete engineering flow.

---

# SECTION 208 — OUR PROJECT EXAMPLE

## 238. Backend

```text
BackendApplication.java
       ↓
Java 17
       ↓
Maven
       ↓
Spring Boot
       ↓
JVM
       ↓
Backend Service
       ↓
Port 8080
```

---

## 239. API Automation

```text
Test Classes
     ↓
Java 17
     ↓
Maven
     ↓
Surefire
     ↓
TestNG
     ↓
JVM
     ↓
REST Assured
     ↓
Backend APIs
```

---

# SECTION 209 — COMMON INTERVIEW TRAPS

## 240. Maven Is Not Java Compiler

Incorrect:

```text
Maven compiles Java itself.
```

Better:

```text
Maven orchestrates compilation through build plugins and the Java compiler/toolchain.
```

---

## 241. JVM Is Not JDK

Incorrect:

```text
JVM contains all development tools.
```

Correct:

```text
JDK provides development tools.
JVM executes bytecode.
```

---

## 242. Import Does Not Download Dependency

Incorrect:

```text
import RestAssured downloads REST Assured.
```

Correct:

```text
Maven dependency makes the library available.
Java import references its type in source code.
```

---

## 243. Build Success Does Not Mean App Works

```text
BUILD SUCCESS
```

does not prove:

```text
DB is reachable
API is correct
deployment is healthy
business logic has no defects
```

---

## 244. Java Is Not Completely Environment Independent

Bytecode portability is strong, but runtime behavior can still depend on:

```text
JDK/JVM version
OS
architecture
environment variables
network
filesystem
native code
```

---

# SECTION 210 — SENIOR SDET SCENARIO

## 245. CI Shows Java Error But Local Is Fine

My approach:

```text
1. Capture local Java/Maven versions
2. Capture CI Java/Maven versions
3. Compare JAVA_HOME/PATH setup
4. Check project Java requirement
5. Check Maven compiler configuration
6. Check dependency/plugin compatibility
7. Reproduce using matching runtime where possible
8. Fix environment or compatibility root cause
```

Interview answer:

> "I would first prove whether the local and CI Java environments are equivalent. I would compare the runtime and compiler versions, Maven's Java version and build configuration. Only after ruling out environment mismatch would I treat it as a source-code problem."

---

# SECTION 211 — SENIOR SDET SCENARIO

## 246. Application Compiles But Crashes on Startup

Possible areas:

```text
runtime dependency
environment variables
Spring configuration
database
port conflict
Java runtime version
class loading
```

Flow:

```text
Read exception
↓
Read caused-by chain
↓
Check JVM/version
↓
Check env
↓
Check port
↓
Check DB/dependencies
```

---

# SECTION 212 — SENIOR SDET SCENARIO

## 247. `NoSuchMethodError` Appears After Dependency Upgrade

Do:

```bash
./mvnw dependency:tree
```

Then inspect:

```text
duplicate versions
transitive dependencies
framework compatibility
dependency management
```

Do NOT fix it by randomly adding multiple library versions.

---

# SECTION 213 — SENIOR SDET SCENARIO

## 248. App Does Not Respond on Port 8080

Check progressively:

```bash
pgrep -fl java
```

Then:

```bash
lsof -i :8080
```

Then:

```bash
curl -i http://localhost:8080/v3/api-docs
```

Interpretation:

```text
No Java process
→ application probably did not start

Java process but no 8080 listener
→ startup/config/port issue

8080 listening but curl fails HTTP-level
→ inspect application/API behavior
```

---

# SECTION 214 — 5-MINUTE JAVA TOOLING REVISION

## 249. Memorize

```text
JDK
→ development kit

JVM
→ executes bytecode

javac
→ compiler

java
→ runtime command

.java
→ source

.class
→ bytecode

JAR
→ Java archive

JAVA_HOME
→ Java installation

PATH
→ executable lookup

Classpath
→ class/library lookup

Maven
→ build orchestration + dependencies
```

---

# SECTION 215 — ERROR MEMORY MAP

## 250.

```text
Cannot Find Symbol
→ compile/source/dependency problem

UnsupportedClassVersionError
→ Java version incompatibility

ClassNotFoundException
→ runtime class unavailable

NoClassDefFoundError
→ expected class unavailable/unusable

NoSuchMethodError
→ dependency/binary version mismatch

NullPointerException
→ null runtime reference

StackOverflowError
→ often excessive recursion

OutOfMemoryError
→ JVM cannot satisfy memory allocation
```

---

# SECTION 216 — TOOLING COMMAND CHEAT SHEET

## 251.

```bash
java -version
```

```bash
javac -version
```

```bash
echo "$JAVA_HOME"
```

```bash
which java
```

```bash
./mvnw -version
```

```bash
./mvnw dependency:tree
```

```bash
pgrep -fl java
```

```bash
lsof -i :8080
```

```bash
java -jar <application>.jar
```

```bash
jar tf <application>.jar
```

---

# SECTION 217 — ONE-MINUTE INTERVIEW ANSWER

## 252.

> "In my Java-based SDET work, I understand the full build and runtime flow rather than only writing test code. Java source is compiled by the JDK into bytecode, which runs on the JVM. Maven orchestrates dependency resolution, compilation and test execution, and I use the Maven Wrapper for consistent builds. I also understand `JAVA_HOME`, PATH, classpath, JAR packaging and common runtime compatibility issues such as `UnsupportedClassVersionError`, `ClassNotFoundException` and dependency-related `NoSuchMethodError`. This helps me troubleshoot both local and CI failures systematically."

---

# SECTION 218 — FINAL MENTAL MODEL

## 253.

```text
SOURCE CODE
    ↓
.java
    ↓
JDK / javac
    ↓
.class BYTECODE
    ↓
Maven packages application
    ↓
JAR
    ↓
JVM
    ↓
JAVA PROCESS
    ↓
APPLICATION
    ↓
PORT
    ↓
HTTP/API
```

For automation:

```text
TEST SOURCE
    ↓
JDK
    ↓
MAVEN
    ↓
TESTNG
    ↓
JVM
    ↓
REST ASSURED
    ↓
HTTP REQUEST
    ↓
BACKEND
    ↓
DATABASE
    ↓
ASSERTIONS
    ↓
ALLURE
```

---

# END OF PART 2 — JDK, JRE, JVM & JAVA BUILD FUNDAMENTALS

Next:

**PART 3 — SPRING BOOT BUILD, STARTUP, CONFIGURATION & RUNTIME FOR SDET**

---

# PART 3 — SPRING BOOT BUILD, STARTUP, CONFIGURATION & RUNTIME FOR SDET

# SECTION 219 — WHY SPRING BOOT MATTERS FOR SDET

As an SDET, you do not need to become a full backend developer.

But you should understand enough Spring Boot to answer:

```text
How does the application start?
Where does configuration come from?
Why is an endpoint returning 404/401/403/500?
How does Controller connect to Repository?
Why does DB connection failure stop startup?
What does spring-boot:run actually do?
What is happening when the backend listens on port 8080?
```

This helps in:

```text
API testing
integration testing
backend debugging
CI troubleshooting
environment debugging
defect analysis
release validation
```

---

# SECTION 220 — WHAT IS SPRING?

## 254. Spring Framework

Spring is a Java framework/ecosystem used to build applications.

It provides capabilities such as:

```text
dependency injection
web applications
data access
security
configuration
transaction management
```

Spring itself is large and flexible.

---

# SECTION 221 — WHAT IS SPRING BOOT?

## 255. Definition

Spring Boot is built on top of the Spring ecosystem and simplifies creating and running Spring applications.

It reduces manual setup by providing:

```text
auto-configuration
starter dependencies
embedded server support
externalized configuration
production-oriented defaults
```

Simple mental model:

```text
Spring
→ framework ecosystem

Spring Boot
→ easier way to configure, package and run Spring applications
```

---

# SECTION 222 — SPRING VS SPRING BOOT

## 256. Interview Difference

```text
Spring Framework
→ core framework and ecosystem

Spring Boot
→ opinionated setup on top of Spring
   that reduces configuration and simplifies startup
```

Do not say:

```text
Spring Boot replaces Spring
```

It uses Spring underneath.

---

# SECTION 223 — WHY SPRING BOOT IS POPULAR

## 257.

Without modern bootstrapping, developers may need significant manual configuration.

Spring Boot provides:

```text
starter dependencies
auto-configuration
embedded web server
convention over configuration
simple executable application model
```

This makes it easier to create APIs and services.

---

# SECTION 224 — OUR BACKEND

## 258. Current Backend

Our project backend is:

```text
Java 17
+
Spring Boot
+
REST APIs
+
Spring Security
+
JPA
+
PostgreSQL
+
JWT
+
Swagger/OpenAPI
```

High-level architecture:

```text
HTTP Request
    ↓
Security Layer
    ↓
Controller
    ↓
Business/Data Layer
    ↓
Repository
    ↓
PostgreSQL
```

---

# SECTION 225 — @SpringBootApplication

## 259. Main Annotation

A Spring Boot application commonly starts with:

```java
@SpringBootApplication
```

on the main application class.

Our project has:

```text
BackendApplication.java
```

Conceptually:

```java
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
```

---

# SECTION 226 — WHAT DOES @SpringBootApplication REPRESENT?

## 260. High-Level Meaning

At a high level, it enables important Spring Boot behavior including:

```text
configuration
auto-configuration
component scanning
```

You do not need to memorize every internal annotation for an SDET interview.

Understand:

```text
This is the main Spring Boot application entry point.
```

---

# SECTION 227 — APPLICATION STARTUP FLOW

## 261. Complete Mental Model

```text
java / Maven
    ↓
main()
    ↓
SpringApplication.run(...)
    ↓
Spring Boot initializes
    ↓
configuration loaded
    ↓
application context created
    ↓
beans created
    ↓
security/data/web configuration initialized
    ↓
embedded server starts
    ↓
port opens
    ↓
application ready
```

If one critical startup dependency fails:

```text
application may fail before becoming ready
```

---

# SECTION 228 — WHAT IS APPLICATION CONTEXT?

## 262. Simple Definition

Spring's Application Context is the container that manages application objects called:

```text
beans
```

Think:

```text
Application Context
│
├── Controller objects
├── Service objects
├── Repository objects
├── Security components
└── Configuration components
```

Spring creates and connects these objects.

---

# SECTION 229 — WHAT IS A BEAN?

## 263. Simple Definition

A bean is an object managed by the Spring container.

Instead of application code manually creating every dependency:

```java
new Something()
```

Spring can create and manage those objects.

This is central to dependency injection.

---

# SECTION 230 — DEPENDENCY INJECTION

## 264. What Problem Does It Solve?

Suppose:

```text
Controller needs Service
Service needs Repository
```

Without dependency injection, each class might manually create its dependencies.

With Spring:

```text
Spring creates objects
       ↓
Spring connects dependencies
       ↓
application uses them
```

This is called:

```text
Dependency Injection
```

---

# SECTION 231 — WHY SDET SHOULD UNDERSTAND DI

## 265.

When startup errors say things like:

```text
bean could not be created
unsatisfied dependency
failed to autowire
```

you should understand that Spring could not create or connect required application components.

This is often:

```text
application/configuration problem
```

not:

```text
automation issue
```

---

# SECTION 232 — COMMON SPRING COMPONENTS

## 266.

Common annotations/classes you may see:

```text
@RestController
@Controller
@Service
@Repository
@Component
@Configuration
@Bean
```

For SDET purposes:

```text
Controller
→ HTTP layer

Service
→ business logic layer

Repository
→ data-access layer

Configuration
→ application setup
```

---

# SECTION 233 — CONTROLLER

## 267. Controller Responsibility

A controller receives HTTP requests.

Example concept:

```text
POST /users/register
        ↓
Controller
```

Controller may:

```text
read request
validate input
call service/repository
return HTTP response
```

---

# SECTION 234 — REST CONTROLLER

## 268. @RestController

For REST APIs, we commonly see:

```java
@RestController
```

It indicates a controller whose methods return data for HTTP responses.

Typical output:

```text
JSON
```

---

# SECTION 235 — REQUEST MAPPING

## 269. Endpoint Mapping

Examples:

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

These map HTTP methods to Java methods.

Concept:

```text
GET
→ @GetMapping

POST
→ @PostMapping

PUT
→ @PutMapping

DELETE
→ @DeleteMapping
```

---

# SECTION 236 — REQUEST PATH

## 270. Example

Conceptual controller:

```java
@RequestMapping("/products")
```

and:

```java
@GetMapping
```

may represent:

```text
GET /products
```

Another:

```java
@GetMapping("/{id}")
```

may represent:

```text
GET /products/{id}
```

---

# SECTION 237 — PATH VARIABLE

## 271.

Example URL:

```text
/products/10
```

Here:

```text
10
```

can be captured as a path variable.

Conceptual annotation:

```java
@PathVariable
```

---

# SECTION 238 — QUERY PARAMETER

## 272.

Example:

```text
/products/search?name=phone
```

`name=phone` is a query parameter.

Spring can read it using concepts such as:

```java
@RequestParam
```

---

# SECTION 239 — REQUEST BODY

## 273.

For JSON requests:

```json
{
  "name": "Sample Product",
  "price": 100
}
```

Spring can map the request body to a Java object.

Common annotation:

```java
@RequestBody
```

---

# SECTION 240 — JSON TO JAVA OBJECT

## 274. Deserialization

Flow:

```text
JSON Request
     ↓
Spring/Jackson
     ↓
Java Object
```

This process is commonly called:

```text
deserialization
```

Reverse:

```text
Java Object
     ↓
JSON Response
```

is:

```text
serialization
```

---

# SECTION 241 — WHY DESERIALIZATION FAILURES MATTER

## 275.

If client sends incorrect JSON:

```text
wrong data type
malformed JSON
invalid field structure
```

request may fail before normal business logic executes.

As a tester, distinguish:

```text
request parsing failure
validation failure
business-rule failure
```

---

# SECTION 242 — VALIDATION

## 276. Request Validation

Java/Spring applications can validate request objects.

Conceptual annotations:

```text
@NotNull
@NotBlank
@Email
@Size
@Min
```

Then malformed business input can return:

```text
4xx response
```

depending on application error handling.

---

# SECTION 243 — VALIDATION VS BUSINESS RULE

## 277.

Example:

```text
email blank
→ field validation

user already exists
→ business rule

product stock insufficient
→ business rule
```

Testing should cover both.

---

# SECTION 244 — SERVICE LAYER

## 278. What Is a Service?

A service layer commonly contains business logic.

Flow:

```text
Controller
   ↓
Service
   ↓
Repository
```

Example:

```text
Create Order
   ↓
validate cart
   ↓
check stock
   ↓
calculate state
   ↓
save order
```

Exact architecture varies by project.

---

# SECTION 245 — REPOSITORY LAYER

## 279. What Is Repository?

Repository is commonly used for data access.

In Spring Data JPA:

```text
Repository
    ↓
JPA/Hibernate
    ↓
Database
```

Instead of manually writing SQL for every operation, Spring Data can provide repository abstractions.

---

# SECTION 246 — JPA

## 280. What Is JPA?

JPA means:

```text
Jakarta Persistence API
```

It is a specification/API for object-relational persistence in Java.

Important:

```text
JPA
≠ database
```

and:

```text
JPA
≠ Hibernate exactly
```

Hibernate is a common JPA implementation.

---

# SECTION 247 — HIBERNATE

## 281. Simple Definition

Hibernate is an ORM framework and common JPA implementation.

ORM means:

```text
Object Relational Mapping
```

Concept:

```text
Java Object
    ↕
Database Row
```

---

# SECTION 248 — ENTITY

## 282. What Is an Entity?

An entity represents persistable domain data.

Example concept:

```text
User Java object
      ↕
users table row
```

Common annotation:

```java
@Entity
```

---

# SECTION 249 — OUR USER ENTITY

## 283. Concept

Our project has a User entity with fields such as:

```text
id
name
email
password
role
```

At a high level:

```text
User entity
    ↓
JPA/Hibernate
    ↓
users table
```

---

# SECTION 250 — REPOSITORY QUERY

## 284.

Repository operations may conceptually provide:

```text
save
findById
findAll
delete
custom find methods
```

The exact SQL may be generated by the persistence framework.

This is why API testing + DB validation can complement each other.

---

# SECTION 251 — API TO DB FLOW

## 285. Complete Example

```text
POST /users/register
        ↓
Security allows public endpoint
        ↓
Controller
        ↓
Validation
        ↓
Business logic
        ↓
Repository
        ↓
JPA/Hibernate
        ↓
PostgreSQL
        ↓
response returned
```

As SDET, a failure can happen at ANY layer.

---

# SECTION 252 — SPRING SECURITY

## 286. Role in Our Application

Spring Security protects backend endpoints.

High-level flow:

```text
HTTP Request
     ↓
Security Filter Chain
     ↓
JWT Validation
     ↓
Authentication
     ↓
Authorization
     ↓
Controller
```

---

# SECTION 253 — AUTHENTICATION

## 287.

Authentication asks:

```text
Who are you?
```

For our project:

```text
JWT
```

is used after login.

If authentication fails:

```text
401
```

is expected in our configured security behavior.

---

# SECTION 254 — AUTHORIZATION

## 288.

Authorization asks:

```text
What are you allowed to do?
```

Example:

```text
ROLE_USER
→ read product

ROLE_ADMIN
→ create/update/delete product
```

When authenticated USER attempts an admin operation:

```text
403
```

is expected.

---

# SECTION 255 — SECURITY FILTER

## 289. JWT Filter Concept

Our project has a JWT authentication filter.

High-level:

```text
Request
   ↓
Read Authorization header
   ↓
Extract Bearer token
   ↓
Validate JWT
   ↓
Extract user/role
   ↓
Create authentication
   ↓
Continue filter chain
```

You do not need to know every Spring Security internal class to understand this flow.

---

# SECTION 256 — WHY CONTROLLER MAY NEVER EXECUTE

## 290.

Suppose request is:

```text
POST /products
```

with:

```text
ROLE_USER
```

Security may reject it before controller logic runs.

Flow:

```text
Request
  ↓
Security
  ↓
403
```

Controller:

```text
not reached
```

This is useful while debugging.

---

# SECTION 257 — APPLICATION.PROPERTIES

## 291. Purpose

Spring Boot configuration can be stored in:

```text
application.properties
```

or YAML equivalents.

Our project currently uses:

```text
application.properties
```

---

# SECTION 258 — OUR APPLICATION CONFIGURATION

## 292. Conceptual Configuration

Our backend config includes areas such as:

```text
application name
database URL
database username
database password
JPA settings
JWT configuration
```

Sensitive values are externalized using environment variables rather than committed secrets.

---

# SECTION 259 — PROPERTY PLACEHOLDER

## 293. Example Pattern

Spring supports patterns such as:

```properties
some.property=${ENV_VARIABLE}
```

or:

```properties
some.property=${ENV_VARIABLE:defaultValue}
```

Meaning:

```text
read environment value
or
use default if configured
```

---

# SECTION 260 — DEFAULT VALUE EXAMPLE

## 294.

Conceptually:

```properties
server.port=${SERVER_PORT:8080}
```

means:

```text
If SERVER_PORT exists
→ use it

Otherwise
→ use 8080
```

This makes applications easier to configure across environments.

---

# SECTION 261 — EXTERNALIZED CONFIGURATION

## 295. Why It Matters

Configuration should vary without modifying source code.

Examples:

```text
DB URL
environment
server port
JWT settings
external service URL
```

Concept:

```text
Same Application Artifact
        ↓
Different Runtime Configuration
        ↓
LOCAL / QA / STAGE / PROD
```

---

# SECTION 262 — WHY SECRETS SHOULD NOT BE IN application.properties

## 296.

Hardcoded secrets can:

```text
enter Git
appear in code reviews
remain in history
become difficult to rotate
```

Better:

```text
environment variables
CI secrets
approved secret manager
```

---

# SECTION 263 — OUR .env + SCRIPT FLOW

## 297.

Our local backend uses:

```text
.env
```

for local runtime configuration.

The local runner:

```text
loads .env
     ↓
exports variables
     ↓
runs Maven Wrapper
     ↓
starts Spring Boot
```

The real `.env` is not committed.

---

# SECTION 264 — IMPORTANT: SPRING BOOT DOES NOT AUTOMATICALLY MEAN .env

## 298.

A `.env` file is not inherently a standard Spring Boot configuration source by itself.

In our setup:

```text
shell script
→ loads .env into environment
→ Spring Boot reads environment variables
```

This distinction is important.

---

# SECTION 265 — SPRING PROFILES

## 299. What Is a Profile?

Spring profiles allow configuration/components to vary by environment or scenario.

Concept:

```text
application-local.properties
application-qa.properties
application-prod.properties
```

and:

```text
active profile
→ determines profile-specific configuration
```

---

# SECTION 266 — DO WE NEED SPRING PROFILES NOW?

## 300.

Not automatically.

Our current project already externalizes important runtime configuration through environment variables.

Do not add profiles merely to make architecture look advanced.

Use them when they solve a real configuration problem.

---

# SECTION 267 — CONFIGURATION PRECEDENCE

## 301. High-Level Concept

Spring Boot can receive configuration from multiple sources such as:

```text
properties files
environment variables
system properties
command-line arguments
profile-specific configuration
```

When the same property appears in multiple places, precedence rules determine the effective value.

You do not need to memorize the entire precedence list for normal SDET interviews.

Important troubleshooting question:

```text
Which value is actually active at runtime?
```

---

# SECTION 268 — COMMAND-LINE PROPERTY OVERRIDE

## 302. Concept

A Spring Boot property can sometimes be supplied through command-line arguments.

Example concept:

```bash
java -jar app.jar --server.port=9090
```

Then runtime may start on:

```text
9090
```

instead of configured default.

This is useful to understand when local and CI behavior differs.

---

# SECTION 269 — SERVER PORT

## 303.

Spring Boot web application listens on a configured port.

Default commonly:

```text
8080
```

Our backend uses:

```text
8080
```

in local development.

Check:

```bash
lsof -i :8080
```

---

# SECTION 270 — EMBEDDED SERVER

## 304. What Is It?

Spring Boot web applications commonly run with an embedded servlet server.

Meaning:

```text
application
+
web server runtime
```

can be started together.

You do not necessarily need to manually deploy into a separate external server.

---

# SECTION 271 — COMMON EMBEDDED SERVER

## 305.

Spring-based web applications often use embedded server technology such as:

```text
Tomcat
Jetty
Undertow
```

depending on stack and configuration.

Do not assume a specific one unless the project configuration confirms it.

---

# SECTION 272 — WHEN IS BACKEND ACTUALLY READY?

## 306.

Maven process starting is NOT enough.

Application becomes usable only after startup succeeds.

Need evidence such as:

```text
startup logs indicate ready
port listening
HTTP endpoint responds
```

Example:

```bash
curl -i http://localhost:8080/v3/api-docs
```

---

# SECTION 273 — STARTING OUR BACKEND

## 307.

From project root:

```bash
./backend/run-local.sh
```

Conceptually:

```text
shell script
     ↓
load runtime env
     ↓
Maven Wrapper
     ↓
spring-boot:run
     ↓
Spring Boot startup
     ↓
PostgreSQL connection
     ↓
security/JPA/web initialization
     ↓
port 8080
```

---

# SECTION 274 — spring-boot:run

## 308.

Command inside backend project:

```bash
./mvnw spring-boot:run
```

This executes:

```text
Spring Boot Maven Plugin
```

goal:

```text
run
```

Useful for development.

---

# SECTION 275 — PACKAGE AND RUN

## 309.

Another flow:

```bash
./mvnw clean package
```

then:

```bash
java -jar target/<application>.jar
```

Conceptually:

```text
Build First
    ↓
Create JAR
    ↓
Run JAR
```

This is closer to how packaged artifacts are executed.

---

# SECTION 276 — STARTUP LOGS

## 310. Why They Matter

Spring Boot startup logs can reveal:

```text
active profiles
server port
DB initialization
JPA/Hibernate
security
bean failures
configuration failures
application-ready status
```

Do not only look for the word:

```text
ERROR
```

Read the startup sequence.

---

# SECTION 277 — ROOT CAUSE IN SPRING STARTUP ERROR

## 311.

Spring errors can be long because one failed bean can cause many dependent components to fail.

Read:

```text
top-level exception
↓
Caused by
↓
Caused by
↓
deepest meaningful cause
```

The last meaningful cause may identify:

```text
missing property
DB failure
invalid configuration
dependency issue
```

---

# SECTION 278 — COMMON STARTUP FAILURE: PORT ALREADY IN USE

## 312.

Symptom:

```text
Web server failed to start
Port 8080 was already in use
```

Check:

```bash
lsof -i :8080
```

Then identify the owning process.

Do NOT automatically kill it.

First determine:

```text
Is it an old backend instance?
A different required service?
Another developer tool?
```

---

# SECTION 279 — COMMON STARTUP FAILURE: DATABASE CONNECTION

## 313.

Symptoms may mention:

```text
connection refused
authentication failure
datasource
JDBC
PostgreSQL
Hibernate
```

Check:

```bash
docker ps
```

Then:

```bash
docker compose ps
```

and container logs if needed.

Also validate:

```text
DB URL
DB user configuration
DB password availability
database port
container health
```

---

# SECTION 280 — DB CONTAINER VS SPRING APP

## 314.

Important distinction:

```text
PostgreSQL container running
≠
Spring Boot successfully connected
```

Need to verify both.

Possible flow:

```text
docker ps
     ↓
Postgres running
     ↓
Spring logs
     ↓
JDBC connection
     ↓
application ready
```

---

# SECTION 281 — COMMON STARTUP FAILURE: MISSING ENV VARIABLE

## 315.

Suppose required property is:

```properties
some.secret=${SOME_SECRET}
```

and environment variable is missing.

Spring may fail during configuration/startup.

Troubleshoot:

```bash
printenv SOME_SECRET
```

But:

```text
DO NOT print real sensitive values into shared logs/CI output
```

For secrets, safer diagnostics may verify presence without revealing value.

---

# SECTION 282 — SAFE SECRET PRESENCE CHECK

## 316. Example Concept

Instead of:

```bash
echo "$JWT_SECRET"
```

in shared output, use a presence check such as:

```bash
if [ -n "$JWT_SECRET" ]; then
  echo "JWT_SECRET is set"
else
  echo "JWT_SECRET is missing"
fi
```

This confirms configuration without exposing the secret.

---

# SECTION 283 — COMMON STARTUP FAILURE: BEAN CREATION

## 317.

Typical messages:

```text
BeanCreationException
UnsatisfiedDependencyException
```

Possible causes:

```text
dependency missing
configuration error
constructor dependency unavailable
DB setup failure
circular dependency
property binding failure
```

Do not treat:

```text
BeanCreationException
```

as the root cause automatically.

Read the nested cause.

---

# SECTION 284 — COMMON STARTUP FAILURE: APPLICATION CONTEXT

## 318.

Message may say:

```text
ApplicationContext failed to start
```

This is usually a consequence.

Find the actual underlying failure.

Mental model:

```text
Context failed
because
a component failed
because
some dependency/configuration failed
```

---

# SECTION 285 — ENDPOINT NOT FOUND: 404

## 319.

HTTP:

```text
404 Not Found
```

may indicate:

```text
wrong URL
wrong path
wrong context path
controller mapping missing
endpoint not implemented
wrong HTTP environment
```

Example:

```text
GET /
```

returning 404 does NOT prove the backend is down.

If the application has no `/` endpoint:

```text
404 can be correct
```

---

# SECTION 286 — OUR ROOT 404 EXAMPLE

## 320.

In our project:

```text
GET /
```

was not defined.

Therefore:

```text
404
```

was expected.

But an actual configured endpoint could still work.

This demonstrates:

```text
service reachable
≠ every URL exists
```

---

# SECTION 287 — 401

## 321.

```text
401 Unauthorized
```

typically means authentication is missing or invalid.

Check:

```text
Authorization header
Bearer token
token expiration
signature
environment
```

---

# SECTION 288 — 403

## 322.

```text
403 Forbidden
```

typically means:

```text
request authenticated
but user lacks required permission
```

In our project:

```text
USER POST /products
→ 403
```

This is expected RBAC behavior.

---

# SECTION 289 — 400

## 323.

```text
400 Bad Request
```

may arise from:

```text
validation
malformed request
incorrect type
missing required input
invalid business input
```

Inspect response body because application-specific error details matter.

---

# SECTION 290 — 500

## 324.

```text
500 Internal Server Error
```

means server encountered an unexpected failure while handling the request.

Troubleshoot:

```text
request
response
backend logs
stack trace
DB
downstream service
recent change
```

Never change test expectation to 500 just to pass automation.

---

# SECTION 291 — HTTP ERROR LAYER MAPPING

## 325.

Simple mental model:

```text
404
→ route/resource

400
→ client/request validation

401
→ authentication

403
→ authorization

500
→ unexpected server-side failure
```

But always use actual application contract and logs.

---

# SECTION 292 — SWAGGER / OPENAPI

## 326. Our Project

We expose OpenAPI documentation through Springdoc.

Useful local URLs include:

```text
/swagger-ui/index.html
/v3/api-docs
```

Swagger helps:

```text
discover endpoints
inspect request/response schema
manually test APIs
understand authentication requirements
```

---

# SECTION 293 — SWAGGER IS NOT THE API

## 327.

Important distinction:

```text
Swagger UI
→ documentation/testing interface

OpenAPI document
→ API contract description

Backend Controller
→ actual API implementation
```

Swagger being unavailable does not always mean every API is unavailable.

---

# SECTION 294 — JWT AUTHORIZE IN SWAGGER

## 328.

Our OpenAPI configuration defines bearer authentication.

Flow:

```text
Login
  ↓
Get JWT
  ↓
Swagger Authorize
  ↓
Token applied to secured requests
```

In our UI configuration, we provide the raw token to Swagger's bearer authorization field rather than manually prefixing it with an extra `Bearer `.

---

# SECTION 295 — SPRING DATA INITIALIZATION

## 329.

During startup, Spring Data/JPA may initialize repositories and persistence configuration.

If persistence setup fails:

```text
repository-dependent application components may fail
```

That can prevent the whole application from starting.

---

# SECTION 296 — ddl-auto

## 330. Concept

Our project uses a JPA schema behavior setting.

Common values you may encounter include:

```text
none
validate
update
create
create-drop
```

Meaning varies by selected strategy.

For example:

```text
update
```

allows Hibernate to attempt schema updates based on mappings.

---

# SECTION 297 — WHY ddl-auto MATTERS TO TESTING

## 331.

Schema behavior can affect:

```text
local startup
test data
environment safety
schema drift
database state
```

Production environments often use more controlled migration strategies rather than relying blindly on automatic schema changes.

---

# SECTION 298 — SHOW SQL

## 332.

Spring/JPA can log generated SQL.

Useful during development/debugging.

But avoid assuming:

```text
SQL logging should always be enabled everywhere
```

Because production logs can become noisy and may expose sensitive data.

---

# SECTION 299 — TRANSACTIONS

## 333. High-Level Concept

A transaction groups DB operations into a logical unit.

Concept:

```text
Operation A
Operation B
Operation C
       ↓
Commit together
```

If failure occurs:

```text
rollback
```

may restore consistency depending on transaction boundaries/configuration.

---

# SECTION 300 — WHY TRANSACTION MATTERS TO SDET

## 334.

Consider Create Order:

```text
create order
decrement stock
clear cart
```

If operation fails halfway, system consistency matters.

Testing should consider:

```text
partial update
rollback
data consistency
```

even if implementation details are handled by developers.

---

# SECTION 301 — EXCEPTION HANDLING

## 335.

Spring applications commonly convert internal exceptions into structured HTTP responses.

Concept:

```text
Internal exception
      ↓
Exception handler
      ↓
HTTP status + response body
```

This is important for API test assertions.

---

# SECTION 302 — GLOBAL EXCEPTION HANDLER

## 336.

Applications may use concepts such as:

```java
@ControllerAdvice
@ExceptionHandler
```

to centralize error responses.

Benefits:

```text
consistent status codes
consistent error schema
clean controller code
better testability
```

---

# SECTION 303 — WHY ERROR CONTRACT MATTERS

## 337.

Automation should ideally validate more than:

```text
status = 400
```

For important negative scenarios, validate relevant response structure such as:

```text
error code
message
field
timestamp
```

if those are part of the API contract.

---

# SECTION 304 — ACTUATOR

## 338. What Is Spring Boot Actuator?

Spring Boot Actuator provides operational endpoints/metrics for application monitoring and management.

Conceptual examples:

```text
health
metrics
info
```

Whether endpoints are available depends on:

```text
dependency
configuration
security
exposure settings
```

---

# SECTION 305 — WHY ACTUATOR IS USEFUL FOR SDET/CI

## 339.

A health endpoint can support:

```text
deployment validation
CI readiness checks
environment smoke checks
dependency health visibility
```

Example concept:

```text
Deploy
  ↓
Health endpoint
  ↓
Application ready
  ↓
Run regression
```

Our current project does not need Actuator merely for decoration; we can add it later when CI/deployment makes it useful.

---

# SECTION 306 — HEALTH CHECK VS FUNCTIONAL TEST

## 340.

Health check:

```text
Is service operational enough to respond?
```

Functional test:

```text
Does business behavior work correctly?
```

A green health endpoint does NOT prove:

```text
checkout works
RBAC works
order flow works
```

---

# SECTION 307 — LOG LEVELS

## 341.

Common logging levels:

```text
TRACE
DEBUG
INFO
WARN
ERROR
```

Simplified:

```text
TRACE/DEBUG
→ detailed diagnostics

INFO
→ normal significant events

WARN
→ potential issue

ERROR
→ failure
```

---

# SECTION 308 — DEBUG LOGGING CAUTION

## 342.

Increasing debug logging can help investigation.

But:

```text
more logs
→ more noise
→ possible sensitive information
→ performance/storage impact
```

Enable intentionally and temporarily where appropriate.

---

# SECTION 309 — STARTUP SUCCESS VS BUSINESS SUCCESS

## 343.

Spring Boot starts successfully:

```text
Application Ready
```

This proves:

```text
startup completed
```

It does NOT prove:

```text
all APIs work
all business rules are correct
all integrations are healthy
```

Therefore after startup:

```text
smoke validation
```

is still useful.

---

# SECTION 310 — REQUEST LIFECYCLE

## 344. Full API Request Flow

```text
Client/Test
    ↓
HTTP Request
    ↓
Embedded Server
    ↓
Spring Security
    ↓
Controller Mapping
    ↓
Request Parsing
    ↓
Validation
    ↓
Business Logic
    ↓
Repository
    ↓
Database
    ↓
Response Serialization
    ↓
HTTP Response
```

Every layer can produce a different type of failure.

---

# SECTION 311 — SDET FAILURE CLASSIFICATION

## 345.

When an API test fails, classify:

```text
Connectivity
Authentication
Authorization
Routing
Request parsing
Validation
Business logic
Persistence
Dependency
Automation
Environment
```

This prevents random debugging.

---

# SECTION 312 — SCENARIO: CONNECTION REFUSED

## 346.

If automation receives:

```text
Connection refused
```

possible issue is before Spring controller logic.

Check:

```bash
pgrep -fl java
lsof -i :8080
curl -i http://localhost:8080/v3/api-docs
```

Potential causes:

```text
backend not running
wrong port
startup failed
wrong BASE_URL
```

---

# SECTION 313 — SCENARIO: 404 AFTER SUCCESSFUL STARTUP

## 347.

Check:

```text
HTTP method
endpoint path
controller mapping
base path
environment/version
```

Do not restart server first.

---

# SECTION 314 — SCENARIO: 401 AFTER LOGIN

## 348.

Check:

```text
login actually returned token?
token parsed correctly?
Authorization header present?
token expired?
correct backend/environment?
JWT signature/configuration consistent?
```

---

# SECTION 315 — SCENARIO: USER GET WORKS BUT POST FAILS 403

## 349.

This can be valid RBAC.

Ask:

```text
Does endpoint require ADMIN?
What role is inside JWT?
What does SecurityConfig allow?
```

Do not report defect until expected authorization is understood.

---

# SECTION 316 — SCENARIO: POST RETURNS 201 BUT DB RECORD MISSING

## 350.

Investigate:

```text
correct database?
transaction committed?
response mocked?
wrong environment?
async persistence?
cleanup deleted record?
```

This is where DB validation becomes valuable.

---

# SECTION 317 — SCENARIO: DB RECORD EXISTS BUT API RESPONSE WRONG

## 351.

Possible layer:

```text
serialization
mapping
DTO conversion
controller response
business logic
```

DB validation helps isolate persistence from response logic.

---

# SECTION 318 — SCENARIO: STARTUP FAILS AFTER DATABASE PASSWORD CHANGE

## 352.

Check:

```text
environment variable updated?
shell loaded new env?
container credentials match?
DB volume retained old initialization?
application config reading correct source?
```

This is a great real-world configuration problem.

---

# SECTION 319 — DOCKER POSTGRES INITIAL CREDENTIAL NOTE

## 353.

For official PostgreSQL Docker images, initialization environment variables are used when the database data directory is first initialized.

If a persistent volume already contains database data:

```text
changing environment variables
```

does not necessarily recreate existing DB users/passwords automatically.

This is why:

```text
container recreated
```

and:

```text
database reinitialized
```

are not the same thing.

---

# SECTION 320 — SCENARIO: application.properties LOOKS CORRECT BUT VALUE IS DIFFERENT

## 354.

Possible reason:

```text
environment variable override
system property
command-line argument
profile-specific property
CI configuration
```

Question:

```text
What is the effective runtime value?
```

Do not only inspect one file.

---

# SECTION 321 — SCENARIO: BEAN CREATION ERROR AFTER CODE CHANGE

## 355.

Approach:

```text
Read deepest meaningful cause
↓
Identify bean
↓
Identify missing dependency/config
↓
Check constructor/component scanning
↓
Check repository/config initialization
↓
fix root cause
```

---

# SECTION 322 — COMPONENT SCANNING

## 356. High-Level Concept

Spring automatically discovers components in configured package areas.

Package placement matters.

If a component is outside scanned structure:

```text
Spring may not create it as a bean
```

This can cause dependency errors.

---

# SECTION 323 — WHY MAIN CLASS LOCATION MATTERS

## 357.

A common Spring Boot convention is placing the main application class in a top-level package so subpackages are scanned.

Example:

```text
com.sdetcommerce.backend
│
├── controller
├── repository
├── entity
├── security
└── ...
```

Main class:

```text
com.sdetcommerce.backend.BackendApplication
```

provides a natural package root.

---

# SECTION 324 — DTO

## 358. What Is a DTO?

DTO means:

```text
Data Transfer Object
```

Used to transfer structured data between layers/API boundaries.

Example:

```text
RegistrationRequest
LoginRequest
ProductRequest
```

Using DTOs can prevent API contracts from being tightly coupled to database entities.

---

# SECTION 325 — ENTITY VS DTO

## 359.

```text
Entity
→ persistence/domain mapping

DTO
→ data transfer/API contract
```

They can contain similar fields but solve different problems.

Important for SDET because:

```text
API field
≠ necessarily DB column one-to-one
```

---

# SECTION 326 — RESPONSE ENTITY CONCEPT

## 360.

Spring can return responses with explicit status/body control.

Conceptually:

```java
ResponseEntity
```

can represent:

```text
status code
headers
response body
```

This explains why controller behavior can intentionally return:

```text
201
400
404
etc.
```

---

# SECTION 327 — HTTP METHOD SEMANTICS

## 361.

Common conventions:

```text
GET
→ read

POST
→ create/action

PUT
→ replace/update

PATCH
→ partial update

DELETE
→ remove
```

Actual API contract is always the source of truth.

---

# SECTION 328 — HTTP STATUS SEMANTICS IN OUR PROJECT

## 362.

Examples:

```text
Successful creation
→ 201

Successful read
→ 200

Unauthorized
→ 401

Forbidden
→ 403
```

Use exact endpoint requirements for other statuses.

---

# SECTION 329 — IDE VS COMMAND LINE

## 363.

Application may work in IDE but fail from terminal.

Possible differences:

```text
environment variables
working directory
Java version
IDE run configuration
classpath
active profile
```

Always know how to run the project outside the IDE.

For us:

```bash
./backend/run-local.sh
```

is a reproducible local path.

---

# SECTION 330 — IDE GREEN RUN BUTTON IS NOT ARCHITECTURE

## 364.

Do not explain in interview:

```text
I click Run in IntelliJ/VS Code and backend starts.
```

Better:

> "The application starts through the Java main class and Spring Boot runtime. Locally I wrap the Maven `spring-boot:run` command with a shell script that loads environment configuration first."

---

# SECTION 331 — BUILD VS STARTUP FAILURE

## 365.

### Build Failure

```text
source cannot compile
tests fail
dependency/plugin resolution fails
```

Application never successfully reaches execution.

### Startup Failure

```text
build can succeed
but application fails while initializing
```

Examples:

```text
DB unavailable
port conflict
missing property
bean creation error
```

Important distinction.

---

# SECTION 332 — STARTUP VS REQUEST-TIME FAILURE

## 366.

### Startup

```text
application cannot become ready
```

### Request-Time

```text
application is running
but a particular API call fails
```

Example:

```text
app starts
GET /products works
POST /orders returns 500
```

That is not a startup issue.

---

# SECTION 333 — SPRING BOOT TESTING LAYERS

## 367. Conceptual Testing Levels

Spring applications can be tested at different layers:

```text
unit tests
controller tests
repository tests
integration tests
API/E2E tests
```

Our separate REST Assured project focuses primarily on:

```text
external API/integration behavior
```

with DB validation where appropriate.

---

# SECTION 334 — WHY EXTERNAL API AUTOMATION IS VALUABLE

## 368.

A separate test project sees the backend more like a real client:

```text
HTTP
authentication
serialization
routing
business logic
DB persistence
```

It does not depend directly on internal controller methods.

This reduces coupling between:

```text
test implementation
and
backend source implementation
```

---

# SECTION 335 — INTERNAL VS EXTERNAL TEST

## 369.

Internal test:

```text
may instantiate/mock application classes
```

External API test:

```text
calls deployed/running HTTP service
```

Both are useful.

They answer different questions.

---

# SECTION 336 — SPRING BOOT AND CORS

## 370. What Is CORS?

CORS means:

```text
Cross-Origin Resource Sharing
```

Browsers enforce origin-related security rules.

This becomes important when our future React frontend runs on something like:

```text
localhost:5173
```

and backend runs on:

```text
localhost:8080
```

These are different origins because the ports differ.

---

# SECTION 337 — WHY POSTMAN MAY WORK BUT BROWSER FAILS

## 371.

A common scenario:

```text
Postman/API automation
→ works

Browser frontend
→ CORS error
```

Why?

Because:

```text
CORS is primarily enforced by browsers
```

not by REST Assured/Postman in the same way.

This will matter in our frontend phase.

---

# SECTION 338 — CORS IS NOT AUTHENTICATION

## 372.

Do not confuse:

```text
CORS
→ browser cross-origin policy

JWT
→ authentication token

RBAC
→ authorization
```

They solve different problems.

---

# SECTION 339 — CORS FAILURE DEBUGGING

## 373.

If React cannot call backend:

Check:

```text
browser console
network tab
Origin
backend CORS config
allowed methods
allowed headers
credentials behavior
```

Do not immediately change REST Assured tests because they may remain completely valid.

---

# SECTION 340 — SPRING BOOT DEVTOOLS CONCEPT

## 374.

Spring Boot has development tooling such as DevTools that can support developer conveniences like restart behavior.

It is not essential for our automation architecture.

Know the concept but do not add dependencies without need.

---

# SECTION 341 — CONFIGURATION BINDING

## 375.

Spring can bind groups of properties into Java configuration objects.

Useful in larger applications for:

```text
structured configuration
type safety
centralized settings
```

Again, understand conceptually; no need to overengineer our portfolio now.

---

# SECTION 342 — SERVER CONTEXT PATH

## 376.

Applications can configure a base/context path.

Conceptually:

```text
/api
```

Then:

```text
/products
```

may become:

```text
/api/products
```

If every endpoint suddenly returns 404 after deployment, verify:

```text
base URL
context path
reverse proxy path
environment configuration
```

---

# SECTION 343 — REVERSE PROXY CONCEPT

## 377.

In deployed environments, clients may not connect directly to Spring Boot.

Possible architecture:

```text
Client
  ↓
Load Balancer / Reverse Proxy
  ↓
Spring Boot
```

Then failures can occur outside the Java application itself.

This becomes relevant in AWS/cloud stages later.

---

# SECTION 344 — REQUEST ID / TRACE ID

## 378.

Production systems may attach identifiers such as:

```text
request ID
correlation ID
trace ID
```

These help correlate:

```text
client request
API gateway
backend logs
downstream calls
```

As a Senior SDET, capture them when available during failure investigation.

---

# SECTION 345 — TIMEOUTS

## 379.

A timeout differs from:

```text
connection refused
```

Simplified:

```text
Connection refused
→ connection could not be established to listening service

Timeout
→ operation did not complete within expected time
```

Possible timeout causes:

```text
slow DB
downstream dependency
network
deadlock/resource contention
overloaded service
```

---

# SECTION 346 — SPRING BOOT + DATABASE LATENCY

## 380.

API response may be slow because:

```text
controller logic
DB query
connection pool
transaction
external service
```

Do not conclude:

```text
Spring Boot is slow
```

without measuring where time is spent.

---

# SECTION 347 — CONNECTION POOL CONCEPT

## 381.

Applications commonly reuse DB connections through a connection pool rather than opening a new physical connection for every query.

Concept:

```text
Application
    ↓
Connection Pool
    ↓
PostgreSQL
```

If pool is exhausted:

```text
requests may wait/fail
```

Useful performance troubleshooting concept.

---

# SECTION 348 — SERIALIZATION FAILURE

## 382.

Possible scenario:

```text
DB query succeeds
business logic succeeds
response conversion to JSON fails
```

This can still produce:

```text
500
```

Therefore DB success alone does not prove API success.

---

# SECTION 349 — SPRING BOOT ERROR DEBUGGING MAP

## 383.

```text
Backend Issue
   ↓
Can it build?
   |
   ├── NO → Maven / compile / dependency
   |
   └── YES
        ↓
Can it start?
   |
   ├── NO → env / DB / bean / port / config
   |
   └── YES
        ↓
Is port listening?
   |
   ├── NO → server/config/startup
   |
   └── YES
        ↓
Does endpoint exist?
   |
   ├── NO → routing / URL / version
   |
   └── YES
        ↓
Authentication?
        ↓
Authorization?
        ↓
Validation?
        ↓
Business logic?
        ↓
DB?
        ↓
Response serialization?
```

---

# SECTION 350 — INTERVIEW: WHAT IS SPRING BOOT?

## 384. Strong Answer

> "Spring Boot is built on the Spring ecosystem and simplifies building and running Java applications through auto-configuration, starter dependencies, externalized configuration and embedded-server support. From an SDET perspective, I use that understanding to troubleshoot application startup, configuration, security, API routing and database integration issues."

---

# SECTION 351 — INTERVIEW: WHAT IS DEPENDENCY INJECTION?

## 385. Strong Answer

> "Dependency injection means an application's dependencies are provided by the framework rather than each class manually constructing them. In Spring, the application context creates and manages beans and connects dependencies such as controllers, services and repositories."

---

# SECTION 352 — INTERVIEW: CONTROLLER VS SERVICE VS REPOSITORY

## 386.

> "The controller handles HTTP interaction, the service layer commonly contains business logic, and the repository handles persistence/data access. The exact architecture can vary, but this separation helps keep API, business and database responsibilities distinct."

---

# SECTION 353 — INTERVIEW: SPRING BOOT STARTUP

## 387.

> "The Java main method calls `SpringApplication.run`, Spring initializes the application context, loads configuration, creates required beans and initializes web, security and data components. For a web application the embedded server then starts listening on the configured port. If a critical bean or dependency fails during initialization, the application can fail before becoming ready."

---

# SECTION 354 — INTERVIEW: application.properties

## 388.

> "`application.properties` is one source of Spring Boot configuration. I prefer environment-specific and sensitive values to be externalized rather than hardcoded. When debugging, I also remember that environment variables, profiles and other runtime configuration sources may override file-based values."

---

# SECTION 355 — INTERVIEW: 404 BUT SERVER RUNNING

## 389.

> "A 404 does not mean the server is necessarily down. It means the HTTP request reached a server but the requested route/resource was not found. I would verify the HTTP method, path, context path, deployed version and controller mappings."

---

# SECTION 356 — INTERVIEW: 401 VS 403 IN SPRING SECURITY

## 390.

> "A 401 indicates the request could not be authenticated, while 403 means the user is authenticated but not authorized for that resource. In my portfolio project's RBAC tests, a normal user attempting an admin product operation is expected to receive 403."

---

# SECTION 357 — INTERVIEW: WHY API RETURNS 500

## 391.

> "A 500 means the server encountered an unexpected failure. I correlate the failing request with backend logs and the exception cause, then inspect the relevant business, persistence and dependency layers. I do not treat every 500 as an automation issue or simply change the expected status."

---

# SECTION 358 — INTERVIEW: SPRING BOOT + POSTGRESQL

## 392.

> "Spring Boot uses datasource configuration to connect to PostgreSQL. JPA/Hibernate maps application entities to database persistence, while Spring Data repositories provide data-access abstractions. In testing, I validate API behavior and use direct SQL/JDBC checks for selected critical persistence scenarios."

---

# SECTION 359 — INTERVIEW: WHY EXTERNALIZED CONFIG?

## 393.

> "Externalized configuration allows the same application code or artifact to run in different environments with different database URLs, credentials or runtime settings. It also avoids hardcoding secrets and makes CI/CD configuration easier."

---

# SECTION 360 — INTERVIEW: ACTUATOR

## 394.

> "Spring Boot Actuator provides operational endpoints such as health and metrics when enabled and configured. In CI or deployed environments, health endpoints can help determine whether the application is ready before running functional automation, although a healthy service still needs business-level testing."

---

# SECTION 361 — INTERVIEW: WHAT IF APPLICATION FAILS TO START?

## 395.

> "I first separate build failure from startup failure. If the build succeeds but startup fails, I inspect the Spring exception chain for the deepest meaningful cause, then check runtime configuration, database connectivity, bean creation and port availability. I verify readiness using logs, the listening port and a known HTTP endpoint."

---

# SECTION 362 — INTERVIEW: HOW DO YOU DEBUG SPRING API?

## 396.

> "I debug it layer by layer: connectivity, routing, authentication, authorization, validation, business logic and persistence. I correlate the HTTP request and response with backend logs and database state where needed. That helps avoid assuming every failed automated test is a test-script defect."

---

# SECTION 363 — RAPID FIRE SPRING BOOT QUESTIONS

## 397. Spring Boot main annotation?

```text
@SpringBootApplication
```

## 398. Starts Spring application?

```text
SpringApplication.run(...)
```

## 399. HTTP controller?

```text
@RestController
```

## 400. GET mapping?

```text
@GetMapping
```

## 401. POST mapping?

```text
@PostMapping
```

## 402. JSON request body?

```text
@RequestBody
```

## 403. URL path value?

```text
@PathVariable
```

## 404. Query parameter?

```text
@RequestParam
```

## 405. Persistence specification?

```text
JPA
```

## 406. Common JPA implementation?

```text
Hibernate
```

## 407. Persistence object annotation?

```text
@Entity
```

## 408. Runtime-managed object?

```text
Spring Bean
```

## 409. Authentication failure?

```text
401
```

## 410. Authorization failure?

```text
403
```

## 411. Missing route/resource?

```text
404
```

## 412. Unexpected server failure?

```text
500
```

## 413. Local application port?

```text
8080
```

## 414. OpenAPI JSON endpoint in our project?

```text
/v3/api-docs
```

## 415. Swagger UI?

```text
/swagger-ui/index.html
```

---

# SECTION 364 — COMMAND REVISION

## 416. Start Our Backend

From repository root:

```bash
./backend/run-local.sh
```

---

## 417. Check Port

```bash
lsof -i :8080
```

---

## 418. Check Java Process

```bash
pgrep -fl java
```

---

## 419. Check API Connectivity

```bash
curl -i http://localhost:8080/v3/api-docs
```

---

## 420. Check Docker Containers

```bash
docker ps
```

---

## 421. Check Compose Services

```bash
docker compose ps
```

---

## 422. Build Backend

Inside backend:

```bash
./mvnw clean package
```

provided required runtime/build configuration is available.

---

## 423. Run Packaged JAR

Conceptually:

```bash
java -jar target/<application>.jar
```

---

# SECTION 365 — 5-MINUTE SPRING BOOT REVISION

## 424. Architecture

```text
HTTP
 ↓
Security
 ↓
Controller
 ↓
Service/Business Logic
 ↓
Repository
 ↓
JPA/Hibernate
 ↓
PostgreSQL
```

---

## 425. Startup

```text
main()
 ↓
SpringApplication.run
 ↓
Configuration
 ↓
Application Context
 ↓
Beans
 ↓
Embedded Server
 ↓
Port
 ↓
Ready
```

---

## 426. Configuration

```text
application.properties
environment variables
runtime properties
profiles
```

Question:

```text
Which value is effective at runtime?
```

---

## 427. Errors

```text
Build Failure
→ compile/dependency/test/build

Startup Failure
→ config/bean/DB/port

Connection Refused
→ process/listener/network

404
→ routing

401
→ authentication

403
→ authorization

400
→ request/validation

500
→ server-side failure
```

---

# SECTION 366 — OUR PROJECT COMPLETE REQUEST FLOW

## 428. Example Secured API

```text
REST Assured
    ↓
HTTP Request
    ↓
Spring Boot
    ↓
JWT Authentication Filter
    ↓
Spring Security Authorization
    ↓
Controller
    ↓
Validation
    ↓
Business Logic
    ↓
Repository
    ↓
JPA/Hibernate
    ↓
PostgreSQL
    ↓
HTTP Response
    ↓
REST Assured Assertions
    ↓
Optional JDBC Validation
    ↓
Sanitized Allure Evidence
```

This is one of the strongest architecture diagrams to remember for our portfolio.

---

# SECTION 367 — SPRING BOOT TROUBLESHOOTING CHECKLIST

## 429.

When backend fails:

```text
1. Read actual error
2. Determine build vs runtime
3. Check Java/Maven version
4. Check required environment configuration
5. Check PostgreSQL/container
6. Check port
7. Read deepest Spring cause
8. Verify endpoint independently
9. Compare API behavior
10. Check DB state where relevant
```

---

# SECTION 368 — WHAT A SENIOR SDET SHOULD KNOW VS NOT CLAIM

## 430. Strong Knowledge

We should confidently understand:

```text
Spring Boot startup
REST architecture
Controller/Service/Repository
Dependency Injection
Configuration
JPA/Hibernate basics
Security request flow
JWT/RBAC
HTTP error classification
DB connectivity
Logs and exception diagnosis
```

We do NOT need to claim:

```text
expert JVM internals
Spring framework contributor-level knowledge
production database administration
advanced Hibernate tuning
enterprise backend architecture ownership
```

unless we actually have that experience.

---

# SECTION 369 — ONE-MINUTE SPRING BOOT ANSWER

## 431.

> "I use Spring Boot in my SDET portfolio project to understand and validate the complete backend flow, not just to write tests against a black box. I understand how the application starts through the Spring context, how controllers, business logic and repositories connect, how JPA/Hibernate persists data to PostgreSQL, and how Spring Security handles JWT authentication and role-based authorization. This helps me diagnose failures across startup, routing, security, validation, business logic and persistence instead of assuming every failed API test is an automation issue."

---

# SECTION 370 — FINAL SPRING BOOT MENTAL MODEL

## 432.

```text
CODE
 ↓
MAVEN
 ↓
JDK
 ↓
SPRING BOOT
 ↓
APPLICATION CONTEXT
 ↓
BEANS
 ↓
EMBEDDED SERVER
 ↓
SECURITY
 ↓
CONTROLLERS
 ↓
BUSINESS LOGIC
 ↓
REPOSITORIES
 ↓
JPA / HIBERNATE
 ↓
POSTGRESQL
```

Runtime troubleshooting:

```text
BUILD?
 ↓
STARTUP?
 ↓
PORT?
 ↓
HTTP?
 ↓
ROUTE?
 ↓
AUTHENTICATION?
 ↓
AUTHORIZATION?
 ↓
VALIDATION?
 ↓
BUSINESS?
 ↓
DATABASE?
```

---

# END OF PART 3 — SPRING BOOT BUILD, STARTUP, CONFIGURATION & RUNTIME

Next:

**PART 4 — HTTP, HTTPS, DNS, TCP, CORS, PROXY & API NETWORK TROUBLESHOOTING FOR SDET**

---

# PART 4 — HTTP, HTTPS, DNS, TCP, CORS, PROXY & API NETWORK TROUBLESHOOTING FOR SDET

# SECTION 371 — WHY NETWORKING MATTERS FOR SDET

As an SDET, API failure ka matlab hamesha backend bug nahi hota.

Failure kisi bhi layer par ho sakta hai:

```text
DNS
TCP
TLS
Proxy
Load Balancer
Gateway
Application
Authentication
Authorization
Database
```

Isliye strong SDET ko ye difference samajhna chahiye:

```text
Connection refused
Timeout
DNS failure
SSL certificate error
401
403
404
502
503
504
CORS error
```

Sab ek jaise nahi hote.

---

# SECTION 372 — COMPLETE API COMMUNICATION FLOW

## 433. High-Level Flow

Suppose request:

```text
https://api.example.com/products
```

Conceptually:

```text
Client
  ↓
DNS lookup
  ↓
IP address
  ↓
TCP connection
  ↓
TLS handshake
  ↓
HTTP request
  ↓
Proxy / Load Balancer / Gateway
  ↓
Spring Boot backend
  ↓
Business logic
  ↓
Database
  ↓
HTTP response
```

Local environment me simpler flow ho sakta hai:

```text
REST Assured
   ↓
http://localhost:8080
   ↓
Spring Boot
```

---

# SECTION 373 — WHAT IS HTTP?

## 434. Definition

HTTP means:

```text
Hypertext Transfer Protocol
```

It is an application-layer protocol used for communication between clients and servers.

Example:

```text
Client sends HTTP Request
Server returns HTTP Response
```

API automation heavily relies on HTTP.

---

# SECTION 374 — HTTP REQUEST

## 435. Main Parts

A request can contain:

```text
HTTP Method
URL
Headers
Query Parameters
Path Parameters
Cookies
Request Body
```

Example:

```http
POST /orders HTTP/1.1
Host: api.example.com
Authorization: Bearer <token>
Content-Type: application/json

{
  "cartId": 10
}
```

---

# SECTION 375 — HTTP RESPONSE

## 436. Main Parts

Response contains:

```text
Status Code
Headers
Body
```

Example:

```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": 101,
  "status": "CREATED"
}
```

---

# SECTION 376 — HTTP METHODS

## 437. Common Methods

```text
GET
POST
PUT
PATCH
DELETE
HEAD
OPTIONS
```

Most API testing focuses heavily on first five.

---

# SECTION 377 — GET

## 438.

Typically used to retrieve data.

Example:

```text
GET /products
```

Expected property:

```text
normally should not modify server state
```

---

# SECTION 378 — POST

## 439.

Commonly used for:

```text
creating resource
submitting action
```

Example:

```text
POST /orders
```

Can return:

```text
201 Created
```

depending on contract.

---

# SECTION 379 — PUT

## 440.

Typically used for:

```text
full replacement
or update semantics defined by API
```

Example:

```text
PUT /products/10
```

Actual contract is source of truth.

---

# SECTION 380 — PATCH

## 441.

Usually used for:

```text
partial update
```

Example:

```text
PATCH /users/10
```

with only changed fields.

---

# SECTION 381 — DELETE

## 442.

Used to remove resource.

Example:

```text
DELETE /products/10
```

Possible responses:

```text
200
202
204
```

depending on API design.

---

# SECTION 382 — IDEMPOTENCY

## 443. Important Concept

An operation is idempotent when repeating the same request has the same intended effect as executing it once.

Typically:

```text
GET
PUT
DELETE
```

are considered idempotent by HTTP semantics.

POST is generally:

```text
not inherently idempotent
```

But application design can implement idempotency for POST using mechanisms such as:

```text
idempotency keys
```

Very important for payment/order systems.

---

# SECTION 383 — SAFE HTTP METHODS

## 444.

A safe HTTP method is intended only for retrieval and should not change server state.

Typical examples:

```text
GET
HEAD
OPTIONS
```

Safe and idempotent are related but not identical concepts.

---

# SECTION 384 — URL STRUCTURE

## 445.

Example:

```text
https://api.example.com:443/products/10?currency=INR
```

Breakdown:

```text
https
→ scheme/protocol

api.example.com
→ hostname

443
→ port

/products/10
→ path

currency=INR
→ query parameter
```

---

# SECTION 385 — PATH PARAMETER

## 446.

Example:

```text
/products/10
```

Here:

```text
10
```

identifies resource in path.

Concept:

```text
GET /products/{id}
```

---

# SECTION 386 — QUERY PARAMETER

## 447.

Example:

```text
/products?category=electronics&page=2
```

Query parameters commonly represent:

```text
filtering
sorting
pagination
search
options
```

---

# SECTION 387 — PATH PARAM VS QUERY PARAM

## 448.

Simple distinction:

```text
Path parameter
→ usually identifies resource/path

Query parameter
→ usually modifies/filter/searches request
```

Example:

```text
/products/10
→ product ID

/products?name=phone
→ product filtering/search
```

---

# SECTION 388 — HTTP HEADERS

## 449. What Are Headers?

Headers carry metadata about request or response.

Common request headers:

```text
Authorization
Content-Type
Accept
User-Agent
Cookie
Origin
```

Common response headers:

```text
Content-Type
Set-Cookie
Cache-Control
Location
```

---

# SECTION 389 — CONTENT-TYPE

## 450.

`Content-Type` describes the format of the body being sent.

Example:

```http
Content-Type: application/json
```

Meaning:

```text
request body is JSON
```

---

# SECTION 390 — ACCEPT

## 451.

`Accept` tells server what response media types client can understand.

Example:

```http
Accept: application/json
```

Meaning:

```text
client prefers JSON response
```

---

# SECTION 391 — CONTENT-TYPE VS ACCEPT

## 452.

```text
Content-Type
→ format I am sending

Accept
→ format I want to receive
```

Common interview question.

---

# SECTION 392 — AUTHORIZATION HEADER

## 453.

Bearer authentication typically uses:

```http
Authorization: Bearer <token>
```

In our project:

```text
JWT
```

is passed using this header.

Security warning:

```text
Never expose real tokens in logs or screenshots.
```

---

# SECTION 393 — COOKIES

## 454. What Is a Cookie?

A cookie is small data stored by client/browser and sent with applicable HTTP requests.

Example response:

```http
Set-Cookie: sessionId=abc...
```

Later browser may send:

```http
Cookie: sessionId=abc...
```

Cookies are commonly used for:

```text
sessions
preferences
authentication
tracking
```

depending on application.

---

# SECTION 394 — COOKIE VS BEARER TOKEN

## 455.

Cookie:

```text
browser can automatically attach it
```

Bearer token:

```text
client usually explicitly sends Authorization header
```

Both can support authentication designs.

They are not interchangeable by definition.

---

# SECTION 395 — HTTP STATUS CODE GROUPS

## 456.

```text
1xx
→ informational

2xx
→ success

3xx
→ redirection

4xx
→ client/request/auth-related issue

5xx
→ server/upstream issue
```

This is high-level classification.

---

# SECTION 396 — IMPORTANT 2XX STATUS CODES

## 457.

```text
200 OK
→ successful request

201 Created
→ resource successfully created

202 Accepted
→ request accepted for asynchronous processing

204 No Content
→ successful request with no response body
```

---

# SECTION 397 — IMPORTANT 3XX STATUS CODES

## 458.

```text
301 Moved Permanently
302 Found
303 See Other
307 Temporary Redirect
308 Permanent Redirect
```

Redirect behavior matters especially for:

```text
SEO
authentication
legacy URLs
gateway routing
```

---

# SECTION 398 — 301 VS 302

## 459.

Simplified:

```text
301
→ permanent redirect

302
→ temporary redirect
```

But exact client method behavior can vary historically, which is why modern HTTP also defines:

```text
307
308
```

more explicitly.

---

# SECTION 399 — 307 VS 308

## 460.

```text
307
→ temporary redirect while preserving HTTP method

308
→ permanent redirect while preserving HTTP method
```

Useful when redirecting non-GET requests.

---

# SECTION 400 — IMPORTANT 4XX STATUS CODES

## 461.

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
405 Method Not Allowed
409 Conflict
415 Unsupported Media Type
422 Unprocessable Content
429 Too Many Requests
```

Exact API behavior depends on implementation/contract.

---

# SECTION 401 — 405 METHOD NOT ALLOWED

## 462.

Example:

Endpoint supports:

```text
GET /products
```

but client sends:

```text
DELETE /products
```

Server may return:

```text
405 Method Not Allowed
```

Different from 404:

```text
404
→ route/resource not found

405
→ route may exist but method not allowed
```

---

# SECTION 402 — 409 CONFLICT

## 463.

Used when request conflicts with current resource/system state.

Examples conceptually:

```text
duplicate resource
state conflict
version conflict
```

Actual contract determines whether API uses 409.

---

# SECTION 403 — 415 UNSUPPORTED MEDIA TYPE

## 464.

Example:

API expects:

```http
Content-Type: application/json
```

but client sends unsupported body format.

Possible result:

```text
415
```

Check:

```text
Content-Type
request body
API contract
```

---

# SECTION 404 — 422

## 465.

`422 Unprocessable Content` may be used when request syntax is understandable but semantic validation fails.

Not every application uses 422.

Some use:

```text
400
```

for validation errors.

Follow API contract.

---

# SECTION 405 — 429 TOO MANY REQUESTS

## 466.

Usually represents:

```text
rate limit exceeded
```

Response may include headers such as:

```text
Retry-After
```

Important for:

```text
performance testing
API reliability
automation retries
```

Do not aggressively retry 429 without understanding server policy.

---

# SECTION 406 — IMPORTANT 5XX STATUS CODES

## 467.

```text
500 Internal Server Error
502 Bad Gateway
503 Service Unavailable
504 Gateway Timeout
```

These do NOT mean exactly the same thing.

---

# SECTION 407 — 500 INTERNAL SERVER ERROR

## 468.

Usually means application/server encountered unexpected error.

Potential areas:

```text
code exception
DB error
serialization
configuration
business logic
```

Need backend evidence.

---

# SECTION 408 — 502 BAD GATEWAY

## 469.

Usually occurs when a proxy/gateway receives an invalid/unusable response from upstream service.

Architecture:

```text
Client
 ↓
Gateway
 ↓
Backend
```

Possible:

```text
Gateway reaches backend unsuccessfully
→ 502
```

This may occur even if gateway itself is healthy.

---

# SECTION 409 — 503 SERVICE UNAVAILABLE

## 470.

Usually means service is temporarily unavailable.

Possible reasons:

```text
maintenance
overload
backend unavailable
no healthy instances
```

Often relevant with:

```text
load balancers
service discovery
deployments
```

---

# SECTION 410 — 504 GATEWAY TIMEOUT

## 471.

Usually means proxy/gateway waited for upstream service but response did not arrive within configured timeout.

Flow:

```text
Client
 ↓
Gateway
 ↓
Backend too slow
 ↓
Gateway timeout
 ↓
504
```

---

# SECTION 411 — 500 VS 502 VS 503 VS 504

## 472.

Mental model:

```text
500
→ application/server failed while processing

502
→ gateway got bad response from upstream

503
→ service unavailable

504
→ gateway waited too long for upstream
```

Always validate actual architecture.

---

# SECTION 412 — WHAT IS HTTPS?

## 473.

HTTPS means:

```text
HTTP over TLS
```

Simplified:

```text
HTTP
+
Encryption/Authentication via TLS
=
HTTPS
```

HTTPS protects data in transit.

---

# SECTION 413 — HTTP VS HTTPS

## 474.

HTTP:

```text
http://
```

HTTPS:

```text
https://
```

HTTPS provides capabilities including:

```text
encryption
server authentication
data integrity
```

through TLS.

---

# SECTION 414 — SSL VS TLS

## 475.

You may hear:

```text
SSL certificate
SSL handshake
```

Modern secure web communication uses:

```text
TLS
```

SSL is older terminology/protocol family.

People still commonly say:

```text
SSL certificate
```

even though TLS is the modern protocol.

---

# SECTION 415 — TLS HANDSHAKE

## 476. Simplified Flow

Before HTTPS request data is exchanged securely:

```text
Client connects
     ↓
TLS negotiation
     ↓
Server presents certificate
     ↓
Client validates certificate
     ↓
Cryptographic keys established
     ↓
Encrypted HTTP communication
```

Actual protocol is more detailed, but this is enough for SDET troubleshooting.

---

# SECTION 416 — DIGITAL CERTIFICATE

## 477.

A server certificate helps clients verify:

```text
server identity
public key information
certificate issuer
validity period
hostname association
```

Browsers/JVMs validate certificates against trusted certificate authorities.

---

# SECTION 417 — CERTIFICATE EXPIRY

## 478.

Certificates have validity periods.

If expired:

```text
browser
API client
Java application
CI job
```

may reject the HTTPS connection.

Possible symptom:

```text
certificate expired
SSLHandshakeException
```

---

# SECTION 418 — HOSTNAME VALIDATION

## 479.

Certificate should be valid for requested hostname.

Example:

Request:

```text
https://api.company.com
```

but certificate is not valid for that hostname.

Possible result:

```text
hostname verification failure
```

---

# SECTION 419 — SELF-SIGNED CERTIFICATE

## 480.

A self-signed certificate is signed by itself instead of a normally trusted CA chain.

Common in:

```text
local/internal environments
```

Clients may reject it unless configured to trust it.

Important:

```text
Disabling SSL validation globally is not a good production solution.
```

---

# SECTION 420 — JAVA TRUSTSTORE

## 481.

Java uses trust configuration to decide which certificate issuers/certificates are trusted.

Conceptually:

```text
HTTPS server certificate
       ↓
Java trust validation
       ↓
trusted?
```

Enterprise environments may require:

```text
corporate CA certificate
custom truststore
```

---

# SECTION 421 — SSLHandshakeException

## 482.

Possible causes:

```text
untrusted certificate
expired certificate
hostname mismatch
TLS protocol incompatibility
certificate chain issue
corporate proxy interception
```

Do not assume:

```text
backend API is broken
```

The problem may happen before HTTP request reaches application.

---

# SECTION 422 — WHAT IS DNS?

## 483.

DNS means:

```text
Domain Name System
```

It translates hostnames into network addresses.

Example:

```text
api.example.com
      ↓
DNS
      ↓
IP address
```

Humans use names.

Networks ultimately communicate using addresses.

---

# SECTION 423 — HOSTNAME VS IP ADDRESS

## 484.

Hostname:

```text
api.example.com
```

IP address:

```text
203.0.113.x
```

Concept:

```text
DNS
→ hostname to address resolution
```

Do not hardcode IPs unless architecture specifically requires it because services can move/change addresses.

---

# SECTION 424 — DNS FAILURE

## 485.

Symptoms may include:

```text
Could not resolve host
UnknownHostException
DNS lookup failure
```

This means failure can occur before:

```text
TCP
TLS
HTTP
```

---

# SECTION 425 — DNS TROUBLESHOOTING COMMANDS

## 486.

Useful tools:

```bash
nslookup example.com
```

or:

```bash
dig example.com
```

Availability depends on OS/tooling.

Also:

```bash
curl -v https://example.com
```

can reveal name resolution/connection details.

---

# SECTION 426 — WHAT IS IP?

## 487.

IP provides addressing/routing across networks.

For SDET understanding:

```text
DNS gives an address
IP helps packets reach destination
TCP creates reliable connection
HTTP runs above that connection
```

Simplified stack:

```text
HTTP
 ↓
TLS
 ↓
TCP
 ↓
IP
```

for typical HTTPS API communication.

---

# SECTION 427 — WHAT IS TCP?

## 488.

TCP means:

```text
Transmission Control Protocol
```

It provides a reliable connection-oriented transport.

HTTP/1.1 and HTTP/2 commonly operate over TCP.

Concept:

```text
Client
 ↓
TCP connection
 ↓
Server
```

---

# SECTION 428 — TCP CONNECTION

## 489.

Before normal HTTP communication over TCP:

```text
client must establish connection
```

If connection cannot be created, HTTP endpoint logic is never reached.

---

# SECTION 429 — TCP THREE-WAY HANDSHAKE

## 490. High-Level

TCP connection establishment traditionally:

```text
Client → SYN
Server → SYN-ACK
Client → ACK
```

Then connection is established.

For SDET interviews, knowing the high-level idea is enough.

---

# SECTION 430 — WHAT IS A PORT?

## 491.

An IP identifies machine/network interface.

A port identifies a service endpoint on that machine.

Example:

```text
localhost:8080
```

```text
localhost
→ host

8080
→ port
```

---

# SECTION 431 — COMMON PORTS

## 492.

Common defaults:

```text
HTTP  → 80
HTTPS → 443
SSH   → 22
PostgreSQL → 5432
```

Our local Spring Boot backend:

```text
8080
```

Our local PostgreSQL exposure:

```text
5432
```

---

# SECTION 432 — CONNECTION REFUSED

## 493. Meaning

Connection refused usually means:

```text
target host was reachable
but nothing accepted connection on target port
```

Possible causes:

```text
service not running
wrong port
service crashed
service listening elsewhere
container port not exposed
```

---

# SECTION 433 — CONNECTION REFUSED TROUBLESHOOTING

## 494.

For local backend:

```bash
pgrep -fl java
```

then:

```bash
lsof -i :8080
```

then:

```bash
curl -i http://localhost:8080/v3/api-docs
```

Check in order:

```text
process
port
HTTP
```

---

# SECTION 434 — TIMEOUT

## 495.

Timeout means an operation did not finish within expected time.

Possible stages:

```text
connection timeout
read timeout
gateway timeout
```

Potential causes:

```text
network packet loss
firewall
slow backend
slow DB
downstream dependency
overload
```

---

# SECTION 435 — CONNECTION REFUSED VS TIMEOUT

## 496.

Simplified:

```text
Connection refused
→ destination actively/reliably says no listener available

Timeout
→ no successful response within allowed time
```

They suggest different troubleshooting paths.

---

# SECTION 436 — READ TIMEOUT

## 497.

Connection may succeed:

```text
TCP connected
```

but server takes too long to respond.

Then client may hit:

```text
read timeout
```

Example:

```text
slow query
slow downstream call
deadlock
resource exhaustion
```

---

# SECTION 437 — CONNECT TIMEOUT

## 498.

Client cannot establish connection within configured time.

Potential reasons:

```text
network path issue
firewall
unreachable host
service/network problem
```

Different from:

```text
read timeout
```

---

# SECTION 438 — WHAT IS LOCALHOST?

## 499.

`localhost` refers to the local machine.

Common loopback IPv4:

```text
127.0.0.1
```

If REST Assured calls:

```text
http://localhost:8080
```

it expects backend on the same machine/network namespace as the test process.

---

# SECTION 439 — IMPORTANT DOCKER LOCALHOST CONCEPT

## 500.

Inside a Docker container:

```text
localhost
```

means:

```text
that container itself
```

not automatically your Mac/host machine.

This becomes extremely important when we Dockerize the automation/frontend/backend later.

---

# SECTION 440 — DOCKER NETWORK EXAMPLE

## 501.

Imagine:

```text
backend container
postgres container
```

Inside backend container:

```text
localhost:5432
```

would mean:

```text
backend container's own port 5432
```

not PostgreSQL container.

With Docker Compose, containers commonly communicate using:

```text
service name
```

Example concept:

```text
jdbc:postgresql://postgres:5432/database
```

if Compose service is named:

```text
postgres
```

---

# SECTION 441 — 0.0.0.0 VS 127.0.0.1

## 502. High-Level

A service bound to:

```text
127.0.0.1
```

accepts connections from local loopback interface.

A service bound to:

```text
0.0.0.0
```

typically listens on all available IPv4 interfaces.

This matters in:

```text
containers
VMs
remote access
```

---

# SECTION 442 — WHAT IS A PROXY?

## 503.

A proxy sits between client and destination.

Flow:

```text
Client
 ↓
Proxy
 ↓
Server
```

Proxy can:

```text
forward requests
filter traffic
inspect traffic
apply security policy
cache
control internet access
```

---

# SECTION 443 — CORPORATE PROXY

## 504.

Enterprise environments often require outbound traffic through a corporate proxy.

Possible impact:

```text
Maven dependency downloads fail
npm install fails
API tests fail
certificate validation fails
Docker pull fails
```

This is why:

```text
works on personal network
fails on corporate network
```

can be infrastructure/configuration related.

---

# SECTION 444 — HTTP_PROXY / HTTPS_PROXY

## 505.

Some CLI tools respect environment variables such as:

```text
HTTP_PROXY
HTTPS_PROXY
NO_PROXY
```

Exact behavior varies by tool.

`NO_PROXY` can define hosts that should bypass proxy.

Example concept:

```text
localhost
127.0.0.1
internal services
```

---

# SECTION 445 — PROXY VS REVERSE PROXY

## 506.

Forward proxy:

```text
Client
 ↓
Proxy
 ↓
Internet/Server
```

Represents client side.

Reverse proxy:

```text
Client
 ↓
Reverse Proxy
 ↓
Backend Servers
```

Represents server side/backend infrastructure.

Examples of reverse-proxy-type components may include:

```text
Nginx
API Gateway
Load Balancer
Ingress
```

depending on architecture.

---

# SECTION 446 — LOAD BALANCER

## 507.

A load balancer distributes traffic across backend instances.

Example:

```text
Client
   ↓
Load Balancer
   ↓
Backend 1
Backend 2
Backend 3
```

Benefits:

```text
availability
scaling
traffic distribution
health-based routing
```

---

# SECTION 447 — API GATEWAY

## 508.

An API gateway can act as an entry point for APIs and provide features such as:

```text
routing
authentication
rate limiting
logging
request transformation
```

Architecture:

```text
Client
 ↓
API Gateway
 ↓
Service A
Service B
Service C
```

---

# SECTION 448 — WHY GATEWAY MATTERS FOR TESTING

## 509.

Suppose:

```text
direct backend endpoint works
public API URL fails
```

Problem may be:

```text
gateway route
gateway authentication
TLS
rate limit
proxy
load balancer
```

not backend business logic.

---

# SECTION 449 — WHAT IS CORS?

## 510.

CORS means:

```text
Cross-Origin Resource Sharing
```

It is a browser security mechanism controlling cross-origin web requests.

Important:

```text
CORS is primarily a browser concern.
```

---

# SECTION 450 — WHAT IS AN ORIGIN?

## 511.

Origin is based on:

```text
scheme
host
port
```

Example:

```text
http://localhost:5173
```

and:

```text
http://localhost:8080
```

are different origins because:

```text
ports differ
```

---

# SECTION 451 — SAME ORIGIN EXAMPLE

## 512.

Same origin requires matching:

```text
scheme
host
port
```

Example:

```text
https://example.com/app
https://example.com/api
```

same origin.

But:

```text
http://example.com
https://example.com
```

different because scheme differs.

---

# SECTION 452 — WHY OUR FUTURE REACT APP WILL NEED CORS

## 513.

Future frontend:

```text
http://localhost:5173
```

Backend:

```text
http://localhost:8080
```

Browser sees:

```text
different origins
```

Therefore backend needs appropriate CORS policy for development.

---

# SECTION 453 — WHY REST ASSURED CAN WORK WHILE BROWSER FAILS

## 514.

REST Assured is not a browser enforcing browser-origin policy.

Therefore:

```text
REST Assured
→ API works

Browser React app
→ blocked by CORS
```

can happen.

Important:

```text
backend may be reachable
but browser refuses frontend JavaScript access to response
```

---

# SECTION 454 — CORS RESPONSE HEADER

## 515.

Common CORS header:

```http
Access-Control-Allow-Origin
```

It tells browser which origins are permitted.

Other possible headers:

```text
Access-Control-Allow-Methods
Access-Control-Allow-Headers
Access-Control-Allow-Credentials
```

depending on policy.

---

# SECTION 455 — PREFLIGHT REQUEST

## 516.

Browser may send:

```text
OPTIONS
```

request before actual cross-origin request.

This is called:

```text
preflight
```

It asks server whether actual request is permitted.

---

# SECTION 456 — PREFLIGHT FLOW

## 517.

Example:

```text
Browser wants POST
with Authorization header
       ↓
OPTIONS request
       ↓
Server sends CORS permissions
       ↓
Browser checks response
       ↓
Actual POST allowed or blocked
```

---

# SECTION 457 — CORS ERROR TROUBLESHOOTING

## 518.

Check:

```text
browser console
Network tab
request Origin
OPTIONS request
Access-Control-Allow-Origin
allowed methods
allowed headers
credentials policy
backend CORS configuration
```

Do NOT solve by:

```text
disabling browser security
```

as a real application fix.

---

# SECTION 458 — CORS VS CSRF

## 519.

These are different concepts.

```text
CORS
→ controls cross-origin browser access

CSRF
→ attack where browser is tricked into sending authenticated request
```

Do not use terms interchangeably.

---

# SECTION 459 — CSRF HIGH-LEVEL

## 520.

CSRF means:

```text
Cross-Site Request Forgery
```

It matters especially when authentication credentials such as cookies are automatically attached by browser.

Token-based stateless APIs have different threat models, but CSRF should still be understood in the context of authentication design.

---

# SECTION 460 — HTTP STATELESSNESS

## 521.

HTTP is conceptually stateless:

```text
each request is independent
```

Applications add state using mechanisms such as:

```text
cookies
sessions
tokens
databases
```

JWT-based authentication is one example.

---

# SECTION 461 — KEEP-ALIVE

## 522.

Opening a new TCP connection for every request can be expensive.

HTTP clients may reuse connections.

Concept:

```text
TCP Connection
   ↓
Request 1
Request 2
Request 3
```

instead of reconnecting each time.

Connection reuse improves efficiency.

---

# SECTION 462 — CONNECTION POOLING

## 523.

HTTP clients may maintain connection pools.

Useful for:

```text
performance
parallel automation
service communication
```

Incorrect pool configuration can contribute to:

```text
connection exhaustion
timeouts
resource issues
```

---

# SECTION 463 — HTTP/1.1 VS HTTP/2

## 524. High-Level

HTTP/1.1 and HTTP/2 differ in how communication is transported/organized.

HTTP/2 supports features such as:

```text
multiplexing
header compression
binary framing
```

You do not need protocol-internals expertise for typical SDET interviews.

Know:

```text
API behavior may be same at application level while transport implementation differs.
```

---

# SECTION 464 — HTTP/3

## 525.

HTTP/3 uses:

```text
QUIC
```

over UDP rather than TCP.

Again, useful general awareness but not a priority for our current portfolio.

For our practical SDET troubleshooting:

```text
HTTP/HTTPS
TCP
DNS
TLS
```

remain core.

---

# SECTION 465 — REQUEST TIMEOUTS IN AUTOMATION

## 526.

Automation clients can have:

```text
connection timeout
socket/read timeout
overall request timeout
```

Do not increase timeout blindly.

First determine:

```text
Is service slow?
Is network unstable?
Is expected operation asynchronous?
Is test waiting incorrectly?
```

---

# SECTION 466 — RETRIES

## 527.

Retries can help with certain transient failures.

But retries can also:

```text
hide defects
increase load
duplicate non-idempotent requests
make flaky tests look green
```

Never retry blindly.

---

# SECTION 467 — RETRYING POST REQUESTS

## 528.

Suppose:

```text
POST /payments
```

times out.

Client does not know whether server completed processing.

Blind retry could cause:

```text
duplicate payment
```

unless API supports:

```text
idempotency
```

This is why retry strategy is critical in payments/order systems.

---

# SECTION 468 — RATE LIMITING

## 529.

Servers may limit requests:

```text
requests per second
requests per minute
per user/token/IP
```

When exceeded:

```text
429
```

may occur.

Automation should respect environment limits.

---

# SECTION 469 — API THROTTLING VS PERFORMANCE BUG

## 530.

If test sends thousands of requests and gets 429:

```text
could be expected rate limiting
```

not necessarily performance failure.

Need requirement/limit baseline.

---

# SECTION 470 — CACHE

## 531.

HTTP responses may be cached.

Common header:

```text
Cache-Control
```

Caching can cause:

```text
stale responses
different repeated-request behavior
environment confusion
```

Important when testing APIs/CDNs.

---

# SECTION 471 — ETAG

## 532.

ETag is a response validator used for caching/concurrency scenarios.

Example:

```http
ETag: "abc123"
```

Client can use conditions such as:

```text
If-None-Match
```

Server may return:

```text
304 Not Modified
```

Useful concept for advanced API testing.

---

# SECTION 472 — 304 NOT MODIFIED

## 533.

Means cached representation can still be used.

Response normally does not send full resource representation.

This is not a failure.

---

# SECTION 473 — REDIRECT TESTING

## 534.

When testing redirects, validate:

```text
status code
Location header
final destination
query parameters
HTTP/HTTPS behavior
method preservation if relevant
redirect loops
```

---

# SECTION 474 — REDIRECT LOOP

## 535.

Example:

```text
A → B
B → A
```

Browser/client can eventually report:

```text
too many redirects
```

Possible causes:

```text
proxy config
HTTP→HTTPS rule
authentication routing
application routing
```

---

# SECTION 475 — CURL

## 536. Why curl Matters

`curl` is one of the most valuable SDET/network debugging tools.

It can isolate:

```text
browser
automation framework
frontend
```

from the API itself.

If REST Assured fails, try direct curl.

---

# SECTION 476 — BASIC CURL

## 537.

```bash
curl http://localhost:8080/v3/api-docs
```

Sends HTTP request and prints response body.

---

# SECTION 477 — CURL WITH HEADERS

## 538.

```bash
curl -i http://localhost:8080/v3/api-docs
```

`-i` includes response headers.

Useful to inspect:

```text
status
Content-Type
other response headers
```

---

# SECTION 478 — CURL VERBOSE

## 539.

```bash
curl -v http://localhost:8080/v3/api-docs
```

Verbose output can show:

```text
DNS
connection
request headers
response headers
TLS information for HTTPS
```

Very useful for troubleshooting.

Be cautious because verbose output can expose:

```text
Authorization headers
cookies
tokens
```

if supplied.

---

# SECTION 479 — CURL GET

## 540.

```bash
curl -i http://localhost:8080/products
```

For secured endpoints this may return:

```text
401
```

without authentication.

That itself confirms:

```text
network
routing
security
```

are functioning up to authentication layer.

---

# SECTION 480 — CURL AUTHENTICATION

## 541.

Conceptual command:

```bash
curl -i \
  -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8080/products
```

Never put real tokens into:

```text
documentation
Git
screenshots
shared logs
```

---

# SECTION 481 — CURL POST JSON

## 542.

Generic example:

```bash
curl -i \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"name":"Example"}' \
  http://localhost:8080/example
```

For secured endpoint:

```bash
-H "Authorization: Bearer <TOKEN>"
```

can be added.

Use placeholder tokens in notes.

---

# SECTION 482 — CURL QUERY PARAM

## 543.

Example:

```bash
curl -i "http://localhost:8080/products?name=phone"
```

Quote URLs containing special shell characters such as:

```text
&
?
```

to avoid shell interpretation problems.

---

# SECTION 483 — CURL FOLLOW REDIRECT

## 544.

```bash
curl -L http://example.com
```

`-L` follows redirects.

Without it you can inspect original redirect response directly.

---

# SECTION 484 — CURL HEAD REQUEST

## 545.

```bash
curl -I https://example.com
```

Requests headers using HEAD behavior.

Useful for quickly inspecting:

```text
status
redirect
cache headers
server metadata
```

assuming server supports it.

---

# SECTION 485 — CURL RESPONSE STATUS ONLY

## 546. Useful Pattern

```bash
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/v3/api-docs
```

This prints only HTTP status.

Useful in:

```text
shell scripts
health checks
CI
```

---

# SECTION 486 — CURL TIMING

## 547.

`curl` can expose timing information using `-w`.

Conceptual example:

```bash
curl -s -o /dev/null \
  -w "total=%{time_total}\n" \
  http://localhost:8080/v3/api-docs
```

Useful as quick diagnostic.

Not a replacement for:

```text
proper performance testing
```

---

# SECTION 487 — CURL CONNECTIVITY LAYERS

## 548.

With:

```bash
curl -v https://api.example.com
```

you can often observe:

```text
DNS resolution
IP selected
connection attempt
TLS handshake
HTTP request
HTTP response
```

This makes curl powerful for isolating network failures.

---

# SECTION 488 — PING

## 549.

```bash
ping example.com
```

tests ICMP reachability when allowed.

Important:

```text
Ping failure does NOT prove HTTP server is down.
```

Many environments block ICMP while HTTPS still works.

---

# SECTION 489 — TELNET / NC CONCEPT

## 550.

To test whether TCP port is reachable, tools such as:

```text
nc
telnet
```

can help.

Example with netcat:

```bash
nc -vz localhost 8080
```

If available.

This checks:

```text
TCP connectivity
```

not API correctness.

---

# SECTION 490 — LSOF

## 551.

Local port check:

```bash
lsof -i :8080
```

This answers:

```text
Which process is listening/using port 8080?
```

Very useful before blaming Spring Boot.

---

# SECTION 491 — DNS VS PORT VS HTTP

## 552. Debug Layer by Layer

```text
Can hostname resolve?
      ↓
Can TCP connection establish?
      ↓
Can TLS handshake succeed?
      ↓
Can HTTP request reach server?
      ↓
What HTTP status returned?
```

Never jump directly from:

```text
API failed
```

to:

```text
application bug
```

---

# SECTION 492 — REST ASSURED NETWORK FAILURE TYPES

## 553.

Automation may fail with:

```text
UnknownHostException
ConnectException
SocketTimeoutException
SSLHandshakeException
HTTP 401
HTTP 500
```

These represent very different layers.

Classifying exception is first troubleshooting step.

---

# SECTION 493 — UnknownHostException

## 554.

Usually:

```text
DNS/name resolution problem
```

Check:

```text
base URL
hostname typo
DNS
VPN
corporate network
environment configuration
```

---

# SECTION 494 — ConnectException

## 555.

May indicate:

```text
connection refused
network connection issue
```

Check:

```text
host
port
service
container
firewall/network
```

---

# SECTION 495 — SocketTimeoutException

## 556.

May indicate operation exceeded socket timeout.

Check:

```text
slow server
DB latency
network
downstream service
timeout configuration
```

---

# SECTION 496 — SSLHandshakeException

## 557.

Check:

```text
certificate
truststore
hostname
TLS version
corporate proxy
certificate expiry
```

---

# SECTION 497 — HTTP ERROR IS DIFFERENT FROM NETWORK ERROR

## 558.

If server returns:

```text
401
403
404
500
```

HTTP communication succeeded enough to receive a response.

If client gets:

```text
UnknownHostException
connection refused
TLS handshake failure
```

failure occurred before normal application HTTP response.

This is a very strong troubleshooting distinction.

---

# SECTION 498 — BROWSER DEVTOOLS

## 559.

Browser Developer Tools Network tab helps inspect:

```text
request URL
method
status
headers
payload
response
timing
CORS
redirects
```

When React frontend starts, DevTools will become one of our main debugging tools.

---

# SECTION 499 — POSTMAN VS CURL VS REST ASSURED VS BROWSER

## 560.

### Postman

Good for:

```text
interactive API exploration
manual testing
collections
```

### curl

Good for:

```text
fast low-level debugging
shell/CI checks
network inspection
```

### REST Assured

Good for:

```text
automated Java API testing
assertions
framework integration
regression
```

### Browser

Good for:

```text
real frontend behavior
CORS
cookies
UI/API interaction
```

Use correct tool for correct question.

---

# SECTION 500 — SCENARIO: POSTMAN WORKS, AUTOMATION FAILS

## 561.

Compare:

```text
base URL
method
headers
body
authentication
query params
cookies
content type
environment
proxy
TLS config
```

Do not assume REST Assured bug immediately.

---

# SECTION 501 — SCENARIO: REST ASSURED WORKS, REACT FAILS

## 562.

First check:

```text
browser console
Network tab
CORS
frontend base URL
Authorization handling
preflight OPTIONS
```

Likely browser-specific layer.

---

# SECTION 502 — SCENARIO: LOCAL WORKS, QA FAILS DNS

## 563.

Possible:

```text
QA hostname wrong
DNS inaccessible
VPN required
corporate DNS
environment variable wrong
```

Do not change application assertions.

---

# SECTION 503 — SCENARIO: LOCAL WORKS, CI CONNECTION REFUSED

## 564.

Check:

```text
Is backend started in CI?
Correct port?
Correct BASE_URL?
Container networking?
Service readiness?
```

Classic CI issue:

```text
test starts before backend is ready
```

---

# SECTION 504 — READINESS

## 565.

Starting process and service being ready are not identical.

Flow:

```text
Process starts
 ↓
Spring initializes
 ↓
DB connects
 ↓
port opens
 ↓
application ready
```

CI should ideally wait for:

```text
readiness condition
```

not an arbitrary sleep only.

---

# SECTION 505 — BAD CI WAIT

## 566.

Weak approach:

```bash
sleep 30
```

Problem:

```text
sometimes app ready in 5 sec
sometimes 40 sec
```

Better concept:

```text
poll health/known endpoint
until ready
with timeout
```

---

# SECTION 506 — EXAMPLE READINESS LOOP

## 567. Generic Concept

```bash
for i in {1..30}; do
  if curl -s -f http://localhost:8080/v3/api-docs > /dev/null; then
    echo "Backend is ready"
    break
  fi

  sleep 2
done
```

Production CI implementation should also fail clearly if readiness is never reached.

---

# SECTION 507 — REQUEST CORRELATION

## 568.

When API fails in shared environment, capture:

```text
timestamp
endpoint
method
status
request ID/trace ID
test name
environment
```

Avoid storing:

```text
password
token
sensitive payload
```

---

# SECTION 508 — AUTHORIZATION LOG SANITIZATION

## 569.

Our framework already sanitizes sensitive values in Allure attachments.

Concept:

```text
Actual request
→ real token sent to backend

Report/log
→ token replaced with [REDACTED]
```

This is good security engineering.

---

# SECTION 509 — WHY MASKING MUST NOT CHANGE REAL REQUEST

## 570.

Wrong approach:

```text
replace token with [REDACTED]
before sending request
```

Then authentication fails.

Correct:

```text
send real request
create sanitized copy only for reporting/logging
```

---

# SECTION 510 — REQUEST CONTENT-TYPE ERROR

## 571.

Scenario:

```text
API expects JSON
automation sends plain text
```

Possible:

```text
415
400
```

depending on application.

Check:

```text
Content-Type
serialization
request body
```

---

# SECTION 511 — ACCEPT ERROR

## 572.

If client requests unsupported response format:

```text
Accept: application/xml
```

but API only supports JSON.

Possible:

```text
406 Not Acceptable
```

depending on implementation/content negotiation.

---

# SECTION 512 — 406 NOT ACCEPTABLE

## 573.

Means server cannot produce representation acceptable according to request's `Accept` header.

Not extremely common in modern JSON APIs, but useful to know.

---

# SECTION 513 — CONTENT NEGOTIATION

## 574.

Content negotiation determines representation format between client and server.

Headers:

```text
Accept
Content-Type
```

play important roles.

---

# SECTION 514 — COMPRESSION

## 575.

HTTP responses may use compression such as:

```text
gzip
br
```

Headers may include:

```text
Accept-Encoding
Content-Encoding
```

Clients usually handle decompression automatically.

Useful awareness for:

```text
performance
proxy
browser/network debugging
```

---

# SECTION 515 — HOST HEADER

## 576.

HTTP request contains target host information.

Example:

```http
Host: api.example.com
```

This allows infrastructure to route multiple domains on shared systems.

Incorrect host routing can cause:

```text
wrong service
404
TLS problems
```

---

# SECTION 516 — USER-AGENT

## 577.

`User-Agent` identifies client software.

Examples:

```text
browser
curl
Postman
Java HTTP client
```

Some systems behave differently based on User-Agent, though API contracts ideally should not rely unnecessarily on it.

---

# SECTION 517 — REQUEST ID

## 578.

A client/system may use headers like:

```text
X-Request-ID
X-Correlation-ID
```

Exact header names are system-specific.

These help correlate:

```text
test request
gateway logs
backend logs
```

---

# SECTION 518 — BASIC FIREWALL CONCEPT

## 579.

A firewall controls allowed network traffic.

Possible scenario:

```text
service running
port listening locally
remote client cannot connect
```

because network/firewall rules block access.

This matters later with:

```text
AWS Security Groups
VMs
containers
corporate networks
```

---

# SECTION 519 — VPN

## 580.

Some internal environments require VPN access.

Without VPN:

```text
DNS may fail
route may fail
connection may timeout
```

Automation executed from CI must also have permitted network connectivity.

---

# SECTION 520 — WHY CI MAY NOT ACCESS INTERNAL QA

## 581.

Possible causes:

```text
CI runner outside corporate network
VPN absent
firewall
DNS
allowlist
proxy
security group
```

Not every CI failure is application-related.

---

# SECTION 521 — PROXY CERTIFICATE INTERCEPTION

## 582.

Some enterprise proxies inspect HTTPS by presenting certificates signed by a corporate CA.

Java/tool must trust that CA.

Otherwise:

```text
TLS handshake error
```

may occur.

This can affect:

```text
Maven
REST Assured
Java HTTPS
Docker
npm
```

depending on configuration.

---

# SECTION 522 — API BASE URL

## 583.

A base URL combines core service location.

Examples conceptually:

```text
LOCAL
http://localhost:8080

QA
https://qa-api.example.com

STAGE
https://stage-api.example.com
```

Test code should avoid hardcoding these everywhere.

---

# SECTION 523 — OUR ENVIRONMENT SWITCHING

## 584.

Our automation supports runtime environment selection.

Concept:

```text
TEST_ENV=local
      ↓
local base URL

TEST_ENV=qa
      ↓
QA_BASE_URL

TEST_ENV=stage
      ↓
STAGE_BASE_URL
```

This separates:

```text
test logic
from
environment configuration
```

---

# SECTION 524 — WHY BASE URL ERRORS ARE COMMON

## 585.

Example:

```text
Test expects QA
but BASE_URL points to local
```

Possible symptom:

```text
unexpected data
401
404
connection refused
```

Always print safe environment metadata such as:

```text
Environment: QA
Base URL: https://...
```

without secrets.

Our Allure environment metadata already follows this principle.

---

# SECTION 525 — API VERSIONING

## 586.

APIs may expose versions:

```text
/api/v1/products
/api/v2/products
```

Version mismatch can produce:

```text
404
schema mismatch
unexpected behavior
```

Always verify deployed version when environment behavior differs.

---

# SECTION 526 — URI VS URL

## 587. High-Level

URI is broader identifier concept.

URL identifies resource and its location/access mechanism.

In everyday API testing people commonly use:

```text
URL
endpoint
URI
```

somewhat loosely.

For interviews, know URL is a type of URI.

---

# SECTION 527 — DOMAIN, HOSTNAME, ENDPOINT

## 588.

Example:

```text
https://api.example.com/products/10
```

```text
Domain/hostname
→ api.example.com

Path
→ /products/10

Full endpoint URL
→ https://api.example.com/products/10
```

---

# SECTION 528 — LATENCY

## 589.

Latency is time delay involved in request/communication.

API response time can contain:

```text
DNS time
TCP connection
TLS handshake
network travel
server processing
DB processing
response transfer
```

Therefore:

```text
API took 2 sec
```

does not automatically mean:

```text
Java method took 2 sec
```

---

# SECTION 529 — PERFORMANCE LAYERS

## 590.

```text
Client
 ↓
Network
 ↓
Gateway
 ↓
Backend
 ↓
DB
 ↓
External Services
```

Performance bottleneck can exist at any layer.

Later k6 testing will help quantify system behavior.

---

# SECTION 530 — DNS CACHING

## 591.

DNS responses can be cached by:

```text
OS
browser
JVM
network resolver
```

This can sometimes cause temporary differences after hostname/IP changes.

Useful concept during deployment/infrastructure troubleshooting.

---

# SECTION 531 — TLS HANDSHAKE COST

## 592.

TLS adds cryptographic negotiation overhead when establishing secure connections.

Connection reuse can reduce repeated handshake overhead.

This is one reason connection management matters in performance testing.

---

# SECTION 532 — CLIENT-SIDE VS SERVER-SIDE FAILURE

## 593.

Client-side issue examples:

```text
wrong URL
wrong header
wrong token
CORS
client timeout
```

Server-side examples:

```text
business exception
DB failure
server crash
```

Infrastructure examples:

```text
DNS
gateway
load balancer
network
TLS
proxy
```

Strong debugging separates all three.

---

# SECTION 533 — SDET NETWORK DEBUGGING FRAMEWORK

## 594.

When API fails:

```text
1. Confirm environment
2. Confirm base URL
3. Resolve hostname
4. Test port/connectivity
5. Validate TLS
6. Send minimal curl request
7. Inspect HTTP status
8. Validate auth
9. Check gateway/proxy if present
10. Correlate backend logs
11. Validate DB/downstream
12. Compare automation request
```

---

# SECTION 534 — MINIMAL REPRODUCTION

## 595.

If automation request is complex:

```text
reduce it to simplest reproducible curl
```

Example progression:

```text
Can /v3/api-docs respond?
      ↓
Can secured GET respond?
      ↓
Can POST reproduce?
```

This removes framework noise.

---

# SECTION 535 — NETWORK TROUBLESHOOTING PRINCIPLE

## 596.

Never say:

```text
API is down
```

only because:

```text
my test failed
```

Prove which layer failed.

Better statement:

```text
"The client cannot establish a TCP connection to host X on port Y."
```

or:

```text
"The API is reachable but returns 403 for the USER role."
```

Evidence is more useful than assumptions.

---

# SECTION 536 — INTERVIEW: HTTP VS HTTPS

## 597. Strong Answer

> "HTTP is the application protocol used for request-response communication, while HTTPS is HTTP protected by TLS. TLS provides encryption, integrity and server authentication. When HTTPS fails before an HTTP response is received, I inspect certificate trust, hostname validation and TLS configuration rather than treating it as an API functional failure."

---

# SECTION 537 — INTERVIEW: DNS

## 598.

> "DNS resolves a hostname to a network address. If my API test throws an `UnknownHostException`, I first verify the configured hostname, DNS resolution and network/VPN access because the request may not have reached the TCP or HTTP layer at all."

---

# SECTION 538 — INTERVIEW: CONNECTION REFUSED VS TIMEOUT

## 599.

> "Connection refused usually indicates that the target connection was actively rejected, often because nothing is listening on the target port. A timeout means the connection or response did not complete within the configured period. I investigate them differently rather than increasing timeout immediately."

---

# SECTION 539 — INTERVIEW: 502 VS 504

## 600.

> "A 502 generally means a gateway or proxy received an invalid response from its upstream service, while a 504 means the gateway waited for the upstream service and timed out. Both can indicate infrastructure or upstream-service problems rather than a defect in the client."

---

# SECTION 540 — INTERVIEW: WHAT IS CORS?

## 601.

> "CORS is a browser security mechanism controlling JavaScript requests across different origins. An origin is defined by scheme, host and port. This is why an API can work in REST Assured or Postman but fail from a React application if the backend does not return appropriate CORS headers."

---

# SECTION 541 — INTERVIEW: CONTENT-TYPE VS ACCEPT

## 602.

> "`Content-Type` describes the media type of the body being sent, while `Accept` tells the server which response media types the client can accept."

---

# SECTION 542 — INTERVIEW: WHAT IS A PROXY?

## 603.

> "A proxy sits between a client and destination and forwards traffic, while a reverse proxy sits in front of backend services. Corporate proxies can affect dependency downloads, TLS trust and API access, while reverse proxies and gateways can affect routing, authentication and upstream responses."

---

# SECTION 543 — INTERVIEW: WHY POSTMAN WORKS BUT BROWSER FAILS

## 604.

> "I would first check browser-specific behavior such as CORS, cookies, preflight requests and frontend authentication handling. Postman does not enforce browser CORS policy, so successful Postman execution does not prove a cross-origin browser call is configured correctly."

---

# SECTION 544 — INTERVIEW: HOW DO YOU DEBUG API CONNECTIVITY?

## 605.

> "I debug layer by layer. I verify the environment and base URL, then DNS resolution, TCP port connectivity, TLS for HTTPS and finally HTTP response behavior. I use tools such as curl and lsof to create a minimal reproduction before comparing the automation request. If an HTTP response exists, I then investigate routing, authentication, authorization and business behavior."

---

# SECTION 545 — INTERVIEW: WHAT DOES 401 PROVE?

## 606.

A 401 can actually prove several lower layers are working:

```text
DNS likely resolved
TCP connected
TLS succeeded if HTTPS
HTTP reached server/security layer
```

Authentication then failed.

This is much more informative than saying:

```text
API isn't working.
```

---

# SECTION 546 — INTERVIEW: WHAT DOES CONNECTION REFUSED PROVE?

## 607.

It tells us HTTP business logic likely was not reached.

Investigate:

```text
service/process
host
port
container
network
```

before:

```text
controller
database
business logic
```

---

# SECTION 547 — INTERVIEW: IDEMPOTENCY

## 608.

> "An idempotent operation has the same intended server-side effect when the same request is repeated. GET, PUT and DELETE are generally defined as idempotent by HTTP semantics, while POST is not inherently idempotent. This becomes especially important for payment APIs where retrying a timed-out POST could create duplicate processing unless idempotency is designed."

---

# SECTION 548 — SENIOR SDET SCENARIO: 504 IN QA

## 609.

Approach:

```text
1. Confirm reproducibility
2. Capture endpoint/time/trace ID
3. Measure response duration
4. Check whether gateway timeout is consistent
5. Inspect backend logs
6. Check DB/downstream latency
7. Compare direct backend route if permitted
8. Compare recent deployment/config changes
```

Do not immediately:

```text
increase automation timeout
```

because client timeout and gateway timeout are separate things.

---

# SECTION 549 — SENIOR SDET SCENARIO: CORS FAILURE

## 610.

Symptoms:

```text
API works in Swagger/Postman
React browser request blocked
```

Approach:

```text
Browser Network tab
   ↓
Inspect OPTIONS
   ↓
Inspect Origin
   ↓
Inspect CORS response headers
   ↓
Check backend allowed origin/method/header
```

---

# SECTION 550 — SENIOR SDET SCENARIO: CI DNS FAILURE

## 611.

Local:

```text
QA API works
```

CI:

```text
UnknownHostException
```

Investigate:

```text
CI runner network
DNS configuration
VPN/internal access
hostname
proxy
allowlist
```

Do not rewrite working API test logic first.

---

# SECTION 551 — SENIOR SDET SCENARIO: 502 AFTER DEPLOYMENT

## 612.

Possible architecture:

```text
Client
 ↓
Load Balancer
 ↓
Spring Boot
```

After deployment:

```text
502
```

Investigate:

```text
backend instances healthy?
port correct?
health check correct?
app startup complete?
gateway upstream configured?
```

---

# SECTION 552 — SENIOR SDET SCENARIO: SSL ERROR ONLY IN CI

## 613.

Check:

```text
CI JDK truststore
certificate chain
corporate CA
proxy
hostname
Java version
```

Possible:

```text
local machine trusts certificate
CI runner does not
```

---

# SECTION 553 — SENIOR SDET SCENARIO: RANDOM API TIMEOUTS

## 614.

Collect:

```text
frequency
endpoint
response time
concurrency
DB timing
gateway timing
server load
network pattern
trace IDs
```

Classify:

```text
client timeout
network timeout
gateway timeout
application latency
```

Avoid:

```text
blind retry until green
```

---

# SECTION 554 — SENIOR SDET SCENARIO: WRONG ENVIRONMENT

## 615.

Symptoms:

```text
expected data missing
unexpected 401
schema mismatch
different behavior
```

Check first:

```text
TEST_ENV
base URL
reported environment metadata
```

Environment mistakes can look like product bugs.

---

# SECTION 555 — RAPID FIRE NETWORKING QUESTIONS

## 616. HTTP full form?

```text
Hypertext Transfer Protocol
```

## 617. HTTPS?

```text
HTTP over TLS
```

## 618. DNS?

```text
Domain Name System
```

## 619. TCP?

```text
Transmission Control Protocol
```

## 620. Default HTTP port?

```text
80
```

## 621. Default HTTPS port?

```text
443
```

## 622. PostgreSQL default port?

```text
5432
```

## 623. Our local backend port?

```text
8080
```

## 624. Authentication failure?

```text
401
```

## 625. Authorization failure?

```text
403
```

## 626. Route missing?

```text
404
```

## 627. Method unsupported?

```text
405
```

## 628. Unsupported body type?

```text
415
```

## 629. Rate limit?

```text
429
```

## 630. Gateway bad upstream response?

```text
502
```

## 631. Service unavailable?

```text
503
```

## 632. Gateway timeout?

```text
504
```

## 633. Browser cross-origin mechanism?

```text
CORS
```

## 634. Browser preflight method?

```text
OPTIONS
```

---

# SECTION 556 — CURL RAPID REVISION

## 635. Basic GET

```bash
curl http://localhost:8080/v3/api-docs
```

## 636. Include Headers

```bash
curl -i http://localhost:8080/v3/api-docs
```

## 637. Verbose

```bash
curl -v http://localhost:8080/v3/api-docs
```

## 638. Follow Redirect

```bash
curl -L https://example.com
```

## 639. Headers Only

```bash
curl -I https://example.com
```

## 640. Check Status Only

```bash
curl -s -o /dev/null -w "%{http_code}\n" \
  http://localhost:8080/v3/api-docs
```

## 641. Check Port

```bash
lsof -i :8080
```

## 642. Check Java Process

```bash
pgrep -fl java
```

## 643. DNS

```bash
nslookup example.com
```

or:

```bash
dig example.com
```

if available.

---

# SECTION 557 — FULL NETWORK MENTAL MODEL

## 644.

When calling:

```text
https://api.example.com/products
```

think:

```text
Hostname
   ↓
DNS
   ↓
IP
   ↓
TCP connection
   ↓
TLS handshake
   ↓
HTTP request
   ↓
Proxy/Gateway/LB
   ↓
Spring Security
   ↓
Controller
   ↓
Business Logic
   ↓
Database
   ↓
HTTP response
```

---

# SECTION 558 — ERROR TO LAYER MAPPING

## 645.

```text
UnknownHostException
→ DNS

Connection refused
→ TCP/service/port

SSLHandshakeException
→ TLS/certificate/trust

CORS error
→ browser/origin policy

401
→ authentication

403
→ authorization

404
→ routing/resource

400/422
→ request/validation

500
→ backend failure

502
→ gateway/upstream response

503
→ service unavailable

504
→ gateway/upstream timeout
```

---

# SECTION 559 — COMPLETE SDET DEBUGGING FLOW

## 646.

```text
TEST FAILS
    ↓
Correct Environment?
    ↓
Correct URL?
    ↓
DNS Resolves?
    ↓
Port Reachable?
    ↓
TLS Valid?
    ↓
HTTP Response Exists?
    ↓
Correct Route?
    ↓
Authentication?
    ↓
Authorization?
    ↓
Validation?
    ↓
Business Logic?
    ↓
Database?
    ↓
Downstream Service?
    ↓
Automation Assertion?
```

This order saves a huge amount of debugging time.

---

# SECTION 560 — ONE-MINUTE NETWORKING INTERVIEW ANSWER

## 647.

> "For API automation I troubleshoot failures layer by layer rather than assuming every failure is in the application. I understand DNS resolution, TCP connectivity, ports, HTTPS/TLS and HTTP request-response behavior. I use curl and port checks to isolate connectivity from application behavior, then differentiate authentication, authorization, routing and server errors through status codes. I also understand browser-specific CORS behavior, proxy and gateway layers, and the difference between connection refused, client timeout, 502 and 504. This helps me diagnose local, CI and shared-environment failures much faster."

---

# SECTION 561 — OUR PROJECT NETWORK FLOW

## 648. Current Local Setup

```text
REST Assured
     ↓
http://localhost:8080
     ↓
Spring Boot
     ↓
JWT / RBAC
     ↓
Controllers
     ↓
JPA
     ↓
PostgreSQL
localhost:5432
```

Future frontend:

```text
React
localhost:5173
     ↓
Browser CORS
     ↓
Spring Boot
localhost:8080
```

Future CI/cloud:

```text
GitHub Actions
     ↓
Internet / Network
     ↓
AWS Load Balancer / Public Endpoint
     ↓
Spring Boot Service
     ↓
RDS PostgreSQL
```

Exact AWS architecture will be decided when we implement that phase.

---

# SECTION 562 — FINAL 5-MINUTE REVISION

## 649. Remember These Distinctions

```text
HTTP
vs
HTTPS

DNS
vs
TCP

Host
vs
Port

Connection Refused
vs
Timeout

Content-Type
vs
Accept

Authentication
vs
Authorization

401
vs
403

404
vs
405

500
vs
502
vs
503
vs
504

Proxy
vs
Reverse Proxy

Postman
vs
Browser

CORS
vs
CSRF

Build Failure
vs
Network Failure
vs
HTTP Failure
```

---

# SECTION 563 — FINAL TROUBLESHOOTING PRINCIPLE

## 650.

A Senior SDET should be able to say:

```text
"The test failed because DNS resolution failed."
```

instead of:

```text
"API is down."
```

Or:

```text
"The TCP connection reaches the service and the application responds,
but authorization rejects the USER role with 403."
```

instead of:

```text
"The API doesn't work."
```

The goal is:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
PROVE
   ↓
FIX / REPORT
```

---

# END OF PART 4 — HTTP, HTTPS, DNS, TCP, CORS, PROXY & API NETWORK TROUBLESHOOTING

Next:

**PART 5 — DOCKER & DOCKER COMPOSE DEEP DIVE FOR SDET**

---

# PART 5 — DOCKER & DOCKER COMPOSE DEEP DIVE FOR SDET

# SECTION 564 — WHY DOCKER MATTERS FOR SDET

Docker helps us create consistent, isolated environments.

Without containers:

```text
Works on my machine
But fails on another machine
```

because of differences in:

```text
OS
installed software
versions
configuration
dependencies
```

Docker reduces these differences by packaging application/runtime requirements into images and running them as containers.

For SDET, Docker is useful for:

```text
databases
test environments
application services
CI pipelines
integration testing
local environments
service dependencies
```

---

# SECTION 565 — WHAT IS DOCKER?

## 651. Simple Definition

Docker is a platform for:

```text
building
packaging
running
distributing
```

applications in containers.

Mental model:

```text
Application
+
Runtime
+
Dependencies
+
Configuration
        ↓
Docker Image
        ↓
Docker Container
```

---

# SECTION 566 — WHAT IS A CONTAINER?

## 652.

A container is a running instance of an image.

Simple:

```text
Image
→ Blueprint

Container
→ Running instance
```

Example:

```text
postgres:16
→ Docker image

sdet-commerce-postgres
→ running container
```

---

# SECTION 567 — IMAGE VS CONTAINER

## 653.

```text
Docker Image
→ immutable template/package

Docker Container
→ running or stopped instance created from image
```

One image can create multiple containers.

Example:

```text
postgres:16
   ↓
Container A
Container B
Container C
```

---

# SECTION 568 — DOCKER IMAGE

## 654.

An image contains layers required to create a container.

It may include:

```text
base operating-system files
runtime
application files
dependencies
startup instructions
```

An image itself is not a running process.

---

# SECTION 569 — CONTAINER PROCESS

## 655.

A container normally runs one primary process.

Example:

```text
PostgreSQL container
      ↓
PostgreSQL server process
```

If the main process stops:

```text
container typically stops
```

---

# SECTION 570 — CONTAINER VS VIRTUAL MACHINE

## 656. High-Level Difference

Virtual Machine:

```text
Hardware virtualization
+
Guest OS
+
Applications
```

Container:

```text
Shares host kernel
+
isolated processes/filesystem/networking
```

Containers are generally:

```text
lighter
faster to start
more portable
```

than full VMs.

---

# SECTION 571 — DOCKER ENGINE

## 657.

Docker Engine provides the runtime responsible for managing:

```text
images
containers
networks
volumes
```

On macOS, Docker Desktop provides the local Docker environment.

---

# SECTION 572 — DOCKER CLI

## 658.

Commands such as:

```bash
docker ps
docker images
docker run
docker logs
```

are Docker CLI commands.

The CLI communicates with the Docker engine.

---

# SECTION 573 — CHECK DOCKER VERSION

## 659.

```bash
docker --version
```

Our machine already has Docker Desktop installed and working.

Compose can be checked using:

```bash
docker compose version
```

---

# SECTION 574 — DOCKER IMAGES

## 660.

List local images:

```bash
docker images
```

or:

```bash
docker image ls
```

Output commonly contains:

```text
repository
tag
image ID
created
size
```

---

# SECTION 575 — IMAGE NAME AND TAG

## 661.

Example:

```text
postgres:16
```

Breakdown:

```text
postgres
→ image repository/name

16
→ tag
```

A tag commonly represents:

```text
version
variant
environment
release
```

---

# SECTION 576 — LATEST TAG

## 662.

Example:

```text
postgres:latest
```

or simply:

```text
postgres
```

may resolve to `latest`.

For reproducible environments, explicit version tags are usually better.

Our project uses:

```text
postgres:16
```

which makes the database major version intentional.

---

# SECTION 577 — DOCKER REGISTRY

## 663.

A registry stores Docker images.

Examples:

```text
Docker Hub
GitHub Container Registry
AWS ECR
private enterprise registries
```

Flow:

```text
Registry
   ↓
docker pull
   ↓
Local Image
   ↓
Container
```

---

# SECTION 578 — DOCKER PULL

## 664.

Download image:

```bash
docker pull postgres:16
```

Docker may also pull an image automatically when `docker run` or Compose needs it and the image is not local.

---

# SECTION 579 — DOCKER RUN

## 665.

Basic pattern:

```bash
docker run <image>
```

Example:

```bash
docker run postgres:16
```

But PostgreSQL also requires configuration such as credentials/database settings.

That is why Compose is convenient for our project.

---

# SECTION 580 — DETACHED MODE

## 666.

Run container in background:

```bash
docker run -d <image>
```

`-d` means:

```text
detached mode
```

Without detached mode, terminal may remain attached to container output.

---

# SECTION 581 — CONTAINER NAME

## 667.

Specify name:

```bash
docker run --name my-container <image>
```

Then use readable name in commands:

```bash
docker logs my-container
docker stop my-container
```

Our PostgreSQL container name is:

```text
sdet-commerce-postgres
```

---

# SECTION 582 — LIST RUNNING CONTAINERS

## 668.

```bash
docker ps
```

Shows running containers.

Useful fields:

```text
container ID
image
command
status
ports
name
```

---

# SECTION 583 — LIST ALL CONTAINERS

## 669.

```bash
docker ps -a
```

Shows:

```text
running
stopped
exited
```

containers.

Important when:

```text
docker ps shows nothing
```

but a failed/stopped container still exists.

---

# SECTION 584 — CONTAINER STATES

## 670.

Common states:

```text
created
running
paused
restarting
exited
dead
```

For testing most common:

```text
running
exited
```

---

# SECTION 585 — START EXISTING CONTAINER

## 671.

```bash
docker start <container>
```

This starts a previously created/stopped container.

It does NOT create a new container.

---

# SECTION 586 — STOP CONTAINER

## 672.

```bash
docker stop <container>
```

Docker requests graceful shutdown.

Prefer this before forceful killing.

---

# SECTION 587 — RESTART CONTAINER

## 673.

```bash
docker restart <container>
```

Equivalent concept:

```text
stop
+
start
```

Useful after certain runtime issues, but do not use restart as a substitute for root-cause analysis.

---

# SECTION 588 — REMOVE CONTAINER

## 674.

```bash
docker rm <container>
```

Container must normally be stopped first.

Force removal:

```bash
docker rm -f <container>
```

Use carefully.

---

# SECTION 589 — REMOVE IMAGE

## 675.

```bash
docker rmi <image>
```

or:

```bash
docker image rm <image>
```

An image may not be removable while dependent containers still exist.

---

# SECTION 590 — DOCKER LOGS

## 676.

```bash
docker logs <container>
```

For our database:

```bash
docker logs sdet-commerce-postgres
```

Logs can reveal:

```text
startup
database initialization
errors
authentication issues
shutdown
```

---

# SECTION 591 — FOLLOW LOGS

## 677.

```bash
docker logs -f <container>
```

`-f` means:

```text
follow
```

similar to:

```bash
tail -f
```

Stop viewing with:

```text
Ctrl + C
```

This does not normally stop the container.

---

# SECTION 592 — LAST N LOG LINES

## 678.

```bash
docker logs --tail 100 <container>
```

Useful instead of dumping huge logs.

Can combine:

```bash
docker logs -f --tail 100 <container>
```

---

# SECTION 593 — DOCKER EXEC

## 679.

Run command inside a running container:

```bash
docker exec <container> <command>
```

Example concept:

```bash
docker exec sdet-commerce-postgres <command>
```

---

# SECTION 594 — INTERACTIVE EXEC

## 680.

Common pattern:

```bash
docker exec -it <container> <shell>
```

Example if shell exists:

```bash
docker exec -it <container> bash
```

or sometimes:

```bash
docker exec -it <container> sh
```

Options:

```text
-i
→ interactive STDIN

-t
→ pseudo-terminal
```

---

# SECTION 595 — CONTAINER SHELL

## 681.

Once inside:

```text
you are operating inside container filesystem/process environment
```

Commands such as:

```bash
pwd
ls
env
```

now describe the container, not necessarily host machine.

Exit:

```bash
exit
```

---

# SECTION 596 — DOCKER INSPECT

## 682.

```bash
docker inspect <container>
```

Returns detailed JSON including:

```text
networking
mounts
environment
ports
state
image
```

Useful for deep troubleshooting.

---

# SECTION 597 — DOCKER STATS

## 683.

```bash
docker stats
```

Shows live resource usage such as:

```text
CPU
memory
network
```

Useful when container is:

```text
slow
memory-heavy
resource constrained
```

Not a full observability solution.

---

# SECTION 598 — PORT MAPPING

## 684.

Containers have their own network namespace.

To expose a container port to host:

```bash
docker run -p <host-port>:<container-port> <image>
```

Example:

```bash
docker run -p 5432:5432 postgres:16
```

Mental model:

```text
Host 5432
    ↓
Container 5432
```

---

# SECTION 599 — HOST PORT VS CONTAINER PORT

## 685.

Example:

```text
5433:5432
```

means:

```text
Host port
5433

Container port
5432
```

From host:

```text
localhost:5433
```

Inside container/service network:

```text
service may still listen on 5432
```

---

# SECTION 600 — OUR POSTGRES PORT

## 686.

Current Compose mapping:

```yaml
ports:
  - "5432:5432"
```

Meaning:

```text
Mac localhost:5432
       ↓
PostgreSQL container:5432
```

This is why our local Spring Boot app running on the host can use:

```text
localhost:5432
```

for the database.

---

# SECTION 601 — CRITICAL LOCALHOST RULE

## 687.

On our Mac:

```text
localhost
→ Mac
```

Inside a Docker container:

```text
localhost
→ that container
```

This is one of the most common Docker mistakes.

---

# SECTION 602 — LOCALHOST EXAMPLE

## 688.

Suppose future backend is Dockerized:

```text
backend container
postgres container
```

Inside backend container:

```text
localhost:5432
```

means:

```text
look for PostgreSQL inside backend container
```

which is wrong if DB is in a separate container.

---

# SECTION 603 — CONTAINER-TO-CONTAINER COMMUNICATION

## 689.

Containers on the same Docker network can communicate using:

```text
service/container DNS names
```

In Docker Compose, service names are particularly useful.

If service is:

```yaml
services:
  postgres:
```

backend can conceptually use:

```text
postgres:5432
```

instead of:

```text
localhost:5432
```

---

# SECTION 604 — DOCKER NETWORK

## 690.

Docker networks allow containers to communicate.

List:

```bash
docker network ls
```

Inspect:

```bash
docker network inspect <network>
```

---

# SECTION 605 — DEFAULT BRIDGE NETWORK

## 691.

Docker can use a bridge-style network for local container communication.

Docker Compose usually creates a project network automatically unless configured otherwise.

Concept:

```text
backend
   │
   └── compose-network
            │
            └── postgres
```

---

# SECTION 606 — SERVICE DISCOVERY

## 692.

Docker Compose provides DNS-based service discovery.

Example:

```yaml
services:
  postgres:
    image: postgres:16

  backend:
    ...
```

Inside `backend`:

```text
postgres
```

can resolve to the PostgreSQL service/container on the Compose network.

---

# SECTION 607 — DOCKER VOLUME

## 693.

Container filesystem is not a good place to assume permanent data storage.

Volumes provide persistent Docker-managed storage.

Concept:

```text
Container
   ↓
Volume
   ↓
Persistent Data
```

---

# SECTION 608 — WHY VOLUME MATTERS FOR POSTGRES

## 694.

Without persistent storage:

```text
remove DB container
→ database data may disappear
```

With volume:

```text
remove container
→ volume remains
→ new container can reuse data
```

depending on commands and configuration.

---

# SECTION 609 — OUR POSTGRES VOLUME

## 695.

Our Compose configuration includes:

```yaml
volumes:
  - postgres_data:/var/lib/postgresql/data
```

and:

```yaml
volumes:
  postgres_data:
```

Mental model:

```text
PostgreSQL data directory
        ↓
Named Docker volume
        ↓
postgres_data
```

---

# SECTION 610 — LIST VOLUMES

## 696.

```bash
docker volume ls
```

Inspect:

```bash
docker volume inspect <volume>
```

---

# SECTION 611 — REMOVE VOLUME

## 697.

```bash
docker volume rm <volume>
```

This can permanently remove persisted data.

Use with care.

---

# SECTION 612 — DOCKER COMPOSE

## 698. What Is Compose?

Docker Compose defines and manages multi-container applications using YAML.

Example:

```text
Backend
Database
Cache
Message Broker
```

can eventually be managed together.

Configuration normally lives in:

```text
compose.yaml
docker-compose.yml
```

Our project currently has:

```text
docker-compose.yml
```

at repository root.

---

# SECTION 613 — WHY COMPOSE IS USEFUL

## 699.

Without Compose:

```bash
docker run ...
docker run ...
docker network ...
docker volume ...
```

Many manual commands.

With Compose:

```bash
docker compose up
```

can create:

```text
containers
network
volumes
port mappings
environment configuration
```

from one file.

---

# SECTION 614 — OUR CURRENT COMPOSE STRUCTURE

## 700.

Conceptually:

```yaml
services:
  postgres:
    image: postgres:16
    container_name: sdet-commerce-postgres
    environment:
      ...
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

Sensitive values are provided through external environment configuration.

---

# SECTION 615 — COMPOSE UP

## 701.

```bash
docker compose up
```

Starts configured services.

Foreground mode shows logs.

---

# SECTION 616 — COMPOSE UP DETACHED

## 702.

```bash
docker compose up -d
```

Starts services in background.

Then:

```bash
docker compose ps
```

checks status.

---

# SECTION 617 — COMPOSE PS

## 703.

```bash
docker compose ps
```

Shows services for current Compose project.

Useful to check:

```text
state
ports
health
```

if health checks are configured.

---

# SECTION 618 — COMPOSE LOGS

## 704.

All services:

```bash
docker compose logs
```

Specific:

```bash
docker compose logs postgres
```

Follow:

```bash
docker compose logs -f postgres
```

---

# SECTION 619 — COMPOSE STOP

## 705.

```bash
docker compose stop
```

Stops containers but does not remove Compose resources.

Later:

```bash
docker compose start
```

can restart them.

---

# SECTION 620 — COMPOSE DOWN

## 706.

```bash
docker compose down
```

Stops and removes Compose-created containers and network.

Named volumes are normally preserved unless explicitly requested for removal.

---

# SECTION 621 — DANGEROUS COMPOSE DOWN -v

## 707.

```bash
docker compose down -v
```

also removes Compose-managed named volumes.

For PostgreSQL this can mean:

```text
database data deleted
```

Use only when intentionally resetting database state.

---

# SECTION 622 — COMPOSE RESTART

## 708.

```bash
docker compose restart
```

Restarts services.

Specific service:

```bash
docker compose restart postgres
```

---

# SECTION 623 — COMPOSE EXEC

## 709.

Instead of container name:

```bash
docker compose exec postgres <command>
```

This targets Compose service.

Example concept:

```bash
docker compose exec postgres psql ...
```

Do not put real DB credentials into shared notes.

---

# SECTION 624 — DOCKER ENVIRONMENT VARIABLES

## 710.

Containers can receive environment variables.

Example:

```bash
docker run \
  -e MY_VARIABLE=value \
  <image>
```

Compose:

```yaml
environment:
  MY_VARIABLE: value
```

---

# SECTION 625 — POSTGRES ENV VARIABLES

## 711.

Official PostgreSQL image accepts initialization configuration such as:

```text
POSTGRES_DB
POSTGRES_USER
POSTGRES_PASSWORD
```

These help initialize the database on first startup of an empty data directory.

---

# SECTION 626 — IMPORTANT POSTGRES INITIALIZATION BEHAVIOR

## 712.

If PostgreSQL volume already contains initialized DB data:

```text
changing POSTGRES_USER or POSTGRES_PASSWORD
```

does not necessarily recreate existing users/passwords.

Why?

```text
initialization variables
→ mainly used during first initialization
```

This explains a common Docker/database confusion.

---

# SECTION 627 — CONTAINER ENV VS HOST ENV

## 713.

Host variable:

```bash
export DB_USERNAME=...
```

does not automatically mean every container receives it.

Compose must explicitly reference/pass it.

Concept:

```yaml
environment:
  POSTGRES_USER: ${DB_USERNAME}
```

Then Compose resolves host/project environment value.

---

# SECTION 628 — COMPOSE VARIABLE SUBSTITUTION

## 714.

Compose syntax:

```yaml
${VARIABLE}
```

Example:

```yaml
POSTGRES_USER: ${DB_USERNAME}
```

Default:

```yaml
${DB_NAME:-sdetcommerce}
```

Meaning:

```text
use DB_NAME if available
otherwise sdetcommerce
```

---

# SECTION 629 — .env WITH DOCKER COMPOSE

## 715.

Docker Compose can use `.env` for variable substitution depending on how Compose is invoked/configured.

Important:

```text
Compose .env behavior
≠ Spring Boot .env behavior
```

Do not mix these concepts.

---

# SECTION 630 — OUR SECRET STRATEGY

## 716.

Real secrets remain in local ignored configuration.

Git should contain only placeholders such as:

```text
.env.example
```

Good principle:

```text
Repository
→ variable names/placeholders

Runtime
→ actual secret values
```

---

# SECTION 631 — NEVER BAKE SECRETS INTO IMAGE

## 717.

Bad:

```dockerfile
ENV DB_PASSWORD=real-password
```

or copying secret `.env` into image.

Why dangerous?

```text
image history/layers
registry access
shared artifacts
CI logs
```

can expose secrets.

---

# SECTION 632 — DOCKERFILE

## 718. What Is Dockerfile?

A Dockerfile contains instructions to build an image.

Example concepts:

```dockerfile
FROM ...
WORKDIR ...
COPY ...
RUN ...
ENV ...
EXPOSE ...
CMD ...
ENTRYPOINT ...
```

---

# SECTION 633 — FROM

## 719.

```dockerfile
FROM <base-image>
```

Defines base image.

Example:

```dockerfile
FROM eclipse-temurin:17-jre
```

is conceptually a Java 17 runtime base.

Exact image selection should be decided when we implement backend containerization.

---

# SECTION 634 — WORKDIR

## 720.

```dockerfile
WORKDIR /app
```

Sets working directory for following instructions/runtime.

Concept:

```text
cd /app
```

inside image/container context.

---

# SECTION 635 — COPY

## 721.

```dockerfile
COPY source destination
```

Copies files from Docker build context into image.

Example concept:

```dockerfile
COPY target/app.jar app.jar
```

---

# SECTION 636 — RUN

## 722.

```dockerfile
RUN <command>
```

Executes during image build.

Example:

```dockerfile
RUN some-install-command
```

Important:

```text
RUN
→ build time
```

not normal container-start command.

---

# SECTION 637 — CMD

## 723.

`CMD` provides default command/arguments for container startup.

Example concept:

```dockerfile
CMD ["java", "-jar", "app.jar"]
```

It runs when container starts.

---

# SECTION 638 — ENTRYPOINT

## 724.

`ENTRYPOINT` defines the executable the container is intended to run.

Example concept:

```dockerfile
ENTRYPOINT ["java", "-jar", "app.jar"]
```

`CMD` and `ENTRYPOINT` can be combined.

For interview:

```text
RUN
→ image build

CMD/ENTRYPOINT
→ container runtime
```

---

# SECTION 639 — EXPOSE

## 725.

Example:

```dockerfile
EXPOSE 8080
```

Documents that application expects to listen on port 8080.

Important:

```text
EXPOSE does NOT automatically publish port to host
```

Host publishing still uses:

```text
-p
```

or Compose `ports`.

---

# SECTION 640 — ENV

## 726.

```dockerfile
ENV SOME_VAR=value
```

sets environment inside image/container.

Avoid embedding secrets.

Runtime variables are generally preferable for environment-specific configuration.

---

# SECTION 641 — ARG

## 727.

`ARG` defines build-time variables.

Concept:

```dockerfile
ARG APP_VERSION
```

Important:

```text
ARG
→ primarily build time

ENV
→ available in resulting image/runtime environment
```

Exact behavior should be understood before using either for sensitive information.

---

# SECTION 642 — BUILD CONTEXT

## 728.

Command:

```bash
docker build .
```

The `.` means current directory is build context.

Docker can access files in that context subject to `.dockerignore`.

Do not use unnecessarily huge build contexts.

---

# SECTION 643 — .dockerignore

## 729.

`.dockerignore` prevents unwanted files from entering build context.

Typical exclusions:

```text
.git
target
node_modules
.env
logs
reports
IDE files
```

depending on project.

Especially important:

```text
.env
```

must not accidentally be copied into images.

---

# SECTION 644 — BUILD AN IMAGE

## 730.

Generic:

```bash
docker build -t my-app:1.0 .
```

Breakdown:

```text
-t
→ tag/name

my-app
→ image name

1.0
→ tag

.
→ build context
```

---

# SECTION 645 — IMAGE LAYERS

## 731.

Docker images are built in layers.

Each Dockerfile instruction can contribute to image layers.

Concept:

```text
Base image
   ↓
Dependencies
   ↓
Application files
   ↓
Final image
```

Layer caching can make rebuilds faster.

---

# SECTION 646 — BUILD CACHE

## 732.

Docker may reuse unchanged layers.

This means Dockerfile instruction order matters for efficient builds.

Example concept:

```text
dependency-related files change rarely
source changes frequently
```

Separate appropriately to maximize cache reuse.

We will apply this when Dockerizing backend/frontend later.

---

# SECTION 647 — MULTI-STAGE BUILD

## 733.

Multi-stage builds use multiple `FROM` stages.

Example idea:

```text
Stage 1
→ compile/build

Stage 2
→ runtime-only image
```

Benefits:

```text
smaller final image
no build tools in runtime image
cleaner deployment artifact
```

Very useful for Spring Boot.

---

# SECTION 648 — SPRING BOOT DOCKER CONCEPT

## 734.

Future build could conceptually be:

```text
Source Code
   ↓
Maven Build Stage
   ↓
JAR
   ↓
Java Runtime Image
   ↓
Backend Container
```

Then:

```text
Backend Container
      ↓
PostgreSQL Container
```

through Docker network.

---

# SECTION 649 — BACKEND CONTAINER DB URL

## 735.

Current host-run backend can use:

```text
localhost:5432
```

Future containerized backend should conceptually use:

```text
postgres:5432
```

if PostgreSQL Compose service is named `postgres`.

Very important architecture change.

---

# SECTION 650 — DOCKER COMPOSE FUTURE ARCHITECTURE

## 736.

Future:

```text
Docker Compose
│
├── postgres
│
├── backend
└── frontend
```

Possible network flow:

```text
Browser
 ↓
Frontend
 ↓
Backend
 ↓
Postgres
```

Exact implementation will be decided later.

---

# SECTION 651 — DEPENDS_ON

## 737.

Compose supports:

```yaml
depends_on:
```

to express startup dependency ordering.

Example concept:

```yaml
backend:
  depends_on:
    - postgres
```

Important:

```text
started
≠ ready
```

A database container may start before PostgreSQL is ready to accept connections.

---

# SECTION 652 — STARTED VS READY

## 738.

This is critical.

```text
Container Running
≠
Application Ready
```

Example:

```text
Postgres process starts
      ↓
initializes database
      ↓
eventually accepts connections
```

Backend may fail if it connects too early.

---

# SECTION 653 — HEALTH CHECK

## 739.

Docker/Compose health checks can report whether a service is healthy.

Example concept:

```yaml
healthcheck:
  test: ...
  interval: ...
  timeout: ...
  retries: ...
```

Useful for:

```text
DB readiness
backend readiness
CI orchestration
```

---

# SECTION 654 — POSTGRES HEALTH CHECK CONCEPT

## 740.

PostgreSQL includes tools such as:

```text
pg_isready
```

that can help verify DB readiness.

We can add a proper health check later during full application Dockerization.

Do not add complexity before it solves a real orchestration issue.

---

# SECTION 655 — RESTART POLICY

## 741.

Compose can configure restart behavior.

Conceptual examples:

```text
no
always
on-failure
unless-stopped
```

Restart policies help runtime resilience but can also hide crash loops if you only see a container continuously restarting.

Always inspect logs.

---

# SECTION 656 — RESTART LOOP

## 742.

Symptom:

```text
Restarting
Restarting
Restarting
```

Possible causes:

```text
main process crashes
missing config
DB unavailable
startup command wrong
permissions
```

Check:

```bash
docker ps -a
docker logs <container>
```

---

# SECTION 657 — EXIT CODE

## 743.

Container process exit status matters.

Inspect:

```bash
docker inspect <container>
```

or:

```bash
docker ps -a
```

Exit code:

```text
0
→ normally success

non-zero
→ failure
```

depending on application.

---

# SECTION 658 — CONTAINER IMMEDIATELY EXITS

## 744.

Possible reasons:

```text
startup command completed
startup command wrong
application crashed
missing environment variable
port/config issue
permission error
```

Container is not a VM that stays alive automatically.

It stays alive while its main process runs.

---

# SECTION 659 — FILESYSTEM INSIDE CONTAINER

## 745.

Container filesystem is isolated from host.

Host file:

```text
~/project/file.txt
```

does not automatically exist inside container.

Need:

```text
COPY
volume
bind mount
```

depending on requirement.

---

# SECTION 660 — BIND MOUNT

## 746.

Bind mount maps a host path into container.

Concept:

```text
Host Directory
     ↕
Container Directory
```

Example syntax concept:

```bash
-v /host/path:/container/path
```

Common in:

```text
development
source mounting
test artifacts
configuration
```

---

# SECTION 661 — VOLUME VS BIND MOUNT

## 747.

Named volume:

```text
Docker-managed persistent storage
```

Bind mount:

```text
specific host filesystem path mapped into container
```

For PostgreSQL persistent local data:

```text
named volume
```

is a good common approach.

---

# SECTION 662 — READ-ONLY MOUNT

## 748.

Some mounts can be read-only.

Concept:

```text
configuration mounted
but application cannot modify it
```

Useful security principle where write access is unnecessary.

---

# SECTION 663 — CONTAINER USER

## 749.

Containers run processes as a user.

Not every container should run as:

```text
root
```

Production container hardening commonly prefers:

```text
non-root runtime user
```

where possible.

For our portfolio later, this can be a security improvement.

---

# SECTION 664 — DOCKER SECURITY BASICS

## 750.

Avoid:

```text
hardcoded secrets
unnecessary root
privileged containers
untrusted images
latest tag everywhere
mounting sensitive host directories
exposing unnecessary ports
```

Prefer:

```text
minimal images
known versions
least privilege
secret externalization
limited network exposure
```

---

# SECTION 665 — IMAGE VULNERABILITIES

## 751.

Images contain:

```text
OS packages
runtime
libraries
application
```

Any of these may have vulnerabilities.

Production workflows may use:

```text
container vulnerability scanners
dependency scanners
registry policies
```

This will connect later to CI/CD security.

---

# SECTION 666 — IMAGE SIZE

## 752.

Large images can cause:

```text
slow pull
slow CI
larger attack surface
more storage
```

Multi-stage builds and appropriate runtime base images help.

But:

```text
smallest possible
```

is not always automatically best if maintainability/debuggability suffers.

---

# SECTION 667 — DOCKER TAGGING

## 753.

Bad deployment habit:

```text
only latest
```

Better conceptual release tags:

```text
app:1.0.0
app:1.1.0
app:<commit-sha>
```

This improves:

```text
traceability
rollback
reproducibility
```

---

# SECTION 668 — IMMUTABLE IMAGE PRINCIPLE

## 754.

Better deployment model:

```text
Build image once
      ↓
Promote same image
      ↓
Different runtime configuration
```

instead of rebuilding different application code for each environment.

Concept:

```text
Same Artifact
DEV → QA → PROD
```

with environment-specific configuration externalized.

---

# SECTION 669 — DOCKER AND CI/CD

## 755.

CI pipeline can:

```text
checkout code
build application
run tests
build Docker image
scan image
push registry
deploy image
run smoke/regression
```

Docker improves consistency between:

```text
developer environment
CI
deployment
```

---

# SECTION 670 — TEST AUTOMATION IN CONTAINERS

## 756.

Possible model:

```text
API Automation Container
        ↓
Backend Container
        ↓
Database Container
```

Advantages:

```text
reproducibility
isolation
CI portability
clean environment
```

But containerizing tests should solve a real problem, not just add buzzwords.

---

# SECTION 671 — UI AUTOMATION CONTAINERS

## 757.

Playwright can also run in containers.

Potential benefits:

```text
browser dependencies packaged
consistent CI environment
reproducible versions
```

We will decide exact strategy when Playwright phase is implemented.

---

# SECTION 672 — DOCKER COMPOSE FOR INTEGRATION TESTING

## 758.

Future concept:

```text
docker compose up
     ↓
DB ready
     ↓
backend ready
     ↓
run API tests
     ↓
collect reports
     ↓
docker compose down
```

This can create predictable integration environments.

---

# SECTION 673 — TESTCONTAINERS CONCEPT

## 759.

Testcontainers is a library approach that allows tests to dynamically start Docker containers.

Example uses:

```text
PostgreSQL
Kafka
Redis
```

It can improve integration-test isolation.

We are not adding it now because current architecture already works.

Know the concept for interviews.

---

# SECTION 674 — DOCKER COMPOSE VS TESTCONTAINERS

## 760.

Compose:

```text
environment-level orchestration
multiple services
developer/CI setup
```

Testcontainers:

```text
test-code-driven disposable dependencies
integration tests
```

Both can be useful.

---

# SECTION 675 — KUBERNETES RELATIONSHIP

## 761.

Docker/container knowledge is foundation for understanding Kubernetes.

Simplified:

```text
Docker/Container
→ package/run application

Kubernetes
→ orchestrate containers at scale
```

Do not say:

```text
Docker and Kubernetes are the same
```

They solve different levels of problem.

---

# SECTION 676 — DOCKER COMMAND: PORTS

## 762.

Show container port mappings:

```bash
docker port <container>
```

Useful if unsure what host port maps to container.

---

# SECTION 677 — DOCKER COMMAND: TOP

## 763.

```bash
docker top <container>
```

Shows processes running in container.

Useful for diagnostics.

---

# SECTION 678 — DOCKER CP

## 764.

Copy between host and container:

```bash
docker cp <container>:/path/file ./file
```

or host → container.

Useful occasionally for:

```text
logs
reports
diagnostic files
```

Not ideal as a normal deployment/configuration strategy.

---

# SECTION 679 — DOCKER SYSTEM DF

## 765.

```bash
docker system df
```

Shows Docker disk usage:

```text
images
containers
volumes
build cache
```

Useful when Docker consumes large disk space.

---

# SECTION 680 — DOCKER PRUNE

## 766.

Commands such as:

```bash
docker system prune
```

remove unused resources.

Potentially destructive variants exist.

Never run cleanup commands blindly on machines with important local environments.

Inspect first.

---

# SECTION 681 — DANGLING IMAGES

## 767.

Repeated builds can leave unused intermediate/untagged images.

These can consume disk.

Inspect:

```bash
docker images
docker system df
```

before cleanup.

---

# SECTION 682 — OUR CURRENT POSTGRES STARTUP

## 768.

From repository root:

```bash
docker compose up -d
```

Then:

```bash
docker compose ps
```

Then backend:

```bash
./backend/run-local.sh
```

Current architecture:

```text
Backend
→ running directly on Mac

PostgreSQL
→ running in Docker
```

This distinction matters.

---

# SECTION 683 — OUR CURRENT LOCAL ARCHITECTURE

## 769.

```text
Mac Host
│
├── Java 17
├── Spring Boot backend
│      ↓
│   localhost:5432
│
└── Docker
       ↓
   PostgreSQL Container
       ↓
   port 5432
       ↓
   postgres_data volume
```

Backend is NOT currently running inside Docker.

Only database is containerized.

---

# SECTION 684 — FUTURE ARCHITECTURE

## 770.

Later:

```text
Docker Compose
│
├── postgres
├── backend
└── frontend
```

Then:

```text
backend → postgres:5432
```

not:

```text
backend → localhost:5432
```

---

# SECTION 685 — DATABASE ACCESS FROM HOST

## 771.

Current:

```text
Host Spring Boot
     ↓
localhost:5432
     ↓
Docker published port
     ↓
PostgreSQL
```

This works because Compose maps:

```text
host 5432
→ container 5432
```

---

# SECTION 686 — DATABASE ACCESS FROM CONTAINER

## 772.

Future backend container:

```text
Backend Container
     ↓
Docker network
     ↓
postgres:5432
```

No need to travel through host-published DB port for normal container-to-container communication.

---

# SECTION 687 — DATABASE PORT EXPOSURE

## 773.

For local development we expose:

```text
5432
```

to host.

In a production-style architecture, database usually should not be publicly exposed unnecessarily.

Application communicates through private/internal networking.

This connects later to AWS RDS/security groups.

---

# SECTION 688 — DOCKER TROUBLESHOOTING: DB NOT RUNNING

## 774.

Check:

```bash
docker compose ps
```

If not running:

```bash
docker compose up -d
```

Then:

```bash
docker compose logs postgres
```

---

# SECTION 689 — DOCKER TROUBLESHOOTING: CONTAINER EXITED

## 775.

```bash
docker ps -a
```

Then:

```bash
docker logs sdet-commerce-postgres
```

Then inspect:

```text
exit reason
environment
volume
port
permissions
startup
```

---

# SECTION 690 — DOCKER TROUBLESHOOTING: PORT CONFLICT

## 776.

If Compose reports:

```text
port already allocated
```

check:

```bash
lsof -i :5432
```

Possible:

```text
local PostgreSQL already using port
another container
```

Do not randomly kill process.

Identify first.

---

# SECTION 691 — DOCKER TROUBLESHOOTING: BACKEND CANNOT CONNECT TO DB

## 777.

Current host architecture checks:

```text
1. Is PostgreSQL container running?
2. Is 5432 published?
3. Is DB URL correct?
4. Are runtime env variables loaded?
5. Are credentials consistent with initialized DB?
6. What do PostgreSQL logs show?
7. What does Spring Boot error say?
```

---

# SECTION 692 — DOCKER TROUBLESHOOTING: WRONG LOCALHOST

## 778.

Scenario:

```text
backend moved into Docker
DB connection refused at localhost:5432
```

Likely question:

```text
Is backend trying to connect to itself?
```

Fix architecture/configuration:

```text
use Compose service DNS name
```

such as:

```text
postgres
```

when that is the service name.

---

# SECTION 693 — DOCKER TROUBLESHOOTING: DATA STILL EXISTS

## 779.

User runs:

```bash
docker compose down
docker compose up -d
```

but old DB data remains.

Why?

```text
named volume remains
```

This is expected.

---

# SECTION 694 — DOCKER TROUBLESHOOTING: WANT CLEAN DB

## 780.

Conceptually:

```text
remove DB volume
→ reinitialize database
```

But this destroys local data.

Therefore first confirm:

```text
Is reset actually desired?
```

Never use:

```bash
docker compose down -v
```

casually.

---

# SECTION 695 — DOCKER TROUBLESHOOTING: ENV CHANGED BUT DB PASSWORD DIDN'T

## 781.

Likely reason:

```text
existing PostgreSQL data volume was already initialized
```

Changing Compose initialization environment variables does not automatically rewrite existing database credentials.

Need deliberate DB credential change or controlled reinitialization.

---

# SECTION 696 — DOCKER TROUBLESHOOTING: IMAGE NOT FOUND

## 782.

Possible:

```text
wrong image name
wrong tag
private registry auth
network/proxy
```

Try:

```bash
docker pull <image>
```

and inspect actual error.

---

# SECTION 697 — DOCKER TROUBLESHOOTING: PULL FAILS CORPORATE NETWORK

## 783.

Potential:

```text
proxy
TLS certificate
registry blocked
DNS
authentication
```

Connects directly to Part 4 networking knowledge.

---

# SECTION 698 — DOCKER TROUBLESHOOTING: WORKS ON INTEL, FAILS ARM

## 784.

Possible architecture mismatch:

```text
amd64
vs
arm64
```

Our Mac uses Apple Silicon architecture.

Check:

```bash
uname -m
```

Container images may be:

```text
multi-architecture
or
architecture-specific
```

---

# SECTION 699 — MULTI-ARCH IMAGE

## 785.

A multi-platform image can provide variants for architectures such as:

```text
linux/amd64
linux/arm64
```

Docker selects compatible variant when available.

Architecture issues are more common with:

```text
native dependencies
older images
specialized tooling
```

---

# SECTION 700 — PLATFORM OPTION

## 786.

Docker can sometimes be told to use a specific platform:

```bash
docker run --platform=linux/amd64 ...
```

But this may involve emulation and performance overhead.

Do not force platform unless needed.

---

# SECTION 701 — BUILD ARGUMENT VS RUNTIME VARIABLE

## 787.

Remember:

```text
Build ARG
→ image build stage

Runtime ENV
→ container execution
```

Environment-specific URLs/passwords usually belong at runtime, not baked into image.

---

# SECTION 702 — IMAGE VS CONFIGURATION

## 788.

Strong architecture principle:

```text
IMAGE
→ application/runtime

ENVIRONMENT
→ configuration/secrets
```

This makes the same image reusable.

---

# SECTION 703 — DOCKER LOGGING PRINCIPLE

## 789.

Containers should generally emit logs to:

```text
stdout
stderr
```

Then Docker/platform can collect them.

Inspect:

```bash
docker logs
```

instead of relying only on files hidden inside container.

---

# SECTION 704 — EPHEMERAL CONTAINERS

## 790.

Containers should be treated as replaceable.

Concept:

```text
Container broken?
→ replace it
```

Persistent state should live in:

```text
volume
external DB/storage
```

rather than depending on one container instance.

---

# SECTION 705 — PET VS CATTLE CONCEPT

## 791.

Traditional server:

```text
carefully maintain one machine
```

Container-oriented approach:

```text
replace instances from known image/config
```

This is sometimes summarized as:

```text
cattle, not pets
```

Know conceptually; no need to use the phrase in interviews unless useful.

---

# SECTION 706 — HEALTH VS RUNNING

## 792.

Container:

```text
running
```

does NOT guarantee:

```text
application healthy
```

Example:

```text
Java process alive
but API cannot connect to DB
```

Health checks/readiness validation are stronger than process existence alone.

---

# SECTION 707 — DOCKER AND TEST ISOLATION

## 793.

Containers help create isolated dependencies.

Example:

```text
Test Suite A
→ temporary PostgreSQL

Test Suite B
→ separate PostgreSQL
```

reducing data collision.

This is one reason Testcontainers is popular in integration testing.

---

# SECTION 708 — CONTAINER DATA COLLISION

## 794.

Docker alone does NOT automatically prevent test-data collisions.

If multiple tests use:

```text
same database
same records
same credentials
```

they can still interfere.

Need:

```text
test data strategy
cleanup
unique identifiers
isolation
```

---

# SECTION 709 — CONTAINER STARTUP COST

## 795.

Containers are lightweight but not free.

Large integration environments can take time for:

```text
image pull
container startup
DB initialization
application readiness
```

CI strategy should account for this.

---

# SECTION 710 — DOCKER NETWORK DEBUGGING

## 796.

For container-to-container issue:

Check:

```bash
docker network ls
```

then:

```bash
docker inspect <container>
```

Verify:

```text
same network
service name
port
application bind address
```

---

# SECTION 711 — CURL INSIDE CONTAINER

## 797.

If curl exists inside container:

```bash
docker exec -it <container> curl http://other-service:8080/...
```

can test network from container perspective.

But not every minimal image includes curl.

Do not install debug tools into production image just for convenience without considering image/security impact.

---

# SECTION 712 — HOST.DOCKER.INTERNAL

## 798.

Docker Desktop commonly provides:

```text
host.docker.internal
```

for containers to reach services running on the host.

Example concept:

```text
container
→ host.docker.internal:8080
→ Spring Boot running on Mac
```

Useful in local mixed container/host setups.

Do not assume the same behavior exists identically in every production Linux environment.

---

# SECTION 713 — OUR CURRENT MIXED ARCHITECTURE

## 799.

Current:

```text
Spring Boot
→ Host

PostgreSQL
→ Docker Container
```

If in future API automation itself runs in Docker while backend remains on Mac, then:

```text
localhost:8080
```

inside automation container would NOT point to Mac backend.

A local Docker Desktop solution could use:

```text
host.docker.internal:8080
```

or containerize backend too.

---

# SECTION 714 — DOCKER DESKTOP

## 800.

On macOS, containers cannot directly share the macOS kernel like native Linux containers do.

Docker Desktop runs Linux container infrastructure through a lightweight virtualized environment.

For daily usage Docker abstracts most of this.

Useful interview nuance:

```text
Linux containers depend on Linux kernel features.
```

---

# SECTION 715 — DOCKERFILE VS COMPOSE

## 801.

Dockerfile:

```text
How to build ONE image
```

Compose:

```text
How to run/connect MULTIPLE services
```

Example:

```text
Dockerfile
→ backend image instructions

docker-compose.yml
→ backend + database + frontend orchestration
```

---

# SECTION 716 — IMAGE VS CONTAINER VS COMPOSE

## 802.

Mental model:

```text
Dockerfile
    ↓
Image
    ↓
Container
```

Compose:

```text
coordinates multiple containers
```

---

# SECTION 717 — DOCKERFILE VS IMAGE

## 803.

```text
Dockerfile
→ recipe

Image
→ built package

Container
→ running instance
```

Classic interview question.

---

# SECTION 718 — INTERVIEW: WHAT IS DOCKER?

## 804. Strong Answer

> "Docker packages applications and their runtime dependencies into images and runs them as isolated containers. In testing, I use containers to create reproducible dependencies such as PostgreSQL and later they can also provide consistent backend, UI-automation and CI environments."

---

# SECTION 719 — INTERVIEW: IMAGE VS CONTAINER

## 805.

> "An image is an immutable packaged template containing the filesystem and runtime instructions, while a container is a running or stopped instance created from that image. Multiple containers can be created from the same image."

---

# SECTION 720 — INTERVIEW: CONTAINER VS VM

## 806.

> "A VM virtualizes hardware and normally runs a complete guest operating system, while containers isolate processes while sharing the host's underlying kernel environment. Containers are therefore generally lighter and faster to start, although they provide a different isolation model from VMs."

---

# SECTION 721 — INTERVIEW: DOCKERFILE VS COMPOSE

## 807.

> "A Dockerfile defines how an image is built, while Docker Compose defines how multiple services are configured and run together, including their networks, volumes, environment variables and port mappings."

---

# SECTION 722 — INTERVIEW: EXPOSE VS PORT MAPPING

## 808.

> "`EXPOSE` documents the port an image/application expects to use but does not publish it to the host. Port publishing such as `-p 8080:8080`, or a Compose `ports` mapping, makes the container port accessible through a host port."

---

# SECTION 723 — INTERVIEW: VOLUME

## 809.

> "A Docker volume provides persistent storage outside the writable lifecycle of a specific container. In my project PostgreSQL stores its data in a named volume so recreating the database container does not automatically remove the database."

---

# SECTION 724 — INTERVIEW: WHY LOCALHOST FAILS BETWEEN CONTAINERS

## 810.

> "Inside a container, localhost refers to that container itself. If my backend and database are separate Compose services, the backend should communicate using Docker service discovery, for example the database service name and container port, rather than localhost."

---

# SECTION 725 — INTERVIEW: CONTAINER RUNNING BUT APP NOT READY

## 811.

> "A running container only proves its main process is alive. The application may still be initializing or unhealthy. I use logs, port checks and ideally health/readiness endpoints rather than treating container state as application readiness."

---

# SECTION 726 — INTERVIEW: COMPOSE DOWN VS DOWN -V

## 812.

> "`docker compose down` removes Compose containers and networks while normally preserving named volumes. Adding `-v` also removes the volumes, which can delete persistent database data, so I only use it when I intentionally need a clean reset."

---

# SECTION 727 — INTERVIEW: WHY ENV CHANGE DIDN'T CHANGE POSTGRES PASSWORD

## 813.

> "The official PostgreSQL image uses initialization variables when creating a new database data directory. If an existing persistent volume is reused, simply changing the Docker environment variable does not automatically recreate users or reset the existing database password."

---

# SECTION 728 — INTERVIEW: RUN VS CMD

## 814.

> "`RUN` executes while building the image and creates part of the image layer, whereas `CMD` provides a default command when a container starts."

---

# SECTION 729 — INTERVIEW: CMD VS ENTRYPOINT

## 815.

> "`ENTRYPOINT` defines the primary executable the container is intended to run, while `CMD` provides default command or arguments and can work together with ENTRYPOINT. The exact behavior depends on how they are defined and how runtime arguments are supplied."

---

# SECTION 730 — INTERVIEW: WHY MULTI-STAGE BUILDS?

## 816.

> "Multi-stage builds separate build tooling from the final runtime image. For a Spring Boot service, Maven and the JDK can exist in the build stage while the final image contains only the runtime and packaged JAR, reducing image size and attack surface."

---

# SECTION 731 — INTERVIEW: HOW DO YOU TROUBLESHOOT A FAILED CONTAINER?

## 817.

> "I first check container state with `docker ps -a`, then inspect its logs and exit reason. I verify environment variables, mounts, port mappings and networking. If it depends on another service, I verify that dependency is actually ready and reachable rather than restarting the container blindly."

---

# SECTION 732 — SENIOR SDET SCENARIO: TESTS FAIL ONLY IN DOCKER

## 818.

Check:

```text
base URL
localhost assumptions
container DNS
ports
environment variables
file paths
permissions
architecture
certificates
```

A common cause:

```text
localhost points to wrong place
```

---

# SECTION 733 — SENIOR SDET SCENARIO: DB DATA DISAPPEARS

## 819.

Investigate:

```text
Was a named volume used?
Was `docker compose down -v` executed?
Was volume renamed?
Was a different Compose project created?
Was database writing to expected data directory?
```

---

# SECTION 734 — SENIOR SDET SCENARIO: OLD DATA RETURNS AFTER RECREATE

## 820.

Likely:

```text
persistent named volume reused
```

This is expected Docker behavior.

---

# SECTION 735 — SENIOR SDET SCENARIO: BACKEND STARTS BEFORE DB

## 821.

Weak assumption:

```text
depends_on means database ready
```

Better:

```text
use DB health/readiness
application retry strategy
CI readiness validation
```

depending on architecture.

---

# SECTION 736 — SENIOR SDET SCENARIO: CI DOCKER BUILD SLOW

## 822.

Investigate:

```text
image size
build context
cache
Dockerfile layer ordering
dependency downloads
base image pull
network
```

Do not only upgrade runner size first.

---

# SECTION 737 — SENIOR SDET SCENARIO: IMAGE WORKS LOCALLY, FAILS CI

## 823.

Compare:

```text
CPU architecture
build arguments
secrets
registry access
Docker version
build context
case-sensitive file paths
network/proxy
```

---

# SECTION 738 — SENIOR SDET SCENARIO: PORT ALREADY ALLOCATED

## 824.

Check:

```bash
lsof -i :5432
```

and:

```bash
docker ps
```

Then identify:

```text
local process?
another container?
existing project?
```

Resolve intentionally.

---

# SECTION 739 — SENIOR SDET SCENARIO: CONTAINER CANNOT REACH HOST BACKEND

## 825.

Inside container:

```text
localhost
→ container
```

For Docker Desktop local development, investigate:

```text
host.docker.internal
```

or move backend into same Docker network.

---

# SECTION 740 — DOCKER RAPID FIRE

## 826. List running containers?

```bash
docker ps
```

## 827. List all containers?

```bash
docker ps -a
```

## 828. List images?

```bash
docker images
```

## 829. Start container?

```bash
docker start <container>
```

## 830. Stop container?

```bash
docker stop <container>
```

## 831. Remove container?

```bash
docker rm <container>
```

## 832. Container logs?

```bash
docker logs <container>
```

## 833. Follow logs?

```bash
docker logs -f <container>
```

## 834. Execute inside container?

```bash
docker exec -it <container> <command>
```

## 835. Inspect container?

```bash
docker inspect <container>
```

## 836. Resource usage?

```bash
docker stats
```

## 837. List networks?

```bash
docker network ls
```

## 838. List volumes?

```bash
docker volume ls
```

## 839. Start Compose?

```bash
docker compose up -d
```

## 840. Compose status?

```bash
docker compose ps
```

## 841. Compose logs?

```bash
docker compose logs
```

## 842. Stop/remove Compose containers?

```bash
docker compose down
```

## 843. Also delete volumes?

```bash
docker compose down -v
```

Use only intentionally.

---

# SECTION 741 — DOCKER COMMAND MEMORY MAP

## 844.

```text
docker pull
→ download image

docker build
→ build image

docker run
→ create + start container

docker ps
→ running containers

docker ps -a
→ all containers

docker logs
→ container output

docker exec
→ command inside running container

docker inspect
→ detailed metadata

docker stop
→ stop container

docker rm
→ remove container

docker rmi
→ remove image
```

---

# SECTION 742 — COMPOSE COMMAND MEMORY MAP

## 845.

```text
docker compose up -d
→ create/start services

docker compose ps
→ status

docker compose logs
→ logs

docker compose exec
→ execute command in service

docker compose stop
→ stop only

docker compose start
→ start existing

docker compose restart
→ restart

docker compose down
→ remove containers/network

docker compose down -v
→ also delete volumes/data
```

---

# SECTION 743 — HOST VS CONTAINER MEMORY MAP

## 846.

Current:

```text
Host Spring Boot
    ↓
localhost:5432
    ↓
Published Docker Port
    ↓
Postgres Container
```

Future:

```text
Backend Container
    ↓
postgres:5432
    ↓
Postgres Container
```

Rule:

```text
localhost always means "this current network namespace/machine/container"
```

---

# SECTION 744 — DOCKER STORAGE MEMORY MAP

## 847.

```text
Container writable layer
→ temporary/container-specific

Named volume
→ persistent Docker-managed data

Bind mount
→ host path mapped into container
```

---

# SECTION 745 — DOCKER BUILD MEMORY MAP

## 848.

```text
Dockerfile
    ↓
docker build
    ↓
Image Layers
    ↓
Tagged Image
    ↓
docker run
    ↓
Container
```

---

# SECTION 746 — FUTURE SPRING BOOT CONTAINERIZATION FLOW

## 849.

```text
Source
  ↓
Maven Build
  ↓
JAR
  ↓
Docker Image
  ↓
Backend Container
  ↓
Docker Network
  ↓
Postgres Container
```

Runtime configuration:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

should remain externalized.

---

# SECTION 747 — FUTURE FULL PROJECT FLOW

## 850.

```text
Browser
  ↓
React Container
  ↓
Spring Boot Container
  ↓
PostgreSQL Container
```

Automation:

```text
REST Assured
      ↓
Backend
```

Later:

```text
Playwright
    ↓
React UI
    ↓
Backend
```

CI:

```text
GitHub Actions
      ↓
Build
      ↓
Containers
      ↓
Tests
      ↓
Allure / Artifacts
```

---

# SECTION 748 — WHAT WE USE TODAY

## 851.

Currently implemented:

```text
Docker Desktop
Docker Compose
PostgreSQL 16 container
named PostgreSQL volume
port mapping
environment variable substitution
```

Current backend:

```text
runs on host
```

Current API automation:

```text
runs on host
```

Important:

Do not claim yet that:

```text
backend is Dockerized
frontend is Dockerized
API automation runs in Docker
```

Those are future phases.

---

# SECTION 749 — WHAT WE WILL IMPLEMENT LATER

## 852.

Planned:

```text
backend Dockerfile
frontend Dockerfile
full Compose environment
health checks
container-to-container networking
CI container workflow
```

These should be added when implementation phase reaches Dockerization.

---

# SECTION 750 — ONE-MINUTE DOCKER INTERVIEW ANSWER

## 853.

> "I use Docker to create reproducible test and application dependencies. In my current SDET portfolio project PostgreSQL 16 runs through Docker Compose with a named volume for persistence and environment-based configuration, while the Spring Boot backend currently runs on the host. I understand image versus container, port mapping, volumes, Compose networking and the important localhost difference between host and containers. When we fully containerize the project, the backend will communicate with PostgreSQL using the Compose service name rather than localhost. I also use container state, logs, networking and health/readiness checks to troubleshoot failures systematically."

---

# SECTION 751 — FINAL DOCKER TROUBLESHOOTING FLOW

## 854.

```text
DOCKER ISSUE
    ↓
Is Docker running?
    ↓
Image exists/pulls?
    ↓
Container created?
    ↓
Container running?
    ↓
Main process healthy?
    ↓
Logs?
    ↓
Environment correct?
    ↓
Port mapping correct?
    ↓
Network correct?
    ↓
Service DNS correct?
    ↓
Volume/mount correct?
    ↓
Dependency ready?
    ↓
Application HTTP/DB behavior?
```

---

# SECTION 752 — FINAL SENIOR SDET PRINCIPLE

## 855.

Do not say:

```text
"Docker is not working."
```

Say:

```text
"The PostgreSQL container exits during initialization."
```

or:

```text
"The backend container is healthy but cannot resolve the `postgres`
service on the Compose network."
```

or:

```text
"The database data persists because the named volume is still attached."
```

Good troubleshooting is:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
VERIFY
   ↓
FIX / REPORT
```

---

# END OF PART 5 — DOCKER & DOCKER COMPOSE DEEP DIVE FOR SDET

Next:

**PART 6 — NODE.JS, NPM, PACKAGE.JSON & JAVASCRIPT/TYPESCRIPT TOOLING FOR SDET**

---

# PART 6 — NODE.JS, NPM, PACKAGE.JSON & JAVASCRIPT/TYPESCRIPT TOOLING FOR SDET

# SECTION 753 — WHY NODE.JS MATTERS FOR SDET

Modern SDET roles frequently use JavaScript or TypeScript tooling for:

```text
Playwright
frontend testing
React applications
API utilities
test-data scripts
CI tooling
reporting tools
build tools
```

For our project, Node.js knowledge will directly support:

```text
React + TypeScript frontend
Playwright + TypeScript automation
future npm-based CI commands
```

Important:

```text
Node.js
≠ JavaScript language itself

npm
≠ Node.js

TypeScript
≠ completely separate runtime
```

We need to understand how these pieces connect.

---

# SECTION 754 — WHAT IS JAVASCRIPT?

## 856. Simple Definition

JavaScript is a programming language.

It is widely used in:

```text
web browsers
frontend applications
servers through Node.js
automation frameworks
build tools
```

Example:

```javascript
const name = "Animesh";
console.log(name);
```

---

# SECTION 755 — JAVASCRIPT IN BROWSER

## 857.

Traditionally JavaScript executes inside browsers.

Example:

```text
Chrome
Safari
Firefox
Edge
```

Browser provides APIs such as:

```text
document
window
localStorage
fetch
```

These APIs are provided by the browser environment.

---

# SECTION 756 — JAVASCRIPT OUTSIDE BROWSER

## 858.

JavaScript can also run outside the browser.

One common runtime is:

```text
Node.js
```

This allows JavaScript to be used for:

```text
servers
CLI tools
automation
build systems
scripts
```

---

# SECTION 757 — WHAT IS NODE.JS?

## 859. Definition

Node.js is a JavaScript runtime that allows JavaScript to execute outside a web browser.

Simple:

```text
JavaScript Code
      ↓
Node.js Runtime
      ↓
Operating System
```

Node.js is not:

```text
a programming language
```

The language is JavaScript.

---

# SECTION 758 — NODE.JS MENTAL MODEL

## 860.

For Java:

```text
Java source
   ↓
JDK / JVM
   ↓
execution
```

For JavaScript tooling:

```text
JavaScript
   ↓
Node.js runtime
   ↓
execution
```

This comparison is not technically identical internally, but it is useful as a learning model.

---

# SECTION 759 — WHY PLAYWRIGHT NEEDS NODE.JS

## 861.

Playwright's JavaScript/TypeScript ecosystem is installed and executed through Node.js tooling.

Concept:

```text
Playwright Test Code
       ↓
Node.js
       ↓
Playwright Library
       ↓
Browser
```

When we implement Playwright later, Node.js becomes part of our automation runtime.

---

# SECTION 760 — WHY REACT NEEDS NODE.JS DURING DEVELOPMENT

## 862.

React frontend development commonly uses Node.js tooling for:

```text
dependency installation
development server
TypeScript compilation/transformation
bundling
production build
```

Important nuance:

The final JavaScript delivered to a user's browser does not mean Node.js itself must execute inside that browser.

Node.js is heavily used in the development/build tooling.

---

# SECTION 761 — CHECK NODE VERSION

## 863.

Before starting frontend/Playwright implementation, check:

```bash
node --version
```

or:

```bash
node -v
```

Example output format:

```text
vXX.X.X
```

We should verify the actual installed version instead of assuming it.

---

# SECTION 762 — CHECK NPM VERSION

## 864.

```bash
npm --version
```

or:

```bash
npm -v
```

Again, record the actual version when we start implementation.

---

# SECTION 763 — WHICH NODE

## 865.

To identify executable being used:

```bash
which node
```

For npm:

```bash
which npm
```

Useful when:

```text
different terminals use different Node versions
IDE behaves differently
CI uses another runtime
```

---

# SECTION 764 — WHAT IS NPM?

## 866.

npm is the package manager commonly distributed with Node.js.

It is used to:

```text
install packages
manage dependencies
run scripts
publish packages
manage project metadata
```

Typical commands:

```bash
npm install
npm test
npm run build
```

---

# SECTION 765 — NODE VS NPM

## 867.

```text
Node.js
→ JavaScript runtime

npm
→ package/dependency/script management tool
```

Similar mental comparison:

```text
Java world:
JDK + Maven

JavaScript world:
Node.js + npm
```

Not exactly equivalent internally, but useful conceptually.

---

# SECTION 766 — NPM REGISTRY

## 868.

npm packages are commonly downloaded from a package registry.

Default public ecosystem:

```text
npm registry
```

Enterprise environments may use private registries such as:

```text
Artifactory
Nexus
private npm registries
```

Flow:

```text
package.json
    ↓
npm
    ↓
registry
    ↓
package downloaded
```

---

# SECTION 767 — WHAT IS A PACKAGE?

## 869.

A package is reusable JavaScript/TypeScript code distributed with package metadata.

Examples conceptually:

```text
Playwright
Axios
React
TypeScript
ESLint
```

A project itself can also be considered a package in npm terminology.

---

# SECTION 768 — package.json

## 870. Core File

A Node project usually has:

```text
package.json
```

It contains project metadata and configuration such as:

```text
name
version
scripts
dependencies
devDependencies
```

Example:

```json
{
  "name": "sample-project",
  "version": "1.0.0",
  "scripts": {
    "test": "playwright test"
  }
}
```

---

# SECTION 769 — package.json IS SIMILAR TO WHAT IN MAVEN?

## 871.

For learning:

```text
pom.xml
→ Maven project/dependencies/plugins/build metadata

package.json
→ Node project/dependencies/scripts/package metadata
```

They are not structurally identical.

But both are central project-management files.

---

# SECTION 770 — CREATE package.json

## 872.

A new npm project can be initialized with:

```bash
npm init
```

Interactive mode asks questions.

Quick defaults:

```bash
npm init -y
```

This creates:

```text
package.json
```

---

# SECTION 771 — PACKAGE NAME

## 873.

Inside `package.json`:

```json
{
  "name": "ui-automation"
}
```

Package names generally should be:

```text
lowercase
simple
valid according to npm naming rules
```

---

# SECTION 772 — PACKAGE VERSION

## 874.

Example:

```json
{
  "version": "1.0.0"
}
```

Commonly follows Semantic Versioning:

```text
MAJOR.MINOR.PATCH
```

Example:

```text
1.4.2
```

---

# SECTION 773 — SEMANTIC VERSIONING

## 875.

General idea:

```text
MAJOR
→ breaking change

MINOR
→ backward-compatible feature

PATCH
→ backward-compatible fix
```

Example:

```text
2.3.5
```

means:

```text
Major = 2
Minor = 3
Patch = 5
```

Actual package versioning practices can vary.

---

# SECTION 774 — DEPENDENCIES

## 876.

In `package.json`:

```json
"dependencies": {
  "some-package": "^1.0.0"
}
```

`dependencies` generally contain packages required by the application/runtime.

---

# SECTION 775 — DEVDEPENDENCIES

## 877.

Example:

```json
"devDependencies": {
  "@playwright/test": "^1.0.0"
}
```

Development dependencies commonly include tools needed for:

```text
testing
compilation
linting
build tooling
development
```

---

# SECTION 776 — DEPENDENCIES VS DEVDEPENDENCIES

## 878.

Mental model:

```text
dependencies
→ application/runtime dependencies

devDependencies
→ development/test/build-time tooling
```

Exact deployment behavior depends on how the project is installed and built.

---

# SECTION 777 — INSTALL A PACKAGE

## 879.

Generic:

```bash
npm install <package>
```

Short form:

```bash
npm i <package>
```

This normally:

```text
downloads package
updates dependency metadata
updates lock file
```

---

# SECTION 778 — INSTALL DEV DEPENDENCY

## 880.

```bash
npm install --save-dev <package>
```

Short form:

```bash
npm i -D <package>
```

Common for:

```text
TypeScript
Playwright
ESLint
testing libraries
```

depending on project.

---

# SECTION 779 — GLOBAL INSTALL

## 881.

Generic:

```bash
npm install -g <package>
```

This installs package globally for the current Node/npm environment.

Avoid unnecessary global dependencies because they can cause:

```text
version mismatch
works-on-my-machine problems
CI differences
```

Prefer project-local tooling where possible.

---

# SECTION 780 — LOCAL PACKAGE INSTALL

## 882.

Local project dependencies typically go into:

```text
node_modules/
```

Example:

```text
project/
├── package.json
├── package-lock.json
└── node_modules/
```

---

# SECTION 781 — node_modules

## 883.

`node_modules` contains installed project packages and their dependencies.

It can become large.

Normally:

```text
node_modules/
```

should NOT be committed to Git.

Instead commit:

```text
package.json
package-lock.json
```

and reinstall dependencies.

---

# SECTION 782 — WHY NODE_MODULES IS NOT COMMITTED

## 884.

Reasons include:

```text
large size
generated content
platform differences
reproducible installation through lock file
huge Git history
```

Typical `.gitignore`:

```text
node_modules/
```

---

# SECTION 783 — package-lock.json

## 885.

`package-lock.json` records resolved dependency information.

Purpose:

```text
more reproducible dependency installation
exact resolved dependency tree
```

For applications and automation projects, it should generally be committed.

---

# SECTION 784 — PACKAGE.JSON VS PACKAGE-LOCK.JSON

## 886.

```text
package.json
→ dependency requirements/ranges + scripts + project metadata

package-lock.json
→ exact dependency resolution details
```

Simple:

```text
package.json
→ what project asks for

package-lock.json
→ what npm resolved
```

---

# SECTION 785 — WHY LOCK FILE MATTERS FOR SDET

## 887.

Without stable dependency resolution:

```text
local
→ package version A

CI
→ newer package version B

test suddenly fails
```

Lock file reduces this variability.

This improves:

```text
reproducibility
CI consistency
debugging
```

---

# SECTION 786 — npm install

## 888.

```bash
npm install
```

reads project dependency files and installs dependencies.

It can also update the lock file depending on dependency state and npm behavior.

Use commonly during development.

---

# SECTION 787 — npm ci

## 889.

```bash
npm ci
```

is designed for clean, reproducible installations using the lock file.

Common CI choice.

Concept:

```text
package-lock.json must match package.json
      ↓
clean dependency install
```

---

# SECTION 788 — npm install VS npm ci

## 890.

High-level:

```text
npm install
→ normal development dependency installation/update workflow

npm ci
→ clean lock-file-driven installation, especially useful in CI
```

In CI, `npm ci` is often preferred when a valid lock file exists.

---

# SECTION 789 — WHY npm ci IS GOOD FOR CI

## 891.

Benefits:

```text
consistent dependency tree
fails on package/lock mismatch
clean installation
does not behave like normal dependency update workflow
```

This makes build failures easier to reproduce.

---

# SECTION 790 — NPM SCRIPTS

## 892.

`package.json` can define commands:

```json
{
  "scripts": {
    "test": "playwright test",
    "build": "vite build"
  }
}
```

Then run:

```bash
npm run build
```

or:

```bash
npm test
```

---

# SECTION 791 — npm run

## 893.

Generic:

```bash
npm run <script-name>
```

Example:

```bash
npm run build
```

npm finds command under:

```json
"scripts"
```

in `package.json`.

---

# SECTION 792 — SPECIAL NPM TEST COMMAND

## 894.

If script is:

```json
"test": "..."
```

you can usually run:

```bash
npm test
```

instead of:

```bash
npm run test
```

Both concepts are useful.

---

# SECTION 793 — WHY NPM SCRIPTS MATTER

## 895.

Instead of asking every developer/CI system to remember long commands:

```text
playwright test --project=chromium ...
```

project can expose:

```bash
npm test
```

or:

```bash
npm run test:e2e
```

Scripts create a standard project interface.

---

# SECTION 794 — SCRIPT EXAMPLE FOR PLAYWRIGHT

## 896.

Future conceptual example:

```json
{
  "scripts": {
    "test": "playwright test",
    "test:headed": "playwright test --headed",
    "test:debug": "playwright test --debug"
  }
}
```

We will define exact scripts only when the Playwright project is implemented.

---

# SECTION 795 — SCRIPT NAMING

## 897.

Common patterns:

```text
test
test:e2e
test:api
test:smoke
build
dev
lint
format
typecheck
```

These are conventions, not mandatory npm keywords.

---

# SECTION 796 — NPX

## 898. What Is npx?

`npx` can execute package binaries.

Example:

```bash
npx playwright test
```

It can resolve project-local executables from installed packages.

This avoids requiring global installation.

---

# SECTION 797 — WHY NPX IS USEFUL

## 899.

Suppose Playwright is installed locally.

Instead of:

```text
global playwright command
```

we can use:

```bash
npx playwright test
```

This helps use the project's intended version.

---

# SECTION 798 — NPX VS NPM

## 900.

Simplified:

```text
npm
→ install/manage packages and run scripts

npx
→ execute package-provided binaries
```

Example:

```bash
npm install -D @playwright/test
```

then:

```bash
npx playwright test
```

---

# SECTION 799 — VERSION CHECK THROUGH NPX

## 901.

Example concept:

```bash
npx playwright --version
```

Useful to confirm actual project tool version.

Do not assume global command version matches project version.

---

# SECTION 800 — TRANSITIVE DEPENDENCIES

## 902.

Suppose:

```text
Our Project
   ↓
Package A
   ↓
Package B
   ↓
Package C
```

B and C may be transitive dependencies.

This is similar conceptually to Maven transitive dependencies.

---

# SECTION 801 — DEPENDENCY TREE

## 903.

Inspect npm dependency tree:

```bash
npm ls
```

Specific package:

```bash
npm ls <package>
```

Useful for:

```text
version conflict
duplicate dependency
unexpected transitive package
```

---

# SECTION 802 — NPM OUTDATED

## 904.

```bash
npm outdated
```

can show outdated packages.

Do not automatically upgrade everything just because updates exist.

For automation projects:

```text
stability
compatibility
security
```

all matter.

---

# SECTION 803 — NPM UPDATE

## 905.

```bash
npm update
```

can update packages within allowed dependency ranges.

Before updating:

```text
review changes
understand version range
run regression
```

---

# SECTION 804 — REMOVE PACKAGE

## 906.

```bash
npm uninstall <package>
```

This updates project dependency metadata.

Alias:

```bash
npm remove <package>
```

---

# SECTION 805 — NPM CACHE

## 907.

npm maintains a cache of downloaded package data.

Inspect conceptually:

```bash
npm cache verify
```

Cache can improve installation speed.

Avoid repeatedly clearing cache as first troubleshooting step.

---

# SECTION 806 — DEPENDENCY VERSION PREFIXES

## 908.

You may see:

```text
1.2.3
^1.2.3
~1.2.3
*
```

These define allowed version ranges.

Understanding them is important for dependency stability.

---

# SECTION 807 — EXACT VERSION

## 909.

```json
"package": "1.2.3"
```

means:

```text
exact declared version
```

Lock file still records full dependency tree.

---

# SECTION 808 — CARET VERSION

## 910.

Typical:

```text
^1.2.3
```

For stable `1.x` packages, this generally allows compatible updates within the same major version.

Conceptually:

```text
>=1.2.3
<2.0.0
```

Semver behavior for `0.x` versions has additional rules.

---

# SECTION 809 — TILDE VERSION

## 911.

Typical:

```text
~1.2.3
```

Generally allows patch-level updates within the same minor line.

Conceptually:

```text
>=1.2.3
<1.3.0
```

---

# SECTION 810 — WHY VERSION RANGE MATTERS

## 912.

If `package.json` says:

```text
^1.2.3
```

different fresh resolutions could potentially select different compatible versions over time.

`package-lock.json` helps pin the actual resolved tree.

---

# SECTION 811 — SEMVER + LOCK FILE MENTAL MODEL

## 913.

```text
package.json
→ allowed range

package-lock.json
→ resolved exact version
```

This is one reason CI should honor lock file.

---

# SECTION 812 — WHAT IS TYPESCRIPT?

## 914.

TypeScript is a language developed as a typed superset of JavaScript.

It adds capabilities such as:

```text
static typing
interfaces
type aliases
generics
compile-time checking
```

TypeScript code is transformed into JavaScript for execution.

---

# SECTION 813 — TYPESCRIPT VS JAVASCRIPT

## 915.

JavaScript:

```javascript
let age = 30;
```

TypeScript:

```typescript
let age: number = 30;
```

TypeScript can catch certain mistakes before runtime.

---

# SECTION 814 — TYPESCRIPT DOES NOT REPLACE NODE.JS

## 916.

Typical flow:

```text
TypeScript Source
      ↓
TypeScript compiler/tooling
      ↓
JavaScript
      ↓
Node.js or Browser
```

TypeScript itself is not the normal final runtime environment.

---

# SECTION 815 — WHY TYPESCRIPT FOR SDET

## 917.

Benefits in large automation frameworks:

```text
better autocomplete
safer refactoring
clear function contracts
typed test data
typed API models
compile-time errors
maintainability
```

Very useful for:

```text
Playwright
React
framework utilities
```

---

# SECTION 816 — TYPE ANNOTATIONS

## 918.

Example:

```typescript
const username: string = "user1";
const retryCount: number = 3;
const active: boolean = true;
```

Types make intent explicit.

---

# SECTION 817 — TYPE INFERENCE

## 919.

TypeScript can often infer type:

```typescript
const name = "Animesh";
```

TypeScript understands:

```text
name is string
```

without explicit:

```typescript
: string
```

Do not add types everywhere unnecessarily.

---

# SECTION 818 — FUNCTION TYPES

## 920.

Example:

```typescript
function add(a: number, b: number): number {
  return a + b;
}
```

Meaning:

```text
a → number
b → number
return → number
```

---

# SECTION 819 — OPTIONAL PARAMETER

## 921.

```typescript
function greet(name?: string) {
  // ...
}
```

`?` means parameter may be absent.

Common in:

```text
test utilities
configuration
API models
```

---

# SECTION 820 — UNION TYPE

## 922.

Example:

```typescript
let status: "PASS" | "FAIL";
```

Only allowed:

```text
PASS
FAIL
```

Useful for test domain modeling.

---

# SECTION 821 — ARRAY TYPES

## 923.

```typescript
const ids: number[] = [1, 2, 3];
```

Alternative:

```typescript
const ids: Array<number> = [1, 2, 3];
```

---

# SECTION 822 — OBJECT TYPE

## 924.

Example:

```typescript
const user: {
  name: string;
  email: string;
} = {
  name: "Test User",
  email: "test@example.com"
};
```

---

# SECTION 823 — INTERFACE

## 925.

Example:

```typescript
interface User {
  id: number;
  name: string;
  email: string;
}
```

Then:

```typescript
const user: User = {
  id: 1,
  name: "Test User",
  email: "test@example.com"
};
```

Useful for API response models.

---

# SECTION 824 — TYPE ALIAS

## 926.

Example:

```typescript
type TestStatus = "PASS" | "FAIL" | "SKIPPED";
```

or:

```typescript
type Credentials = {
  username: string;
  password: string;
};
```

Type aliases and interfaces overlap in many object-modeling use cases but also have differences.

For basic SDET work, understand both.

---

# SECTION 825 — INTERFACE VS TYPE

## 927.

Simplified interview answer:

```text
Both can describe object shapes.

Interfaces are particularly natural for extendable object contracts.

Type aliases are flexible and can represent unions, primitives,
tuples and object shapes.
```

Avoid saying:

```text
one is always better
```

---

# SECTION 826 — ANY

## 928.

TypeScript has:

```typescript
any
```

Example:

```typescript
let response: any;
```

This disables much of TypeScript's type safety for that value.

Use sparingly.

---

# SECTION 827 — UNKNOWN

## 929.

`unknown` is safer than `any` when type is genuinely unknown.

Before using a value, code must narrow/check its type.

Example concept:

```typescript
let data: unknown;
```

Then validate before accessing fields.

---

# SECTION 828 — NEVER TYPE

## 930.

`never` represents values that should never occur.

Example concept:

```typescript
function fail(message: string): never {
  throw new Error(message);
}
```

Useful advanced type awareness.

Not a priority for first Playwright implementation.

---

# SECTION 829 — NULL AND UNDEFINED

## 931.

JavaScript/TypeScript distinguishes:

```text
null
undefined
```

Simplified:

```text
undefined
→ value not assigned/absent

null
→ intentionally no value
```

API contracts may also return:

```json
null
```

which must be handled explicitly.

---

# SECTION 830 — OPTIONAL PROPERTY

## 932.

Example:

```typescript
interface Product {
  id: number;
  description?: string;
}
```

`description` may be absent.

Important when modeling API responses where fields are optional.

---

# SECTION 831 — READONLY

## 933.

Example:

```typescript
interface User {
  readonly id: number;
}
```

Prevents normal reassignment through that TypeScript reference.

Useful for immutable identifiers/configuration patterns.

---

# SECTION 832 — ENUM

## 934.

TypeScript supports enums.

Example concept:

```typescript
enum Role {
  USER = "USER",
  ADMIN = "ADMIN"
}
```

But modern TypeScript projects may also prefer string unions:

```typescript
type Role = "USER" | "ADMIN";
```

Choose based on project design.

---

# SECTION 833 — GENERICS

## 935.

Generic example:

```typescript
function identity<T>(value: T): T {
  return value;
}
```

For SDET, generics become useful in:

```text
API wrappers
response models
utility functions
fixtures
```

Do not overuse before need exists.

---

# SECTION 834 — ASYNC JAVASCRIPT

## 936.

JavaScript frequently handles asynchronous operations such as:

```text
HTTP requests
browser actions
file operations
timers
```

This is especially important in Playwright.

---

# SECTION 835 — PROMISE

## 937.

A Promise represents a value that may become available later.

Concept:

```text
Operation starts
     ↓
Promise
     ↓
fulfilled
or
rejected
```

---

# SECTION 836 — ASYNC FUNCTION

## 938.

Example:

```typescript
async function loadData() {
  // ...
}
```

An `async` function returns a Promise.

---

# SECTION 837 — AWAIT

## 939.

Example:

```typescript
const response = await fetch(url);
```

`await` pauses execution of that async function until the Promise settles.

This makes asynchronous code easier to read.

---

# SECTION 838 — PLAYWRIGHT AND AWAIT

## 940.

Future Playwright code will commonly look like:

```typescript
await page.goto(url);
await page.getByRole("button").click();
await expect(page).toHaveTitle(...);
```

Why?

Browser operations are asynchronous.

Missing `await` can produce:

```text
race conditions
incorrect execution order
flaky behavior
```

---

# SECTION 839 — DON'T USE RANDOM SLEEPS

## 941.

Weak automation:

```typescript
await page.waitForTimeout(5000);
```

for every synchronization issue.

Better:

```text
wait for actual state/condition
```

Playwright has auto-waiting and assertion-based waiting capabilities.

We will cover this deeply in Playwright phase.

---

# SECTION 840 — EVENT LOOP

## 942. High-Level Concept

Node.js uses an event-driven execution model.

The event loop coordinates asynchronous operations.

Simplified:

```text
JavaScript executes
     ↓
async work delegated/registered
     ↓
event completes
     ↓
callback/promise continuation runs
```

You do NOT need Node internals at system-engineer depth for SDET interviews.

---

# SECTION 841 — SINGLE JAVASCRIPT THREAD SIMPLIFICATION

## 943.

People often say:

```text
Node.js is single-threaded
```

More accurate interview understanding:

```text
JavaScript execution commonly runs on a main event-loop thread,
while Node.js/runtime/OS can use additional threads and system facilities
for certain asynchronous work.
```

Avoid oversimplifying:

```text
Node can only do one thing ever
```

---

# SECTION 842 — SYNCHRONOUS VS ASYNCHRONOUS

## 944.

Synchronous:

```text
Task A
↓ complete
Task B
↓ complete
Task C
```

Asynchronous:

```text
Task starts
↓
other work can continue
↓
result handled later
```

Automation frameworks rely heavily on async behavior.

---

# SECTION 843 — CALLBACK

## 945.

Traditional JavaScript async APIs often used callbacks:

```javascript
someOperation((error, result) => {
  // ...
});
```

Modern code frequently uses:

```text
Promises
async/await
```

for readability.

---

# SECTION 844 — PROMISE STATES

## 946.

A Promise can be:

```text
pending
fulfilled
rejected
```

Rejected promises must be handled appropriately.

---

# SECTION 845 — ERROR HANDLING

## 947.

Typical synchronous/async error handling:

```typescript
try {
  // operation
} catch (error) {
  // handling
}
```

With async:

```typescript
try {
  await someOperation();
} catch (error) {
  // handling
}
```

---

# SECTION 846 — DON'T SWALLOW ERRORS

## 948.

Bad:

```typescript
try {
  await someOperation();
} catch {
  // do nothing
}
```

This can make automation falsely continue.

Better:

```text
log useful context
clean up where needed
rethrow/fail appropriately
```

unless failure is intentionally expected.

---

# SECTION 847 — JAVASCRIPT MODULES

## 949.

Modern JavaScript/TypeScript code is organized into modules.

Common modern syntax:

```typescript
export
import
```

Example:

```typescript
export function createUser() {
  // ...
}
```

then:

```typescript
import { createUser } from "./user";
```

---

# SECTION 848 — COMMONJS

## 950.

Older/alternative Node module style:

```javascript
const fs = require("fs");
```

and:

```javascript
module.exports = ...
```

This is known as:

```text
CommonJS
```

Modern ecosystems increasingly use ES Modules.

---

# SECTION 849 — ES MODULES

## 951.

ES Module syntax:

```typescript
import ...
export ...
```

Example:

```typescript
import { test, expect } from "@playwright/test";
```

This is common in modern TypeScript/Playwright code.

---

# SECTION 850 — COMMONJS VS ESM

## 952.

```text
CommonJS
→ require / module.exports

ES Modules
→ import / export
```

Project configuration determines module behavior.

Do not randomly mix module systems.

---

# SECTION 851 — PACKAGE TYPE

## 953.

`package.json` can contain:

```json
"type": "module"
```

which influences how `.js` files are interpreted by Node.

TypeScript/build tooling can add additional configuration.

---

# SECTION 852 — FILE EXTENSIONS

## 954.

Common:

```text
.js
→ JavaScript

.ts
→ TypeScript

.jsx
→ JavaScript with JSX

.tsx
→ TypeScript with JSX
```

For React TypeScript components, commonly:

```text
.tsx
```

For Playwright TypeScript tests:

```text
.ts
```

is common.

---

# SECTION 853 — JSX

## 955.

JSX allows UI-like syntax in JavaScript/TypeScript ecosystems.

Example concept:

```tsx
function App() {
  return <h1>Hello</h1>;
}
```

React commonly uses JSX/TSX.

Browser does not directly execute TypeScript/TSX as authored; tooling transforms it.

---

# SECTION 854 — WHAT IS VITE?

## 956.

Vite is a modern frontend development/build tool.

It can provide:

```text
development server
module handling
build tooling
fast development workflow
```

We plan to use it for the React + TypeScript frontend.

Exact version will be determined during implementation.

---

# SECTION 855 — REACT + VITE FLOW

## 957.

Future development:

```text
React + TypeScript source
        ↓
Vite
        ↓
Development server
        ↓
Browser
```

Likely frontend local address:

```text
localhost:5173
```

unless configuration/version chooses otherwise.

---

# SECTION 856 — FRONTEND BUILD

## 958.

Conceptually:

```bash
npm run build
```

will create production-ready frontend assets according to Vite configuration.

Common output directory may be:

```text
dist/
```

but we will confirm actual project behavior after creation.

---

# SECTION 857 — DEV SERVER

## 959.

Typical Vite development command:

```bash
npm run dev
```

Then development server serves frontend locally.

Exact script will be generated/confirmed after project scaffolding.

---

# SECTION 858 — ENVIRONMENT VARIABLES IN VITE

## 960.

Vite frontend environment variables exposed to application code commonly use:

```text
VITE_
```

prefix.

Future example:

```text
VITE_API_BASE_URL=http://localhost:8080
```

---

# SECTION 859 — FRONTEND ENV VARIABLES ARE NOT SECRET

## 961. Critical Security Concept

Anything delivered to browser can potentially be inspected by the user.

Therefore:

```text
VITE_API_BASE_URL
→ fine

database password
JWT signing secret
private API secret
→ NEVER
```

Do not store backend secrets in React frontend environment variables.

---

# SECTION 860 — BUILD-TIME FRONTEND CONFIG

## 962.

Frontend environment values are commonly embedded into generated client code at build/dev-tool time.

Therefore:

```text
.env in frontend
≠ secure secret storage
```

This is a critical interview concept.

---

# SECTION 861 — FRONTEND .ENV

## 963.

Future frontend may use files such as:

```text
.env
.env.local
.env.example
```

depending on workflow.

Rules:

```text
real environment-specific local values
→ ignored if appropriate

example variables
→ committed without secrets
```

---

# SECTION 862 — UI AUTOMATION ENV

## 964.

Future Playwright framework can also use runtime environment variables.

Examples conceptually:

```text
BASE_URL
TEST_ENV
USERNAME
PASSWORD
```

Secrets should remain externalized.

Do not hardcode credentials in:

```text
spec files
page objects
Git
```

---

# SECTION 863 — PROCESS.ENV

## 965.

Node.js exposes environment variables through:

```typescript
process.env
```

Example:

```typescript
const baseUrl = process.env.BASE_URL;
```

This retrieves runtime environment value.

---

# SECTION 864 — ENV VARIABLES ARE STRINGS

## 966.

Values from:

```typescript
process.env
```

are generally strings or undefined from TypeScript's perspective.

Example:

```typescript
const retries = Number(process.env.RETRIES ?? "2");
```

If numeric/boolean behavior is required, parse and validate explicitly.

---

# SECTION 865 — MISSING ENV VARIABLE

## 967.

Bad:

```typescript
const password = process.env.PASSWORD;
```

then assume it always exists.

Better framework behavior:

```text
validate required config at startup
fail early with clear error
```

Example concept:

```typescript
if (!process.env.BASE_URL) {
  throw new Error("BASE_URL is required");
}
```

---

# SECTION 866 — FAIL FAST CONFIGURATION

## 968.

Strong automation framework:

```text
Startup
  ↓
Validate required environment
  ↓
Fail clearly if missing
```

instead of:

```text
Test begins
↓
50 tests fail with confusing undefined URL
```

---

# SECTION 867 — .ENV LIBRARIES

## 969.

Node projects may use libraries such as:

```text
dotenv
```

to load `.env` files.

But not every project needs it if configuration is supplied another way.

Important:

```text
Node runtime does not mean every .env file automatically becomes process.env
```

Tool/framework behavior matters.

---

# SECTION 868 — NODE ENV VS SPRING ENV

## 970.

Compare:

Spring project:

```text
shell/runtime env
     ↓
Spring property resolution
```

Node project:

```text
OS/process env
     ↓
process.env
```

Both can externalize configuration, but frameworks/tools load and expose configuration differently.

---

# SECTION 869 — tsconfig.json

## 971.

TypeScript projects commonly use:

```text
tsconfig.json
```

This controls TypeScript compiler behavior.

It can configure:

```text
target
module behavior
strictness
paths
included files
output
module resolution
```

---

# SECTION 870 — TSC

## 972.

TypeScript compiler executable:

```text
tsc
```

Often installed via:

```text
typescript
```

package.

Example:

```bash
npx tsc --noEmit
```

can perform type checking without producing output files, depending on config.

---

# SECTION 871 — TYPE CHECKING

## 973.

Type checking catches problems before runtime.

Example:

```typescript
const count: number = "three";
```

TypeScript should flag:

```text
string is not assignable to number
```

This can prevent automation defects.

---

# SECTION 872 — STRICT MODE

## 974.

TypeScript supports strict compiler checks.

Concept:

```json
{
  "compilerOptions": {
    "strict": true
  }
}
```

Strict mode helps catch:

```text
undefined/null mistakes
unsafe assumptions
incorrect types
```

For maintainable automation, strict typing is usually valuable.

---

# SECTION 873 — TRANSPILATION

## 975.

TypeScript source:

```text
.ts
```

is transformed into JavaScript or handled through runtime tooling.

Term commonly used:

```text
transpilation
```

In modern build systems, transformation may be handled by multiple tools rather than only `tsc`.

---

# SECTION 874 — TYPE CHECKING VS TRANSFORMATION

## 976.

Important:

Some tools can transform TypeScript into JavaScript without performing full type checking.

Therefore:

```text
application builds/runs
```

does not always prove:

```text
TypeScript has zero type errors
```

Many projects add explicit:

```bash
npx tsc --noEmit
```

or equivalent type-check script.

---

# SECTION 875 — LINTING

## 977.

Linting statically analyzes source code for:

```text
style problems
possible bugs
bad patterns
consistency
```

A common JavaScript/TypeScript linter is:

```text
ESLint
```

---

# SECTION 876 — LINTER VS TYPE CHECKER

## 978.

```text
TypeScript compiler
→ type correctness

ESLint
→ coding rules/patterns/style and certain bug detection
```

There can be overlap.

Both can be useful quality gates.

---

# SECTION 877 — FORMATTING

## 979.

Formatting tools such as:

```text
Prettier
```

can automatically format code.

Purpose:

```text
consistent formatting
less style discussion in PRs
```

Formatter:

```text
does not replace testing
```

---

# SECTION 878 — LINT VS FORMAT

## 980.

```text
Lint
→ analyze code quality/rules

Format
→ rewrite layout/style
```

These concepts can work together.

---

# SECTION 879 — SOURCE MAPS

## 981.

Source maps help map generated/transformed JavaScript back to original source such as TypeScript.

Useful for:

```text
debugging
stack traces
browser DevTools
```

Build tools often manage this automatically.

---

# SECTION 880 — NODE STACK TRACE

## 982.

JavaScript error:

```text
Error message
stack trace
file
line
function
```

As with Java:

```text
read actual first meaningful application frame
```

Do not only focus on the final test runner message.

---

# SECTION 881 — COMMON ERROR: MODULE NOT FOUND

## 983.

Example:

```text
Cannot find module
```

Potential causes:

```text
dependency not installed
wrong import path
wrong package name
module resolution issue
node_modules missing
case mismatch
```

---

# SECTION 882 — FIXING MODULE NOT FOUND

## 984.

Check:

```bash
npm install
```

Then:

```bash
npm ls <package>
```

Also inspect:

```text
import path
package.json
file case
```

Do not blindly delete everything first.

---

# SECTION 883 — COMMON ERROR: COMMAND NOT FOUND

## 985.

Example:

```text
playwright: command not found
```

Possible:

```text
package not installed globally
project executable should be invoked through npx
node_modules missing
PATH issue
```

Try project-local:

```bash
npx playwright --version
```

if Playwright is installed.

---

# SECTION 884 — COMMON ERROR: NODE VERSION UNSUPPORTED

## 986.

Some packages require minimum Node versions.

Symptoms:

```text
unsupported engine
syntax errors
install failure
build failure
```

Check:

```bash
node --version
```

and package requirements.

---

# SECTION 885 — ENGINES FIELD

## 987.

`package.json` can declare expected runtime versions.

Example concept:

```json
{
  "engines": {
    "node": ">=20"
  }
}
```

This documents/enforces expectations depending on tooling configuration.

---

# SECTION 886 — NODE VERSION MANAGEMENT

## 988.

Developers may use tools such as:

```text
nvm
fnm
Volta
asdf
```

to manage Node versions.

We should not add one until needed.

First verify:

```bash
node --version
npm --version
```

---

# SECTION 887 — NVM CONCEPT

## 989.

Node Version Manager allows switching Node versions.

Concept:

```text
Project A → Node version X
Project B → Node version Y
```

Useful when multiple projects have different compatibility requirements.

---

# SECTION 888 — .NVMRC CONCEPT

## 990.

Projects using `nvm` may commit:

```text
.nvmrc
```

containing intended Node version.

Then developers can switch to that version.

This improves consistency, but exact version-management strategy should be chosen intentionally.

---

# SECTION 889 — CI NODE VERSION

## 991.

CI should explicitly choose Node version.

Concept:

```text
Checkout
  ↓
Setup Node X
  ↓
npm ci
  ↓
lint/typecheck/test/build
```

Do not rely on whatever Node happens to be preinstalled on runner.

---

# SECTION 890 — DEPENDENCY INSTALLATION IN CI

## 992.

Preferred high-level sequence:

```text
package.json
+
package-lock.json
      ↓
npm ci
      ↓
deterministic dependency tree
```

Then:

```text
tests/build
```

---

# SECTION 891 — NODE_MODULES CACHE IN CI

## 993.

CI may cache dependency-related data to speed up pipelines.

Important:

```text
cache
≠ source of truth
```

Lock file remains critical.

Bad cache should not permanently hide dependency problems.

---

# SECTION 892 — PACKAGE CACHE VS NODE_MODULES CACHE

## 994.

CI systems may cache:

```text
npm download cache
```

rather than directly reusing an old `node_modules`.

Exact strategy varies.

Goal:

```text
faster install
without sacrificing reproducibility
```

---

# SECTION 893 — NPM AUDIT

## 995.

```bash
npm audit
```

checks dependency tree against known vulnerability information available to npm.

It can report:

```text
severity
affected package
dependency path
possible remediation
```

---

# SECTION 894 — NPM AUDIT IS NOT COMPLETE SECURITY

## 996.

`npm audit` is useful but does not prove:

```text
application is secure
```

Security also requires:

```text
secure coding
secret management
container scanning
auth testing
dependency governance
```

---

# SECTION 895 — NPM AUDIT FIX

## 997.

```bash
npm audit fix
```

can attempt compatible dependency updates.

Do not run blindly in important project branches.

Always:

```text
review diff
understand dependency changes
run regression
```

especially if major versions are involved.

---

# SECTION 896 — PACKAGE SECURITY

## 998.

Before adding dependency:

```text
Do we need it?
Is it maintained?
Is version supported?
Does it introduce many transitive packages?
Are there known vulnerabilities?
```

Dependency count is part of software supply-chain risk.

---

# SECTION 897 — TYPO-SQUATTING

## 999.

Malicious packages may use names similar to popular packages.

Example concept:

```text
real-package
rea1-package
real-pakage
```

Always verify package name before:

```bash
npm install
```

especially when copying commands from random sources.

---

# SECTION 898 — LOCKFILE SECURITY

## 1000.

Unexpected changes in:

```text
package-lock.json
```

should be reviewed.

A small `package.json` change may update many transitive resolutions.

In PR review, do not ignore lock-file changes automatically.

---

# SECTION 899 — CI SCRIPT EXAMPLE

## 1001. Future Concept

A JavaScript/TypeScript project pipeline could run:

```bash
npm ci
npm run lint
npm run typecheck
npm test
```

For frontend:

```bash
npm run build
```

Exact commands will be based on scripts we actually implement.

---

# SECTION 900 — FAIL FAST IN CI

## 1002.

Useful order:

```text
dependency install
↓
lint
↓
type check
↓
fast tests
↓
larger tests
↓
build/deploy
```

Why?

Cheap failures should ideally be detected early.

Exact order depends on project needs.

---

# SECTION 901 — EXIT CODES

## 1003.

Node/npm commands return process exit codes.

Generally:

```text
0
→ success

non-zero
→ failure
```

CI uses exit codes to determine job success.

Example:

```bash
npm test
echo $?
```

---

# SECTION 902 — NPM SCRIPT FAILURE

## 1004.

If test script fails:

```text
npm itself may report script failure
```

But root cause can be:

```text
test assertion
TypeScript error
dependency failure
environment configuration
browser failure
```

Read underlying command output.

---

# SECTION 903 — SHELL ENV INLINE OVERRIDE

## 1005.

On Unix-like shells:

```bash
BASE_URL=http://localhost:8080 npm test
```

sets variable for that command process.

Useful for temporary execution.

But cross-platform compatibility differs between shell environments.

---

# SECTION 904 — CROSS-PLATFORM ENV ISSUE

## 1006.

Commands written for:

```text
macOS/Linux shell
```

may not behave identically on:

```text
Windows cmd
PowerShell
```

For team frameworks, avoid shell-specific assumptions where cross-platform support matters.

---

# SECTION 905 — PATH SEPARATORS

## 1007.

Common filesystem difference:

```text
macOS/Linux
/

Windows
\
```

Node APIs can help construct portable paths.

---

# SECTION 906 — NODE PATH MODULE

## 1008.

Node has built-in path utilities.

Concept:

```typescript
import path from "node:path";
```

Useful to avoid hardcoding platform-specific separators.

---

# SECTION 907 — BUILT-IN NODE MODULES

## 1009.

Node provides built-in modules such as:

```text
fs
path
os
http
https
crypto
```

These do not need normal npm package installation.

---

# SECTION 908 — FS MODULE

## 1010.

`fs` means filesystem utilities.

Automation can use it for:

```text
reading test data
writing reports
handling downloaded files
config
```

Be careful with:

```text
absolute paths
CI working directory
permissions
```

---

# SECTION 909 — WORKING DIRECTORY

## 1011.

Node process has a current working directory.

Retrieve conceptually:

```typescript
process.cwd()
```

Important:

```text
current working directory
≠ source file's directory
```

This can cause path issues locally vs CI.

---

# SECTION 910 — __dirname AND ESM NUANCE

## 1012.

CommonJS historically provides:

```text
__dirname
```

ES Modules handle module-file location differently.

Do not copy `__dirname` patterns blindly when project uses ESM.

Exact approach depends on project module system.

---

# SECTION 911 — JSON

## 1013.

JSON means:

```text
JavaScript Object Notation
```

Despite the name, JSON is language-independent data format.

Example:

```json
{
  "name": "Product",
  "price": 100
}
```

---

# SECTION 912 — JSON VS JAVASCRIPT OBJECT

## 1014.

JavaScript object:

```javascript
{
  name: "Product",
  price: 100
}
```

JSON:

```json
{
  "name": "Product",
  "price": 100
}
```

Important differences include:

```text
JSON requires quoted property names
JSON supports a limited set of value types
JSON is text/data representation
```

---

# SECTION 913 — JSON.PARSE

## 1015.

Convert JSON string to JavaScript value:

```typescript
const data = JSON.parse(jsonText);
```

Bad JSON can throw:

```text
SyntaxError
```

---

# SECTION 914 — JSON.STRINGIFY

## 1016.

Convert JavaScript value to JSON string:

```typescript
const json = JSON.stringify(data);
```

Useful for:

```text
logging
API payloads
files
```

Be careful not to stringify secrets into reports.

---

# SECTION 915 — DESTRUCTURING

## 1017.

JavaScript/TypeScript feature:

```typescript
const user = {
  id: 10,
  name: "Test User"
};

const { id, name } = user;
```

Useful when working with:

```text
API responses
fixtures
test data
```

---

# SECTION 916 — SPREAD OPERATOR

## 1018.

Example:

```typescript
const updatedUser = {
  ...user,
  name: "Updated User"
};
```

Useful for building modified test payloads.

---

# SECTION 917 — OPTIONAL CHAINING

## 1019.

Example:

```typescript
const city = user.address?.city;
```

If `address` is missing:

```text
returns undefined
```

instead of throwing property-access error.

But excessive optional chaining can hide unexpected missing data in tests.

Assertions should still verify required contract fields.

---

# SECTION 918 — NULLISH COALESCING

## 1020.

Example:

```typescript
const timeout = config.timeout ?? 5000;
```

Uses fallback only when value is:

```text
null
or
undefined
```

Different from:

```text
||
```

which also treats other falsy values as false.

---

# SECTION 919 — == VS ===

## 1021.

JavaScript:

```text
==
→ loose equality with coercion

===
→ strict equality
```

Prefer:

```text
===
```

for predictable automation code.

Example:

```javascript
"1" == 1
```

can be true.

But:

```javascript
"1" === 1
```

is false.

---

# SECTION 920 — FALSY VALUES

## 1022.

Common JavaScript falsy values include:

```text
false
0
""
null
undefined
NaN
```

This matters in validation code.

Do not confuse:

```text
missing
```

with:

```text
valid value 0
```

---

# SECTION 921 — LET CONST VAR

## 1023.

Modern JavaScript commonly uses:

```text
const
let
```

Prefer `const` when reference does not need reassignment.

Use `let` when reassignment is required.

Avoid `var` in modern code unless there is a specific reason.

---

# SECTION 922 — CONST DOES NOT MAKE OBJECT IMMUTABLE

## 1024.

Example:

```typescript
const user = {
  name: "A"
};

user.name = "B";
```

This is allowed.

`const` prevents rebinding variable itself:

```typescript
user = anotherUser;
```

not all mutations inside object.

---

# SECTION 923 — ARROW FUNCTIONS

## 1025.

Example:

```typescript
const add = (a: number, b: number) => {
  return a + b;
};
```

Common in modern JavaScript/TypeScript.

---

# SECTION 924 — MAP

## 1026.

Example:

```typescript
const ids = users.map(user => user.id);
```

Transforms array elements.

Useful for:

```text
API response validation
data extraction
test-data transformation
```

---

# SECTION 925 — FILTER

## 1027.

```typescript
const admins = users.filter(user => user.role === "ADMIN");
```

Returns matching items.

---

# SECTION 926 — FIND

## 1028.

```typescript
const user = users.find(user => user.id === 10);
```

Returns first matching item or:

```text
undefined
```

---

# SECTION 927 — SOME

## 1029.

```typescript
const hasAdmin = users.some(user => user.role === "ADMIN");
```

Returns boolean.

Useful for collection assertions.

---

# SECTION 928 — EVERY

## 1030.

```typescript
const allActive = users.every(user => user.active);
```

Checks whether every element satisfies condition.

---

# SECTION 929 — FOR...OF

## 1031.

Example:

```typescript
for (const user of users) {
  console.log(user.name);
}
```

Good for sequential iteration.

Especially useful when each iteration needs:

```typescript
await
```

---

# SECTION 930 — FOREACH + ASYNC TRAP

## 1032.

Be careful with:

```typescript
items.forEach(async item => {
  await doSomething(item);
});
```

`forEach` does not naturally await all async callback work the way many beginners expect.

For sequential flow, use:

```typescript
for (const item of items) {
  await doSomething(item);
}
```

For parallel flow, use deliberate Promise patterns.

---

# SECTION 931 — PROMISE.ALL

## 1033.

Parallel async work:

```typescript
await Promise.all([
  taskA(),
  taskB(),
  taskC()
]);
```

Useful when operations are independent.

Be careful:

```text
parallelism can overload systems
create test-data conflicts
change execution order
```

---

# SECTION 932 — PARALLEL TESTING

## 1034.

Playwright supports parallel execution.

Parallelism can reduce execution time but requires:

```text
isolated test data
independent accounts/resources
safe cleanup
no shared mutable state
```

Do not enable maximum workers blindly.

---

# SECTION 933 — TEST DATA + PARALLELISM

## 1035.

Bad:

```text
10 tests update same user
```

Better:

```text
independent data
unique records
cleanup strategy
```

Parallel execution exposes hidden test coupling.

---

# SECTION 934 — DATE/TIME

## 1036.

JavaScript provides:

```typescript
Date
```

for date/time operations.

Tests involving timestamps must consider:

```text
timezone
UTC
locale
clock precision
server/client differences
```

Avoid exact timestamp comparisons unless requirement needs them.

---

# SECTION 935 — ENVIRONMENT TIMEZONE ISSUE

## 1037.

Test:

```text
passes locally in IST
fails in CI UTC
```

Possible reason:

```text
timezone assumption
```

Prefer:

```text
explicit timezone handling
UTC where appropriate
contract-based comparison
```

---

# SECTION 936 — NODE PROCESS EXIT

## 1038.

Node process can exit with:

```typescript
process.exit(code);
```

But test frameworks should normally control process exit based on test results.

Avoid manually terminating process unnecessarily because it may skip:

```text
cleanup
report generation
after hooks
```

---

# SECTION 937 — SIGNALS

## 1039.

Node processes can receive OS signals such as:

```text
SIGINT
SIGTERM
```

Examples:

```text
Ctrl+C → often SIGINT
container shutdown → commonly SIGTERM
```

Useful later for Docker/runtime understanding.

---

# SECTION 938 — PACKAGE MANAGERS BEYOND NPM

## 1040.

Other Node package managers include:

```text
Yarn
pnpm
```

For our project, npm is enough unless we have a reason to change.

Do not mix package managers casually because multiple lock files can create confusion.

---

# SECTION 939 — LOCK FILES FROM DIFFERENT MANAGERS

## 1041.

Examples:

```text
package-lock.json
→ npm

yarn.lock
→ Yarn

pnpm-lock.yaml
→ pnpm
```

Project should normally standardize one package manager.

---

# SECTION 940 — COREPACK CONCEPT

## 1042.

Modern Node ecosystems may use Corepack to manage certain package manager versions.

This is useful awareness.

Not needed for our current npm-based plan.

---

# SECTION 941 — NPM CONFIG

## 1043.

Inspect npm configuration:

```bash
npm config list
```

Useful for:

```text
registry
proxy
cache
prefix
```

Troubleshooting package installation issues.

Avoid exposing sensitive registry credentials in shared output.

---

# SECTION 942 — NPM REGISTRY CHECK

## 1044.

```bash
npm config get registry
```

Can help verify whether npm uses:

```text
public registry
corporate registry
```

Useful when package exists publicly but install fails internally.

---

# SECTION 943 — CORPORATE PROXY + NPM

## 1045.

Possible failures:

```text
ETIMEDOUT
ECONNRESET
certificate error
registry unreachable
```

Potential causes:

```text
proxy
VPN
corporate CA
registry config
DNS
```

Connect this to Part 4 networking.

---

# SECTION 944 — NPM EAI_AGAIN / ENOTFOUND

## 1046.

Errors such as:

```text
ENOTFOUND
EAI_AGAIN
```

often point toward:

```text
DNS/network resolution problems
```

Do not immediately delete `node_modules`.

---

# SECTION 945 — ECONNREFUSED

## 1047.

In Node ecosystem:

```text
ECONNREFUSED
```

usually means connection could not be established because target refused it.

Same networking principle:

```text
host
port
service
```

---

# SECTION 946 — ETIMEDOUT

## 1048.

```text
ETIMEDOUT
```

indicates operation/connection exceeded allowed time.

Possible:

```text
network
proxy
registry
server
firewall
```

Again:

```text
timeout
≠ package corruption automatically
```

---

# SECTION 947 — SELF-SIGNED CERT ERROR

## 1049.

Corporate networks may cause errors around:

```text
self-signed certificate
certificate chain
unable to verify issuer
```

Correct approach:

```text
understand corporate CA/trust/proxy configuration
```

Bad approach:

```text
disable SSL verification everywhere
```

---

# SECTION 948 — NODE_TLS_REJECT_UNAUTHORIZED

## 1050. Security Warning

You may find internet suggestions like:

```bash
NODE_TLS_REJECT_UNAUTHORIZED=0
```

This disables important TLS certificate validation.

Do NOT use this as a normal fix.

Correct solution:

```text
proper trust/certificate/proxy configuration
```

---

# SECTION 949 — PACKAGE SCRIPT WORKING DIRECTORY

## 1051.

When you run:

```bash
npm run test
```

command executes with project context based on package location/current invocation.

Relative paths should be designed carefully.

CI may execute from repository root or module folder.

---

# SECTION 950 — MONOREPO CONCEPT

## 1052.

A repository can contain multiple Node projects.

Example future structure:

```text
SDET-Commerce-Automation/
│
├── frontend/
│   └── package.json
│
└── ui-automation/
    └── package.json
```

Each could manage its own:

```text
dependencies
scripts
lock files
```

depending on design.

---

# SECTION 951 — NPM WORKSPACES

## 1053.

npm supports workspaces for managing multiple packages from a root project.

Useful in monorepos.

We do NOT need to adopt workspaces automatically.

Simple separate projects may be clearer for our portfolio.

---

# SECTION 952 — OUR FUTURE FRONTEND DIRECTORY

## 1054.

Planned:

```text
frontend/
```

Likely contents after implementation:

```text
package.json
package-lock.json
src/
public/
tsconfig files
Vite configuration
```

Exact generated structure should be documented after scaffolding, not before.

---

# SECTION 953 — OUR FUTURE UI AUTOMATION DIRECTORY

## 1055.

Existing directory:

```text
ui-automation/
```

Future Playwright implementation may include:

```text
package.json
package-lock.json
playwright.config.ts
tests/
pages/
fixtures/
utils/
```

Exact framework structure will be designed during implementation.

---

# SECTION 954 — DON'T MIX FRONTEND AND AUTOMATION DEPENDENCIES BLINDLY

## 1056.

Possible structures:

### Option A

```text
frontend/package.json
ui-automation/package.json
```

Advantages:

```text
clear ownership
independent dependencies
independent lifecycle
```

### Option B

Shared workspace/monorepo tooling.

For our learning portfolio, separate modules can remain easier to understand.

We will decide based on actual implementation.

---

# SECTION 955 — REACT DEPENDENCIES VS PLAYWRIGHT DEPENDENCIES

## 1057.

Frontend:

```text
React
React DOM
Vite
TypeScript
Axios
React Router
```

planned.

UI automation:

```text
Playwright Test
TypeScript/tooling
```

Frontend packages should not be added to automation project unless needed there.

---

# SECTION 956 — AXIOS

## 1058.

Axios is a popular HTTP client library for JavaScript.

We plan to use it in frontend services.

Concept:

```text
React Component
    ↓
Axios/API Service
    ↓
Spring Boot REST API
```

Playwright API testing itself does not require Axios because Playwright has its own API request capabilities.

---

# SECTION 957 — REACT ROUTER

## 1059.

React Router helps handle client-side navigation.

Future screens may include:

```text
/login
/register
/products
/cart
/orders
/admin/products
```

Exact routes will be implemented later.

---

# SECTION 958 — CLIENT-SIDE ROUTING

## 1060.

In a single-page application:

```text
browser URL changes
```

can be handled by frontend routing without full traditional page reload for every navigation.

This has implications for:

```text
Playwright navigation
deep links
deployment config
404 handling
```

---

# SECTION 959 — API BASE URL IN FRONTEND

## 1061.

Planned local configuration:

```text
VITE_API_BASE_URL=http://localhost:8080
```

Then API service can read:

```typescript
import.meta.env.VITE_API_BASE_URL
```

rather than hardcoding the backend URL throughout components.

---

# SECTION 960 — IMPORT.META.ENV

## 1062.

Vite exposes frontend build environment values through:

```typescript
import.meta.env
```

Example:

```typescript
const apiBaseUrl = import.meta.env.VITE_API_BASE_URL;
```

Different from Node server-side:

```typescript
process.env
```

---

# SECTION 961 — PROCESS.ENV VS IMPORT.META.ENV

## 1063.

```text
Node.js code
→ process.env

Vite browser-facing application
→ import.meta.env
```

depending on tool/runtime.

This is a very useful debugging distinction.

---

# SECTION 962 — FRONTEND CORS CONNECTION

## 1064.

When future React runs:

```text
localhost:5173
```

and Spring Boot runs:

```text
localhost:8080
```

browser sees different origins.

Therefore:

```text
React → API
```

will depend on correct backend CORS configuration.

REST Assured can still work even if browser CORS fails.

---

# SECTION 963 — NODE + DOCKER CONNECTION

## 1065.

Later frontend Docker build may conceptually be:

```text
Node build stage
      ↓
npm ci
      ↓
npm run build
      ↓
static frontend assets
      ↓
runtime web server/container
```

Exact Dockerfile will be designed in Dockerization phase.

---

# SECTION 964 — NODE + PLAYWRIGHT DOCKER CONNECTION

## 1066.

Playwright container concept:

```text
Node runtime
+
Playwright package
+
browser binaries/dependencies
+
test code
```

This can provide consistent CI browser environment.

Again:

```text
planned
not currently implemented
```

---

# SECTION 965 — PLAYWRIGHT BROWSER INSTALLATION

## 1067.

Playwright commonly requires browser binaries.

A command commonly used is:

```bash
npx playwright install
```

Some environments may require OS dependencies as well.

We will use the exact recommended setup for the Playwright version we install.

---

# SECTION 966 — PACKAGE VERSION COMPATIBILITY

## 1068.

Possible compatibility matrix:

```text
Node version
↓
Playwright version
↓
browser binaries
↓
OS/container image
```

When test environment breaks after upgrade, check all four.

---

# SECTION 967 — PLAYWRIGHT PACKAGE VERSION

## 1069.

Do not write framework code assuming:

```text
latest online docs
```

always match installed version.

Check:

```bash
npx playwright --version
```

and use documentation compatible with that version.

---

# SECTION 968 — TYPESCRIPT VERSION

## 1070.

Check:

```bash
npx tsc --version
```

if TypeScript is installed.

Useful when local and CI type behavior differs.

---

# SECTION 969 — VITE VERSION

## 1071.

Once installed, exact Vite version can be checked through project tooling.

Do not assume version from tutorials.

Frontend ecosystems change quickly.

---

# SECTION 970 — LOCKING TOOL VERSIONS

## 1072.

Project files should make it possible to reproduce:

```text
Playwright version
TypeScript version
frontend dependency versions
```

through:

```text
package.json
package-lock.json
```

CI should use committed lock file.

---

# SECTION 971 — NPM PACKAGE SCRIPTS AS BUILD CONTRACT

## 1073.

A strong project exposes standard commands.

Future frontend concept:

```bash
npm run dev
npm run build
npm run lint
```

Future Playwright:

```bash
npm test
npm run test:smoke
npm run test:headed
```

CI calls these scripts instead of duplicating long implementation-specific commands.

---

# SECTION 972 — WHY THIS HELPS SDET ARCHITECTURE

## 1074.

If implementation changes:

```text
playwright command flags change
```

we update:

```text
package.json script
```

while CI can continue calling:

```bash
npm test
```

This reduces coupling.

---

# SECTION 973 — TEST CONFIGURATION

## 1075.

Future Playwright config may include:

```text
baseURL
retries
workers
reporters
timeouts
projects
use options
```

in:

```text
playwright.config.ts
```

We will define each intentionally later.

---

# SECTION 974 — RETRIES ENVIRONMENT DIFFERENCE

## 1076.

A framework may configure:

```text
local retries = 0
CI retries = limited value
```

But retries should not be used to mask deterministic defects.

We will design retry policy based on evidence.

---

# SECTION 975 — NODE ENVIRONMENT VARIABLE `CI`

## 1077.

Many tools recognize:

```text
CI=true
```

to alter behavior.

Examples can include:

```text
reporting
retry strategy
non-interactive output
```

Exact behavior depends on each tool.

---

# SECTION 976 — PACKAGE SCRIPT DEBUGGING

## 1078.

If:

```bash
npm run build
```

fails:

First find underlying executed command.

Then classify:

```text
dependency install?
TypeScript?
Vite?
environment?
source code?
filesystem?
```

Do not report:

```text
npm broken
```

without evidence.

---

# SECTION 977 — DEBUGGING INSTALL FAILURE

## 1079.

Flow:

```text
npm install / npm ci fails
      ↓
Read actual package/error
      ↓
Check Node/npm version
      ↓
Check registry
      ↓
Check DNS/network/proxy/TLS
      ↓
Check package-lock consistency
      ↓
Check architecture/native dependency
```

---

# SECTION 978 — DEBUGGING LOCAL PASSES CI FAILS

## 1080.

Compare:

```text
Node version
npm version
OS
CPU architecture
environment variables
package-lock
working directory
case-sensitive paths
browser dependencies
network
```

---

# SECTION 979 — CASE-SENSITIVE FILE PATH ISSUE

## 1081.

Example:

Actual file:

```text
LoginPage.ts
```

Import:

```typescript
import "./loginPage";
```

May appear to work on some local filesystems but fail in Linux CI.

Use exact casing.

This is a common cross-platform CI issue.

---

# SECTION 980 — ESM/CJS CI DIFFERENCE

## 1082.

Errors may mention:

```text
require is not defined
Cannot use import statement outside a module
ERR_REQUIRE_ESM
```

These often point to:

```text
module system/configuration mismatch
```

Check:

```text
package.json type
tsconfig
dependency version
Node version
```

---

# SECTION 981 — NATIVE MODULES

## 1083.

Some npm packages contain native components.

Possible issues:

```text
macOS vs Linux
arm64 vs amd64
Node ABI/version
missing build tools
```

Prefer pure JS/tool-supported packages when appropriate, but some native modules are legitimate.

---

# SECTION 982 — APPLE SILICON

## 1084.

Our development machine uses Apple Silicon architecture.

This can matter if:

```text
package has native binary
Docker image lacks arm64 support
browser dependency differs
```

Check:

```bash
uname -m
```

when architecture-specific failure occurs.

---

# SECTION 983 — BUILD ARTIFACT

## 1085.

Frontend build artifact commonly consists of static files such as:

```text
HTML
JavaScript
CSS
assets
```

These can be served by:

```text
web server
CDN
container
cloud storage
```

Later AWS deployment may use appropriate static hosting architecture.

---

# SECTION 984 — SOURCE VS BUILD OUTPUT

## 1086.

Example:

```text
src/
→ source

dist/
→ generated build output
```

Generated output usually should not be manually edited.

Fix source and rebuild.

---

# SECTION 985 — GITIGNORE FOR NODE PROJECT

## 1087.

Typical entries:

```gitignore
node_modules/
dist/
coverage/
.env
playwright-report/
test-results/
```

depending on project.

We will confirm actual generated folders after implementation.

---

# SECTION 986 — SHOULD PACKAGE-LOCK BE IGNORED?

## 1088.

Normally for our frontend and automation applications:

```text
NO
```

Commit:

```text
package-lock.json
```

to improve reproducible installs.

---

# SECTION 987 — PLAYWRIGHT REPORT ARTIFACTS

## 1089.

Future test runs may create:

```text
playwright-report/
test-results/
screenshots
videos
traces
```

depending on configuration.

Generally generated reports should not be committed as permanent source files.

CI should publish them as artifacts.

---

# SECTION 988 — CI ARTIFACT VS GIT SOURCE

## 1090.

```text
Git
→ source/config/framework

CI artifact
→ generated report/evidence
```

Do not use Git commits as a report-storage mechanism.

---

# SECTION 989 — SOURCE CONTROL SECURITY

## 1091.

Before:

```bash
git add .
```

for Node projects, inspect:

```bash
git status
```

Ensure not staging:

```text
.env
tokens
auth state
downloads
screenshots containing secrets
generated reports
node_modules
```

---

# SECTION 990 — PLAYWRIGHT AUTH STATE

## 1092.

Playwright can save authenticated browser state to a file.

Such files may contain:

```text
cookies
tokens
session information
```

Therefore they may be sensitive.

If we implement stored auth state:

```text
ignore real auth state
generate/provide it securely
```

Do not casually commit it.

---

# SECTION 991 — SECRETS IN FRONTEND

## 1093.

Never assume:

```text
frontend env file
```

is secret.

If code reaches browser:

```text
user can inspect it
```

Frontend should call backend, and backend should hold real server secrets.

---

# SECTION 992 — API TOKEN IN PLAYWRIGHT

## 1094.

Automation secrets:

```text
test usernames
passwords
tokens
```

should come from:

```text
environment
CI secrets
approved secret manager
```

Reports/logging should sanitize them.

Same security principle already used in our REST Assured framework.

---

# SECTION 993 — TEST DATA FACTORY CONCEPT IN TYPESCRIPT

## 1095.

Future example:

```typescript
function uniqueEmail(): string {
  return `user-${Date.now()}@example.com`;
}
```

This demonstrates dynamic data generation concept.

Actual framework should consider:

```text
parallelism
collision risk
cleanup
readability
```

Timestamp alone may not be enough at high concurrency.

---

# SECTION 994 — RANDOM DATA LIBRARIES

## 1096.

Libraries exist for fake/random test data.

Do not add one automatically.

Ask:

```text
Can a small internal utility solve this?
Do we need dependency overhead?
Do we need reproducibility/seed support?
```

---

# SECTION 995 — DETERMINISTIC TEST DATA

## 1097.

Randomness can make debugging harder.

Good framework balances:

```text
unique data
+
repeatability
+
traceability
```

Example:

```text
test name + timestamp/UUID
```

depending on scenario.

---

# SECTION 996 — UUID

## 1098.

Modern environments can generate UUID-style identifiers.

Node provides cryptographic utilities such as:

```typescript
crypto.randomUUID()
```

depending on supported Node version.

Useful for unique test records.

---

# SECTION 997 — PAGE OBJECTS

## 1099.

Future Playwright framework may use Page Object Model.

Concept:

```text
Test
 ↓
Page Object
 ↓
Page/Locator
```

Goal:

```text
reduce locator duplication
improve readability
centralize page behavior
```

Do not turn every single UI element into unnecessary abstraction.

---

# SECTION 998 — FIXTURES

## 1100.

Playwright fixtures provide reusable test setup/context.

Conceptually:

```text
browser/page
auth
API client
test data
```

can be managed through fixtures.

We'll cover deeply in Playwright-specific notes.

---

# SECTION 999 — TYPESCRIPT FOR PAGE OBJECTS

## 1101.

Example concept:

```typescript
class LoginPage {
  constructor(private readonly page: Page) {}
}
```

TypeScript makes framework relationships explicit:

```text
LoginPage requires Page
```

---

# SECTION 1000 — READONLY LOCATORS

## 1102.

Page objects commonly define typed locators:

```typescript
readonly loginButton: Locator;
```

This helps maintainability and IDE support.

Exact implementation comes later.

---

# SECTION 1001 — NODE + API HYBRID TESTING

## 1103.

Future Playwright tests can combine:

```text
API setup
↓
UI validation
```

Example:

```text
create product through API
↓
open product in UI
↓
validate display
```

This is often faster and cleaner than creating all test data through UI.

---

# SECTION 1002 — UI + API + DB ARCHITECTURE

## 1104.

Future complete test architecture:

```text
Playwright UI
      ↓
React Frontend
      ↓
Spring Boot API
      ↓
PostgreSQL
```

Supporting API automation:

```text
REST Assured
      ↓
Spring Boot
```

Potential hybrid setup:

```text
Playwright API setup
+
Playwright UI validation
```

DB validation remains selective, not for every UI test.

---

# SECTION 1003 — WHY KEEP REST ASSURED AFTER PLAYWRIGHT?

## 1105.

Playwright can make API calls, but that does not automatically make existing REST Assured framework unnecessary.

Our architecture can intentionally use:

```text
REST Assured
→ deep API regression/integration suite

Playwright
→ UI + selected API setup/hybrid workflows
```

Each tool has a clear role.

---

# SECTION 1004 — DON'T DUPLICATE EVERY API TEST IN PLAYWRIGHT

## 1106.

Bad portfolio strategy:

```text
48 REST Assured API tests
+
same 48 rewritten in Playwright
```

without purpose.

Better:

```text
deep API coverage
→ REST Assured

critical UI/API integrated user journeys
→ Playwright
```

Shows test-strategy maturity.

---

# SECTION 1005 — JAVASCRIPT EXCEPTION

## 1107.

Example:

```text
TypeError
ReferenceError
SyntaxError
```

Understanding categories helps debugging.

---

# SECTION 1006 — TYPEERROR

## 1108.

Example scenario:

```typescript
user.name
```

when:

```text
user is undefined
```

can lead to:

```text
TypeError
```

Potential root cause:

```text
API field absent
test data not initialized
async operation not awaited
```

---

# SECTION 1007 — REFERENCEERROR

## 1109.

Occurs when code references identifier that does not exist in scope.

Example concept:

```javascript
console.log(notDefinedVariable);
```

---

# SECTION 1008 — SYNTAXERROR

## 1110.

Invalid JavaScript syntax.

Can happen during:

```text
parsing
JSON.parse
module parsing
```

depending on context.

---

# SECTION 1009 — ASSERTION ERROR

## 1111.

Test frameworks fail assertions intentionally.

Difference:

```text
Assertion failed
→ actual behavior did not meet expectation

TypeError
→ test/framework code may have crashed
```

Classify before reporting product bug.

---

# SECTION 1010 — AUTOMATION BUG VS PRODUCT BUG

## 1112.

Example:

```text
Playwright click throws because locator undefined
```

likely framework/test issue.

Example:

```text
UI shows wrong order total after API succeeds
```

may be product defect.

Evidence matters.

---

# SECTION 1011 — DEBUG NODE PROCESS

## 1113.

Basic diagnostic commands:

```bash
node --version
npm --version
which node
which npm
pwd
npm ls
```

Then inspect exact project script/configuration.

---

# SECTION 1012 — NPM CLEAN REINSTALL

## 1114.

Sometimes a clean reinstall is appropriate.

Concept:

```text
remove node_modules
reinstall from lock file
```

But:

```text
do not use destructive cleanup as step 1
```

First understand root cause.

In CI, `npm ci` already performs clean-style install behavior.

---

# SECTION 1013 — DELETE LOCK FILE? CAUTION

## 1115.

Internet advice often says:

```text
delete package-lock.json
delete node_modules
npm install
```

This changes dependency resolution and can hide original issue.

Do not delete lock file casually.

It is part of reproducibility.

---

# SECTION 1014 — NODE VERSION DRIFT

## 1116.

Local:

```text
Node X
```

CI:

```text
Node Y
```

Potential effects:

```text
package incompatibility
different runtime behavior
tool installation failure
```

Pin/declare CI version.

---

# SECTION 1015 — NPM VERSION DRIFT

## 1117.

Different npm versions can influence:

```text
lock file format
dependency installation behavior
warnings
```

Record versions during troubleshooting.

---

# SECTION 1016 — CI TROUBLESHOOTING EVIDENCE

## 1118.

Useful safe logs:

```bash
node --version
npm --version
pwd
npm config get registry
```

Potentially:

```text
OS
architecture
```

Do NOT print:

```text
tokens
registry passwords
all environment variables blindly
```

---

# SECTION 1017 — NEVER PRINT `env` IN CI CARELESSLY

## 1119.

CI environment contains secrets.

Bad:

```bash
env
```

in public/shared logs.

Better:

```text
print only safe metadata
check secret presence without printing value
```

Same security principle as Java/Spring tooling.

---

# SECTION 1018 — SAFE ENV CHECK

## 1120.

Example:

```bash
if [ -n "$BASE_URL" ]; then
  echo "BASE_URL is set"
else
  echo "BASE_URL is missing"
fi
```

For non-secret base URLs, printing actual value may be acceptable depending on environment.

For secrets:

```text
presence only
```

---

# SECTION 1019 — PLAYWRIGHT CONFIG AS CODE

## 1121.

One strength of TypeScript automation:

```text
configuration is code
```

Examples later:

```text
browser projects
timeouts
baseURL
retries
reporters
```

can be typed and version-controlled.

---

# SECTION 1020 — TYPESCRIPT COMPILE-TIME VS RUNTIME

## 1122.

Critical:

TypeScript can validate:

```text
types
```

before runtime.

But it cannot guarantee external data matches those types.

Example:

```typescript
interface User {
  id: number;
}
```

If backend actually returns:

```json
{
  "id": "wrong"
}
```

TypeScript interface alone does NOT validate runtime JSON.

---

# SECTION 1021 — TYPE ASSERTION IS NOT VALIDATION

## 1123.

Example:

```typescript
const user = response as User;
```

This tells TypeScript:

```text
treat this as User
```

It does not magically verify server response.

For API contract validation, use:

```text
assertions
schema validation
runtime validators
```

when needed.

---

# SECTION 1022 — API RESPONSE VALIDATION

## 1124.

For test:

```text
HTTP 200
+
body structure
+
critical fields
+
business values
```

should be validated based on contract.

Do not rely on TypeScript type declaration alone.

---

# SECTION 1023 — PLAYWRIGHT LOCATOR ASYNC MODEL

## 1125.

Important future mental model:

```text
Locator
→ description of element
```

Actual actions/assertions happen asynchronously.

We will avoid:

```text
manual sleeps
fragile XPath everywhere
```

where robust role/text/test-id locators are available.

---

# SECTION 1024 — NODE DEBUGGING WITH CONSOLE

## 1126.

Simple:

```typescript
console.log("value", value);
```

useful locally.

But framework-quality logging should avoid:

```text
sensitive values
huge payload dumps
random noisy debug statements
```

---

# SECTION 1025 — STRUCTURED LOGGING CONCEPT

## 1127.

Instead of:

```text
something failed
```

better logs can include:

```text
test
environment
operation
resource ID
status
timestamp
```

while sanitizing secrets.

This improves CI troubleshooting.

---

# SECTION 1026 — CONSOLE LOG VS TEST ATTACHMENT

## 1128.

Console:

```text
quick runtime evidence
```

Test attachment:

```text
structured report evidence
```

Future Playwright can attach:

```text
screenshots
traces
videos
API evidence
```

selectively.

---

# SECTION 1027 — TYPESCRIPT ACCESS MODIFIERS

## 1129.

TypeScript classes support:

```text
public
private
protected
```

Example:

```typescript
class UserService {
  private token: string;
}
```

Useful for framework encapsulation.

---

# SECTION 1028 — CONSTRUCTOR

## 1130.

Example:

```typescript
class LoginPage {
  constructor(private readonly page: Page) {}
}
```

Constructor receives dependencies.

Conceptually similar to constructor dependency injection style:

```text
object requires dependency
```

---

# SECTION 1029 — CLASS VS FUNCTION UTILITIES

## 1131.

Do not create classes for everything.

Use:

```text
class
```

when modeling state/behavior makes sense.

Use:

```text
function/module
```

for simple stateless utilities.

Good framework architecture minimizes unnecessary abstraction.

---

# SECTION 1030 — DRY PRINCIPLE

## 1132.

DRY:

```text
Don't Repeat Yourself
```

Useful for repeated framework behavior.

But over-DRY code can become difficult to understand.

Don't abstract two simple lines just to claim framework design.

---

# SECTION 1031 — KISS PRINCIPLE

## 1133.

KISS:

```text
Keep It Simple
```

A maintainable automation framework should favor:

```text
clear
predictable
easy-to-debug
```

code over clever abstractions.

---

# SECTION 1032 — YAGNI

## 1134.

YAGNI:

```text
You Aren't Gonna Need It
```

Do not implement:

```text
Kafka utility
Redis wrapper
five reporting systems
custom retry engine
```

before project actually needs them.

Strong portfolio engineering is intentional.

---

# SECTION 1033 — CLEAN AUTOMATION ARCHITECTURE

## 1135.

Future Playwright architecture should separate:

```text
tests
page/component objects
fixtures
test data
configuration
utilities
```

without creating too many layers.

Tests should remain readable.

---

# SECTION 1034 — INTERVIEW: WHAT IS NODE.JS?

## 1136. Strong Answer

> "Node.js is a JavaScript runtime that allows JavaScript to execute outside the browser. In SDET work it is particularly relevant because frameworks and tools such as Playwright, TypeScript compilers, frontend build tools and npm scripts run through the Node ecosystem."

---

# SECTION 1035 — INTERVIEW: NODE.JS VS JAVASCRIPT

## 1137.

> "JavaScript is the programming language, while Node.js is a runtime environment that can execute JavaScript outside a browser and provides server-side and operating-system APIs."

---

# SECTION 1036 — INTERVIEW: WHAT IS NPM?

## 1138.

> "npm is the package manager commonly used with Node.js. It manages project dependencies, package metadata, lock files and project scripts. In CI I prefer lock-file-driven reproducible dependency installation rather than relying on globally installed tools."

---

# SECTION 1037 — INTERVIEW: package.json

## 1139.

> "`package.json` is the central project metadata file for a Node project. It defines project information, scripts and dependency requirements such as dependencies and devDependencies."

---

# SECTION 1038 — INTERVIEW: package-lock.json

## 1140.

> "`package-lock.json` records the resolved dependency tree so installations are reproducible. I commit it for application and automation projects and use `npm ci` in CI where a valid lock file is present."

---

# SECTION 1039 — INTERVIEW: npm install VS npm ci

## 1141.

> "`npm install` is the normal development command for installing or updating dependencies according to project metadata, while `npm ci` performs a clean installation using the committed lock file and is well suited to reproducible CI environments."

---

# SECTION 1040 — INTERVIEW: dependencies VS devDependencies

## 1142.

> "`dependencies` normally contain packages required by the application at runtime, while `devDependencies` contain tooling required during development, testing or building, such as TypeScript or test frameworks. Exact deployment behavior depends on how the project is installed."

---

# SECTION 1041 — INTERVIEW: npx

## 1143.

> "`npx` allows me to execute package-provided binaries, often using the locally installed project version. For example, I can run a project's Playwright command without depending on a globally installed Playwright version."

---

# SECTION 1042 — INTERVIEW: TYPESCRIPT

## 1144.

> "TypeScript is a typed superset of JavaScript. It adds compile-time type checking and improves refactoring, IDE support and maintainability. It is particularly useful in larger Playwright frameworks because page objects, fixtures, API models and test utilities can have explicit contracts."

---

# SECTION 1043 — INTERVIEW: DOES TYPESCRIPT VALIDATE API JSON?

## 1145.

> "No. A TypeScript interface only describes what the code expects at compile time. External JSON still needs runtime assertions or schema validation if I need to prove the API response matches the contract."

---

# SECTION 1044 — INTERVIEW: ASYNC/AWAIT

## 1146.

> "`async/await` is syntax for working with Promises in a readable way. Browser and network operations are asynchronous, so Playwright actions and assertions commonly need `await`. Missing it can create incorrect ordering or flaky test behavior."

---

# SECTION 1045 — INTERVIEW: WHY NOT COMMIT NODE_MODULES?

## 1147.

> "`node_modules` is generated from package metadata, can be very large and can contain platform-specific dependency content. I commit `package.json` and the lock file, then recreate dependencies using npm rather than version-controlling `node_modules`."

---

# SECTION 1046 — INTERVIEW: WHY LOCK FILE?

## 1148.

> "The lock file improves reproducibility by recording the exact resolved dependency tree. Without it, two environments can potentially resolve different compatible versions from the ranges declared in `package.json`."

---

# SECTION 1047 — INTERVIEW: WHAT IS `process.env`?

## 1149.

> "`process.env` exposes environment variables to Node.js code. I use environment variables to keep environment-specific configuration and secrets outside test source code and validate required values early so configuration errors fail clearly."

---

# SECTION 1048 — INTERVIEW: FRONTEND ENV SECRET?

## 1150.

> "Frontend environment variables should not be treated as secret because values included in browser-delivered code can be inspected by users. Backend credentials and signing secrets must remain server-side."

---

# SECTION 1049 — INTERVIEW: NPM CI FAILS ONLY IN CI

## 1151.

> "I compare Node and npm versions, lock-file consistency, registry configuration, DNS, proxy/TLS settings, OS and CPU architecture. I avoid immediately deleting the lock file because that changes the dependency resolution and can hide the original issue."

---

# SECTION 1050 — INTERVIEW: WHY TYPESCRIPT FOR PLAYWRIGHT?

## 1152.

> "TypeScript gives me typed fixtures, page objects, configuration and test data, better IDE support and safer refactoring. That becomes valuable as an automation framework grows across multiple modules and contributors."

---

# SECTION 1051 — INTERVIEW: COMMONJS VS ESM

## 1153.

> "CommonJS commonly uses `require` and `module.exports`, while ES Modules use `import` and `export`. Modern TypeScript and Playwright projects frequently use ES-module-style imports, and I keep the project's module configuration consistent rather than mixing both styles blindly."

---

# SECTION 1052 — SENIOR SDET SCENARIO: LOCAL PASSES, CI MODULE NOT FOUND

## 1154.

Check:

```text
package committed?
package-lock committed?
npm ci successful?
import path exact?
case mismatch?
working directory?
Node version?
```

Classic example:

```text
LoginPage.ts
vs
loginPage.ts
```

Linux CI may expose casing problems hidden locally.

---

# SECTION 1053 — SENIOR SDET SCENARIO: PLAYWRIGHT COMMAND NOT FOUND

## 1155.

Check:

```text
dependency installed?
node_modules present?
package.json contains package?
use npx/project script?
```

Try:

```bash
npx playwright --version
```

after dependency installation.

---

# SECTION 1054 — SENIOR SDET SCENARIO: NPM INSTALL TIMEOUT

## 1156.

Classify as dependency/network issue.

Check:

```text
registry
DNS
proxy
VPN
TLS
corporate CA
network stability
```

Do not modify application test assertions.

---

# SECTION 1055 — SENIOR SDET SCENARIO: BUILD WORKS, TYPECHECK FAILS

## 1157.

Possible explanation:

```text
build tool transformed TypeScript
without enforcing full type checking
```

Investigate:

```text
tsconfig
typecheck script
actual compiler errors
```

This is why separate quality gates can be valuable.

---

# SECTION 1056 — SENIOR SDET SCENARIO: UI TEST FLAKY WITH MISSING AWAIT

## 1158.

Symptom:

```text
click sometimes executes before prerequisite action completes
```

Investigate:

```text
missing await
incorrect Promise handling
unnecessary manual async wrapper
test-data race
```

Do not add:

```text
5 second sleep
```

first.

---

# SECTION 1057 — SENIOR SDET SCENARIO: 20 PARALLEL TESTS FAIL RANDOMLY

## 1159.

Check:

```text
shared user
shared product
shared cart
shared order
cleanup race
rate limit
environment capacity
worker configuration
```

Parallel framework does not automatically mean tests are parallel-safe.

---

# SECTION 1058 — SENIOR SDET SCENARIO: FRONTEND BUILD EXPOSES SECRET

## 1160.

Cause:

```text
secret included in browser-facing environment config
```

Correct architecture:

```text
Browser
↓
Backend
↓
Secret-protected external dependency
```

Never solve server-secret access by putting secret into Vite variable.

---

# SECTION 1059 — SENIOR SDET SCENARIO: NPM AUDIT HIGH VULNERABILITY

## 1161.

Approach:

```text
identify affected dependency
determine direct vs transitive
check exploitability/context
review available compatible upgrade
test upgrade
record risk/remediation
```

Do not blindly force upgrade major versions.

---

# SECTION 1060 — SENIOR SDET SCENARIO: DIFFERENT PACKAGE VERSION IN CI

## 1162.

Verify:

```text
package-lock committed?
npm ci used?
correct branch?
lock-file changed?
cache issue?
npm version?
```

Reproducible builds require dependency discipline.

---

# SECTION 1061 — RAPID FIRE NODE/NPM

## 1163. JavaScript runtime outside browser?

```text
Node.js
```

## 1164. Node package manager?

```text
npm
```

## 1165. Main npm project file?

```text
package.json
```

## 1166. Lock file?

```text
package-lock.json
```

## 1167. Installed dependency folder?

```text
node_modules
```

## 1168. Install dependencies?

```bash
npm install
```

## 1169. Clean CI installation?

```bash
npm ci
```

## 1170. Install dev dependency?

```bash
npm install -D <package>
```

## 1171. Remove dependency?

```bash
npm uninstall <package>
```

## 1172. Run npm script?

```bash
npm run <script>
```

## 1173. Execute local package binary?

```bash
npx <command>
```

## 1174. Node version?

```bash
node --version
```

## 1175. npm version?

```bash
npm --version
```

## 1176. Dependency tree?

```bash
npm ls
```

## 1177. Security audit?

```bash
npm audit
```

## 1178. TypeScript compiler?

```text
tsc
```

---

# SECTION 1062 — RAPID FIRE TYPESCRIPT

## 1179. TypeScript extension?

```text
.ts
```

## 1180. React TypeScript component extension?

```text
.tsx
```

## 1181. TypeScript config?

```text
tsconfig.json
```

## 1182. Typed object contract?

```text
interface / type
```

## 1183. Optional property?

```text
?
```

## 1184. Unsafe escape from typing?

```text
any
```

## 1185. Safer unknown value?

```text
unknown
```

## 1186. Async result abstraction?

```text
Promise
```

## 1187. Wait for Promise in async function?

```text
await
```

## 1188. Modern module syntax?

```text
import / export
```

---

# SECTION 1063 — PACKAGE FILE MENTAL MODEL

## 1189.

```text
package.json
   ↓
declares packages/scripts

package-lock.json
   ↓
pins resolved tree

npm ci
   ↓
installs dependencies

node_modules
   ↓
local installed packages

npm run ...
   ↓
executes project workflow
```

---

# SECTION 1064 — TYPESCRIPT EXECUTION MENTAL MODEL

## 1190.

```text
TypeScript Source
     ↓
Type Checking / Transformation
     ↓
JavaScript
     ↓
Node.js
or
Browser
```

---

# SECTION 1065 — FUTURE FRONTEND MENTAL MODEL

## 1191.

```text
React + TypeScript
      ↓
Vite
      ↓
Frontend Dev Server
      ↓
Browser
      ↓
CORS
      ↓
Spring Boot
      ↓
PostgreSQL
```

---

# SECTION 1066 — FUTURE PLAYWRIGHT MENTAL MODEL

## 1192.

```text
TypeScript Tests
      ↓
Node.js
      ↓
Playwright Test
      ↓
Browser
      ↓
React UI
      ↓
Spring Boot API
```

Supporting setup:

```text
API Request
→ create/prepare data

UI
→ validate user journey
```

---

# SECTION 1067 — JAVA + NODE PROJECT MENTAL MODEL

## 1193.

Our eventual repository:

```text
SDET-Commerce-Automation
│
├── backend
│   ├── Java
│   ├── Maven
│   └── Spring Boot
│
├── api-automation
│   ├── Java
│   ├── Maven
│   └── REST Assured
│
├── frontend
│   ├── React
│   ├── TypeScript
│   ├── Node.js tooling
│   └── npm
│
└── ui-automation
    ├── Playwright
    ├── TypeScript
    ├── Node.js
    └── npm
```

This shows two different build ecosystems:

```text
Java
→ Maven

JavaScript/TypeScript
→ npm
```

---

# SECTION 1068 — MAVEN VS NPM MENTAL COMPARISON

## 1194.

```text
JAVA WORLD                NODE WORLD

pom.xml                   package.json
dependency management     dependency management
Maven Central             npm registry
.m2 repository            npm cache/node_modules
mvn test                  npm test
Maven Wrapper             project-local npm tooling
dependency tree           npm ls
```

This is conceptual, not one-to-one.

---

# SECTION 1069 — SDET TOOLCHAIN FLOW

## 1195.

Future full toolchain:

```text
Git
 ↓
Java / Node
 ↓
Maven / npm
 ↓
Spring Boot / React / Playwright
 ↓
Docker
 ↓
CI/CD
 ↓
AWS
```

Understanding each layer lets us troubleshoot without guessing.

---

# SECTION 1070 — TOOLING TROUBLESHOOTING DECISION TREE

## 1196.

```text
JS/TS PROJECT FAILS
      ↓
Does Node run?
      |
      ├── NO
      │    ↓
      │  PATH/version/install
      │
      └── YES
           ↓
Does npm work?
           |
           ├── NO
           │    ↓
           │  Node/npm setup
           │
           └── YES
                ↓
Can dependencies install?
                |
                ├── NO
                │    ↓
                │ registry/network/proxy/TLS/lock
                │
                └── YES
                     ↓
Does typecheck/build pass?
                     |
                     ├── NO
                     │    ↓
                     │ source/config/dependency
                     │
                     └── YES
                          ↓
Do tests execute?
                          |
                          ├── NO
                          │    ↓
                          │ framework/config/env/browser
                          │
                          └── YES
                               ↓
Product behavior/assertion?
```

---

# SECTION 1071 — DON'T MIX FAILURE LAYERS

## 1197.

Examples:

```text
npm ci failed
→ dependency/tooling issue

TypeScript compile failed
→ source/type/config issue

Playwright browser launch failed
→ browser/runtime issue

HTTP 401
→ application authentication issue

Assertion failed
→ expected vs actual behavior
```

Correct classification saves time.

---

# SECTION 1072 — ONE-MINUTE NODE/NPM/TYPESCRIPT INTERVIEW ANSWER

## 1198.

> "For JavaScript and TypeScript automation I understand the full Node.js toolchain rather than only Playwright syntax. Node.js is the runtime, npm manages project dependencies and scripts, `package.json` defines the project and dependency ranges, and the lock file keeps resolved dependencies reproducible. For CI I prefer a known Node version and lock-file-driven installation such as `npm ci`. TypeScript adds compile-time type safety for page objects, fixtures, API models and framework utilities, while I still validate external API responses at runtime rather than assuming TypeScript types prove the contract."

---

# SECTION 1073 — CURRENT VS FUTURE PROJECT STATUS

## 1199. Currently Implemented

```text
Java 17
Spring Boot
Maven
REST Assured
TestNG
PostgreSQL
Docker Compose for PostgreSQL
JWT/RBAC
Allure
Swagger
```

## 1200. Next Application Phase

```text
React
TypeScript
Vite
npm
Axios
React Router
```

## 1201. Then Automation Phase

```text
Playwright
TypeScript
npm
UI automation
UI + API hybrid flows
```

Important:

```text
Do not claim React/Playwright implementation is complete yet.
```

---

# SECTION 1074 — FIRST PRACTICAL CHECK BEFORE FRONTEND

## 1202.

When we reach frontend implementation, first run:

```bash
node --version
npm --version
```

Then decide:

```text
Is installed Node version compatible with chosen Vite/React tooling?
```

Only after that should we scaffold the frontend.

---

# SECTION 1075 — FINAL 5-MINUTE REVISION

## 1203. Core Tools

```text
JavaScript
→ language

Node.js
→ runtime

npm
→ package manager

package.json
→ project/dependency/script metadata

package-lock.json
→ resolved dependency tree

node_modules
→ installed packages

npx
→ execute package binary

TypeScript
→ typed JavaScript superset/tooling

tsconfig.json
→ TypeScript configuration

Vite
→ frontend development/build tooling
```

---

# SECTION 1076 — FINAL SECURITY REVISION

## 1204.

Never commit:

```text
.env secrets
tokens
passwords
auth state
node_modules
generated sensitive reports
```

Never assume:

```text
VITE_* variable is secret
```

Never solve TLS issue with:

```text
certificate verification disabled globally
```

Never blindly:

```text
npm audit fix --force
delete package-lock.json
upgrade every package
```

Review and understand changes.

---

# SECTION 1077 — FINAL CI REVISION

## 1205.

Conceptual Node CI:

```text
Checkout
   ↓
Set known Node version
   ↓
npm ci
   ↓
lint
   ↓
typecheck
   ↓
tests
   ↓
build
   ↓
publish artifacts
```

Exact steps will be created when our frontend/UI automation CI is implemented.

---

# SECTION 1078 — FINAL SDET PRINCIPLE

## 1206.

Do not say:

```text
"Playwright isn't working."
```

Say:

```text
"The Playwright package is installed, but browser launch fails
because the CI runner is missing required browser dependencies."
```

Do not say:

```text
"npm is broken."
```

Say:

```text
"`npm ci` cannot resolve the configured registry because DNS resolution
fails on the CI runner."
```

Do not say:

```text
"TypeScript issue."
```

Say:

```text
"The build succeeds, but explicit TypeScript type checking fails because
the API model declares `id` as a number while the assignment supplies
a string."
```

Senior SDET mindset:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
REPRODUCE
   ↓
FIX / REPORT
```

---

# END OF PART 6 — NODE.JS, NPM, PACKAGE.JSON & JAVASCRIPT/TYPESCRIPT TOOLING FOR SDET

Next:

**PART 7 — JSON, YAML & CONFIGURATION MANAGEMENT FOR SDET**

---

# PART 7 — JSON, YAML & CONFIGURATION MANAGEMENT FOR SDET

# SECTION 1079 — WHY JSON & YAML MATTER FOR SDET

## 1207. Where We Use Them

As an SDET, JSON and YAML appear almost everywhere:

```text
REST API request/response
GraphQL variables
test data
configuration
Docker Compose
CI/CD pipelines
OpenAPI
package files
cloud configuration
test reports
```

In our project:

```text
REST API
→ JSON

Swagger/OpenAPI
→ JSON representation available

Docker Compose
→ YAML

Future GitHub Actions
→ YAML

Frontend/API communication
→ JSON
```

Understanding the format is not enough.

A Senior SDET should understand:

```text
syntax
data types
nesting
validation
configuration precedence
environment management
secret handling
parsing failures
CI configuration
```

---

# SECTION 1080 — WHAT IS JSON?

## 1208. Definition

JSON stands for:

```text
JavaScript Object Notation
```

It is a lightweight text-based data-interchange format.

Example:

```json
{
  "name": "Test Product",
  "price": 499.99,
  "available": true
}
```

JSON is widely used for communication between:

```text
frontend
backend
services
automation frameworks
external APIs
```

---

# SECTION 1081 — JSON IS LANGUAGE INDEPENDENT

## 1209.

Although JSON originated from JavaScript syntax concepts, it is used by many languages:

```text
Java
JavaScript
TypeScript
Python
C#
Go
Kotlin
```

Example:

```text
Spring Boot
     ↓ JSON
React
```

REST Assured can also send and validate JSON.

---

# SECTION 1082 — JSON OBJECT

## 1210.

JSON object uses:

```text
{ }
```

Example:

```json
{
  "id": 101,
  "name": "Laptop"
}
```

It contains:

```text
key : value
```

pairs.

---

# SECTION 1083 — JSON ARRAY

## 1211.

Array uses:

```text
[ ]
```

Example:

```json
[
  {
    "id": 1,
    "name": "Product A"
  },
  {
    "id": 2,
    "name": "Product B"
  }
]
```

An array is an ordered collection of values.

---

# SECTION 1084 — JSON DATA TYPES

## 1212.

JSON supports:

```text
string
number
boolean
null
object
array
```

Example:

```json
{
  "name": "Laptop",
  "price": 75000,
  "rating": 4.5,
  "available": true,
  "discount": null,
  "seller": {
    "name": "Demo Seller"
  },
  "tags": [
    "electronics",
    "computer"
  ]
}
```

---

# SECTION 1085 — JSON DOES NOT HAVE EVERY PROGRAMMING TYPE

## 1213.

JSON does NOT directly contain types such as:

```text
Date
LocalDateTime
enum
class
function
undefined
```

They must be represented using supported JSON values.

Example date:

```json
{
  "createdAt": "2026-09-10T10:30:00Z"
}
```

This is technically:

```text
string
```

whose meaning is defined by the API contract.

---

# SECTION 1086 — JSON STRING

## 1214.

Strings require double quotes.

Correct:

```json
{
  "name": "Laptop"
}
```

Invalid JSON:

```text
{
  "name": 'Laptop'
}
```

Single quotes are not valid JSON string delimiters.

---

# SECTION 1087 — JSON PROPERTY NAMES

## 1215.

JSON object property names must use double quotes.

Correct:

```json
{
  "email": "test@example.com"
}
```

Invalid JSON:

```text
{
  email: "test@example.com"
}
```

JavaScript object syntax may allow this.

JSON does not.

---

# SECTION 1088 — JSON NUMBER

## 1216.

Example:

```json
{
  "quantity": 3,
  "price": 499.99
}
```

Important testing point:

```text
3
```

is different in representation/type from:

```text
"3"
```

If API contract says number but backend returns string, that can be a schema/contract issue.

---

# SECTION 1089 — JSON BOOLEAN

## 1217.

Correct:

```json
{
  "active": true
}
```

Not:

```json
{
  "active": "true"
}
```

The second value is a:

```text
string
```

not boolean.

---

# SECTION 1090 — JSON NULL

## 1218.

Example:

```json
{
  "middleName": null
}
```

`null` can represent an intentional absence of value.

But:

```json
{
}
```

means the property itself is absent.

These are not always semantically identical.

---

# SECTION 1091 — NULL VS MISSING FIELD

## 1219.

Response A:

```json
{
  "description": null
}
```

Response B:

```json
{
}
```

Difference:

```text
A
→ property exists with null value

B
→ property does not exist
```

A good API test should know which behavior the contract expects.

---

# SECTION 1092 — EMPTY STRING VS NULL

## 1220.

These are different:

```json
{
  "name": ""
}
```

and:

```json
{
  "name": null
}
```

and:

```json
{}
```

Possible test cases:

```text
missing field
null field
empty string
whitespace-only string
valid value
```

---

# SECTION 1093 — NESTED JSON

## 1221.

Example:

```json
{
  "orderId": 1001,
  "customer": {
    "id": 20,
    "name": "Test User"
  },
  "items": [
    {
      "productId": 5,
      "quantity": 2
    }
  ]
}
```

Structure:

```text
order
├── orderId
├── customer
│   ├── id
│   └── name
└── items
    └── product
```

Understanding nested structures is essential for API assertions.

---

# SECTION 1094 — JSON ESCAPING

## 1222.

Special characters may need escaping.

Example:

```json
{
  "message": "He said \"hello\""
}
```

Common escapes:

```text
\"
\\
\n
\t
\r
```

---

# SECTION 1095 — JSON COMMENTS

## 1223.

Standard JSON does NOT support comments.

Invalid:

```json
{
  // API URL
  "baseUrl": "http://localhost:8080"
}
```

This is important when editing configuration files.

Some tools support JSON-like formats with comments, but that is tool-specific and not standard JSON.

---

# SECTION 1096 — TRAILING COMMA

## 1224.

Invalid standard JSON:

```json
{
  "name": "Laptop",
  "price": 1000,
}
```

Trailing comma after final property is not valid JSON.

---

# SECTION 1097 — JSON PARSING

## 1225.

Parsing means converting JSON text into an in-memory representation.

Concept:

```text
JSON text
   ↓ parser
Object / Map / DTO / JavaScript object
```

Malformed JSON causes parsing failure.

---

# SECTION 1098 — SERIALIZATION

## 1226.

Serialization:

```text
Programming Object
      ↓
JSON
```

Example:

```text
Java User object
      ↓
JSON response
```

---

# SECTION 1099 — DESERIALIZATION

## 1227.

Deserialization:

```text
JSON
 ↓
Programming Object
```

Example:

```text
API JSON response
      ↓
Java DTO
```

---

# SECTION 1100 — SERIALIZATION IN SPRING BOOT

## 1228.

At a high level:

```text
Controller returns Java object
        ↓
Spring HTTP message conversion
        ↓
JSON response
```

Spring Boot commonly uses Jackson for JSON processing when the relevant web stack is configured.

Do not confuse:

```text
Java object
```

with:

```text
JSON text
```

---

# SECTION 1101 — DESERIALIZATION IN SPRING BOOT

## 1229.

Concept:

```text
HTTP JSON Request
      ↓
Spring
      ↓
Java request object
```

If incoming JSON cannot be converted to expected Java structure/type, request processing may fail before business logic executes.

---

# SECTION 1102 — JSON TYPE MISMATCH

## 1230.

Suppose backend expects:

```text
quantity → integer
```

Client sends:

```json
{
  "quantity": "abc"
}
```

Possible result:

```text
deserialization/binding failure
4xx response
```

depending on application handling.

This is a valuable negative API test.

---

# SECTION 1103 — UNKNOWN JSON FIELD

## 1231.

Request:

```json
{
  "name": "Laptop",
  "unexpectedField": "value"
}
```

Behavior depends on application configuration.

Backend may:

```text
ignore it
reject it
process custom extension
```

Do not assume.

Test according to API contract.

---

# SECTION 1104 — JSON SCHEMA

## 1232.

JSON Schema can describe expected JSON structure.

Concept:

```text
field names
types
required fields
nested structure
constraints
```

Example conceptual schema:

```json
{
  "type": "object",
  "required": ["id", "name"],
  "properties": {
    "id": {
      "type": "integer"
    },
    "name": {
      "type": "string"
    }
  }
}
```

---

# SECTION 1105 — SCHEMA VALIDATION

## 1233.

Schema validation can detect:

```text
missing required property
wrong datatype
incorrect nested structure
unexpected contract changes
```

Example:

Expected:

```text
id → integer
```

Actual:

```text
id → string
```

Schema validation can detect the mismatch.

---

# SECTION 1106 — SCHEMA VS BUSINESS ASSERTION

## 1234.

Schema validation:

```text
Does response have correct structure/type?
```

Business assertion:

```text
Does order total equal expected value?
```

Both matter.

Example:

```json
{
  "total": 10
}
```

Schema may pass because:

```text
total is number
```

But business rule may expect:

```text
total = 500
```

So schema alone is not enough.

---

# SECTION 1107 — CONTRACT TESTING MINDSET

## 1235.

API validation can include:

```text
HTTP status
headers
schema
required fields
data types
business values
error contract
security behavior
```

Senior SDET should choose the appropriate depth rather than asserting every field mechanically.

---

# SECTION 1108 — JSON PATH

## 1236.

JSONPath is a way to navigate JSON structures.

Conceptually similar to:

```text
XPath for XML
```

but designed for JSON.

Example JSON:

```json
{
  "user": {
    "name": "Test User"
  }
}
```

Path concept:

```text
user.name
```

---

# SECTION 1109 — JSONPATH ARRAY

## 1237.

Example:

```json
{
  "products": [
    {
      "name": "A"
    },
    {
      "name": "B"
    }
  ]
}
```

Concept:

```text
products[0].name
```

returns first product name.

---

# SECTION 1110 — JSONPATH IN REST ASSURED

## 1238.

Conceptual REST Assured assertion:

```java
.then()
    .statusCode(200)
    .body("name", equalTo("Test Product"));
```

Nested example:

```java
.body("user.email", equalTo("test@example.com"));
```

Exact assertions depend on actual API response.

---

# SECTION 1111 — EXTRACTING JSON VALUE

## 1239.

Concept:

```java
String token =
    response.jsonPath().getString("token");
```

This is useful for:

```text
authentication
resource IDs
dependent API calls
```

Sensitive values should not be printed into logs unnecessarily.

---

# SECTION 1112 — JSON COLLECTION ASSERTIONS

## 1240.

For arrays we may validate:

```text
size
specific values
contains item
ordering if contract requires it
unique values
filter/search behavior
```

Do not assume API ordering unless specified.

---

# SECTION 1113 — ORDER OF JSON OBJECT KEYS

## 1241.

Tests should generally not depend on object-property display order.

These:

```json
{
  "id": 1,
  "name": "A"
}
```

and:

```json
{
  "name": "A",
  "id": 1
}
```

represent the same object properties for normal API semantics.

Avoid raw string equality for JSON when structural comparison is intended.

---

# SECTION 1114 — RAW JSON STRING COMPARISON

## 1242.

Weak:

```text
actualJson.equals(expectedJsonString)
```

because differences in:

```text
whitespace
property order
formatting
```

may cause unnecessary failure.

Prefer:

```text
parsed structural assertions
schema validation
specific business assertions
```

---

# SECTION 1115 — PRETTY JSON

## 1243.

Pretty formatting:

```json
{
  "id": 1,
  "name": "Product"
}
```

Compact:

```json
{"id":1,"name":"Product"}
```

Both can represent equivalent JSON.

Formatting should not drive functional test results.

---

# SECTION 1116 — WHAT IS YAML?

## 1244.

YAML is a human-readable data serialization format commonly used for configuration.

Typical uses:

```text
Docker Compose
GitHub Actions
Kubernetes
application configuration
CI/CD
OpenAPI
```

Files commonly use:

```text
.yaml
.yml
```

---

# SECTION 1117 — YAML STRUCTURE

## 1245.

Example:

```yaml
name: SDET Commerce
environment: local
enabled: true
```

YAML uses:

```text
key: value
```

syntax.

---

# SECTION 1118 — YAML INDENTATION

## 1246. Critical Rule

YAML structure depends heavily on indentation.

Example:

```yaml
database:
  host: localhost
  port: 5432
```

Meaning:

```text
database
├── host
└── port
```

Incorrect indentation can:

```text
change meaning
cause parsing failure
break pipeline
break Docker Compose
```

---

# SECTION 1119 — TABS IN YAML

## 1247.

Use spaces for YAML indentation.

Avoid tabs for indentation.

Recommended practical approach:

```text
configure editor to insert spaces
keep indentation consistent
```

---

# SECTION 1120 — YAML NESTED MAPPING

## 1248.

Example:

```yaml
application:
  name: sdet-commerce
  environment: local
```

Equivalent conceptually to JSON:

```json
{
  "application": {
    "name": "sdet-commerce",
    "environment": "local"
  }
}
```

---

# SECTION 1121 — YAML LIST

## 1249.

Example:

```yaml
browsers:
  - chromium
  - firefox
  - webkit
```

Equivalent JSON:

```json
{
  "browsers": [
    "chromium",
    "firefox",
    "webkit"
  ]
}
```

---

# SECTION 1122 — YAML LIST OF OBJECTS

## 1250.

Example:

```yaml
environments:
  - name: local
    url: http://localhost:8080

  - name: qa
    url: https://example.test
```

This represents a list containing structured objects.

---

# SECTION 1123 — YAML COMMENTS

## 1251.

Unlike JSON, YAML supports comments.

Example:

```yaml
# Local development configuration
environment: local
```

Useful for documentation.

But avoid excessive comments that become stale.

---

# SECTION 1124 — YAML STRINGS

## 1252.

Strings can often be written without quotes:

```yaml
name: backend
```

Or quoted:

```yaml
name: "backend"
```

Quoting can be useful when a value could be interpreted ambiguously.

---

# SECTION 1125 — YAML BOOLEAN / TYPE SURPRISES

## 1253.

YAML parsers/schema versions can interpret certain unquoted values differently.

Safe engineering principle:

```text
when type interpretation matters,
be explicit and validate with the target tool
```

Do not assume every YAML parser behaves identically.

---

# SECTION 1126 — YAML MULTILINE STRING

## 1254.

Literal block:

```yaml
message: |
  First line
  Second line
```

Folded block:

```yaml
message: >
  First line
  Second line
```

These have different newline-folding behavior.

Useful in:

```text
scripts
configuration
CI messages
```

---

# SECTION 1127 — YAML ANCHORS

## 1255.

YAML supports anchors and aliases.

Concept:

```yaml
defaults: &defaults
  retries: 2
  timeout: 30
```

Then reuse conceptually with alias/merge patterns.

Useful but can reduce readability if overused.

For CI configuration:

```text
clarity > clever YAML
```

---

# SECTION 1128 — JSON VS YAML

## 1256.

```text
JSON
→ strict data exchange format
→ common for APIs
→ braces/brackets

YAML
→ human-oriented configuration
→ indentation-based
→ comments supported
```

Typical SDET usage:

```text
API body
→ JSON

CI pipeline
→ YAML
```

---

# SECTION 1129 — SAME DATA IN JSON AND YAML

## 1257.

JSON:

```json
{
  "database": {
    "host": "localhost",
    "port": 5432
  }
}
```

YAML:

```yaml
database:
  host: localhost
  port: 5432
```

Same conceptual structure.

Different serialization syntax.

---

# SECTION 1130 — YAML PARSER ERROR

## 1258.

Common causes:

```text
wrong indentation
missing colon
bad list structure
tabs
incorrect quoting
tool-specific schema violation
```

Important distinction:

```text
valid YAML
≠ valid configuration for the tool
```

---

# SECTION 1131 — SYNTAX VS SCHEMA

## 1259.

Example YAML may be syntactically valid:

```yaml
something:
  abc: xyz
```

But GitHub Actions may reject it because:

```text
keys do not match workflow schema
```

So troubleshooting has two layers:

```text
1. Is YAML valid?
2. Is configuration valid for target tool?
```

---

# SECTION 1132 — DOCKER COMPOSE YAML

## 1260.

Our project currently uses root:

```text
docker-compose.yml
```

for PostgreSQL.

Known structure includes:

```yaml
services:
  postgres:
    image: postgres:16
```

This tells Compose that:

```text
postgres
```

is a service using:

```text
postgres:16
```

image.

---

# SECTION 1133 — DOCKER COMPOSE ENVIRONMENT VARIABLES

## 1261.

Our Compose configuration externalizes database credentials.

Pattern:

```yaml
environment:
  POSTGRES_DB: ${DB_NAME:-sdetcommerce}
  POSTGRES_USER: ${DB_USERNAME}
  POSTGRES_PASSWORD: ${DB_PASSWORD}
```

Meaning:

```text
DB_NAME
→ use environment value if supplied

sdetcommerce
→ default if DB_NAME is not supplied

DB_USERNAME
→ must come from environment/context

DB_PASSWORD
→ must come from environment/context
```

Never hardcode real credentials in committed YAML.

---

# SECTION 1134 — `${VAR}`

## 1262.

Pattern:

```text
${VAR}
```

means:

```text
resolve variable from configuration/environment context
```

Exact interpolation behavior is tool-specific.

For Docker Compose, Compose performs its own variable interpolation.

---

# SECTION 1135 — DEFAULT VALUE SYNTAX

## 1263.

In our Compose file:

```text
${DB_NAME:-sdetcommerce}
```

means conceptually:

```text
use DB_NAME when appropriately set,
otherwise use sdetcommerce
```

Shell-like interpolation details matter.

Do not assume every tool uses the same `${...}` semantics.

---

# SECTION 1136 — SPRING PROPERTY PLACEHOLDERS

## 1264.

Our Spring Boot configuration uses patterns such as:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/sdetcommerce}
```

Notice:

Docker Compose:

```text
${VAR:-default}
```

Spring property placeholder:

```text
${VAR:default}
```

These are similar-looking but belong to different configuration systems.

This is an important troubleshooting distinction.

---

# SECTION 1137 — SAME SYMBOL, DIFFERENT TOOL

## 1265.

Never think:

```text
${...}
```

has one universal behavior.

Examples:

```text
shell
Docker Compose
Spring
GitHub Actions
```

can all have different syntax/rules.

Always identify:

```text
Which tool is parsing this file?
```

---

# SECTION 1138 — CONFIGURATION MANAGEMENT

## 1266.

Configuration management means separating:

```text
application behavior
```

from:

```text
environment-specific values
```

Examples:

```text
base URL
database URL
credentials
timeouts
feature flags
environment name
```

---

# SECTION 1139 — WHY NOT HARDCODE CONFIG?

## 1267.

Bad:

```java
String baseUrl = "https://qa.example.com";
```

inside every test.

Problems:

```text
environment switching difficult
duplicate values
secret leakage risk
CI override difficult
maintenance cost
```

Better:

```text
external configuration
```

---

# SECTION 1140 — CONFIGURATION CATEGORIES

## 1268.

Think of configuration as:

```text
non-secret config
secret config
environment-specific config
build config
runtime config
test config
```

Examples:

```text
BASE_URL
→ non-secret/environment-specific

DB_PASSWORD
→ secret

TEST_ENV
→ test/runtime config
```

---

# SECTION 1141 — SECRET VS CONFIGURATION

## 1269.

Not every config is secret.

Example:

```text
http://localhost:8080
```

usually not secret.

But:

```text
password
JWT signing secret
private token
API key
```

are secrets.

This distinction matters for:

```text
logs
Git
CI
reports
```

---

# SECTION 1142 — ENVIRONMENT VARIABLES

## 1270.

Environment variables are commonly used to inject runtime configuration.

Example:

```bash
export TEST_ENV=qa
```

Application/test process reads:

```text
TEST_ENV
```

without hardcoding it in source.

---

# SECTION 1143 — PROCESS ENVIRONMENT

## 1271.

Environment variables belong to a process environment.

Concept:

```text
Shell
  ↓
Environment Variables
  ↓
Child Process
```

Example:

```bash
export TEST_ENV=qa
mvn test
```

Maven/test process inherits the exported variable.

---

# SECTION 1144 — TEMPORARY ENV VALUE

## 1272.

Unix-like shell:

```bash
TEST_ENV=qa mvn test
```

This sets:

```text
TEST_ENV
```

for that command execution.

Useful for temporary overrides.

---

# SECTION 1145 — `.env` FILE

## 1273.

A `.env` file commonly stores local environment values in simple form:

```text
DB_USERNAME=...
DB_PASSWORD=...
```

Important:

```text
.env is a convention
```

It is not automatically understood by every application/tool.

Something must load/read it.

---

# SECTION 1146 — OUR BACKEND `.env` FLOW

## 1274.

Our backend uses:

```text
backend/.env
```

for real local values.

It is ignored by Git.

Our local startup script loads it into shell environment before starting Spring Boot.

Flow:

```text
.env
 ↓
run-local.sh
 ↓
shell environment
 ↓
Spring Boot
```

---

# SECTION 1147 — OUR BACKEND STARTUP SCRIPT

## 1275.

Current structure:

```bash
#!/bin/bash
set -a
source "$(dirname "$0")/.env"
set +a
cd "$(dirname "$0")"
echo "Starting SDET Commerce backend..."
./mvnw spring-boot:run
```

Important:

```text
Spring Boot is not automatically reading this .env file itself.
```

The shell script sources it first.

---

# SECTION 1148 — `set -a`

## 1276.

In shell:

```bash
set -a
```

causes subsequently defined variables to be exported automatically.

Then:

```bash
source .env
```

loads variables.

This allows child processes such as:

```text
Maven
Java
Spring Boot
```

to inherit them.

---

# SECTION 1149 — `set +a`

## 1277.

```bash
set +a
```

turns automatic export behavior back off.

This keeps shell behavior controlled after loading configuration.

---

# SECTION 1150 — API AUTOMATION `.env`

## 1278.

Our API automation also uses local environment configuration for values such as:

```text
TEST_ENV
base URLs
test credentials
database configuration
```

Real values remain ignored by Git.

Committed examples should contain placeholders only.

---

# SECTION 1151 — `.env.example`

## 1279.

Purpose:

```text
document required configuration
without committing real secrets
```

Example:

```text
DB_USERNAME=<your-db-username>
DB_PASSWORD=<your-db-password>
```

A new developer can understand what needs to be configured.

---

# SECTION 1152 — WHY COMMIT `.env.example`

## 1280.

Without example:

```text
application fails
developer doesn't know required variables
```

With example:

```text
required variable names documented
secret values absent
setup easier
```

---

# SECTION 1153 — NEVER PUT REAL SECRET IN EXAMPLE FILE

## 1281.

Bad:

```text
JWT_SECRET=actual-secret-value
```

Better:

```text
JWT_SECRET=<your-jwt-secret>
```

Example files are still committed to Git.

---

# SECTION 1154 — ENVIRONMENT SWITCHING

## 1282.

Our API automation already supports:

```text
local
qa
stage
```

conceptually through environment-aware configuration.

Current behavior:

```text
local
→ default localhost API

qa
→ requires QA_BASE_URL

stage
→ requires STAGE_BASE_URL

BASE_URL
→ explicit override
```

This is stronger than editing source code for each environment.

---

# SECTION 1155 — CONFIGURATION PRECEDENCE

## 1283.

When multiple configuration sources exist, one may override another.

Example conceptual sources:

```text
default value
config file
environment variable
command-line value
CI variable
```

Important:

```text
precedence is tool/framework-specific
```

Never assume universal order.

---

# SECTION 1156 — WHY PRECEDENCE CAUSES BUGS

## 1284.

You change:

```text
application.properties
```

but behavior does not change.

Possible reason:

```text
environment variable overrides property
```

Troubleshooting question:

```text
What is the effective value at runtime?
```

not only:

```text
What does this file contain?
```

---

# SECTION 1157 — EFFECTIVE CONFIGURATION

## 1285.

Effective configuration means:

```text
final value actually used by application
after all configuration sources/overrides are resolved
```

This is what matters during debugging.

---

# SECTION 1158 — CONFIGURATION DEBUGGING PRINCIPLE

## 1286.

When configuration appears wrong:

```text
1. Identify configuration key
2. Identify parser/framework
3. Identify all possible sources
4. Understand precedence
5. Verify effective value safely
6. Check environment differences
```

---

# SECTION 1159 — DON'T PRINT SECRETS TO VERIFY CONFIG

## 1287.

Bad:

```bash
echo "$DB_PASSWORD"
```

in CI logs.

Better:

```bash
if [ -n "$DB_PASSWORD" ]; then
  echo "DB_PASSWORD is configured"
else
  echo "DB_PASSWORD is missing"
fi
```

Verify presence without exposing value.

---

# SECTION 1160 — MASKING

## 1288.

Sensitive values should be masked/redacted in:

```text
logs
reports
CI output
request attachments
error messages
```

Our API framework already follows this principle through sanitized Allure request/response attachments.

---

# SECTION 1161 — CONFIGURATION VALIDATION

## 1289.

Strong framework behavior:

```text
startup
 ↓
validate required configuration
 ↓
fail immediately if invalid
```

Instead of:

```text
50 tests fail because BASE_URL is null
```

---

# SECTION 1162 — FAIL FAST EXAMPLE

## 1290.

Concept:

```text
TEST_ENV=qa
```

but:

```text
QA_BASE_URL missing
```

Correct framework behavior:

```text
clear configuration error
```

rather than silently falling back to wrong environment.

Our environment switching was designed with this principle.

---

# SECTION 1163 — CONFIGURATION TYPO

## 1291.

Example:

Expected:

```text
QA_BASE_URL
```

Actual:

```text
QA_BASEURL
```

Result:

```text
expected variable appears missing
```

Environment-variable names are exact.

---

# SECTION 1164 — CASE SENSITIVITY

## 1292.

On typical Unix environments:

```text
BASE_URL
```

and:

```text
base_url
```

are different environment-variable names.

Use consistent naming conventions.

---

# SECTION 1165 — CONFIGURATION NAMING

## 1293.

Good names:

```text
TEST_ENV
BASE_URL
QA_BASE_URL
STAGE_BASE_URL
DB_URL
DB_USERNAME
```

Avoid unclear names:

```text
url1
thing
pass
config2
```

Configuration is part of framework design.

---

# SECTION 1166 — DEFAULT VALUES

## 1294.

Defaults are useful for safe non-sensitive values.

Example:

```text
local API URL
timeout
local database name
```

Do NOT provide insecure defaults for secrets such as:

```text
production password
JWT secret
```

---

# SECTION 1167 — SAFE DEFAULT

## 1295.

Our Spring datasource URL pattern:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/sdetcommerce}
```

provides a convenient local URL default.

But credentials remain externalized.

This is a good separation:

```text
safe local default
+
external secret
```

---

# SECTION 1168 — JWT SECRET CONFIG

## 1296.

Our backend uses:

```properties
app.jwt.secret=${JWT_SECRET}
```

Meaning:

```text
JWT signing secret
```

must come externally.

This should never be committed as a real secret.

---

# SECTION 1169 — JWT EXPIRATION CONFIG

## 1297.

Our backend uses:

```properties
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:3600000}
```

Meaning:

```text
environment can override expiration
```

while:

```text
3600000
```

acts as default.

This demonstrates configurable application behavior.

---

# SECTION 1170 — CONFIGURATION AS CODE

## 1298.

Files such as:

```text
docker-compose.yml
GitHub Actions workflows
application.properties
playwright.config.ts
```

are version-controlled configuration.

Benefits:

```text
reviewable
repeatable
auditable
change history
```

But secrets should remain external.

---

# SECTION 1171 — CONFIGURATION VS SECRET STORAGE

## 1299.

Git is good for:

```text
configuration structure
defaults
workflow definitions
example files
```

Git is NOT a secret manager.

Secrets should use:

```text
CI secret storage
approved secret manager
runtime environment injection
```

depending on environment.

---

# SECTION 1172 — CI/CD YAML

## 1300.

Future GitHub Actions workflow will live under:

```text
.github/workflows/
```

Workflow files use YAML.

Concept:

```yaml
name: CI

on:
  push:

jobs:
  test:
    runs-on: ubuntu-latest
```

This is only a learning example.

We will write the real workflow during the CI/CD implementation phase.

---

# SECTION 1173 — GITHUB ACTIONS STRUCTURE

## 1301.

High-level:

```text
workflow
  ↓
event/trigger
  ↓
jobs
  ↓
steps
  ↓
commands/actions
```

Example concept:

```text
Push
 ↓
Backend Test Job
 ↓
API Automation Job
 ↓
Frontend/UI jobs later
```

---

# SECTION 1174 — `on` IN GITHUB ACTIONS

## 1302.

`on` defines workflow triggers.

Examples conceptually:

```text
push
pull_request
manual workflow dispatch
schedule
```

Exact triggers will be selected later based on project needs.

---

# SECTION 1175 — JOBS

## 1303.

A workflow contains one or more:

```text
jobs
```

Each job runs on a runner.

Example:

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
```

---

# SECTION 1176 — STEPS

## 1304.

Jobs contain steps.

Concept:

```yaml
steps:
  - uses: ...
  - run: ...
```

A step may:

```text
run shell command
use reusable action
```

---

# SECTION 1177 — CI ENV VARIABLES

## 1305.

CI workflow can provide environment variables.

Conceptually:

```yaml
env:
  TEST_ENV: qa
```

Secrets should not be hardcoded.

Use the CI platform's secret mechanism.

---

# SECTION 1178 — GITHUB ACTIONS SECRETS

## 1306.

Conceptual usage:

```text
CI secret storage
      ↓
workflow runtime
      ↓
environment variable
      ↓
application/test
```

Do not commit:

```text
passwords
tokens
private keys
```

into workflow YAML.

---

# SECTION 1179 — CI SECRET LOGGING

## 1307.

Even when CI masks configured secrets, do not deliberately print them.

Bad:

```text
echo secret
```

Good:

```text
verify secret exists
without displaying it
```

Defense in depth.

---

# SECTION 1180 — YAML CI INDENTATION FAILURE

## 1308.

Example:

```yaml
jobs:
test:
  runs-on: ubuntu-latest
```

may have incorrect structure.

Correct conceptual indentation:

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
```

One indentation mistake can prevent workflow parsing.

---

# SECTION 1181 — VALID YAML BUT INVALID WORKFLOW

## 1309.

Example:

```yaml
jobs:
  test:
    randomKey: hello
```

could be valid YAML syntax but invalid according to GitHub Actions schema.

Always distinguish:

```text
serialization syntax
from
application schema
```

---

# SECTION 1182 — YAML DUPLICATE KEYS

## 1310.

Duplicate keys can create confusing behavior depending on parser/tool.

Example:

```yaml
environment: local
environment: qa
```

Avoid duplicate mapping keys.

Lint/validation tools can help detect them.

---

# SECTION 1183 — CONFIG FILE REVIEW

## 1311.

Treat configuration changes like code changes.

Review:

```text
syntax
environment impact
defaults
secret exposure
backward compatibility
CI impact
deployment impact
```

A one-line config change can break every test.

---

# SECTION 1184 — ENVIRONMENT PARITY

## 1312.

Goal:

```text
local
QA
stage
CI
```

should use similar configuration structure.

Values differ.

Architecture should not.

Bad:

```text
local uses env variables
CI uses hardcoded source changes
stage requires manual code edit
```

---

# SECTION 1185 — CONFIGURATION DRIFT

## 1313.

Configuration drift occurs when environments become unintentionally different.

Example:

```text
QA timeout = X
Stage timeout = Y
```

without intentional reason.

This can create:

```text
passes in QA
fails in Stage
```

Version-controlled config + environment documentation helps reduce drift.

---

# SECTION 1186 — ENVIRONMENT-SPECIFIC DATA

## 1314.

Not everything should be shared between environments.

Examples:

```text
base URL
database host
credentials
test user
feature availability
external integrations
```

Tests should know:

```text
which differences are intentional
```

---

# SECTION 1187 — CONFIGURATION SHOULD NOT CHANGE TEST INTENT

## 1315.

Environment configuration can change:

```text
where test runs
credentials
timeouts within reason
```

But should not silently change:

```text
what business behavior the test claims to validate
```

Otherwise environments are no longer comparable.

---

# SECTION 1188 — FEATURE FLAGS

## 1316.

Feature flags can enable/disable behavior by configuration.

Testing may require:

```text
flag ON
flag OFF
different audience/configuration
```

Important:

```text
know flag state before interpreting failure
```

Feature flags are configuration, not automatically defects.

---

# SECTION 1189 — CONFIGURATION AND TEST EVIDENCE

## 1317.

Reports should capture safe context such as:

```text
environment name
base URL
framework version
OS
Java/Node version
```

without exposing:

```text
password
token
secret
```

Our Allure environment metadata already follows this general approach.

---

# SECTION 1190 — ALLURE ENVIRONMENT METADATA

## 1318.

Our API automation records useful safe metadata such as:

```text
Environment
Base URL
Framework
Test Runner
Java Version
OS
```

This helps answer:

```text
Where did this failure happen?
```

without opening source code.

---

# SECTION 1191 — CONFIGURATION AND REPRODUCIBILITY

## 1319.

To reproduce a failure, we need more than test code.

We may need:

```text
commit
environment
configuration
runtime versions
test data
dependency versions
```

This is why configuration management is part of quality engineering.

---

# SECTION 1192 — JSON API ERROR CONTRACT

## 1320.

A good API may return structured errors.

Conceptual example:

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request"
}
```

Tests can validate:

```text
status code
error code
message semantics
field-level details
```

depending on contract.

---

# SECTION 1193 — DON'T OVERASSERT ERROR TEXT

## 1321.

If exact wording is not part of contract, brittle assertion:

```text
entire exact error sentence
```

can fail after harmless copy changes.

Prefer stable contract fields where available:

```text
status
error code
field
```

and assert message only when required.

---

# SECTION 1194 — JSON REQUEST BUILDER

## 1322.

Avoid huge raw JSON strings duplicated across tests.

Possible strategies:

```text
Java request objects
Map structures
test-data factory
builder
JSON fixture
```

Choose based on complexity.

Our Java API automation already uses a test-data factory approach for reusable test data.

---

# SECTION 1195 — STATIC JSON FIXTURES

## 1323.

Static JSON files can be useful for:

```text
large payloads
contract fixtures
special edge cases
```

But problems include:

```text
hardcoded IDs
stale values
duplication
poor dynamic data support
```

Use intentionally.

---

# SECTION 1196 — DYNAMIC JSON DATA

## 1324.

Dynamic test data is useful for:

```text
unique users
unique products
parallel execution
repeatability
```

But generated values should still be:

```text
traceable
valid
cleaned up where appropriate
```

---

# SECTION 1197 — JSON AND DATABASE VALIDATION

## 1325.

Example:

```text
POST /orders
      ↓
JSON response
      ↓
extract order ID
      ↓
SQL query
      ↓
validate persisted state
```

This connects:

```text
API layer
+
database layer
```

Our project already uses selective JDBC/DB validation for this reason.

---

# SECTION 1198 — API RESPONSE VS DB MODEL

## 1326.

Do not assume:

```text
every DB column
```

must appear in API JSON.

API contract and persistence schema serve different purposes.

Example:

```text
internal DB fields
```

may intentionally be hidden from clients.

---

# SECTION 1199 — SECURITY: PASSWORD IN JSON

## 1327.

Request may legitimately contain:

```json
{
  "email": "user@example.com",
  "password": "<secret>"
}
```

But automation logging/reporting should redact:

```text
password
```

before attaching request evidence.

Our custom Allure sanitization already follows this principle.

---

# SECTION 1200 — SECURITY: TOKEN IN JSON

## 1328.

Fields such as:

```text
token
accessToken
refreshToken
secret
```

must be treated as sensitive.

Do not:

```text
commit
print
attach unredacted
```

---

# SECTION 1201 — SECURITY: AUTHORIZATION HEADER

## 1329.

Example:

```text
Authorization: Bearer <token>
```

is not JSON, but commonly accompanies JSON APIs.

Reports should sanitize it.

Our API framework masks authorization/token information in Allure attachments.

---

# SECTION 1202 — CONFIGURATION LEAK THROUGH ERROR

## 1330.

Even if config file is ignored, secrets can leak through:

```text
stack trace
console output
CI command
report attachment
screenshot
debug dump
```

Secret hygiene must cover the full lifecycle.

---

# SECTION 1203 — CONFIGURATION LEAK THROUGH GIT HISTORY

## 1331.

Deleting a secret from the latest file does not necessarily remove it from Git history if it was previously committed.

If a real secret is committed:

```text
rotate/revoke secret
assess history exposure
clean history if required
```

Do not assume deletion alone solves exposure.

---

# SECTION 1204 — PRE-COMMIT CONFIG CHECK

## 1332.

Before committing:

```bash
git status
git diff
git diff --cached
```

Check for:

```text
.env
credentials
tokens
passwords
private keys
unexpected config
generated files
```

This matches the security discipline used before our first GitHub milestone.

---

# SECTION 1205 — CONFIGURATION CHANGE TESTING

## 1333.

If configuration changes:

```text
don't only inspect file
```

Test actual behavior.

Example:

```text
change DB URL
↓
start application
↓
verify connection
```

Or:

```text
change TEST_ENV
↓
run tests
↓
verify correct target environment
```

---

# SECTION 1206 — CONFIGURATION TEST MATRIX

## 1334.

Possible configuration tests:

```text
valid value
missing value
invalid value
default value
override value
unsupported environment
```

Not every config needs automated tests.

Prioritize critical configuration.

---

# SECTION 1207 — INVALID ENVIRONMENT

## 1335.

Suppose:

```text
TEST_ENV=production123
```

but framework supports:

```text
local
qa
stage
```

Better:

```text
fail clearly
```

rather than silently choose local.

Fail-fast prevents tests hitting wrong environment.

---

# SECTION 1208 — WRONG ENVIRONMENT RISK

## 1336.

One of the most dangerous automation mistakes:

```text
test intended for QA
↓
runs against another environment
```

Potential consequences:

```text
wrong data
unexpected writes
production impact
misleading report
```

Environment selection should be explicit and visible.

---

# SECTION 1209 — PRODUCTION SAFETY

## 1337.

Automation capable of:

```text
POST
PUT
DELETE
```

should never be casually pointed at production.

Controls can include:

```text
environment allow-list
read-only production suite
manual approval
separate credentials
CI protection
```

depending on organization.

---

# SECTION 1210 — BASE URL VALIDATION

## 1338.

Framework can validate:

```text
BASE_URL exists
URL syntax valid
environment matches intended host
```

before destructive tests.

This is especially important for admin/API automation.

---

# SECTION 1211 — YAML LINTING

## 1339.

YAML can be checked using linting/validation tools.

Benefits:

```text
catch syntax problems early
indentation consistency
duplicate-key detection
CI quality gate
```

Exact tool should be chosen only when needed.

---

# SECTION 1212 — JSON VALIDATION TOOLS

## 1340.

JSON can be validated using:

```text
IDE
parser
schema validator
CLI tools such as jq
```

We already have:

```text
jq
```

available locally.

---

# SECTION 1213 — JQ

## 1341.

`jq` is a command-line JSON processor.

Example:

```bash
echo '{"name":"Product"}' | jq .
```

Pretty prints/validates JSON.

---

# SECTION 1214 — JQ EXTRACT VALUE

## 1342.

Example:

```bash
echo '{"id":10}' | jq '.id'
```

Output:

```text
10
```

Useful during API debugging.

---

# SECTION 1215 — JQ RAW STRING

## 1343.

Example:

```bash
echo '{"token":"abc"}' | jq -r '.token'
```

`-r` outputs raw string rather than JSON-quoted string.

Security warning:

```text
do not print real sensitive tokens into shared logs
```

---

# SECTION 1216 — CURL + JQ

## 1344.

Conceptual pattern:

```bash
curl -s http://localhost:8080/some-endpoint | jq .
```

Useful for:

```text
manual API debugging
JSON validation
quick inspection
```

For authenticated APIs, use safe token handling and avoid exposing secrets in shell history/logs.

---

# SECTION 1217 — INVALID JSON WITH JQ

## 1345.

If input is malformed:

```bash
echo '{"id":1' | jq .
```

`jq` reports parse error.

Useful to distinguish:

```text
invalid JSON
```

from:

```text
valid JSON but wrong business content
```

---

# SECTION 1218 — YAML COMMAND-LINE TOOLING

## 1346.

Tools such as:

```text
yq
```

can process YAML.

But do not install tools just for the sake of the portfolio.

Use when actual workflow benefits.

---

# SECTION 1219 — DOCKER COMPOSE CONFIG VALIDATION

## 1347.

Docker Compose can validate/render effective Compose configuration.

Useful command:

```bash
docker compose config
```

This can help detect:

```text
YAML problems
interpolation issues
effective configuration
```

Be careful:

```text
rendered output may include resolved sensitive values
```

Do not paste it publicly without checking.

---

# SECTION 1220 — SAFE DOCKER COMPOSE DEBUGGING

## 1348.

Instead of exposing all resolved environment values:

Check:

```bash
docker compose ps
```

and:

```bash
docker compose logs postgres
```

when troubleshooting database container.

Use:

```bash
docker compose config
```

carefully.

---

# SECTION 1221 — CONFIGURATION FILE EXTENSIONS

## 1349.

Common:

```text
.json
.yaml
.yml
.properties
.env
.xml
.toml
```

Different tools choose different formats.

A Senior SDET should identify:

```text
format
parser
schema
precedence
runtime owner
```

---

# SECTION 1222 — PROPERTIES FILE

## 1350.

Spring commonly uses:

```text
application.properties
```

Example:

```properties
spring.application.name=backend
```

Properties format is:

```text
key=value
```

Different from YAML and JSON.

---

# SECTION 1223 — SPRING YAML ALTERNATIVE

## 1351.

Spring Boot can also use YAML configuration such as:

```text
application.yml
```

But our current backend uses:

```text
application.properties
```

Do not change format just because YAML exists.

Current format is perfectly valid.

---

# SECTION 1224 — CONFIGURATION FORMAT SELECTION

## 1352.

Choose format based on:

```text
framework convention
team readability
nesting complexity
tool support
existing architecture
```

Do not convert configuration files without a reason.

---

# SECTION 1225 — ENVIRONMENT PROFILES

## 1353.

Many frameworks support environment-specific profiles/configuration.

Spring Boot supports profile concepts.

But our current backend primarily uses:

```text
environment variables
+
property placeholders
```

Do not claim we have implemented Spring profile files unless we actually add them.

---

# SECTION 1226 — CONFIGURATION DOCUMENTATION

## 1354.

README should explain:

```text
required variables
optional variables
defaults
startup command
example file
supported environments
```

without publishing secrets.

This reduces onboarding time.

---

# SECTION 1227 — CONFIGURATION OWNERSHIP

## 1355.

Ask:

```text
Who owns this value?
```

Examples:

```text
application default
→ source/config

environment URL
→ deployment/test config

password
→ secret store

test retry count
→ framework config
```

This prevents dumping everything into one `.env`.

---

# SECTION 1228 — CONFIGURATION LAYERS

## 1356.

Mental model:

```text
CODE
 ↓
DEFAULT CONFIG
 ↓
ENVIRONMENT CONFIG
 ↓
SECRET CONFIG
 ↓
RUNTIME OVERRIDE
 ↓
EFFECTIVE CONFIGURATION
```

Exact precedence differs by tool.

---

# SECTION 1229 — CONFIGURATION ANTI-PATTERN: MAGIC VALUES

## 1357.

Bad:

```java
Thread.sleep(7000);
```

Why 7000?

No context.

Better when configuration is justified:

```text
named timeout
documented purpose
environment-aware only when necessary
```

But do not make every constant configurable.

---

# SECTION 1230 — OVER-CONFIGURATION

## 1358.

Bad architecture:

```text
150 environment variables
```

for values that never need to change.

Configuration has maintenance cost.

Rule:

```text
externalize values that genuinely vary or are secret
```

not every constant.

---

# SECTION 1231 — CONFIGURATION ANTI-PATTERN: ONE FILE FOR EVERYTHING

## 1359.

Mixing:

```text
frontend config
backend secret
DB password
test user
CI setting
Playwright timeout
```

into one shared file can create:

```text
ownership confusion
security exposure
coupling
```

Separate by module/responsibility.

---

# SECTION 1232 — OUR MODULE CONFIGURATION MINDSET

## 1360.

```text
backend/
→ backend runtime config

api-automation/
→ API test config

frontend/
→ future browser-facing frontend config

ui-automation/
→ future Playwright runtime config

.github/workflows/
→ future CI orchestration
```

Each module owns relevant configuration.

---

# SECTION 1233 — FRONTEND CONFIG SECURITY

## 1361.

Future:

```text
VITE_API_BASE_URL
```

is acceptable browser-facing config.

Never:

```text
VITE_DB_PASSWORD
VITE_JWT_SECRET
```

because browser users can inspect bundled frontend code.

---

# SECTION 1234 — PLAYWRIGHT CONFIG SECURITY

## 1362.

Future Playwright can receive secrets from:

```text
local environment
CI secret storage
```

Then:

```text
tests use secret
reports sanitize secret
Git never stores real secret
```

---

# SECTION 1235 — CONFIGURATION AND DOCKER

## 1363.

Docker container can receive configuration through:

```text
environment variables
Compose
command arguments
mounted config/secrets
platform-specific secret systems
```

Exact strategy depends on deployment architecture.

---

# SECTION 1236 — CONFIGURATION AND AWS

## 1364.

Later AWS deployment may use services/configuration mechanisms for:

```text
environment configuration
credentials
application secrets
database connectivity
```

We will design this during AWS phase.

Do not put cloud credentials in:

```text
Git
Docker image
frontend bundle
```

---

# SECTION 1237 — INTERVIEW: WHAT IS JSON?

## 1365.

> "JSON is a lightweight text-based data-interchange format that represents data using objects, arrays and primitive values such as strings, numbers, booleans and null. In SDET work I use it heavily for REST API payloads, responses, test data and contract validation."

---

# SECTION 1238 — INTERVIEW: JSON OBJECT VS ARRAY

## 1366.

> "A JSON object is an unordered collection of named key-value properties represented with braces, while an array is an ordered collection of values represented with square brackets. API responses often combine both through nested structures."

---

# SECTION 1239 — INTERVIEW: NULL VS MISSING

## 1367.

> "A null field is present with an explicit null value, while a missing field does not exist in the JSON object. I test them separately when the API contract distinguishes between optional, nullable and required fields."

---

# SECTION 1240 — INTERVIEW: SERIALIZATION

## 1368.

> "Serialization converts an in-memory object into a transferable representation such as JSON, while deserialization converts JSON back into an application object or data structure."

---

# SECTION 1241 — INTERVIEW: SCHEMA VS BUSINESS VALIDATION

## 1369.

> "Schema validation checks structural aspects such as required fields and data types, while business assertions validate meaning, such as whether an order total or status is correct. A structurally valid response can still be functionally wrong."

---

# SECTION 1242 — INTERVIEW: JSON VS YAML

## 1370.

> "JSON is a strict data-interchange format commonly used for APIs, while YAML is a human-readable serialization format commonly used for configuration such as Docker Compose and CI pipelines. YAML relies heavily on indentation and supports comments, while standard JSON does not."

---

# SECTION 1243 — INTERVIEW: WHY YAML FAILS?

## 1371.

> "I first distinguish a YAML syntax failure from a tool-schema failure. Incorrect indentation or malformed mappings can make YAML invalid, while a file can be valid YAML but still contain unsupported keys for Docker Compose or GitHub Actions."

---

# SECTION 1244 — INTERVIEW: WHY ENV VARIABLES?

## 1372.

> "Environment variables let me externalize environment-specific values and secrets instead of hardcoding them in source. This makes the same codebase reusable across local, QA, stage and CI while keeping sensitive values outside Git."

---

# SECTION 1245 — INTERVIEW: `.env` FILE

## 1373.

> "A `.env` file is a common convention for storing local environment values, but it is not automatically loaded by every framework. In our backend, a shell startup script sources the local `.env` into the process environment before Maven starts Spring Boot."

---

# SECTION 1246 — INTERVIEW: HOW DO YOU HANDLE SECRETS?

## 1374.

> "I keep real secrets outside Git, use local ignored environment files for development and CI secret storage or an approved secret manager in automated environments. I also prevent leakage through logs and reports by masking authorization headers, passwords and tokens."

---

# SECTION 1247 — INTERVIEW: CONFIGURATION PRECEDENCE

## 1375.

> "When multiple configuration sources exist, the effective runtime value depends on the framework's precedence rules. During troubleshooting I identify all possible sources and determine which value actually wins instead of looking at only one config file."

---

# SECTION 1248 — INTERVIEW: FAIL FAST CONFIGURATION

## 1376.

> "I validate critical configuration before running tests. For example, if QA is selected but the QA base URL is missing, the framework should stop with a clear configuration error rather than silently target another environment and generate misleading failures."

---

# SECTION 1249 — INTERVIEW: WHY `.env.example`?

## 1377.

> "An `.env.example` documents the variable names a developer needs without exposing real values. It improves onboarding while keeping credentials and secrets out of version control."

---

# SECTION 1250 — INTERVIEW: CONFIGURATION DRIFT

## 1378.

> "Configuration drift is unintended divergence between environments. It can cause tests to pass in one environment and fail in another even when the application build is the same. I reduce it through version-controlled configuration structure, documented environment differences and explicit runtime values."

---

# SECTION 1251 — SENIOR SDET SCENARIO: API RETURNS STRING INSTEAD OF NUMBER

## 1379.

Expected:

```json
{
  "quantity": 2
}
```

Actual:

```json
{
  "quantity": "2"
}
```

Check:

```text
API contract
schema
consumer expectation
serialization behavior
```

If contract requires integer:

```text
contract defect
```

even if displayed value looks similar.

---

# SECTION 1252 — SENIOR SDET SCENARIO: FIELD DISAPPEARS

## 1380.

Expected:

```json
{
  "description": null
}
```

Actual:

```json
{}
```

Question:

```text
Is field nullable or optional?
```

Do not fail solely from assumption.

Validate against contract.

---

# SECTION 1253 — SENIOR SDET SCENARIO: DOCKER COMPOSE FAILS

## 1381.

Check:

```text
YAML syntax
indentation
Compose schema
environment interpolation
required variables
image
port conflict
Docker daemon
```

Useful:

```bash
docker compose config
docker compose ps
```

with secret-safety awareness.

---

# SECTION 1254 — SENIOR SDET SCENARIO: APP USES WRONG DB

## 1382.

Investigate:

```text
DB_URL default
environment variable
startup script
effective configuration
Docker port
database name
```

Do not immediately modify Java code.

---

# SECTION 1255 — SENIOR SDET SCENARIO: QA TESTS HIT LOCAL

## 1383.

Check:

```text
TEST_ENV
QA_BASE_URL
BASE_URL override
config precedence
startup script
CI variables
```

Framework should ideally fail rather than silently fall back when a required environment URL is missing.

---

# SECTION 1256 — SENIOR SDET SCENARIO: SECRET APPEARS IN ALLURE

## 1384.

Treat as security issue in test tooling.

Fix:

```text
identify attachment source
sanitize header/body
verify redaction
rerun report
check historical artifacts
rotate secret if exposed beyond safe boundary
```

Our REST Assured framework already implements request/response sanitization.

---

# SECTION 1257 — SENIOR SDET SCENARIO: WORKFLOW YAML IS VALID BUT CI REJECTS IT

## 1385.

Reason may be:

```text
GitHub Actions schema
```

not YAML parser.

Approach:

```text
validate workflow keys
job structure
action syntax
expression syntax
permissions
```

---

# SECTION 1258 — SENIOR SDET SCENARIO: LOCAL CONFIG WORKS, CI FAILS

## 1386.

Compare:

```text
required env vars
working directory
file availability
secret configuration
case sensitivity
runtime versions
shell behavior
network access
```

Do not copy local `.env` into Git as a shortcut.

---

# SECTION 1259 — SENIOR SDET SCENARIO: EXACT JSON STRING ASSERTION FAILS

## 1387.

If only property order/whitespace differs:

```text
test design problem
```

Use:

```text
structural parsing
field assertions
schema validation
```

unless exact raw payload formatting is genuinely part of requirement.

---

# SECTION 1260 — SENIOR SDET SCENARIO: API RETURNS 200 BUT WRONG DATA

## 1388.

Status assertion alone is insufficient.

Validate:

```text
business fields
schema
resource identity
database state where valuable
```

A successful HTTP status does not guarantee business correctness.

---

# SECTION 1261 — RAPID FIRE JSON

## 1389. JSON object symbols?

```text
{ }
```

## 1390. JSON array symbols?

```text
[ ]
```

## 1391. String delimiter?

```text
double quotes
```

## 1392. Does JSON support comments?

```text
No, standard JSON does not.
```

## 1393. Does JSON support undefined?

```text
No.
```

## 1394. Object → JSON?

```text
Serialization
```

## 1395. JSON → object?

```text
Deserialization
```

## 1396. Structural contract validation?

```text
Schema validation
```

## 1397. CLI JSON processor?

```text
jq
```

---

# SECTION 1262 — RAPID FIRE YAML

## 1398. YAML commonly used for?

```text
Configuration
CI/CD
Docker Compose
```

## 1399. Does indentation matter?

```text
Yes.
```

## 1400. YAML comments?

```text
#
```

## 1401. YAML list marker?

```text
-
```

## 1402. Common extensions?

```text
.yml
.yaml
```

## 1403. Valid YAML guarantees valid GitHub workflow?

```text
No.
```

## 1404. Docker Compose config validation?

```bash
docker compose config
```

---

# SECTION 1263 — RAPID FIRE CONFIGURATION

## 1405. Why externalize config?

```text
Environment flexibility
security
maintainability
```

## 1406. Should passwords be committed?

```text
No.
```

## 1407. Local secret file?

```text
.env
```

## 1408. Safe committed template?

```text
.env.example
```

## 1409. Runtime variable in shell?

```text
Environment variable
```

## 1410. Final resolved value?

```text
Effective configuration
```

## 1411. Wrong environments becoming different?

```text
Configuration drift
```

## 1412. Validate config before tests?

```text
Fail fast
```

---

# SECTION 1264 — JSON MENTAL MODEL

## 1413.

```text
HTTP Response
      ↓
JSON text
      ↓
Parser
      ↓
Object / JSONPath
      ↓
Schema Assertion
+
Business Assertion
```

---

# SECTION 1265 — YAML MENTAL MODEL

## 1414.

```text
YAML File
   ↓
YAML Parser
   ↓
Structured Data
   ↓
Tool Schema
   ↓
Docker / CI / Application Behavior
```

Two possible failure layers:

```text
YAML invalid
```

or:

```text
tool configuration invalid
```

---

# SECTION 1266 — CONFIGURATION MENTAL MODEL

## 1415.

```text
Source Code
     +
Safe Defaults
     +
Environment Config
     +
Secrets
     +
Runtime Overrides
     ↓
Effective Runtime Configuration
     ↓
Application/Test Behavior
```

---

# SECTION 1267 — OUR BACKEND CONFIG FLOW

## 1416.

```text
backend/.env
      ↓
run-local.sh
      ↓
Shell Environment
      ↓
Maven Wrapper
      ↓
Spring Boot
      ↓
application.properties placeholders
      ↓
Effective Runtime Configuration
```

---

# SECTION 1268 — OUR API AUTOMATION CONFIG FLOW

## 1417.

```text
api-automation/.env
       ↓
run-tests-local.sh
       ↓
Environment Variables
       ↓
Maven
       ↓
Test Framework
       ↓
Environment Resolution
       ↓
local / QA / stage Base URL
```

Important:

```text
API automation currently uses Maven through its local test script.
Do not assume Maven Wrapper there unless verified.
```

---

# SECTION 1269 — FUTURE FRONTEND CONFIG FLOW

## 1418.

```text
Frontend Environment
      ↓
Vite
      ↓
VITE_API_BASE_URL
      ↓
React Build/Dev Runtime
      ↓
Browser
```

Remember:

```text
browser-facing config is inspectable
```

so:

```text
NO backend secrets
```

---

# SECTION 1270 — FUTURE PLAYWRIGHT CONFIG FLOW

## 1419.

```text
Local / CI Environment
       ↓
Playwright Config
       ↓
Base URL
Credentials
Retries
Workers
       ↓
Test Execution
```

Secrets remain external.

---

# SECTION 1271 — FUTURE CI CONFIG FLOW

## 1420.

```text
GitHub Actions YAML
       ↓
Runner
       ↓
CI Variables + Secrets
       ↓
Backend/API/Frontend/UI Commands
       ↓
Reports + Artifacts
```

Git contains:

```text
workflow logic
```

not:

```text
real credentials
```

---

# SECTION 1272 — CONFIGURATION TROUBLESHOOTING DECISION TREE

## 1421.

```text
WRONG CONFIGURATION BEHAVIOR
          ↓
Which key is wrong?
          ↓
Which tool reads it?
          ↓
What sources can define it?
          ↓
What is precedence?
          ↓
What is effective value?
          ↓
Is environment correct?
          ↓
Is value valid?
          ↓
Is secret/config available?
          ↓
Fix source of truth
```

---

# SECTION 1273 — JSON TROUBLESHOOTING DECISION TREE

## 1422.

```text
API JSON FAILURE
      ↓
Is response valid JSON?
      |
      ├── NO
      │    ↓
      │ parser/server/content issue
      │
      └── YES
           ↓
Does schema match?
           |
           ├── NO
           │    ↓
           │ contract/type/field issue
           │
           └── YES
                ↓
Do business values match?
                |
                ├── NO
                │    ↓
                │ functional defect/data issue
                │
                └── YES
                     ↓
                test likely passes
```

---

# SECTION 1274 — YAML TROUBLESHOOTING DECISION TREE

## 1423.

```text
YAML-BASED TOOL FAILS
       ↓
Is YAML syntax valid?
       |
       ├── NO
       │    ↓
       │ indentation/syntax
       │
       └── YES
            ↓
Does tool schema accept it?
            |
            ├── NO
            │    ↓
            │ invalid key/structure
            │
            └── YES
                 ↓
Are variables resolved?
                 |
                 ├── NO
                 │    ↓
                 │ environment/interpolation
                 │
                 └── YES
                      ↓
                 runtime/tool issue
```

---

# SECTION 1275 — ONE-MINUTE INTERVIEW ANSWER

## 1424.

> "I use JSON primarily for API payloads and responses, and YAML for infrastructure and CI configuration such as Docker Compose and future GitHub Actions workflows. In API automation I validate more than HTTP status — I check JSON structure, data types, critical business values and database state where it adds value. For configuration, I externalize environment-specific values, keep real secrets outside Git, provide safe example files and validate required configuration early. During troubleshooting I distinguish syntax, schema, interpolation and runtime-precedence issues rather than treating every config failure as an application defect."

---

# SECTION 1276 — PROJECT-SPECIFIC INTERVIEW ANSWER

## 1425.

> "In my SDET Commerce project, the Spring Boot backend uses externalized database and JWT configuration through environment variables and property placeholders. Local values are loaded through an ignored `.env` file by a shell startup script, while committed `.env.example` files contain placeholders only. The API automation independently supports local, QA and stage environment resolution, and our Docker Compose PostgreSQL configuration also uses environment-variable interpolation instead of committed credentials."

---

# SECTION 1277 — FINAL 5-MINUTE REVISION

## 1426. JSON

```text
API data
objects {}
arrays []
strict syntax
double quotes
no comments
schema + business assertions
```

## 1427. YAML

```text
configuration
indentation matters
comments supported
Docker Compose
CI/CD
valid YAML != valid tool configuration
```

## 1428. Configuration

```text
externalize environment values
do not hardcode secrets
understand precedence
validate required values
fail fast
document examples
```

## 1429. Security

```text
.env ignored
.env.example committed safely
CI secrets external
logs sanitized
reports sanitized
frontend variables not secret
```

## 1430. Troubleshooting

```text
syntax
↓
schema
↓
interpolation
↓
precedence
↓
effective value
↓
runtime behavior
```

---

# SECTION 1278 — SENIOR SDET PRINCIPLE

## 1431.

Do not say:

```text
"The JSON is wrong."
```

Say:

```text
"The response is syntactically valid JSON, but the `quantity`
property violates the API contract because it is returned as a
string instead of an integer."
```

Do not say:

```text
"The YAML isn't working."
```

Say:

```text
"The file is valid YAML, but Docker Compose rejects the service
configuration because the structure does not match its schema."
```

Do not say:

```text
"The QA config isn't picked."
```

Say:

```text
"`TEST_ENV` resolves to QA, but the required `QA_BASE_URL` is
missing from the runtime environment, so the framework should
fail during configuration validation before tests execute."
```

Senior SDET approach:

```text
FORMAT
  ↓
PARSER
  ↓
SCHEMA
  ↓
CONFIGURATION SOURCE
  ↓
PRECEDENCE
  ↓
EFFECTIVE VALUE
  ↓
RUNTIME BEHAVIOR
```

---

# END OF PART 7 — JSON, YAML & CONFIGURATION MANAGEMENT FOR SDET

Next:

**PART 8 — DEPENDENCIES, ARTIFACTS, MAVEN CENTRAL, NEXUS/ARTIFACTORY & SOFTWARE SUPPLY CHAIN**

---

# PART 8 — DEPENDENCIES, ARTIFACTS, MAVEN CENTRAL, NEXUS/ARTIFACTORY & SOFTWARE SUPPLY CHAIN

# SECTION 1279 — WHY DEPENDENCY & ARTIFACT KNOWLEDGE MATTERS FOR SDET

## 1432. Why This Matters

Modern applications rarely contain only code written by the team.

They depend on:

```text
frameworks
libraries
plugins
drivers
SDKs
test frameworks
reporting libraries
database drivers
browser tooling
container images
```

Examples from our project:

```text
Spring Boot
REST Assured
TestNG
PostgreSQL JDBC Driver
JWT libraries
Allure
Springdoc OpenAPI
```

An SDET should understand:

```text
where dependencies come from
how versions are resolved
where they are cached
how build tools download them
what happens when they conflict
how CI retrieves them
how private artifacts work
how supply-chain risks appear
```

---

# SECTION 1280 — WHAT IS A DEPENDENCY?

## 1433.

A dependency is external code or component that our project relies on.

Example:

```text
Our API Automation
      ↓
REST Assured
      ↓
other libraries
```

Dependency relationships can be:

```text
direct
transitive
runtime
test-only
build-time
plugin-related
```

---

# SECTION 1281 — DIRECT DEPENDENCY

## 1434.

A dependency explicitly declared by our project is a direct dependency.

Example concept in Maven:

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>...</version>
</dependency>
```

Our project directly asks Maven for REST Assured.

---

# SECTION 1282 — TRANSITIVE DEPENDENCY

## 1435.

Suppose:

```text
Our Project
   ↓
Library A
   ↓
Library B
```

Our project may not explicitly declare Library B.

But Maven can bring it because Library A depends on it.

Then Library B is:

```text
transitive dependency
```

---

# SECTION 1283 — WHY TRANSITIVE DEPENDENCIES MATTER

## 1436.

Problems can come from packages we never explicitly declared.

Example:

```text
Project
 ↓
Framework A
 ↓
Utility Library X version 1

Project
 ↓
Framework B
 ↓
Utility Library X version 2
```

Potential result:

```text
version conflict
runtime error
unexpected method mismatch
```

---

# SECTION 1284 — DEPENDENCY TREE

## 1437.

For Maven:

```bash
mvn dependency:tree
```

or for backend where Maven Wrapper exists:

```bash
./mvnw dependency:tree
```

This helps understand:

```text
direct dependencies
transitive dependencies
versions
conflicts
```

---

# SECTION 1285 — DEPENDENCY TREE AS TROUBLESHOOTING TOOL

## 1438.

If runtime shows:

```text
NoSuchMethodError
```

one possible cause is:

```text
wrong/incompatible library version loaded
```

Check:

```bash
mvn dependency:tree
```

before randomly changing code.

---

# SECTION 1286 — WHAT IS AN ARTIFACT?

## 1439.

An artifact is a build/distribution unit produced or consumed by tooling.

Examples:

```text
JAR
WAR
POM
ZIP
container image
npm package
test report archive
```

In Maven, a typical artifact is:

```text
JAR
```

---

# SECTION 1287 — SOURCE CODE VS ARTIFACT

## 1440.

Source:

```text
.java
.ts
.js
```

Build artifact:

```text
.jar
.zip
compiled bundle
container image
```

Flow:

```text
Source Code
   ↓
Build Tool
   ↓
Artifact
```

---

# SECTION 1288 — MAVEN COORDINATES

## 1441.

Maven commonly identifies an artifact using:

```text
groupId
artifactId
version
```

Example concept:

```text
com.example
my-service
1.0.0
```

Together these identify a specific artifact version.

---

# SECTION 1289 — GROUPID

## 1442.

`groupId` usually identifies:

```text
organization
company
project namespace
```

Example:

```text
com.sdetcommerce
```

---

# SECTION 1290 — ARTIFACTID

## 1443.

`artifactId` identifies a particular module/artifact.

Examples:

```text
backend
api-automation
security-sdk
```

---

# SECTION 1291 — VERSION

## 1444.

Version identifies artifact release/build line.

Examples:

```text
1.0.0
2.1.3
1.0.0-SNAPSHOT
```

---

# SECTION 1292 — GAV

## 1445.

Maven coordinates are often called:

```text
GAV
```

Meaning:

```text
GroupId
ArtifactId
Version
```

Example:

```text
com.example:payment-sdk:1.2.0
```

---

# SECTION 1293 — WHAT IS A POM ARTIFACT?

## 1446.

Maven repositories can store `.pom` metadata alongside JARs.

The POM describes information such as:

```text
artifact dependencies
project metadata
dependency management information
```

When Maven resolves a library, it may also read its POM to discover transitive dependencies.

---

# SECTION 1294 — MAVEN REPOSITORY

## 1447.

A Maven repository stores artifacts and metadata.

Three important concepts:

```text
Local Repository
Remote Repository
Repository Manager
```

---

# SECTION 1295 — LOCAL MAVEN REPOSITORY

## 1448.

By default Maven commonly stores downloaded artifacts under:

```text
~/.m2/repository
```

Example conceptual structure:

```text
~/.m2/repository/
└── group/
    └── artifact/
        └── version/
            ├── artifact-version.jar
            └── artifact-version.pom
```

---

# SECTION 1296 — WHY LOCAL REPOSITORY EXISTS

## 1449.

Without local caching:

```text
every Maven build
→ download same dependencies again
```

With local repository:

```text
download once
→ reuse later
```

Benefits:

```text
faster builds
reduced network dependency
```

---

# SECTION 1297 — LOCAL REPOSITORY IS CACHE + REPOSITORY

## 1450.

The local Maven repository is more than random temporary cache.

It is Maven's local artifact repository used for:

```text
downloaded dependencies
locally installed artifacts
plugin artifacts
metadata
```

Avoid deleting the entire `.m2` folder as first troubleshooting step.

---

# SECTION 1298 — MAVEN CENTRAL

## 1451.

Maven Central is a major public repository containing Java/JVM ecosystem artifacts.

Concept:

```text
pom.xml
   ↓
Maven
   ↓
Maven Central
   ↓
dependency downloaded
```

Examples of public libraries may be retrieved from Maven Central depending on their publishing setup.

---

# SECTION 1299 — REMOTE REPOSITORY

## 1452.

A remote repository is accessible over network.

Examples:

```text
Maven Central
company Nexus
company Artifactory
vendor Maven repository
```

Maven resolves artifacts from configured repositories.

---

# SECTION 1300 — PRIVATE DEPENDENCY

## 1453.

Not every library can be public.

Organizations may have internal packages such as:

```text
company SDK
banking SDK
security SDK
shared test framework
internal utility library
```

These are often stored in:

```text
Nexus
Artifactory
private Maven repository
```

---

# SECTION 1301 — WHY COMPANIES USE PRIVATE REPOSITORIES

## 1454.

Reasons:

```text
internal libraries
access control
artifact retention
dependency governance
caching public dependencies
auditability
version control
security policies
```

---

# SECTION 1302 — NEXUS

## 1455.

Sonatype Nexus Repository is an artifact repository manager.

It can host/proxy repositories for formats such as:

```text
Maven
npm
Docker
NuGet
```

depending on setup.

For Maven:

```text
Developer / CI
      ↓
Nexus
      ↓
internal artifacts
and/or
proxied public artifacts
```

---

# SECTION 1303 — JFROG ARTIFACTORY

## 1456.

JFrog Artifactory is another artifact repository manager.

It can manage artifacts across multiple package ecosystems.

Conceptually similar organizational purpose:

```text
central artifact management
access control
internal package hosting
public repository proxying
build integration
```

---

# SECTION 1304 — NEXUS VS ARTIFACTORY

## 1457.

For interview:

```text
Both are artifact repository managers.
```

Organizations may choose either.

Typical capabilities include:

```text
host private artifacts
proxy public repositories
manage versions
control access
integrate with CI/CD
```

Do not claim one is always better.

---

# SECTION 1305 — REPOSITORY MANAGER ARCHITECTURE

## 1458.

Instead of every CI runner directly contacting many public repositories:

```text
Developer/CI
     ↓
Company Repository Manager
     ↓
Maven Central / Vendor Repositories
```

Benefits:

```text
central control
cache
availability
security
audit
```

---

# SECTION 1306 — PROXY REPOSITORY

## 1459.

A proxy repository caches artifacts from another repository.

Example:

```text
Maven
 ↓
Company Nexus
 ↓
Maven Central
```

First request:

```text
Nexus fetches dependency
```

Later requests:

```text
Nexus may serve cached artifact
```

---

# SECTION 1307 — HOSTED REPOSITORY

## 1460.

A hosted repository stores organization-owned artifacts.

Example:

```text
company-security-sdk
internal-test-framework
shared-library
```

Artifacts are uploaded/deployed to it.

---

# SECTION 1308 — GROUP / VIRTUAL REPOSITORY

## 1461.

Repository managers can expose one logical URL combining multiple repositories.

Concept:

```text
maven-all
   ↓
internal releases
internal snapshots
Maven Central proxy
vendor repo
```

Developer only configures one logical endpoint.

Exact terminology differs by product.

---

# SECTION 1309 — MAVEN SETTINGS.XML

## 1462.

Maven user/global configuration can live in:

```text
settings.xml
```

Common user path:

```text
~/.m2/settings.xml
```

It can configure:

```text
servers
credentials
mirrors
proxies
profiles
repositories indirectly
```

Do not commit personal credential-filled settings files.

---

# SECTION 1310 — MAVEN MIRROR

## 1463.

A Maven mirror can redirect repository requests.

Concept:

```text
Maven Central request
       ↓
Company Nexus/Artifactory mirror
```

This is common in enterprise environments.

---

# SECTION 1311 — WHY MIRRORS MATTER FOR SDET

## 1464.

Scenario:

```text
dependency exists in Maven Central
```

but company build fails.

Reason could be:

```text
company mirror
repository manager outage
proxy policy
artifact not allowed
TLS issue
```

Don't assume Maven Central itself is down.

---

# SECTION 1312 — MAVEN SERVER CREDENTIALS

## 1465.

Private repositories can require authentication.

Credentials may be referenced through:

```text
settings.xml
CI secrets
environment-managed setup
```

Avoid hardcoding repository credentials in:

```text
pom.xml
source code
Git
```

---

# SECTION 1313 — SERVER ID

## 1466.

Maven repository credentials commonly use a server ID.

Concept:

```xml
<server>
    <id>company-repository</id>
    ...
</server>
```

Repository/deployment configuration refers to matching ID.

Actual credentials should remain external/private.

---

# SECTION 1314 — POM REPOSITORY DECLARATION

## 1467.

A POM can conceptually define repositories.

But enterprise organizations may prefer central repository configuration through:

```text
settings.xml
mirrors
repository managers
```

to maintain governance.

Do not add random repositories to `pom.xml` just because a dependency fails.

---

# SECTION 1315 — RANDOM REPOSITORY ANTI-PATTERN

## 1468.

Bad troubleshooting:

```text
dependency not found
↓
add 10 random Maven repository URLs
```

Risks:

```text
security
build unpredictability
wrong artifacts
slow builds
supply-chain exposure
```

First determine:

```text
where artifact is officially published
```

---

# SECTION 1316 — MAVEN RESOLUTION FLOW

## 1469.

Simplified:

```text
pom.xml
   ↓
Maven reads dependency coordinates
   ↓
Check local repository
   ↓
If required, query configured remote repository
   ↓
Download POM/JAR/metadata
   ↓
Store locally
   ↓
Build classpath
```

---

# SECTION 1317 — WHAT IF DEPENDENCY ALREADY EXISTS LOCALLY?

## 1470.

Maven may reuse local artifact depending on:

```text
version type
metadata
update policy
local state
```

This can sometimes create:

```text
works locally
fails in CI
```

because local machine has an artifact CI cannot retrieve.

---

# SECTION 1318 — LOCAL-ONLY DEPENDENCY PROBLEM

## 1471.

Classic scenario:

Developer manually installs private JAR:

```text
local .m2
```

Build passes locally.

CI:

```text
artifact not found
```

because CI has a clean local repository and cannot retrieve it remotely.

Correct solution:

```text
publish artifact to accessible repository
```

not:

```text
copy developer .m2 into CI
```

---

# SECTION 1319 — MAVEN INSTALL

## 1472.

Command:

```bash
mvn install
```

typically:

```text
compiles/tests/packages project
and installs built artifact into local Maven repository
```

This allows other local Maven projects to use it.

---

# SECTION 1320 — INSTALL:INSTALL-FILE

## 1473.

Maven can manually install a JAR into local repository.

Conceptually useful for:

```text
temporary local testing
vendor artifact not yet hosted
```

But it creates portability problem if CI/team cannot obtain that JAR.

For durable architecture:

```text
use proper artifact repository
```

---

# SECTION 1321 — DEPLOY

## 1474.

Maven `deploy` lifecycle phase typically publishes built artifact to a configured remote repository.

Concept:

```text
Source
 ↓
Build
 ↓
Package
 ↓
Deploy
 ↓
Nexus/Artifactory
```

This is not the same as deploying an application to a server.

Important interview distinction.

---

# SECTION 1322 — MAVEN DEPLOY VS APPLICATION DEPLOYMENT

## 1475.

Maven:

```bash
mvn deploy
```

means conceptually:

```text
publish Maven artifact to artifact repository
```

Application deployment means:

```text
run/release application in environment
```

These are different.

---

# SECTION 1323 — SNAPSHOT VERSION

## 1476.

Example:

```text
1.0.0-SNAPSHOT
```

indicates development version that may change over time.

Repositories can treat snapshots differently from releases.

---

# SECTION 1324 — RELEASE VERSION

## 1477.

Example:

```text
1.0.0
```

Release versions should conceptually be immutable.

Once published:

```text
1.0.0
```

should not silently become different code.

This improves:

```text
reproducibility
traceability
```

---

# SECTION 1325 — SNAPSHOT VS RELEASE

## 1478.

```text
SNAPSHOT
→ development/evolving artifact

Release
→ fixed published artifact
```

Typical repository setups may have:

```text
snapshot repository
release repository
```

---

# SECTION 1326 — WHY RELEASE IMMUTABILITY MATTERS

## 1479.

Bad:

```text
Monday:
library:1.0.0 = code A

Friday:
library:1.0.0 = code B
```

Then same build definition produces different behavior.

Better:

```text
new code
→ new version
```

---

# SECTION 1327 — VERSION RANGE RISK

## 1480.

Dynamic version resolution can reduce reproducibility.

Conceptually risky patterns include:

```text
latest
open-ended ranges
uncontrolled snapshots
```

For stable automation/builds:

```text
intentional versions
lock/dependency management where appropriate
```

are preferable.

---

# SECTION 1328 — MAVEN DEPENDENCY CONFLICT

## 1481.

Example:

```text
Library A
→ Utility 1.0

Library B
→ Utility 2.0
```

Maven must resolve which version appears on effective classpath.

If wrong version wins:

```text
compile issue
runtime method error
behavior difference
```

---

# SECTION 1329 — MAVEN NEAREST-WINS CONCEPT

## 1482.

Maven dependency mediation commonly uses the nearest dependency in the dependency graph when multiple versions are present.

If same depth, declaration ordering may influence resolution.

This is why:

```bash
mvn dependency:tree
```

is important.

Do not memorize only:

```text
highest version always wins
```

because that is incorrect for Maven's normal mediation model.

---

# SECTION 1330 — DEPENDENCY MANAGEMENT

## 1483.

`dependencyManagement` allows a parent/project to centrally define dependency versions/configuration.

Concept:

```text
Parent
 ↓
defines version
 ↓
Child/module dependency
 ↓
uses managed version
```

Useful for multi-module consistency.

---

# SECTION 1331 — DEPENDENCYMANAGEMENT DOES NOT AUTOMATICALLY ADD DEPENDENCY

## 1484.

Important:

```text
dependencyManagement
```

primarily manages dependency metadata/version.

It does not automatically mean every listed dependency is placed on classpath.

The dependency still normally needs to be declared where used.

---

# SECTION 1332 — BOM

## 1485.

BOM means:

```text
Bill of Materials
```

In Maven, a BOM can centrally define compatible versions of a set of dependencies.

Example concept:

```text
Framework BOM
  ↓
Library A version
Library B version
Library C version
```

This reduces manual version mismatch.

---

# SECTION 1333 — WHY BOM IS USEFUL

## 1486.

Without BOM:

```text
library-a 2.0
library-b 5.1
library-c 3.4
```

Developer manually selects all.

With framework BOM:

```text
known-compatible dependency set
```

can be managed centrally.

---

# SECTION 1334 — SPRING BOOT DEPENDENCY MANAGEMENT

## 1487.

Spring Boot provides dependency-management conventions for many ecosystem libraries.

This can reduce the need to manually specify every dependency version.

Exact behavior depends on project setup, such as:

```text
Spring Boot parent
or
dependency management import
```

When debugging, inspect the effective POM instead of assuming version.

---

# SECTION 1335 — EFFECTIVE POM

## 1488.

Command:

```bash
mvn help:effective-pom
```

or backend:

```bash
./mvnw help:effective-pom
```

This shows the POM after inheritance/profile/management resolution.

Useful to answer:

```text
Which dependency/plugin version is actually active?
```

---

# SECTION 1336 — PARENT POM

## 1489.

A Maven project can inherit configuration from a parent POM.

Potential inherited items include:

```text
properties
dependency management
plugin management
build configuration
```

This is why not every effective setting appears directly in child `pom.xml`.

---

# SECTION 1337 — PLUGIN DEPENDENCY VS PROJECT DEPENDENCY

## 1490.

Maven plugins are build-time tools.

Examples conceptually:

```text
compiler plugin
Surefire
Spring Boot Maven plugin
```

Project dependencies are libraries used by application/test code.

Do not confuse them.

---

# SECTION 1338 — PLUGIN REPOSITORY

## 1491.

Maven may retrieve plugins from repositories too.

A build can fail because:

```text
plugin artifact cannot be resolved
```

even if normal dependencies resolve.

Error wording matters.

---

# SECTION 1339 — DEPENDENCY SCOPE

## 1492.

Maven dependency scopes include concepts such as:

```text
compile
test
runtime
provided
```

Scope controls where dependency participates.

This affects:

```text
compile classpath
test classpath
runtime
packaging
```

---

# SECTION 1340 — TEST SCOPE

## 1493.

A test-only dependency should normally not be needed by production application runtime.

Example concept:

```text
TestNG
```

in a test automation module.

Using correct scope helps keep dependency boundaries clean.

---

# SECTION 1341 — RUNTIME SCOPE

## 1494.

Runtime dependency:

```text
not necessarily needed to compile direct application source
but required when application runs
```

Example patterns can include database drivers depending on project structure.

Always inspect actual framework conventions.

---

# SECTION 1342 — PROVIDED SCOPE

## 1495.

Provided means dependency is available during compile/test but expected to be supplied by runtime/container environment.

Common classic example:

```text
servlet APIs in external servlet container scenarios
```

Not especially important for our current Spring Boot executable setup, but useful interview concept.

---

# SECTION 1343 — EXCLUSION

## 1496.

Maven can exclude a transitive dependency.

Concept:

```xml
<exclusions>
    ...
</exclusions>
```

Useful when:

```text
transitive version is unwanted
conflict exists
dependency should come from another source
```

Do not exclude dependencies blindly.

---

# SECTION 1344 — WHY BLIND EXCLUSION IS DANGEROUS

## 1497.

Removing dependency can compile but fail at runtime.

Possible:

```text
ClassNotFoundException
NoClassDefFoundError
```

Before exclusion:

```text
understand dependency tree
understand why dependency exists
verify tests
```

---

# SECTION 1345 — OPTIONAL DEPENDENCY

## 1498.

Maven optional dependency communicates that downstream consumers should not automatically inherit it as a normal transitive dependency.

Useful in library design.

Not a common concern in simple application projects but valuable for SDK/library interviews.

---

# SECTION 1346 — CLASSLOADER CONFLICT

## 1499.

Dependency issues can surface through class loading.

Common symptoms:

```text
ClassNotFoundException
NoClassDefFoundError
NoSuchMethodError
NoSuchFieldError
LinkageError
```

Do not always treat these as source-code syntax bugs.

---

# SECTION 1347 — NOSUCHMETHODERROR

## 1500.

Very common dependency mismatch symptom.

Concept:

```text
Code compiled expecting method X
      ↓
Runtime loads older/different library
      ↓
method X missing
      ↓
NoSuchMethodError
```

Check:

```text
runtime dependency tree/classpath
```

---

# SECTION 1348 — CLASSNOTFOUNDEXCEPTION

## 1501.

Possible:

```text
required class not available
wrong scope
missing dependency
packaging problem
classloader issue
```

Check:

```text
dependency declaration
scope
artifact packaging
runtime classpath
```

---

# SECTION 1349 — DEPENDENCY VERSION TROUBLESHOOTING FLOW

## 1502.

```text
Runtime/Class Error
       ↓
Read exact class/method
       ↓
Identify owning dependency
       ↓
mvn dependency:tree
       ↓
Check duplicate versions
       ↓
Check managed version
       ↓
Check scope
       ↓
Check final artifact/runtime
       ↓
Fix intentionally
```

---

# SECTION 1350 — MAVEN FORCE UPDATE

## 1503.

Command:

```bash
mvn -U ...
```

`-U` asks Maven to check for updated releases/snapshots according to Maven behavior.

Useful when:

```text
metadata/stale snapshot issue suspected
```

Not something to add to every build without reason.

---

# SECTION 1351 — OFFLINE MODE

## 1504.

```bash
mvn -o test
```

runs Maven in offline mode.

Works only if required artifacts/plugins are already available locally.

Useful diagnostic question:

```text
Can this build run from local repository only?
```

---

# SECTION 1352 — DEPENDENCY RESOLUTION FAILURE

## 1505.

Typical errors can include:

```text
Could not resolve dependencies
Could not find artifact
Connection timed out
PKIX path building failed
401 Unauthorized
403 Forbidden
```

Each points to different layer.

---

# SECTION 1353 — ARTIFACT NOT FOUND

## 1506.

Check:

```text
groupId correct?
artifactId correct?
version correct?
repository correct?
artifact actually published?
```

Do not first assume network issue.

---

# SECTION 1354 — REPOSITORY 401

## 1507.

Means repository is reachable enough to return HTTP response but authentication failed.

Check:

```text
credentials
server ID
token expiry
CI secret
settings.xml
```

---

# SECTION 1355 — REPOSITORY 403

## 1508.

Repository may recognize identity but deny requested operation/resource.

Potential:

```text
permission
repository policy
publish permission
artifact path restriction
```

---

# SECTION 1356 — REPOSITORY 404

## 1509.

Potential:

```text
wrong artifact coordinates
wrong repository URL
artifact/version does not exist
routing/group issue
```

---

# SECTION 1357 — REPOSITORY TLS ERROR

## 1510.

Possible Java/Maven error:

```text
PKIX path building failed
SSLHandshakeException
```

Check:

```text
corporate CA
truststore
proxy
repository certificate
certificate chain
```

Connects directly to Part 4.

---

# SECTION 1358 — REPOSITORY DNS FAILURE

## 1511.

Possible:

```text
Unknown host
```

Check:

```text
DNS
VPN
hostname
network
proxy
```

This is not a dependency version problem.

---

# SECTION 1359 — REPOSITORY TIMEOUT

## 1512.

Potential:

```text
network
proxy
remote outage
slow repository
firewall
```

Do not delete `.m2` because of network timeout.

---

# SECTION 1360 — CORRUPTED LOCAL ARTIFACT

## 1513.

Sometimes a local downloaded artifact can be incomplete/corrupt.

Then targeted cleanup may be reasonable.

Better:

```text
remove specific artifact/version directory
```

rather than:

```text
delete entire ~/.m2
```

when root cause is known.

---

# SECTION 1361 — TARGETED `.m2` CLEANUP

## 1514.

Concept:

```text
identify exact dependency
↓
remove only affected local artifact folder
↓
rerun Maven
```

This preserves all unrelated cached dependencies.

---

# SECTION 1362 — `.LASTUPDATED` FILES

## 1515.

Maven may create metadata files related to failed/update attempts.

If resolution failed previously, Maven behavior can be influenced by update policy and local metadata.

Before manually deleting metadata:

```text
understand exact error
try intentional update if appropriate
```

---

# SECTION 1363 — REPOSITORY CACHE ISSUE

## 1516.

An enterprise repository manager may cache:

```text
successful artifacts
metadata
sometimes negative/missing resolution information
```

A newly published artifact may temporarily appear missing depending on proxy/cache configuration.

This is infrastructure behavior, not necessarily project code.

---

# SECTION 1364 — WHAT IS AN ARTIFACTORY BUILD INFO CONCEPT?

## 1517.

Repository/CI systems can associate metadata such as:

```text
build number
Git commit
dependency versions
published artifacts
```

with a build.

This improves traceability:

```text
Which source produced this artifact?
```

---

# SECTION 1365 — TRACEABILITY

## 1518.

Ideal release chain:

```text
Git Commit
   ↓
CI Build
   ↓
Artifact Version
   ↓
Container Image
   ↓
Deployment
   ↓
Test Report
```

A Senior SDET should be able to trace failures across this chain.

---

# SECTION 1366 — BUILD ARTIFACT TRACEABILITY

## 1519.

If QA reports a defect:

```text
Which deployed artifact?
Which Git commit?
Which DB/config?
Which test report?
```

Without artifact traceability, reproducing release issues becomes difficult.

---

# SECTION 1367 — CI ARTIFACT

## 1520.

CI systems can also produce artifacts such as:

```text
JAR
test report
Allure results
screenshots
coverage
logs
```

These CI artifacts are different from permanent package repositories in purpose.

---

# SECTION 1368 — PACKAGE REPOSITORY VS CI ARTIFACT STORAGE

## 1521.

Package repository:

```text
versioned reusable software artifacts
dependencies
release packages
```

CI artifact storage:

```text
build-specific outputs
test reports
logs
screenshots
```

There can be overlap depending on tooling, but purposes differ.

---

# SECTION 1369 — ARTIFACT PROMOTION

## 1522.

Good release practice:

```text
Build once
   ↓
Test artifact
   ↓
Promote same artifact
   ↓
Deploy higher environment
```

Avoid:

```text
rebuild different code for QA
rebuild again for PROD
```

when reproducibility matters.

---

# SECTION 1370 — IMMUTABLE ARTIFACT

## 1523.

An immutable artifact should not change after it has been published under the same immutable version/reference.

Examples conceptually:

```text
release JAR
versioned container image
```

This improves:

```text
reproducibility
rollback
auditability
```

---

# SECTION 1371 — SOFTWARE SUPPLY CHAIN

## 1524.

Software supply chain includes everything involved in building software:

```text
source code
dependencies
build tools
plugins
CI runners
artifact repositories
container images
registries
deployment tooling
```

A compromise anywhere can affect final software.

---

# SECTION 1372 — SUPPLY-CHAIN RISK

## 1525.

Potential risks:

```text
malicious dependency
compromised package account
typosquatting
dependency confusion
stolen CI token
compromised build runner
untrusted container image
artifact tampering
```

SDET/quality engineers increasingly need awareness of these risks.

---

# SECTION 1373 — DEPENDENCY CONFUSION

## 1526.

Dependency confusion can occur when:

```text
internal package name
```

collides with or is resolved from an unintended public repository.

Example concept:

```text
company-internal-sdk
```

exists internally but package manager retrieves a public package with same/similar name due to configuration.

Mitigation involves:

```text
repository governance
namespacing
resolution policy
private repositories
```

---

# SECTION 1374 — TYPOSQUATTING

## 1527.

A malicious package may use a name close to a trusted package.

Example concept:

```text
spring-security
spring-securty
```

or npm-style typo variants.

Before adding dependency:

```text
verify exact publisher/name/source
```

---

# SECTION 1375 — MALICIOUS TRANSITIVE DEPENDENCY

## 1528.

Even if our direct dependency is trusted:

```text
it may bring transitive packages
```

Supply-chain review includes:

```text
transitive dependency awareness
```

not only direct POM entries.

---

# SECTION 1376 — DEPENDENCY SCANNING

## 1529.

Security tools can analyze dependencies against known vulnerability databases.

Concept:

```text
Dependency Tree
      ↓
Vulnerability Database
      ↓
Findings
```

Examples of tooling categories:

```text
OWASP dependency scanning
SCA platforms
repository scanner
GitHub dependency security features
```

Exact tooling will depend on organization/project.

---

# SECTION 1377 — SCA

## 1530.

SCA means:

```text
Software Composition Analysis
```

It focuses on third-party/open-source components.

It can identify:

```text
known vulnerabilities
licenses
dependency inventory
version risks
```

---

# SECTION 1378 — CVE

## 1531.

CVE means:

```text
Common Vulnerabilities and Exposures
```

It provides identifiers for publicly known vulnerabilities.

Example format:

```text
CVE-YYYY-NNNN...
```

Finding a CVE does not automatically mean:

```text
our application is exploitable
```

Context still matters.

---

# SECTION 1379 — CVSS

## 1532.

CVSS is a vulnerability severity scoring system.

It helps communicate severity.

But engineering decisions should also consider:

```text
exploitability
usage context
exposure
available mitigations
business impact
```

Do not prioritize only by numeric score.

---

# SECTION 1380 — VULNERABLE DEPENDENCY DECISION FLOW

## 1533.

```text
Scanner Finding
     ↓
Which dependency?
     ↓
Direct or transitive?
     ↓
Which version?
     ↓
Is vulnerable code path used?
     ↓
Is service exposed?
     ↓
Fixed version available?
     ↓
Compatibility impact?
     ↓
Upgrade / mitigate / document
```

---

# SECTION 1381 — DON'T BLINDLY UPGRADE DEPENDENCY

## 1534.

Security scanner says:

```text
upgrade to latest
```

But upgrade can introduce:

```text
breaking API changes
framework incompatibility
test failures
runtime differences
```

Correct process:

```text
understand
upgrade intentionally
run regression
```

---

# SECTION 1382 — DEPENDENCY PINNING

## 1535.

Pinning means intentionally controlling versions.

Benefits:

```text
reproducibility
predictability
controlled upgrades
```

Tradeoff:

```text
stale dependencies if never reviewed
```

Therefore:

```text
pin + regularly review
```

---

# SECTION 1383 — AUTOMATED DEPENDENCY UPDATES

## 1536.

Tools can automatically open PRs for dependency updates.

Examples conceptually:

```text
Dependabot
Renovate
```

Benefits:

```text
visibility
regular upgrade cadence
security updates
```

Still require:

```text
CI
review
regression
```

---

# SECTION 1384 — DEPENDABOT CONCEPT

## 1537.

GitHub Dependabot can help identify/update dependencies.

Possible workflow:

```text
new dependency version
     ↓
automated PR
     ↓
CI tests
     ↓
human review
     ↓
merge
```

We may consider this later during GitHub portfolio hardening.

Do not enable features only for appearance.

---

# SECTION 1385 — LICENSE RISK

## 1538.

Third-party dependencies also have software licenses.

Organizations may need to track:

```text
allowed licenses
copyleft obligations
commercial restrictions
attribution
```

SCA tools may also report licensing information.

---

# SECTION 1386 — SBOM

## 1539.

SBOM means:

```text
Software Bill of Materials
```

It is an inventory of software components included in an application.

Think:

```text
ingredients list for software
```

It can include:

```text
libraries
versions
package identifiers
relationships
```

---

# SECTION 1387 — WHY SBOM MATTERS

## 1540.

When a new vulnerability is announced:

```text
Which applications use affected library?
```

SBOM helps answer this quickly.

Important for:

```text
security
compliance
supply-chain visibility
```

---

# SECTION 1388 — BOM VS SBOM

## 1541. Important Distinction

Maven BOM:

```text
dependency version management
```

SBOM:

```text
inventory of components actually present/used in software
```

Do not confuse them.

---

# SECTION 1389 — CHECKSUM

## 1542.

A checksum/hash can help verify artifact integrity.

Concept:

```text
downloaded artifact
      ↓
hash calculation
      ↓
expected hash?
```

Examples algorithms:

```text
SHA-256
SHA-512
```

Repository tooling may manage integrity metadata.

---

# SECTION 1390 — HASH DOES NOT PROVE TRUST BY ITSELF

## 1543.

A matching checksum proves:

```text
artifact matches expected bytes for that checksum
```

But if attacker controls both artifact and published checksum:

```text
trust problem remains
```

Integrity and authenticity are related but different concerns.

---

# SECTION 1391 — ARTIFACT SIGNING

## 1544.

Artifacts can be cryptographically signed to provide stronger authenticity/integrity guarantees.

Public repositories may have signing requirements/processes.

For SDET:

```text
understand concept
```

Deep cryptographic implementation is not currently necessary.

---

# SECTION 1392 — CONTAINER IMAGE AS SUPPLY-CHAIN ARTIFACT

## 1545.

Docker images contain dependencies too:

```text
base image
OS packages
JRE/Node
application JAR
libraries
```

Therefore vulnerability scanning should include:

```text
container image
```

not only Maven/npm dependencies.

---

# SECTION 1393 — BASE IMAGE TRUST

## 1546.

When selecting image:

```text
verify publisher
use known registry
choose maintained image
use intentional version/tag
```

Avoid random images from unknown publishers.

---

# SECTION 1394 — `latest` SUPPLY-CHAIN RISK

## 1547.

If build uses:

```text
some-image:latest
```

the contents may change later.

This harms:

```text
reproducibility
traceability
```

Prefer intentional versioned references where practical.

---

# SECTION 1395 — DIGEST

## 1548.

Container images can also be referenced by immutable digest.

Concept:

```text
image@sha256:...
```

This identifies exact image content.

Tags are human-friendly.

Digests provide stronger exact-content reference.

---

# SECTION 1396 — TAG VS DIGEST

## 1549.

```text
Tag
→ human-readable reference
→ may potentially move

Digest
→ content-addressed immutable reference
```

Production security-sensitive systems may use digest pinning.

Not required for our current local PostgreSQL learning environment.

---

# SECTION 1397 — CI DEPENDENCY CACHE

## 1550.

CI can cache dependencies to speed builds.

Examples:

```text
~/.m2/repository
npm cache
```

Benefits:

```text
faster pipeline
less network usage
```

But cache should not become:

```text
hidden source of unreproducible dependencies
```

---

# SECTION 1398 — COLD BUILD VS WARM BUILD

## 1551.

Cold build:

```text
dependency cache empty
```

Warm build:

```text
dependencies cached
```

Cold builds are useful to prove:

```text
project can resolve everything from declared/configured sources
```

---

# SECTION 1399 — WHY CLEAN CI ENVIRONMENT MATTERS

## 1552.

Developer machine may contain:

```text
old local artifacts
manually installed JARs
cached snapshots
custom settings
```

CI clean runner exposes hidden assumptions.

If CI fails but local passes:

```text
that's valuable evidence
```

not just inconvenience.

---

# SECTION 1400 — REPRODUCIBLE BUILD PRINCIPLE

## 1553.

Strong build should ideally be reproducible from:

```text
source
declared dependency versions
known repository configuration
known runtime/tool versions
```

without hidden developer-machine state.

---

# SECTION 1401 — MAVEN WRAPPER & REPRODUCIBILITY

## 1554.

Maven Wrapper helps a project use a defined Maven distribution/version.

Our backend provides:

```text
./mvnw
```

This improves Maven-tooling consistency.

Dependencies themselves are still resolved separately through repositories.

---

# SECTION 1402 — MAVEN WRAPPER DOES NOT CACHE LIBRARIES

## 1555.

Important:

```text
Maven Wrapper
```

controls Maven launcher/version.

It does NOT mean:

```text
all dependencies are bundled in repository
```

Maven still needs artifact resolution.

---

# SECTION 1403 — JAVA VERSION + DEPENDENCY COMPATIBILITY

## 1556.

A library may require:

```text
minimum Java version
```

Example failure can happen if:

```text
library compiled for newer Java
runtime is older
```

Possible symptom:

```text
UnsupportedClassVersionError
```

This connects Part 2 to dependency management.

---

# SECTION 1404 — FRAMEWORK VERSION COMPATIBILITY

## 1557.

Examples:

```text
Spring Boot version
Java version
Springdoc version
JWT library version
```

must work together.

Do not choose versions independently from random tutorials.

Use:

```text
official compatibility guidance
dependency management
tests
```

---

# SECTION 1405 — LIBRARY API BREAKING CHANGE

## 1558.

Major version upgrade can change:

```text
class names
methods
configuration
defaults
behavior
```

Tests must validate upgrade impact.

This applies to:

```text
Spring Boot
REST Assured
Playwright
React
Docker images
```

---

# SECTION 1406 — BUILD BREAK AFTER DEPENDENCY UPDATE

## 1559.

Classify:

```text
compile-time?
test-time?
runtime?
behavioral?
```

Then compare:

```text
old version
new version
release notes
dependency tree
```

Do not immediately revert without understanding unless release needs urgent stabilization.

---

# SECTION 1407 — TRANSITIVE UPDATE WITHOUT DIRECT VERSION CHANGE

## 1560.

Sometimes changing one direct dependency can change many transitive dependencies.

Example:

```text
Framework A 1.0 → 1.1
```

may bring:

```text
Utility X
JSON library
HTTP client
logging
```

new versions.

This is why dependency-tree diff is useful for major upgrades.

---

# SECTION 1408 — DEPENDENCY TREE BEFORE/AFTER

## 1561.

For significant upgrade:

```bash
mvn dependency:tree > before.txt
```

After change:

```bash
mvn dependency:tree > after.txt
```

Then compare:

```bash
diff before.txt after.txt
```

Useful in controlled local analysis.

Do not necessarily commit these temporary files.

---

# SECTION 1409 — EFFECTIVE CONFIGURATION BEFORE/AFTER

## 1562.

For complex Maven issue, compare:

```text
effective POM
dependency tree
Java version
Maven version
```

This creates evidence instead of guessing.

---

# SECTION 1410 — MAVEN HELP EFFECTIVE SETTINGS

## 1563.

Command:

```bash
mvn help:effective-settings
```

can show resolved Maven settings.

Useful for:

```text
mirrors
profiles
repositories
proxy setup
```

Security warning:

```text
inspect before sharing
```

because configuration may contain sensitive repository information.

---

# SECTION 1411 — PRIVATE REPOSITORY CI FLOW

## 1564.

Concept:

```text
GitHub/Jenkins Runner
      ↓
Repository Credentials
      ↓
Nexus/Artifactory
      ↓
Private Dependency
      ↓
Build
```

Credentials should come from:

```text
CI secrets/credentials store
```

not repository source.

---

# SECTION 1412 — CI 401 PRIVATE REPOSITORY

## 1565.

Local build works.

CI gets:

```text
401
```

Check:

```text
CI repository credential configured?
server ID matches?
token expired?
settings file generated correctly?
```

Do not modify dependency version first.

---

# SECTION 1413 — CI 404 PRIVATE ARTIFACT

## 1566.

Possible:

```text
wrong version
artifact not published
CI points to wrong repository group
snapshot/release repository mismatch
```

---

# SECTION 1414 — SNAPSHOT NOT UPDATING

## 1567.

Scenario:

```text
same SNAPSHOT version
new code published
local build still sees old behavior
```

Potential areas:

```text
local Maven cache
remote metadata
update policy
repository proxy cache
```

Use intentional troubleshooting.

Long-term better practice:

```text
avoid relying on unstable snapshots for release reproducibility
```

---

# SECTION 1415 — RELEASE ARTIFACT NOT FOUND

## 1568.

If release version should exist:

```text
verify repository browser/path
verify GAV
verify publishing pipeline
verify release repository
```

Don't repeatedly run build hoping it appears.

---

# SECTION 1416 — ARTIFACT RETENTION

## 1569.

Repositories may apply retention policies.

Examples:

```text
delete old snapshots
keep releases
retain last N builds
```

Test/release systems should not depend forever on temporary artifacts unless policy supports it.

---

# SECTION 1417 — ARTIFACT CLEANUP

## 1570.

Good repository governance prevents unlimited accumulation of:

```text
snapshots
temporary builds
unused images
```

But release artifacts may need longer retention for:

```text
rollback
audit
reproducibility
```

---

# SECTION 1418 — BUILD NUMBER VS VERSION

## 1571.

A CI build number may be:

```text
#153
```

Artifact version might be:

```text
1.4.2
```

Git commit:

```text
abc123...
```

These are different identifiers.

Good traceability may link all three.

---

# SECTION 1419 — COMMIT SHA AS ARTIFACT METADATA

## 1572.

Useful release traceability:

```text
Artifact
→ built from commit SHA
```

For containers, a tag could conceptually include:

```text
short commit SHA
```

This makes debugging deployment much easier.

---

# SECTION 1420 — CURRENT PROJECT ARTIFACTS

## 1573.

Current project already produces/uses artifacts such as:

```text
Spring Boot JAR/build output
Maven dependencies
Allure results
Swagger/OpenAPI specification
PostgreSQL Docker image
```

But we have not yet implemented:

```text
private Nexus/Artifactory publishing
container image publishing
GitHub Actions artifact publishing
```

Do not claim those as completed.

---

# SECTION 1421 — FUTURE CI ARTIFACT FLOW

## 1574.

Later conceptual GitHub Actions flow:

```text
Checkout
 ↓
Build backend
 ↓
Run tests
 ↓
Create reports
 ↓
Store CI artifacts
 ↓
Potentially build Docker images
```

Exact implementation will be created during CI/CD phase.

---

# SECTION 1422 — ARTIFACT REPOSITORY IN OUR PORTFOLIO

## 1575.

We do NOT need to add Nexus/Artifactory just to make the project look enterprise.

Current learning goal:

```text
understand how enterprise artifact management works
```

Later, if a real use case appears:

```text
private shared library
published internal SDK
multi-service dependency
```

we can decide whether repository-manager implementation adds value.

---

# SECTION 1423 — WHY NOT ADD EVERY ENTERPRISE TOOL?

## 1576.

Portfolio strength comes from:

```text
clear architecture
working implementation
quality decisions
ability to explain tradeoffs
```

not:

```text
maximum number of tools
```

Senior engineering means knowing:

```text
when NOT to add complexity
```

---

# SECTION 1424 — DEPENDENCY HYGIENE

## 1577.

Regularly review:

```text
unused dependencies
duplicate libraries
outdated versions
known vulnerabilities
scope
transitive dependencies
```

Unused dependencies increase:

```text
build complexity
attack surface
maintenance
```

---

# SECTION 1425 — UNUSED DEPENDENCY

## 1578.

A dependency remaining in POM after code no longer uses it can:

```text
increase dependency tree
introduce vulnerability
confuse maintenance
```

Remove only after verifying:

```text
direct use
reflection/runtime use
plugin/framework use
tests
```

---

# SECTION 1426 — DEPENDENCY:ANALYZE CONCEPT

## 1579.

Maven has analysis goals that can help detect:

```text
used undeclared dependencies
unused declared dependencies
```

But static analysis can have limitations with:

```text
reflection
framework loading
generated code
```

Treat output as evidence, not unquestionable truth.

---

# SECTION 1427 — DUPLICATE LIBRARY FUNCTIONALITY

## 1580.

Avoid adding multiple tools doing same job unnecessarily.

Example concept:

```text
three JSON libraries
two assertion libraries
multiple HTTP clients
```

unless architecture has a reason.

Benefits of simplicity:

```text
less conflict
less learning overhead
smaller attack surface
```

---

# SECTION 1428 — TEST FRAMEWORK DEPENDENCY BOUNDARY

## 1581.

API automation dependencies should support:

```text
test execution
HTTP
assertions
reporting
DB validation
```

Do not mix backend implementation dependencies into API automation just because both are Java.

This keeps external test framework independent.

---

# SECTION 1429 — BACKEND VS API-AUTOMATION ARTIFACTS

## 1582.

Backend:

```text
application artifact
```

API automation:

```text
test framework/project
```

They can build independently.

This is good because API automation should be able to test:

```text
local
QA
stage
```

without depending on backend source build every time.

---

# SECTION 1430 — CONTRACT BETWEEN MODULES

## 1583.

API automation should depend on:

```text
HTTP contract
```

not internal Java classes from backend.

Bad:

```text
API test imports backend entity/controller classes
```

because test becomes implementation-coupled.

Better:

```text
API test communicates externally
```

---

# SECTION 1431 — WHY EXTERNAL API TESTING IS STRONG

## 1584.

It validates:

```text
serialization
routing
security
HTTP behavior
application runtime
database integration
```

from client perspective.

This would be weakened if automation simply called internal Java methods.

---

# SECTION 1432 — DEPENDENCY VERSION OWNERSHIP

## 1585.

Ask:

```text
Who should control this version?
```

Could be:

```text
Spring Boot dependency management
root parent POM
module POM
BOM
repository policy
```

Avoid defining same version in multiple places without reason.

---

# SECTION 1433 — VERSION PROPERTY

## 1586.

Maven versions can be centralized using properties.

Concept:

```xml
<properties>
    <some.library.version>1.2.3</some.library.version>
</properties>
```

Then reused.

Useful when:

```text
same version referenced multiple times
```

Do not create a property for every one-off version unnecessarily.

---

# SECTION 1434 — DEPENDENCY UPGRADE TEST STRATEGY

## 1587.

After important dependency upgrade:

```text
clean build
unit/integration tests
API regression
security checks
startup validation
critical manual smoke if needed
```

Scope depends on dependency impact.

---

# SECTION 1435 — DATABASE DRIVER UPDATE

## 1588.

If PostgreSQL JDBC driver changes:

Potential impact:

```text
DB connectivity
type mappings
TLS
driver behavior
performance
```

Regression should target relevant DB interactions.

---

# SECTION 1436 — SPRING SECURITY UPDATE

## 1589.

Potential impact:

```text
authentication
authorization
filter behavior
401/403 handling
security defaults
```

Our RBAC tests become particularly valuable.

---

# SECTION 1437 — JWT LIBRARY UPDATE

## 1590.

Potential impact:

```text
token creation
signature validation
claims
expiration handling
API changes
```

Run:

```text
register/login
authenticated requests
invalid token
expired token if covered
RBAC flows
```

as appropriate.

---

# SECTION 1438 — REST ASSURED UPDATE

## 1591.

Potential impact:

```text
HTTP client behavior
filters
serialization
logging
response parsing
```

Our 48-test API regression would provide strong upgrade confidence.

---

# SECTION 1439 — ALLURE UPDATE

## 1592.

Potential impact:

```text
listener integration
attachments
report generation
metadata
```

Especially verify:

```text
sanitization still works
```

after reporting-library changes.

---

# SECTION 1440 — OPENAPI LIBRARY UPDATE

## 1593.

Potential impact:

```text
Swagger UI
/v3/api-docs
security scheme
JWT Authorize
schema generation
```

Run smoke checks after upgrade.

---

# SECTION 1441 — DEPENDENCY UPDATE PRIORITIZATION

## 1594.

Prioritize based on:

```text
security severity
compatibility
support status
business impact
maintenance cost
release risk
```

Not:

```text
latest version exists → upgrade immediately
```

---

# SECTION 1442 — VERSION EOL

## 1595.

Libraries/runtimes eventually become unsupported.

EOL means:

```text
End of Life
```

Unsupported versions may stop receiving:

```text
security patches
bug fixes
vendor support
```

Track important platform EOL dates.

---

# SECTION 1443 — DEPENDENCY OWNERSHIP IN TEAM

## 1596.

In mature teams:

```text
dependency upgrades
security findings
framework versions
```

should have clear ownership.

Otherwise vulnerabilities remain because:

```text
everyone assumes someone else will fix them
```

---

# SECTION 1444 — SUPPLY-CHAIN QUALITY GATE

## 1597.

Future CI may conceptually include:

```text
Build
 ↓
Tests
 ↓
Dependency Scan
 ↓
Container Scan
 ↓
Artifact
```

Exact tool selection should happen later.

Do not block every build on low-confidence/no-context findings without policy.

---

# SECTION 1445 — SECURITY GATE DESIGN

## 1598.

A good policy might consider:

```text
severity
exploitability
direct/transitive
environment exposure
available fix
approved exception
```

Goal:

```text
real risk reduction
```

not only:

```text
zero scanner warnings
```

---

# SECTION 1446 — ARTIFACT VERIFICATION IN TESTING

## 1599.

Before testing release:

```text
confirm deployed version
```

Evidence can include:

```text
build number
commit
artifact version
deployment metadata
```

Otherwise you may test:

```text
wrong build
```

and create misleading defect results.

---

# SECTION 1447 — WRONG BUILD SCENARIO

## 1600.

Developer says:

```text
fix deployed
```

Tester still sees issue.

Before reopening defect:

```text
verify build/version/commit deployed
```

Potentially old artifact is still running.

---

# SECTION 1448 — CACHE / CDN VS OLD ARTIFACT

## 1601.

Sometimes:

```text
backend updated
```

but user sees old frontend/static content due to:

```text
browser cache
CDN cache
deployment mismatch
```

Do not assume source fix failed until deployment path is verified.

---

# SECTION 1449 — REPOSITORY VS REGISTRY

## 1602.

Terminology:

```text
Maven repository
→ JAR/POM artifacts

npm registry
→ JavaScript packages

container registry
→ Docker/OCI images
```

All are artifact/package distribution systems but for different ecosystems.

---

# SECTION 1450 — OCI IMAGE

## 1603.

Modern container images commonly follow OCI standards.

For our current learning:

```text
Docker image
container registry
```

is sufficient terminology.

Know OCI exists but do not overfocus yet.

---

# SECTION 1451 — GITHUB PACKAGES CONCEPT

## 1604.

GitHub can also host packages/artifacts through services such as GitHub Packages.

Potential ecosystems can include:

```text
containers
Maven
npm
```

depending on setup.

We do not currently need this for the project.

---

# SECTION 1452 — AWS ECR CONNECTION

## 1605.

Later AWS phase may use:

```text
Amazon ECR
```

as container registry.

Concept:

```text
CI
 ↓
Docker Image
 ↓
ECR
 ↓
AWS Runtime
```

We will implement only when AWS deployment phase begins.

---

# SECTION 1453 — INTERNAL SDK USE CASE

## 1606.

Enterprise Android/backend projects often consume internal SDK artifacts.

Typical flow:

```text
SDK Team
 ↓
build AAR/JAR
 ↓
private artifact repository
 ↓
application project
```

As tester/developer, dependency failures can originate from:

```text
artifact missing
wrong version
repository auth
transitive conflict
```

---

# SECTION 1454 — JAR VS AAR

## 1607.

JAR:

```text
Java archive
```

AAR:

```text
Android Archive
```

AAR can package Android-specific resources/components in addition to compiled code.

Important for Android SDK/library work.

---

# SECTION 1455 — MAVEN REPOSITORY CAN HOST AAR

## 1608.

Android libraries can also be published through Maven-compatible repositories.

Coordinates still conceptually use:

```text
groupId
artifactId
version
```

Application pulls AAR via dependency management instead of manually copying binary files.

---

# SECTION 1456 — MANUAL `libs/` VS REPOSITORY

## 1609.

Manual local file:

```text
app/libs/sdk.aar
```

can work.

But repository-based dependency provides better:

```text
versioning
team distribution
CI resolution
metadata
transitive dependency handling
```

depending on SDK packaging.

---

# SECTION 1457 — MAVENLOCAL

## 1610.

Maven local can be used as a repository source in some Gradle/Android setups.

Concept:

```text
publish/install SDK locally
      ↓
mavenLocal()
      ↓
application resolves it
```

Useful during local SDK development.

But:

```text
CI/team cannot depend on developer-only local artifacts long term
```

unless artifact is published centrally.

---

# SECTION 1458 — LOCAL REPOSITORY TRAP

## 1611.

Developer:

```text
SDK available in local Maven repository
```

CI:

```text
dependency missing
```

Cause:

```text
artifact was never published remotely
```

Same principle applies across Maven/Gradle ecosystems.

---

# SECTION 1459 — PUBLISH LOCALLY VS PUBLISH REMOTELY

## 1612.

Local publishing:

```text
quick development/integration
```

Remote publishing:

```text
team/CI/shared usage
```

Use correct distribution level for intended consumers.

---

# SECTION 1460 — ARTIFACT VERSION CONTRACT

## 1613.

When SDK changes:

```text
new API
bug fix
behavior
```

version should communicate change.

Consumers should know:

```text
which exact version is integrated
```

This matters in defect reproduction.

---

# SECTION 1461 — DEPENDENCY ISSUE REPORT

## 1614.

Good defect/build report:

```text
Dependency: <group>:<artifact>
Requested version: X
Resolved version: Y
Environment: CI/local
Java/Maven version
Repository response/error
Relevant dependency-tree evidence
```

Better than:

```text
Maven issue
```

---

# SECTION 1462 — ARTIFACT ISSUE REPORT

## 1615.

Example:

```text
"The CI build cannot resolve version 1.4.0 of the internal SDK from
the configured release repository. The repository is reachable, but
returns 404 for that GAV. Version 1.3.2 resolves successfully."
```

This gives actionable evidence.

---

# SECTION 1463 — SUPPLY-CHAIN INCIDENT RESPONSE

## 1616.

If dependency is found malicious/compromised:

```text
identify affected versions
stop new builds if needed
remove/replace dependency
rotate exposed secrets if necessary
rebuild clean artifacts
retest
redeploy
audit previous releases
```

Exact response depends on organization/security team.

---

# SECTION 1464 — BUILD FROM CLEAN SOURCE

## 1617.

After suspected artifact/dependency compromise:

```text
clean source
known-good repository
known-good dependency versions
clean CI runner
```

may be required.

Simply rerunning same compromised cache may not help.

---

# SECTION 1465 — CI RUNNER TRUST

## 1618.

Build security also depends on CI runner.

Runner has access to:

```text
source
secrets
artifact credentials
build outputs
```

Compromised runner can compromise artifacts.

Therefore supply-chain security is broader than dependency scanning.

---

# SECTION 1466 — LEAST PRIVILEGE FOR REPOSITORY TOKEN

## 1619.

CI credential should have only needed permission.

Examples:

```text
read-only dependency download
publish only to specific repository
```

Avoid broad admin tokens.

---

# SECTION 1467 — SEPARATE READ AND WRITE CREDENTIALS

## 1620.

Where architecture supports it:

```text
build consumer
→ read repository access

publishing job
→ controlled write access
```

This limits blast radius.

---

# SECTION 1468 — NEVER LOG REPOSITORY TOKEN

## 1621.

Repository credentials can leak through:

```text
Maven debug output
curl command
CI environment dump
settings.xml
error attachments
```

Always sanitize before sharing diagnostics.

---

# SECTION 1469 — `-X` MAVEN DEBUG WARNING

## 1622.

Maven debug:

```bash
mvn -X ...
```

provides deep diagnostics.

But it may expose:

```text
repository URLs
system properties
configuration
potentially sensitive context
```

Use locally/securely and inspect before sharing.

---

# SECTION 1470 — DEPENDENCY TROUBLESHOOTING COMMANDS

## 1623.

Useful:

```bash
mvn dependency:tree
```

```bash
mvn help:effective-pom
```

```bash
mvn help:effective-settings
```

```bash
mvn -U test
```

```bash
mvn -o test
```

Use each for a specific reason.

---

# SECTION 1471 — BACKEND WRAPPER COMMANDS

## 1624.

For backend:

```bash
./mvnw dependency:tree
```

```bash
./mvnw help:effective-pom
```

```bash
./mvnw clean test
```

when required environment variables are correctly loaded.

Remember backend `.env` is not automatically loaded by Maven command itself.

---

# SECTION 1472 — API AUTOMATION COMMANDS

## 1625.

Our API automation local script runs Maven:

```text
mvn clean test
```

after loading its local environment configuration.

Do not claim API automation currently uses Maven Wrapper unless verified.

---

# SECTION 1473 — INTERVIEW: WHAT IS A DEPENDENCY?

## 1626.

> "A dependency is external code that my project relies on. It can be declared directly or introduced transitively by another dependency. I use the dependency tree to understand the effective versions and troubleshoot conflicts instead of assuming the version visible in one POM entry is necessarily the only version involved."

---

# SECTION 1474 — INTERVIEW: WHAT IS AN ARTIFACT?

## 1627.

> "An artifact is a build or distribution output that can be stored and consumed later, such as a JAR, WAR, npm package or container image. In Maven, artifacts are identified using coordinates such as groupId, artifactId and version."

---

# SECTION 1475 — INTERVIEW: MAVEN CENTRAL

## 1628.

> "Maven Central is a public repository for Java and JVM ecosystem artifacts. Maven can resolve dependencies from it directly or, in enterprise environments, through a company repository manager such as Nexus or Artifactory."

---

# SECTION 1476 — INTERVIEW: NEXUS / ARTIFACTORY

## 1629.

> "Nexus and Artifactory are artifact repository managers. Organizations use them to host internal packages, proxy external repositories, enforce access control, improve dependency availability and integrate artifact management with CI/CD."

---

# SECTION 1477 — INTERVIEW: LOCAL MAVEN REPOSITORY

## 1630.

> "Maven's local repository, commonly under `~/.m2/repository`, stores downloaded and locally installed artifacts. It speeds builds, but it can also hide portability problems if a developer has an artifact locally that CI cannot retrieve from any remote repository."

---

# SECTION 1478 — INTERVIEW: SNAPSHOT VS RELEASE

## 1631.

> "A snapshot is a development version that can evolve over time, while a release version should represent immutable published content. For reproducible releases I prefer fixed release artifacts rather than depending on changing snapshots."

---

# SECTION 1479 — INTERVIEW: MAVEN `install` VS `deploy`

## 1632.

> "`mvn install` puts the built artifact into the local Maven repository for local consumption, while the Maven `deploy` phase publishes an artifact to a configured remote artifact repository. That is different from deploying the running application to an environment."

---

# SECTION 1480 — INTERVIEW: TRANSITIVE DEPENDENCY

## 1633.

> "A transitive dependency is brought into my project because one of my direct dependencies requires it. This is why dependency conflicts can happen even when I never explicitly declared the conflicting library."

---

# SECTION 1481 — INTERVIEW: HOW MAVEN RESOLVES VERSION CONFLICTS

## 1634.

> "Maven performs dependency mediation based primarily on the nearest dependency in the graph rather than simply choosing the highest version. I verify the actual result with `mvn dependency:tree` and use dependency management or exclusions only when I understand the compatibility impact."

---

# SECTION 1482 — INTERVIEW: BOM

## 1635.

> "A Maven BOM, or Bill of Materials, centrally manages a compatible set of dependency versions. It reduces manual version selection and helps keep framework-related libraries aligned."

---

# SECTION 1483 — INTERVIEW: BOM VS SBOM

## 1636.

> "A Maven BOM manages dependency versions during the build, while an SBOM is an inventory of software components included in a product. The first helps dependency alignment; the second helps supply-chain visibility and vulnerability response."

---

# SECTION 1484 — INTERVIEW: NOSUCHMETHODERROR AFTER UPGRADE

## 1637.

> "I would suspect a binary dependency mismatch. The code may have been compiled against one library version while a different version is loaded at runtime. I identify the class or method owner, inspect the dependency tree and effective version, and then correct the dependency alignment rather than patching the test code blindly."

---

# SECTION 1485 — INTERVIEW: WORKS LOCALLY, DEPENDENCY FAILS IN CI

## 1638.

> "I check whether my local Maven repository contains manually installed or cached artifacts that the clean CI runner cannot access. Then I verify repository configuration, credentials, network access and the exact artifact coordinates. The long-term fix is to make every required dependency resolvable from an approved repository."

---

# SECTION 1486 — INTERVIEW: WHAT IS SOFTWARE SUPPLY CHAIN?

## 1639.

> "The software supply chain includes the source code, third-party dependencies, build tools, CI runners, artifact repositories, container images and deployment tooling used to produce software. A compromise in any of those layers can affect the final release."

---

# SECTION 1487 — INTERVIEW: WHAT IS SCA?

## 1640.

> "Software Composition Analysis identifies third-party components and can report known vulnerabilities, licenses and dependency risks. I treat scanner findings as inputs to risk analysis rather than automatically assuming every CVE is exploitable in my application."

---

# SECTION 1488 — INTERVIEW: WHAT IS SBOM?

## 1641.

> "An SBOM is a Software Bill of Materials — an inventory of components and versions included in software. It helps teams quickly identify whether a product contains a library affected by a newly disclosed vulnerability."

---

# SECTION 1489 — INTERVIEW: WHY NOT DELETE `.m2` FIRST?

## 1642.

> "Deleting the entire local Maven repository is a broad destructive troubleshooting step that hides evidence and forces every dependency to download again. I first identify the failing artifact and root cause, then use targeted cleanup only if local artifact corruption or stale metadata is actually suspected."

---

# SECTION 1490 — SENIOR SDET SCENARIO: BUILD FAILS WITH 401

## 1643.

Error:

```text
401 from private Maven repository
```

Approach:

```text
repository reachable
↓
authentication failed
↓
check CI/local credentials
↓
server ID mapping
↓
token expiry
↓
secret injection
```

Do not change dependency version.

---

# SECTION 1491 — SENIOR SDET SCENARIO: BUILD FAILS WITH 404

## 1644.

Approach:

```text
check GAV
check repository
check snapshot vs release
check whether artifact was actually published
```

---

# SECTION 1492 — SENIOR SDET SCENARIO: PKIX ERROR

## 1645.

Approach:

```text
certificate chain
Java truststore
corporate CA
proxy
repository certificate
```

Do not globally disable TLS verification.

---

# SECTION 1493 — SENIOR SDET SCENARIO: BUILD WORKS ONLY ON ONE LAPTOP

## 1646.

Compare:

```text
local .m2
settings.xml
Java version
Maven version
environment
private repository access
manually installed JARs
```

Goal:

```text
remove hidden local dependency
```

---

# SECTION 1494 — SENIOR SDET SCENARIO: NOSUCHMETHODERROR

## 1647.

Check:

```text
which library owns method?
which versions exist?
what version Maven selected?
what version runtime loaded?
```

Then:

```text
dependency management/exclusion/upgrade
```

only after evidence.

---

# SECTION 1495 — SENIOR SDET SCENARIO: SECURITY SCANNER FLAGS TRANSITIVE LIBRARY

## 1648.

Approach:

```text
dependency tree
↓
identify direct parent dependency
↓
fixed version?
↓
can direct dependency be upgraded?
↓
can version be safely overridden?
↓
run regression
```

Do not remove random JAR.

---

# SECTION 1496 — SENIOR SDET SCENARIO: SNAPSHOT CHANGED BEHAVIOR

## 1649.

Potential cause:

```text
same SNAPSHOT coordinates
new remote artifact
```

Result:

```text
build behavior changes without POM version change
```

Lesson:

```text
release testing should prefer immutable versioned artifacts
```

---

# SECTION 1497 — SENIOR SDET SCENARIO: DEPLOYED FIX NOT VISIBLE

## 1650.

Check:

```text
which artifact version deployed?
which commit produced it?
which instance handled request?
is old instance still receiving traffic?
cache?
```

Before reopening defect.

---

# SECTION 1498 — SENIOR SDET SCENARIO: DEPENDENCY UPDATE BREAKS AUTH

## 1651.

If Spring Security/JWT dependency changed:

Focus regression on:

```text
login
token creation
token validation
invalid token
role handling
401
403
admin endpoints
user endpoints
```

Our RBAC suite gives valuable protection.

---

# SECTION 1499 — SENIOR SDET SCENARIO: REPORTING LIBRARY UPGRADE

## 1652.

If Allure-related dependency changes:

Check:

```text
tests still run
listener works
attachments generated
environment metadata present
sanitization still redacts secrets
report opens
```

Security behavior is part of regression.

---

# SECTION 1500 — SENIOR SDET SCENARIO: DOCKER IMAGE UPDATE

## 1653.

Example:

```text
postgres:16.x → newer 16.x
```

Validate:

```text
container startup
data compatibility
backend connection
schema behavior
API regression
DB tests
```

Version upgrades should be evidence-based.

---

# SECTION 1501 — DEPENDENCY REVIEW CHECKLIST

## 1654.

Before adding a new dependency:

```text
Do we need it?
Who publishes it?
Is it maintained?
What version?
What transitive dependencies?
Any known security concern?
License acceptable?
Can existing tool solve the need?
```

---

# SECTION 1502 — DEPENDENCY UPGRADE CHECKLIST

## 1655.

Before upgrading:

```text
Why upgrade?
Security?
Bug fix?
Feature?
Support/EOL?
```

Then:

```text
read release notes
check compatibility
inspect dependency tree
run regression
review security impact
```

---

# SECTION 1503 — PRIVATE ARTIFACT CHECKLIST

## 1656.

If private artifact fails:

```text
coordinates correct?
repository correct?
artifact published?
snapshot/release correct?
credentials valid?
permission valid?
network reachable?
TLS trusted?
CI settings correct?
```

---

# SECTION 1504 — SUPPLY-CHAIN CHECKLIST

## 1657.

```text
trusted source
known version
dependency scanning
secret protection
controlled repositories
trusted CI runner
artifact traceability
container scan later
regular upgrades
```

---

# SECTION 1505 — RAPID FIRE: MAVEN REPOSITORY

## 1658. Local Maven repository?

```text
~/.m2/repository
```

## 1659. Public Java artifact repository?

```text
Maven Central
```

## 1660. Enterprise repository managers?

```text
Nexus
Artifactory
```

## 1661. Maven artifact coordinates?

```text
groupId
artifactId
version
```

## 1662. Short name?

```text
GAV
```

---

# SECTION 1506 — RAPID FIRE: DEPENDENCIES

## 1663. Direct dependency?

```text
Declared by our project
```

## 1664. Transitive dependency?

```text
Brought by another dependency
```

## 1665. Dependency tree command?

```bash
mvn dependency:tree
```

## 1666. Central version management?

```text
dependencyManagement
```

## 1667. Compatible dependency set?

```text
BOM
```

## 1668. Remove unwanted transitive dependency?

```text
Exclusion
```

---

# SECTION 1507 — RAPID FIRE: MAVEN LIFECYCLE

## 1669. Install artifact locally?

```bash
mvn install
```

## 1670. Publish to remote Maven repository?

```bash
mvn deploy
```

## 1671. Development-changing version?

```text
SNAPSHOT
```

## 1672. Immutable published version concept?

```text
Release
```

---

# SECTION 1508 — RAPID FIRE: SECURITY

## 1673. Software Composition Analysis?

```text
SCA
```

## 1674. Vulnerability identifier?

```text
CVE
```

## 1675. Severity scoring system?

```text
CVSS
```

## 1676. Software component inventory?

```text
SBOM
```

## 1677. Internal package resolved from malicious public package?

```text
Dependency confusion
```

## 1678. Malicious near-identical package name?

```text
Typosquatting
```

---

# SECTION 1509 — BOM VS SBOM MEMORY MAP

## 1679.

```text
BOM
→ controls dependency versions during build

SBOM
→ records software components for visibility/security
```

---

# SECTION 1510 — ARTIFACT FLOW MEMORY MAP

## 1680.

```text
Source Code
   ↓
Maven
   ↓
Compile/Test
   ↓
Package
   ↓
JAR Artifact
   ↓
Artifact Repository
   ↓
Deployment
```

---

# SECTION 1511 — DEPENDENCY RESOLUTION MEMORY MAP

## 1681.

```text
pom.xml
  ↓
GAV
  ↓
Local Repository
  ↓
Remote Repository / Mirror
  ↓
POM + JAR
  ↓
Transitive Resolution
  ↓
Classpath
```

---

# SECTION 1512 — ENTERPRISE REPOSITORY MEMORY MAP

## 1682.

```text
Developer / CI
      ↓
Nexus / Artifactory
      ↓
├── Internal Artifacts
├── Maven Central Proxy
└── Vendor Repositories
```

---

# SECTION 1513 — SUPPLY-CHAIN MEMORY MAP

## 1683.

```text
Developer
   ↓
Git
   ↓
Dependencies
   ↓
Build Tool
   ↓
CI Runner
   ↓
Artifact Repository
   ↓
Container Registry
   ↓
Deployment
```

Any compromised layer can affect final release.

---

# SECTION 1514 — DEPENDENCY TROUBLESHOOTING DECISION TREE

## 1684.

```text
DEPENDENCY FAILURE
      ↓
Coordinates valid?
      |
      ├── NO
      │    ↓
      │ Fix GAV/version
      │
      └── YES
           ↓
Artifact exists remotely?
           |
           ├── NO
           │    ↓
           │ Publish/use correct repository
           │
           └── YES
                ↓
Can repository be reached?
                |
                ├── NO
                │    ↓
                │ DNS/proxy/TLS/network
                │
                └── YES
                     ↓
Authenticated/authorized?
                     |
                     ├── NO
                     │    ↓
                     │ Credentials/permissions
                     │
                     └── YES
                          ↓
Correct version resolved?
                          |
                          ├── NO
                          │    ↓
                          │ dependency tree/management
                          │
                          └── YES
                               ↓
Runtime compatibility?
```

---

# SECTION 1515 — RUNTIME DEPENDENCY ERROR DECISION TREE

## 1685.

```text
RUNTIME ERROR
   ↓
ClassNotFound?
NoClassDefFound?
NoSuchMethod?
NoSuchField?
   ↓
Identify owning library
   ↓
Dependency tree
   ↓
Resolved version
   ↓
Scope/classpath
   ↓
Compatibility
   ↓
Correct dependency configuration
```

---

# SECTION 1516 — CLEAN BUILD PRINCIPLE

## 1686.

When validating dependency changes:

```text
clean build
```

is valuable because it reduces stale build-output effects.

Example:

```bash
./mvnw clean test
```

for backend with correct runtime environment.

But:

```text
clean
```

does not mean:

```text
delete whole Maven repository
```

These are different.

---

# SECTION 1517 — CURRENT PROJECT POSITIONING

## 1687.

Correct statement:

> "My project uses Maven-based dependency management for the Spring Boot backend and Java REST Assured automation. I understand dependency scopes, transitive resolution, local and remote repositories, and how enterprise systems use Nexus or Artifactory for private artifacts. The project does not currently host its own private artifact repository, so I keep that as architecture knowledge rather than claiming an implementation that does not exist."

---

# SECTION 1518 — SENIOR SDET ONE-MINUTE ANSWER

## 1688.

> "I treat dependency management as part of build reliability and software supply-chain quality. In Maven I understand direct versus transitive dependencies, scopes, dependency management, BOMs, local and remote repositories, and effective dependency resolution. When a build fails I distinguish coordinate errors, repository access, TLS or authentication failures from version conflicts. In enterprise environments I understand how Nexus or Artifactory can host internal packages and proxy external repositories. I also consider SCA, CVEs, artifact traceability and SBOMs when evaluating dependency risk rather than blindly upgrading or deleting caches."

---

# SECTION 1519 — FINAL 5-MINUTE REVISION

## 1689. Dependency

```text
Direct
Transitive
Scope
Version
Tree
Conflict
Exclusion
```

## 1690. Artifact

```text
JAR
GAV
Version
Snapshot
Release
Install
Deploy
```

## 1691. Repository

```text
Local .m2
Maven Central
Nexus
Artifactory
Mirror
Proxy repository
Hosted repository
```

## 1692. Troubleshooting

```text
Coordinates
Repository
Network
TLS
Authentication
Permissions
Resolution
Classpath
Compatibility
```

## 1693. Security

```text
SCA
CVE
CVSS
SBOM
Dependency confusion
Typosquatting
Trusted repository
Artifact integrity
```

---

# SECTION 1520 — FINAL SENIOR SDET PRINCIPLE

## 1694.

Do not say:

```text
"Maven cannot download the dependency."
```

Say:

```text
"Maven resolves the artifact coordinates correctly, but the configured
private repository returns HTTP 401, so the issue is repository
authentication rather than dependency version resolution."
```

Do not say:

```text
"Library issue."
```

Say:

```text
"The application was compiled against a newer API, but Maven resolves
an older transitive version at runtime, which causes NoSuchMethodError.
The dependency tree shows the conflicting path."
```

Do not say:

```text
"Security scanner says upgrade everything."
```

Say:

```text
"The flagged CVE is present through a transitive dependency. I would
identify the dependency path, confirm exposure and fixed versions,
upgrade the owning dependency where compatible, and run targeted plus
regression testing."
```

Senior SDET approach:

```text
IDENTIFY
   ↓
RESOLVE
   ↓
TRACE
   ↓
VERIFY
   ↓
SECURE
   ↓
RETEST
```

---

# END OF PART 8 — DEPENDENCIES, ARTIFACTS, MAVEN CENTRAL, NEXUS/ARTIFACTORY & SOFTWARE SUPPLY CHAIN

Next:

**PART 9 — BUILD, ENVIRONMENT & TOOLCHAIN TROUBLESHOOTING FOR SENIOR SDET**

---

# PART 9 — BUILD, ENVIRONMENT & TOOLCHAIN TROUBLESHOOTING FOR SENIOR SDET

# SECTION 1521 — WHY TOOLCHAIN TROUBLESHOOTING MATTERS

## 1695. Senior SDET Perspective

A test failure does not always mean:

```text
Application Bug
```

A failure can originate from:

```text
Source Code
Build Tool
Dependency
Runtime
Environment Variable
Configuration
Network
Database
Container
Test Framework
CI Runner
Operating System
Architecture
```

A Senior SDET should first classify the failure before trying to fix it.

---

# SECTION 1522 — COMPLETE TOOLCHAIN MENTAL MODEL

## 1696.

Our engineering flow can be viewed as:

```text
Source Code
    ↓
Git
    ↓
JDK / Node
    ↓
Build Tool
    ↓
Dependencies
    ↓
Build Artifact
    ↓
Configuration
    ↓
Runtime
    ↓
Network
    ↓
Database / External Services
    ↓
Application
    ↓
Automation
    ↓
Reports
```

Failure can happen at any layer.

---

# SECTION 1523 — FIRST RULE OF TROUBLESHOOTING

## 1697.

Do not start with:

```text
What should I change?
```

Start with:

```text
What exactly failed?
At which layer?
What evidence do I have?
```

Senior troubleshooting:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
VERIFY
   ↓
FIX
   ↓
REGRESSION
```

---

# SECTION 1524 — ERROR MESSAGE IS EVIDENCE

## 1698.

Bad approach:

```text
Build failed.
Let me change pom.xml.
```

Better:

```text
Read first meaningful error.
Identify component.
Identify failure phase.
Check nested cause.
```

Many tools print:

```text
top-level failure
    ↓
nested failure
    ↓
root cause
```

Always inspect:

```text
Caused by:
```

when available.

---

# SECTION 1525 — FAILURE CLASSIFICATION

## 1699.

Classify failure into one of these buckets:

```text
1. Source / Compilation
2. Dependency Resolution
3. Build Configuration
4. Runtime / Startup
5. Environment Configuration
6. Network
7. Database
8. Authentication / Authorization
9. Test Framework
10. Test Data
11. Container
12. CI/CD
13. OS / Architecture
14. External Service
```

This immediately reduces the search space.

---

# SECTION 1526 — BUILD-TIME VS RUN-TIME FAILURE

## 1700.

Build-time failure happens before application successfully starts.

Examples:

```text
Compilation error
Dependency resolution failure
Plugin failure
Test compilation failure
Packaging failure
```

Runtime failure happens after code is built and execution begins.

Examples:

```text
Application startup failure
Database connection failure
Port conflict
NoSuchMethodError
HTTP 500
```

---

# SECTION 1527 — COMPILE-TIME FAILURE

## 1701.

Example:

```text
cannot find symbol
```

Potential causes:

```text
wrong import
missing dependency
renamed class/method
wrong source version
generated source missing
```

First inspect:

```text
exact file
line number
symbol name
```

---

# SECTION 1528 — TEST-COMPILE FAILURE

## 1702.

Production code may compile while test code does not.

Possible:

```text
test dependency missing
test helper renamed
wrong package
test API changed
```

Important distinction:

```text
compile
```

and:

```text
testCompile
```

are different build phases conceptually.

---

# SECTION 1529 — DEPENDENCY RESOLUTION FAILURE

## 1703.

Examples:

```text
Could not resolve artifact
Could not find artifact
401 repository error
PKIX error
Connection timeout
```

Do not inspect application business logic.

Inspect:

```text
coordinates
repository
network
TLS
authentication
Maven configuration
```

---

# SECTION 1530 — BUILD PLUGIN FAILURE

## 1704.

Maven plugins perform build tasks.

A failure may originate from:

```text
compiler plugin
Surefire
Spring Boot plugin
resource plugin
```

Read:

```text
which plugin goal failed?
```

before debugging application code.

---

# SECTION 1531 — MAVEN LIFECYCLE FAILURE LOCATION

## 1705.

Ask:

```text
Did clean fail?
compile?
test?
package?
install?
deploy?
```

The failing phase tells us where to investigate.

Example:

```text
compile succeeds
test fails
```

means:

```text
compiler/toolchain is probably mostly functional
```

and investigation moves toward tests/runtime/test dependencies.

---

# SECTION 1532 — MAVEN ERROR DIAGNOSTICS

## 1706.

Useful Maven options:

```bash
mvn test
```

More stack trace:

```bash
mvn -e test
```

Debug-level Maven output:

```bash
mvn -X test
```

Use `-X` only when needed.

Review debug logs before sharing because they may contain sensitive configuration.

---

# SECTION 1533 — MAVEN QUIET MODE WARNING

## 1707.

Command:

```bash
mvn -q test
```

reduces output.

Useful sometimes in automation.

But during troubleshooting:

```text
less output can hide useful evidence
```

Prefer normal logs first.

---

# SECTION 1534 — CLEAN BUILD

## 1708.

```bash
mvn clean test
```

removes previous build output such as:

```text
target/
```

before rebuilding.

Useful when stale compiled output is suspected.

It does NOT clear:

```text
~/.m2/repository
```

---

# SECTION 1535 — CLEAN BUILD VS DEPENDENCY CACHE CLEANUP

## 1709.

Different actions:

```text
mvn clean
→ removes project build output

delete artifact from ~/.m2
→ removes local dependency artifact
```

Do not confuse them.

---

# SECTION 1536 — JAVA TOOLCHAIN CHECK

## 1710.

First commands:

```bash
java -version
```

```bash
javac -version
```

```bash
echo "$JAVA_HOME"
```

```bash
which java
```

```bash
which javac
```

Questions:

```text
Expected Java version?
Compiler available?
JAVA_HOME correct?
PATH using expected binary?
```

---

# SECTION 1537 — MAVEN JAVA CHECK

## 1711.

Run:

```bash
mvn -version
```

This shows Maven information including the Java runtime Maven is using.

Very important because:

```text
java -version
```

and Maven's actual Java runtime can sometimes differ due to environment configuration.

---

# SECTION 1538 — JAVA VERSION MISMATCH

## 1712.

Scenario:

```text
Terminal Java = 17
IDE Java = 21
CI Java = 17
```

Potential result:

```text
works in IDE
fails in terminal
```

or:

```text
works locally
fails in CI
```

Always compare actual runtimes.

---

# SECTION 1539 — JAVA_HOME VS PATH

## 1713.

`JAVA_HOME`:

```text
points to Java installation
```

`PATH`:

```text
determines which executable shell finds
```

They can disagree.

Example:

```text
JAVA_HOME → JDK 17
PATH java → JDK 21
```

That can create confusing behavior.

---

# SECTION 1540 — COMMAND RESOLUTION

## 1714.

Check:

```bash
which java
```

```bash
which mvn
```

```bash
which node
```

```bash
which npm
```

```bash
which docker
```

This tells us which executable is being used.

---

# SECTION 1541 — COMMAND NOT FOUND

## 1715.

Example:

```text
mvn: command not found
```

This is not:

```text
Maven project failure
```

It is:

```text
shell/tool installation/PATH problem
```

Check:

```bash
which mvn
```

```bash
echo "$PATH"
```

---

# SECTION 1542 — PERMISSION DENIED

## 1716.

Example:

```text
permission denied: ./run-local.sh
```

Check:

```bash
ls -l run-local.sh
```

If execute permission is missing:

```bash
chmod +x run-local.sh
```

Do not immediately use:

```bash
sudo
```

or:

```bash
chmod 777
```

---

# SECTION 1543 — BAD INTERPRETER / SHEBANG ISSUE

## 1717.

Shell scripts commonly begin with:

```bash
#!/bin/bash
```

or:

```bash
#!/usr/bin/env bash
```

If interpreter path is invalid, script can fail before application code runs.

Check first line:

```bash
head -n 1 script.sh
```

---

# SECTION 1544 — LINE ENDING ISSUE

## 1718.

Scripts created on Windows can contain:

```text
CRLF
```

while Unix/macOS tools commonly expect:

```text
LF
```

Possible symptoms:

```text
bad interpreter
strange ^M characters
```

This is a file-format/toolchain issue, not application logic.

---

# SECTION 1545 — CURRENT WORKING DIRECTORY ISSUE

## 1719.

Script may assume:

```text
current directory = project directory
```

But user can execute it from somewhere else.

Safer scripts determine their own location.

Our backend local script uses the script directory concept so it can locate `.env` and backend files more reliably.

---

# SECTION 1546 — `dirname "$0"`

## 1720.

In shell scripts:

```bash
dirname "$0"
```

helps determine the directory containing the script path.

This is useful for scripts that should work regardless of caller's current directory.

---

# SECTION 1547 — ENVIRONMENT VARIABLE FAILURE

## 1721.

Application may require:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

If missing:

```text
startup can fail
configuration can resolve incorrectly
authentication can fail
```

Do not print secret values while troubleshooting.

---

# SECTION 1548 — SAFE ENVIRONMENT CHECK

## 1722.

Instead of:

```bash
echo "$JWT_SECRET"
```

use:

```bash
if [ -n "$JWT_SECRET" ]; then
  echo "JWT_SECRET is set"
else
  echo "JWT_SECRET is missing"
fi
```

This confirms presence without exposing value.

---

# SECTION 1549 — `printenv`

## 1723.

To inspect a non-sensitive variable:

```bash
printenv TEST_ENV
```

For secrets, prefer presence-only checks.

Never dump the complete environment in shared CI logs without understanding what it contains.

---

# SECTION 1550 — `.env` IS NOT MAGIC

## 1724.

A `.env` file existing in a folder does NOT automatically mean every program loads it.

Something must load it.

Our local shell scripts use:

```bash
set -a
source .env
set +a
```

conceptually to export variables to child processes.

---

# SECTION 1551 — `source`

## 1725.

```bash
source .env
```

executes variable assignments in the current shell.

Without export behavior, child processes may not receive all shell variables.

That is why:

```bash
set -a
```

can be useful.

---

# SECTION 1552 — `set -a`

## 1726.

```bash
set -a
```

causes subsequently defined shell variables to be automatically exported.

Then:

```bash
source .env
```

loads them.

Then:

```bash
set +a
```

disables auto-export.

---

# SECTION 1553 — BACKEND LOCAL START FLOW

## 1727.

Our backend local script conceptually performs:

```text
Find script directory
      ↓
Load backend .env
      ↓
Export variables
      ↓
Move to backend directory
      ↓
Run Maven Wrapper
      ↓
Start Spring Boot
```

Root command:

```bash
./backend/run-local.sh
```

---

# SECTION 1554 — WHY DIRECT MAVEN TEST MAY DIFFER

## 1728.

Running:

```bash
./mvnw clean test
```

does not automatically mean backend `.env` has been loaded.

If tests/configuration need those variables:

```text
load environment first
```

then run Maven.

---

# SECTION 1555 — ENVIRONMENT OVERRIDE TROUBLESHOOTING

## 1729.

If application uses unexpected value:

Check:

```text
shell environment
application.properties
command-line arguments
profile/config source
CI variables
```

Higher-precedence configuration may override expected default.

---

# SECTION 1556 — DEFAULT VALUE CAN HIDE MISSING CONFIG

## 1730.

Example concept:

```properties
some.value=${SOME_VALUE:default}
```

If `SOME_VALUE` is missing:

```text
application still starts using default
```

This can hide environment mistakes.

Therefore verify:

```text
which environment/config is actually active?
```

---

# SECTION 1557 — LOCAL / QA / STAGE ENVIRONMENT MIX-UP

## 1731.

Our API automation supports environment selection such as:

```text
local
qa
stage
```

A common failure is:

```text
test intended for QA
but base URL points to local
```

Always verify effective environment and base URL.

---

# SECTION 1558 — BASE URL TROUBLESHOOTING

## 1732.

Ask:

```text
Which environment?
Which base URL?
Can host resolve?
Can port connect?
Does endpoint exist?
Is authentication valid there?
```

This prevents debugging test assertions when test is calling wrong environment.

---

# SECTION 1559 — CONFIGURATION DRIFT

## 1733.

Configuration drift means environments differ unintentionally.

Example:

```text
Local feature flag = ON
QA = OFF
Stage = ON
```

Tests can behave differently even with identical code.

Always compare:

```text
code
configuration
data
infrastructure
```

---

# SECTION 1560 — CODE SAME, ENVIRONMENT DIFFERENT

## 1734.

If same commit behaves differently:

Possible:

```text
environment variables
database state
dependency/service version
network
feature flag
external integration
runtime version
```

Do not immediately conclude:

```text
random flaky issue
```

---

# SECTION 1561 — PORT CONFLICT

## 1735.

If Spring Boot cannot start on 8080:

Check:

```bash
lsof -i :8080
```

Potential:

```text
another backend instance
another application
old process
```

Then identify process before terminating it.

---

# SECTION 1562 — CONNECTION REFUSED

## 1736.

Example:

```text
Connection refused localhost:8080
```

Usually means:

```text
host reachable
but nothing listening on requested port
```

Check:

```bash
lsof -i :8080
```

Then:

```text
is backend running?
```

---

# SECTION 1563 — TIMEOUT

## 1737.

Timeout differs from connection refused.

Possible:

```text
network path blocked
service overloaded
firewall
proxy
downstream hanging
wrong hostname
```

Classify timeout:

```text
connect timeout
read timeout
test timeout
CI job timeout
```

---

# SECTION 1564 — DNS FAILURE

## 1738.

Example:

```text
UnknownHostException
```

Check:

```bash
nslookup <hostname>
```

or:

```bash
dig <hostname>
```

Possible:

```text
wrong hostname
DNS issue
VPN
network configuration
```

---

# SECTION 1565 — HTTP FAILURE VS NETWORK FAILURE

## 1739.

If response is:

```text
401
403
404
500
```

network connectivity worked enough to receive HTTP response.

If:

```text
connection refused
DNS failure
timeout before response
```

problem is lower in the stack.

This distinction saves time.

---

# SECTION 1566 — CURL AS ISOLATION TOOL

## 1740.

Suppose REST Assured test fails.

Try endpoint independently:

```bash
curl -i http://localhost:8080/v3/api-docs
```

If curl also fails:

```text
likely service/environment/network issue
```

If curl succeeds but automation fails:

```text
investigate automation configuration/request
```

---

# SECTION 1567 — TOOL TRIANGULATION

## 1741.

Useful comparison:

```text
Browser
curl
Swagger
Automation
```

If only one fails:

```text
problem may be client-specific
```

If all fail:

```text
server/environment likely
```

---

# SECTION 1568 — SWAGGER AS DEBUGGING TOOL

## 1742.

Swagger can help verify:

```text
endpoint available
HTTP method
request schema
JWT authentication
response
```

But Swagger success does not automatically prove:

```text
automation is correct
```

Compare actual requests.

---

# SECTION 1569 — REQUEST COMPARISON

## 1743.

If Swagger succeeds but REST Assured fails, compare:

```text
URL
method
headers
Authorization
Content-Type
body
query params
path params
```

Usually there is a meaningful difference.

---

# SECTION 1570 — 400 TROUBLESHOOTING

## 1744.

HTTP 400:

```text
Bad Request
```

Inspect:

```text
JSON structure
required fields
data type
validation
query/path params
Content-Type
```

Do not treat as server outage.

---

# SECTION 1571 — 401 TROUBLESHOOTING

## 1745.

HTTP 401:

```text
authentication problem
```

Check:

```text
token present?
token valid?
token expired?
signature valid?
correct environment?
Authorization header format?
```

---

# SECTION 1572 — 403 TROUBLESHOOTING

## 1746.

HTTP 403:

```text
identity understood
but permission denied
```

Check:

```text
role
authority
resource ownership
endpoint policy
```

In our project:

```text
USER POST product → 403
ADMIN POST product → allowed
```

is expected RBAC behavior.

---

# SECTION 1573 — 404 TROUBLESHOOTING

## 1747.

Check:

```text
base URL
path
HTTP method
context path
route deployment
environment version
```

Do not assume:

```text
record not found
```

unless API contract uses 404 that way.

---

# SECTION 1574 — 405 TROUBLESHOOTING

## 1748.

HTTP 405:

```text
Method Not Allowed
```

Example:

```text
endpoint exists
but POST used where GET is allowed
```

Check:

```text
HTTP method
```

---

# SECTION 1575 — 409 TROUBLESHOOTING

## 1749.

HTTP 409 commonly represents:

```text
state/resource conflict
```

Examples:

```text
duplicate unique resource
invalid state transition
```

Interpret according to API contract.

---

# SECTION 1576 — 415 TROUBLESHOOTING

## 1750.

HTTP 415:

```text
Unsupported Media Type
```

Check:

```text
Content-Type
request body format
```

Example:

```text
server expects application/json
```

---

# SECTION 1577 — 422 TROUBLESHOOTING

## 1751.

Some APIs use 422 for semantic validation errors.

Meaning depends on API design.

Check:

```text
contract
validation response
```

Do not assume every API uses 422.

---

# SECTION 1578 — 429 TROUBLESHOOTING

## 1752.

HTTP 429:

```text
Too Many Requests
```

Potential:

```text
rate limiting
test parallelism
shared environment traffic
```

Check:

```text
Retry-After if present
rate-limit policy
test design
```

Do not blindly retry aggressively.

---

# SECTION 1579 — 500 TROUBLESHOOTING

## 1753.

HTTP 500:

```text
server-side failure
```

Collect:

```text
request
response
server logs
timestamp
correlation ID
test data
```

Then trace root cause.

---

# SECTION 1580 — 502 TROUBLESHOOTING

## 1754.

HTTP 502:

```text
gateway/proxy received invalid/unusable upstream response
```

Potential:

```text
upstream service down
bad proxy routing
connection reset
```

Investigate infrastructure/upstream, not only test assertion.

---

# SECTION 1581 — 503 TROUBLESHOOTING

## 1755.

HTTP 503:

```text
Service Unavailable
```

Potential:

```text
service unavailable
maintenance
overload
dependency unhealthy
```

Can be transient, but verify before retrying.

---

# SECTION 1582 — 504 TROUBLESHOOTING

## 1756.

HTTP 504:

```text
Gateway Timeout
```

Usually gateway waited too long for upstream.

Investigate:

```text
upstream latency
timeout settings
service health
network
```

---

# SECTION 1583 — DATABASE CONNECTION FAILURE

## 1757.

Potential symptoms:

```text
Connection refused
authentication failed
database does not exist
timeout
```

Check:

```text
PostgreSQL container running?
port 5432 available?
DB URL correct?
credentials loaded?
database exists?
```

---

# SECTION 1584 — CHECK POSTGRES CONTAINER

## 1758.

```bash
docker ps
```

or:

```bash
docker compose ps
```

Check:

```text
container running?
port mapping?
health/status?
```

---

# SECTION 1585 — DATABASE LOGS

## 1759.

```bash
docker logs sdet-commerce-postgres
```

or with Compose:

```bash
docker compose logs postgres
```

Look for:

```text
startup failure
authentication issue
storage issue
shutdown
```

---

# SECTION 1586 — BACKEND LOGS VS DATABASE LOGS

## 1760.

Backend may say:

```text
cannot connect
```

Database logs may explain:

```text
authentication rejected
database missing
server startup problem
```

Always inspect both sides when possible.

---

# SECTION 1587 — DATABASE STATE FAILURE

## 1761.

Tests may fail even when DB connection works.

Possible:

```text
stale test data
duplicate unique values
unexpected existing records
wrong cleanup
stock already modified
order already cancelled
```

This is:

```text
test-data/state issue
```

not connectivity issue.

---

# SECTION 1588 — TEST DATA COLLISION

## 1762.

Parallel/repeated tests can collide if they use fixed values.

Examples:

```text
same email
same unique product field
same order state
```

Good automation should use:

```text
controlled unique data
cleanup
isolated ownership
```

where required.

---

# SECTION 1589 — CLEANUP FAILURE

## 1763.

Test:

```text
passes first time
fails second time
```

Potential:

```text
test did not clean state
```

Check:

```text
DB records
created entities
cleanup utility
test ordering assumptions
```

---

# SECTION 1590 — TEST ORDER DEPENDENCY

## 1764.

Bad:

```text
Test B passes only if Test A ran first
```

Independent automated tests should ideally create required state themselves.

Otherwise:

```text
parallel execution
reruns
subset execution
```

become unreliable.

---

# SECTION 1591 — FLAKY TEST CLASSIFICATION

## 1765.

Do not label every intermittent failure:

```text
flaky
```

Classify cause:

```text
timing
data
environment
network
race condition
external dependency
test bug
application bug
```

"Flaky" describes behavior, not root cause.

---

# SECTION 1592 — RETRY IS NOT A FIX

## 1766.

Bad:

```text
test fails
→ add retry 5 times
```

Retry can be appropriate for carefully identified transient failures.

It should not hide:

```text
deterministic bug
bad locator
wrong assertion
invalid data
401
403
validation error
```

---

# SECTION 1593 — WHEN RETRY MAY BE REASONABLE

## 1767.

Potential:

```text
temporary network issue
eventual consistency
known transient external service
```

But use:

```text
bounded retries
backoff
logging
failure visibility
```

---

# SECTION 1594 — FIXED SLEEP ANTI-PATTERN

## 1768.

Bad UI/API synchronization:

```text
sleep 10 seconds
```

Better:

```text
wait for actual condition
poll intentionally
use framework waits
```

Fixed sleep:

```text
slows tests
still may fail
hides timing behavior
```

---

# SECTION 1595 — TIMEOUT TUNING

## 1769.

Do not solve every timeout by:

```text
increase from 30 sec to 5 minutes
```

First determine:

```text
normal operation slow?
service hung?
network issue?
test waiting for wrong condition?
```

Timeout should reflect expected behavior.

---

# SECTION 1596 — DOCKER TOOLCHAIN FAILURE

## 1770.

Potential:

```text
Docker daemon not running
container exits
port conflict
image unavailable
volume permission
environment variable missing
network issue
```

Start with:

```bash
docker ps
```

---

# SECTION 1597 — DOCKER DAEMON ISSUE

## 1771.

If:

```text
Cannot connect to Docker daemon
```

this is not PostgreSQL application configuration yet.

Check:

```text
Docker Desktop / Docker engine running?
```

---

# SECTION 1598 — CONTAINER EXITED

## 1772.

Check:

```bash
docker ps -a
```

Then:

```bash
docker logs <container>
```

Container status tells you:

```text
process exited
```

Logs tell you:

```text
why
```

---

# SECTION 1599 — DOCKER PORT MAPPING

## 1773.

Example:

```text
5432:5432
```

means:

```text
host port 5432
      ↓
container port 5432
```

If host port is already occupied:

```text
container startup may fail
```

---

# SECTION 1600 — CONTAINER `localhost`

## 1774.

Inside a container:

```text
localhost
```

means:

```text
that container itself
```

It does NOT automatically mean:

```text
your Mac
```

or:

```text
another container
```

This is one of the most common container networking mistakes.

---

# SECTION 1601 — CONTAINER-TO-CONTAINER COMMUNICATION

## 1775.

In Docker Compose, services can communicate using service names on the Compose network.

Concept:

```text
backend container
      ↓
postgres:5432
```

rather than:

```text
localhost:5432
```

when both are containers.

We will apply this when full application Dockerization begins.

---

# SECTION 1602 — HOST TO CONTAINER

## 1776.

Current local architecture can be:

```text
Spring Boot on Mac
      ↓
localhost:5432
      ↓
PostgreSQL container
```

because PostgreSQL port is published to host.

Different from future:

```text
backend container
      ↓
postgres service
```

---

# SECTION 1603 — DOCKER VOLUME STATE

## 1777.

Persistent Docker volume means database data can survive:

```text
container recreation
```

This is useful.

But it can also surprise testers:

```text
"I recreated container, why is old data still there?"
```

Because:

```text
volume persisted
```

---

# SECTION 1604 — `docker compose down`

## 1778.

Typically:

```bash
docker compose down
```

stops/removes Compose containers and network.

It does not automatically mean persistent named volumes are deleted.

---

# SECTION 1605 — `docker compose down -v` WARNING

## 1779.

```bash
docker compose down -v
```

can remove Compose volumes.

For database:

```text
this can delete local persisted data
```

Use only intentionally.

Never use as generic troubleshooting step.

---

# SECTION 1606 — IMAGE VERSION TROUBLESHOOTING

## 1780.

Check:

```bash
docker images
```

Potential:

```text
wrong image tag
old local image
unexpected architecture
```

Future CI should make image versions traceable.

---

# SECTION 1607 — APPLE SILICON ARCHITECTURE

## 1781.

Our development machine uses Apple Silicon architecture.

Potential compatibility issue:

```text
tool/image supports amd64 only
```

while machine is:

```text
arm64
```

Possible symptoms:

```text
image platform mismatch
native library failure
binary cannot execute
```

---

# SECTION 1608 — CHECK MACHINE ARCHITECTURE

## 1782.

```bash
uname -m
```

Possible on Apple Silicon:

```text
arm64
```

Java may report architecture information through runtime properties/logging as well.

---

# SECTION 1609 — NATIVE DEPENDENCY ISSUE

## 1783.

Pure Java bytecode is relatively portable.

Native components can depend on:

```text
OS
CPU architecture
native library
system package
```

This matters for:

```text
JNI
native SDKs
browser binaries
some database/tool dependencies
```

---

# SECTION 1610 — IDE VS TERMINAL DIFFERENCE

## 1784.

If code works in IDE but not terminal, compare:

```text
JDK
working directory
environment variables
Maven version
run configuration
profiles
classpath
```

IDE may silently provide configuration that shell does not.

---

# SECTION 1611 — TERMINAL VS CI DIFFERENCE

## 1785.

Compare:

```text
OS
Java
Maven
Node
environment variables
secrets
network
repository access
working directory
filesystem case sensitivity
timezone
locale
```

CI should not be assumed identical to laptop.

---

# SECTION 1612 — CASE-SENSITIVITY ISSUE

## 1786.

A filename/import may work on one filesystem but fail on another if case behavior differs.

Example:

```text
UserService.java
userservice.java
```

Be exact with casing.

This becomes especially important in Linux CI environments.

---

# SECTION 1613 — FILE PATH SEPARATOR

## 1787.

Avoid hardcoding OS-specific path assumptions where possible.

Unix-like:

```text
/path/to/file
```

Windows:

```text
C:\path\to\file
```

Use language/framework path utilities when application code needs portability.

---

# SECTION 1614 — TIMEZONE FAILURE

## 1788.

Tests involving:

```text
date
time
expiry
scheduling
```

can pass locally and fail in CI due to timezone.

Check:

```bash
date
```

and application/runtime timezone assumptions.

Use explicit timezone handling for deterministic tests.

---

# SECTION 1615 — LOCALE FAILURE

## 1789.

Locale can affect:

```text
date formatting
decimal separators
sorting
text formatting
```

Avoid tests that depend accidentally on developer machine locale.

---

# SECTION 1616 — ENVIRONMENT-SPECIFIC DATA

## 1790.

QA may have:

```text
different users
different products
different feature flags
different permissions
```

Automation should externalize environment-specific configuration.

Our API framework's environment switching supports this direction.

---

# SECTION 1617 — SECRET MISSING IN CI

## 1791.

Local `.env` works.

CI fails.

Likely because:

```text
CI does not receive local .env
```

Correct:

```text
configure CI secret
inject as environment variable
```

Never commit `.env` just to make CI work.

---

# SECTION 1618 — SECRET NAME MISMATCH

## 1792.

Example:

Application expects:

```text
DB_USERNAME
```

CI provides:

```text
DATABASE_USERNAME
```

Both exist conceptually, but names differ.

Result:

```text
missing config
```

Check exact variable names.

---

# SECTION 1619 — SECRET VALUE WHITESPACE

## 1793.

Copied credentials can accidentally include:

```text
leading/trailing whitespace
newline
```

This can cause authentication failure.

Verify secret-management input carefully without logging value.

---

# SECTION 1620 — CI WORKING DIRECTORY

## 1794.

Monorepo-style repository:

```text
root
├── backend
└── api-automation
```

CI command:

```bash
mvn test
```

from root may fail if root has no relevant POM.

Pipeline must run in correct directory or specify correct project file.

---

# SECTION 1621 — CI ORDER OF OPERATIONS

## 1795.

API automation may require:

```text
backend available
database available
environment configured
```

before tests.

Pipeline must orchestrate prerequisites.

Do not run tests before target environment is ready.

---

# SECTION 1622 — SERVICE READINESS

## 1796.

Container/process started does not necessarily mean:

```text
application ready
```

Example:

```text
process starts
↓
Spring context initializes
↓
DB connection establishes
↓
HTTP port becomes ready
```

CI should wait for readiness, not arbitrary fixed sleep.

---

# SECTION 1623 — READINESS CHECK

## 1797.

Conceptually poll:

```text
health endpoint
known lightweight endpoint
TCP port + application-level response
```

until ready or timeout.

If no health endpoint exists, use an appropriate known endpoint.

Do not claim our current project has Spring Boot Actuator health endpoint unless implemented.

---

# SECTION 1624 — PROCESS EXISTS BUT APP IS BROKEN

## 1798.

A process can exist while:

```text
application partially failed
dependency unavailable
requests returning 500
```

Therefore:

```text
process check ≠ functional readiness
```

---

# SECTION 1625 — CI TEST REPORT MISSING

## 1799.

Tests may have failed before report generation.

Check:

```text
Did test runner start?
Did report directory get created?
Did upload step run?
Was path correct?
```

Do not assume reporting plugin itself failed.

---

# SECTION 1626 — ALLURE RESULTS VS ALLURE REPORT

## 1800.

Conceptually:

```text
Test execution
      ↓
Allure results
      ↓
Allure report generation
```

If report missing:

```text
first verify results exist
```

Our current API framework generates Allure results and can serve a report locally.

---

# SECTION 1627 — REPORT PATH ISSUE

## 1801.

CI artifact upload can fail because:

```text
wrong relative path
working directory mismatch
report not generated
```

Always inspect actual filesystem/path.

---

# SECTION 1628 — TEST FAILURE VS REPORT FAILURE

## 1802.

These are different:

```text
tests failed
```

vs:

```text
tests completed but report publication failed
```

Pipeline status should make distinction clear.

---

# SECTION 1629 — TEST RUNNER FAILURE

## 1803.

If Maven starts but zero tests run:

Check:

```text
test naming
Surefire configuration
TestNG suite/config
includes/excludes
groups/tags
source directory
```

Zero tests passing is not:

```text
successful regression
```

---

# SECTION 1630 — ZERO TESTS EXECUTED

## 1804.

Critical quality rule:

```text
BUILD SUCCESS
Tests run: 0
```

does NOT mean:

```text
tests passed
```

It means:

```text
no tests executed
```

CI should detect unexpected zero-test situations.

---

# SECTION 1631 — EXPECTED TEST COUNT

## 1805.

Our current API regression baseline:

```text
48 tests
48 passed
```

If future run suddenly shows:

```text
12 tests
12 passed
```

ask:

```text
Why did 36 tests disappear?
```

Pass percentage alone is insufficient.

---

# SECTION 1632 — TEST SELECTION ERROR

## 1806.

Potential:

```text
wrong tag
wrong group
wrong suite
wrong folder
wrong profile
```

causing subset execution.

Always verify:

```text
what was intended
what actually ran
```

---

# SECTION 1633 — PARALLEL EXECUTION FAILURE

## 1807.

Parallel tests can expose:

```text
shared data
static mutable state
token collision
resource contention
DB race
order dependency
```

Do not assume parallelism itself is defective.

It may reveal hidden test design issues.

---

# SECTION 1634 — THREAD-SAFETY

## 1808.

If shared object is mutated by multiple test threads:

```text
race condition
```

Potential:

```text
wrong token
wrong request data
intermittent assertion
```

Design test framework state carefully before enabling aggressive parallelism.

---

# SECTION 1635 — DATABASE TEST PARALLELISM

## 1809.

DB validation tests can interfere when:

```text
same records
same user
same product
same cleanup
```

Use:

```text
unique data
ownership
transaction/isolation strategy
controlled cleanup
```

as appropriate.

---

# SECTION 1636 — AUTH TOKEN ENVIRONMENT MISMATCH

## 1810.

Token generated from:

```text
Environment A
```

used against:

```text
Environment B
```

may fail.

Always generate/use authentication in intended target environment unless architecture explicitly supports cross-environment tokens.

---

# SECTION 1637 — TOKEN EXPIRY DURING SUITE

## 1811.

Long-running suite may use token that expires mid-run.

Potential:

```text
early tests pass
later tests 401
```

Investigate:

```text
token lifetime
token creation strategy
suite duration
```

Do not simply retry 401.

---

# SECTION 1638 — SYSTEM CLOCK EFFECT

## 1812.

JWT and TLS depend on time.

Incorrect system clock can cause:

```text
token appears expired/not yet valid
certificate validation issues
```

Check time when errors appear inconsistent.

---

# SECTION 1639 — TLS TRUSTSTORE DIFFERENCE

## 1813.

Local Java may trust certificate.

CI Java may not.

Result:

```text
works locally
PKIX fails in CI
```

Compare:

```text
JDK
truststore
corporate CA setup
proxy
```

Do not disable TLS validation as permanent fix.

---

# SECTION 1640 — CORPORATE PROXY DIFFERENCE

## 1814.

Enterprise environment may require:

```text
HTTP_PROXY
HTTPS_PROXY
NO_PROXY
Maven proxy configuration
```

If external dependency/API access fails only in corporate environment, inspect proxy path.

Do not assume our current local project uses a corporate proxy.

---

# SECTION 1641 — PROXY BYPASS / NO_PROXY

## 1815.

Some local/internal hosts should not pass through external proxy.

Concept:

```text
NO_PROXY
```

may include:

```text
localhost
127.0.0.1
internal domains
```

depending on environment.

Incorrect proxy routing can break local services.

---

# SECTION 1642 — CERTIFICATE ERROR CLASSIFICATION

## 1816.

Examples:

```text
certificate expired
hostname mismatch
unknown CA
incomplete chain
```

All are TLS problems, but root causes differ.

Read exact certificate error.

---

# SECTION 1643 — LOG LEVELS

## 1817.

Common:

```text
TRACE
DEBUG
INFO
WARN
ERROR
```

During troubleshooting:

```text
increase logging intentionally
```

not permanently everywhere.

Excessive logging can:

```text
slow system
hide useful signals
leak data
```

---

# SECTION 1644 — DEBUG LOG SECRET RISK

## 1818.

HTTP debug logs may contain:

```text
Authorization
cookies
passwords
tokens
personal data
```

Our API automation uses sanitization for Allure attachments.

Apply same security mindset to every troubleshooting output.

---

# SECTION 1645 — CORRELATION ID

## 1819.

In distributed systems, request/correlation IDs help trace:

```text
client request
gateway
service
downstream service
logs
```

When available, include correlation ID in defect evidence.

---

# SECTION 1646 — TIMESTAMP

## 1820.

Always capture:

```text
exact failure time
timezone
```

This helps correlate:

```text
test log
application log
DB log
infrastructure log
```

---

# SECTION 1647 — MINIMAL REPRODUCTION

## 1821.

If complex automation fails:

Reduce:

```text
full suite
↓
single test
↓
single request
↓
curl/manual call
```

This isolates layers.

---

# SECTION 1648 — BINARY SEARCH MINDSET

## 1822.

Troubleshooting is often:

```text
Does backend start?
YES

Does endpoint work via curl?
YES

Does Swagger work?
YES

Does REST Assured request work?
NO
```

Now problem scope becomes:

```text
automation/request configuration
```

Instead of whole system.

---

# SECTION 1649 — CHANGE ONE VARIABLE AT A TIME

## 1823.

Bad:

```text
change Java
change Maven
change dependency
delete cache
restart Docker
change config
```

all together.

If issue disappears:

```text
you don't know why
```

Better:

```text
one hypothesis
one controlled change
one observation
```

---

# SECTION 1650 — KEEP BEFORE/AFTER EVIDENCE

## 1824.

For configuration/toolchain change capture:

```text
version before
error before
change made
version after
result after
```

This creates reproducible engineering knowledge.

---

# SECTION 1651 — REPRODUCIBILITY

## 1825.

A good defect/build failure report should let another engineer reproduce it.

Include:

```text
commit
environment
tool versions
command
expected behavior
actual error
relevant logs
```

without secrets.

---

# SECTION 1652 — "WORKS ON MY MACHINE"

## 1826.

This statement is not enough.

Ask:

```text
Which machine state differs?
```

Compare:

```text
source
runtime
dependency
configuration
data
network
OS
architecture
```

---

# SECTION 1653 — ENVIRONMENT FINGERPRINT

## 1827.

Useful non-sensitive fingerprint:

```text
Git commit
Java version
Maven version
OS
architecture
target environment
base URL
Docker version
```

This can make CI troubleshooting much faster.

---

# SECTION 1654 — CURRENT PROJECT TOOLCHAIN FINGERPRINT

## 1828.

Current verified project context includes:

```text
Java 17
Maven
Spring Boot backend
PostgreSQL 16 container
REST Assured
TestNG
Allure
Swagger/OpenAPI
Docker / Docker Compose
```

Backend has Maven Wrapper.

API automation local execution uses Maven through its local test script.

---

# SECTION 1655 — NODE TOOLCHAIN LATER

## 1829.

Future frontend will add:

```text
Node.js
npm
React
TypeScript
Vite
```

Then troubleshooting will also include:

```text
Node version
npm version
package-lock
node_modules
Vite config
frontend environment variables
```

We will apply this when frontend development begins.

---

# SECTION 1656 — PACKAGE LOCK IMPORTANCE

## 1830.

For npm-based projects, lockfiles help capture exact dependency resolution.

Example:

```text
package-lock.json
```

This improves reproducibility across:

```text
developer machines
CI
```

Do not delete lockfile casually to solve dependency issues.

---

# SECTION 1657 — `npm install` VS `npm ci`

## 1831.

Conceptually:

```text
npm install
```

can resolve/update dependency tree according to package configuration.

```text
npm ci
```

is designed for clean, reproducible installation based on lockfile and is commonly useful in CI.

We will validate exact usage when frontend exists.

---

# SECTION 1658 — `node_modules` IS GENERATED

## 1832.

`node_modules` contains installed JavaScript dependencies.

It is typically:

```text
generated
large
not committed
```

Dependencies are reconstructed using:

```text
package.json
lockfile
```

---

# SECTION 1659 — `target/` IS GENERATED

## 1833.

Maven:

```text
target/
```

contains build output.

It should generally not be treated as source code.

If strange stale build behavior occurs:

```bash
mvn clean
```

is safer than manually modifying generated class files.

---

# SECTION 1660 — GENERATED FILE PRINCIPLE

## 1834.

Do not debug generated artifacts as if they are source of truth.

Source of truth should generally be:

```text
source code
build config
dependency definitions
environment config
```

Generated outputs can be recreated.

---

# SECTION 1661 — GIT STATUS BEFORE TROUBLESHOOTING

## 1835.

Run:

```bash
git status
```

Why?

Because unexpected local modifications can explain:

```text
works only locally
different behavior
failed build
```

Always know what code you're actually testing.

---

# SECTION 1662 — VERIFY COMMIT

## 1836.

```bash
git log -1 --oneline
```

helps identify current commit.

In team/CI debugging:

```text
same branch name
```

does not guarantee:

```text
same commit
```

Compare commit SHA.

---

# SECTION 1663 — STALE BRANCH

## 1837.

A local branch can be behind remote.

Before concluding environment issue:

```text
verify branch/commit state
```

Use:

```bash
git status
```

```bash
git log --oneline --decorate -n 5
```

and fetch/compare when needed.

---

# SECTION 1664 — UNCOMMITTED CHANGE

## 1838.

Local test may pass because of:

```text
uncommitted fix
```

CI fails because CI only sees committed code.

Always inspect:

```bash
git diff
```

---

# SECTION 1665 — CI USES COMMITTED STATE

## 1839.

CI generally checks out repository state.

It does not automatically include:

```text
your local uncommitted files
your local .env
your local .m2-only JAR
your IDE configuration
```

This explains many local-vs-CI differences.

---

# SECTION 1666 — BUILD ARTIFACT STALE

## 1840.

If source changed but old process still running:

```text
test may hit old application
```

Check:

```text
process
startup timestamp
deployed artifact
commit/version metadata
```

Restart/redeploy intentionally.

---

# SECTION 1667 — MULTIPLE BACKEND INSTANCES

## 1841.

Possible:

```text
one backend on 8080
another on 8081
```

Automation points to wrong one.

Check:

```bash
lsof -i :8080
```

and effective base URL.

---

# SECTION 1668 — WRONG DATABASE INSTANCE

## 1842.

Application may connect to:

```text
different PostgreSQL instance
```

than tester expects.

Symptoms:

```text
API record exists
manual SQL can't find it
```

Check:

```text
DB host
port
database name
environment
```

without exposing credentials.

---

# SECTION 1669 — WRONG SCHEMA

## 1843.

Same database server can contain:

```text
multiple databases/schemas
```

Querying wrong one creates false conclusions.

Always verify database context.

---

# SECTION 1670 — DATABASE MIGRATION / SCHEMA DRIFT

## 1844.

Application expects:

```text
new column
```

environment has:

```text
old schema
```

Potential:

```text
SQL errors
startup failure
500
```

In production-grade systems schema migrations should be controlled and traceable.

Our current learning project uses JPA schema behavior locally; future production design may evolve.

---

# SECTION 1671 — `ddl-auto=update` LIMITATION

## 1845.

Convenient for local learning/development:

```text
Hibernate updates schema
```

But production systems commonly prefer explicit migration tooling because migrations need:

```text
review
versioning
rollback planning
traceability
```

Later we can consider:

```text
Flyway
Liquibase
```

if it adds portfolio value.

Do not add now just for tool count.

---

# SECTION 1672 — EXTERNAL SERVICE FAILURE

## 1846.

If application calls external service:

```text
our code may be healthy
downstream may fail
```

Possible response:

```text
500
502
503
504
```

depending on architecture.

Identify dependency boundary.

---

# SECTION 1673 — MOCK VS REAL SERVICE

## 1847.

Testing strategy can use:

```text
mock
stub
sandbox
real integration environment
```

Each validates different risk.

Our current payment implementation is intentionally mock payment functionality.

Do not describe it as a real payment gateway integration.

---

# SECTION 1674 — MOCK SERVICE TROUBLESHOOTING

## 1848.

Even mock flow can fail due to:

```text
state
validation
order ownership
duplicate payment
cancelled order
```

These are business-rule failures, not network gateway failures.

---

# SECTION 1675 — AUTHENTICATION VS AUTHORIZATION VS BUSINESS RULE

## 1849.

Example:

```text
No token
→ authentication

USER calls admin endpoint
→ authorization

User tries duplicate payment
→ business rule
```

Classify correctly.

---

# SECTION 1676 — TEST ASSERTION FAILURE

## 1850.

Example:

```text
Expected 201
Actual 200
```

Could mean:

```text
application contract changed
test expectation wrong
wrong endpoint/environment
```

Do not automatically label application bug.

Check API contract/ticket first.

---

# SECTION 1677 — FALSE POSITIVE

## 1851.

Test passes even though feature is broken.

Possible:

```text
weak assertion
wrong environment
asserting only status code
test data misses bug
test didn't execute intended path
```

Pass result itself must be trustworthy.

---

# SECTION 1678 — FALSE NEGATIVE

## 1852.

Test fails even though application is correct.

Possible:

```text
bad locator
wrong assertion
stale data
environment issue
timing issue
automation bug
```

Senior SDET reduces both:

```text
false positives
false negatives
```

---

# SECTION 1679 — STATUS CODE ONLY IS NOT ENOUGH

## 1853.

Example:

```text
POST /orders → 201
```

Also validate as appropriate:

```text
response schema
business fields
DB state
stock
cart state
ownership
```

Our project intentionally combines API and DB validation for important workflows.

---

# SECTION 1680 — FAILURE EVIDENCE PACKAGE

## 1854.

For API failure capture:

```text
test name
environment
timestamp
request method/path
sanitized headers
sanitized body
response status
response body
correlation ID if available
relevant DB evidence
```

Never expose secrets.

---

# SECTION 1681 — SCREENSHOT IS NOT ENOUGH

## 1855.

For UI failure screenshot helps.

But also capture:

```text
URL
timestamp
browser/device
console/network if relevant
test data
steps
logs
```

Screenshot alone may not reveal root cause.

---

# SECTION 1682 — LOG SEARCH

## 1856.

Useful:

```bash
grep -i "error" application.log
```

```bash
grep -i "exception" application.log
```

But do not search only "error".

Root cause may appear under:

```text
Caused by
WARN
specific exception name
```

---

# SECTION 1683 — LIVE LOG MONITORING

## 1857.

```bash
tail -f application.log
```

Useful:

```text
start monitoring
reproduce failure
observe exact log timing
```

Stop:

```text
Ctrl+C
```

---

# SECTION 1684 — LARGE LOG FILE

## 1858.

Use:

```bash
less application.log
```

Search inside `less`:

```text
/error
```

or specific request ID.

Avoid opening huge logs in editor unnecessarily.

---

# SECTION 1685 — DISK SPACE

## 1859.

Build/container can fail if disk full.

Check:

```bash
df -h
```

Inspect directories:

```bash
du -sh *
```

Potential sources:

```text
Docker images
build artifacts
logs
node_modules
Maven cache
```

Do not randomly delete until you know what consumes space.

---

# SECTION 1686 — MEMORY PRESSURE

## 1860.

Potential symptoms:

```text
OutOfMemoryError
process killed
container exit
slow build
```

Investigate:

```text
JVM memory
container limits
host memory
test parallelism
```

Do not blindly increase heap before finding cause.

---

# SECTION 1687 — CPU PRESSURE

## 1861.

Heavy parallel tests/builds can consume CPU.

Symptoms:

```text
slow CI
timeouts
browser instability
```

More parallelism is not always faster.

---

# SECTION 1688 — PROCESS TROUBLESHOOTING

## 1862.

```bash
ps aux
```

Search:

```bash
ps aux | grep java
```

or:

```bash
pgrep -fl java
```

Useful to identify running Java processes.

---

# SECTION 1689 — KILL PROCESS SAFELY

## 1863.

Normal termination:

```bash
kill <PID>
```

Use force:

```bash
kill -9 <PID>
```

only when necessary.

Do not use `kill -9` as first response.

---

# SECTION 1690 — PROCESS EXIT CODE

## 1864.

After command:

```bash
echo $?
```

Typical:

```text
0 → success
non-zero → failure
```

CI systems use exit codes to determine step success/failure.

---

# SECTION 1691 — SCRIPT EXIT CODE

## 1865.

A script that hides failing command and exits 0 can create:

```text
false green pipeline
```

Shell scripts should propagate meaningful failures.

---

# SECTION 1692 — `set -e`

## 1866.

In Bash:

```bash
set -e
```

can make script exit when certain commands fail.

Useful in CI scripts.

But shell behavior has nuances.

Do not treat it as complete error handling.

---

# SECTION 1693 — `set -u`

## 1867.

```bash
set -u
```

can treat use of unset variables as errors.

Useful for catching configuration mistakes.

But existing scripts must be compatible.

---

# SECTION 1694 — `set -o pipefail`

## 1868.

Normally pipeline behavior can hide failure in earlier commands.

Example:

```bash
command1 | command2
```

With:

```bash
set -o pipefail
```

pipeline can reflect failures from commands within the pipe.

Useful in robust CI shell scripts.

---

# SECTION 1695 — ROBUST SHELL HEADER

## 1869.

Common pattern:

```bash
set -euo pipefail
```

This can improve shell script failure handling.

But understand each option before adding it to existing scripts because behavior may change.

---

# SECTION 1696 — SHELL DEBUG MODE

## 1870.

```bash
bash -x script.sh
```

shows command execution.

Very useful for:

```text
script flow
variable expansion
working directory
```

Security warning:

```text
can expose secrets
```

Never paste raw `bash -x` output publicly without sanitization.

---

# SECTION 1697 — CI DEBUGGING PRINCIPLE

## 1871.

Do not add:

```bash
env
```

to CI just to "see everything."

That may expose secrets.

Instead print safe metadata:

```text
Java version
Maven version
working directory
non-sensitive environment name
file listing
```

---

# SECTION 1698 — SAFE CI DIAGNOSTICS

## 1872.

Examples:

```bash
pwd
```

```bash
java -version
```

```bash
mvn -version
```

```bash
git log -1 --oneline
```

```bash
ls -la
```

Use only what is needed.

---

# SECTION 1699 — BUILD FAILURE DECISION TREE

## 1873.

```text
BUILD FAILED
    ↓
Did tool start?
    |
    ├── NO
    │    ↓
    │ Installation/PATH/permission
    │
    └── YES
         ↓
Dependency resolution?
         |
         ├── YES
         │    ↓
         │ Repository/network/TLS/auth/GAV
         │
         └── NO
              ↓
Compilation?
              |
              ├── YES
              │    ↓
              │ Source/JDK/classpath
              │
              └── NO
                   ↓
Tests?
                   |
                   ├── YES
                   │    ↓
                   │ Test/application/data/environment
                   │
                   └── NO
                        ↓
Packaging/plugin/runtime
```

---

# SECTION 1700 — APPLICATION STARTUP DECISION TREE

## 1874.

```text
APP DOES NOT START
      ↓
Java process launches?
      |
      ├── NO
      │    ↓
      │ JDK/command/JAR
      │
      └── YES
           ↓
Spring context fails?
           |
           ├── YES
           │    ↓
           │ Read nested cause
           │
           └── NO
                ↓
DB connection?
                |
                ├── FAIL
                │    ↓
                │ DB/container/config
                │
                └── OK
                     ↓
Port binding?
                     |
                     ├── FAIL
                     │    ↓
                     │ Port conflict
                     │
                     └── OK
                          ↓
Ready
```

---

# SECTION 1701 — API FAILURE DECISION TREE

## 1875.

```text
API TEST FAILS
    ↓
Can host resolve?
    |
    ├── NO → DNS
    |
    └── YES
         ↓
Can port connect?
         |
         ├── NO → service/port/network
         |
         └── YES
              ↓
HTTP response?
              |
              ├── 401 → authentication
              ├── 403 → authorization
              ├── 404 → path/environment
              ├── 4xx → request/business contract
              ├── 5xx → server/downstream
              └── expected status
                    ↓
              Assertion/data/test logic
```

---

# SECTION 1702 — DATABASE FAILURE DECISION TREE

## 1876.

```text
DB ISSUE
  ↓
Container/process running?
  |
  ├── NO → start/debug DB
  |
  └── YES
       ↓
Port reachable?
       |
       ├── NO → mapping/network
       |
       └── YES
            ↓
Authentication?
            |
            ├── FAIL → credentials/config
            |
            └── OK
                 ↓
Correct DB/schema?
                 |
                 ├── NO → environment/config
                 |
                 └── YES
                      ↓
Data/schema/query issue
```

---

# SECTION 1703 — LOCAL VS CI DECISION TREE

## 1877.

```text
LOCAL PASS
CI FAIL
   ↓
Same commit?
   ↓
Same Java/Maven/Node?
   ↓
Same environment variables?
   ↓
Same repository access?
   ↓
Same network?
   ↓
Same DB/service availability?
   ↓
Same OS/architecture?
   ↓
Same test selection?
```

---

# SECTION 1704 — FLAKY TEST DECISION TREE

## 1878.

```text
INTERMITTENT FAILURE
       ↓
Same test data?
       ↓
Timing?
       ↓
Network?
       ↓
External dependency?
       ↓
Parallel execution?
       ↓
Shared mutable state?
       ↓
Application race?
       ↓
Test bug?
```

Only after root cause is understood decide:

```text
fix
wait strategy
isolation
bounded retry
```

---

# SECTION 1705 — INTERVIEW: HOW DO YOU TROUBLESHOOT A FAILED BUILD?

## 1879.

> "I first identify the exact phase that failed — dependency resolution, compilation, test execution, packaging or runtime startup. Then I read the first meaningful error and nested cause, verify the toolchain versions and environment, and isolate the failure before changing anything. I prefer evidence-driven troubleshooting and change one variable at a time."

---

# SECTION 1706 — INTERVIEW: WORKS LOCALLY BUT FAILS IN CI

## 1880.

> "I compare the exact Git commit, JDK and build-tool versions, environment variables, secrets, repository access, network, operating system, architecture and test selection. I also check for hidden local state such as cached dependencies, uncommitted files or manually installed artifacts. The goal is to identify what makes the local environment different from the clean CI environment."

---

# SECTION 1707 — INTERVIEW: APPLICATION CANNOT START

## 1881.

> "I separate build success from startup success. If the application builds but does not start, I inspect the Spring Boot startup logs and nested cause, then verify configuration, required environment variables, database availability and port conflicts. I avoid changing business code until the failing runtime layer is identified."

---

# SECTION 1708 — INTERVIEW: API AUTOMATION GETS CONNECTION REFUSED

## 1882.

> "Connection refused means I have not reached the HTTP application layer yet. I verify the effective base URL, confirm the service is running and check whether anything is listening on the target port. Only after connectivity is established do I investigate authentication or API assertions."

---

# SECTION 1709 — INTERVIEW: 401 VS 403

## 1883.

> "I treat 401 as an authentication problem and 403 as an authorization problem. For 401 I check token presence, validity, expiry and environment. For 403 I check the authenticated user's role, authority and resource access policy."

---

# SECTION 1710 — INTERVIEW: HOW DO YOU HANDLE FLAKY TESTS?

## 1884.

> "I don't use retries as the first solution. I classify whether the intermittent failure comes from timing, test data, shared state, network, environment, an external dependency, application race conditions or the automation itself. Then I fix the underlying cause. I use bounded retries only when the failure is genuinely transient and the retry does not hide product defects."

---

# SECTION 1711 — INTERVIEW: ZERO TESTS BUT BUILD SUCCESS

## 1885.

> "I would not consider that a successful regression. I verify test discovery, suite configuration, groups or tags and expected test count. A green build with zero tests can be a false quality signal, so CI should detect unexpected drops in executed test count."

---

# SECTION 1712 — INTERVIEW: HOW DO YOU DEBUG DB FAILURE?

## 1886.

> "I first distinguish connectivity from data issues. I verify the database process or container, port, target database and configuration without exposing credentials. If connectivity works, I inspect schema and test data state, because stale data or cleanup failures can produce test failures even when the database itself is healthy."

---

# SECTION 1713 — INTERVIEW: IDE WORKS, TERMINAL FAILS

## 1887.

> "I compare the JDK, Maven version, working directory, environment variables and run configuration. IDEs can inject configuration or use a different runtime, so I verify the actual command-line environment rather than assuming both execution paths are equivalent."

---

# SECTION 1714 — INTERVIEW: HOW DO YOU DEBUG 500?

## 1888.

> "I capture the sanitized request and response, timestamp and correlation ID if available, then inspect server logs and downstream dependencies. A 500 confirms that the request reached the server, so I focus on application or dependency failures rather than DNS or basic connectivity."

---

# SECTION 1715 — INTERVIEW: WHAT EVIDENCE DO YOU COLLECT?

## 1889.

> "I collect the environment, Git commit, test name, timestamp, tool versions when relevant, sanitized request and response, server logs, correlation ID and database evidence where appropriate. I capture enough information to reproduce and trace the failure without leaking credentials or tokens."

---

# SECTION 1716 — INTERVIEW: WHY IS ENVIRONMENT SWITCHING IMPORTANT?

## 1890.

> "The test framework should not hardcode one environment. I externalize the base URL and environment-specific configuration so the same automation code can target local, QA or stage. I also fail clearly when required environment configuration is missing rather than silently testing the wrong system."

---

# SECTION 1717 — INTERVIEW: WHY IS CI VALUABLE BEYOND AUTOMATION?

## 1891.

> "CI gives a clean and repeatable execution environment. It exposes hidden developer-machine assumptions such as local artifacts, uncommitted code, IDE configuration or missing secrets. That makes CI an important build-quality and reproducibility control, not just a place to run tests."

---

# SECTION 1718 — INTERVIEW: DOCKER CONTAINER RUNNING BUT API FAILS

## 1892.

> "A running container only proves that the container process is alive. I verify application readiness, logs, port mapping, downstream dependencies and an application-level endpoint. Process status and functional readiness are different checks."

---

# SECTION 1719 — INTERVIEW: WHY NOT PRINT ENV IN CI?

## 1893.

> "A full environment dump can expose tokens, passwords and repository credentials. I print only safe diagnostic metadata such as runtime versions, working directory, environment name and commit, and I use presence checks for sensitive variables."

---

# SECTION 1720 — INTERVIEW: SENIOR TROUBLESHOOTING STYLE

## 1894.

> "My troubleshooting approach is evidence first. I classify the failing layer, reduce the problem to the smallest reproducible case, compare working and failing environments, change one variable at a time and verify the fix with targeted tests followed by appropriate regression."

---

# SECTION 1721 — REAL PROJECT EXAMPLE: ENVIRONMENT SWITCHING

## 1895.

Our API automation supports:

```text
local
qa
stage
```

with environment-aware base URL handling.

Senior explanation:

> "I separated test logic from environment configuration so the same API suite can execute against different environments. This reduces duplication and prevents hardcoded endpoint configuration from spreading across tests."

---

# SECTION 1722 — REAL PROJECT EXAMPLE: SECRET HANDLING

## 1896.

Current approach:

```text
real .env
→ ignored by Git

.env.example
→ placeholders only

local script
→ loads environment

Allure
→ sanitized attachments
```

Senior explanation:

> "I treat test credentials and JWTs as runtime configuration rather than source code and also sanitize reporting, because secret protection must cover both Git history and generated test evidence."

---

# SECTION 1723 — REAL PROJECT EXAMPLE: RBAC FAILURE CLASSIFICATION

## 1897.

Expected:

```text
USER
POST product
→ 403

ADMIN
POST product
→ 201
```

This proves:

```text
403 is not always a defect
```

Correct interpretation depends on:

```text
role
endpoint policy
expected contract
```

---

# SECTION 1724 — REAL PROJECT EXAMPLE: API + DB

## 1898.

For workflows such as:

```text
cart
orders
payment
```

API response alone may not be enough.

DB validation helps verify:

```text
persistence
state transition
stock changes
ownership
```

This improves root-cause isolation.

---

# SECTION 1725 — REAL PROJECT EXAMPLE: FINAL REGRESSION

## 1899.

Current verified API automation milestone:

```text
48 tests
48 passed
```

Senior mindset:

```text
Do not only check:
48 passed

Also check:
48 expected
48 executed
0 failed
correct environment
correct build
```

---

# SECTION 1726 — TROUBLESHOOTING COMMAND CHEAT SHEET

## 1900. Git

```bash
git status
git diff
git log -1 --oneline
```

## 1901. Java

```bash
java -version
javac -version
echo "$JAVA_HOME"
which java
```

## 1902. Maven

```bash
mvn -version
mvn clean test
mvn -e test
mvn dependency:tree
mvn help:effective-pom
```

## 1903. Process / Port

```bash
ps aux
pgrep -fl java
lsof -i :8080
kill <PID>
```

## 1904. Network

```bash
curl -i http://localhost:8080/v3/api-docs
nslookup <hostname>
dig <hostname>
```

## 1905. Docker

```bash
docker ps
docker ps -a
docker compose ps
docker compose logs postgres
```

## 1906. System

```bash
pwd
uname -m
df -h
date
```

---

# SECTION 1727 — 30-SECOND TROUBLESHOOTING ANSWER

## 1907.

> "When something fails, I first identify whether it is a source, build, dependency, runtime, environment, network, database or test issue. I verify the exact error and environment, isolate the smallest failing layer and compare it with a working case. I avoid random configuration changes and retries, and once the root cause is fixed I run targeted validation followed by appropriate regression."

---

# SECTION 1728 — FIVE QUESTIONS BEFORE TOUCHING CODE

## 1908.

Ask:

```text
1. What exactly failed?
2. Where did it fail?
3. What changed?
4. Can I reproduce it?
5. What evidence separates app failure from environment/test failure?
```

Only then:

```text
change code/configuration
```

---

# SECTION 1729 — FIVE QUESTIONS FOR LOCAL VS CI

## 1909.

```text
Same commit?
Same runtime?
Same configuration?
Same dependencies?
Same target environment?
```

Then investigate:

```text
network
OS
architecture
data
```

---

# SECTION 1730 — FIVE QUESTIONS FOR API FAILURE

## 1910.

```text
Correct environment?
Service reachable?
Correct endpoint/method?
Authentication/authorization correct?
Request and expected contract correct?
```

---

# SECTION 1731 — FIVE QUESTIONS FOR DATABASE FAILURE

## 1911.

```text
DB running?
Correct host/port?
Correct database?
Credentials loaded?
Expected data/schema?
```

---

# SECTION 1732 — FIVE QUESTIONS FOR FLAKY TEST

## 1912.

```text
Timing?
Shared data?
Parallelism?
External dependency?
Application or automation race?
```

---

# SECTION 1733 — ANTI-PATTERN: RANDOM RESTART

## 1913.

Bad:

```text
restart IDE
restart Docker
restart laptop
rerun tests
```

without evidence.

Restart can temporarily hide issue.

If restart fixes it, still ask:

```text
What state was reset?
```

---

# SECTION 1734 — ANTI-PATTERN: DELETE EVERYTHING

## 1914.

Bad:

```text
delete target
delete .m2
delete Docker volumes
delete node_modules
reclone repo
```

all together.

You lose:

```text
evidence
time
root-cause understanding
```

Use targeted cleanup.

---

# SECTION 1735 — ANTI-PATTERN: DISABLE SECURITY

## 1915.

Bad permanent fixes:

```text
disable TLS verification
permit all endpoints
hardcode credentials
turn off certificate validation
```

A troubleshooting workaround must never silently become architecture.

---

# SECTION 1736 — ANTI-PATTERN: LOG SECRETS

## 1916.

Never solve auth issue by publicly printing:

```text
password
JWT
API key
repository token
```

Verify presence/metadata safely.

---

# SECTION 1737 — ANTI-PATTERN: RETRY EVERYTHING

## 1917.

Retrying:

```text
400
401
403
validation failure
deterministic assertion
```

usually adds noise.

Retry should correspond to:

```text
known transient condition
```

---

# SECTION 1738 — ANTI-PATTERN: BLAME CI

## 1918.

"CI issue" is not root cause.

Better:

```text
CI runner uses Java X while local uses Java Y
```

or:

```text
CI cannot authenticate to repository
```

or:

```text
required environment variable is absent
```

Be precise.

---

# SECTION 1739 — ANTI-PATTERN: BLAME ENVIRONMENT

## 1919.

"Environment issue" is too broad.

Specify:

```text
QA database contains stale order state
```

or:

```text
stage base URL resolves but upstream returns 503
```

or:

```text
local backend is not listening on 8080
```

---

# SECTION 1740 — ANTI-PATTERN: BLAME AUTOMATION

## 1920.

"Automation issue" is also too broad.

Specify:

```text
test reused expired token
```

or:

```text
request spec points to wrong environment
```

or:

```text
cleanup leaves persistent record
```

---

# SECTION 1741 — SENIOR SDET LANGUAGE

## 1921.

Junior-style:

```text
API is not working.
```

Senior-style:

```text
The service is reachable and returns HTTP 403 only for ROLE_USER
on the product creation endpoint, while ROLE_ADMIN succeeds,
which matches the configured RBAC policy.
```

---

# SECTION 1742 — SENIOR SDET LANGUAGE: NETWORK

## 1922.

Instead of:

```text
Server is down.
```

Say:

```text
The hostname resolves, but TCP connection to port 8080 is refused,
and no process is listening on that port.
```

---

# SECTION 1743 — SENIOR SDET LANGUAGE: DATABASE

## 1923.

Instead of:

```text
DB issue.
```

Say:

```text
PostgreSQL is running and reachable, but the application is using
a different database configuration than the one being inspected.
```

---

# SECTION 1744 — SENIOR SDET LANGUAGE: CI

## 1924.

Instead of:

```text
Works locally, Jenkins/GitHub Actions issue.
```

Say:

```text
The same commit passes locally, but the CI environment does not
receive the required runtime configuration, so application startup
fails before test execution.
```

---

# SECTION 1745 — SENIOR SDET LANGUAGE: DEPENDENCY

## 1925.

Instead of:

```text
Maven issue.
```

Say:

```text
Maven starts correctly, but dependency resolution fails because the
configured repository cannot provide the requested artifact version.
```

---

# SECTION 1746 — ROOT CAUSE VS SYMPTOM

## 1926.

Example:

```text
Symptom:
API test gets connection refused.

Immediate cause:
Nothing listening on 8080.

Root cause:
Backend startup failed because DB configuration was missing.
```

Senior engineers continue until they find actionable root cause.

---

# SECTION 1747 — CONTRIBUTING FACTOR

## 1927.

Sometimes failure has multiple factors.

Example:

```text
Primary root cause:
token expired

Contributing factor:
suite stores one token for a very long execution
```

Fix may involve:

```text
token lifecycle design
```

not only regenerating token manually.

---

# SECTION 1748 — VERIFY THE FIX

## 1928.

After fix:

```text
reproduce original failing scenario
```

Then:

```text
targeted nearby tests
```

Then:

```text
appropriate regression
```

Do not declare fixed only because:

```text
error disappeared once
```

---

# SECTION 1749 — REGRESSION SCOPE

## 1929.

Regression depends on change.

Examples:

```text
JWT fix
→ auth + RBAC + secured APIs

DB change
→ persistence + business flows

reporting change
→ test execution + attachments + sanitization

dependency upgrade
→ affected layer + full regression as needed
```

---

# SECTION 1750 — PREVENT RECURRENCE

## 1930.

After resolving recurring issue, ask:

```text
Can CI catch this?
Can startup fail more clearly?
Can validation detect missing config?
Can documentation prevent it?
Can test data be isolated?
Can monitoring expose it?
```

Senior troubleshooting includes prevention.

---

# SECTION 1751 — TROUBLESHOOTING DOCUMENTATION

## 1931.

Useful runbook structure:

```text
Symptom
Likely causes
Diagnostic commands
Expected output
Safe fix
Verification
Escalation
```

This converts individual knowledge into team knowledge.

---

# SECTION 1752 — ESCALATION

## 1932.

Escalate with evidence.

Bad:

```text
Please check server.
```

Better:

```text
At 10:32 IST, stage returned 503 for three authenticated requests.
DNS and TLS succeeded. The same endpoint returned 200 earlier.
Request IDs are X/Y/Z. No client-side request change was observed.
```

This reduces investigation time.

---

# SECTION 1753 — WHEN TO STOP DEBUGGING

## 1933.

You do not need to personally fix every layer.

SDET responsibility can be:

```text
identify
isolate
provide evidence
route correctly
verify fix
```

Example:

```text
certificate chain problem
```

may need platform/security team.

But SDET should still explain why it is not an API assertion problem.

---

# SECTION 1754 — TOOLCHAIN OWNERSHIP MINDSET

## 1934.

Senior SDET should be comfortable moving across:

```text
Git
Shell
JDK
Maven
HTTP
Docker
DB
Test framework
CI
```

Not necessarily as specialist in every tool.

Goal:

```text
understand boundaries
trace failures
communicate precisely
```

---

# SECTION 1755 — COMPLETE TROUBLESHOOTING MENTAL MAP

## 1935.

```text
FAILURE
   ↓
WHAT CHANGED?
   ↓
WHICH LAYER?
   ↓
CAN I REPRODUCE?
   ↓
WHAT IS WORKING?
   ↓
WHAT IS NOT WORKING?
   ↓
SMALLEST FAILING BOUNDARY
   ↓
HYPOTHESIS
   ↓
ONE CONTROLLED CHANGE
   ↓
VERIFY
   ↓
REGRESSION
   ↓
PREVENT RECURRENCE
```

---

# SECTION 1756 — RAPID REVISION

## 1936. Build fails before Maven starts?

```text
Tool/PATH/permission
```

## 1937. Maven cannot find dependency?

```text
GAV/repository/resolution
```

## 1938. `cannot find symbol`?

```text
Compilation/source/classpath
```

## 1939. Spring Boot build passes but app fails?

```text
Runtime/startup/config
```

## 1940. Connection refused?

```text
No service listening / port issue
```

## 1941. UnknownHost?

```text
DNS
```

## 1942. 401?

```text
Authentication
```

## 1943. 403?

```text
Authorization
```

## 1944. 500?

```text
Server-side failure
```

## 1945. Local passes, CI fails?

```text
Compare environment/toolchain/commit/config
```

---

# SECTION 1757 — RAPID REVISION: DOCKER

## 1946. Running containers?

```bash
docker ps
```

## 1947. All containers?

```bash
docker ps -a
```

## 1948. Compose status?

```bash
docker compose ps
```

## 1949. PostgreSQL logs?

```bash
docker compose logs postgres
```

## 1950. Remove containers/network?

```bash
docker compose down
```

## 1951. Remove volumes too?

```bash
docker compose down -v
```

Danger:

```text
persistent data may be deleted
```

---

# SECTION 1758 — RAPID REVISION: JAVA/MAVEN

## 1952.

```bash
java -version
```

→ Java runtime.

```bash
javac -version
```

→ Java compiler.

```bash
mvn -version
```

→ Maven + Java used by Maven.

```bash
./mvnw clean test
```

→ backend clean test using project Maven Wrapper.

---

# SECTION 1759 — RAPID REVISION: NETWORK

## 1953.

```bash
lsof -i :8080
```

→ who is listening on port 8080?

```bash
curl -i http://localhost:8080/v3/api-docs
```

→ HTTP/API connectivity.

```bash
nslookup <hostname>
```

→ DNS.

---

# SECTION 1760 — RAPID REVISION: ENVIRONMENT

## 1954.

```bash
printenv TEST_ENV
```

→ inspect non-sensitive environment variable.

```bash
pwd
```

→ working directory.

```bash
which java
```

→ resolved executable.

```bash
echo "$PATH"
```

→ command search path.

---

# SECTION 1761 — RAPID REVISION: GIT

## 1955.

```bash
git status
```

→ local state.

```bash
git diff
```

→ uncommitted changes.

```bash
git log -1 --oneline
```

→ current commit.

---

# SECTION 1762 — 10-MINUTE INTERVIEW REVISION

## 1956.

Remember this order:

```text
1. Exact error
2. Failure phase
3. Git commit
4. Runtime/tool versions
5. Environment/config
6. Network
7. Database/external dependency
8. Test logic/data
9. Root cause
10. Regression
```

---

# SECTION 1763 — ONE-MINUTE SENIOR SDET ANSWER

## 1957.

> "I troubleshoot by boundaries rather than guessing. I first classify whether the failure is in source compilation, dependency resolution, runtime startup, environment configuration, network, database, application behavior or the test framework. I verify the exact commit and toolchain, reduce the issue to the smallest reproducible case, compare working and failing environments, and change one variable at a time. I also make sure diagnostic logs are sanitized. Once the root cause is fixed, I run targeted validation and the appropriate regression, and for recurring issues I look for a CI check or framework improvement that can prevent recurrence."

---

# SECTION 1764 — PROJECT-SPECIFIC TROUBLESHOOTING FLOW

## 1958.

For our current project:

```text
Git
 ↓
Java 17
 ↓
Maven
 ↓
Spring Boot
 ↓
Environment Variables
 ↓
PostgreSQL Docker Container
 ↓
JWT / Security
 ↓
REST API
 ↓
REST Assured + TestNG
 ↓
DB Validation
 ↓
Allure
```

When test fails:

```text
find which arrow broke
```

That is the simplest way to think about the entire stack.

---

# SECTION 1765 — FINAL SENIOR PRINCIPLE

## 1959.

Do not become the engineer who knows only:

```text
how to rerun
```

Become the engineer who can explain:

```text
why it failed
where it failed
how it was isolated
how it was fixed
how the fix was verified
how recurrence can be prevented
```

Final troubleshooting loop:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
PROVE
   ↓
FIX
   ↓
VERIFY
   ↓
PREVENT
```

---

# END OF PART 9 — BUILD, ENVIRONMENT & TOOLCHAIN TROUBLESHOOTING FOR SENIOR SDET

Next:

**PART 10 — CI/CD TOOLING CONNECTION: GitHub Actions, Jenkins, Pipelines, Secrets, Artifacts, Quality Gates & SDET Strategy**

---

# PART 10 — CI/CD TOOLING CONNECTION FOR SENIOR SDET

# SECTION 1766 — WHAT IS CI/CD?

## 1960. CI

CI means:

```text
Continuous Integration
```

Developers frequently integrate code into a shared repository.

Every meaningful change can trigger automated checks such as:

```text
Checkout
   ↓
Build
   ↓
Unit Tests
   ↓
API Tests
   ↓
Quality Checks
   ↓
Reports
```

Goal:

```text
Detect problems early.
```

---

# SECTION 1767 — CD

## 1961.

CD can mean:

```text
Continuous Delivery
```

or:

```text
Continuous Deployment
```

They are related but different.

Continuous Delivery:

```text
Code is automatically prepared and validated
for release.

Production deployment can still require approval.
```

Continuous Deployment:

```text
Validated changes can automatically reach production.
```

---

# SECTION 1768 — CI VS CD

## 1962.

```text
CI
→ integrate + build + test

Continuous Delivery
→ keep software deployable

Continuous Deployment
→ automatically deploy validated software
```

Interview answer:

> "CI focuses on continuously validating integrated code, while CD extends the process toward reliable release and deployment."

---

# SECTION 1769 — WHY SDET NEEDS CI/CD

## 1963.

Without CI:

```text
Automation exists
but someone must remember to run it.
```

With CI:

```text
Code change
   ↓
Automatic validation
   ↓
Fast feedback
```

SDET responsibilities can include:

```text
test strategy
pipeline integration
test selection
quality gates
reporting
failure diagnosis
test stability
environment strategy
```

---

# SECTION 1770 — CI/CD IS NOT ONLY DEVOPS

## 1964.

DevOps/platform engineers may own infrastructure.

Developers may own application build.

SDET may own or contribute to:

```text
automated quality stages
test execution
test reports
quality gates
failure analysis
release confidence
```

Therefore:

```text
SDET + CI/CD knowledge
```

is extremely valuable.

---

# SECTION 1771 — BASIC PIPELINE

## 1965.

A simple pipeline:

```text
Developer Push
     ↓
Checkout
     ↓
Setup Runtime
     ↓
Install Dependencies
     ↓
Build
     ↓
Run Tests
     ↓
Generate Reports
     ↓
Publish Results
```

---

# SECTION 1772 — OUR FUTURE PROJECT PIPELINE

## 1966.

Future architecture can evolve toward:

```text
GitHub Push / Pull Request
        ↓
GitHub Actions
        ↓
Backend Build
        ↓
Backend Tests
        ↓
Start PostgreSQL
        ↓
Start Backend
        ↓
Wait for Readiness
        ↓
API Automation
        ↓
Allure Results
        ↓
Frontend Build
        ↓
Playwright Tests
        ↓
Quality Gate
```

Later:

```text
k6 Performance
AWS Deployment
Cloud Validation
```

These are roadmap items, not current completed implementation.

---

# SECTION 1773 — PIPELINE AS CODE

## 1967.

Modern CI pipelines are often defined as code.

Examples:

```text
GitHub Actions → YAML
Jenkins → Jenkinsfile / Pipeline
Azure DevOps → YAML
```

Benefits:

```text
version controlled
reviewable
repeatable
traceable
```

---

# SECTION 1774 — WHY VERSION-CONTROL PIPELINE CONFIG

## 1968.

Pipeline configuration stored with code means:

```text
pipeline change
can go through
Git review
```

This provides:

```text
history
ownership
rollback
code review
traceability
```

---

# SECTION 1775 — GITHUB ACTIONS

## 1969.

GitHub Actions is GitHub's automation platform.

It can run workflows based on events such as:

```text
push
pull request
manual trigger
schedule
release
```

---

# SECTION 1776 — WORKFLOW

## 1970.

A GitHub Actions workflow is normally a YAML file under:

```text
.github/workflows/
```

Example future file:

```text
.github/workflows/api-tests.yml
```

A repository can contain multiple workflows.

---

# SECTION 1777 — WORKFLOW STRUCTURE

## 1971.

High-level:

```yaml
name: API Tests

on:
  workflow_dispatch:

jobs:
  api-tests:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        # action configuration

      - name: Setup Java
        # action configuration

      - name: Run tests
        # command
```

This is conceptual structure.

We will write the actual workflow only when we implement CI in the project.

---

# SECTION 1778 — `name`

## 1972.

Example:

```yaml
name: API Tests
```

This gives the workflow a human-readable name.

Useful in GitHub UI.

---

# SECTION 1779 — `on`

## 1973.

`on` defines:

```text
when workflow should run
```

Examples:

```text
push
pull_request
workflow_dispatch
schedule
```

---

# SECTION 1780 — PUSH TRIGGER

## 1974.

Concept:

```yaml
on:
  push:
```

Meaning:

```text
workflow runs when matching push event occurs
```

Usually we narrow it using:

```text
branch
path
```

depending on strategy.

---

# SECTION 1781 — PULL REQUEST TRIGGER

## 1975.

Concept:

```yaml
on:
  pull_request:
```

Useful for:

```text
PR validation
```

Before merge, pipeline can verify:

```text
build
tests
quality checks
```

---

# SECTION 1782 — MANUAL TRIGGER

## 1976.

```yaml
on:
  workflow_dispatch:
```

Allows authorized users to manually start workflow from GitHub.

Useful for:

```text
on-demand regression
environment-specific execution
debugging
release validation
```

---

# SECTION 1783 — SCHEDULED TRIGGER

## 1977.

GitHub Actions supports cron-based schedules.

Concept:

```yaml
on:
  schedule:
    - cron: "..."
```

Useful for:

```text
nightly regression
periodic environment checks
```

Cron timing should be designed carefully, including timezone expectations.

---

# SECTION 1784 — EVENT-BASED VS SCHEDULED TESTING

## 1978.

PR:

```text
fast feedback
```

Nightly:

```text
broader regression
```

Release:

```text
release confidence
```

Do not necessarily run every expensive test on every commit.

---

# SECTION 1785 — JOB

## 1979.

Workflow contains one or more:

```text
jobs
```

Example:

```text
backend-build
api-tests
ui-tests
```

Jobs can:

```text
run independently
```

or:

```text
depend on each other
```

---

# SECTION 1786 — STEP

## 1980.

A job contains:

```text
steps
```

Example:

```text
Checkout
Setup Java
Run Maven
Upload Report
```

---

# SECTION 1787 — ACTION

## 1981.

A reusable GitHub Actions component is called an:

```text
action
```

Examples conceptually include actions for:

```text
checkout
runtime setup
artifact upload
```

Pin and review third-party actions carefully because CI workflows execute trusted automation with repository context.

---

# SECTION 1788 — RUNNER

## 1982.

Runner is the machine/environment executing a job.

Common hosted runner:

```text
ubuntu-latest
```

Other environments can include:

```text
Windows
macOS
self-hosted runners
```

---

# SECTION 1789 — HOSTED RUNNER

## 1983.

GitHub-hosted runner:

```text
temporary execution environment
```

Pipeline receives a relatively clean environment for each job.

This helps expose:

```text
hidden local dependencies
```

---

# SECTION 1790 — SELF-HOSTED RUNNER

## 1984.

Self-hosted runner is infrastructure managed by an organization.

Useful when needing:

```text
internal network
special hardware
custom software
private environments
```

But requires:

```text
security
maintenance
patching
capacity management
```

---

# SECTION 1791 — WHY CLEAN RUNNERS MATTER

## 1985.

Developer laptop may contain:

```text
cached dependency
manually installed tool
local .env
IDE config
old artifact
```

CI runner may not.

Therefore:

```text
CI failure can expose reproducibility problems.
```

---

# SECTION 1792 — CHECKOUT

## 1986.

First common pipeline step:

```text
checkout repository
```

Without checkout:

```text
runner does not automatically have project source
```

unless workflow/action explicitly obtains it.

---

# SECTION 1793 — SETUP JAVA

## 1987.

Our backend requires:

```text
Java 17
```

Future CI should explicitly configure Java 17.

Do not rely on whatever default Java happens to exist on runner.

---

# SECTION 1794 — WHY PIN RUNTIME VERSION

## 1988.

If local:

```text
Java 17
```

but CI unexpectedly changes runtime:

```text
build behavior can change
```

Therefore pipeline should define intended runtime.

---

# SECTION 1795 — MAVEN IN CI

## 1989.

Backend has Maven Wrapper.

Future backend CI can use:

```bash
./mvnw clean test
```

Benefits:

```text
project-defined Maven version
less dependence on globally installed Maven
```

---

# SECTION 1796 — API AUTOMATION IN CI

## 1990.

Current API automation local script runs Maven tests.

Future CI will need to:

```text
configure target environment
provide credentials securely
ensure target backend is available
run Maven tests
collect Allure results
```

---

# SECTION 1797 — BUILD JOB

## 1991.

Future backend build job could conceptually:

```text
Checkout
   ↓
Setup Java 17
   ↓
Load CI configuration
   ↓
Compile
   ↓
Run tests
   ↓
Package
```

Exact implementation will be decided when CI is added.

---

# SECTION 1798 — CI ENVIRONMENT VARIABLES

## 1992.

Pipeline can inject variables such as:

```text
TEST_ENV
BASE_URL
DB_URL
```

Sensitive values should come from secure secret storage rather than repository code.

---

# SECTION 1799 — CI SECRETS

## 1993.

Examples:

```text
DB_PASSWORD
JWT_SECRET
test account password
cloud credentials
```

should not be:

```text
hardcoded in YAML
committed in .env
printed in logs
```

---

# SECTION 1800 — GITHUB SECRETS CONCEPT

## 1994.

GitHub can store secrets separately from source code.

Workflow references the secret at runtime.

Concept:

```text
Repository Secret
      ↓
Workflow
      ↓
Environment Variable
      ↓
Application/Test
```

---

# SECTION 1801 — SECRET MASKING

## 1995.

CI systems may mask recognized secret values in logs.

But never rely only on masking.

Bad:

```text
print all secrets because CI will hide them
```

Correct:

```text
do not intentionally log secrets
```

---

# SECTION 1802 — OUR SECURITY PRINCIPLE

## 1996.

Current project already follows:

```text
.env
→ ignored

.env.example
→ placeholders

Allure
→ sanitized
```

CI should extend the same principle:

```text
Secrets Store
→ Runtime Injection
→ No Secret in Repository
→ No Secret in Reports
```

---

# SECTION 1803 — ENVIRONMENT-SPECIFIC CI

## 1997.

Example future flow:

```text
PR
→ local/ephemeral validation

Manual QA regression
→ QA_BASE_URL

Stage regression
→ STAGE_BASE_URL
```

The same test code should not require source-code changes to switch environments.

---

# SECTION 1804 — WORKFLOW INPUTS

## 1998.

Manual workflows can accept inputs conceptually such as:

```text
environment = qa
```

or:

```text
environment = stage
```

Then pipeline selects corresponding configuration.

This can make one workflow reusable.

---

# SECTION 1805 — NEVER ACCEPT ARBITRARY UNSAFE INPUT

## 1999.

Pipeline inputs should be:

```text
validated
restricted
understood
```

Do not allow arbitrary commands or sensitive configuration injection.

CI input is part of the security boundary.

---

# SECTION 1806 — SERVICE CONTAINERS

## 2000.

CI can provide service dependencies such as databases.

Future example:

```text
Job
├── PostgreSQL service
└── Backend build/test
```

This can provide isolated database state.

---

# SECTION 1807 — POSTGRESQL IN CI

## 2001.

Our local project uses:

```text
postgres:16
```

Future CI can use PostgreSQL 16 as a service/container so backend integration tests can run against a real PostgreSQL instance.

---

# SECTION 1808 — WHY REAL DB IN CI

## 2002.

Using PostgreSQL can validate:

```text
SQL behavior
constraints
persistence
repository integration
schema behavior
```

more realistically than replacing every DB interaction with mocks.

---

# SECTION 1809 — DATABASE READINESS

## 2003.

Starting PostgreSQL container does not guarantee:

```text
DB ready immediately
```

Pipeline should use:

```text
health/readiness checks
```

instead of arbitrary:

```text
sleep 20
```

---

# SECTION 1810 — BACKEND READINESS

## 2004.

Same principle:

```text
process started
≠
application ready
```

Future CI should wait until an appropriate backend endpoint responds.

---

# SECTION 1811 — BACKGROUND PROCESS

## 2005.

If CI starts backend for external API tests:

```text
backend must remain running
```

while API tests execute.

Pipeline design must manage:

```text
startup
logs
readiness
shutdown/cleanup
```

---

# SECTION 1812 — API TEST DEPENDENCY

## 2006.

API automation requires:

```text
reachable backend
```

Therefore:

```text
API tests
```

should not start until target application is ready.

---

# SECTION 1813 — JOB DEPENDENCY

## 2007.

GitHub Actions supports dependencies between jobs.

Concept:

```text
build
  ↓
api-tests
```

If build fails:

```text
dependent testing may not need to run
```

depending on pipeline design.

---

# SECTION 1814 — `needs`

## 2008.

Conceptually:

```yaml
api-tests:
  needs: backend-build
```

Meaning:

```text
api-tests depends on backend-build job
```

Exact YAML will be implemented later.

---

# SECTION 1815 — FAIL FAST

## 2009.

If compilation fails:

```text
do not waste time running integration tests
```

Pipeline should stop or skip irrelevant downstream stages.

This gives faster feedback.

---

# SECTION 1816 — BUT DON'T HIDE USEFUL FAILURES

## 2010.

Sometimes independent checks can run in parallel:

```text
backend static checks
frontend checks
documentation checks
```

One failure does not always require cancelling every unrelated check.

Pipeline design balances:

```text
speed
cost
diagnostic value
```

---

# SECTION 1817 — PARALLEL JOBS

## 2011.

Independent jobs can run concurrently.

Example future:

```text
Backend Build
        ↘
          API tests later

Frontend Build
        ↘
          UI tests later
```

Parallelism reduces pipeline duration when dependencies permit.

---

# SECTION 1818 — PARALLELISM RISKS

## 2012.

Parallel tests can cause:

```text
data collision
resource contention
rate limiting
shared environment instability
```

Do not increase parallelism blindly.

---

# SECTION 1819 — CACHE

## 2013.

CI can cache downloaded dependencies.

Examples:

```text
Maven dependencies
npm dependencies/cache
```

Benefit:

```text
faster builds
```

---

# SECTION 1820 — CACHE IS NOT SOURCE OF TRUTH

## 2014.

Cache should improve performance.

Pipeline should still be reproducible if cache is missing.

If build works only because of cache:

```text
dependency/repository configuration may be broken
```

---

# SECTION 1821 — BAD CACHE

## 2015.

Rarely cache can contribute to confusing behavior.

Troubleshooting:

```text
compare cached vs clean execution
```

But do not delete caches as first response to every failure.

---

# SECTION 1822 — ARTIFACT

## 2016.

Pipeline artifact is output preserved from a job.

Examples:

```text
JAR
test results
Allure results
screenshots
logs
coverage report
```

Artifacts help:

```text
debugging
traceability
handoff between jobs
```

---

# SECTION 1823 — BUILD ARTIFACT

## 2017.

Backend package output can be:

```text
JAR
```

Future flow:

```text
Build once
   ↓
Store artifact
   ↓
Deploy same artifact
```

This is preferable to rebuilding different code for each environment.

---

# SECTION 1824 — TEST ARTIFACT

## 2018.

Test artifacts can include:

```text
Allure results
JUnit XML
screenshots
videos
logs
```

depending on framework.

---

# SECTION 1825 — ALLURE IN CI

## 2019.

Our current API framework already produces:

```text
Allure results
```

Future CI can:

```text
run tests
   ↓
preserve Allure results
   ↓
generate/publish report
```

---

# SECTION 1826 — REPORT SHOULD SURVIVE FAILURE

## 2020.

Important:

```text
tests fail
```

is exactly when report is most valuable.

Report/artifact upload steps should be designed to run when appropriate even after test failures.

---

# SECTION 1827 — `if: always()` CONCEPT

## 2021.

GitHub Actions supports conditional execution.

Concept:

```yaml
if: always()
```

Useful for steps such as:

```text
upload test evidence
```

even if previous test step failed.

Exact implementation later.

---

# SECTION 1828 — ARTIFACT RETENTION

## 2022.

Artifacts should not necessarily be retained forever.

Consider:

```text
storage
security
compliance
debugging needs
```

Set appropriate retention policies.

---

# SECTION 1829 — REPORT SECURITY

## 2023.

Artifacts may contain:

```text
request bodies
headers
screenshots
user data
logs
```

Therefore:

```text
sanitize
control access
avoid secrets
```

Our Allure sanitization work directly supports this mindset.

---

# SECTION 1830 — TEST RESULTS VS ARTIFACTS

## 2024.

Test result:

```text
pass/fail/skipped
```

Artifact:

```text
file/output produced by job
```

Examples:

```text
JUnit XML → structured test result/report input
Allure result files → reporting input
JAR → build artifact
```

---

# SECTION 1831 — QUALITY GATE

## 2025.

Quality gate decides whether pipeline can continue.

Examples:

```text
build must succeed
critical tests must pass
security scan must pass
quality threshold must be met
```

---

# SECTION 1832 — SIMPLE QUALITY GATE

## 2026.

For current project future CI:

```text
Backend build
→ must pass

API regression
→ must pass

Expected test count
→ should be validated
```

Later:

```text
UI tests
performance thresholds
code quality
security checks
```

can be added.

---

# SECTION 1833 — QUALITY GATE IS NOT "RUN EVERYTHING"

## 2027.

A quality gate should represent:

```text
release risk
```

Not:

```text
maximum possible number of tools
```

Good CI is intentional.

---

# SECTION 1834 — PR QUALITY GATE

## 2028.

Before merge:

```text
Compile
Unit tests
Relevant API tests
Fast quality checks
```

Potential.

Goal:

```text
fast developer feedback
```

---

# SECTION 1835 — NIGHTLY QUALITY GATE

## 2029.

Nightly can run:

```text
broader regression
cross-browser tests
larger data combinations
```

because execution time is less sensitive than PR feedback.

---

# SECTION 1836 — RELEASE QUALITY GATE

## 2030.

Before release:

```text
critical regression
integration validation
deployment validation
risk-based checks
```

Exact checks depend on product.

---

# SECTION 1837 — TEST PYRAMID + CI

## 2031.

Our strategy:

```text
Many API/integration tests
Fewer critical UI tests
```

CI can reflect this:

```text
PR
→ fast lower-layer tests

Nightly/release
→ broader UI + E2E
```

---

# SECTION 1838 — WHY NOT ALL UI TESTS ON EVERY COMMIT?

## 2032.

UI tests are generally:

```text
slower
more infrastructure-dependent
more expensive
```

Run the right tests at the right stage.

---

# SECTION 1839 — TEST TAGGING

## 2033.

Future tests can be classified:

```text
smoke
regression
critical
admin
checkout
```

Then CI can select suites intentionally.

Do not create tags without a real execution strategy.

---

# SECTION 1840 — SMOKE TEST

## 2034.

Smoke suite answers:

```text
Is the build/environment fundamentally usable?
```

Examples:

```text
login
product retrieval
critical order flow
```

depending on application.

---

# SECTION 1841 — REGRESSION TEST

## 2035.

Regression validates:

```text
existing functionality remains correct after change
```

Usually broader than smoke.

---

# SECTION 1842 — SANITY TEST

## 2036.

Sanity often means:

```text
focused validation of a specific change/area
```

Terminology varies across organizations.

Always understand team definition.

---

# SECTION 1843 — CI FAILURE OWNERSHIP

## 2037.

When pipeline fails:

```text
someone must own triage
```

Possible categories:

```text
application failure
test failure
environment failure
infrastructure failure
pipeline failure
```

SDET should help classify quickly.

---

# SECTION 1844 — PIPELINE FAILURE IS NOT TEST FAILURE

## 2038.

Example:

```text
GitHub runner cannot download dependency
```

This is:

```text
pipeline/build infrastructure failure
```

not:

```text
product regression failure
```

---

# SECTION 1845 — TEST FAILURE IS NOT ALWAYS PRODUCT FAILURE

## 2039.

Possible:

```text
test bug
bad data
expired token
environment issue
```

Pipeline should provide enough evidence to classify.

---

# SECTION 1846 — BUILD FAILURE

## 2040.

Example:

```text
Java compilation error
```

Then:

```text
API regression never started
```

Report accurately:

```text
Build failed during compilation.
API tests were not executed.
```

Not:

```text
API tests failed.
```

---

# SECTION 1847 — FAILURE SUMMARY

## 2041.

A good pipeline summary should answer:

```text
Which stage failed?
How many tests ran?
How many failed?
Which environment?
Which commit?
Where is evidence?
```

---

# SECTION 1848 — EXPECTED TEST COUNT GATE

## 2042.

Current API baseline:

```text
48 expected
48 executed
```

Future CI can detect unexpected test discovery problems.

Example:

```text
48 expected
0 executed
build green
```

should not be accepted as valid regression.

---

# SECTION 1849 — SKIPPED TESTS

## 2043.

Skipped tests should be visible.

A pipeline with:

```text
48 expected
20 passed
28 skipped
```

is not equivalent to:

```text
48 passed
```

Understand why tests were skipped.

---

# SECTION 1850 — KNOWN FAILURES

## 2044.

Avoid permanently hiding failing tests.

Bad:

```text
disable failing test forever
```

Better:

```text
track defect
document reason
define ownership
restore coverage
```

---

# SECTION 1851 — QUARANTINE

## 2045.

Some teams temporarily quarantine unstable tests.

Purpose:

```text
protect pipeline signal
while root cause is actively fixed
```

Quarantine must not become:

```text
test graveyard
```

---

# SECTION 1852 — CI SIGNAL QUALITY

## 2046.

A good pipeline is:

```text
trustworthy
fast enough
diagnosable
repeatable
secure
```

If pipeline is frequently falsely red:

```text
developers stop trusting it
```

---

# SECTION 1853 — PIPELINE SPEED

## 2047.

Optimize through:

```text
test pyramid
parallelism where safe
dependency caching
test selection
reusable artifacts
```

Not through:

```text
removing meaningful validation
```

---

# SECTION 1854 — PIPELINE FEEDBACK TIME

## 2048.

PR checks should ideally provide useful feedback quickly.

Long feedback loops cause:

```text
slow development
large batches of changes
harder debugging
```

---

# SECTION 1855 — CI AS DEFECT PREVENTION

## 2049.

CI prevents:

```text
broken build reaching shared branch
known regression merging
missing test execution
bad configuration
```

when gates are designed correctly.

---

# SECTION 1856 — BRANCH PROTECTION

## 2050.

GitHub can protect important branches.

Possible rules:

```text
PR required
review required
status checks required
direct push restricted
```

Useful for:

```text
main branch quality
```

---

# SECTION 1857 — REQUIRED STATUS CHECK

## 2051.

A pipeline job can become:

```text
required check
```

Then PR cannot merge until:

```text
check passes
```

depending on repository policy.

---

# SECTION 1858 — CODE REVIEW + CI

## 2052.

Strong merge process:

```text
PR
 ↓
Code Review
 ↓
Automated Checks
 ↓
Approval
 ↓
Merge
```

Neither:

```text
review alone
```

nor:

```text
automation alone
```

is sufficient for every risk.

---

# SECTION 1859 — MERGE STRATEGY

## 2053.

Possible:

```text
merge commit
squash merge
rebase merge
```

CI should validate the code state intended for merge according to repository workflow.

---

# SECTION 1860 — POST-MERGE CI

## 2054.

Even after PR checks pass:

```text
main branch can run validation again
```

because merged state may differ from isolated branch state.

---

# SECTION 1861 — DEPLOYMENT PIPELINE

## 2055.

Future:

```text
Build
 ↓
Test
 ↓
Package
 ↓
Deploy
 ↓
Smoke Test
```

Later when AWS is introduced:

```text
deployment target
credentials
artifact strategy
health checks
rollback
```

will become relevant.

---

# SECTION 1862 — BUILD ONCE, DEPLOY MANY

## 2056.

Preferred principle:

```text
Build one immutable artifact
```

Then promote:

```text
DEV
→ QA
→ STAGE
→ PROD
```

using environment-specific configuration.

Avoid rebuilding different source for each environment.

---

# SECTION 1863 — IMMUTABLE ARTIFACT

## 2057.

Artifact should not be manually modified between environments.

Example:

```text
same JAR
different runtime configuration
```

This improves:

```text
traceability
confidence
reproducibility
```

---

# SECTION 1864 — DEPLOYMENT IDENTIFICATION

## 2058.

Ideally know:

```text
Git SHA
artifact version
deployment environment
deployment time
```

Then tester can answer:

```text
What exactly am I testing?
```

---

# SECTION 1865 — POST-DEPLOYMENT SMOKE

## 2059.

After deployment:

```text
service up?
critical API working?
authentication working?
critical business path working?
```

This quickly validates deployment health.

---

# SECTION 1866 — ROLLBACK

## 2060.

Rollback means:

```text
return deployment to known-good version/state
```

Used when release causes unacceptable impact.

Rollback strategy depends on:

```text
application
database
infrastructure
deployment mechanism
```

---

# SECTION 1867 — ROLLBACK VS GIT REVERT

## 2061.

Not identical.

Git revert:

```text
creates a new commit reversing code changes
```

Deployment rollback:

```text
restores previously deployed version/state
```

A deployment may need rollback before code history is changed.

---

# SECTION 1868 — DATABASE ROLLBACK COMPLEXITY

## 2062.

Application rollback can be complicated by:

```text
database schema/data changes
```

Example:

```text
new version writes data
old version cannot understand it
```

Release design must consider backward compatibility.

---

# SECTION 1869 — BLUE/GREEN DEPLOYMENT

## 2063.

Concept:

```text
Blue = current
Green = new
```

Deploy new version separately.

Validate.

Then switch traffic.

Benefits can include:

```text
reduced downtime
easier rollback
```

Infrastructure complexity is higher.

---

# SECTION 1870 — CANARY DEPLOYMENT

## 2064.

Canary:

```text
release new version to small percentage/users first
```

Observe:

```text
errors
latency
business metrics
```

Then gradually increase traffic.

---

# SECTION 1871 — FEATURE FLAGS

## 2065.

Feature flag allows functionality to be:

```text
enabled/disabled
```

without necessarily deploying new code each time.

Testing must consider:

```text
flag ON
flag OFF
audience/environment
```

---

# SECTION 1872 — DEPLOYMENT ≠ FEATURE RELEASE

## 2066.

With feature flags:

```text
code can be deployed
```

while:

```text
feature remains disabled
```

Important distinction for testers.

---

# SECTION 1873 — JENKINS

## 2067.

Jenkins is an automation server widely used for CI/CD.

It can:

```text
build
test
schedule
deploy
publish reports
orchestrate pipelines
```

---

# SECTION 1874 — JENKINS CONTROLLER

## 2068.

Conceptually Jenkins has a controller responsible for:

```text
pipeline orchestration
configuration
scheduling
```

Execution can happen on:

```text
agents
```

---

# SECTION 1875 — JENKINS AGENT

## 2069.

Agent executes work such as:

```text
Maven build
tests
Docker commands
scripts
```

Similar broad concept to:

```text
CI runner
```

---

# SECTION 1876 — JENKINSFILE

## 2070.

Pipeline can be stored as:

```text
Jenkinsfile
```

in repository.

This provides:

```text
pipeline as code
```

---

# SECTION 1877 — DECLARATIVE JENKINS PIPELINE

## 2071.

Conceptual structure:

```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // build commands
            }
        }

        stage('Test') {
            steps {
                // test commands
            }
        }
    }
}
```

Do not treat this as our current project implementation.

---

# SECTION 1878 — JENKINS STAGE

## 2072.

Stages organize pipeline into meaningful phases:

```text
Checkout
Build
Test
Package
Deploy
```

Good stage names make failures easier to understand.

---

# SECTION 1879 — JENKINS PARAMETERS

## 2073.

A Jenkins job can accept parameters such as:

```text
environment
browser
suite
```

Useful for manual/on-demand regression.

Input should still be validated.

---

# SECTION 1880 — JENKINS CREDENTIALS

## 2074.

Jenkins can securely manage credentials.

Use credential mechanisms instead of:

```text
hardcoding password in Jenkinsfile
```

Same security principle as GitHub Secrets.

---

# SECTION 1881 — JENKINS SCHEDULE

## 2075.

Jenkins can schedule jobs.

Useful for:

```text
nightly regression
periodic checks
```

Exact cron syntax/behavior should be verified in the target Jenkins environment.

---

# SECTION 1882 — JENKINS ARTIFACTS

## 2076.

Jenkins can preserve:

```text
reports
logs
build artifacts
```

and integrate with reporting plugins/tools.

---

# SECTION 1883 — JENKINS VS GITHUB ACTIONS

## 2077.

Both can orchestrate CI/CD.

High-level:

```text
GitHub Actions
→ deeply integrated with GitHub repositories

Jenkins
→ highly customizable automation server
```

Selection depends on:

```text
organization
infrastructure
security
existing ecosystem
maintenance model
```

---

# SECTION 1884 — DON'T SAY ONE IS ALWAYS BETTER

## 2078.

Interview answer:

> "I choose based on the organization's ecosystem, infrastructure and pipeline requirements. The underlying CI/CD concepts — triggers, runners or agents, stages, secrets, artifacts and quality gates — transfer across tools."

---

# SECTION 1885 — AZURE DEVOPS PIPELINE CONCEPT

## 2079.

Another CI/CD platform can use:

```text
Azure Pipelines
```

with YAML or UI-based configuration.

Core concepts remain:

```text
trigger
agent
job
step
artifact
environment
secret
```

Tool changes.

Engineering principles remain.

---

# SECTION 1886 — TOOL-INDEPENDENT CI KNOWLEDGE

## 2080.

Learn:

```text
CI architecture
```

not only:

```text
button locations
```

Then moving between:

```text
GitHub Actions
Jenkins
Azure DevOps
GitLab CI
```

becomes easier.

---

# SECTION 1887 — PIPELINE ENVIRONMENTS

## 2081.

A pipeline can target:

```text
DEV
QA
STAGE
PROD
```

Each may have:

```text
different URL
credentials
network access
approval rules
```

Code should remain reusable.

---

# SECTION 1888 — ENVIRONMENT APPROVAL

## 2082.

Sensitive environments may require:

```text
manual approval
```

before deployment.

Example:

```text
Build
→ Test
→ Stage
→ Approval
→ Production
```

This is common in controlled release processes.

---

# SECTION 1889 — LEAST PRIVILEGE

## 2083.

CI credentials should receive:

```text
only permissions they actually need
```

Example:

```text
test job should not automatically receive production deployment permissions
```

This reduces security risk.

---

# SECTION 1890 — SECRET SCOPE

## 2084.

Different environments should ideally use separate credentials.

Example:

```text
QA credential
Stage credential
Production credential
```

Do not reuse one powerful credential everywhere.

---

# SECTION 1891 — OIDC CONCEPT

## 2085.

Modern CI platforms can sometimes authenticate to cloud providers using short-lived identity federation such as:

```text
OIDC
```

instead of storing long-lived cloud access keys.

When AWS phase begins, this is worth considering.

Do not implement it yet.

---

# SECTION 1892 — THIRD-PARTY ACTION SECURITY

## 2086.

CI workflows can execute third-party actions/plugins.

Risks:

```text
supply-chain compromise
over-permission
secret exposure
unexpected updates
```

Good practices include:

```text
trusted sources
version pinning
minimal permissions
reviewing changes
```

---

# SECTION 1893 — CI PERMISSIONS

## 2087.

Workflow token permissions should follow:

```text
least privilege
```

Do not grant write access if workflow only needs read access.

---

# SECTION 1894 — DEPENDENCY SECURITY

## 2088.

Build pipelines download dependencies.

Risks include:

```text
vulnerable package
malicious package
compromised repository
dependency confusion
```

Later security tooling can be added intentionally.

---

# SECTION 1895 — CI SUPPLY CHAIN

## 2089.

Pipeline itself is security-sensitive because it can access:

```text
source code
secrets
artifacts
deployment systems
```

Protect:

```text
workflow changes
branch
credentials
actions/plugins
```

---

# SECTION 1896 — QUALITY VS SECURITY GATE

## 2090.

Quality gate:

```text
tests
coverage
code quality
```

Security gate:

```text
dependency scan
secret scan
SAST
container scan
```

They overlap but are not identical.

---

# SECTION 1897 — SECRET SCANNING

## 2091.

Before first GitHub push we manually verified:

```text
.env ignored
.env.example only staged
no obvious secret/private key
```

Future CI can automate some secret scanning.

This helps prevent accidental credential commits.

---

# SECTION 1898 — STATIC ANALYSIS

## 2092.

Static analysis inspects code without executing full application behavior.

Potential checks:

```text
bugs
code smells
security patterns
quality issues
```

Tools can include:

```text
SonarQube
language-specific analyzers
```

Add only when it supports project goals.

---

# SECTION 1899 — SONARQUBE CONCEPT

## 2093.

SonarQube can analyze:

```text
code quality
bugs
code smells
security hotspots/vulnerabilities
coverage data integration
```

depending on language/configuration.

It can participate in a:

```text
quality gate
```

---

# SECTION 1900 — CODE COVERAGE

## 2094.

Coverage measures which code was exercised by tests.

Common metrics:

```text
line coverage
branch coverage
```

Coverage is useful.

But:

```text
100% coverage ≠ bug-free application
```

---

# SECTION 1901 — COVERAGE QUALITY

## 2095.

Bad test:

```text
executes line
asserts nothing useful
```

can increase coverage.

Therefore:

```text
coverage quantity
+
assertion quality
+
risk coverage
```

matter together.

---

# SECTION 1902 — QUALITY GATE THRESHOLD

## 2096.

Example concept:

```text
coverage below threshold
→ fail quality gate
```

Threshold should be:

```text
meaningful
team-agreed
risk-based
```

Do not select random percentage just to look impressive.

---

# SECTION 1903 — PERFORMANCE IN CI

## 2097.

Future k6 integration can support:

```text
performance smoke
```

or:

```text
scheduled/load environment tests
```

Do not run heavy load tests against shared environment on every PR without planning.

---

# SECTION 1904 — PERFORMANCE THRESHOLD

## 2098.

Examples conceptually:

```text
error rate
response-time percentile
throughput
```

k6 can enforce thresholds.

Exact targets should come from performance requirements/SLOs, not guesses.

---

# SECTION 1905 — UI TESTS IN CI

## 2099.

Future Playwright CI will need:

```text
Node
dependencies
browser binaries
application URL
test credentials
artifacts
```

Potential evidence:

```text
screenshots
videos
traces
HTML report
```

depending on configuration.

---

# SECTION 1906 — HEADLESS EXECUTION

## 2100.

CI browsers commonly run:

```text
headless
```

because there is no interactive desktop requirement.

But headless behavior still needs validation against intended browser behavior.

---

# SECTION 1907 — PLAYWRIGHT TRACE

## 2101.

Future Playwright can provide traces containing useful debugging information.

Potential:

```text
DOM snapshots
network
screenshots
actions
```

Configuration should balance:

```text
debug value
storage
security
```

---

# SECTION 1908 — UI TEST SHARDING

## 2102.

Large UI suites can be divided across workers/jobs.

This is called:

```text
sharding
```

Useful later if suite becomes large.

Do not add complexity before needed.

---

# SECTION 1909 — API TEST PARALLELISM IN CI

## 2103.

Our API suite may eventually run parallel.

Before enabling:

```text
verify test data isolation
token safety
DB cleanup
shared resource handling
```

Fast but unreliable is not improvement.

---

# SECTION 1910 — MATRIX STRATEGY

## 2104.

GitHub Actions can run combinations conceptually:

```text
Java version
browser
OS
environment
```

Example future UI:

```text
Chromium
Firefox
WebKit
```

But matrix size can multiply execution cost.

---

# SECTION 1911 — CROSS-BROWSER STRATEGY

## 2105.

Do not necessarily run:

```text
all browsers
all tests
every PR
```

Possible strategy:

```text
PR → Chromium critical
Nightly → broader cross-browser
Release → risk-based full set
```

---

# SECTION 1912 — CI COST

## 2106.

CI consumes:

```text
compute
time
storage
engineering attention
```

Optimize based on risk.

More jobs do not automatically mean better quality.

---

# SECTION 1913 — CI OBSERVABILITY

## 2107.

Pipeline should answer:

```text
what ran?
where?
against what?
how long?
what failed?
where are logs?
```

Without observability, CI becomes difficult to trust.

---

# SECTION 1914 — PIPELINE METRICS

## 2108.

Useful metrics can include:

```text
pipeline duration
failure rate
flaky test rate
test execution time
mean time to recovery
```

Use metrics to improve engineering flow, not to punish teams.

---

# SECTION 1915 — FAILURE TREND

## 2109.

Repeated failure in same area can indicate:

```text
unstable test
unstable environment
product reliability problem
slow dependency
```

Trend analysis is more useful than isolated reruns.

---

# SECTION 1916 — TEST DURATION TREND

## 2110.

If API suite grows:

```text
2 min
→ 5 min
→ 20 min
```

investigate:

```text
more tests?
slow environment?
unnecessary waits?
serial bottleneck?
```

---

# SECTION 1917 — PIPELINE BOTTLENECK

## 2111.

Find longest stage.

Example:

```text
Checkout        10 sec
Build           1 min
API tests       4 min
UI tests       20 min
```

Optimization should target:

```text
actual bottleneck
```

---

# SECTION 1918 — CI LOGS

## 2112.

Good CI logs should be:

```text
structured enough
searchable
timestamped
sanitized
```

Avoid:

```text
gigabytes of meaningless debug output
```

---

# SECTION 1919 — CI FAILURE REPRODUCTION

## 2113.

Best scenario:

```text
CI command
≈
local command
```

Then developer can reproduce:

```bash
mvn clean test
```

or future equivalent.

Avoid completely different build paths between local and CI.

---

# SECTION 1920 — WRAPPER ADVANTAGE IN CI

## 2114.

Backend:

```bash
./mvnw clean test
```

can be used both:

```text
local
CI
```

This reduces tool-version drift.

---

# SECTION 1921 — SHELL SCRIPT REUSE

## 2115.

Reusable project scripts can make:

```text
local
CI
```

behavior more consistent.

But scripts must:

```text
handle errors
avoid secrets
use reliable paths
```

---

# SECTION 1922 — DO NOT COPY LOCAL `.env` TO CI

## 2116.

Local:

```text
.env
```

CI:

```text
secret store + environment injection
```

Same configuration names.

Different secure source.

---

# SECTION 1923 — CI CONFIGURATION MENTAL MODEL

## 2117.

```text
Repository
   ↓
Workflow
   ↓
Secrets / Variables
   ↓
Runner
   ↓
Build
   ↓
Application / Test
```

Secrets should enter as late and narrowly as practical.

---

# SECTION 1924 — PIPELINE FAILURE DECISION TREE

## 2118.

```text
PIPELINE FAILED
      ↓
Did workflow start?
      |
      ├── NO
      │    ↓
      │ Trigger/config/permission
      │
      └── YES
           ↓
Checkout?
           |
           ├── FAIL
           │    ↓
           │ Repo/auth/network
           │
           └── PASS
                ↓
Setup?
                |
                ├── FAIL
                │    ↓
                │ Runtime/tool
                │
                └── PASS
                     ↓
Build?
                     |
                     ├── FAIL
                     │    ↓
                     │ Source/dependency
                     │
                     └── PASS
                          ↓
Tests?
                          |
                          ├── FAIL
                          │    ↓
                          │ App/test/data/env
                          │
                          └── PASS
                               ↓
Artifact/Deploy?
```

---

# SECTION 1925 — CI API TEST DECISION TREE

## 2119.

```text
API CI FAIL
   ↓
Correct environment?
   ↓
Backend reachable?
   ↓
Authentication available?
   ↓
Expected test count?
   ↓
Actual HTTP failures?
   ↓
DB/test data?
   ↓
Framework/reporting?
```

---

# SECTION 1926 — CI SECURITY DECISION TREE

## 2120.

```text
Does job need secret?
      ↓
YES
      ↓
Can permission be reduced?
      ↓
Inject securely
      ↓
Never log
      ↓
Sanitize artifacts
      ↓
Rotate/revoke if exposed
```

---

# SECTION 1927 — INTERVIEW: WHAT IS CI?

## 2121.

> "Continuous Integration is the practice of frequently integrating code changes into a shared repository and automatically validating them through build and test checks. Its main benefit is fast feedback, so integration and regression problems are detected close to the change that introduced them."

---

# SECTION 1928 — INTERVIEW: WHAT IS YOUR ROLE AS SDET IN CI/CD?

## 2122.

> "As an SDET, I focus on how quality checks fit into the delivery pipeline. That includes deciding which tests run at pull-request, nightly and release stages, making automation environment-independent, publishing useful test evidence, maintaining reliable quality gates and helping classify whether failures come from the product, test framework, environment or pipeline."

---

# SECTION 1929 — INTERVIEW: WHAT WOULD YOU PUT IN A PR PIPELINE?

## 2123.

> "I prioritize fast, deterministic checks that provide immediate feedback — compilation, unit tests, relevant API or integration tests and lightweight quality checks. I keep slower broad UI or performance suites for appropriate later stages unless the change risk specifically requires them."

---

# SECTION 1930 — INTERVIEW: HOW WOULD YOU INTEGRATE YOUR API FRAMEWORK?

## 2124.

> "I would configure the target environment through runtime variables, inject credentials through the CI secret store, ensure the backend and database are ready, execute the Maven-based REST Assured and TestNG suite, validate the expected test count and preserve sanitized Allure results as pipeline evidence."

---

# SECTION 1931 — INTERVIEW: HOW DO YOU HANDLE CI SECRETS?

## 2125.

> "I never commit runtime credentials into the repository. I store them in the CI platform's secret mechanism and inject them only into jobs that require them. I also avoid printing them and sanitize generated reports, because protecting the repository alone is not enough if logs or artifacts leak credentials."

---

# SECTION 1932 — INTERVIEW: WHY DID PIPELINE PASS WITH ZERO TESTS?

## 2126.

> "A successful build only means the executed commands returned successfully. If test discovery is misconfigured, the test runner can execute zero tests and still return success. That's why I validate expected test counts or test-result output rather than treating a green build as proof that regression actually ran."

---

# SECTION 1933 — INTERVIEW: TESTS FAIL IN CI BUT PASS LOCALLY

## 2127.

> "I compare the exact commit, runtime and dependency versions, environment variables, secrets, target URL, network access and test selection. I also check for hidden local state such as cached dependencies or uncommitted files. CI is useful because it exposes assumptions that may exist only on a developer machine."

---

# SECTION 1934 — INTERVIEW: GITHUB ACTIONS VS JENKINS

## 2128.

> "The core CI/CD concepts are similar: triggers, execution agents, stages or jobs, secrets, artifacts and quality gates. GitHub Actions is tightly integrated with GitHub, while Jenkins is a highly customizable automation server that organizations can host and extend. I choose based on the existing ecosystem and infrastructure rather than treating one tool as universally better."

---

# SECTION 1935 — INTERVIEW: WHAT IS A QUALITY GATE?

## 2129.

> "A quality gate is a set of conditions that must be satisfied before code can progress. Examples are successful compilation, required automated tests, security checks or agreed quality thresholds. I design gates around release risk rather than simply adding every available tool."

---

# SECTION 1936 — INTERVIEW: HOW DO YOU REDUCE PIPELINE TIME?

## 2130.

> "I first identify the actual bottleneck. Then I use the test pyramid, safe parallelism, dependency caching, risk-based test selection and reusable build artifacts. I avoid reducing pipeline time by removing important validation."

---

# SECTION 1937 — INTERVIEW: WHAT ARE ARTIFACTS?

## 2131.

> "Artifacts are outputs preserved from a pipeline job, such as an application JAR, test-result files, Allure data, logs or UI screenshots. They support deployment, debugging and traceability between pipeline stages."

---

# SECTION 1938 — INTERVIEW: BUILD ONCE DEPLOY MANY

## 2132.

> "I prefer building one immutable artifact and promoting that same artifact through environments while externalizing environment-specific configuration. This reduces the risk of QA validating one binary while production receives a differently built binary."

---

# SECTION 1939 — INTERVIEW: WHAT IF TEST REPORT UPLOAD FAILS?

## 2133.

> "I separate test execution from report publication. I first verify whether the tests actually ran and whether result files were generated. Then I check artifact paths, working directory and publication configuration. A reporting failure should not be misreported as an application regression."

---

# SECTION 1940 — INTERVIEW: WHAT IF TESTS ARE FLAKY IN CI?

## 2134.

> "I classify the instability before adding retries. CI can expose timing, shared-data, resource, network and parallelism issues that may not appear locally. I isolate the root cause, improve synchronization or test isolation, and use bounded retries only for clearly transient conditions."

---

# SECTION 1941 — INTERVIEW: HOW DO YOU DESIGN NIGHTLY REGRESSION?

## 2135.

> "I use nightly execution for broader tests that would make pull-request feedback too slow, such as larger regression suites or wider browser coverage. I make failures visible with reports and ownership, and I track recurring instability instead of simply rerunning until green."

---

# SECTION 1942 — INTERVIEW: CI/CD AND TEST PYRAMID

## 2136.

> "I align pipeline stages with the test pyramid. Fast unit and API-level checks provide early feedback, while a smaller set of critical UI tests validates end-to-end behavior. Broader and more expensive suites can run nightly or before release."

---

# SECTION 1943 — INTERVIEW: HOW WOULD YOU DEBUG FAILED CI?

## 2137.

> "I first identify the failing stage and whether the failure occurred before tests, during tests or during reporting or deployment. I verify the commit and environment, inspect the first meaningful error, reproduce the same command locally where possible and isolate whether the cause is source code, dependency resolution, configuration, infrastructure, application behavior or automation."

---

# SECTION 1944 — INTERVIEW: HOW DO YOU PREVENT SECRET LEAKS?

## 2138.

> "I keep secrets out of source control, use CI secret storage, apply least-privilege access, avoid printing sensitive values and sanitize reports and logs. I also review workflow permissions and third-party actions because the CI pipeline itself is part of the software supply chain."

---

# SECTION 1945 — REAL PROJECT STORY: API FRAMEWORK READY FOR CI

## 2139.

Current implementation already has several CI-friendly characteristics:

```text
Environment switching
REST Assured
TestNG
Maven execution
Allure results
Sanitized HTTP evidence
Externalized credentials
48-test regression baseline
```

Therefore CI does not require rewriting the test framework.

It requires:

```text
orchestration
secure configuration
execution
report publication
quality gating
```

---

# SECTION 1946 — REAL PROJECT STORY: SECURITY

## 2140.

Before GitHub publication we already established:

```text
.env excluded
.env.example safe
secret review performed
```

Future CI should preserve this model.

Senior explanation:

> "I designed the project so credentials are runtime concerns rather than source-code concerns, which makes later CI secret injection straightforward."

---

# SECTION 1947 — REAL PROJECT STORY: REPORTING

## 2141.

Allure already captures:

```text
environment metadata
request
response
test result
```

with secret sanitization.

Future CI can preserve those results after every regression run.

This gives:

```text
failure evidence
traceability
debugging support
```

---

# SECTION 1948 — REAL PROJECT STORY: RBAC QUALITY GATE

## 2142.

Critical security behavior includes:

```text
USER cannot create product
ADMIN can create product
```

Future CI should keep such RBAC tests as important regression coverage.

A build should not be considered healthy if authorization rules regress.

---

# SECTION 1949 — REAL PROJECT STORY: DATABASE VALIDATION

## 2143.

Our API automation includes database validation.

Future CI therefore needs:

```text
controlled DB connectivity
secure credentials
predictable test data
cleanup
```

This is more realistic than treating CI as HTTP-only execution.

---

# SECTION 1950 — FUTURE CI PHASE 1

## 2144.

When we actually implement GitHub Actions, start simple:

```text
1. Trigger workflow manually
2. Setup Java 17
3. Build backend
4. Run backend tests
5. Preserve results
```

Verify.

Then expand.

---

# SECTION 1951 — FUTURE CI PHASE 2

## 2145.

Add:

```text
PostgreSQL service
backend startup
readiness check
API automation
Allure artifact
```

Verify end-to-end.

---

# SECTION 1952 — FUTURE CI PHASE 3

## 2146.

After frontend exists:

```text
Node setup
frontend build
Playwright installation
UI automation
Playwright artifacts
```

---

# SECTION 1953 — FUTURE CI PHASE 4

## 2147.

Later:

```text
Docker build
full Compose environment
performance checks
security/quality checks
AWS deployment
post-deployment validation
```

Only add each layer when previous layer is stable.

---

# SECTION 1954 — WHY BUILD CI IN PHASES?

## 2148.

If we add:

```text
DB
Backend
API tests
Frontend
Playwright
Docker
k6
AWS
```

in one giant workflow:

```text
first failure becomes difficult to diagnose
```

Incremental pipeline design gives:

```text
clear ownership
easy debugging
stable foundation
```

---

# SECTION 1955 — PIPELINE DESIGN PRINCIPLE

## 2149.

Do not create:

```text
one 1000-line pipeline
```

without structure.

Use:

```text
logical jobs
clear names
reusable commands
documented environment strategy
```

---

# SECTION 1956 — REUSABLE WORKFLOW CONCEPT

## 2150.

GitHub Actions supports reusable workflows.

Useful later if multiple pipelines repeat:

```text
Java setup
test execution
artifact upload
```

Do not abstract too early.

First:

```text
make it work
```

Then:

```text
remove meaningful duplication
```

---

# SECTION 1957 — DRY VS READABILITY IN CI

## 2151.

DRY:

```text
Don't Repeat Yourself
```

is useful.

But over-abstraction can make CI hard to understand.

Pipeline should optimize for:

```text
maintainability
debuggability
clarity
```

not minimum line count.

---

# SECTION 1958 — CI FAILURE NOTIFICATION

## 2152.

Teams may notify failures through:

```text
GitHub
email
Slack
Teams
```

Important questions:

```text
Who owns failure?
Is notification actionable?
Is failure new?
```

Too many noisy notifications cause alert fatigue.

---

# SECTION 1959 — FAILURE OWNERSHIP MODEL

## 2153.

Example:

```text
Compile failure
→ developer/change owner

Automation framework failure
→ SDET/automation owner

Environment outage
→ platform/environment owner

Product regression
→ development + QA
```

Exact ownership varies by team.

---

# SECTION 1960 — DON'T AUTO-RERUN UNTIL GREEN

## 2154.

Bad pipeline culture:

```text
Fail
→ rerun
→ fail
→ rerun
→ pass
→ ignore
```

This destroys signal.

A rerun may help classify intermittency.

It is not root-cause analysis.

---

# SECTION 1961 — PIPELINE RELIABILITY

## 2155.

Pipeline itself is a product.

It needs:

```text
maintenance
testing
security
observability
documentation
```

Broken CI reduces delivery confidence.

---

# SECTION 1962 — PIPELINE DOCUMENTATION

## 2156.

README/docs should explain:

```text
what triggers pipeline
what stages run
required secrets
expected reports
how to reproduce locally
how to troubleshoot
```

Do not document secret values.

---

# SECTION 1963 — CI RUNBOOK

## 2157.

Useful:

```text
Failure:
API tests cannot connect.

Check:
1. Backend job
2. Readiness step
3. Base URL
4. Port
5. backend logs
```

Runbooks reduce repeated debugging effort.

---

# SECTION 1964 — PIPELINE VERSION TRACEABILITY

## 2158.

Every execution should ideally identify:

```text
commit SHA
branch
workflow version
environment
```

Then defect evidence can say:

```text
Failed against commit XYZ in stage.
```

---

# SECTION 1965 — CI + RELEASE TRACEABILITY

## 2159.

Ideal chain:

```text
Requirement
 ↓
Code Change
 ↓
Pull Request
 ↓
CI Run
 ↓
Artifact
 ↓
Deployment
 ↓
Test Evidence
 ↓
Release
```

This is strong engineering traceability.

---

# SECTION 1966 — CI/CD AND SHIFT LEFT

## 2160.

Shift left means:

```text
quality/security feedback earlier in delivery
```

Examples:

```text
PR tests
static analysis
API contract validation
secret scanning
```

Goal:

```text
find defects earlier
```

not simply:

```text
move all testing earlier
```

---

# SECTION 1967 — SHIFT RIGHT

## 2161.

Shift right focuses on validation/learning after deployment.

Examples:

```text
monitoring
synthetic checks
canary analysis
production observability
```

SDET can contribute to both.

---

# SECTION 1968 — CONTINUOUS TESTING

## 2162.

Continuous testing means quality validation is integrated throughout delivery.

Not:

```text
run same 500 tests after every action
```

Instead:

```text
right test
right stage
right risk
```

---

# SECTION 1969 — CI/CD MATURITY

## 2163.

Basic:

```text
Build automatically
```

Better:

```text
Build + test
```

Stronger:

```text
risk-based tests
quality gates
reports
secure secrets
reliable artifacts
```

Advanced:

```text
deployment
observability
rollback
performance/security integration
```

---

# SECTION 1970 — SENIOR SDET CI MINDSET

## 2164.

Do not say only:

```text
I know Jenkins.
```

Better:

```text
I understand how to integrate automated quality checks
into CI/CD using triggers, agents/runners, environment
configuration, secrets, artifacts and quality gates.
```

Tools can change.

Concepts transfer.

---

# SECTION 1971 — SENIOR SDET PIPELINE STORY

## 2165.

Interview structure:

```text
Problem
→ regression was manual/on-demand

Design
→ automation integrated into pipeline

Execution
→ environment config + secure credentials

Evidence
→ reports/artifacts

Gate
→ critical failures block progression

Result
→ faster and more repeatable feedback
```

Only use a specific real-company example if you can personally defend that implementation.

---

# SECTION 1972 — DON'T OVERCLAIM

## 2166.

For our portfolio project today:

Correct:

> "The API framework is CI-ready, and GitHub Actions integration is the next implementation phase."

Incorrect:

> "I already implemented the complete GitHub Actions CI/CD pipeline."

We will change this statement only after actual implementation.

---

# SECTION 1973 — CI/CD COMMAND MENTAL MAP

## 2167.

Local:

```bash
git status
```

```bash
java -version
```

```bash
mvn -version
```

```bash
docker compose ps
```

```bash
./backend/run-local.sh
```

API local test script:

```bash
./api-automation/run-tests-local.sh
```

These local commands help us later map the same engineering flow into CI.

---

# SECTION 1974 — LOCAL TO CI MAPPING

## 2168.

```text
LOCAL
--------------------------------
Clone/pull repo
Java installed
.env
Docker Desktop
Start backend
Run tests
Open Allure

CI
--------------------------------
Checkout action
Setup Java
Secrets/variables
Service/container
Start backend
Run tests
Upload/publish results
```

This is the key mental model.

---

# SECTION 1975 — CI IS AUTOMATED TERMINAL WORK

## 2169.

At a simplified level:

```text
CI runner
```

is executing many of the same things we understand from terminal:

```text
checkout
setup
commands
environment variables
exit codes
files/artifacts
```

That is why Linux, Git, Maven and environment knowledge makes CI easier.

---

# SECTION 1976 — WHY PREVIOUS PARTS MATTER

## 2170.

Our tooling learning sequence:

```text
Maven
 ↓
Java Runtime
 ↓
Spring Boot
 ↓
HTTP/Networking
 ↓
Docker
 ↓
Node/npm
 ↓
JSON/YAML
 ↓
Dependencies/Artifacts
 ↓
Troubleshooting
 ↓
CI/CD
```

CI/CD connects all previous topics.

---

# SECTION 1977 — PIPELINE DEBUGGING MENTAL MODEL

## 2171.

```text
CI FAILS
   ↓
Workflow syntax?
   ↓
Trigger?
   ↓
Runner?
   ↓
Checkout?
   ↓
Runtime?
   ↓
Dependency?
   ↓
Configuration?
   ↓
Service?
   ↓
Test?
   ↓
Report?
   ↓
Deployment?
```

Always identify stage first.

---

# SECTION 1978 — YAML FAILURE

## 2172.

Pipeline may fail before running because YAML/configuration is invalid.

Possible:

```text
indentation
wrong key
invalid expression
unsupported configuration
```

This is:

```text
pipeline configuration failure
```

not application failure.

---

# SECTION 1979 — TRIGGER FAILURE

## 2173.

Workflow exists but does not run.

Check:

```text
event
branch filter
path filter
workflow file
permissions
```

Do not debug Maven if workflow never started.

---

# SECTION 1980 — CHECKOUT FAILURE

## 2174.

Possible:

```text
repository permission
token permission
network
submodule/private dependency
```

Application source may never have been built.

---

# SECTION 1981 — SETUP FAILURE

## 2175.

Example:

```text
Java setup failed
```

Then:

```text
Maven never ran
```

Report stage accurately.

---

# SECTION 1982 — DEPENDENCY DOWNLOAD FAILURE

## 2176.

Possible:

```text
Maven Central unavailable
private repository auth
TLS
proxy
wrong artifact
```

Classify as:

```text
dependency/infrastructure
```

before product regression.

---

# SECTION 1983 — TEST EXECUTION FAILURE

## 2177.

Now inspect:

```text
which tests?
expected count?
HTTP response?
test data?
environment?
```

Use Allure/test results.

---

# SECTION 1984 — ARTIFACT UPLOAD FAILURE

## 2178.

Tests may already be complete.

Check:

```text
path
permissions
artifact existence
conditional execution
```

Do not rerun entire suite automatically without understanding.

---

# SECTION 1985 — DEPLOYMENT FAILURE

## 2179.

Build/test can pass while deployment fails.

Possible:

```text
cloud auth
network
artifact
configuration
health check
```

Again:

```text
pipeline stage matters.
```

---

# SECTION 1986 — QUALITY GATE FAILURE

## 2180.

Example:

```text
tests pass
but quality gate fails
```

Potential:

```text
coverage
static analysis
security finding
policy
```

A green test suite does not mean every pipeline gate passes.

---

# SECTION 1987 — PR STATUS

## 2181.

A PR can show multiple checks:

```text
Backend Build       PASS
API Tests           PASS
UI Tests            FAIL
Security Scan       PASS
```

Overall merge readiness depends on:

```text
required checks
```

---

# SECTION 1988 — PIPELINE EVIDENCE

## 2182.

When CI fails, capture:

```text
workflow/run
job
step
commit
environment
error
artifact/report
```

This is equivalent to collecting evidence during application troubleshooting.

---

# SECTION 1989 — CI/CD RAPID FIRE

## 2183. CI?

```text
Continuous Integration
```

## 2184. CD?

```text
Continuous Delivery / Deployment
```

## 2185. Runner?

```text
Machine/environment executing CI job
```

## 2186. Job?

```text
Logical execution unit in workflow
```

## 2187. Step?

```text
Individual action/command within job
```

## 2188. Artifact?

```text
Preserved output from pipeline
```

## 2189. Secret?

```text
Sensitive runtime configuration
```

## 2190. Quality gate?

```text
Condition required before progression
```

---

# SECTION 1990 — RAPID FIRE CONTINUED

## 2191. PR pipeline?

```text
Validate change before merge
```

## 2192. Nightly regression?

```text
Scheduled broader test execution
```

## 2193. Smoke?

```text
Fast critical health validation
```

## 2194. Cache?

```text
Reusable downloaded/build data for speed
```

## 2195. Service container?

```text
Dependency service started for CI job
```

## 2196. Branch protection?

```text
Rules controlling changes to important branch
```

---

# SECTION 1991 — RAPID FIRE SECURITY

## 2197. Commit password?

```text
Never
```

## 2198. Print token in CI?

```text
Never intentionally
```

## 2199. Secret source?

```text
CI secret management
```

## 2200. Workflow permission?

```text
Least privilege
```

## 2201. Third-party action?

```text
Review and pin trusted dependency
```

---

# SECTION 1992 — RAPID FIRE TEST STRATEGY

## 2202. Every UI test every commit?

```text
Usually not necessary
```

## 2203. API tests in PR?

```text
Good candidate when fast and reliable
```

## 2204. Heavy performance test every PR?

```text
Usually no
```

## 2205. Zero tests + green build?

```text
Not valid regression evidence
```

## 2206. Retry all failures?

```text
No
```

---

# SECTION 1993 — 5-MINUTE CI/CD REVISION

## 2207.

Remember:

```text
Trigger
 ↓
Runner
 ↓
Checkout
 ↓
Runtime
 ↓
Dependencies
 ↓
Build
 ↓
Test
 ↓
Report
 ↓
Artifact
 ↓
Quality Gate
 ↓
Deploy
 ↓
Validate
```

Security surrounds all stages:

```text
Secrets
Permissions
Sanitization
Supply Chain
```

---

# SECTION 1994 — 30-SECOND CI ANSWER

## 2208.

> "I see CI as automated, repeatable validation of integrated code. A good pipeline checks out the exact commit, sets up a controlled runtime, builds the application, runs the right level of automated tests, publishes useful evidence and enforces risk-based quality gates. From an SDET perspective, I focus heavily on test selection, environment configuration, secret handling, failure diagnosis and pipeline signal quality."

---

# SECTION 1995 — 30-SECOND GITHUB ACTIONS ANSWER

## 2209.

> "GitHub Actions defines CI/CD workflows as YAML in the repository. Workflows are triggered by events such as pull requests, pushes, schedules or manual execution and contain jobs running on hosted or self-hosted runners. Jobs contain steps for activities such as checkout, runtime setup, build, test and artifact publication."

---

# SECTION 1996 — 30-SECOND JENKINS ANSWER

## 2210.

> "Jenkins is an automation server used to orchestrate CI/CD. Pipelines can be stored in a Jenkinsfile and organized into stages such as build, test and deploy. Work executes on agents, while credentials, parameters, schedules and reporting can be integrated into the pipeline."

---

# SECTION 1997 — 30-SECOND QUALITY GATE ANSWER

## 2211.

> "A quality gate is an automated condition that determines whether a change can progress. I use gates for meaningful risks such as compilation, critical regression, security checks or agreed quality thresholds rather than adding arbitrary checks that slow delivery without improving confidence."

---

# SECTION 1998 — COMPLETE PROJECT FUTURE CI ARCHITECTURE

## 2212.

```text
                    GitHub
                       │
                       ▼
                 Pull Request
                       │
                       ▼
                GitHub Actions
                       │
        ┌──────────────┴──────────────┐
        ▼                             ▼
 Backend Build                  Frontend Build
 Java 17                       Node + TypeScript
 Maven                         React/Vite
        │                             │
        ▼                             ▼
 Backend Tests                  Frontend Checks
        │
        ▼
 PostgreSQL Service
        │
        ▼
 Start Backend
        │
        ▼
 Readiness Check
        │
        ▼
 REST Assured + TestNG
        │
        ▼
 API + DB Validation
        │
        ▼
 Allure Results
        │
        ▼
 Future Playwright E2E
        │
        ▼
 Quality Gate
        │
        ▼
 Future Docker Build
        │
        ▼
 Future AWS Deployment
        │
        ▼
 Post-Deployment Validation
```

Not all stages are implemented yet.

This is the target evolution.

---

# SECTION 1999 — CURRENT VS FUTURE

## 2213. CURRENT

```text
Spring Boot backend
PostgreSQL
JWT/RBAC
REST API
REST Assured
TestNG
API + DB validation
48 passing API tests
Allure
Swagger/OpenAPI
Environment switching
Docker PostgreSQL
Git/GitHub
```

## 2214. FUTURE

```text
React + TypeScript
Playwright
Full Dockerization
GitHub Actions
k6
AWS
Additional quality/security tooling where valuable
```

Keep this distinction clear in interviews and README.

---

# SECTION 2000 — FINAL CI/CD MENTAL MODEL

## 2215.

CI/CD is not:

```text
YAML memorization
```

It is:

```text
automating the software delivery feedback loop
```

Senior SDET thinks:

```text
What changed?
      ↓
What risk does it create?
      ↓
Which automated checks provide confidence?
      ↓
Where should those checks run?
      ↓
What evidence should be retained?
      ↓
Should failure block progression?
```

---

# SECTION 2001 — FINAL INTERVIEW STORY

## 2216.

> "My approach to CI/CD is tool-independent. I start by making the automation reproducible locally and externalizing environment configuration. Then I integrate the same execution path into CI, inject secrets securely, validate prerequisites such as database and application readiness, execute the appropriate test layer, preserve sanitized evidence and use risk-based quality gates. I keep pull-request feedback fast and move broader or more expensive regression to scheduled or release stages."

---

# SECTION 2002 — FINAL SENIOR SDET PRINCIPLE

## 2217.

```text
A CI pipeline should answer:

WHAT changed?
WHAT was tested?
WHERE was it tested?
WHAT passed?
WHAT failed?
WHY did it fail?
CAN we safely proceed?
```

If pipeline cannot answer these questions:

```text
it is running automation,
but it is not yet providing strong engineering confidence.
```

---

# END OF PART 10 — CI/CD TOOLING CONNECTION

Next:

PART 11 — SENIOR SDET TOOLING & SYSTEM TROUBLESHOOTING INTERVIEW MASTER SET

Then:

PART 12 — FINAL RAPID REVISION / CHEAT SHEET

---

# PART 11 — SENIOR SDET TOOLING & SYSTEM TROUBLESHOOTING INTERVIEW MASTER SET

# SECTION 2003 — PURPOSE OF THIS PART

## 2218.

Senior SDET interviews usually do not stop at:

```text
What is Maven?
What is Docker?
What is HTTP?
```

They move toward:

```text
The test works locally but fails in CI. What will you do?

API returns 403 instead of 401. How will you investigate?

Container is running but application cannot connect to DB. Why?

Build passed but zero tests executed. Is the pipeline healthy?

Frontend works through Postman but fails in browser. Why?
```

This part connects everything we learned:

```text
Linux
Git
Java
Maven
Spring Boot
HTTP
Networking
Docker
Node/npm
JSON/YAML
Dependencies
CI/CD
Testing
```

---

# SECTION 2004 — SENIOR SDET TROUBLESHOOTING FRAMEWORK

## 2219.

Use this framework:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
VERIFY
   ↓
FIX
   ↓
RETEST
   ↓
PREVENT
```

---

# SECTION 2005 — OBSERVE

## 2220.

First collect evidence.

Ask:

```text
What exactly failed?
Where did it fail?
When did it start?
What changed?
Is it reproducible?
Is everyone affected?
Which environment?
Which commit?
```

Do not immediately change code.

---

# SECTION 2006 — CLASSIFY

## 2221.

Classify failure:

```text
Build
Application
Test
Environment
Network
Database
Authentication
Authorization
Dependency
Container
CI/CD
Configuration
Test Data
```

Correct classification saves debugging time.

---

# SECTION 2007 — ISOLATE

## 2222.

Reduce the problem.

Instead of:

```text
Entire regression failed
```

find:

```text
One API?
One service?
One environment?
One test class?
One dependency?
One user role?
```

---

# SECTION 2008 — VERIFY

## 2223.

Verify assumptions.

Example:

Do not assume:

```text
Backend is running.
```

Verify:

```bash
lsof -i :8080
```

Then:

```bash
curl -i http://localhost:8080/v3/api-docs
```

---

# SECTION 2009 — FIX

## 2224.

Fix the root cause.

Avoid:

```text
random configuration changes
unlimited retries
disabling tests
ignoring certificate validation
hardcoding credentials
```

---

# SECTION 2010 — RETEST

## 2225.

After fix:

```text
reproduce original scenario
```

Then run:

```text
targeted validation
```

followed by:

```text
appropriate regression
```

---

# SECTION 2011 — PREVENT

## 2226.

Ask:

```text
Can automation detect this earlier?
Can CI prevent it?
Can configuration validation catch it?
Can logging improve diagnosis?
Can documentation/runbook help?
```

Senior engineers think beyond immediate fix.

---

# SECTION 2012 — FIRST MEANINGFUL ERROR

## 2227.

Logs often contain:

```text
50 errors
```

But many are consequences.

Find:

```text
first meaningful/root error
```

Example:

```text
ApplicationContext failed
```

may be caused by:

```text
database authentication failure
```

Read:

```text
Caused by:
```

carefully.

---

# SECTION 2013 — INTERVIEW QUESTION: TEST PASSES LOCALLY BUT FAILS IN CI

## 2228.

Strong answer:

> "I first verify that local and CI are testing the same commit, runtime, dependency versions and environment. Then I compare environment variables, target URLs, credentials, test data, network access and test-selection configuration. I also look for hidden local dependencies such as cached artifacts, local files or services. Finally, I reproduce the exact CI command locally where possible and use the first meaningful CI error to classify whether the issue belongs to the application, automation, environment or pipeline."

---

# SECTION 2014 — LOCAL VS CI CHECKLIST

## 2229.

```text
Same Git SHA?
Same Java?
Same Maven behavior?
Same environment?
Same base URL?
Same secrets?
Same DB?
Same test count?
Same dependency versions?
Same network access?
```

---

# SECTION 2015 — JAVA VERSION DIFFERENCE

## 2230.

Local:

```bash
java -version
```

CI:

Check pipeline logs.

If project requires:

```text
Java 17
```

but CI uses incompatible runtime:

Possible:

```text
compile failure
class version error
plugin incompatibility
```

---

# SECTION 2016 — MAVEN VERSION DIFFERENCE

## 2231.

Check:

```bash
mvn -version
```

or where Maven Wrapper is provided:

```bash
./mvnw -version
```

Wrapper helps reduce Maven-version differences.

Our backend provides Maven Wrapper.

---

# SECTION 2017 — ENVIRONMENT DIFFERENCE

## 2232.

Local may load:

```text
.env
```

CI may use:

```text
secret store
environment variables
```

Missing variable can cause:

```text
startup failure
wrong URL
authentication failure
DB failure
```

---

# SECTION 2018 — INTERVIEW QUESTION: MAVEN BUILD FAILS

## 2233.

> "I first identify whether the failure occurs during dependency resolution, compilation, test execution, packaging or plugin execution. I inspect the first relevant error, verify Java and Maven versions, confirm repository access and configuration, and reproduce with the same Maven command. If necessary I use Maven debug or effective configuration output rather than immediately deleting caches."

---

# SECTION 2019 — MAVEN FAILURE CLASSIFICATION

## 2234.

```text
mvn command
   ↓
Dependency resolution?
   ↓
Compilation?
   ↓
Test compilation?
   ↓
Test execution?
   ↓
Packaging?
   ↓
Plugin?
```

---

# SECTION 2020 — DEPENDENCY RESOLUTION FAILURE

## 2235.

Possible:

```text
artifact does not exist
wrong version
repository unavailable
proxy
TLS
authentication
corrupted local artifact
```

Look for messages such as:

```text
Could not resolve artifact
```

---

# SECTION 2021 — COMPILATION FAILURE

## 2236.

Possible:

```text
syntax error
missing class
wrong API usage
Java compatibility
dependency mismatch
```

Do not call this:

```text
test failure
```

Tests may never have started.

---

# SECTION 2022 — TEST COMPILATION FAILURE

## 2237.

Application source may compile.

But:

```text
test source
```

can fail compilation.

Possible:

```text
changed method signature
removed test dependency
invalid import
```

---

# SECTION 2023 — TEST EXECUTION FAILURE

## 2238.

Now tests actually started.

Investigate:

```text
assertion
HTTP response
DB
test data
environment
authentication
```

---

# SECTION 2024 — INTERVIEW QUESTION: BUILD IS GREEN BUT ZERO TESTS EXECUTED

## 2239.

> "I would not consider that a valid regression run. I would inspect test discovery, naming conventions, runner configuration, tags or groups, include/exclude rules and test-result files. A pipeline should validate that the expected tests actually executed, because process success alone does not prove test coverage."

---

# SECTION 2025 — ZERO TEST CAUSES

## 2240.

Possible:

```text
wrong test naming
wrong suite
wrong group/tag
runner misconfiguration
wrong directory
tests excluded
profile disabled tests
```

---

# SECTION 2026 — EXPECTED TEST BASELINE

## 2241.

Current project regression baseline:

```text
48 tests
48 passed
```

Future CI should detect abnormal situations such as:

```text
0 tests
5 tests
unexpected skips
```

when full regression was expected.

---

# SECTION 2027 — INTERVIEW QUESTION: SPRING BOOT DOES NOT START

## 2242.

> "I classify startup failures separately from request-time failures. I inspect the startup logs and nested causes, then verify configuration, required environment variables, database connectivity, port availability and bean creation. I confirm Java and dependency compatibility before changing application code."

---

# SECTION 2028 — SPRING BOOT STARTUP CHECKLIST

## 2243.

```text
Java runtime
Environment variables
Properties
Database
Port
Dependencies
Bean creation
Security configuration
ApplicationContext
```

---

# SECTION 2029 — PORT ALREADY USED

## 2244.

Symptom:

```text
Port 8080 already in use
```

Check:

```bash
lsof -i :8080
```

Identify process before killing anything.

---

# SECTION 2030 — DON'T RANDOMLY KILL PROCESS

## 2245.

Bad:

```bash
kill -9 <PID>
```

immediately.

Better:

```text
identify process
understand ownership
stop gracefully
escalate only if needed
```

---

# SECTION 2031 — DATABASE STARTUP FAILURE

## 2246.

Possible:

```text
PostgreSQL not running
wrong host
wrong port
wrong DB
wrong credentials
network issue
schema problem
```

Check local Docker:

```bash
docker compose ps
```

---

# SECTION 2032 — DATABASE CONTAINER LOGS

## 2247.

```bash
docker compose logs postgres
```

Useful for:

```text
startup
authentication
initialization
database errors
```

---

# SECTION 2033 — INTERVIEW QUESTION: CONTAINER RUNNING BUT APP CANNOT CONNECT

## 2248.

> "A running container only proves the container process is running; it does not prove the service is healthy or reachable from the application. I verify container health and logs, host and port configuration, network context, credentials and whether the application is connecting from the host or another container. I also remember that localhost means different things inside and outside containers."

---

# SECTION 2034 — LOCALHOST CONTAINER TRAP

## 2249.

On Mac host:

```text
localhost
```

means:

```text
Mac
```

Inside backend container:

```text
localhost
```

means:

```text
backend container itself
```

Therefore:

```text
backend container → localhost:5432
```

does not automatically mean PostgreSQL container.

---

# SECTION 2035 — DOCKER COMPOSE SERVICE NAME

## 2250.

In a Compose network, containers can generally communicate using:

```text
service name
```

Example conceptual:

```text
postgres:5432
```

instead of:

```text
localhost:5432
```

when backend and DB are both containers in the same Compose network.

---

# SECTION 2036 — HOST-TO-CONTAINER

## 2251.

Current local PostgreSQL mapping:

```text
5432:5432
```

allows host-side application to reach:

```text
localhost:5432
```

assuming configuration and container state are correct.

---

# SECTION 2037 — INTERVIEW QUESTION: DOCKER IMAGE VS CONTAINER

## 2252.

> "An image is an immutable packaged template containing the application and its runtime dependencies. A container is a running instance created from an image. Multiple containers can be created from the same image."

---

# SECTION 2038 — INTERVIEW QUESTION: CONTAINER VS VM

## 2253.

> "A virtual machine includes a guest operating system, while containers share the host kernel and isolate processes at the operating-system level. Containers are generally lighter and faster to start, although the isolation model and operational characteristics differ."

---

# SECTION 2039 — INTERVIEW QUESTION: WHAT IS A DOCKER VOLUME?

## 2254.

> "A Docker volume provides persistent storage independent of the lifecycle of an individual container. It is useful for stateful data such as a PostgreSQL database because deleting and recreating a container should not necessarily delete the database data."

---

# SECTION 2040 — VOLUME TROUBLESHOOTING

## 2255.

Persistent volume can also preserve:

```text
old state
old users
old database initialization
```

Therefore:

```text
changing Compose environment variables
```

does not necessarily reinitialize an existing PostgreSQL data volume.

---

# SECTION 2041 — INTERVIEW QUESTION: API RETURNS 401

## 2256.

> "401 normally indicates that authentication is missing, invalid or no longer accepted. I verify whether the request contains the expected authentication mechanism, whether the token is valid and unexpired, and whether the request reached the correct environment. I distinguish that from authorization, which is typically represented by 403."

---

# SECTION 2042 — INTERVIEW QUESTION: API RETURNS 403

## 2257.

> "403 means the server understood the request and the authenticated identity does not have sufficient permission for the operation. I verify the user role or authority, endpoint security rules and token claims rather than treating it as a login failure."

---

# SECTION 2043 — CURRENT RBAC EXAMPLE

## 2258.

Current expected behavior:

```text
USER
GET product
→ allowed

USER
POST product
→ 403

ADMIN
POST product
→ allowed
```

This is useful real project evidence for authorization testing.

---

# SECTION 2044 — 401 VS 403

## 2259.

```text
401
→ Who are you / authentication problem

403
→ I know who you are, but operation is forbidden
```

Simplified mental model.

---

# SECTION 2045 — INTERVIEW QUESTION: 404

## 2260.

Possible:

```text
wrong path
wrong base URL
wrong API version
wrong HTTP mapping
resource does not exist
gateway/proxy routing
```

Do not automatically conclude:

```text
backend is down
```

---

# SECTION 2046 — INTERVIEW QUESTION: 405

## 2261.

```text
Method Not Allowed
```

Example:

Endpoint supports:

```text
GET
```

but client sends:

```text
POST
```

Verify:

```text
HTTP method
route mapping
```

---

# SECTION 2047 — 415

## 2262.

```text
Unsupported Media Type
```

Common cause:

```text
incorrect Content-Type
```

Example:

Server expects:

```text
application/json
```

but request sends incompatible body/media type.

---

# SECTION 2048 — 429

## 2263.

```text
Too Many Requests
```

Possible:

```text
rate limiting
quota
throttling
```

Automation should not blindly retry aggressively.

Respect:

```text
server policy
Retry-After where applicable
bounded retry strategy
```

---

# SECTION 2049 — 500

## 2264.

```text
Internal Server Error
```

Means server-side processing failed.

Tester should capture:

```text
request
response
timestamp
correlation ID
environment
```

Then investigate backend evidence where accessible.

---

# SECTION 2050 — 502

## 2265.

```text
Bad Gateway
```

Often:

```text
proxy/gateway received invalid/no usable response from upstream
```

Possible:

```text
backend unavailable
upstream connection failure
bad gateway routing
```

---

# SECTION 2051 — 503

## 2266.

```text
Service Unavailable
```

Possible:

```text
service down
overloaded
maintenance
dependency unavailable
```

---

# SECTION 2052 — 504

## 2267.

```text
Gateway Timeout
```

Gateway/proxy did not receive upstream response within expected time.

Investigate:

```text
upstream latency
network
dependency latency
timeout configuration
```

---

# SECTION 2053 — INTERVIEW QUESTION: POSTMAN WORKS BUT BROWSER FAILS

## 2268.

One important possibility:

```text
CORS
```

Browsers enforce CORS.

Tools such as:

```text
curl
Postman
REST Assured
```

are not governed by browser CORS enforcement in the same way.

---

# SECTION 2054 — CORS ORIGIN

## 2269.

Origin consists of:

```text
scheme + host + port
```

Example:

```text
http://localhost:5173
```

and:

```text
http://localhost:8080
```

are different origins because ports differ.

---

# SECTION 2055 — FUTURE FRONTEND EXAMPLE

## 2270.

Future React:

```text
http://localhost:5173
```

Backend:

```text
http://localhost:8080
```

Browser request becomes:

```text
cross-origin
```

CORS configuration may therefore be required.

Frontend is not implemented yet.

---

# SECTION 2056 — PREFLIGHT

## 2271.

Browser may send:

```text
OPTIONS
```

before actual request.

This is:

```text
CORS preflight
```

Server response may need appropriate:

```text
Access-Control-Allow-*
```

headers.

---

# SECTION 2057 — INTERVIEW QUESTION: WHAT IS DNS?

## 2272.

> "DNS translates human-readable hostnames into IP addresses. During troubleshooting I distinguish DNS resolution failure from TCP connectivity failure because the application cannot connect to a host if it cannot first resolve the hostname."

---

# SECTION 2058 — DNS COMMANDS

## 2273.

```bash
nslookup example.com
```

or:

```bash
dig example.com
```

Useful for checking DNS resolution.

---

# SECTION 2059 — DNS FAILURE VS CONNECTION REFUSED

## 2274.

DNS failure:

```text
hostname cannot be resolved
```

Connection refused:

```text
host resolved/reached
but target port is not accepting connection
```

Different failure layers.

---

# SECTION 2060 — CONNECTION TIMEOUT

## 2275.

Timeout can mean:

```text
connection could not be established in time
```

Possible:

```text
firewall
network route
unreachable host
service/network issue
```

Do not confuse with:

```text
HTTP 504
```

which is an HTTP response from gateway.

---

# SECTION 2061 — READ TIMEOUT

## 2276.

Connection may succeed.

But server does not return response within configured read timeout.

Investigate:

```text
slow backend
slow DB
dependency latency
timeout too aggressive
```

---

# SECTION 2062 — INTERVIEW QUESTION: CURL `-v`

## 2277.

```bash
curl -v <URL>
```

shows verbose connection/request details.

Useful for:

```text
DNS
connection
TLS
headers
redirect/debugging
```

Be careful because verbose output can expose sensitive headers.

---

# SECTION 2063 — CURL HEADERS

## 2278.

Example safe placeholder:

```bash
curl -i \
  -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8080/api/example
```

Never paste real tokens into notes or screenshots.

---

# SECTION 2064 — CURL JSON

## 2279.

Concept:

```bash
curl -i \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"example":"value"}' \
  http://localhost:8080/api/example
```

Useful for isolating:

```text
API behavior
```

from:

```text
automation framework
```

---

# SECTION 2065 — WHY CURL IS IMPORTANT FOR SDET

## 2280.

Suppose REST Assured test fails.

Try equivalent request using:

```text
curl
```

If curl also fails:

```text
likely API/environment/config issue
```

If curl works:

```text
inspect automation request construction/configuration
```

This isolates layers.

---

# SECTION 2066 — INTERVIEW QUESTION: HTTP VS HTTPS

## 2281.

> "HTTP transfers application data without TLS protection, while HTTPS is HTTP over TLS. TLS provides encrypted transport and server identity validation through certificates, and it can also support client authentication in specific configurations."

---

# SECTION 2067 — TLS CERTIFICATE ERROR

## 2282.

Possible:

```text
expired certificate
hostname mismatch
untrusted CA
incomplete certificate chain
wrong trust store
```

Do not solve production TLS issues by permanently disabling certificate validation.

---

# SECTION 2068 — `curl -k`

## 2283.

```bash
curl -k
```

disables certificate verification.

It can sometimes help as a controlled diagnostic comparison.

But:

```text
it is NOT the real fix
```

for certificate problems.

---

# SECTION 2069 — INTERVIEW QUESTION: WHAT IS PROXY?

## 2284.

Forward proxy:

```text
Client
 ↓
Proxy
 ↓
Internet/Server
```

Often used for:

```text
outbound access
filtering
security
corporate network control
```

---

# SECTION 2070 — REVERSE PROXY

## 2285.

```text
Client
 ↓
Reverse Proxy
 ↓
Backend Service
```

Client interacts with proxy endpoint.

Reverse proxy forwards to backend.

Can provide:

```text
routing
TLS termination
load balancing
caching
```

depending on configuration.

---

# SECTION 2071 — API GATEWAY

## 2286.

API gateway can provide capabilities such as:

```text
routing
authentication
rate limiting
observability
policy enforcement
```

Architecture varies.

Do not assume every reverse proxy is a full API gateway.

---

# SECTION 2072 — LOAD BALANCER

## 2287.

Load balancer distributes traffic among multiple service instances.

Concept:

```text
Client
  ↓
Load Balancer
  ↓
Backend A
Backend B
Backend C
```

---

# SECTION 2073 — INTERVIEW QUESTION: API SLOW

## 2288.

Strong answer:

> "I first measure where the latency occurs instead of assuming the application is slow. I compare DNS, connection and server-response timing, inspect backend and database evidence, check downstream dependencies and determine whether the slowdown is isolated to one endpoint, environment or data set. I also compare recent changes and baseline response times."

---

# SECTION 2074 — API LATENCY LAYERS

## 2289.

```text
DNS
 ↓
TCP connection
 ↓
TLS
 ↓
Gateway
 ↓
Backend
 ↓
Database
 ↓
External dependency
 ↓
Response
```

Latency can occur at any layer.

---

# SECTION 2075 — CURL TIMING

## 2290.

`curl` supports timing output through:

```text
-w
```

Useful for investigating:

```text
connection time
total time
```

Exact format can be customized.

---

# SECTION 2076 — INTERVIEW QUESTION: RETRY STRATEGY

## 2291.

> "I use retries only for clearly transient failures and keep them bounded. I do not retry deterministic validation failures such as 400, 401, 403 or assertion defects simply to make the pipeline green. Excessive retries hide instability and increase execution time."

---

# SECTION 2077 — GOOD RETRY CANDIDATES

## 2292.

Potential transient scenarios:

```text
temporary network interruption
short-lived service unavailability
eventual consistency where contract permits
rate-limit retry when explicitly supported
```

Even then:

```text
bounded
observable
intentional
```

---

# SECTION 2078 — BAD RETRY CANDIDATES

## 2293.

```text
wrong password
403 authorization failure
invalid request
broken locator
wrong expected result
product defect
```

Retry will not fix deterministic problems.

---

# SECTION 2079 — INTERVIEW QUESTION: WHAT IS IDEMPOTENCY?

## 2294.

> "An idempotent operation can be repeated with the same intended effect on server state as executing it once. GET, PUT and DELETE are defined as idempotent by HTTP semantics, while POST is not inherently idempotent."

---

# SECTION 2080 — IDEMPOTENCY TESTING

## 2295.

For idempotent operations test:

```text
send same request repeatedly
```

Verify:

```text
server state remains consistent
```

according to API contract.

---

# SECTION 2081 — SAFE HTTP METHOD

## 2296.

Safe method means intended to:

```text
retrieve information
```

without requesting state change.

Examples:

```text
GET
HEAD
```

Safe and idempotent are related but different concepts.

---

# SECTION 2082 — PUT VS PATCH

## 2297.

General distinction:

```text
PUT
→ replace/update complete representation semantics

PATCH
→ partial modification
```

Actual API contract determines exact expected behavior.

---

# SECTION 2083 — POST

## 2298.

Commonly used for:

```text
create resource
submit operation
trigger processing
```

POST is not inherently idempotent.

---

# SECTION 2084 — INTERVIEW QUESTION: CONTENT-TYPE VS ACCEPT

## 2299.

```text
Content-Type
→ format of body being sent

Accept
→ response formats client can accept
```

Example:

```http
Content-Type: application/json
Accept: application/json
```

---

# SECTION 2085 — PATH PARAM VS QUERY PARAM

## 2300.

Path:

```text
/products/123
```

`123` identifies resource.

Query:

```text
/products?name=phone
```

often controls:

```text
filtering
search
sorting
pagination
```

Exact semantics depend on API design.

---

# SECTION 2086 — INTERVIEW QUESTION: REST ASSURED TEST FAILS WITH WRONG BASE URL

## 2301.

Check:

```text
TEST_ENV
BASE_URL override
QA_BASE_URL
STAGE_BASE_URL
```

Our framework already supports environment switching.

Do not hardcode URL in every test.

---

# SECTION 2087 — ENVIRONMENT SWITCHING BENEFIT

## 2302.

Same automation:

```text
Local
QA
Stage
```

should be reusable through configuration.

This improves:

```text
maintainability
CI integration
portability
```

---

# SECTION 2088 — INTERVIEW QUESTION: WHAT IS TEST DATA ISOLATION?

## 2303.

> "Test data isolation means one test should not unintentionally depend on or corrupt another test's data. I prefer creating predictable test-specific data and cleaning it when appropriate so tests remain repeatable and safer for parallel execution."

---

# SECTION 2089 — SHARED TEST DATA PROBLEM

## 2304.

Example:

```text
Test A modifies product
Test B expects old product
```

Result:

```text
order-dependent failure
```

This is poor isolation.

---

# SECTION 2090 — TEST ORDER DEPENDENCY

## 2305.

Bad:

```text
Test 2 only passes if Test 1 ran first
```

Unless explicitly testing a single intentional end-to-end sequence.

Independent tests should establish their own prerequisites.

---

# SECTION 2091 — INTERVIEW QUESTION: API TESTING VS UNIT TESTING

## 2306.

> "Unit tests validate small pieces of code in isolation and provide very fast developer feedback. API tests validate behavior through the service interface and can cover routing, validation, security, serialization, business logic and integration. They operate at different layers and complement each other."

---

# SECTION 2092 — API VS UI AUTOMATION

## 2307.

API:

```text
faster
less UI-dependent
good business/service coverage
```

UI:

```text
validates real user interaction
browser behavior
frontend/backend integration
```

Strong strategy uses both appropriately.

---

# SECTION 2093 — INTERVIEW QUESTION: WHY MORE API TESTS THAN UI?

## 2308.

> "API tests generally provide faster and more stable coverage of business rules and service behavior. I keep UI automation focused on critical user journeys and frontend-specific risks. This gives better feedback speed and lower maintenance while still validating end-to-end behavior."

---

# SECTION 2094 — INTERVIEW QUESTION: DB VALIDATION IN API TESTING

## 2309.

> "I use database validation when persistence itself is part of the risk I need to verify — for example whether an order or payment state was correctly stored. I avoid coupling every API test directly to implementation details because excessive DB assertions can make tests brittle."

---

# SECTION 2095 — CURRENT PROJECT DB VALIDATION

## 2310.

Our API automation includes DB validations for important flows.

Examples conceptually:

```text
Product persistence
Cart state
Order state
Payment state
```

This provides integration-level confidence.

---

# SECTION 2096 — INTERVIEW QUESTION: DATABASE TEST PASSES BUT API FAILS

## 2311.

Possible:

```text
controller
serialization
security
business logic
HTTP mapping
response construction
```

DB correctness alone does not prove API correctness.

---

# SECTION 2097 — API PASSES BUT DB WRONG

## 2312.

Possible:

```text
API returned optimistic response
transaction issue
wrong persistence
wrong database
async persistence issue
```

Verify both:

```text
external contract
internal persistence
```

where risk requires.

---

# SECTION 2098 — TRANSACTION

## 2313.

Transaction groups database operations into a logical unit.

Important concept:

```text
all-or-nothing behavior
```

depending on transaction boundaries and failure handling.

---

# SECTION 2099 — INTERVIEW QUESTION: WHAT IS RACE CONDITION?

## 2314.

> "A race condition occurs when system behavior depends on the timing or ordering of concurrent operations. It can cause intermittent failures such as duplicate updates, incorrect inventory or conflicting state."

---

# SECTION 2100 — RACE CONDITION TESTING

## 2315.

Potential tests:

```text
simultaneous updates
multiple purchases
duplicate submissions
concurrent login/session behavior
```

Need controlled concurrency.

---

# SECTION 2101 — INTERVIEW QUESTION: FLAKY TEST

## 2316.

> "A flaky test produces inconsistent results without a meaningful application change. I investigate synchronization, shared data, environment instability, external dependencies, timing assumptions and parallel execution before adding retries."

---

# SECTION 2102 — COMMON FLAKINESS CAUSES

## 2317.

```text
hard waits
shared data
unstable selectors
async behavior
network
environment
parallel collisions
clock/timezone
external dependency
```

---

# SECTION 2103 — HARD WAIT

## 2318.

Example:

```text
sleep 5000
```

Problem:

```text
sometimes too long
sometimes not long enough
```

Prefer:

```text
condition-based synchronization
```

where framework supports it.

---

# SECTION 2104 — PLAYWRIGHT FUTURE ADVANTAGE

## 2319.

Playwright provides automatic waiting for many actions.

But:

```text
auto-waiting does not remove need to understand application state
```

Poor test design can still be flaky.

---

# SECTION 2105 — INTERVIEW QUESTION: AUTOMATION FRAMEWORK DESIGN

## 2320.

Strong answer:

> "I separate configuration, reusable clients or page abstractions, test data, authentication, assertions, reporting and tests. I avoid duplicating environment URLs and credentials inside test classes. The framework should be readable, independently executable, CI-friendly and easy to debug."

---

# SECTION 2106 — CURRENT API FRAMEWORK DESIGN

## 2321.

Current framework includes:

```text
RequestSpecFactory
AuthHelper
BaseTest
TestDataFactory
cleanup
environment switching
Allure integration
sanitized request/response attachments
```

This is good interview material because it demonstrates framework-level thinking.

---

# SECTION 2107 — INTERVIEW QUESTION: WHY REQUEST SPECIFICATION?

## 2322.

> "A reusable request specification centralizes common API configuration such as base URI, headers, content type and filters. It reduces duplication and makes environment or reporting changes easier to apply consistently."

---

# SECTION 2108 — INTERVIEW QUESTION: WHY AUTH HELPER?

## 2323.

> "Authentication is cross-cutting test setup, so I keep token creation and credential handling out of individual test methods. A reusable authentication helper improves readability and makes environment-specific authentication easier to manage."

---

# SECTION 2109 — INTERVIEW QUESTION: WHY BASE TEST?

## 2324.

> "A base test can centralize common setup such as environment initialization or reusable authenticated contexts. I keep it focused and avoid turning it into a large inheritance hierarchy containing unrelated behavior."

---

# SECTION 2110 — INTERVIEW QUESTION: REPORTING

## 2325.

> "A useful automation report should help diagnose failure, not only show pass or fail. I include relevant environment metadata and request-response evidence while sanitizing credentials and sensitive headers."

---

# SECTION 2111 — ALLURE SECURITY STORY

## 2326.

Current implementation masks:

```text
Authorization
Cookie
Set-Cookie
X-API-Key
API-Key
Bearer tokens
password-like JSON fields
token-like JSON fields
secret-like JSON fields
```

while leaving the actual backend request unchanged.

---

# SECTION 2112 — INTERVIEW QUESTION: WHY SANITIZE REPORTS?

## 2327.

> "Reports are often uploaded as CI artifacts and shared widely. If they contain authentication tokens or passwords, the test framework can become a security leak. I sanitize evidence before attachment rather than assuming repository security is sufficient."

---

# SECTION 2113 — INTERVIEW QUESTION: SWAGGER VS AUTOMATION

## 2328.

> "Swagger/OpenAPI is useful for understanding and manually exploring an API contract, while automated tests continuously validate expected behavior. Swagger does not replace regression automation, and automation does not remove the value of API documentation."

---

# SECTION 2114 — OPENAPI

## 2329.

OpenAPI describes API structure such as:

```text
paths
operations
parameters
request bodies
responses
schemas
security
```

Swagger UI renders an interactive interface from the OpenAPI description.

---

# SECTION 2115 — CURRENT SWAGGER

## 2330.

Current backend exposes:

```text
/swagger-ui/index.html
```

and:

```text
/v3/api-docs
```

JWT bearer authorization is configured for Swagger UI.

---

# SECTION 2116 — INTERVIEW QUESTION: API CONTRACT CHANGED

## 2331.

Process:

```text
confirm intended change
update contract/documentation
identify affected tests
update implementation/tests
run regression
check consumers
```

Do not blindly change tests just because they fail.

First determine:

```text
bug or intended requirement change?
```

---

# SECTION 2117 — TEST FAILURE AFTER REQUIREMENT CHANGE

## 2332.

```text
Old expectation
≠
new requirement
```

Then test may be outdated.

But verify requirement before updating assertion.

Otherwise automation can accidentally hide product regression.

---

# SECTION 2118 — INTERVIEW QUESTION: BUG OR AUTOMATION ISSUE?

## 2333.

> "I reproduce the behavior outside the automation framework where possible, using the API client, browser or direct database evidence. I compare actual behavior with the agreed requirement and isolate whether the discrepancy exists in the product or only in the test implementation."

---

# SECTION 2119 — ISOLATION EXAMPLE

## 2334.

```text
REST Assured fails
   ↓
Try curl/Swagger
   ↓
Also fails?
   |
   ├── YES → product/environment/config
   |
   └── NO → automation/framework/request construction
```

---

# SECTION 2120 — INTERVIEW QUESTION: DEFECT PRIORITY VS SEVERITY

## 2335.

Severity:

```text
technical/business impact
```

Priority:

```text
how urgently it should be fixed
```

They influence each other but are not identical.

---

# SECTION 2121 — HIGH SEVERITY LOW PRIORITY EXAMPLE

## 2336.

Example conceptual:

```text
major failure in rarely used legacy feature
```

Impact may be high when encountered.

Business may still schedule it later.

Context matters.

---

# SECTION 2122 — LOW SEVERITY HIGH PRIORITY

## 2337.

Example:

```text
small visible issue on a major launch page
```

Technical impact small.

Business urgency high.

---

# SECTION 2123 — INTERVIEW QUESTION: RELEASE BLOCKER

## 2338.

Do not decide only from:

```text
number of failed tests
```

Consider:

```text
critical journey
security
data integrity
regulatory risk
customer impact
workaround
failure scope
```

---

# SECTION 2124 — 1 TEST CAN BLOCK RELEASE

## 2339.

Example:

```text
ADMIN authorization bypass
```

One failing security test can be more serious than:

```text
20 cosmetic failures
```

Risk-based testing.

---

# SECTION 2125 — 20 FAILURES CAN BE ONE ROOT CAUSE

## 2340.

Example:

```text
authentication service unavailable
```

causes:

```text
20 downstream test failures
```

Do not report 20 independent product defects.

Find common cause.

---

# SECTION 2126 — INTERVIEW QUESTION: PRODUCTION DEFECT ESCAPED

## 2341.

Senior response:

> "I first focus on impact and containment rather than blame. After resolution I perform root-cause analysis across requirement, implementation, test coverage, environment and release process. Then I identify the smallest effective prevention — for example adding regression coverage, improving a quality gate or improving observability."

---

# SECTION 2127 — RCA

## 2342.

RCA:

```text
Root Cause Analysis
```

Ask:

```text
Why did defect happen?
Why was it not detected?
Why did process allow release?
How can recurrence be reduced?
```

---

# SECTION 2128 — FIVE WHYS

## 2343.

Simple technique:

```text
Why?
 ↓
Why?
 ↓
Why?
 ↓
Why?
 ↓
Why?
```

Goal:

```text
move beyond symptom
```

Do not force exactly five if root cause becomes clear earlier.

---

# SECTION 2129 — INTERVIEW QUESTION: AUTOMATE EVERYTHING?

## 2344.

> "No. I automate where repeatability, regression value, execution frequency and risk justify the maintenance cost. One-time exploratory checks, rapidly changing features or scenarios requiring subjective human judgment may not be good automation candidates."

---

# SECTION 2130 — GOOD AUTOMATION CANDIDATES

## 2345.

```text
repeatable
stable enough
high regression value
data-driven
critical business flow
frequently executed
```

---

# SECTION 2131 — POOR AUTOMATION CANDIDATES

## 2346.

Potential:

```text
one-time validation
highly unstable prototype
subjective visual judgment
low-value rare scenario
```

unless risk justifies it.

---

# SECTION 2132 — INTERVIEW QUESTION: WHAT TO AUTOMATE FIRST?

## 2347.

Prioritize:

```text
business criticality
regression frequency
manual effort
defect risk
stability
automation ROI
```

Not:

```text
easiest tests first only
```

---

# SECTION 2133 — AUTOMATION ROI

## 2348.

Consider:

```text
build cost
maintenance cost
execution frequency
manual savings
risk reduction
feedback speed
```

Automation is engineering investment.

---

# SECTION 2134 — INTERVIEW QUESTION: TEST PYRAMID

## 2349.

> "The test pyramid recommends a larger number of fast lower-level tests, fewer service or integration tests above them and a smaller number of expensive end-to-end UI tests. I use it as a design principle rather than a rigid percentage."

---

# SECTION 2135 — OUR PROJECT PYRAMID

## 2350.

```text
       UI
     /    \
    /      \
   API / Integration
  /                \
 Unit / Component
```

Our portfolio focus:

```text
strong API/integration automation
+
critical UI later
```

---

# SECTION 2136 — INTERVIEW QUESTION: SHIFT LEFT

## 2351.

> "Shift left means moving useful quality feedback earlier in the development lifecycle. Examples include requirement review, API contract validation, developer tests, pull-request automation and static analysis. It does not mean moving every test to the earliest stage."

---

# SECTION 2137 — SHIFT RIGHT

## 2352.

> "Shift right extends quality practices into deployed environments through monitoring, synthetic checks, canary validation and production observability. It complements shift-left testing rather than replacing it."

---

# SECTION 2138 — INTERVIEW QUESTION: QUALITY ENGINEERING

## 2353.

> "Quality engineering is broader than executing test cases. It focuses on building quality into requirements, architecture, automation, CI/CD, observability, testability and release decisions so feedback is continuous across the delivery lifecycle."

---

# SECTION 2139 — QA VS QE

## 2354.

Simplified:

```text
Traditional QA
→ validate product

Quality Engineering
→ engineer systems/processes that continuously create quality feedback
```

Real organizations may use the terms differently.

---

# SECTION 2140 — SENIOR SDET EXPECTATION

## 2355.

Senior SDET should discuss:

```text
architecture
risk
framework
CI
debugging
test strategy
security
performance
observability
mentoring
trade-offs
```

Not only:

```text
Selenium syntax
```

---

# SECTION 2141 — INTERVIEW QUESTION: HOW DO YOU DESIGN TEST STRATEGY?

## 2356.

> "I start with business-critical workflows, architecture, integration points and failure risks. Then I decide which risks belong at unit, API, integration, UI, database, security or performance layers. I define environments, test data, automation scope, CI execution stages, reporting and release gates while keeping feedback speed and maintenance cost in mind."

---

# SECTION 2142 — TEST STRATEGY COMPONENTS

## 2357.

```text
Scope
Risk
Test levels
Automation
Environment
Data
Non-functional
CI/CD
Reporting
Entry/exit criteria
Defect process
Release validation
```

---

# SECTION 2143 — INTERVIEW QUESTION: ENTRY CRITERIA

## 2358.

Possible:

```text
build deployed
environment available
required dependencies available
test data ready
requirements understood
```

Exact criteria depend on team.

---

# SECTION 2144 — EXIT CRITERIA

## 2359.

Possible:

```text
critical tests passed
acceptable defect status
regression completed
risk reviewed
required evidence available
```

Not simply:

```text
100% tests passed
```

in every organization.

---

# SECTION 2145 — INTERVIEW QUESTION: RISK-BASED TESTING

## 2360.

> "Risk-based testing prioritizes coverage based on probability and impact of failure. I give deeper and earlier coverage to areas such as authentication, payment, order state, authorization and data integrity rather than distributing equal effort across every feature."

---

# SECTION 2146 — CURRENT PROJECT HIGH-RISK AREAS

## 2361.

Examples:

```text
Authentication
RBAC
Orders
Payment
Stock
Database state
```

These deserve strong automated coverage.

---

# SECTION 2147 — INTERVIEW QUESTION: PAYMENT TESTING

## 2362.

For a real payment system consider:

```text
success
failure
timeout
duplicate submission
idempotency
currency
amount
authorization
callback/webhook
security
reconciliation
```

Our current project uses:

```text
mock payment
```

Do not present it as a real payment gateway integration.

---

# SECTION 2148 — INTERVIEW QUESTION: ORDER TESTING

## 2363.

Important:

```text
create
retrieve
ownership
stock decrement
cart state
cancel
stock restoration
invalid state transitions
authorization
persistence
```

Current project already covers important order behaviors.

---

# SECTION 2149 — STATE TRANSITION TESTING

## 2364.

Example:

```text
CREATED
 ↓
PAID
 ↓
COMPLETED
```

or:

```text
CREATED
 ↓
CANCELLED
```

Test invalid transitions too.

Actual states depend on system contract.

---

# SECTION 2150 — INTERVIEW QUESTION: DUPLICATE REQUEST

## 2365.

Test:

```text
same request sent twice
```

Questions:

```text
duplicate resource?
same result?
409?
idempotency key?
```

Expected behavior depends on API design.

---

# SECTION 2151 — INTERVIEW QUESTION: 409

## 2366.

```text
Conflict
```

Can represent:

```text
duplicate resource
state conflict
version conflict
```

depending on API contract.

---

# SECTION 2152 — INTERVIEW QUESTION: PAGINATION

## 2367.

Validate:

```text
page size
page number/cursor
first page
last page
empty page
duplicates
missing records
sorting consistency
invalid values
```

---

# SECTION 2153 — SORTING

## 2368.

Test:

```text
ascending
descending
duplicate values
null values
case sensitivity
numeric vs lexical behavior
```

according to contract.

---

# SECTION 2154 — SEARCH

## 2369.

Test:

```text
exact
partial
case
spaces
special characters
no result
multiple results
```

according to requirements.

---

# SECTION 2155 — VALIDATION TESTING

## 2370.

Boundary thinking:

```text
minimum - 1
minimum
minimum + 1

maximum - 1
maximum
maximum + 1
```

Useful for:

```text
length
quantity
price
pagination
```

---

# SECTION 2156 — EQUIVALENCE PARTITIONING

## 2371.

Divide inputs into groups expected to behave similarly.

Example:

```text
valid quantity
invalid zero
invalid negative
invalid above stock
```

Test representative values.

---

# SECTION 2157 — BOUNDARY VALUE ANALYSIS

## 2372.

Defects often occur at boundaries.

If valid:

```text
1–100
```

test around:

```text
0
1
2
99
100
101
```

---

# SECTION 2158 — DECISION TABLE

## 2373.

Useful when behavior depends on combinations.

Example:

```text
Authenticated?
Admin?
Product exists?
```

Different combinations produce different results.

---

# SECTION 2159 — STATE TRANSITION

## 2374.

Useful when system has states:

```text
Order
Payment
Account
Workflow
```

Validate:

```text
valid transitions
invalid transitions
repeated transitions
```

---

# SECTION 2160 — INTERVIEW QUESTION: NEGATIVE TESTING

## 2375.

> "Negative testing verifies that the system handles invalid inputs, unauthorized actions, invalid states and dependency failures safely and predictably. I validate both the status or error contract and that invalid requests do not corrupt system state."

---

# SECTION 2161 — NEGATIVE API EXAMPLES

## 2376.

```text
missing field
invalid field
invalid token
wrong role
nonexistent resource
duplicate request
invalid quantity
malformed JSON
unsupported media type
```

---

# SECTION 2162 — ERROR CONTRACT

## 2377.

Validate:

```text
status
error code
message
field errors
response schema
```

But avoid asserting fragile wording unless wording is contractually important.

---

# SECTION 2163 — INTERVIEW QUESTION: SCHEMA VALIDATION

## 2378.

> "Schema validation checks that the response structure and data types conform to the expected contract. I combine it with business assertions because a structurally valid response can still contain incorrect data."

---

# SECTION 2164 — SCHEMA PASS BUT BUSINESS FAIL

## 2379.

Example:

```json
{
  "price": -100
}
```

Schema may allow:

```text
number
```

but business rule may forbid negative price.

Need:

```text
schema + business validation
```

---

# SECTION 2165 — INTERVIEW QUESTION: MOCKING

## 2380.

> "Mocking replaces a dependency with controlled behavior so a component can be tested independently. It is useful for deterministic lower-level tests or unavailable dependencies, but I still keep integration tests against real components where integration risk matters."

---

# SECTION 2166 — MOCK VS REAL DEPENDENCY

## 2381.

Mock:

```text
fast
controlled
isolated
```

Real integration:

```text
more realistic
catches integration/configuration issues
```

Use both at appropriate layers.

---

# SECTION 2167 — INTERVIEW QUESTION: CONTRACT TESTING

## 2382.

Contract testing validates assumptions between:

```text
consumer
and
provider
```

Useful in distributed systems where independent services evolve.

It helps detect:

```text
breaking interface changes
```

before full end-to-end testing.

---

# SECTION 2168 — INTERVIEW QUESTION: MICROSERVICES TESTING

## 2383.

Consider:

```text
service-level API
contract
integration
database ownership
messaging
failure handling
observability
end-to-end critical paths
```

Avoid relying entirely on massive E2E suites.

---

# SECTION 2169 — DISTRIBUTED SYSTEM FAILURE

## 2384.

Possible:

```text
Service A succeeds
Service B times out
message delayed
duplicate event
partial failure
```

Testing must consider:

```text
resilience
retries
idempotency
eventual consistency
```

---

# SECTION 2170 — EVENTUAL CONSISTENCY

## 2385.

In distributed systems:

```text
write occurs
```

but all readers may not immediately see final state.

Test should use:

```text
bounded polling
```

when eventual consistency is part of contract.

Not:

```text
fixed long sleep
```

---

# SECTION 2171 — BOUNDED POLLING

## 2386.

Concept:

```text
check condition
 ↓
not ready
 ↓
wait short interval
 ↓
retry until timeout
```

If timeout reached:

```text
fail with evidence
```

---

# SECTION 2172 — INTERVIEW QUESTION: KAFKA TESTING

## 2387.

Kafka is planned for later, not current implementation.

When introduced, testing may include:

```text
event produced
topic
key
payload
schema
consumer behavior
duplicate handling
ordering assumptions
retry/DLQ
```

Do not claim current Kafka implementation.

---

# SECTION 2173 — REDIS TESTING

## 2388.

Redis is also future scope.

Potential testing:

```text
cache hit
cache miss
TTL
invalidation
stale data
fallback
```

Do not add now simply for résumé keywords.

---

# SECTION 2174 — INTERVIEW QUESTION: PERFORMANCE TESTING

## 2389.

> "Performance testing evaluates behavior under expected and stressful workloads. I define workload and success criteria first, then measure response-time percentiles, throughput, error rate and resource behavior. I avoid treating a single average response time as sufficient evidence."

---

# SECTION 2175 — PERFORMANCE TYPES

## 2390.

```text
Load
Stress
Spike
Soak
Volume
```

Different goals.

---

# SECTION 2176 — LOAD TEST

## 2391.

Validate system under:

```text
expected workload
```

Question:

```text
Can system meet requirements under normal/peak expected load?
```

---

# SECTION 2177 — STRESS TEST

## 2392.

Push beyond expected capacity.

Goal:

```text
find limits
observe degradation/recovery
```

---

# SECTION 2178 — SPIKE TEST

## 2393.

Sudden workload increase/decrease.

Useful for:

```text
flash sale
sudden traffic
event launch
```

---

# SECTION 2179 — SOAK TEST

## 2394.

Run sustained workload for extended period.

Can expose:

```text
memory leaks
connection leaks
resource exhaustion
gradual degradation
```

---

# SECTION 2180 — PERFORMANCE METRICS

## 2395.

Important:

```text
p50
p90
p95
p99
throughput
error rate
CPU
memory
DB behavior
```

Exact metrics depend on system.

---

# SECTION 2181 — WHY AVERAGE CAN MISLEAD

## 2396.

Example:

```text
90 requests = 100 ms
10 requests = 10 sec
```

Average may hide poor tail latency.

Percentiles reveal user experience better.

---

# SECTION 2182 — K6 FUTURE

## 2397.

Our roadmap includes:

```text
k6
```

Later we can create:

```text
smoke performance
load scenarios
thresholds
CI integration
```

Not implemented yet.

---

# SECTION 2183 — INTERVIEW QUESTION: SECURITY TESTING AS SDET

## 2398.

> "I treat security as part of quality. At the SDET layer I validate authentication, authorization, input handling, secret management and security-sensitive business rules, and I integrate appropriate automated security checks into CI. I also know when deeper security assessment requires specialist tooling and expertise."

---

# SECTION 2184 — SECURITY TEST AREAS

## 2399.

```text
Authentication
Authorization
Session/token handling
Input validation
Sensitive data exposure
Secrets
Rate limiting
Error leakage
Transport security
```

---

# SECTION 2185 — AUTHORIZATION MATRIX

## 2400.

Example:

| Endpoint | USER | ADMIN |
|---|---|---|
| View Product | Allow | Allow |
| Create Product | Deny | Allow |
| Update Product | Deny | Allow |
| Delete Product | Deny | Allow |

Automation can verify this systematically.

---

# SECTION 2186 — INTERVIEW QUESTION: JWT

## 2401.

> "JWT is a compact token format containing claims. In our project it is used for bearer authentication and includes authorization-related information such as role. I validate valid, missing, malformed and unauthorized-role scenarios rather than only the happy path."

---

# SECTION 2187 — JWT STRUCTURE

## 2402.

JWT commonly contains:

```text
Header
Payload
Signature
```

Encoded as:

```text
xxxxx.yyyyy.zzzzz
```

Payload is not automatically encrypted.

Do not put sensitive secrets in JWT claims merely because token is encoded.

---

# SECTION 2188 — JWT SIGNATURE

## 2403.

Signature helps server verify:

```text
token integrity/authenticity
```

according to configured signing mechanism.

Changing payload without valid signature should invalidate token.

---

# SECTION 2189 — JWT EXPIRY

## 2404.

Test:

```text
valid token
expired token
missing token
malformed token
invalid signature
wrong role
```

Expected status depends on security contract.

---

# SECTION 2190 — INTERVIEW QUESTION: PASSWORD IN API RESPONSE

## 2405.

If API returns password or sensitive credential:

```text
security defect
```

Even hashed password generally should not be unnecessarily exposed through normal API response.

---

# SECTION 2191 — LOGGING SENSITIVE DATA

## 2406.

Avoid logs containing:

```text
password
token
cookie
secret
full payment data
```

Observability should not create a data leak.

---

# SECTION 2192 — INTERVIEW QUESTION: GIT SECRET COMMITTED

## 2407.

Strong response:

> "I treat the secret as compromised. I rotate or revoke it first, remove it from current source and prevent future commits through ignore rules and scanning. If repository history must be rewritten, I coordinate carefully because history rewriting affects collaborators. Simply deleting the file in a later commit does not make the exposed secret safe."

---

# SECTION 2193 — ROTATE FIRST

## 2408.

Important:

```text
remove secret from Git
```

does not invalidate copies already exposed.

Therefore:

```text
revoke/rotate
```

is critical.

---

# SECTION 2194 — INTERVIEW QUESTION: GIT REVERT VS RESET

## 2409.

> "Revert creates a new commit that reverses an earlier commit and is generally safer for shared history. Reset moves the branch reference and can rewrite local history, so I use it carefully and avoid rewriting shared branches without coordination."

---

# SECTION 2195 — INTERVIEW QUESTION: REBASE

## 2410.

> "Rebase reapplies commits onto a new base and creates a cleaner linear history, but it rewrites commit hashes. I avoid rebasing commits that others are already depending on unless the team workflow explicitly supports it."

---

# SECTION 2196 — INTERVIEW QUESTION: MERGE CONFLICT

## 2411.

Process:

```text
identify conflicting files
understand both changes
resolve intentionally
run tests
stage resolution
continue merge/rebase
```

Never remove conflict markers without understanding code.

---

# SECTION 2197 — INTERVIEW QUESTION: GIT REFLOG

## 2412.

> "Reflog records movements of local references such as HEAD. It is useful for recovering commits after operations such as reset or rebase when the commit is no longer visible in normal branch history."

---

# SECTION 2198 — INTERVIEW QUESTION: CHERRY-PICK

## 2413.

> "Cherry-pick applies the changes represented by a specific commit onto the current branch. It is useful for selectively moving a fix, but overusing it can create duplicated history and maintenance complexity."

---

# SECTION 2199 — INTERVIEW QUESTION: `git pull`

## 2414.

Conceptually:

```text
fetch
+
integrate
```

Integration strategy depends on Git configuration/workflow.

For controlled workflows:

```text
fetch first
inspect
then integrate
```

can provide better visibility.

---

# SECTION 2200 — INTERVIEW QUESTION: LINUX PROCESS

## 2415.

Process:

```text
running instance of a program
```

Useful commands:

```bash
ps
```

```bash
pgrep <name>
```

```bash
lsof -i :8080
```

---

# SECTION 2201 — INTERVIEW QUESTION: KILL

## 2416.

Graceful:

```bash
kill <PID>
```

sends default termination signal.

Force:

```bash
kill -9 <PID>
```

should be last resort because process cannot perform graceful cleanup.

---

# SECTION 2202 — INTERVIEW QUESTION: PIPE

## 2417.

Pipe:

```text
|
```

sends stdout of one command into stdin of another.

Example:

```bash
ps aux | grep java
```

---

# SECTION 2203 — REDIRECTION

## 2418.

```bash
>
```

overwrite output file.

```bash
>>
```

append output.

Example:

```bash
echo "hello" > file.txt
```

---

# SECTION 2204 — `&&`

## 2419.

```bash
command1 && command2
```

Run command2 only if command1 succeeds.

Useful in scripts/pipelines.

---

# SECTION 2205 — `||`

## 2420.

```bash
command1 || command2
```

Run command2 if command1 fails.

---

# SECTION 2206 — EXIT CODE

## 2421.

Unix convention:

```text
0
→ success

non-zero
→ failure/special status
```

CI relies heavily on exit codes.

---

# SECTION 2207 — WHY TEST COMMAND MUST RETURN FAILURE

## 2422.

If tests fail but process exits:

```text
0
```

CI may incorrectly mark job green.

Automation tools must communicate failure through correct exit status.

---

# SECTION 2208 — INTERVIEW QUESTION: ENV VARIABLE

## 2423.

Environment variable provides runtime configuration.

Example:

```bash
export TEST_ENV=qa
```

Application/test can read it without hardcoding value.

---

# SECTION 2209 — `source`

## 2424.

```bash
source .env
```

executes file in current shell context.

Without export behavior, variables may not automatically become environment variables for child processes.

---

# SECTION 2210 — `set -a`

## 2425.

```bash
set -a
source .env
set +a
```

automatically exports variables defined while auto-export is enabled.

Our local scripts use this pattern.

---

# SECTION 2211 — INTERVIEW QUESTION: PATH

## 2426.

`PATH` contains directories shell searches for executable commands.

If:

```text
command not found
```

check:

```bash
which <command>
```

and:

```bash
echo "$PATH"
```

---

# SECTION 2212 — INTERVIEW QUESTION: JAVA_HOME

## 2427.

`JAVA_HOME` points to intended Java installation.

Build tools can use it to determine Java runtime/toolchain location.

Verify:

```bash
echo "$JAVA_HOME"
```

---

# SECTION 2213 — INTERVIEW QUESTION: JDK VS JVM

## 2428.

```text
JDK
→ development tools + runtime capabilities

JVM
→ executes Java bytecode
```

Modern Java distributions may not always ship a separately installed JRE in the old model.

---

# SECTION 2214 — JAVA BUILD FLOW

## 2429.

```text
.java
 ↓
javac
 ↓
.class bytecode
 ↓
JVM
 ↓
execution
```

Maven orchestrates this through lifecycle/plugins.

---

# SECTION 2215 — INTERVIEW QUESTION: JAR

## 2430.

JAR:

```text
Java Archive
```

Packages:

```text
compiled classes
resources
metadata
```

Spring Boot can package an executable application JAR when configured appropriately.

---

# SECTION 2216 — INTERVIEW QUESTION: CLASSNOTFOUND

## 2431.

`ClassNotFoundException` often indicates runtime attempted to load a class but could not find it.

Investigate:

```text
classpath
dependency
packaging
version
```

---

# SECTION 2217 — `NoClassDefFoundError`

## 2432.

Class may have existed during compilation or initial loading assumptions but is unavailable or failed initialization at runtime.

Investigate:

```text
runtime classpath
dependency packaging
initialization failure
```

---

# SECTION 2218 — `NoSuchMethodError`

## 2433.

Often indicates:

```text
binary dependency version mismatch
```

Code expects method:

```text
runtime class does not provide it
```

Inspect dependency tree/version conflicts.

---

# SECTION 2219 — INTERVIEW QUESTION: MAVEN TRANSITIVE DEPENDENCY

## 2434.

If:

```text
A depends on B
B depends on C
```

then C may become a transitive dependency of A.

Maven resolves this according to dependency rules.

---

# SECTION 2220 — DEPENDENCY TREE

## 2435.

Useful:

```bash
mvn dependency:tree
```

Helps inspect:

```text
transitive dependencies
version conflicts
unexpected libraries
```

---

# SECTION 2221 — INTERVIEW QUESTION: MAVEN SCOPE

## 2436.

Common:

```text
compile
test
runtime
provided
```

Scope controls where dependency is available.

Example:

```text
test framework
```

usually:

```text
test scope
```

---

# SECTION 2222 — INTERVIEW QUESTION: BOM

## 2437.

BOM:

```text
Bill of Materials
```

helps manage compatible dependency versions centrally.

Useful for ecosystems containing many related libraries.

---

# SECTION 2223 — INTERVIEW QUESTION: ARTIFACTORY/NEXUS

## 2438.

Repository managers can host:

```text
internal artifacts
cached external dependencies
release artifacts
```

Benefits:

```text
control
availability
security
traceability
```

---

# SECTION 2224 — INTERVIEW QUESTION: SNAPSHOT VS RELEASE

## 2439.

Conceptually:

```text
SNAPSHOT
→ development-changing version

Release
→ fixed published version
```

Organizations may enforce different repository policies.

---

# SECTION 2225 — INTERVIEW QUESTION: NODE.JS

## 2440.

Node.js executes JavaScript outside browser.

For our future frontend/tooling it supports:

```text
Vite
TypeScript tooling
Playwright
npm scripts
```

---

# SECTION 2226 — NPM

## 2441.

npm is:

```text
package manager
+
script runner
```

for Node ecosystem.

Important files:

```text
package.json
package-lock.json
```

---

# SECTION 2227 — `package.json`

## 2442.

Contains:

```text
metadata
dependencies
devDependencies
scripts
```

---

# SECTION 2228 — `package-lock.json`

## 2443.

Captures resolved dependency graph/version information.

Commit lock file for reproducible application builds unless project policy intentionally differs.

---

# SECTION 2229 — `npm install` VS `npm ci`

## 2444.

General:

```text
npm install
→ development dependency installation/update behavior

npm ci
→ clean deterministic install based on lock file
```

CI commonly prefers:

```bash
npm ci
```

when valid lock file exists.

---

# SECTION 2230 — INTERVIEW QUESTION: DEVDEPENDENCY

## 2445.

`devDependencies` are packages mainly needed for:

```text
development
testing
build tooling
```

Example future:

```text
Playwright
TypeScript
```

depending on project setup.

---

# SECTION 2231 — INTERVIEW QUESTION: JSON VS YAML

## 2446.

JSON:

```text
strict data format
braces/brackets
```

YAML:

```text
human-readable configuration format
indentation-sensitive
```

Both represent structured data.

---

# SECTION 2232 — YAML INDENTATION

## 2447.

Incorrect indentation can change structure or make file invalid.

This is especially important in:

```text
Docker Compose
GitHub Actions
Azure pipelines
```

---

# SECTION 2233 — INTERVIEW QUESTION: DOCKER COMPOSE

## 2448.

> "Docker Compose defines and runs a multi-container application through configuration describing services, environment, ports, volumes and networks. It is useful for creating reproducible local or test environments."

---

# SECTION 2234 — CURRENT COMPOSE

## 2449.

Current project uses Compose for:

```text
PostgreSQL 16
```

Future:

```text
backend
frontend
```

can be added when full application Dockerization is implemented.

---

# SECTION 2235 — INTERVIEW QUESTION: `docker compose up`

## 2450.

Starts configured services.

Common:

```bash
docker compose up -d
```

runs detached.

---

# SECTION 2236 — `docker compose down`

## 2451.

Stops/removes Compose-created containers and network.

Be careful with:

```text
volume removal options
```

because database data may be destroyed.

---

# SECTION 2237 — INTERVIEW QUESTION: DOCKERFILE

## 2452.

Dockerfile defines instructions for building an image.

Typical concepts:

```text
FROM
WORKDIR
COPY
RUN
EXPOSE
CMD/ENTRYPOINT
```

Exact Dockerfiles will be written when application Dockerization begins.

---

# SECTION 2238 — MULTI-STAGE BUILD

## 2453.

Docker multi-stage builds allow:

```text
build stage
```

and:

```text
runtime stage
```

to be separated.

Benefits can include:

```text
smaller runtime image
fewer build tools in final image
```

---

# SECTION 2239 — INTERVIEW QUESTION: HEALTH CHECK

## 2454.

Health check determines whether service is actually healthy.

Container:

```text
running
```

does not necessarily mean:

```text
ready
```

Important for CI and orchestration.

---

# SECTION 2240 — INTERVIEW QUESTION: CI ARTIFACT

## 2455.

> "A CI artifact is an output preserved from a job, such as a JAR, test result, Allure result, log or screenshot. Artifacts support deployment, debugging and traceability."

---

# SECTION 2241 — INTERVIEW QUESTION: CI CACHE

## 2456.

> "A cache stores reusable data such as downloaded dependencies to improve pipeline speed. It should be an optimization rather than a requirement for build correctness."

---

# SECTION 2242 — INTERVIEW QUESTION: QUALITY GATE

## 2457.

> "A quality gate is a set of conditions that must pass before a change progresses. I use risk-based gates such as successful build, critical regression and security or quality checks rather than adding arbitrary thresholds."

---

# SECTION 2243 — INTERVIEW QUESTION: CI SECRET

## 2458.

> "I store sensitive runtime values in the CI platform's secret-management mechanism and inject them only into jobs that require them. I also apply least privilege and prevent secrets from appearing in logs or test artifacts."

---

# SECTION 2244 — INTERVIEW QUESTION: RUNNER VS AGENT

## 2459.

Broadly:

```text
GitHub Actions
→ runner

Jenkins
→ agent
```

Both represent execution environments where pipeline work runs.

Implementation details differ.

---

# SECTION 2245 — INTERVIEW QUESTION: PIPELINE FAILURE

## 2460.

Always answer:

```text
Which stage?
```

Possible:

```text
workflow
checkout
runtime
dependency
build
test
report
deployment
```

Never begin with:

```text
rerun pipeline
```

as the only strategy.

---

# SECTION 2246 — INTERVIEW SCENARIO: 48 TESTS BECOME 47

## 2461.

Question:

```text
Pipeline green.
47 tests passed.
Yesterday 48 tests passed.
What do you do?
```

Answer:

> "I investigate test discovery before accepting the result. A missing test can mean accidental deletion, exclusion, tagging or runner configuration changes. I compare the test inventory and commit diff because a green pipeline with reduced coverage can still represent a quality regression."

---

# SECTION 2247 — SCENARIO: ALLURE REPORT SHOWS TOKEN

## 2462.

Action:

```text
Treat token as exposed.
```

Then:

```text
revoke/rotate token
identify sanitization gap
remove exposed artifact where possible
fix report filter
rerun validation
```

Do not merely hide report.

---

# SECTION 2248 — SCENARIO: API TEST RETURNS 403 ONLY FOR USER

## 2463.

Check:

```text
Is endpoint admin-only?
What role is in token?
SecurityConfig?
Expected requirement?
```

If admin succeeds and user receives expected 403:

```text
test may be validating correct RBAC
```

---

# SECTION 2249 — SCENARIO: ADMIN ALSO GETS 403

## 2464.

Investigate:

```text
admin token
role claim
authority mapping
security rule
environment
token generation
```

Do not immediately change expected result.

---

# SECTION 2250 — SCENARIO: DATABASE HAS DATA BUT API RETURNS 404

## 2465.

Possible:

```text
wrong DB/environment
query/filter issue
ownership/security
wrong resource ID
transaction/visibility
API mapping
```

Verify which DB the application is actually using.

---

# SECTION 2251 — SCENARIO: API RETURNS 201 BUT DB RECORD MISSING

## 2466.

Investigate:

```text
correct database?
transaction?
async persistence?
mocked repository?
wrong schema?
response generated before persistence?
```

Do not trust HTTP status alone.

---

# SECTION 2252 — SCENARIO: ORDER CANCELLED BUT STOCK NOT RESTORED

## 2467.

This is a cross-domain business consistency issue.

Validate:

```text
order state
inventory state
transaction behavior
duplicate cancellation
DB persistence
```

Current project includes stock restoration behavior in order cancellation.

---

# SECTION 2253 — SCENARIO: PAYMENT CALLED TWICE

## 2468.

Questions:

```text
Should duplicate payment be rejected?
Is operation idempotent?
What happens to order state?
Is second payment record created?
```

Current mock payment flow includes duplicate validation.

---

# SECTION 2254 — SCENARIO: CI CANNOT CONNECT TO LOCALHOST:8080

## 2469.

Important:

```text
localhost in CI runner
```

means:

```text
that runner
```

not:

```text
your Mac
```

CI must start backend in runner/network or target a reachable deployed environment.

---

# SECTION 2255 — SCENARIO: DOCKER BACKEND CANNOT CONNECT TO `localhost:5432`

## 2470.

If DB is another container:

```text
localhost
```

inside backend points to backend container.

Use correct container network/service hostname according to Compose architecture.

---

# SECTION 2256 — SCENARIO: POSTMAN PASSES, REACT FAILS

## 2471.

Investigate:

```text
CORS
browser cookies
frontend base URL
preflight
mixed content
browser security
request differences
```

Not automatically backend logic.

---

# SECTION 2257 — SCENARIO: REACT CALLS WRONG ENVIRONMENT

## 2472.

Future frontend should externalize:

```text
VITE_API_BASE_URL
```

Check build/runtime behavior carefully because frontend environment variables can be embedded into client build depending on tooling.

Never put secrets into client-side environment variables.

---

# SECTION 2258 — CLIENT-SIDE SECRET

## 2473.

Anything shipped to browser should be treated as:

```text
visible to user
```

Therefore:

```text
VITE_*
```

must not contain:

```text
DB password
JWT signing secret
private API secret
```

---

# SECTION 2259 — INTERVIEW QUESTION: LOCALSTORAGE JWT

## 2474.

For a demo project localStorage can be simple.

But security trade-off:

```text
JavaScript can access it
```

so XSS can expose token.

Production architecture may prefer:

```text
HttpOnly
Secure
SameSite cookies
```

depending on system design.

Do not claim one mechanism is universally correct.

---

# SECTION 2260 — INTERVIEW QUESTION: HTTPONLY

## 2475.

HttpOnly cookie:

```text
not accessible through normal client-side JavaScript
```

This reduces direct token theft through JavaScript.

It does not solve every web security problem.

---

# SECTION 2261 — SECURE COOKIE

## 2476.

`Secure` cookie is sent over secure HTTPS contexts according to browser rules.

Important for protecting transport.

---

# SECTION 2262 — SAMESITE

## 2477.

SameSite controls when cookies are sent in cross-site contexts.

Can help mitigate certain CSRF risks.

Values commonly include:

```text
Strict
Lax
None
```

Exact behavior should be understood with browser/security requirements.

---

# SECTION 2263 — INTERVIEW QUESTION: XSS VS CSRF

## 2478.

XSS:

```text
attacker executes/injects script in trusted web context
```

CSRF:

```text
attacker causes victim browser to send unwanted authenticated request
```

Different threats.

Controls differ.

---

# SECTION 2264 — INTERVIEW QUESTION: SQL INJECTION

## 2479.

SQL injection occurs when untrusted input can alter intended SQL query structure.

Prevention includes:

```text
parameterized queries
safe ORM usage
input handling
least DB privilege
```

Testing should not rely only on frontend validation.

---

# SECTION 2265 — INTERVIEW QUESTION: API SECURITY NEGATIVE TESTS

## 2480.

Test:

```text
missing auth
invalid auth
wrong role
invalid input
resource ownership
ID manipulation
unexpected fields
rate limiting where required
sensitive response data
```

---

# SECTION 2266 — RESOURCE OWNERSHIP

## 2481.

Example:

```text
User A creates order.
User B requests User A's order.
```

Expected:

```text
access denied/not exposed
```

according to API contract.

This is critical authorization testing.

---

# SECTION 2267 — IDOR CONCEPT

## 2482.

IDOR:

```text
Insecure Direct Object Reference
```

Example:

Changing:

```text
/orders/101
```

to:

```text
/orders/102
```

must not expose another user's data without authorization.

---

# SECTION 2268 — SENIOR SDET DEBUGGING STORY FORMAT

## 2483.

Use:

```text
Situation
 ↓
Evidence
 ↓
Isolation
 ↓
Root Cause
 ↓
Fix
 ↓
Regression
 ↓
Prevention
```

This sounds stronger than:

```text
I checked logs and fixed it.
```

---

# SECTION 2269 — STAR FORMAT

## 2484.

Interview behavioral answer:

```text
S → Situation
T → Task
A → Action
R → Result
```

For technical debugging add:

```text
Evidence + Root Cause
```

inside Action.

---

# SECTION 2270 — EXAMPLE TECHNICAL STORY STRUCTURE

## 2485.

```text
Situation:
Regression failed in CI.

Task:
Identify whether release was blocked by product or test issue.

Action:
Compared environment and commit,
isolated failing API,
reproduced outside framework,
checked request/response and logs.

Root Cause:
Environment configuration mismatch.

Result:
Corrected configuration,
reran targeted tests,
then regression.

Prevention:
Added configuration validation.
```

Use only real details from your actual experience when answering interview questions.

---

# SECTION 2271 — SENIOR ANSWER: DON'T GUESS

## 2486.

Good phrase:

> "I would first verify that assumption from logs/configuration before changing the implementation."

This shows:

```text
evidence-based debugging
```

---

# SECTION 2272 — SENIOR ANSWER: TRADE-OFF

## 2487.

Good senior answers often include:

```text
benefit
+
trade-off
```

Example:

> "Parallel execution reduces pipeline time, but I enable it only after ensuring test-data and resource isolation."

---

# SECTION 2273 — SENIOR ANSWER: CONTEXT

## 2488.

Avoid:

```text
Always use X.
Never use Y.
```

Better:

```text
It depends on:
risk
architecture
team workflow
security
cost
```

Then explain your preferred default.

---

# SECTION 2274 — INTERVIEW QUESTION: HOW DO YOU MENTOR SDETS?

## 2489.

> "I focus on engineering reasoning rather than only framework syntax. During reviews I ask engineers to explain test intent, failure evidence, data isolation, maintainability and CI impact. I also encourage reusable patterns and root-cause analysis so the team becomes less dependent on individual experts."

---

# SECTION 2275 — CODE REVIEW FOR AUTOMATION

## 2490.

Check:

```text
test intent
assertions
duplication
naming
test data
secrets
hard waits
environment hardcoding
cleanup
logging
reporting
parallel safety
```

---

# SECTION 2276 — INTERVIEW QUESTION: FRAMEWORK REVIEW

## 2491.

Ask:

```text
Can new tester understand it?
Can tests run independently?
Can environment switch without code change?
Can failures be diagnosed?
Are secrets protected?
Can CI run it?
Can framework scale?
```

---

# SECTION 2277 — INTERVIEW QUESTION: AUTOMATION DEBT

## 2492.

Automation debt includes:

```text
duplicate code
ignored failures
flaky tests
hardcoded data
obsolete tests
slow suite
poor reports
outdated dependencies
```

It needs intentional maintenance.

---

# SECTION 2278 — TEST MAINTENANCE STRATEGY

## 2493.

Regularly review:

```text
failure trends
flaky tests
execution time
unused tests
duplicate coverage
framework dependencies
```

Automation suite is production engineering code.

---

# SECTION 2279 — INTERVIEW QUESTION: TEST CODE QUALITY

## 2494.

Test code should follow:

```text
readability
modularity
version control
review
logging
error handling
security
maintainability
```

Poor test code creates false confidence.

---

# SECTION 2280 — INTERVIEW QUESTION: PAGE OBJECT MODEL

## 2495.

> "Page Object Model separates page interaction details from test intent. It can reduce locator duplication, but I avoid creating giant page objects containing assertions, business workflows and unrelated responsibilities."

---

# SECTION 2281 — API EQUIVALENT OF PAGE OBJECT THINKING

## 2496.

API frameworks can separate:

```text
client/request layer
authentication
data factory
assertions
tests
```

Same principle:

```text
separation of concerns
```

---

# SECTION 2282 — INTERVIEW QUESTION: HARD-CODED TEST DATA

## 2497.

Hardcoding can cause:

```text
collisions
environment dependence
maintenance
parallel failures
```

Prefer controlled:

```text
factories
builders
fixtures
unique data where required
```

---

# SECTION 2283 — CLEANUP

## 2498.

Cleanup strategy depends on:

```text
test environment
data ownership
failure investigation
parallel execution
```

Cleanup should not accidentally delete:

```text
shared/business data
```

---

# SECTION 2284 — INTERVIEW QUESTION: BEFORE/AFTER TEST

## 2499.

Setup should establish:

```text
required preconditions
```

Teardown:

```text
restore/clean test-owned state
```

But avoid expensive global setup for every test if unnecessary.

---

# SECTION 2285 — INTERVIEW QUESTION: PARALLEL TESTING

## 2500.

> "Before enabling parallel execution I verify that tests do not share mutable data, accounts, files or database records unexpectedly. I also check thread safety of framework utilities and external system limits. Parallelism is valuable only if reliability remains high."

---

# SECTION 2286 — THREAD SAFETY

## 2501.

Thread-safe component behaves correctly when accessed concurrently.

Potential issues:

```text
shared mutable token
static test data
shared request object
shared driver
```

Parallel framework design must consider this.

---

# SECTION 2287 — INTERVIEW QUESTION: STATIC VARIABLE IN TESTS

## 2502.

Static mutable state can be dangerous during:

```text
parallel execution
```

because tests may modify same shared value.

Use only when lifecycle and concurrency are understood.

---

# SECTION 2288 — INTERVIEW QUESTION: TESTNG

## 2503.

TestNG provides:

```text
test execution
annotations
groups
parameters
data providers
listeners
parallel capabilities
```

Our API framework uses TestNG.

---

# SECTION 2289 — TESTNG LISTENER

## 2504.

Listener can observe test lifecycle events.

Current framework uses listener-related integration for reporting metadata.

Useful for:

```text
reporting
logging
failure evidence
```

---

# SECTION 2290 — INTERVIEW QUESTION: DATA PROVIDER

## 2505.

DataProvider allows same test logic to execute with multiple datasets.

Good for:

```text
input combinations
validation scenarios
```

Avoid creating hundreds of redundant cases without risk value.

---

# SECTION 2291 — INTERVIEW QUESTION: REST ASSURED

## 2506.

> "REST Assured is a Java library for API automation. I use reusable request specifications, authentication helpers, TestNG assertions and reporting integration so tests focus on behavior instead of repeatedly constructing low-level HTTP setup."

---

# SECTION 2292 — REST ASSURED FLOW

## 2507.

Concept:

```text
given
 ↓
request configuration
 ↓
when
 ↓
HTTP action
 ↓
then
 ↓
validation
```

Readable BDD-style API.

---

# SECTION 2293 — INTERVIEW QUESTION: ASSERTION

## 2508.

Good assertion validates:

```text
business expectation
```

not only:

```text
request did not crash
```

Examples:

```text
status
response field
DB state
authorization
business transition
```

---

# SECTION 2294 — TOO MANY ASSERTIONS?

## 2509.

One test can contain multiple related assertions.

But if test validates:

```text
10 unrelated behaviors
```

failure diagnosis becomes difficult.

Keep:

```text
clear test intent
```

---

# SECTION 2295 — SOFT VS HARD ASSERTION

## 2510.

Hard assertion:

```text
failure stops current test flow
```

Soft assertion:

```text
collects multiple assertion failures before final evaluation
```

Use soft assertions carefully where multiple independent validations are useful.

---

# SECTION 2296 — INTERVIEW QUESTION: API CHAINING

## 2511.

Example:

```text
Create user
 ↓
Login
 ↓
Create order
 ↓
Pay
```

Useful for:

```text
workflow testing
```

But avoid making all tests depend on one giant chain.

---

# SECTION 2297 — INTERVIEW QUESTION: SETUP API

## 2512.

Sometimes use API to prepare UI test state.

Example:

```text
API creates prerequisite
 ↓
UI validates user journey
```

This can reduce UI setup time.

But critical UI setup behavior should still be tested separately where required.

---

# SECTION 2298 — HYBRID UI + API

## 2513.

Future portfolio:

```text
Playwright UI
+
API helpers
```

Potential:

```text
API setup
UI action
API/DB verification
```

This demonstrates strong SDET architecture.

---

# SECTION 2299 — INTERVIEW QUESTION: WHAT WILL YOU BUILD NEXT?

## 2514.

Current portfolio roadmap:

```text
React + TypeScript frontend
 ↓
Playwright + TypeScript
 ↓
UI/API hybrid tests
 ↓
Full Dockerization
 ↓
GitHub Actions
 ↓
k6
 ↓
AWS
```

This is roadmap.

Not current implementation.

---

# SECTION 2300 — SENIOR SDET 1-MINUTE PROJECT ANSWER

## 2515.

> "I built an end-to-end commerce quality-engineering portfolio project with a Java 17 Spring Boot backend and PostgreSQL database. The application includes authentication, JWT-based RBAC, products, cart, orders, mock payment and admin functionality. I created a separate Java API automation framework using REST Assured and TestNG with environment switching, reusable authentication and request specifications, database validation and Allure reporting. The reporting layer sanitizes sensitive request and response data. The current API regression baseline is 48 passing tests. Swagger/OpenAPI is integrated for API exploration, PostgreSQL runs through Docker Compose, and the code is published in GitHub with secrets externalized. The next phases are React and TypeScript, Playwright, full Dockerization, GitHub Actions, k6 and AWS."

---

# SECTION 2301 — WHY THIS PROJECT IS STRONG

## 2516.

It demonstrates:

```text
development understanding
API automation
database validation
security/RBAC
framework design
reporting
environment configuration
Docker
Git
documentation
CI-ready thinking
```

Not just:

```text
UI automation scripts
```

---

# SECTION 2302 — DON'T SAY "FULL STACK DEVELOPER"

## 2517.

Primary positioning:

```text
Senior SDET
Automation Lead
Quality Engineer
```

Development knowledge supports:

```text
testability
debugging
architecture understanding
```

Portfolio does not need to reposition you as a frontend/backend developer.

---

# SECTION 2303 — INTERVIEW QUESTION: WHY DID YOU BUILD BACKEND?

## 2518.

> "I wanted to understand and demonstrate the complete system under test rather than only automate a public demo website. Building the backend lets me design and test authentication, authorization, persistence, business state transitions and failure scenarios, and then connect those layers to API, UI, CI/CD, performance and cloud testing."

---

# SECTION 2304 — INTERVIEW QUESTION: WHY SEPARATE API AUTOMATION?

## 2519.

> "I keep API automation separate from backend source so the tests behave like an external consumer. That allows the same framework to target local, QA or stage environments without being tightly coupled to the application build."

---

# SECTION 2305 — INTERVIEW QUESTION: WHY POSTGRESQL?

## 2520.

> "I wanted a real relational database so I could validate persistence, constraints and integration behavior rather than testing only mocked data. It also gives the automation framework realistic database-validation scenarios."

---

# SECTION 2306 — INTERVIEW QUESTION: WHY DOCKER POSTGRES?

## 2521.

> "Docker Compose gives me a reproducible local PostgreSQL environment without requiring every developer or tester to manually install and configure the database. It also prepares the architecture for later CI and full application containerization."

---

# SECTION 2307 — INTERVIEW QUESTION: WHY ALLURE?

## 2522.

> "I use Allure to make automation results diagnosable by attaching environment metadata and sanitized HTTP evidence. The goal is not just a visually attractive report but faster failure analysis."

---

# SECTION 2308 — INTERVIEW QUESTION: WHY SWAGGER?

## 2523.

> "Swagger/OpenAPI gives an interactive view of the backend contract and makes manual exploration easier. It is especially useful during development and debugging, while the REST Assured framework provides repeatable regression validation."

---

# SECTION 2309 — INTERVIEW QUESTION: WHY JWT/RBAC?

## 2524.

> "Authentication and authorization are critical real-world API concerns. Implementing JWT and role-based access lets me test both identity and permission boundaries, such as ensuring a normal user cannot perform admin product operations."

---

# SECTION 2310 — INTERVIEW QUESTION: BIGGEST FRAMEWORK SECURITY IMPROVEMENT

## 2525.

> "One improvement I intentionally made was sanitizing Allure request and response attachments. Automation frameworks often capture detailed HTTP evidence, and without filtering they can expose bearer tokens, cookies or passwords in CI artifacts. I built the reporting path so sensitive values are redacted while the actual request sent to the backend remains unchanged."

---

# SECTION 2311 — INTERVIEW QUESTION: ENVIRONMENT STRATEGY

## 2526.

> "The API framework resolves its base URL from runtime configuration rather than hardcoding it in tests. It supports local, QA and stage-style targets, with an explicit base-URL override when needed. This makes the framework easier to run locally and later integrate into CI."

---

# SECTION 2312 — INTERVIEW QUESTION: WHAT IF QA URL MISSING?

## 2527.

Correct framework behavior:

```text
fail clearly
```

rather than silently:

```text
run against wrong environment
```

Configuration failure should be obvious.

---

# SECTION 2313 — DANGEROUS SILENT FALLBACK

## 2528.

Bad:

```text
QA URL missing
→ automatically test local
```

User thinks QA passed.

Actually:

```text
local passed
```

Environment configuration should fail safely.

---

# SECTION 2314 — INTERVIEW QUESTION: CONFIG VALIDATION

## 2529.

> "I validate required configuration early so the test suite fails with a clear message before executing against the wrong environment. A configuration error is much easier to diagnose when it fails at startup rather than producing misleading downstream test failures."

---

# SECTION 2315 — INTERVIEW QUESTION: WHAT MAKES FRAMEWORK CI-READY?

## 2530.

```text
command-line execution
externalized configuration
no local IDE dependency
secure credentials
deterministic test discovery
useful exit codes
reports/artifacts
environment switching
```

Current API framework already has many of these characteristics.

---

# SECTION 2316 — SENIOR SDET RAPID SCENARIO 1

## 2531.

Question:

```text
API returns 500.
What first?
```

Answer:

```text
Capture exact request/response.
Confirm reproducibility.
Check correlation/timestamp.
Inspect backend logs.
Find first meaningful exception.
```

---

# SECTION 2317 — RAPID SCENARIO 2

## 2532.

```text
Connection refused.
```

Think:

```text
host resolved
target port not accepting
service down?
wrong port?
wrong network context?
```

---

# SECTION 2318 — RAPID SCENARIO 3

## 2533.

```text
Unknown host.
```

Think:

```text
DNS
hostname
network configuration
```

---

# SECTION 2319 — RAPID SCENARIO 4

## 2534.

```text
403 only in CI.
```

Think:

```text
CI credential
role
environment
token generation
secret mapping
```

---

# SECTION 2320 — RAPID SCENARIO 5

## 2535.

```text
Maven works yesterday, fails today.
```

Think:

```text
What changed?
Git diff
dependency availability
Java
settings
repository/network
```

---

# SECTION 2321 — RAPID SCENARIO 6

## 2536.

```text
Docker container exits immediately.
```

Think:

```bash
docker compose ps
```

then:

```bash
docker compose logs <service>
```

Find startup failure.

---

# SECTION 2322 — RAPID SCENARIO 7

## 2537.

```text
UI fails but API passes.
```

Think:

```text
frontend
browser
CORS
locator
rendering
client state
integration
```

---

# SECTION 2323 — RAPID SCENARIO 8

## 2538.

```text
API passes but DB validation fails.
```

Think:

```text
wrong DB
persistence
transaction
async state
query
test data
```

---

# SECTION 2324 — RAPID SCENARIO 9

## 2539.

```text
Test fails only when suite runs.
```

Think:

```text
shared state
test order
cleanup
parallelism
data collision
```

---

# SECTION 2325 — RAPID SCENARIO 10

## 2540.

```text
Test passes after rerun.
```

Do not say:

```text
fixed.
```

Say:

```text
intermittent.
Investigate flakiness/root cause.
```

---

# SECTION 2326 — RAPID SCENARIO 11

## 2541.

```text
Pipeline didn't start.
```

Think:

```text
trigger
branch filter
workflow config
permissions
```

Not:

```text
test code
```

---

# SECTION 2327 — RAPID SCENARIO 12

## 2542.

```text
Pipeline fails before tests.
```

Report:

```text
tests not executed
```

Do not report:

```text
regression failed
```

unless tests actually ran.

---

# SECTION 2328 — RAPID SCENARIO 13

## 2543.

```text
All tests skipped.
```

Think:

```text
suite
tag/group
runner
configuration
conditional execution
```

---

# SECTION 2329 — RAPID SCENARIO 14

## 2544.

```text
Certificate expired.
```

Think:

```text
certificate renewal
hostname/chain
environment ownership
```

Not:

```text
permanently disable TLS validation
```

---

# SECTION 2330 — RAPID SCENARIO 15

## 2545.

```text
API suddenly slower.
```

Think:

```text
measure
compare baseline
backend
DB
network
dependency
recent change
```

---

# SECTION 2331 — RAPID SCENARIO 16

## 2546.

```text
POST creates duplicates.
```

Think:

```text
API contract
idempotency
duplicate handling
client retry
DB constraints
```

---

# SECTION 2332 — RAPID SCENARIO 17

## 2547.

```text
User can access another user's order.
```

Think:

```text
authorization/security defect
resource ownership
IDOR
```

High priority investigation.

---

# SECTION 2333 — RAPID SCENARIO 18

## 2548.

```text
Password visible in Allure.
```

Think:

```text
security incident
rotate if real credential
fix sanitization
remove exposure
regression test sanitizer
```

---

# SECTION 2334 — RAPID SCENARIO 19

## 2549.

```text
`.env` appears in `git status`.
```

Do not blindly:

```bash
git add .
```

Check:

```bash
git check-ignore -v <path>
```

Fix ignore configuration before commit.

---

# SECTION 2335 — RAPID SCENARIO 20

## 2550.

```text
Secret already pushed.
```

Think:

```text
rotate/revoke first
contain
clean source/history if needed
prevent recurrence
```

---

# SECTION 2336 — RAPID SCENARIO 21

## 2551.

```text
Merge conflict before release.
```

Think:

```text
understand both changes
resolve
build
targeted tests
regression based on risk
```

Never choose:

```text
ours/theirs blindly
```

---

# SECTION 2337 — RAPID SCENARIO 22

## 2552.

```text
Dependency update breaks tests.
```

Think:

```text
release notes
dependency tree
API compatibility
transitive changes
runtime compatibility
```

---

# SECTION 2338 — RAPID SCENARIO 23

## 2553.

```text
`NoSuchMethodError`
```

Think:

```text
dependency binary mismatch
```

Inspect:

```bash
mvn dependency:tree
```

---

# SECTION 2339 — RAPID SCENARIO 24

## 2554.

```text
Java class version error.
```

Think:

```text
compiled with newer Java
running on older Java
```

Check:

```bash
java -version
```

---

# SECTION 2340 — RAPID SCENARIO 25

## 2555.

```text
Frontend build fails in CI but local passes.
```

Think:

```text
Node version
lock file
npm ci
case-sensitive path
environment variables
uncommitted local file
```

---

# SECTION 2341 — CASE-SENSITIVE FILESYSTEM

## 2556.

Potential issue:

```text
import UserService
```

but actual file:

```text
userService
```

Some local filesystems may tolerate case differences differently than CI Linux environments.

This can produce:

```text
works locally
fails CI
```

---

# SECTION 2342 — RAPID SCENARIO 26

## 2557.

```text
Docker build works locally, fails CI.
```

Think:

```text
build context
missing ignored file
architecture
network
credentials
Docker version
case sensitivity
```

---

# SECTION 2343 — RAPID SCENARIO 27

## 2558.

```text
Database tests fail after parallelization.
```

Think:

```text
shared records
cleanup race
transaction isolation
same account
same product/order
```

---

# SECTION 2344 — RAPID SCENARIO 28

## 2559.

```text
Nightly fails, PR passed.
```

Could be:

```text
broader suite
different environment
time-based issue
larger data
cross-browser
environment instability
```

Compare execution scope.

---

# SECTION 2345 — RAPID SCENARIO 29

## 2560.

```text
Same test fails every midnight.
```

Think:

```text
timezone
date boundary
token expiry
scheduled data reset
batch process
```

Time is a dependency too.

---

# SECTION 2346 — RAPID SCENARIO 30

## 2561.

```text
API returns different data in QA and Stage.
```

Think:

```text
environment data
version
feature/config
DB
cache
deployment
```

Do not assume automation defect.

---

# SECTION 2347 — SENIOR TROUBLESHOOTING COMMAND SET

## 2562.

Git:

```bash
git status
git diff
git diff --cached
git log --oneline --decorate -10
```

Java/Maven:

```bash
java -version
mvn -version
```

Backend Maven Wrapper:

```bash
./mvnw -version
```

---

# SECTION 2348 — NETWORK COMMAND SET

## 2563.

```bash
curl -i <URL>
```

```bash
curl -v <URL>
```

```bash
lsof -i :8080
```

```bash
nslookup <host>
```

```bash
dig <host>
```

```bash
nc -vz <host> <port>
```

Use commands according to problem.

---

# SECTION 2349 — DOCKER COMMAND SET

## 2564.

```bash
docker compose ps
```

```bash
docker compose logs <service>
```

```bash
docker ps
```

```bash
docker inspect <container>
```

```bash
docker exec -it <container> <command>
```

---

# SECTION 2350 — PROCESS COMMAND SET

## 2565.

```bash
ps aux
```

```bash
pgrep <process>
```

```bash
lsof -i :<port>
```

```bash
kill <PID>
```

---

# SECTION 2351 — LOG COMMAND SET

## 2566.

```bash
tail -f application.log
```

```bash
grep -i "error" application.log
```

```bash
grep -i "exception" application.log
```

Use combinations:

```bash
grep -i "error" application.log | tail -50
```

---

# SECTION 2352 — FILE COMMAND SET

## 2567.

```bash
pwd
```

```bash
ls -la
```

```bash
find . -name "<pattern>"
```

```bash
cat <file>
```

```bash
head <file>
```

```bash
tail <file>
```

---

# SECTION 2353 — ENVIRONMENT COMMAND SET

## 2568.

```bash
env
```

Be careful:

```text
may expose secrets.
```

Prefer targeted:

```bash
printenv TEST_ENV
```

For sensitive variable:

```bash
if [ -n "$JWT_SECRET" ]; then
  echo "JWT_SECRET is set"
else
  echo "JWT_SECRET is not set"
fi
```

Do not print value.

---

# SECTION 2354 — SENIOR SDET "WHAT CHANGED?" CHECKLIST

## 2569.

```text
Code?
Config?
Dependency?
Runtime?
Environment?
Data?
Infrastructure?
Credential?
Network?
Deployment?
```

Many bugs become easier after identifying recent change.

---

# SECTION 2355 — SENIOR SDET "WHERE DOES IT FAIL?" CHECKLIST

## 2570.

```text
Client
 ↓
DNS
 ↓
Network
 ↓
Gateway
 ↓
Security
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
Database
```

Locate layer.

---

# SECTION 2356 — SENIOR SDET "WHO OWNS IT?" CHECKLIST

## 2571.

Possible:

```text
Test code
Product code
Environment
Infrastructure
Data
Third party
Pipeline
```

Ownership comes after evidence.

Not before.

---

# SECTION 2357 — SENIOR SDET "IS IT SAFE TO RELEASE?"

## 2572.

Consider:

```text
What failed?
Critical path?
Security?
Data integrity?
Customer impact?
Scope?
Workaround?
Regression confidence?
Rollback?
```

Release decisions are risk decisions.

---

# SECTION 2358 — INTERVIEW: RELEASE WITH FAILED TEST?

## 2573.

Strong answer:

> "I would not make the decision from the test count alone. I would identify what failed, whether the failure is a confirmed product defect or test/environment issue, its customer and business impact, available workaround and the confidence from unaffected coverage. Then I would communicate the residual risk clearly to the release stakeholders."

---

# SECTION 2359 — INTERVIEW: ALL TESTS PASS, RELEASE SAFE?

## 2574.

Not automatically.

Tests only provide:

```text
evidence
```

within:

```text
covered risks
environments
data
assumptions
```

Also consider:

```text
monitoring
deployment
security
performance
uncovered changes
```

---

# SECTION 2360 — INTERVIEW: HOW DO YOU REPORT QUALITY?

## 2575.

Avoid:

```text
95% tests passed.
```

alone.

Better:

```text
Critical flows
Failures
Defect severity
Coverage
Known risks
Environment
Build/version
Recommendation
```

---

# SECTION 2361 — QUALITY STATUS EXAMPLE

## 2576.

```text
Build: XYZ
Environment: Stage

Critical regression:
Passed

API regression:
47/48 passed

Failure:
Admin product creation authorization regression

Risk:
High — authorization behavior affected

Recommendation:
Do not promote until authorization defect resolved.
```

This communicates risk.

---

# SECTION 2362 — SENIOR SDET INTERVIEW MASTER ANSWER

## 2577.

When interviewer gives any technical incident:

```text
1. Clarify symptom
2. Identify layer
3. Collect evidence
4. Reproduce
5. Compare expected vs actual
6. Isolate dependency
7. Find root cause
8. Apply fix
9. Run targeted validation
10. Run risk-based regression
11. Add prevention
```

---

# SECTION 2363 — NEVER SAY THESE AS ONLY ANSWER

## 2578.

Avoid:

```text
I will restart.
I will rerun.
I will clear cache.
I will increase timeout.
I will disable SSL.
I will ask developer.
```

These may occasionally be actions.

But without diagnosis:

```text
they are not troubleshooting strategies.
```

---

# SECTION 2364 — BETTER LANGUAGE

## 2579.

Instead of:

```text
Maybe DB issue.
```

Say:

> "I would verify database connectivity and application configuration before concluding that the database is the cause."

Instead of:

```text
CI issue.
```

Say:

> "The failure occurs before test execution during dependency resolution, so I would classify it as a build or infrastructure issue first."

---

# SECTION 2365 — INTERVIEW COMMUNICATION

## 2580.

Good answer structure:

```text
First...
Then...
If X...
If not...
Finally...
```

Shows logical debugging.

Example:

> "First I confirm whether the request reaches the service. If it does not, I investigate DNS, network and port connectivity. If it reaches the service but returns an HTTP error, I move up to authentication, routing and application logs."

---

# SECTION 2366 — DON'T DUMP 20 POSSIBILITIES

## 2581.

Bad interview answer:

```text
Could be DB, network, code, cache, server,
browser, token, DNS, Docker...
```

Better:

```text
classify first
then narrow systematically
```

---

# SECTION 2367 — INTERVIEWER WANTS REASONING

## 2582.

Usually interviewer is testing:

```text
How do you think?
```

Not:

```text
Can you memorize every command?
```

Explain:

```text
why you run a command
```

---

# SECTION 2368 — COMMAND + PURPOSE

## 2583.

Example:

```bash
lsof -i :8080
```

Purpose:

```text
verify whether a process is listening on backend port
```

Not simply:

```text
I know lsof.
```

---

# SECTION 2369 — TOOL + PURPOSE

## 2584.

```text
Maven
→ build/dependency/test orchestration

Docker
→ reproducible isolated runtime/services

Allure
→ test evidence/reporting

Swagger
→ API contract exploration

Git
→ version control/collaboration

CI
→ automated delivery feedback
```

---

# SECTION 2370 — FRAMEWORK + PURPOSE

## 2585.

```text
REST Assured
→ API automation

TestNG
→ test execution/lifecycle

Playwright
→ future browser automation

k6
→ future performance testing
```

Always connect tool to engineering problem.

---

# SECTION 2371 — FINAL SENIOR SDET MENTAL MODEL

## 2586.

```text
              REQUIREMENT
                   │
                   ▼
                RISK
                   │
                   ▼
             TEST STRATEGY
                   │
        ┌──────────┼──────────┐
        ▼          ▼          ▼
       API         UI         DB
        │          │          │
        └──────────┼──────────┘
                   ▼
               AUTOMATION
                   │
                   ▼
                  CI
                   │
                   ▼
              EVIDENCE
                   │
                   ▼
             QUALITY GATE
                   │
                   ▼
                RELEASE
                   │
                   ▼
            OBSERVABILITY
                   │
                   ▼
                FEEDBACK
```

Quality Engineering is the complete loop.

---

# SECTION 2372 — FINAL DEBUGGING MENTAL MODEL

## 2587.

```text
SYMPTOM
   ↓
EVIDENCE
   ↓
LAYER
   ↓
ISOLATION
   ↓
ROOT CAUSE
   ↓
FIX
   ↓
REGRESSION
   ↓
PREVENTION
```

Memorize this.

It works for:

```text
API
UI
DB
Docker
CI
Git
Maven
Java
Network
```

---

# SECTION 2373 — FINAL PROJECT DEFENSE

## 2588.

If interviewer asks:

```text
Did you just copy this project?
```

You should be able to explain:

```text
Why Spring Boot?
Why separate API automation?
Why PostgreSQL?
Why Docker?
Why JWT?
Why RBAC?
Why environment switching?
Why request specification?
Why DB validation?
Why Allure?
Why sanitize reports?
Why Swagger?
Why 48-test baseline?
Why more API than UI?
Why Playwright next?
Why GitHub Actions later?
Why k6?
Why AWS?
```

Understanding these decisions is more valuable than memorizing thousands of lines of code.

---

# SECTION 2374 — FINAL 60-SECOND SENIOR SDET ANSWER

## 2589.

> "My strength as an SDET is not limited to writing automation scripts. I approach quality across the complete system. I understand application runtime, APIs, databases, authentication and authorization, networking, containers, build tools, Git and CI/CD. When something fails, I first classify the failure and collect evidence rather than immediately changing tests. I design automation to be environment-independent, secure, diagnosable and CI-ready, and I use risk-based testing to decide which checks belong at API, integration and UI layers. My goal is to provide fast and trustworthy engineering feedback, not simply increase the number of automated test cases."

---

# SECTION 2375 — PART 11 FINAL CHECKLIST

## 2590.

You should now be comfortable answering:

```text
✓ Local works, CI fails
✓ Maven build failure
✓ Spring Boot startup failure
✓ Docker connectivity
✓ localhost/container networking
✓ 401 vs 403
✓ HTTP status troubleshooting
✓ DNS/TCP/TLS
✓ CORS
✓ retries
✓ API automation architecture
✓ DB validation
✓ flaky tests
✓ Git incidents
✓ Java runtime issues
✓ dependency conflicts
✓ npm/Node basics
✓ CI/CD troubleshooting
✓ test strategy
✓ risk-based testing
✓ performance concepts
✓ security testing
✓ release decisions
✓ Senior SDET framework design
```

---

# END OF PART 11 — SENIOR SDET TOOLING & SYSTEM TROUBLESHOOTING INTERVIEW MASTER SET

Next:

PART 12 — FINAL RAPID REVISION / CHEAT SHEET

PART 12 will be intentionally much shorter than Parts 1–11.

It will convert everything into:

```text
1-day-before-interview revision
commands cheat sheet
tool comparison sheet
HTTP cheat sheet
Docker cheat sheet
Maven cheat sheet
Java cheat sheet
CI/CD cheat sheet
debugging decision trees
50 rapid-fire questions
project explanation
30-second / 1-minute answers
```

After PART 12:

```text
SDET-Engineering-Tooling-Notes.md
→ COMPLETE
```
---

# PART 12 — FINAL RAPID REVISION / SENIOR SDET CHEAT SHEET

# SECTION 2376 — HOW TO USE THIS PART

## 2591.

This section is not for learning concepts from zero.

Use it:

```text
1 week before interview
→ detailed revision

1 day before interview
→ complete Part 12

1 hour before interview
→ commands + mental models + project answers

5 minutes before interview
→ troubleshooting framework + introduction
```

Core Senior SDET mindset:

```text
Don't memorize tools.

Understand:

WHY
WHEN
HOW
FAILURE MODE
DEBUGGING
TRADE-OFF
```

---

# SECTION 2377 — COMPLETE SDET TOOLCHAIN IN ONE VIEW

## 2592.

```text
Source Code
    │
    ▼
   Git
    │
    ▼
GitHub
    │
    ▼
CI/CD
    │
    ├───────────────┐
    ▼               ▼
  Maven            npm
    │               │
    ▼               ▼
 Java Build     Frontend Build
    │               │
    ▼               ▼
Spring Boot       React
    │               │
    └───────┬───────┘
            ▼
          HTTP
            │
            ▼
        REST APIs
            │
      ┌─────┴─────┐
      ▼           ▼
 PostgreSQL      Security
                  JWT/RBAC

Testing:

REST Assured
TestNG
Playwright
DB Validation
k6

Infrastructure:

Docker
Docker Compose
CI/CD
AWS — future

Evidence:

Allure
Logs
Swagger/OpenAPI
```

---

# SECTION 2378 — SENIOR SDET TROUBLESHOOTING FORMULA

## 2593.

Memorize:

```text
OBSERVE
   ↓
CLASSIFY
   ↓
ISOLATE
   ↓
VERIFY
   ↓
FIX
   ↓
RETEST
   ↓
PREVENT
```

Shorter version:

```text
Symptom
→ Evidence
→ Layer
→ Root Cause
→ Fix
→ Regression
→ Prevention
```

---

# SECTION 2379 — FIRST QUESTION DURING FAILURE

## 2594.

Ask:

```text
What exactly failed?
```

Then:

```text
Where?
When?
Which environment?
Which build?
Which commit?
Reproducible?
What changed?
```

Never begin with:

```text
restart
rerun
increase timeout
clear cache
```

without evidence.

---

# SECTION 2380 — FAILURE CLASSIFICATION

## 2595.

```text
Build
Application startup
API
UI
Database
Network
Authentication
Authorization
Configuration
Dependency
Container
Test automation
CI/CD
Test data
```

Correct classification reduces debugging time.

---

# SECTION 2381 — COMPLETE REQUEST PATH

## 2596.

```text
Client
  ↓
DNS
  ↓
TCP
  ↓
TLS
  ↓
Proxy / Gateway
  ↓
Application
  ↓
Security
  ↓
Controller
  ↓
Service / Business Logic
  ↓
Repository
  ↓
Database
```

When request fails:

```text
Find the layer.
```

---

# SECTION 2382 — LINUX 60-SECOND REVISION

## 2597.

Navigation:

```bash
pwd
ls -la
cd <directory>
```

Files:

```bash
touch file.txt
mkdir directory
cp source destination
mv source destination
rm file.txt
```

Search:

```bash
grep "text" file.txt
find . -name "*.java"
```

Logs:

```bash
tail -f application.log
grep -i "error" application.log
```

Processes:

```bash
ps aux
pgrep java
lsof -i :8080
```

Environment:

```bash
printenv TEST_ENV
echo "$JAVA_HOME"
```

Command location:

```bash
which java
which mvn
```

---

# SECTION 2383 — LINUX OPERATORS

## 2598.

Pipe:

```bash
command1 | command2
```

Meaning:

```text
stdout of command1
→ stdin of command2
```

Overwrite:

```bash
>
```

Append:

```bash
>>
```

Run second only on success:

```bash
&&
```

Run second on failure:

```bash
||
```

---

# SECTION 2384 — EXIT CODE

## 2599.

```text
0
→ success

non-zero
→ failure/special condition
```

CI depends heavily on exit codes.

Important:

```text
Test failures must produce correct process failure.
```

Otherwise pipeline may become falsely green.

---

# SECTION 2385 — PERMISSIONS

## 2600.

```text
r → read
w → write
x → execute
```

Make script executable:

```bash
chmod +x script.sh
```

Avoid casually using:

```bash
chmod 777
```

---

# SECTION 2386 — PROCESS TROUBLESHOOTING

## 2601.

Check port:

```bash
lsof -i :8080
```

Then identify process.

Graceful termination:

```bash
kill <PID>
```

Force only when necessary:

```bash
kill -9 <PID>
```

---

# SECTION 2387 — ENVIRONMENT VARIABLES

## 2602.

Example:

```bash
export TEST_ENV=qa
```

Environment variables allow:

```text
runtime configuration
without hardcoding
```

For `.env`:

```bash
set -a
source .env
set +a
```

This exports variables loaded from the file.

Never commit real `.env`.

---

# SECTION 2388 — SAFE SECRET CHECK

## 2603.

Bad:

```bash
echo "$JWT_SECRET"
```

Better:

```bash
if [ -n "$JWT_SECRET" ]; then
  echo "JWT_SECRET is set"
else
  echo "JWT_SECRET is not set"
fi
```

Verify presence without exposing value.

---

# SECTION 2389 — GIT MASTER FLOW

## 2604.

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

# SECTION 2390 — MOST IMPORTANT GIT COMMAND

## 2605.

```bash
git status
```

Run frequently.

It tells:

```text
branch
modified files
staged files
untracked files
working-tree state
```

---

# SECTION 2391 — GIT DIFF

## 2606.

Unstaged:

```bash
git diff
```

Staged:

```bash
git diff --cached
```

Before important commit:

```bash
git status
git diff
git diff --cached
```

---

# SECTION 2392 — SAFE COMMIT FLOW

## 2607.

```bash
git status
git diff
git add <specific-files>
git diff --cached
git commit -m "meaningful message"
git push
```

Avoid blindly:

```bash
git add .
```

when secrets or generated files may exist.

---

# SECTION 2393 — GITIGNORE

## 2608.

Verify:

```bash
git check-ignore -v <file>
```

Important for:

```text
.env
build output
reports
IDE files
secrets
```

---

# SECTION 2394 — FETCH VS PULL

## 2609.

```text
git fetch
→ download remote information

git pull
→ fetch + integrate
```

For careful workflows:

```bash
git fetch
```

then inspect before integration.

---

# SECTION 2395 — MERGE VS REBASE

## 2610.

Merge:

```text
combines histories
can create merge commit
does not rewrite existing commits
```

Rebase:

```text
replays commits on new base
cleaner linear history
rewrites commit hashes
```

Avoid casually rebasing shared history.

---

# SECTION 2396 — REVERT VS RESET

## 2611.

Revert:

```text
creates new commit reversing earlier change
safer for shared history
```

Reset:

```text
moves branch pointer
can rewrite history
```

Use reset carefully.

---

# SECTION 2397 — STASH

## 2612.

Temporarily save work:

```bash
git stash
```

View:

```bash
git stash list
```

Restore:

```bash
git stash pop
```

Useful when switching context before work is ready to commit.

---

# SECTION 2398 — REFLOG

## 2613.

```bash
git reflog
```

Useful for recovering:

```text
lost commit references
after reset/rebase
```

Think:

```text
Git safety net for local reference movement.
```

---

# SECTION 2399 — SECRET PUSHED TO GIT

## 2614.

Correct order:

```text
1. Treat secret as compromised
2. Rotate/revoke
3. Remove from source
4. Clean history if required
5. Improve ignore/scanning
6. Prevent recurrence
```

Deleting secret in a later commit is not enough.

---

# SECTION 2400 — JAVA MASTER MODEL

## 2615.

```text
.java source
   ↓
 javac
   ↓
.class bytecode
   ↓
  JVM
   ↓
execution
```

---

# SECTION 2401 — JDK VS JVM

## 2616.

```text
JDK
→ Java development tools + runtime capabilities

JVM
→ executes Java bytecode
```

Current project:

```text
Java 17
```

---

# SECTION 2402 — JAVA VERSION CHECK

## 2617.

```bash
java -version
javac -version
```

Check Maven's Java:

```bash
mvn -version
```

Important:

```text
Terminal Java
and
Maven Java
should be understood separately.
```

---

# SECTION 2403 — JAVA_HOME

## 2618.

```bash
echo "$JAVA_HOME"
```

`JAVA_HOME` points toward intended Java installation.

PATH determines which executable shell finds.

Debug with:

```bash
which java
```

---

# SECTION 2404 — CLASS VERSION ERROR

## 2619.

Typical reason:

```text
compiled using newer Java
running using older Java
```

Check:

```bash
java -version
```

and build configuration.

---

# SECTION 2405 — CLASSNOTFOUND

## 2620.

Think:

```text
runtime classpath
missing dependency
packaging
```

---

# SECTION 2406 — NOCLASSDEFFOUNDERROR

## 2621.

Think:

```text
runtime dependency/class availability
or class initialization failure
```

---

# SECTION 2407 — NOSUCHMETHODERROR

## 2622.

Think:

```text
binary dependency mismatch
```

Inspect:

```bash
mvn dependency:tree
```

---

# SECTION 2408 — JAR

## 2623.

JAR:

```text
Java Archive
```

Can contain:

```text
compiled classes
resources
metadata
```

Spring Boot applications can be packaged as executable JARs when configured appropriately.

---

# SECTION 2409 — JVM MEMORY

## 2624.

```text
Heap
→ objects

Stack
→ method calls/local execution state
```

Common:

```text
OutOfMemoryError
StackOverflowError
```

Do not confuse them.

---

# SECTION 2410 — MAVEN MASTER MODEL

## 2625.

Maven handles:

```text
build lifecycle
dependencies
plugins
testing
packaging
```

Main file:

```text
pom.xml
```

---

# SECTION 2411 — MAVEN LIFECYCLE

## 2626.

Important phases:

```text
validate
compile
test
package
verify
install
deploy
```

Calling later phase generally executes earlier required phases.

---

# SECTION 2412 — COMMON MAVEN COMMANDS

## 2627.

```bash
mvn clean
```

```bash
mvn test
```

```bash
mvn clean test
```

```bash
mvn clean package
```

Dependency inspection:

```bash
mvn dependency:tree
```

---

# SECTION 2413 — MAVEN WRAPPER

## 2628.

When project provides wrapper:

```bash
./mvnw
```

Benefits:

```text
project-controlled Maven distribution
more consistent builds
```

Our backend provides Maven Wrapper.

API automation local script is known to run Maven through:

```bash
mvn clean test
```

Do not claim it uses wrapper unless we later add one.

---

# SECTION 2414 — MAVEN FAILURE LAYERS

## 2629.

```text
Dependency resolution
       ↓
Compilation
       ↓
Test compilation
       ↓
Test execution
       ↓
Packaging
       ↓
Plugin execution
```

Always identify which layer failed.

---

# SECTION 2415 — DEPENDENCY

## 2630.

Direct:

```text
explicitly declared
```

Transitive:

```text
pulled through another dependency
```

Inspect:

```bash
mvn dependency:tree
```

---

# SECTION 2416 — MAVEN SCOPE

## 2631.

Common:

```text
compile
test
runtime
provided
```

Test libraries generally belong in:

```text
test scope
```

when only required for tests.

---

# SECTION 2417 — BOM

## 2632.

```text
Bill of Materials
```

Helps centrally manage compatible dependency versions.

---

# SECTION 2418 — MAVEN REPOSITORY

## 2633.

Dependencies may come from:

```text
local repository
Maven Central
Nexus
Artifactory
other configured repositories
```

Failure may involve:

```text
network
proxy
TLS
credentials
artifact version
```

---

# SECTION 2419 — SPRING BOOT MASTER MODEL

## 2634.

```text
Spring
→ application framework

Spring Boot
→ simplifies configuration/startup/deployment of Spring applications
```

---

# SECTION 2420 — SPRING BOOT ENTRY POINT

## 2635.

Common:

```java
@SpringBootApplication
```

with:

```java
SpringApplication.run(...)
```

starts application context.

---

# SECTION 2421 — APPLICATION CONTEXT

## 2636.

Spring ApplicationContext manages:

```text
beans
dependencies
configuration
application lifecycle
```

---

# SECTION 2422 — DEPENDENCY INJECTION

## 2637.

Instead of components manually creating dependencies:

```text
Spring supplies required dependencies.
```

Benefits:

```text
decoupling
testability
maintainability
```

---

# SECTION 2423 — SPRING LAYERING

## 2638.

Typical:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Not every application must use exactly this structure.

---

# SECTION 2424 — REQUEST FLOW

## 2639.

```text
HTTP Request
    ↓
Security
    ↓
Controller
    ↓
Business Logic
    ↓
Repository
    ↓
Database
    ↓
Response
```

Use this when debugging API behavior.

---

# SECTION 2425 — SPRING BOOT STARTUP FAILURE

## 2640.

Check:

```text
Java
environment variables
application properties
DB connectivity
port
dependencies
bean creation
ApplicationContext
```

Find first meaningful:

```text
Caused by:
```

---

# SECTION 2426 — CURRENT BACKEND START

## 2641.

From project root:

```bash
./backend/run-local.sh
```

Script loads local environment and starts backend.

Do not commit:

```text
backend/.env
```

---

# SECTION 2427 — DATABASE CONFIG

## 2642.

Current backend externalizes:

```text
DB URL
DB username
DB password
JWT secret
JWT expiration
```

This is better than hardcoding secrets.

---

# SECTION 2428 — HTTP URL ANATOMY

## 2643.

Example:

```text
https://api.example.com:443/products/123?view=full
```

Parts:

```text
https
→ scheme

api.example.com
→ host

443
→ port

/products/123
→ path

view=full
→ query
```

---

# SECTION 2429 — HTTP METHODS

## 2644.

```text
GET
→ retrieve

POST
→ create/submit

PUT
→ full replacement/update semantics

PATCH
→ partial update

DELETE
→ delete
```

Actual API contract remains source of truth.

---

# SECTION 2430 — SAFE VS IDEMPOTENT

## 2645.

Safe:

```text
intended not to request state change
```

Example:

```text
GET
```

Idempotent:

```text
repeating operation has same intended effect on server state
```

Common HTTP semantics:

```text
GET
PUT
DELETE
→ idempotent

POST
→ not inherently idempotent
```

---

# SECTION 2431 — HTTP STATUS MASTER TABLE

## 2646.

```text
200 OK
→ successful request

201 Created
→ resource created

204 No Content
→ success without response body

301/302
→ redirect

304
→ not modified/cache-related

400
→ bad request

401
→ authentication required/invalid

403
→ authenticated but forbidden

404
→ resource/route not found

405
→ method not allowed

409
→ conflict

415
→ unsupported media type

422
→ semantically invalid request in APIs using this status

429
→ too many requests

500
→ internal server error

502
→ bad gateway

503
→ service unavailable

504
→ gateway timeout
```

---

# SECTION 2432 — 401 VS 403

## 2647.

```text
401
→ authentication problem

403
→ authorization/permission problem
```

Current project:

```text
USER POST product
→ 403

ADMIN POST product
→ allowed
```

---

# SECTION 2433 — CONTENT-TYPE VS ACCEPT

## 2648.

```text
Content-Type
→ body format being sent

Accept
→ response formats client accepts
```

---

# SECTION 2434 — PATH VS QUERY

## 2649.

Path:

```text
/products/123
```

Query:

```text
/products?name=phone
```

Typically:

```text
Path
→ resource identity

Query
→ filtering/search/sort/pagination
```

---

# SECTION 2435 — HTTP VS HTTPS

## 2650.

```text
HTTPS
=
HTTP over TLS
```

TLS provides:

```text
encrypted transport
server identity validation
```

---

# SECTION 2436 — TLS FAILURE

## 2651.

Think:

```text
expired certificate
hostname mismatch
untrusted CA
certificate chain
trust store
```

Do not permanently disable certificate verification.

---

# SECTION 2437 — DNS

## 2652.

```text
Hostname
 ↓
DNS
 ↓
IP address
```

Check:

```bash
nslookup example.com
```

or:

```bash
dig example.com
```

---

# SECTION 2438 — DNS VS CONNECTION REFUSED

## 2653.

DNS failure:

```text
hostname cannot resolve
```

Connection refused:

```text
host reachable/resolved
but port is not accepting connection
```

---

# SECTION 2439 — TCP

## 2654.

TCP provides reliable connection-oriented transport.

HTTP commonly operates over TCP.

HTTPS adds TLS above transport.

High-level:

```text
DNS
 ↓
TCP
 ↓
TLS
 ↓
HTTP
```

for HTTPS request.

---

# SECTION 2440 — PORT TEST

## 2655.

```bash
nc -vz <host> <port>
```

Useful for basic TCP connectivity checks.

---

# SECTION 2441 — CURL CHEAT SHEET

## 2656.

Headers + body:

```bash
curl -i <URL>
```

Verbose:

```bash
curl -v <URL>
```

Follow redirects:

```bash
curl -L <URL>
```

POST JSON:

```bash
curl -i \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"example":"value"}' \
  <URL>
```

Bearer placeholder:

```bash
curl -i \
  -H "Authorization: Bearer <TOKEN>" \
  <URL>
```

Never put real token in notes.

---

# SECTION 2442 — CURL TLS WARNING

## 2657.

```bash
curl -k
```

disables certificate verification.

Use only as controlled diagnostic comparison.

Never treat it as production fix.

---

# SECTION 2443 — TIMEOUT TYPES

## 2658.

```text
DNS timeout
connection timeout
TLS timeout
read/response timeout
gateway timeout
test timeout
pipeline timeout
```

"Timeout" alone is not enough diagnosis.

---

# SECTION 2444 — CORS MASTER MODEL

## 2659.

Origin:

```text
scheme + host + port
```

Future:

```text
React
http://localhost:5173

Backend
http://localhost:8080
```

Different origins.

Browser may enforce:

```text
CORS
```

---

# SECTION 2445 — CORS PREFLIGHT

## 2660.

Browser may send:

```text
OPTIONS
```

before actual request.

Server may return:

```text
Access-Control-Allow-Origin
Access-Control-Allow-Methods
Access-Control-Allow-Headers
```

as required.

---

# SECTION 2446 — POSTMAN WORKS, BROWSER FAILS

## 2661.

Think:

```text
CORS
preflight
cookies
browser security
frontend request differences
mixed content
```

REST Assured/curl/Postman do not enforce browser CORS the same way.

---

# SECTION 2447 — PROXY VS REVERSE PROXY

## 2662.

Forward proxy:

```text
Client
 ↓
Proxy
 ↓
Server
```

Reverse proxy:

```text
Client
 ↓
Reverse Proxy
 ↓
Backend
```

---

# SECTION 2448 — API GATEWAY

## 2663.

May provide:

```text
routing
authentication
rate limiting
policy enforcement
observability
```

Architecture-specific.

---

# SECTION 2449 — LOAD BALANCER

## 2664.

```text
Client
 ↓
Load Balancer
 ↓
Service Instance A
Service Instance B
Service Instance C
```

Purpose:

```text
traffic distribution
```

---

# SECTION 2450 — DOCKER MASTER MODEL

## 2665.

```text
Dockerfile
   ↓
docker build
   ↓
Image
   ↓
docker run
   ↓
Container
```

---

# SECTION 2451 — IMAGE VS CONTAINER

## 2666.

```text
Image
→ packaged template

Container
→ running instance of image
```

---

# SECTION 2452 — CONTAINER VS VM

## 2667.

VM:

```text
guest OS
```

Container:

```text
shares host kernel
process-level isolation
```

Containers are generally lighter.

---

# SECTION 2453 — DOCKER COMPOSE

## 2668.

Compose defines multi-container application/services.

Current project:

```text
PostgreSQL 16
```

Future:

```text
backend
frontend
```

after full Dockerization.

---

# SECTION 2454 — DOCKER COMMANDS

## 2669.

```bash
docker ps
```

```bash
docker compose ps
```

```bash
docker compose up -d
```

```bash
docker compose logs postgres
```

```bash
docker compose down
```

---

# SECTION 2455 — CONTAINER DEBUGGING

## 2670.

Container running does not mean:

```text
service healthy
```

Check:

```text
container status
logs
health
port
network
application process
```

---

# SECTION 2456 — LOCALHOST TRAP

## 2671.

Host:

```text
localhost
→ host machine
```

Inside container:

```text
localhost
→ that same container
```

Therefore two containers usually communicate using:

```text
Compose service/network hostname
```

not each other's localhost.

---

# SECTION 2457 — DOCKER VOLUME

## 2672.

Volume:

```text
persistent storage independent of one container lifecycle
```

Current PostgreSQL uses persistent volume.

Important:

```text
existing volume may retain old DB initialization
```

even after Compose environment values change.

---

# SECTION 2458 — DOCKERFILE KEYWORDS

## 2673.

Remember:

```text
FROM
WORKDIR
COPY
RUN
EXPOSE
CMD
ENTRYPOINT
```

Exact usage depends on image design.

---

# SECTION 2459 — MULTI-STAGE BUILD

## 2674.

```text
Build stage
   ↓
Runtime stage
```

Benefits:

```text
smaller image
less build tooling in runtime image
```

---

# SECTION 2460 — NODE.JS MASTER MODEL

## 2675.

Node.js:

```text
JavaScript runtime outside browser
```

Future project use:

```text
React tooling
TypeScript
Vite
Playwright
npm
```

---

# SECTION 2461 — NPM

## 2676.

npm handles:

```text
packages
dependency installation
scripts
```

Files:

```text
package.json
package-lock.json
```

---

# SECTION 2462 — PACKAGE.JSON

## 2677.

Contains:

```text
scripts
dependencies
devDependencies
metadata
```

---

# SECTION 2463 — PACKAGE-LOCK

## 2678.

Stores resolved dependency graph information.

Helps:

```text
reproducible installs
```

---

# SECTION 2464 — NPM INSTALL VS NPM CI

## 2679.

```text
npm install
→ normal development installation behavior

npm ci
→ clean deterministic install from lock file
```

CI commonly prefers:

```bash
npm ci
```

when lock file is valid.

---

# SECTION 2465 — JSON

## 2680.

Example:

```json
{
  "name": "Animesh",
  "role": "SDET"
}
```

Important:

```text
strict syntax
double-quoted keys/strings
structured data
```

---

# SECTION 2466 — YAML

## 2681.

Example:

```yaml
name: test
environment: qa
```

Important:

```text
indentation matters
```

Used heavily in:

```text
Docker Compose
CI/CD
configuration
```

---

# SECTION 2467 — CI/CD MASTER MODEL

## 2682.

```text
Developer Push
      ↓
Checkout
      ↓
Setup Runtime
      ↓
Dependencies
      ↓
Build
      ↓
Tests
      ↓
Reports
      ↓
Quality Gate
      ↓
Artifact
      ↓
Deploy
```

Actual pipeline can differ.

---

# SECTION 2468 — CI VS CD

## 2683.

CI:

```text
Continuous Integration
```

Frequent integration + automated feedback.

CD can mean:

```text
Continuous Delivery
or
Continuous Deployment
```

Delivery:

```text
software kept deployable
```

Deployment:

```text
automatically deployed after gates
```

---

# SECTION 2469 — CI FAILURE CLASSIFICATION

## 2684.

Ask:

```text
Did workflow start?

Did checkout pass?

Did runtime setup pass?

Did dependencies resolve?

Did build pass?

Did tests start?

Did report generation pass?

Did deployment start?
```

---

# SECTION 2470 — PIPELINE GREEN WITH ZERO TESTS

## 2685.

Not acceptable.

Check:

```text
test discovery
runner
groups/tags
include/exclude
naming
suite configuration
```

Current baseline:

```text
48 API tests
```

A future full regression should validate expected test count.

---

# SECTION 2471 — CI ARTIFACT

## 2686.

Examples:

```text
JAR
test results
Allure results
screenshots
logs
```

Artifact helps:

```text
traceability
debugging
deployment
```

---

# SECTION 2472 — CI CACHE

## 2687.

Cache can improve:

```text
pipeline speed
```

Example:

```text
Maven dependencies
npm dependencies
```

But build correctness should not depend on cache.

---

# SECTION 2473 — CI SECRET

## 2688.

Secrets should live in:

```text
CI secret management
```

Not:

```text
Git
YAML plaintext
logs
reports
```

Use least privilege.

---

# SECTION 2474 — QUALITY GATE

## 2689.

Possible:

```text
build passed
critical tests passed
security checks passed
quality checks passed
acceptable risk
```

Quality gate should be:

```text
risk-driven
```

---

# SECTION 2475 — API AUTOMATION MASTER MODEL

## 2690.

```text
Test
 ↓
Test Data
 ↓
Auth
 ↓
Request Specification
 ↓
REST Assured
 ↓
HTTP
 ↓
Backend
 ↓
Database
 ↓
Assertions
 ↓
Allure
```

---

# SECTION 2476 — CURRENT API FRAMEWORK

## 2691.

Current framework includes:

```text
REST Assured
TestNG
RequestSpecFactory
AuthHelper
BaseTest
TestDataFactory
cleanup
environment switching
DB validation
Allure
sanitized HTTP evidence
```

Regression:

```text
48 / 48 passed
```

---

# SECTION 2477 — WHY REQUEST SPECIFICATION?

## 2692.

Centralize:

```text
base URI
headers
content type
filters
common configuration
```

Benefits:

```text
less duplication
easier environment switching
consistent reporting
```

---

# SECTION 2478 — WHY AUTH HELPER?

## 2693.

Keeps:

```text
login/token handling
```

outside individual tests.

Benefits:

```text
readability
reuse
environment management
```

---

# SECTION 2479 — WHY BASE TEST?

## 2694.

Centralize appropriate shared setup such as:

```text
authenticated contexts
environment setup
```

But avoid:

```text
huge inheritance hierarchy
```

---

# SECTION 2480 — TEST DATA

## 2695.

Good tests should minimize:

```text
shared mutable state
hardcoded environment-specific data
test-order dependency
```

Prefer:

```text
controlled test data
cleanup
isolation
```

---

# SECTION 2481 — API TESTING LAYERS

## 2696.

Validate:

```text
status
headers
schema
business data
security
state transition
DB persistence
```

Not every test needs every layer.

Choose according to risk.

---

# SECTION 2482 — SCHEMA VS BUSINESS VALIDATION

## 2697.

Schema:

```text
Is structure/type correct?
```

Business:

```text
Is actual value/behavior correct?
```

Need both where appropriate.

---

# SECTION 2483 — NEGATIVE API TESTING

## 2698.

Remember:

```text
missing field
invalid value
malformed JSON
missing token
invalid token
wrong role
nonexistent resource
duplicate request
invalid state
unsupported media type
```

---

# SECTION 2484 — AUTHENTICATION VS AUTHORIZATION

## 2699.

Authentication:

```text
Who are you?
```

Authorization:

```text
What are you allowed to do?
```

---

# SECTION 2485 — JWT

## 2700.

Structure:

```text
Header.Payload.Signature
```

Current project uses:

```text
JWT bearer authentication
+
role-based authorization
```

JWT payload is encoded, not automatically encrypted.

---

# SECTION 2486 — JWT TESTS

## 2701.

Think:

```text
valid
missing
malformed
expired
invalid signature
wrong role
```

Expected responses follow application contract.

---

# SECTION 2487 — RBAC

## 2702.

```text
Role-Based Access Control
```

Current roles:

```text
ROLE_USER
ROLE_ADMIN
```

Example:

```text
USER create product
→ forbidden

ADMIN create product
→ allowed
```

---

# SECTION 2488 — RESOURCE OWNERSHIP

## 2703.

Test:

```text
User A owns resource
User B attempts access
```

Verify authorization prevents unauthorized exposure.

Important for:

```text
orders
payments
profiles
```

---

# SECTION 2489 — IDOR

## 2704.

IDOR:

```text
Insecure Direct Object Reference
```

Changing resource ID must not bypass authorization.

Example:

```text
/orders/101
→ /orders/102
```

---

# SECTION 2490 — DB VALIDATION

## 2705.

Use when persistence is important.

Examples:

```text
order stored
payment state stored
stock changed
cart cleared
```

Do not couple every test unnecessarily to DB implementation.

---

# SECTION 2491 — DATABASE MASTER MODEL

## 2706.

```text
API
 ↓
Repository
 ↓
SQL
 ↓
PostgreSQL
```

Current DB:

```text
PostgreSQL 16
```

running through Docker Compose.

---

# SECTION 2492 — TRANSACTION

## 2707.

Transaction represents logical unit of DB work.

Goal often:

```text
all required changes succeed
or
changes roll back
```

depending on configured transaction boundaries.

---

# SECTION 2493 — ORDER CONSISTENCY

## 2708.

Current project behaviors include:

```text
Create order
→ stock decrement
→ cart clear

Cancel order
→ stock restoration
```

These are valuable integration assertions.

---

# SECTION 2494 — MOCK PAYMENT

## 2709.

Current project has:

```text
mock payment
```

including validation around:

```text
ownership
cancelled order
duplicate payment
```

Do not present this as real payment gateway integration.

---

# SECTION 2495 — ALLURE

## 2710.

Purpose:

```text
reporting
diagnosis
evidence
environment metadata
```

Not simply:

```text
pretty report
```

---

# SECTION 2496 — ALLURE SANITIZATION

## 2711.

Current custom filter masks sensitive values such as:

```text
Authorization
Cookie
Set-Cookie
X-API-Key
API-Key
Bearer token
password
token
accessToken
refreshToken
secret
```

Actual backend request remains unchanged.

Only report attachment is sanitized.

---

# SECTION 2497 — WHY SANITIZE?

## 2712.

CI reports may be:

```text
uploaded
shared
retained
downloaded
```

Therefore sensitive data must not leak through test evidence.

---

# SECTION 2498 — SWAGGER / OPENAPI

## 2713.

Current:

```text
Swagger UI
/v3/api-docs
JWT Authorize
```

Purpose:

```text
API contract exploration
manual testing
debugging
documentation
```

Automation still provides regression.

---

# SECTION 2499 — TEST PYRAMID

## 2714.

```text
          UI
        /    \
       /      \
      API / Integration
     /                \
    Unit / Component
```

Principle:

```text
more fast lower-level tests
fewer expensive UI tests
```

Not rigid percentages.

---

# SECTION 2500 — WHY MORE API THAN UI?

## 2715.

API tests:

```text
faster
more stable
good business-rule coverage
easier diagnosis
```

UI:

```text
critical user journeys
browser behavior
frontend integration
```

Use both strategically.

---

# SECTION 2501 — FLAKY TEST

## 2716.

Flaky:

```text
same code
same intended environment
different result
```

Common causes:

```text
timing
shared data
network
unstable selectors
parallelism
async behavior
environment
```

Do not hide with unlimited retry.

---

# SECTION 2502 — RETRIES

## 2717.

Good:

```text
bounded transient retry
```

Bad:

```text
retry 400
retry 403
retry assertion defect
retry broken locator forever
```

Retries must not hide deterministic bugs.

---

# SECTION 2503 — PARALLEL EXECUTION

## 2718.

Before parallelization verify:

```text
data isolation
account isolation
thread safety
shared resources
DB records
external limits
```

Faster is useful only when reliable.

---

# SECTION 2504 — PERFORMANCE MASTER MODEL

## 2719.

Future tool:

```text
k6
```

Types:

```text
Load
Stress
Spike
Soak
```

Metrics:

```text
p50
p90
p95
p99
throughput
error rate
resource behavior
```

---

# SECTION 2505 — LOAD VS STRESS

## 2720.

Load:

```text
expected workload
```

Stress:

```text
beyond expected capacity
```

---

# SECTION 2506 — SPIKE VS SOAK

## 2721.

Spike:

```text
sudden traffic change
```

Soak:

```text
sustained workload over long duration
```

---

# SECTION 2507 — WHY P95/P99?

## 2722.

Average can hide slow users.

Percentiles show:

```text
tail latency
```

Example:

```text
p95 = 95% requests completed at or below this latency
```

---

# SECTION 2508 — SECURITY MASTER CHECKLIST

## 2723.

```text
Authentication
Authorization
Resource ownership
Input validation
Secret handling
Sensitive response data
TLS
Token handling
Rate limiting
Error leakage
```

---

# SECTION 2509 — XSS VS CSRF

## 2724.

XSS:

```text
malicious script executes in trusted page context
```

CSRF:

```text
victim browser is induced to make unwanted authenticated request
```

Different attacks.

---

# SECTION 2510 — CLIENT-SIDE SECRETS

## 2725.

Future React variables such as:

```text
VITE_*
```

are client-side.

Treat as:

```text
visible to users
```

Never place:

```text
DB password
JWT signing secret
private credentials
```

there.

---

# SECTION 2511 — JWT STORAGE TRADE-OFF

## 2726.

Demo:

```text
localStorage
```

is simple but accessible to JavaScript.

Potential production architecture:

```text
HttpOnly
Secure
SameSite cookie
```

depending on application/security design.

No mechanism is universally correct.

---

# SECTION 2512 — QUALITY ENGINEERING

## 2727.

Quality Engineering means:

```text
requirements
risk
testability
automation
CI/CD
security
performance
observability
release confidence
```

Not only:

```text
executing test cases
```

---

# SECTION 2513 — SHIFT LEFT

## 2728.

Earlier feedback through:

```text
requirement review
contract review
unit tests
API tests
PR automation
static analysis
```

---

# SECTION 2514 — SHIFT RIGHT

## 2729.

After deployment:

```text
monitoring
synthetic checks
observability
production feedback
```

Shift left and right complement each other.

---

# SECTION 2515 — RISK-BASED TESTING

## 2730.

Priority:

```text
Probability of failure
×
Impact of failure
```

High-risk examples:

```text
authentication
authorization
payment
orders
inventory
data integrity
```

---

# SECTION 2516 — RELEASE DECISION

## 2731.

Do not use only:

```text
pass percentage
```

Consider:

```text
critical journey
security
data integrity
customer impact
defect severity
workaround
coverage
environment
rollback
```

---

# SECTION 2517 — BUG OR TEST ISSUE?

## 2732.

```text
Automation fails
      ↓
Reproduce outside automation
      ↓
Compare requirement
      ↓
Product also fails?
   /          \
 YES          NO
  ↓            ↓
Product /     Automation /
Environment   Framework
```

---

# SECTION 2518 — API ISOLATION

## 2733.

REST Assured fails:

```text
Try equivalent curl/Swagger request.
```

If both fail:

```text
product/environment/config likely
```

If external request works:

```text
inspect automation construction/configuration
```

---

# SECTION 2519 — TEST STRATEGY

## 2734.

Think:

```text
Requirements
 ↓
Architecture
 ↓
Risks
 ↓
Test Levels
 ↓
Environment
 ↓
Data
 ↓
Automation
 ↓
CI
 ↓
Reporting
 ↓
Release Gates
```

---

# SECTION 2520 — WHAT TO AUTOMATE FIRST?

## 2735.

Prioritize:

```text
business criticality
regression frequency
manual effort
defect risk
stability
ROI
```

---

# SECTION 2521 — DON'T AUTOMATE EVERYTHING

## 2736.

Poor candidates may include:

```text
one-time validation
unstable prototype
subjective visual evaluation
low-value rare scenario
```

unless risk justifies automation.

---

# SECTION 2522 — TEST DESIGN TECHNIQUES

## 2737.

Remember:

```text
Boundary Value Analysis
Equivalence Partitioning
Decision Table
State Transition
Negative Testing
Risk-Based Testing
```

---

# SECTION 2523 — BOUNDARY

## 2738.

If valid:

```text
1–100
```

test:

```text
0
1
2
99
100
101
```

---

# SECTION 2524 — EQUIVALENCE PARTITIONING

## 2739.

Group inputs expected to behave similarly.

Example:

```text
valid quantity
zero
negative
above stock
```

Test representatives.

---

# SECTION 2525 — DECISION TABLE

## 2740.

Useful for combinations:

```text
Authenticated?
Admin?
Resource exists?
```

Map combinations to expected behavior.

---

# SECTION 2526 — STATE TRANSITION

## 2741.

Useful for:

```text
Order
Payment
Account
Workflow
```

Test:

```text
valid transitions
invalid transitions
repeated transitions
```

---

# SECTION 2527 — TEST DATA ISOLATION

## 2742.

Independent tests should not require:

```text
Test A runs before Test B
```

unless intentionally testing one workflow.

Avoid:

```text
shared mutable data
```

---

# SECTION 2528 — SENIOR SDET CODE REVIEW CHECKLIST

## 2743.

```text
Clear test intent?
Good naming?
Correct assertions?
Reusable design?
Hardcoded URL?
Hardcoded secrets?
Shared data?
Cleanup?
Hard waits?
Useful failure evidence?
Parallel-safe?
CI-friendly?
```

---

# SECTION 2529 — FRAMEWORK REVIEW CHECKLIST

## 2744.

```text
Readable?
Maintainable?
Environment-independent?
Secure?
Diagnosable?
Independent tests?
CI executable?
Scalable?
```

---

# SECTION 2530 — SENIOR SDET INTERVIEW ANSWER FORMULA

## 2745.

For scenario question:

```text
First...
Then...
If...
Otherwise...
Finally...
```

Example:

> "First I verify whether the request reaches the application. If it does not, I investigate DNS, connectivity and port availability. If it reaches the service and returns an HTTP error, I inspect authentication, routing and application logs. Once the root cause is fixed, I run targeted validation followed by risk-based regression."

---

# SECTION 2531 — STAR FOR BEHAVIORAL QUESTIONS

## 2746.

```text
Situation
Task
Action
Result
```

For technical incident add:

```text
Evidence
Root Cause
Prevention
```

---

# SECTION 2532 — 30-SECOND INTRODUCTION

## 2747.

> "I am a Senior SDET and Automation Lead with over seven years of experience in quality engineering across web, mobile, API and integration testing. My core strengths are Java, Selenium, Playwright, REST API automation, Appium and CI/CD. I have worked across insurance, banking, gaming, fitness and retail domains, and I focus on building maintainable automation frameworks, improving regression efficiency and providing reliable release feedback."

Adjust wording according to exact interview role.

---

# SECTION 2533 — 60-SECOND PROJECT INTRODUCTION

## 2748.

> "I built an end-to-end commerce quality-engineering portfolio project using Java 17, Spring Boot and PostgreSQL. The backend includes JWT authentication, role-based authorization, products, cart, orders, mock payment and admin operations. I built a separate REST Assured and TestNG API automation framework with environment switching, reusable authentication and request specifications, database validation and Allure reporting. I also implemented sanitization so sensitive headers and credentials are not exposed in reports. Swagger/OpenAPI is integrated, PostgreSQL runs through Docker Compose, and the current API regression baseline is 48 passing tests. The next phases are React with TypeScript, Playwright, full Dockerization, GitHub Actions, k6 and AWS."

---

# SECTION 2534 — WHY SEPARATE API AUTOMATION?

## 2749.

> "I keep the API framework separate from backend source so it behaves like an external consumer and can target local, QA or stage environments independently."

---

# SECTION 2535 — WHY BUILD YOUR OWN BACKEND?

## 2750.

> "Building the system under test allows me to demonstrate testing beyond UI scripts. I can design and validate authentication, authorization, persistence, business state transitions, database consistency and failure scenarios, and later connect those layers to UI, CI/CD, performance and cloud testing."

---

# SECTION 2536 — WHY POSTGRESQL?

## 2751.

> "I wanted a real relational persistence layer so the project could include database integration, constraints and DB validation rather than relying entirely on mocked data."

---

# SECTION 2537 — WHY DOCKER?

## 2752.

> "Docker gives me reproducible runtime environments and reduces machine-specific setup. Currently I use Docker Compose for PostgreSQL, and later I will containerize the backend and frontend for CI and deployment."

---

# SECTION 2538 — WHY ALLURE?

## 2753.

> "I use Allure for diagnosable automation evidence, including environment metadata and sanitized request-response information, rather than using it only as a visual report."

---

# SECTION 2539 — WHY SWAGGER?

## 2754.

> "Swagger/OpenAPI provides interactive API documentation and helps with manual exploration and debugging. Automated REST Assured tests then provide repeatable regression coverage."

---

# SECTION 2540 — WHY JWT/RBAC?

## 2755.

> "Authentication and authorization are important real-world API risks. JWT and RBAC allow me to validate both identity and permission boundaries, such as ensuring a normal user cannot execute admin product operations."

---

# SECTION 2541 — WHY API + DB?

## 2756.

> "The API validates the external contract, while selective DB assertions validate persistence and integration behavior for high-risk flows such as orders and payment."

---

# SECTION 2542 — WHY PLAYWRIGHT NEXT?

## 2757.

> "The backend and API layer are stable, so the next logical step is to build the frontend and add Playwright for critical end-to-end browser journeys while keeping most business-rule coverage at the API layer."

---

# SECTION 2543 — WHY GITHUB ACTIONS LATER?

## 2758.

> "I first wanted a stable application and automation baseline. CI becomes more meaningful when the same reliable local commands can be executed automatically with externalized configuration and useful reports."

---

# SECTION 2544 — WHY K6 LATER?

## 2759.

> "Performance testing should be added after functional behavior is stable enough to define meaningful workloads and thresholds. I plan to use k6 for API-level performance scenarios and CI-friendly thresholds."

---

# SECTION 2545 — WHY AWS LATER?

## 2760.

> "I want cloud deployment to represent a working system rather than simply adding AWS keywords. After containerization and CI/CD, I can deploy the application and use AWS services in a way that supports realistic environment and observability scenarios."

---

# SECTION 2546 — CURRENT VS FUTURE

## 2761.

Current:

```text
Spring Boot
Java 17
PostgreSQL
JWT/RBAC
REST Assured
TestNG
DB validation
Allure
Swagger/OpenAPI
Docker Compose PostgreSQL
Git/GitHub
48 passing API tests
```

Future:

```text
React
TypeScript
Playwright
UI/API hybrid
full Dockerization
GitHub Actions
k6
AWS
Redis later
Kafka later
```

Never present future work as completed.

---

# SECTION 2547 — RAPID-FIRE QUESTIONS 1–10

## 2762.

### Q1. Git vs GitHub?

```text
Git
→ distributed version control

GitHub
→ remote hosting/collaboration platform
```

### Q2. `git fetch` vs `git pull`?

```text
fetch
→ download

pull
→ download + integrate
```

### Q3. Revert vs reset?

```text
revert
→ new reverse commit

reset
→ move branch pointer
```

### Q4. Merge vs rebase?

```text
merge
→ combine history

rebase
→ replay commits / rewrite hashes
```

### Q5. Why `.gitignore`?

```text
prevent unwanted untracked files from being added
```

### Q6. Secret pushed?

```text
rotate first
```

### Q7. `git status`?

```text
current working/staging/branch state
```

### Q8. `git diff --cached`?

```text
show staged changes
```

### Q9. Reflog?

```text
local reference movement history
```

### Q10. Stash?

```text
temporarily save uncommitted work
```

---

# SECTION 2548 — RAPID-FIRE QUESTIONS 11–20

## 2763.

### Q11. JDK?

```text
Java development kit
```

### Q12. JVM?

```text
executes bytecode
```

### Q13. Bytecode?

```text
compiled Java intermediate instructions
```

### Q14. `JAVA_HOME`?

```text
points to Java installation
```

### Q15. Maven?

```text
build/dependency/plugin lifecycle tool
```

### Q16. `pom.xml`?

```text
Maven project configuration
```

### Q17. Transitive dependency?

```text
dependency brought through another dependency
```

### Q18. Maven Wrapper?

```text
project-controlled Maven launcher/distribution
```

### Q19. `dependency:tree`?

```text
inspect dependency graph
```

### Q20. `NoSuchMethodError`?

```text
often runtime dependency version mismatch
```

---

# SECTION 2549 — RAPID-FIRE QUESTIONS 21–30

## 2764.

### Q21. Spring Boot?

```text
simplifies Spring application setup/runtime
```

### Q22. Dependency injection?

```text
dependencies supplied rather than manually constructed everywhere
```

### Q23. Controller?

```text
handles HTTP-facing request mapping
```

### Q24. Repository?

```text
data-access abstraction
```

### Q25. 401?

```text
authentication
```

### Q26. 403?

```text
authorization
```

### Q27. 404?

```text
route/resource not found
```

### Q28. 500?

```text
server processing error
```

### Q29. JWT?

```text
signed token format containing claims
```

### Q30. RBAC?

```text
role-based access control
```

---

# SECTION 2550 — RAPID-FIRE QUESTIONS 31–40

## 2765.

### Q31. DNS?

```text
hostname → IP resolution
```

### Q32. TCP?

```text
reliable connection-oriented transport
```

### Q33. HTTPS?

```text
HTTP over TLS
```

### Q34. CORS?

```text
browser cross-origin access policy
```

### Q35. Origin?

```text
scheme + host + port
```

### Q36. OPTIONS?

```text
can be used for CORS preflight
```

### Q37. 502?

```text
bad gateway
```

### Q38. 503?

```text
service unavailable
```

### Q39. 504?

```text
gateway timeout
```

### Q40. `curl -v`?

```text
verbose connection/request diagnostics
```

---

# SECTION 2551 — RAPID-FIRE QUESTIONS 41–50

## 2766.

### Q41. Docker image?

```text
packaged immutable template
```

### Q42. Container?

```text
running image instance
```

### Q43. Volume?

```text
persistent Docker-managed storage
```

### Q44. Compose?

```text
multi-service container configuration/orchestration for local-style environments
```

### Q45. `localhost` inside container?

```text
that container itself
```

### Q46. CI?

```text
continuous integration
```

### Q47. CI artifact?

```text
preserved pipeline output
```

### Q48. CI cache?

```text
reusable data to improve pipeline speed
```

### Q49. Quality gate?

```text
conditions required before progression
```

### Q50. Green build with zero tests?

```text
not valid regression evidence
```

---

# SECTION 2552 — 10 SENIOR SCENARIOS

## 2767.

### Scenario 1

```text
Works locally, fails CI.
```

Check:

```text
commit
Java
dependencies
environment
secrets
network
test scope
```

### Scenario 2

```text
Container running, API unavailable.
```

Check:

```text
logs
health
port
process
network
```

### Scenario 3

```text
Postman works, browser fails.
```

Check:

```text
CORS/browser behavior
```

### Scenario 4

```text
USER gets 403.
```

Check:

```text
expected RBAC?
role?
endpoint rule?
```

### Scenario 5

```text
ADMIN gets 403.
```

Check:

```text
token
role claim
authority mapping
security config
```

### Scenario 6

```text
API 201, DB record missing.
```

Check:

```text
correct DB
transaction
persistence
async behavior
```

### Scenario 7

```text
Test only fails in suite.
```

Check:

```text
shared state
order dependency
cleanup
parallelism
```

### Scenario 8

```text
Pipeline green but test count dropped.
```

Check:

```text
test discovery
deleted/excluded test
suite configuration
```

### Scenario 9

```text
Certificate error.
```

Check:

```text
expiry
hostname
CA
chain
trust
```

### Scenario 10

```text
Secret appears in report.
```

Action:

```text
contain
rotate if real credential
fix sanitizer
rerun
prevent recurrence
```

---

# SECTION 2553 — 10 DEBUGGING COMMANDS TO REMEMBER

## 2768.

```bash
git status
```

```bash
git diff
```

```bash
java -version
```

```bash
mvn -version
```

```bash
mvn dependency:tree
```

```bash
lsof -i :8080
```

```bash
curl -i <URL>
```

```bash
curl -v <URL>
```

```bash
docker compose ps
```

```bash
docker compose logs <service>
```

These ten commands already cover a large amount of first-level investigation.

---

# SECTION 2554 — 5 QUESTIONS BEFORE CHANGING CODE

## 2769.

Ask:

```text
1. Can I reproduce it?
2. Which layer failed?
3. What changed?
4. What evidence proves my assumption?
5. Is this product, test, configuration or environment?
```

Only then change code.

---

# SECTION 2555 — 5 QUESTIONS BEFORE RELEASE

## 2770.

```text
1. Did critical journeys pass?
2. Any security/data-integrity risk?
3. Are failures understood?
4. Is environment representative?
5. What residual risk remains?
```

---

# SECTION 2556 — 5 QUESTIONS BEFORE AUTOMATING

## 2771.

```text
1. Is scenario valuable?
2. Is it repeated?
3. Is behavior stable enough?
4. Which layer should test it?
5. Is maintenance cost justified?
```

---

# SECTION 2557 — 5 QUESTIONS BEFORE PARALLELIZATION

## 2772.

```text
1. Is test data isolated?
2. Are accounts shared?
3. Is framework thread-safe?
4. Are DB records shared?
5. Can external service handle concurrency?
```

---

# SECTION 2558 — 5 QUESTIONS BEFORE RETRY

## 2773.

```text
1. Is failure genuinely transient?
2. Why should retry succeed?
3. Is retry bounded?
4. Will retry hide a defect?
5. Will failure remain observable?
```

---

# SECTION 2559 — 5 QUESTIONS BEFORE ADDING A TOOL

## 2774.

```text
1. What problem does it solve?
2. Do we already solve that problem?
3. What maintenance does it add?
4. Can I explain it in interview?
5. Can I demonstrate real usage?
```

This is why:

```text
Kafka
Redis
AWS
```

should be added when they solve a real project problem, not just for keywords.

---

# SECTION 2560 — SENIOR SDET TOOL COMPARISON

## 2775.

```text
Git
→ version control

GitHub
→ collaboration/remote repository

Maven
→ Java build/dependency management

Spring Boot
→ backend application framework

PostgreSQL
→ relational database

Docker
→ containerization

Docker Compose
→ multi-service container configuration

REST Assured
→ Java API automation

TestNG
→ test execution framework

Allure
→ reporting/evidence

Swagger/OpenAPI
→ API documentation/exploration

Node.js
→ JavaScript runtime

npm
→ Node package management

React
→ future frontend

Playwright
→ future UI automation

k6
→ future performance testing

GitHub Actions
→ future CI/CD

AWS
→ future cloud deployment
```

---

# SECTION 2561 — CURRENT PROJECT ARCHITECTURE

## 2776.

Current:

```text
                 REST Assured
                      │
                      ▼
                 Spring Boot
                      │
          ┌───────────┼───────────┐
          ▼           ▼           ▼
        JWT         RBAC      Business APIs
                                  │
                                  ▼
                                 JPA
                                  │
                                  ▼
                             PostgreSQL
                                  │
                                  ▼
                         Docker Compose
```

Supporting:

```text
TestNG
Allure
Swagger
Git/GitHub
Maven
```

---

# SECTION 2562 — FUTURE ARCHITECTURE

## 2777.

```text
                React + TypeScript
                       │
                       ▼
                  Spring Boot
                       │
                       ▼
                   PostgreSQL

Testing:

Playwright ───────► React UI
REST Assured ─────► Backend API
JDBC/SQL ─────────► Database
k6 ───────────────► Performance

Infrastructure:

Docker
GitHub Actions
AWS
```

---

# SECTION 2563 — CURRENT PROJECT MODULES

## 2778.

```text
User
→ Register
→ Login
→ Profile

Products
→ Get
→ Search
→ Details
→ Admin CRUD

Cart
→ Add
→ Update
→ Remove
→ Clear

Orders
→ Create
→ Get
→ List
→ Cancel

Payment
→ Mock payment

Security
→ JWT
→ USER / ADMIN RBAC
```

---

# SECTION 2564 — PROJECT TEST COVERAGE SUMMARY

## 2779.

Current automation covers areas including:

```text
Products
Cart
Orders
Payment
RBAC
Validation
Security
Database integration
```

Current regression baseline:

```text
48 tests
48 passed
```

---

# SECTION 2565 — PROJECT SECURITY STORY

## 2780.

Strong interview points:

```text
JWT authentication
role-based authorization
401/403 distinction
resource ownership validation
secret externalization
.env ignored
sanitized Allure reports
Swagger JWT Authorize
```

---

# SECTION 2566 — PROJECT FRAMEWORK STORY

## 2781.

Strong points:

```text
separate API automation module
environment switching
reusable request specification
authentication helper
base test
test-data utilities
cleanup
DB validation
Allure reporting
secret sanitization
```

---

# SECTION 2567 — PROJECT DEVOPS STORY

## 2782.

Current:

```text
Git
GitHub
Maven
Docker Compose
environment configuration
shell scripts
```

Planned:

```text
full Dockerization
GitHub Actions
AWS
```

This distinction is important.

---

# SECTION 2568 — WHAT NOT TO CLAIM

## 2783.

Do not currently say:

```text
I deployed this project to AWS.
I implemented Kafka.
I implemented Redis.
I built React frontend.
I implemented Playwright UI suite.
I implemented GitHub Actions pipeline.
I integrated a real payment gateway.
```

These are future phases.

Interview credibility matters more than keyword count.

---

# SECTION 2569 — HOW TO ANSWER "I DON'T KNOW"

## 2784.

Good:

> "I haven't implemented that directly yet, but my understanding is..."

Then explain what you genuinely understand.

Even better:

> "I haven't used that in this project yet. It is part of my next phase because..."

Never fabricate implementation experience.

---

# SECTION 2570 — HOW TO ANSWER UNKNOWN INCIDENT

## 2785.

If interviewer gives unfamiliar problem:

> "I haven't encountered that exact failure, but I would start by identifying the layer and collecting evidence..."

Then use:

```text
Observe
Classify
Isolate
Verify
Fix
Retest
Prevent
```

Reasoning matters.

---

# SECTION 2571 — 30-SECOND TROUBLESHOOTING ANSWER

## 2786.

> "I troubleshoot by first reproducing and classifying the failure rather than immediately modifying the test. I identify whether it belongs to the application, automation, environment, network, database or pipeline, collect evidence from requests, logs and configuration, isolate the failing layer, fix the root cause, run targeted validation and then perform risk-based regression. If possible, I also add a preventive check so the same issue is detected earlier."

---

# SECTION 2572 — 30-SECOND AUTOMATION STRATEGY ANSWER

## 2787.

> "I use risk and feedback speed to decide the automation layer. Business rules and service behavior are covered primarily through API and integration tests, while UI automation focuses on critical user journeys and frontend-specific risks. I keep test data isolated, configuration externalized and the framework CI-friendly so failures are reliable and easy to diagnose."

---

# SECTION 2573 — 30-SECOND CI/CD ANSWER

## 2788.

> "For CI/CD, I expect every change to be reproducibly built and tested from the command line using externalized configuration and secure secrets. I classify pipeline failures by stage — checkout, runtime setup, dependency resolution, build, test or reporting — and I treat expected test execution and useful artifacts as part of pipeline correctness, not just a green job status."

---

# SECTION 2574 — 30-SECOND API ANSWER

## 2789.

> "My API testing approach validates more than HTTP status codes. I cover positive and negative behavior, authentication, authorization, business rules, schema where useful, state transitions and selective database persistence. I keep authentication, request configuration and test data reusable so tests remain readable and environment-independent."

---

# SECTION 2575 — 30-SECOND SECURITY ANSWER

## 2790.

> "At the SDET level I treat authentication, authorization, resource ownership, secret handling and sensitive-data exposure as core quality risks. In my portfolio project I implemented JWT and RBAC tests and also sanitized Allure HTTP evidence so tokens, cookies and password-like fields are not leaked through test reports."

---

# SECTION 2576 — 30-SECOND DOCKER ANSWER

## 2791.

> "I use Docker to make dependencies and runtime environments reproducible. My current project runs PostgreSQL through Docker Compose with persistent storage. I understand that a running container does not necessarily mean a healthy service and that localhost inside a container refers to that container itself, which is important when debugging multi-container networking."

---

# SECTION 2577 — 30-SECOND MAVEN ANSWER

## 2792.

> "I use Maven for Java build orchestration, dependency management, plugin execution and test execution. During failures I identify whether the issue is dependency resolution, compilation, test compilation, test execution or packaging, and I use tools such as dependency:tree and version checks instead of treating every Maven failure as a test failure."

---

# SECTION 2578 — 30-SECOND GIT ANSWER

## 2793.

> "I use Git with a review-first workflow: inspect status and diffs, stage intentional changes, review the staged diff, commit logically and then push. I understand merge, rebase, revert, reset, stash and reflog, and I treat secrets carefully because removing a secret in a later commit does not undo exposure."

---

# SECTION 2579 — 30-SECOND JAVA ANSWER

## 2794.

> "I understand the Java build and runtime path from source compilation into bytecode and JVM execution. For build issues I verify Java and Maven runtime versions, JAVA_HOME, dependency compatibility and runtime classpath. Errors such as class-version mismatches or NoSuchMethodError often point to runtime or dependency compatibility rather than application logic."

---

# SECTION 2580 — FINAL 5-MINUTE REVISION

## 2795.

Read only this:

```text
1. Observe → Classify → Isolate → Verify → Fix → Retest → Prevent.

2. 401 = authentication.
   403 = authorization.

3. DNS → TCP → TLS → HTTP.

4. Postman works/browser fails → think CORS.

5. localhost inside container = that container.

6. Green pipeline + zero/missing tests ≠ valid regression.

7. Maven failure:
   dependency → compile → test compile → test → package.

8. Works local/fails CI:
   compare commit, runtime, config, secrets, network, data.

9. Secret exposed:
   rotate/revoke first.

10. Flaky test:
    find root cause before retry.

11. API testing:
    status + contract + business + security + state + DB where useful.

12. UI:
    critical user journeys.

13. Risk determines testing depth.

14. Current project:
    Spring Boot + PostgreSQL + REST Assured + TestNG
    + JWT/RBAC + DB validation + Allure + Swagger + Docker Compose.

15. Current API regression:
    48/48.

16. Future:
    React + TypeScript → Playwright → Docker → GitHub Actions → k6 → AWS.

17. Never claim future work as completed.

18. Explain WHY a tool exists, not only syntax.

19. When unsure:
    collect evidence.

20. Senior SDET:
    quality engineer, not automation-script writer.
```

---

# SECTION 2581 — FINAL INTERVIEW MENTAL MAP

## 2796.

```text
                     SENIOR SDET
                         │
        ┌────────────────┼────────────────┐
        ▼                ▼                ▼
   ENGINEERING         TESTING          DELIVERY
        │                │                │
   Java/Linux          API/UI           Git
   Maven               DB               CI/CD
   Spring Boot         Security         Docker
   HTTP/Network        Performance      Cloud
        │                │                │
        └────────────────┼────────────────┘
                         ▼
                    QUALITY RISK
                         │
                         ▼
                     EVIDENCE
                         │
                         ▼
                 RELEASE CONFIDENCE
```

---

# SECTION 2582 — FINAL SENIOR SDET PRINCIPLES

## 2797.

Remember:

```text
Don't automate because you can.
Automate because it provides valuable feedback.

Don't retry because a test failed.
Retry only when failure is genuinely transient.

Don't blame environment without evidence.
Verify it.

Don't trust green pipeline blindly.
Verify expected tests executed.

Don't expose secrets for debugging convenience.
Use safe diagnostics.

Don't test only happy paths.
Validate failure behavior.

Don't use UI for every business rule.
Choose the right test layer.

Don't add technology only for résumé keywords.
Use it to solve a real problem.

Don't report test percentages without risk context.
Explain impact.

Don't stop after fixing.
Prevent recurrence.
```

---

# SECTION 2583 — FINAL PROJECT DEFENSE CHECK

## 2798.

Before interview, you should be able to explain without notes:

```text
Why Java 17?
Why Spring Boot?
Why PostgreSQL?
Why Docker Compose?
Why separate API automation?
Why REST Assured?
Why TestNG?
Why RequestSpecFactory?
Why AuthHelper?
Why DB validation?
Why JWT?
Why RBAC?
Why 401 vs 403?
Why Allure?
Why sanitize reports?
Why Swagger/OpenAPI?
Why environment switching?
Why test pyramid?
Why 48 tests matter?
Why React next?
Why Playwright next?
Why CI after stable local execution?
Why k6?
Why AWS?
Why Kafka/Redis later?
```

If you can explain these naturally:

```text
you understand the project.
```

---

# SECTION 2584 — FINAL PROJECT STORY

## 2799.

```text
I did not build only an automation framework.

I built:

System Under Test
      +
API
      +
Database
      +
Security
      +
Automation
      +
Reporting
      +
Environment Strategy
      +
Containerized Dependency
      +
Version Control
      +
Documentation

And I am evolving it toward:

Frontend
      +
UI Automation
      +
CI/CD
      +
Performance
      +
Cloud
```

This is the portfolio story.

---

# SECTION 2585 — FINAL SENIOR SDET ANSWER

## 2800.

> "My approach to SDET work is to understand the complete system rather than treating automation as a separate activity. I look at requirements, architecture, APIs, databases, security, runtime configuration and delivery pipelines, and then choose the appropriate test layer based on risk. I build automation that is maintainable, environment-independent, secure and easy to diagnose. When failures occur, I classify and isolate them using evidence rather than relying on retries or assumptions. My goal is to provide fast, trustworthy quality feedback that helps the team make better release decisions."

---

# SECTION 2586 — FINAL STATUS OF ENGINEERING TOOLING NOTES

## 2801.

Completed:

```text
PART 1
Maven — Zero to Advanced

PART 2
JDK, JRE, JVM & Java Build Fundamentals

PART 3
Spring Boot Build, Startup, Configuration & Runtime

PART 4
HTTP, HTTPS, DNS, TCP, CORS, Proxy & API Network Troubleshooting

PART 5
Docker & Docker Compose

PART 6
Node.js, npm & Package Management

PART 7
JSON, YAML & Configuration Management

PART 8
Dependencies, Artifacts, Repositories & Build Ecosystem

PART 9
Build & Environment Troubleshooting

PART 10
CI/CD Tooling Connection

PART 11
Senior SDET Tooling & System Troubleshooting Interview Master Set

PART 12
Final Rapid Revision / Cheat Sheet
```

---

# SECTION 2587 — DOCUMENT COMPLETION

## 2802.

```text
SDET-Engineering-Tooling-Notes.md

STATUS:

COMPLETE
```

You now have separate detailed notes for:

```text
Linux + Git
Engineering Tooling
Main SDET Project Learning
```

Keep them separate.

This makes revision easier than maintaining one extremely large mixed document.

---

# SECTION 2588 — WHAT COMES NEXT

## 2803.

Do not continue adding random theory to this file.

Next project-learning sequence:

```text
1. Append latest GitHub milestone to main SDET notes

2. React + TypeScript frontend

3. Connect frontend to Spring Boot API

4. Authentication + RBAC UI

5. Products / Cart / Orders / Payment UI

6. Playwright + TypeScript framework

7. UI + API hybrid testing

8. Full application Dockerization

9. GitHub Actions CI/CD

10. k6 performance testing

11. AWS deployment

12. Final GitHub portfolio polishing
```

From this point:

```text
Learn by implementing.
```

Not:

```text
Learn by endlessly collecting notes.
```

---

# SECTION 2589 — FINAL LINE

## 2804.

```text
Understand the system.
Test the risk.
Automate the feedback.
Debug with evidence.
Protect the secrets.
Integrate with CI.
Measure the quality.
Communicate the risk.
```

That is the Senior SDET mindset.

---

# END OF PART 12

# END OF SDET ENGINEERING TOOLING NOTES