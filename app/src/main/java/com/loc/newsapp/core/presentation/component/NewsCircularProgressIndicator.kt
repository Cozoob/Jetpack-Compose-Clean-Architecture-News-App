package com.loc.newsapp.core.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.loc.newsapp.core.domain.model.DayNightPreviews

@Composable
fun NewsCircularProgressIndicator(modifier: Modifier = Modifier) {
  Box(modifier = Modifier.fillMaxSize().then(modifier), contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
  }
}

@DayNightPreviews
@Composable
private fun NewsCircularProgressIndicator_Default_Preview() {
  NewsAppPreviewSurface(content = { NewsCircularProgressIndicator() })
}
