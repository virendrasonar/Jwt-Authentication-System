# AuthVault

AuthVault is a student demonstration project for JWT authentication, refresh tokens, role-based authorization, password recovery, and admin-managed user accounts. Spring Boot serves both the REST API and a responsive HTML/CSS/JavaScript frontend.

> Demo learning project only. Do not enter real passwords, banking details, or personal information. AuthVault is not affiliated with any company or real service.

## Features

- User registration and login
- BCrypt password hashing
- JWT access tokens containing the user's email and role
- Database-backed refresh tokens
- `USER` and `ADMIN` role-based authorization
- Personal dashboard for authenticated users
- Admin user listing, creation, role changes, and deletion
- Protection against deleting the logged-in admin or removing the last admin
- Admin user editing limited to name and role, never passwords
- Password visibility controls on login, signup, reset, and admin create-user forms
- Forgot-password flow with 15-minute, one-time reset tokens
- Rejection of password resets that reuse the current password
- Refresh-session invalidation after a successful password reset
- Responsive layouts for desktop, tablet, and mobile
- MySQL for local development, PostgreSQL on Railway, and H2 for tests

## Technology

- Java 17
- Spring Boot 3.5
- Spring Web
- Spring Security
- Spring Data JPA and Hibernate
- Bean Validation
- JJWT 0.11.5
- BCrypt
- MySQL 8 for local development
- PostgreSQL for Railway production
- H2 for automated tests
- HTML, CSS, and vanilla JavaScript
- Maven Wrapper

## Architecture

```mermaid
flowchart LR
    Browser["HTML/CSS/JavaScript frontend"] -->|"Auth requests"| Auth["/auth/**"]
    Browser -->|"Bearer access token"| Dashboard["/dashboard"]
    Browser -->|"Bearer ADMIN token"| Admin["/admin/**"]
    Auth --> Services["Spring services"]
    Dashboard --> Services
    Admin --> Services
    Services --> JPA["Spring Data JPA"]
    JPA --> DB["MySQL local / PostgreSQL production"]
```

The frontend uses `api-config.js` to build API URLs from `window.location.origin`. Local pages therefore call the local Spring Boot server, while deployed Railway pages call the same Railway service. No deployment URL is hardcoded in frontend requests.

## Authentication Flow

1. A user registers or submits an email and password to `/auth/login`.
2. The backend verifies the password against its BCrypt hash.
3. The backend returns a signed JWT access token and a random refresh token.
4. The frontend stores both tokens in `localStorage`.
5. Protected requests include the access token:

```http
Authorization: Bearer <accessToken>
```

6. `JwtFilter` validates the signature and expiry, extracts the email and role, and creates the Spring Security authentication context.
7. Spring Security permits authenticated dashboard requests and requires `ROLE_ADMIN` for `/admin/**`.

### Access Tokens

JWT access tokens are signed with HS256 and contain:

- `sub`: user email
- `role`: `USER` or `ADMIN`
- `iat`: issued-at time
- `exp`: expiry time

The default access-token lifetime is 15 minutes (`900000` milliseconds).

### Refresh Tokens

- Refresh tokens are random UUID values stored in `refresh_tokens`.
- They expire after 7 days.
- Login and registration replace older refresh tokens for that user.
- `/auth/refresh` issues a new access token from a valid refresh token.
- `/auth/logout` deletes the user's refresh-token sessions.
- A successful password reset also deletes all refresh tokens for that user.

## Roles And Permissions

| Capability | USER | ADMIN |
| --- | :---: | :---: |
| Register and login | Yes | Yes |
| View own dashboard | Yes | Yes |
| List all users | No | Yes |
| Create users | No | Yes |
| Edit user name and role | No | Yes |
| Delete users | No | Yes |
| Edit user passwords from admin panel | No | No |

Additional admin protections:

- An admin cannot delete their own logged-in account.
- An admin cannot change their own role while logged in.
- The final remaining admin cannot be demoted or deleted.
- Deleting a user also deletes related refresh and password-reset tokens.

Normal users do not see the admin panel. Attempting to open the Users section shows an administrator-access message.

## Password Rules

New and reset passwords must:

- Be 8 to 20 characters long
- Include an uppercase letter
- Include a lowercase letter
- Include a number
- Include a special character

Names must be 3 to 50 characters and contain only letters and spaces.

## Password Reset Flow

