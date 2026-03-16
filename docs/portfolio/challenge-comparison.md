# Challenge Comparison Matrix

## Consolidated View

| Project | Primary outcome | Main technical scope | Total estimate | Must Have focus | Main risk |
| --- | --- | --- | --- | --- | --- |
| `votacao-backend` | end-to-end voting workflow | Angular frontend, gateway, discovery, 3 domain services, MongoDB, RabbitMQ | 84 to 136 points / 42 to 68 person-days | member, agenda, and session flow with stable APIs and core integration tests | contract drift and asynchronous integration inconsistency |
| `desafioMagalu` | reliable communication scheduling API | single Spring Boot service, MySQL, Flyway, OpenAPI, Docker Compose | 50 to 78 points / 25 to 39 person-days | create, retrieve, list, and delete schedules with persistence and API documentation | data model not being ready for future delivery-status evolution |
| `desafioBTG` | order ingestion and report queries | Angular frontend, `ms-customer`, `ms-order`, MySQL, RabbitMQ | 66 to 102 points / 33 to 51 person-days | consume orders, persist aggregates, expose report APIs, and integrate frontend | duplicate message processing and backend/frontend contract mismatch |

## Priority Matrix

| Project | Must Have | Should Have | Could Have | Won't Have in current phase |
| --- | --- | --- | --- | --- |
| `votacao-backend` | local setup, core CRUDs, voting flow, OpenAPI, CI, critical tests | versioned events, retries, DLQ, frontend integration, logs | dashboards, generated clients, performance tests, deploy automation | external IAM, Kubernetes, multi-region DR, advanced analytics |
| `desafioMagalu` | core scheduling endpoints, MySQL persistence, OpenAPI, CI, critical tests | delivery-status-ready schema, standardized API errors, health checks | extra resilience scenarios, richer examples, operational metrics | real channel delivery implementation, multi-service orchestration, external IAM |
| `desafioBTG` | RabbitMQ consumption, MySQL persistence, report APIs, frontend integration, CI, critical tests | idempotency, retries, updated OpenAPI, local troubleshooting | dashboards, generated clients, performance tests | full gateway/discovery ecosystem in this repo, corporate IAM, advanced cloud deployment |

## Risk Register Summary

| Project | Risk | Impact | Mitigation |
| --- | --- | --- | --- |
| `votacao-backend` | API contract and implementation diverge across services | frontend breakage and integration churn | validate OpenAPI in CI and keep contract-first workflow |
| `votacao-backend` | asynchronous processing becomes non-idempotent | duplicate events and inconsistent domain state | enforce `eventId`, retry policy, and DLQ strategy |
| `desafioMagalu` | schedule schema becomes too narrow for future delivery stages | rework in persistence and service layer | model status lifecycle early and version migrations with Flyway |
| `desafioMagalu` | local DB configuration varies across environments | onboarding friction and unstable tests | standardize `.env`, Docker Compose, and local run documentation |
| `desafioBTG` | duplicated queue consumption corrupts aggregates | wrong totals and customer counts | process idempotently by order identifier |
| `desafioBTG` | frontend and backend evolve on different contracts | broken report screens and rework | validate contracts in CI and keep frontend integration aligned to stable APIs |

## Comparative Readout

- `votacao-backend` is the broadest scope and carries the highest integration complexity because it combines multiple services, synchronous and asynchronous flows, and frontend coordination.
- `desafioMagalu` is the most contained project and the best candidate for a clean evaluation package with strong documentation and reliable local execution.
- `desafioBTG` sits in the middle: smaller than the voting platform, but with higher consistency risk because queue processing and reporting accuracy depend on correct aggregate behavior.

## Suggested Portfolio Order

1. Finish and stabilize `desafioMagalu` first when the goal is evaluator-ready completeness.
2. Push `desafioBTG` next when the goal is demonstrating event-driven reporting and frontend/backend integration.
3. Keep `votacao-backend` as the most strategic and complex portfolio piece, with stronger emphasis on architecture, contracts, and integration quality.