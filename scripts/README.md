# Pip-Droid Automation Scripts

This folder contains scripts to automate various tasks for the Pip-Droid project.

---

## 🚀 Publishing Scripts

### **Publish-PipDroid.ps1** (Recommended)
PowerShell script that automates the entire GitHub publishing process.

**Usage**:
```powershell
# Run interactively (will prompt for token)
.\scripts\Publish-PipDroid.ps1

# Or provide parameters
.\scripts\Publish-PipDroid.ps1 -UserName "Your Name" -UserEmail "your@email.com"
```

**What it does**:
1. ✅ Initializes Git repository
2. ✅ Configures Git with your name/email
3. ✅ Adds GitHub remote
4. ✅ Stages all files
5. ✅ Creates commit with detailed message
6. ✅ Pushes to GitHub (prompts for token)
7. ✅ Prepares release APK
8. ✅ Generates SHA256 checksum
9. ✅ Creates release info
10. ✅ Opens GitHub in browser

### **auto_publish_to_github.bat**
Batch file version for Windows Command Prompt.

**Usage**:
```cmd
scripts\auto_publish_to_github.bat
```

Same functionality as PowerShell version, but for CMD.

---

## 🎨 Image Generation Scripts

### **generate_images_simple.py**
Generates professional repository images using Google Gemini API.

**Requirements**:
```bash
pip install google-genai pillow
```

**Usage**:
```bash
# Set API key
set GEMINI_API_KEY=your_gemini_api_key_here

# Generate images
python scripts/generate_images_simple.py
```

**Generates**:
- banner.png (1920x480) - Repository banner
- logo.png (512x512) - App logo
- boot_sequence.png (1080x2400) - Terminal boot
- main_interface.png (1080x2400) - Main launcher
- stats_screen.png (1080x2400) - S.P.E.C.I.A.L. stats
- quest_log.png (1080x2400) - Quest log
- terminal.png (1080x2400) - Terminal mode
- radio.png (1080x2400) - Radio player

### **create_placeholder_images.py**
Creates simple placeholder images (already run).

**Usage**:
```bash
python scripts/create_placeholder_images.py
```

Creates basic green-text-on-black placeholders for all 8 images.

---

## 📦 Release Preparation

### **prepare_github_release.bat**
Prepares release files without pushing to GitHub.

**Usage**:
```cmd
scripts\prepare_github_release.bat
```

**What it does**:
- Cleans build artifacts
- Builds release APK
- Copies APK to docs/releases
- Generates checksums
- Verifies repository structure

---

## 🔑 Token Security

### ⚠️ **NEVER commit tokens to Git!**

All scripts will prompt for your GitHub token securely:
- PowerShell: Uses SecureString (hidden input)
- Batch: Prompts during git push (hidden)

**To create a token**:
1. Go to https://github.com/settings/tokens
2. Click "Generate new token (classic)"
3. Name: "Pip-Droid Repository"
4. Scope: ✅ "repo"
5. Generate and copy immediately

---

## 📋 Quick Reference

### First-Time Publishing
```powershell
# Easiest method - PowerShell (Recommended)
.\scripts\Publish-PipDroid.ps1

# Alternative - Batch file
scripts\auto_publish_to_github.bat
```

### Improve Images
```bash
# Generate with Gemini API
set GEMINI_API_KEY=your_key
python scripts/generate_images_simple.py
```

### Manual Publishing
```bash
git init
git branch -M main
git remote add origin https://github.com/Snapwave333/pip-droid.git
git add .
git commit -m "Initial commit: Pip-Droid v0.1.0-beta"
git push -u origin main
```

---

## 📖 Documentation

For detailed instructions, see:
- `../SETUP_AND_PUBLISH.md` - Quick start guide
- `../GITHUB_REPOSITORY_READY.md` - Comprehensive instructions
- `../docs/guides/GITHUB_SETUP.md` - GitHub setup details

---

## 🐛 Troubleshooting

### "Permission denied" or "Authentication failed"
- Ensure your token has "repo" scope
- Token might be expired - create a new one
- Try pasting token carefully (no extra spaces)

### "Repository not found"
- Verify repository exists: https://github.com/Snapwave333/pip-droid
- Check repository name spelling
- Ensure you have access to the repository

### "Push rejected"
- Repository might already have commits
- Try: `git pull origin main --rebase` then `git push`

### Images not generating
- Check GEMINI_API_KEY environment variable
- Verify `google-genai` and `pillow` are installed
- Check internet connection

---

## 💡 Tips

1. **First time**: Use `Publish-PipDroid.ps1` - it handles everything
2. **Better images**: Run Gemini script or take screenshots
3. **Updates**: Just run `git add .`, `git commit`, `git push`
4. **Release**: Create on GitHub web interface with APK

---

## ✅ What's Already Done

- ✅ All 8 placeholder images created
- ✅ Documentation complete
- ✅ .gitignore configured
- ✅ Scripts ready to run

You just need to run one of the publishing scripts!

---

*Last Updated: October 8, 2025*

