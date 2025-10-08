# Contributing to Pip-Droid

First off, thank you for considering contributing to Pip-Droid! It's people like you that make this launcher awesome. ☢️

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Commit Messages](#commit-messages)
- [Pull Request Process](#pull-request-process)
- [Project Structure](#project-structure)
- [Testing Guidelines](#testing-guidelines)

---

## 📜 Code of Conduct

This project adheres to a Code of Conduct that all contributors are expected to follow. Please read [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before contributing.

**TL;DR**: Be respectful, inclusive, and constructive. The wasteland is harsh enough; let's keep our community welcoming.

---

## 🤝 How Can I Contribute?

### 🐛 Reporting Bugs

Before creating bug reports, please check the [issue tracker](https://github.com/Snapwave333/pip-droid/issues) to avoid duplicates.

**When filing a bug report, include**:
- Clear, descriptive title
- Steps to reproduce
- Expected vs actual behavior
- Screenshots/recordings if applicable
- Device info (model, Android version)
- App version
- Crash logs if available

**Use this template**:
```markdown
**Bug Description**
A clear description of the bug.

**To Reproduce**
1. Go to '...'
2. Tap on '...'
3. Scroll down to '...'
4. See error

**Expected Behavior**
What you expected to happen.

**Screenshots**
Add screenshots if applicable.

**Device Info**
 - Device: [e.g. Pixel 8]
 - Android Version: [e.g. 14]
 - App Version: [e.g. 0.1.0-beta]

**Additional Context**
Any other relevant information.
```

### 💡 Suggesting Features

We love feature suggestions! Before creating a feature request:
- Check if it's already in the [roadmap](docs/ROADMAP.md)
- Search [existing issues](https://github.com/Snapwave333/pip-droid/issues)
- Consider if it fits the Pip-Boy theme

**Feature request template**:
```markdown
**Feature Description**
Clear description of the feature.

**Why This Feature?**
What problem does it solve? How does it enhance the launcher?

**Proposed Implementation**
(Optional) How you envision it working.

**Fallout Connection**
How does this relate to the Pip-Boy/Fallout universe?

**Mockups/Examples**
(Optional) Visual references or examples.
```

### 🔨 Contributing Code

We welcome code contributions! Here are areas we need help:

#### High Priority
- 🔊 **Sound Effects System** - Add Pip-Boy sounds
- 🏆 **Achievement System** - Implement achievements/perks
- 🎮 **Mini-Games** - Build holotape games
- 📻 **Radio Scraper** - Local station detection

#### Medium Priority
- 🗺️ **Map Enhancements** - Waypoints, markers
- 📦 **Inventory Improvements** - Weight, condition, favorites
- 🔄 **Google Sync** - Full Calendar/Tasks integration
- 🎨 **Widget System** - Home screen widgets

#### Low Priority (but appreciated!)
- 🌐 **Translations** - Multi-language support
- 📝 **Documentation** - Improve guides and docs
- 🧪 **Testing** - Unit and integration tests
- ♿ **Accessibility** - Improve accessibility features

---

## 🛠️ Development Setup

### Prerequisites
- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17** or later
- **Gradle 8.5** (included in wrapper)
- **Git**

### Setup Steps

```bash
# 1. Fork the repository on GitHub

# 2. Clone your fork
git clone https://github.com/YOUR_USERNAME/pip-droid.git
cd pip-droid

# 3. Add upstream remote
git remote add upstream https://github.com/Snapwave333/pip-droid.git

# 4. Create a feature branch
git checkout -b feature/your-feature-name

# 5. Open in Android Studio
# File → Open → Select pip-droid folder

# 6. Sync Gradle files
# File → Sync Project with Gradle Files

# 7. Build the project
./gradlew assembleDebug

# 8. Run on device/emulator
# Run → Run 'app'
```

### Project Dependencies

Key libraries used:
- **Jetpack Compose** - Modern UI toolkit
- **Kotlin Coroutines** - Asynchronous programming
- **DataStore** - Data persistence
- **Gson** - JSON serialization
- **Navigation Compose** - App navigation
- **Hilt** - Dependency injection (setup ready)

---

## 📝 Coding Standards

### Kotlin Style Guide

Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).

**Key points**:
- Use 4 spaces for indentation (no tabs)
- Max line length: 120 characters
- Use camelCase for functions and variables
- Use PascalCase for classes
- Use SCREAMING_SNAKE_CASE for constants

### Code Quality

```kotlin
// ✅ Good - Clear, documented, type-safe
/**
 * Calculates XP required for next stat level
 * @param currentLevel Current stat level (1-10)
 * @return XP required, or 0 if max level
 */
fun calculateXpToNextLevel(currentLevel: Int): Int {
    if (currentLevel >= MAX_STAT_LEVEL) return 0
    return BASE_XP * currentLevel * currentLevel
}

// ❌ Bad - No docs, magic numbers, unclear
fun calcXp(lvl: Int): Int {
    return 100 * lvl * lvl
}
```

### Compose Guidelines

```kotlin
// ✅ Good - Stateless, reusable, documented
/**
 * Pip-Boy styled button
 */
@Composable
fun PipBoyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // Implementation
}

// ❌ Bad - Stateful, hard-coded, no docs
@Composable
fun Button() {
    var clicked by remember { mutableStateOf(false) }
    // Hard-coded implementation
}
```

### Architecture Patterns

Follow **Clean Architecture** and **MVVM**:

```
UI Layer (Compose + ViewModels)
    ↓ uses
Domain Layer (UseCases + Models)
    ↓ uses
Data Layer (Repositories + DataSources)
```

**Example structure**:
```kotlin
// ViewModel (UI Layer)
class QuestViewModel @Inject constructor(
    private val questRepository: QuestRepository
) : ViewModel() {
    val quests = questRepository.getAllQuestsFlow()
    fun completeQuest(id: String) { /* ... */ }
}

// Repository (Data Layer)
class QuestRepository(private val context: Context) {
    fun getAllQuestsFlow(): Flow<List<Quest>> { /* ... */ }
    suspend fun completeQuest(id: String) { /* ... */ }
}
```

---

## 💬 Commit Messages

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- **feat**: New feature
- **fix**: Bug fix
- **docs**: Documentation changes
- **style**: Code style changes (formatting, no logic change)
- **refactor**: Code refactoring
- **perf**: Performance improvements
- **test**: Adding or updating tests
- **chore**: Build process, dependencies, etc.

### Examples

```bash
# Good commit messages
feat(stats): add S.P.E.C.I.A.L. level progression
fix(radio): resolve streaming crash on network loss
docs(readme): update installation instructions
refactor(quests): extract quest card to separate component

# Bad commit messages
update stuff
fixed bug
changes
WIP
```

### Detailed Example

```
feat(terminal): add command history navigation

Implement up/down arrow key navigation for terminal command history.
Users can now cycle through previously entered commands, similar to
bash/zsh behavior.

- Add command history list
- Implement arrow key handlers
- Add history index tracking
- Update terminal UI to support key events

Closes #42
```

---

## 🔄 Pull Request Process

### Before Submitting

1. ✅ Code follows style guidelines
2. ✅ All tests pass (`./gradlew test`)
3. ✅ App builds successfully (`./gradlew assembleDebug`)
4. ✅ No lint errors (`./gradlew lint`)
5. ✅ Self-review completed
6. ✅ Comments added for complex code
7. ✅ Documentation updated
8. ✅ CHANGELOG.md updated

### PR Template

```markdown
## Description
Brief description of changes.

## Type of Change
- [ ] Bug fix (non-breaking change fixing an issue)
- [ ] New feature (non-breaking change adding functionality)
- [ ] Breaking change (fix or feature causing existing functionality to change)
- [ ] Documentation update

## Related Issues
Closes #(issue number)

## Testing
- [ ] Tested on physical device
- [ ] Tested on emulator
- [ ] Unit tests added/updated
- [ ] Manual testing completed

## Screenshots
(If applicable)

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex sections
- [ ] Documentation updated
- [ ] No new warnings generated
- [ ] Tests pass
- [ ] CHANGELOG.md updated
```

### Review Process

1. Create PR from your feature branch to `main`
2. Automated checks run (build, lint, tests)
3. At least one maintainer review required
4. Address review feedback
5. Maintainer approves and merges

---

## 📂 Project Structure

```
pip-droid/
├── src/main/java/com/supernova/pipboy/
│   ├── ui/
│   │   ├── screens/          # Screen composables
│   │   ├── components/       # Reusable UI components
│   │   ├── effects/          # CRT effects
│   │   ├── theme/            # Theming
│   │   ├── viewmodel/        # ViewModels
│   │   └── navigation/       # Navigation logic
│   ├── data/
│   │   ├── model/            # Data models
│   │   ├── repository/       # Repositories
│   │   ├── quests/           # Quest system
│   │   ├── stats/            # Stats system
│   │   └── terminal/         # Terminal commands
│   └── PipBoyApplication.kt  # Application class
├── res/                      # Resources
├── docs/                     # Documentation
└── build.gradle             # Build configuration
```

### Adding New Features

1. **Screen**: Add to `ui/screens/`
2. **Component**: Add to `ui/components/`
3. **ViewModel**: Add to `ui/viewmodel/`
4. **Repository**: Add to `data/repository/`
5. **Model**: Add to `data/model/`

---

## 🧪 Testing Guidelines

### Unit Tests

```kotlin
@Test
fun `calculateXpToNextLevel returns correct XP for level 5`() {
    val stats = SpecialStat(type = SpecialStatType.STRENGTH, level = 5)
    val xpRequired = stats.xpToNextLevel
    assertEquals(2500, xpRequired)
}
```

### UI Tests

```kotlin
@Test
fun questCard_showsTitle() {
    composeTestRule.setContent {
        QuestCard(
            quest = testQuest,
            onQuestClick = {},
            primaryColor = PipBoyColor.GREEN
        )
    }
    
    composeTestRule
        .onNodeWithText("Test Quest")
        .assertIsDisplayed()
}
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test
./gradlew test --tests QuestRepositoryTest

# Run with coverage
./gradlew testDebugUnitTest jacocoTestReport
```

---

## 🎨 Design Guidelines

### Pip-Boy Aesthetic

- **Colors**: Monochrome (green, amber, blue, white)
- **Typography**: Monospace fonts
- **Layout**: Retro-futuristic, chunky buttons
- **Effects**: CRT scanlines, glow, flicker
- **Sounds**: Terminal beeps, clicks (when implemented)

### UI Best Practices

1. **Consistency**: Match existing Pip-Boy style
2. **Performance**: Minimize recompositions
3. **Accessibility**: Support TalkBack, large text
4. **Dark Mode**: Already dark by default
5. **Animations**: Smooth, retro-feeling

---

## 📞 Getting Help

- **Questions**: [GitHub Discussions](https://github.com/Snapwave333/pip-droid/discussions)
- **Bugs**: [GitHub Issues](https://github.com/Snapwave333/pip-droid/issues)
- **Chat**: (Coming soon - Discord/Matrix)

---

## 🏆 Recognition

Contributors will be:
- Listed in [CONTRIBUTORS.md](CONTRIBUTORS.md)
- Mentioned in release notes
- Given credit in the app's About section

---

## 📄 License

By contributing, you agree that your contributions will be licensed under the [MIT License](LICENSE).

---

**Thank you for contributing to Pip-Droid! Together, we're building the best Pip-Boy launcher in the wasteland.** ☢️

*War. War never changes. But this project? It's constantly evolving thanks to contributors like you.*

