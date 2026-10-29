# API completa — administrador no PostgreSQL e JWT

Versão final: `09-jwt-bearer-token-impl`. A etapa 07 preserva a API sem autenticação;
a 08 usa Basic somente como checkpoint pedagógico. Na versão final, somente o
administrador ativo cadastrado no banco recebe JWT e pode editar/excluir sugestões.

## Primeira utilização

Requisitos: JDK 17 e Docker. Na raiz do projeto, no terminal do VSCode:

```bash
docker compose up -d --wait
./gradlew bootJar
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar --setup-admin
```

No Windows, use `.\gradlew.bat bootJar`. O comando pede usuário, senha e confirmação
sem mostrar a senha. Ele aplica as migrations e cadastra a primeira conta no banco,
gravando somente BCrypt. A senha exige 12 caracteres ou mais e no máximo 72 bytes
UTF-8. Se já houver uma conta, o comando recusa alterar ou sobrescrever seus dados.
Ele não serve como reset de senha e não abre um servidor HTTP.

A migration V3 cria `administrators` sem seed de credencial. Não há senha padrão,
cadastro público, ADMIN_USERNAME ou ADMIN_PASSWORD_HASH na configuração da API.

Depois do cadastro:

```bash
./gradlew bootRun
```

Ou execute o JAR normalmente. As próximas execuções precisam somente do banco.
É possível iniciar a API antes do cadastro; as operações públicas funcionam,
mas o login só terá sucesso depois de provisionar uma conta ativa.

## Chave JWT sem comandos manuais

Na primeira inicialização, uma chave aleatória é criada em `.local/jwt-signing.key`.
Ela é reutilizada nos próximos boots e ignorada pelo Git. Não apague esse arquivo
se quiser preservar tokens não expirados. Em POSIX, arquivo e pasta nova são
criados com permissões 600 e 700. A chave não é a senha do administrador.

Mantenha o mesmo diretório de trabalho. Para outro local ou implantação,
configure `JWT_KEY_FILE` com um caminho privado persistente. `JWT_SECRET_BASE64`
continua disponível como override externo. Réplicas devem compartilhar a mesma
chave; arquivos de containers precisam de volume persistente. Não versão a chave.

A validade padrão é 900 segundos; `JWT_TTL_SECONDS` aceita 60–3600. Os auxiliares
antigos de geração manual ainda existem por compatibilidade, mas não são
necessários para este fluxo.

## Swagger, login e collections

A porta de referência é 8000; use a porta indicada no seu log. `SERVER_PORT` pode
sobrescrevê-la. Swagger: `/swagger-ui.html`; OpenAPI JSON: `/v3/api-docs`.

| Operação | Acesso |
| --- | --- |
| Consultar sugestões, detalhe, cursos e categorias | Público |
| Enviar sugestão, inclusive anônima | Público |
| POST `/api/auth/login` | Credenciais de administrador ativo no banco |
| PUT/DELETE sugestão | JWT válido com ADMIN e conta ainda ativa |

O login recebe username/password escolhidos no setup e devolve accessToken,
tokenType=`Bearer` e expiresIn em segundos. Envie `Authorization: Bearer <token>`.
No Authorize do Swagger, cole somente o JWT. Não habilite autenticação global
para as chamadas públicas.

Importe os arquivos de [collections](collections). Cada cliente tem **29 requests**.
Preencha adminUsername/adminPassword com a conta provisionada, somente localmente,
e execute em ordem. 03 captura o token; 06 cria publicamente; 07–09 verificam 401;
16 atualiza com Bearer; 25–28 excluem e conferem ausência. Os demais cobrem
catálogos, filtros, erros e OpenAPI. A exclusão atinge o registro criado pelo fluxo.
Não exporte senhas, loginBody, tokens ou respostas reais salvas.

## Configuração opcional

Não é necessário preencher um arquivo de credenciais para iniciar. Se quiser
personalizar porta, validade ou caminho da chave, copie uma vez
`config/application.properties.example` para `config/application.properties`.
O arquivo preenchido é ignorado pelo Git e carregado automaticamente pelo Spring
Boot quando executado da raiz. Variáveis de ambiente têm prioridade.

O `.env` do Compose não configura automaticamente a aplicação Java. Banco,
usuário e senha locais são `suggestions`; DB_URL, DB_USERNAME e DB_PASSWORD
permitem overrides. Se mudar a senha no Compose, configure também a API.

## Testes e preservação

```bash
./gradlew build
```

A suíte final tem **39 testes**, com PostgreSQL Testcontainers, sem ignorados.
O banco temporário dos testes não é o banco do Compose usado pelo Insomnia.
O verificador opcional com Node.js 18+ executa as duas collections:

```bash
node scripts/verify-collections.mjs http://localhost:8000
```

Somente esse **cliente** recebe ADMIN_USERNAME e ADMIN_PASSWORD no seu ambiente,
para autenticar a conta já cadastrada. Essas variáveis não provisionam o servidor.

Reiniciar com o mesmo banco e chave preserva conta, sugestões e tokens não expirados.
Trocar a chave invalida tokens anteriores. Desativar a conta bloqueia login e uso
de tokens enquanto inativa. Trocar a senha não revoga automaticamente os tokens;
não há refresh ou revogação individual. Use HTTPS fora de localhost.

`docker compose down` preserva o volume; `down -v` apaga o banco e o administrador.
Faça backup antes de operações destrutivas. Diagnóstico:

| Falha | Conferência |
| --- | --- |
| Login 401 | Conta provisionada? Username/senha corretos? Active=true? |
| Setup informa conta existente | Não altera a conta; não é reset de senha |
| Chave inválida/sem permissão | Confira JWT_KEY_FILE ou override externo; não apague sem avaliar tokens |
| PostgreSQL indisponível | Docker iniciado, Compose saudável e DB_URL correta |
| Porta ocupada | Encerre a cópia anterior ou altere SERVER_PORT e baseUrl |

Tutoriais: [08](docs/08-spring-security.md), [09](docs/09-jwt-bearer-token.md)
e [planejamento web](docs/10-continuacao-web.md).
