# One-Page Executiva - Portfólio de Desafios

## Resumo

Este portfólio reúne três projetos com escopos complementares:

- `desafioMagalu`: API de agendamento de comunicação, com foco em completude, clareza contratual e execução local previsível.
- `desafioBTG`: processamento assíncrono de pedidos e consultas de relatório, com foco em consistência de mensageria e integração frontend/backend.
- `votacao-backend`: plataforma de votação distribuída, com foco em arquitetura de microsserviços, contratos e integração entre múltiplos módulos.

## Visão Comparativa

| Projeto | Escopo | Complexidade | Esforço estimado | Principal evidência |
| --- | --- | --- | --- | --- |
| `desafioMagalu` | serviço único + MySQL + OpenAPI | baixa a média | 50 a 78 pontos | API REST coesa e documentação forte |
| `desafioBTG` | frontend + 2 serviços + RabbitMQ + MySQL | média | 66 a 102 pontos | processamento orientado a eventos com relatórios |
| `votacao-backend` | frontend + gateway + discovery + 3 serviços + MongoDB + RabbitMQ | alta | 84 a 136 pontos | arquitetura distribuída e integração ponta a ponta |

## Destaques por Projeto

### `desafioMagalu`

- melhor candidato para avaliação rápida e objetiva
- menor superfície de risco técnico
- forte aderência a API REST, persistência e documentação OpenAPI

### `desafioBTG`

- melhor demonstração de consumo RabbitMQ e agregação de dados
- combina backend e frontend no mesmo fluxo de valor
- risco principal concentrado em idempotência e correção dos relatórios

### `votacao-backend`

- peça mais estratégica e arquitetural do portfólio
- demonstra coordenação entre múltiplos serviços, gateway, discovery e eventos
- maior potencial de profundidade técnica em entrevista

## Prioridades Executivas

1. garantir Must Have completo, executável e documentado em cada projeto
2. estabilizar contratos e integrações para reduzir risco de demonstração
3. elevar observabilidade e qualidade de entrega para ambiente de avaliação

## Riscos Principais

- `desafioMagalu`: schema de agendamento não acomodar bem evolução de status
- `desafioBTG`: duplicidade de consumo impactar agregados e relatórios
- `votacao-backend`: drift entre contratos e comportamento real entre múltiplos serviços

## Ordem Recomendada de Apresentação

1. `desafioMagalu`
2. `desafioBTG`
3. `votacao-backend`

## Mensagem de Portfólio

Em conjunto, os três projetos demonstram capacidade em:

- modelagem de APIs e contratos
- persistência relacional e não relacional
- mensageria e integração assíncrona
- frontend integrado a backend
- documentação executiva e técnica
- planejamento, qualidade e readiness de entrega