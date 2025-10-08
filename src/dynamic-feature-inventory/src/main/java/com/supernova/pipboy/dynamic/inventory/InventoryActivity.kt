package com.supernova.pipboy.dynamic.inventory

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.supernova.pipboy.dynamic.inventory.ui.InventoryScreen
import com.supernova.pipboy.dynamic.inventory.ui.theme.InventoryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activity for the Dynamic Inventory Feature
 * Handles on-demand loading and display of inventory functionality
 */
@AndroidEntryPoint
class InventoryActivity : ComponentActivity() {

    private lateinit var splitInstallManager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splitInstallManager = SplitInstallManagerFactory.create(this)

        setContent {
            InventoryTheme {
                DynamicInventoryContent(
                    splitInstallManager = splitInstallManager,
                    intent = intent
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        recreate()
    }
}

/**
 * Main content for the dynamic inventory feature
 */
@Composable
fun DynamicInventoryContent(
    splitInstallManager: SplitInstallManager,
    intent: Intent,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isModuleReady by remember {
        mutableStateOf(splitInstallManager.installedModules.contains("inventory"))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isModuleReady) {
            // Module is installed, show inventory content
            InventoryScreen(
                initialCategory = extractCategoryFromIntent(intent),
                onNavigateBack = { (context as? InventoryActivity)?.finish() }
            )
        } else {
            // Module not installed, show installation UI
            ModuleInstallationScreen(
                splitInstallManager = splitInstallManager,
                onModuleInstalled = {
                    // Recreate activity to show content
                    (context as? InventoryActivity)?.recreate()
                }
            )
        }
    }
}

/**
 * Screen shown when the dynamic module is not installed
 */
@Composable
fun ModuleInstallationScreen(
    splitInstallManager: SplitInstallManager,
    onModuleInstalled: () -> Unit
) {
    var isInstalling by remember { mutableStateOf(false) }
    var installationProgress by remember { mutableStateOf(0f) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = "INVENTORY MODULE",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Green
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This feature requires additional components to be downloaded.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Green.copy(alpha = 0.8f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isInstalling) {
            LinearProgressIndicator(
                progress = installationProgress,
                color = Color.Green,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Downloading... ${((installationProgress * 100).toInt())}%",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Green
            )
        } else {
            errorMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = {
                    isInstalling = true
                    errorMessage = null
                    installInventoryModule(splitInstallManager, context) { success, error ->
                        isInstalling = false
                        if (success) {
                            onModuleInstalled()
                        } else {
                            errorMessage = error
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black
                )
            ) {
                Text("DOWNLOAD INVENTORY MODULE")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { (context as? InventoryActivity)?.finish() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Green
            )
        ) {
            Text("CANCEL")
        }
    }
}

/**
 * Install the inventory dynamic module
 */
private fun installInventoryModule(
    splitInstallManager: SplitInstallManager,
    context: Context,
    onComplete: (Boolean, String?) -> Unit
) {
    val request = SplitInstallRequest.newBuilder()
        .addModule("inventory")
        .build()

    splitInstallManager.registerListener { state ->
        when (state.status()) {
            com.google.android.play.core.splitinstall.SplitInstallSessionStatus.DOWNLOADING -> {
                val progress = state.bytesDownloaded().toFloat() / state.totalBytesToDownload().toFloat()
                // Update progress in UI
            }
            com.google.android.play.core.splitinstall.SplitInstallSessionStatus.INSTALLED -> {
                onComplete(true, null)
            }
            com.google.android.play.core.splitinstall.SplitInstallSessionStatus.FAILED -> {
                val errorMessage = when (state.errorCode()) {
                    com.google.android.play.core.splitinstall.SplitInstallErrorCode.NETWORK_ERROR -> "Network error occurred"
                    com.google.android.play.core.splitinstall.SplitInstallErrorCode.INSUFFICIENT_STORAGE -> "Insufficient storage space"
                    com.google.android.play.core.splitinstall.SplitInstallErrorCode.MODULE_UNAVAILABLE -> "Module temporarily unavailable"
                    else -> "Installation failed: ${state.errorCode()}"
                }
                onComplete(false, errorMessage)
            }
        }
    }

    context.lifecycleScope.launch {
        try {
            splitInstallManager.startInstall(request)
        } catch (e: Exception) {
            onComplete(false, "Failed to start installation: ${e.message}")
        }
    }
}

/**
 * Extract category from intent data
 */
private fun extractCategoryFromIntent(intent: Intent): String? {
    return intent.data?.let { uri ->
        when {
            uri.scheme == "pipboy" && uri.host == "inventory" -> {
                uri.pathSegments.firstOrNull()
            }
            else -> null
        }
    }
}
