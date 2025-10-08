package com.supernova.pipboy.data.stats

import java.util.*

/**
 * S.P.E.C.I.A.L. Stats System
 * Tracks phone usage as Fallout RPG statistics
 */

/**
 * The S.P.E.C.I.A.L. stat categories
 */
enum class SpecialStat {
    STRENGTH,       // Physical power - correlates to storage, heavy apps
    PERCEPTION,     // Awareness - correlates to notifications, sensors
    ENDURANCE,      // Stamina - correlates to battery life, uptime
    CHARISMA,       // Social - correlates to contacts, social apps
    INTELLIGENCE,   // Mental - correlates to productivity apps, calculator
    AGILITY,        // Speed - correlates to CPU speed, response time
    LUCK            // Chance - correlates to random events, achievements
}

/**
 * Individual stat value with level
 */
data class StatValue(
    val stat: SpecialStat,
    val level: Int,          // 1-10 (Fallout scale)
    val experience: Long,    // Raw experience points
    val nextLevelXp: Long,   // XP needed for next level
    val description: String,
    val effects: List<String> = emptyList()
)

/**
 * Complete S.P.E.C.I.A.L. profile
 */
data class SpecialProfile(
    val strength: StatValue,
    val perception: StatValue,
    val endurance: StatValue,
    val charisma: StatValue,
    val intelligence: StatValue,
    val agility: StatValue,
    val luck: StatValue,
    val totalLevel: Int,
    val overallXp: Long,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun getStatValue(stat: SpecialStat): StatValue {
        return when (stat) {
            SpecialStat.STRENGTH -> strength
            SpecialStat.PERCEPTION -> perception
            SpecialStat.ENDURANCE -> endurance
            SpecialStat.CHARISMA -> charisma
            SpecialStat.INTELLIGENCE -> intelligence
            SpecialStat.AGILITY -> agility
            SpecialStat.LUCK -> luck
        }
    }
    
    fun getAllStats(): List<StatValue> {
        return listOf(strength, perception, endurance, charisma, intelligence, agility, luck)
    }
}

/**
 * Perk earned from high stats
 */
