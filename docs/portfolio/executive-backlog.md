# Executive Backlog - Challenge Portfolio

## Objective

Provide a single executive backlog format to compare the projects in the workspace:

- `votacao-backend`
- `desafioMagalu`
- `desafioBTG`
- `desafio-fullstack`

Planning assumption used across all projects:

- 2 points = 1 person-day
- estimates are directional and should be recalibrated at the end of each sprint

## Unified Delivery Streams

### 1. Foundation and Local Setup

Common objective:

- stabilize build, local environment, configuration, and developer onboarding

Shared backlog items:

- validate Docker Compose and required infrastructure
- standardize build, tests, coverage, and CI execution
- review environment variables and local startup guides
- confirm architecture baseline and project conventions

Project focus:

| Project | Main items | Estimate |
| --- | --- | --- |
| `votacao-backend` | gateway, discovery, domain services, MongoDB, RabbitMQ, frontend integration baseline | 16 to 24 points |
| `desafioMagalu` | `ms-agendamento`, MySQL, Flyway, Docker Compose, local execution baseline | 10 to 16 points |
| `desafioBTG` | frontend, `ms-customer`, `ms-order`, MySQL, RabbitMQ, root build | 12 to 18 points |
| `desafio-fullstack` | `frontend`, `backend-module`, `ejb-module`, local DB setup, integrated compose baseline | 10 to 16 points |

### 2. Core Domain Delivery

Common objective:

- implement the challenge-critical business capabilities with stable domain rules

Shared backlog items:

- finalize domain models and persistence flows
- implement primary business APIs and validations
- document critical rules and expected outcomes
- cover the main business flow with unit tests

Project focus:

| Project | Main items | Estimate |
| --- | --- | --- |
| `votacao-backend` | members, agendas, sessions, vote registration, session closing, result counting | 36 to 56 points |
| `desafioMagalu` | create, retrieve, list, and delete communication schedules | 16 to 24 points |
| `desafioBTG` | RabbitMQ order consumption, order persistence, customer aggregates, report APIs | 24 to 38 points |
| `desafio-fullstack` | benefits CRUD, activate/cancel operations, transfer flow through EJB | 20 to 30 points |

### 3. Contracts, Integrations, and Consistency

Common objective:

- stabilize contracts between services, infrastructure, and clients

Shared backlog items:

- publish or validate OpenAPI contracts
- verify integration flows with real infrastructure containers
- enforce consistent error handling and contract regression protection
- document payloads, events, and integration boundaries

Project focus:

| Project | Main items | Estimate |
| --- | --- | --- |
| `votacao-backend` | API contracts, RabbitMQ events, idempotent consumers, frontend stability | 24 to 40 points |
| `desafioMagalu` | OpenAPI stabilization, integration tests with MySQL, contract regression | 8 to 12 points |
| `desafioBTG` | report contracts, frontend/backend alignment, message idempotency, retry and failure handling | 22 to 34 points |
| `desafio-fullstack` | OpenAPI hardening, frontend/backend contract alignment, transfer reliability across backend and EJB | 16 to 24 points |

### 4. Quality, Observability, and Release Readiness

Common objective:

- make each project demonstrable, maintainable, and safe to evolve

Shared backlog items:

- improve logs, health checks, and troubleshooting
- validate coverage thresholds and release checklists
- review performance hotspots and configuration risks
- consolidate operational and evaluator-facing documentation

Project focus:

| Project | Main items | Estimate |
| --- | --- | --- |
| `votacao-backend` | observability, hardening, Docker validation, release readiness | 8 to 16 points |
| `desafioMagalu` | health checks, error handling, Docker validation, evaluation readiness | 6 to 10 points |
| `desafioBTG` | logs, query performance, Docker validation, technical report readiness | 8 to 12 points |
| `desafio-fullstack` | static quality, troubleshooting maturity, and release demonstration readiness | 8 to 12 points |

## Suggested Sprint Mapping

| Sprint | `votacao-backend` | `desafioMagalu` | `desafioBTG` | `desafio-fullstack` |
| --- | --- | --- | --- | --- |
| Sprint 1 | foundation, contracts, startup baseline | foundation, migrations, API baseline | foundation, frontend/backend baseline, message contract review | foundation, multi-module build, API baseline |
| Sprint 2 | core CRUDs and vote flow | core scheduling endpoints | core ingestion and report APIs | benefits CRUD and state operations |
| Sprint 3 | events, integration consistency, idempotency | contract hardening and integration stability | idempotency, retries, frontend report integration | frontend/backend integration and transfer hardening |
| Sprint 4 | observability and release readiness | observability and evaluation readiness | observability, hardening, and delivery report | quality, observability, and release readiness |

## Executive Priorities

### Priority 1

- complete the Must Have scope for each challenge
- guarantee build, tests, and documentation are evaluator-ready

### Priority 2

- stabilize contracts and integration behavior
- reduce hidden risk in infrastructure, queue handling, and persistence

### Priority 3

- improve observability, developer experience, and optional automation

## Total Estimated Effort

| Project | Total points | Total person-days |
| --- | --- | --- |
| `votacao-backend` | 84 to 136 | 42 to 68 |
| `desafioMagalu` | 50 to 78 | 25 to 39 |
| `desafioBTG` | 66 to 102 | 33 to 51 |
| `desafio-fullstack` | 54 to 82 | 27 to 41 |