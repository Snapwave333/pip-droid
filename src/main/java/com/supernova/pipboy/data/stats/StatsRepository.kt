package com.supernova.pipboy.data.stats

import android.content.Context
import android.os.StatFs
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.File

// DataStore extension
private val Context.statsDataStore: DataStore<Preferences> by preferencesDataStore(name = "pipboy_stats")

/**
 * Repository for managing S.P.E.C.I.A.L. stats
 * Tracks phone usage and calculates corresponding stats
 */
class StatsRepository(private val context: Context) {

    private val gson = Gson()
    
    companion object {
        private val SPECIAL_PROFILE_KEY = stringPreferencesKey("special_profile")
        private val USAGE_METRICS_KEY = stringPreferencesKey("usage_metrics")
        private val PERKS_KEY = stringPreferencesKey("unlocked_perks")
        private val STAT_HISTORY_KEY = stringPreferencesKey("stat_history")
        
        // Metric tracking keys
        private val NOTIFICATIONS_COUNT_KEY = longPreferencesKey("notifications_count")
        private val UPTIME_START_KEY = longPreferencesKey("uptime_start")
        private val GESTURES_COUNT_KEY = longPreferencesKey("gestures_count")
        private val ACHIEVEMENTS_COUNT_KEY = longPreferencesKey("achievements_count")
    }

    /**
     * Get current S.P.E.C.I.A.L. profile as Flow
     */
    fun getSpecialProfileFlow(): Flow<SpecialProfile> {
        return context.statsDataStore.data.map { preferences ->
            val json = preferences[SPECIAL_PROFILE_KEY]
            if (json != null) {
                try {
                    gson.fromJson(json, SpecialProfile::class.java)
                } catch (e: Exception) {
                    Timber.e(e, "Error parsing special profile")
                    createDefaultProfile()
                }
            } else {
                createDefaultProfile()
            }
        }
    }

    /**
     * Get current S.P.E.C.I.A.L. profile (suspend)
     */
    suspend fun getSpecialProfile(): SpecialProfile {
        val preferences = context.statsDataStore.data.map { it }.first()
        val json = preferences[SPECIAL_PROFILE_KEY]
        return if (json != null) {
            try {
                gson.fromJson(json, SpecialProfile::class.java)
            } catch (e: Exception) {
                Timber.e(e, "Error parsing special profile")
                createDefaultProfile()
            }
        } else {
            createDefaultProfile()
        }
    }

    /**
     * Save S.P.E.C.I.A.L. profile
     */
    private suspend fun saveSpecialProfile(profile: SpecialProfile) {
        context.statsDataStore.edit { preferences ->
            preferences[SPECIAL_PROFILE_KEY] = gson.toJson(profile)
        }
    }

    /**
     * Calculate and update all stats based on current usage
     */
    suspend fun updateStats() {
        try {
            val oldProfile = getSpecialProfile()
            val metrics = collectUsageMetrics()
            val newProfile = calculateStatsFromMetrics(metrics)
            saveSpecialProfile(newProfile)
            checkAndUnlockPerks(newProfile)
            
            // Track achievement for stat level ups
            trackStatLevelUps(oldProfile, newProfile)
            
            Timber.d("Stats updated successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to update stats")
        }
    }
    
