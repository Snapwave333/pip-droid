package com.supernova.pipboy.ui.effects

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.BlendMode
import kotlin.math.sin

/**
 * CRT (Cathode Ray Tube) effects for authentic retro display
 * Includes scanlines, phosphor glow, flicker, and vignette
 */

/**
 * Scanline effect overlay
 */
@Composable
fun ScanlineEffect(
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Black.copy(alpha = 0.15f),
    lineSpacing: Float = 4f,
    enabled: Boolean = true
) {
    if (!enabled) return
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        var y = 0f
        while (y < height) {
            drawLine(
                color = lineColor,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1f
            )
            y += lineSpacing
        }
    }
}

/**
 * Animated scanline sweep effect
 */
@Composable
fun ScanlineSweepEffect(
    modifier: Modifier = Modifier,
    sweepColor: Color = Color.White.copy(alpha = 0.03f),
    enabled: Boolean = true
) {
    if (!enabled) return
    
    val infiniteTransition = rememberInfiniteTransition(label = "scanline_sweep")
    val sweepPosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep_position"
    )
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val height = size.height
        val sweepY = height * sweepPosition
        val sweepHeight = height * 0.1f
        
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    sweepColor,
                    sweepColor,
                    Color.Transparent
                ),
                startY = sweepY - sweepHeight / 2,
                endY = sweepY + sweepHeight / 2
            ),
            topLeft = Offset(0f, sweepY - sweepHeight / 2),
            size = androidx.compose.ui.geometry.Size(size.width, sweepHeight)
        )
    }
}

/**
 * Phosphor glow effect
 */
@Composable
fun PhosphorGlowEffect(
    modifier: Modifier = Modifier,
    glowColor: Color = Color.Green.copy(alpha = 0.3f),
    enabled: Boolean = true
) {
    if (!enabled) return
    
    val infiniteTransition = rememberInfiniteTransition(label = "phosphor_glow")
    val glowIntensity by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_intensity"
    )
    
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            color = glowColor.copy(alpha = glowIntensity * glowColor.alpha),
            blendMode = BlendMode.Screen
        )
    }
}

/**
 * Screen flicker effect
 */
@Composable
fun FlickerEffect(
    modifier: Modifier = Modifier,
    flickerColor: Color = Color.Black.copy(alpha = 0.05f),
    enabled: Boolean = true
) {
    if (!enabled) return
    
    var flickerAlpha by remember { mutableStateOf(0f) }
    
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(kotlin.random.Random.nextLong(100, 500))
            flickerAlpha = kotlin.random.Random.nextFloat() * 0.1f
            kotlinx.coroutines.delay(50)
            flickerAlpha = 0f
        }
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        if (flickerAlpha > 0f) {
            drawRect(
                color = flickerColor.copy(alpha = flickerAlpha),
                blendMode = BlendMode.Multiply
            )
        }
    }
}

/**
 * Vignette effect (darkened corners)
 */
@Composable
fun VignetteEffect(
    modifier: Modifier = Modifier,
    vignetteColor: Color = Color.Black.copy(alpha = 0.3f),
    enabled: Boolean = true
) {
    if (!enabled) return
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = maxOf(size.width, size.height) * 0.8f
        
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Transparent,
                    vignetteColor
                ),
                center = Offset(centerX, centerY),
                radius = radius
            )
        )
    }
}

/**
 * Screen curvature effect (subtle barrel distortion simulation)
 */