1. `/auth/forgot-password` verifies the account email.
2. Any older reset token for that user is removed.
3. A new UUID reset token is stored in `password_reset_tokens` with a 15-minute expiry.
4. The demo API returns a reset URL for `reset-password.html`.
5. `/auth/reset-password` validates the token and password rules.
6. If the new password matches the current BCrypt hash, the request is rejected with:

```text
Please enter a new password different from your current password.
```

The password is not changed and the token remains valid for another attempt.

7. If the new password is different, the backend hashes it, updates the user, deletes old refresh sessions, and deletes the used reset token.

Reset tokens are deliberately temporary. A row exists after requesting a reset and is removed after successful use, expiry handling, a newer reset request, or user deletion.

## Security Rules

Public pages and APIs include:

- `/`
- `/login.html`
- `/signup.html`
- `/dashboard.html` (page shell only)
- `/forgot-password.html`
- `/reset-password.html`
- `/auth/**`
- Public CSS, JavaScript, images, and favicon files

Protected APIs:

- `/dashboard`: valid JWT required
- `/admin/**`: valid JWT with `ADMIN` role required
- Any backend route not explicitly public: authentication required

The dashboard HTML must be publicly loadable because normal browser navigation cannot attach a bearer token. Its JavaScript immediately checks for a token and obtains all user data from the protected `/dashboard` API.

Security is stateless, HTTP Basic is disabled, and the JWT filter runs before Spring Security's username/password filter.

## API Reference

### Register

```http
POST /auth/register
Content-Type: application/json
```

```json
{
  "name": "Demo User",
  "email": "demo.user@example.com",
  "password": "Demo@1234"
}
```

Self-registered accounts receive the `USER` role.

### Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "demo.user@example.com",
  "password": "Demo@1234"
}
```

Authentication responses contain the access token, refresh token, user ID, name, email, and role.

### Refresh Access Token

```http
POST /auth/refresh
Content-Type: application/json
```

```json
{
  "refreshToken": "<refreshToken>"
}
```

### Logout

```http
POST /auth/logout
Content-Type: application/json
```

```json
{
  "refreshToken": "<refreshToken>"
}
```

### Request Password Reset

```http
POST /auth/forgot-password
Content-Type: application/json
```

```json
{
  "email": "demo.user@example.com"
}
```

### Reset Password

```http
POST /auth/reset-password
Content-Type: application/json
```

```json
{
  "token": "<resetToken>",
  "password": "NewDemo@1234"
}
```

### User Dashboard

```http
GET /dashboard
Authorization: Bearer <accessToken>
```

Returns the authenticated user's ID, name, email, role, and dashboard message.

### List Users (ADMIN)

```http
GET /admin/users
Authorization: Bearer <adminAccessToken>
```

### Get User (ADMIN)

```http
GET /admin/users/{id}
Authorization: Bearer <adminAccessToken>
```

### Create User (ADMIN)

```http
POST /admin/users
Authorization: Bearer <adminAccessToken>
Content-Type: application/json
```

```json
{
  "name": "Team Member",
  "email": "member@example.com",
  "password": "Member@1234",
  "role": "USER"
}
```

### Update User (ADMIN)

Only the name and role are editable. Email and password editing are intentionally unavailable.

```http
PUT /admin/users/{id}
Authorization: Bearer <adminAccessToken>
Content-Type: application/json
```

```json
{
  "name": "Updated Name",
  "role": "ADMIN"
}
```

### Delete User (ADMIN)

```http
DELETE /admin/users/{id}
Authorization: Bearer <adminAccessToken>
```

## Frontend Pages

| Page | Purpose |
| --- | --- |
| `/login.html` | Sign in and open the dashboard |
| `/signup.html` | Create a USER account |
| `/dashboard.html` | User dashboard and conditional admin tools |
| `/forgot-password.html` | Generate a demo reset link |
| `/reset-password.html?token=...` | Set a new password |

The UI clearly identifies itself as a student demo and warns users not to enter real credentials or personal information.

## Database Model

### `users`

- Unique email
- BCrypt password hash
- Name and role
- Created and updated timestamps

### `refresh_tokens`

- Unique UUID token
- Expiry timestamp
- User foreign key

### `password_reset_tokens`

- Unique UUID token
- Expiry timestamp
- User foreign key
- Compatibility fields for consumed-token state

Hibernate uses `ddl-auto=update` to create or update tables. It does not create the MySQL database itself.

## Local Development With MySQL

### Prerequisites

- Java 17 or newer
- MySQL 8 running on port `3306`
- PowerShell or another terminal

### 1. Create The Database

```sql
CREATE DATABASE jwt_auth_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

