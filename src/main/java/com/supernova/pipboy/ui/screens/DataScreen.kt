package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.ui.components.PipBoyCalendar
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * DATA tab - Quest Log, Notes, and information display
 */
@Composable
fun DataScreen(viewModel: MainViewModel) {
    val notes by viewModel.notes.collectAsState()
    val primaryColor by viewModel.primaryColor.collectAsState()
    val systemStatus by viewModel.systemStatus.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var selectedTab by remember { mutableStateOf(0) }

    // Sample notifications for demonstration
    val sampleNotifications = remember {
        listOf(
            NotificationItem(
                title = "SYSTEM UPDATE",
                message = "Android system update available",
                timestamp = "14:32",
                type = NotificationType.SYSTEM
            ),
            NotificationItem(
                title = "THERMAL WARNING",
                message = "Device temperature elevated",
                timestamp = "14:28",
                type = NotificationType.WARNING
            ),
            NotificationItem(
                title = "APP INSTALLED",
                message = "New application installed: TestApp",
                timestamp = "14:15",
                type = NotificationType.INFO
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Sub-tab selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DataTabButton("QUESTS", selectedTab == 0, primaryColor) { selectedTab = 0 }
            DataTabButton("NOTES", selectedTab == 1, primaryColor) { selectedTab = 1 }
            DataTabButton("LOG", selectedTab == 2, primaryColor) { selectedTab = 2 }
        }
        
        // Content based on selected tab
        when (selectedTab) {
            0 -> QuestLogScreen(primaryColor = primaryColor)
            1 -> NotesScreen(notes, primaryColor, viewModel)
            2 -> MessageLogScreen(sampleNotifications, primaryColor)
        }
    }
}

/**
 * Data tab button
 */
@Composable
fun DataTabButton(
    text: String,
    isSelected: Boolean,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(if (isSelected) primaryColor.toComposeColor() else Color.Transparent)
            .border(1.dp, primaryColor.toComposeColor())
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        androidx.compose.material3.Text(
            text = text,
            style = PipBoyTypography.bodyMedium,
            color = if (isSelected) Color.Black else primaryColor.toComposeColor(),
            fontSize = 12.sp
        )
    }
}

/**
 * Notes screen
 */
@Composable
fun NotesScreen(
    notes: String,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        // Quick Notes Section
        QuickNotesSection(
            notes = notes,
            primaryColor = primaryColor,
            onNotesChanged = { newNotes ->
                viewModel.updateNotes(newNotes)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Section
        androidx.compose.material3.Text(
            text = "CALENDAR/LOG",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        PipBoyCalendar(
            primaryColor = primaryColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

/**
 * Message Log screen
 */
@Composable
fun MessageLogScreen(
    notifications: List<NotificationItem>,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        androidx.compose.material3.Text(
            text = "MESSAGE LOG",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        MessageLogSection(
            notifications = notifications,
            primaryColor = primaryColor
        )
    }
}

/**
 * Quick Notes section with terminal-style input
 */
@Composable
private fun QuickNotesSection(
    notes: String,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor,
    onNotesChanged: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(notes) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        androidx.compose.material3.Text(
            text = "QUICK NOTES",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.8f))
                .border(
                    width = 1.dp,
                    color = primaryColor.toComposeColor(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                )
                .padding(12.dp)
        ) {
            if (!isEditing && notes.isEmpty()) {
                androidx.compose.material3.Text(
                    text = "> Enter your notes here...",
                    style = PipBoyTypography.bodyMedium,
                    color = primaryColor.toComposeColor().copy(alpha = 0.5f)
                )
            }

            BasicTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    onNotesChanged(it)
                },
                textStyle = PipBoyTypography.bodyMedium.copy(
                    color = primaryColor.toComposeColor()
                ),
                cursorBrush = SolidColor(primaryColor.toComposeColor()),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                    },
                decorationBox = { innerTextField ->
                    Box {
                        innerTextField()
                    }
                }
            )
        }

        // Character buffer indicator
        androidx.compose.material3.Text(
            text = "${textFieldValue.length}/256",
            style = PipBoyTypography.labelLarge.copy(fontSize = 10.sp),
            color = primaryColor.toComposeColor().copy(alpha = 0.7f),
            modifier = Modifier.align(Alignment.End)
        )
    }
}

/**
 * Message Log section
 */
@Composable
private fun MessageLogSection(
    notifications: List<NotificationItem>,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Black.copy(alpha = 0.6f))
            .border(
                width = 1.dp,
                color = primaryColor.toComposeColor().copy(alpha = 0.5f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(notifications) { notification ->
            NotificationItemView(
                notification = notification,
                primaryColor = primaryColor
            )
        }
    }
}

/**
 * Individual notification item
 */
@Composable
private fun NotificationItemView(
    notification: NotificationItem,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray.copy(alpha = 0.3f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Notification type indicator
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    when (notification.type) {
                        NotificationType.SYSTEM -> Color.Blue
                        NotificationType.WARNING -> Color.Red
                        NotificationType.INFO -> primaryColor.toComposeColor()
                    },
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            androidx.compose.material3.Text(
                text = notification.title,
                style = PipBoyTypography.bodyMedium,
                color = primaryColor.toComposeColor()
            )

            androidx.compose.material3.Text(
                text = notification.message,
                style = PipBoyTypography.bodyMedium.copy(fontSize = 12.sp),
                color = primaryColor.toComposeColor().copy(alpha = 0.8f)
            )
        }

        androidx.compose.material3.Text(
            text = notification.timestamp,
            style = PipBoyTypography.labelLarge.copy(fontSize = 10.sp),
            color = primaryColor.toComposeColor().copy(alpha = 0.6f)
        )
    }
}

/**
 * Data classes for notifications
 */
data class NotificationItem(
    val title: String,
    val message: String,
    val timestamp: String,
    val type: NotificationType
)

enum class NotificationType {
    SYSTEM, WARNING, INFO
}
