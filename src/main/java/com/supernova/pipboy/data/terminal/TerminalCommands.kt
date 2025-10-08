package com.supernova.pipboy.data.terminal

import android.content.Context
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import com.supernova.pipboy.data.quests.QuestRepository
import com.supernova.pipboy.data.repository.SystemRepository
import com.supernova.pipboy.data.stats.StatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Terminal command system
 * Provides text-based interface to Pip-Boy functions
 */

data class TerminalOutput(
    val lines: List<String>,
    val isError: Boolean = false
)

/**
 * Terminal command processor
 */
class TerminalCommands(private val context: Context) {

    private val systemRepo = SystemRepository(context)
    private val statsRepo = StatsRepository(context)
    private val questRepo = QuestRepository(context)
    private val preferences = PipBoyPreferences(context)
    
    private val easterEggTriggers = mutableMapOf<String, Int>()

    suspend fun executeCommand(input: String): TerminalOutput {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) {
            return TerminalOutput(emptyList())
        }

        val parts = trimmed.split(" ", limit = 2)
        val command = parts[0].lowercase()
        val args = if (parts.size > 1) parts[1] else ""

        return withContext(Dispatchers.IO) {
            try {
                when (command) {
                    "help" -> helpCommand()
                    "?" -> helpCommand()
                    "status" -> statusCommand()
                    "stats" -> statsCommand()
                    "special" -> statsCommand()
                    "quests" -> questsCommand()
                    "quest" -> questCommand(args)
                    "inv" -> inventoryCommand()
                    "inventory" -> inventoryCommand()
                    "radio" -> radioCommand()
                    "map" -> mapCommand()
                    "clear" -> clearCommand()
                    "cls" -> clearCommand()
                    "info" -> infoCommand()
                    "about" -> aboutCommand()
                    "version" -> versionCommand()
                    "exit" -> exitCommand()
                    "quit" -> exitCommand()
                    
                    // Easter eggs
                    "war" -> warCommand()
                    "nuke" -> nukeCommand()
                    "thredog", "three-dog", "threedog" -> threeDogCommand()
                    "tunnelsnakes", "tunnel-snakes" -> tunnelSnakesCommand()
                    "gary" -> garyCommand()
                    "vault" -> vaultCommand(args)
                    "id" -> idBadgeCommand(args)
                    "badge" -> idBadgeCommand(args)
                    "vaultroom" -> vaultRoomCommand()
                    "override" -> vaultDoorOverrideCommand()
                    "destruct" -> selfDestructCommand()
                    "selfdestruct" -> selfDestructCommand()
                    "overseer" -> overseerCommand()
                    "goat" -> goatCommand()
                    "vats" -> vatsCommand()
                    "fatman" -> fatManCommand()
                    "nuka", "nuka-cola", "nukacola" -> nukaColaCommand()
                    "dogmeat" -> dogmeatCommand()
                    "fisto" -> fistoCommand()
                    "mrhandy", "mr-handy" -> mrHandyCommand()
                    "please" -> pleaseCommand()
                    "assume" -> assumeCommand()
                    "lobotomite" -> lobotomiteCommand()
                    "patrolling" -> patrollingCommand()
                    "ring" -> ringCommand()
                    "degenerates" -> degeneratesCommand()
                    
                    else -> unknownCommand(command)
                }
            } catch (e: Exception) {
                errorOutput("Error executing command: ${e.message}")
            }
        }
    }

    // ==================== Core Commands ====================

    private fun helpCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "ROBCO INDUSTRIES UNIFIED OPERATING SYSTEM",
            "COPYRIGHT 2075-2077 ROBCO INDUSTRIES",
            "",
            "Available Commands:",
            "",
            "  help, ?        - Show this help message",
            "  status         - Display system status",
            "  stats, special - Show S.P.E.C.I.A.L. statistics",
            "  quests         - List active quests",
            "  quest [id]     - Show quest details",
            "  inv, inventory - Display inventory",
            "  radio          - Radio controls",
            "  map            - Map information",
            "  info, about    - About Pip-Boy 3000",
            "  version        - System version",
            "  clear, cls     - Clear screen",
            "  exit, quit     - Exit terminal mode",
            "",
            "Type any command to execute.",
            "Try exploring... there might be Easter eggs. ;)",
            ""
        ))
    }

    private suspend fun statusCommand(): TerminalOutput {
        val status = systemRepo.systemStatus.value
        val storagePct = status.getStorageUsagePercentage()
        return TerminalOutput(listOf(
            "=== SYSTEM STATUS ===",
            "",
            "Battery: ${status.batteryLevel}%",
            "CPU Usage: ${status.cpuUsage.toInt()}%",
            "Memory Usage: ${status.memoryUsage.toInt()}%",
            "Temperature: ${status.temperature.toInt()}°C",
            "Storage: ${storagePct.toInt()}% used",
            "",
            "WiFi: ${if (status.wifiConnected) "Connected" else "Disconnected"}",
            "Bluetooth: ${if (status.bluetoothEnabled) "Enabled" else "Disabled"}",
            "",
            "Status: ${if (status.isHealthy()) "All systems operational" else "Needs attention"}",
            ""
        ))
    }

    private suspend fun statsCommand(): TerminalOutput {
        val profile = statsRepo.getSpecialProfile()
        return TerminalOutput(listOf(
            "=== S.P.E.C.I.A.L. STATISTICS ===",
            "",
            "S - Strength:     Level ${profile.strength.level}     (${profile.strength.experience} XP)",
            "P - Perception:   Level ${profile.perception.level}     (${profile.perception.experience} XP)",
            "E - Endurance:    Level ${profile.endurance.level}     (${profile.endurance.experience} XP)",
            "C - Charisma:     Level ${profile.charisma.level}     (${profile.charisma.experience} XP)",
            "I - Intelligence: Level ${profile.intelligence.level}     (${profile.intelligence.experience} XP)",
            "A - Agility:      Level ${profile.agility.level}     (${profile.agility.experience} XP)",
            "L - Luck:         Level ${profile.luck.level}     (${profile.luck.experience} XP)",
            "",
            "Total Level: ${profile.totalLevel}",
            "Overall XP: ${profile.overallXp}",
            ""
        ))
    }

    private suspend fun questsCommand(): TerminalOutput {
        val quests = questRepo.getActiveQuests()
        val lines = mutableListOf(
            "=== ACTIVE QUESTS ===",
            ""
        )
        
        if (quests.isEmpty()) {
            lines.add("No active quests.")
        } else {
            quests.forEachIndexed { index, quest ->
                lines.add("${index + 1}. [${quest.category.name}] ${quest.title} (${quest.progress}%)")
            }
        }
        
        lines.add("")
        lines.add("Use 'quest [number]' for details")
        lines.add("")
        
        return TerminalOutput(lines)
    }

    private suspend fun questCommand(args: String): TerminalOutput {
        if (args.isBlank()) {
            return errorOutput("Usage: quest [number]")
        }
        
        val quests = questRepo.getActiveQuests()
        val index = args.toIntOrNull()?.minus(1)
        
        if (index == null || index < 0 || index >= quests.size) {
            return errorOutput("Invalid quest number")
        }
        
        val quest = quests[index]
        val lines = mutableListOf(
            "=== QUEST DETAILS ===",
            "",
            "Title: ${quest.title}",
            "Category: ${quest.category.name}",
            "Priority: ${quest.priority.name}",
            "Progress: ${quest.progress}%",
            "",
            "Description:",
            quest.description,
            ""
        )
        
        if (quest.objectives.isNotEmpty()) {
            lines.add("Objectives:")
            quest.objectives.forEach { obj ->
                val status = if (obj.isCompleted) "[X]" else "[ ]"
                lines.add("  $status ${obj.description}")
            }
            lines.add("")
        }
        
        if (quest.xpReward > 0 || quest.capReward > 0) {
            lines.add("Rewards: ${quest.xpReward} XP, ${quest.capReward} Caps")
            lines.add("")
        }
        
        return TerminalOutput(lines)
    }

    private fun inventoryCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "=== INVENTORY ===",
            "",
            "Use the GUI to manage your inventory.",
            "Tap INVENTORY tab for full interface.",
            ""
        ))
    }

    private fun radioCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "=== RADIO ===",
            "",
            "Available Stations:",
            "  1. Galaxy News Radio",
            "  2. Radio New Vegas",
            "  3. Diamond City Radio",
            "",
            "Use RADIO tab for playback controls.",
            ""
        ))
    }

    private fun mapCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "=== MAP ===",
            "",
            "Current Location: Unknown",
            "Markers: 0",
            "",
            "Use MAP tab for navigation.",
            ""
        ))
    }

    private fun clearCommand(): TerminalOutput {
        return TerminalOutput(listOf("CLEAR_SCREEN"))
    }

    private fun infoCommand(): TerminalOutput {
        return aboutCommand()
    }

    private fun aboutCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "╔═══════════════════════════════════════╗",
            "║      PIP-BOY 3000 MARK IV            ║",
            "╚═══════════════════════════════════════╝",
            "",
            "Personal Information Processor",
            "Biological Operating System",
            "Model 3000 Mark IV",
            "",
            "Manufacturer: RobCo Industries",
            "Patent: US 5,184,120",
            "Copyright: 2075-2077",
            "",
            "Features:",
            "  • S.P.E.C.I.A.L. Statistics",
            "  • Quest Management",
            "  • Inventory Tracking",
            "  • Radio Receiver",
            "  • Local Mapping",
            "  • Holotape Player",
            "",
            "\"Technology serving humanity... underground!\"",
            "        - Vault-Tec Industries",
            ""
        ))
    }

    private fun versionCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "Pip-Boy 3000 Mark IV",
            "OS Version: 1.0.8",
            "Build: 2077.10.23",
            "RobCo Certified",
            ""
        ))
    }

    private fun exitCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "Exiting terminal mode...",
            "Thank you for using RobCo products!",
            "EXIT_TERMINAL"
        ))
    }

    // ==================== Easter Eggs ====================

    private fun warCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "\"War. War never changes.\"",
            "",
            "\"The Romans waged war to gather slaves and wealth.",
            "Spain built an empire from its lust for gold and territory.",
            "Hitler shaped a battered Germany into an economic superpower.\"",
            "",
            "\"But war never changes.\"",
            "",
            "        - Fallout opening sequence",
            ""
        ))
    }

    private fun nukeCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "⚠️  NUCLEAR LAUNCH DETECTED  ⚠️",
            "",
            "███████████████████████",
            "█ ICBM LAUNCH SEQUENCE █",
            "███████████████████████",
            "",
            "Target: Vault 111",
            "Warhead: W87 Thermonuclear",
            "Yield: 475 kilotons",
            "",
            "Impact in: 30 minutes",
            "",
            "Recommendation: Seek shelter immediately.",
            "Vault-Tec recommends our premium vaults!",
            "",
            "...Just kidding. No nukes launched. 😅",
            ""
        ))
    }

    private fun threeDogCommand(): TerminalOutput {
        easterEggTriggers["threedog"] = (easterEggTriggers["threedog"] ?: 0) + 1
        val count = easterEggTriggers["threedog"]!!
        
        return when (count) {
            1 -> TerminalOutput(listOf(
                "",
                "🎙️ THREE DOG SAYS:",
                "",
                "\"This is Three Dog, coming to you loud and proud",
                "from Galaxy News Radio!\"",
                "",
                "\"Fighting the good fight!\"",
                ""
            ))
            2 -> TerminalOutput(listOf(
                "",
                "🎙️ \"You're listening to Galaxy News Radio!",
                "We're here for you!\"",
                "",
                "\"Now here's some music...\"",
                "♪ I don't want to set the world on fire... ♪",
                ""
            ))
            else -> TerminalOutput(listOf(
                "",
                "\"Keep fighting the good fight, children!",
                "This is Three Dog... Owwww!\"",
                "",
                "📻 *Galaxy News Radio signing off*",
                ""
            ))
        }
    }

    private fun tunnelSnakesCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🐍 TUNNEL SNAKES RULE! 🐍",
            "",
            "\"We're the Tunnel Snakes.",
            "That's us.",
            "And we rule!\"",
            "",
            "      - Butch DeLoria",
            "",
            "🎵 Tunnel Snakes rule! 🎵",
            "🎵 We're the Tunnel Snakes! 🎵",
            "🎵 That's us, and we rule! 🎵",
            ""
        ))
    }

    private fun garyCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "Gary?",
            "Gary!",
            "Ga-ry!",
            "Gary!",
            "Gary?",
            "Haha, Gary!",
            "",
            "        - Vault 108 Garys",
            ""
        ))
    }

    private fun vaultCommand(args: String): TerminalOutput {
        val vaultNum = args.trim()
        return when (vaultNum) {
            "13" -> TerminalOutput(listOf("Vault 13: The water chip is missing!", "Status: Abandoned", ""))
            "101" -> TerminalOutput(listOf("Vault 101: The jewel of the wastes!", "Status: Sealed", "Overseer: Still thinks he's in charge", ""))
            "111" -> TerminalOutput(listOf("Vault 111: Cryogenic preservation facility", "Status: Compromised", "Sole Survivor: Escaped", ""))
            "76" -> TerminalOutput(listOf("Vault 76: Reclamation Day vault", "Status: Open", "Mission: Rebuild America!", ""))
            "666" -> TerminalOutput(listOf("Vault 666: ERROR - VAULT NOT FOUND", "Or is it...? 😈", ""))
            else -> TerminalOutput(listOf("Vault $vaultNum: Status unknown", "Check Vault-Tec records for details.", ""))
        }
    }

    private fun overseerCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "📋 OVERSEER MESSAGE",
            "",
            "\"Citizens of the Vault,",
            "",
            "Your continued cooperation is mandatory.",
            "Remember: the Overseer knows best.\"",
            "",
            "\"Vault-Tec: Building a brighter future...underground!\"",
            "",
            "        - Your Friendly Overseer",
            ""
        ))
    }

    private fun goatCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "📝 G.O.A.T. - Generalized Occupational Aptitude Test",
            "",
            "Question 1:",
            "You are approached by a frenzied vault scientist...",
            "",
            "A) Ignore him",
            "B) Hear him out",
            "C) Throw a grenade",
            "",
            "...Actually, you already have a job: Vault Dweller!",
            ""
        ))
    }

    private fun vatsCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🎯 V.A.T.S. - Vault-Tec Assisted Targeting System",
            "",
            "Target: Terminal Command",
            "Hit Chance: 95%",
            "Action Points: 65/65",
            "",
            "[ENTER] to execute",
            "",
            "*CRITICAL HIT*",
            "Command executed successfully!",
            ""
        ))
    }

    private fun fatManCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "💣 Fat Man - Portable Nuclear Catapult",
            "",
            "Ammo: Mini-Nuke",
            "Damage: Yes.",
            "Range: Over there somewhere",
            "Side Effects: Everything",
            "",
            "\"When you absolutely, positively need to",
            "kill every living thing in the area.\"",
            ""
        ))
    }

    private fun nukaColaCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🥤 Nuka-Cola - The taste of a refreshed America!",
            "",
            "Effects: +20 HP, +10 AP, +5 Rads",
            "Flavor: Slightly radioactive",
            "",
            "\"Nothing beats a cold Nuka-Cola!\"",
            "",
            "Collect all flavors:",
            "  • Nuka-Cola",
            "  • Nuka-Cola Quantum (GLOWS!)",
            "  • Nuka-Cherry",
            "  • Nuka-Victory",
            ""
        ))
    }

    private fun dogmeatCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🐕 Dogmeat - The goodest boy in the wasteland",
            "",
            "Loyalty: 100%",
            "Combat Effectiveness: High",
            "Cuteness: Maximum",
            "",
            "*Dogmeat found something!*",
            "*Dogmeat is happy!*",
            "",
            "\"Who's a good boy? You are!\"",
            ""
        ))
    }

    private fun fistoCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🤖 FISTO - Fully Integrated Security Technotronic Officer",
            "",
            "\"Please assume the position.\"",
            "",
            "        - FISTO, Fallout: New Vegas",
            "",
            "...I should probably stop here. 😅",
            ""
        ))
    }

    private fun mrHandyCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🤖 Mister Handy - General Atomics International",
            "",
            "\"Greetings, Sir! How may I serve you today?\"",
            "",
            "Features:",
            "  • Three mechanical arms",
            "  • Flamethrower attachment",
            "  • Cheerful personality",
            "  • British accent (why?)",
            "",
            "\"Pip-pip, cheerio!\"",
            ""
        ))
    }

    private fun pleaseCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "Ah, such good manners!",
            "",
            "\"Are you asking me out?\"",
            "\"That's... weird.\"",
            "",
            "        - Arcade Gannon, Fallout: New Vegas",
            "",
            "(Try being more specific with your command!)",
            ""
        ))
    }

    private fun assumeCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "\"Please assume the position.\"",
            "",
            "        - FISTO",
            "",
            "...No. Just no.",
            ""
        ))
    }

    private fun lobotomiteCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "🧠 \"Is that a lobotomite I see? Maybe not. Maybe so!\"",
            "",
            "\"The brain waves! The brain waves! Make them stop!\"",
            "",
            "        - Think Tank, Old World Blues",
            ""
        ))
    }

    private fun patrollingCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "\"Patrolling the Mojave almost makes you",
            "wish for a nuclear winter.\"",
            "",
            "        - NCR Trooper (every 5 minutes)",
            "",
            "Yes, we know. We ALL know.",
            ""
        ))
    }

    private fun ringCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "\"Ring-a-ding-ding, baby!\"",
            "",
            "        - Benny, Fallout: New Vegas",
            "",
            "🎰 The House Always Wins 🎰",
            ""
        ))
    }

    private fun degeneratesCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "",
            "\"Degenerates like you belong on a cross.\"",
            "",
            "        - Caesar's Legion",
            "",
            "Ave, true to Caesar!",
            "(Just kidding, Legion is awful.)",
            ""
        ))
    }

    // ========== EASTER EGG COMMANDS ==========

    private suspend fun idBadgeCommand(args: String): TerminalOutput {
        val argsList = args.trim().split(" ")

        return when {
            args.isBlank() -> TerminalOutput(listOf(
                "=== PIP-BOY ID BADGE ===",
                "",
                "Name: ${preferences.idBadgeName}",
                "Vault: ${preferences.idBadgeVaultNumber}",
                "Rank: ${preferences.idBadgeRank}",
                "",
                "Use 'id set [name] [vault] [rank]' to customize",
                "Use 'id show' to toggle visibility",
                "Use 'id hide' to hide badge",
                ""
            ))

            args.startsWith("set") -> {
                if (argsList.size < 4) {
                    TerminalOutput(listOf(
                        "Usage: id set [name] [vault] [rank]",
                        "Example: id set \"Vault Dweller\" 101 Citizen",
                        ""
                    ), isError = true)
                } else {
                    val name = argsList[1]
                    val vault = argsList[2]
                    val rank = argsList[3]
                    preferences.idBadgeName = name
                    preferences.idBadgeVaultNumber = vault
                    preferences.idBadgeRank = rank
                    TerminalOutput(listOf(
                        "ID Badge updated!",
                        "Name: $name",
                        "Vault: $vault",
                        "Rank: $rank",
                        "",
                        "Achievement unlocked: ID Customizer",
                        ""
                    ))
                }
            }

            args == "show" -> {
                preferences.idBadgeVisible = true
                TerminalOutput(listOf(
                    "ID Badge is now visible!",
                    "Check your Pip-Boy interface.",
                    ""
                ))
            }

            args == "hide" -> {
                preferences.idBadgeVisible = false
                TerminalOutput(listOf(
                    "ID Badge is now hidden.",
                    ""
                ))
            }

            else -> TerminalOutput(listOf(
                "Unknown ID command: $args",
                "Use 'id', 'id set', 'id show', or 'id hide'",
                ""
            ), isError = true)
        }
    }

    private suspend fun vaultRoomCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "=== VAULT-TEC CONTROL ROOM ACCESS ===",
            "",
            "ACCESS DENIED",
            "Security Clearance: INSUFFICIENT",
            "",
            "This command requires physical access to the",
            "Vault-Tec Control Room terminal.",
            "",
            "Try a different approach... or tap the screen 3 times.",
            ""
        ))
    }

    private suspend fun vaultDoorOverrideCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "=== VAULT DOOR OVERRIDE ===",
            "",
            "⚠️  SECURITY PROTOCOL VIOLATION ⚠️",
            "",
            "Override initiated...",
            "Door mechanisms engaged...",
            "Pressure seals released...",
            "",
            "Vault door is now OPEN",
            "",
            "Achievement unlocked: Vault Door Override",
            ""
        ))
    }

    private suspend fun selfDestructCommand(): TerminalOutput {
        return TerminalOutput(listOf(
            "=== SELF-DESTRUCT SEQUENCE ===",
            "",
            "⚠️  WARNING ⚠️",
            "Self-destruct sequence initiated!",
            "",
            "This is not a drill.",
            "All Vault-Tec personnel evacuate immediately.",
            "",
            "Detonation in 10 seconds...",
            "",
            "Just kidding. Vault-Tec loves you. 💙",
            "",
            "Achievement unlocked: Self-Destruct Survivor",
            ""
        ))
    }

    private fun unknownCommand(command: String): TerminalOutput {
        val suggestions = listOf(
            "Command not found: '$command'",
            "Did you mean: 'help'?",
            "",
            "Type 'help' for available commands.",
            ""
        )
        return TerminalOutput(suggestions, isError = true)
    }

    private fun errorOutput(message: String): TerminalOutput {
        return TerminalOutput(listOf("ERROR: $message", ""), isError = true)
    }
}

