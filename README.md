# votacao-backend

[Português](README.md) | [English](README.en.md)

![Java](https://img.shields.io/badge/java-17-orange)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.5.x-6DB33F)
![Status](https://img.shields.io/badge/status-active-brightgreen)

Backend de votacao baseado em microsservicos com Spring Boot, Spring Cloud e Eureka.

## Sumario

- [Visao Geral](#visao-geral)
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