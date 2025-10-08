# Automated GitHub Publishing Script for Pip-Droid (PowerShell)
# This script handles EVERYTHING to publish your repository

param(
    [Parameter(Mandatory=$false)]
    [string]$GitHubToken,
    
    [Parameter(Mandatory=$false)]
    [string]$UserName,
    
    [Parameter(Mandatory=$false)]
    [string]$UserEmail
)

Write-Host @"
╔══════════════════════════════════════════════════════════════════════════╗
║                                                                          ║
║              PIP-DROID AUTOMATED GITHUB PUBLISHER                       ║
║                                                                          ║
╚══════════════════════════════════════════════════════════════════════════╝
"@ -ForegroundColor Green

# Check prerequisites
Write-Host "`n[1/10] Checking prerequisites..." -ForegroundColor Cyan

# Check Git
if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Host "ERROR: Git is not installed!" -ForegroundColor Red
    Write-Host "Download from: https://git-scm.com/"
    exit 1
}
Write-Host "✓ Git installed" -ForegroundColor Green

# Check project directory
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERROR: Must run from project root directory!" -ForegroundColor Red
    exit 1
}
Write-Host "✓ In project directory" -ForegroundColor Green

# Initialize Git
Write-Host "`n[2/10] Initializing Git repository..." -ForegroundColor Cyan

if (Test-Path ".git") {
    Write-Host "Git repository already exists." -ForegroundColor Yellow
    $reinit = Read-Host "Reinitialize? This will delete git history (y/n)"
    if ($reinit -eq 'y') {
        Remove-Item -Path ".git" -Recurse -Force
        git init
        git branch -M main
        Write-Host "✓ Git reinitialized" -ForegroundColor Green
    }
} else {
    git init
    git branch -M main
    Write-Host "✓ Git initialized" -ForegroundColor Green
}

# Configure Git
Write-Host "`n[3/10] Configuring Git..." -ForegroundColor Cyan

if (-not $UserName) {
    $UserName = Read-Host "Enter your name for git commits"
}
if (-not $UserEmail) {
    $UserEmail = Read-Host "Enter your email for git commits"
}

git config user.name "$UserName"
git config user.email "$UserEmail"
Write-Host "✓ Git configured as $UserName <$UserEmail>" -ForegroundColor Green

# Add remote
Write-Host "`n[4/10] Configuring remote repository..." -ForegroundColor Cyan

git remote remove origin 2>$null
git remote add origin https://github.com/Snapwave333/pip-droid.git
Write-Host "✓ Remote added: https://github.com/Snapwave333/pip-droid.git" -ForegroundColor Green

# Stage files
Write-Host "`n[5/10] Staging files..." -ForegroundColor Cyan

git add .
$stagedFiles = (git diff --cached --numstat | Measure-Object).Count
Write-Host "✓ Staged $stagedFiles file(s)" -ForegroundColor Green

# Commit
Write-Host "`n[6/10] Creating commit..." -ForegroundColor Cyan

$commitMessage = @"
Initial commit: Pip-Droid v0.1.0-beta

- Full Android launcher functionality with 6 tabs
- S.P.E.C.I.A.L. stats system tracking phone usage
- Quest log with CRUD, branching logic, due dates, reminders
- Terminal mode with 40+ commands and 20+ Easter eggs
- CRT visual effects (scanlines, glow, flicker, vignette)
- Radio player with 8 Fallout.FM stations
- Comprehensive documentation (README, CHANGELOG, CONTRIBUTING)
- 8 placeholder images (ready for Gemini upgrade)
- Automation scripts for images and releases
- 85% feature completion

Features:
- Launcher: STATUS, INVENTORY, DATA, MAP, RADIO, HOLOTAPES tabs
- Stats: 7 S.P.E.C.I.A.L. stats with levels 1-10 and XP tracking
- Quests: Full CRUD, 5 categories, 4 priorities, branching chains
- Terminal: 40+ commands, 20+ Easter eggs, command history
- CRT: Scanlines, phosphor glow, screen flicker (toggleable)
- Radio: 8 Fallout.FM stations with playback controls

See CHANGELOG.md for complete feature list.
"@

git commit -m $commitMessage
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Git commit failed!" -ForegroundColor Red
    Write-Host "This might be because there are no changes to commit." -ForegroundColor Yellow
    exit 1
}
Write-Host "✓ Commit created" -ForegroundColor Green

# Get or prompt for token
Write-Host "`n[7/10] Preparing to push to GitHub..." -ForegroundColor Cyan

if (-not $GitHubToken) {
    Write-Host @"

╔══════════════════════════════════════════════════════════════════════════╗
║                  GITHUB PERSONAL ACCESS TOKEN NEEDED                     ║
╚══════════════════════════════════════════════════════════════════════════╝

You need a GitHub Personal Access Token to push.

Steps to create one:
1. Go to: https://github.com/settings/tokens
2. Click "Generate new token (classic)"
3. Name: "Pip-Droid Repository"
4. Scope: Check "repo" (Full control of private repositories)
5. Click "Generate token"
6. COPY THE TOKEN (you won't see it again!)

"@ -ForegroundColor Yellow

    $GitHubToken = Read-Host "Paste your GitHub Personal Access Token" -AsSecureString
    $GitHubToken = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
        [Runtime.InteropServices.Marshal]::SecureStringToBSTR($GitHubToken))
}

