package com.loc.newsapp.boarding.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.loc.newsapp.core.constant.DimensionConstants.INDICATOR_SIZE
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.ui.theme.BlueGray

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
              Modifier.size(INDICATOR_SIZE).clip(CircleShape).background(color = backgroundColor))
    }
  }
}

@DayNightPreviews
@Composable
private fun PageIndicator_SecondPageOutOfFive_Preview() {
  NewsAppPreviewSurface(content = { PageIndicator(numberOfPages = 5, selectedPageIndex = 2) })
}
