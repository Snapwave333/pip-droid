package com.supernova.pipboy.dependencyinjection

import android.content.Context
import androidx.lifecycle.ViewModel
import com.supernova.pipboy.data.repository.AppRepository
import com.supernova.pipboy.data.repository.SystemRepository
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for ViewModel dependencies
 * Provides ViewModel instances with their required dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMainViewModel(
        systemRepository: SystemRepository,
        appRepository: AppRepository,
        preferences: com.supernova.pipboy.data.preferences.PipBoyPreferences,
        @ApplicationContext context: Context
    ): MainViewModel {
        return MainViewModel(systemRepository, appRepository, preferences, context)
    }
}
