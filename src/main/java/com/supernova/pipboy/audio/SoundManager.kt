package com.supernova.pipboy.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized sound effects manager for the Pip-Boy interface.
 * Uses SoundPool for low-latency playback of short sound effects.
 * 
 * Features:
 * - Preloads all sound effects for instant playback
 * - Global volume control
 * - Per-sound volume adjustment
 * - Enable/disable sounds globally
 * - Sound playback state tracking
 */
@Singleton
class SoundManager @Inject constructor(
    private val context: Context
) {
    private companion object {
        const val TAG = "SoundManager"
        const val MAX_STREAMS = 10 // Maximum simultaneous sounds
        const val PREFS_KEY_ENABLED = "sound_effects_enabled"
        const val PREFS_KEY_VOLUME = "sound_effects_volume"
        const val DEFAULT_VOLUME = 0.7f
    }
    
    // SoundPool for low-latency playback
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(MAX_STREAMS)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()
    
    // Map of SoundEffect to SoundPool sound IDs
    private val soundIds = mutableMapOf<SoundEffect, Int>()
    
    // Map of currently playing sound stream IDs
    private val playingStreams = mutableSetOf<Int>()
    
    // Sound settings
    private val prefs = context.getSharedPreferences("sound_settings", Context.MODE_PRIVATE)
    
    private val _isEnabled = MutableStateFlow(prefs.getBoolean(PREFS_KEY_ENABLED, true))
    val isEnabled: StateFlow<Boolean> = _isEnabled.asStateFlow()
    
    private val _masterVolume = MutableStateFlow(prefs.getFloat(PREFS_KEY_VOLUME, DEFAULT_VOLUME))
    val masterVolume: StateFlow<Float> = _masterVolume.asStateFlow()
    
    // Playback statistics
    private val _soundPlayCount = MutableStateFlow<Map<SoundEffect, Int>>(emptyMap())
    val soundPlayCount: StateFlow<Map<SoundEffect, Int>> = _soundPlayCount.asStateFlow()
    
    init {
        preloadAllSounds()
    }
    
    /**
     * Preload all sound effects into memory for instant playback
     */
    private fun preloadAllSounds() {
        Log.d(TAG, "Preloading all sound effects...")
        
        SoundEffect.values().forEach { effect ->
            try {
                val soundId = soundPool.load(context, effect.resourceId, 1)
                soundIds[effect] = soundId
                Log.d(TAG, "Loaded sound: ${effect.name} (ID: $soundId)")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load sound: ${effect.name}", e)
            }
        }
        
        Log.d(TAG, "Preloaded ${soundIds.size} sound effects")
    }
    
    /**
     * Play a sound effect
     * 
     * @param effect The sound effect to play
     * @param volumeMultiplier Optional volume multiplier (0.0 to 1.0)
     * @param loop Whether to loop the sound (-1 for infinite, 0 for no loop)
     * @return Stream ID if playing, null if disabled or failed
     */
    fun play(
        effect: SoundEffect,
        volumeMultiplier: Float = 1.0f,
        loop: Int = 0
    ): Int? {
        if (!_isEnabled.value) {
            return null
        }
        
        val soundId = soundIds[effect]
        if (soundId == null) {
            Log.w(TAG, "Sound not loaded: ${effect.name}")
            return null
        }
        
        // Calculate final volume
        val effectVolume = effect.defaultVolume * volumeMultiplier * _masterVolume.value
        val clampedVolume = effectVolume.coerceIn(0f, 1f)
        
        return try {
            val streamId = soundPool.play(
                soundId,
                clampedVolume, // Left volume
                clampedVolume, // Right volume
                1, // Priority
                loop, // Loop mode
                1.0f // Playback rate
            )
            
            if (streamId != 0) {
                playingStreams.add(streamId)
                incrementPlayCount(effect)
                Log.d(TAG, "Playing sound: ${effect.name} (stream: $streamId, volume: $clampedVolume)")
            }
            
            streamId
        } catch (e: Exception) {
            Log.e(TAG, "Failed to play sound: ${effect.name}", e)
            null
        }
    }
    
    /**
     * Play a button click sound (convenience method)
     */
    fun playButtonClick() = play(SoundEffect.BUTTON_CLICK)
    
    /**
     * Play a page turn sound (convenience method)
     */
    fun playPageTurn() = play(SoundEffect.PAGE_TURN)
    
    /**
     * Play a terminal type sound (convenience method)
     */
    fun playTerminalType() = play(SoundEffect.TERMINAL_TYPE)
    
    /**
     * Play a quest complete sound (convenience method)
     */
    fun playQuestComplete() = play(SoundEffect.QUEST_COMPLETE)
    
    /**
     * Play a level up sound (convenience method)
     */
    fun playLevelUp() = play(SoundEffect.LEVEL_UP)
    
    /**
     * Play an error beep (convenience method)
     */
    fun playErrorBeep() = play(SoundEffect.ERROR_BEEP)
    
    /**
     * Play a success beep (convenience method)
     */
    fun playSuccessBeep() = play(SoundEffect.SUCCESS_BEEP)
    
    /**
     * Stop a specific stream
     */
    fun stop(streamId: Int) {
        soundPool.stop(streamId)
        playingStreams.remove(streamId)
    }
    
    /**
     * Stop all currently playing sounds
     */
    fun stopAll() {
        playingStreams.forEach { streamId ->
            soundPool.stop(streamId)
        }
        playingStreams.clear()
    }
    
    /**
     * Pause a specific stream
     */
    fun pause(streamId: Int) {
        soundPool.pause(streamId)
    }
    
    /**
     * Resume a specific stream
     */
    fun resume(streamId: Int) {
        soundPool.resume(streamId)
    }
    
    /**
     * Enable or disable all sound effects
     */
    fun setEnabled(enabled: Boolean) {
        _isEnabled.value = enabled
        prefs.edit().putBoolean(PREFS_KEY_ENABLED, enabled).apply()
        
        if (!enabled) {
            stopAll()
        }
        
        Log.d(TAG, "Sound effects ${if (enabled) "enabled" else "disabled"}")
    }
    
    /**
     * Set master volume (0.0 to 1.0)
     */
    fun setMasterVolume(volume: Float) {
        val clampedVolume = volume.coerceIn(0f, 1f)
        _masterVolume.value = clampedVolume
        prefs.edit().putFloat(PREFS_KEY_VOLUME, clampedVolume).apply()
        
        Log.d(TAG, "Master volume set to: $clampedVolume")
    }
    
    /**
     * Increment play count for statistics
     */
    private fun incrementPlayCount(effect: SoundEffect) {
        val currentCounts = _soundPlayCount.value.toMutableMap()
        currentCounts[effect] = (currentCounts[effect] ?: 0) + 1
        _soundPlayCount.value = currentCounts
    }
    
    /**
     * Get total number of sounds played
     */
    fun getTotalSoundsPlayed(): Int {
        return _soundPlayCount.value.values.sum()
    }
    
    /**
     * Get most played sound effect
     */
    fun getMostPlayedSound(): Pair<SoundEffect, Int>? {
        return _soundPlayCount.value.maxByOrNull { it.value }?.toPair()
    }
    
    /**
     * Reset play count statistics
     */
    fun resetStatistics() {
        _soundPlayCount.value = emptyMap()
    }
    
    /**
     * Release all resources
     * Call this when the app is destroyed
     */
    fun release() {
        stopAll()
        soundPool.release()
        soundIds.clear()
        Log.d(TAG, "SoundManager released")
    }
}

