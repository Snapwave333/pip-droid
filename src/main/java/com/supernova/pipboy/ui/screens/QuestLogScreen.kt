package com.supernova.pipboy.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.quests.*
import com.supernova.pipboy.ui.theme.PipBoyTypography
import kotlinx.coroutines.launch

/**
 * Quest Log Screen - Fallout-style quest management with Google integration
 */
@Composable
fun QuestLogScreen(
    primaryColor: PipBoyColor,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val questRepository = remember { QuestRepository(context) }
    val scope = rememberCoroutineScope()
    
    var allQuests by remember { mutableStateOf<List<Quest>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf<QuestCategory?>(null) }
    var showNewQuestDialog by remember { mutableStateOf(false) }
    var showEditQuestDialog by remember { mutableStateOf(false) }
    var questToEdit by remember { mutableStateOf<Quest?>(null) }
    var selectedQuest by remember { mutableStateOf<Quest?>(null) }
    var totalXP by remember { mutableStateOf(0) }
    var totalCaps by remember { mutableStateOf(0) }
    var isGoogleSyncEnabled by remember { mutableStateOf(false) }
    
    // Load quests
    LaunchedEffect(Unit) {
        questRepository.getAllQuestsFlow().collect { quests ->
            allQuests = quests
        }
    }
    
    // Load stats
    LaunchedEffect(Unit) {
        scope.launch {
            totalXP = questRepository.getTotalXP()
            totalCaps = questRepository.getTotalCaps()
            isGoogleSyncEnabled = questRepository.isGoogleSyncEnabled()
        }
    }
    
    val color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
    
    // Filter quests by category
    val filteredQuests = if (selectedCategory != null) {
        allQuests.filter { it.category == selectedCategory }
    } else {
        allQuests
    }.sortedWith(
        compareBy<Quest> { it.status.ordinal }
            .thenBy { it.priority.ordinal }
            .thenBy { it.dueDate ?: Long.MAX_VALUE }
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "☢ QUEST LOG",
            style = PipBoyTypography.displayLarge,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Stats Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("XP: $totalXP", style = PipBoyTypography.bodyMedium, color = color, fontSize = 12.sp)
                Text("Level: ${calculateLevel(totalXP)}", style = PipBoyTypography.bodyMedium, color = color, fontSize = 10.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("CAPS: $totalCaps", style = PipBoyTypography.bodyMedium, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isGoogleSyncEnabled) "SYNC: ON" else "SYNC: OFF",
                        style = PipBoyTypography.bodyMedium,
                        color = if (isGoogleSyncEnabled) Color.Green else Color.Gray,
                        fontSize = 10.sp
                    )
                }
                Text(
                    text = "${allQuests.count { it.status == QuestStatus.ACTIVE }} Active",
                    style = PipBoyTypography.bodyMedium,
                    color = color,
                    fontSize = 10.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Category Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CategoryButton("ALL", selectedCategory == null, color) {
                selectedCategory = null
            }
            CategoryButton("MAIN", selectedCategory == QuestCategory.MAIN, color) {
                selectedCategory = QuestCategory.MAIN
            }
            CategoryButton("SIDE", selectedCategory == QuestCategory.SIDE, color) {
                selectedCategory = QuestCategory.SIDE
            }
            CategoryButton("MISC", selectedCategory == QuestCategory.MISC, color) {
                selectedCategory = QuestCategory.MISC
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Quest List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, color),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (filteredQuests.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "NO ACTIVE QUESTS\n\nPress [+] to add a new quest",
                            style = PipBoyTypography.bodyMedium,
                            color = color.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                items(filteredQuests) { quest ->
                    QuestCard(
                        quest = quest,
                        color = color,
                        isExpanded = selectedQuest?.id == quest.id,
                        onClick = {
                            selectedQuest = if (selectedQuest?.id == quest.id) null else quest
                        },
                        onCompleteObjective = { objectiveId ->
                            scope.launch {
                                questRepository.completeObjective(quest.id, objectiveId)
                                totalXP = questRepository.getTotalXP()
                                totalCaps = questRepository.getTotalCaps()
                            }
                        },
                        onEditQuest = {
                            questToEdit = quest
                            showEditQuestDialog = true
                        },
                        onDeleteQuest = {
                            scope.launch {
                                questRepository.deleteQuest(quest.id)
                            }
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { showNewQuestDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = color
                ),
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, color)
            ) {
                Icon(Icons.Default.Add, contentDescription = "New Quest")
                Spacer(modifier = Modifier.width(4.dp))
                Text("NEW QUEST", fontSize = 10.sp)
            }
            
            Button(
                onClick = {
                    // TODO: Implement Google Sign-In
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = if (isGoogleSyncEnabled) Color.Green else color
                ),
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, color)
            ) {
                Icon(Icons.Default.Sync, contentDescription = "Sync")
                Spacer(modifier = Modifier.width(4.dp))
                Text("GOOGLE SYNC", fontSize = 10.sp)
            }
        }
    }
    
    // New Quest Dialog
    if (showNewQuestDialog) {
        QuestEditorDialog(
            quest = null,
            color = color,
            onDismiss = { showNewQuestDialog = false },
            onSaveQuest = { quest ->
                scope.launch {
                    questRepository.saveQuest(quest)
                    showNewQuestDialog = false
                }
            }
        )
    }
    
    // Edit Quest Dialog
    if (showEditQuestDialog && questToEdit != null) {
        QuestEditorDialog(
            quest = questToEdit,
            color = color,
            onDismiss = { 
                showEditQuestDialog = false
                questToEdit = null
            },
            onSaveQuest = { quest ->
                scope.launch {
                    questRepository.saveQuest(quest)
                    showEditQuestDialog = false
                    questToEdit = null
                }
            }
        )
    }
}

