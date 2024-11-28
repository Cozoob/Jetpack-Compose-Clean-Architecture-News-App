package com.loc.newsapp.boarding.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.loc.newsapp.core.presentation.constants.Dimensions.IndicatorSize
import com.loc.newsapp.ui.theme.BlueGray
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    numberOfPages: Int,
    selectedPageIndex: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = BlueGray
) {
  Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
    repeat(numberOfPages) { pageIndex ->
      val backgroundColor = if (pageIndex == selectedPageIndex) selectedColor else unselectedColor

      Box(
          modifier =
              Modifier.size(IndicatorSize).clip(CircleShape).background(color = backgroundColor))
    }
  }
}

@Preview(name = "Page Indicator, light mode", showBackground = true)
@Preview(
    name = "Page Indicator, dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun PageIndicatorPreview() {
  NewsAppTheme { Surface { PageIndicator(numberOfPages = 5, selectedPageIndex = 2) } }
}
