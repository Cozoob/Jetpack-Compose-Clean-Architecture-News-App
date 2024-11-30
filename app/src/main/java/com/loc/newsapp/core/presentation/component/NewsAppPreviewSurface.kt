package com.loc.newsapp.core.presentation.component

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.loc.newsapp.core.domain.model.DayNightPreviews
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsAppPreviewSurface(content: @Composable () -> Unit) {
  NewsAppTheme { Surface { content() } }
}

@DayNightPreviews
@Composable
private fun NewsAppPreviewSurface_HelloWorld_Preview() {
  NewsAppPreviewSurface(content = { Text(text = "Hello World!") })
}