    /**
     * Track achievements for stat level increases
     */
    private fun trackStatLevelUps(oldProfile: SpecialProfile, newProfile: SpecialProfile) {
        try {
            val app = context.applicationContext as? PipBoyApplication ?: return
            
            // Check each stat for level ups
            listOf(
                "strength" to (oldProfile.strength.level to newProfile.strength.level),
                "perception" to (oldProfile.perception.level to newProfile.perception.level),
                "endurance" to (oldProfile.endurance.level to newProfile.endurance.level),
                "charisma" to (oldProfile.charisma.level to newProfile.charisma.level),
                "intelligence" to (oldProfile.intelligence.level to newProfile.intelligence.level),
                "agility" to (oldProfile.agility.level to newProfile.agility.level),
                "luck" to (oldProfile.luck.level to newProfile.luck.level)
            ).forEach { (statName, levels) ->
                val (oldLevel, newLevel) = levels
                if (newLevel > oldLevel) {
                    // Track the new level reached
                    app.achievementManager.trackEvent(
                        AchievementEvent.StatLevelReached(statName, newLevel)
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to track stat level achievements")
        }
    }

    /**
     * Collect current usage metrics from device
     */
    private suspend fun collectUsageMetrics(): UsageMetrics {
        return UsageMetrics(
            // Strength: Storage
            totalStorageUsedMb = getTotalStorageUsedMb(),
            heavyAppsInstalled = getHeavyAppsCount(),
            largeFilesStored = getLargeFilesCount(),
            
            // Perception: Notifications & Sensors
            notificationsReceived = getNotificationsCount(),
            sensorsUsed = getActiveSensorsCount(),
            activeAlerts = getActiveAlertsCount(),
            
            // Endurance: Battery & Uptime
            uptimeHours = getUptimeHours(),
            batteryHealthPercent = getBatteryHealth(),
            chargeCycles = getChargeCycles(),
            
            // Charisma: Social
            contactsCount = getContactsCount(),
            socialAppUsageMinutes = getSocialAppUsage(),
            messagesExchanged = getMessagesExchanged(),
            
            // Intelligence: Productivity
            productivityAppUsageMinutes = getProductivityAppUsage(),
            calculationsPerformed = getCalculationsPerformed(),
            documentsCreated = getDocumentsCreated(),
            
            // Agility: Speed
            averageResponseTimeMs = getAverageResponseTime(),
            gesturesPerformed = getGesturesPerformed(),
            quickActionsUsed = getQuickActionsUsed(),
            
            // Luck: Random events
            randomEventsTriggered = getRandomEventsTriggered(),
            achievementsUnlocked = getAchievementsUnlocked(),
            questsCompleted = getQuestsCompleted()
        )
    }

    /**
     * Calculate stats from metrics
     */
    private fun calculateStatsFromMetrics(metrics: UsageMetrics): SpecialProfile {
        // Strength: Based on storage usage
        val strengthXp = (metrics.totalStorageUsedMb / 1000) + 
                        (metrics.heavyAppsInstalled * 50L) +
                        (metrics.largeFilesStored * 20L)
        
        // Perception: Based on notifications and sensors
        val perceptionXp = (metrics.notificationsReceived * 5L) +
                          (metrics.sensorsUsed * 100L) +
                          (metrics.activeAlerts * 10L)
        
        // Endurance: Based on uptime and battery
        val enduranceXp = (metrics.uptimeHours * 10L) +
                         (metrics.batteryHealthPercent * 5L) +
                         (100 - metrics.chargeCycles.coerceAtMost(100)) * 2L
        
        // Charisma: Based on social activity
        val charismaXp = (metrics.contactsCount * 10L) +
                        (metrics.socialAppUsageMinutes / 60) * 20L +
                        (metrics.messagesExchanged * 2L)
        
        // Intelligence: Based on productivity
        val intelligenceXp = (metrics.productivityAppUsageMinutes / 60) * 30L +
                            (metrics.calculationsPerformed * 5L) +
                            (metrics.documentsCreated * 25L)
        
        // Agility: Based on speed and interactions
        val agilityXp = (10000 / (metrics.averageResponseTimeMs.coerceAtLeast(1))) +
                       (metrics.gesturesPerformed / 10) +
                       (metrics.quickActionsUsed * 15L)
        
        // Luck: Based on achievements and events
        val luckXp = (metrics.randomEventsTriggered * 50L) +
                    (metrics.achievementsUnlocked * 100L) +
                    (metrics.questsCompleted * 75L)
        
        val strength = createStatValue(SpecialStat.STRENGTH, strengthXp)
        val perception = createStatValue(SpecialStat.PERCEPTION, perceptionXp)
        val endurance = createStatValue(SpecialStat.ENDURANCE, enduranceXp)
        val charisma = createStatValue(SpecialStat.CHARISMA, charismaXp)
        val intelligence = createStatValue(SpecialStat.INTELLIGENCE, intelligenceXp)
        val agility = createStatValue(SpecialStat.AGILITY, agilityXp)
        val luck = createStatValue(SpecialStat.LUCK, luckXp)
        
        val totalLevel = strength.level + perception.level + endurance.level + 
                        charisma.level + intelligence.level + agility.level + luck.level
        val overallXp = strengthXp + perceptionXp + enduranceXp + charismaXp +
                       intelligenceXp + agilityXp + luckXp
        
        return SpecialProfile(
            strength = strength,
            perception = perception,
            endurance = endurance,
            charisma = charisma,
            intelligence = intelligence,
            agility = agility,
            luck = luck,
            totalLevel = totalLevel,
            overallXp = overallXp
        )
    }

    /**
     * Create stat value from experience
     */
    private fun createStatValue(stat: SpecialStat, experience: Long): StatValue {
        val level = StatCalculations.calculateLevel(experience)
        val nextLevelXp = StatCalculations.xpForNextLevel(level)
        val description = StatCalculations.getStatDescription(stat, level)
        val effects = StatCalculations.getStatEffects(stat, level)
        
        return StatValue(
            stat = stat,
            level = level,
            experience = experience,
            nextLevelXp = nextLevelXp,
            description = description,
            effects = effects
        )
    }

    /**
     * Create default profile for new users
     */
    private fun createDefaultProfile(): SpecialProfile {
        val defaultStat = StatValue(
            stat = SpecialStat.STRENGTH,
            level = 1,
            experience = 0,
            nextLevelXp = 100,
            description = "New vault dweller",
            effects = emptyList()
        )
        
        return SpecialProfile(
            strength = defaultStat.copy(stat = SpecialStat.STRENGTH),
            perception = defaultStat.copy(stat = SpecialStat.PERCEPTION),
            endurance = defaultStat.copy(stat = SpecialStat.ENDURANCE),
            charisma = defaultStat.copy(stat = SpecialStat.CHARISMA),
            intelligence = defaultStat.copy(stat = SpecialStat.INTELLIGENCE),
            agility = defaultStat.copy(stat = SpecialStat.AGILITY),
            luck = defaultStat.copy(stat = SpecialStat.LUCK),
            totalLevel = 7,
            overallXp = 0
        )
    }

    /**
     * Check and unlock perks based on stat levels
     */
    private suspend fun checkAndUnlockPerks(profile: SpecialProfile) {
        // TODO: Implement perk unlocking system
        // This would check each stat level and unlock corresponding perks
    }

    // ==================== Metric Collection Methods ====================

    private fun getTotalStorageUsedMb(): Long {
        return try {
            val stat = StatFs(context.filesDir.path)
            val totalBytes = stat.totalBytes
            val availableBytes = stat.availableBytes
            (totalBytes - availableBytes) / (1024 * 1024)
        } catch (e: Exception) {
            0L
        }
    }

    private fun getHeavyAppsCount(): Int {
        return try {
            val pm = context.packageManager
            pm.getInstalledApplications(0).count { appInfo ->
                val file = File(appInfo.sourceDir)
                file.length() > 100 * 1024 * 1024 // Apps > 100MB
            }
        } catch (e: Exception) {
            0
        }
    }

    private fun getLargeFilesCount(): Int {
        // Simplified: count files in app directory over 10MB
        return try {
            context.filesDir.listFiles()?.count { file ->
                file.length() > 10 * 1024 * 1024
            } ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private suspend fun getNotificationsCount(): Int {
        val preferences = context.statsDataStore.data.map { it }.first()
        return preferences[NOTIFICATIONS_COUNT_KEY]?.toInt() ?: 0
    }

    private fun getActiveSensorsCount(): Int {
        // Simplified: return fixed number for now
        return 3 // GPS, Accelerometer, Light sensor
    }

    private fun getActiveAlertsCount(): Int {
        // Would integrate with notification manager
        return 0
    }

    private suspend fun getUptimeHours(): Long {
        val preferences = context.statsDataStore.data.map { it }.first()
        val startTime = preferences[UPTIME_START_KEY] ?: System.currentTimeMillis()
        return (System.currentTimeMillis() - startTime) / (1000 * 60 * 60)
    }

    private fun getBatteryHealth(): Int {
        // Simplified: return 100 for now
        return 100
    }

    private fun getChargeCycles(): Int {
        // Would need to track battery charges
        return 0
    }

    private fun getContactsCount(): Int {
        // Would query contact provider
        return 0
    }

    private fun getSocialAppUsage(): Long {
        // Would query usage stats
        return 0L
    }

    private fun getMessagesExchanged(): Int {
        // Would query SMS/messaging apps
        return 0
    }

    private fun getProductivityAppUsage(): Long {
        // Would query usage stats for productivity apps
        return 0L
    }

    private fun getCalculationsPerformed(): Int {
        // Would track calculator usage
        return 0
    }

    private fun getDocumentsCreated(): Int {
        // Would track document creation
        return 0
    }

    private fun getAverageResponseTime(): Long {
        // Would track touch/interaction latency
        return 100L
    }

    private suspend fun getGesturesPerformed(): Int {
        val preferences = context.statsDataStore.data.map { it }.first()
        return preferences[GESTURES_COUNT_KEY]?.toInt() ?: 0
    }

    private fun getQuickActionsUsed(): Int {
        // Would track quick action usage
        return 0
    }

    private fun getRandomEventsTriggered(): Int {
        // Would track random events
        return 0
    }

    private suspend fun getAchievementsUnlocked(): Int {
        val preferences = context.statsDataStore.data.map { it }.first()
        return preferences[ACHIEVEMENTS_COUNT_KEY]?.toInt() ?: 0
    }

    private suspend fun getQuestsCompleted(): Int {
        // Would query quest repository
        return 0
    }

    /**
     * Increment notification count
     */
    suspend fun incrementNotifications(count: Int = 1) {
        context.statsDataStore.edit { preferences ->
            val current = preferences[NOTIFICATIONS_COUNT_KEY] ?: 0L
            preferences[NOTIFICATIONS_COUNT_KEY] = current + count
        }
    }

    /**
     * Increment gesture count
     */
    suspend fun incrementGestures(count: Int = 1) {
        context.statsDataStore.edit { preferences ->
            val current = preferences[GESTURES_COUNT_KEY] ?: 0L
            preferences[GESTURES_COUNT_KEY] = current + count
        }
    }

    /**
     * Increment achievements
     */
    suspend fun incrementAchievements(count: Int = 1) {
        context.statsDataStore.edit { preferences ->
            val current = preferences[ACHIEVEMENTS_COUNT_KEY] ?: 0L
            preferences[ACHIEVEMENTS_COUNT_KEY] = current + count
        }
    }
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

