# Desktop Commander MCP Guide

## ⚡ Give AI Complete System Control

**Desktop Commander** is a revolutionary MCP server that gives AI direct access to your terminal, file system, and advanced editing capabilities - turning AI into a true development partner.

**Official Repository**: https://github.com/wonderwhy-er/desktopcommandermcp  
**Website**: https://desktopcommander.app/

---

## 🌟 What is Desktop Commander?

Desktop Commander transforms AI from a chatbot into a **powerful system operator** with:

⚡ **Terminal Control** - Execute commands directly on your system  
📁 **File System Access** - Search, read, write, and edit files anywhere  
✂️ **Surgical Edits** - Make precise changes with diff-based editing  
🔍 **Code Analysis** - Explore and understand complex codebases  
🤖 **Full Automation** - Automate tasks across your entire OS  

**Key Difference**: Unlike IDE-focused tools (Cursor, Windsurf), Desktop Commander works with your **entire OS**, not just within a coding environment.

---

## 📁 Configuration

Your `.cursor/mcp.json` now includes:

```json
{
  "mcpServers": {
    "desktop-commander": {
      "command": "npx",
      "args": ["-y", "desktop-commander"]
    }
  }
}
```

**Auto-updates enabled!** Desktop Commander automatically updates when you restart Cursor.

---

## 🚀 Core Capabilities

### 1. **Terminal Control** 🖥️

Execute any command directly on your system:

```
Run 'gradlew assembleDebug' to build the Android app
Execute 'adb devices' to list connected devices
Run 'git status' and tell me what files changed
```

**Safety Features**:
- Requires explicit permission for dangerous commands
- Shows command output in real-time
- Handles both foreground and background processes

### 2. **File System Search** 🔍

Find files and code patterns across your project:

```
Search for all Kotlin files that use 'Composable'
Find files containing 'QuestRepository'
List all XML files in the res directory
```

**Advanced Search**:
- Fast fuzzy search across large codebases
- Filter by file type, directory, or content
- Works with multiple projects simultaneously

### 3. **Surgical File Editing** ✂️

Make precise changes with diff-based editing:

```
In StatusScreen.kt, change the stat level calculation to use logarithmic scaling
Update the QuestRepository to add caching for frequent queries
Refactor the CRTEffect composable to improve performance
```

**Benefits**:
- Changes are atomic (all or nothing)
- Shows exactly what will change before applying
- Works with large files efficiently
- Preserves formatting and style

### 4. **Codebase Exploration** 🗺️

Understand complex projects:

```
Analyze the architecture of the Pip-Droid app
Explain how the Quest Log syncs with Google Calendar
Show me the data flow from QuestRepository to QuestLogScreen
```

**Intelligence**:
- Reads full files (not chunks like some tools)
- Understands project structure
- Tracks dependencies and relationships

### 5. **Multi-Project Work** 📂

Work across multiple projects simultaneously:

```
Compare the MVVM implementation between Pip-Droid and my other app
Copy the CRT effects from this project to my new launcher
Sync the database schema between the app and domain modules
```

---

## 💡 Use Cases for Pip-Droid

### **Use Case 1: Build & Deploy**

```
You: "Build the debug APK and install it on my connected device"
AI:  [Executes gradlew assembleDebug]
     [Checks build output]
     [Runs adb install]
     "Build successful! APK installed on device."
```

### **Use Case 2: Bulk Refactoring**

```
You: "Rename 'PipBoyColor' to 'VaultColor' across the entire codebase"
AI:  [Searches all occurrences]
     [Shows diff preview for each file]
     [Applies changes atomically]
     "Renamed in 23 files across 5 modules."
```

### **Use Case 3: Code Analysis**

```
You: "Analyze the performance of my CRT effects and suggest optimizations"
AI:  [Reads CRTEffect.kt, PipBoyTheme.kt]
     [Analyzes composable recomposition]
     [Checks Canvas usage patterns]
     [Provides specific optimization recommendations]
```

### **Use Case 4: Git Workflow**

```
You: "Stage all Kotlin files with quest-related changes and commit them"
AI:  [Runs git status]
     [Filters quest-related files]
     [Stages specific files]
     [Creates descriptive commit message]
     [Commits changes]
```

### **Use Case 5: Dependency Management**

```
You: "Update all outdated dependencies in build.gradle"
AI:  [Reads build.gradle files]
     [Checks latest versions]
     [Updates dependencies with diff preview]
     [Runs gradle sync to verify]
```

