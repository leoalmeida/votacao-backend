# votacao-backend

[Português](README.md) | [English](README.en.md)

Backend de votação baseado em microsserviços com Spring Boot, Spring Cloud, Eureka, MongoDB, RabbitMQ e frontend Angular.

## Resumo Executivo

Este repositório concentra a solução de votação distribuída, organizada em serviços independentes para associados, pautas e sessões, com descoberta de serviços e gateway de entrada.

Objetivos do projeto:

- separar responsabilidades de domínio por microsserviço
- permitir evolução incremental com contratos claros de API
- suportar integração síncrona e assíncrona
- manter qualidade contínua com testes, cobertura e pipeline automatizado

Escopo principal:

- `ms-associados`: gestão de associados
- `ms-pautas`: gestão de pautas
- `ms-sessoes`: abertura, votação, encerramento e apuração de sessões
- `api-gateway`: ponto de entrada para clientes
- `service-discovery`: registro e descoberta de serviços
- `frontend`: interface Angular para operação do fluxo

Arquitetura resumida:

Frontend
	|
API Gateway
	|
Microsserviços de domínio
	|
RabbitMQ + MongoDB

## Stack

- Java 17
- Spring Boot 3.5.x
- Spring Cloud 2025.0.0
- Maven multi-modulo
- Angular
- Docker Compose
- GitHub Actions + Codecov

## Status do Projeto

- arquitetura baseada em microsserviços estabelecida
- pipelines de build, testes e cobertura publicados por módulo
- documentação técnica disponível por módulo e em `docs/architecture`
- roadmap detalhado em [plano.md](plano.md)

## Build, Testes e Cobertura

| Tipo | Status |
| --- | --- |
| Build | [![Build ms-associados](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Build ms-pautas](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Build ms-sessoes](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) |
| Testes | [![Testes ms-associados](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-associados.yml?branch=main&label=testes-ms-associados)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Testes ms-pautas](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-pautas.yml?branch=main&label=testes-ms-pautas)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Testes ms-sessoes](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-sessoes.yml?branch=main&label=testes-ms-sessoes)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) |
| Cobertura | [![Codecov ms-associados](https://codecov.io/gh/leoalmeida/votacao-backend/branch/main/graph/badge.svg?flag=ms-associados)](https://codecov.io/gh/leoalmeida/votacao-backend) [![Codecov ms-pautas](https://codecov.io/gh/leoalmeida/votacao-backend/branch/main/graph/badge.svg?flag=ms-pautas)](https://codecov.io/gh/leoalmeida/votacao-backend) [![Codecov ms-sessoes](https://codecov.io/gh/leoalmeida/votacao-backend/branch/main/graph/badge.svg?flag=ms-sessoes)](https://codecov.io/gh/leoalmeida/votacao-backend) [![Codecov frontend](https://codecov.io/gh/leoalmeida/votacao-backend/branch/main/graph/badge.svg?flag=frontend)](https://codecov.io/gh/leoalmeida/votacao-backend) [![JaCoCo](https://img.shields.io/badge/cobertura-JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow.yml)|

Os relatorios de cobertura sao gerados com JaCoCo e publicados como artefatos no job de testes de cada modulo.

## Sumario

- [Resumo Executivo](#resumo-executivo)
- [Stack](#stack)
- [Status do Projeto](#status-do-projeto)
- [Documentacao dos Modulos](#documentacao-dos-modulos)
- [Build, Testes e Cobertura](#build-testes-e-cobertura)
- [Portas e Discovery](#portas-e-discovery)
- [Requisitos](#requisitos)
- [Modulos do Agregador](#modulos-do-agregador)
- [Como Rodar Rapido](#como-rodar-rapido)
- [Configuracao](#configuracao)
- [Testes](#testes)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [Referencias](#referencias)

## Modulos do Agregador

Modulos no agregador (`pom.xml` raiz):

- `service-discovery` (Eureka Server)
- `api-gateway` (Gateway de entrada)
- `ms-associados`
- `ms-pautas`
- `ms-sessoes`

## Documentacao dos Modulos

| Modulo | README PT-BR | README EN |
| --- | --- | --- |
| frontend | [README.md](frontend/README.md) | - |
| ms-associados | [README.md](ms-associados/README.md) | [README.en.md](ms-associados/README.en.md) |
| ms-pautas | [README.md](ms-pautas/README.md) | [README.en.md](ms-pautas/README.en.md) |
| ms-sessoes | - | - |

Modulos em repositorios dedicados:

- `api-gateway` (com README PT/EN)
- `service-discovery` (com README PT/EN)

## Portas e Discovery

- `service-discovery`: `http://localhost:8761`
- `api-gateway`: `http://localhost:8082`
- `ms-associados`, `ms-pautas`, `ms-sessoes`: `server.port=0` (porta dinamica, registrada no Eureka)

## Requisitos

- JDK 17
- Maven 3.9+ (ou `mvnw`)

## Como Rodar Rapido

### Subida local essencial

1. executar o build na raiz
2. subir `service-discovery`
3. subir `api-gateway`
4. subir `ms-associados`, `ms-pautas` e `ms-sessoes`
5. iniciar o frontend, se aplicavel

### 1. Build completo

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend"
.\mvnw.cmd clean install
```

### 2. Subir servicos (ordem recomendada)

1. `service-discovery`
2. `api-gateway`
3. `ms-associados`
4. `ms-pautas`
5. `ms-sessoes`

Exemplo (um modulo por terminal):

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend\service-discovery"
..\mvnw.cmd spring-boot:run
```

Repita para os demais modulos trocando a pasta.

### Resultado esperado

- Eureka disponivel em `http://localhost:8761`
- Gateway disponivel em `http://localhost:8082`
- servicos de dominio registrados automaticamente no Eureka

## Configuracao

Os microsservicos de dominio usam import opcional de `.env` (`spring.config.import=optional:file:.env`) e esperam propriedades como:

- `dburl-associados`, `dburl-pautas`, `dburl-sessoes`
- `dbuser`, `dbpassword`
- `eurekaurl`

## Testes

Rodar testes de todos os modulos:

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend"
.\mvnw.cmd test
```

Rodar testes de um modulo especifico:

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-backend\ms-sessoes"
..\mvnw.cmd test
```

## Docker

Existe `docker-compose.yaml` no projeto para ambiente com app + MongoDB.

```powershell
docker compose up --build
```

Se usar variaveis por `.env`, ajuste os valores antes da subida.

## Troubleshooting

- Servicos nao aparecem no Eureka: confirme `eurekaurl` e se o discovery iniciou antes.
- Erro de conexao com banco: valide as propriedades `dburl-*`, usuario e senha.
- Erro de porta em `8082`/`8761`: altere `application.properties` dos modulos fixos.

## Referencias

- roadmap e planejamento: [plano.md](plano.md)
- backlog executivo do portfólio: [docs/portfolio/backlog-executivo.md](docs/portfolio/backlog-executivo.md)
- matriz comparativa dos desafios: [docs/portfolio/matriz-comparativa.md](docs/portfolio/matriz-comparativa.md)
- diagramas: [docs/architecture](docs/architecture)
- frontend: [frontend/README.md](frontend/README.md)
- ms-associados: [ms-associados/README.md](ms-associados/README.md)
- ms-pautas: [ms-pautas/README.md](ms-pautas/README.md)
