# Serena MCP Server Guide

## 🧠 Semantic Code Intelligence - The Ultimate Coding Agent

**Serena** is a revolutionary coding agent toolkit with **13.8k+ GitHub stars** that provides **semantic code retrieval and editing** using language servers - making it the most powerful coding assistant for complex codebases.

**Official Repository**: https://github.com/oraios/serena  
**Sponsored by**: Microsoft VS Code team & GitHub Open Source

---

## 🌟 What Makes Serena Revolutionary?

Serena is **NOT just another code editor** - it's a **semantic code intelligence system** that:

🧠 **Understands Code Semantically** - Uses language servers to comprehend code structure  
🔍 **Smart Symbol Search** - Find symbols, references, and definitions instantly  
✂️ **Surgical Editing** - Modify code by symbol, not by line numbers  
📊 **Project Understanding** - Automatically analyzes and remembers project structure  
🎯 **Context-Aware** - Maintains memory of your codebase and conversations  
⚡ **Multi-Language** - Supports 20+ programming languages  

---

## 🆚 Serena vs Other Tools

### **What Makes Serena Unique**

| Feature | Serena | Desktop Commander | Other MCP Servers |
|---------|--------|-------------------|-------------------|
| **Code Understanding** | Semantic (LSP) | Text-based | Text-based |
| **Symbol Search** | ✅ By meaning | ❌ By text | ❌ By text |
| **Code Editing** | By symbol | By line/diff | By line |
| **Project Memory** | ✅ Persistent | ❌ None | ❌ None |
| **Refactoring** | ✅ Intelligent | Manual | Manual |
| **Language Support** | 20+ languages | N/A | Limited |

**Key Difference**: Serena uses **Language Server Protocol (LSP)** to understand code structure, not just text patterns.

---

## 📁 Configuration

Your `.cursor/mcp.json` now includes:

```json
{
  "mcpServers": {
    "serena": {
      "command": "uvx",
      "args": ["--from", "serena-ai", "serena", "mcp"],
      "env": {
        "SERENA_PROJECT_ROOT": "${workspaceFolder}"
      }
    }
  }
}
```

**Requirements**:
- Python 3.11+ with `uv` package manager
- Language servers (auto-installed for supported languages)

---

## 🚀 Setup Instructions

### Step 1: Install UV (if not already installed)

**Windows (PowerShell)**:
```powershell
irm https://astral.sh/uv/install.ps1 | iex
```

