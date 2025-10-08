package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import com.supernova.pipboy.data.stats.SpecialProfile
import com.supernova.pipboy.data.stats.StatValue
import com.supernova.pipboy.data.stats.StatsRepository
import com.supernova.pipboy.ui.components.PipBoyIDBadge
import com.supernova.pipboy.ui.components.VaultBoyIcons
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * Status Screen - Displays S.P.E.C.I.A.L. stats based on phone usage
 */
@Composable
fun StatusScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    initialSection: String = "overview",
    highlightedItemId: String = ""
) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val context = LocalContext.current
    val preferences = remember { PipBoyPreferences(context) }
    val statsRepository = remember { StatsRepository(context) }
    val scope = rememberCoroutineScope()

    var specialProfile by remember { mutableStateOf<SpecialProfile?>(null) }
    
    // Load stats
    LaunchedEffect(Unit) {
        statsRepository.getSpecialProfileFlow().collect { profile ->
            specialProfile = profile
        }
    }
    
    // Update stats on screen load
    LaunchedEffect(Unit) {
        scope.launch {
            statsRepository.updateStats()
        }
    }
    
    val color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "☢ S.P.E.C.I.A.L.",
            style = PipBoyTypography.displayLarge,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        specialProfile?.let { profile ->
            // Overall stats bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, color)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Total Level: ${profile.totalLevel}", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Total XP: ${profile.overallXp}", color = color.copy(alpha = 0.7f), fontSize = 10.sp)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Vault Dweller", color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Status: Active", color = Color.Green, fontSize = 10.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            // ID Badge (if visible)
            if (preferences.idBadgeVisible) {
                PipBoyIDBadge(
                    name = preferences.idBadgeName,
                    vaultNumber = preferences.idBadgeVaultNumber,
                    rank = preferences.idBadgeRank,
                    primaryColor = color,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // S.P.E.C.I.A.L. stats list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(profile.getAllStats()) { stat ->
                    StatCard(stat = stat, color = color)
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Initializing S.P.E.C.I.A.L. system...",
                    color = color,
                    style = PipBoyTypography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun StatCard(stat: StatValue, color: Color) {
    val vaultBoyPose = when (stat.stat) {
        com.supernova.pipboy.data.stats.SpecialStat.STRENGTH -> VaultBoyIcons.VaultBoyPose.THUMBS_UP
        com.supernova.pipboy.data.stats.SpecialStat.PERCEPTION -> VaultBoyIcons.VaultBoyPose.SALUTE
        com.supernova.pipboy.data.stats.SpecialStat.ENDURANCE -> VaultBoyIcons.VaultBoyPose.HAPPY
        com.supernova.pipboy.data.stats.SpecialStat.CHARISMA -> VaultBoyIcons.VaultBoyPose.PEACE_SIGN
        com.supernova.pipboy.data.stats.SpecialStat.INTELLIGENCE -> VaultBoyIcons.VaultBoyPose.THINKING
        com.supernova.pipboy.data.stats.SpecialStat.AGILITY -> VaultBoyIcons.VaultBoyPose.EXCITED
        com.supernova.pipboy.data.stats.SpecialStat.LUCK -> VaultBoyIcons.VaultBoyPose.COOL
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, color.copy(alpha = 0.5f))
            .background(color.copy(alpha = 0.05f))
            .padding(12.dp)
    ) {
        // Stat name, level, and Vault Boy icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.supernova.pipboy.ui.components.VaultBoyIcon(
                    pose = vaultBoyPose,
                    size = 32.dp,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Text(
                    text = stat.stat.name,
                    style = PipBoyTypography.displayMedium,
                    color = color,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "${stat.level}",
                style = PipBoyTypography.displayLarge,
                color = color,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Description
        Text(
            text = stat.description,
            style = PipBoyTypography.bodyMedium,
            color = color.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // XP Progress bar
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "XP: ${stat.experience}",
                    color = color.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
                if (stat.level < 10) {
                    Text(
                        text = "Next: ${stat.nextLevelXp}",
                        color = color.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                } else {
                    Text(
                        text = "MAX LEVEL",
                        color = Color.Yellow,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            if (stat.level < 10) {
                val currentLevelXp = stat.experience - com.supernova.pipboy.data.stats.StatCalculations.totalXpForLevel(stat.level)
                val progress = (currentLevelXp.toFloat() / stat.nextLevelXp.toFloat()).coerceIn(0f, 1f)
                
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = color,
                    trackColor = color.copy(alpha = 0.2f)
                )
            }
        }
        
        // Effects
        if (stat.effects.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Effects:",
                color = color,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            stat.effects.forEach { effect ->
                Text(
                    text = "• $effect",
                    color = Color.Cyan,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }
        }
    }
}
