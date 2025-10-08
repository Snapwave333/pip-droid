# 🔊 Freesound MCP Server Guide

## 🌟 Access 500,000+ Professional Sound Effects for FREE!

**Freesound** is a collaborative database of Creative Commons licensed sounds with over **500,000 professional sound effects** available for free!

**Official Website**: https://freesound.org  
**API Documentation**: https://freesound.org/docs/api/

---

## 🎯 What is Freesound?

Freesound is a massive collaborative database where people from around the world upload and share sound effects. It's like GitHub for audio!

### **Features**:
- 🆓 **Completely FREE** (no credit card required)
- 📚 **500,000+ sounds** and growing daily
- ⚖️ **Legal for commercial use** (CC0 and CC-BY licenses)
- 🔍 **Advanced search** by tags, duration, license
- 🎵 **High quality** professional recordings
- 📥 **Direct download** via API
- 👥 **Community driven** with ratings and reviews

---

## 🚀 Quick Start

### **Step 1: Get FREE API Key**

1. Go to https://freesound.org/apiv2/apply/
2. Create account (or login)
3. Click "Apply for an API key"
4. Fill in basic info (instant approval!)
5. Copy your API key

### **Step 2: Set Environment Variable**

**Windows PowerShell**:
```powershell
$env:FREESOUND_API_KEY="your_api_key_here"
# Or set permanently in System Environment Variables
```

**macOS/Linux**:
```bash
export FREESOUND_API_KEY="your_api_key_here"
# Add to ~/.bashrc or ~/.zshrc for persistence
```

### **Step 3: Restart Cursor**

Close Cursor completely and reopen for the server to load.

---

## 🔧 Configuration

Your `.cursor/mcp.json` now includes:

```json
{
  "freesound": {
    "command": "python",
    "args": ["${workspaceFolder}/mcp_server_freesound.py"],
    "env": {
      "FREESOUND_API_KEY": "${env:FREESOUND_API_KEY}"
    }
  }
}
```

**Requirements**:
- Python 3.8+
- `fastmcp` library (auto-installed)
- Internet connection
- Freesound API key (free)

---

## 🛠️ Available Tools

### **1. search_sounds** - Find Sound Effects

Search Freesound's massive library:

```
Search for "mechanical button click" sounds with CC0 license
```

**Parameters**:
- `query`: Search query (e.g., "button click", "terminal beep")
- `filter_license`: "CC0" (public domain), "CC-BY" (attribution), "all"
- `max_results`: 1-150 results
- `min_duration`: Minimum length in seconds
- `max_duration`: Maximum length in seconds
- `sort`: "rating_desc", "downloads_desc", "created_desc"

**Example**:
```
Search for retro computer beep sounds, CC0 license, 
0.1 to 1.0 seconds, top 5 results
```

### **2. download_sound** - Download a Sound Effect

Download by sound ID from search results:

```
Download sound ID 12345 to src/main/res/raw/button_click.wav
```

**Parameters**:
- `sound_id`: Freesound sound ID (from search)
- `output_path`: Where to save the file
- `use_preview`: Use MP3 preview (smaller, faster)

**Example**:
```
Download sound 54321 as preview to src/main/res/raw/terminal_beep.mp3
```

### **3. get_sound_details** - Get Metadata

Get complete information about a sound:

```
Get details for sound ID 12345
```

Returns: duration, bitrate, sample rate, license, ratings, tags, preview URLs, etc.

### **4. download_pip_boy_sounds** - Automated Download

Download a curated set of Pip-Boy style sounds automatically:

```
Download curated Pip-Boy sound effects to src/main/res/raw/
```

**What it does**:
- Searches for 11 essential sounds
- Downloads highest-rated CC0 sounds
- Saves to specified directory
- Returns summary with file sizes

**Sounds downloaded**:
1. button_click
2. terminal_type
3. terminal_beep
4. terminal_boot
5. page_turn
6. success_beep
7. error_beep
8. radio_static
9. radio_on
10. quest_complete
11. level_up

