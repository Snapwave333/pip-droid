package com.supernova.pipboy.ui.theme

import android.app.WallpaperColors
import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.supernova.pipboy.data.model.PipBoyColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Dynamic Theme Manager for Pip-Boy
 * Handles Material You dynamic colors, dark mode, and custom Pip-Boy theming
 */
class DynamicThemeManager(private val context: Context) {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _primaryColor = MutableStateFlow(PipBoyColor.DEFAULT)
    val primaryColor: StateFlow<PipBoyColor> = _primaryColor.asStateFlow()

    private val _useSystemTheme = MutableStateFlow(true)
    val useSystemTheme: StateFlow<Boolean> = _useSystemTheme.asStateFlow()

    private val _useMaterialYou = MutableStateFlow(false)
    val useMaterialYou: StateFlow<Boolean> = _useMaterialYou.asStateFlow()

    /**
     * Get dynamic color scheme based on current settings
     */
    fun getColorScheme(): ColorScheme {
        return when {
            useMaterialYou.value && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                if (isDarkMode.value) {
                    dynamicDarkColorScheme(context)
                } else {
                    dynamicLightColorScheme(context)
                }
            }
            isDarkMode.value -> getPipBoyDarkColorScheme()
            else -> getPipBoyLightColorScheme()
        }
    }

    /**
     * Get Pip-Boy themed light color scheme
     */
    private fun getPipBoyLightColorScheme(): ColorScheme {
        val baseColor = primaryColor.value.toComposeColor()
        return lightColorScheme(
            primary = baseColor,
            onPrimary = Color.Black,
            primaryContainer = baseColor.copy(alpha = 0.2f),
            onPrimaryContainer = baseColor,
            secondary = Color.Gray,
            onSecondary = Color.White,
            secondaryContainer = Color.DarkGray,
            onSecondaryContainer = Color.LightGray,
            tertiary = Color.Green.copy(green = 0.7f),
            onTertiary = Color.Black,
            tertiaryContainer = Color.Green.copy(alpha = 0.3f),
            onTertiaryContainer = Color.Green,
            background = Color.Black,
            onBackground = Color.Green,
            surface = Color.Black.copy(alpha = 0.9f),
            onSurface = Color.Green,
            surfaceVariant = Color.DarkGray,
            onSurfaceVariant = Color.Green,
            outline = Color.Green,
            outlineVariant = Color.DarkGray
        )
    }

    /**
     * Get Pip-Boy themed dark color scheme
     */
    private fun getPipBoyDarkColorScheme(): ColorScheme {
        val baseColor = primaryColor.value.toComposeColor()
        return darkColorScheme(
            primary = baseColor,
            onPrimary = Color.Black,
            primaryContainer = baseColor.copy(alpha = 0.3f),
            onPrimaryContainer = baseColor,
            secondary = Color.Gray.copy(alpha = 0.7f),
            onSecondary = Color.Black,
            secondaryContainer = Color.DarkGray.copy(alpha = 0.8f),
            onSecondaryContainer = Color.Gray,
            tertiary = Color.Green.copy(green = 0.5f),
            onTertiary = Color.Black,
            tertiaryContainer = Color.Green.copy(alpha = 0.2f),
            onTertiaryContainer = Color.Green.copy(green = 0.7f),
            background = Color.Black,
            onBackground = Color.Green,
            surface = Color.Black.copy(alpha = 0.95f),
            onSurface = Color.Green,
            surfaceVariant = Color.DarkGray.copy(alpha = 0.8f),
            onSurfaceVariant = Color.Green.copy(green = 0.8f),
            outline = Color.Green.copy(green = 0.7f),
            outlineVariant = Color.DarkGray.copy(alpha = 0.7f)
        )
    }

    /**
     * Update dark mode setting
     */
    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    /**
     * Update primary color
     */
    fun setPrimaryColor(color: PipBoyColor) {
        _primaryColor.value = color
    }

    /**
     * Toggle system theme usage
     */
    fun setUseSystemTheme(useSystem: Boolean) {
        _useSystemTheme.value = useSystem
        if (useSystem) {
            // Update dark mode based on system setting
            val nightModeFlags = context.resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK
            _isDarkMode.value = nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES
        }
    }

    /**
     * Toggle Material You usage
     */
    fun setUseMaterialYou(useMaterialYou: Boolean) {
        _useMaterialYou.value = useMaterialYou
    }

    /**
     * Get system wallpaper colors for dynamic theming
     */
    fun getWallpaperColors(): WallpaperColors? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
        } else {
            null
        }
    }

    /**
     * Get complementary color for current primary color
     */
    fun getComplementaryColor(): Color {
        return when (primaryColor.value) {
            PipBoyColor.DEFAULT -> Color.Green.copy(green = 0.8f)
            PipBoyColor.EMERGENCY_RED -> Color.Red.copy(red = 0.8f)
            else -> Color.Gray
        }
    }

    /**
     * Get color palette for current theme
     */
    fun getColorPalette(): PipBoyColorPalette {
        val baseColor = primaryColor.value.toComposeColor()
        return PipBoyColorPalette(
            primary = baseColor,
            secondary = getComplementaryColor(),
            background = if (isDarkMode.value) Color.Black else Color.Black.copy(alpha = 0.95f),
            surface = if (isDarkMode.value) Color.Black.copy(alpha = 0.9f) else Color.Black.copy(alpha = 0.85f),
            text = Color.Green,
            accent = baseColor.copy(alpha = 0.7f)
        )
    }
}

/**
 * Color palette for Pip-Boy theming
 */
data class PipBoyColorPalette(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val text: Color,
    val accent: Color
)

/**
 * Composable function to remember dynamic theme manager
 */
@Composable
fun rememberDynamicThemeManager(): DynamicThemeManager {
    val context = LocalContext.current
    return remember { DynamicThemeManager(context) }
}

/**
 * Extension function to convert PipBoyColor to Compose Color
 */
fun PipBoyColor.toComposeColor(): Color {
    return when (this) {
        PipBoyColor.DEFAULT -> Color.Green
        PipBoyColor.EMERGENCY_RED -> Color.Red
        else -> Color(this.red, this.green, this.blue)
    }
}

/**
 * Get theme mode description
 */
fun getThemeModeDescription(
    useSystemTheme: Boolean,
    useMaterialYou: Boolean,
    isDarkMode: Boolean
): String {
    return when {
        useMaterialYou -> "Material You ${if (isDarkMode) "Dark" else "Light"}"
        useSystemTheme -> "System ${if (isDarkMode) "Dark" else "Light"}"
        else -> "Pip-Boy ${if (isDarkMode) "Dark" else "Light"}"
    }
}
