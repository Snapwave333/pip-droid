# Pip-Droid Images & Assets

This folder contains images and visual assets for the Pip-Droid repository.

## 📸 Required Images

The following images need to be generated and added to this folder:

### 1. **banner.png**
- **Purpose**: Repository banner (displayed at top of README)
- **Dimensions**: 1920x480px (or similar wide format)
- **Style**: Fallout Pip-Boy inspired, monochrome green, glowing UI elements
- **Content**: "Pip-Droid Launcher" text, vault background, circuit overlays, scanlines

**Gemini Prompt**:
```
Create a wide banner in retro-futuristic style inspired by Fallout's Pip-Boy interface. 
Use a monochrome green color palette (#00FF00) with glowing UI elements, circuit overlays, 
and a stylized vault background. Include the text 'Pip-Droid Launcher' in bold pixel-style 
font, centered. Add subtle scanlines and CRT distortion for authenticity. Resolution: 1920x480px.
```

### 2. **logo.png**
- **Purpose**: App logo/icon
- **Dimensions**: 512x512px (circular)
- **Style**: Glowing green Pip-Boy button with vault/gear motif
- **Content**: Circular icon, simple, iconic, readable at small sizes

**Gemini Prompt**:
```
Design a circular logo for an Android launcher called Pip-Droid. Style it like a glowing 
green Pip-Boy interface button, with a gear or vault door motif in the center. Use pixel 
art and retro tech textures. Keep it simple, iconic, and readable at small sizes. Monochrome 
green (#00FF00) on black background. Resolution: 512x512px.
```

### 3. **boot_sequence.png**
- **Purpose**: Screenshot of terminal boot animation
- **Dimensions**: 1080x2400px (phone screenshot)
- **Style**: ROBCO Industries terminal with initialization messages
- **Content**: Green text on black, boot messages, cursor

**Gemini Prompt**:
```
Generate a mockup of a Fallout-style terminal boot sequence on an Android phone screen. 
Show 'ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL' at top, followed by system initialization 
messages in monochrome green text on black background. Include a blinking cursor at bottom. 
Add scanline overlay. Resolution: 1080x2400px (phone screenshot).
```

### 4. **main_interface.png**
- **Purpose**: Screenshot of main launcher with tabs
- **Dimensions**: 1080x2400px (phone screenshot)
- **Style**: Pip-Boy green interface with bottom navigation
- **Content**: Main screen with STATUS/INVENTORY/DATA/MAP/RADIO/HOLOTAPES tabs

**Gemini Prompt**:
```
Create a mockup of a Pip-Boy–style Android home screen with six bottom navigation tabs 
labeled STATUS, INV, DATA, MAP, RADIO, HOLOTAPES. Show main content area with system 
status display in monochrome green. Include time, battery, and system info. Add CRT 
scanline overlay. Resolution: 1080x2400px.
```

### 5. **stats_screen.png**
- **Purpose**: Screenshot of S.P.E.C.I.A.L. stats
- **Dimensions**: 1080x2400px (phone screenshot)
- **Style**: Fallout S.P.E.C.I.A.L. stat display
- **Content**: Seven stats (S.P.E.C.I.A.L.) with levels, XP bars, descriptions

**Gemini Prompt**:
```
Generate a mockup showing Fallout S.P.E.C.I.A.L. stats on a Pip-Boy screen. Display 7 
stats (Strength, Perception, Endurance, Charisma, Intelligence, Agility, Luck) with level 
numbers, XP progress bars, and expandable descriptions. Monochrome green text on black. 
Add CRT effects. Resolution: 1080x2400px.
```

### 6. **quest_log.png**
- **Purpose**: Screenshot of quest management
- **Dimensions**: 1080x2400px (phone screenshot)
- **Style**: Pip-Boy quest log interface
- **Content**: List of quests with categories, progress bars, objectives

