package com.loc.newsapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loc.newsapp.boarding.presentation.OnBoardingScreenRoot
import com.loc.newsapp.core.domain.routes.HomeScreenRoute
import com.loc.newsapp.core.domain.routes.IRoute
import com.loc.newsapp.core.domain.routes.OnBoardingScreenRoute
import com.loc.newsapp.home.presentation.HomeScreenRoot

@Composable
fun NavigationNewsApp(startRoute: IRoute) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable<OnBoardingScreenRoute> {
            OnBoardingScreenRoot()
        }
        composable<HomeScreenRoute> {
            HomeScreenRoot()
        }
    }
}