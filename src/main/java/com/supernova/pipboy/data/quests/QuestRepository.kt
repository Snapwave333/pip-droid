package com.supernova.pipboy.data.quests

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

// DataStore extension
private val Context.questDataStore: DataStore<Preferences> by preferencesDataStore(name = "pipboy_quests")

/**
 * Repository for managing quests
 * Handles local storage and Google sync
 */
class QuestRepository(private val context: Context) {

    private val gson = Gson()
    private val googleSync = GoogleQuestSyncStub(context) // Using stub until full API is configured
    
    companion object {
        private val QUESTS_KEY = stringPreferencesKey("all_quests")
        private val TOTAL_XP_KEY = stringPreferencesKey("total_xp")
        private val TOTAL_CAPS_KEY = stringPreferencesKey("total_caps")
    }

    /**
     * Get all quests as Flow
     */
    fun getAllQuestsFlow(): Flow<List<Quest>> {
        return context.questDataStore.data.map { preferences ->
            val json = preferences[QUESTS_KEY] ?: "[]"
            try {
                val type = object : TypeToken<List<Quest>>() {}.type
                gson.fromJson<List<Quest>>(json, type) ?: emptyList()
            } catch (e: Exception) {
                Timber.e(e, "Error parsing quests")
                emptyList()
            }
        }
    }

    /**
     * Get all quests (suspend function)
     */
    suspend fun getAllQuests(): List<Quest> {
        val preferences = context.questDataStore.data.map { it }.first()
        val json = preferences[QUESTS_KEY] ?: "[]"
        return try {
            val type = object : TypeToken<List<Quest>>() {}.type
            gson.fromJson<List<Quest>>(json, type) ?: emptyList()
        } catch (e: Exception) {
            Timber.e(e, "Error parsing quests")
            emptyList()
        }
    }

    /**
     * Save all quests
     */
    private suspend fun saveQuests(quests: List<Quest>) {
        context.questDataStore.edit { preferences ->
            preferences[QUESTS_KEY] = gson.toJson(quests)
        }
    }

    /**
     * Add or update quest
     */
    suspend fun saveQuest(quest: Quest, syncToGoogle: Boolean = true): Quest {
        val allQuests = getAllQuests().toMutableList()
        val index = allQuests.indexOfFirst { it.id == quest.id }
        
        var updatedQuest = quest.copy(updatedAt = System.currentTimeMillis())
        
        // Sync to Google if enabled
        if (syncToGoogle && googleSync.isSignedIn()) {
            // Sync to Calendar if has due date
            if (quest.dueDate != null) {
                val calendarEventId = googleSync.syncQuestToCalendar(updatedQuest)
                if (calendarEventId != null) {
                    updatedQuest = updatedQuest.copy(googleCalendarEventId = calendarEventId)
                }
            }
            
            // Sync to Tasks
            val (taskId, taskListId) = googleSync.syncQuestToTasks(updatedQuest)
            if (taskId != null) {
                updatedQuest = updatedQuest.copy(
                    googleTaskId = taskId,
                    googleTaskListId = taskListId
                )
            }
        }
        
        if (index >= 0) {
            allQuests[index] = updatedQuest
        } else {
            allQuests.add(updatedQuest)
        }
        
        saveQuests(allQuests)
        
        // Check if quest completion unlocks other quests
        if (updatedQuest.status == QuestStatus.COMPLETED && quest.status != QuestStatus.COMPLETED) {
            unlockDependentQuests(updatedQuest)
            awardQuestRewards(updatedQuest)
        }
        
        return updatedQuest
    }

    /**
     * Delete quest
     */
    suspend fun deleteQuest(questId: String, deleteFromGoogle: Boolean = true) {
        val allQuests = getAllQuests().toMutableList()
        val quest = allQuests.find { it.id == questId }
        
        // Delete from Google if synced
        if (deleteFromGoogle && quest != null && googleSync.isSignedIn()) {
            quest.googleCalendarEventId?.let { googleSync.deleteFromCalendar(it) }
            quest.googleTaskId?.let { googleSync.deleteFromTasks(it) }
        }
        
        allQuests.removeAll { it.id == questId }
        saveQuests(allQuests)
    }

    /**
     * Get quest by ID
     */
    suspend fun getQuestById(questId: String): Quest? {
        return getAllQuests().find { it.id == questId }
    }

