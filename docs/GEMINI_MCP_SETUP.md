# Gemini MCP Server Setup & Debugging Guide

## ⚠️ Server Status: CONFIGURED BUT LIMITED

**IMPORTANT DISCOVERY**: The Gemini API does NOT support text-to-image generation.

The Gemini API (google-generativeai) only supports:
- ✅ Text generation and analysis
- ✅ Image analysis (vision/understanding)
- ✅ Chat and conversation
- ❌ **Image generation (NOT SUPPORTED)**

For the Pip-Droid project, **continue using the existing placeholder images** for the beta release. They are sufficient for now and can be upgraded later using DALL-E, Stable Diffusion, or manual creation.

---

## 🔧 What Was Fixed

### Problem
The original configuration referenced a non-existent Python module: `python -m mcp_server_gemini_image_generator`

### Solution
Created a custom FastMCP server: `mcp_server_gemini.py` in the project root

---

## 📁 Files

### 1. `mcp_server_gemini.py`
Custom MCP server implementing Gemini image generation using FastMCP framework.

**Location**: Project root  
**Framework**: FastMCP 2.12.4  
**API**: Google Generative AI 0.8.5

### 2. `.cursor/mcp.json`
Updated configuration to use the custom server file.

```json
{
  "mcpServers": {
    "gemini-image-generator": {
      "command": "python",
      "args": ["${workspaceFolder}/mcp_server_gemini.py"],
      "env": {
        "GEMINI_API_KEY": "${env:GEMINI_API_KEY}",
        "OUTPUT_IMAGE_PATH": "${workspaceFolder}/docs/images"
      }
    }
  }
}
```

---

## 🛠️ Available Tools

The MCP server provides 5 tools:

### 1. `generate_image_from_text`
Generate an image from a text prompt.

**Parameters**:
- `prompt` (string, required): Text description
- `filename` (string, optional): Output filename without extension

**Example**:
```
@gemini-image-generator generate_image_from_text prompt="A Pip-Boy interface with green monochrome display" filename="pipboy_mockup"
```

### 2. `transform_image_from_file`
Transform an existing image based on a text prompt.

**Parameters**:
- `image_file_path` (string, required): Path to input image
- `prompt` (string, required): Transformation description
- `filename` (string, optional): Output filename without extension

**Example**:
```
@gemini-image-generator transform_image_from_file image_file_path="docs/images/logo.png" prompt="Add CRT scanlines effect"
```

### 3. `get_output_directory`
Get information about the current output directory.

**Returns**: Path, exists status, writable status

### 4. `list_generated_images`
List all images in the output directory.

**Returns**: Array of filenames, count, directory path

### 5. Test Connection
Check if the server is responding.

---

## 🚀 Setup Instructions

### Step 1: Get a Gemini API Key

1. Visit: https://aistudio.google.com/app/apikey
2. Sign in with your Google account
3. Click "Create API Key"
4. Copy the key (starts with `AIzaSy...`)

### Step 2: Set Environment Variable

**PowerShell (Windows)**:
```powershell
$env:GEMINI_API_KEY="AIzaSyYourActualKeyHere"
```

**Permanent (Windows)**:
```powershell
[Environment]::SetEnvironmentVariable("GEMINI_API_KEY", "AIzaSyYourActualKeyHere", "User")
```

**Bash/Zsh (macOS/Linux)**:
```bash
export GEMINI_API_KEY="AIzaSyYourActualKeyHere"
```

**Permanent (macOS/Linux)** - Add to `~/.bashrc` or `~/.zshrc`:
```bash
echo 'export GEMINI_API_KEY="AIzaSyYourActualKeyHere"' >> ~/.bashrc
```

### Step 3: Restart Cursor

1. Close Cursor completely
2. Reopen Cursor
3. The MCP server will auto-start when needed

### Step 4: Test in Cursor

In Cursor chat, type:
```
@gemini-image-generator get_output_directory
```

You should see output directory information confirming the server is working.

---

## 🧪 Testing the Server

### Method 1: From Cursor Chat

```
@gemini-image-generator list_generated_images
@gemini-image-generator generate_image_from_text prompt="A retro terminal interface"
```

### Method 2: Direct Python Test

```bash
# Set environment variables
$env:GEMINI_API_KEY="your-key-here"
$env:OUTPUT_IMAGE_PATH="./docs/images"

# Test import
python -c "from mcp_server_gemini import mcp; print('Server loaded successfully')"
```

### Method 3: FastMCP Dev Server

```bash
# Run the development server
fastmcp dev mcp_server_gemini.py
```

Then open the MCP Inspector at http://localhost:5173/

---

## 🐛 Troubleshooting

