package com.loc.newsapp.articleDetails.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.loc.newsapp.R
import com.loc.newsapp.ui.theme.NewsAppTheme

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
      modifier = Modifier
          .fillMaxWidth()
          .then(modifier),
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

@Preview(name = "Article Details Top Bar, is not bookmarked, light mode", showBackground = true)
@Preview(
    name = "Article Details Top Bar, is not bookmarked, dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun ArticleDetailsTopBarPreview() {
  NewsAppTheme { Surface { ArticleDetailsTopBar() } }
}

@Preview(name = "Article Details Top Bar, is bookmarked, light mode", showBackground = true)
@Preview(
    name = "Article Details Top Bar, is bookmarked, dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun ArticleDetailsTopBarPreviewBookmarked() {
  NewsAppTheme { Surface { ArticleDetailsTopBar(isBookmarked = true) } }
}
