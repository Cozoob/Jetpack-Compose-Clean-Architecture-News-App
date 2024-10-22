package com.loc.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.loc.newsapp.boarding.presentation.OnBoardingScreenRoot
import com.loc.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
        installSplashScreen()
        setContent {
            NewsAppTheme {
                OnBoardingScreenRoot()
            }
        }
    }
}
