# Matriz Comparativa de Desafios

## Visão Consolidada

| Projeto | Resultado principal | Escopo técnico principal | Estimativa total | Foco Must Have | Principal risco |
| --- | --- | --- | --- | --- | --- |
| `votacao-backend` | fluxo fim a fim de votação | frontend Angular, gateway, discovery, 3 serviços de domínio, MongoDB e RabbitMQ | 84 a 136 pontos / 42 a 68 dias-pessoa | fluxo de associados, pautas e sessões com APIs estáveis e testes de integração essenciais | deriva entre contratos e implementação, além de inconsistência na integração assíncrona |
| `desafioMagalu` | API confiável de agendamento de comunicação | serviço Spring Boot único, MySQL, Flyway, OpenAPI e Docker Compose | 50 a 78 pontos / 25 a 39 dias-pessoa | criar, consultar, listar e remover agendamentos com persistência e documentação de API | modelo de dados não suportar bem a evolução futura do status de entrega |
| `desafioBTG` | ingestão de pedidos e consultas de relatório | frontend Angular, `ms-customer`, `ms-order`, MySQL e RabbitMQ | 66 a 102 pontos / 33 a 51 dias-pessoa | consumir pedidos, persistir agregados, expor APIs de relatório e integrar frontend | duplicidade no consumo de mensagens e desalinhamento entre backend e frontend |

## Matriz de Prioridades

| Projeto | Must Have | Should Have | Could Have | Won't Have na fase atual |
| --- | --- | --- | --- | --- |
| `votacao-backend` | setup local, CRUDs core, fluxo de votação, OpenAPI, CI e testes críticos | eventos versionados, retries, DLQ, integração frontend e logs | dashboards, clients gerados, testes de performance e automação de deploy | IAM externo, Kubernetes, DR multi-região e analytics avançado |
| `desafioMagalu` | endpoints core de agendamento, persistência em MySQL, OpenAPI, CI e testes críticos | schema preparado para evolução de status, erros padronizados e health checks | cenários extras de resiliência, exemplos mais ricos e métricas operacionais | implementação real de canais, orquestração multi-serviço e IAM externo |
| `desafioBTG` | consumo RabbitMQ, persistência em MySQL, APIs de relatório, integração frontend, CI e testes críticos | idempotência, retries, OpenAPI atualizado e troubleshooting local | dashboards, clients gerados e testes de performance | ecossistema completo de gateway/discovery neste repositório, IAM corporativo e deploy cloud avançado |

## Resumo de Riscos

| Projeto | Risco | Impacto | Mitigação |
| --- | --- | --- | --- |
| `votacao-backend` | divergência entre contrato de API e implementação entre serviços | quebra no frontend e retrabalho de integração | validar OpenAPI em CI e manter abordagem contract-first |
| `votacao-backend` | processamento assíncrono não idempotente | eventos duplicados e estado inconsistente | usar `eventId`, política de retry e DLQ |
| `desafioMagalu` | schema de agendamento estreito demais para próximos estágios de entrega | retrabalho em persistência e serviço | modelar cedo o ciclo de status e versionar migrations com Flyway |
| `desafioMagalu` | configuração local do banco variar entre ambientes | onboarding ruim e testes instáveis | padronizar `.env`, Docker Compose e documentação local |
| `desafioBTG` | consumo duplicado de fila corromper agregados | totais e quantidades incorretas | processar idempotentemente por identificador do pedido |
| `desafioBTG` | frontend e backend evoluírem com contratos diferentes | telas quebradas e retrabalho | validar contratos em CI e alinhar integração frontend/backend |

## Leitura Comparativa

- `votacao-backend` tem o maior escopo e a maior complexidade de integração porque combina múltiplos serviços, fluxos síncronos e assíncronos e coordenação com frontend.
- `desafioMagalu` é o projeto mais contido e o melhor candidato para um pacote de avaliação limpo, com forte documentação e execução local previsível.
- `desafioBTG` fica no meio: menor que a plataforma de votação, mas com risco de consistência elevado porque o processamento em fila impacta diretamente a correção dos relatórios.

## Ordem Sugerida no Portfólio

1. Finalizar e estabilizar `desafioMagalu` primeiro quando o objetivo for completude pronta para avaliação.
2. Destacar `desafioBTG` em seguida quando o objetivo for mostrar processamento orientado a eventos e integração frontend/backend.
3. Manter `votacao-backend` como peça mais estratégica e complexa do portfólio, com ênfase maior em arquitetura, contratos e qualidade de integração.