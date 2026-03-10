# votacao-backend

[Português](README.md) | [English](README.en.md)

![Java](https://img.shields.io/badge/java-17-orange)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.5.x-6DB33F)
![Status](https://img.shields.io/badge/status-active-brightgreen)

Voting backend based on a Spring Boot microservices architecture with Spring Cloud and Eureka.

## Table of Contents

- [Overview](#overview)
- [Ports and Discovery](#ports-and-discovery)
- [Requirements](#requirements)
- [How to Run](#how-to-run)
- [Configuration](#configuration)
- [Tests](#tests)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)

## Overview

- Java 17
- Spring Boot 3.5.x
- Spring Cloud 2025.0.0
- Maven multi-module project

Modules in the root aggregator (`pom.xml`):

- `service-discovery` (Eureka Server)
- `api-gateway` (entry gateway)
- `ms-associados`
- `ms-pautas`
- `ms-sessoes`

## Ports and Discovery

- `service-discovery`: `http://localhost:8761`
- `api-gateway`: `http://localhost:8082`
- `ms-associados`, `ms-pautas`, `ms-sessoes`: `server.port=0` (dynamic port, registered in Eureka)

## Requirements

- JDK 17
- Maven 3.9+ (or `mvnw`)

## How to Run

### 1. Full build

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend"
.\mvnw.cmd clean install
```

### 2. Start services (recommended order)

1. `service-discovery`
2. `api-gateway`
3. `ms-associados`
4. `ms-pautas`
5. `ms-sessoes`

Example (one module per terminal):

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend\service-discovery"
..\mvnw.cmd spring-boot:run
```

Repeat for other modules by changing directory.

## Configuration

Domain microservices use optional `.env` import (`spring.config.import=optional:file:.env`) and expect properties such as:

- `dburl-associados`, `dburl-pautas`, `dburl-sessoes`
- `dbuser`, `dbpassword`
- `eurekaurl`

## Tests

Run tests for all modules:

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend"
.\mvnw.cmd test
```

Run tests for one module:

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend\ms-sessoes"
..\mvnw.cmd test
```

## Docker

There is a `docker-compose.yaml` file for an app + MongoDB environment.

```powershell
docker compose up --build
```

If you use `.env` variables, adjust values before starting.

## Troubleshooting

- Services do not appear in Eureka: verify `eurekaurl` and make sure discovery starts first.
- Database connection errors: validate `dburl-*`, username, and password.
- Port conflicts on `8082`/`8761`: change `application.properties` in fixed-port modules.
