package com.supernova.pipboy.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.viewmodel.CRTEffects
import kotlinx.coroutines.delay
import kotlin.math.sin

/**
 * Composition local for DynamicThemeManager
 */
val LocalDynamicThemeManager = staticCompositionLocalOf<DynamicThemeManager> {
    error("No DynamicThemeManager provided")
}

/**
 * Main theme wrapper that applies Pip-Boy styling with Material 3 and dynamic theming
 */
@Composable
fun PipBoyTheme(
    themeManager: DynamicThemeManager = rememberDynamicThemeManager(),
    crtEffects: CRTEffects,
    content: @Composable () -> Unit
) {
    val colorScheme = themeManager.getColorScheme()

    // Provide theme manager to composition tree
    CompositionLocalProvider(LocalDynamicThemeManager provides themeManager) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = androidx.compose.material3.Typography(
                displayLarge = PipBoyTypography.displayLarge,
                displayMedium = PipBoyTypography.displayMedium,
                displaySmall = PipBoyTypography.displayMedium,
                headlineLarge = PipBoyTypography.displayMedium,
                headlineMedium = PipBoyTypography.displayMedium,
                headlineSmall = PipBoyTypography.bodyLarge,
                titleLarge = PipBoyTypography.bodyLarge,
                titleMedium = PipBoyTypography.bodyLarge,
                titleSmall = PipBoyTypography.bodyMedium,
                bodyLarge = PipBoyTypography.bodyLarge,
                bodyMedium = PipBoyTypography.bodyMedium,
                bodySmall = PipBoyTypography.bodyMedium,
                labelLarge = PipBoyTypography.labelLarge,
                labelMedium = PipBoyTypography.bodyMedium,
                labelSmall = PipBoyTypography.bodyMedium
            )
        ) {
            PipBoyThemeEffects(crtEffects, content)
        }
    }
}

/**
 * Apply CRT effects and styling
 */
@Composable
private fun PipBoyThemeEffects(
    crtEffects: CRTEffects,
    content: @Composable () -> Unit
) {
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
            .background(MaterialTheme.colorScheme.background)
            .drawWithContent {
                drawContent()

                if (crtEffects.scanlines) {
                    drawScanlines()
                }

                if (crtEffects.distortion) {
                    drawCRTDistortion()
                }
            }
    ) {
        // Apply brightness modulation for flicker effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = flickerIntensity)
        ) {
            content()
        }
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

// PipBoyTypography is defined in PipBoyTheme.kt to avoid duplication

// PipBoyColorScheme is defined in PipBoyTheme.kt to avoid duplication
