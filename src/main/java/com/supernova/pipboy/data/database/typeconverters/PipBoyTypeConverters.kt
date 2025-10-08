package com.supernova.pipboy.data.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.supernova.pipboy.data.model.AppCategory
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.database.entities.PreferenceType

/**
 * Type converters for Room database
 * Handles conversion between complex types and database-stored types
 */
class PipBoyTypeConverters {

    private val gson = Gson()

    // AppCategory converters
    @TypeConverter
    fun fromAppCategory(category: AppCategory): String {
        return category.name
    }

    @TypeConverter
    fun toAppCategory(value: String): AppCategory {
        return try {
            AppCategory.valueOf(value)
        } catch (e: IllegalArgumentException) {
            AppCategory.OTHER // Default fallback
        }
    }

    // PipBoyColor converters
    @TypeConverter
    fun fromPipBoyColor(color: PipBoyColor): String {
        return "${color.red},${color.green},${color.blue}"
    }

    @TypeConverter
    fun toPipBoyColor(value: String): PipBoyColor {
        return try {
            val parts = value.split(",")
            if (parts.size == 3) {
                PipBoyColor(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            } else {
                PipBoyColor.DEFAULT
            }
        } catch (e: Exception) {
            PipBoyColor.DEFAULT // Default fallback
        }
    }

    // PreferenceType converters
    @TypeConverter
    fun fromPreferenceType(type: PreferenceType): String {
        return type.name
    }

    @TypeConverter
    fun toPreferenceType(value: String): PreferenceType {
        return try {
            PreferenceType.valueOf(value)
        } catch (e: IllegalArgumentException) {
            PreferenceType.STRING // Default fallback
        }
    }

    // List<String> converters for favorite apps
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(value, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Set<String> converters for favorite apps
    @TypeConverter
    fun fromStringSet(set: Set<String>): String {
        return gson.toJson(set)
    }

    @TypeConverter
    fun toStringSet(value: String): Set<String> {
        return try {
            val type = object : TypeToken<Set<String>>() {}.type
            gson.fromJson(value, type) ?: emptySet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    // Map<String, Any> converters for complex settings
    @TypeConverter
    fun fromStringMap(map: Map<String, Any>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, Any> {
        return try {
            val type = object : TypeToken<Map<String, Any>>() {}.type
            gson.fromJson(value, type) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }

    // Boolean converters
    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value != 0
    }

    // Timestamp converters
    @TypeConverter
    fun fromTimestamp(value: Long?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toTimestamp(value: String?): Long? {
        return value?.toLongOrNull()
    }

    // Float converters
    @TypeConverter
    fun fromFloat(value: Float): String {
        return value.toString()
    }

    @TypeConverter
    fun toFloat(value: String): Float {
        return value.toFloatOrNull() ?: 0.0f
    }

    // Int converters
    @TypeConverter
    fun fromInt(value: Int): String {
        return value.toString()
    }

    @TypeConverter
    fun toInt(value: String): Int {
        return value.toIntOrNull() ?: 0
    }

    // Long converters
    @TypeConverter
    fun fromLong(value: Long): String {
        return value.toString()
    }

    @TypeConverter
    fun toLong(value: String): Long {
        return value.toLongOrNull() ?: 0L
    }
}
