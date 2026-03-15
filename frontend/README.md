# votacao-app

[Português](README.md) | [English](README.en.md)

![Angular](https://img.shields.io/badge/angular-21-red)
![Node](https://img.shields.io/badge/node-20%2B-green)
![Status](https://img.shields.io/badge/status-active-brightgreen)

Aplicacao frontend Angular para o fluxo de votacao, integrada ao `votacao-backend` via API Gateway.

## Sumario

- [Visao Geral](#visao-geral)
- [Requisitos](#requisitos)
- [Instalacao](#instalacao)
- [Execucao](#execucao)
- [Testes e Qualidade](#testes-e-qualidade)
- [Docker](#docker)
- [Estrutura](#estrutura)
- [Troubleshooting](#troubleshooting)

## Visao Geral

- Stack: Angular 21, TypeScript, Angular Material
- Porta local: `3001`
- Backend esperado: `http://localhost:8082`

As URLs das APIs estao centralizadas em `src/environments/environment.ts`:

- `ms-sessoes`: `http://localhost:8082/ms-sessoes/v1/sessoes`
- `ms-associados`: `http://localhost:8082/ms-associados/v1/associados`
- `ms-pautas`: `http://localhost:8082/ms-pautas/v1/pautas`
- `votos`: `http://localhost:8082/ms-sessoes/v1/votos`

## Requisitos

- Node.js 20+
- npm 10+

## Instalacao

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-app"
npm install
```

## Execucao

Subir em desenvolvimento:

```powershell
npm start
```

Build de producao:

```powershell
npm run build
```

## Testes e Qualidade

Executar testes unitarios:

```powershell
npm test -- --watch=false
```

Executar lint:

```powershell
npm run lint
```

Formatar codigo:

```powershell
npm run prettier
```

## Docker

Build e execucao com Compose:

```powershell
docker compose up --build
```

## Estrutura

```text
votacao-app/
  src/
    app/
    environments/
    mocks/
  public/
  deployment/
  Dockerfile
  docker-compose.yaml
```

## Troubleshooting

- Erro de CORS/404 nas chamadas: valide se `votacao-backend` esta ativo e o gateway em `localhost:8082`.
- Porta ocupada no frontend: altere a porta no script `start` do `package.json`.
