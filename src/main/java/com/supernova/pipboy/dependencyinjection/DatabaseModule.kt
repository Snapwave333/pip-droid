package com.supernova.pipboy.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.supernova.pipboy.data.database.PipBoyDatabase
import com.supernova.pipboy.data.database.dao.AppDao
import com.supernova.pipboy.data.database.dao.SystemStatusDao
import com.supernova.pipboy.data.database.dao.UserPreferencesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * Hilt module for database-related dependencies
 * Provides Room database and DAO instances
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePipBoyDatabase(@ApplicationContext context: Context): PipBoyDatabase {
        return Room.databaseBuilder(
            context,
            PipBoyDatabase::class.java,
            PipBoyDatabase.DATABASE_NAME
        )
        .addCallback(object : androidx.room.RoomDatabase.Callback() {
            override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onCreate(db)
                // Database initialization logic
            }

            override fun onOpen(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onOpen(db)
                // Database opened
            }
        })
        .addMigrations() // Add migrations when available
        .fallbackToDestructiveMigration() // For development only
        .build()
    }

    @Provides
    @Singleton
    fun provideAppDao(database: PipBoyDatabase): AppDao {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideSystemStatusDao(database: PipBoyDatabase): SystemStatusDao {
        return database.systemStatusDao()
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDao(database: PipBoyDatabase): UserPreferencesDao {
        return database.userPreferencesDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
