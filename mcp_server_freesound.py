#!/usr/bin/env python3
"""
Freesound MCP Server
Provides access to 500,000+ professional sound effects via Freesound.org API

Features:
- Search sound effects by description
- Download sounds directly
- Filter by license (CC0, CC-BY, etc.)
- Preview before download
- Get sound metadata

API Documentation: https://freesound.org/docs/api/
"""

import os
import sys
import json
import urllib.request
import urllib.parse
import logging
from typing import Any

# Add fastmcp to path if needed
try:
    from mcp.server import Server
    from mcp.types import Tool, TextContent, ImageContent, EmbeddedResource
except ImportError:
    print("Installing fastmcp...")
    os.system(f"{sys.executable} -m pip install fastmcp")
    from mcp.server import Server
    from mcp.types import Tool, TextContent, ImageContent, EmbeddedResource

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Freesound API Configuration
FREESOUND_API_KEY = os.environ.get("FREESOUND_API_KEY", "")
FREESOUND_BASE_URL = "https://freesound.org/apiv2"

# Create MCP server
mcp = Server("freesound")

def make_api_request(endpoint: str, params: dict = None) -> dict:
    """Make a request to the Freesound API"""
    if not FREESOUND_API_KEY:
        raise ValueError(
            "FREESOUND_API_KEY environment variable not set. "
            "Get your free API key at: https://freesound.org/apiv2/apply/"
        )
    
    # Add API key to params
    if params is None:
        params = {}
    params["token"] = FREESOUND_API_KEY
    
    # Build URL
    query_string = urllib.parse.urlencode(params)
    url = f"{FREESOUND_BASE_URL}/{endpoint}?{query_string}"
    
    logger.info(f"API Request: {endpoint}")
    
    try:
        with urllib.request.urlopen(url) as response:
            return json.loads(response.read().decode())
    except urllib.error.HTTPError as e:
        error_body = e.read().decode()
        raise Exception(f"Freesound API Error {e.code}: {error_body}")
    except Exception as e:
        raise Exception(f"Request failed: {str(e)}")

@mcp.tool()
def search_sounds(
    query: str,
    filter_license: str = "CC0",
    max_results: int = 10,
    min_duration: float = 0.1,
    max_duration: float = 5.0,
    sort: str = "rating_desc"
) -> str:
    """
    Search for sound effects on Freesound.org
    
    Args:
        query: Search query (e.g., "button click", "terminal beep", "radio static")
        filter_license: License filter - "CC0" (public domain), "CC-BY" (attribution), 
                       "CC-BY-NC" (non-commercial), or "all"
        max_results: Maximum number of results (1-150)
        min_duration: Minimum sound duration in seconds
        max_duration: Maximum sound duration in seconds
        sort: Sort order - "rating_desc", "downloads_desc", "created_desc", "duration_asc"
    
    Returns:
        JSON array of sound effects with download info
    """
    try:
        # Build filter string
        filters = [f"duration:[{min_duration} TO {max_duration}]"]
        
        if filter_license and filter_license != "all":
            if filter_license == "CC0":
                filters.append("license:\"Creative Commons 0\"")
            elif filter_license == "CC-BY":
                filters.append("license:\"Attribution\"")
            elif filter_license == "CC-BY-NC":
                filters.append("license:\"Attribution Noncommercial\"")
        
        # Make API request
        params = {
            "query": query,
            "filter": " AND ".join(filters),
            "sort": sort,
            "page_size": min(max_results, 150),
            "fields": "id,name,description,duration,download,previews,username,license,tags"
        }
        
        result = make_api_request("search/text/", params)
        
        # Format results
        sounds = []
        for sound in result.get("results", []):
            sounds.append({
                "id": sound["id"],
                "name": sound["name"],
                "description": sound.get("description", "")[:200],
                "duration": sound["duration"],
                "download_url": sound["download"],
                "preview_url": sound["previews"]["preview-hq-mp3"],
                "username": sound["username"],
                "license": sound["license"],
                "tags": sound.get("tags", [])[:5]
            })
        
        return json.dumps({
            "total_found": result.get("count", 0),
            "returned": len(sounds),
            "sounds": sounds
        }, indent=2)
        
    except Exception as e:
        return json.dumps({"error": str(e)}, indent=2)

