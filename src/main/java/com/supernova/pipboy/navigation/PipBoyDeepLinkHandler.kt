package com.supernova.pipboy.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import com.supernova.pipboy.monitoring.CrashlyticsManagerStub

/**
 * Deep Link Handler for Pip-Boy application
 * Handles incoming deep links and navigates to appropriate screens
 */
class PipBoyDeepLinkHandler(
    private val context: Context,
    private val crashlyticsManager: CrashlyticsManagerStub
) {

    companion object {
        const val SCHEME = "pipboy"
        const val HOST = "pipboy"

        // Deep link patterns
        const val URI_STATUS = "pipboy://status"
        const val URI_INVENTORY = "pipboy://inventory"
        const val URI_DATA = "pipboy://data"
        const val URI_MAP = "pipboy://map"
        const val URI_RADIO = "pipboy://radio"
        const val URI_SETTINGS = "pipboy://settings"
        const val URI_PROFILE = "pipboy://profile"
    }

    /**
     * Handle incoming deep link intent
     */
    fun handleDeepLink(intent: Intent, navController: NavController): Boolean {
        return when (intent.action) {
            Intent.ACTION_VIEW -> {
                val uri = intent.data
                uri?.let { handleUri(it, navController) } ?: false
            }
            else -> false
        }
    }

    /**
     * Handle URI-based deep links
     */
    private fun handleUri(uri: Uri, navController: NavController): Boolean {
        return try {
            when {
                uri.scheme == SCHEME && uri.host == HOST -> {
                    handlePipBoyUri(uri, navController)
                }
                uri.scheme == "https" || uri.scheme == "http" -> {
                    handleWebUri(uri, navController)
                }
                else -> false
            }
        } catch (e: Exception) {
            crashlyticsManager.logException(e)
            false
        }
    }

    /**
     * Handle Pip-Boy specific URIs
     */
    private fun handlePipBoyUri(uri: Uri, navController: NavController): Boolean {
        val path = uri.path ?: return false

        crashlyticsManager.logUserAction("deep_link_received: $path")

        return when {
            path.startsWith("/status") -> {
                handleStatusDeepLink(uri, navController)
            }
            path.startsWith("/inventory") -> {
                handleInventoryDeepLink(uri, navController)
            }
            path.startsWith("/data") -> {
                handleDataDeepLink(uri, navController)
            }
            path.startsWith("/map") -> {
                handleMapDeepLink(uri, navController)
            }
            path.startsWith("/radio") -> {
                handleRadioDeepLink(uri, navController)
            }
            path == "/settings" -> {
                navController.navigate("settings")
                true
            }
            path == "/profile" -> {
                navController.navigate("profile")
                true
            }
            else -> false
        }
    }

    /**
     * Handle status screen deep links
     */
    private fun handleStatusDeepLink(uri: Uri, navController: NavController): Boolean {
        val pathSegments = uri.pathSegments

        return when (pathSegments.size) {
            1 -> { // /status
                navController.navigate("status")
                true
            }
            2 -> { // /status/{section}
                val section = pathSegments[1]
                navController.navigate("status/$section")
                true
            }
            3 -> { // /status/{section}/{itemId}
                val section = pathSegments[1]
                val itemId = pathSegments[2]
                navController.navigate("status/$section/$itemId")
                true
            }
            else -> false
        }
    }

    /**
     * Handle inventory screen deep links
     */
    private fun handleInventoryDeepLink(uri: Uri, navController: NavController): Boolean {
        val pathSegments = uri.pathSegments

        return when (pathSegments.size) {
            1 -> { // /inventory
                navController.navigate("inventory")
                true
            }
            2 -> { // /inventory/{category}
                val category = pathSegments[1]
                navController.navigate("inventory/$category")
                true
            }
            else -> false
        }
    }

    /**
     * Handle data screen deep links
     */
    private fun handleDataDeepLink(uri: Uri, navController: NavController): Boolean {
        val pathSegments = uri.pathSegments

        return when (pathSegments.size) {
            1 -> { // /data
                navController.navigate("data")
                true
            }
            2 -> { // /data/{datasetId}
                val datasetId = pathSegments[1]
                navController.navigate("data/$datasetId")
                true
            }
            else -> false
        }
    }

    /**
     * Handle map screen deep links
     */
    private fun handleMapDeepLink(uri: Uri, navController: NavController): Boolean {
        val pathSegments = uri.pathSegments

        return when (pathSegments.size) {
            1 -> { // /map
                navController.navigate("map")
                true
            }
            3 -> { // /map/{latitude}/{longitude}
                try {
                    val latitude = pathSegments[1].toFloat()
                    val longitude = pathSegments[2].toFloat()
                    navController.navigate("map/$latitude/$longitude")
                    true
                } catch (e: NumberFormatException) {
                    crashlyticsManager.logException(e)
                    false
                }
            }
            else -> false
        }
    }

    /**
     * Handle radio screen deep links
     */
    private fun handleRadioDeepLink(uri: Uri, navController: NavController): Boolean {
        val pathSegments = uri.pathSegments

        return when (pathSegments.size) {
            1 -> { // /radio
                navController.navigate("radio")
                true
            }
            2 -> { // /radio/{stationId}
                val stationId = pathSegments[1]
                navController.navigate("radio/$stationId")
                true
            }
            else -> false
        }
    }

    /**
     * Handle web URIs (for web-to-app navigation)
     */
    private fun handleWebUri(uri: Uri, navController: NavController): Boolean {
        return when (uri.host) {
            "pipboy.app" -> {
                handlePipBoyWebUri(uri, navController)
            }
            "play.google.com" -> {
                // Handle Play Store links
                handlePlayStoreUri(uri, navController)
            }
            else -> false
        }
    }

    /**
     * Handle Pip-Boy web URIs
     */
    private fun handlePipBoyWebUri(uri: Uri, navController: NavController): Boolean {
        val path = uri.path ?: return false

        return when {
            path.startsWith("/status") -> {
                navController.navigate("status")
                true
            }
            path.startsWith("/inventory") -> {
                navController.navigate("inventory")
                true
            }
            path.startsWith("/data") -> {
                navController.navigate("data")
                true
            }
            path.startsWith("/map") -> {
                navController.navigate("map")
                true
            }
            path.startsWith("/radio") -> {
                navController.navigate("radio")
                true
            }
            else -> false
        }
    }

    /**
     * Handle Play Store URIs
     */
    private fun handlePlayStoreUri(uri: Uri, navController: NavController): Boolean {
        // Extract app package information and navigate to appropriate screen
        val packageName = extractPackageName(uri)
        return if (packageName == context.packageName) {
            navController.navigate("status")
            true
        } else {
            false
        }
    }

    /**
     * Extract package name from Play Store URI
     */
    private fun extractPackageName(uri: Uri): String? {
        val path = uri.path ?: return null
        val regex = """/store/apps/details\?id=([^&]+)""".toRegex()
        return regex.find(path)?.groupValues?.get(1)
    }

    /**
     * Create deep link intent for external sharing
     */
    fun createDeepLinkIntent(screen: String, vararg params: Pair<String, String>): Intent {
        val uriBuilder = Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)

        when (screen) {
            "status" -> {
                uriBuilder.path("status")
                params.forEach { (key, value) ->
                    when (key) {
                        "section" -> uriBuilder.appendPath(value)
                        "itemId" -> uriBuilder.appendPath(value)
                    }
                }
            }
            "inventory" -> {
                uriBuilder.path("inventory")
                params.forEach { (key, value) ->
                    if (key == "category") uriBuilder.appendPath(value)
                }
            }
            "data" -> {
                uriBuilder.path("data")
                params.forEach { (key, value) ->
                    if (key == "datasetId") uriBuilder.appendPath(value)
                }
            }
            "map" -> {
                uriBuilder.path("map")
                params.forEach { (key, value) ->
                    when (key) {
                        "latitude" -> uriBuilder.appendPath(value)
                        "longitude" -> uriBuilder.appendPath(value)
                    }
                }
            }
            "radio" -> {
                uriBuilder.path("radio")
                params.forEach { (key, value) ->
                    if (key == "stationId") uriBuilder.appendPath(value)
                }
            }
            else -> uriBuilder.path(screen)
        }

        return Intent(Intent.ACTION_VIEW).apply {
            data = uriBuilder.build()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
    }

    /**
     * Check if URI is a valid Pip-Boy deep link
     */
    fun isPipBoyDeepLink(uri: Uri): Boolean {
        return uri.scheme == SCHEME && uri.host == HOST
    }

    /**
     * Get screen destination from URI
     */
    fun getDestinationFromUri(uri: Uri): String? {
        if (!isPipBoyDeepLink(uri)) return null

        return when (uri.path?.split("/")?.getOrNull(1)) {
            "status" -> "status"
            "inventory" -> "inventory"
            "data" -> "data"
            "map" -> "map"
            "radio" -> "radio"
            "settings" -> "settings"
            "profile" -> "profile"
            else -> null
        }
    }
}
