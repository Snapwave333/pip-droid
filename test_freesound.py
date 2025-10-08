import os
import urllib.request
import json

# API credentials
API_KEY = "dq6hwquuB64bLeURBAjUYXiQSDxzWNxf7IDCWuYx"
CLIENT_ID = "7p0GEE2t82BtiZMNS5Ea"

print("=" * 60)
print("FREESOUND API TEST")
print("=" * 60)
print()

# Method 1: Try with token parameter
print("Method 1: Testing with token parameter...")
url1 = f"https://freesound.org/apiv2/me/?token={API_KEY}"
print(f"URL: {url1}")

try:
    with urllib.request.urlopen(url1) as response:
        data = json.loads(response.read().decode())
        print("SUCCESS with token!")
        print(f"Username: {data.get('username')}")
        print(f"Email: {data.get('email')}")
        exit(0)
except urllib.error.HTTPError as e:
    print(f"FAILED: HTTP {e.code}: {e.reason}")
    print(f"Response: {e.read().decode()}")
    print()

# Method 2: Try with Authorization header
print("Method 2: Testing with Authorization header...")
url2 = "https://freesound.org/apiv2/me/"
req = urllib.request.Request(url2)
req.add_header("Authorization", f"Token {API_KEY}")
print(f"URL: {url2}")
print(f"Header: Authorization: Token {API_KEY[:8]}...")

try:
    with urllib.request.urlopen(req) as response:
        data = json.loads(response.read().decode())
        print("SUCCESS with Authorization header!")
        print(f"Username: {data.get('username')}")
        print(f"Email: {data.get('email')}")
        exit(0)
except urllib.error.HTTPError as e:
    print(f"FAILED: HTTP {e.code}: {e.reason}")
    try:
        print(f"Response: {e.read().decode()}")
    except:
        pass
    print()

# Method 3: Try simple search (doesn't require auth)
print("Method 3: Testing simple search (no auth)...")
url3 = "https://freesound.org/apiv2/search/text/?query=beep&page_size=1"
print(f"URL: {url3}")

try:
    with urllib.request.urlopen(url3) as response:
        data = json.loads(response.read().decode())
        print("SUCCESS! API is reachable")
        print(f"Found {data.get('count')} results")
        print()
        print("CONCLUSION: API works, but authentication failed.")
        print("Possible issues:")
        print("  1. API key may need activation (check your email)")
        print("  2. API key may need OAuth2 flow first")
        print("  3. Key format might be incorrect")
        print()
        print("Next step: Check Freesound.org > Settings > API Keys")
except Exception as e:
    print(f"FAILED: {e}")
    print()
    print("API endpoint is not reachable!")

