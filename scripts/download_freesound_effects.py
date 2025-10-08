#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Download Professional Pip-Boy Sound Effects from Freesound
"""

import os
import sys
import json
import urllib.request
import urllib.parse
import time

# Fix Unicode output on Windows
if sys.platform == "win32":
    import io
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# Get API key from environment
API_KEY = os.environ.get("FREESOUND_API_KEY", "")
if not API_KEY:
    print("ERROR: FREESOUND_API_KEY environment variable not set!")
    sys.exit(1)

BASE_URL = "https://freesound.org/apiv2"
OUTPUT_DIR = "src/main/res/raw"

# Curated search queries for Pip-Boy sounds
SOUND_SEARCHES = {
    "button_click": "mechanical button click computer",
    "button_hover": "interface hover beep short",
    "tab_switch": "interface switch tab",
    "panel_open": "panel slide mechanical open",
    "panel_close": "panel slide mechanical close",
    "page_turn": "interface swoosh transition",
    "scroll": "interface scroll short",
    "back_button": "interface back button",
    "terminal_type": "keyboard key press mechanical single",
    "terminal_enter": "keyboard enter return",
    "terminal_error": "error beep short",
    "terminal_boot": "computer startup boot sequence retro",
    "quest_accept": "confirmation beep accept",
    "quest_complete": "success chime achievement",
    "objective_complete": "objective complete beep",
    "achievement_unlock": "achievement unlock fanfare",
    "level_up": "power up level up",
    "radio_static": "radio static white noise",
    "radio_on": "radio power on",
    "radio_off": "radio power off",
    "station_switch": "radio tuning dial",
    "geiger_click": "geiger counter click single",
    "vault_hum": "computer hum ambient loop",
    "error_beep": "error beep warning",
    "success_beep": "success beep confirmation",
    "alert": "alert beep urgent",
    "shutdown": "computer shutdown power down"
}

def make_api_request(endpoint, params=None):
    """Make request to Freesound API"""
    if params is None:
        params = {}
    params["token"] = API_KEY
    
    query_string = urllib.parse.urlencode(params)
    url = f"{BASE_URL}/{endpoint}?{query_string}"
    
    try:
        with urllib.request.urlopen(url) as response:
            return json.loads(response.read().decode())
    except Exception as e:
        print(f"API Error: {e}")
        return None

def search_sound(query, max_duration=3.0):
    """Search for a sound effect"""
    print(f"  🔍 Searching: {query}")
    
    params = {
        "query": query,
        "filter": f'duration:[0.1 TO {max_duration}] AND license:"Creative Commons 0"',
        "sort": "rating_desc",
        "page_size": 1,
        "fields": "id,name,download,previews,duration,license"
    }
    
    result = make_api_request("search/text/", params)
    
    if result and result.get("count", 0) > 0:
        sound = result["results"][0]
        print(f"  ✓ Found: {sound['name']} ({sound['duration']:.2f}s)")
        return sound
    else:
        print(f"  ✗ No results found")
        return None

def download_sound(sound, output_path):
    """Download sound file"""
    # Use preview (HQ MP3) for faster downloads
    download_url = sound["previews"]["preview-hq-mp3"]
    download_url_with_token = f"{download_url}?token={API_KEY}"
    
    try:
        os.makedirs(os.path.dirname(output_path), exist_ok=True)
        
        print(f"  📥 Downloading to: {output_path}")
        urllib.request.urlretrieve(download_url_with_token, output_path)
        
        file_size = os.path.getsize(output_path)
        print(f"  ✓ Downloaded: {file_size / 1024:.1f} KB")
        return True
    except Exception as e:
        print(f"  ✗ Download failed: {e}")
        return False

def main():
    print("=" * 80)
    print("FREESOUND PROFESSIONAL SOUND EFFECTS DOWNLOADER")
    print("=" * 80)
    print()
    
    # Test API connection
    print("Testing API connection...")
    me = make_api_request("me/")
    if me:
        print(f"✓ Connected as: {me.get('username', 'Unknown')}")
        print()
    else:
        print("✗ API connection failed!")
        sys.exit(1)
    
    # Download sounds
    results = {
        "downloaded": [],
        "failed": [],
        "total_size_kb": 0
    }
    
    print(f"Downloading {len(SOUND_SEARCHES)} sound effects...")
    print("=" * 80)
    print()
    
    for sound_name, search_query in SOUND_SEARCHES.items():
        print(f"[{len(results['downloaded']) + len(results['failed']) + 1}/{len(SOUND_SEARCHES)}] {sound_name}")
        
        # Search for sound
        sound = search_sound(search_query)
        
        if sound:
            # Download sound
            output_path = os.path.join(OUTPUT_DIR, f"{sound_name}.mp3")
            if download_sound(sound, output_path):
                file_size = os.path.getsize(output_path)
                results["downloaded"].append({
                    "name": sound_name,
                    "file": output_path,
                    "size_kb": file_size / 1024
                })
                results["total_size_kb"] += file_size / 1024
            else:
                results["failed"].append(sound_name)
        else:
            results["failed"].append(sound_name)
        
        print()
        time.sleep(0.5)  # Rate limiting
    
    # Summary
    print("=" * 80)
    print("DOWNLOAD COMPLETE!")
    print("=" * 80)
    print()
    print(f"✓ Downloaded: {len(results['downloaded'])}/{len(SOUND_SEARCHES)} sounds")
    print(f"✗ Failed: {len(results['failed'])} sounds")
    print(f"📦 Total Size: {results['total_size_kb']:.1f} KB")
    print()
    
    if results["failed"]:
        print("Failed sounds:")
        for name in results["failed"]:
            print(f"  - {name}")
        print()
    
    print("All sounds saved to: src/main/res/raw/")
    print()
    print("Next steps:")
    print("  1. Update SoundEffect.kt to use R.raw.[sound_name]")
    print("  2. Build the app: gradle assembleDebug")
    print("  3. Test sounds in emulator!")
    print()
    print("☢️ Your Pip-Boy sounds are now PROFESSIONAL! ☢️")

if __name__ == "__main__":
    main()

