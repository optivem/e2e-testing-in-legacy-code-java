# System Test Configuration
# This file contains configuration values for Run-SystemTests.ps1

$Config = @{
    # Test Configuration
    TestInstallCommands = $null

    SmokeTestCommand = "& .\gradlew.bat clean test --tests 'com.optivem.eshop.systemtest.smoketests.*'"
    E2ETestCommand = "& .\gradlew.bat clean test --tests 'com.optivem.eshop.systemtest.e2etests.*'"

    TestReportPath = "build\reports\tests\test\index.html"
}

# Export the configuration
return $Config

