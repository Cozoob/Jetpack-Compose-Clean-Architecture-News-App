package com.loc.newsapp.core.presentation.components

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.loc.newsapp.ui.theme.NewsAppTheme
import com.loc.newsapp.ui.theme.WhiteGray

@Composable
fun NewsTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = WhiteGray
        )
    }
}

@Preview(name = "News Text Button, light mode", showBackground = true)
@Preview(name = "News Text Button, dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun NewsTextPreview() {
    NewsAppTheme {
        Surface {
            NewsTextButton(
                text = "Previous",
                onClick = {
                    print("Hello world :)")
                }
            )
        }
    }
}