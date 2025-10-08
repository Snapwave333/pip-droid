# ElevenLabs MCP Server Guide

## 🎙️ Give AI a Voice - Professional Text-to-Speech & Audio Processing

**ElevenLabs MCP** is the official Model Context Protocol server that brings **world-class voice AI** to your development workflow - generate speech, clone voices, transcribe audio, and create immersive soundscapes.

**Official Repository**: https://github.com/elevenlabs/elevenlabs-mcp  
**Official Blog**: https://elevenlabs.io/blog/introducing-elevenlabs-mcp  
**ElevenLabs**: https://elevenlabs.io/

---

## 🌟 What is ElevenLabs?

ElevenLabs is the **leading AI voice platform** that provides:

🎤 **Text-to-Speech** - Natural, human-like AI voices  
🎭 **Voice Cloning** - Create custom voices from audio samples  
🔊 **Voice Design** - Generate unique character voices from descriptions  
📝 **Speech-to-Text** - Transcribe audio with speaker identification  
🎵 **Sound Effects** - Generate custom sound effects and ambiences  
🔄 **Audio Processing** - Isolate vocals, remove background noise  
🌍 **Multi-Language** - Support for 29+ languages  

---

## 📁 Configuration

Your `.cursor/mcp.json` now includes:

```json
{
  "mcpServers": {
    "elevenlabs": {
      "command": "uvx",
      "args": ["elevenlabs-mcp"],
      "env": {
        "ELEVENLABS_API_KEY": "${env:ELEVENLABS_API_KEY}",
        "ELEVENLABS_MCP_BASE_PATH": "${workspaceFolder}/docs/audio",
        "ELEVENLABS_MCP_OUTPUT_MODE": "files"
      }
    }
  }
}
```

**Requirements**:
- Python package manager `uv` (auto-installs with `uvx`)
- ElevenLabs API key (free tier: 10k credits/month)

---

## 🚀 Setup Instructions

### Step 1: Get ElevenLabs API Key

1. Go to https://elevenlabs.io/
2. Sign up for a free account (10,000 credits/month)
3. Navigate to Profile → API Keys
4. Generate a new API key

### Step 2: Set Environment Variable

**Windows (PowerShell)**:
```powershell
[System.Environment]::SetEnvironmentVariable('ELEVENLABS_API_KEY', 'your-key-here', 'User')
```

**Windows (System Properties)**:
1. Search "Environment Variables" in Start menu
2. Click "Environment Variables"
3. Under "User variables", click "New"
4. Variable name: `ELEVENLABS_API_KEY`
5. Variable value: Your API key
6. Click OK

**macOS/Linux**:
```bash
export ELEVENLABS_API_KEY="your-key-here"
# Add to ~/.bashrc or ~/.zshrc for persistence
```

### Step 3: Install UV (if needed)

**macOS/Linux**:
```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

**Windows**:
```powershell
irm https://astral.sh/uv/install.ps1 | iex
```

Or install via pip:
```bash
pip install uv
```

### Step 4: Restart Cursor

Close Cursor completely and reopen for the MCP server to load.

---

## 🎯 Core Features

### 1. **Text-to-Speech** 🎤

Convert text to natural-sounding speech in multiple voices:

```
Generate speech: "Welcome to the Pip-Boy 3000. All systems operational."
Use voice "Antoni" for a professional narrator
Create audio in the voice of "Rachel" with emotion
```

**Use Cases**:
- App tutorial voiceovers
- Character dialogue for games
- Audio notifications
- Accessibility features

### 2. **Voice Design** 🎭

Create custom AI voices from text descriptions:

```
Design a voice: "A wise, ancient dragon with a deep, rumbling voice 
that sounds like distant thunder"

