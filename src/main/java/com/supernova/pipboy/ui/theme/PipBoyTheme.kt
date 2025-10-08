package com.supernova.pipboy.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.effects.CRTOverlay
import com.supernova.pipboy.ui.viewmodel.CRTEffects
import kotlinx.coroutines.delay
import kotlin.math.sin

/**
 * Main theme wrapper that applies Pip-Boy styling and CRT effects
 */
@Composable
fun PipBoyTheme(
    primaryColor: PipBoyColor,
    crtEffects: CRTEffects,
    isDarkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = PipBoyColorScheme.fromPrimaryColor(primaryColor)
    
    var flickerIntensity by remember { mutableStateOf(1.0f) }
    var time by remember { mutableStateOf(0L) }

    // CRT flicker effect
    LaunchedEffect(crtEffects.flicker) {
        if (crtEffects.flicker) {
            while (true) {
                delay(100) // 10 FPS flicker
                time += 100
                flickerIntensity = 0.95f + 0.05f * sin(time * 0.02f).toFloat()
            }
        } else {
            flickerIntensity = 1.0f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(primaryColor.red, primaryColor.green, primaryColor.blue, 0x1A))
    ) {
        // Apply brightness modulation for flicker effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = flickerIntensity)
        ) {
            content()
        }
        
        // Enhanced CRT overlay effects
        CRTOverlay(
            primaryColor = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
            enableScanlines = crtEffects.scanlines,
            enableSweep = true,
            enableGlow = crtEffects.scanlines, // Use scanlines toggle for glow
            enableFlicker = crtEffects.flicker,
            enableVignette = crtEffects.distortion, // Use distortion toggle for vignette
            enableCurvature = crtEffects.distortion,
            enableNoise = false // Disabled by default as it can be distracting
        )
    }
}

/**
 * Draw scanlines effect
 */
private fun DrawScope.drawScanlines() {
    val scanlineColor = Color.Black.copy(alpha = 0.1f)
    val scanlineHeight = 2.dp.toPx()

    for (y in 0..size.height.toInt() step scanlineHeight.toInt() * 2) {
        drawRect(
            color = scanlineColor,
            topLeft = androidx.compose.ui.geometry.Offset(0f, y.toFloat()),
            size = androidx.compose.ui.geometry.Size(size.width, scanlineHeight)
        )
    }
}

/**
 * Draw CRT barrel distortion effect
 */
private fun DrawScope.drawCRTDistortion() {
    // Simplified barrel distortion using a subtle vignette effect
    val vignetteColor = Color.Black.copy(alpha = 0.05f)

    // Draw subtle corner darkening
    val cornerRadius = 50.dp.toPx()
    val corners = listOf(
        androidx.compose.ui.geometry.Offset(0f, 0f),
        androidx.compose.ui.geometry.Offset(size.width, 0f),
        androidx.compose.ui.geometry.Offset(0f, size.height),
        androidx.compose.ui.geometry.Offset(size.width, size.height)
    )

    corners.forEach { corner ->
        drawCircle(
            color = vignetteColor,
            center = corner,
            radius = cornerRadius
        )
    }
}

/**
 * Pip-Boy typography styles
 */
object PipBoyTypography {
    val displayLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        fontSize = 32.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.5.sp
    )

    val displayMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        fontSize = 24.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.25.sp
    )

    val bodyLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        fontSize = 16.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.sp
    )

    val bodyMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        fontSize = 14.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        letterSpacing = 0.sp
    )

    val labelLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        fontSize = 12.sp,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

/**
 * Color scheme for Pip-Boy theme
 */
data class PipBoyColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onError: Color
) {
    companion object {
        fun fromPrimaryColor(pipBoyColor: PipBoyColor): PipBoyColorScheme {
            val primary = pipBoyColor.toComposeColor()
            val isLight = pipBoyColor.getLuminance() > 0.5f

            return PipBoyColorScheme(
                primary = primary,
                onPrimary = if (isLight) Color.Black else Color.White,
                background = Color(PipBoyColor.DARK_SUBSTRATE.red, PipBoyColor.DARK_SUBSTRATE.green, PipBoyColor.DARK_SUBSTRATE.blue),
                surface = Color(PipBoyColor.DARK_SUBSTRATE.red, PipBoyColor.DARK_SUBSTRATE.green, PipBoyColor.DARK_SUBSTRATE.blue).copy(alpha = 0.8f),
                error = PipBoyColor.EMERGENCY_RED.toComposeColor(),
                onError = Color.White
            )
        }
    }
}
