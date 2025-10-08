package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography

/**
 * Simplified RGB Color Picker Component
 * TODO: Implement proper color picker when complex graphics are ready
 */
@Composable
fun RGBColorPicker(
    initialColor: PipBoyColor,
    onColorChange: (PipBoyColor) -> Unit,
    modifier: Modifier = Modifier
) {
    var red by remember { mutableStateOf(initialColor.red.toFloat()) }
    var green by remember { mutableStateOf(initialColor.green.toFloat()) }
    var blue by remember { mutableStateOf(initialColor.blue.toFloat()) }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "RGB Color Picker",
            style = PipBoyTypography.displayMedium,
            color = Color.White,
            fontSize = 16.sp
        )
        
        Text(
            text = "Red: ${red.toInt()}",
            style = PipBoyTypography.bodyMedium,
            color = Color.Red,
            fontSize = 12.sp
        )
        Slider(
            value = red,
            onValueChange = { 
                red = it
                onColorChange(PipBoyColor(red.toInt(), green.toInt(), blue.toInt()))
            },
            valueRange = 0f..255f
        )
        
        Text(
            text = "Green: ${green.toInt()}",
            style = PipBoyTypography.bodyMedium,
            color = Color.Green,
            fontSize = 12.sp
        )
        Slider(
            value = green,
            onValueChange = { 
                green = it
                onColorChange(PipBoyColor(red.toInt(), green.toInt(), blue.toInt()))
            },
            valueRange = 0f..255f
        )
        
        Text(
            text = "Blue: ${blue.toInt()}",
            style = PipBoyTypography.bodyMedium,
            color = Color.Blue,
            fontSize = 12.sp
        )
        Slider(
            value = blue,
            onValueChange = { 
                blue = it
                onColorChange(PipBoyColor(red.toInt(), green.toInt(), blue.toInt()))
            },
            valueRange = 0f..255f
        )
        
        // Color preview
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(red.toInt(), green.toInt(), blue.toInt()))
        )
    }
}
