package com.supernova.pipboy.wear.services

import android.util.Log
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import com.google.gson.Gson
import com.supernova.pipboy.wear.data.WearDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Wear Data Listener Service
 * Handles data synchronization between phone and watch
 */
class WearDataListenerService : WearableListenerService() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val gson = Gson()
    private val dataManager = WearDataManager.getInstance(applicationContext)

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        super.onDataChanged(dataEvents)

        scope.launch {
            try {
                dataEvents.forEach { event ->
                    when (event.type) {
                        DataEvent.TYPE_CHANGED -> handleDataChanged(event)
                        DataEvent.TYPE_DELETED -> handleDataDeleted(event)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing data events", e)
            }
        }
    }

    private fun handleDataChanged(event: DataEvent) {
        val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap

        when (event.dataItem.uri.path) {
            PATH_SYSTEM_STATUS -> {
                val statusJson = dataMap.getString(KEY_SYSTEM_STATUS)
                statusJson?.let {
                    try {
                        val systemStatus = gson.fromJson(it, SystemStatus::class.java)
                        dataManager.updateSystemStatus(systemStatus)
                        Log.d(TAG, "Updated system status: $systemStatus")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing system status", e)
                    }
                }
            }

            PATH_APP_DATA -> {
                val appsJson = dataMap.getString(KEY_APP_DATA)
                appsJson?.let {
                    try {
                        val appList = gson.fromJson(it, Array<AppInfo>::class.java).toList()
                        dataManager.updateAppData(appList)
                        Log.d(TAG, "Updated app data: ${appList.size} apps")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing app data", e)
                    }
                }
            }

            PATH_USER_PREFERENCES -> {
                val prefsJson = dataMap.getString(KEY_USER_PREFERENCES)
                prefsJson?.let {
                    try {
                        val preferences = gson.fromJson(it, UserPreferences::class.java)
                        dataManager.updateUserPreferences(preferences)
                        Log.d(TAG, "Updated user preferences: $preferences")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing user preferences", e)
                    }
                }
            }

            PATH_THEME_SETTINGS -> {
                val themeJson = dataMap.getString(KEY_THEME_SETTINGS)
                themeJson?.let {
                    try {
                        val themeSettings = gson.fromJson(it, ThemeSettings::class.java)
                        dataManager.updateThemeSettings(themeSettings)
                        Log.d(TAG, "Updated theme settings: $themeSettings")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing theme settings", e)
                    }
                }
            }
        }
    }

    private fun handleDataDeleted(event: DataEvent) {
        when (event.dataItem.uri.path) {
            PATH_SYSTEM_STATUS -> {
                Log.d(TAG, "System status data deleted")
                dataManager.clearSystemStatus()
            }
            PATH_APP_DATA -> {
                Log.d(TAG, "App data deleted")
                dataManager.clearAppData()
            }
            PATH_USER_PREFERENCES -> {
                Log.d(TAG, "User preferences deleted")
                dataManager.clearUserPreferences()
            }
        }
    }

    companion object {
        private const val TAG = "WearDataListener"

        // Data paths
        const val PATH_SYSTEM_STATUS = "/pipboy/system_status"
        const val PATH_APP_DATA = "/pipboy/app_data"
        const val PATH_USER_PREFERENCES = "/pipboy/user_preferences"
        const val PATH_THEME_SETTINGS = "/pipboy/theme_settings"

        // Data keys
        const val KEY_SYSTEM_STATUS = "system_status"
        const val KEY_APP_DATA = "app_data"
        const val KEY_USER_PREFERENCES = "user_preferences"
        const val KEY_THEME_SETTINGS = "theme_settings"
    }
}

/**
 * System status data class
 */
data class SystemStatus(
    val batteryLevel: Int,
    val storageUsed: Long,
    val storageTotal: Long,
    val wifiConnected: Boolean,
    val cellularConnected: Boolean,
    val bluetoothEnabled: Boolean,
    val cpuUsage: Float,
    val temperature: Float,
    val isOverheating: Boolean,
    val memoryUsage: Float,
    val currentTime: String,
    val uptime: Long,
    val timestamp: Long
)

/**
 * App information data class
 */
data class AppInfo(
    val name: String,
    val packageName: String,
    val category: String,
    val isFavorite: Boolean,
    val installTime: Long,
    val lastUsedTime: Long,
    val usageCount: Int,
    val versionName: String,
    val versionCode: Int,
    val isSystemApp: Boolean,
    val isEnabled: Boolean
)

/**
 * User preferences data class
 */
data class UserPreferences(
    val primaryColor: String,
    val crtScanlinesEnabled: Boolean,
    val crtFlickerEnabled: Boolean,
    val crtDistortionEnabled: Boolean,
    val userNotes: String,
    val favoriteApps: List<String>,
    val themeMode: String,
    val hapticFeedbackEnabled: Boolean,
    val soundEffectsEnabled: Boolean,
    val autoBrightnessEnabled: Boolean,
    val notificationSettings: Map<String, Boolean>,
    val backupSettings: Map<String, Boolean>,
    val privacySettings: Map<String, Boolean>,
    val performanceSettings: Map<String, Boolean>,
    val accessibilitySettings: Map<String, Boolean>
)

/**
 * Theme settings data class
 */
data class ThemeSettings(
    val isDarkMode: Boolean,
    val useSystemTheme: Boolean,
    val useMaterialYou: Boolean,
    val primaryColor: String,
    val colorPalette: Map<String, String>
)
