# Monolith

This is the main e-shop application built with Spring Boot.

> **Note**: For building and running the system, see the [root README](../README.md). Use the `run.ps1` script at the project root.

## Prerequisites

- Java 21 (JDK)
- Gradle 8.14 (wrapper included)

### Verify Prerequisites

Check Java version:
```shell
java -version
```

Check JAVA_HOME is set correctly:
```shell
echo $env:JAVA_HOME
```

Check Gradle version:
```shell
.\gradlew --version
```

## Building

### Using root orchestration script (recommended)
From the **project root**:
```powershell
.\run.ps1 start  # Builds and starts everything
```

### Direct Gradle build
From the **monolith directory**:
```shell
.\gradlew clean build
```

The JAR file will be created in `build/libs/`

## Running Locally

From the **monolith directory**:
```shell
.\gradlew bootRun
```

The application will start on `http://localhost:8080`

## Configuration

Configuration is in `src/main/resources/application.yml`:

- **e2e profile** (default): Uses `http://erp-api:3000` for ERP API (for Docker)
- **qa profile**: Uses `http://erp-api:3000` for ERP API
- **prod profile**: Uses `http://erp-api:3000` for ERP API