---

## 🆚 Desktop Commander vs IDE Tools

| Feature | Desktop Commander | Cursor/Windsurf |
|---------|-------------------|-----------------|
| **Scope** | Entire OS | IDE only |
| **File Reading** | Full files | Chunked |
| **Multi-Project** | ✅ Seamless | ❌ Single project |
| **Terminal Access** | ✅ Full control | Limited |
| **Automation** | ✅ System-wide | IDE scripts |
| **Edit Approach** | Surgical diffs | Line-by-line review |
| **Updates** | Auto-updates | Manual |

---

## 🛠️ Available Tools

Desktop Commander provides these MCP tools:

### **1. execute_command**
Execute terminal commands with real-time output

**Example**:
```
Execute 'npm install' to install dependencies
Run 'python scripts/generate_images.py' with verbose output
```

### **2. search_files**
Search for files and content patterns

**Example**:
```
Find all files containing 'CrashlyticsManager'
Search for Kotlin files with 'ViewModel' in the name
```

### **3. read_file**
Read file contents (full file, not chunked)

**Example**:
```
Read src/main/AndroidManifest.xml
Show me the contents of build.gradle
```

### **4. write_file**
Create new files or overwrite existing ones

**Example**:
```
Create a new feature module structure
Write a test file for QuestRepository
```

### **5. edit_file**
Make surgical edits with diff preview

**Example**:
```
In MainActivity.kt, add crash logging to onCreate
Update the theme colors in PipBoyTheme.kt
```

### **6. list_directory**
List files and directories

**Example**:
```
Show me all files in src/main/java/com/supernova/pipboy/
List the contents of the build directory
```

### **7. get_usage_stats**
View your Desktop Commander usage statistics

**Example**:
```
Show my usage stats
How many commands have I run this week?
```

---

## 🎯 Real-World Workflows

### **Workflow 1: Feature Implementation (End-to-End)**

```
Step 1: Plan with PromptX
   "I need a product manager - should we add home screen widgets?"
   [PM provides analysis and recommendation]

Step 2: Design with PromptX
   "I need a UX designer - design the widget configuration flow"
   [Designer provides wireframes and user flows]

Step 3: Generate Mockup with Pollinations
   @pollinations-image Create Pip-Boy style widget preview

Step 4: Implement with Desktop Commander
   "Create a new module 'feature-widget' with MVVM structure"
   [Creates all necessary files and boilerplate]
   
   "Implement WidgetProvider with RemoteViews for Pip-Boy display"
   [Writes complete implementation with best practices]
   
   "Update AndroidManifest.xml to register the widget"
   [Makes surgical edit to manifest]

Step 5: Test with Desktop Commander
   "Build debug APK and install on device"
   [Executes build and install]
   
   "Show me logcat filtered for our widget"
   [Monitors real-time logs]

Step 6: Document with PromptX Writer
   "Activate Writer - create user documentation for widgets"
   [Generates authentic user guide]

Result: Complete feature from planning to shipped!
```

### **Workflow 2: Bug Investigation & Fix**

```
Step 1: Reproduce Issue
   "Build and run the app, then check logcat for crashes"
   [Desktop Commander builds, installs, monitors logs]

Step 2: Analyze Crash
   "Read the stack trace and find the problematic code"
   [Searches files, reads relevant sources]

Step 3: Identify Root Cause
   "Analyze QuestRepository.kt for potential null pointer issues"
   [Performs deep code analysis]

Step 4: Fix with Surgical Edit
   "Add null check before accessing quest.objectives in line 145"
   [Shows diff, applies fix]

Step 5: Verify Fix
   "Rebuild and test on device"
   [Builds, installs, verifies crash is resolved]

Step 6: Commit Changes
   "Stage the fix and commit with descriptive message"
   [Git workflow automation]
```

### **Workflow 3: Dependency Upgrade**

```
Step 1: Check Current Versions
   "Read build.gradle and list all dependencies"
   
Step 2: Research with Puppeteer
   @puppeteer Check latest versions of Compose libraries

Step 3: Update with Desktop Commander
   "Update Jetpack Compose to version 1.6.0"
   [Edits build.gradle with diff preview]

Step 4: Sync & Build
   "Run gradlew sync and then assembleDebug"
   [Executes gradle commands]

Step 5: Test
   "Install on device and check for compatibility issues"
   [Monitors for errors]

Step 6: Document with PromptX
   "Activate Writer - update CHANGELOG.md with dependency updates"
```

---

