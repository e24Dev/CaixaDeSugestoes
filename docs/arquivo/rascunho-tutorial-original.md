# Missão: dar voz à escola 🗣️

**Laboratório de desenvolvimento web — Etec Alberto Feres**

Imagine uma caixa de sugestões que nunca fica perdida no corredor. Cada ideia recebe um número, pode ser consultada e continua guardada quando o computador reinicia. Nossa missão é construir o motor dessa caixa: uma API em Java.

A primeira temporada termina com uma API funcionando e testada. Na segunda, construiremos uma página web com Spring MVC e Thymeleaf para consumir essa API. **Ainda não implementaremos a página nesta trilha.**

## Como usar este material

Você precisa conhecer variáveis, métodos, classes e o básico de Java. Interfaces, records, JSON, HTTP e anotações serão explicados conforme aparecerem. Trabalhe em dupla quando possível: uma pessoa digita, outra confere e explica; troquem de papel a cada checkpoint.

Cada parte tem uma missão, uma analogia, passos de construção, um checkpoint e uma investigação. Os checkpoints não são uma corrida: se algo divergir, consulte o guia de resgate antes de avançar. Anote o erro completo, o comando utilizado e sua hipótese sobre a causa.

**Convenção:** todo bloco identificado por `Arquivo: caminho` contém o arquivo inteiro. Crie ou substitua somente esse arquivo no seu projeto de aluno. Preserve o `package`, os imports e o nome da classe. Blocos de terminal, exemplos JSON e exercícios não são arquivos Java. Caminhos são relativos à pasta que contém `build.gradle`.

## Mapa da aventura

| Parte | Missão | Você conquista |
| --- | --- | --- |
| [00 — Preparar a mochila](00-ambiente.md) | Conferir ferramentas e abrir um projeto inicial vazio | Um ambiente que compila |
| [01 — Ligar o motor](01-primeiro-boot.md) | Entender projeto, Gradle e servidor HTTP | Uma aplicação Spring Boot iniciando |
| [02 — Construir o arquivo da escola](02-banco-migrations.md) | PostgreSQL, configuração e Flyway | Tabelas e catálogos reproduzíveis |
| [03 — Criar o vocabulário](03-dominio.md) | Modelos e contratos de persistência | Domínio sem dependência de HTTP/JPA |
| [04 — Ensinar Java a guardar dados](04-persistencia.md) | ORM, repositories e adapters | Persistência e busca paginada |
| [05 — Definir as regras da caixa](05-aplicacao.md) | Casos de uso, validação e transações | Criação, consulta, alteração e exclusão |
| [06 — Abrir o balcão HTTP](06-api-rest.md) | DTOs, controllers e erros | API pública com respostas previsíveis |
| [07 — Conversar com a API](07-swagger-laboratorio.md) | Swagger e experimentos de CRUD | Evidências reais do contrato |
| [08 — Montar a equipe de detetives](08-testes.md) | JUnit, Mockito, MockMvc e PostgreSQL isolado | Uma suíte de testes automatizados |
| [09 — Entregar e guardar a chave](09-entrega.md) | JAR, reinício e desafio final | API pronta para receber um cliente web |
| [10 — Próxima temporada: Thymeleaf](10-proxima-temporada.md) | Entender a futura página consumidora | Um roteiro de continuação, sem implementação |

Materiais de apoio: [guia de resgate](resgate.md), [glossário](glossario.md) e [guia do professor](guia-professor.md).

## Combinados do laboratório

- Construa em uma **pasta nova**. O repositório do professor contém a solução; não apague o código dele para simular um projeto vazio.
- Use as versões do kit e dos arquivos apresentados. Isso evita que uma atualização de ferramenta mude o exercício no meio da turma.
- Execute os comandos na raiz do seu projeto. Não dentro de `src`.
- Guarde os IDs retornados pela API. Um banco já utilizado pode não começar pelo ID 1.
- Pare o servidor com `Ctrl+C` antes de iniciá-lo novamente. Uma porta só atende um servidor de cada vez.
- Digite e explique o código. Copiar ajuda a eliminar erros de transcrição, mas não substitui entender sua responsabilidade.

## Por que aparecem cursos da Fatec no banco?

A turma destinatária é a **Etec Alberto Feres**. O estudo de caso e os dados herdados desta API são da **Caixa de Sugestões Fatec Araras**. Por isso, Swagger e catálogos continuam com os nomes do projeto de origem. Não são uma lista oficial dos cursos da Etec. Mantemos esse conjunto para que o tutorial produza o mesmo resultado da implementação validada. Uma futura adaptação do catálogo deve ser combinada com o professor e feita por uma migration nova, sem editar uma migration já aplicada.

## Seu diário de bordo

Para cada parte registre: o que mudou, o comando do checkpoint, o resultado e uma explicação com suas palavras. Na entrega, apresente uma sugestão anônima, uma identificada, um erro esperado e a prova de que o registro sobrevive a um reinício. Use somente dados fictícios nas atividades.
