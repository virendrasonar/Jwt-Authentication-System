<div align="center">

# JWT Authentication System

### Secure Spring Boot Authentication Backend with JWT, Refresh Tokens, RBAC, and Admin User Management

![Project Banner](docs/images/banner-placeholder.png)

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=for-the-badge&logo=jsonwebtokens)](https://jwt.io/)
[![MySQL](https://img.shields.io/badge/MySQL-Local-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Production-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Railway](https://img.shields.io/badge/Railway-Deployment-0B0D0E?style=for-the-badge&logo=railway&logoColor=white)](https://railway.app/)

</div>

## 📌 Project Overview

JWT Authentication System is a secure backend authentication service built with Java 21, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, MySQL, PostgreSQL, Maven, and Railway. It provides a complete authentication workflow for user registration, login, JWT-based authorization, refresh tokens, logout, password reset, protected profile access, and admin-only user management.

The application follows a layered Controller -> Service -> Repository -> Database architecture and secures REST APIs through a stateless Spring Security filter chain. It is designed as a production-quality portfolio project that demonstrates clean backend engineering, secure API design, validation, centralized exception handling, and deployment-ready configuration.

## 🔗 Live Demo

| Resource | Link |
| --- | --- |
| Live Application | [https://jwt-authentication-system-production.up.railway.app/login.html](https://jwt-authentication-system-production.up.railway.app/login.html) |
| Register Page | [https://jwt-authentication-system-production.up.railway.app/signup.html](https://jwt-authentication-system-production.up.railway.app/signup.html) |

## 📦 Repository

```text
https://github.com/your-username/jwt-authentication-system
```

## 📚 Table of Contents

- [Project Overview](#-project-overview)
- [Live Demo](#-live-demo)
- [Repository](#-repository)
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Project Statistics](#-project-statistics)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [API Modules](#-api-modules)
- [Authentication Flow](#-authentication-flow)
- [Security Features](#-security-features)
- [Database Design](#-database-design)
- [Installation & Setup](#-installation--setup)
- [Configuration](#-configuration)
- [Running the Project](#-running-the-project)
- [API Endpoints](#-api-endpoints)
- [Sample Request & Response JSON](#-sample-request--response-json)
- [JWT Authentication Example](#-jwt-authentication-example)
- [Role-Based Access Control](#-role-based-access-control)
- [Exception Handling](#-exception-handling)
- [Validation](#-validation)
- [Deployment on Railway](#-deployment-on-railway)
- [Screenshots](#-screenshots)
- [Design Principles](#-design-principles)
- [Key Learning Outcomes](#-key-learning-outcomes)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

## ✨ Features

| Category | Description |
| --- | --- |
| User Registration | Creates new users with validated name, email, and password fields. |
| User Login | Authenticates users and returns an access token, refresh token, and user profile data. |
| JWT Authentication | Secures protected APIs using bearer tokens validated by a custom Spring Security filter. |
| Refresh Tokens | Persists refresh tokens in the database to issue new access tokens without re-login. |
| Logout | Revokes refresh tokens to invalidate active refresh sessions. |
| Password Reset | Supports forgot-password and reset-password workflows using database-backed reset tokens. |
| Role-Based Authorization | Restricts admin APIs to users with the `ADMIN` role. |
| Admin User Management | Allows admins to list, create, update, and delete users with safety checks. |
| Centralized Error Handling | Returns consistent validation and runtime error responses. |
| Railway Deployment | Supports production deployment with PostgreSQL and environment variables. |
| Static UI Pages | Includes login, signup, dashboard, forgot-password, and reset-password pages. |

## 🧰 Technologies Used

| Layer | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT, BCrypt |
| Persistence | Spring Data JPA, Hibernate |
| Local Database | MySQL |
| Production Database | PostgreSQL |
| Build Tool | Maven |
| Validation | Jakarta Bean Validation |
| Deployment | Railway |
| Testing | Spring Boot Test, Spring Security Test, H2 |

## 📊 Project Statistics

| Metric | Value |
| --- | ---: |
| REST APIs | 15 |
| Controllers | 5 |
| Services | 3 |
| Entities | 3 |
| Repositories | 3 |
| Database Tables | 3 |
| Security | Spring Security + JWT |
| Authentication | JWT + Refresh Tokens |
| Authorization | RBAC |
| Deployment Target | Railway |

## 🏗️ Architecture

The project uses a clean layered architecture where each layer has a clear responsibility. Controllers expose REST endpoints, services contain business logic, repositories handle persistence, and the database stores users and token records.

```mermaid
graph TD
    Client["Client / Browser / Postman"] --> Security["Spring Security Filter Chain"]
    Security --> Controller["Controller Layer"]
    Controller --> Service["Service Layer"]
    Service --> Repository["Repository Layer"]
    Repository --> Database[("MySQL / PostgreSQL")]
    Service --> JwtService["JWT Service"]
    Service --> PasswordEncoder["BCrypt Password Encoder"]
```

| Layer | Responsibility |
| --- | --- |
| Client | Sends HTTP requests through browser pages, Postman, or another REST client. |
| Security Filter Chain | Validates JWT tokens and applies route-level authorization rules. |
| Controller | Handles REST endpoints and maps request/response DTOs. |
| Service | Implements authentication, refresh token, password reset, and business rules. |
| Repository | Uses Spring Data JPA to access relational database tables. |
| Database | Stores users, refresh tokens, and password reset tokens. |

## 📁 Project Structure

```text
JWT Authentication System
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/auth/authproject
│   │   │       ├── config
│   │   │       │   ├── AdminBootstrapConfig.java
│   │   │       │   ├── CorsConfig.java
│   │   │       │   ├── ProductionDataSourceConfig.java
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controller
│   │   │       │   ├── AdminUserController.java
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── DashboardController.java
│   │   │       │   ├── HomeController.java
│   │   │       │   └── TestController.java
│   │   │       ├── dto
│   │   │       ├── entity
│   │   │       │   ├── PasswordResetToken.java
│   │   │       │   ├── RefreshToken.java
│   │   │       │   └── User.java
│   │   │       ├── exception
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── repository
│   │   │       │   ├── PasswordResetTokenRepository.java
│   │   │       │   ├── RefreshTokenRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── security
│   │   │       │   ├── JwtFilter.java
│   │   │       │   └── JwtService.java
│   │   │       ├── service
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── PasswordResetService.java
│   │   │       │   └── RefreshTokenService.java
│   │   │       └── AuthprojectApplication.java
│   │   └── resources
│   │       ├── static
│   │       │   ├── login.html
│   │       │   ├── signup.html
│   │       │   ├── dashboard.html
│   │       │   ├── forgot-password.html
│   │       │   └── reset-password.html
│   │       ├── application.properties
│   │       ├── application-local.properties
│   │       └── application-prod.properties
│   └── test
├── docs
│   └── API.md
├── Dockerfile
├── pom.xml
└── README.md
```

## 🧩 API Modules

| Module | Description |
| --- | --- |
| Authentication Module | Handles registration, login, token refresh, logout, and admin login defaults. |
| Password Reset Module | Generates reset tokens and updates passwords through validated requests. |
| Dashboard Module | Returns authenticated user dashboard/profile data. |
| Admin User Module | Provides admin-only APIs for user listing, lookup, creation, update, and deletion. |
| Security Module | Validates JWTs and enforces Spring Security authorization rules. |

## 🔐 Authentication Flow

```mermaid
sequenceDiagram
    participant User
    participant Server
    participant Security as Spring Security
    participant Database

    User->>Server: POST /auth/login
    Server->>Database: Load user by email
    Database-->>Server: User record
    Server->>Server: Verify BCrypt password
    Server->>Database: Save refresh token
    Server-->>User: JWT access token + refresh token
    User->>Security: API request with Authorization header
    Security->>Security: Validate JWT signature and claims
    Security->>Server: Allow protected request
    Server-->>User: Protected resource
```

## 🛡️ Security Features

| Security Area | Implementation |
| --- | --- |
| JWT Authentication | Access tokens are sent as bearer tokens in the `Authorization` header. |
| Refresh Tokens | Refresh tokens are stored in the database and used to request new access tokens. |
| BCrypt Password Hashing | Passwords are encoded using BCrypt through Spring Security's `PasswordEncoder`. |
| Role-Based Access Control | `/admin/**` endpoints require the `ADMIN` role. |
| Stateless Authentication | Server sessions are disabled with `SessionCreationPolicy.STATELESS`. |
| Spring Security Filter Chain | A custom `JwtFilter` runs before `UsernamePasswordAuthenticationFilter`. |
| Protected Routes | Public routes are explicitly permitted; all other routes require authentication. |
| CSRF Configuration | CSRF is disabled for stateless REST API usage. |
| CORS Configuration | Allowed origins are controlled through `CORS_ALLOWED_ORIGINS`. |

### Protected Route Strategy

| Route Group | Access Level |
| --- | --- |
| `/auth/**` | Public |
| Static pages and assets | Public |
| `/dashboard` | Authenticated users |
| `/test/secure` | Authenticated users |
| `/admin/**` | Admin users only |
| Any other route | Authenticated users |

## 🗄️ Database Design

```mermaid
erDiagram
    USERS ||--o{ REFRESH_TOKENS : owns
    USERS ||--o{ PASSWORD_RESET_TOKENS : owns

    USERS {
        bigint id PK
        varchar name
        varchar email UK
        varchar password
        varchar role
        timestamp created_at
        timestamp updated_at
    }

    REFRESH_TOKENS {
        bigint id PK
        varchar token UK
        timestamp expiry_date
        bigint user_id FK
    }

    PASSWORD_RESET_TOKENS {
        bigint id PK
        varchar token UK
        timestamp expiry_date
        boolean used
        timestamp used_at
        bigint user_id FK
    }
```

| Table | Purpose |
| --- | --- |
| `users` | Stores registered users, encrypted passwords, roles, and audit timestamps. |
| `refresh_tokens` | Stores refresh tokens associated with users. |
| `password_reset_tokens` | Stores reset tokens, expiry metadata, usage status, and owning users. |

## ⚙️ Installation & Setup

### Prerequisites

| Tool | Recommended Version |
| --- | --- |
| Java | 21 |
| Maven | 3.9+ |
| MySQL | 8.x |
| Git | Latest |

### Clone the Repository

```bash
git clone https://github.com/your-username/jwt-authentication-system.git
cd jwt-authentication-system
```

### Create Local MySQL Database

```sql
CREATE DATABASE jwt_auth_db;
```

### Build the Application

```bash
./mvnw clean install
```

On Windows:

```bash
mvnw.cmd clean install
```

## 🔧 Configuration

The application uses profile-based configuration. Keep real credentials in environment variables and never commit production secrets to GitHub.

### `application.properties`

```properties
spring.profiles.active=${SPRING_PROFILES_ACTIVE:local}
server.port=${PORT:8080}

spring.jpa.hibernate.ddl-auto=update

jwt.secret=${JWT_SECRET:<your-secret>}
jwt.expiration=${JWT_EXPIRATION:900000}

app.admin.name=${ADMIN_NAME:Admin}
app.admin.email=${ADMIN_EMAIL:admin@example.com}
app.admin.password=${ADMIN_PASSWORD:<your-admin-password>}

app.cors.allowed-origins=${CORS_ALLOWED_ORIGINS:https://jwt-authentication-system-production.up.railway.app}
```

### Local MySQL Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_auth_db
spring.datasource.username=root
spring.datasource.password=${DATABASE_PASSWORD:<your-password>}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
```

### Environment Variables

| Variable | Description | Example |
| --- | --- | --- |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `local` or `prod` |
| `PORT` | Server port | `8080` |
| `JWT_SECRET` | Secret key used to sign JWTs | `<your-secret>` |
| `JWT_EXPIRATION` | Access token expiration in milliseconds | `900000` |
| `DATABASE_PASSWORD` | Local database password | `<your-password>` |
| `ADMIN_NAME` | Bootstrap admin display name | `Admin` |
| `ADMIN_EMAIL` | Bootstrap admin email | `admin@example.com` |
| `ADMIN_PASSWORD` | Bootstrap admin password | `<your-admin-password>` |
| `CORS_ALLOWED_ORIGINS` | Allowed frontend origins | `https://jwt-authentication-system-production.up.railway.app` |
| `DATABASE_URL` | Railway PostgreSQL database URL | Provided by Railway |

## ▶️ Running the Project

### Run Locally with Maven

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

### Run with a Specific Profile

```bash
SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE="local"
.\mvnw.cmd spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## 📡 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/auth/` | Public | Redirects to the login page. |
| `POST` | `/auth/register` | Public | Register a new user. |
| `POST` | `/auth/login` | Public | Authenticate a user and return tokens. |
| `GET` | `/auth/admin-login-defaults` | Public | Return configured admin demo credentials. |
| `POST` | `/auth/refresh` | Public | Generate a new access token from a refresh token. |
| `POST` | `/auth/logout` | Public | Revoke a refresh token. |
| `POST` | `/auth/forgot-password` | Public | Create a password reset token. |
| `POST` | `/auth/reset-password` | Public | Reset password using a valid reset token. |

### Protected User Endpoints

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/` | Public | Redirects to `login.html`. |
| `GET` | `/dashboard` | Authenticated | Return current user dashboard/profile data. |
| `GET` | `/test/secure` | Authenticated | Verify authenticated access. |

### Admin Endpoints

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/admin/users` | Admin | List all users. |
| `GET` | `/admin/users/{id}` | Admin | Get a user by ID. |
| `POST` | `/admin/users` | Admin | Create a user. |
| `PUT` | `/admin/users/{id}` | Admin | Update a user's name or role. |
| `DELETE` | `/admin/users/{id}` | Admin | Delete a user and related token records. |

## 🧪 Sample Request & Response JSON

### Register User

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "name": "Virendra Kumar",
  "email": "virendra@example.com",
  "password": "StrongPass@123"
}
```

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXJlbmRyYUBleGFtcGxlLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzIwMDAwMDAwLCJleHAiOjE3MjAwMDA5MDB9.XYZsignature",
  "refreshToken": "8b94d6e7-fb7d-432c-97ff-46e0c5de3d89",
  "userId": 1,
  "name": "Virendra Kumar",
  "email": "virendra@example.com",
  "role": "USER"
}
```

### Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "virendra@example.com",
  "password": "StrongPass@123"
}
```

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXJlbmRyYUBleGFtcGxlLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzIwMDAwMDAwLCJleHAiOjE3MjAwMDA5MDB9.XYZsignature",
  "refreshToken": "0f3c2d1b-37b1-4a6d-950d-e0cf1c996e12",
  "userId": 1,
  "name": "Virendra Kumar",
  "email": "virendra@example.com",
  "role": "USER"
}
```

### Refresh Token

```http
POST /auth/refresh
Content-Type: application/json
```

```json
{
  "refreshToken": "0f3c2d1b-37b1-4a6d-950d-e0cf1c996e12"
}
```

### Logout

```http
POST /auth/logout
Content-Type: application/json
```

```json
{
  "refreshToken": "0f3c2d1b-37b1-4a6d-950d-e0cf1c996e12"
}
```

### Dashboard

```http
GET /dashboard
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXJlbmRyYUBleGFtcGxlLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzIwMDAwMDAwLCJleHAiOjE3MjAwMDA5MDB9.XYZsignature
```

```json
{
  "id": 1,
  "name": "Virendra Kumar",
  "email": "virendra@example.com",
  "role": "USER",
  "message": "Welcome to your dashboard."
}
```

### Validation Error

```json
{
  "timestamp": "2026-07-17T10:15:30.123Z",
  "status": 400,
  "errors": {
    "email": "Invalid email format",
    "password": "Password must contain uppercase, lowercase, number and special character"
  }
}
```

## 🔑 JWT Authentication Example

After login or registration, include the access token in the `Authorization` header.

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXJlbmRyYUBleGFtcGxlLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzIwMDAwMDAwLCJleHAiOjE3MjAwMDA5MDB9.XYZsignature
```

Example cURL request:

```bash
curl -X GET http://localhost:8080/dashboard \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXJlbmRyYUBleGFtcGxlLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzIwMDAwMDAwLCJleHAiOjE3MjAwMDA5MDB9.XYZsignature"
```

## 👥 Role-Based Access Control

The system supports two primary roles.

| Role | Permissions |
| --- | --- |
| `USER` | Register, login, access dashboard, refresh token, logout, reset password. |
| `ADMIN` | All user permissions plus user listing, creation, update, deletion, and role management. |

Admin endpoints are protected with Spring Security:

```java
.requestMatchers("/admin/**").hasRole("ADMIN")
.anyRequest().authenticated()
```

Admin safeguards include:

- An admin cannot delete their own account while logged in.
- An admin cannot change their own role.
- The system prevents removing the last remaining admin account.
- Deleting a user also removes related refresh and password reset token records.

## 🚨 Exception Handling

The application uses centralized exception handling with `@RestControllerAdvice`, which keeps API error responses consistent across validation failures and business rule errors.

| Exception Type | HTTP Status | Response Body |
| --- | --- | --- |
| `RuntimeException` | `400 Bad Request` | `timestamp`, `message`, `status` |
| `MethodArgumentNotValidException` | `400 Bad Request` | `timestamp`, `status`, `errors` |

Example runtime error:

```json
{
  "timestamp": "2026-07-17T10:15:30.123Z",
  "message": "User already exists",
  "status": 400
}
```

## ✅ Validation

Validation is applied at the DTO layer using Jakarta Bean Validation. Invalid requests are rejected before reaching business logic, which keeps service methods focused and predictable.

| Field | Rules |
| --- | --- |
| `name` | Required, 3-50 characters, letters and spaces only. |
| `email` | Required, valid email format. |
| `password` | Required, 8-20 characters, uppercase, lowercase, number, and special character. |
| `role` | Must be `USER` or `ADMIN`. |
| `token` | Required for password reset. |

## 🚀 Deployment on Railway

The project is ready for Railway deployment using PostgreSQL in production.

### Railway Deployment Steps

1. Push the project to GitHub.
2. Create a new Railway project.
3. Connect the GitHub repository.
4. Add a PostgreSQL database service.
5. Configure production environment variables.
6. Deploy the Spring Boot service.
7. Verify the login, registration, dashboard, and admin flows.

### Recommended Railway Variables

```env
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=<your-secret>
JWT_EXPIRATION=900000
DATABASE_PASSWORD=<your-password>
ADMIN_NAME=Admin
ADMIN_EMAIL=admin@example.com
ADMIN_PASSWORD=<your-admin-password>
CORS_ALLOWED_ORIGINS=https://jwt-authentication-system-production.up.railway.app
```

Production uses the PostgreSQL dialect:

```properties
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=false
```

## 🖼️ Screenshots

| Screen | Placeholder |
| --- | --- |
| Login Page | `docs/images/login-page.png` |
| Register Page | `docs/images/register-page.png` |
| Dashboard | `docs/images/dashboard.png` |
| Admin Panel | `docs/images/admin-panel.png` |
| Postman Testing | `docs/images/postman-testing.png` |
| Railway Deployment | `docs/images/railway-deployment.png` |

## 🧭 Design Principles

- Keep authentication stateless and scalable.
- Separate controller, service, repository, security, DTO, and entity responsibilities.
- Validate input at the API boundary.
- Store passwords securely using BCrypt hashing.
- Use environment variables for sensitive deployment configuration.
- Keep admin operations explicit, restricted, and protected by role checks.
- Return consistent API responses for validation and application errors.

## 🎯 Key Learning Outcomes

- Building secure REST APIs with Spring Boot and Spring Security.
- Implementing JWT access tokens and database-backed refresh tokens.
- Applying role-based access control for admin-only endpoints.
- Designing layered backend architecture with Spring Data JPA and Hibernate.
- Creating DTO-based request validation with Jakarta Bean Validation.
- Handling application exceptions through centralized REST advice.
- Preparing a Spring Boot application for Railway deployment with PostgreSQL.

## 🤝 Contributing

Contributions are welcome. Please follow a clean and reviewable workflow:

1. Fork the repository.
2. Create a feature branch.
3. Make focused changes.
4. Add or update tests when applicable.
5. Open a pull request with a clear summary.

```bash
git checkout -b feature/add-refresh-token-rotation
git commit -m "Add refresh token rotation"
git push origin feature/add-refresh-token-rotation
```

## 📄 License

This project is intended for learning, portfolio demonstration, and open-source collaboration. Add a license file before distributing it under a specific open-source license.

## 📬 Contact

**Virendra Sonar**  
Java Full Stack Developer

| Platform | Details |
| --- | --- |
| LinkedIn | [www.linkedin.com/in/virendra-sonar](https://www.linkedin.com/in/virendra-sonar) |
| Email | [virendrasonar187@gmail.com](mailto:virendrasonar187@gmail.com) |

<p align="center">
  Built with Java, Spring Boot, Spring Security, JWT, and clean backend engineering practices.
</p>
