# Plano de Trabalho - votacao-backend

## 1. Objetivo

Entregar e evoluir o ecossistema de votação com arquitetura de microsserviços, garantindo:

- consistência funcional entre `ms-associados`, `ms-pautas` e `ms-sessoes`
- integração via API Gateway e Service Discovery
- qualidade contínua por testes automatizados e pipelines
- documentação técnica e de API sempre atualizada

## 2. Escopo Atual do Repositório

Módulos principais:

- `frontend`
- `ms-associados`
- `ms-pautas`
- `ms-sessoes`
- `service-discovery` (quando presente no workspace local)
- `api-gateway` (quando presente no workspace local)

Tecnologias base:

- Java 17
- Spring Boot 3.5.x
- Spring Cloud 2025.0.0
- Eureka (Service Discovery)
- MongoDB
- RabbitMQ
- Angular (frontend)
- Docker + Docker Compose
- GitHub Actions + Codecov

## 3. Arquitetura e Decisões

Fluxo macro:

Frontend
   |
API Gateway
   |
Microsserviços de domínio
   |-- ms-associados
   |-- ms-pautas
   |-- ms-sessoes
   |
RabbitMQ (eventos)
   |
MongoDB

Documentos de apoio:

- [Context Diagram](docs/architecture/context-diagram.puml)
- [Container Diagram](docs/architecture/container-diagram.puml)
- [Component Diagram](docs/architecture/component-diagram-backend.puml)

Decisões obrigatórias para novas features:

- contrato OpenAPI primeiro
- modelo de evento versionado (`eventType`, `eventVersion`, `occurredAt`, `payload`)
- comunicação síncrona apenas quando necessária
- idempotência em consumidores de eventos

## 4. Estratégia de Desenvolvimento

Modelo de execução: vertical slices por capacidade de negócio.

Cada slice deve incluir:

1. modelagem de domínio
2. persistência (`repository`)
3. regra de negócio (`service`)
4. API (`controller` + DTOs + validações)
5. documentação OpenAPI
6. testes unitários
7. testes de integração
8. atualização frontend (quando aplicável)

Estrutura padrão de cada microsserviço:

- `controller`
- `service`
- `repository`
- `domain`
- `dto`
- `config`
- `messaging`

## 5. Fases de Entrega

### Fase 1 - Foundation

Objetivo: estabilizar ambiente e padrões.

Entregáveis:

- docker-compose funcional para dependências e aplicação
- configuração de variáveis (`dburl-*`, `dbuser`, `dbpassword`, `eurekaurl`)
- checkstyle, cobertura e padrões de build validados
- documentação de execução local revisada

Critérios de aceite:

- build completo com `mvnw clean install`
- todos os módulos sobem localmente sem erro de descoberta

### Fase 2 - Domínio de Votação

Objetivo: consolidar casos de uso core.

Entregáveis mínimos:

- `ms-associados`: gestão de associados
- `ms-pautas`: gestão de pautas
- `ms-sessoes`: abertura, fechamento e contabilização de sessões
- regras de negócio transversais documentadas

Critérios de aceite:

- fluxos ponta a ponta cobrindo criar pauta -> abrir sessão -> votar -> encerrar sessão
- validações de negócio com testes automatizados

### Fase 3 - Mensageria e Consistência

Objetivo: integrar eventos entre módulos.

Eventos iniciais sugeridos:

- `pauta.criada.v1`
- `sessao.aberta.v1`
- `voto.registrado.v1`
- `sessao.encerrada.v1`

Entregáveis:

- publisher e consumer por contexto
- tratamento de duplicidade (idempotência por `eventId`)
- estratégia de retry e dead-letter queue

Critérios de aceite:

- cenário de falha com reprocessamento testado
- consumo idempotente validado em integração

### Fase 4 - Qualidade, Observabilidade e Hardening

Objetivo: elevar robustez para ambientes superiores.

Entregáveis:

- logs estruturados e correlação por `traceId`
- métricas de aplicação e saúde dos serviços
- documentação operacional de troubleshooting
- revisão de segurança de APIs e mensagens

Critérios de aceite:

- indicadores de saúde disponíveis
- cobertura mínima de testes acordada por módulo

## 6. Estratégia de Testes

### 6.1 Unitários

Ferramentas:

- JUnit 5
- Mockito

Cobertura mínima por slice:

- regras de negócio
- cenários de erro
- mapeamentos críticos

### 6.2 Integração

Ferramentas:

- Spring Boot Test
- Testcontainers (MongoDB e RabbitMQ)

Escopo:

- integração repositório + banco
- integração producer/consumer de eventos
- integração entre camadas da API

### 6.3 Contrato de API

Ferramentas sugeridas:

- validação OpenAPI no pipeline
- testes de contrato com Rest Assured (ou equivalente já adotado no módulo)

Objetivos:

- garantir aderência ao contrato OpenAPI
- prevenir quebra de compatibilidade entre frontend e backend

## 7. CI/CD e Releases

Pipeline mínimo por módulo:

1. build
2. testes unitários
3. testes de integração
4. cobertura e publicação de relatório
5. build de imagem Docker

Ambientes-alvo:

- local
- dev
- staging
- prod

Política de merge:

- pipeline verde obrigatório
- cobertura mínima respeitada
- revisão de código aprovada

## 8. Roadmap Sugerido (4 Sprints)