Create three voice variations for a film noir detective character
```

**Perfect for**:
- Game characters
- Virtual assistants
- Audiobook narrators
- Brand voices

### 3. **Voice Cloning** 👤

Clone voices from audio samples:

```
Clone my voice from this recording
Create a voice profile that sounds like a medieval knight
```

**Applications**:
- Personal voice assistants
- Custom TTS for accessibility
- Character voice consistency
- Brand voice identity

### 4. **Speech-to-Text** 📝

Transcribe audio with speaker identification:

```
Transcribe this audio file with speaker labels
Convert this recording to text and identify different speakers
```

**Uses**:
- Meeting transcription
- Podcast notes
- Interview documentation
- Accessibility captions

### 5. **Sound Effects & Soundscapes** 🎵

Generate custom sound effects and ambient audio:

```
Create a soundscape of a thunderstorm in a dense jungle with animals
Generate the sound of a Geiger counter clicking
Create retro terminal beep sounds for UI feedback
```

**Perfect for**:
- Game audio
- App sound effects
- Ambient backgrounds
- UI feedback sounds

### 6. **Audio Processing** 🔧

Isolate vocals, remove noise, and enhance audio:

```
Remove background noise from this recording
Isolate the vocals from this audio file
Enhance the audio quality of this voice recording
```

---

## 💡 Use Cases for Pip-Droid

### **Use Case 1: Quest Audio Narration**

```
You: "Generate audio for quest completion: 'Quest completed. 
      You gained 50 XP and 25 Caps. Well done, wanderer.'"
AI:  [Generates speech in Fallout-style narrator voice]
     [Saves to docs/audio/quest_complete.mp3]
     ✅ Audio ready for integration
```

**Implementation**:
- Add audio feedback to quest completion
- Narrate quest objectives
- Voice character dialogue

### **Use Case 2: Terminal Audio Effects**

```
You: "Create retro computer beep sounds for terminal commands"
AI:  [Generates authentic 1980s terminal sounds]
     [Creates: keypress.mp3, command_execute.mp3, error.mp3]
     ✅ Terminal sound effects ready
```

**Enhancement**:
- Terminal command feedback
- Error/success sounds
- Boot sequence audio

### **Use Case 3: Radio Station Voiceovers**

```
You: "Create a 50s-style radio DJ voice saying: 'You're listening 
      to Galaxy News Radio, bringing you the truth, no matter how bad it hurts'"
AI:  [Generates authentic radio DJ voice]
     [Saves to docs/audio/radio_intro.mp3]
     ✅ Radio station ident ready
```

**Features**:
- Custom radio station intros
- DJ voiceovers
- News bulletins
- Station identification

### **Use Case 4: S.P.E.C.I.A.L. Stats Audio**

```
You: "Generate level-up audio: 'Congratulations! Your Strength 
      has increased to level 5. You can now carry more weight.'"
AI:  [Creates celebratory announcement voice]
     [Saves to docs/audio/levelup_strength.mp3]
     ✅ Level-up audio notification ready
```

**Implementation**:
- Stat increase announcements
- Achievement unlocks
- Skill progression feedback

### **Use Case 5: Tutorial Voiceover**

```
You: "Create a helpful guide voice explaining: 'Tap STATUS to 
      view your S.P.E.C.I.A.L. stats. Tap QUESTS to track your missions.'"
AI:  [Generates friendly tutorial narrator]
     [Saves to docs/audio/tutorial_intro.mp3]
     ✅ Tutorial audio ready
