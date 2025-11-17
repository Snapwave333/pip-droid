package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.supernova.pipboy.data.model.AppCategory
import com.supernova.pipboy.data.model.AppInfo
import com.supernova.pipboy.ui.components.MonochromeIcon
import com.supernova.pipboy.ui.theme.PipBoyTypography
import com.supernova.pipboy.ui.viewmodel.MainViewModel

/**
 * INVENTORY tab - Application directory and management
 */
@Composable
fun InventoryScreen(viewModel: MainViewModel) {
    val filteredApps by viewModel.filteredApps.collectAsState(emptyMap())
    val favoriteApps by viewModel.favoriteApps.collectAsState()
    val recentApps by viewModel.recentApps.collectAsState()
    val primaryColor by viewModel.primaryColor.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        androidx.compose.material3.OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholder = {
                androidx.compose.material3.Text(
                    "SEARCH INVENTORY...",
                    color = primaryColor.toComposeColor().copy(alpha = 0.5f)
                )
            },
            textStyle = PipBoyTypography.bodyMedium.copy(color = primaryColor.toComposeColor()),
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = primaryColor.toComposeColor(),
                unfocusedIndicatorColor = primaryColor.toComposeColor().copy(alpha = 0.5f),
                cursorColor = primaryColor.toComposeColor()
            ),
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    androidx.compose.material3.IconButton(
                        onClick = { viewModel.clearSearch() }
                    ) {
                        androidx.compose.material3.Text(
                            "X",
                            color = primaryColor.toComposeColor(),
                            style = PipBoyTypography.bodyMedium
                        )
                    }
                }
            },
            singleLine = true
        )

        // Recent Apps Section
        if (recentApps.isNotEmpty() && searchQuery.isEmpty()) {
            androidx.compose.material3.Text(
                text = "RECENT ACTIVITY",
                style = PipBoyTypography.bodyLarge,
                color = primaryColor.toComposeColor(),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recentApps.take(8)) { app ->
                    FavoriteAppItem(
                        app = app,
                        primaryColor = primaryColor,
                        onClick = { viewModel.launchApp(app.packageName) },
                        onToggleFavorite = { viewModel.toggleFavoriteApp(app.packageName) }
                    )
                }
            }
        }

        // Favorites Dock
        androidx.compose.material3.Text(
            text = "FAVORITES/REQUISITIONED TOOLS",
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoriteApps.take(5)) { app ->
                FavoriteAppItem(
                    app = app,
                    primaryColor = primaryColor,
                    onClick = { viewModel.launchApp(app.packageName) },
                    onToggleFavorite = { viewModel.toggleFavoriteApp(app.packageName) }
                )
            }
        }

        // App Categories
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // WEAPONS Category
            filteredApps[AppCategory.GAME]?.let { weapons ->
                item {
                    AppCategorySection(
                        title = "WEAPONS",
                        apps = weapons,
                        primaryColor = primaryColor,
                        onAppClick = { viewModel.launchApp(it.packageName) },
                        onToggleFavorite = { viewModel.toggleFavoriteApp(it.packageName) },
                        favoriteApps = favoriteApps
                    )
                }
            }

            // AID Category
            filteredApps[AppCategory.UTILITIES]?.let { aid ->
                item {
                    AppCategorySection(
                        title = "AID",
                        apps = aid,
                        primaryColor = primaryColor,
                        onAppClick = { viewModel.launchApp(it.packageName) },
                        onToggleFavorite = { viewModel.toggleFavoriteApp(it.packageName) },
                        favoriteApps = favoriteApps
                    )
                }
            }

            // MISC Category
            filteredApps[AppCategory.OTHER]?.let { misc ->
                item {
                    AppCategorySection(
                        title = "MISC",
                        apps = misc,
                        primaryColor = primaryColor,
                        onAppClick = { viewModel.launchApp(it.packageName) },
                        onToggleFavorite = { viewModel.toggleFavoriteApp(it.packageName) },
                        favoriteApps = favoriteApps
                    )
                }
            }
        }
    }
}

/**
 * Favorite app item in the dock
 */
@Composable
private fun FavoriteAppItem(
    app: AppInfo,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp)
    ) {
        // Display actual app icon with monochrome effect
        Box(
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            app.icon?.let { drawable ->
                MonochromeIcon(
                    painter = BitmapPainter(drawable.toBitmap().asImageBitmap()),
                    contentDescription = app.name,
                    tint = primaryColor.toComposeColor(),
                    modifier = Modifier.size(48.dp)
                )
            } ?: Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(primaryColor.red, primaryColor.green, primaryColor.blue))
            )
        }

        androidx.compose.material3.Text(
            text = app.name.take(8), // Truncate long names
            style = PipBoyTypography.labelLarge.copy(fontSize = 8.sp),
            color = primaryColor.toComposeColor(),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

/**
 * App category section
 */
@Composable
private fun AppCategorySection(
    title: String,
    apps: List<AppInfo>,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor,
    onAppClick: (AppInfo) -> Unit,
    onToggleFavorite: (AppInfo) -> Unit,
    favoriteApps: List<AppInfo>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        androidx.compose.material3.Text(
            text = title,
            style = PipBoyTypography.bodyLarge,
            color = primaryColor.toComposeColor()
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            apps.forEach { app ->
                AppListItem(
                    app = app,
                    primaryColor = primaryColor,
                    isFavorite = favoriteApps.any { it.packageName == app.packageName },
                    onAppClick = { onAppClick(app) },
                    onToggleFavorite = { onToggleFavorite(app) }
                )
            }
        }
    }
}

/**
 * Individual app list item
 */
@Composable
private fun AppListItem(
    app: AppInfo,
    primaryColor: com.supernova.pipboy.data.model.PipBoyColor,
    isFavorite: Boolean,
    onAppClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray.copy(alpha = 0.3f))
            .clickable(onClick = onAppClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Display actual app icon with monochrome effect
        Box(
            modifier = Modifier.size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            app.icon?.let { drawable ->
                MonochromeIcon(
                    painter = BitmapPainter(drawable.toBitmap().asImageBitmap()),
                    contentDescription = app.name,
                    tint = primaryColor.toComposeColor(),
                    modifier = Modifier.size(32.dp)
                )
            } ?: Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(primaryColor.red, primaryColor.green, primaryColor.blue))
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            androidx.compose.material3.Text(
                text = app.name,
                style = PipBoyTypography.bodyMedium,
                color = primaryColor.toComposeColor()
            )

            androidx.compose.material3.Text(
                text = app.packageName,
                style = PipBoyTypography.labelLarge.copy(fontSize = 10.sp),
                color = primaryColor.toComposeColor().copy(alpha = 0.7f)
            )
        }

        // Favorite indicator
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    if (isFavorite) primaryColor.toComposeColor() else Color.Gray.copy(alpha = 0.3f),
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .clickable(onClick = onToggleFavorite)
        )
    }
}
