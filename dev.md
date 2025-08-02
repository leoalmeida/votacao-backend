# Votação

## Objetivo

No cooperativismo, 
- cada associado possui um voto e as  
- decisões são tomadas em assembleias, por votação. 

- solução mobile para gerenciar e participar dessas sessões de votação.

Solução cloud com as seguintes API REST:
- Cadastro de nova pauta (PautaController->criarPauta)
- Abertura de sessão de votação (para uma pauta)
  * Sessão aberta por tempo determinado na solicitação (default 1 min)
  (SessaoController->criarSessao)
  * Contabilização dos votos no fim da sessão
  (SessaoController->finalizarSessao)
- Recebimento dos votos de uma sessão 
  * Votos contabilizados no formato ('Sim'/'Não'). 
  * Associado identificado por seu id único
  * Cada associado possui apenas 1 voto por pauta
  (SessaoController->finalizarSessao)
- Consulta de resultado da votação para uma pauta   


** Segurança das interfaces deve ser realizada utilizando tokens de acesso. 
** Solução Backend construída com Spring-boot
** Pautas e votos Persistidos com MongoDB 

** Arquitetura da aplicação será separada em 2 camadas (Backend e App Mobile) 
  - A comunicação entre elas será realizada através de mensagens no formato JSON
  - Mensagens interpretadas no cliente para montagem das telas do usuário
  - Cliente não faz parte desse desafio, apenas o backend. 
  - Formato padrão dessas mensagens detalhados abaixo

    ```
    GET http://www.lasf.space/api/v1/associado
    ```

    ```
    GET http://www.lasf.space/api/v1/referencias
    ```

    ```
    POST http://www.lasf.space/api/v1/pauta
    JWT: {
        "id": "login"
    }
    Body: {
        "tipo": "tipo1",
        "descricao": "descricao"
    }
    Result: {
        "idPauta": 999,
        "idSessao": 999,
        "DataInicioVotacao": "01/01/2000",
        "tempoVotacao": 1
    }
    ```

    ```
    POST http://www.lasf.space/api/v1/sessao
    JWT: {
        "id": "login"
    }
    Body: {
        "tempoVotacao": 1
    }
    Result: {
        "idSessao": 999,
        "status": "CREATED",
        "tempoVotacao": 1
    }
    ```

    ```
    PUT http://www.lasf.space/pauta/api/v1/sessao/:id/open
    JWT: {
        "id": "login"
    }
    Result: {
        "idPauta": 999,
        "idSessao": 999,
        "status": OPEN_TO_VOTE,
        "DataInicioVotacao": "01/01/2000",
        "DataFimVotacao": "01/01/2000"
    }
    ```

    ```
    PUT http://www.lasf.space/api/v1/pauta/:id/close
    JWT: {
        "id": "server"
    }
    Result: {
        "idPauta": 999,
        "idSessao": 999,
        "status": CLOSED,
        "DataInicioVotacao": "01/01/2000",
        "DataFimVotacao": "01/01/2000"
    }
    ```
    
    ```
    PUT http://www.lasf.space/api/v1/pauta/:id/voto/:opcao
    Result: {
        "idPauta": 999,
        "idSessao": 999,
        "idVoto": 999
    }
    ```

    ```
    GET http://www.lasf.space/api/v1/pauta/:id
    Result: {
        "idPauta": 999,
        "idSessao": 999,
        "status": CLOSED,
        "DataInicio": "01/01/2000",
        "DataFim": "01/01/2000",
        "tempoVotacao": 1,
        "solicitante": "login",
        "resultado": "SIM",
        "totalizadores": [{
            "opcao": "SIM",
            "quantdade": 10 
        },{
            "opcao": "NAO",
            "quantdade": 5 
        }]
    }
    ```



A tela do tipo SELECAO exibe uma lista de opções para que o usuário.

O aplicativo envia uma requisição POST para a url informada e com o body definido pelo objeto dentro de cada item da lista de seleção, quando o mesmo é acionado, semelhando ao funcionamento dos botões da tela FORMULARIO.