# Push to GitHub
Write-Host "`nPushing to GitHub..." -ForegroundColor Cyan

$remoteUrl = "https://$($GitHubToken)@github.com/Snapwave333/pip-droid.git"
git push $remoteUrl main --set-upstream

if ($LASTEXITCODE -ne 0) {
    Write-Host "`nERROR: Push failed!" -ForegroundColor Red
    Write-Host @"

Common issues:
1. Wrong token or invalid token
2. Token doesn't have 'repo' scope
3. Repository doesn't exist on GitHub
4. Network connection issues

To retry manually:
  git push -u origin main

"@ -ForegroundColor Yellow
    exit 1
}

Write-Host "✓ Successfully pushed to GitHub!" -ForegroundColor Green

# Prepare release files
Write-Host "`n[8/10] Preparing release files..." -ForegroundColor Cyan

$releasePath = "docs\releases"
if (-not (Test-Path $releasePath)) {
    New-Item -ItemType Directory -Path $releasePath | Out-Null
}

$apkSource = "build\outputs\apk\debug\PipBoy-debug.apk"
$apkDest = "$releasePath\PipDroid-v0.1.0-beta.apk"

if (Test-Path $apkSource) {
    Copy-Item $apkSource $apkDest -Force
    Write-Host "✓ APK copied to $apkDest" -ForegroundColor Green
    
    # Generate checksum
    $hash = Get-FileHash $apkDest -Algorithm SHA256
    $hash.Hash | Out-File "$apkDest.sha256" -Encoding ASCII
    Write-Host "✓ SHA256 checksum generated" -ForegroundColor Green
} else {
    Write-Host "⚠ APK not found at $apkSource" -ForegroundColor Yellow
    Write-Host "  Build it first with: gradle-8.5\bin\gradle.bat assembleDebug" -ForegroundColor Yellow
}

# Create release info
Write-Host "`n[9/10] Creating release information..." -ForegroundColor Cyan

$releaseInfo = @"
Pip-Droid v0.1.0-beta Release
==============================

Date: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
Repository: https://github.com/Snapwave333/pip-droid
Release: https://github.com/Snapwave333/pip-droid/releases/tag/v0.1.0-beta

APK File: PipDroid-v0.1.0-beta.apk
SHA256: $(if (Test-Path "$apkDest.sha256") { Get-Content "$apkDest.sha256" } else { "Not generated" })

Installation:
1. Download PipDroid-v0.1.0-beta.apk
2. Enable "Unknown Sources" in Android settings
3. Install the APK
4. Set as default launcher

See docs\RELEASE_NOTES_v0.1.0-beta.md for complete release notes.
"@

$releaseInfo | Out-File "$releasePath\RELEASE_INFO.txt" -Encoding UTF8
Write-Host "✓ Release info created" -ForegroundColor Green

# Open GitHub
Write-Host "`n[10/10] Opening GitHub in browser..." -ForegroundColor Cyan
Start-Process "https://github.com/Snapwave333/pip-droid"

# Final summary
Write-Host @"

╔══════════════════════════════════════════════════════════════════════════╗
║                                                                          ║
║                        ✅ SUCCESS! ALL DONE! ✅                          ║
║                                                                          ║
╚══════════════════════════════════════════════════════════════════════════╝

Repository successfully pushed to GitHub!
Repository URL: https://github.com/Snapwave333/pip-droid

📦 NEXT STEPS:

1. Create GitHub Release:
   ✓ Go to: https://github.com/Snapwave333/pip-droid/releases
   ✓ Click "Create a new release"
   ✓ Tag: v0.1.0-beta
   ✓ Title: Pip-Droid v0.1.0-beta - Initial Beta Release
   ✓ Description: Copy from docs\RELEASE_NOTES_v0.1.0-beta.md
   ✓ Upload: docs\releases\PipDroid-v0.1.0-beta.apk
   ✓ Check "This is a pre-release"
   ✓ Publish release

2. Configure Repository:
   ✓ Settings → General → Add description
   ✓ Settings → General → Add topics: android, launcher, fallout, pipboy, kotlin
   ✓ Settings → Features → Enable Issues and Discussions
   ✓ Settings → Social preview → Upload docs\images\banner.png

3. Optional Improvements:
   ✓ Generate better images: python scripts\generate_images_simple.py
   ✓ Take screenshots from actual app
   ✓ Create custom graphics

📊 REPOSITORY STATS:
   Code: ~20,000 lines
   Files: ~150 source files
   Features: 85% complete
   Documentation: Professional quality
   Images: 8 placeholders (improvable)

☢️ War. War never changes.
   But your GitHub repository just went live!

"@ -ForegroundColor Green

Write-Host "Press any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

