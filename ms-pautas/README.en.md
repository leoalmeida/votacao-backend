# ms-pautas

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.0.0-6DB33F?logo=spring&logoColor=white)
![Eureka Client](https://img.shields.io/badge/Eureka-Client-E50914?logo=netflix&logoColor=white)
![OpenFeign](https://img.shields.io/badge/OpenFeign-Client-6DB33F?logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-success)
![Codecov](https://img.shields.io/badge/Codecov-enabled-F01F7A?logo=codecov&logoColor=white)

[PT-BR](README.md) | [EN](README.en.md)

Microservice responsible for the agendas/topics domain in the voting ecosystem.

## Overview

- Project: `ms-pautas`
- Port: dynamic (`server.port=0`)
- Registry: Eureka Client
- Java: `17`
- Spring Boot: `3.5.4`
- Spring Cloud: `2025.0.0`

## Tech Stack

- Spring Boot Web
- Spring Data JPA
- Flyway
- OpenFeign
- Eureka Client
- MySQL
- Maven
- JaCoCo
- Checkstyle
- PMD
- SpotBugs

## Configuration

Main file: `src/main/resources/application.properties`

```properties
spring.config.import=optional:file:.env
spring.application.name=ms-pautas
spring.datasource.url=${dburl-pautas}
spring.datasource.username=${dbuser}
spring.datasource.password=${dbpassword}
eureka.client.serviceUrl.defaultZone=${eurekaurl}
server.port=0
```

Expected `.env` variables:

- `dburl-pautas`
- `dbuser`
- `dbpassword`
- `eurekaurl`

## Run Locally

### Windows (PowerShell)

```powershell
.\mvnw spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

## Build and Tests

```bash
./mvnw clean package
```

## Code Quality

```bash
./mvnw checkstyle:check
./mvnw pmd:check
./mvnw spotbugs:check
```

Note: in the current workflow, `spotless:check` is commented out.

## Test Coverage

```bash
./mvnw jacoco:report
```

Report location:

- `target/site/jacoco/index.html`

## CI Pipeline

Main workflow in repository root:

- `.github/workflows/workflow-backend-pautas.yml`

Stages:

- Quality analysis (Checkstyle, PMD, SpotBugs)
- Tests + JaCoCo
- Codecov upload
- Build and artifact upload

## License

Internal usage for study/challenge purposes.