### **5. get_api_status** - Check Connection

Verify your API key and connection:

```
Check Freesound API status
```

Returns: Connection status, username, sounds count, troubleshooting.

---

## 💡 Usage Examples for Pip-Droid

### **Example 1: Find Button Click Sound**

```
Search Freesound for "mechanical button click computer" sounds, 
CC0 license only, 0.1 to 0.5 seconds, top 3 results
```

**AI Response**:
```json
{
  "total_found": 245,
  "returned": 3,
  "sounds": [
    {
      "id": 341695,
      "name": "Button Click Mechanical.wav",
      "duration": 0.18,
      "license": "Creative Commons 0",
      "download_url": "...",
      "preview_url": "...",
      "username": "InspectorJ"
    },
    ...
  ]
}
```

### **Example 2: Download Specific Sound**

```
Download Freesound sound 341695 to src/main/res/raw/button_click.wav
```

**AI Response**:
```json
{
  "success": true,
  "name": "Button Click Mechanical.wav",
  "file_path": "src/main/res/raw/button_click.wav",
  "file_size_kb": 15.6,
  "message": "Downloaded successfully!"
}
```

### **Example 3: Batch Download All Sounds**

```
Download curated Pip-Boy sound effects using Freesound
```

**AI Response**:
```json
{
  "downloaded": [
    {
      "name": "button_click",
      "file_path": "src/main/res/raw/button_click.mp3",
      "size_kb": 12.4,
      "license": "Creative Commons 0"
    },
    ...
  ],
  "total_size_kb": 156.8,
  "summary": "Downloaded 11/11 sounds"
}
```

### **Example 4: Search for Specific Style**

```
Search Freesound for "retro computer terminal beep" sounds,
CC0 license, 0.2 to 2.0 seconds, sorted by rating
```

### **Example 5: Get Sound Info Before Download**

```
Get details for Freesound sound 341695
```

Returns full metadata including waveform, ratings, tags, and all preview formats.

---

## 🎨 Best Search Queries for Pip-Boy Sounds

### **UI Sounds**:
- "mechanical button click"
- "computer button press"
- "switch toggle click"
- "interface beep"
- "panel slide mechanical"

### **Terminal Sounds**:
- "keyboard mechanical single key"
- "computer beep short"
- "terminal beep"
- "computer startup boot"
- "electronic beep"

### **Radio Sounds**:
- "radio static noise"
- "radio tuning dial"
- "radio power on"
- "AM radio static"
- "white noise short"

### **Quest/Achievement Sounds**:
- "success chime"
- "achievement unlock"
- "level up power"
- "quest complete"
- "victory fanfare short"

### **Ambient Sounds**:
- "geiger counter click"
- "mechanical hum"
- "computer hum ambient"
- "vault atmosphere"

---

## ⚖️ License Information

Freesound offers multiple license types:

### **CC0 (Public Domain)** ✅ BEST FOR COMMERCIAL
- **Use freely** for any purpose
- **No attribution required**
- **Safest for commercial apps**
- Filter: `filter_license="CC0"`

### **CC-BY (Attribution)** ✅ GOOD FOR COMMERCIAL
- **Use freely** for any purpose
- **Attribution required** (credit the author)
- Most common license on Freesound
- Filter: `filter_license="CC-BY"`

### **CC-BY-NC (Non-Commercial)** ⚠️ PERSONAL USE ONLY
- **Free for personal/non-commercial** use
- **Cannot use in paid apps**
- Avoid for Pip-Droid if selling
- Filter: `filter_license="CC-BY-NC"`

**Recommendation**: Use `filter_license="CC0"` for Pip-Droid to avoid attribution requirements and ensure commercial use rights.

---

## 🎯 Complete Workflow for Pip-Droid

### **Step-by-Step: Replace All Sounds**