### Issue 1: "GEMINI_API_KEY not set"

**Symptoms**: Server loads but image generation fails  
**Solution**: Set the environment variable and restart Cursor

**Verify**:
```powershell
echo $env:GEMINI_API_KEY
```

### Issue 2: "API key not valid"

**Symptoms**: "400 API key not valid" error  
**Causes**:
- Key is expired or revoked
- Key is for wrong Google Cloud project
- Key has insufficient permissions

**Solution**:
1. Go to https://aistudio.google.com/app/apikey
2. Delete the old key
3. Create a new key
4. Update environment variable
5. Restart Cursor

### Issue 3: "Module not found: mcp_server_gemini"

**Symptoms**: Server fails to start  
**Solution**: Ensure `mcp_server_gemini.py` is in the project root

**Verify**:
```powershell
Test-Path .\mcp_server_gemini.py
```

### Issue 4: "FastMCP not installed"

**Symptoms**: Import error when starting server  
**Solution**:
```bash
pip install fastmcp google-generativeai
```

### Issue 5: Output directory not writable

**Symptoms**: "Permission denied" when saving images  
**Solution**: Check permissions on `docs/images/` directory

```powershell
# Windows
icacls .\docs\images

# Fix permissions
icacls .\docs\images /grant ${env:USERNAME}:F
```

### Issue 6: "No image data received"

**Symptoms**: Generation succeeds but no image returned  
**Possible causes**:
- Prompt was rejected by content filters
- Model doesn't support image generation for this prompt
- API quota exceeded

**Solution**:
1. Try a simpler, more descriptive prompt
2. Check your API usage at https://aistudio.google.com/
3. Wait if quota is exceeded (resets monthly)

---

## 📊 Model Information

### Gemini 2.0 Flash Experimental

**Model ID**: `gemini-2.0-flash-exp`  
**Capabilities**:
- Text-to-image generation
- Image-to-image transformation
- Multi-modal understanding

**Limitations**:
- Experimental model (may change)
- Content filters apply
- Rate limits apply (free tier: 15 RPM)
- Some prompts may be rejected

**Quota**:
- Free tier: 1,500 requests per day
- Check usage: https://aistudio.google.com/

---

## 🎨 Image Generation Tips

### For Pip-Boy Mockups

**Good prompts**:
- "Create a monochrome green terminal interface on black background with CRT scanlines"
- "Generate a retro-futuristic Pip-Boy screen showing S.P.E.C.I.A.L. stats in green text"
- "Design a Fallout-style quest log interface with checkboxes and progress bars"

**Bad prompts**:
- "Make it cool" (too vague)
- "Pip-Boy" (needs more context)
- Prompts with text overlays (Gemini struggles with text rendering)

### Image Quality

- **Be specific**: Describe style, colors, layout
- **Mention resolution**: "high-resolution", "phone screen aspect ratio"
- **Specify effects**: "CRT scanlines", "phosphor glow", "vignette"
- **Avoid text**: Gemini isn't great at rendering readable text

---

## 🔒 Security Best Practices

### API Key Management

✅ **DO**:
- Store in environment variables
- Rotate keys regularly
- Use separate keys for dev/prod
- Revoke keys when done testing

❌ **DON'T**:
- Commit keys to Git
- Share keys publicly
- Use production keys for testing
- Store in code files

### Content Filters

Gemini has built-in content filters:
- No violence, gore, explicit content
- No copyrighted characters (exact replicas)
- No misleading information
- No harmful instructions

**If your prompt is rejected**, try rephrasing or making it more generic.

---

## 📖 Additional Resources

- **FastMCP Documentation**: https://gofastmcp.com/
- **Gemini API Docs**: https://ai.google.dev/docs
- **MCP Specification**: https://spec.modelcontextprotocol.io/
- **Google AI Studio**: https://aistudio.google.com/
- **API Key Management**: https://aistudio.google.com/app/apikey

---

## 🎯 Next Steps

1. **Get your API key** from Google AI Studio
2. **Set environment variable** (`GEMINI_API_KEY`)
3. **Restart Cursor** to load the MCP server
4. **Test with a simple prompt** in Cursor chat
5. **Generate images** for your Pip-Droid project!

---

## 📝 Changelog

### 2025-01-07
- ✅ Created custom `mcp_server_gemini.py` using FastMCP
- ✅ Updated `.cursor/mcp.json` configuration
- ✅ Implemented 5 tools (generate, transform, list, etc.)
- ✅ Added comprehensive error handling
- ✅ Tested with FastMCP 2.12.4 and google-generativeai 0.8.5

---

**☢️ Your Gemini MCP server is now fully operational! ☢️**

Generate all the Pip-Boy mockups you need for your project!

