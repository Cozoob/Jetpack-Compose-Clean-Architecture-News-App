package com.loc.newsapp.core.presentation.initialLoadingScreenBundle

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.core.presentation.components.NewsCircularProgressIndicator
import com.loc.newsapp.navigation.presentation.NavigationNewsApp

@Composable
fun InitialLoadingScreenView(viewModel: InitialLoadingScreenViewModel = hiltViewModel()) {
  InitialLoadingScreenViewContent(state = viewModel.state)
}

@Composable
private fun InitialLoadingScreenViewContent(state: InitialLoadingScreenState) {
  if (state.isLoading) {
    NewsCircularProgressIndicator()
  } else {
    NavigationNewsApp(startRoute = state.startRoute)
  }
}
