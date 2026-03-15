# ms-pautas

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.0.0-6DB33F?logo=spring&logoColor=white)
![Eureka Client](https://img.shields.io/badge/Eureka-Client-E50914?logo=netflix&logoColor=white)
![OpenFeign](https://img.shields.io/badge/OpenFeign-Client-6DB33F?logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-success)
![Codecov](https://img.shields.io/badge/Codecov-enabled-F01F7A?logo=codecov&logoColor=white)

[PT-BR](README.md) | [EN](README.en.md)

Microsservico responsavel pelo dominio de pautas no ecossistema de votacao.

## Visao geral

- Projeto: `ms-pautas`
- Porta: dinamica (`server.port=0`)
- Registry: Eureka Client
- Java: `17`
- Spring Boot: `3.5.4`
- Spring Cloud: `2025.0.0`

## Tecnologias

- Spring Boot Web
- Spring Data JPA
- Flyway
- OpenFeign
- Eureka Client
- MySQL
- Maven
- JaCoCo
- Checkstyle
- PMD
- SpotBugs

## Configuracao

Arquivo principal: `src/main/resources/application.properties`

```properties
spring.config.import=optional:file:.env
spring.application.name=ms-pautas
spring.datasource.url=${dburl-pautas}
spring.datasource.username=${dbuser}
spring.datasource.password=${dbpassword}
eureka.client.serviceUrl.defaultZone=${eurekaurl}
server.port=0
```

Variaveis esperadas em `.env`:

- `dburl-pautas`
- `dbuser`
- `dbpassword`
- `eurekaurl`

## Executar localmente

### Windows (PowerShell)

```powershell
.\mvnw spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

## Build e testes

```bash
./mvnw clean package
```

## Qualidade de codigo

```bash
./mvnw checkstyle:check
./mvnw pmd:check
./mvnw spotbugs:check
```

Observacao: no workflow atual, o `spotless:check` esta comentado.

## Cobertura de testes

```bash
./mvnw jacoco:report
```

Relatorio gerado em:

- `target/site/jacoco/index.html`

## Pipeline CI

Workflow principal no repositorio raiz:

- `.github/workflows/workflow-backend-pautas.yml`

Etapas:

- Analise de qualidade (Checkstyle, PMD, SpotBugs)
- Testes + JaCoCo
- Upload para Codecov
- Build e upload de artefato

## Licenca

Uso interno para fins de estudo/desafio.
