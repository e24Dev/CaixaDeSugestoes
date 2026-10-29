# Caixa de Sugestões — Etec Prefeito Alberto Feres

Construa uma API Java que uma aplicação web com Thymeleaf poderá consumir.
A trilha começa com um servidor sem banco e evolui até CRUD, persistência,
validação, Swagger, testes, collections Postman/Insomnia e autenticação administrativa com JWT.

## Comece por aqui

1. Prepare o [ambiente Windows](docs/ambiente-windows.md), ou JDK 17 e Git no seu sistema.
2. Leia [como estudar e usar as branches](docs/como-estudar.md).
3. Entenda o [modelo de dados e o contrato HTTP](docs/00-modelo-dados.md).
4. Crie seu próprio projeto no módulo 01 e continue nele até 09.

A `main` é a entrada documental. Os capítulos contêm instruções, caminhos e
arquivos completos para reprodução; o código executável fica nos checkpoints
`-impl`. A solução final protegida está em `09-jwt-bearer-token-impl`.
A etapa `07-testes-entrega-impl` preserva a versão completa sem autenticação.
Em cada checkpoint executável, leia `SOLUCAO.md` para iniciar a versão correspondente.

## Roteiro incremental

```mermaid
flowchart LR
    Modelo["00 · Modelo e contrato"] --> Projeto["01 · Projeto"]
    Projeto --> HTTP["02 · HTTP"]
    HTTP --> Banco["03 · PostgreSQL"]
    Banco --> CRUD["04 · CRUD"]
    CRUD --> Erros["05 · Validação"]
    Erros --> Swagger["06 · Swagger"]
    Swagger --> Entrega["07 · Testes e entrega"]
    Entrega --> Security["08 · Spring Security"]
    Security --> JWT["09 · JWT administrativo"]
    JWT --> Web["10 · Planejar Thymeleaf"]
```

| Módulo | Entrega | Branch de estudo | Código de apoio |
| --- | --- | --- | --- |
| [01 — Projeto](docs/01-projeto-spring-boot.md) | Aplicação inicia sem banco | [01-projeto-spring-boot](../../tree/01-projeto-spring-boot) | [01-projeto-spring-boot-impl](../../tree/01-projeto-spring-boot-impl) |
| [02 — API REST](docs/02-api-rest.md) | Primeiro GET, com array vazio | [02-api-rest](../../tree/02-api-rest) | [02-api-rest-impl](../../tree/02-api-rest-impl) |
| [03 — PostgreSQL, Flyway e JPA](docs/03-postgresql-flyway-jpa.md) | Schema, migrations e catálogos | [03-postgresql-flyway-jpa](../../tree/03-postgresql-flyway-jpa) | [03-postgresql-flyway-jpa-impl](../../tree/03-postgresql-flyway-jpa-impl) |
| [04 — Casos de uso CRUD](docs/04-casos-de-uso-crud.md) | Cadastro, busca, alteração e exclusão | [04-casos-de-uso-crud](../../tree/04-casos-de-uso-crud) | [04-casos-de-uso-crud-impl](../../tree/04-casos-de-uso-crud-impl) |
| [05 — Validação e erros](docs/05-validacao-erros.md) | Entradas validadas e erros uniformes | [05-validacao-erros](../../tree/05-validacao-erros) | [05-validacao-erros-impl](../../tree/05-validacao-erros-impl) |
| [06 — OpenAPI e Swagger](docs/06-openapi-swagger.md) | Contrato navegável e executável | [06-openapi-swagger](../../tree/06-openapi-swagger) | [06-openapi-swagger-impl](../../tree/06-openapi-swagger-impl) |
| [07 — Testes e entrega](docs/07-testes-entrega.md) | API completa, JAR e collections | [07-testes-entrega](../../tree/07-testes-entrega) | [07-testes-entrega-impl](../../tree/07-testes-entrega-impl) |
| [08 — Spring Security](docs/08-spring-security.md) | Administrador BCrypt e permissões; Basic intermediário | [08-spring-security](../../tree/08-spring-security) | [08-spring-security-impl](../../tree/08-spring-security-impl) |
| [09 — JWT e Bearer Token](docs/09-jwt-bearer-token.md) | Login administrativo e API final protegida | [09-jwt-bearer-token](../../tree/09-jwt-bearer-token) | [09-jwt-bearer-token-impl](../../tree/09-jwt-bearer-token-impl) |

As branches de estudo do professor são documentais. As `-impl` contêm código
cumulativo, sem exigir que o aluno troque seu projeto pelo gabarito. Os capítulos
explicam o que criar ou substituir; preserve o restante da etapa anterior.
Os links remotos dependem da publicação das branches pelo professor.

## Tecnologias e limites desta entrega

Java 17, Spring Boot 4.1.1, Gradle Wrapper 9.7.1, PostgreSQL 17, Flyway,
JPA/Hibernate e Springdoc 3.1.1. A configuração usa `application.yaml`.
Porta de referência: **8000**. Não é necessário instalar Gradle globalmente.

O domínio fica separado da aplicação, persistência e REST. A API oferece
sugestões com autor opcional, catálogos somente para consulta, filtros combinados
e lista sem paginação. As etapas 01–07 não têm autenticação. Em 08–09, somente o administrador
faz login e recebe permissão para editar/excluir; consultas e envio continuam
públicos. Não há cadastro de usuários ou triagem.
Use somente dados fictícios. Os cursos são exemplos didáticos, não o catálogo
oficial da escola.

Depois da API, o [roteiro web](docs/10-continuacao-web.md) orienta o planejamento
do projeto Java com Thymeleaf, que será desenvolvido separadamente.

## Para o professor

Consulte o [guia do professor](docs/guia-professor.md) para revisar e distribuir
somente os Markdown na `main`, preservar checkpoints e usar a solução final.
A [validação documental](docs/validacao.md) registra a reprodução dos capítulos.

O [rascunho original](docs/arquivo/rascunho-tutorial-original.md) permanece como
arquivo histórico. Suas rotas, paginação e tecnologias não definem esta trilha.
A organização didática teve como referência o projeto PIApi; o contrato vigente
é o descrito neste repositório.
