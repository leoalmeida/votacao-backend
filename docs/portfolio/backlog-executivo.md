# Backlog Executivo - Portfólio de Desafios

## Objetivo

Disponibilizar um formato único de backlog executivo para comparar os projetos do workspace:

- `votacao-backend`
- `desafioMagalu`
- `desafioBTG`
- `desafio-fullstack`

Premissa de planejamento usada em todos os projetos:

- 2 pontos = 1 dia-pessoa
- as estimativas são direcionais e devem ser recalibradas ao fim de cada sprint

## Frentes Unificadas de Entrega

### 1. Foundation e Setup Local

Objetivo comum:

- estabilizar build, ambiente local, configuração e onboarding técnico

Itens compartilhados:

- validar Docker Compose e a infraestrutura necessária
- padronizar build, testes, cobertura e execução em CI
- revisar variáveis de ambiente e guias de subida local
- confirmar baseline arquitetural e convenções do projeto

Foco por projeto:

| Projeto | Itens principais | Estimativa |
| --- | --- | --- |
| `votacao-backend` | gateway, discovery, serviços de domínio, MongoDB, RabbitMQ e baseline de integração com frontend | 16 a 24 pontos |
| `desafioMagalu` | `ms-agendamento`, MySQL, Flyway, Docker Compose e baseline de execução local | 10 a 16 pontos |
| `desafioBTG` | frontend, `ms-customer`, `ms-order`, MySQL, RabbitMQ e build raiz | 12 a 18 pontos |
| `desafio-fullstack` | `frontend`, `backend-module`, `ejb-module`, banco local e compose integrado | 10 a 16 pontos |

### 2. Entrega do Domínio Core

Objetivo comum:

- implementar as capacidades centrais do desafio com regras de negócio estáveis

Itens compartilhados:

- finalizar modelos de domínio e fluxos de persistência
- implementar APIs principais e validações
- documentar regras críticas e resultados esperados
- cobrir o fluxo de negócio principal com testes unitários

Foco por projeto:

| Projeto | Itens principais | Estimativa |
| --- | --- | --- |
| `votacao-backend` | associados, pautas, sessões, registro de voto, encerramento e apuração | 36 a 56 pontos |
| `desafioMagalu` | criar, consultar, listar e remover agendamentos de comunicação | 16 a 24 pontos |
| `desafioBTG` | consumo de pedidos via RabbitMQ, persistência, agregados por cliente e APIs de relatório | 24 a 38 pontos |
| `desafio-fullstack` | CRUD de benefícios, ativar/cancelar e transferência via EJB | 20 a 30 pontos |

### 3. Contratos, Integrações e Consistência

Objetivo comum:

- estabilizar contratos entre serviços, infraestrutura e clientes

Itens compartilhados:

- publicar ou validar contratos OpenAPI
- verificar fluxos de integração com infraestrutura real via containers
- reforçar tratamento consistente de erros e proteção contra regressão de contrato
- documentar payloads, eventos e fronteiras de integração

Foco por projeto:

| Projeto | Itens principais | Estimativa |
| --- | --- | --- |
| `votacao-backend` | contratos de API, eventos RabbitMQ, consumidores idempotentes e estabilidade do frontend | 24 a 40 pontos |
| `desafioMagalu` | estabilização OpenAPI, integração com MySQL e regressão de contrato | 8 a 12 pontos |
| `desafioBTG` | contratos de relatório, alinhamento frontend/backend, idempotência de mensagens, retry e tratamento de falhas | 22 a 34 pontos |
| `desafio-fullstack` | contrato OpenAPI, integração frontend/backend e robustez da transferência EJB | 16 a 24 pontos |

### 4. Qualidade, Observabilidade e Readiness de Release

Objetivo comum:

- tornar cada projeto demonstrável, sustentável e seguro para evoluir

Itens compartilhados:

- melhorar logs, health checks e troubleshooting
- validar metas de cobertura e checklists de release
- revisar gargalos de performance e riscos de configuração
- consolidar documentação operacional e orientada ao avaliador

Foco por projeto:

| Projeto | Itens principais | Estimativa |
| --- | --- | --- |
| `votacao-backend` | observabilidade, hardening, validação Docker e prontidão de release | 8 a 16 pontos |
| `desafioMagalu` | health checks, tratamento de erros, validação Docker e prontidão para avaliação | 6 a 10 pontos |
| `desafioBTG` | logs, performance de consultas, validação Docker e prontidão do relatório técnico | 8 a 12 pontos |
| `desafio-fullstack` | qualidade estática, troubleshooting e readiness de demonstração | 8 a 12 pontos |

## Mapeamento Sugerido por Sprint

| Sprint | `votacao-backend` | `desafioMagalu` | `desafioBTG` | `desafio-fullstack` |
| --- | --- | --- | --- | --- |
| Sprint 1 | foundation, contratos e baseline de inicialização | foundation, migrations e baseline de API | foundation, baseline frontend/backend e revisão do contrato de mensagem | foundation, build multi-modulo e baseline de API |
| Sprint 2 | CRUDs core e fluxo de votação | endpoints core de agendamento | ingestão core e APIs de relatório | CRUD de benefícios e operações de estado |
| Sprint 3 | eventos, consistência de integração e idempotência | endurecimento de contrato e estabilidade de integração | idempotência, retries e integração dos relatórios no frontend | integração frontend/backend e robustez da transferência |
| Sprint 4 | observabilidade e prontidão de release | observabilidade e prontidão para avaliação | observabilidade, hardening e relatório de entrega | qualidade, observabilidade e readiness de entrega |

## Prioridades Executivas

### Prioridade 1

- concluir o escopo Must Have de cada desafio
- garantir build, testes e documentação prontos para avaliação

### Prioridade 2

- estabilizar contratos e comportamento de integração
- reduzir risco oculto em infraestrutura, filas e persistência

### Prioridade 3

- melhorar observabilidade, experiência de desenvolvimento e automações opcionais

## Esforço Total Estimado

| Projeto | Total em pontos | Total em dias-pessoa |
| --- | --- | --- |
| `votacao-backend` | 84 a 136 | 42 a 68 |
| `desafioMagalu` | 50 a 78 | 25 a 39 |
| `desafioBTG` | 66 a 102 | 33 a 51 |
| `desafio-fullstack` | 54 a 82 | 27 a 41 |