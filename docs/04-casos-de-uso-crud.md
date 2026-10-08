# 04 — Dar vida à caixa

[Roteiro](../README.md) · [Contrato](00-modelo-dados.md)

## Missão

Implementar o ciclo completo de sugestões e seus filtros.

## Pré-requisitos

Módulo 03-postgresql-flyway-jpa concluído no seu próprio projeto. Você continuará o seu código; a branch de exercício não fornece a solução anterior.

## Passos

1. Crie as interfaces de persistência no domínio e os adapters JPA em infrastructure.persistence. O domínio não importa HTTP nem JPA. Use um serviço de aplicação, sem criar uma classe por operação sem necessidade.
2. Defina DTO de entrada com title, content, author, courseId e categoryId, e saída conforme contrato. Faça mapeamento manual. Nunca retorne entidade JPA pelo controller.
3. Implemente criação: consultar curso/categoria, normalizar author, gerar Instant no servidor e salvar. Retorne 201 e Location. IDs dos catálogos vêm da consulta, não de uma suposição sobre seu valor.
4. Substitua o GET demonstrativo pela consulta real. Combine filtros por AND e título/conteúdo por OR; faça busca literal case-insensitive, escapando curingas SQL. Retorne lista simples ordenada e `[]` quando vazia.
5. Implemente detalhe, PUT com substituição de campos editáveis e DELETE físico. Preserve id e data no PUT e devolva 404 para sugestão inexistente.
6. Delimite transações no serviço: escrita para mutações e readOnly para consultas. Evite consultas adicionais por item ao carregar curso e categoria, usando entity graph ou fetch explícito.
7. Execute o ciclo criar → consultar → filtrar → atualizar → excluir no Insomnia. Reinicie a aplicação depois de criar e comprove persistência.

**Desafio:** envie author ausente e depois somente espaços; compare com uma sugestão identificada. Teste q contendo `%` e `_`.

**Pronto quando:** todos os endpoints funcionam, lista não é paginada, data é preservada no PUT e os testes de serviço verificam o ciclo. O formato uniforme de erros será concluído no próximo módulo.

## Registro da etapa

Execute os comandos na raiz do seu projeto. Faça um commit explicando o resultado da missão. Consulte a branch `04-casos-de-uso-crud-impl` em uma cópia separada somente depois de tentar; ela contém a solução até esta etapa, não a API futura.

[Próximo módulo](05-validacao-erros.md)
