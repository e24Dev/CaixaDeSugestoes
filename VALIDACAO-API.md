# Validação da API — 29/10/2026

Este registro se refere ao código entregue em `07-testes-entrega-impl`.
A reprodução dos tutoriais foi validada em uma etapa posterior e está
registrada separadamente em [docs/validacao.md](docs/validacao.md).

- `./gradlew test bootJar`: aprovado, 20 testes, zero falhas e zero ignorados.
- Compilação com toolchain Java 17; execução do JAR com Java 21.0.12.
- Spring Boot 4.1.1, Gradle 9.7.1, Springdoc 3.1.1 e PostgreSQL 17.
- Testes Mockito de serviço, MockMvc de controller e integração com PostgreSQL
  real via Testcontainers, incluindo migrations, sete seeds, CRUD, anonimato,
  filtros isolados/combinados, caracteres literais, ordenação, texto longo,
  preservação da criação e rejeição de atualizações inválidas.
- Swagger redireciona corretamente e OpenAPI contém as sete operações.
- JAR iniciado com PostgreSQL do Compose em `localhost:8000`.
- `scripts/verify-collections.mjs`: 23 requisições Postman e 23 Insomnia aprovadas
  contra o JAR, incluindo execução dos scripts exportados com uma camada local
  de compatibilidade dos métodos de teste e variáveis usados pelas collections.
  Não substitui a validação da importação na interface dos aplicativos, que não
  foi automatizada.
- Persistência após reinício do JAR conferida via HTTP: mesmo objeto, ID e data;
  catálogos continuaram com quatro cursos e três categorias. Registro temporário
  removido ao final, preservando os catálogos.

Para repetir as collections com Node.js 18 ou superior, mantenha a API iniciada:

```bash
node scripts/verify-collections.mjs
```

O argumento opcional permite mudar a URL base. O script cria e exclui somente
os registros dos seus próprios fluxos de verificação.