**macOS/Linux**:
```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

**Or via pip**:
```bash
pip install uv
```

### Step 2: Verify Installation

```bash
uv --version
```

### Step 3: Restart Cursor

Close Cursor completely and reopen for Serena to load.

---

## 🎯 Core Capabilities

### 1. **Semantic Symbol Search** 🔍

Find symbols by meaning, not text:

```
Find all ViewModel classes in the Pip-Droid project
Show me where QuestRepository is defined
Find all references to PipBoyColor enum
List all Composable functions in StatusScreen
```

**Better than grep because**:
- Understands code structure
- Filters by symbol type (class, function, variable)
- Finds semantic relationships

### 2. **Intelligent Code Editing** ✂️

Edit code by symbol, not line numbers:

```
Replace the body of refreshQuests() function in QuestRepository
Insert a new function after updateQuestStatus()
Add a parameter to the QuestLogScreen composable
Modify the CRTEffect class to add animation property
```

**Advantages**:
- No line numbers needed
- Automatically handles formatting
- Updates all references
- Preserves code structure

### 3. **Project Understanding** 📊

Serena learns your codebase:

```
What is the architecture of Pip-Droid?
Explain the data flow from QuestRepository to UI
Show me the project structure and key components
What testing frameworks are used?
```

**Features**:
- Automatic onboarding
- Project structure analysis
- Dependency mapping
- Build system detection

### 4. **Persistent Memory** 🧠

Serena remembers your project:

```
Remember: QuestRepository uses DataStore for local persistence
Remember: All screens follow MVVM architecture pattern
Remember: Use PipBoyColor.TERMINAL_GREEN for all text
```

**Benefits**:
- Context persists across conversations
- Project-specific knowledge
- Design decisions tracked
- Conventions enforced

### 5. **Symbol-Based Refactoring** 🔄

Intelligent refactoring operations:

```
Rename PipBoyColor to VaultColor across entire project
Extract this code into a new function called calculateXP
Move refreshQuests function to a new utility class
Change QuestStatus from enum to sealed class
```

**Smart refactoring**:
- Updates all references
- Maintains code correctness
- Handles imports automatically
- Preserves functionality

---

## 🛠️ Available Tools (30+ Tools!)

### **Code Navigation**

| Tool | Description |
|------|-------------|
| `find_symbol` | Search for symbols globally or locally |
| `find_referencing_symbols` | Find where a symbol is used |
| `get_symbols_overview` | Get top-level symbols in a file |

### **Code Editing**

| Tool | Description |
|------|-------------|
| `replace_symbol_body` | Replace entire symbol definition |
| `insert_before_symbol` | Insert code before symbol |
| `insert_after_symbol` | Insert code after symbol |
| `replace_regex` | Replace using regex patterns |

### **File Operations**

| Tool | Description |
|------|-------------|
| `read_file` | Read file contents |
| `create_text_file` | Create or overwrite file |
| `find_file` | Find files by path pattern |
| `list_dir` | List directory contents |

### **Project Management**

| Tool | Description |
|------|-------------|
| `onboarding` | Analyze project structure |
| `activate_project` | Switch between projects |
| `search_for_pattern` | Search for text patterns |

### **Memory System**

| Tool | Description |
|------|-------------|
| `write_memory` | Store project knowledge |
| `read_memory` | Retrieve stored knowledge |
| `list_memories` | List all memories |
| `delete_memory` | Remove a memory |

### **Advanced Tools** (Optional)

| Tool | Description |
|------|-------------|
| `execute_shell_command` | Run terminal commands |
| `restart_language_server` | Restart LSP server |
| `delete_lines` | Remove line ranges |
| `insert_at_line` | Insert at specific line |

---

## 💡 Use Cases for Pip-Droid

### **Use Case 1: Intelligent Code Search**

```
You: "Find all ViewModel classes that manage quest data"
AI:  [Uses find_symbol with semantic filtering]
     [Finds: QuestViewModel, QuestLogViewModel, QuestDetailViewModel]
     [Shows definitions and usage patterns]
     ✅ Found 3 ViewModels with quest-related functionality
