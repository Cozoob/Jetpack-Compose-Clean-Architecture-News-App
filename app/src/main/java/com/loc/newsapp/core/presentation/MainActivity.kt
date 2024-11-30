package com.loc.newsapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.loc.newsapp.core.presentation.initialLoadingScreenBundle.InitialLoadingScreenView
import com.loc.newsapp.core.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    installSplashScreen()
    setContent {
      NewsAppTheme {
        val isSystemInDarkMode = isSystemInDarkTheme()
        val systemUiController = rememberSystemUiController()

        SideEffect {
          systemUiController.setSystemBarsColor(
              color = Color.Transparent, darkIcons = !isSystemInDarkMode)
        }

        Surface { InitialLoadingScreenView() }
      }
    }
  }
}