@mcp.tool()
def download_sound(
    sound_id: int,
    output_path: str,
    use_preview: bool = False
) -> str:
    """
    Download a sound effect from Freesound
    
    Args:
        sound_id: Freesound sound ID (from search results)
        output_path: Local path to save the file (e.g., "src/main/res/raw/button_click.wav")
        use_preview: If True, download MP3 preview instead of original file
    
    Returns:
        JSON with download status and file info
    """
    try:
        # Get sound details
        params = {
            "fields": "id,name,download,previews,type,filesize,samplerate,bitrate"
        }
        sound = make_api_request(f"sounds/{sound_id}/", params)
        
        # Choose download URL
        if use_preview:
            download_url = sound["previews"]["preview-hq-mp3"]
            file_extension = ".mp3"
        else:
            download_url = sound["download"]
            # Get file extension from type
            file_type = sound.get("type", "wav")
            file_extension = f".{file_type}" if not file_type.startswith(".") else file_type
        
        # Ensure output path has correct extension
        if not output_path.endswith(file_extension):
            output_path = os.path.splitext(output_path)[0] + file_extension
        
        # Create directory if needed
        os.makedirs(os.path.dirname(output_path), exist_ok=True)
        
        # Download file
        logger.info(f"Downloading sound {sound_id} to {output_path}")
        
        # Add API token to download URL
        download_url_with_token = f"{download_url}?token={FREESOUND_API_KEY}"
        
        urllib.request.urlretrieve(download_url_with_token, output_path)
        
        file_size = os.path.getsize(output_path)
        
        return json.dumps({
            "success": True,
            "sound_id": sound_id,
            "name": sound["name"],
            "file_path": output_path,
            "file_size_bytes": file_size,
            "file_size_kb": round(file_size / 1024, 2),
            "format": file_extension.replace(".", ""),
            "sample_rate": sound.get("samplerate"),
            "message": f"Downloaded: {sound['name']}"
        }, indent=2)
        
    except Exception as e:
        return json.dumps({
            "success": False,
            "error": str(e)
        }, indent=2)

@mcp.tool()
def get_sound_details(sound_id: int) -> str:
    """
    Get detailed information about a specific sound
    
    Args:
        sound_id: Freesound sound ID
    
    Returns:
        JSON with complete sound metadata
    """
    try:
        sound = make_api_request(f"sounds/{sound_id}/")
        
        return json.dumps({
            "id": sound["id"],
            "name": sound["name"],
            "description": sound.get("description", ""),
            "tags": sound.get("tags", []),
            "license": sound["license"],
            "username": sound["username"],
            "duration": sound["duration"],
            "filesize": sound.get("filesize", 0),
            "type": sound.get("type", "unknown"),
            "samplerate": sound.get("samplerate"),
            "bitrate": sound.get("bitrate"),
            "channels": sound.get("channels"),
            "download_url": sound["download"],
            "preview_hq": sound["previews"]["preview-hq-mp3"],
            "preview_lq": sound["previews"]["preview-lq-mp3"],
            "waveform_url": sound["images"]["waveform_l"],
            "created": sound.get("created"),
            "num_downloads": sound.get("num_downloads", 0),
            "avg_rating": sound.get("avg_rating", 0),
            "num_ratings": sound.get("num_ratings", 0)
        }, indent=2)
        
    except Exception as e:
        return json.dumps({"error": str(e)}, indent=2)

