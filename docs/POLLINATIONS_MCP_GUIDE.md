# Pollinations.AI MCP Server Guide

## ✅ Perfect Solution for Pip-Droid Image Generation!

**Pollinations.AI** is a **FREE, open-source** image generation service that works perfectly with Cursor's MCP protocol. No API keys required!

**Official Repository**: https://github.com/pollinations/pollinations

---

## 🎯 Why Pollinations.AI?

✅ **Completely FREE** - No API keys, no credits, no limits  
✅ **Open Source** - MIT licensed, community-driven  
✅ **MCP Ready** - Built-in Model Context Protocol support  
✅ **High Quality** - Multiple AI models (Flux, DALL-E 3, Stable Diffusion)  
✅ **Fast** - Optimized image generation and caching  
✅ **No Setup** - Works via npx, no installation needed  

---

## 📁 Configuration

Your `.cursor/mcp.json` is now configured with:

```json
{
  "mcpServers": {
    "pollinations-image": {
      "command": "npx",
      "args": ["-y", "@pollinations/mcp-server"]
    }
  }
}
```

**No API keys required!** 🎉

---

## 🚀 How to Use in Cursor

### After Restarting Cursor:

Simply use `@pollinations-image` in your Cursor chat:

```
@pollinations-image generate a Pip-Boy interface with green monochrome display
@pollinations-image create a retro terminal screen with CRT scanlines
@pollinations-image design a Fallout-style quest log interface
```

### Example Commands:

**Generate Banner**:
```
@pollinations-image Create a wide banner in retro-futuristic style inspired by Fallout's Pip-Boy interface. Use monochrome green color palette on black background with glowing UI elements, circuit overlays, and stylized vault background. Include "Pip-Droid Launcher" text in bold pixel-style font. Add subtle scanlines and CRT distortion.
```

**Generate Logo**:
```
@pollinations-image Design a circular logo for Pip-Droid Android launcher. Style it like a glowing green Pip-Boy interface button with a gear or vault door motif in the center. Use pixel art and retro tech textures. Simple, iconic, and readable at small sizes. Monochrome green on black.
```

**Generate UI Mockups**:
```
@pollinations-image Generate a mockup of a Pip-Boy Android home screen with six bottom tabs labeled STATUS, INV, DATA, MAP, RADIO, HOLOTAPES. Show green monochrome text on black, CRT scanline overlay, phone screen aspect ratio 9:20.
```

---

## 🛠️ Available Models

Pollinations.AI supports multiple AI models:

| Model | Best For | Speed |
|-------|----------|-------|
| **Flux** | High-quality realistic images | Medium |
| **Turbo** | Fast generation, good quality | Fast |
| **DALL-E 3** | Creative interpretations | Medium |
| **Stable Diffusion** | Artistic styles | Fast |

The MCP server automatically selects the best model for your prompt!

---

## 🎨 Image Generation Tips for Pip-Boy Mockups

### Good Prompts:

✅ "Monochrome green terminal interface on black background with CRT scanlines"  
✅ "Retro-futuristic Pip-Boy screen showing S.P.E.C.I.A.L. stats in green text"  
✅ "Fallout-style quest log interface with checkboxes and progress bars, green on black"  
✅ "Vintage computer terminal with phosphor glow effect, 1980s aesthetic"  

### Bad Prompts:

❌ "Cool UI" (too vague)  
❌ "Pip-Boy" (needs more detail)  
❌ "Make something awesome" (not specific)  

### Best Practices:

1. **Be Specific**: Describe colors, layout, style
2. **Mention Effects**: CRT scanlines, phosphor glow, vignette
3. **Specify Aspect Ratio**: "Phone screen 9:20", "wide banner", "square icon"
4. **Include References**: "Fallout-style", "retro-futuristic", "1980s terminal"
5. **Avoid Text**: AI struggles with readable text - add text manually later

---

## 💡 Features of Pollinations.AI

### 1. **Free API Access**
```
https://image.pollinations.ai/prompt/{your-prompt-here}
```

### 2. **Multiple Models**
Choose from Flux, Turbo, DALL-E 3, or Stable Diffusion

### 3. **Customizable Parameters**
- Width & Height
- Seed (for reproducibility)
- Model selection
- Enhance prompts

### 4. **MCP Integration**
Direct integration with Cursor IDE via Model Context Protocol

### 5. **React Components**
Use in web projects with `@pollinations/react`

---

## 📖 MCP Tools Available

The Pollinations MCP server provides these tools:

### 1. `generate_image`
Generate an image from a text prompt

**Parameters**:
- `prompt` (string): Description of the image
- `width` (number, optional): Image width (default: 1024)
- `height` (number, optional): Image height (default: 1024)
- `model` (string, optional): Model to use (flux, turbo, etc.)
- `seed` (number, optional): Seed for reproducibility

