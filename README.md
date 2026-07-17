# JWT Authentication System

> A production-ready Spring Boot authentication backend with JWT access tokens, refresh tokens, role-based authorization, password reset support, and admin user management.

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Spring%20Security-6.x-6DB33F?style=for-the-badge&logo=springsecurity" alt="Spring Security" />
  <img src="https://img.shields.io/badge/JWT-JSON%20Web%20Token-black?style=for-the-badge&logo=jsonwebtokens" alt="JWT" />
  <img src="https://img.shields.io/badge/MySQL-Local-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
  <img src="https://img.shields.io/badge/PostgreSQL-Production-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
</p>

## 📌 Project Description

JWT Authentication System is a secure backend authentication service built with Java, Spring Boot, Spring Security, JWT, Spring Data JPA, Hibernate, MySQL, and PostgreSQL. It provides a complete authentication workflow for user registration, login, token refresh, logout, password reset, protected profile access, and role-based admin operations.

The application follows a clean layered architecture with Controllers, Services, Repositories, DTOs, Entities, Security filters, and centralized exception handling. It is designed to demonstrate production-oriented backend engineering practices suitable for real-world REST APIs and recruiter-facing GitHub portfolios.

## ✨ Features

| Category | Capability |
| --- | --- |
| Authentication | User registration, login, logout, JWT access tokens, refresh tokens |
| Authorization | Role-based access control for `USER` and `ADMIN` accounts |
| Security | Stateless Spring Security filter chain, BCrypt password hashing, protected REST APIs |
| User Management | Admin-only user listing, creation, update, and deletion |
| Password Recovery | Forgot password and reset password token workflow |
| Validation | Bean Validation for email, name, password, role, and token fields |
| Persistence | JPA/Hibernate entities with MySQL for local development and PostgreSQL for production |
| Deployment | Railway-ready configuration using environment variables |
| Frontend Assets | Static login, signup, dashboard, forgot password, and reset password pages |

## 🧰 Technologies Used

| Layer | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT, BCrypt |
| Persistence | Spring Data JPA, Hibernate |
| Databases | MySQL, PostgreSQL |
| Build Tool | Maven |
| Validation | Jakarta Bean Validation |
| Deployment | Railway |
| Testing | Spring Boot Test, Spring Security Test, H2 |

## 🏗️ Architecture

The project follows a layered Controller -> Service -> Repository -> Database architecture.

```mermaid
flowchart LR
    Client["Client / REST Consumer"] --> Controller["Controller Layer"]
    Controller --> Service["Service Layer"]
    Service --> Repository["Repository Layer"]
    Repository --> Database[("MySQL / PostgreSQL")]

    Security["Spring Security + JWT Filter"] --> Controller
    Service --> TokenStore[("Refresh Tokens / Reset Tokens")]
```

| Layer | Responsibility |
| --- | --- |
| Controller | Exposes REST endpoints and handles HTTP request/response mapping |
| Service | Contains authentication, token, password reset, and business logic |
| Repository | Provides database access through Spring Data JPA |
| Entity | Maps Java objects to relational database tables |
| Security | Validates JWTs and enforces protected/admin-only route access |

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
| Authentication Module | Handles registration, login, token refresh, and logout |
| Password Reset Module | Generates and validates password reset tokens |
| Dashboard Module | Returns authenticated user profile details |
| Admin User Module | Provides admin-only user management APIs |
| Security Module | Processes JWT validation and Spring Security authorization |

## 🔐 Authentication Flow

```mermaid
sequenceDiagram
    participant Client
    participant AuthController
    participant AuthService
    participant JwtService
    participant Database

    Client->>AuthController: POST /auth/login
    AuthController->>AuthService: Validate credentials
    AuthService->>Database: Load user by email
    Database-->>AuthService: User record
    AuthService->>AuthService: Verify BCrypt password
    AuthService->>JwtService: Generate access token
    AuthService->>Database: Persist refresh token
    AuthService-->>AuthController: AuthResponse
    AuthController-->>Client: Access token + refresh token
```

## 🛡️ Security Features

| Feature | Implementation |
| --- | --- |
| Stateless sessions | `SessionCreationPolicy.STATELESS` |
| Password hashing | BCrypt via `PasswordEncoder` |
| JWT validation | Custom `JwtFilter` before `UsernamePasswordAuthenticationFilter` |
| Public routes | Static pages and `/auth/**` endpoints |
| Protected routes | Any non-public endpoint requires authentication |
| Admin routes | `/admin/**` requires `ADMIN` role |
| CSRF | Disabled for stateless REST API usage |
| CORS | Configurable via `CORS_ALLOWED_ORIGINS` |

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
| `users` | Stores registered users, encrypted passwords, roles, and audit timestamps |
| `refresh_tokens` | Stores refresh tokens associated with users |
| `password_reset_tokens` | Stores password reset tokens, expiry status, and usage metadata |

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
git clone https://github.com/VIRENDRA/jwt-authentication-system.git
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

The application uses profile-based configuration.

### `application.properties`

