# Backlog Operacional - Jira / GitHub Projects

## Objetivo

Traduzir os planos dos três projetos em um backlog operacional com épicos, histórias e tasks curtas, pronto para cadastro em Jira ou GitHub Projects.

Padrão de IDs:

- épico: `PROJ-EP-XXX`
- história: `PROJ-SVC-XXX`
- task: `PROJ-TSK-XXX`

Campos recomendados na ferramenta:

- `Tipo`: Epic, Story, Task
- `Título`
- `Descrição`
- `Critérios de aceite`
- `Labels`: projeto, serviço, tipo, prioridade
- `Sprint`
- `Responsável`

## `votacao-backend`

### Épicos

| Tipo | ID | Título | Sprint sugerida | Labels |
| --- | --- | --- | --- | --- |
| Epic | `VTB-EP-001` | Foundation e ambiente local | Sprint 1 | `votacao`, `foundation`, `devops` |
| Epic | `VTB-EP-002` | Fluxo core de votação | Sprint 2 | `votacao`, `backend`, `must-have` |
| Epic | `VTB-EP-003` | Eventos e consistência | Sprint 3 | `votacao`, `mensageria`, `integracao` |
| Epic | `VTB-EP-004` | Observabilidade e release readiness | Sprint 4 | `votacao`, `observabilidade`, `release` |

### Histórias e Tasks

#### `VTB-DSC-001` - Discovery local previsível

- Parent: `VTB-EP-001`
- Critérios de aceite:
  - serviços registram no discovery em ambiente local
  - ordem de subida e configuração mínima documentadas

Tasks:

- `VTB-TSK-001` Revisar configuração do discovery
- `VTB-TSK-002` Validar registro de serviços em ambiente local
- `VTB-TSK-003` Atualizar documentação operacional

#### `VTB-GTW-001` - Gateway único para APIs de domínio

- Parent: `VTB-EP-001`
- Critérios de aceite:
  - rotas de associados, pautas e sessões funcionam pelo gateway
  - erros de roteamento retornam resposta consistente

Tasks:

- `VTB-TSK-004` Revisar rotas do gateway
- `VTB-TSK-005` Validar tratamento de erro e indisponibilidade
- `VTB-TSK-006` Documentar configuração por ambiente

#### `VTB-ASS-001` - CRUD principal de associados

- Parent: `VTB-EP-002`
- Critérios de aceite:
  - criar, consultar, atualizar e listar associados conforme contrato
  - validações impedem dados inconsistentes

Tasks:

- `VTB-TSK-007` Revisar DTOs e validações de associados
- `VTB-TSK-008` Implementar casos de uso faltantes do CRUD
- `VTB-TSK-009` Cobrir cenários críticos com testes

#### `VTB-PAU-001` - CRUD principal de pautas

- Parent: `VTB-EP-002`
- Critérios de aceite:
  - criar, consultar e listar pautas com OpenAPI atualizado
  - regras de negócio e persistência cobertas por testes

Tasks:

- `VTB-TSK-010` Revisar contrato e payloads de pautas
- `VTB-TSK-011` Implementar regras de negócio da pauta
- `VTB-TSK-012` Cobrir persistência e API com testes

#### `VTB-SES-001` - Abrir e encerrar sessões

- Parent: `VTB-EP-002`
- Critérios de aceite:
  - sessões respeitam regras de estado e tempo
  - tentativas inválidas retornam erro consistente

Tasks:

- `VTB-TSK-013` Implementar ciclo de vida da sessão
- `VTB-TSK-014` Validar regras temporais e de estado
- `VTB-TSK-015` Adicionar testes unitários e de integração

#### `VTB-SES-002` - Registro e apuração de votos

- Parent: `VTB-EP-002`
- Critérios de aceite:
  - votos válidos são persistidos e apurados corretamente
  - duplicidades não permitidas são rejeitadas

Tasks:

- `VTB-TSK-016` Implementar registro de voto
- `VTB-TSK-017` Implementar apuração final
- `VTB-TSK-018` Testar cenários de sucesso e erro

