package com.loc.newsapp.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.loc.newsapp.core.constant.DimensionConstants.ARTICLE_CARD_SIZE
import com.loc.newsapp.core.constant.DimensionConstants.EXTRA_SMALL_PADDING
import com.loc.newsapp.core.constant.DimensionConstants.SMALL_PADDING
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.presentation.extension.shimmerEffect

@Composable
fun ArticleCardShimmerEffect(modifier: Modifier = Modifier) {
  Row(modifier = modifier) {
    Box(
        modifier =
            Modifier.size(ARTICLE_CARD_SIZE).clip(MaterialTheme.shapes.medium).shimmerEffect())
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(horizontal = SMALL_PADDING).height(ARTICLE_CARD_SIZE)) {
          Box(
              modifier =
                  Modifier.fillMaxWidth()
                      .height(30.dp)
                      .padding(horizontal = EXTRA_SMALL_PADDING)
                      .clip(MaterialTheme.shapes.extraSmall)
                      .shimmerEffect())
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier.fillMaxWidth(fraction = 0.6f)
                        .height(15.dp)
                        .padding(horizontal = EXTRA_SMALL_PADDING)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .shimmerEffect())
          }
        }
  }
}

@DayNightPreviews
@Composable
private fun ArticleCardShimmerEffect_Default_Preview() {
  NewsAppPreviewSurface(content = { ArticleCardShimmerEffect() })
}
