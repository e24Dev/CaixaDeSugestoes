# Checkpoint 08 — Spring Security

API cumulativa até 08. A etapa 07 permanece disponível sem autenticação.
Este checkpoint intermediário usa Basic para ensinar autenticação e autorização;
a etapa 09 substitui Basic por login administrativo e Bearer JWT.

Leia [o tutorial completo](docs/08-spring-security.md). Requisitos: JDK 17 e Docker.

```bash
./gradlew bootJar
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar --generate-admin-password-hash
```

Configure `ADMIN_USERNAME` e `ADMIN_PASSWORD_HASH` no terminal/IDE que inicia a API.
O hash gerado deve ficar entre aspas simples para preservar os caracteres `$`.
Não existe senha padrão nem cadastro público. A API exige essa configuração.

```bash
docker compose up -d --wait
./gradlew bootRun
```

Consultas e envio de sugestões são públicos; PUT e DELETE exigem ADMIN via Basic.
As collections usam `adminUsername` e `adminPassword` preenchidos localmente.
Não exporte senhas reais. Use HTTPS fora de localhost. Swagger: porta 8000,
`/swagger-ui.html`. Defina `SERVER_PORT` para alterar a porta e ajuste `baseUrl`.

`./gradlew test bootJar` executa a suíte com Docker e gera o JAR. A configuração
dos testes é isolada e não fornece credenciais padrão para a aplicação.
