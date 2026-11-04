# Repository Guidelines

## Project Structure & Module Organization

This repository is a Kotlin/Spring Boot cafe-management API. Application code is in `src/main/kotlin/com/se114/cafeManagement`:

- `controller/` defines REST endpoints.
- `service/` contains business rules and transactions.
- `repository/` holds Spring Data JPA repositories.
- `entity/` maps PostgreSQL tables.
- `dto/` contains request and response models, grouped by feature such as `dto/product/`.
- `config/` contains framework configuration.

Tests mirror production packages under `src/test/kotlin`. Runtime configuration lives in `src/main/resources/application.properties`; Docker files and `.env.example` support local PostgreSQL development.

## Build, Test, and Development Commands

- `gradlew.bat bootRun` starts the API on Windows using JDK 21. Set datasource environment variables first.
- `gradlew.bat build` compiles, runs tests, and creates the application artifact.
- `gradlew.bat test` runs the JUnit 5 suite.
- `docker compose up --build` builds and starts the API plus PostgreSQL from `.env`.
- `docker compose down` stops containers while retaining the database volume.

On macOS/Linux, use `./gradlew` instead of `gradlew.bat`.

## Coding Style & Naming Conventions

Use idiomatic Kotlin with four-space indentation and minimal imports. Packages are lowercase; classes use PascalCase (`ProductService`), while functions and properties use camelCase (`createProduct`). Name request/response DTOs by role, for example `CreateCategoryRequest` and `CategoryResponse`.

Use constructor injection, validate incoming bodies with `@Valid`, and keep controllers thin: controllers delegate to services, services own transactions, and repositories only access persistence. No formatter or linter is configured; preserve nearby formatting.

## Testing Guidelines

Tests use JUnit 5 and Spring Boot test starters. Put a test in the matching package under `src/test/kotlin`, name classes `*Tests`, and use behavior-focused names such as `createProductRejectsMissingCategory`. Add regression tests for every bug fix or new endpoint. Run `gradlew.bat test` before submitting changes. No coverage threshold is currently configured.

## Commit & Pull Request Guidelines

Use short, imperative commit subjects, such as `add products categories api` or `test basic API`. Keep commits scoped. Pull requests should explain the change, list validation performed, link relevant issues, and include request/response examples when an endpoint changes.

## Security & Configuration

Never commit `.env`, credentials, or production secrets. Copy `.env.example` for local use and configure production secrets in the deployment environment. Validate client input and do not treat client-provided payment status as trusted.