```properties
spring.profiles.active=${SPRING_PROFILES_ACTIVE:local}
server.port=${PORT:8080}

spring.jpa.hibernate.ddl-auto=update

jwt.secret=${JWT_SECRET:myverysecuresecretkeymyverysecuresecretkey123}
jwt.expiration=${JWT_EXPIRATION:900000}

app.admin.name=${ADMIN_NAME:Admin}
app.admin.email=${ADMIN_EMAIL:admin@example.com}
app.admin.password=${ADMIN_PASSWORD:Admin@123}

app.cors.allowed-origins=${CORS_ALLOWED_ORIGINS:https://jwt-authentication-system-production.up.railway.app}
```

### Local MySQL Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_auth_db
spring.datasource.username=root
spring.datasource.password=LocalMysqlPassword@2026
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
| `JWT_SECRET` | Secret key used to sign JWTs | `JwtAuthSystemProductionSecretKey2026!` |
| `JWT_EXPIRATION` | Access token expiration in milliseconds | `900000` |
| `ADMIN_NAME` | Bootstrap admin display name | `Admin` |
| `ADMIN_EMAIL` | Bootstrap admin email | `admin@example.com` |
| `ADMIN_PASSWORD` | Bootstrap admin password | `Admin@123` |
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
| `POST` | `/auth/register` | Public | Register a new user |
| `POST` | `/auth/login` | Public | Authenticate a user and return tokens |
| `POST` | `/auth/refresh` | Public | Generate a new access token from a refresh token |
| `POST` | `/auth/logout` | Public | Revoke a refresh token |
| `POST` | `/auth/forgot-password` | Public | Create a password reset token |
| `POST` | `/auth/reset-password` | Public | Reset password using a valid reset token |
| `GET` | `/auth/admin-login-defaults` | Public | Return configured admin demo credentials |

### Protected User Endpoints

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/dashboard` | Authenticated | Return current user dashboard/profile data |
| `GET` | `/test/secure` | Authenticated | Verify authenticated access |

### Admin Endpoints

| Method | Endpoint | Access | Description |
| --- | --- | --- | --- |
| `GET` | `/admin/users` | Admin | List all users |
| `GET` | `/admin/users/{id}` | Admin | Get a user by ID |
| `POST` | `/admin/users` | Admin | Create a user |
| `PUT` | `/admin/users/{id}` | Admin | Update a user's name or role |
| `DELETE` | `/admin/users/{id}` | Admin | Delete a user |

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
  "password": "User@1234"
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
  "password": "User@1234"
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
| `USER` | Register, login, access dashboard, refresh token, logout, reset password |
| `ADMIN` | All user permissions plus user listing, creation, update, deletion, and role management |

Admin endpoints are protected with Spring Security:

```java
.requestMatchers("/admin/**").hasRole("ADMIN")
.anyRequest().authenticated()
```

Admin safeguards include:

- An admin cannot delete their own account while logged in.
- An admin cannot change their own role.
- The system prevents removing the last remaining admin account.

## 🚨 Exception Handling

The application uses centralized exception handling with `@RestControllerAdvice`.

| Exception Type | Response |
| --- | --- |
| `RuntimeException` | Returns HTTP `400` with timestamp, message, and status |
| `MethodArgumentNotValidException` | Returns HTTP `400` with field-level validation errors |

Example runtime error:

```json
{
  "timestamp": "2026-07-17T10:15:30.123Z",
  "message": "User already exists",
  "status": 400
}
```

## ✅ Validation

Validation is applied at the DTO layer using Jakarta Bean Validation annotations.

| Field | Rules |
| --- | --- |
| `name` | Required, 3-50 characters, letters and spaces only |
| `email` | Required, valid email format |
| `password` | Required, 8-20 characters, uppercase, lowercase, number, and special character |
| `role` | Must be `USER` or `ADMIN` |
| `token` | Required for password reset |

## 🚀 Deployment on Railway

The project is ready for Railway deployment using PostgreSQL in production.

### Railway Deployment Steps

1. Push the project to GitHub.
2. Create a new Railway project.
3. Connect the GitHub repository.
4. Add a PostgreSQL database service.
5. Configure environment variables.
6. Deploy the Spring Boot service.

### Recommended Railway Variables

```env
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=JwtAuthSystemProductionSecretKey2026!
JWT_EXPIRATION=900000
ADMIN_NAME=Admin
ADMIN_EMAIL=admin@example.com
ADMIN_PASSWORD=Admin@123
CORS_ALLOWED_ORIGINS=https://jwt-authentication-system-production.up.railway.app
```

Production uses the PostgreSQL dialect:

```properties
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=false
```

## 🔮 Future Enhancements

- Email delivery integration for password reset links.
- Refresh token rotation with device/session metadata.
- Account verification through email OTP.
- Rate limiting for login and password reset endpoints.
- Audit logs for admin user management actions.
- Docker Compose setup for local MySQL development.
- OpenAPI/Swagger documentation.
- CI/CD pipeline with GitHub Actions.
- Unit and integration test expansion for authentication and authorization paths.

## 🖼️ Screenshots Placeholder

| Screen | Description |
| --- | --- |
| Login Page | Static login UI for authenticating users |
| Signup Page | Static registration UI with client-side API integration |
| Dashboard Page | Protected user dashboard rendered after authentication |
| Admin User Management | Admin-only user management API workflow |
| Railway Deployment | Production deployment overview |

<p align="center">
  Built with Java, Spring Boot, Spring Security, JWT, and clean backend engineering practices.
</p>
