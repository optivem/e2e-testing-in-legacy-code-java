# System Test

This directory contains system-level acceptance tests for the e-shop application.

> **Note**: For system orchestration commands (`start`, `stop`, `logs`), see the [root README](../README.md). The `run.ps1` script has been moved to the project root.

## Test Structure

The test suite includes:

### Smoke Tests
- **ApiSmokeTest**: Verifies basic API connectivity
- **UiSmokeTest**: Verifies home page loads

### E2E Tests (API)
- **ApiE2eTest**: Tests order management via REST API
  - Place order
  - Get order details
  - Cancel order
  - Validation (negative quantity, non-integer SKU, non-integer quantity)

### E2E Tests (UI)
- **UiE2eTest**: Tests order management via web interface (Playwright)
  - Calculate total order price
  - Retrieve order history
  - Cancel order
  - Validation (negative quantity, non-integer SKU, non-integer quantity)

## Test Data

Tests use the following product SKUs (defined in `json-server-db.erp-api.json` at root):
- **SKU 10**: HP Pavilion Laptop - $109.95
- **SKU 11**: Samsung Galaxy Book - $499.99
- **SKU 12**: Huawei P30 - $679.99

## Running Tests Locally

From the **project root**:

```powershell
# Run all tests
.\run.ps1 test

# Or run everything (build, start services, test, show logs)
.\run.ps1 all
```

From the **system-test directory** (direct Gradle):

```powershell
.\gradlew test
```

## Test Reports

After running tests, view the HTML report:
```
system-test/build/reports/tests/test/index.html
```

## Configuration

Test configuration is in `src/test/resources/application.yml`:

```yaml
test:
  eshop:
    baseUrl: http://localhost:8080  # Monolith URL
  wait:
    seconds: 10  # Timeout for UI waits
```

