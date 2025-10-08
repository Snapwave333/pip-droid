#!/usr/bin/env python3
"""
Simple Pip-Droid Image Generator using Gemini API
Based on user's provided example code
"""

import os
from pathlib import Path

# NOTE: You need to set your Gemini API key first
# Option 1: Set environment variable: GEMINI_API_KEY
# Option 2: Modify this script to include your key directly (not recommended for public repos)

try:
    from google import genai
    from google.genai import types
    from PIL import Image
    from io import BytesIO
except ImportError:
    print("ERROR: Required packages not installed!")
    print("\nInstall with:")
    print("  pip install google-genai pillow")
    print("\nOr using conda:")
    print("  conda install -c conda-forge pillow")
    print("  pip install google-genai")
    exit(1)

# Configuration
OUTPUT_DIR = Path(__file__).parent.parent / "docs" / "images"
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Initialize Gemini client
client = genai.Client()

# Image prompts for Pip-Droid repository
IMAGES = {
    "banner": """Create a wide banner in retro-futuristic style inspired by Fallout's Pip-Boy interface. 
Use a monochrome green color palette with glowing UI elements, circuit overlays, and a stylized vault 
background. Include the text 'Pip-Droid Launcher' in bold pixel-style font, centered. Add subtle 
scanlines and CRT distortion for authenticity.""",
    
    "logo": """Design a circular logo for an Android launcher called Pip-Droid. Style it like a glowing 
green Pip-Boy interface button, with a gear or vault door motif in the center. Use pixel art and retro 
tech textures. Keep it simple, iconic, and readable at small sizes.""",
    
    "boot_sequence": """Generate a mockup of a Fallout-style terminal boot sequence on an Android phone 
screen. Show 'ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL' at top, followed by system initialization messages 
in monochrome green text on black background. Include a blinking cursor at bottom. Add scanline overlay.""",
    
    "main_interface": """Create a mockup of a Pip-Boy–style Android home screen with six bottom navigation 
tabs labeled STATUS, INV, DATA, MAP, RADIO, HOLOTAPES. Show main content area with system status display 
in monochrome green. Include time, battery, and system info. Add CRT scanline overlay.""",
    
    "stats_screen": """Generate a mockup showing Fallout S.P.E.C.I.A.L. stats on a Pip-Boy screen. Display 
7 stats (Strength, Perception, Endurance, Charisma, Intelligence, Agility, Luck) with level numbers, XP 
progress bars, and expandable descriptions. Monochrome green text on black. Add CRT effects.""",
    
    "quest_log": """Create a Pip-Boy quest log screen mockup showing 3-4 quests in a list. Each quest 
should have title, category tag (MAIN/SIDE), progress bar, and checkbox objectives. Include '+' button 
to add quests. Monochrome green on black with CRT scanlines.""",
    
    "terminal": """Generate a retro terminal interface mockup for a Pip-Boy. Show command prompt '>' with 
example commands like 'help', 'status', 'quests'. Display terminal output in monospace green text on 
black background. Add heavy CRT scanlines and glow effect.""",
    
    "radio": """Create a Pip-Boy radio interface showing a list of Fallout radio stations (Galaxy News Radio, 
Radio New Vegas, Diamond City Radio, etc.). Include play/pause/stop buttons and volume slider. Monochrome 
green on black with retro gauges and dials. Add CRT effects."""
}

def generate_image(name, prompt):
    """Generate and save a single image"""
    print(f"\n{'='*70}")
    print(f"Generating: {name}.png")
    print(f"{'='*70}")
    print(f"Prompt: {prompt[:100]}...")
    
    try:
        response = client.models.generate_content(
            model="gemini-2.5-flash-image-preview",
            contents=[prompt],
        )
        
        # Process response
        for part in response.candidates[0].content.parts:
            if part.text is not None:
                print(f"\nResponse text: {part.text}")
            elif part.inline_data is not None:
                # Save image
                image = Image.open(BytesIO(part.inline_data.data))
                output_path = OUTPUT_DIR / f"{name}.png"
                image.save(output_path)
                
                # Get image info
                width, height = image.size
                file_size = os.path.getsize(output_path)
                
                print(f"\n✅ SUCCESS!")
                print(f"   Saved to: {output_path}")
                print(f"   Size: {width}x{height} pixels")
                print(f"   File size: {file_size / 1024:.1f} KB")
                return True
        
        print("\n⚠️  No image data in response")
        return False
        
    except Exception as e:
        print(f"\n❌ ERROR: {str(e)}")
        return False

def main():
    print("""
╔══════════════════════════════════════════════════════════════════════════╗
║                                                                          ║
║              PIP-DROID IMAGE GENERATOR (Gemini API)                     ║
║                                                                          ║
╚══════════════════════════════════════════════════════════════════════════╝
""")
    
    # Check for API key
    api_key = os.getenv("GEMINI_API_KEY")
    if api_key:
        print(f"✓ API Key found (length: {len(api_key)})")
    else:
        print("⚠️  No GEMINI_API_KEY environment variable found")
        print("   Attempting to use default client configuration...")
    
    print(f"\nOutput directory: {OUTPUT_DIR.absolute()}")
    print(f"Images to generate: {len(IMAGES)}")
    
    # Generate all images
    print("\n" + "="*70)
    print("STARTING IMAGE GENERATION")
    print("="*70)
    
    results = {}
    for name, prompt in IMAGES.items():
        success = generate_image(name, prompt)
        results[name] = success
    
    # Summary
    print("\n" + "="*70)
    print("GENERATION COMPLETE - SUMMARY")
    print("="*70)
    
    successful = sum(1 for v in results.values() if v)
    total = len(results)
    
    for name, success in results.items():
        status = "✅" if success else "❌"
        print(f"{status} {name}.png")
    
    print(f"\nResults: {successful}/{total} images generated successfully")
    
    if successful == total:
        print("\n🎉 All images generated successfully!")
        print(f"   Location: {OUTPUT_DIR.absolute()}")
        print("\nNext steps:")
        print("1. Review images in docs/images/")
        print("2. Commit images: git add docs/images/*.png")
        print("3. Push to GitHub: git push")
    elif successful > 0:
        print(f"\n⚠️  {total - successful} images failed to generate")
        print("   You can try running the script again or create them manually")
    else:
        print("\n❌ No images were generated")
        print("   Check your API key and internet connection")
        print("   See docs/images/README.md for manual creation guide")

if __name__ == "__main__":
    main()