@Composable
fun CurvatureEffect(
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    if (!enabled) return
    
    // Note: True screen curvature requires shader effects
    // This is a simplified gradient overlay to simulate edge darkness
    Canvas(modifier = modifier.fillMaxSize()) {
        // Draw darker edges to simulate CRT curvature
        val edgeDarkness = Color.Black.copy(alpha = 0.1f)
        
        // Left edge
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(edgeDarkness, Color.Transparent),
                startX = 0f,
                endX = size.width * 0.05f
            ),
            size = androidx.compose.ui.geometry.Size(size.width * 0.05f, size.height)
        )
        
        // Right edge
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Transparent, edgeDarkness),
                startX = size.width * 0.95f,
                endX = size.width
            ),
            topLeft = Offset(size.width * 0.95f, 0f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.05f, size.height)
        )
        
        // Top edge
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(edgeDarkness, Color.Transparent),
                startY = 0f,
                endY = size.height * 0.05f
            ),
            size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.05f)
        )
        
        // Bottom edge
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, edgeDarkness),
                startY = size.height * 0.95f,
                endY = size.height
            ),
            topLeft = Offset(0f, size.height * 0.95f),
            size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.05f)
        )
    }
}

/**
 * RGB color separation (chromatic aberration)
 */
@Composable
fun ChromaticAberrationEffect(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    intensity: Float = 0.005f
) {
    if (!enabled) return
    
    // Note: True chromatic aberration requires shader effects
    // This is a placeholder for future implementation
}

/**
 * Noise/static effect
 */
@Composable
fun NoiseEffect(
    modifier: Modifier = Modifier,
    noiseIntensity: Float = 0.02f,
    enabled: Boolean = true
) {
    if (!enabled) return
    
    var noisePhase by remember { mutableStateOf(0f) }
    
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(50)
            noisePhase = kotlin.random.Random.nextFloat()
        }
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        // Draw random noise pixels
        val pixelSize = 2f
        val width = size.width
        val height = size.height
        
        repeat((width * height * noiseIntensity).toInt()) {
            val x = kotlin.random.Random.nextInt(0, width.toInt() + 1).toFloat()
            val y = kotlin.random.Random.nextInt(0, height.toInt() + 1).toFloat()
            val alpha = (0.1f + kotlin.random.Random.nextFloat() * 0.2f) * noisePhase
            
            drawRect(
                color = Color.White.copy(alpha = alpha),
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(pixelSize, pixelSize)
            )
        }
    }
}

/**
 * Bloom effect (glow around bright areas)
 */
@Composable
fun BloomEffect(
    modifier: Modifier = Modifier,
    bloomColor: Color = Color.Green.copy(alpha = 0.2f),
    enabled: Boolean = true
) {
    if (!enabled) return
    
    Canvas(modifier = modifier.fillMaxSize()) {
        // Simplified bloom - draw a soft glow overlay
        drawRect(
            color = bloomColor,
            blendMode = BlendMode.Screen
        )
    }
}

/**
 * Combined CRT effect overlay
 */
@Composable
fun CRTOverlay(
    modifier: Modifier = Modifier,
    primaryColor: Color = Color.Green,
    enableScanlines: Boolean = true,
    enableSweep: Boolean = true,
    enableGlow: Boolean = true,
    enableFlicker: Boolean = true,
    enableVignette: Boolean = true,
    enableCurvature: Boolean = true,
    enableNoise: Boolean = false
) {
    androidx.compose.foundation.layout.Box(modifier = modifier.fillMaxSize()) {
        // Base glow
        if (enableGlow) {
            PhosphorGlowEffect(
                glowColor = primaryColor.copy(alpha = 0.15f)
            )
        }
        
        // Scanlines
        if (enableScanlines) {
            ScanlineEffect(
                lineSpacing = 3f
            )
        }
        
        // Animated sweep
        if (enableSweep) {
            ScanlineSweepEffect(
                sweepColor = primaryColor.copy(alpha = 0.05f)
            )
        }
        
        // Flicker
        if (enableFlicker) {
            FlickerEffect()
        }
        
        // Vignette (darkened corners)
        if (enableVignette) {
            VignetteEffect()
        }
        
        // Screen curvature
        if (enableCurvature) {
            CurvatureEffect()
        }
        
        // Noise (optional, can be distracting)
        if (enableNoise) {
            NoiseEffect(noiseIntensity = 0.01f)
        }
    }
}

