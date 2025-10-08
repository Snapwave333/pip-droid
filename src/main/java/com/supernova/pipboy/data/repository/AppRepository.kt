package com.supernova.pipboy.data.repository

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.supernova.pipboy.data.model.AppInfo
import com.supernova.pipboy.data.model.AppCategory
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Repository for managing installed applications
 */
class AppRepository(
    private val context: Context,
    private val preferences: PipBoyPreferences? = null
) {

    private val _allApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val allApps: StateFlow<List<AppInfo>> = _allApps

    private val _favoriteApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val favoriteApps: StateFlow<List<AppInfo>> = _favoriteApps

    private val _categorizedApps = MutableStateFlow<Map<AppCategory, List<AppInfo>>>(emptyMap())
    val categorizedApps: StateFlow<Map<AppCategory, List<AppInfo>>> = _categorizedApps

    private val packageManager = context.packageManager
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        refreshAppList()
    }

    /**
     * Refresh the complete app list from system
     */
    fun refreshAppList() {
        coroutineScope.launch {
            val apps = loadInstalledApps()
            _allApps.value = apps

            val favorites = apps.filter { app ->
                preferences?.favoriteApps?.contains(app.packageName) == true
            }
            _favoriteApps.value = favorites

            val categorized = categorizeApps(apps)
            _categorizedApps.value = categorized
        }
    }

    /**
     * Toggle favorite status for an app
     */
    suspend fun toggleFavoriteApp(packageName: String) {
        withContext(Dispatchers.IO) {
            val currentFavorites = preferences?.favoriteApps ?: emptySet()
            val newFavorites = if (currentFavorites.contains(packageName)) {
                currentFavorites - packageName
            } else {
                currentFavorites + packageName
            }

            preferences?.favoriteApps = newFavorites
            refreshAppList() // Refresh to update the flow
        }
    }

    /**
     * Launch an application
     */
    suspend fun launchApp(packageName: String) {
        withContext(Dispatchers.IO) {
            try {
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                if (intent != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    ContextCompat.startActivity(context, intent, null)
                }
            } catch (e: Exception) {
                // Handle launch failure gracefully
            }
        }
    }

    /**
     * Get monochrome version of app icon
     */
    fun getMonochromeIcon(drawable: Drawable, color: PipBoyColor): Drawable {
        // In a real implementation, this would convert the drawable to monochrome
        // using the specified color. For now, return the original drawable.
        return drawable
    }

    private suspend fun loadInstalledApps(): List<AppInfo> {
        return withContext(Dispatchers.IO) {
            val apps = mutableListOf<AppInfo>()

            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            val resolveInfos = packageManager.queryIntentActivities(intent, 0)

            for (resolveInfo in resolveInfos) {
                try {
                    val appInfo = resolveInfo.activityInfo.applicationInfo
                    val appName = appInfo.loadLabel(packageManager).toString()
                    val packageName = appInfo.packageName
                    val icon = appInfo.loadIcon(packageManager)

                    // Skip system apps unless they're important system apps
                    if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                        if (isImportantSystemApp(packageName)) {
                            apps.add(AppInfo(appName, packageName, AppCategory.HEALTH))
                        }
                    } else {
                        // User app - categorize it
                        val category = categorizeApp(packageName, appName)
                        apps.add(AppInfo(appName, packageName, category))
                    }
                } catch (e: Exception) {
                    // Skip apps that can't be loaded
                }
            }

            apps.sortBy { it.name.lowercase() }
            apps
        }
    }

    private fun categorizeApps(apps: List<AppInfo>): Map<AppCategory, List<AppInfo>> {
        return apps.groupBy { it.category }
    }

    private fun categorizeApp(packageName: String, appName: String): AppCategory {
        val nameLower = appName.lowercase()

        // WEAPONS category - high performance/gaming apps
        if (nameLower.contains("game") ||
            nameLower.contains("gaming") ||
            packageName.contains("game") ||
            nameLower.contains("steam") ||
            nameLower.contains("epic") ||
            nameLower.contains("battle") ||
            nameLower.contains("call of duty") ||
            nameLower.contains("pubg") ||
            nameLower.contains("fortnite")) {
            return AppCategory.GAME
        }

        // AID category - health, calculator, utility apps
        if (nameLower.contains("calculator") ||
            nameLower.contains("health") ||
            nameLower.contains("fitness") ||
            nameLower.contains("medical") ||
            nameLower.contains("clock") ||
            nameLower.contains("alarm") ||
            nameLower.contains("weather") ||
            nameLower.contains("math") ||
            nameLower.contains("unit converter")) {
            return AppCategory.UTILITIES
        }

        // Default to MISC
        return AppCategory.OTHER
    }

    private fun isImportantSystemApp(packageName: String): Boolean {
        return packageName == "com.android.settings" ||
               packageName == "com.android.phone" ||
               packageName == "com.android.contacts" ||
               packageName == "com.android.messaging" ||
               packageName.startsWith("com.android.systemui")
    }
}

