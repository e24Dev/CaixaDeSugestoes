# Validação da API protegida — 29/10/2026

Solução atual: `09-jwt-bearer-token-impl`. A versão sem autenticação permanece
em `07-testes-entrega-impl`; o checkpoint 08 usa Basic como exercício intermediário.

- `./gradlew test bootJar`: aprovado, **32 testes**, zero falhas e zero ignorados.
- Checkpoint 08: **26 testes** aprovados, antes da substituição por JWT.
- Toolchain Java 17; execução do JAR com Java 21.0.12.
- PostgreSQL 17 real via Testcontainers nos testes e Compose na execução do JAR.
- Login real e uso do JWT real para PUT/DELETE, sem sessão ou cookies de autenticação.
- Consultas e POST público de sugestão permanecem disponíveis sem token.
- Testes de senha incorreta, usuário inexistente, entrada inválida, 401 e 403.
- Rejeição de token expirado, assinatura adulterada, issuer/audience/subject
  incorretos e ausência de expiração.
- Configuração de segredo fraco/ausente e senha administrativa sem hash é rejeitada.
- Basic deixa de autenticar na versão final.
- OpenAPI marca somente PUT/DELETE com requisito Bearer; login e POST público
  continuam sem requisito global de autenticação.
- **58 requisições** aprovadas: 29 Postman e 29 Insomnia contra o JAR protegido.
  O verificador executa pré-scripts, requests e scripts de resposta usando uma
  camada local de compatibilidade dos métodos usados pelas collections.
- Senha temporária contendo aspas e barra invertida validada, conferindo a
  serialização do JSON pelos pré-scripts, sem credenciais versionadas.
- Reinício do JAR com a mesma chave: sugestão preservada com os mesmos dados e
  JWT não expirado aceito na exclusão administrativa. Registro temporário removido.
- A execução de validação usou porta 18000 e credenciais aleatórias temporárias,
  sem interromper a API que já estava aberta no navegador.

A interface de importação dos clientes não foi automatizada. As credenciais e o
segredo usados na validação não constituem defaults da aplicação. Para iniciar
sua própria instância, siga [SOLUCAO.md](SOLUCAO.md).

A reprodução dos tutoriais e a renderização dos diagramas estão registradas
separadamente em [docs/validacao.md](docs/validacao.md).

## Diagnóstico de bootRun no VSCode

`./gradlew build` foi executado novamente: aprovado, com os 32 testes. A falha
de `./gradlew bootRun` foi reproduzida e identificada como ausência de
`ADMIN_USERNAME` no ambiente de execução; PostgreSQL e migrations funcionaram.

Foi acrescentado `config/application.properties.example` e a regra de Git para
ignorar o arquivo preenchido. A leitura automática de `config/application.properties`
foi verificada em um JAR iniciado de uma pasta temporária, sem as variáveis
administrativas exportadas: login, token e persistência após reinício aprovados.
A validação usou credenciais aleatórias temporárias e não provisionou uma senha
padrão na cópia de trabalho. O administrador deve escolher e configurar suas
próprias credenciais conforme `SOLUCAO.md`.
