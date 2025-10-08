# MCP Servers Configuration

This document describes the Model Context Protocol (MCP) servers configured for the Pip-Droid project in Cursor IDE.

---

## What is MCP?

The **Model Context Protocol (MCP)** is a protocol that allows AI assistants (like Claude in Cursor) to connect to external tools, APIs, and services. MCP servers extend the capabilities of your AI coding assistant by providing specialized functionality.

**Learn more**: [MCP Specification](https://spec.modelcontextprotocol.io/)

---

## Configured MCP Servers

### 1. Gemini Image Generator

**Purpose**: Generate high-quality images using Google's Gemini AI model.

**Configuration**: `.cursor/mcp.json`

```json
{
  "gemini-image-generator": {
    "command": "python",
    "args": ["-m", "mcp_server_gemini_image_generator"],
    "env": {
      "GEMINI_API_KEY": "${env:GEMINI_API_KEY}",
      "OUTPUT_IMAGE_PATH": "${workspaceFolder}/docs/images"
    }
  }
}
```

**Features**:
- Text-to-image generation
- Image-to-image transformation
- Automatic filename generation
- Automatic translation of non-English prompts
- High-resolution output
- Local image storage

**Available Tools**:
- `generate_image_from_text` - Creates new images from text descriptions
- `transform_image_from_encoded` - Transforms base64-encoded images
- `transform_image_from_file` - Transforms existing image files

**Usage in Cursor**:
```
@gemini-image-generator generate a Pip-Boy interface mockup
```

**Setup**:
1. Get a Gemini API key from [Google AI Studio](https://aistudio.google.com/app/apikey)
2. Set environment variable: `GEMINI_API_KEY=your-key-here`
3. Restart Cursor

**Repository**: [qhdrl12/mcp-server-gemini-image-generator](https://github.com/MCP-Mirror/qhdrl12_mcp-server-gemini-image-generator)

---

### 2. Puppeteer Browser Automation

**Purpose**: Provides browser automation capabilities using Puppeteer for web scraping, testing, and interaction.

**Configuration**: `.cursor/mcp.json`

```json
{
  "puppeteer": {
    "command": "npx",
    "args": ["-y", "@modelcontextprotocol/server-puppeteer"]
  }
}
```

**Features**:
- Browser automation
- Screenshot capture
- JavaScript execution
- Form interaction
- Element selection and clicking
- Console log monitoring
- Navigation control

**Available Tools**:
- `puppeteer_navigate` - Navigate to URLs
- `puppeteer_screenshot` - Capture screenshots
- `puppeteer_click` - Click elements
- `puppeteer_hover` - Hover over elements
- `puppeteer_fill` - Fill form inputs
- `puppeteer_select` - Select dropdown options
- `puppeteer_evaluate` - Execute JavaScript

**Usage in Cursor**:
```
@puppeteer navigate to https://github.com/Snapwave333/pip-droid and take a screenshot
@puppeteer click the "Releases" button and capture the page
```

**Setup**:
1. Requires Node.js installed
2. Will automatically install on first use via `npx`
3. Runs with browser window visible (use Docker for headless)

**Use Cases**:
- Testing repository pages
- Automating screenshots for documentation
- Web scraping for data collection
- Form automation for testing
- Monitoring console logs

**Documentation**: [MCP Cursor - Puppeteer](https://mcpcursor.com/server/puppeteer)

**Repository**: [@modelcontextprotocol/server-puppeteer](https://www.npmjs.com/package/@modelcontextprotocol/server-puppeteer)

---

## Advanced Configuration

### Headless Puppeteer (Docker)

For headless operation without browser windows:

```json
{
  "puppeteer": {
    "command": "docker",
    "args": ["run", "-i", "--rm", "--init", "-e", "DOCKER_CONTAINER=true", "mcp/puppeteer"]
  }
}
```

**Setup**:
1. Build Docker image: `docker build -t mcp/puppeteer -f src/puppeteer/Dockerfile .`
2. Update `.cursor/mcp.json` with Docker configuration
3. Restart Cursor

---

## How to Use MCP Servers in Cursor

### In Chat/Composer

Simply reference the MCP server with `@` symbol:

```
@gemini-image-generator create a banner for Pip-Droid
@puppeteer navigate to the GitHub repo and capture a screenshot
```

### Checking Available Tools

In Cursor chat, you can ask:
```
What tools are available from @gemini-image-generator?
What can @puppeteer do?
```

### Combining Multiple MCPs

You can use multiple MCP servers in a single workflow:

```
@puppeteer navigate to https://fallout.fandom.com/wiki/Pip-Boy_3000
@puppeteer take a screenshot
@gemini-image-generator create a similar UI mockup based on this design
```

---

## Troubleshooting

### Gemini Image Generator

**Issue**: API key not valid
- **Solution**: Get a new API key from [Google AI Studio](https://aistudio.google.com/app/apikey)
- **Verify**: Run `echo $env:GEMINI_API_KEY` in PowerShell

**Issue**: Images not generating
- **Solution**: Check `docs/images/` directory for output
- **Fallback**: Use `tools/gemini_image_mcp.py` directly

### Puppeteer

**Issue**: `npx` command not found
- **Solution**: Install Node.js from [nodejs.org](https://nodejs.org/)
- **Verify**: Run `node --version` and `npm --version`

**Issue**: Browser window stays open
- **Solution**: Use Docker configuration for headless mode
- **Alternative**: Close browser manually or add timeout

**Issue**: Element not found
- **Solution**: Wait for page load before interacting
- **Use**: More specific CSS selectors

---

## Adding More MCP Servers

To add additional MCP servers:

1. **Find a server**: Browse [MCP Cursor](https://mcpcursor.com/) or [MCP GitHub](https://github.com/modelcontextprotocol)

2. **Update configuration**: Add to `.cursor/mcp.json`:
   ```json
   {
     "mcpServers": {
       "existing-server": { ... },
       "new-server": {
         "command": "...",
         "args": ["..."]
       }
     }
   }
   ```

3. **Restart Cursor**: Reload the window or restart the application

4. **Test**: Use `@new-server` in Cursor chat

---

## Useful MCP Servers for Pip-Droid Development

### Recommended Additions

1. **GitHub MCP** - For issue tracking and repo management
   - Repository: [@modelcontextprotocol/server-github](https://github.com/modelcontextprotocol/servers/tree/main/src/github)
   - Use case: Automated issue creation, PR management

2. **Sequential Thinking MCP** - For complex problem-solving
   - Repository: [sequentialthinking/mcp-server](https://github.com/sequentialthinking/mcp-server)
   - Use case: Breaking down complex Android development tasks

3. **Filesystem MCP** - For advanced file operations
   - Repository: [@modelcontextprotocol/server-filesystem](https://github.com/modelcontextprotocol/servers/tree/main/src/filesystem)
   - Use case: Batch file operations, code generation

4. **Memory MCP** - For persistent knowledge across sessions
   - Repository: [@modelcontextprotocol/server-memory](https://github.com/modelcontextprotocol/servers/tree/main/src/memory)
   - Use case: Remember project decisions and patterns

---

## Security Considerations

### API Keys
- **Never commit** API keys to Git
- **Use environment variables** for sensitive data
- **Rotate keys** regularly
- **Limit scopes** to minimum required permissions

### Browser Automation
- **Review scripts** before execution
- **Avoid credentials** in plain text
- **Use test accounts** for form automation
- **Respect robots.txt** and terms of service

### Network Access
- **Firewall rules** may block MCP servers
- **VPN/Proxy** configuration may need adjustment
- **Corporate networks** may restrict certain tools

---

## Resources

- **MCP Specification**: https://spec.modelcontextprotocol.io/
- **MCP Cursor**: https://mcpcursor.com/
- **MCP GitHub**: https://github.com/modelcontextprotocol
- **Cursor IDE**: https://cursor.sh/
- **Google AI Studio**: https://aistudio.google.com/

---

## Contributing

If you find useful MCP servers for Android development, consider:
1. Testing them with Pip-Droid
2. Documenting the configuration
3. Submitting a PR to update this document
4. Sharing in GitHub Discussions

---

**Last Updated**: October 2025  
**MCP Version**: 1.0  
**Cursor Version**: Latest

---

☢️ **Enhance your Pip-Droid development with MCP superpowers!** ☢️

