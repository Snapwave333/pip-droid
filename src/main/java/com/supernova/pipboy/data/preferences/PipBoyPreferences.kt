package com.supernova.pipboy.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.supernova.pipboy.data.model.PipBoyColor

/**
 * Manages user preferences for the Pip-Boy launcher
 */
class PipBoyPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var primaryColor: PipBoyColor
        get() {
            val red = prefs.getInt(KEY_RED, PipBoyColor.DEFAULT.red)
            val green = prefs.getInt(KEY_GREEN, PipBoyColor.DEFAULT.green)
            val blue = prefs.getInt(KEY_BLUE, PipBoyColor.DEFAULT.blue)
            return PipBoyColor(red, green, blue)
        }
        set(value) {
            prefs.edit {
                putInt(KEY_RED, value.red)
                putInt(KEY_GREEN, value.green)
                putInt(KEY_BLUE, value.blue)
            }
        }

    var crtScanlinesEnabled: Boolean
        get() = prefs.getBoolean(KEY_CRT_SCANLINES, true)
        set(value) = prefs.edit { putBoolean(KEY_CRT_SCANLINES, value) }

    var crtFlickerEnabled: Boolean
        get() = prefs.getBoolean(KEY_CRT_FLICKER, true)
        set(value) = prefs.edit { putBoolean(KEY_CRT_FLICKER, value) }

    var crtDistortionEnabled: Boolean
        get() = prefs.getBoolean(KEY_CRT_DISTORTION, true)
        set(value) = prefs.edit { putBoolean(KEY_CRT_DISTORTION, value) }

    var soundEnabled: Boolean
        get() = prefs.getBoolean(KEY_SOUND_ENABLED, true)
        set(value) = prefs.edit { putBoolean(KEY_SOUND_ENABLED, value) }

    var autoBrightness: Boolean
        get() = prefs.getBoolean(KEY_AUTO_BRIGHTNESS, false)
        set(value) = prefs.edit { putBoolean(KEY_AUTO_BRIGHTNESS, value) }

    var brightness: Float
        get() = prefs.getFloat(KEY_BRIGHTNESS, 1.0f)
        set(value) = prefs.edit { putFloat(KEY_BRIGHTNESS, value.coerceIn(0.1f, 2.0f)) }

    var favoriteApps: Set<String>
        get() = prefs.getStringSet(KEY_FAVORITE_APPS, emptySet()) ?: emptySet()
        set(value) = prefs.edit { putStringSet(KEY_FAVORITE_APPS, value) }

    var notes: String
        get() = prefs.getString(KEY_NOTES, "") ?: ""
        set(value) = prefs.edit { putString(KEY_NOTES, value) }

    // ========== Achievement System ==========
    
    var masterVolume: Float
        get() = prefs.getFloat(KEY_MASTER_VOLUME, 1.0f)
        set(value) = prefs.edit { putFloat(KEY_MASTER_VOLUME, value.coerceIn(0f, 1f)) }

    fun getAchievementProgress(): Map<String, Int> {
        val json = prefs.getString(KEY_ACHIEVEMENT_PROGRESS, "{}") ?: "{}"
        return try {
            com.google.gson.Gson().fromJson(json, Map::class.java)
                .mapKeys { it.key.toString() }
                .mapValues { (it.value as? Double)?.toInt() ?: 0 }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    fun setAchievementProgress(progress: Map<String, Int>) {
        val json = com.google.gson.Gson().toJson(progress)
        prefs.edit { putString(KEY_ACHIEVEMENT_PROGRESS, json) }
    }

    var lastOpenDay: Long
        get() = prefs.getLong(KEY_LAST_OPEN_DAY, 0)
        set(value) = prefs.edit { putLong(KEY_LAST_OPEN_DAY, value) }

    var consecutiveDays: Int
        get() = prefs.getInt(KEY_CONSECUTIVE_DAYS, 0)
        set(value) = prefs.edit { putInt(KEY_CONSECUTIVE_DAYS, value) }

    var totalDaysUsed: Int
        get() = prefs.getInt(KEY_TOTAL_DAYS_USED, 0)
        set(value) = prefs.edit { putInt(KEY_TOTAL_DAYS_USED, value) }

    var lastQuestDay: Long
        get() = prefs.getLong(KEY_LAST_QUEST_DAY, 0)
        set(value) = prefs.edit { putLong(KEY_LAST_QUEST_DAY, value) }

    var questStreak: Int
        get() = prefs.getInt(KEY_QUEST_STREAK, 0)
        set(value) = prefs.edit { putInt(KEY_QUEST_STREAK, value) }

    var listenedStations: Set<String>
        get() = prefs.getStringSet(KEY_LISTENED_STATIONS, emptySet()) ?: emptySet()
        set(value) = prefs.edit { putStringSet(KEY_LISTENED_STATIONS, value) }

    var radioMinutes: Int
        get() = prefs.getInt(KEY_RADIO_MINUTES, 0)
        set(value) = prefs.edit { putInt(KEY_RADIO_MINUTES, value) }
    
    var tabVisits: Int
        get() = prefs.getInt(KEY_TAB_VISITS, 0)
        set(value) = prefs.edit { putInt(KEY_TAB_VISITS, value) }

    // ID Badge preferences
    var idBadgeName: String
        get() = prefs.getString(KEY_ID_BADGE_NAME, "Vault Dweller") ?: "Vault Dweller"
        set(value) = prefs.edit { putString(KEY_ID_BADGE_NAME, value) }

    var idBadgeVaultNumber: String
        get() = prefs.getString(KEY_ID_BADGE_VAULT_NUMBER, "101") ?: "101"
        set(value) = prefs.edit { putString(KEY_ID_BADGE_VAULT_NUMBER, value) }

    var idBadgeRank: String
        get() = prefs.getString(KEY_ID_BADGE_RANK, "Citizen") ?: "Citizen"
        set(value) = prefs.edit { putString(KEY_ID_BADGE_RANK, value) }

    var idBadgeVisible: Boolean
        get() = prefs.getBoolean(KEY_ID_BADGE_VISIBLE, false)
        set(value) = prefs.edit { putBoolean(KEY_ID_BADGE_VISIBLE, value) }

    /**
     * Reset all preferences to default values
     */
    fun resetToDefaults() {
        primaryColor = PipBoyColor.DEFAULT
        crtScanlinesEnabled = true
        crtFlickerEnabled = true
        crtDistortionEnabled = true
        soundEnabled = true
        autoBrightness = false
        brightness = 1.0f
        favoriteApps = emptySet()
        notes = ""
        masterVolume = 1.0f
    }

    companion object {
        private const val PREFS_NAME = "pipboy_preferences"

        private const val KEY_RED = "primary_color_red"
        private const val KEY_GREEN = "primary_color_green"
        private const val KEY_BLUE = "primary_color_blue"

        private const val KEY_CRT_SCANLINES = "crt_scanlines_enabled"
        private const val KEY_CRT_FLICKER = "crt_flicker_enabled"
        private const val KEY_CRT_DISTORTION = "crt_distortion_enabled"

        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_AUTO_BRIGHTNESS = "auto_brightness"
        private const val KEY_BRIGHTNESS = "brightness"

        private const val KEY_FAVORITE_APPS = "favorite_apps"
        private const val KEY_NOTES = "quick_notes"
        
        // Achievement system keys
        private const val KEY_MASTER_VOLUME = "master_volume"
        private const val KEY_ACHIEVEMENT_PROGRESS = "achievement_progress"
        private const val KEY_LAST_OPEN_DAY = "last_open_day"
        private const val KEY_CONSECUTIVE_DAYS = "consecutive_days"
        private const val KEY_TOTAL_DAYS_USED = "total_days_used"
        private const val KEY_LAST_QUEST_DAY = "last_quest_day"
        private const val KEY_QUEST_STREAK = "quest_streak"
        private const val KEY_LISTENED_STATIONS = "listened_stations"
        private const val KEY_RADIO_MINUTES = "radio_minutes"
        private const val KEY_TAB_VISITS = "tab_visits"
        private const val KEY_ID_BADGE_NAME = "id_badge_name"
        private const val KEY_ID_BADGE_VAULT_NUMBER = "id_badge_vault_number"
        private const val KEY_ID_BADGE_RANK = "id_badge_rank"
        private const val KEY_ID_BADGE_VISIBLE = "id_badge_visible"
    }
}
