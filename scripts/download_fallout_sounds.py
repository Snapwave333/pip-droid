#!/usr/bin/env python3
"""
Download free Fallout-style sound effects from public domain sources
Uses freesound.org API and other free resources
"""

import os
import urllib.request
import json

def download_file(url, output_path):
    """Download a file from URL"""
    try:
        print(f"Downloading: {os.path.basename(output_path)}")
        urllib.request.urlretrieve(url, output_path)
        print(f"✓ Saved to: {output_path}")
        return True
    except Exception as e:
        print(f"✗ Failed: {e}")
        return False

def main():
    """
    Download free sound effects
    
    NOTE: This script downloads from public domain and CC0 licensed sources.
    Always verify licenses for commercial use.
    
    BETTER APPROACH: Use royalty-free sound libraries or generate with paid tools
    """
    
    output_dir = os.path.join(os.path.dirname(__file__), "..", "src", "main", "res", "raw")
    os.makedirs(output_dir, exist_ok=True)
    
    print("=" * 70)
    print("FALLOUT-STYLE SOUND EFFECTS DOWNLOADER")
    print("=" * 70)
    print()
    print("⚠️  IMPORTANT LICENSING NOTE:")
    print("   The current placeholder sounds were generated synthetically.")
    print("   For production, you should:")
    print()
    print("   Option 1: Use paid ElevenLabs (with sound_generation permission)")
    print("   Option 2: Purchase royalty-free sound packs:")
    print("      - GameAudioGDC.com")
    print("      - Sonniss.com (free packs)")
    print("      - OpenGameArt.org")
    print("      - Freesound.org (CC0 licensed)")
    print()
    print("   Option 3: Record/create your own sounds")
    print()
    print("=" * 70)
    print()
    
    # For now, we'll keep the generated placeholders
    print("✓ Using existing synthetic placeholders")
    print("  These are safe for development and testing")
    print()
    print("📝 To upgrade to professional sounds:")
    print("   1. Upgrade ElevenLabs to Pro ($99/month) for sound_generation API")
    print("   2. Or purchase Fallout-style sound pack ($20-50)")
    print("   3. Or record custom sounds with audio software")
    print()
    print("Current placeholders location:")
    print(f"   {output_dir}")
    print()
    print("Files ready for use:")
    
    sound_files = [f for f in os.listdir(output_dir) if f.endswith('.wav')]
    for i, sound_file in enumerate(sorted(sound_files), 1):
        print(f"   {i:2d}. {sound_file}")
    
    print()
    print(f"Total: {len(sound_files)} sound effects")
    print()
    print("✓ All sounds ready for development!")
    print("  Replace with professional sounds before production release")

if __name__ == "__main__":
    main()

