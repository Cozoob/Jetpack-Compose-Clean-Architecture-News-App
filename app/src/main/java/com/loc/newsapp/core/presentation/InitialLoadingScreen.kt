package com.loc.newsapp.core.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.navigation.presentation.NavigationNewsApp
import com.loc.newsapp.core.presentation.components.NewsCircularProgressIndicator

@Composable
fun InitialLoadingScreenRoot(
    viewModel: InitialLoadingViewModel = hiltViewModel()
) {
    InitialLoadingScreen(state = viewModel.state)
}

@Composable
fun InitialLoadingScreen(
    state: InitialLoadingState
) {
    if(state.isLoading) {
        NewsCircularProgressIndicator()
    } else {
        NavigationNewsApp(startRoute = state.startRoute)
    }
}