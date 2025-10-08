package com.supernova.pipboy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index
import com.supernova.pipboy.data.model.AppCategory

/**
 * Entity representing an installed application
 * Stored locally for caching and offline access
 */
@Entity(
    tableName = "apps",
    indices = [
        Index(value = ["package_name"], unique = true),
        Index(value = ["category"]),
        Index(value = ["is_favorite"])
    ]
)
data class AppEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "package_name")
    val packageName: String,

    @ColumnInfo(name = "category")
    val category: AppCategory,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "install_time")
    val installTime: Long,

    @ColumnInfo(name = "last_used_time")
    val lastUsedTime: Long = 0,

    @ColumnInfo(name = "usage_count")
    val usageCount: Int = 0,

    @ColumnInfo(name = "icon_path")
    val iconPath: String? = null,

    @ColumnInfo(name = "version_name")
    val versionName: String,

    @ColumnInfo(name = "version_code")
    val versionCode: Int,

    @ColumnInfo(name = "is_system_app")
    val isSystemApp: Boolean = false,

    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