/**
 * Category filter button
 */
@Composable
fun CategoryButton(
    text: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(if (isSelected) color else Color.Transparent)
            .border(1.dp, color)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = PipBoyTypography.bodyMedium,
            color = if (isSelected) Color.Black else color,
            fontSize = 10.sp
        )
    }
}

/**
 * Individual quest card
 */
@Composable
fun QuestCard(
    quest: Quest,
    color: Color,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onCompleteObjective: (String) -> Unit,
    onEditQuest: () -> Unit,
    onDeleteQuest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isExpanded) color.copy(alpha = 0.1f) else Color.Transparent)
            .border(1.dp, color.copy(alpha = 0.3f))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        // Quest Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status Icon
            Text(
                text = when (quest.status) {
                    QuestStatus.ACTIVE -> "►"
                    QuestStatus.COMPLETED -> "✓"
                    QuestStatus.FAILED -> "✗"
                    QuestStatus.LOCKED -> "🔒"
                },
                color = when (quest.status) {
                    QuestStatus.ACTIVE -> color
                    QuestStatus.COMPLETED -> Color.Green
                    QuestStatus.FAILED -> Color.Red
                    QuestStatus.LOCKED -> Color.Gray
                },
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            
            // Title and Category
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = quest.title,
                        style = PipBoyTypography.bodyMedium,
                        color = color,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "[${quest.category.name}]",
                        style = PipBoyTypography.bodyMedium,
                        color = color.copy(alpha = 0.6f),
                        fontSize = 9.sp
                    )
                }
                
                // Progress bar
                if (quest.progress > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .background(color.copy(alpha = 0.2f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(quest.progress / 100f)
                                    .background(color)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${quest.progress}%",
                            style = PipBoyTypography.bodyMedium,
                            color = color,
                            fontSize = 9.sp
                        )
                    }
                }
            }
            
            // Priority indicator
            if (quest.priority == QuestPriority.CRITICAL || quest.priority == QuestPriority.HIGH) {
                Text(
                    text = "!",
                    color = if (quest.priority == QuestPriority.CRITICAL) Color.Red else Color.Yellow,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        
        // Expanded Details
        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                // Description
                if (quest.description.isNotEmpty()) {
                    Text(
                        text = quest.description,
                        style = PipBoyTypography.bodyMedium,
                        color = color.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                
                // Objectives
                if (quest.objectives.isNotEmpty()) {
                    Text(
                        text = "OBJECTIVES:",
                        style = PipBoyTypography.bodyMedium,
                        color = color,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    
                    quest.objectives.sortedBy { it.order }.forEach { objective ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (!objective.isCompleted && quest.status == QuestStatus.ACTIVE) {
                                        onCompleteObjective(objective.id)
                                    }
                                }
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (objective.isCompleted) "✓" else "○",
                                color = if (objective.isCompleted) Color.Green else color,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = objective.description,
                                style = PipBoyTypography.bodyMedium,
                                color = if (objective.isCompleted) color.copy(alpha = 0.5f) else color,
                                fontSize = 10.sp
                            )
                            if (objective.isOptional) {
                                Text(
                                    text = " (Optional)",
                                    style = PipBoyTypography.bodyMedium,
                                    color = color.copy(alpha = 0.4f),
                                    fontSize = 8.sp
                                )
                            }
                        }
                    }
                }
                
                // Due Date
                quest.dueDate?.let { dueDate ->
                    val dateText = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.US)
                        .format(java.util.Date(dueDate))
                    Text(
                        text = "Due: $dateText",
                        style = PipBoyTypography.bodyMedium,
                        color = color.copy(alpha = 0.7f),
                        fontSize = 9.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                // Rewards
                if (quest.xpReward > 0 || quest.capReward > 0) {
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        if (quest.xpReward > 0) {
                            Text(
                                text = "XP: +${quest.xpReward}  ",
                                style = PipBoyTypography.bodyMedium,
                                color = Color.Cyan,
                                fontSize = 9.sp
                            )
                        }
                        if (quest.capReward > 0) {
                            Text(
                                text = "Caps: +${quest.capReward}",
                                style = PipBoyTypography.bodyMedium,
                                color = Color.Yellow,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
                
                // Action buttons
                if (quest.status == QuestStatus.ACTIVE) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(onClick = onEditQuest) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = color, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "EDIT",
                                color = color,
                                fontSize = 9.sp
                            )
                        }
                        TextButton(onClick = onDeleteQuest) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "DELETE",
                                color = Color.Red,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Enhanced dialog for creating/editing quests with due dates and reminders
 */
@Composable
fun QuestEditorDialog(
    quest: Quest?,
    color: Color,
    onDismiss: () -> Unit,
    onSaveQuest: (Quest) -> Unit
) {
    val isEditing = quest != null
    var title by remember { mutableStateOf(quest?.title ?: "") }
    var description by remember { mutableStateOf(quest?.description ?: "") }
    var category by remember { mutableStateOf(quest?.category ?: QuestCategory.MISC) }
    var priority by remember { mutableStateOf(quest?.priority ?: QuestPriority.NORMAL) }
    var dueDate by remember { mutableStateOf<Long?>(quest?.dueDate) }
    var hasReminder by remember { mutableStateOf(quest?.reminderTime != null) }
    var objectivesText by remember { mutableStateOf(quest?.objectives?.joinToString("\n") { it.description } ?: "") }
    var showDatePicker by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.Black,
        title = {
            Text(
                if (isEditing) "☢ EDIT QUEST" else "☢ NEW QUEST",
                style = PipBoyTypography.displayMedium,
                color = color,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Quest Title*", color = color, fontSize = 12.sp) },
                    textStyle = PipBoyTypography.bodyMedium.copy(color = color, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = color,
                        unfocusedBorderColor = color.copy(alpha = 0.5f),
                        cursorColor = color
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description", color = color, fontSize = 12.sp) },
                    textStyle = PipBoyTypography.bodyMedium.copy(color = color, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = color,
                        unfocusedBorderColor = color.copy(alpha = 0.5f),
                        cursorColor = color
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 4
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Objectives
                OutlinedTextField(
                    value = objectivesText,
                    onValueChange = { objectivesText = it },
                    label = { Text("Objectives (one per line)", color = color, fontSize = 12.sp) },
                    textStyle = PipBoyTypography.bodyMedium.copy(color = color, fontSize = 11.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = color,
                        unfocusedBorderColor = color.copy(alpha = 0.5f),
                        cursorColor = color
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 6,
                    placeholder = {
                        Text(
                            "Example:\nComplete research\nWrite report\nPresent findings",
                            color = color.copy(alpha = 0.3f),
                            fontSize = 10.sp
                        )
                    }
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Category
                Text("Category:", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    QuestCategory.values().take(4).forEach { cat ->
                        CategoryButton(
                            text = cat.name.take(4),
                            isSelected = category == cat,
                            color = color
                        ) {
                            category = cat
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Priority
                Text("Priority:", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    QuestPriority.values().forEach { pri ->
                        CategoryButton(
                            text = when(pri) {
                                QuestPriority.CRITICAL -> "CRIT"
                                QuestPriority.HIGH -> "HIGH"
                                QuestPriority.NORMAL -> "NORM"
                                QuestPriority.LOW -> "LOW"
                            },
                            isSelected = priority == pri,
                            color = when(pri) {
                                QuestPriority.CRITICAL -> Color.Red
                                QuestPriority.HIGH -> Color.Yellow
                                else -> color
                            }
                        ) {
                            priority = pri
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Due Date
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color.copy(alpha = 0.3f))
                        .clickable { showDatePicker = true }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "Due Date", tint = color, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Due Date", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (dueDate != null) {
                                java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.US)
                                    .format(java.util.Date(dueDate!!))
                            } else {
                                "No due date set"
                            },
                            color = color.copy(alpha = 0.7f),
                            fontSize = 10.sp
                        )
                    }
                    if (dueDate != null) {
                        IconButton(
                            onClick = { dueDate = null },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Red, modifier = Modifier.size(16.dp))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Reminder
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color.copy(alpha = 0.3f))
                        .clickable { if (dueDate != null) hasReminder = !hasReminder }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Checkbox(
                        checked = hasReminder && dueDate != null,
                        onCheckedChange = { if (dueDate != null) hasReminder = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = color,
                            uncheckedColor = color.copy(alpha = 0.5f),
                            checkmarkColor = Color.Black
                        ),
                        enabled = dueDate != null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Reminder", color = if (dueDate != null) color else color.copy(alpha = 0.3f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (dueDate == null) "Set due date first" else if (hasReminder) "1 day before" else "No reminder",
                            color = color.copy(alpha = 0.5f),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotEmpty()) {
                        val objectives = objectivesText.split("\n")
                            .filter { it.isNotBlank() }
                            .mapIndexed { index, text ->
                                QuestObjective(
                                    description = text.trim(),
                                    order = index,
                                    isCompleted = false
                                )
                            }
                        
                        val reminderTime = if (hasReminder && dueDate != null) {
                            dueDate!! - (24 * 60 * 60 * 1000) // 1 day before
                        } else null
                        
                        onSaveQuest(
                            Quest(
                                id = quest?.id ?: java.util.UUID.randomUUID().toString(),
                                title = title,
                                description = description,
                                category = category,
                                priority = priority,
                                status = quest?.status ?: QuestStatus.ACTIVE,
                                objectives = objectives,
                                dueDate = dueDate,
                                reminderTime = reminderTime,
                                progress = quest?.progress ?: 0,
                                xpReward = quest?.xpReward ?: when (category) {
                                    QuestCategory.MAIN -> 200
                                    QuestCategory.SIDE -> 100
                                    QuestCategory.FACTION -> 150
                                    else -> 50
                                },
                                capReward = quest?.capReward ?: when (priority) {
                                    QuestPriority.CRITICAL -> 100
                                    QuestPriority.HIGH -> 50
                                    else -> 25
                                },
                                createdAt = quest?.createdAt ?: System.currentTimeMillis()
                            )
                        )
                    }
                },
                enabled = title.isNotEmpty()
            ) {
                Text(
                    if (isEditing) "SAVE" else "CREATE",
                    color = if (title.isNotEmpty()) color else color.copy(alpha = 0.3f),
                    fontSize = 12.sp
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = Color.Red, fontSize = 12.sp)
            }
        }
    )
    
    // Simple date picker (using timestamp selection)
    if (showDatePicker) {
        SimpleDatePickerDialog(
            color = color,
            initialDate = dueDate,
            onDateSelected = { selectedDate ->
                dueDate = selectedDate
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

/**
 * Simple date picker dialog
 */
@Composable
fun SimpleDatePickerDialog(
    color: Color,
    initialDate: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = remember {
        java.util.Calendar.getInstance().apply {
            if (initialDate != null) {
                timeInMillis = initialDate
            }
        }
    }
    
    var selectedDays by remember { mutableStateOf((initialDate?.let { (it - System.currentTimeMillis()) / (24 * 60 * 60 * 1000) } ?: 7).toInt()) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.Black,
        title = {
            Text("SET DUE DATE", style = PipBoyTypography.displayMedium, color = color, fontSize = 16.sp)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Days from now:", color = color, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(1, 3, 7, 14, 30).forEach { days ->
                        CategoryButton(
                            text = "${days}d",
                            isSelected = selectedDays == days,
                            color = color
                        ) {
                            selectedDays = days
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Due: ${java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.US).format(System.currentTimeMillis() + (selectedDays * 24 * 60 * 60 * 1000L))}",
                    color = color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(System.currentTimeMillis() + (selectedDays * 24 * 60 * 60 * 1000L))
            }) {
                Text("SET", color = color, fontSize = 12.sp)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = Color.Red, fontSize = 12.sp)
            }
        }
    )
}

/**
 * Calculate player level from total XP
 */
private fun calculateLevel(xp: Int): Int {
    return (kotlin.math.sqrt(xp.toDouble() / 100.0).toInt()) + 1
}

