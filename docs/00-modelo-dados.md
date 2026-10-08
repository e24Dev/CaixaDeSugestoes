# 00 — Modelo de dados e contrato

## Missão

Definir o vocabulário antes de escrever código. O estudo de caso é a Caixa de Sugestões Fatec Araras; os cursos abaixo são os dados didáticos solicitados, não uma certificação do catálogo institucional atual.

## Pré-requisitos

Conhecer classes, métodos e tipos Java. Leia o README e prepare o ambiente.

## Passos

1. Modele `Suggestion` com `id: Long`, `title: String`, `content: String`, `author: String` opcional e `createdAt: Instant`, além de um `Course` e uma `Category` obrigatórios.
2. Modele `Course` e `Category` com `id: Long` e `name: String`. Cada catálogo participa de muitas sugestões. Uma sugestão pertence a exatamente um curso e uma categoria. Não exclua catálogos em cascata ao excluir sugestões.
3. IDs são positivos e gerados pelo banco. Data é definida pelo servidor em UTC e não muda no PUT. Autor nulo, vazio ou só com espaços é armazenado como ausente e aparece como `Anônimo` na resposta.
4. Título e conteúdo não aceitam texto vazio ou somente espaços. Não impor limites de negócio adicionais nesta versão; usar colunas text no PostgreSQL. Nomes dos catálogos são obrigatórios e únicos.
5. Defina o banco com tabelas `courses`, `categories`, `suggestions` e chaves estrangeiras `course_id` e `category_id`. Use `timestamptz` para criação.

Seeds de cursos: Desenvolvimento de Software Multiplataforma; Gestão Empresarial; Análise e Desenvolvimento de Sistemas; Administração de Empresas. Seeds de categorias: Sugestão; Crítica; Elogio.

## Contrato HTTP final

| Método e rota | Resultado |
| --- | --- |
| POST `/api/suggestions` | 201, recurso criado e header Location |
| GET `/api/suggestions` | 200, array simples, inclusive `[]` |
| GET `/api/suggestions/{id}` | 200 ou 404 |
| PUT `/api/suggestions/{id}` | 200 com recurso atualizado ou 404 |
| DELETE `/api/suggestions/{id}` | 204 após exclusão física ou 404 |
| GET `/api/courses` | 200, array com id e name |
| GET `/api/categories` | 200, array com id e name |

Criação e atualização recebem os mesmos campos editáveis:

```json
{"title":"Mais tomadas na biblioteca","content":"Instalar tomadas nas mesas de estudo.","author":"Pessoa Exemplo","courseId":1,"categoryId":1}
```

A resposta contém `id`, `title`, `content`, `author`, `createdAt`, `course: {id, name}` e `category: {id, name}`. O cliente não define id nem data. PUT substitui todos os campos editáveis; omitir author remove a identificação.

Filtros opcionais: `courseId`, `categoryId` e `q`. Combine-os por AND; `q` procura um trecho literal do título OU conteúdo, sem distinguir maiúsculas. Remova espaços nas extremidades de q e ignore termo vazio. `%` e `_` devem ser buscados como caracteres literais. Filtro por ID positivo sem registros retorna `[]`. IDs não positivos ou malformados retornam 400.

Sugestões: criação decrescente, depois id decrescente. Catálogos: nome crescente. Não há `page`, `size`, status ou autenticação.

Erros usam `timestamp`, `status`, `message`, `path` e `fieldErrors` (mapa campo → mensagem). Dados inválidos ou JSON malformado: 400; sugestão ou catálogo referenciado inexistente: 404; falha inesperada: 500 com mensagem genérica, sem detalhes internos.

## Desafio

Explique por que autor não é uma entidade Usuario, por que o cliente não escolhe a data e por que um catálogo deve ser selecionado pelo ID retornado pela API.

## Pronto quando

- Você consegue desenhar as duas relações muitos-para-um.
- Explica a diferença entre corpo JSON, parâmetro de rota e filtro.
- Sabe quais chamadas retornarão array, objeto ou corpo vazio.
