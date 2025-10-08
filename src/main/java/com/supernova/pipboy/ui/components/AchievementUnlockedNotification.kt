package com.supernova.pipboy.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.achievements.Achievement
import com.supernova.pipboy.data.achievements.AchievementTier
import kotlinx.coroutines.delay

/**
 * Achievement unlock notification that appears at the top of the screen
 */
@Composable
fun AchievementUnlockedNotification(
    achievement: Achievement?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val visible = achievement != null
    
    // Auto-dismiss after 5 seconds
    LaunchedEffect(achievement) {
        if (achievement != null) {
            delay(5000)
            onDismiss()
        }
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeOut()
    ) {
        if (achievement != null) {
            AchievementCard(
                achievement = achievement,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement,
    modifier: Modifier = Modifier
) {
    // Get tier color
    val tierColor = Color(achievement.tier.color)
    
    // Pulsing glow effect
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )
    
    // Scale animation on appear
    var scale by remember { mutableStateOf(0.8f) }
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0.8f,
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) { value, _ ->
            scale = value
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .scale(scale)
    ) {
        // Outer glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(tierColor.copy(alpha = glowAlpha * 0.3f))
                .padding(2.dp)
        ) {
            // Main card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.95f))
                    .border(2.dp, tierColor)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // "ACHIEVEMENT UNLOCKED" header
                Text(
                    text = "⚡ ACHIEVEMENT UNLOCKED ⚡",
                    color = tierColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Achievement name
                Text(
                    text = achievement.title.uppercase(),
                    color = tierColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Tier badge
                Text(
                    text = "[${achievement.tier.name}]",
                    color = tierColor.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description
                Text(
                    text = achievement.description,
                    color = Color(0xFF00FF00).copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // XP reward
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "+${achievement.xpReward}",
                        color = Color(0xFFFFD700),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "XP",
                        color = Color(0xFFFFD700).copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Simple toast-style notification for achievements
 */
@Composable
fun AchievementToast(
    achievement: Achievement?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val visible = achievement != null
    
    // Auto-dismiss after 3 seconds
    LaunchedEffect(achievement) {
        if (achievement != null) {
            delay(3000)
            onDismiss()
        }
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(200)
        ) + fadeOut()
    ) {
        if (achievement != null) {
            val tierColor = Color(achievement.tier.color)
            
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.9f))
                    .border(1.dp, tierColor)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🏆 ${achievement.title}",
                        color = tierColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "+${achievement.xpReward} XP",
                        color = Color(0xFFFFD700),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

