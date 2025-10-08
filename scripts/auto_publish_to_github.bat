@echo off
REM Automated GitHub Publishing Script for Pip-Droid
REM This script does EVERYTHING to publish your repository

echo ===============================================
echo Pip-Droid Automated GitHub Publisher
echo ===============================================
echo.

REM Check if git is installed
where git >nul 2>nul
if errorlevel 1 (
    echo ERROR: Git is not installed!
    echo Download from: https://git-scm.com/
    pause
    exit /b 1
)

REM Check if we're in the right directory
if not exist "build.gradle" (
    echo ERROR: Must run from project root directory!
    pause
    exit /b 1
)

echo [STEP 1/10] Checking for existing git repository...
if exist ".git" (
    echo Git repository already exists.
    choice /C YN /M "Reinitialize git repository? This will delete git history"
    if errorlevel 2 goto skip_init
    rmdir /s /q .git
)

:skip_init
if not exist ".git" (
    echo Initializing git repository...
    git init
    git branch -M main
)

echo.
echo [STEP 2/10] Setting up git configuration...
set /p GIT_NAME="Enter your name for git commits: "
set /p GIT_EMAIL="Enter your email for git commits: "

git config user.name "%GIT_NAME%"
git config user.email "%GIT_EMAIL%"

echo.
echo [STEP 3/10] Adding remote repository...
git remote remove origin 2>nul
git remote add origin https://github.com/Snapwave333/pip-droid.git

echo.
echo [STEP 4/10] Staging all files...
git add .

echo.
echo [STEP 5/10] Creating commit...
git commit -m "Initial commit: Pip-Droid v0.1.0-beta

- Full Android launcher functionality with 6 tabs
- S.P.E.C.I.A.L. stats system tracking phone usage
- Quest log with CRUD, branching logic, due dates, reminders
- Terminal mode with 40+ commands and 20+ Easter eggs
- CRT visual effects (scanlines, glow, flicker, vignette)
- Radio player with 8 Fallout.FM stations
- Comprehensive documentation (README, CHANGELOG, CONTRIBUTING)
- 8 placeholder images (ready for Gemini upgrade)
- Automation scripts for images and releases
- 85%% feature completion

Features:
- Launcher: STATUS, INVENTORY, DATA, MAP, RADIO, HOLOTAPES tabs
- Stats: 7 S.P.E.C.I.A.L. stats with levels 1-10 and XP tracking
- Quests: Full CRUD, 5 categories, 4 priorities, branching chains
- Terminal: 40+ commands, 20+ Easter eggs, command history
- CRT: Scanlines, phosphor glow, screen flicker (toggleable)
- Radio: 8 Fallout.FM stations with playback controls

See CHANGELOG.md for complete feature list."

if errorlevel 1 (
    echo ERROR: Git commit failed!
    echo This might be because there are no changes to commit.
    pause
    exit /b 1
)

echo.
echo ===============================================
echo READY TO PUSH TO GITHUB!
echo ===============================================
echo.
echo IMPORTANT: You need a GitHub Personal Access Token
echo.
echo 1. Go to: https://github.com/settings/tokens
echo 2. Click "Generate new token (classic)"
echo 3. Give it a name: "Pip-Droid Repository"
echo 4. Select scope: "repo" (Full control of private repositories)
echo 5. Click "Generate token"
echo 6. COPY THE TOKEN (you won't see it again!)
echo.
echo When prompted below, paste your token as the password.
echo.
pause

echo.
echo [STEP 6/10] Pushing to GitHub...
git push -u origin main

if errorlevel 1 (
    echo.
    echo ===============================================
    echo PUSH FAILED
    echo ===============================================
    echo.
    echo Common issues:
    echo 1. Wrong token or no token provided
    echo 2. Token doesn't have "repo" scope
    echo 3. Repository doesn't exist on GitHub
    echo 4. Network connection issues
    echo.
    echo To retry:
    echo   git push -u origin main
    echo.
    pause
    exit /b 1
)

echo.
echo ===============================================
echo SUCCESS! Repository pushed to GitHub!
echo ===============================================
echo.
echo Repository URL: https://github.com/Snapwave333/pip-droid
echo.

echo.
echo [STEP 7/10] Preparing release APK...
if not exist "docs\releases" mkdir "docs\releases"

if exist "build\outputs\apk\debug\PipBoy-debug.apk" (
    copy "build\outputs\apk\debug\PipBoy-debug.apk" "docs\releases\PipDroid-v0.1.0-beta.apk" >nul
    echo ✓ APK copied to docs\releases\PipDroid-v0.1.0-beta.apk
) else (
    echo WARNING: APK not found. Build it first with:
    echo   gradle-8.5\bin\gradle.bat assembleDebug
)

echo.
echo [STEP 8/10] Generating SHA256 checksum...
if exist "docs\releases\PipDroid-v0.1.0-beta.apk" (
    certutil -hashfile "docs\releases\PipDroid-v0.1.0-beta.apk" SHA256 > "docs\releases\PipDroid-v0.1.0-beta.apk.sha256"
    echo ✓ Checksum generated
)

echo.
echo [STEP 9/10] Creating release info...
echo Release: v0.1.0-beta > "docs\releases\RELEASE_INFO.txt"
echo Date: %date% %time% >> "docs\releases\RELEASE_INFO.txt"
echo Repository: https://github.com/Snapwave333/pip-droid >> "docs\releases\RELEASE_INFO.txt"
echo APK: PipDroid-v0.1.0-beta.apk >> "docs\releases\RELEASE_INFO.txt"
echo. >> "docs\releases\RELEASE_INFO.txt"
echo SHA256 Checksum: >> "docs\releases\RELEASE_INFO.txt"
if exist "docs\releases\PipDroid-v0.1.0-beta.apk.sha256" (
    type "docs\releases\PipDroid-v0.1.0-beta.apk.sha256" >> "docs\releases\RELEASE_INFO.txt"
)

echo.
echo [STEP 10/10] Opening GitHub in browser...
start https://github.com/Snapwave333/pip-droid

echo.
echo ===============================================
echo ALL DONE! Next Steps:
echo ===============================================
echo.
echo 1. Create GitHub Release:
echo    - Go to: https://github.com/Snapwave333/pip-droid/releases
echo    - Click "Create a new release"
echo    - Tag: v0.1.0-beta
echo    - Title: Pip-Droid v0.1.0-beta - Initial Beta Release
echo    - Description: Copy from docs\RELEASE_NOTES_v0.1.0-beta.md
echo    - Upload APK: docs\releases\PipDroid-v0.1.0-beta.apk
echo    - Check "This is a pre-release"
echo    - Click "Publish release"
echo.
echo 2. Configure Repository:
echo    - Settings → Add description and topics
echo    - Settings → Enable Issues and Discussions
echo    - Settings → Upload social preview (docs\images\banner.png)
echo.
echo 3. Optional - Improve Images:
echo    - Set GEMINI_API_KEY environment variable
echo    - Run: python scripts\generate_images_simple.py
echo.
echo Repository: https://github.com/Snapwave333/pip-droid
echo APK: docs\releases\PipDroid-v0.1.0-beta.apk
echo.
echo ===============================================
echo Thank you for using Pip-Droid!
echo War. War never changes. ☢️
echo ===============================================
pause

