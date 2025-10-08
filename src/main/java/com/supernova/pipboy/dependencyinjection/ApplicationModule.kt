package com.supernova.pipboy.dependencyinjection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Hilt module for application-level dependencies
 * Provides core application components and configurations
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun providePipBoyPreferences(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): PipBoyPreferences {
        return PipBoyPreferences(context)
    }

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree {
        return if (com.supernova.pipboy.BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // Production logging - could integrate with crash reporting
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideGson(): com.google.gson.Gson {
        return com.google.gson.Gson()
    }

    @Provides
    @Singleton
    fun provideJson(): kotlinx.serialization.json.Json {
        return kotlinx.serialization.json.Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }
}

/**
 * Qualifier for application scope
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

/**
 * Qualifier for IO dispatcher
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

/**
 * Qualifier for Main dispatcher
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

/**
 * Qualifier for Default dispatcher
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
