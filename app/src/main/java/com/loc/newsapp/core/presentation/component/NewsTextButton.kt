package com.loc.newsapp.core.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.ui.theme.WhiteGray

@Composable
fun NewsTextButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
  TextButton(modifier = modifier, onClick = onClick) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
        color = WhiteGray)
  }
}

@DayNightPreviews
@Composable
private fun NewsText_Previous_Preview() {
  NewsAppPreviewSurface(content = { NewsTextButton(text = "Previous", onClick = {}) })
}