**Gemini Prompt**:
```
Create a Pip-Boy quest log screen mockup showing 3-4 quests in a list. Each quest should 
have title, category tag (MAIN/SIDE), progress bar, and checkbox objectives. Include '+' 
button to add quests. Monochrome green on black with CRT scanlines. Resolution: 1080x2400px.
```

### 7. **terminal.png**
- **Purpose**: Screenshot of terminal mode
- **Dimensions**: 1080x2400px (phone screenshot)
- **Style**: Retro computer terminal
- **Content**: Terminal with command prompt, output text, monospace font

**Gemini Prompt**:
```
Generate a retro terminal interface mockup for a Pip-Boy. Show command prompt '>' with 
example commands like 'help', 'status', 'quests'. Display terminal output in monospace 
green text on black background. Add heavy CRT scanlines and glow effect. Resolution: 1080x2400px.
```

### 8. **radio.png**
- **Purpose**: Screenshot of radio player
- **Dimensions**: 1080x2400px (phone screenshot)
- **Style**: Pip-Boy radio tuner
- **Content**: Radio station list, playback controls, volume slider

**Gemini Prompt**:
```
Create a Pip-Boy radio interface showing a list of Fallout radio stations (Galaxy News Radio, 
Radio New Vegas, Diamond City Radio, etc.). Include play/pause/stop buttons and volume slider. 
Monochrome green on black with retro gauges and dials. Add CRT effects. Resolution: 1080x2400px.
```

---

## 🎨 Image Generation Instructions

### Using Gemini API

The project can generate these images using the Gemini API. To generate:

1. Ensure you have the Gemini API key configured
2. Run the image generation script (when implemented):
   ```bash
   python scripts/generate_images.py
   ```

### Manual Generation

Alternatively, create these images manually using:
- **Photoshop/GIMP**: For banner and logo
- **Figma**: For UI mockups
- **Screenshots**: From actual app (for boot_sequence, main_interface, etc.)

### Design Guidelines

#### Colors
- **Primary**: #00FF00 (Pip-Boy green)
- **Background**: #000000 (black)
- **Accent**: #003300 (dark green for shadows)
- **Glow**: #00FF00 with blur/opacity

#### Typography
- **Headings**: VT323, Share Tech Mono, or similar pixel font
- **Body**: Courier New, Roboto Mono, or similar monospace
- **Size**: Large enough to read at thumbnail size

#### Effects
- **Scanlines**: Horizontal lines every 2-4px, 10-20% opacity
- **Glow**: Outer glow on text and UI elements
- **Vignette**: Darkened corners
- **CRT Curve**: Slight barrel distortion on edges
- **Noise**: Optional 1-2% noise overlay

---

## 📐 Image Specifications

### Banner (banner.png)
```
Dimensions: 1920x480px
Format: PNG with transparency or solid black background
File Size: <500KB
DPI: 72
Color Mode: RGB
```

### Logo (logo.png)
```
Dimensions: 512x512px
Format: PNG with transparency
File Size: <200KB
DPI: 72
Color Mode: RGB
Shape: Circular or square with rounded corners
```

### Screenshots (*.png)
```
Dimensions: 1080x2400px (9:20 aspect ratio)
Format: PNG
File Size: <1MB each
DPI: 320 (phone screen density)
Color Mode: RGB
```

---

## 🔄 Placeholder Images

Until real images are generated, use these placeholders:

1. **Banner**: Solid green (#00FF00) with "PIP-DROID" text
2. **Logo**: Green circle with "PD" initials
3. **Screenshots**: Actual app screenshots (when available)

---

## 📝 Attribution

All images should:
- Be original creations or properly licensed
- Credit any third-party assets used
- Comply with Bethesda's fan content policy
- Not use copyrighted Fallout game assets directly

---

## 🚀 Current Status

- [ ] banner.png
- [ ] logo.png  
- [ ] boot_sequence.png
- [ ] main_interface.png
- [ ] stats_screen.png
- [ ] quest_log.png
- [ ] terminal.png
- [ ] radio.png

---

*Last Updated: October 8, 2025*