    /**
     * Get active quests
     */
    suspend fun getActiveQuests(): List<Quest> {
        return getAllQuests().filter { it.status == QuestStatus.ACTIVE }
            .sortedWith(compareBy({ it.priority.ordinal }, { it.dueDate }))
    }

    /**
     * Get quests by category
     */
    suspend fun getQuestsByCategory(category: QuestCategory): List<Quest> {
        return getAllQuests().filter { it.category == category }
    }

    /**
     * Complete quest objective
     */
    suspend fun completeObjective(questId: String, objectiveId: String) {
        val quest = getQuestById(questId) ?: return
        val updatedObjectives = quest.objectives.map { obj ->
            if (obj.id == objectiveId) obj.copy(isCompleted = true) else obj
        }
        
        val completedCount = updatedObjectives.count { it.isCompleted && !it.isOptional }
        val requiredCount = updatedObjectives.count { !it.isOptional }
        val newProgress = if (requiredCount > 0) (completedCount * 100) / requiredCount else 0
        
        val updatedQuest = quest.copy(
            objectives = updatedObjectives,
            progress = newProgress,
            status = if (newProgress >= 100) QuestStatus.COMPLETED else quest.status,
            completedAt = if (newProgress >= 100) System.currentTimeMillis() else null
        )
        
        saveQuest(updatedQuest)
    }

    /**
     * Unlock dependent quests when prerequisites are met
     */
    private suspend fun unlockDependentQuests(completedQuest: Quest) {
        val allQuests = getAllQuests().toMutableList()
        var updated = false
        
        completedQuest.unlocksQuests.forEach { unlockedQuestId ->
            val questIndex = allQuests.indexOfFirst { it.id == unlockedQuestId }
            if (questIndex >= 0) {
                val quest = allQuests[questIndex]
                if (quest.status == QuestStatus.LOCKED) {
                    // Check if all prerequisites are met
                    val allPrereqsMet = quest.prerequisites.all { prereqId ->
                        allQuests.any { it.id == prereqId && it.status == QuestStatus.COMPLETED }
                    }
                    
                    if (allPrereqsMet) {
                        allQuests[questIndex] = quest.copy(status = QuestStatus.ACTIVE)
                        updated = true
                        Timber.d("Quest unlocked: ${quest.title}")
                    }
                }
            }
        }
        
        if (updated) {
            saveQuests(allQuests)
        }
    }

    /**
     * Award quest rewards
     */
    private suspend fun awardQuestRewards(quest: Quest) {
        if (quest.xpReward > 0) {
            val currentXp = getTotalXP()
            setTotalXP(currentXp + quest.xpReward)
            Timber.d("Awarded ${quest.xpReward} XP")
        }
        
        if (quest.capReward > 0) {
            val currentCaps = getTotalCaps()
            setTotalCaps(currentCaps + quest.capReward)
            Timber.d("Awarded ${quest.capReward} Caps")
        }
    }

    /**
     * Get total XP
     */
    suspend fun getTotalXP(): Int {
        val preferences = context.questDataStore.data.map { it }.first()
        return preferences[TOTAL_XP_KEY]?.toIntOrNull() ?: 0
    }

    /**
     * Set total XP
     */
    private suspend fun setTotalXP(xp: Int) {
        context.questDataStore.edit { preferences ->
            preferences[TOTAL_XP_KEY] = xp.toString()
        }
    }

    /**
     * Get total Caps
     */
    suspend fun getTotalCaps(): Int {
        val preferences = context.questDataStore.data.map { it }.first()
        return preferences[TOTAL_CAPS_KEY]?.toIntOrNull() ?: 0
    }

    /**
     * Set total Caps
     */
    private suspend fun setTotalCaps(caps: Int) {
        context.questDataStore.edit { preferences ->
            preferences[TOTAL_CAPS_KEY] = caps.toString()
        }
    }

    /**
     * Initialize Google Sync
     */
    suspend fun initializeGoogleSync(account: com.google.android.gms.auth.api.signin.GoogleSignInAccount): Boolean {
        return googleSync.initialize(account)
    }

    /**
     * Check if Google Sync is enabled
     */
    fun isGoogleSyncEnabled(): Boolean {
        return googleSync.isSignedIn()
    }

    /**
     * Get Google Sign-In options
     */
    fun getGoogleSignInOptions() = googleSync.getSignInOptions()
}

// Extension to get first item from Flow
private suspend fun <T> Flow<T>.first(): T {
    var result: T? = null
    this.collect { value ->
        result = value
        return@collect
    }
    return result ?: throw NoSuchElementException("Flow is empty")
}