```

**Perfect for**:
- First-time user onboarding
- Feature explanations
- Contextual help
- Accessibility mode

---

## 🎨 Voice Library Examples

### **Fallout-Style Voices**

| Voice | Description | Best For |
|-------|-------------|----------|
| **Gideon** | Wise, authoritative narrator | Quest narration, announcements |
| **Rachel** | Warm, friendly guide | Tutorials, helpful tips |
| **Antoni** | Professional, clear | System messages, notifications |
| **Domi** | Energetic, upbeat | Radio DJ, entertainment |
| **Adam** | Deep, masculine | Combat messages, warnings |

### **Custom Voice Design Prompts**

```
"A mysterious vault computer AI with a slightly glitchy, robotic quality"
"An old-time radio announcer from the 1950s with warm, nostalgic tone"
"A grizzled wasteland survivor with a rough, weathered voice"
"An enthusiastic Vault-Tec representative with corporate cheerfulness"
"A haunting, ethereal voice that sounds like distant echoes"
```

---

## 🛠️ Available MCP Tools

### **1. generate_speech**
Convert text to speech with customizable voices

**Parameters**:
- `text` - Text to convert to speech
- `voice_id` - Voice to use (optional)
- `model_id` - Model quality (optional)
- `output_format` - Audio format (mp3, wav, etc.)

**Example**:
```
Generate speech with Antoni's voice: "Welcome to Pip-Droid"
```

### **2. design_voice**
Create custom voices from text descriptions

**Parameters**:
- `description` - Detailed voice description
- `name` - Name for the voice
- `accent` - Accent preference (optional)

**Example**:
```
Design a voice: "Deep, commanding military officer with slight southern accent"
```

### **3. clone_voice**
Clone a voice from audio samples

**Parameters**:
- `audio_files` - Audio samples for cloning
- `name` - Name for cloned voice
- `description` - Voice description

**Example**:
```
Clone voice from my recording: recording.mp3
```

### **4. transcribe_audio**
Convert speech to text with speaker identification

**Parameters**:
- `audio_file` - Audio file to transcribe
- `identify_speakers` - Enable speaker identification

**Example**:
```
Transcribe this meeting recording with speaker labels
```

### **5. generate_sound_effect**
Create custom sound effects

**Parameters**:
- `description` - Detailed sound description
- `duration_seconds` - Length of audio

**Example**:
```
Generate sound: "Geiger counter clicking slowly, then rapidly"
```

### **6. isolate_vocals**
Remove background music/noise from audio

**Parameters**:
- `audio_file` - Audio file to process

**Example**:
```
Remove background noise from this voice recording
```

---

## 🔧 Configuration Options

### **Output Modes**

Configure via `ELEVENLABS_MCP_OUTPUT_MODE`:

**1. `files` (Default)** - Save to disk:
```json
{
  "ELEVENLABS_MCP_OUTPUT_MODE": "files"
}
```
- Audio saved to `docs/audio/`
- Returns file paths
- Best for local development

**2. `resources`** - Return as MCP resources:
```json
{
  "ELEVENLABS_MCP_OUTPUT_MODE": "resources"
}
```
- Audio returned as base64 data
- No disk I/O
- Best for cloud/serverless

**3. `both`** - Save AND return:
```json
{
  "ELEVENLABS_MCP_OUTPUT_MODE": "both"
}
```
- Audio saved to disk AND returned as resources
- Maximum flexibility
- Access via `elevenlabs://filename` URI

### **Base Path**

Set output directory via `ELEVENLABS_MCP_BASE_PATH`:
```json
{
  "ELEVENLABS_MCP_BASE_PATH": "${workspaceFolder}/docs/audio"
}
```

### **Data Residency** (Enterprise)

Specify region via `ELEVENLABS_API_RESIDENCY`:
```json
{
  "ELEVENLABS_API_RESIDENCY": "eu"  // or "us"
}
```

---

## 💰 Pricing & Credits

### **Free Tier**
- **10,000 credits/month**
- ~10,000 characters of speech
- Access to all voices
- Commercial use allowed

### **Paid Plans**
- **Starter**: 30,000 credits/month ($5)
- **Creator**: 100,000 credits/month ($22)
- **Pro**: 500,000 credits/month ($99)
- **Enterprise**: Custom pricing

**Credit Usage**:
- Text-to-Speech: ~1 credit per character
- Voice Design: ~1,000 credits per voice
- Voice Cloning: ~2,000 credits
- Sound Effects: ~500 credits

---

## 🎯 Complete Workflow Examples

### **Workflow 1: Complete App Audio Suite**

