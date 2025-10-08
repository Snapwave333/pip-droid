#!/usr/bin/env python3
"""
Gemini Image Generator MCP Tool
Generates images using Google's Gemini 2.0 Flash Image model
"""

import os
import sys
from pathlib import Path
import google.generativeai as genai
from PIL import Image
from io import BytesIO

# Configuration
OUTPUT_DIR = Path(__file__).parent.parent / "docs" / "images"
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Get API key from environment
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
if not GEMINI_API_KEY:
    print("Error: GEMINI_API_KEY environment variable not set")
    sys.exit(1)

# Configure Gemini
genai.configure(api_key=GEMINI_API_KEY)

# Image prompts for Pip-Droid repository
PROMPTS = {
    "banner": """Create a wide banner in retro-futuristic style inspired by Fallout's Pip-Boy interface. 
    Use a monochrome green color palette (#00FF00) with glowing UI elements, circuit overlays, 
    and a stylized vault background. Include the text 'Pip-Droid Launcher' in bold pixel-style 
    font, centered. Add subtle scanlines and CRT distortion for authenticity. Wide banner format, 
    professional quality, dark atmosphere.""",
    
    "logo": """Design a circular logo for an Android launcher called Pip-Droid. Style it like a glowing 
    green Pip-Boy interface button, with a gear or vault door motif in the center. Use pixel 
    art and retro tech textures. Keep it simple, iconic, and readable at small sizes. Monochrome 
    green (#00FF00) on black background. Circular composition, high contrast.""",
    
    "boot_sequence": """Generate a mockup of a Fallout-style terminal boot sequence on an Android phone screen. 
    Show 'ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL' at top, followed by system initialization 
    messages in monochrome green text (#00FF00) on black background. Include a blinking cursor at bottom. 
    Add scanline overlay. Phone screen aspect ratio (9:20), retro computer terminal aesthetic.""",
    
    "main_interface": """Create a mockup of a Pip-Boy–style Android home screen with six bottom navigation tabs 
    labeled STATUS, INV, DATA, MAP, RADIO, HOLOTAPES. Show main content area with system 
    status display in monochrome green (#00FF00). Include time, battery, and system info. Add CRT 
    scanline overlay. Phone screen aspect ratio, futuristic UI design.""",
    
    "stats_screen": """Generate a mockup showing Fallout S.P.E.C.I.A.L. stats on a Pip-Boy screen. Display 7 
    stats (Strength, Perception, Endurance, Charisma, Intelligence, Agility, Luck) with level 
    numbers, XP progress bars, and expandable descriptions. Monochrome green text (#00FF00) on black. 
    Add CRT effects. Phone screen format, RPG stat display.""",
    
    "quest_log": """Create a Pip-Boy quest log screen mockup showing 3-4 quests in a list. Each quest should 
    have title, category tag (MAIN/SIDE), progress bar, and checkbox objectives. Include '+' 
    button to add quests. Monochrome green (#00FF00) on black with CRT scanlines. Phone screen, 
    retro UI design, quest management interface.""",
    
    "terminal": """Generate a retro terminal interface mockup for a Pip-Boy. Show command prompt '>' with 
    example commands like 'help', 'status', 'quests'. Display terminal output in monospace 
    green text (#00FF00) on black background. Add heavy CRT scanlines and glow effect. Phone screen, 
    classic computer terminal aesthetic.""",
    
    "radio": """Create a Pip-Boy radio interface showing a list of Fallout radio stations (Galaxy News Radio, 
    Radio New Vegas, Diamond City Radio, etc.). Include play/pause/stop buttons and volume slider. 
    Monochrome green (#00FF00) on black with retro gauges and dials. Add CRT effects. Phone screen, 
    vintage radio tuner design."""
}


def generate_image(name: str, prompt: str) -> bool:
    """Generate a single image using Gemini 2.0 Flash"""
    print(f"\n🎨 Generating {name}...")
    print(f"   Prompt: {prompt[:80]}...")
    
    try:
        # Use Gemini 2.0 Flash for image generation
        model = genai.GenerativeModel('gemini-2.0-flash-exp')
        
        response = model.generate_content([
            prompt,
            {"text": "Generate this as a high-quality image. Do not include any text in the output, only respond with the image data."}
        ])
        
        # Check if we got image data
        if hasattr(response, 'parts'):
            for part in response.parts:
                if hasattr(part, 'inline_data') and part.inline_data:
                    # Save the image
                    image_data = part.inline_data.data
                    image = Image.open(BytesIO(image_data))
                    
                    output_path = OUTPUT_DIR / f"{name}.png"
                    image.save(output_path)
                    
                    print(f"   ✅ Saved to: {output_path}")
                    return True
        
        print(f"   ⚠️  No image data received - trying alternative method")
        
        # Alternative: Try using imagen
        try:
            imagen_model = genai.ImageGenerationModel("imagen-3.0-generate-001")
            result = imagen_model.generate_images(
                prompt=prompt,
                number_of_images=1,
                safety_filter_level="block_some",
                person_generation="allow_adult",
                aspect_ratio="1:1"
            )
            
            if result.images:
                output_path = OUTPUT_DIR / f"{name}.png"
                result.images[0].save(output_path)
                print(f"   ✅ Saved to: {output_path}")
                return True
                
        except Exception as e_alt:
            print(f"   ⚠️  Alternative method also failed: {e_alt}")
        
        return False
        
    except Exception as e:
        print(f"   ❌ Error: {e}")
        return False


def main():
    """Generate all repository images"""
    print("=" * 70)
    print("Pip-Droid Repository Image Generator (MCP)")
    print("Using Google Gemini AI")
    print("=" * 70)
    
    results = {}
    for name, prompt in PROMPTS.items():
        success = generate_image(name, prompt)
        results[name] = success
    
    # Summary
    print("\n" + "=" * 70)
    print("Generation Summary:")
    print("=" * 70)
    
    successful = sum(1 for v in results.values() if v)
    total = len(results)
    
    for name, success in results.items():
        status = "✅" if success else "❌"
        print(f"{status} {name}.png")
    
    print(f"\nTotal: {successful}/{total} images generated successfully")
    
    if successful < total:
        print("\n⚠️  Some images failed to generate.")
        print("Keeping existing placeholder images for failed generations.")
    
    print("\n📁 Output directory:", OUTPUT_DIR.absolute())
    print("=" * 70)
    
    return 0 if successful == total else 1


if __name__ == "__main__":
    sys.exit(main())

