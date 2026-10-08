# Registro de validação da trilha

Validação local realizada em 8 de outubro de 2026 com Java 17, Gradle 9.7.1,
Spring Boot 4.1.1, PostgreSQL 17 em Docker e Springdoc 3.1.1.

## Checkpoints de implementação

| Módulo | Verificação | Resultado |
| --- | --- | --- |
| 01 | Contexto Spring sem banco | Aprovado |
| 02 | Contexto e GET demonstrativo com MockMvc | Aprovado |
| 03 | PostgreSQL, migrations, contexto e catálogos | Aprovado |
| 04 | Serviços e controllers do CRUD | Aprovado |
| 05 | Validação, erros de entrada e falhas inesperadas | Aprovado |
| 06 | Swagger, OpenAPI e tipos de conteúdo JSON | Aprovado |
| 07 | 25 testes, incluindo CRUD HTTP e consultas reais em PostgreSQL | Aprovado |

O comando final foi `./gradlew test bootJar`. Nenhum teste foi ignorado.
Os testes de integração usaram Testcontainers com banco isolado.

## Verificação do JAR

- PostgreSQL criado por Docker Compose em instância isolada.
- Swagger acessível e título institucional conferido no OpenAPI.
- Catálogos com quatro cursos e três categorias.
- POST retornando 201, Location e autor Anônimo quando omitido.
- Request inválido retornando 400 com erro padronizado.
- Processo Java encerrado e iniciado novamente: a sugestão persistiu.
- PUT preservando criação, filtro encontrando a alteração, DELETE retornando 204 e consulta posterior retornando 404.
- Reinício sem duplicar seeds.

## Material didático

Os 14 diagramas Mermaid originais foram renderizados com Mermaid CLI e Chrome headless:
entidade-relacionamento, classes, estados, sequência e fluxos dos módulos.
A verificação garante sintaxe renderizável; o renderizador do GitHub pode variar
levemente no posicionamento dos elementos.

Após a revisão dos estados, o material passou a ter 15 diagramas: consulta e
cadastro da interface agora têm desenhos separados. Os cinco diagramas de estado
revisados foram renderizados com Mermaid CLI e Chrome headless e inspecionados
visualmente para conferir rótulos, setas e legibilidade.

Importação visual da coleção no aplicativo Insomnia não foi automatizada. O arquivo
usa o formato de exportação v4; sua estrutura, referências de ambiente e payloads
foram conferidos, e as operações equivalentes foram exercitadas nos testes HTTP.

## Como repetir

Consulte a branch `07-testes-entrega-impl` em uma cópia separada, leia SOLUCAO.md,
inicie Docker e execute `./gradlew test bootJar`. As branches de exercício e a main
não contêm aplicação para executar. As branches permanecem locais até publicação
pelo professor.