```
Step 1: Plan Audio Strategy [PromptX PM]
   You: "I need a product manager - what audio features should Pip-Droid have?"
   AI:  [Analyzes audio opportunities, prioritizes features]

Step 2: Design Voice Identity [PromptX UX + ElevenLabs]
   You: "Design a Vault-Tec computer AI voice"
   AI:  [ElevenLabs designs custom voice]
        [Saves voice to library]

Step 3: Generate System Audio
   You: "Generate all system sounds: boot, error, success, warning"
   AI:  [Creates 4 audio files with consistent voice]
        [Saves to docs/audio/system/]

Step 4: Create Quest Audio
   You: "Generate quest completion audio for each category"
   AI:  [Creates narration for MAIN, SIDE, DAILY quests]
        [Saves to docs/audio/quests/]

Step 5: Radio Station Content
   You: "Create radio station intro and 3 DJ transitions"
   AI:  [Generates authentic 50s radio voice]
        [Saves to docs/audio/radio/]

Step 6: Integrate with Desktop Commander
   You: "Copy audio files to Android project res/raw directory"
   AI:  [Desktop Commander copies files]
        [Updates resource references]

Result: Complete professional audio suite for Pip-Droid!
```

### **Workflow 2: Interactive Tutorial**

```
Step 1: Script with PromptX Writer
   You: "Activate Writer - create tutorial script for Pip-Droid"
   AI:  [Writes engaging, conversational tutorial]

Step 2: Generate Narration with ElevenLabs
   You: "Convert tutorial script to audio with friendly voice"
   AI:  [Generates professional tutorial narration]

Step 3: Create Visuals with Pollinations
   You: "@pollinations-image Generate tutorial screen mockups"
   AI:  [Creates visual guides]

Step 4: Implement with Desktop Commander
   You: "Create TutorialScreen.kt with audio playback"
   AI:  [Implements complete tutorial feature]

Result: Professional tutorial with voice + visuals!
```

---

## 🎨 Creative Examples

### **Example 1: Character Dialogue System**

```
You: "Design 5 unique voices for different Pip-Droid characters:
      1. Vault-Tec AI - corporate, cheerful
      2. Wasteland Guide - gruff, experienced
      3. Radio DJ - smooth, 50s announcer
      4. Security Alert - urgent, robotic
      5. Companion - friendly, supportive"

AI:  [Designs all 5 voices]
     [Saves to voice library]
     
You: "Generate sample dialogue for each character"
AI:  [Creates 5 audio samples demonstrating each voice]
     ✅ Complete character voice library ready
```

### **Example 2: Ambient Soundscapes**

```
You: "Create 3 ambient soundscapes for Pip-Droid backgrounds:
      1. Vault interior - computer hums, distant machinery
      2. Wasteland - wind, distant wildlife, Geiger clicks
      3. Radio static - authentic 50s radio interference"

AI:  [Generates 3 immersive soundscapes]
     [Each 30 seconds, loopable]
     ✅ Ambient audio ready for background playback
```

### **Example 3: Dynamic Notifications**

```
You: "Generate audio for all notification types:
      - Quest completed (+XP, +Caps)
      - Quest failed
      - Stat increased
      - Level up
      - Achievement unlocked
      - Low battery (in character!)
      - New message
      Use Vault-Tec AI voice"

AI:  [Creates 8 notification audio files]
     [Consistent voice across all]
     ✅ Complete notification audio system
```

---

## 🆚 ElevenLabs vs Alternatives

| Feature | ElevenLabs | Google TTS | Amazon Polly |
|---------|-----------|------------|--------------|
| **Voice Quality** | ⭐⭐⭐⭐⭐ Excellent | ⭐⭐⭐ Good | ⭐⭐⭐ Good |
| **Voice Cloning** | ✅ Yes | ❌ No | ❌ No |
| **Voice Design** | ✅ Yes | ❌ No | ❌ No |
| **Sound Effects** | ✅ Yes | ❌ No | ❌ No |
| **Free Tier** | ✅ 10k credits | ✅ Limited | ✅ Limited |
| **MCP Integration** | ✅ Official | ❌ None | ❌ None |
| **Languages** | 29+ | 40+ | 29+ |

