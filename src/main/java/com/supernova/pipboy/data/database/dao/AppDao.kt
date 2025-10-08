package com.supernova.pipboy.data.database.dao

import androidx.room.*
import androidx.room.Query
import com.supernova.pipboy.data.database.entities.AppEntity
import com.supernova.pipboy.data.model.AppCategory
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for app-related database operations
 */
@Dao
interface AppDao {

    // Insert operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: AppEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApps(apps: List<AppEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAppIfNotExists(app: AppEntity): Long

    // Update operations
    @Update
    suspend fun updateApp(app: AppEntity)

    @Query("UPDATE apps SET is_favorite = :isFavorite, updated_at = :timestamp WHERE package_name = :packageName")
    suspend fun updateFavoriteStatus(packageName: String, isFavorite: Boolean, timestamp: Long)

    @Query("UPDATE apps SET last_used_time = :timestamp, usage_count = usage_count + 1 WHERE package_name = :packageName")
    suspend fun updateAppUsage(packageName: String, timestamp: Long)

    @Query("UPDATE apps SET is_enabled = :isEnabled, updated_at = :timestamp WHERE package_name = :packageName")
    suspend fun updateAppEnabledStatus(packageName: String, isEnabled: Boolean, timestamp: Long)

    // Delete operations
    @Delete
    suspend fun deleteApp(app: AppEntity)

    @Query("DELETE FROM apps WHERE package_name = :packageName")
    suspend fun deleteAppByPackageName(packageName: String)

    @Query("DELETE FROM apps WHERE is_system_app = 0")
    suspend fun deleteAllUserApps()

    @Query("DELETE FROM apps")
    suspend fun deleteAllApps()

    // Query operations
    @Query("SELECT * FROM apps WHERE package_name = :packageName")
    suspend fun getAppByPackageName(packageName: String): AppEntity?

    @Query("SELECT * FROM apps WHERE id = :id")
    suspend fun getAppById(id: Long): AppEntity?

    @Query("SELECT * FROM apps")
    suspend fun getAllApps(): List<AppEntity>

    @Query("SELECT * FROM apps")
    fun observeAllApps(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE is_favorite = 1")
    suspend fun getFavoriteApps(): List<AppEntity>

    @Query("SELECT * FROM apps WHERE is_favorite = 1")
    fun observeFavoriteApps(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE category = :category")
    suspend fun getAppsByCategory(category: AppCategory): List<AppEntity>

    @Query("SELECT * FROM apps WHERE category = :category")
    fun observeAppsByCategory(category: AppCategory): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE is_system_app = :isSystemApp")
    suspend fun getSystemApps(isSystemApp: Boolean): List<AppEntity>

    @Query("SELECT * FROM apps WHERE is_enabled = :isEnabled")
    suspend fun getEnabledApps(isEnabled: Boolean): List<AppEntity>

    @Query("SELECT * FROM apps WHERE name LIKE '%' || :query || '%' OR package_name LIKE '%' || :query || '%'")
    suspend fun searchApps(query: String): List<AppEntity>

    @Query("SELECT * FROM apps WHERE name LIKE '%' || :query || '%' OR package_name LIKE '%' || :query || '%'")
    fun observeSearchResults(query: String): Flow<List<AppEntity>>

    @Query("SELECT COUNT(*) FROM apps")
    suspend fun getAppCount(): Int

    @Query("SELECT COUNT(*) FROM apps WHERE is_favorite = 1")
    suspend fun getFavoriteAppCount(): Int

    @Query("SELECT COUNT(*) FROM apps WHERE category = :category")
    suspend fun getAppCountByCategory(category: AppCategory): Int

    @Query("SELECT COUNT(*) FROM apps WHERE is_system_app = 0")
    suspend fun getUserAppCount(): Int

    @Query("SELECT COUNT(*) FROM apps WHERE is_system_app = 1")
    suspend fun getSystemAppCount(): Int

    // Complex queries
    @Query("SELECT * FROM apps WHERE is_favorite = 1 ORDER BY last_used_time DESC LIMIT :limit")
    suspend fun getRecentlyUsedFavoriteApps(limit: Int): List<AppEntity>

    @Query("SELECT * FROM apps WHERE usage_count > 0 ORDER BY usage_count DESC, last_used_time DESC LIMIT :limit")
    suspend fun getMostUsedApps(limit: Int): List<AppEntity>

    @Query("SELECT * FROM apps WHERE last_used_time > :since ORDER BY last_used_time DESC")
    suspend fun getRecentlyUsedApps(since: Long): List<AppEntity>

    @Query("SELECT DISTINCT category FROM apps WHERE is_enabled = 1")
    suspend fun getAvailableCategories(): List<AppCategory>

    // Batch operations
    @Query("UPDATE apps SET is_favorite = :isFavorite WHERE package_name IN (:packageNames)")
    suspend fun updateFavoriteStatusBatch(packageNames: List<String>, isFavorite: Boolean)

    @Query("DELETE FROM apps WHERE package_name IN (:packageNames)")
    suspend fun deleteAppsByPackageNames(packageNames: List<String>)

    // Statistics queries
    @Query("SELECT category, COUNT(*) as count FROM apps WHERE is_enabled = 1 GROUP BY category ORDER BY count DESC")
    suspend fun getCategoryStatistics(): List<CategoryStatistics>

    @Query("SELECT AVG(usage_count) FROM apps WHERE usage_count > 0")
    suspend fun getAverageUsageCount(): Double

    @Query("SELECT MAX(last_used_time) FROM apps")
    suspend fun getLastAppUsageTime(): Long?

    // Maintenance queries
    @Query("DELETE FROM apps WHERE updated_at < :timestamp")
    suspend fun deleteOldApps(timestamp: Long)

    @Query("UPDATE apps SET updated_at = :timestamp WHERE package_name = :packageName")
    suspend fun touchApp(packageName: String, timestamp: Long)
}

/**
 * Statistics data class for category counts
 */
data class CategoryStatistics(
    val category: AppCategory,
    val count: Int
)
