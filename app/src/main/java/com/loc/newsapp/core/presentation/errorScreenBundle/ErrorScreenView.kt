package com.loc.newsapp.core.presentation.errorScreenBundle

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.loc.newsapp.ui.theme.NewsAppTheme
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
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnimationValue)
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnimationValue),
            text = state.message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
        )
      }
}

@Preview(
    name = "Error Screen, server error, light mode", group = "Server Error", showBackground = true)
@Preview(
    name = "Error Screen, server error, dark mode",
    group = "Server Error",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun EmptyScreenPreviewServerError() {
  NewsAppTheme {
      Surface { ErrorScreenView(error = LoadState.Error(error = SocketTimeoutException())) }
  }
}

@Preview(
    name = "Error Screen, internet error, light mode",
    group = "Internet Error",
    showBackground = true)
@Preview(
    name = "Error Screen, internet error, dark mode",
    group = "Internet Error",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun EmptyScreenPreviewInternetError() {
    NewsAppTheme { Surface { ErrorScreenView(error = LoadState.Error(error = ConnectException())) } }
}

@Preview(
    name = "Error Screen, unknown error, light mode",
    group = "Unknown Error",
    showBackground = true)
@Preview(
    name = "Error Screen, unknown error, dark mode",
    group = "Unknown Error",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun EmptyScreenPreviewUnknownError() {
    NewsAppTheme { Surface { ErrorScreenView(error = LoadState.Error(error = Exception("UnknownError"))) } }
}

@Preview(name = "Error Screen, no error, light mode", group = "No Error", showBackground = true)
@Preview(
    name = "Error Screen, no error, dark mode",
    group = "No Error",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun EmptyScreenPreviewNoError() {
    NewsAppTheme { Surface { ErrorScreenView(error = null) } }
}
