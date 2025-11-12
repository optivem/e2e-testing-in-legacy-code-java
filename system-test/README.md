# System Test (Java)

## Architecture

The system test environment uses Docker Compose to run:
- **monolith**: The main e-shop application
- **erp-api**: A mock ERP API server that provides product pricing data

The ERP API provides product information for the test products (IDs: 10, 11, 12) via the `/products/{id}` endpoint.

### Docker Compose Files

Three versions are available:

- **docker-compose.yml** - Default file that includes `docker-compose.local.yml` (for developer convenience)
- **docker-compose.local.yml** - Builds the monolith image locally (for development)
- **docker-compose.ci.yml** - Uses pre-built monolith image from container registry (for CI/CD)

Developers can simply run `docker compose up` which will use the default local build configuration.

## Prerequisites

Check that you have Powershell 7

```shell
$PSVersionTable.PSVersion
```

## Instructions

Open up the 'system-test' folder

```shell
cd system-test
```

Start System

### Local Mode (builds image locally - default)

```shell
.\run.ps1 start
# or explicitly
.\run.ps1 start local
```

### Pipeline Mode (uses pre-built image from registry)

```shell
.\run.ps1 start pipeline
```

Run Tests

```shell
.\run.ps1 test
```

Stop System

```shell
.\run.ps1 stop
# or for pipeline mode
.\run.ps1 stop pipeline
```

View Logs

```shell
# View logs for all services
.\run.ps1 logs
# or for pipeline mode
.\run.ps1 logs pipeline
```

## PowerShell Script

The `run.ps1` script provides the following commands:

- **.\run.ps1 all [mode]** - Runs everything: builds (local only), starts services, runs tests, then shows logs
- **.\run.ps1 start [mode]** - Starts all Docker services (mode: local or pipeline, default: local)
- **.\run.ps1 test** - Runs all tests
- **.\run.ps1 stop [mode]** - Stops all Docker services
- **.\run.ps1 logs [mode]** - Views container logs for all services

**Modes:**
- `local` (default) - Builds monolith image locally
- `pipeline` - Uses pre-built monolith image from container registry