## 🔐 Security & Safety

### **Permission System**

Desktop Commander asks for permission before:
- Executing potentially dangerous commands
- Modifying system files
- Accessing sensitive directories
- Running scripts with elevated privileges

### **Safe Defaults**

- Commands run in your current working directory by default
- File edits show diff preview before applying
- Atomic operations (all changes succeed or fail together)
- Local usage analytics (never sent externally by default)

### **Privacy**

Desktop Commander collects **minimal anonymous telemetry**:
- ✅ Local usage stats (always stored locally)
- ✅ Tool call counts and success rates
- ❌ NO file contents
- ❌ NO file paths
- ❌ NO command arguments

**Disable External Telemetry**:
```
Disable telemetry
```

See full privacy policy: [PRIVACY.md](https://github.com/wonderwhy-er/DesktopCommanderMCP/blob/main/PRIVACY.md)

---

## 💎 Pro Tips

### **1. Combine with Other MCP Servers**

```
"Generate a mockup with @pollinations-image, then use it as reference 
 to implement the UI with precise layout dimensions"
```

### **2. Use Natural Language**

❌ Don't: "execute_command('gradlew assembleDebug')"  
✅ Do: "Build the debug APK"

### **3. Let AI Read Full Context**

```
"Read all files in the quest feature module and explain the architecture"
```

Desktop Commander reads full files, not chunks - it understands complete context.

### **4. Leverage Multi-Project**

```
"Compare the navigation implementation between Pip-Droid 
 and my other project at ~/projects/mynewapp"
```

### **5. Automate Repetitive Tasks**

```
"Create a script that builds, installs, and launches the app with one command"
```

### **6. Use Surgical Edits for Large Files**

```
"In the 2000-line MainActivity.kt, update only the onCreate method 
 to add analytics tracking"
```

Shows minimal diff, precise change.

---

## 🐛 Troubleshooting

### Issue 1: Desktop Commander Not Available

**Symptoms**: `@desktop-commander` doesn't appear  
**Solutions**:
1. Restart Cursor completely
2. Check Node.js: `node --version` (v16+ required)
3. Verify `.cursor/mcp.json` configuration
4. Clear npm cache: `npm cache clean --force`

### Issue 2: Commands Fail to Execute

**Symptoms**: Terminal commands return errors  
**Solutions**:
- Check working directory: "What's my current directory?"
- Verify command exists: "Check if gradle is installed"
- Review permissions: Commands may need admin/sudo
- Check command syntax: Ensure proper escaping

### Issue 3: File Edits Don't Apply

**Symptoms**: Changes shown in diff but not applied  
**Solutions**:
- Check file permissions (read/write access)
- Ensure file isn't locked by another process
- Verify file path is correct
- Review diff for conflicts

### Issue 4: Slow Performance

**Symptoms**: Commands take long time to execute  
**Solutions**:
- Large file searches can be slow - be more specific
- Terminal commands with lots of output may delay
- Consider breaking complex operations into steps
- Check system resources (CPU, memory)

---

## 📊 Usage Analytics

Desktop Commander tracks local usage statistics for your benefit:

```
Show me my usage stats
```

**See**:
- Total commands executed
- Success/failure rates
- Most used tools
- Performance metrics
- Days of active use

**Note**: This data stays on your machine and helps you understand your workflow patterns.

---

## 🎓 Learning Resources

### **Official Resources**
- **Website**: https://desktopcommander.app/
- **GitHub**: https://github.com/wonderwhy-er/desktopcommandermcp
- **FAQ**: [Comprehensive FAQ](https://github.com/wonderwhy-er/DesktopCommanderMCP/blob/main/FAQ.md)
- **Discord**: Join the community for support

### **Video Tutorials**
- [Desktop Commander Setup & Demo](https://www.youtube.com/watch?v=ly3bed99Dy8)
- Watch real developers using Desktop Commander

### **Articles**
- [AnalyticsIndiaMag Article](https://analyticsindiamag.com/) - "This Developer Ditched Windsurf, Cursor Using Claude with MCPs"

---

## 💬 Community Testimonials

> "It's a life saver! I paid Claude + Cursor currently which I always feel it's kind of duplicated. This solves the problem ultimately."

> "I've been struggling to update an old Flutter app... I implemented your MCP in Claude desktop and was able to fix the issues in a couple of hours."

> "Great! This Claude MCP... can code as much as I want not worrying about token cost."

> "It is a great tool, thank you, I like using it, as it gives claude an ability to do surgical edits, making it more like a human developer."

---

## 🌟 Why Desktop Commander is Game-Changing

### **Traditional AI Coding**
❌ Limited to IDE environment  
❌ Chunked file reading (loses context)  
❌ Constant review needed  
❌ Single project focus  
❌ No system automation  

### **Desktop Commander**
✅ Entire OS access  
✅ Full file reading (complete context)  
✅ Surgical edits (atomic changes)  
✅ Multi-project workflows  
✅ System-wide automation  
✅ Terminal control  
✅ Auto-updates  

---

## 🚀 Quick Start for Pip-Droid

After restarting Cursor, try these commands:

### **1. Build the App**
```
Build debug APK and install on connected device
```

### **2. Analyze Code**
```
Read CRTEffect.kt and suggest performance optimizations
```

### **3. Refactor**
```
Search all files using 'PipBoyColor' and show me where it's defined
```

### **4. Git Workflow**
```
Show git status, then stage all quest-related changes
```

### **5. Documentation**
```
Generate a dependency graph of the quest module
```

---

## 🎯 Integration with Your MCP Ecosystem

Desktop Commander works **seamlessly** with your other MCP servers:

| Server | Purpose | Combined with Desktop Commander |
|--------|---------|--------------------------------|
| **Pollinations.AI** | Generate images | Create mockup → Implement UI with exact dimensions |
| **PromptX** | Expert guidance | Expert designs feature → Desktop Commander implements it |
| **Puppeteer** | Web automation | Research dependency versions → Update build.gradle |

**Example Combined Workflow**:
```
1. [PromptX] I need a product manager - plan widget feature
2. [PromptX] I need a UX designer - design widget layout  
3. [Pollinations] Generate widget mockup
4. [Desktop Commander] Implement WidgetProvider code
5. [Desktop Commander] Build and test on device
6. [PromptX Writer] Create release notes
```

---

## 🔥 Power User Examples

### **Automated Release Workflow**
```
You: "Prepare release: bump version, build release APK, generate changelog"
AI:  1. Reads build.gradle, increments version
     2. Updates version code and name
     3. Runs gradlew assembleRelease
     4. Reads git log since last tag
     5. Generates CHANGELOG.md
     6. Creates git tag with version
     "Release 0.2.0 ready! APK at app/build/outputs/apk/release/"
```

### **Cross-Project Code Sync**
```
You: "Copy the quest system from Pip-Droid to my new RPG game project"
AI:  1. Reads Quest.kt, QuestRepository.kt, QuestLogScreen.kt
     2. Analyzes dependencies
     3. Adapts code to new project structure
     4. Creates files in target project
     5. Updates package names and imports
     "Quest system ported! 8 files created in ~/projects/rpg-game/"
```

### **Intelligent Debugging**
```
You: "App crashes on quest completion. Find and fix the bug."
AI:  1. Builds and runs app
     2. Monitors logcat for crash
     3. Reads stack trace
     4. Analyzes QuestRepository and related files
     5. Identifies null pointer issue
     6. Applies surgical fix with null check
     7. Rebuilds and verifies fix
     "Bug fixed! Issue was in QuestRepository.markComplete() - 
      missing null check on quest.rewards"
```

---

## 📚 Best Practices

### **1. Be Specific**
✅ "Update the CRTEffect scanline density to 2.0 in line 145"  
❌ "Make the effects better"

### **2. Review Diffs**
Always review the diff preview before approving file edits

### **3. Commit Frequently**
Use git commits to create save points:
```
Stage changes and commit with message "Add widget support"
```

### **4. Use Working Directory**
Navigate to project root first:
```
Change to the Pip-Droid project directory
```

### **5. Leverage Full Context**
```
Read all quest-related files and explain the complete data flow
```

---

## 🎉 Next Steps

1. **RESTART CURSOR** (Required for Desktop Commander to load)

2. **Test Terminal Control**:
   ```
   Run 'echo Hello from Desktop Commander'
   ```

3. **Try File Operations**:
   ```
   List all Kotlin files in the quest module
   ```

4. **Build Your App**:
   ```
   Build debug APK for Pip-Droid
   ```

5. **Explore Your Codebase**:
   ```
   Analyze the architecture of the stats engine
   ```

---

**☢️ Your AI just became a full-system operator! ☢️**

Desktop Commander + PromptX + Pollinations.AI + Puppeteer = **The Ultimate AI Development Team**

Ready to revolutionize your workflow! 🚀💻⚡