@mcp.tool()
def download_pip_boy_sounds(
    output_dir: str = "src/main/res/raw",
    use_preview: bool = True
) -> str:
    """
    Download a curated set of Pip-Boy style sound effects
    
    This searches for and downloads sounds that match the Fallout Pip-Boy aesthetic:
    - Retro computer/terminal sounds
    - Mechanical button clicks
    - Electronic beeps and boops
    - Radio static and tuning
    
    Args:
        output_dir: Directory to save sounds (default: src/main/res/raw)
        use_preview: Use MP3 previews instead of full files (faster, smaller)
    
    Returns:
        JSON with download results for each sound
    """
    try:
        # Curated search queries for Pip-Boy sounds
        sound_searches = {
            "button_click": "mechanical button click computer",
            "terminal_type": "keyboard key press mechanical",
            "terminal_beep": "computer beep terminal",
            "terminal_boot": "computer startup boot sequence",
            "page_turn": "interface swoosh transition",
            "success_beep": "success beep confirmation",
            "error_beep": "error beep warning",
            "radio_static": "radio static white noise",
            "radio_on": "radio power on",
            "quest_complete": "success chime achievement",
            "level_up": "power up level up",
        }
        
        results = {
            "downloaded": [],
            "failed": [],
            "total_size_kb": 0
        }
        
        for sound_name, search_query in sound_searches.items():
            try:
                # Search for sound
                logger.info(f"Searching for: {sound_name}")
                search_result = json.loads(search_sounds(
                    query=search_query,
                    filter_license="CC0",
                    max_results=1,
                    min_duration=0.1,
                    max_duration=3.0,
                    sort="rating_desc"
                ))
                
                if search_result.get("returned", 0) == 0:
                    results["failed"].append({
                        "name": sound_name,
                        "reason": "No results found"
                    })
                    continue
                
                # Get first result
                sound = search_result["sounds"][0]
                
                # Download sound
                output_path = os.path.join(output_dir, f"{sound_name}.wav")
                download_result = json.loads(download_sound(
                    sound_id=sound["id"],
                    output_path=output_path,
                    use_preview=use_preview
                ))
                
                if download_result.get("success"):
                    results["downloaded"].append({
                        "name": sound_name,
                        "file_path": download_result["file_path"],
                        "size_kb": download_result["file_size_kb"],
                        "sound_name": sound["name"],
                        "license": sound["license"]
                    })
                    results["total_size_kb"] += download_result["file_size_kb"]
                else:
                    results["failed"].append({
                        "name": sound_name,
                        "reason": download_result.get("error", "Unknown error")
                    })
                    
            except Exception as e:
                results["failed"].append({
                    "name": sound_name,
                    "reason": str(e)
                })
        
        results["summary"] = f"Downloaded {len(results['downloaded'])}/{len(sound_searches)} sounds"
        
        return json.dumps(results, indent=2)
        
    except Exception as e:
        return json.dumps({"error": str(e)}, indent=2)

@mcp.tool()
def get_api_status() -> str:
    """
    Check Freesound API connection and get API key status
    
    Returns:
        JSON with API status information
    """
    if not FREESOUND_API_KEY:
        return json.dumps({
            "connected": False,
            "error": "FREESOUND_API_KEY not set",
            "instructions": "Get your free API key at: https://freesound.org/apiv2/apply/",
            "setup_steps": [
                "1. Go to https://freesound.org/apiv2/apply/",
                "2. Create account or login",
                "3. Request API key (instant approval)",
                "4. Set environment variable: FREESOUND_API_KEY=your_key",
                "5. Restart Cursor"
            ]
        }, indent=2)
    
    try:
        # Test API connection
        result = make_api_request("me/")
        
        return json.dumps({
            "connected": True,
            "username": result.get("username"),
            "email": result.get("email"),
            "sounds_count": result.get("num_sounds", 0),
            "api_key": FREESOUND_API_KEY[:8] + "..." + FREESOUND_API_KEY[-4:],
            "message": "✓ Freesound API connected successfully!"
        }, indent=2)
        
    except Exception as e:
        return json.dumps({
            "connected": False,
            "error": str(e),
            "api_key_set": True,
            "troubleshooting": [
                "- Check if API key is valid",
                "- Ensure you have internet connection",
                "- API key may be pending approval"
            ]
        }, indent=2)

if __name__ == "__main__":
    # Run the MCP server
    logger.info("Starting Freesound MCP Server...")
    logger.info(f"API Key configured: {bool(FREESOUND_API_KEY)}")
    
    if not FREESOUND_API_KEY:
        logger.warning("⚠️  FREESOUND_API_KEY not set!")
        logger.warning("Get your free API key at: https://freesound.org/apiv2/apply/")
    
    mcp.run()

