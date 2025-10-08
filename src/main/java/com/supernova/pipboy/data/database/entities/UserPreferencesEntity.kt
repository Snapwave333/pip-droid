package com.supernova.pipboy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import com.supernova.pipboy.data.model.PipBoyColor

/**
 * Entity representing user preferences
 * Stored locally for persistence across app sessions
 */
@Entity(
    tableName = "user_preferences",
    indices = [
        Index(value = ["preference_key"], unique = true)
    ]
)
data class UserPreferencesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "preference_key")
    val preferenceKey: String,

    @ColumnInfo(name = "preference_value")
    val preferenceValue: String,

    @ColumnInfo(name = "preference_type")
    val preferenceType: PreferenceType,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
) {

    companion object {
        // Predefined preference keys
        const val KEY_PRIMARY_COLOR = "primary_color"
        const val KEY_CRT_SCANLINES = "crt_scanlines_enabled"
        const val KEY_CRT_FLICKER = "crt_flicker_enabled"
        const val KEY_CRT_DISTORTION = "crt_distortion_enabled"
        const val KEY_USER_NOTES = "user_notes"
        const val KEY_FAVORITE_APPS = "favorite_apps"
        const val KEY_THEME_MODE = "theme_mode"
        const val KEY_HAPTIC_FEEDBACK = "haptic_feedback_enabled"
        const val KEY_SOUND_EFFECTS = "sound_effects_enabled"
        const val KEY_AUTO_BRIGHTNESS = "auto_brightness_enabled"
        const val KEY_NOTIFICATION_SETTINGS = "notification_settings"
        const val KEY_BACKUP_SETTINGS = "backup_settings"
        const val KEY_PRIVACY_SETTINGS = "privacy_settings"
        const val KEY_PERFORMANCE_SETTINGS = "performance_settings"
        const val KEY_ACCESSIBILITY_SETTINGS = "accessibility_settings"
    }
}

/**
 * Types of preferences for proper serialization/deserialization
 */
enum class PreferenceType {
    STRING,
    INT,
    BOOLEAN,
    FLOAT,
    LONG,
    COLOR, // PipBoyColor enum
    STRING_SET, // Set<String> for favorite apps
    JSON // JSON string for complex objects
}
