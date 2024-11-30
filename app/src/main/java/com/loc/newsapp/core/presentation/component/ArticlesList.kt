package com.loc.newsapp.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.DayNightPreviews
import com.loc.newsapp.core.domain.model.Source
import com.loc.newsapp.core.presentation.constant.Dimensions.ExtraSmallPadding1
import com.loc.newsapp.core.presentation.constant.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constant.Dimensions.MediumPadding1
import com.loc.newsapp.core.presentation.extension.noItems

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

@DayNightPreviews
@Composable
private fun ArticlesList_ThreeArticles_Preview() {
  NewsAppPreviewSurface(
      content = {
        ArticlesList(
            articles =
                listOf(
                    Article(
                        author = "John Smith",
                        content =
                            "Recent studies reveal that advancements in AI technology are transforming industries at an unprecedented rate.",
                        description =
                            "An in-depth look at how AI is reshaping the future of work and daily life.",
                        publishedAt = "2024.11.20",
                        source = Source(id = "techcrunch", name = "TechCrunch"),
                        title = "The Rise of AI: Opportunities and Challenges",
                        url = "https://www.techcrunch.com/articles/rise-of-ai",
                        urlToImage = "https://www.techcrunch.com/images/ai-article.jpg"),
                    Article(
                        author = "Emily Johnson",
                        content =
                            "NASA announces its ambitious plans for a new lunar base, aiming to support long-term exploration missions.",
                        description =
                            "NASA's latest project focuses on building a sustainable lunar base to advance space exploration.",
                        publishedAt = "2024.12.01",
                        source = Source(id = "nasa", name = "NASA"),
                        title = "NASA Unveils Plans for Lunar Base",
                        url = "https://www.nasa.gov/articles/lunar-base-announcement",
                        urlToImage = "https://www.nasa.gov/images/lunar-base.jpg"),
                    Article(
                        author = "Michael Brown",
                        content =
                            "Global markets respond positively to new economic policies introduced at the G20 summit, sparking optimism among investors.",
                        description =
                            "The G20 summit brings promising economic reforms, boosting global market confidence.",
                        publishedAt = "2024.11.28",
                        source = Source(id = "financial-times", name = "Financial Times"),
                        title = "G20 Summit: Economic Policies Drive Market Surge",
                        url = "https://www.ft.com/content/g20-summit-markets",
                        urlToImage = "https://www.ft.com/images/g20-summit.jpg")),
            onClick = {})
      })
}