---

## 🐛 Troubleshooting

### Issue 1: MCP Server Not Loading

**Symptoms**: `@elevenlabs` doesn't appear in Cursor  
**Solutions**:
1. Install `uv`: `pip install uv`
2. Verify API key is set: `echo $env:ELEVENLABS_API_KEY` (Windows)
3. Restart Cursor completely
4. Check logs: `%APPDATA%\Claude\logs\mcp-server-elevenlabs.log` (Windows)

### Issue 2: "ENOENT uvx" Error

**Symptoms**: "spawn uvx ENOENT" error  
**Solutions**:
1. Find uvx path: `which uvx` (Mac/Linux) or `where uvx` (Windows)
2. Update config with absolute path: `"command": "/full/path/to/uvx"`

### Issue 3: API Key Not Found

**Symptoms**: Authentication errors  
**Solutions**:
- Verify key in environment: `echo $env:ELEVENLABS_API_KEY`
- Set in `.cursor/mcp.json` directly (less secure)
- Restart Cursor after setting environment variable

### Issue 4: Timeout Errors

**Symptoms**: Operations timeout in MCP Inspector  
**Solutions**:
- This is normal for long operations (voice design, audio isolation)
- Works fine in Claude Desktop/Cursor (longer timeouts)
- Check `docs/audio/` for output even if timeout occurs

### Issue 5: Credit Exhausted

**Symptoms**: "Insufficient credits" errors  
**Solutions**:
- Check usage: https://elevenlabs.io/usage
- Upgrade plan or wait for monthly reset
- Free tier: 10k credits resets monthly

---

## 💎 Pro Tips

### **1. Voice Library Management**

Build a consistent voice library for your app:
```
Design 3-5 core voices for different purposes
Save them with descriptive names
Use consistently across features
```

### **2. Optimize Credit Usage**

- Cache generated audio files
- Reuse common phrases
- Batch generate similar content
- Use shorter voices for UI feedback

### **3. Combine with Other MCP Servers**

```
[PromptX Writer] → Write script
[ElevenLabs] → Generate narration
[Desktop Commander] → Integrate audio
[Pollinations] → Create matching visuals
```

### **4. Quality Settings**

- Use `eleven_multilingual_v2` for best quality
- Use `eleven_turbo_v2` for faster generation
- Higher quality = more credits

### **5. Audio Format**

- MP3: Smaller files, good quality
- WAV: Highest quality, larger files
- Use MP3 for most app audio

---

## 📚 Additional Resources

- **Official Docs**: https://elevenlabs.io/docs
- **Voice Library**: https://elevenlabs.io/voice-library
- **API Reference**: https://elevenlabs.io/docs/api-reference
- **Community**: https://discord.gg/elevenlabs
- **Blog**: https://elevenlabs.io/blog

---

## 🎯 Next Steps for Pip-Droid

After restarting Cursor:

1. **Generate System Audio**:
   ```
   Create boot sequence audio: "Pip-Boy 3000 Mark IV. Initiating..."
   Generate error sound effect
   Create success chime
   ```

2. **Voice Identity**:
   ```
   Design a Vault-Tec computer AI voice for all system messages
   ```

3. **Quest Audio**:
   ```
   Generate quest completion audio for different categories
   ```

4. **Radio Content**:
   ```
   Create radio station identification audio
   Generate DJ transition audio
   ```

5. **Tutorial Narration**:
   ```
   Generate tutorial voiceover explaining app features
   ```

---

**☢️ Give your Pip-Droid a voice with professional AI audio! ☢️**

Your app just gained Hollywood-quality voice capabilities! 🎙️🎬🔊

