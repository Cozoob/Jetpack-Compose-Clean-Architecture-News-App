package com.loc.newsapp.core.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.presentation.constant.Dimensions.ExtraSmallPadding1
import com.loc.newsapp.core.presentation.constant.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constant.Dimensions.MediumPadding1
import com.loc.newsapp.core.presentation.extension.noItems
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
  if (articles.isEmpty()) {
    EmptyContent()
  }
  LazyColumn(
      modifier = modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(MediumPadding1),
      contentPadding = PaddingValues(all = ExtraSmallPadding2)) {
        items(
            count = articles.size,
        ) {
          articles[it].let { article ->
            ArticleCard(article = article, onClick = { onClick(article) })
          }
        }
      }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
  val handlePagingResult = handlePagingResult(articles = articles)

  if (handlePagingResult) {
    if (articles.noItems) {
      EmptyContent()
    } else {
      LazyColumn(
          modifier = modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(MediumPadding1),
          contentPadding = PaddingValues(all = ExtraSmallPadding2)) {
            items(count = articles.itemCount) { index ->
              articles[index]?.let { article ->
                ArticleCard(article = article, onClick = { onClick(article) })
              }
            }
          }
    }
  }
}

@Composable
private fun handlePagingResult(articles: LazyPagingItems<Article>): Boolean {
  val loadState = articles.loadState

  val error =
      when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
      }

  return when {
    loadState.refresh is LoadState.Loading -> {
      ShimmerEffect()
      false
    }

    error != null -> {
      false
    }

    else -> {
      return true
    }
  }
}

@Composable
private fun ShimmerEffect(modifier: Modifier = Modifier) {
  Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
    repeat(times = 10) {
      ArticleCardShimmerEffect(modifier = Modifier.padding(horizontal = ExtraSmallPadding1))
    }
  }
}

@Preview(name = "Article Card Shimmer Effect, light mode", showBackground = true)
@Preview(
    name = "Article Card Shimmer Effect, dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
private fun ArticlesListPreview() {
  NewsAppTheme {
    Surface {
      //            ArticlesList()
    }
  }
}