#### `VTB-PAU-002` - Eventos de pauta versionados

- Parent: `VTB-EP-003`
- Critérios de aceite:
  - eventos seguem contrato versionado
  - publicação testada em integração

Tasks:

- `VTB-TSK-019` Definir contrato de evento de pauta
- `VTB-TSK-020` Implementar publicação
- `VTB-TSK-021` Validar cenário de integração

#### `VTB-SES-003` - Eventos de sessão e voto

- Parent: `VTB-EP-003`
- Critérios de aceite:
  - eventos possuem `eventId` e versionamento
  - fluxo assíncrono testado com RabbitMQ

Tasks:

- `VTB-TSK-022` Definir contratos de evento de sessão e voto
- `VTB-TSK-023` Implementar publicação e consumo associado
- `VTB-TSK-024` Validar idempotência, retry e DLQ

#### `VTB-FE-001` - Fluxo web de votação

- Parent: `VTB-EP-002`
- Critérios de aceite:
  - telas core funcionam com contratos estáveis
  - estados de carregamento, erro e sucesso são coerentes

Tasks:

- `VTB-TSK-025` Integrar telas de associados
- `VTB-TSK-026` Integrar telas de pautas e sessões
- `VTB-TSK-027` Ajustar UX de erro e carregamento

## `desafioMagalu`

### Épicos

| Tipo | ID | Título | Sprint sugerida | Labels |
| --- | --- | --- | --- | --- |
| Epic | `MAG-EP-001` | Foundation e persistência | Sprint 1 | `magalu`, `foundation`, `mysql` |
| Epic | `MAG-EP-002` | API core de agendamento | Sprint 2 | `magalu`, `backend`, `must-have` |
| Epic | `MAG-EP-003` | Contrato e robustez | Sprint 3 | `magalu`, `api`, `qualidade` |
| Epic | `MAG-EP-004` | Observabilidade e entrega | Sprint 4 | `magalu`, `release`, `observabilidade` |

### Histórias e Tasks

#### `MAG-AGD-001` - Criar agendamento

- Parent: `MAG-EP-002`
- Critérios de aceite:
  - endpoint aceita campos obrigatórios e persiste registro
  - payload inválido retorna erro consistente

Tasks:

- `MAG-TSK-001` Revisar DTO de criação
- `MAG-TSK-002` Implementar persistência do agendamento
- `MAG-TSK-003` Cobrir validações com testes

#### `MAG-AGD-002` - Consultar agendamento por ID

- Parent: `MAG-EP-002`
- Critérios de aceite:
  - consulta retorna agendamento existente
  - identificador inexistente retorna erro apropriado

Tasks:

- `MAG-TSK-004` Implementar busca por identificador
- `MAG-TSK-005` Padronizar erro de não encontrado
- `MAG-TSK-006` Adicionar testes de sucesso e falha

#### `MAG-AGD-003` - Listar agendamentos

- Parent: `MAG-EP-002`
- Critérios de aceite:
  - listagem retorna registros persistidos
  - contrato de resposta está documentado

Tasks:

- `MAG-TSK-007` Implementar endpoint de listagem
- `MAG-TSK-008` Atualizar documentação OpenAPI

#### `MAG-AGD-004` - Remover agendamento

- Parent: `MAG-EP-002`
- Critérios de aceite:
  - remoção segue a estratégia documentada
  - comportamento para ID inexistente é previsível

Tasks:

- `MAG-TSK-009` Implementar exclusão
- `MAG-TSK-010` Definir resposta para inexistente
- `MAG-TSK-011` Cobrir remoção com testes

#### `MAG-AGD-005` - Preparar domínio para evolução de status

- Parent: `MAG-EP-003`
- Critérios de aceite:
  - schema suporta evolução do status de entrega
  - migrations e documentação refletem essa intenção

Tasks:

