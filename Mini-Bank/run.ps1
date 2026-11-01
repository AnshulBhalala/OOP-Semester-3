$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
$javac = (Get-Command javac -ErrorAction SilentlyContinue).Source
if (-not $javac -and $env:JAVA_HOME) {
    $candidate = Join-Path $env:JAVA_HOME "bin\javac.exe"
    if (Test-Path $candidate) { $javac = $candidate }
}
if (-not $javac) {
    foreach ($root in @("$env:ProgramFiles\Microsoft", "$env:ProgramFiles\Java")) {
        if (Test-Path $root) {
            $candidate = Get-ChildItem $root -Directory -Filter "jdk*" | Sort-Object Name -Descending | ForEach-Object { Join-Path $_.FullName "bin\javac.exe" } | Where-Object { Test-Path $_ } | Select-Object -First 1
            if ($candidate) { $javac = $candidate; break }
        }
    }
}
if (-not $javac) { Write-Error "JDK 17 or later is required. Install a JDK or set JAVA_HOME."; exit 1 }
$java = Join-Path (Split-Path $javac) "java.exe"
if (-not (Test-Path $java)) { Write-Error "Could not find java.exe beside javac.exe."; exit 1 }
$version = (& $javac -version 2>&1 | Out-String)
if ($version -notmatch "javac\s+(\d+)") { Write-Error "Could not determine the JDK version."; exit 1 }
if ([int]$Matches[1] -lt 17) { Write-Error "JDK 17 or later is required; found $version."; exit 1 }
if (Test-Path "build") { Remove-Item "build" -Recurse -Force }
New-Item -ItemType Directory -Force -Path "build" | Out-Null
$files = Get-ChildItem -Path "src\minibank" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
if (-not $files) { throw "No Java source files found." }
& $javac -encoding UTF-8 -d build $files
if ($LASTEXITCODE -ne 0) { Write-Error "Compilation failed."; exit 1 }
& $java -cp build minibank.Main
exit $LASTEXITCODE
