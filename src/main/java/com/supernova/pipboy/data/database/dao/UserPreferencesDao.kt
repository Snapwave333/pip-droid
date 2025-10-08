package com.supernova.pipboy.data.database.dao

import androidx.room.*
import androidx.room.Query
import com.supernova.pipboy.data.database.entities.PreferenceType
import com.supernova.pipboy.data.database.entities.UserPreferencesEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for user preferences database operations
 */
@Dao
interface UserPreferencesDao {

    // Insert operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreference(preference: UserPreferencesEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreferences(preferences: List<UserPreferencesEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPreferenceIfNotExists(preference: UserPreferencesEntity): Long

    // Update operations
    @Update
    suspend fun updatePreference(preference: UserPreferencesEntity)

    @Query("UPDATE user_preferences SET preference_value = :value, updated_at = :timestamp WHERE preference_key = :key")
    suspend fun updatePreferenceValue(key: String, value: String, timestamp: Long)

    // Delete operations
    @Delete
    suspend fun deletePreference(preference: UserPreferencesEntity)

    @Query("DELETE FROM user_preferences WHERE preference_key = :key")
    suspend fun deletePreferenceByKey(key: String)

    @Query("DELETE FROM user_preferences WHERE preference_type = :type")
    suspend fun deletePreferencesByType(type: PreferenceType)

    @Query("DELETE FROM user_preferences")
    suspend fun deleteAllPreferences()

    // Query operations
    @Query("SELECT * FROM user_preferences WHERE preference_key = :key")
    suspend fun getPreferenceByKey(key: String): UserPreferencesEntity?

    @Query("SELECT * FROM user_preferences WHERE id = :id")
    suspend fun getPreferenceById(id: Long): UserPreferencesEntity?

    @Query("SELECT * FROM user_preferences")
    suspend fun getAllPreferences(): List<UserPreferencesEntity>

    @Query("SELECT * FROM user_preferences")
    fun observeAllPreferences(): Flow<List<UserPreferencesEntity>>

    @Query("SELECT * FROM user_preferences WHERE preference_type = :type")
    suspend fun getPreferencesByType(type: PreferenceType): List<UserPreferencesEntity>

    @Query("SELECT * FROM user_preferences WHERE preference_type = :type")
    fun observePreferencesByType(type: PreferenceType): Flow<List<UserPreferencesEntity>>

    @Query("SELECT preference_value FROM user_preferences WHERE preference_key = :key")
    suspend fun getPreferenceValue(key: String): String?

    @Query("SELECT preference_value FROM user_preferences WHERE preference_key = :key")
    fun observePreferenceValue(key: String): Flow<String?>

    @Query("SELECT COUNT(*) FROM user_preferences")
    suspend fun getPreferenceCount(): Int

    @Query("SELECT COUNT(*) FROM user_preferences WHERE preference_type = :type")
    suspend fun getPreferenceCountByType(type: PreferenceType): Int

    // Type-specific getters
    @Query("SELECT preference_value FROM user_preferences WHERE preference_key = :key")
    suspend fun getStringPreference(key: String): String?

    @Query("SELECT CAST(preference_value AS INTEGER) FROM user_preferences WHERE preference_key = :key")
    suspend fun getIntPreference(key: String): Int?

    @Query("SELECT CASE WHEN CAST(preference_value AS INTEGER) = 1 THEN 1 ELSE 0 END FROM user_preferences WHERE preference_key = :key")
    suspend fun getBooleanPreference(key: String): Boolean?

    @Query("SELECT CAST(preference_value AS REAL) FROM user_preferences WHERE preference_key = :key")
    suspend fun getFloatPreference(key: String): Float?

    @Query("SELECT CAST(preference_value AS INTEGER) FROM user_preferences WHERE preference_key = :key")
    suspend fun getLongPreference(key: String): Long?

    // Batch operations
    @Query("SELECT preference_value FROM user_preferences WHERE preference_key IN (:keys)")
    suspend fun getPreferenceValues(keys: List<String>): List<String>

    @Query("SELECT * FROM user_preferences WHERE preference_key IN (:keys)")
    suspend fun getPreferencesByKeys(keys: List<String>): List<UserPreferencesEntity>

    // Preference existence checks
    @Query("SELECT EXISTS(SELECT 1 FROM user_preferences WHERE preference_key = :key)")
    suspend fun preferenceExists(key: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM user_preferences WHERE preference_key = :key AND preference_type = :type)")
    suspend fun preferenceExists(key: String, type: PreferenceType): Boolean

    // Preference management helpers
    @Query("SELECT preference_key FROM user_preferences WHERE preference_type = :type")
    suspend fun getPreferenceKeysByType(type: PreferenceType): List<String>

    @Query("SELECT DISTINCT preference_type FROM user_preferences")
    suspend fun getAllPreferenceTypes(): List<PreferenceType>

    // Cleanup operations
    @Query("DELETE FROM user_preferences WHERE updated_at < :timestamp")
    suspend fun deleteOldPreferences(timestamp: Long)

    @Query("SELECT MIN(updated_at) FROM user_preferences")
    suspend fun getOldestPreferenceTimestamp(): Long?

    @Query("SELECT MAX(updated_at) FROM user_preferences")
    suspend fun getNewestPreferenceTimestamp(): Long?

    // Atomic operations for common preference updates
    @Query("INSERT OR REPLACE INTO user_preferences (preference_key, preference_value, preference_type, updated_at) VALUES (:key, :value, :type, :timestamp)")
    suspend fun upsertPreference(key: String, value: String, type: PreferenceType, timestamp: Long)

    @Query("INSERT OR REPLACE INTO user_preferences (preference_key, preference_value, preference_type, updated_at) VALUES (:key, :value, :type, :timestamp)")
    suspend fun upsertStringPreference(key: String, value: String, timestamp: Long)

    @Query("INSERT OR REPLACE INTO user_preferences (preference_key, preference_value, preference_type, updated_at) VALUES (:key, :value, :type, :timestamp)")
    suspend fun upsertIntPreference(key: String, value: Int, timestamp: Long)

    @Query("INSERT OR REPLACE INTO user_preferences (preference_key, preference_value, preference_type, updated_at) VALUES (:key, :value, :type, :timestamp)")
    suspend fun upsertBooleanPreference(key: String, value: Boolean, timestamp: Long)

    @Query("INSERT OR REPLACE INTO user_preferences (preference_key, preference_value, preference_type, updated_at) VALUES (:key, :value, :type, :timestamp)")
    suspend fun upsertFloatPreference(key: String, value: Float, timestamp: Long)

    @Query("INSERT OR REPLACE INTO user_preferences (preference_key, preference_value, preference_type, updated_at) VALUES (:key, :value, :type, :timestamp)")
    suspend fun upsertLongPreference(key: String, value: Long, timestamp: Long)

    // Transaction helpers
    @Query("SELECT * FROM user_preferences WHERE preference_key = :key")
    suspend fun getPreferenceForUpdate(key: String): UserPreferencesEntity?

    @Query("UPDATE user_preferences SET preference_value = :newValue, updated_at = :timestamp WHERE preference_key = :key AND preference_value = :oldValue")
    suspend fun compareAndSwapPreference(key: String, oldValue: String, newValue: String, timestamp: Long): Int
}
