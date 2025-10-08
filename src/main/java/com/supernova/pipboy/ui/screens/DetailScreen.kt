package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography

/**
 * Placeholder Detail Screen
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    primaryColor: PipBoyColor = PipBoyColor.DEFAULT
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "DETAIL",
                style = PipBoyTypography.displayLarge,
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Detail Screen",
                style = PipBoyTypography.bodyMedium,
                color = Color(primaryColor.red, primaryColor.green, primaryColor.blue),
                fontSize = 16.sp
            )
        }
    }
}
