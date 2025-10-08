# PromptX MCP Server Guide

## 🎭 Transform Your AI into Industry Experts

**PromptX** is a leading AI agent context platform that turns your AI into professional experts with specialized knowledge, tools, and cognitive memory.

**Official Repository**: https://github.com/deepractice/promptx

---

## 🌟 What is PromptX?

PromptX is **not just another tool** - it's a revolutionary platform that:

✨ **Creates AI Roles** - Transform AI into product managers, architects, developers, etc.  
🔧 **Builds Smart Tools** - Integrate APIs, databases, and services with natural language  
🧠 **Provides Cognitive Memory** - AI remembers context across conversations  
💬 **Natural Conversation** - Just talk naturally - no complex commands needed  

**Core Philosophy**: *Treat AI as a person, not software*

---

## 📁 Configuration

Your `.cursor/mcp.json` now includes:

```json
{
  "mcpServers": {
    "promptx": {
      "command": "npx",
      "args": ["-y", "@promptx/mcp-server"]
    }
  }
}
```

**Zero configuration needed!** 🎉

---

## 🚀 How to Use in Cursor

### After Restarting Cursor:

PromptX uses **natural conversation** - no complex commands!

### Step 1: Discover Experts
```
User: "Show me what experts are available"
AI:   Displays 23+ professional roles instantly
```

### Step 2: Summon Expert
```
User: "I need a product manager expert"
AI:   Transforms into professional PM with complete methodology
```

### Step 3: Professional Dialogue
```
User: "Help me redesign the Pip-Droid app's quest log"
AI:   Provides deep product strategy as a professional PM
```

---

## 🎭 Built-in Roles & Experts

PromptX comes with 23+ professional roles:

### 💼 **Product & Strategy**
- **Product Manager** - Product strategy, user research, roadmap planning
- **Technical Product Manager** - Engineering + product dual expertise
- **UX Designer** - User experience and interface design
- **Business Analyst** - Data analysis and business insights

### 💻 **Engineering & Development**
- **Software Architect** - System design and architecture patterns
- **Backend Developer** - API design, databases, server logic
- **Frontend Developer** - UI/UX implementation, React, Compose
- **DevOps Engineer** - CI/CD, deployment, infrastructure
- **QA Engineer** - Testing strategies, automation, quality assurance

### 📊 **Data & Analytics**
- **Data Scientist** - Machine learning, statistical analysis
- **Data Engineer** - Data pipelines, ETL, data architecture
- **Business Intelligence Analyst** - Reporting, dashboards, insights

### 🎨 **Content & Design**
- **Content Writer** - Technical writing, documentation
- **Copywriter** - Marketing copy, persuasive writing
- **Visual Designer** - Graphic design, branding, visual identity

### 🔧 **Specialized Roles**
- **Security Expert** - Security audits, vulnerability assessment
- **Performance Engineer** - Optimization, profiling, scaling
- **Mobile Developer** - iOS/Android specific expertise
- **AI/ML Engineer** - AI model development and deployment

---

## 🎨 Special Features: The Creation Twins

### 🎭 **Nuwa - AI Role Designer**

**Create custom AI experts with natural language**

```
User: "Activate Nuwa, I want to create an Android launcher expert"
AI:   [Nuwa creates specialized role with Android UI, launcher APIs, Jetpack Compose expertise]
```

**Example Custom Roles for Pip-Droid:**

| **What You Want** | **Say to Nuwa** | **Result** |
|-------------------|-----------------|------------|
| Android UI Expert | "Create an expert in Android Jetpack Compose with Pip-Boy theming" | AI becomes Compose + theming specialist |
| Fallout Lore Expert | "I need someone who knows Fallout universe deeply" | AI provides authentic Fallout references |
| Terminal UX Expert | "Create expert in retro terminal UI design" | AI designs authentic CRT interfaces |

### 🔧 **Luban - Tool Integration Master**

**Connect any API or service in 3 minutes**

```
User: "Activate Luban, connect to my PostgreSQL database"
AI:   [Luban creates database tool with safe queries]
```

**Useful Integrations for Pip-Droid:**

| **Integration** | **Command** | **Use Case** |
|-----------------|-------------|--------------|
| GitHub API | "Connect GitHub for issues" | Auto-create issues from user feedback |
| Firebase | "Integrate Firebase" | Sync quest data, analytics |
| Google Play | "Connect Play Console" | Monitor app reviews, ratings |

### ✍️ **Writer - Professional Content Creator**

**Create authentic, human-like content**

```
User: "Activate Writer, I need release notes that don't sound like AI"
AI:   [Writer creates engaging, authentic release notes]
```

**Perfect for:**
- Release notes and changelogs
- App Store descriptions
- Technical blog posts
- Marketing materials
- User documentation

