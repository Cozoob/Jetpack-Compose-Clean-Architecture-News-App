package com.loc.newsapp.articleDetails.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.loc.newsapp.R
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsTopBar(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean = false,
    onBrowsingClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onBookMarkClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
  TopAppBar(
      modifier = Modifier.fillMaxWidth().then(modifier),
      colors =
          TopAppBarDefaults.topAppBarColors(
              containerColor = Color.Transparent,
              actionIconContentColor = colorResource(id = R.color.body),
              navigationIconContentColor = colorResource(id = R.color.body)),
      title = {},
      navigationIcon = {
        IconButton(onClick = onBackClick) {
          Icon(
              painter = painterResource(id = R.drawable.icon_back_arrow),
              contentDescription =
                  stringResource(id = R.string.common_icon_backArrow_contentDescription))
        }
      },
      actions = {
        IconButton(onClick = onBookMarkClick) {
          val bookmarkIcon =
              if (isBookmarked) R.drawable.icon_bookmark_filled else R.drawable.icon_bookmark_border
          Icon(
              painter = painterResource(id = bookmarkIcon),
              contentDescription =
                  stringResource(id = R.string.common_icon_bookmark_contentDescription))
        }
        IconButton(onClick = onShareClick) {
          Icon(
              imageVector = Icons.Default.Share,
              contentDescription =
                  stringResource(id = R.string.common_icon_share_contentDescription))
        }
        IconButton(onClick = onBrowsingClick) {
          Icon(
              painter = painterResource(id = R.drawable.icon_network),
              contentDescription =
                  stringResource(id = R.string.common_icon_network_contentDescription))
        }
      })
}

@DayNightPreviews
@Composable
private fun ArticleDetailsTopBar_Default_Preview() {
  NewsAppPreviewSurface(content = { ArticleDetailsTopBar() })
}

@DayNightPreviews
@Composable
private fun ArticleDetailsTopBar_Bookmarked_Preview() {
  NewsAppPreviewSurface(content = { ArticleDetailsTopBar(isBookmarked = true) })
}
