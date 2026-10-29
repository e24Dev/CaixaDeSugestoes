# Validação da reprodução dos tutoriais

[Roteiro](../README.md) · [Guia do professor](guia-professor.md)

Validação realizada em **29 de outubro de 2026** na revisão
`09-jwt-bearer-token-impl` (incluindo a atualização da conta no banco).

## O que foi reproduzido

Um projeto novo foi montado em uma pasta temporária, aplicando em ordem os
blocos marcados com `<!-- arquivo: caminho -->` nos capítulos 01–09. Cada bloco
contém um arquivo completo, criado ou substituído conforme a instrução.
Nenhum arquivo Java, SQL ou de configuração foi obtido da solução para completar
uma lacuna entre os capítulos.

Os únicos arquivos de bootstrap copiados do checkpoint 01 foram `gradlew`,
`gradlew.bat` e o binário `gradle/wrapper/gradle-wrapper.jar`. Na reprodução pelo
aluno, esses três arquivos vêm do projeto gerado pelo Initializr, conforme 01.
O arquivo textual `gradle-wrapper.properties` está incluído no tutorial.

Depois de cada capítulo:

1. O conteúdo cumulativo de `src`, `build.gradle`, `settings.gradle`,
   `gradle-wrapper.properties` e, a partir de 03, `compose.yaml` foi comparado
   com a branch `-impl` correspondente.
2. Foi conferida a ausência de arquivos de código pertencentes a etapas futuras.
3. `./gradlew test bootJar --console=plain` foi executado no projeto reconstruído.
4. Os resultados XML do Gradle foram conferidos quanto a falhas e testes ignorados.

As branches foram usadas para comparação, não para preencher o código do projeto.
As collections e o verificador opcional Node.js são materiais de apoio da solução
final; sua importação está explicada em 07, sem exigir transcrição de seus exports.

## Resultado por etapa

| Etapa | Arquivos apresentados no capítulo | Arquivos cumulativos comparados | Testes | Resultado |
| --- | --- | --- | --- | --- |
| 01 | 6 | 6 | 1 | Testes e JAR aprovados |
| 02 | 1 | 7 | 1 | Testes e JAR aprovados |
| 03 | 20 | 24 | 1 | Testes e JAR aprovados |
| 04 | 11 | 32 | 1 | Testes e JAR aprovados |
| 05 | 6 | 35 | 9 | Testes e JAR aprovados |
| 06 | 7 | 36 | 9 | Testes e JAR aprovados |
| 07 | 3 | 38 | 20 | Testes e JAR aprovados |

Todos os testes executados terminaram sem falhas, erros ou ignorados.
A contagem por capítulo inclui arquivos substituídos. O conjunto cumulativo não
inclui os três arquivos binários/scripts de bootstrap, os Markdown ou collections.

A compilação usa a toolchain Java 17 do projeto. O Gradle e os testes foram
executados no ambiente local disponível, com PostgreSQL 17 real via Testcontainers
a partir de 03. Não foi utilizado H2.

## Extensão de segurança: reprodução dos módulos 08–09

A mesma extração de blocos foi aplicada em um projeto temporário novo, começando
pelos capítulos 01–07 e continuando pelos arquivos de 08 e 09. A exclusão do teste
Basic, instruída em 09, também foi aplicada. O conteúdo cumulativo foi comparado
com o checkpoint 08 e com a implementação final 09.

- 08: `test bootJar` aprovado, **30 testes**, zero falhas/erros/ignorados.
- 09: `test bootJar` aprovado, **39 testes**, zero falhas/erros/ignorados.
- O perfil de teste tem configuração isolada; a aplicação consulta o administrador
  no banco e cria sua chave local automaticamente, sem credencial padrão.
- As collections foram atualizadas por versão: Basic no checkpoint 08 e JWT no 09.
- A validação final executou **58 requests** e verificou dados e token após reinício.
- A documentação da interface web foi movida para `10-continuacao-web.md`.

## Coerência documental conferida

- Links locais de navegação, arquivos e branches de apoio.
- Arquivos completos e caminhos explícitos em cada etapa.
- Pacotes e imports iguais aos checkpoints de implementação.
- Porta de referência 8000 e instruções para usar outra porta.
- Banco, usuário e senha locais `suggestions`, iguais ao Compose e ao YAML.
- Nenhuma dependência de Lombok nos exemplos.
- Distinção entre banco do Compose e banco temporário de Testcontainers.
- Testes e recursos introduzidos somente na etapa em que existem no gabarito.
- Separação entre repositório documental, projeto do aluno e cópia de consulta.
- Distribuição somente de `README.md` e `docs/` para manter main documental.

## Diagramas Mermaid

A documentação contém **30 diagramas**. A revisão inicial acrescentou nove e refinou o fluxo de erros. A extensão de
segurança acrescentou cinco: acesso público/administrativo, permissões, login,
validação Bearer e ciclo de expiração/reinício. Todos foram analisados e renderizados
com Mermaid 12.2.0 em Chrome headless, sem falhas de sintaxe. Os novos diagramas
e o fluxo de erros também foram conferidos visualmente. A atualização no banco
acrescenta o provisionamento inicial e o DER administrativo, e refina a geração
automática de chave e a validação de conta ativa.

| Cenário acrescentado | Onde estudar |
| --- | --- |
| Material do professor e evolução do projeto do aluno | [Como estudar](como-estudar.md) |
| Inicialização de PostgreSQL, Flyway e Hibernate | [03](03-postgresql-flyway-jpa.md) |
| Sequência de atualização com ID e criação preservados | [04](04-casos-de-uso-crud.md) |
| AND entre filtros e OR entre título e conteúdo | [Contrato](00-modelo-dados.md) |
| Contrato OpenAPI e execução real pelo Swagger | [06](06-openapi-swagger.md) |
| Banco temporário de teste e banco persistente da aplicação | [07](07-testes-entrega.md) |
| Estados da interface após respostas da API | [Continuação web](10-continuacao-web.md) |
| JDK, SDK, JVM do Gradle e toolchain | [Ambiente Windows](ambiente-windows.md) |
| Distribuição somente de Markdown para main e checkpoints | [Guia do professor](guia-professor.md) |

O fluxo de erros de [05](05-validacao-erros.md) agora distingue validação,
ausência, protocolo e falha inesperada. Cada diagrama acompanha uma explicação,
pergunta ou exercício de leitura. A versão do renderizador do GitHub pode
produzir diferenças de posicionamento sem alterar a regra representada.

## Limites da verificação

A reconstrução foi automatizada pela extração dos blocos Markdown. Ela verifica
que os arquivos necessários estão presentes, são cumulativos e compilam/testam,
mas não simula digitação na IDE, geração pelo site Initializr ou configuração de
um computador Windows novo. A preparação Windows continua sujeita à conferência do professor no ambiente
da turma. A renderização dos diagramas foi verificada separadamente, conforme acima.

A interface de importação do Insomnia/Postman não foi automatizada nesta revisão.
A validação da API protegida incluiu 58 requisições das duas collections contra o
JAR e preservação de dados e token após reinício; seu registro está em
`VALIDACAO-API.md` na solução completa. Em aula, execute o fluxo do módulo 07 no
cliente escolhido antes de avançar para Thymeleaf.

A documentação é distribuída para main e branches de estudo por cópia somente
de README.md e docs/. O código dos checkpoints 01–07 permanece preservado.
Os checkpoints 08–09 incluem a atualização administrativa; a publicação deve ser
conferida comparando as referências locais e remotas, sem force push.
