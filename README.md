# JwtProject — API REST de Autenticação com Spring Boot

API REST para autenticação e autorização de usuários com JWT, construída com Spring Boot e PostgreSQL. O projeto inclui endpoints de registro e login, proteção de rotas via Spring Security e interface web com Thymeleaf.

---

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **JWT (JSON Web Token)**
- **Thymeleaf**
- **Lombok**
- **Maven**

---

## Estrutura do Projeto

```
src/
└── main/
    └── java/com/pedro/auth/
        ├── config/         # SecurityConfig, JwtAuthFilter
        ├── controller/     # AuthController, DashboardController
        ├── dto/            # LoginRequestDTO, RegisterRequestDTO
        ├── model/          # User
        ├── repository/     # UserRepository
        └── service/        # JwtService, UserService
    └── resources/
        ├── templates/      # login.html, register.html, dashboard.html
        └── application.properties
```

---

## Configuração e Execução Local

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL rodando localmente

### 1. Clone o repositório

```bash
git clone https://github.com/PedroH05/JwtProject.git
cd JwtProject
```

### 2. Configure o banco de dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE auth_db;
```

### 3. Configure as variáveis de ambiente

Crie as seguintes variáveis de ambiente no seu sistema ou configure diretamente no `application.properties` para uso local:

| Variável | Descrição | Exemplo |
|---|---|---|
| `DATABASE_URL` | URL JDBC do banco | `jdbc:postgresql://localhost:5432/auth_db` |
| `DATABASE_USERNAME` | Usuário do banco | `postgres` |
| `DATABASE_PASSWORD` | Senha do banco | `postgres` |
| `JWT_SECRET` | Chave secreta para assinar o token | `minhachavesecreta123` |
| `PORT` | Porta da aplicação (opcional) | `8080` |

### 4. Execute o projeto

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## Endpoints

### Autenticação (públicos)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/auth/login` | Página de login |
| `GET` | `/auth/register` | Página de registro |
| `POST` | `/auth/login` | Realiza login e retorna token JWT |
| `POST` | `/auth/register` | Cadastra novo usuário e retorna token JWT |

### Área protegida

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/dashboard` | Painel do usuário autenticado |

---

## Fluxo de Autenticação

1. O usuário se registra via `POST /auth/register` com nome, e-mail e senha
2. A senha é armazenada com hash **BCrypt**
3. Ao fazer login via `POST /auth/login`, a API retorna um **token JWT**
4. O token é armazenado no `localStorage` do browser
5. Rotas protegidas exigem o header `Authorization: Bearer <token>`
6. O token expira em **24 horas**

---

## Exemplos de Requisição

### Registro

```http
POST /auth/register
Content-Type: application/json

{
  "name": "Pedro",
  "email": "pedro@email.com",
  "password": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Login

```http
POST /auth/login
Content-Type: application/json

{
  "email": "pedro@email.com",
  "password": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Deploy

O projeto está hospedado na **Railway** com banco de dados PostgreSQL gerenciado.

- **URL de produção:** `https://jwtproject-production.up.railway.app`

As variáveis de ambiente são configuradas diretamente no painel do Railway, sem necessidade de arquivo `.env` em produção.

---

## Autor

**Pedro Henrique**  
[GitHub](https://github.com/PedroH05) • [LinkedIn](www.linkedin.com/in/pedro-henrique-carvalho-a1345a2b7)
