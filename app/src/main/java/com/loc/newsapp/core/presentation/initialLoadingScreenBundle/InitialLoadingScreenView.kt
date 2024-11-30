package com.loc.newsapp.core.presentation.initialLoadingScreenBundle

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.core.domain.model.DayNightInSystemUiPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.presentation.component.NewsCircularProgressIndicator
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

@DayNightInSystemUiPreviews
@Composable
private fun InitialLoadingScreenView_Loading_Preview() {
  NewsAppPreviewSurface(
      content = { InitialLoadingScreenViewContent(state = InitialLoadingScreenState()) })
}

@DayNightInSystemUiPreviews
@Composable
private fun InitialLoadingScreenView_HomeRoute_Preview() {
  NewsAppPreviewSurface(
      content = {
        InitialLoadingScreenViewContent(state = InitialLoadingScreenState(isLoading = false))
      })
}
