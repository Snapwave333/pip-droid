package com.supernova.pipboy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.audio.SoundEffect
import com.supernova.pipboy.audio.SoundManager

/**
 * Modifier extension to add sound effects to clickable elements
 */
fun Modifier.clickableWithSound(
    soundEffect: SoundEffect = SoundEffect.BUTTON_CLICK,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: androidx.compose.ui.semantics.Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    val context = LocalContext.current
    val soundManager = remember {
        (context.applicationContext as PipBoyApplication).soundManager
    }
    
    this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        soundManager.play(soundEffect)
        onClick()
    }
}

/**
 * Play a sound effect from a Composable context
 */
@Composable
fun rememberSoundManager(): SoundManager {
    val context = LocalContext.current
    return remember {
        (context.applicationContext as PipBoyApplication).soundManager
    }
}

/**
 * Play a sound effect
 */
@Composable
fun PlaySound(effect: SoundEffect) {
    val soundManager = rememberSoundManager()
    soundManager.play(effect)
}

