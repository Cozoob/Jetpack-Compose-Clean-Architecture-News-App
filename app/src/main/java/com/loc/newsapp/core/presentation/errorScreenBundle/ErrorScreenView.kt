package com.loc.newsapp.core.presentation.errorScreenBundle

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.loc.newsapp.core.domain.annotation.DayNightInSystemUiPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ErrorScreenView(error: LoadState.Error? = null) {
  val viewModel = ErrorScreenViewModel(error = error)

  ErrorScreenViewContent(state = viewModel.state)
}

@Composable
private fun ErrorScreenViewContent(state: ErrorScreenState) {
  val alphaAnimation = rememberInfiniteTransition(label = "Error Animation")
  val alphaAnimationValue by
      alphaAnimation.animateFloat(
          initialValue = 0.3f,
          targetValue = 0f,
          animationSpec =
              infiniteRepeatable(
                  animation = tween(durationMillis = 2000), repeatMode = RepeatMode.Reverse),
          label = "Error Animation")

  Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {
        Icon(
            painter = painterResource(id = state.icon),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier = Modifier.size(120.dp).alpha(alphaAnimationValue))
        Text(
            modifier = Modifier.padding(10.dp).alpha(alphaAnimationValue),
            text = state.message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
        )
      }
}

@DayNightInSystemUiPreviews
@Composable
private fun ErrorScreenView_ServerError_Preview() {
  NewsAppPreviewSurface(
      content = { ErrorScreenView(error = LoadState.Error(error = SocketTimeoutException())) })
}

@DayNightInSystemUiPreviews
@Composable
private fun ErrorScreenView_InternetError_Preview() {
  NewsAppPreviewSurface(
      content = { ErrorScreenView(error = LoadState.Error(error = ConnectException())) })
}

@DayNightInSystemUiPreviews
@Composable
private fun ErrorScreenView_UnknownError_Preview() {
  NewsAppPreviewSurface(
      content = { ErrorScreenView(error = LoadState.Error(error = Exception("UnknownError"))) })
}

@DayNightInSystemUiPreviews
@Composable
private fun ErrorScreenView_NoError_Preview() {
  NewsAppPreviewSurface(content = { ErrorScreenView(error = null) })
}
