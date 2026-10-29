# API completa — administrador com JWT

Branch final: `09-jwt-bearer-token-impl`. A branch `07-testes-entrega-impl`
preserva a API sem autenticação; `08-spring-security-impl` é o exercício
intermediário com Basic. A implementação final aceita somente Bearer para
as operações administrativas.

## Preparar e iniciar

Requisitos: JDK 17, Docker e as variáveis locais abaixo. Não existe senha padrão.

```powershell
.\gradlew.bat bootJar
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar --generate-admin-password-hash
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar --generate-jwt-secret
```

O primeiro auxiliar pede uma senha em terminal interativo, sem mostrá-la. Copie
seu hash BCrypt; o segundo imprime uma chave aleatória. Ambos funcionam antes de
iniciar Spring e sem configuração de banco.

Configure no terminal/IDE que iniciará a API:

```powershell
$env:ADMIN_USERNAME = 'administrador'
$env:ADMIN_PASSWORD_HASH = 'COLE_O_HASH_BCRYPT_GERADO'
$env:JWT_SECRET_BASE64 = 'COLE_A_CHAVE_ALEATORIA_GERADA'
$env:JWT_TTL_SECONDS = '900'
$env:SERVER_PORT = '8000'
docker compose up -d --wait
.\gradlew.bat bootRun
```

No Linux/macOS, use `export NOME='valor'` e `./gradlew`. Não mantenha os
placeholders literais. O Java não lê automaticamente o `.env` do Compose.
Banco, usuário e senha locais do PostgreSQL são `suggestions`; overrides:
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`. Preserve o volume para manter os dados.

Swagger: `http://localhost:8000/swagger-ui.html`. JSON: `/v3/api-docs`.
Defina `SERVER_PORT` e ajuste `baseUrl` se precisar de outra porta.

## Acesso e login

| Operação | Acesso |
| --- | --- |
| Consultar sugestões, cursos e categorias | Público |
| Enviar sugestão | Público, inclusive anônimo |
| POST `/api/auth/login` | Emite JWT somente com credencial administrativa válida |
| PUT/DELETE de sugestão | Bearer JWT válido e papel ADMIN |

O login recebe JSON com `username` e `password`. A resposta contém `accessToken`,
`tokenType: Bearer` e `expiresIn` em segundos. Envie o token como
`Authorization: Bearer <accessToken>`. No Authorize do Swagger, cole apenas o JWT.

A API não oferece cadastro, refresh token ou revogação individual. JWT expira
em 15 minutos por padrão; o intervalo configurável é 60–3600 segundos. A mesma
chave preserva tokens não expirados após reinício. Trocar a chave invalida tokens;
trocar somente a senha não revoga os já emitidos. Use HTTPS fora de localhost.
Não salve tokens em logs, exemplos ou arquivos versionados.

## Postman e Insomnia

Importe os arquivos da pasta [collections](collections). Cada coleção tem **29
requisições**. Preencha `adminUsername` e `adminPassword` localmente, execute em
ordem e deixe os scripts capturarem os IDs e `accessToken`. Não habilite Bearer
na coleção inteira: somente as operações protegidas enviam o header.
O pré-script do login serializa o JSON para aceitar aspas e barras na senha.

01–02 consultam catálogos; 03 autentica; 04–05 verificam login inválido;
06 cria publicamente; 07–09 conferem bloqueios; 10–15 consultam/filtram;
16 atualiza; 17–24 verificam erros; 25–28 excluem e confirmam ausência;
29 consulta OpenAPI. A exclusão atinge somente o registro criado pelo fluxo.

Não exporte credenciais, loginBody, token nem respostas reais salvas. As
collections versionadas têm esses dados vazios. A importação visual permanece
uma conferência manual no cliente; os requests/scripts foram exercitados pelo
verificador local.

## Testes e entrega

```powershell
.\gradlew.bat test bootJar
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar
```

O ambiente da execução do JAR deve ter as variáveis acima. A suíte tem **32
testes**, incluindo PostgreSQL Testcontainers e JWT real, sem testes ignorados.
Os valores do perfil `test` não são defaults da aplicação.

Opcionalmente, com Node.js 18+, o verificador lê `ADMIN_USERNAME` e
`ADMIN_PASSWORD` no ambiente **do cliente** e executa as duas collections:

```bash
node scripts/verify-collections.mjs http://localhost:8000
```

A API recebe somente o hash; `ADMIN_PASSWORD` não configura o servidor.
`docker compose down` preserva o volume; `down -v` apaga os dados.

Tutoriais: [08 — Spring Security](docs/08-spring-security.md),
[09 — JWT](docs/09-jwt-bearer-token.md) e [planejamento web](docs/10-continuacao-web.md).
