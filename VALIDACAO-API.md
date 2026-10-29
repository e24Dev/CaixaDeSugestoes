# Validação da API — administrador no banco

Validação realizada em 29/10/2026 na solução `09-jwt-bearer-token-impl`.

- `./gradlew build`: aprovado, **39 testes**, zero falhas/erros/ignorados.
- Checkpoint 08 atualizado: build aprovado com **30 testes**.
- Toolchain Java 17; JAR executado com Java 21.0.12.
- PostgreSQL 17 real, Flyway V1/V2/V3 e validação JPA.
- V3 cria administrators sem seed de credenciais.
- Provisionamento grava BCrypt e recusa alterar/substituir uma conta existente.
- Entrada inválida não cria conta; consultas e envio continuam públicos.
- Login consulta o banco; conta inativa é rejeitada tanto no login quanto na
  validação de um JWT emitido antes de sua desativação.
- Chave automática aleatória de 32 bytes, reutilizada no arquivo local privado.
- Testes de chave persistente, permissões POSIX, override externo e rejeição de
  chave inválida sem sobrescrever o arquivo existente.
- Testes JWT de assinatura, claims, expiração, 401/403, ausência de sessão e Basic desabilitado.

## Fluxo real validado em PostgreSQL isolado

1. A API iniciou sem ADMIN_USERNAME, ADMIN_PASSWORD_HASH ou JWT_SECRET_BASE64.
2. Migrations criaram o banco com catálogo e tabela administrativa vazia.
3. A chave nasceu automaticamente em arquivo privado; login sem conta retornou 401.
4. O JAR --setup-admin recebeu username, senha e confirmação em PTY real.
5. A senha não apareceu no eco; a conta ficou persistida. Repetir o comando
   retornou erro de conta existente, sem alterar a chave ou os dados.
6. As duas collections executaram **58 requests** aprovados, com captura de
   accessToken e autorização apenas nas operações administrativas.
7. A senha temporária continha aspas e barra invertida, verificando a serialização.
8. Desativar a conta no banco bloqueou login e uso do token.
9. Reiniciar o JAR preservou a sugestão, a conta e a chave; o token ainda válido
   foi aceito na exclusão administrativa.
10. O container de validação foi removido e a API já aberta pelo usuário não foi interrompida.

Não houve senha padrão ou credencial real versionada. A interface de importação
no Insomnia/Postman não foi automatizada; requests e scripts foram exercitados
com o verificador local de compatibilidade. Os testes não usam o banco do Compose.

Os tutoriais 08 e 09 foram reconstruídos a partir dos próprios Markdown e passaram
com 30 e 39 testes. Os **30 diagramas Mermaid** foram renderizados sem falhas.
Consulte [SOLUCAO.md](SOLUCAO.md) para executar o fluxo e
[docs/validacao.md](docs/validacao.md) para a verificação documental.
