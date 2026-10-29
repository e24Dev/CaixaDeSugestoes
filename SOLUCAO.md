# Checkpoint 08 — administrador no banco

A migration V3 cria `administrators` sem seed de senha. Configure o banco com
`docker compose up -d --wait`, execute `./gradlew bootJar` e faça o cadastro inicial:

```bash
java -jar build/libs/CaixaDeSugestoes-0.0.1-SNAPSHOT.jar --setup-admin
```

O comando pede usuário, senha e confirmação sem eco, grava somente BCrypt no
banco e recusa sobrescrever uma conta já existente. Depois, use `./gradlew bootRun`.
Não há ADMIN_USERNAME nem ADMIN_PASSWORD_HASH na configuração do servidor.

Este checkpoint usa Basic somente como etapa pedagógica. PUT/DELETE exigem ADMIN;
consultas e envio são públicos. A etapa 09 adiciona login JWT e chave automática.
Leia [o tutorial](docs/08-spring-security.md). Testes precisam de Docker.
