# votacao-backend

[Português](README.md) | [English](README.en.md)

## Build, Tests and Coverage

| Type | Status |
| --- | --- |
| Build | [![Build ms-associados](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Build ms-pautas](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Build ms-sessoes](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Build api-gateway](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Build service-discovery](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |
| Tests | [![Tests ms-associados](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-associados.yml?branch=main&label=tests-ms-associados)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Tests ms-pautas](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-pautas.yml?branch=main&label=tests-ms-pautas)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Tests ms-sessoes](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-sessoes.yml?branch=main&label=tests-ms-sessoes)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Tests api-gateway](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-gateway.yml?branch=main&label=tests-api-gateway)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Tests service-discovery](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-discovery.yml?branch=main&label=tests-service-discovery)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |
| Coverage (JaCoCo) | [![Coverage ms-associados](https://img.shields.io/badge/coverage-ms--associados%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Coverage ms-pautas](https://img.shields.io/badge/coverage-ms--pautas%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Coverage ms-sessoes](https://img.shields.io/badge/coverage-ms--sessoes%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Coverage api-gateway](https://img.shields.io/badge/coverage-api--gateway%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Coverage service-discovery](https://img.shields.io/badge/coverage-service--discovery%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |

Coverage reports are generated with JaCoCo and published as artifacts in each module's test job.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-6DB33F?logo=spring&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-Service%20Discovery-2C3E50)

Voting backend based on a Spring Boot microservices architecture with Spring Cloud and Eureka.

## Table of Contents

- [Overview](#overview)
- [Module Documentation](#module-documentation)
- [Build, Tests and Coverage](#build-tests-and-coverage)
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

## Module Documentation

| Module | README PT-BR | README EN |
| --- | --- | --- |
| frontend | [README.md](frontend/README.md) | - |
| ms-associados | [README.md](ms-associados/README.md) | [README.en.md](ms-associados/README.en.md) |
| ms-pautas | [README.md](ms-pautas/README.md) | [README.en.md](ms-pautas/README.en.md) |
| ms-sessoes | - | - |

Modules in dedicated repositories:

- `api-gateway` (with PT/EN README)
- `service-discovery` (with PT/EN README)

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
