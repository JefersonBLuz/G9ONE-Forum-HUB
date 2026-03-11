<h1 align="center">💬 ForumHub API</h1>

<p align="center">
  <img src="URL_DA_SUA_IMAGEM_AQUI" alt="Capa do Projeto ForumHub" width="700">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-red" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen" alt="Spring Boot">
  <img src="https://img.shields.io/badge/PostgreSQL-blue" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Auth-JWT-orange" alt="JWT">
  <img src="https://img.shields.io/badge/Migrations-Flyway-red" alt="Flyway">
  <img src="https://img.shields.io/badge/Status-Concluído-success" alt="Status Concluído">
</p>

---

## 📑 Índice

* [📚 Sobre o projeto](#-sobre-o-projeto)
* [🚀 Tecnologias utilizadas](#-tecnologias-utilizadas)
* [🧠 Conceito da aplicação](#-conceito-da-aplicação)
* [📂 Estrutura do projeto](#-estrutura-do-projeto)
* [⚙️ Configuração do ambiente](#️-configuração-do-ambiente)
* [🗄️ Configuração do banco de dados](#️-configuração-do-banco-de-dados)
* [📡 Endpoints principais](#-endpoints-principais)
* [🧪 Testando a API](#-testando-a-api)
* [🗃️ Versionamento do banco](#️-versionamento-do-banco)
* [🔐 Autenticação](#-autenticação)
* [🛡️ Segurança](#️-segurança)
* [📈 Melhorias futuras](#-melhorias-futuras)
* [👨‍💻 Autor](#-autor)
* [📜 Licença](#-licença)

---

## 📚 Sobre o projeto

Um fórum é um espaço onde todos os participantes de uma plataforma podem colocar suas perguntas sobre determinados assuntos. O **ForumHub** é uma API REST desenvolvida em **Java com Spring Boot** que replica esse processo no nível do back-end, simulando o funcionamento de um fórum de discussão.

O projeto foi desenvolvido como parte do **Challenge Back-End do programa Oracle Next Education (ONE) em parceria com a Alura**, com foco em práticas modernas de desenvolvimento. 

A API foca especificamente no gerenciamento de tópicos (CRUD) e implementa:
* Arquitetura em camadas
* Autenticação e autorização com **JWT**
* Persistência em banco de dados relacional (**PostgreSQL**)
* Versionamento de banco com **Flyway**
* Validações rigorosas de regras de negócio
* Endpoints REST seguindo boas práticas

---

## 🚀 Tecnologias utilizadas

| Tecnologia | Descrição |
|---|---|
| **Java 21** | Linguagem principal |
| **Spring Boot 4.0.3** | Framework principal |
| **Spring Security** | Segurança da aplicação |
| **JWT** | Autenticação baseada em token |
| **Spring Data JPA** | ORM |
| **Hibernate** | Implementação JPA |
| **PostgreSQL** | Banco de dados relacional |
| **Flyway** | Versionamento de banco |
| **Maven** | Gerenciamento de dependências |

---

## 🧠 Conceito da aplicação

A API simula o backend de um fórum de estudos onde:
- Usuários podem criar, consultar, atualizar e deletar (CRUD) tópicos.
- Tópicos pertencem a cursos específicos.
- Tópicos possuem respostas relacionadas.
- Usuários possuem perfis distintos.
- O acesso à API é restrito e protegido por autenticação.

---

## 📂 Estrutura do projeto

```bash
forumhub
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.JefersonBLuz.forumhub
│   │   │
│   │   │       ├── controller
│   │   │       │   ├── TopicoController
│   │   │       │   ├── UsuarioController
│   │   │       │   ├── CursoController
│   │   │       │   ├── RespostaController
│   │   │       │   └── AuthController
│   │   │       │
│   │   │       ├── domain
│   │   │       │   ├── model
│   │   │       │   │   ├── Usuario
│   │   │       │   │   ├── Topico
│   │   │       │   │   ├── Curso
│   │   │       │   │   ├── Resposta
│   │   │       │   │   └── Perfil
│   │   │       │   │
│   │   │       │   ├── repository
│   │   │       │   │   ├── UsuarioRepository
│   │   │       │   │   ├── TopicoRepository
│   │   │       │   │   ├── CursoRepository
│   │   │       │   │   └── RespostaRepository
│   │   │       │
│   │   │       ├── dto
│   │   │       │   ├── request
│   │   │       │   └── response
│   │   │       │
│   │   │       ├── security
│   │   │       │   ├── SecurityConfig
│   │   │       │   ├── TokenService
│   │   │       │   └── JwtFilter
│   │   │       │
│   │   │       ├── service
│   │   │       │   └── (regras de negócio)
│   │   │       │
│   │   │       └── ForumHubApplication
│   │   │
│   │   └── resources
│   │       ├── application.properties
│   │       └── db/migration
│   │           └── (scripts Flyway)
│   │
│   └── test
│
├── .gitignore
├── pom.xml
├── mvnw
└── README.md
```

---

## ⚙️ Configuração do ambiente

### Pré-requisitos
Antes de iniciar o projeto, certifique-se de ter instalado:
- Java 21+
- Maven
- PostgreSQL
- Git

---

## 🗄️ Configuração do banco de dados

1. Crie um banco PostgreSQL:
```sql
CREATE DATABASE forumhub;
```

2. 🔑 **Variáveis de ambiente**
Configure as variáveis no seu arquivo `.env` ou nas propriedades do seu sistema:
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=forumhub
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

3. ▶️ **Executando a aplicação**
Clone o projeto:
```bash
git clone <URL_DO_REPOSITORIO>
cd <PASTA_DO_REPOSITORIO>
```

Execute a aplicação via terminal (ou diretamente pelo seu VSCode):
```bash
./mvnw spring-boot:run
```

A documentação/interface do Swagger estará disponível em:
`http://localhost:8080/swagger-ui/index.html`

---

## 🔐 Autenticação

A API utiliza JWT (JSON Web Token) para proteger as rotas.

**Login:**
```http
POST /login
```
Body:
```json
{
 "email": "usuario@email.com",
 "senha": "123456"
}
```
Resposta:
```json
{
 "token": "jwt_token_aqui"
}
```

Para acessar as rotas protegidas, utilize o token gerado no header da requisição:
```http
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 📡 Endpoints principais

### 👤 Usuários
| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/usuario` | Lista usuários |
| `GET` | `/usuario/{id}` | Detalha usuário por ID |
| `POST` | `/usuario` | Cria usuário |
| `PUT` | `/usuario/{id}` | Atualiza usuário |
| `DELETE` | `/usuario/{id}` | Remove usuário |

### 📚 Cursos
| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/curso` | Lista cursos |
| `GET` | `/curso/{id}` | Detalha curso por ID |
| `POST` | `/curso` | Cria curso |
| `PUT` | `/curso/{id}` | Atualiza curso |
| `DELETE` | `/curso/{id}` | Remove curso |

### 🧵 Tópicos
| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/topicos` | Lista tópicos (paginado) |
| `GET` | `/topicos/{id}` | Detalha tópico |
| `POST` | `/topicos` | Cria tópico |
| `PUT` | `/topicos/{id}` | Atualiza tópico |
| `DELETE` | `/topicos/{id}` | Remove tópico |

### 💬 Respostas
| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/respostas` | Lista respostas |
| `GET` | `/respostas/{id}` | Detalha resposta por ID |
| `POST` | `/respostas` | Cria resposta |
| `PUT` | `/respostas/{id}` | Atualiza resposta |
| `DELETE` | `/respostas/{id}` | Remove resposta |

---

## 🧪 Testando a API

Você pode testar a API utilizando ferramentas como **Postman**, **Insomnia**, **Thunder Client** ou via linha de comando com **cURL**.

Exemplo de requisição para listar tópicos:
```bash
curl -X GET "http://localhost:8080/topicos" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

## 🗃️ Versionamento do banco

O projeto utiliza **Flyway** para o controle e versionamento automatizado das migrações do banco de dados.
Os scripts estão localizados em:
```bash
src/main/resources/db/migration
```
A nomenclatura dos arquivos segue o padrão:
```text
V1__create_tables.sql
V2__add_relations.sql
```

---

## 🛡️ Segurança

A segurança da aplicação foi desenvolvida focando na proteção dos dados e endpoints restritos utilizando:
- **Spring Security**
- **Autenticação JWT (JSON Web Tokens)**
- Filtros de autenticação para interceptação de rotas
- Validação de rotas protegidas garantindo acesso exclusivo a usuários autenticados

**Fluxo de segurança:**
1. O usuário realiza o Login.
2. A API valida as credenciais e gera o token JWT.
3. O cliente envia o token no header (`Authorization: Bearer TOKEN`) nas próximas requisições.
4. O Spring Security intercepta, valida o token e libera o acesso à rota solicitada.

---

## 📈 Melhorias futuras

- Documentação detalhada e interativa com Swagger / OpenAPI
- Implementação de cache de consultas utilizando Redis
- Dockerização do ambiente (Dockerfile e docker-compose)
- Criação de testes automatizados (unitários e de integração)

---

## 👨‍💻 Autor

Desenvolvido por **Jeferson Braga Luz**.

[![GitHub](https://img.shields.io/badge/GitHub-JefersonBLuz-181717?style=flat-square&logo=github)](https://github.com/JefersonBLuz) 
[![Website](https://img.shields.io/badge/Website-jefersonbraga.cloud-0052CC?style=flat-square&logo=google-chrome&logoColor=white)](https://jefersonbraga.cloud)
[![WhatsApp](https://img.shields.io/badge/WhatsApp-Contato-25D366?style=flat-square&logo=whatsapp&logoColor=white)](https://wa.me/557196585476)

---

## 📜 Licença

Este projeto foi desenvolvido para fins educacionais dentro do programa **Oracle Next Education (ONE)** em parceria com a **Alura**.