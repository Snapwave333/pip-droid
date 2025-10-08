package com.supernova.pipboy.wear.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.supernova.pipboy.wear.services.*

/**
 * Wear Data Manager
 * Manages data storage and synchronization for the Wear OS app
 */
class WearDataManager private constructor(private val context: Context) {

    private val gson = Gson()
    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val PREF_SYSTEM_STATUS = "system_status"
        private const val PREF_APP_DATA = "app_data"
        private const val PREF_USER_PREFERENCES = "user_preferences"
        private const val PREF_THEME_SETTINGS = "theme_settings"
        private const val PREF_LAST_SYNC = "last_sync"

        @Volatile
        private var INSTANCE: WearDataManager? = null

        fun getInstance(context: Context): WearDataManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: WearDataManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    // System Status Management
    fun updateSystemStatus(status: SystemStatus) {
        val statusJson = gson.toJson(status)
        preferences.edit()
            .putString(PREF_SYSTEM_STATUS, statusJson)
            .putLong(PREF_LAST_SYNC, System.currentTimeMillis())
            .apply()

        // Notify listeners
        notifySystemStatusChanged(status)
    }

    fun getSystemStatus(): SystemStatus? {
        val statusJson = preferences.getString(PREF_SYSTEM_STATUS, null)
        return statusJson?.let {
            try {
                gson.fromJson(it, SystemStatus::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun clearSystemStatus() {
        preferences.edit()
            .remove(PREF_SYSTEM_STATUS)
            .apply()
    }

    // App Data Management
    fun updateAppData(apps: List<AppInfo>) {
        val appsJson = gson.toJson(apps)
        preferences.edit()
            .putString(PREF_APP_DATA, appsJson)
            .putLong(PREF_LAST_SYNC, System.currentTimeMillis())
            .apply()

        // Notify listeners
        notifyAppDataChanged(apps)
    }

    fun getAppData(): List<AppInfo> {
        val appsJson = preferences.getString(PREF_APP_DATA, null)
        return appsJson?.let {
            try {
                gson.fromJson(it, Array<AppInfo>::class.java).toList()
            } catch (e: Exception) {
                emptyList()
            }
        } ?: emptyList()
    }

    fun clearAppData() {
        preferences.edit()
            .remove(PREF_APP_DATA)
            .apply()
    }

    // User Preferences Management
    fun updateUserPreferences(prefs: UserPreferences) {
        val prefsJson = gson.toJson(prefs)
        preferences.edit()
            .putString(PREF_USER_PREFERENCES, prefsJson)
            .putLong(PREF_LAST_SYNC, System.currentTimeMillis())
            .apply()

        // Notify listeners
        notifyUserPreferencesChanged(prefs)
    }

    fun getUserPreferences(): UserPreferences? {
        val prefsJson = preferences.getString(PREF_USER_PREFERENCES, null)
        return prefsJson?.let {
            try {
                gson.fromJson(it, UserPreferences::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun clearUserPreferences() {
        preferences.edit()
            .remove(PREF_USER_PREFERENCES)
            .apply()
    }

    // Theme Settings Management
    fun updateThemeSettings(theme: ThemeSettings) {
        val themeJson = gson.toJson(theme)
        preferences.edit()
            .putString(PREF_THEME_SETTINGS, themeJson)
            .putLong(PREF_LAST_SYNC, System.currentTimeMillis())
            .apply()

        // Notify listeners
        notifyThemeSettingsChanged(theme)
    }

    fun getThemeSettings(): ThemeSettings? {
        val themeJson = preferences.getString(PREF_THEME_SETTINGS, null)
        return themeJson?.let {
            try {
                gson.fromJson(it, ThemeSettings::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun clearThemeSettings() {
        preferences.edit()
            .remove(PREF_THEME_SETTINGS)
            .apply()
    }

    // Sync Status
    fun getLastSyncTime(): Long {
        return preferences.getLong(PREF_LAST_SYNC, 0L)
    }

    fun isDataStale(maxAgeMs: Long = 300000): Boolean { // 5 minutes default
        val lastSync = getLastSyncTime()
        return System.currentTimeMillis() - lastSync > maxAgeMs
    }

    // Data Listeners
    private val systemStatusListeners = mutableListOf<(SystemStatus) -> Unit>()
    private val appDataListeners = mutableListOf<(List<AppInfo>) -> Unit>()
    private val userPreferencesListeners = mutableListOf<(UserPreferences) -> Unit>()
    private val themeSettingsListeners = mutableListOf<(ThemeSettings) -> Unit>()

    fun addSystemStatusListener(listener: (SystemStatus) -> Unit) {
        systemStatusListeners.add(listener)
    }

    fun removeSystemStatusListener(listener: (SystemStatus) -> Unit) {
        systemStatusListeners.remove(listener)
    }

    fun addAppDataListener(listener: (List<AppInfo>) -> Unit) {
        appDataListeners.add(listener)
    }

    fun removeAppDataListener(listener: (List<AppInfo>) -> Unit) {
        appDataListeners.remove(listener)
    }

    fun addUserPreferencesListener(listener: (UserPreferences) -> Unit) {
        userPreferencesListeners.add(listener)
    }

    fun removeUserPreferencesListener(listener: (UserPreferences) -> Unit) {
        userPreferencesListeners.remove(listener)
    }

    fun addThemeSettingsListener(listener: (ThemeSettings) -> Unit) {
        themeSettingsListeners.add(listener)
    }

    fun removeThemeSettingsListener(listener: (ThemeSettings) -> Unit) {
        themeSettingsListeners.remove(listener)
    }

    private fun notifySystemStatusChanged(status: SystemStatus) {
        systemStatusListeners.forEach { it(status) }
    }

    private fun notifyAppDataChanged(apps: List<AppInfo>) {
        appDataListeners.forEach { it(apps) }
    }

    private fun notifyUserPreferencesChanged(prefs: UserPreferences) {
        userPreferencesListeners.forEach { it(prefs) }
    }

    private fun notifyThemeSettingsChanged(theme: ThemeSettings) {
        themeSettingsListeners.forEach { it(theme) }
    }

    // Utility methods
    fun clearAllData() {
        preferences.edit()
            .remove(PREF_SYSTEM_STATUS)
            .remove(PREF_APP_DATA)
            .remove(PREF_USER_PREFERENCES)
            .remove(PREF_THEME_SETTINGS)
            .remove(PREF_LAST_SYNC)
            .apply()
    }

    fun getDataSummary(): Map<String, Any> {
        return mapOf(
            "system_status_exists" to (getSystemStatus() != null),
            "app_count" to getAppData().size,
            "preferences_exist" to (getUserPreferences() != null),
            "theme_settings_exist" to (getThemeSettings() != null),
            "last_sync" to getLastSyncTime(),
            "is_stale" to isDataStale()
        )
    }
}
