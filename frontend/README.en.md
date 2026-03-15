# votacao-app

[Português](README.md) | [English](README.en.md)

![Angular](https://img.shields.io/badge/angular-21-red)
![Node](https://img.shields.io/badge/node-20%2B-green)
![Status](https://img.shields.io/badge/status-active-brightgreen)

Angular frontend application for the voting flow, integrated with `votacao-backend` through the API Gateway.

## Table of Contents

- [Overview](#overview)
- [Requirements](#requirements)
- [Installation](#installation)
- [Running](#running)
- [Tests and Quality](#tests-and-quality)
- [Docker](#docker)
- [Structure](#structure)
- [Troubleshooting](#troubleshooting)

## Overview

- Stack: Angular 21, TypeScript, Angular Material
- Local port: `3001`
- Expected backend: `http://localhost:8082`

API URLs are centralized in `src/environments/environment.ts`:

- `ms-sessoes`: `http://localhost:8082/ms-sessoes/v1/sessoes`
- `ms-associados`: `http://localhost:8082/ms-associados/v1/associados`
- `ms-pautas`: `http://localhost:8082/ms-pautas/v1/pautas`
- `votos`: `http://localhost:8082/ms-sessoes/v1/votos`

## Requirements

- Node.js 20+
- npm 10+

## Installation

```powershell
Set-Location "c:\Users\leo_a\projetos\votacao-app"
npm install
```

## Running

Start in development mode:

```powershell
npm start
```

Production build:

```powershell
npm run build
```

## Tests and Quality

Run unit tests:

```powershell
npm test -- --watch=false
```

Run lint:

```powershell
npm run lint
```

Format code:

```powershell
npm run prettier
```

## Docker

Build and run with Compose:

```powershell
docker compose up --build
```

## Structure

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

- CORS/404 errors on API calls: confirm `votacao-backend` is running and gateway is reachable at `localhost:8082`.
- Frontend port already in use: change the port in the `start` script in `package.json`.
