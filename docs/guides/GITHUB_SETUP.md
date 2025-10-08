# GitHub Repository Setup Guide

This guide will help you push your Pip-Droid project to GitHub.

## ⚠️ **IMPORTANT SECURITY NOTICE**

**NEVER share your GitHub Personal Access Token publicly!**

The token you shared in your request should be **immediately revoked** at:
https://github.com/settings/tokens

Create a new token and keep it secure. Do not commit it to the repository.

---

## 📋 Prerequisites

1. **GitHub Account**: Ensure you have a GitHub account
2. **Git Installed**: Install Git from https://git-scm.com/
3. **Repository Created**: Create the repository at https://github.com/Snapwave333/pip-droid

---

## 🚀 Setup Steps

### 1. Initialize Git Repository

```bash
cd /path/to/pipboy
git init
git branch -M main
```

### 2. Add Remote

```bash
git remote add origin https://github.com/Snapwave333/pip-droid.git
```

### 3. Stage All Files

```bash
git add .
```

### 4. Create Initial Commit

```bash
git commit -m "Initial commit: Pip-Droid v0.1.0-beta

- Full launcher functionality
- S.P.E.C.I.A.L. stats system
- Quest log with branching logic
- Terminal mode with 40+ commands
- CRT visual effects
- Radio player with Fallout.FM stations
- Comprehensive documentation"
```

### 5. Push to GitHub

#### Option A: HTTPS (Recommended)
```bash
git push -u origin main
```

When prompted, enter your GitHub username and Personal Access Token (not password).

#### Option B: SSH
If you have SSH keys set up:
```bash
git remote set-url origin git@github.com:Snapwave333/pip-droid.git
git push -u origin main
```

---

## 🔐 Creating a New Personal Access Token

1. Go to https://github.com/settings/tokens
2. Click "Generate new token" → "Generate new token (classic)"
3. Give it a descriptive name: "Pip-Droid Repository Access"
4. Set expiration (recommend: 90 days)
5. Select scopes:
   - ✅ `repo` (Full control of private repositories)
   - ✅ `workflow` (Update GitHub Action workflows)
