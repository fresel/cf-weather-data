# Copilot Instructions for CF Weather Data

## Project Overview
This is a weather data API service built with Spring Boot that provides weather information.

## Tech Stack
- **Language**: Java
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Container**: Docker
- **IaC**: Terraform
  - Currently: Keycloak provider for auth infrastructure
  - Future: Hetzner Cloud provider for compute infrastructure
- **Auth**: Keycloak

## Project Structure
- `backend/cf-weather-api/` - Spring Boot application
- `infrastructure/` - Infrastructure as code and deployment configs
  - `docker/dev/` - Local development Docker Compose setup
  - `terraform/` - Terraform configurations
    - Currently managing Keycloak resources
    - Will later include Hetzner Cloud infrastructure
- `design/` - PlantUML diagrams and architecture documentation

## Coding Guidelines
- Follow Java code conventions
- Use meaningful variable and method names
- Keep methods focused and single-purpose
- Write unit tests for business logic
- Use dependency injection via Spring

## Dependencies & Configuration
- Application config: `application.yaml`
- Test config: `application-test.yaml`
- Dependencies managed via Maven POM

## Local Development
- Use `docker-compose.yml` for local services (Keycloak, etc.)
- Keycloak runs on port 9180 (admin/admin)
- API endpoints documented in OpenAPI format

## Testing
- Run tests with Maven: `mvn test`
- Test files mirror main structure in `src/test/java/`

## Preferences
- Prefer constructor injection over field injection
- Use record types for DTOs when applicable
- Keep controllers thin, business logic in services