data class Perk(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val requiredStat: SpecialStat,
    val requiredLevel: Int,
    val icon: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

/**
 * Stat change event for tracking progression
 */
data class StatChangeEvent(
    val stat: SpecialStat,
    val oldLevel: Int,
    val newLevel: Int,
    val reason: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Usage metrics that contribute to stats
 */
data class UsageMetrics(
    // Strength metrics
    val totalStorageUsedMb: Long = 0,
    val heavyAppsInstalled: Int = 0,
    val largeFilesStored: Int = 0,
    
    // Perception metrics
    val notificationsReceived: Int = 0,
    val sensorsUsed: Int = 0,
    val activeAlerts: Int = 0,
    
    // Endurance metrics
    val uptimeHours: Long = 0,
    val batteryHealthPercent: Int = 100,
    val chargeCycles: Int = 0,
    
    // Charisma metrics
    val contactsCount: Int = 0,
    val socialAppUsageMinutes: Long = 0,
    val messagesExchanged: Int = 0,
    
    // Intelligence metrics
    val productivityAppUsageMinutes: Long = 0,
    val calculationsPerformed: Int = 0,
    val documentsCreated: Int = 0,
    
    // Agility metrics
    val averageResponseTimeMs: Long = 0,
    val gesturesPerformed: Int = 0,
    val quickActionsUsed: Int = 0,
    
    // Luck metrics
    val randomEventsTriggered: Int = 0,
    val achievementsUnlocked: Int = 0,
    val questsCompleted: Int = 0,
    
    val lastCalculated: Long = System.currentTimeMillis()
)

/**
 * Helper functions for stat calculations
 */
object StatCalculations {
    
    /**
     * Calculate stat level from experience
     */
    fun calculateLevel(experience: Long): Int {
        // Each level requires progressively more XP
        // Level 1: 0 XP
        // Level 2: 100 XP
        // Level 3: 300 XP (cumulative)
        // Level 4: 600 XP
        // etc.
        var level = 1
        var totalXpNeeded = 0L
        
        while (level < 10) {
            val xpForNextLevel = level * 100L
            if (experience < totalXpNeeded + xpForNextLevel) {
                break
            }
            totalXpNeeded += xpForNextLevel
            level++
        }
        
        return level.coerceIn(1, 10)
    }
    
    /**
     * Calculate XP needed for next level
     */
    fun xpForNextLevel(currentLevel: Int): Long {
        return if (currentLevel >= 10) 0L else currentLevel * 100L
    }
    
    /**
     * Calculate total XP needed to reach a level
     */
    fun totalXpForLevel(level: Int): Long {
        var total = 0L
        for (i in 1 until level) {
            total += i * 100L
        }
        return total
    }
    
    /**
     * Get stat description based on level
     */
    fun getStatDescription(stat: SpecialStat, level: Int): String {
        return when (stat) {
            SpecialStat.STRENGTH -> when (level) {
                1, 2 -> "Weak storage capacity"
                3, 4 -> "Average storage management"
                5, 6 -> "Good storage organization"
                7, 8 -> "Strong storage optimization"
                else -> "Massive storage power"
            }
            SpecialStat.PERCEPTION -> when (level) {
                1, 2 -> "Rarely checks notifications"
                3, 4 -> "Aware of important alerts"
                5, 6 -> "Highly attentive to updates"
                7, 8 -> "Extremely perceptive"
                else -> "Omniscient awareness"
            }
            SpecialStat.ENDURANCE -> when (level) {
                1, 2 -> "Short battery life"
                3, 4 -> "Average uptime"
                5, 6 -> "Good battery management"
                7, 8 -> "Excellent endurance"
                else -> "Unstoppable battery"
            }
            SpecialStat.CHARISMA -> when (level) {
                1, 2 -> "Antisocial device"
                3, 4 -> "Some social activity"
                5, 6 -> "Active social life"
                7, 8 -> "Social butterfly"
                else -> "Legendary charisma"
            }
            SpecialStat.INTELLIGENCE -> when (level) {
                1, 2 -> "Basic device usage"
                3, 4 -> "Average productivity"
                5, 6 -> "Smart user habits"
                7, 8 -> "Highly productive"
                else -> "Genius-level intelligence"
            }
            SpecialStat.AGILITY -> when (level) {
                1, 2 -> "Slow response times"
                3, 4 -> "Average speed"
                5, 6 -> "Quick interactions"
                7, 8 -> "Lightning fast"
                else -> "Superhuman speed"
            }
            SpecialStat.LUCK -> when (level) {
                1, 2 -> "Unlucky user"
                3, 4 -> "Average fortune"
                5, 6 -> "Lucky breaks"
                7, 8 -> "Very fortunate"
                else -> "Blessed by RNGesus"
            }
        }
    }
    
    /**
     * Get stat effects based on level
     */
    fun getStatEffects(stat: SpecialStat, level: Int): List<String> {
        val effects = mutableListOf<String>()
        
        when (stat) {
            SpecialStat.STRENGTH -> {
                if (level >= 3) effects.add("+10% storage efficiency")
                if (level >= 5) effects.add("+20% app installation speed")
                if (level >= 7) effects.add("+30% file transfer speed")
                if (level >= 9) effects.add("Unlock: Heavy Lifter perk")
            }
            SpecialStat.PERCEPTION -> {
                if (level >= 3) effects.add("+10% notification priority")
                if (level >= 5) effects.add("+20% sensor accuracy")
                if (level >= 7) effects.add("+30% alert awareness")
                if (level >= 9) effects.add("Unlock: Eagle Eye perk")
            }
            SpecialStat.ENDURANCE -> {
                if (level >= 3) effects.add("+10% battery life")
                if (level >= 5) effects.add("+20% uptime bonus")
                if (level >= 7) effects.add("+30% thermal resistance")
                if (level >= 9) effects.add("Unlock: Iron Will perk")
            }
            SpecialStat.CHARISMA -> {
                if (level >= 3) effects.add("+10% social app performance")
                if (level >= 5) effects.add("+20% contact synergy")
                if (level >= 7) effects.add("+30% message charm")
                if (level >= 9) effects.add("Unlock: Party Animal perk")
            }
            SpecialStat.INTELLIGENCE -> {
                if (level >= 3) effects.add("+10% productivity boost")
                if (level >= 5) effects.add("+20% calculation speed")
                if (level >= 7) effects.add("+30% document quality")
                if (level >= 9) effects.add("Unlock: Big Brain perk")
            }
            SpecialStat.AGILITY -> {
                if (level >= 3) effects.add("+10% response speed")
                if (level >= 5) effects.add("+20% gesture fluidity")
                if (level >= 7) effects.add("+30% action speed")
                if (level >= 9) effects.add("Unlock: Quickdraw perk")
            }
            SpecialStat.LUCK -> {
                if (level >= 3) effects.add("+10% critical chance")
                if (level >= 5) effects.add("+20% rare event rate")
                if (level >= 7) effects.add("+30% achievement bonus")
                if (level >= 9) effects.add("Unlock: Fortune Finder perk")
            }
        }
        
        return effects
    }
}

