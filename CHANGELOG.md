# Changelog

All notable changes to Pip-Droid will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.1.0-beta] - 2025-10-08

### 🎉 Initial Beta Release

The first public beta of Pip-Droid launcher! This release includes all core features for a fully functional Fallout Pip-Boy themed Android launcher.

### ✅ Added

#### Core Launcher
- Full Android launcher replacement functionality
- Six main tabs: STATUS, INVENTORY, DATA, MAP, RADIO, HOLOTAPES
- Bottom navigation with Pip-Boy styling
- Predictive back gesture support (Android 14+)
- Smooth animations and transitions
- Terminal boot sequence on app launch

#### S.P.E.C.I.A.L. Stats System
- Real-time phone usage tracking mapped to 7 Fallout stats
- Individual stat levels (1-10) with XP progression
- Progress bars showing XP to next level
- Expandable stat cards with descriptions and effects
- Total level and total XP tracking
- Persistent storage using DataStore

**Stats Tracked**:
- Strength (storage, heavy apps)
- Perception (notifications, sensors)
- Endurance (battery, uptime)
- Charisma (contacts, social apps)
- Intelligence (productivity apps)
- Agility (response time, gestures)
- Luck (achievements, random events)

#### Quest Log System
- Full CRUD operations (Create, Read, Update, Delete)
- 5 quest categories: MAIN, SIDE, MISC, RECURRING, FACTION
- 4 priority levels: CRITICAL, HIGH, NORMAL, LOW
- Multi-line objectives with completion checkboxes
- Due date selection with calendar picker
- Reminder notifications (1 day before)
- XP and Caps reward system
- Branching quest chains with prerequisites
- Quest progress auto-calculation
- Total XP/Caps/Level display
- Google Calendar/Tasks integration (stub)
- Local persistence with DataStore + JSON

#### Terminal Mode
- RobCo Termlink interface
- 40+ functional commands
- 20+ hidden Easter eggs
- Command history with arrow key navigation
- Real system integration (status, stats, quests)
- Color-coded output (green/red)
- Monospace terminal font
- Auto-scrolling output
- Enter key execution

**Commands Include**:
- System: `help`, `status`, `version`, `info`, `clear`, `exit`
- Stats: `stats`, `special`
- Quests: `quests`, `quest [id]`
- Features: `inv`, `radio`, `map`
- Easter Eggs: `war`, `gary`, `tunnelsnakes`, `vault [number]`, `nuka`, and more

#### CRT Visual Effects
- Authentic retro display simulation
- Scanlines (horizontal CRT lines)
- Phosphor glow (pulsing green effect)
- Scanline sweep (animated electron beam)
- Screen flicker (brightness variations)
- Vignette (darkened corners)
- Curvature simulation (edge darkening)
- Optional noise/static effect
- All effects toggleable in settings
- Minimal performance impact (~5-10 FPS)

#### Radio System
- 8 pre-configured Fallout.FM stations:
  - Galaxy News Radio
  - Radio New Vegas
  - Diamond City Radio
  - Fallout 4 Classical Radio
  - Fallout.FM Main
  - Fallout 76 Appalachia Radio
  - Fallout 1 & 2 Original Soundtrack
  - Megaton Radio (bonus)
- Station browser with metadata
- Volume control slider
- Play/pause/stop controls
- Safe, crash-proof implementation
- Streaming via network

#### UI Components
- Monochrome icon system
- Pip-Boy themed calendar
- Pip-Boy compass
- Progress bars and meters
- Expandable cards
- Tab navigation
- Theme settings panel
- RGB color picker
- Custom typography (monospace)

#### Data Layer
- DataStore for persistent storage
- Repository pattern architecture
- Flow-based reactive data
- JSON serialization with Gson
- Repositories:
  - SystemRepository (device monitoring)
  - AppRepository (app management)
  - QuestRepository (quest CRUD)
  - StatsRepository (S.P.E.C.I.A.L. tracking)
  
#### Theme System
- 4 color presets: Green, Amber, Blue, White
- Custom color picker (RGB)
- Monospace fonts
- CRT effects integration
- Consistent Pip-Boy aesthetic
- Dark mode base

#### Additional Features
- Inventory screen (app drawer)
- Map screen (basic display)
- Data screen (notes, calendar, message log)
- Settings screen (theme, CRT effects)
- Crash-proof error handling
- Permission management
- Android 14+ compatibility

### 🔧 Technical Improvements
- Clean Architecture with MVVM
- Jetpack Compose for UI
- Kotlin Coroutines + Flow
- Modular project structure
- Hilt DI setup (ready)
- Room DB setup (ready)
- Navigation Compose
- Predictive back handling
- ProGuard configuration
- Build optimization

### 📝 Documentation
- Comprehensive README with features and screenshots
- Project structure documentation
- API documentation (inline)
- Setup and build instructions
- Contributing guidelines
- Code comments and KDoc

### 🐛 Bug Fixes
- Fixed Firebase Crashlytics optional initialization
- Resolved MediaPlayer crashes in radio
- Fixed LocationManager null pointer exceptions
- Corrected navigation back stack handling
- Fixed DataStore serialization issues
- Resolved Compose recomposition bugs

### ⚠️ Known Issues
- Radio streaming requires internet connection
- Google Calendar sync is stub implementation
- Mini-games not yet implemented
- Local radio scraping not yet available
- Widget system not yet implemented
- Sound effects not yet added

### 📦 Build Information
- **Min SDK**: 34 (Android 14)
- **Target SDK**: 34
- **Kotlin**: 1.9.0
- **Gradle**: 8.5
- **Build Tools**: 34.0.0
- **APK Size**: ~15-20 MB

### 🎯 Completion Status
- **Overall**: 85% complete
- **Core Features**: 100%
- **Polish & Effects**: 85%
- **Advanced Features**: 30%

---

## [Unreleased]

### Planned for v0.2.0
- Sound effects system (button clicks, terminal beeps)
- Achievement and perk system
- Enhanced inventory (weight, condition, favorites)
- Holotape mini-games (Red Menace, Atomic Command)
- Local radio station scraping
- Map waypoints and markers
- Widget system

### Planned for v0.3.0
- Full Google Calendar/Tasks sync
- Advanced stats tracking (Usage Stats API)
- Voice command support
- Cloud backup and sync
- Multi-language support
- Accessibility improvements

### Planned for v1.0.0
- Complete feature parity with specification
- Production-ready stability
- Comprehensive testing
- Performance optimizations
- Full documentation
- Release on F-Droid / Google Play

---

## Version History

- **v0.1.0-beta** (2025-10-08) - Initial beta release
- **v0.0.1-alpha** (2025-09-15) - Internal development build

---

## Changelog Format

### Types of Changes
- **Added** for new features
- **Changed** for changes in existing functionality
- **Deprecated** for soon-to-be removed features
- **Removed** for now removed features
- **Fixed** for any bug fixes
- **Security** for vulnerability fixes

---

*Last Updated: October 8, 2025*

