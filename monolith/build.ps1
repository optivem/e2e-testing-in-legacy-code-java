Write-Host "Building monolith application..." -ForegroundColor Cyan

& .\gradlew.bat clean build

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host ""
Write-Host "Build completed successfully!" -ForegroundColor Green
Write-Host "JAR file created in: " -NoNewline
Write-Host "build\libs\" -ForegroundColor Yellow

