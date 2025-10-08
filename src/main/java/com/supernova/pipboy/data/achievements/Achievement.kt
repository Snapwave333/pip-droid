package com.supernova.pipboy.data.achievements

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an unlockable achievement in the Pip-Boy
 */
@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val category: AchievementCategory,
    val xpReward: Int,
    val tier: AchievementTier,
    val unlockCondition: UnlockCondition,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val progress: Int = 0, // Current progress towards unlock
    val maxProgress: Int = 1 // Required progress to unlock
)

/**
 * Achievement categories for organization
 */
enum class AchievementCategory {
    GENERAL,        // Basic app usage
    TERMINAL,       // Terminal command usage
    QUESTS,         // Quest completion
    STATS,          // S.P.E.C.I.A.L. stats
    RADIO,          // Radio usage
    EXPLORATION,    // App discovery
    SPECIAL         // Hidden/Easter egg achievements
}

/**
 * Achievement difficulty/rarity tiers
 */
enum class AchievementTier(val color: Long, val xpMultiplier: Float) {
    COMMON(0xFF4CAF50, 1.0f),      // Green
    UNCOMMON(0xFF2196F3, 1.5f),    // Blue
    RARE(0xFF9C27B0, 2.0f),        // Purple
    EPIC(0xFFFF9800, 3.0f),        // Orange
    LEGENDARY(0xFFFFD700, 5.0f)    // Gold
}

/**
 * Unlock conditions for achievements
 */
sealed class UnlockCondition {
    // App usage
    data class OpenApp(val times: Int) : UnlockCondition()
    data class UseAppDays(val days: Int) : UnlockCondition()
    data class UseAppConsecutiveDays(val days: Int) : UnlockCondition()
    
    // Terminal
    data class ExecuteCommands(val count: Int) : UnlockCondition()
    data class UseSpecificCommand(val command: String, val times: Int) : UnlockCondition()
    data class DiscoverEasterEgg(val eggId: String) : UnlockCondition()
    
    // Quests
    data class CompleteQuests(val count: Int) : UnlockCondition()
    data class CompleteQuestWithBranch(val branchName: String) : UnlockCondition()
    data class CompleteQuestStreak(val streak: Int) : UnlockCondition()
    
    // Stats
    data class ReachStatLevel(val stat: String, val level: Int) : UnlockCondition()
    data class ReachTotalLevel(val level: Int) : UnlockCondition()
    object MaxAllStats : UnlockCondition()
    
    // Radio
    data class ListenToStations(val count: Int) : UnlockCondition()
    data class ListenDuration(val minutes: Int) : UnlockCondition()
    
    // Special
    data class Custom(val conditionId: String) : UnlockCondition()
    object BootSequenceCompleted : UnlockCondition()
    object AllAchievementsUnlocked : UnlockCondition()
}

/**
 * Serialization helpers for Room
 */
object UnlockConditionConverter {
    fun toString(condition: UnlockCondition): String {
        return when (condition) {
            is UnlockCondition.OpenApp -> "open_app:${condition.times}"
            is UnlockCondition.UseAppDays -> "use_days:${condition.days}"
            is UnlockCondition.UseAppConsecutiveDays -> "consecutive_days:${condition.days}"
            is UnlockCondition.ExecuteCommands -> "exec_commands:${condition.count}"
            is UnlockCondition.UseSpecificCommand -> "specific_command:${condition.command}:${condition.times}"
            is UnlockCondition.DiscoverEasterEgg -> "easter_egg:${condition.eggId}"
            is UnlockCondition.CompleteQuests -> "complete_quests:${condition.count}"
            is UnlockCondition.CompleteQuestWithBranch -> "quest_branch:${condition.branchName}"
            is UnlockCondition.CompleteQuestStreak -> "quest_streak:${condition.streak}"
            is UnlockCondition.ReachStatLevel -> "stat_level:${condition.stat}:${condition.level}"
            is UnlockCondition.ReachTotalLevel -> "total_level:${condition.level}"
            UnlockCondition.MaxAllStats -> "max_all_stats"
            is UnlockCondition.ListenToStations -> "listen_stations:${condition.count}"
            is UnlockCondition.ListenDuration -> "listen_duration:${condition.minutes}"
            is UnlockCondition.Custom -> "custom:${condition.conditionId}"
            is UnlockCondition.BootSequenceCompleted -> "boot_complete"
            is UnlockCondition.AllAchievementsUnlocked -> "all_achievements"
        }
    }
    
    fun fromString(value: String): UnlockCondition {
        val parts = value.split(":")
        return when (parts[0]) {
            "open_app" -> UnlockCondition.OpenApp(parts[1].toInt())
            "use_days" -> UnlockCondition.UseAppDays(parts[1].toInt())
            "consecutive_days" -> UnlockCondition.UseAppConsecutiveDays(parts[1].toInt())
            "exec_commands" -> UnlockCondition.ExecuteCommands(parts[1].toInt())
            "specific_command" -> UnlockCondition.UseSpecificCommand(parts[1], parts[2].toInt())
            "easter_egg" -> UnlockCondition.DiscoverEasterEgg(parts[1])
            "complete_quests" -> UnlockCondition.CompleteQuests(parts[1].toInt())
            "quest_branch" -> UnlockCondition.CompleteQuestWithBranch(parts[1])
            "quest_streak" -> UnlockCondition.CompleteQuestStreak(parts[1].toInt())
            "stat_level" -> UnlockCondition.ReachStatLevel(parts[1], parts[2].toInt())
            "total_level" -> UnlockCondition.ReachTotalLevel(parts[1].toInt())
            "max_all_stats" -> UnlockCondition.MaxAllStats
            "listen_stations" -> UnlockCondition.ListenToStations(parts[1].toInt())
            "listen_duration" -> UnlockCondition.ListenDuration(parts[1].toInt())
            "custom" -> UnlockCondition.Custom(parts[1])
            "boot_complete" -> UnlockCondition.BootSequenceCompleted
            "all_achievements" -> UnlockCondition.AllAchievementsUnlocked
            else -> UnlockCondition.Custom("unknown")
        }
    }
}