**Example**:
```
@pollinations-image generate_image prompt="Pip-Boy interface" width=1920 height=1080
```

### 2. `list_models`
Get available AI models

### 3. `get_image_url`
Get direct URL for an image (for embedding)

---

## 🔄 Workflow for Pip-Droid Images

### Step 1: Generate Repository Images

In Cursor chat:

```
@pollinations-image Create 8 images for my Pip-Boy Android launcher:

1. Banner: Wide retro-futuristic banner with "Pip-Droid Launcher" text
2. Logo: Circular green glowing Pip-Boy button
3. Boot Sequence: Terminal initialization screen
4. Main Interface: 6-tab launcher home screen
5. Stats Screen: S.P.E.C.I.A.L. stats display
6. Quest Log: Task list with checkboxes
7. Terminal: Command-line interface
8. Radio: Station list with controls

All in monochrome green on black with CRT effects
```

### Step 2: Download Generated Images

The MCP server will:
1. Generate each image
2. Provide download links
3. Return base64 data (if needed)

### Step 3: Save to docs/images/

Save the generated images with proper filenames:
- `banner.png`
- `logo.png`
- `boot_sequence.png`
- `main_interface.png`
- `stats_screen.png`
- `quest_log.png`
- `terminal.png`
- `radio.png`

---

## 🐛 Troubleshooting

### Issue 1: MCP Server Not Found

**Symptoms**: `@pollinations-image` doesn't appear in Cursor  
**Solution**: 
1. Restart Cursor completely
2. Verify `.cursor/mcp.json` exists
3. Check Node.js is installed: `node --version`

### Issue 2: Image Generation Fails

**Symptoms**: Error messages in Cursor chat  
**Solutions**:
- Make prompt more specific
- Reduce image size
- Try different model
- Simplify prompt (remove special characters)

### Issue 3: Slow Generation

**Symptoms**: Takes a long time to generate  
**Solutions**:
- Use `model="turbo"` for faster results
- Reduce image dimensions
- Split into multiple smaller requests

### Issue 4: NPX Not Found

**Symptoms**: Command not found error  
**Solution**: Install Node.js from https://nodejs.org/

---

## 🆚 Comparison: Pollinations vs Others

| Feature | Pollinations.AI | DALL-E 3 | Stable Diffusion | Gemini |
|---------|-----------------|----------|------------------|--------|
| **Cost** | FREE | $0.040/image | Varies | N/A |
| **API Key** | None | Required | Varies | Required |
| **MCP Support** | ✅ Built-in | ❌ None | ❌ None | ❌ No images |
| **Quality** | High | Very High | High | N/A |
| **Speed** | Fast | Medium | Fast | N/A |
| **Open Source** | ✅ Yes | ❌ No | ✅ Yes | ❌ No |

---

## 🌐 API Usage (Alternative Method)

If you prefer using the API directly:

### Python Example:
```python
import requests
from pathlib import Path

def generate_image(prompt, filename):
    # URL encode the prompt
    url = f"https://image.pollinations.ai/prompt/{requests.utils.quote(prompt)}"
    
    # Optional parameters
    params = {
        "width": 1920,
        "height": 1080,
        "model": "flux",
        "enhance": "true"
    }
    
    # Add parameters to URL
    url += "?" + "&".join(f"{k}={v}" for k, v in params.items())
    
    # Download the image
    response = requests.get(url)
    Path(f"docs/images/{filename}.png").write_bytes(response.content)
    print(f"✅ Generated: {filename}.png")

# Generate all Pip-Droid images
prompts = {
    "banner": "Wide banner in retro-futuristic style, Fallout Pip-Boy interface...",
    "logo": "Circular logo, glowing green Pip-Boy button, gear motif...",
    # ... more prompts
}

for filename, prompt in prompts.items():
    generate_image(prompt, filename)
```

### Direct URL Method:
```
https://image.pollinations.ai/prompt/A%20retro%20Pip-Boy%20interface?width=1920&height=1080&model=flux
```

---

## 📚 Additional Resources

- **Official Site**: https://pollinations.ai/
- **GitHub**: https://github.com/pollinations/pollinations
- **Documentation**: https://github.com/pollinations/pollinations/tree/master/model-context-protocol
- **Discord Community**: Join for support and showcases
- **React Components**: https://www.npmjs.com/package/@pollinations/react

---

## 🎯 Quick Start Checklist

- [ ] `.cursor/mcp.json` configured with Pollinations
- [ ] Cursor IDE restarted
- [ ] Node.js installed (for npx)
- [ ] Ready to generate images!

---

## 💫 Example Prompts for Each Image

