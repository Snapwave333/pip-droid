package com.supernova.pipboy.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.components.rememberSoundManager
import com.supernova.pipboy.audio.SoundEffect
import kotlinx.coroutines.delay

/**
 * Pip-Boy Terminal Startup Animation Screen
 * Simulates a retro terminal boot sequence
 */
@Composable
fun StartupScreen(
    onStartupComplete: () -> Unit,
    primaryColor: PipBoyColor = PipBoyColor.DEFAULT
) {
    val bootMessages = remember {
        listOf(
            "ROBCO INDUSTRIES UNIFIED OPERATING SYSTEM",
            "COPYRIGHT 2075-2077 ROBCO INDUSTRIES",
            "-Server 1-",
            "",
            "INITIALIZING PIP-BOY 3000 MARK IV...",
            "",
            "> Checking system integrity... OK",
            "> Loading ROM modules... OK",
            "> Initializing display drivers... OK",
            "> Scanning for hardware... OK",
            "> Loading V.A.T.S. subroutines... OK",
            "> Calibrating sensors... OK",
            "> Establishing secure connection... OK",
            "> Loading user profile... OK",
            "",
            "SYSTEM STATUS: OPTIMAL",
            "WELCOME TO PIP-BOY 3000",
            "",
            "Press any key to continue..."
        )
    }

    var visibleLines by remember { mutableStateOf(0) }
    var isComplete by remember { mutableStateOf(false) }
    
    val soundManager = rememberSoundManager()

    // Animated cursor blink
    val infiniteTransition = rememberInfiniteTransition(label = "cursor_blink")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )

    // Terminal boot sequence
    LaunchedEffect(Unit) {
        // Play boot sound at start
        soundManager.play(SoundEffect.TERMINAL_BOOT)
        
        for (i in bootMessages.indices) {
            visibleLines = i + 1
            // Play typing sound for each line (except empty lines)
            if (bootMessages[i].isNotEmpty()) {
                soundManager.play(SoundEffect.TERMINAL_TYPE, volumeMultiplier = 0.6f)
            }
            delay(if (bootMessages[i].isEmpty()) 100 else 150) // Faster for empty lines
        }
        delay(1000) // Pause at end
        isComplete = true
        soundManager.play(SoundEffect.SUCCESS_BEEP)
        delay(500)
        onStartupComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            bootMessages.take(visibleLines).forEachIndexed { index, message ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = message,
                        color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 20.sp
                    )
                    
                    // Show blinking cursor on last line
                    if (index == visibleLines - 1 && !isComplete) {
                        Text(
                            text = "█",
                            color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                                .copy(alpha = cursorAlpha),
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        // Scanline effect overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                        .copy(alpha = 0.03f)
                )
        )
    }
}

/**
 * Alternative Pip-Boy Logo Startup Screen
 * Shows a simple logo with initialization text
 */
@Composable
fun StartupScreenSimple(
    onStartupComplete: () -> Unit,
    primaryColor: PipBoyColor = PipBoyColor.DEFAULT
) {
    var progress by remember { mutableStateOf(0) }
    
    LaunchedEffect(Unit) {
        for (i in 0..100 step 5) {
            progress = i
            delay(50)
        }
        delay(500)
        onStartupComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "PIP-BOY 3000",
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                fontSize = 48.sp,
                fontFamily = FontFamily.Monospace
            )
            
            Text(
                text = "MARK IV",
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
                    .copy(alpha = 0.7f),
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "INITIALIZING... $progress%",
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace
            )
            
            // Progress bar
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(20.dp)
                    .background(Color.DarkGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress / 100f)
                        .background(Color(primaryColor.red, primaryColor.green, primaryColor.blue))
                )
            }
        }
    }
}

