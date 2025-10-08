package com.supernova.pipboy.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.supernova.pipboy.PipBoyApplication
import com.supernova.pipboy.ui.navigation.PipBoyNavHost
import com.supernova.pipboy.ui.screens.StartupScreen
import com.supernova.pipboy.ui.theme.PipBoyTheme
import com.supernova.pipboy.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private lateinit var app: PipBoyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as PipBoyApplication

        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(app.systemRepository, app.appRepository, app.preferences) as T
                    }
                }
            )

            PipBoyApp(viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when returning to launcher
        app.appRepository.refreshAppList()
        app.systemRepository.startMonitoring()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // When used as launcher, back button should move task to background instead of closing
        // This makes it behave like a home screen
        moveTaskToBack(true)
    }
}

@Composable
fun PipBoyApp(viewModel: MainViewModel) {
    val primaryColor by viewModel.primaryColor.collectAsState()
    val crtEffects by viewModel.crtEffects.collectAsState()
    var showStartup by remember { mutableStateOf(true) }

    PipBoyTheme(
        primaryColor = primaryColor,
        crtEffects = crtEffects
    ) {
        if (showStartup) {
            StartupScreen(
                onStartupComplete = { showStartup = false },
                primaryColor = primaryColor
            )
        } else {
            PipBoyNavHost(viewModel = viewModel)
        }
    }
}
