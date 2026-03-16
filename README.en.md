# votacao-backend

[Português](README.md) | [English](README.en.md)

Voting backend based on Spring Boot microservices, Spring Cloud, Eureka, MongoDB, RabbitMQ, and an Angular frontend.

## Executive Summary

This repository hosts the distributed voting solution, organized into independent services for members, agendas, and voting sessions, with service discovery and an entry gateway.

Project goals:

- separate domain responsibilities by microservice
- enable incremental evolution through clear API contracts
- support synchronous and asynchronous integration flows
- keep quality visible through tests, coverage, and CI automation

Main scope:

- `ms-associados`: member management
- `ms-pautas`: agenda management
- `ms-sessoes`: session opening, vote registration, closing, and result counting
- `api-gateway`: client entry point
- `service-discovery`: service registry and discovery
- `frontend`: Angular UI for the business flow

Architecture at a glance:

Frontend
	|
API Gateway
	|
Domain microservices
	|
RabbitMQ + MongoDB

## Stack

- Java 17
- Spring Boot 3.5.x
- Spring Cloud 2025.0.0
- Maven multi-module
- Angular
- Docker Compose
- GitHub Actions + Codecov

## Project Status

- microservices architecture established
- build, test, and coverage pipelines published per module
- technical documentation available per module and under `docs/architecture`
- detailed roadmap available in [plano.md](plano.md)

## Build, Tests and Coverage

| Type | Status |
| --- | --- |
| Build | [![Build ms-associados](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Build ms-pautas](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Build ms-sessoes](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Build api-gateway](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Build service-discovery](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |
| Tests | [![Tests ms-associados](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-associados.yml?branch=main&label=tests-ms-associados)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Tests ms-pautas](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-pautas.yml?branch=main&label=tests-ms-pautas)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Tests ms-sessoes](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-sessoes.yml?branch=main&label=tests-ms-sessoes)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Tests api-gateway](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-gateway.yml?branch=main&label=tests-api-gateway)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Tests service-discovery](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-discovery.yml?branch=main&label=tests-service-discovery)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |
| Coverage (JaCoCo) | [![Coverage ms-associados](https://img.shields.io/badge/coverage-ms--associados%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Coverage ms-pautas](https://img.shields.io/badge/coverage-ms--pautas%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Coverage ms-sessoes](https://img.shields.io/badge/coverage-ms--sessoes%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Coverage api-gateway](https://img.shields.io/badge/coverage-api--gateway%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Coverage service-discovery](https://img.shields.io/badge/coverage-service--discovery%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |

Coverage reports are generated with JaCoCo and published as artifacts in each module's test job.

## Table of Contents

- [Executive Summary](#executive-summary)
- [Stack](#stack)
- [Project Status](#project-status)
- [Module Documentation](#module-documentation)
- [Build, Tests and Coverage](#build-tests-and-coverage)
- [Aggregator Modules](#aggregator-modules)
- [Ports and Discovery](#ports-and-discovery)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Tests](#tests)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [References](#references)

## Aggregator Modules

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

## Quick Start

### Essential local startup

1. run the full build at the repository root
2. start `service-discovery`
3. start `api-gateway`
4. start `ms-associados`, `ms-pautas`, and `ms-sessoes`
5. start the frontend if needed

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

### Expected result

- Eureka available at `http://localhost:8761`
- Gateway available at `http://localhost:8082`
- domain services automatically registered in Eureka

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

## References

- roadmap and planning: [plano.md](plano.md)
- executive portfolio backlog: [docs/portfolio/executive-backlog.md](docs/portfolio/executive-backlog.md)
- challenge comparison matrix: [docs/portfolio/challenge-comparison.md](docs/portfolio/challenge-comparison.md)
- architecture diagrams: [docs/architecture](docs/architecture)
- frontend: [frontend/README.md](frontend/README.md)
- ms-associados: [ms-associados/README.en.md](ms-associados/README.en.md)
- ms-pautas: [ms-pautas/README.en.md](ms-pautas/README.en.md)