---

## 🛠️ Built-in Tools

PromptX includes professional document processing tools:

### 📊 **Excel Tool**
- Data analysis and insights
- Automated report generation
- Chart visualization
- Data processing automation

**Use Case**: Analyze user analytics for Pip-Droid

```
@promptx Analyze the user engagement data in this Excel file
```

### 📝 **Word Tool**
- Document reading and analysis
- Professional document creation
- Batch text replacement
- Format conversion

**Use Case**: Generate comprehensive documentation

```
@promptx Create a user manual from these feature descriptions
```

### 📄 **PDF Reader**
- Page-by-page reading with caching
- Content analysis and extraction
- Image extraction
- Smart caching for speed

**Use Case**: Analyze competitor apps or research papers

```
@promptx Summarize this Android development best practices PDF
```

---

## 💡 Use Cases for Pip-Droid Development

### 1. **Product Strategy Session**
```
User: "I need a product manager expert"
AI:   [Becomes PM]
User: "Help me prioritize features for v0.2.0"
AI:   [Analyzes current features, suggests roadmap with user impact analysis]
```

### 2. **Architecture Review**
```
User: "Summon software architect"
AI:   [Becomes architect]
User: "Review my MVVM implementation in Pip-Droid"
AI:   [Provides detailed architecture analysis with improvement suggestions]
```

### 3. **UX Improvement**
```
User: "I need a UX designer"
AI:   [Becomes UX designer]
User: "Improve the Quest Log's user flow"
AI:   [Provides UX analysis with wireframes and interaction patterns]
```

### 4. **Marketing Content**
```
User: "Activate Writer"
AI:   [Becomes Writer]
User: "Write Play Store description for Pip-Droid"
AI:   [Creates compelling, authentic app description that converts]
```

### 5. **Custom Android Expert**
```
User: "Activate Nuwa, create an expert in Android launchers with Fallout aesthetic"
AI:   [Creates specialized role]
User: "How can I improve the CRT effects performance?"
AI:   [Provides Android-specific optimization with Fallout context]
```

---

## 🎯 Example Conversations

### **Scenario 1: Feature Planning**

```
You: "Show me available experts"
AI:  [Lists 23 roles including Product Manager, UX Designer, etc.]

You: "I need a product manager"
AI:  "I'm now your Product Manager. How can I help with your product?"

You: "Help me plan the v0.2.0 release for Pip-Droid"
AI:  "Let's start with your vision for v0.2.0. Based on v0.1.0-beta which 
      includes S.P.E.C.I.A.L. stats and Quest Log, what are your top priorities?"

You: "Users want Google Calendar sync for quests"
AI:  [Provides detailed implementation strategy, user story breakdown, 
      acceptance criteria, and timeline estimates as a professional PM]
```

### **Scenario 2: Technical Architecture**

```
You: "Summon software architect"
AI:  "I'm now your Software Architect. Ready to discuss system design."

You: "Review my current Room database schema for quests"
AI:  [Analyzes schema, suggests normalization, indexing strategies, 
      migration patterns, and performance optimizations]

You: "How should I structure the calendar sync?"
AI:  [Provides detailed architecture with repository pattern, sync strategies,
      conflict resolution, and offline-first design]
```

### **Scenario 3: Content Creation**

```
You: "Activate Writer"
AI:  "Writer activated. Let's create content that feels genuinely human."

You: "Write release notes for v0.1.0-beta that excite users"
AI:  [Creates engaging release notes with personality, user benefits,
      and authentic excitement - not corporate speak]

You: "Now create a blog post about the S.P.E.C.I.A.L. stats feature"
AI:  [Writes compelling technical content that connects with users emotionally]
```

---

## 🆚 PromptX vs Traditional AI

| Aspect | Traditional AI | PromptX AI |
|--------|---------------|------------|
| **Identity** | Generic assistant | Professional expert with role |
| **Knowledge** | General knowledge | Specialized domain expertise |
| **Memory** | Forgets context | Remembers across sessions |
| **Communication** | Formal responses | Natural professional dialogue |
| **Tools** | Limited APIs | Extensible tool ecosystem |

---

## 🔧 Advanced Features

### **Cognitive Memory System**

PromptX AI remembers:
- Previous conversations
- Your preferences
- Project context
- Decision history

```
Session 1: "We decided to use MVVM for Pip-Droid"
Session 2 (days later): "Continue with the architecture we discussed"
AI: "Yes, continuing with MVVM pattern for Pip-Droid..."
```

### **Tool Chaining**

Combine multiple tools for complex workflows:

```
@promptx Read this PDF research paper (PDF Reader)
@promptx Analyze the data mentioned (Excel Tool)
@promptx Write a summary for our docs (Word Tool)
```