- `MAG-TSK-012` Revisar modelo de dados e estados
- `MAG-TSK-013` Ajustar migrations Flyway
- `MAG-TSK-014` Documentar extensibilidade do schema

## `desafioBTG`

### Épicos

| Tipo | ID | Título | Sprint sugerida | Labels |
| --- | --- | --- | --- | --- |
| Epic | `BTG-EP-001` | Foundation e infraestrutura local | Sprint 1 | `btg`, `foundation`, `docker` |
| Epic | `BTG-EP-002` | Ingestão e relatórios core | Sprint 2 | `btg`, `backend`, `must-have` |
| Epic | `BTG-EP-003` | Consistência e integração | Sprint 3 | `btg`, `mensageria`, `frontend` |
| Epic | `BTG-EP-004` | Observabilidade e relatório técnico | Sprint 4 | `btg`, `release`, `observabilidade` |

### Histórias e Tasks

#### `BTG-ORD-001` - Consumir e persistir pedidos

- Parent: `BTG-EP-002`
- Critérios de aceite:
  - mensagem é validada antes da persistência
  - pedido e itens ficam persistidos com os campos necessários

Tasks:

- `BTG-TSK-001` Revisar contrato de mensagem
- `BTG-TSK-002` Implementar persistência de pedido e itens
- `BTG-TSK-003` Cobrir consumo com testes de integração

#### `BTG-ORD-002` - Consultar valor total do pedido

- Parent: `BTG-EP-002`
- Critérios de aceite:
  - API retorna total correto a partir dos itens persistidos
  - pedido inexistente retorna resposta documentada

Tasks:

- `BTG-TSK-004` Implementar cálculo do total
- `BTG-TSK-005` Expor endpoint e atualizar OpenAPI
- `BTG-TSK-006` Testar cálculo e erros

#### `BTG-ORD-003` - Listar pedidos por cliente

- Parent: `BTG-EP-002`
- Critérios de aceite:
  - consulta retorna apenas os pedidos do cliente informado
  - resposta é consistente com a ingestão

Tasks:

- `BTG-TSK-007` Implementar consulta por cliente
- `BTG-TSK-008` Validar paginação ou ordenação, se aplicável
- `BTG-TSK-009` Cobrir endpoint com testes

#### `BTG-ORD-004` - Garantir idempotência no consumo

- Parent: `BTG-EP-003`
- Critérios de aceite:
  - reprocessamento não duplica agregados
  - estratégia de retry e erro está documentada

Tasks:

- `BTG-TSK-010` Definir chave de idempotência
- `BTG-TSK-011` Implementar proteção contra duplicidade
- `BTG-TSK-012` Validar retry e cenário de reprocessamento

#### `BTG-CUS-001` - Consultar quantidade de pedidos por cliente

- Parent: `BTG-EP-002`
- Critérios de aceite:
  - API retorna quantidade correta com base nos pedidos ingeridos
  - cenários com zero e múltiplos pedidos são cobertos

Tasks:

- `BTG-TSK-013` Implementar agregação por cliente
- `BTG-TSK-014` Expor endpoint e contrato
- `BTG-TSK-015` Adicionar testes do agregado

#### `BTG-FE-001` - Consultar relatórios em interface única

- Parent: `BTG-EP-003`
- Critérios de aceite:
  - frontend apresenta consultas por pedido e por cliente
  - dados exibidos correspondem aos contratos do backend

Tasks:

- `BTG-TSK-016` Integrar consulta de total por pedido
- `BTG-TSK-017` Integrar consulta por cliente
- `BTG-TSK-018` Ajustar estados de carregamento, vazio e erro

## Sugestão de Colunas no Board

- `Backlog`
- `Ready`
- `In Progress`
- `In Review`
- `In Test`
- `Done`

## Sugestão de Labels

- por projeto: `votacao`, `magalu`, `btg`
- por tipo: `epic`, `story`, `task`
- por contexto: `backend`, `frontend`, `qa`, `devops`, `mensageria`, `api`, `infra`
- por prioridade: `must-have`, `should-have`, `could-have`