#!/usr/bin/env python3
"""
Pip-Droid Image Generator
Generates repository images using Google Gemini API
"""

import os
import sys
from pathlib import Path

try:
    from google import genai
    from google.genai import types
    from PIL import Image
    from io import BytesIO
except ImportError:
    print("Error: Required packages not installed.")
    print("Install with: pip install google-genai pillow")
    sys.exit(1)

# Configuration
OUTPUT_DIR = Path(__file__).parent.parent / "docs" / "images"
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Gemini API client
# Note: Set your API key as environment variable: GEMINI_API_KEY
client = genai.Client()

# Image prompts
PROMPTS = {
    "banner": {
        "prompt": """Create a wide banner in retro-futuristic style inspired by Fallout's Pip-Boy interface. 
        Use a monochrome green color palette (#00FF00) with glowing UI elements, circuit overlays, 
        and a stylized vault background. Include the text 'Pip-Droid Launcher' in bold pixel-style 
        font, centered. Add subtle scanlines and CRT distortion for authenticity. Wide banner format, 
        professional quality, dark atmosphere.""",
        "filename": "banner.png"
    },
    "logo": {
        "prompt": """Design a circular logo for an Android launcher called Pip-Droid. Style it like a glowing 
        green Pip-Boy interface button, with a gear or vault door motif in the center. Use pixel 
        art and retro tech textures. Keep it simple, iconic, and readable at small sizes. Monochrome 
        green (#00FF00) on black background. Circular composition, high contrast.""",
        "filename": "logo.png"
    },
    "boot_sequence": {
        "prompt": """Generate a mockup of a Fallout-style terminal boot sequence on an Android phone screen. 
        Show 'ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL' at top, followed by system initialization 
        messages in monochrome green text (#00FF00) on black background. Include a blinking cursor at bottom. 
        Add scanline overlay. Phone screen aspect ratio (9:20), retro computer terminal aesthetic.""",
        "filename": "boot_sequence.png"
    },
    "main_interface": {
        "prompt": """Create a mockup of a Pip-Boy–style Android home screen with six bottom navigation tabs 
        labeled STATUS, INV, DATA, MAP, RADIO, HOLOTAPES. Show main content area with system 
        status display in monochrome green (#00FF00). Include time, battery, and system info. Add CRT 
        scanline overlay. Phone screen aspect ratio, futuristic UI design.""",
        "filename": "main_interface.png"
    },
    "stats_screen": {
        "prompt": """Generate a mockup showing Fallout S.P.E.C.I.A.L. stats on a Pip-Boy screen. Display 7 
        stats (Strength, Perception, Endurance, Charisma, Intelligence, Agility, Luck) with level 
        numbers, XP progress bars, and expandable descriptions. Monochrome green text (#00FF00) on black. 
        Add CRT effects. Phone screen format, RPG stat display.""",
        "filename": "stats_screen.png"
    },
    "quest_log": {
        "prompt": """Create a Pip-Boy quest log screen mockup showing 3-4 quests in a list. Each quest should 
        have title, category tag (MAIN/SIDE), progress bar, and checkbox objectives. Include '+' 
        button to add quests. Monochrome green (#00FF00) on black with CRT scanlines. Phone screen, 
        retro UI design, quest management interface.""",
        "filename": "quest_log.png"
    },
    "terminal": {
        "prompt": """Generate a retro terminal interface mockup for a Pip-Boy. Show command prompt '>' with 
        example commands like 'help', 'status', 'quests'. Display terminal output in monospace 
        green text (#00FF00) on black background. Add heavy CRT scanlines and glow effect. Phone screen, 
        classic computer terminal aesthetic.""",
        "filename": "terminal.png"
    },
    "radio": {
        "prompt": """Create a Pip-Boy radio interface showing a list of Fallout radio stations (Galaxy News Radio, 
        Radio New Vegas, Diamond City Radio, etc.). Include play/pause/stop buttons and volume slider. 
        Monochrome green (#00FF00) on black with retro gauges and dials. Add CRT effects. Phone screen, 
        vintage radio tuner design.""",
        "filename": "radio.png"
    }
}


def generate_image(name, prompt, filename):
    """Generate a single image using Gemini API"""
    print(f"\n🎨 Generating {name}...")
    print(f"   Prompt: {prompt[:80]}...")
    
    try:
        response = client.models.generate_content(
            model="gemini-2.5-flash-image-preview",
            contents=[prompt],
        )
        
        # Process response
        for part in response.candidates[0].content.parts:
            if part.text is not None:
                print(f"   Response: {part.text}")
            elif part.inline_data is not None:
                # Save image
                image = Image.open(BytesIO(part.inline_data.data))
                output_path = OUTPUT_DIR / filename
                image.save(output_path)
                print(f"   ✅ Saved to: {output_path}")
                return True
        
        print(f"   ⚠️  No image data received")
        return False
        
    except Exception as e:
        print(f"   ❌ Error: {e}")
        return False


def main():
    """Main execution"""
    print("=" * 70)
    print("Pip-Droid Repository Image Generator")
    print("Using Google Gemini API")
    print("=" * 70)
    
    # Check API key
    if not os.getenv("GEMINI_API_KEY"):
        print("\n⚠️  Warning: GEMINI_API_KEY environment variable not set")
        print("Set it with: export GEMINI_API_KEY='your_key_here'")
        print("\nAttempting to use default client configuration...")
    
    # Generate all images
    results = {}
    for name, config in PROMPTS.items():
        success = generate_image(name, config["prompt"], config["filename"])
        results[name] = success
    
    # Summary
    print("\n" + "=" * 70)
    print("Generation Summary:")
    print("=" * 70)
    
    successful = sum(1 for v in results.values() if v)
    total = len(results)
    
    for name, success in results.items():
        status = "✅" if success else "❌"
        print(f"{status} {name}: {PROMPTS[name]['filename']}")
    
    print(f"\nTotal: {successful}/{total} images generated successfully")
    
    if successful < total:
        print("\n⚠️  Some images failed to generate.")
        print("You can generate them manually or try running again.")
    
    print("\n📁 Output directory:", OUTPUT_DIR.absolute())
    print("=" * 70)


if __name__ == "__main__":
    main()

