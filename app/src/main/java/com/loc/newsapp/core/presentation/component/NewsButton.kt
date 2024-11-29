package com.loc.newsapp.core.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
  Button(
      onClick = onClick,
      colors =
          ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White),
      shape = RoundedCornerShape(size = 6.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
      }
}

@Preview(name = "News Button, light mode", showBackground = true)
@Preview(
    name = "News Button, dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun NewsButtonPreview() {
  NewsAppTheme { Surface { NewsButton(text = "Previous", onClick = { print("Hello world :)") }) } }
}
