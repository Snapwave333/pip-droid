#!/usr/bin/env python3
"""
Create placeholder images for Pip-Droid repository
These are simple text placeholders until proper images are generated
"""

from pathlib import Path

try:
    from PIL import Image, ImageDraw, ImageFont
except ImportError:
    print("ERROR: PIL/Pillow not installed")
    print("Install with: pip install pillow")
    exit(1)

OUTPUT_DIR = Path(__file__).parent.parent / "docs" / "images"
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Image specifications
IMAGES = {
    "banner": {
        "size": (1920, 480),
        "text": "PIP-DROID LAUNCHER\nFallout Pip-Boy Android Launcher",
        "bg": (0, 20, 0),
        "fg": (0, 255, 0)
    },
    "logo": {
        "size": (512, 512),
        "text": "PD\n\nPip-Droid",
        "bg": (0, 20, 0),
        "fg": (0, 255, 0)
    },
    "boot_sequence": {
        "size": (1080, 2400),
        "text": "ROBCO INDUSTRIES (TM)\nTERMLINK PROTOCOL\n\nBOOT SEQUENCE\nINITIALIZING...",
        "bg": (0, 0, 0),
        "fg": (0, 255, 0)
    },
    "main_interface": {
        "size": (1080, 2400),
        "text": "PIP-DROID\nMAIN INTERFACE\n\nSTATUS | INV | DATA\nMAP | RADIO | HOLOTAPES",
        "bg": (0, 0, 0),
        "fg": (0, 255, 0)
    },
    "stats_screen": {
        "size": (1080, 2400),
        "text": "S.P.E.C.I.A.L.\nSTATS SCREEN\n\nStrength\nPerception\nEndurance\nCharisma\nIntelligence\nAgility\nLuck",
        "bg": (0, 0, 0),
        "fg": (0, 255, 0)
    },
    "quest_log": {
        "size": (1080, 2400),
        "text": "QUEST LOG\n\n[ ] Main Quest\n[ ] Side Quest\n[ ] Misc Quest",
        "bg": (0, 0, 0),
        "fg": (0, 255, 0)
    },
    "terminal": {
        "size": (1080, 2400),
        "text": "TERMINAL MODE\n\n> help\n> status\n> quests\n> _",
        "bg": (0, 0, 0),
        "fg": (0, 255, 0)
    },
    "radio": {
        "size": (1080, 2400),
        "text": "RADIO PLAYER\n\nGalaxy News Radio\nRadio New Vegas\nDiamond City Radio",
        "bg": (0, 0, 0),
        "fg": (0, 255, 0)
    }
}

def create_placeholder(name, config):
    """Create a simple placeholder image"""
    width, height = config["size"]
    bg_color = config["bg"]
    fg_color = config["fg"]
    text = config["text"]
    
    # Create image
    img = Image.new('RGB', (width, height), bg_color)
    draw = ImageDraw.Draw(img)
    
    # Calculate text position (center)
    bbox = draw.textbbox((0, 0), text, font=None)
    text_width = bbox[2] - bbox[0]
    text_height = bbox[3] - bbox[1]
    
    x = (width - text_width) // 2
    y = (height - text_height) // 2
    
    # Draw text
    draw.text((x, y), text, fill=fg_color, font=None)
    
    # Add border
    draw.rectangle([(10, 10), (width-10, height-10)], outline=fg_color, width=3)
    
    # Save
    output_path = OUTPUT_DIR / f"{name}.png"
    img.save(output_path)
    
    print(f"✓ Created {name}.png ({width}x{height})")
    return output_path

def main():
    print("Creating placeholder images for Pip-Droid repository...")
    print(f"Output: {OUTPUT_DIR.absolute()}\n")
    
    for name, config in IMAGES.items():
        create_placeholder(name, config)
    
    print(f"\n✅ Created {len(IMAGES)} placeholder images")
    print(f"\nLocation: {OUTPUT_DIR.absolute()}")
    print("\n⚠️  These are PLACEHOLDERS!")
    print("For better images:")
    print("1. Use Gemini API: python scripts/generate_images_simple.py")
    print("2. Take actual screenshots from the app")
    print("3. Create custom images with Photoshop/GIMP")

if __name__ == "__main__":
    main()

