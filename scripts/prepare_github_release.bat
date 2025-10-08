@echo off
REM Pip-Droid GitHub Release Preparation Script
REM This script prepares the repository for GitHub upload

echo ===============================================
echo Pip-Droid GitHub Release Preparation
echo ===============================================
echo.

REM Check if we're in the right directory
if not exist "build.gradle" (
    echo ERROR: Must run from project root directory!
    exit /b 1
)

echo [1/6] Cleaning build artifacts...
call gradle-8.5\bin\gradle.bat clean
if errorlevel 1 (
    echo ERROR: Clean failed
    exit /b 1
)

echo.
echo [2/6] Building release APK...
call gradle-8.5\bin\gradle.bat assembleDebug
if errorlevel 1 (
    echo ERROR: Build failed
    exit /b 1
)

echo.
echo [3/6] Copying APK to docs...
if not exist "docs\releases" mkdir "docs\releases"
copy "build\outputs\apk\debug\PipBoy-debug.apk" "docs\releases\PipDroid-v0.1.0-beta.apk"
if errorlevel 1 (
    echo ERROR: APK copy failed
    exit /b 1
)

echo.
echo [4/6] Generating checksums...
certutil -hashfile "docs\releases\PipDroid-v0.1.0-beta.apk" SHA256 > "docs\releases\PipDroid-v0.1.0-beta.apk.sha256"

echo.
echo [5/6] Creating release notes...
echo Release: v0.1.0-beta > "docs\releases\release_info.txt"
echo Date: %date% >> "docs\releases\release_info.txt"
echo APK: PipDroid-v0.1.0-beta.apk >> "docs\releases\release_info.txt"
echo. >> "docs\releases\release_info.txt"
echo SHA256: >> "docs\releases\release_info.txt"
type "docs\releases\PipDroid-v0.1.0-beta.apk.sha256" >> "docs\releases\release_info.txt"

echo.
echo [6/6] Verifying repository structure...
echo.
echo Checking required files:

set "MISSING=0"

if exist "README.md" (
    echo [OK] README.md
) else (
    echo [MISSING] README.md
    set "MISSING=1"
)

if exist "CHANGELOG.md" (
    echo [OK] CHANGELOG.md
) else (
    echo [MISSING] CHANGELOG.md
    set "MISSING=1"
)

if exist "CONTRIBUTING.md" (
    echo [OK] CONTRIBUTING.md
) else (
    echo [MISSING] CONTRIBUTING.md
    set "MISSING=1"
)

if exist "LICENSE" (
    echo [OK] LICENSE
) else (
    echo [MISSING] LICENSE
    set "MISSING=1"
)

if exist ".gitignore" (
    echo [OK] .gitignore
) else (
    echo [MISSING] .gitignore
    set "MISSING=1"
)

if exist "docs\RELEASE_NOTES_v0.1.0-beta.md" (
    echo [OK] docs\RELEASE_NOTES_v0.1.0-beta.md
) else (
    echo [MISSING] docs\RELEASE_NOTES_v0.1.0-beta.md
    set "MISSING=1"
)

echo.
if "%MISSING%"=="1" (
    echo ERROR: Some required files are missing!
    exit /b 1
)

echo.
echo ===============================================
echo Success! Repository ready for GitHub
echo ===============================================
echo.
echo APK Location: docs\releases\PipDroid-v0.1.0-beta.apk
echo APK Size:
dir "docs\releases\PipDroid-v0.1.0-beta.apk" | find "PipDroid"
echo.
echo Next steps:
echo 1. Initialize git: git init
echo 2. Add remote: git remote add origin https://github.com/Snapwave333/pip-droid.git
echo 3. Add files: git add .
echo 4. Commit: git commit -m "Initial commit: v0.1.0-beta"
echo 5. Push: git push -u origin main
echo.
echo See docs\guides\GITHUB_SETUP.md for detailed instructions
echo ===============================================

pause

