package com.supernova.pipboy.wear.services

import android.util.Log
import com.google.android.gms.wearable.WearableListenerService
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import com.supernova.pipboy.wear.data.WearDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Pip-Boy Wear Service
 * Handles communication and data synchronization with the phone
 */
class PipBoyWearService : WearableListenerService() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val gson = Gson()
    private val dataManager = WearDataManager.getInstance(applicationContext)
    private val messageClient by lazy { Wearable.getMessageClient(applicationContext) }
    private val dataClient by lazy { Wearable.getDataClient(applicationContext) }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        scope.launch {
            try {
                when (messageEvent.path) {
                    MESSAGE_REQUEST_SYNC -> handleSyncRequest()
                    MESSAGE_REQUEST_STATUS -> handleStatusRequest(messageEvent.sourceNodeId)
                    MESSAGE_REQUEST_APPS -> handleAppsRequest(messageEvent.sourceNodeId)
                    MESSAGE_REQUEST_PREFERENCES -> handlePreferencesRequest(messageEvent.sourceNodeId)
                    MESSAGE_REQUEST_THEME -> handleThemeRequest(messageEvent.sourceNodeId)
                    MESSAGE_UPDATE_SETTINGS -> handleSettingsUpdate(messageEvent.data)
                    MESSAGE_UPDATE_THEME -> handleThemeUpdate(messageEvent.data)
                    MESSAGE_UPDATE_PREFERENCES -> handlePreferencesUpdate(messageEvent.data)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error handling message: ${messageEvent.path}", e)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "PipBoy Wear Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "PipBoy Wear Service destroyed")
    }

    private suspend fun handleSyncRequest() {
        Log.d(TAG, "Handling sync request")
        try {
            // Request all data from phone
            sendMessage(MESSAGE_REQUEST_ALL_DATA)

            // Send current watch data to phone
            sendWatchDataToPhone()

        } catch (e: Exception) {
            Log.e(TAG, "Error handling sync request", e)
        }
    }

    private suspend fun handleStatusRequest(sourceNodeId: String) {
        Log.d(TAG, "Handling status request from $sourceNodeId")
        try {
            val status = dataManager.getSystemStatus()
            if (status != null) {
                val statusJson = gson.toJson(status)
                sendMessageToNode(sourceNodeId, MESSAGE_STATUS_RESPONSE, statusJson.toByteArray())
            } else {
                sendMessageToNode(sourceNodeId, MESSAGE_REQUEST_STATUS_UPDATE)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling status request", e)
        }
    }

    private suspend fun handleAppsRequest(sourceNodeId: String) {
        Log.d(TAG, "Handling apps request from $sourceNodeId")
        try {
            val apps = dataManager.getAppData()
            val appsJson = gson.toJson(apps)
            sendMessageToNode(sourceNodeId, MESSAGE_APPS_RESPONSE, appsJson.toByteArray())
        } catch (e: Exception) {
            Log.e(TAG, "Error handling apps request", e)
        }
    }

    private suspend fun handlePreferencesRequest(sourceNodeId: String) {
        Log.d(TAG, "Handling preferences request from $sourceNodeId")
        try {
            val preferences = dataManager.getUserPreferences()
            if (preferences != null) {
                val prefsJson = gson.toJson(preferences)
                sendMessageToNode(sourceNodeId, MESSAGE_PREFERENCES_RESPONSE, prefsJson.toByteArray())
            } else {
                sendMessageToNode(sourceNodeId, MESSAGE_REQUEST_PREFERENCES_UPDATE)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling preferences request", e)
        }
    }

    private suspend fun handleThemeRequest(sourceNodeId: String) {
        Log.d(TAG, "Handling theme request from $sourceNodeId")
        try {
            val theme = dataManager.getThemeSettings()
            if (theme != null) {
                val themeJson = gson.toJson(theme)
                sendMessageToNode(sourceNodeId, MESSAGE_THEME_RESPONSE, themeJson.toByteArray())
            } else {
                sendMessageToNode(sourceNodeId, MESSAGE_REQUEST_THEME_UPDATE)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error handling theme request", e)
        }
    }

    private fun handleSettingsUpdate(data: ByteArray) {
        try {
            val settingsJson = String(data)
            val settings = gson.fromJson(settingsJson, Any::class.java)
            Log.d(TAG, "Settings updated: $settings")
            // Handle settings update
        } catch (e: Exception) {
            Log.e(TAG, "Error handling settings update", e)
        }
    }

    private fun handleThemeUpdate(data: ByteArray) {
        try {
            val themeJson = String(data)
            val theme = gson.fromJson(themeJson, ThemeSettings::class.java)
            dataManager.updateThemeSettings(theme)
            Log.d(TAG, "Theme updated: $theme")
        } catch (e: Exception) {
            Log.e(TAG, "Error handling theme update", e)
        }
    }

    private fun handlePreferencesUpdate(data: ByteArray) {
        try {
            val prefsJson = String(data)
            val preferences = gson.fromJson(prefsJson, UserPreferences::class.java)
            dataManager.updateUserPreferences(preferences)
            Log.d(TAG, "Preferences updated: $preferences")
        } catch (e: Exception) {
            Log.e(TAG, "Error handling preferences update", e)
        }
    }

    private suspend fun sendWatchDataToPhone() {
        try {
            // Send current watch data to phone
            val dataSummary = dataManager.getDataSummary()
            val summaryJson = gson.toJson(dataSummary)
            sendMessage(MESSAGE_WATCH_DATA, summaryJson.toByteArray())
        } catch (e: Exception) {
            Log.e(TAG, "Error sending watch data to phone", e)
        }
    }

    private suspend fun sendMessage(path: String, data: ByteArray? = null) {
        try {
            val nodes = messageClient.connectedNodes.await()
            if (nodes.isNotEmpty()) {
                nodes.forEach { node ->
                    messageClient.sendMessage(node.id, path, data ?: byteArrayOf()).await()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message to path: $path", e)
        }
    }

    private suspend fun sendMessageToNode(nodeId: String, path: String, data: ByteArray? = null) {
        try {
            messageClient.sendMessage(nodeId, path, data ?: byteArrayOf()).await()
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message to node $nodeId, path: $path", e)
        }
    }

    companion object {
        private const val TAG = "PipBoyWearService"

        // Message paths
        const val MESSAGE_REQUEST_SYNC = "/pipboy/request_sync"
        const val MESSAGE_REQUEST_STATUS = "/pipboy/request_status"
        const val MESSAGE_REQUEST_APPS = "/pipboy/request_apps"
        const val MESSAGE_REQUEST_PREFERENCES = "/pipboy/request_preferences"
        const val MESSAGE_REQUEST_THEME = "/pipboy/request_theme"
        const val MESSAGE_REQUEST_ALL_DATA = "/pipboy/request_all_data"
        const val MESSAGE_REQUEST_STATUS_UPDATE = "/pipboy/request_status_update"
        const val MESSAGE_REQUEST_PREFERENCES_UPDATE = "/pipboy/request_preferences_update"
        const val MESSAGE_REQUEST_THEME_UPDATE = "/pipboy/request_theme_update"

        // Response paths
        const val MESSAGE_STATUS_RESPONSE = "/pipboy/status_response"
        const val MESSAGE_APPS_RESPONSE = "/pipboy/apps_response"
        const val MESSAGE_PREFERENCES_RESPONSE = "/pipboy/preferences_response"
        const val MESSAGE_THEME_RESPONSE = "/pipboy/theme_response"
        const val MESSAGE_WATCH_DATA = "/pipboy/watch_data"

        // Update paths
        const val MESSAGE_UPDATE_SETTINGS = "/pipboy/update_settings"
        const val MESSAGE_UPDATE_THEME = "/pipboy/update_theme"
        const val MESSAGE_UPDATE_PREFERENCES = "/pipboy/update_preferences"
    }
}
