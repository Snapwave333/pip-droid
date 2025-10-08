package com.supernova.pipboy.wear.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

/**
 * Pip-Boy Wear Theme
 * Provides consistent theming for the Wear OS companion app
 */
@Composable
fun PipBoyWearTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = androidx.wear.compose.material.Colors(
            primary = androidx.compose.ui.graphics.Color.Green,
            primaryVariant = androidx.compose.ui.graphics.Color.Green.copy(alpha = 0.8f),
            secondary = androidx.compose.ui.graphics.Color.Green.copy(green = 0.8f),
            secondaryVariant = androidx.compose.ui.graphics.Color.Green.copy(green = 0.6f),
            surface = androidx.compose.ui.graphics.Color.Black,
            error = androidx.compose.ui.graphics.Color.Red,
            onPrimary = androidx.compose.ui.graphics.Color.Black,
            onSecondary = androidx.compose.ui.graphics.Color.Black,
            onSurface = androidx.compose.ui.graphics.Color.Green,
            onError = androidx.compose.ui.graphics.Color.White
        ),
        typography = androidx.wear.compose.material.Typography(
            display1 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 32.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.5.sp
            ),
            display2 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 28.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.25.sp
            ),
            display3 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            title1 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            title2 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            title3 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            body1 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                letterSpacing = 0.sp
            ),
            body2 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                letterSpacing = 0.sp
            ),
            button = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 14.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                letterSpacing = 0.5.sp
            ),
            caption1 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 12.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                letterSpacing = 0.sp
            ),
            caption2 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                letterSpacing = 0.sp
            ),
            caption3 = androidx.compose.ui.text.TextStyle(
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                fontSize = 8.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                letterSpacing = 0.sp
            )
        ),
        content = content
    )
}