```
# Step 1: Check API connection
Check Freesound API status

# Step 2: Download all essential sounds
Download curated Pip-Boy sound effects to src/main/res/raw/

# Step 3: Search for additional sounds if needed
Search Freesound for "geiger counter tick" CC0, 0.05 to 0.2 seconds

# Step 4: Download specific sound
Download Freesound sound [ID] to src/main/res/raw/geiger_click.wav

# Step 5: Verify all files
List files in src/main/res/raw/
```

---

## 📊 Comparison: Freesound vs ElevenLabs

| Feature | Freesound | ElevenLabs Sound Gen |
|---------|-----------|----------------------|
| **Cost** | FREE | $99/month |
| **Sounds Available** | 500,000+ | Unlimited (AI generated) |
| **Quality** | Professional recordings | AI-generated |
| **License** | CC0, CC-BY | Full commercial rights |
| **Customization** | Search & filter | Generate from text |
| **Setup Time** | 2 minutes | Instant (if subscribed) |
| **Best For** | Budget projects, variety | Custom sounds, high budget |

**Verdict**: Freesound is perfect for Pip-Droid! Professional quality, free, and legal for commercial use.

---

## 🐛 Troubleshooting

### **Issue 1: "FREESOUND_API_KEY not set"**

**Solution**:
1. Get API key: https://freesound.org/apiv2/apply/
2. Set environment variable
3. Restart Cursor

### **Issue 2: "API Error 401: Unauthorized"**

**Solution**:
- API key is invalid or expired
- Get new key from Freesound
- Check for typos in environment variable

### **Issue 3: "No results found"**

**Solution**:
- Try different search terms
- Remove duration filters
- Change license filter to "all"
- Use broader search queries

### **Issue 4: "Download failed"**

**Solution**:
- Check internet connection
- Verify output directory exists
- Try downloading preview (use_preview=True)
- Check file permissions

### **Issue 5: Server not loading**

**Solution**:
1. Check Python is installed: `python --version`
2. Install fastmcp: `pip install fastmcp`
3. Verify `mcp_server_freesound.py` exists
4. Restart Cursor completely

---

## 💎 Pro Tips

### **1. Use CC0 for Commercial Apps**
Always filter by CC0 license to avoid attribution requirements:
```
filter_license="CC0"
```

### **2. Download Previews for Testing**
MP3 previews are smaller and faster:
```
use_preview=True
```

### **3. Sort by Rating**
Get the best sounds first:
```
sort="rating_desc"
```

### **4. Batch Download**
Use the automated downloader for quick setup:
```
Download curated Pip-Boy sounds
```

### **5. Search by Duration**
Filter by length to get exactly what you need:
```
min_duration=0.1, max_duration=1.0
```

### **6. Check Sound Details First**
Preview before downloading:
```
Get details for sound [ID]
```

### **7. Create Custom Search Presets**
Save your favorite searches in the guide for quick reuse.

---

## 📈 Statistics

- **Total Sounds**: 500,000+
- **Daily Uploads**: ~100-200 new sounds
- **CC0 Sounds**: ~150,000
- **Average File Size**: 200KB - 2MB
- **API Rate Limit**: 60 requests/minute
- **Formats**: WAV, MP3, FLAC, OGG

---

## 🔗 Resources

- **Website**: https://freesound.org
- **API Docs**: https://freesound.org/docs/api/
- **Apply for Key**: https://freesound.org/apiv2/apply/
- **Browse Sounds**: https://freesound.org/browse/
- **Popular Packs**: https://freesound.org/browse/packs/
- **Forums**: https://freesound.org/forum/

---

## 🎯 Next Steps

After Cursor restart:

1. **Test Connection**:
   ```
   Check Freesound API status
   ```

2. **Download Sounds**:
   ```
   Download curated Pip-Boy sound effects
   ```

3. **Replace Placeholders**:
   - All 27 sounds downloaded automatically
   - Professional CC0 licensed
   - Ready to use in app

4. **Build and Test**:
   ```
   gradle assembleDebug
   ```

---

**🔊 500,000+ professional sound effects at your fingertips - for FREE!**

Your Pip-Boy will sound amazing! 🎵☢️✨

