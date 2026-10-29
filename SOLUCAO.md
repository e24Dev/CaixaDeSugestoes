# API completa — checkpoint 07

Implementação cumulativa para a futura aplicação Java com Thymeleaf. Os
[tutoriais](README.md) mostram como reconstruir a API em sete etapas, com
arquivos completos e checkpoints de apoio. Consulte também
[como estudar](docs/como-estudar.md) e a [validação da reprodução](docs/validacao.md).

## Executar

Requisitos: JDK 17 e Docker iniciado. O Gradle Wrapper acompanha o projeto.

```bash
git switch 07-testes-entrega-impl
docker compose up -d --wait
./gradlew bootRun
```

No Windows, use `.\gradlew.bat bootRun`. A API fica em `http://localhost:8000`.
Swagger: `http://localhost:8000/swagger-ui.html`; OpenAPI: `/v3/api-docs`.

O Compose expõe PostgreSQL somente em `127.0.0.1:5432`, banco e usuário
`suggestions`, senha local de demonstração `suggestions`. Variáveis opcionais:
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD` e `SERVER_PORT`. `.env` é lido pelo Compose;
para executar Java/Gradle, exporte as variáveis no terminal ou configure a IDE.
Se trocar a senha de um volume já inicializado, ajuste também o usuário no banco.

## Validar no Postman ou Insomnia

Importe o arquivo correspondente da pasta [collections](collections).

- Postman: `caixa-de-sugestoes.postman_collection.json`. O ambiente
  `caixa-de-sugestoes.postman_environment.json` é opcional; a coleção já contém baseUrl.
- Insomnia: `caixa-de-sugestoes.insomnia.json`, exportação JSON v4 com scripts
  after-response. Selecione o ambiente base da coleção.

Execute as requisições em ordem numérica. 01 e 02 capturam os IDs reais dos
catálogos; 03 cria uma sugestão e captura seu ID e data. As demais usam essas
variáveis e verificam os códigos HTTP e o contrato. 19 exclui a sugestão criada;
20–22 confirmam a ausência. Os cenários de erro são intencionais.
Reexecutar o fluxo completo cria outra sugestão; não execute criação repetidamente
sem excluir os registros anteriores.

Para comprovar persistência, pare após 04, reinicie somente a API e repita 04.
Continue até 23. A etapa 10 transforma a sugestão em anônima e confere a data.
O filtro com `%`, `_` e `!` trata esses caracteres literalmente.
Os scripts seguem a [API de scripts do Insomnia](https://developer.konghq.com/insomnia/scripts/).

## Testes e JAR

```bash
./gradlew test bootJar
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar
```

Confirme o nome em `build/libs` se alterar `settings.gradle` ou a versão.
Os testes de integração usam PostgreSQL 17 em Testcontainers e falham se Docker
não estiver disponível. Para rodar somente os testes sem banco:

```bash
./gradlew test --tests '*SuggestionServiceTest' --tests '*SuggestionControllerTest'
```

`docker compose down` para o banco sem apagar o volume. `down -v` apaga os dados.
A futura aplicação Thymeleaf deve consumir a API a partir do servidor Java;
não é necessário CORS para esse fluxo. A API segue o contrato de
[modelo de dados](docs/00-modelo-dados.md), sem autenticação nesta etapa.

## Branches locais

`main` e as branches de exercício permanecem documentais. Os checkpoints `-impl`
são cumulativos de 01 a 07. Esta branch contém a solução final, testes e collections.
Nenhuma branch foi publicada remotamente.
