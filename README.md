# votacao-backend

[Português](README.md) | [English](README.en.md)

## Build, Testes e Cobertura

| Tipo | Status |
| --- | --- |
| Build | [![Build ms-associados](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Build ms-pautas](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Build ms-sessoes](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Build api-gateway](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Build service-discovery](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml/badge.svg?branch=main)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |
| Testes | [![Testes ms-associados](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-associados.yml?branch=main&label=testes-ms-associados)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Testes ms-pautas](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-pautas.yml?branch=main&label=testes-ms-pautas)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Testes ms-sessoes](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-sessoes.yml?branch=main&label=testes-ms-sessoes)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Testes api-gateway](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-gateway.yml?branch=main&label=testes-api-gateway)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Testes service-discovery](https://img.shields.io/github/actions/workflow/status/leoalmeida/votacao-backend/workflow-backend-discovery.yml?branch=main&label=testes-service-discovery)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |
| Cobertura (JaCoCo) | [![Cobertura ms-associados](https://img.shields.io/badge/cobertura-ms--associados%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-associados.yml) [![Cobertura ms-pautas](https://img.shields.io/badge/cobertura-ms--pautas%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-pautas.yml) [![Cobertura ms-sessoes](https://img.shields.io/badge/cobertura-ms--sessoes%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-sessoes.yml) [![Cobertura api-gateway](https://img.shields.io/badge/cobertura-api--gateway%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-gateway.yml) [![Cobertura service-discovery](https://img.shields.io/badge/cobertura-service--discovery%20JaCoCo-blue)](https://github.com/leoalmeida/votacao-backend/actions/workflows/workflow-backend-discovery.yml) |

Os relatorios de cobertura sao gerados com JaCoCo e publicados como artefatos no job de testes de cada modulo.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-6DB33F?logo=spring&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-Service%20Discovery-2C3E50)

Backend de votacao baseado em microsservicos com Spring Boot, Spring Cloud e Eureka.

## Sumario

- [Visao Geral](#visao-geral)
- [Build, Testes e Cobertura](#build-testes-e-cobertura)
- [Portas e Discovery](#portas-e-discovery)
- [Requisitos](#requisitos)
- [Como Rodar](#como-rodar)
- [Configuracao](#configuracao)
- [Testes](#testes)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)

## Visao Geral

- Java 17
- Spring Boot 3.5.x
- Spring Cloud 2025.0.0
- Maven multi-modulo

Modulos no agregador (`pom.xml` raiz):

- `service-discovery` (Eureka Server)
- `api-gateway` (Gateway de entrada)
- `ms-associados`
- `ms-pautas`
- `ms-sessoes`

## Portas e Discovery

- `service-discovery`: `http://localhost:8761`
- `api-gateway`: `http://localhost:8082`
- `ms-associados`, `ms-pautas`, `ms-sessoes`: `server.port=0` (porta dinamica, registrada no Eureka)

## Requisitos

- JDK 17
- Maven 3.9+ (ou `mvnw`)

## Como Rodar

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