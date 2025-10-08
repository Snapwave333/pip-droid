package com.supernova.pipboy.dependencyinjection

import android.content.Context
import com.supernova.pipboy.services.MediaControlService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for service dependencies
 * Provides system service instances
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideMediaControlService(@ApplicationContext context: Context): MediaControlService {
        return MediaControlService()
    }
}
