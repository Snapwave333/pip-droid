package com.supernova.pipboy.data.model

import androidx.compose.ui.graphics.Color

/**
 * Represents the primary display color for the Pip-Boy interface
 * Uses RGB values to create a monochromatic color scheme
 */
data class PipBoyColor(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    init {
        require(red in 0..255) { "Red value must be between 0 and 255" }
        require(green in 0..255) { "Green value must be between 0 and 255" }
        require(blue in 0..255) { "Blue value must be between 0 and 255" }
    }

    /**
     * Convert to Compose Color
     */
    fun toComposeColor(): Color {
        return Color(red, green, blue)
    }

    /**
     * Get the luminance value for text contrast calculations
     */
    fun getLuminance(): Float {
        // Calculate relative luminance using standard formula
        val r = red / 255.0f
        val g = green / 255.0f
        val b = blue / 255.0f

        val rsRGB = if (r <= 0.03928f) r / 12.92f else Math.pow((r + 0.055).toDouble() / 1.055, 2.4).toFloat()
        val gsRGB = if (g <= 0.03928f) g / 12.92f else Math.pow((g + 0.055).toDouble() / 1.055, 2.4).toFloat()
        val bsRGB = if (b <= 0.03928f) b / 12.92f else Math.pow((b + 0.055).toDouble() / 1.055, 2.4).toFloat()

        return 0.2126f * rsRGB + 0.7152f * gsRGB + 0.0722f * bsRGB
    }

    /**
     * Default Pip-Boy green color (107, 217, 106)
     */
    companion object {
        val DEFAULT = PipBoyColor(107, 217, 106)
        val EMERGENCY_RED = PipBoyColor(217, 107, 106)
        val DARK_SUBSTRATE = PipBoyColor(26, 26, 26)
    }
}
