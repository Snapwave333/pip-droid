package com.supernova.pipboy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.ui.theme.PipBoyTypography
import java.text.SimpleDateFormat
import java.util.*

/**
 * Calendar component displaying the current month in Pip-Boy style
 */
@Composable
fun PipBoyCalendar(
    primaryColor: PipBoyColor,
    modifier: Modifier = Modifier
) {
    val calendar = remember { Calendar.getInstance() }
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)

    val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.6f))
            .border(
                width = 1.dp,
                color = primaryColor.toComposeColor().copy(alpha = 0.5f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .padding(12.dp)
    ) {
        // Month and Year header
        androidx.compose.material3.Text(
            text = monthFormat.format(calendar.time).uppercase(),
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Day headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val daysOfWeek = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
            daysOfWeek.forEach { day ->
                androidx.compose.material3.Text(
                    text = day,
                    style = PipBoyTypography.labelLarge.copy(fontSize = 10.sp),
                    color = primaryColor.toComposeColor().copy(alpha = 0.7f),
                    modifier = Modifier.width(32.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Calendar grid
        val firstDayOfMonth = calendar.clone() as Calendar
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        val startingDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2 // Adjust for Monday start
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // Create calendar grid (6 weeks)
        for (week in 0..5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (dayOfWeek in 0..6) {
                    val dayNumber = week * 7 + dayOfWeek - startingDayOfWeek + 1

                    if (dayNumber in 1..daysInMonth) {
                        val isCurrentDay = (dayNumber == currentDay &&
                                          calendar.get(Calendar.MONTH) == currentMonth &&
                                          calendar.get(Calendar.YEAR) == currentYear)

                        CalendarDayItem(
                            day = dayNumber,
                            isCurrentDay = isCurrentDay,
                            primaryColor = primaryColor
                        )
                    } else {
                        // Empty cell
                        Spacer(modifier = Modifier.width(32.dp))
                    }
                }
            }
        }
    }
}

/**
 * Individual calendar day item
 */
@Composable
private fun CalendarDayItem(
    day: Int,
    isCurrentDay: Boolean,
    primaryColor: PipBoyColor
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                color = if (isCurrentDay) {
                    primaryColor.toComposeColor().copy(alpha = 0.3f)
                } else {
                    Color.Transparent
                },
                shape = androidx.compose.foundation.shape.CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = day.toString(),
            style = PipBoyTypography.bodyMedium.copy(fontSize = 12.sp),
            color = if (isCurrentDay) {
                primaryColor.toComposeColor()
            } else {
                primaryColor.toComposeColor().copy(alpha = 0.8f)
            },
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