6. Click "Generate token"
7. **Copy the token immediately** (you won't see it again)
8. Store it securely (password manager recommended)

---

## 📦 Creating the v0.1.0-beta Release

### Using GitHub Web Interface

1. Go to https://github.com/Snapwave333/pip-droid/releases
2. Click "Create a new release"
3. **Tag**: `v0.1.0-beta`
4. **Release title**: `Pip-Droid v0.1.0-beta - Initial Beta Release`
5. **Description**: Copy content from `docs/RELEASE_NOTES_v0.1.0-beta.md`
6. **Attach files**: Upload `build/outputs/apk/debug/PipBoy-debug.apk` (rename to `PipDroid-v0.1.0-beta.apk`)
7. ✅ Check "This is a pre-release"
8. Click "Publish release"

### Using GitHub CLI

```bash
# Install GitHub CLI (if not already installed)
# https://cli.github.com/

# Authenticate
gh auth login

# Create release
gh release create v0.1.0-beta \
  --title "Pip-Droid v0.1.0-beta - Initial Beta Release" \
  --notes-file docs/RELEASE_NOTES_v0.1.0-beta.md \
  --prerelease \
  build/outputs/apk/debug/PipBoy-debug.apk#PipDroid-v0.1.0-beta.apk
```

---

## 🎨 Generating Images

### Option 1: Using Gemini API

```bash
# Set API key as environment variable
export GEMINI_API_KEY="your_actual_gemini_api_key_here"

# Install dependencies
pip install google-genai pillow

# Run generator
python scripts/generate_repo_images.py
```

### Option 2: Manual Creation

1. Use the prompts in `docs/images/README.md`
2. Create images with Photoshop/GIMP/Figma
3. Save to `docs/images/` with correct filenames
4. Commit and push

### Option 3: Use Screenshots

1. Install the app on your device
2. Take screenshots of each screen
3. Edit to add scanline effects if desired
4. Save to `docs/images/`

---

## 📁 Repository Structure Checklist

Ensure all these files are present:

```
✅ README.md
✅ CHANGELOG.md
✅ CONTRIBUTING.md
✅ LICENSE
✅ .gitignore
✅ docs/RELEASE_NOTES_v0.1.0-beta.md
✅ docs/images/README.md
✅ docs/guides/GITHUB_SETUP.md
✅ scripts/generate_repo_images.py
✅ src/ (all source code)
✅ build.gradle
✅ settings.gradle
```

---

## 🔍 Verify Setup

After pushing, verify:

1. **Repository**: https://github.com/Snapwave333/pip-droid
   - All files visible
   - README displays correctly
   - Images render (if uploaded)

2. **Releases**: https://github.com/Snapwave333/pip-droid/releases
   - v0.1.0-beta tag exists
   - APK file downloadable
   - Release notes display correctly

3. **Issues**: https://github.com/Snapwave333/pip-droid/issues
   - Enabled and accessible

4. **Discussions**: https://github.com/Snapwave333/pip-droid/discussions
   - Enabled in repository settings

---

## 🎯 Post-Setup Tasks

### 1. Configure Repository Settings

Go to repository Settings:

- **Description**: "Retro-futuristic Android launcher inspired by Fallout's Pip-Boy"
- **Topics**: `android`, `launcher`, `fallout`, `pipboy`, `kotlin`, `jetpack-compose`, `retro`, `sci-fi`
- **Features**:
  - ✅ Wikis
  - ✅ Issues
  - ✅ Discussions
  - ❌ Projects (optional)

### 2. Add Repository Image

1. Go to Settings → General
2. Scroll to "Social preview"
3. Upload `docs/images/banner.png` (or logo.png)

### 3. Create Initial Issues

Create some initial issues for community engagement:

- "Add sound effects system" (enhancement)
- "Implement holotape mini-games" (enhancement)
- "Feedback wanted: CRT effects" (question)
- "Help wanted: Translations" (help wanted)

### 4. Pin Important Issues

Pin 2-3 important issues to the Issues tab for visibility.

### 5. Set Up GitHub Actions (Optional)

Create `.github/workflows/build.yml` for automated builds:

```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - name: Build with Gradle
      run: ./gradlew assembleDebug
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: build/outputs/apk/debug/*.apk
```

---

## 🐛 Troubleshooting

### Error: "remote: Repository not found"

**Solution**: Verify repository URL and authentication
```bash
git remote -v
# Should show: https://github.com/Snapwave333/pip-droid.git
```

### Error: "failed to push some refs"

**Solution**: Pull first, then push
```bash
git pull origin main --rebase
git push origin main
```

### Error: "Permission denied"

**Solution**: Check token permissions and expiration
- Token must have `repo` scope
- Token must not be expired
- Use token as password (not GitHub password)

### Large file errors

**Solution**: The APK is too large for Git
- Add `*.apk` to `.gitignore` (already done)
- Upload APK only to Releases, not to repository
- Use Git LFS if needed for assets

---

## 📚 Additional Resources

- **Git Documentation**: https://git-scm.com/doc
- **GitHub Docs**: https://docs.github.com
- **GitHub CLI**: https://cli.github.com/manual/
- **Markdown Guide**: https://guides.github.com/features/mastering-markdown/

---

## ✅ Final Checklist

Before announcing the release:

- [ ] Repository pushed to GitHub
- [ ] README displays correctly
- [ ] Images generated and uploaded
- [ ] v0.1.0-beta release created
- [ ] APK uploaded to release
- [ ] Release notes complete
- [ ] Repository settings configured
- [ ] Topics/tags added
- [ ] Social preview image set
- [ ] Issues enabled
- [ ] Discussions enabled
- [ ] Old token revoked
- [ ] New secure token created
- [ ] Contributing guidelines clear
- [ ] License file present

---

## 🎉 You're Done!

Your Pip-Droid repository is now ready for the wasteland!

Share the repository link:
```
https://github.com/Snapwave333/pip-droid
```

Download link:
```
https://github.com/Snapwave333/pip-droid/releases/tag/v0.1.0-beta
```

---

*Last Updated: October 8, 2025*

