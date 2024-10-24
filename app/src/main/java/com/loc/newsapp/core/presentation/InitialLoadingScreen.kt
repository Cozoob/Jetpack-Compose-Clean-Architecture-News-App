package com.loc.newsapp.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.NavigationNewsApp

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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        NavigationNewsApp(startRoute = state.startRoute)
    }
}