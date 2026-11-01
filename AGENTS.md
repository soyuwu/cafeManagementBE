# Repository Guidelines

## Project Structure & Module Organization

This is a Kotlin/Spring Boot backend for cafe management. Application code is in `src/main/kotlin/com/se114/cafeManagement`, organized by responsibility: `controller/` exposes REST endpoints, `service/` holds business logic, `repository/` contains Spring Data JPA interfaces, `entity/` maps database tables, `dto/` defines request/response payloads, and `config/` contains application configuration. Runtime settings are in `src/main/resources/application.properties`; tests mirror the package layout under `src/test/kotlin`. Docker files and `.env.example` support local PostgreSQL development.

## Build, Test, and Development Commands

- `./gradlew bootRun` (or `gradlew.bat bootRun` on Windows) runs the API with JDK 21; provide the datasource environment variables first.
- `./gradlew build` compiles, tests, and produces the application artifact.
- `./gradlew test` runs the JUnit 5 test suite.
- `docker compose up --build` builds and starts the API plus PostgreSQL using `.env`.
- `docker compose down` stops local services while retaining the database volume.

## Coding Style & Naming Conventions

Use Kotlin idioms and four-space indentation, matching the existing source. Keep packages lowercase and classes in PascalCase (`UserService`); functions and properties use camelCase (`createUser`, `passwordHash`). Name DTOs by role, such as `CreateUserRequest` and `UserResponse`. Prefer constructor injection, `@Valid` request validation, and focused controllers that delegate to services. There is no configured formatter or linter, so preserve surrounding formatting and keep imports minimal.

## Testing Guidelines

Tests use JUnit 5 with Spring Boot test starters. Put tests in the corresponding package under `src/test/kotlin`, name classes `*Tests`, and use descriptive test names such as `createUserRejectsDuplicateEmail`. Run `./gradlew test` before opening a PR. The project has no stated coverage threshold; add regression tests for new behavior and bug fixes.

## Commit & Pull Request Guidelines

Recent commits use short, imperative summaries (for example, `generate tables` or `test some basic API`). Keep each commit scoped and describe the user-facing or technical change clearly. PRs should explain the change, list validation performed, link related issues when available, and include API examples or screenshots/logs when endpoint behavior or deployment configuration changes. Never commit `.env`, database credentials, or other secrets.
