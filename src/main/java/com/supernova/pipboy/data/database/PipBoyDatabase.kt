package com.supernova.pipboy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.supernova.pipboy.data.database.dao.AppDao
import com.supernova.pipboy.data.database.dao.SystemStatusDao
import com.supernova.pipboy.data.database.dao.UserPreferencesDao
import com.supernova.pipboy.data.database.entities.AppEntity
import com.supernova.pipboy.data.database.entities.SystemStatusEntity
import com.supernova.pipboy.data.database.entities.UserPreferencesEntity
import com.supernova.pipboy.data.database.typeconverters.PipBoyTypeConverters

/**
 * Room database for Pip-Boy application
 * Provides local persistence for apps, system status, and user preferences
 */
@Database(
    entities = [
        AppEntity::class,
        SystemStatusEntity::class,
        UserPreferencesEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(PipBoyTypeConverters::class)
abstract class PipBoyDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
    abstract fun systemStatusDao(): SystemStatusDao
    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        const val DATABASE_NAME = "pipboy_database"

        @Volatile
        private var INSTANCE: PipBoyDatabase? = null

        /**
         * Get database instance with singleton pattern
         */
        fun getInstance(context: Context): PipBoyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        /**
         * Build database with proper configuration
         */
        private fun buildDatabase(context: Context): PipBoyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PipBoyDatabase::class.java,
                DATABASE_NAME
            )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Database initialization if needed
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    // Database opened
                }
            })
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration() // For development only
            .build()
        }

        /**
         * Migration from version 1 to 2
         * Add this when you need to modify the database schema
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example migration - uncomment and modify when needed
                // database.execSQL("ALTER TABLE app_entity ADD COLUMN new_column TEXT")
            }
        }
    }
}