Premissa de conversao para planejamento inicial:

- 2 pontos = 1 dia-pessoa
- a conversao serve para previsao macro, nao para compromisso fechado

### Sprint 1

Estimativa: 24 a 32 pontos

Equivalente: 12 a 16 dias-pessoa

- foundation: revisar `docker-compose`, variaveis de ambiente e boot local dos modulos
- foundation: padronizar build Maven, cobertura, checkstyle e execucao de testes
- `api-gateway` e `service-discovery`: validar discovery, roteamento e configuracao base
- `ms-associados`: revisar estrutura de pacote, DTOs e contrato inicial
- `ms-pautas`: revisar estrutura de pacote, DTOs e contrato inicial
- `ms-sessoes`: revisar estrutura de pacote, DTOs e contrato inicial
- frontend: validar integracao base, ambientes e estrategia de consumo das APIs
- docs: consolidar contratos iniciais e atualizar guias de execucao local

### Sprint 2

Estimativa: 28 a 36 pontos

Equivalente: 14 a 18 dias-pessoa

- `ms-associados`: concluir CRUD principal e validacoes de entrada
- `ms-pautas`: concluir CRUD principal e regras de negocio da pauta
- `ms-sessoes`: implementar abertura e encerramento de sessao
- `ms-sessoes`: implementar registro de voto e apuracao basica
- frontend: integrar telas principais de associados, pautas e sessoes
- testes: cobrir regras centrais de votacao com testes unitarios e integracao
- docs: publicar OpenAPI atualizado para APIs estabilizadas

### Sprint 3

Estimativa: 24 a 32 pontos

Equivalente: 12 a 16 dias-pessoa

- mensageria: definir eventos versionados por contexto de dominio
- `ms-pautas`: publicar eventos relevantes de pauta
- `ms-sessoes`: publicar eventos de abertura, voto e encerramento
- consumidores: implementar idempotencia por `eventId`, retry e DLQ
- testes: validar cenarios com MongoDB e RabbitMQ via Testcontainers
- frontend: ajustar comportamentos dependentes de processamento assincrono
- docs: registrar contratos de eventos e fluxos de integracao

### Sprint 4

Estimativa: 20 a 28 pontos

Equivalente: 10 a 14 dias-pessoa

- observabilidade: logs estruturados, health checks e metricas por servico
- hardening: revisar tratamento de erro, timeouts, validacoes e configuracoes sensiveis
- performance: revisar consultas, payloads e pontos de gargalo do fluxo de votacao
- frontend: acabamento de UX, mensagens de erro e estados de carregamento
- release: validar imagens Docker, pipeline e prontidao para ambiente superior
- docs: consolidar troubleshooting, operacao e checklist de release

## 9. Priorização MoSCoW

### Must Have

- setup local estável com Docker Compose, MongoDB, RabbitMQ, API Gateway e Service Discovery
- CRUD principal de associados, pautas e sessões
- fluxo fim a fim de votação com regras de negócio validadas
- documentação OpenAPI atualizada para APIs expostas
- testes unitários e de integração para casos críticos
- pipeline de CI com build, testes e cobertura

### Should Have

- publicação e consumo de eventos versionados
- idempotência, retry e dead-letter queue nos consumidores
- integração do frontend com contratos estáveis
- logs estruturados e health checks padronizados

### Could Have

- métricas avançadas e dashboards operacionais
- geração automatizada de client frontend a partir de OpenAPI
- testes de performance para fluxos críticos
- automação de deploy para ambientes superiores

### Won't Have (nesta fase)

- autenticação/autorização completa com IAM externo
- orquestração em Kubernetes
- multi-região ou estratégias avançadas de disaster recovery
- analytics avançado e relatórios gerenciais

## 10. Estimativa por Frente

Referência inicial para planejamento macro:

- foundation e setup de ambiente: 16 a 24 pontos = 8 a 12 dias-pessoa
- domínio `ms-associados`: 10 a 16 pontos = 5 a 8 dias-pessoa
- domínio `ms-pautas`: 10 a 16 pontos = 5 a 8 dias-pessoa
- domínio `ms-sessoes`: 16 a 24 pontos = 8 a 12 dias-pessoa
- mensageria e consistência: 12 a 20 pontos = 6 a 10 dias-pessoa
- frontend e integração: 12 a 20 pontos = 6 a 10 dias-pessoa
- observabilidade e hardening: 8 a 16 pontos = 4 a 8 dias-pessoa

Observações:

- as estimativas consideram complexidade técnica moderada e equipe já familiarizada com Spring Boot e Angular
- a reestimativa deve acontecer ao final de cada sprint com base em throughput real
- se a equipe trabalhar com outra cadencia, basta recalibrar a regra de conversao sem alterar a priorizacao

## 11. Definition of Done

Antes de cada merge:

- testes unitários passando
- testes de integração passando
- contrato OpenAPI atualizado e válido
- cobertura dentro do mínimo acordado
- build Docker funcionando
- pipeline CI verde
- documentação impactada atualizada

## 12. Riscos e Mitigações

Riscos principais:

- divergência entre contrato e implementação
- falhas intermitentes de integração assíncrona
- inconsistências de configuração por ambiente

Mitigações:

- validação de contrato em CI
- testes de integração com infraestrutura real via containers
- checklist de configuração por módulo e ambiente