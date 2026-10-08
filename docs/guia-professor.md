# Guia do professor

## Organização

main e branches de exercício contêm apenas material didático. Os alunos criam projetos separados e evoluem seu código. Todas as branches de exercício partem da base documental; nenhuma deriva de uma solução. Os gabaritos -impl são cumulativos, com commits por módulo e sem recursos de capítulos futuros.

Os capítulos apresentam decisões, contratos e passos de construção, não arquivos completos da solução. Use dúvidas da turma para discutir responsabilidades antes de revelar o gabarito. Ao atualizar um capítulo, sincronize a correção documental nas branches correspondentes.

## Checkpoints

Nas etapas 01–02, teste e execução não dependem de banco. A partir de 03, Docker é requisito dos testes de integração; PostgreSQL local ou Compose é requisito para bootRun. A etapa 03 mantém apenas GET demonstrativo de sugestões e os catálogos reais; CRUD entra em 04, erros uniformes em 05 e Swagger em 06.

Use `git log --oneline NOME-DA-BRANCH-impl` para localizar os commits. Consulte soluções em worktrees ou clones separados para não afetar projetos dos alunos.

## Histórico preservado

As referências archive/main-antes-modulos-20261008, archive/feature-api-antes-modulos-20261008 e archive/tutorial-antes-modulos-20261008 preservam os commits anteriores. As duas antigas branches permanecem como legado e não fazem parte da trilha. O rascunho local anterior foi arquivado sem alteração em docs/arquivo/rascunho-tutorial-original.md; seus links e requisitos antigos não se aplicam.

## Publicação

A implementação desta trilha cria branches locais. Revise antes de publicar main e os pares de módulos. Não é necessário force push, pois a main avança por commits normais. Branches de arquivo não precisam ser publicadas.