### Banner (1920x400):
```
Create a wide panoramic banner showcasing a retro-futuristic Pip-Boy interface. Feature a prominent "Pip-Droid Launcher" title in chunky, pixelated green font reminiscent of old CRT displays. Background should be deep black with intricate circuit board patterns and glowing green lines. Add authentic CRT scanlines, slight screen curvature, and phosphor glow effects. Include subtle Vault-Tec branding elements. Style: 1980s terminal aesthetic meets Fallout universe. Monochrome green (#00FF00) on black (#000000).
```

### Logo (512x512):
```
Design a circular app icon for Pip-Droid launcher. Center: A stylized gear or vault door mechanism. Outer ring: Glowing green border with small LED-style dots. Style: Pixel art with retro tech textures, reminiscent of Fallout's Pip-Boy. Color: Bright phosphor green (#00FF00) on pure black background. Must be simple, iconic, and instantly recognizable at small sizes. Add subtle emboss effect to give depth. No text, just the icon.
```

### Boot Sequence (1080x1920):
```
Generate a phone screen (9:20 aspect ratio) showing a terminal boot sequence. Top: "ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL" in green ASCII art. Below: System initialization messages like "LOADING OS...", "CHECKING VAULT-TEC SYSTEMS...", "INITIALIZING PIP-BOY 3000...", "CALIBRATING SENSORS...". Include blinking cursor at bottom. Heavy CRT scanlines. Pure monochrome green text (#00FF00) on black (#000000). Style: 1980s computer terminal, Fallout aesthetic.
```

### Main Interface (1080x1920):
```
Phone screen mockup of Pip-Boy Android home screen. Top: Status bar with time, battery, signal in green. Center: Large "STATUS" panel showing system info. Bottom: 6 navigation tabs in green: "STATUS | INV | DATA | MAP | RADIO | HOLO". Each tab has a small icon. Background: Pure black. Text: Bright green. Add CRT scanlines overlay, slight vignette, and phosphor glow. Phone aspect ratio 9:20. Style: Retro terminal UI from Fallout.
```

### Stats Screen (1080x1920):
```
Pip-Boy S.P.E.C.I.A.L. stats display on phone screen. Show 7 stat boxes vertically: STRENGTH, PERCEPTION, ENDURANCE, CHARISMA, INTELLIGENCE, AGILITY, LUCK. Each has: stat name, level number (1-10), horizontal progress bar, and short description. Use large pixel font for numbers. Add expandable arrow icons. Green text on black background with CRT scanlines. Phone aspect 9:20. RPG stats interface style from Fallout.
```

### Quest Log (1080x1920):
```
Quest log interface on phone screen. Title: "QUESTS" in large green font. List of 3-4 quests, each showing: quest name, category tag (MAIN/SIDE), progress bar, 2-3 objectives with checkboxes. Bottom: "+" button to add quest. Use monospace font. Green text (#00FF00) on black, CRT scanlines, slight screen curve. Phone aspect 9:20. Fallout Pip-Boy quest interface style.
```

### Terminal (1080x1920):
```
Retro computer terminal on phone screen. Show command prompt ">" with example commands: "help", "status", "quests". Display terminal output in green monospace font. Include command history. Heavy CRT scanlines, phosphor glow, slight flicker effect. Pure green text on pure black. Phone aspect 9:20. Classic 1980s computer terminal aesthetic, VT100 style.
```

### Radio (1080x1920):
```
Pip-Boy radio interface on phone screen. Title: "RADIO" at top. List of 5-6 Fallout radio stations: Galaxy News Radio, Diamond City Radio, Radio New Vegas, etc. Each with signal strength indicator. Bottom: Large play/pause button, stop button, volume slider with retro gauge. Green vintage radio dial aesthetic. Monochrome green on black, CRT effects. Phone aspect 9:20.
```

---

## 🎉 Benefits for Pip-Droid

✅ **Free Forever** - No costs for image generation  
✅ **No API Keys** - Zero configuration hassle  
✅ **Fast Integration** - Works immediately after Cursor restart  
✅ **High Quality** - Professional-looking mockups  
✅ **Unlimited Use** - Generate as many variations as needed  
✅ **Open Source** - Community-supported, MIT licensed  

---

## 🚀 Next Steps

1. **Restart Cursor** - Close and reopen the IDE
2. **Test the MCP** - Type `@pollinations-image` in Cursor chat
3. **Generate Images** - Create all 8 Pip-Droid repository images
4. **Update GitHub** - Replace placeholder images with generated ones
5. **Publish Release** - Your beta release will look professional!

---

**☢️ Generate amazing Pip-Boy images for FREE with Pollinations.AI! ☢️**

Your repository will look incredible with AI-generated mockups!

