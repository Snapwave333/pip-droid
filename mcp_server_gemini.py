#!/usr/bin/env python3
"""
Gemini Image Generator MCP Server
A Model Context Protocol server for generating images using Google's Gemini AI
"""

import os
import sys
import base64
from pathlib import Path
from typing import Optional

try:
    import google.generativeai as genai
    from mcp.server.fastmcp import FastMCP
except ImportError as e:
    print(f"Error: Required packages not installed: {e}", file=sys.stderr)
    print("Install with: pip install google-generativeai mcp", file=sys.stderr)
    sys.exit(1)

# Initialize FastMCP server
mcp = FastMCP("Gemini Image Generator")

# Configuration
OUTPUT_DIR = Path(os.getenv("OUTPUT_IMAGE_PATH", "./docs/images"))
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Get API key from environment
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
if GEMINI_API_KEY:
    genai.configure(api_key=GEMINI_API_KEY)
else:
    print("Warning: GEMINI_API_KEY not set. Image generation will fail.", file=sys.stderr)


@mcp.tool()
def generate_image_from_text(prompt: str, filename: Optional[str] = None) -> dict:
    """
    Generate an image from a text prompt using Gemini AI.
    
    Args:
        prompt: Text description of the image to generate
        filename: Optional filename (without extension). If not provided, will be auto-generated.
    
    Returns:
        Dictionary with 'success', 'message', 'file_path', and 'base64_data' (if successful)
    """
    if not GEMINI_API_KEY:
        return {
            "success": False,
            "message": "GEMINI_API_KEY environment variable not set"
        }
    
    try:
        # Use Gemini 2.0 Flash Experimental for image generation
        model = genai.GenerativeModel('gemini-2.0-flash-exp')
        
        # Generate the image
        response = model.generate_content([
            prompt,
            {"text": "Generate this as a high-quality image. Do not include any text in the output."}
        ])
        
        # Check for image data in response
        if hasattr(response, 'parts'):
            for part in response.parts:
                if hasattr(part, 'inline_data') and part.inline_data:
                    # Get image data
                    image_data = part.inline_data.data
                    
                    # Generate filename if not provided
                    if not filename:
                        # Create filename from prompt (first 30 chars, sanitized)
                        safe_prompt = "".join(c if c.isalnum() or c in (' ', '-', '_') else '_' for c in prompt[:30])
                        filename = safe_prompt.strip().replace(' ', '_').lower()
                    
                    # Save the image
                    output_path = OUTPUT_DIR / f"{filename}.png"
                    output_path.write_bytes(image_data)
                    
                    # Encode as base64 for return
                    base64_data = base64.b64encode(image_data).decode('utf-8')
                    
                    return {
                        "success": True,
                        "message": f"Image generated successfully",
                        "file_path": str(output_path),
                        "filename": f"{filename}.png",
                        "size_bytes": len(image_data),
                        "base64_preview": base64_data[:100] + "..." if len(base64_data) > 100 else base64_data
                    }
        
        # No image data found
        return {
            "success": False,
            "message": "No image data received from Gemini. The model may not support image generation or the prompt was rejected."
        }
        
    except Exception as e:
        return {
            "success": False,
            "message": f"Error generating image: {str(e)}"
        }


@mcp.tool()
def transform_image_from_file(image_file_path: str, prompt: str, filename: Optional[str] = None) -> dict:
    """
    Transform an existing image based on a text prompt using Gemini AI.
    
    Args:
        image_file_path: Path to the image file to transform
        prompt: Text description of how to transform the image
        filename: Optional filename for the output (without extension)
    
    Returns:
        Dictionary with 'success', 'message', 'file_path', and 'base64_data' (if successful)
    """
    if not GEMINI_API_KEY:
        return {
            "success": False,
            "message": "GEMINI_API_KEY environment variable not set"
        }
    
    try:
        # Read the input image
        image_path = Path(image_file_path)
        if not image_path.exists():
            return {
                "success": False,
                "message": f"Image file not found: {image_file_path}"
            }
        
        image_data = image_path.read_bytes()
        
        # Use Gemini for image transformation
        model = genai.GenerativeModel('gemini-2.0-flash-exp')
        
        # Upload the image
        uploaded_file = genai.upload_file(path=str(image_path))
        
        # Generate transformation
        response = model.generate_content([
            uploaded_file,
            prompt,
            {"text": "Transform the image according to the prompt. Output only the transformed image."}
        ])
        
        # Check for image data in response
        if hasattr(response, 'parts'):
            for part in response.parts:
                if hasattr(part, 'inline_data') and part.inline_data:
                    # Get transformed image data
                    new_image_data = part.inline_data.data
                    
                    # Generate filename if not provided
                    if not filename:
                        filename = f"{image_path.stem}_transformed"
                    
                    # Save the transformed image
                    output_path = OUTPUT_DIR / f"{filename}.png"
                    output_path.write_bytes(new_image_data)
                    
                    # Encode as base64 for return
                    base64_data = base64.b64encode(new_image_data).decode('utf-8')
                    
                    return {
                        "success": True,
                        "message": f"Image transformed successfully",
                        "file_path": str(output_path),
                        "filename": f"{filename}.png",
                        "size_bytes": len(new_image_data),
                        "base64_preview": base64_data[:100] + "..." if len(base64_data) > 100 else base64_data
                    }
        
        # No image data found
        return {
            "success": False,
            "message": "No transformed image data received from Gemini."
        }
        
    except Exception as e:
        return {
            "success": False,
            "message": f"Error transforming image: {str(e)}"
        }


@mcp.tool()
def get_output_directory() -> dict:
    """
    Get the current output directory for generated images.
    
    Returns:
        Dictionary with 'success', 'path', and 'exists' status
    """
    return {
        "success": True,
        "path": str(OUTPUT_DIR.absolute()),
        "exists": OUTPUT_DIR.exists(),
        "is_writable": os.access(OUTPUT_DIR, os.W_OK) if OUTPUT_DIR.exists() else False
    }


@mcp.tool()
def list_generated_images() -> dict:
    """
    List all images in the output directory.
    
    Returns:
        Dictionary with 'success', 'images' (list of filenames), and 'count'
    """
    try:
        if not OUTPUT_DIR.exists():
            return {
                "success": False,
                "message": f"Output directory does not exist: {OUTPUT_DIR}"
            }
        
        images = [f.name for f in OUTPUT_DIR.glob("*.png")]
        images.extend([f.name for f in OUTPUT_DIR.glob("*.jpg")])
        images.extend([f.name for f in OUTPUT_DIR.glob("*.jpeg")])
        images.sort()
        
        return {
            "success": True,
            "images": images,
            "count": len(images),
            "directory": str(OUTPUT_DIR)
        }
        
    except Exception as e:
        return {
            "success": False,
            "message": f"Error listing images: {str(e)}"
        }


# Run the server
if __name__ == "__main__":
    print(f"Starting Gemini Image Generator MCP Server", file=sys.stderr)
    print(f"Output directory: {OUTPUT_DIR.absolute()}", file=sys.stderr)
    print(f"API Key configured: {'Yes' if GEMINI_API_KEY else 'No'}", file=sys.stderr)
    
    # Run the FastMCP server
    mcp.run()

