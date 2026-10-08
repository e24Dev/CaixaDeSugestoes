# Continuação — uma interface para a caixa

Esta é uma proposta futura, sem implementação ou branch -impl nesta entrega.

## Missão

Planejar uma interface que consuma o contrato pronto: listar/filtrar sugestões, consultar detalhe e cadastrar uma sugestão anônima ou identificada.

## Pré-requisitos

API validada e Swagger disponível. A tecnologia da interface será definida numa etapa futura; este roteiro não instala Thymeleaf nem framework frontend.

## Veja o caminho

A interface tem dois ciclos: consultar sugestões e enviar uma nova. Separá-los permite ler os caminhos de recuperação sem misturar falhas de consulta com falhas de envio.

### Consulta de sugestões

```mermaid
stateDiagram-v2
    direction TB
    state "Carregando sugestões" as Carregando
    state "Exibindo resultado" as Resultado
    state "Erro na consulta" as Erro

    [*] --> Carregando
    Carregando --> Resultado: Consulta concluída / 200
    Carregando --> Erro: Falha na consulta
    Erro --> Carregando: Tentar novamente
    Resultado --> Carregando: Alterar filtros ou atualizar
```

O resultado mostra a lista ou a mensagem de lista vazia. Em ambos os casos, **Nova sugestão** abre o formulário abaixo.

### Cadastro de sugestão

```mermaid
stateDiagram-v2
    direction TB
    state "Editando formulário" as Formulario
    state "Enviando sugestão" as Enviando
    state "Exibindo erro" as Erro
    state "Cadastro confirmado" as Sucesso

    [*] --> Formulario
    Formulario --> Enviando: Enviar
    Enviando --> Sucesso: Criação confirmada / 201
    Enviando --> Erro: Envio não confirmado
    Erro --> Formulario: Revisar e tentar novamente
    Sucesso --> [*]
```

Se houver erro de entrada, destaque os campos envolvidos. Em falhas de rede ou do servidor, preserve os dados e explique o problema. Uma falha de rede pode ocorrer após a criação no servidor: consulte a lista antes de reenviar para evitar duplicidade. Após a confirmação, mostre o sucesso e retorne a **Carregando sugestões** no ciclo de consulta.

## Passos

1. Desenhe a lista, os filtros e o formulário com curso e categoria vindos dos catálogos.
2. Preveja carregamento, lista vazia, sucesso, erro por campo e falha de rede.
3. Separe chamadas HTTP da apresentação; use o contrato da API sem duplicar regras de persistência.

## Desafio

Peça a um colega que encontre uma sugestão pelo curso e proponha uma nova sem explicar o desenho.

## Pronto quando

A jornada e os estados estão descritos. Login, segurança e implementação da interface exigirão planejamento próprio.
