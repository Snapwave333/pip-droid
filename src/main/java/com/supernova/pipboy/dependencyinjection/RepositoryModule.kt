package com.supernova.pipboy.dependencyinjection

import android.content.Context
import com.supernova.pipboy.data.database.dao.AppDao
import com.supernova.pipboy.data.database.dao.SystemStatusDao
import com.supernova.pipboy.data.database.dao.UserPreferencesDao
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import com.supernova.pipboy.data.repository.AppRepository
import com.supernova.pipboy.data.repository.SystemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/**
 * Hilt module for repository dependencies
 * Provides data repository instances with their dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSystemRepository(
        @ApplicationContext context: Context
    ): SystemRepository {
        return SystemRepository(context)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        @ApplicationContext context: Context,
        preferences: PipBoyPreferences
    ): AppRepository {
        return AppRepository(context, preferences)
    }
}
