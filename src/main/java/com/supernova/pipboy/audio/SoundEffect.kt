package com.supernova.pipboy.audio

import androidx.annotation.RawRes
import com.supernova.pipboy.R

/**
 * Enumeration of all sound effects in the Pip-Boy interface.
 * Each sound effect maps to a resource file in res/raw/.
 */
enum class SoundEffect(@RawRes val resourceId: Int, val defaultVolume: Float = 1.0f) {
    // UI Interaction Sounds
    BUTTON_CLICK(R.raw.button_click, 0.7f),
    BUTTON_HOVER(R.raw.button_hover, 0.4f),
    TAB_SWITCH(R.raw.tab_switch, 0.6f),
    PANEL_OPEN(R.raw.panel_open, 0.8f),
    PANEL_CLOSE(R.raw.panel_close, 0.8f),
    
    // Navigation Sounds
    PAGE_TURN(R.raw.page_turn, 0.7f),
    SCROLL(R.raw.scroll, 0.3f),
    BACK_BUTTON(R.raw.back_button, 0.7f),
    
    // Terminal Sounds
    TERMINAL_TYPE(R.raw.terminal_type, 0.5f),
    TERMINAL_ENTER(R.raw.terminal_enter, 0.8f),
    TERMINAL_ERROR(R.raw.terminal_error, 0.9f),
    TERMINAL_BOOT(R.raw.terminal_boot, 1.0f),
    
    // Quest/Achievement Sounds
    QUEST_ACCEPT(R.raw.quest_accept, 0.8f),
    QUEST_COMPLETE(R.raw.quest_complete, 1.0f),
    OBJECTIVE_COMPLETE(R.raw.objective_complete, 0.7f),
    ACHIEVEMENT_UNLOCK(R.raw.achievement_unlock, 1.0f),
    LEVEL_UP(R.raw.level_up, 1.0f),
    
    // Radio Sounds
    RADIO_STATIC(R.raw.radio_static, 0.6f),
    RADIO_ON(R.raw.radio_on, 0.8f),
    RADIO_OFF(R.raw.radio_off, 0.8f),
    STATION_SWITCH(R.raw.station_switch, 0.7f),
    
    // Ambient Sounds
    GEIGER_CLICK(R.raw.geiger_click, 0.4f),
    VAULT_HUM(R.raw.vault_hum, 0.3f),
    
    // System Sounds
    ERROR_BEEP(R.raw.error_beep, 0.9f),
    SUCCESS_BEEP(R.raw.success_beep, 0.8f),
    ALERT(R.raw.alert, 1.0f),
    SHUTDOWN(R.raw.shutdown, 1.0f);
    
    companion object {
        /**
         * Get sound effect by name (case-insensitive)
         */
        fun fromName(name: String): SoundEffect? {
            return values().firstOrNull { it.name.equals(name, ignoreCase = true) }
        }
        
        /**
         * Get all UI interaction sounds
         */
        fun getUIInteractionSounds(): List<SoundEffect> = listOf(
            BUTTON_CLICK, BUTTON_HOVER, TAB_SWITCH, PANEL_OPEN, PANEL_CLOSE
        )
        
        /**
         * Get all navigation sounds
         */
        fun getNavigationSounds(): List<SoundEffect> = listOf(
            PAGE_TURN, SCROLL, BACK_BUTTON
        )
        
        /**
         * Get all terminal sounds
         */
        fun getTerminalSounds(): List<SoundEffect> = listOf(
            TERMINAL_TYPE, TERMINAL_ENTER, TERMINAL_ERROR, TERMINAL_BOOT
        )
        
        /**
         * Get all quest/achievement sounds
         */
        fun getQuestSounds(): List<SoundEffect> = listOf(
            QUEST_ACCEPT, QUEST_COMPLETE, OBJECTIVE_COMPLETE, ACHIEVEMENT_UNLOCK, LEVEL_UP
        )
        
        /**
         * Get all radio sounds
         */
        fun getRadioSounds(): List<SoundEffect> = listOf(
            RADIO_STATIC, RADIO_ON, RADIO_OFF, STATION_SWITCH
        )
        
        /**
         * Get all ambient sounds
         */
        fun getAmbientSounds(): List<SoundEffect> = listOf(
            GEIGER_CLICK, VAULT_HUM
        )
        
        /**
         * Get all system sounds
         */
        fun getSystemSounds(): List<SoundEffect> = listOf(
            ERROR_BEEP, SUCCESS_BEEP, ALERT, SHUTDOWN
        )
    }
}

