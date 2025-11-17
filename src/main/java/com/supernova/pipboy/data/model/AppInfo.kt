package com.supernova.pipboy.data.model

import android.graphics.drawable.Drawable

/**
 * Data class representing application information
 */
data class AppInfo(
    val name: String,
    val packageName: String,
    val category: AppCategory,
    val icon: Drawable? = null,
    val isFavorite: Boolean = false,
    val installTime: Long = 0,
    val lastUsedTime: Long = 0,
    val usageCount: Int = 0,
    val versionName: String = "",
    val versionCode: Int = 0,
    val isSystemApp: Boolean = false,
    val isEnabled: Boolean = true
)