```

**vs grep**: grep would find false positives like "QuestView" or "viewModel" mentions

### **Use Case 2: Symbol-Based Refactoring**

```
You: "Rename CRTEffect to PipBoyEffect across the entire project"
AI:  [Uses Serena's semantic refactoring]
     [Updates: 15 files, 47 references]
     [Updates imports automatically]
     [Preserves functionality]
     ✅ Refactored successfully, all references updated
```

**vs Desktop Commander**: Would require manual find-replace with potential errors

### **Use Case 3: Project Memory**

```
Session 1:
You: "Remember: All UI text should use PipBoyColor.TERMINAL_GREEN"
AI:  [Stores in project memory]

Session 2 (days later):
You: "Add a new settings screen"
AI:  [Recalls memory about color scheme]
     [Automatically uses PipBoyColor.TERMINAL_GREEN]
     ✅ Implemented with correct styling
```

**Benefit**: No need to repeat context across sessions

### **Use Case 4: Architectural Analysis**

```
You: "Analyze the architecture of the quest system"
AI:  [Uses get_symbols_overview on quest-related files]
     [Maps data flow: Repository → ViewModel → Screen]
     [Identifies patterns: MVVM, DataStore, Compose]
     
     "The quest system follows MVVM architecture:
      - Data Layer: QuestRepository (DataStore persistence)
      - Domain Layer: Quest models and use cases
      - UI Layer: QuestLogScreen (Compose) + QuestViewModel
      - Sync: GoogleQuestSyncStub (placeholder for API)"
     ✅ Complete architectural understanding
```

### **Use Case 5: Smart Code Insertion**

```
You: "Add a function to calculate total XP after QuestRepository.refreshQuests()"
AI:  [Uses find_symbol to locate refreshQuests]
     [Uses insert_after_symbol]
     [Maintains proper indentation and context]
     
     fun calculateTotalXP(): Int {
         return _quests.value.sumOf { it.reward.xp }
     }
     
     ✅ Inserted at correct location with proper formatting
```

---

## 🎨 Supported Languages (20+)

Serena has **built-in language server support** for:

### **Mobile Development**
- ✅ **Kotlin** (Perfect for Pip-Droid!)
- ✅ **Java** (Android legacy code)
- ✅ Swift (iOS)
- ✅ Objective-C

### **Web Development**
- ✅ **JavaScript/TypeScript**
- ✅ **Python**
- ✅ **Go**
- ✅ **Rust**
- ✅ **C/C++**

### **Other Languages**
- C#, Ruby, PHP, Elixir, Erlang, Haskell, OCaml, Scala, Clojure, and more!

**For Android**: Kotlin and Java are fully supported with all LSP features.

---

## 🔧 Configuration Options

### **Project Root**

Set via `SERENA_PROJECT_ROOT` environment variable:
```json
{
  "env": {
    "SERENA_PROJECT_ROOT": "${workspaceFolder}"
  }
}
```

### **Memory Storage**

Serena stores project-specific memories in:
- `.serena/memories/` directory in your project
- Automatically created on first use

### **Language Server Config**

Serena auto-detects and configures language servers based on your project:
- Kotlin: Uses Kotlin Language Server
- Java: Uses Eclipse JDT LS
- Python: Uses Pylance/Pyright

---

## 🎯 Complete Workflow Examples

### **Workflow 1: Add New Feature with Serena**

```
Step 1: Understand Current Architecture
   You: "Analyze the stats tracking system architecture"
   AI:  [Serena analyzes StatsRepository, SpecialStats, etc.]
        [Provides architectural overview]
        ✅ Architecture understood

Step 2: Find Related Code
   You: "Find all ViewModel classes that display stats"
   AI:  [Semantic search finds StatusViewModel]
        [Shows usage patterns]
        ✅ Found relevant code

Step 3: Create Memory
   You: "Remember: Stats should update in real-time using Flow"
   AI:  [Stores in project memory]
        ✅ Design decision recorded

Step 4: Implement Feature
   You: "Add a function to calculate stat multipliers after SpecialStats.calculateLevel()"
   AI:  [Uses insert_after_symbol]
        [Creates properly formatted function]
        ✅ Feature implemented

Step 5: Update References
   You: "Find all places that call calculateLevel and add multiplier calculation"
   AI:  [Finds references with find_referencing_symbols]
        [Updates each location]
        ✅ All references updated
```

### **Workflow 2: Intelligent Refactoring**

```
Step 1: Find Current Usage
   You: "Find all references to PipBoyColor enum"
   AI:  [Serena finds 87 references across 23 files]
   
Step 2: Refactor with Serena
   You: "Rename PipBoyColor to VaultTecColor across the project"
   AI:  [Uses semantic refactoring]
        [Updates all 87 references]
        [Updates imports]
        [Maintains functionality]
        ✅ Refactored successfully

Step 3: Verify Changes
   You: "Find any remaining references to PipBoyColor"
   AI:  [Searches semantically]
        [Finds 0 references]
        ✅ Refactoring complete
```

### **Workflow 3: Cross-File Analysis**

```
Step 1: Analyze Data Flow
   You: "Trace the data flow from QuestRepository to QuestLogScreen"
   AI:  [Finds QuestRepository definition]
        [Finds all referencing symbols]
        [Maps: Repository → ViewModel → Screen]
        [Shows Compose state flow]
        ✅ Complete data flow mapped

Step 2: Identify Dependencies
   You: "What dependencies does QuestRepository have?"
   AI:  [Analyzes imports and constructor parameters]
        [Shows: DataStore, Context, GoogleQuestSyncStub]
        ✅ Dependencies identified

Step 3: Suggest Improvements
   You: "How can we improve the quest system architecture?"
   AI:  [Based on semantic analysis]
        [Suggests: Use cases layer, better error handling]
        ✅ Architecture recommendations provided
```

---

## 💎 Serena + Other MCP Servers

Serena works **perfectly** with your existing servers:

### **Serena + Desktop Commander**

```
1. [Serena] Find all test files in the project
2. [Desktop Commander] Run all unit tests
3. [Serena] Analyze failing test code semantically
4. [Serena] Fix the failing tests
5. [Desktop Commander] Run tests again to verify
```

### **Serena + PromptX**

```
1. [PromptX] I need a software architect
2. [Architect] Design a new feature architecture
3. [Serena] Analyze current codebase structure
4. [Serena] Implement the new architecture semantically
5. [Serena] Remember the architectural decisions
```

### **Serena + Pollinations + ElevenLabs**

```
1. [PromptX Writer] Write feature description
2. [Pollinations] Generate UI mockup
3. [Serena] Analyze existing UI patterns
4. [Serena] Implement new screen following patterns
5. [ElevenLabs] Generate tutorial audio
```

**Power Combo**: Serena provides the intelligence, others provide the actions!

---

## 🐛 Troubleshooting

### Issue 1: Serena Not Loading

**Symptoms**: Server doesn't appear after restart  
**Solutions**:
1. Install UV: `pip install uv`
2. Verify Python 3.11+: `python --version`
3. Check logs in Cursor
4. Restart Cursor completely

### Issue 2: Language Server Not Starting

**Symptoms**: Symbol search doesn't work  
**Solutions**:
- Wait for initial indexing (can take 1-2 minutes)
- Restart language server: Use Serena's `restart_language_server` tool
- Check language server is installed for your language

### Issue 3: Slow Performance

**Symptoms**: Searches take a long time  
**Solutions**:
- First search builds index (slow), subsequent searches are fast
- Exclude large directories (node_modules, build)
- Restart language server if it crashed

### Issue 4: Memory Issues

**Symptoms**: Out of memory errors  
**Solutions**:
- Large codebases need more RAM
- Close other applications
- Restart Cursor to free memory

---

## 📊 Serena Stats

| Metric | Value |
|--------|-------|
| **GitHub Stars** | 13.8k+ |
| **Languages Supported** | 20+ |
| **Available Tools** | 30+ |
| **Sponsors** | Microsoft VS Code, GitHub |
| **Contributors** | 60+ |
| **License** | MIT |

---

## 💡 Pro Tips

### **1. Let Serena Learn Your Project**

```
On first use: "Perform onboarding on the Pip-Droid project"
```
This creates a semantic map of your codebase.

### **2. Use Project Memory Extensively**

```
"Remember: All ViewModels should use Hilt for dependency injection"
"Remember: Compose previews must be annotated with @Preview"
"Remember: Database operations go through Repository layer"
```

### **3. Semantic Search > Text Search**

❌ Don't: "Search for 'quest'"  
✅ Do: "Find all classes that manage quest data"

### **4. Edit by Symbol, Not Lines**

❌ Don't: "Change lines 145-180 in QuestRepository"  
✅ Do: "Modify the refreshQuests() function to add caching"

### **5. Combine with Desktop Commander**

```
1. [Serena] Analyze and edit code semantically
2. [Desktop Commander] Build and test
3. [Serena] Fix issues found by tests
```

### **6. Use for Complex Refactoring**

Serena excels at:
- Renaming across large codebases
- Architectural changes
- Adding parameters to functions
- Extracting common code

---

## 🚀 Next Steps for Pip-Droid

After restarting Cursor:

1. **Project Onboarding**:
   ```
   Perform onboarding on the Pip-Droid project
   ```

2. **Explore Architecture**:
   ```
   Analyze the overall architecture of Pip-Droid
   Find all ViewModel classes
   Show me the data layer structure
   ```

3. **Set Up Memories**:
   ```
   Remember: Use MVVM architecture pattern
   Remember: All colors use PipBoyColor enum
   Remember: Database persistence via DataStore
   ```

4. **Intelligent Refactoring**:
   ```
   Find all Composable functions that use hardcoded colors
   Replace them to use PipBoyColor enum
   ```

5. **Cross-File Analysis**:
   ```
   Trace data flow from StatsRepository to StatusScreen
   Find all references to QuestRepository
   ```

---

## 🏆 Why Serena is Game-Changing

**Before Serena**:
- ❌ Text-based search (grep, find)
- ❌ Manual line-based editing
- ❌ No project memory
- ❌ Limited code understanding
- ❌ Error-prone refactoring

**With Serena**:
- ✅ Semantic code understanding
- ✅ Symbol-based editing
- ✅ Persistent project knowledge
- ✅ Architectural analysis
- ✅ Intelligent refactoring
- ✅ 20+ language support

---

**☢️ Serena brings semantic intelligence to your AI coding workflow! ☢️**

Your AI now **truly understands** your code, not just sees text! 🧠💻✨

**Documentation**: See official docs at https://github.com/oraios/serena

