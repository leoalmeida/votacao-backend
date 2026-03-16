# Challenge Comparison Matrix

## Consolidated View

| Project | Primary outcome | Main technical scope | Total estimate | Must Have focus | Main risk |
| --- | --- | --- | --- | --- | --- |
| `votacao-backend` | end-to-end voting workflow | Angular frontend, gateway, discovery, 3 domain services, MongoDB, RabbitMQ | 84 to 136 points / 42 to 68 person-days | member, agenda, and session flow with stable APIs and core integration tests | contract drift and asynchronous integration inconsistency |
| `desafioMagalu` | reliable communication scheduling API | single Spring Boot service, MySQL, Flyway, OpenAPI, Docker Compose | 50 to 78 points / 25 to 39 person-days | create, retrieve, list, and delete schedules with persistence and API documentation | data model not being ready for future delivery-status evolution |
| `desafioBTG` | order ingestion and report queries | Angular frontend, `ms-customer`, `ms-order`, MySQL, RabbitMQ | 66 to 102 points / 33 to 51 person-days | consume orders, persist aggregates, expose report APIs, and integrate frontend | duplicate message processing and backend/frontend contract mismatch |
| `desafio-fullstack` | end-to-end benefits lifecycle and transfer flow | Angular frontend, `backend-module`, `ejb-module`, local/external DB support, Docker Compose | 54 to 82 points / 27 to 41 person-days | benefits CRUD, activate/cancel operations, transfer flow with UI integration | transfer logic drift between backend and EJB layers |

## Priority Matrix

| Project | Must Have | Should Have | Could Have | Won't Have in current phase |
| --- | --- | --- | --- | --- |
| `votacao-backend` | local setup, core CRUDs, voting flow, OpenAPI, CI, critical tests | versioned events, retries, DLQ, frontend integration, logs | dashboards, generated clients, performance tests, deploy automation | external IAM, Kubernetes, multi-region DR, advanced analytics |
| `desafioMagalu` | core scheduling endpoints, MySQL persistence, OpenAPI, CI, critical tests | delivery-status-ready schema, standardized API errors, health checks | extra resilience scenarios, richer examples, operational metrics | real channel delivery implementation, multi-service orchestration, external IAM |
| `desafioBTG` | RabbitMQ consumption, MySQL persistence, report APIs, frontend integration, CI, critical tests | idempotency, retries, updated OpenAPI, local troubleshooting | dashboards, generated clients, performance tests | full gateway/discovery ecosystem in this repo, corporate IAM, advanced cloud deployment |
| `desafio-fullstack` | benefits CRUD, activate/cancel endpoints, transfer via EJB, frontend integration, CI, critical tests | stronger error standardization, expanded transfer coverage, operational documentation | UX refinements and broader automated regression | corporate IAM and Kubernetes orchestration |

## Risk Register Summary

| Project | Risk | Impact | Mitigation |
| --- | --- | --- | --- |
| `votacao-backend` | API contract and implementation diverge across services | frontend breakage and integration churn | validate OpenAPI in CI and keep contract-first workflow |
| `votacao-backend` | asynchronous processing becomes non-idempotent | duplicate events and inconsistent domain state | enforce `eventId`, retry policy, and DLQ strategy |
| `desafioMagalu` | schedule schema becomes too narrow for future delivery stages | rework in persistence and service layer | model status lifecycle early and version migrations with Flyway |
| `desafioMagalu` | local DB configuration varies across environments | onboarding friction and unstable tests | standardize `.env`, Docker Compose, and local run documentation |
| `desafioBTG` | duplicated queue consumption corrupts aggregates | wrong totals and customer counts | process idempotently by order identifier |
| `desafioBTG` | frontend and backend evolve on different contracts | broken report screens and rework | validate contracts in CI and keep frontend integration aligned to stable APIs |
| `desafio-fullstack` | transfer rules diverge between backend and EJB | inconsistent business behavior in transfer flow | keep transfer logic centralized and covered by integration tests |
| `desafio-fullstack` | frontend/backend regressions in end-to-end flow | unstable demonstration and QA overhead | validate contracts and critical UI/API scenarios continuously |

## Comparative Readout

- `votacao-backend` is the broadest scope and carries the highest integration complexity because it combines multiple services, synchronous and asynchronous flows, and frontend coordination.
- `desafioMagalu` is the most contained project and the best candidate for a clean evaluation package with strong documentation and reliable local execution.
- `desafioBTG` sits in the middle: smaller than the voting platform, but with higher consistency risk because queue processing and reporting accuracy depend on correct aggregate behavior.
- `desafio-fullstack` offers a strong product-like demonstration with moderate architecture complexity and clear backend + EJB + frontend integration value.

## Suggested Portfolio Order

1. Finish and stabilize `desafioMagalu` first when the goal is evaluator-ready completeness.
2. Showcase `desafio-fullstack` next for clear end-to-end product flow with backend + EJB + frontend.
3. Push `desafioBTG` for event-driven reporting and frontend/backend integration evidence.
4. Keep `votacao-backend` as the most strategic and complex portfolio piece, with stronger emphasis on architecture, contracts, and integration quality.