package com.supernova.pipboy.data.achievements

import android.content.Context
import android.util.Log
import com.supernova.pipboy.audio.SoundManager
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central manager for achievement tracking and unlocking
 */
@Singleton
class AchievementManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: PipBoyPreferences,
    private val soundManager: SoundManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    // Achievement state
    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()
    
    // Unlock events
    private val _achievementUnlocked = MutableSharedFlow<Achievement>()
    val achievementUnlocked: Flow<Achievement> = _achievementUnlocked.asSharedFlow()
    
    // Statistics
    private val _unlockedCount = MutableStateFlow(0)
    val unlockedCount: StateFlow<Int> = _unlockedCount.asStateFlow()
    
    private val _totalXP = MutableStateFlow(0)
    val totalXP: StateFlow<Int> = _totalXP.asStateFlow()
    
    // Progress tracking
    private val progressMap = mutableMapOf<String, Int>()
    
    init {
        loadAchievements()
    }
    
    private fun loadAchievements() {
        scope.launch {
            try {
                // Load saved progress from preferences
                val savedProgress = preferences.getAchievementProgress()
                progressMap.putAll(savedProgress)
                
                // Initialize achievements with saved progress
                val achievementsWithProgress = PredefinedAchievements.ALL_ACHIEVEMENTS.map { achievement ->
                    val progress = progressMap[achievement.id] ?: 0
                    val isUnlocked = progress >= achievement.maxProgress
                    achievement.copy(
                        progress = progress,
                        isUnlocked = isUnlocked,
                        unlockedAt = if (isUnlocked) System.currentTimeMillis() else null
                    )
                }
                
                _achievements.value = achievementsWithProgress
                updateStatistics()
                
                Timber.d("Loaded ${achievementsWithProgress.size} achievements")
            } catch (e: Exception) {
                Timber.e(e, "Failed to load achievements")
                // Fallback to default achievements
                _achievements.value = PredefinedAchievements.ALL_ACHIEVEMENTS
            }
        }
    }
    
    /**
     * Track an event that may unlock achievements
     */
    fun trackEvent(event: AchievementEvent) {
        scope.launch {
            when (event) {
                is AchievementEvent.AppOpened -> handleAppOpened()
                is AchievementEvent.CommandExecuted -> handleCommandExecuted(event.command)
                is AchievementEvent.QuestCompleted -> handleQuestCompleted()
                is AchievementEvent.StationListened -> handleStationListened(event.stationId)
                is AchievementEvent.StatLevelReached -> handleStatLevelReached(event.stat, event.level)
                is AchievementEvent.CustomEvent -> handleCustomEvent(event.eventId)
            }
        }
    }
    
    private suspend fun handleAppOpened() {
        // Track total opens
        incrementProgress("wasteland_wanderer")
        incrementProgress("pip_boy_master")
        
        // Check time-based achievements
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        if (hour < 6) {
            unlockAchievement("night_owl")
        } else if (hour >= 6 && hour < 12) {
            unlockAchievement("early_bird")
        }
        
        // Track consecutive days
        val today = System.currentTimeMillis() / (24 * 60 * 60 * 1000)
        val lastOpenDay = preferences.lastOpenDay
        
        if (lastOpenDay == today - 1) {
            // Consecutive day
            val streak = preferences.consecutiveDays + 1
            preferences.consecutiveDays = streak
            
            updateProgress("dedicated_survivor", streak)
            updateProgress("obsessed", streak)
        } else if (lastOpenDay != today) {
            // New day but not consecutive, reset streak
            preferences.consecutiveDays = 1
        }
        
        preferences.lastOpenDay = today
        
        // Track total days used
        val totalDays = preferences.totalDaysUsed + 1
        preferences.totalDaysUsed = totalDays
        updateProgress("wasteland_veteran", totalDays)
    }
    
    private suspend fun handleCommandExecuted(command: String) {
        incrementProgress("terminal_novice")
        incrementProgress("terminal_hacker")
        incrementProgress("terminal_master")
        
        // Specific commands
        when (command.lowercase()) {
            "help" -> incrementProgress("help_seeker")
            "vault" -> unlockAchievement("vault_secret")
            "easteregg", "secret", "hidden" -> unlockAchievement("easter_egg_hunter")
        }
    }
    
    private suspend fun handleQuestCompleted() {
        incrementProgress("quest_beginner")
        incrementProgress("task_manager")
        incrementProgress("quest_master")
        incrementProgress("completionist")
        
        // Track quest streaks
        val today = System.currentTimeMillis() / (24 * 60 * 60 * 1000)
        val lastQuestDay = preferences.lastQuestDay
        
        if (lastQuestDay == today - 1) {
            val streak = preferences.questStreak + 1
            preferences.questStreak = streak
            updateProgress("streak_master", streak)
        } else if (lastQuestDay != today) {
            preferences.questStreak = 1
        }
        
        preferences.lastQuestDay = today
    }
    
    private suspend fun handleStationListened(stationId: String) {
        // Track unique stations
        val listenedStations = preferences.listenedStations.toMutableSet()
        if (listenedStations.add(stationId)) {
            preferences.listenedStations = listenedStations
            updateProgress("radio_listener", listenedStations.size)
            updateProgress("station_surfer", listenedStations.size)
        }
        
        // Track listening duration (called every minute)
        val totalMinutes = preferences.radioMinutes + 1
        preferences.radioMinutes = totalMinutes
        updateProgress("radio_fanatic", totalMinutes)
    }
    
    private suspend fun handleStatLevelReached(stat: String, level: Int) {
        when (level) {
            5 -> updateProgress("level_5", 5)
            10 -> updateProgress("level_10", 10)
            25 -> updateProgress("level_25", 25)
        }
        
        // Check if all stats are maxed
        // This should be called from S.P.E.C.I.A.L. system
    }
    
    private suspend fun handleCustomEvent(eventId: String) {
        when (eventId) {
            "boot_complete" -> unlockAchievement("vault_dweller")
            "visit_all_tabs" -> unlockAchievement("explorer")
            "branching_quest" -> unlockAchievement("branching_path")
            "all_stats_maxed" -> unlockAchievement("maxed_out")
            "settings_changed" -> incrementProgress("settings_tinkerer")
        }
    }
    
    /**
     * Increment progress for an achievement
     */
    private suspend fun incrementProgress(achievementId: String) {
        val achievement = _achievements.value.find { it.id == achievementId } ?: return
        if (achievement.isUnlocked) return
        
        val newProgress = (progressMap[achievementId] ?: 0) + 1
        updateProgress(achievementId, newProgress)
    }
    
    /**
     * Update progress to a specific value
     */
    private suspend fun updateProgress(achievementId: String, progress: Int) {
        val achievement = _achievements.value.find { it.id == achievementId } ?: return
        if (achievement.isUnlocked) return
        
        progressMap[achievementId] = progress
        preferences.setAchievementProgress(progressMap)
        
        // Update achievement list
        _achievements.value = _achievements.value.map { ach ->
            if (ach.id == achievementId) {
                ach.copy(progress = progress)
            } else {
                ach
            }
        }
        
        // Check if unlocked
        if (progress >= achievement.maxProgress) {
            unlockAchievement(achievementId)
        }
    }
    
    /**
     * Unlock an achievement immediately
     */
    suspend fun unlockAchievement(achievementId: String) {
        val achievement = _achievements.value.find { it.id == achievementId } ?: return
        if (achievement.isUnlocked) return
        
        Timber.i("Achievement unlocked: ${achievement.title}")
        
        // Mark as unlocked
        progressMap[achievementId] = achievement.maxProgress
        preferences.setAchievementProgress(progressMap)
        
        val unlockedAchievement = achievement.copy(
            isUnlocked = true,
            unlockedAt = System.currentTimeMillis(),
            progress = achievement.maxProgress
        )
        
        // Update list
        _achievements.value = _achievements.value.map { ach ->
            if (ach.id == achievementId) unlockedAchievement else ach
        }
        
        // Emit unlock event
        _achievementUnlocked.emit(unlockedAchievement)
        
        // Play sound
        soundManager.playAchievementUnlock()
        
        // Update statistics
        updateStatistics()
        
        // Check for "Platinum Wanderer" achievement
        if (achievement.id != "platinum_wanderer") {
            checkPlatinumAchievement()
        }
    }
    
    private fun updateStatistics() {
        val unlockedAchievements = _achievements.value.filter { it.isUnlocked }
        _unlockedCount.value = unlockedAchievements.size
        _totalXP.value = unlockedAchievements.sumOf { it.xpReward }
    }
    
    private suspend fun checkPlatinumAchievement() {
        val allOtherUnlocked = _achievements.value
            .filter { it.id != "platinum_wanderer" }
            .all { it.isUnlocked }
        
        if (allOtherUnlocked) {
            unlockAchievement("platinum_wanderer")
        }
    }
    
    /**
     * Get achievements by category
     */
    fun getAchievementsByCategory(category: AchievementCategory): List<Achievement> {
        return _achievements.value.filter { it.category == category }
    }
    
    /**
     * Get unlocked achievements
     */
    fun getUnlockedAchievements(): List<Achievement> {
        return _achievements.value.filter { it.isUnlocked }
    }
    
    /**
     * Get completion percentage
     */
    fun getCompletionPercentage(): Float {
        val total = _achievements.value.size
        val unlocked = _unlockedCount.value
        return if (total > 0) (unlocked.toFloat() / total.toFloat()) * 100f else 0f
    }
    
    /**
     * Reset all achievements (for testing/debugging)
     */
    suspend fun resetAllAchievements() {
        progressMap.clear()
        preferences.setAchievementProgress(emptyMap())
        _achievements.value = PredefinedAchievements.ALL_ACHIEVEMENTS
        updateStatistics()
        Timber.w("All achievements reset!")
    }
}

/**
 * Events that can trigger achievement progress
 */
sealed class AchievementEvent {
    object AppOpened : AchievementEvent()
    data class CommandExecuted(val command: String) : AchievementEvent()
    object QuestCompleted : AchievementEvent()
    data class StationListened(val stationId: String) : AchievementEvent()
    data class StatLevelReached(val stat: String, val level: Int) : AchievementEvent()
    data class CustomEvent(val eventId: String) : AchievementEvent()
}

