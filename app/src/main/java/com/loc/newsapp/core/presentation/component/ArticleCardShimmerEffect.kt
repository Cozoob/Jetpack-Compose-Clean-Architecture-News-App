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
import com.loc.newsapp.core.domain.model.DayNightPreviews
import com.loc.newsapp.core.presentation.constant.Dimensions.ArticleCardSize
import com.loc.newsapp.core.presentation.constant.Dimensions.ExtraSmallPadding1
import com.loc.newsapp.core.presentation.constant.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.extension.shimmerEffect

@Composable
fun ArticleCardShimmerEffect(modifier: Modifier = Modifier) {
  Row(modifier = modifier) {
    Box(modifier = Modifier.size(ArticleCardSize).clip(MaterialTheme.shapes.medium).shimmerEffect())
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(horizontal = ExtraSmallPadding2).height(ArticleCardSize)) {
          Box(
              modifier =
                  Modifier.fillMaxWidth()
                      .height(30.dp)
                      .padding(horizontal = ExtraSmallPadding1)
                      .clip(MaterialTheme.shapes.extraSmall)
                      .shimmerEffect())
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier =
                    Modifier.fillMaxWidth(fraction = 0.6f)
                        .height(15.dp)
                        .padding(horizontal = ExtraSmallPadding1)
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
