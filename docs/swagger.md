# Swagger/OpenAPI - ForumHub

Com a dependencia `springdoc-openapi-starter-webmvc-ui` adicionada no `pom.xml`, a documentacao da API e gerada automaticamente.

## URLs

- UI do Swagger: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Como testar endpoints protegidos

1. Gere o token em `POST /login`.
2. Copie o `token` retornado no body.
3. Na UI do Swagger, clique em **Authorize**.
4. Informe: `Bearer <seu_token>`.
5. Clique em **Authorize** e depois em **Close**.
6. Execute endpoints como `/topicos`, `/usuario` e `/respostas`.