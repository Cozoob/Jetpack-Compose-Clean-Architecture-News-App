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
import com.loc.newsapp.R
import com.loc.newsapp.core.domain.model.DayNightPreviews
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
          Icon(painter = painterResource(id = R.drawable.ic_back_arrow), contentDescription = null)
        }
      },
      actions = {
        IconButton(onClick = onBookMarkClick) {
          val bookmarkIcon =
              if (isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_border
          Icon(painter = painterResource(id = bookmarkIcon), contentDescription = null)
        }
        IconButton(onClick = onShareClick) {
          Icon(imageVector = Icons.Default.Share, contentDescription = null)
        }
        IconButton(onClick = onBrowsingClick) {
          Icon(painter = painterResource(id = R.drawable.ic_network), contentDescription = null)
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
