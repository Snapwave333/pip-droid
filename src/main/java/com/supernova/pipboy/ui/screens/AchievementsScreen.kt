package com.supernova.pipboy.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.achievements.Achievement
import com.supernova.pipboy.data.achievements.AchievementCategory
import com.supernova.pipboy.data.achievements.AchievementManager
import com.supernova.pipboy.data.achievements.AchievementTier
import com.supernova.pipboy.ui.components.VaultBoyAchievementIcon
import com.supernova.pipboy.ui.components.VaultBoyIcons
import com.supernova.pipboy.ui.components.clickableWithSound
import com.supernova.pipboy.audio.SoundEffect

@Composable
fun AchievementsScreen(
    achievementManager: AchievementManager,
    modifier: Modifier = Modifier
) {
    val achievements by achievementManager.achievements.collectAsState()
    val unlockedCount by achievementManager.unlockedCount.collectAsState()
    val totalXP by achievementManager.totalXP.collectAsState()
    
    var selectedCategory by remember { mutableStateOf<AchievementCategory?>(null) }
    var selectedTier by remember { mutableStateOf<AchievementTier?>(null) }
    
    // Filter achievements
    val filteredAchievements = achievements.filter { achievement ->
        (selectedCategory == null || achievement.category == selectedCategory) &&
        (selectedTier == null || achievement.tier == selectedTier)
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "ACHIEVEMENTS",
            color = Color(0xFF00FF00),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Statistics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF00FF00).copy(alpha = 0.5f))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "UNLOCKED",
                value = "$unlockedCount / ${achievements.size}",
                color = Color(0xFF00FF00)
            )
            StatItem(
                label = "TOTAL XP",
                value = totalXP.toString(),
                color = Color(0xFFFFD700)
            )
            StatItem(
                label = "PROGRESS",
                value = "${achievementManager.getCompletionPercentage().toInt()}%",
                color = Color(0xFF00BFFF)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Category filter
        Text(
            text = "CATEGORY",
            color = Color(0xFF00FF00).copy(alpha = 0.7f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                label = "ALL",
                selected = selectedCategory == null,
                onClick = { selectedCategory = null }
            )
            AchievementCategory.entries.forEach { category ->
                FilterChip(
                    label = category.name,
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = if (selectedCategory == category) null else category }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Tier filter
        Text(
            text = "RARITY",
            color = Color(0xFF00FF00).copy(alpha = 0.7f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                label = "ALL",
                selected = selectedTier == null,
                onClick = { selectedTier = null }
            )
            AchievementTier.entries.forEach { tier ->
                FilterChip(
                    label = tier.name,
                    selected = selectedTier == tier,
                    onClick = { selectedTier = if (selectedTier == tier) null else tier },
                    color = Color(tier.color)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Achievement list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredAchievements) { achievement ->
                AchievementCard(achievement = achievement)
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = color.copy(alpha = 0.6f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            text = value,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    color: Color = Color(0xFF00FF00),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickableWithSound(
                soundEffect = SoundEffect.BUTTON_CLICK,
                onClick = onClick
            )
            .background(
                if (selected) color.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) color else color.copy(alpha = 0.3f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (selected) color else color.copy(alpha = 0.6f),
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement,
    modifier: Modifier = Modifier
) {
    val tierColor = Color(achievement.tier.color)
    val alpha = if (achievement.isUnlocked) 1f else 0.4f

    // Get Vault Boy pose based on achievement category
    val vaultBoyPose = when (achievement.category) {
        AchievementCategory.GENERAL -> VaultBoyIcons.VaultBoyPose.THUMBS_UP
        AchievementCategory.TERMINAL -> VaultBoyIcons.VaultBoyPose.THINKING
        AchievementCategory.QUESTS -> VaultBoyIcons.VaultBoyPose.EXCITED
        AchievementCategory.STATS -> VaultBoyIcons.VaultBoyPose.COOL
        AchievementCategory.RADIO -> VaultBoyIcons.VaultBoyPose.PEACE_SIGN
        AchievementCategory.EXPLORATION -> VaultBoyIcons.VaultBoyPose.SALUTE
        AchievementCategory.SPECIAL -> VaultBoyIcons.VaultBoyPose.HAPPY
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha)
            .background(Color.Black.copy(alpha = 0.6f))
            .border(
                width = if (achievement.isUnlocked) 2.dp else 1.dp,
                color = if (achievement.isUnlocked) tierColor else tierColor.copy(alpha = 0.3f)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Vault Boy Icon
        VaultBoyAchievementIcon(
            pose = vaultBoyPose,
            size = 48.dp,
            isUnlocked = achievement.isUnlocked,
            modifier = Modifier.padding(end = 12.dp)
        )

        // Achievement details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = achievement.title,
                style = PipBoyTypography.displayMedium,
                color = if (achievement.isUnlocked) tierColor else tierColor.copy(alpha = 0.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = achievement.description,
                style = PipBoyTypography.bodyMedium,
                color = if (achievement.isUnlocked) tierColor.copy(alpha = 0.8f) else tierColor.copy(alpha = 0.4f),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            // XP and category
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${achievement.xpReward} XP",
                    style = PipBoyTypography.bodyMedium,
                    color = if (achievement.isUnlocked) Color.Yellow else Color.Yellow.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = achievement.category.name,
                    style = PipBoyTypography.bodyMedium,
                    color = if (achievement.isUnlocked) tierColor.copy(alpha = 0.7f) else tierColor.copy(alpha = 0.3f),
                    fontSize = 10.sp
                )
            }
        }
    }
}

