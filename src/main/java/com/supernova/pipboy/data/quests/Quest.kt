package com.supernova.pipboy.data.quests

import java.util.*

/**
 * Quest categories matching Fallout game structure
 */
enum class QuestCategory {
    MAIN,           // Main storyline quests
    SIDE,           // Optional side quests
    MISC,           // Miscellaneous tasks
    RECURRING,      // Daily/weekly recurring tasks
    FACTION         // Faction-specific quests
}

/**
 * Quest status
 */
enum class QuestStatus {
    ACTIVE,         // Currently active
    COMPLETED,      // Successfully completed
    FAILED,         // Failed or abandoned
    LOCKED          // Not yet unlocked (prerequisites not met)
}

/**
 * Quest priority (for sorting)
 */
enum class QuestPriority {
    CRITICAL,       // Urgent, time-sensitive
    HIGH,           // Important
    NORMAL,         // Standard priority
    LOW             // Optional, low priority
}

/**
 * Quest data class representing a task/objective
 * Integrates with Google Calendar (for deadlines) and Google Tasks (for subtasks)
 */
data class Quest(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val category: QuestCategory,
    val status: QuestStatus = QuestStatus.LOCKED,
    val priority: QuestPriority = QuestPriority.NORMAL,
    
    // Progress tracking
    val progress: Int = 0,              // 0-100%
    val currentObjective: String? = null,
    val objectives: List<QuestObjective> = emptyList(),
    
    // Deadlines and scheduling
    val startDate: Long? = null,        // Unix timestamp
    val dueDate: Long? = null,          // Unix timestamp
    val reminderTime: Long? = null,     // Unix timestamp
    
    // Branching logic
    val prerequisites: List<String> = emptyList(),  // Quest IDs that must be completed first
    val unlocksQuests: List<String> = emptyList(),  // Quest IDs that unlock after completion
    
    // Rewards
    val xpReward: Int = 0,
    val capReward: Int = 0,             // "Caps" as reward points
    val perkUnlock: String? = null,
    
    // Google integration
    val googleCalendarEventId: String? = null,
    val googleTaskId: String? = null,
    val googleTaskListId: String? = null,
    
    // Metadata
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val location: String? = null,       // Optional location tag
    val tags: List<String> = emptyList(),
    val notes: String? = null,
    
    // UI state
    val isExpanded: Boolean = false,
    val isFavorite: Boolean = false
)

/**
 * Individual objective within a quest
 */
data class QuestObjective(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val isCompleted: Boolean = false,
    val isOptional: Boolean = false,
    val order: Int = 0,
    val googleTaskId: String? = null    // Maps to Google Tasks subtask
)

/**
 * Quest template for creating recurring quests
 */
data class QuestTemplate(
    val id: String,
    val title: String,
    val description: String,
    val category: QuestCategory,
    val objectives: List<String>,
    val recurrencePattern: RecurrencePattern? = null
)

/**
 * Recurrence pattern for recurring quests
 */
enum class RecurrencePattern {
    DAILY,
    WEEKLY,
    MONTHLY,
    CUSTOM
}

