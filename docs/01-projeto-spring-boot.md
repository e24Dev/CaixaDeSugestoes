# 01 — Ligar o motor

[Roteiro](../README.md) · [Contrato](00-modelo-dados.md)

## Missão

Criar o projeto Spring Boot do zero e compreender o que inicia o servidor.

## Pré-requisitos

Ambiente preparado e modelo de dados lido. Você continuará o seu código; a branch de exercício não fornece a solução anterior.

## Passos

1. Crie um projeto Gradle Groovy com Java 17, group `br.com.etecalbertoferes`, nome CaixaDeSugestoes e pacote `br.com.etecalbertoferes.CaixaDeSugestoes`. O Spring Initializr pode gerar o esqueleto; confira as versões em vez de aceitar versões diferentes automaticamente.
2. Use Spring Boot 4.1.1 e plugin dependency-management 1.1.7. Configure toolchain 17 e Wrapper 9.7.1. Inicialmente inclua somente Web MVC e o starter de testes Web MVC. Não adicione persistência antes do módulo 03.
3. Crie a classe de entrada com `@SpringBootApplication` e método main chamando SpringApplication.run. Explique o papel do component scan e por que os demais pacotes ficam abaixo do pacote raiz.
4. Crie `src/main/resources/application.yaml`: `server.port` vale 8000 e `spring.application.name` vale CaixaDeSugestoes. YAML usa espaços para níveis, nunca tabs. Não crie application.properties.
5. Inicialize seu próprio Git e faça o primeiro commit. Execute `./gradlew test` e `./gradlew bootRun` (Windows: `.\gradlew.bat test` e `.\gradlew.bat bootRun`). A aplicação deve iniciar; `/api/suggestions` ainda responde 404.

**Desafio:** explique a diferença entre JDK da IDE, toolchain e Gradle Wrapper. Mude temporariamente a porta no seu projeto e restaure 8000 antes do commit.

**Pronto quando:** aplicação inicia na porta 8000, teste de contexto passa e o projeto não depende de banco.

## Registro da etapa

Execute os comandos na raiz do seu projeto. Faça um commit explicando o resultado da missão. Consulte a branch `01-projeto-spring-boot-impl` em uma cópia separada somente depois de tentar; ela contém a solução até esta etapa, não a API futura.

[Próximo módulo](02-api-rest.md)
