package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.data.achievements.AchievementEvent
import com.supernova.pipboy.data.achievements.AchievementManager
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * C.A.M.P. (Construction and Assembly Mobile Platform) Screen
 * Main desktop hub styled as a Pip-Boy interface
 */
@Composable
fun CampScreen(
    viewModel: MainViewModel,
    achievementManager: AchievementManager
) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val context = LocalContext.current
    val preferences = remember { PipBoyPreferences(context) }
    val app = context.applicationContext as PipBoyApplication
    val scope = rememberCoroutineScope()

    var isBuildMode by remember { mutableStateOf(preferences.campBuildModeEnabled) }
    var buildBudget by remember { mutableIntStateOf(100) }

    val color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)

    // Save build mode state when it changes
    LaunchedEffect(isBuildMode) {
        preferences.campBuildModeEnabled = isBuildMode
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "☢ C.A.M.P. - Construction and Assembly Mobile Platform",
            style = PipBoyTypography.displayLarge,
            color = color,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Blueprint Status
        Text(
            text = "Current Blueprint: ${preferences.currentBlueprintName}",
            style = PipBoyTypography.bodyMedium,
            color = color.copy(alpha = 0.8f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Status Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray.copy(alpha = 0.3f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "C.A.M.P. Integrity: 92%",
                        style = PipBoyTypography.bodyMedium,
                        color = Color.Green,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Build Budget: $buildBudget units",
                        style = PipBoyTypography.bodyMedium,
                        color = color.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                }

                // Build Mode Toggle
                Button(
                    onClick = {
                        isBuildMode = !isBuildMode
                        scope.launch {
                            if (isBuildMode) {
                                app.achievementManager.trackEvent(AchievementEvent.CustomEvent("build_mode_entered"))
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBuildMode) Color.Yellow else color
                    )
                ) {
                    Text(
                        text = if (isBuildMode) "🔧 EXIT BUILD MODE" else "🔨 ENTER BUILD MODE",
                        style = PipBoyTypography.bodyMedium,
                        color = if (isBuildMode) Color.Black else Color.Black,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // C.A.M.P. Modules Grid
        if (isBuildMode) {
            // Build Mode - Grid Layout
            BuildModeGrid(
                buildBudget = buildBudget,
                onBudgetChange = { buildBudget = it },
                primaryColor = color
            )
        } else {
            // Normal Mode - Widget Layout
            CampWidgetLayout(
                primaryColor = color,
                onModuleClick = { moduleName ->
                    scope.launch {
                        app.achievementManager.trackEvent(
                            AchievementEvent.CampModuleUsed(moduleName)
                        )
                    }
                }
            )
        }
    }
}

/**
 * Build Mode - Grid layout for placing modules
 */
@Composable
fun BuildModeGrid(
    buildBudget: Int,
    onBudgetChange: (Int) -> Unit,
    primaryColor: Color
) {
    val availableModules = listOf(
        CampModule("Radio Terminal", "🎵", "Stream Fallout radio stations", 20),
        CampModule("Inventory Wall", "📦", "App shortcuts and inventory", 25),
        CampModule("Workshop Bench", "🔧", "Settings and customization", 30),
        CampModule("Map Table", "🗺️", "GPS and location services", 15),
        CampModule("Vault Status", "📊", "System stats and monitoring", 10),
        CampModule("Weather Station", "🌤️", "Weather and time display", 5)
    )

    Column {
        Text(
            text = "BUILD MODE - Drag modules to place them",
            style = PipBoyTypography.displayMedium,
            color = Color.Yellow,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Build Budget Bar
        LinearProgressIndicator(
            progress = buildBudget / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(vertical = 8.dp),
            color = primaryColor,
            trackColor = Color.DarkGray
        )

        Text(
            text = "Budget: $buildBudget/100 units",
            style = PipBoyTypography.bodyMedium,
            color = primaryColor,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Module Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availableModules) { module ->
                CampModuleCard(
                    module = module,
                    primaryColor = primaryColor,
                    isBuildMode = true,
                    onClick = {
                        if (buildBudget >= module.cost) {
                            onBudgetChange(buildBudget - module.cost)
                        }
                    }
                )
            }
        }
    }
}

/**
 * Normal Mode - Widget layout
 */
@Composable
fun CampWidgetLayout(
    primaryColor: Color,
    onModuleClick: (String) -> Unit
) {
    val modules = listOf(
        CampModule("Radio Terminal", "🎵", "Diamond City Radio", 0),
        CampModule("Inventory Wall", "📦", "App shortcuts", 0),
        CampModule("Workshop Bench", "🔧", "Settings panel", 0),
        CampModule("Map Table", "🗺️", "GPS location", 0),
        CampModule("Vault Status", "📊", "System monitor", 0),
        CampModule("Weather Station", "🌤️", "Current weather", 0)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(modules) { module ->
            CampModuleCard(
                module = module,
                primaryColor = primaryColor,
                isBuildMode = false,
                onClick = { onModuleClick(module.name) }
            )
        }
    }
}

/**
 * Individual C.A.M.P. module card
 */
@Composable
fun CampModuleCard(
    module: CampModule,
    primaryColor: Color,
    isBuildMode: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() }
            .border(
                width = if (isBuildMode) 2.dp else 1.dp,
                color = if (isBuildMode) Color.Yellow else primaryColor,
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.4f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = module.icon,
                style = PipBoyTypography.displayLarge,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = module.name,
                style = PipBoyTypography.displayMedium,
                color = primaryColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            if (module.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = module.description,
                    style = PipBoyTypography.bodyMedium,
                    color = primaryColor.copy(alpha = 0.7f),
                    fontSize = 8.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            if (isBuildMode && module.cost > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cost: ${module.cost}",
                    style = PipBoyTypography.bodyMedium,
                    color = Color.Yellow,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Data class for C.A.M.P. modules
 */
data class CampModule(
    val name: String,
    val icon: String,
    val description: String,
    val cost: Int
)