### Tarefas bônus

- Tarefa Bônus 1 - Integração com sistemas externos
  - Criar uma Facade/Client Fake que retorna aleátoriamente se um CPF recebido é válido ou não.
  - Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos
  - Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação. Essa operação retorna resultados aleatórios, portanto um mesmo CPF pode funcionar em um teste e não funcionar no outro.

```
// CPF Ok para votar
{
    "status": "ABLE_TO_VOTE
}
// CPF Nao Ok para votar - retornar 404 no client tb
{
    "status": "UNABLE_TO_VOTE
}
```

Exemplos de retorno do serviço

### Tarefa Bônus 2 - Performance

- Imagine que sua aplicação possa ser usada em cenários que existam centenas de
  milhares de votos. Ela deve se comportar de maneira performática nesses
  cenários
- Testes de performance são uma boa maneira de garantir e observar como sua
  aplicação se comporta

### Tarefa Bônus 3 - Versionamento da API

○ Como você versionaria a API da sua aplicação? Que estratégia usar?



## O que será analisado

- Simplicidade no design da solução (evitar over engineering)
- Organização do código
- Arquitetura do projeto
- Boas práticas de programação (manutenibilidade, legibilidade etc)
- Possíveis bugs
- Tratamento de erros e exceções
- Explicação breve do porquê das escolhas tomadas durante o desenvolvimento da solução
- Uso de testes automatizados e ferramentas de qualidade
- Limpeza do código
- Documentação do código e da API
- Logs da aplicação
- Mensagens e organização dos commits

## Dicas

- Teste bem sua solução, evite bugs
- Deixe o domínio das URLs de callback passiveis de alteração via configuração, para facilitar
  o teste tanto no emulador, quanto em dispositivos fisicos.
  Observações importantes
- Não inicie o teste sem sanar todas as dúvidas
- Iremos executar a aplicação para testá-la, cuide com qualquer dependência externa e
  deixe claro caso haja instruções especiais para execução do mesmo
  Classificação da informação: Uso Interno

## Anexo 1

### Introdução

A seguir serão detalhados os tipos de tela que o cliente mobile suporta, assim como os tipos de campos disponíveis para a interação do usuário.

### Tipo de tela – FORMULARIO

A tela do tipo FORMULARIO exibe uma coleção de campos (itens) e possui um ou dois botões de ação na parte inferior.

O aplicativo envia uma requisição POST para a url informada e com o body definido pelo objeto dentro de cada botão quando o mesmo é acionado. Nos casos onde temos campos de entrada
de dados na tela, os valores informados pelo usuário são adicionados ao corpo da requisição. Abaixo o exemplo da requisição que o aplicativo vai fazer quando o botão “Ação 1” for acionado:

```
POST http://seudominio.com/ACAO1
{
    “campo1”: “valor1”,
    “campo2”: 123,
    “idCampoTexto”: “Texto”,
    “idCampoNumerico: 999
    “idCampoData”: “01/01/2000”
}
```

Obs: o formato da url acima é meramente ilustrativo e não define qualquer padrão de formato.

### Tipo de tela – SELECAO

A tela do tipo SELECAO exibe uma lista de opções para que o usuário.

O aplicativo envia uma requisição POST para a url informada e com o body definido pelo objeto dentro de cada item da lista de seleção, quando o mesmo é acionado, semelhando ao funcionamento dos botões da tela FORMULARIO.

# desafio-votacao

## Como proceder
Realizar o clone do projeto.
Antes de realizar o deploy acessar o arquivo `application.properties` e alterar o endereco do banco de dados.

O banco de dados utilizado foi o MongoDB.
Alterar as configurações abaixo:

 `spring.data.mongodb.port=8085`

 `spring.data.mongodb.host=localhost`

Com isso feito, basta executar o comando abaixo para subir a aplicação.

 `mvn spring-boot:run`

Com a aplicação rodando, basta acessar o endereço http://localhost:8080/swagger-ui.html para visualizar a documentação da API e testar os endpoints

# desafio-votacao