### **Multi-Expert Collaboration**

Switch between experts seamlessly:

```
User: "Product manager, what should we build?"
AI:   [As PM: "We should add calendar sync"]

User: "Software architect, how should we implement it?"
AI:   [As Architect: "Use Repository pattern with..."]

User: "UX designer, how should users interact with it?"
AI:   [As UX: "Show sync status with..."]
```

---

## 📖 How It Works

### **The Role System**

1. **Role Discovery** - AI shows available expert roles
2. **Role Activation** - Natural language summons the expert
3. **Context Injection** - AI receives complete domain knowledge
4. **Professional Dialogue** - AI responds as that expert

### **Behind the Scenes**

PromptX uses MCP (Model Context Protocol) to:
- Inject role-specific prompts and knowledge
- Provide specialized tools and capabilities
- Maintain conversational context
- Enable cognitive memory

---

## 🐛 Troubleshooting

### Issue 1: PromptX Not Available

**Symptoms**: `@promptx` doesn't appear in Cursor  
**Solutions**:
1. Restart Cursor completely
2. Verify `.cursor/mcp.json` configuration
3. Check Node.js installation: `node --version`
4. Clear npm cache: `npm cache clean --force`

### Issue 2: Role Not Activating

**Symptoms**: AI doesn't transform into requested role  
**Solutions**:
- Use natural language: "I need a product manager"
- Ask to see available roles: "Show me experts"
- Be specific: "Become a software architect"

### Issue 3: Memory Not Persistent

**Symptoms**: AI forgets previous conversations  
**Solutions**:
- Ensure PromptX data directory exists
- Check permissions on `~/.promptx/`
- Restart Cursor to refresh memory system

### Issue 4: Tools Not Working

**Symptoms**: Excel/Word/PDF tools fail  
**Solutions**:
- Ensure files are accessible
- Check file formats (Excel: .xlsx, Word: .docx, PDF: .pdf)
- Verify file permissions

---

## 💫 Pro Tips

### 1. **Be Natural**
❌ Don't: "Execute product manager mode with parameters..."  
✅ Do: "I need a product manager expert"

### 2. **Context is King**
Give experts context about your project:
```
"I'm building Pip-Droid, a Fallout-themed Android launcher. 
 I need a UX designer to help improve the quest log."
```

### 3. **Switch Roles Freely**
Don't hesitate to change experts mid-conversation:
```
"Thanks PM. Now I need a software architect."
```

### 4. **Use Writer for Content**
Activate Writer for any user-facing content:
- Release notes
- Marketing copy
- Documentation
- Blog posts

### 5. **Create Custom Experts**
Use Nuwa for project-specific expertise:
```
"Activate Nuwa, create an expert in Android Material Design 3 
 with Fallout Pip-Boy aesthetics"
```

---

## 🎯 Quick Reference

### **Essential Commands**

| Command | Result |
|---------|--------|
| "Show me experts" | List all available roles |
| "I need a [role]" | Activate specific expert |
| "Activate Nuwa" | Start role creator |
| "Activate Luban" | Start tool builder |
| "Activate Writer" | Start content creator |

### **Available Expert Roles**

Product Manager | UX Designer | Software Architect | Backend Dev | Frontend Dev  
DevOps Engineer | QA Engineer | Data Scientist | Security Expert | Content Writer  
Business Analyst | Mobile Developer | AI/ML Engineer | Performance Engineer  

---

## 🔗 Resources

- **Official Site**: https://promptx.deepractice.ai/
- **GitHub**: https://github.com/deepractice/promptx
- **Documentation**: https://github.com/deepractice/promptx/tree/main/docs
- **Discord Community**: Join for support
- **npm Package**: [@promptx/mcp-server](https://www.npmjs.com/package/@promptx/mcp-server)

---

## 🎉 Benefits for Pip-Droid

✅ **Expert Guidance** - Get professional advice from specialized roles  
✅ **Faster Development** - Architectural decisions made by experts  
✅ **Better UX** - UX designers guide interface improvements  
✅ **Quality Content** - Writer creates engaging marketing materials  
✅ **Custom Expertise** - Create Android launcher specialists with Nuwa  
✅ **Cognitive Memory** - AI remembers your project context  

---

## 🚀 Next Steps

1. **Restart Cursor** - Close and reopen completely
2. **Discover Experts** - Say "Show me available experts"
3. **Activate Your First Role** - Try "I need a product manager"
4. **Get Expert Help** - Discuss Pip-Droid development
5. **Create Custom Roles** - Use Nuwa for specialized expertise

---

**☢️ Transform your AI into a team of professional experts! ☢️**

Your Pip-Droid development just got a whole team of specialists! 🎭🚀

