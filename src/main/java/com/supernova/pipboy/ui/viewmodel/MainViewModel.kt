package com.supernova.pipboy.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import com.supernova.pipboy.data.repository.AppRepository
import com.supernova.pipboy.data.repository.SystemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel(
    private val systemRepository: SystemRepository,
    private val appRepository: AppRepository,
    private val preferences: PipBoyPreferences,
    private val context: Context
) : ViewModel() {

    // Current tab selection
    private val _currentTab = MutableStateFlow(PipBoyTab.STATUS)
    val currentTab: StateFlow<PipBoyTab> = _currentTab

    // Primary color state
    private val _primaryColor = MutableStateFlow(preferences.primaryColor)
    val primaryColor: StateFlow<PipBoyColor> = _primaryColor

    // CRT effects state
    private val _crtEffects = MutableStateFlow(
        CRTEffects(
            scanlines = preferences.crtScanlinesEnabled,
            flicker = preferences.crtFlickerEnabled,
            distortion = preferences.crtDistortionEnabled
        )
    )
    val crtEffects: StateFlow<CRTEffects> = _crtEffects

    // System status
    val systemStatus = systemRepository.systemStatus

    // App lists
    val allApps = appRepository.allApps
    val favoriteApps = appRepository.favoriteApps
    val categorizedApps = appRepository.categorizedApps

    // Media status
    val mediaStatus = systemRepository.mediaStatus

    // Notes
    private val _notes = MutableStateFlow(preferences.notes)
    val notes: StateFlow<String> = _notes

    init {
        // Listen for preference changes
        viewModelScope.launch {
            // This would be implemented with proper flow observation
            // For now, we'll handle updates through explicit methods
        }
    }

    /**
     * Update the current tab
     */
    fun selectTab(tab: PipBoyTab) {
        _currentTab.value = tab
    }

    /**
     * Update primary color
     */
    fun updatePrimaryColor(color: PipBoyColor) {
        _primaryColor.value = color
        preferences.primaryColor = color
        
        // Track settings change for achievement
        trackSettingsChange()
    }

    /**
     * Update CRT effects
     */
    fun updateCRTEffects(effects: CRTEffects) {
        _crtEffects.value = effects
        preferences.crtScanlinesEnabled = effects.scanlines
        preferences.crtFlickerEnabled = effects.flicker
        preferences.crtDistortionEnabled = effects.distortion
        
        // Track settings change for achievement
        trackSettingsChange()
    }
    
    /**
     * Track settings changes for achievements
     */
    private fun trackSettingsChange() {
        try {
            val app = context.applicationContext as? PipBoyApplication
            app?.achievementManager?.trackEvent(AchievementEvent.SettingsChanged)
        } catch (e: Exception) {
            // Silently fail if app context not available
        }
    }

    /**
     * Update notes
     */
    fun updateNotes(newNotes: String) {
        _notes.value = newNotes
        preferences.notes = newNotes
    }

    /**
     * Add/remove favorite app
     */
    fun toggleFavoriteApp(packageName: String) {
        viewModelScope.launch {
            appRepository.toggleFavoriteApp(packageName)
        }
    }

    /**
     * Launch app
     */
    fun launchApp(packageName: String) {
        viewModelScope.launch {
            appRepository.launchApp(packageName)
        }
    }
}

/**
 * Available tabs in the Pip-Boy interface
 */
enum class PipBoyTab {
    STATUS, INVENTORY, DATA, MAP, RADIO, ACHIEVEMENTS, SETTINGS
}

/**
 * CRT effect configuration
 */
data class CRTEffects(
    val scanlines: Boolean = true,
    val flicker: Boolean = true,
    val distortion: Boolean = true
)
