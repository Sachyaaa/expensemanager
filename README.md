# 💸 Expense Manager – Spring Boot Backend

A **production-grade Expense Management backend** built using **Spring Boot**, following **clean architecture, security best practices, Docker, CI/CD, and cloud deployment**.

This project demonstrates **real-world backend engineering skills**, not just CRUD APIs.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Role-based access (`USER`, `ADMIN`)
- Secure endpoints using Spring Security
- Public login and register APIs

### 💰 Expense Management
- Create, update, delete expenses
- User ownership rules (users see only their data)
- Pagination and sorting
- Optimized queries (no N+1 issues)

### 📊 Summary APIs
- Monthly expense summary
- Category-wise expense summary
- Combined summary endpoints

### ⚙️ Engineering Best Practices
- DTO-based API responses
- Centralized exception handling
- Logging with MDC and request tracing
- Environment-based configuration

---

## 🧱 Tech Stack

| Layer | Technology |
|------|-----------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA + Hibernate |
| Build Tool | Maven |
| API Docs | Swagger / OpenAPI |
| Containerization | Docker |
| CI | GitHub Actions |
| Cloud | Render |
| DB Client | pgAdmin |

---

## 🗂️ Project Structure (High Level)

```
src/main/java/com/sachin/expensemanager
├── controller
├── service
├── repository
├── security
├── dto
├── model
└── config
```

---

## 🔑 Environment Profiles

| Profile | Purpose |
|--------|--------|
| `dev` | Local development |
| `test` | Automated testing |
| `prod` | Docker / Cloud deployment |

Profiles are activated using **environment variables or JVM arguments**  
and are **not hardcoded**.

---

## 🖥️ Run Locally (Development)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Ensure PostgreSQL is running locally and credentials match `application-dev.yml`.

---

## 🐳 Run with Docker (Local Production-like Setup)

```bash
docker compose up --build
```

**Services**
- Application: http://localhost:8080
- PostgreSQL: localhost:5433

Environment variables are injected via `docker-compose.yml`.

---

## ☁️ Cloud Deployment (Render)

### Environment Variables

```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/expense_manager
SPRING_DATASOURCE_USERNAME=expense_user
SPRING_DATASOURCE_PASSWORD=********
JWT_SECRET=********
```

- Docker-based deployment
- Managed PostgreSQL on Render
- Config injected via environment variables

---

## 🔄 CI/CD Pipeline

- GitHub Actions pipeline
- Runs on every push and pull request
- Maven build using Java 21
- Docker image build validation

Workflow file:
```
.github/workflows/ci.yml
```

---

## 🧪 Testing Strategy

- Unit tests for service layer
- Integration tests using Spring Security and JWT
- Test profile uses H2 in-memory database

> Some integration tests are temporarily disabled and planned to be fixed later.

---

## 🔐 Security Notes

- Stateless JWT authentication
- Roles mapped to `ROLE_USER` and `ROLE_ADMIN`
- Public auth APIs, secured business APIs
- CSRF disabled (REST + JWT)

---

## 📖 API Documentation

Swagger UI available at:

```
/swagger-ui.html
```

---

## 👨‍💻 Author

**Sachin Divase**  
Java Backend Developer  
Focused on **Spring Boot, System Design, and Production-grade Backend Engineering**