### 2. Configure Local Credentials

The local profile is configured in:

```text
src/main/resources/application-local.properties
```

It uses:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_auth_db
spring.datasource.username=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

Set the local password in that file to match your MySQL installation. Do not commit or publish real database credentials.

### 3. Configure A Local Admin

The project does not contain a permanent hardcoded admin password. Set environment variables before startup:

```powershell
$env:ADMIN_NAME="Admin"
$env:ADMIN_EMAIL="admin@example.com"
$env:ADMIN_PASSWORD="Admin@123"
```

These values are demo credentials. Do not reuse them in production.

`AdminBootstrapConfig` creates the admin if it does not exist or updates it at startup when both email and password are supplied. The password is stored as a BCrypt hash.

### 4. Run The Application

The default profile is `local`:

```powershell
.\mvnw.cmd spring-boot:run
```

Open:

```text
http://localhost:8080/login.html
```

## Application Profiles

The default profile selection is:

```properties
spring.profiles.active=${SPRING_PROFILES_ACTIVE:local}
```

| Profile | Database | Purpose |
| --- | --- | --- |
| `local` | MySQL | Local development |
| `test` | In-memory H2 | Automated tests |
| `prod` | PostgreSQL | Railway deployment |

## Railway Deployment

Production uses the `prod` profile and PostgreSQL. The deployed application URL is:

```text
https://jwt-authentication-system-production.up.railway.app
```

Configure these Railway environment variables:

```text
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=<Railway PostgreSQL connection URL>
JWT_SECRET=<long random secret of at least 32 bytes>
ADMIN_NAME=Admin
ADMIN_EMAIL=<production admin email>
ADMIN_PASSWORD=<strong production admin password>
```

Optional variables:

```text
JWT_EXPIRATION=900000
DB_USERNAME=<username if not included in DATABASE_URL>
DB_PASSWORD=<password if not included in DATABASE_URL>
CORS_ALLOWED_ORIGINS=<comma-separated allowed frontend origins>
```

Railway supplies `PORT` automatically; the application falls back to `8080` locally. `DATABASE_URL` may use either format:

```text
postgresql://username:password@host:5432/database
```

```text
jdbc:postgresql://host:5432/database
```

`ProductionDataSourceConfig` converts a standard PostgreSQL URL into a JDBC URL and configures the PostgreSQL driver. Keep database credentials and JWT secrets in Railway variables, never in frontend files.

Because the frontend and API are served by the same Spring Boot deployment, normal production requests are same-origin. `CORS_ALLOWED_ORIGINS` is available when an additional frontend origin must be allowed.

## Build And Test

Run tests:

```powershell
.\mvnw.cmd test
```

Build the production JAR:

```powershell
.\mvnw.cmd clean package
```

The packaged application is created under `target/`.

## Project Structure

```text
src/
|-- main/
|   |-- java/com/auth/authproject/
|   |   |-- config/       Security, CORS, admin bootstrap, production datasource
|   |   |-- controller/   Auth, dashboard, and admin endpoints
|   |   |-- dto/          Request and response validation models
|   |   |-- entity/       User, refresh-token, and reset-token entities
|   |   |-- exception/    API exception responses
|   |   |-- repository/   Spring Data JPA repositories
|   |   |-- security/     JWT creation and request filter
|   |   `-- service/      Authentication and token business logic
|   `-- resources/
|       |-- static/       Frontend pages, styles, and JavaScript
|       |-- application.properties
|       |-- application-local.properties
|       `-- application-prod.properties
`-- test/
    |-- java/             Spring Boot tests
    `-- resources/        H2 test profile
```

## Security Notes

- Use a long, random `JWT_SECRET` in production.
- Do not use demo admin credentials in production.
- Never commit database URLs, passwords, reset tokens, or JWT secrets.
- Use HTTPS in production.
- Passwords are always stored as BCrypt hashes and are never compared as plain text.
- The demo reset link is returned in the API response. A real application should send it through a verified email provider and avoid exposing the raw token in general UI.
- Tokens are stored in `localStorage` for demonstration simplicity. A hardened production system should consider secure, HTTP-only cookies, stricter content security policies, rate limiting, account lockout controls, and audited email delivery.
- The project intentionally displays student-demo and test-credential warnings to prevent confusion with a real service.

## License

This repository is intended for learning and portfolio demonstration. Add a license file before redistributing it under specific license terms.
