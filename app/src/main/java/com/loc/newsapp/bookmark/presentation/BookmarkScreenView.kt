package com.loc.newsapp.bookmark.presentation

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.DimensionConstant.BIG_PADDING
import com.loc.newsapp.core.constant.TestTag
import com.loc.newsapp.core.domain.annotation.DayNightInSystemUiPreviews
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source
import com.loc.newsapp.core.domain.route.ArticleDetailsRoute
import com.loc.newsapp.core.presentation.component.ArticlesList
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface

@Composable
fun BookmarkScreenView(
    navController: NavController,
    viewModel: BookmarkScreenViewModel = hiltViewModel()
) {
  BookmarkScreenViewContent(
      state = viewModel.state,
      onAction = { action ->
        when (action) {
          is BookmarkScreenAction.NavigateToArticleDetails ->
              navController.navigate(ArticleDetailsRoute(article = action.article))
        }
      })
}

@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun BookmarkScreenViewContent(
    state: BookmarkScreenState,
    onAction: (BookmarkScreenAction) -> Unit
) {
  Column(
      modifier =
          Modifier.fillMaxWidth()
              .statusBarsPadding()
              .padding(top = BIG_PADDING, start = BIG_PADDING, end = BIG_PADDING)) {
        Text(
            text = stringResource(id = R.string.bookmark_text_bookmarks_screenHeader),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title))
        Spacer(modifier = Modifier.height(BIG_PADDING))
        ArticlesList(
            modifier = Modifier.testTag(TestTag.ARTICLES_LIST),
            articles = state.articles,
            onClick = { onAction.invoke(BookmarkScreenAction.NavigateToArticleDetails(it)) })
      }
}

@DayNightInSystemUiPreviews
@Composable
private fun BookmarkScreenView_NoContent_Preview() {
  NewsAppPreviewSurface(
      content = { BookmarkScreenViewContent(state = BookmarkScreenState(), onAction = {}) })
}

@DayNightInSystemUiPreviews
@Composable
private fun BookmarkScreenView_ThreeArticles_Preview() {
  NewsAppPreviewSurface(
      content = {
        BookmarkScreenViewContent(
            state =
                BookmarkScreenState(
                    articles =
                        listOf(
                            Article(
                                author = "John Smith",
                                content =
                                    "Recent studies reveal that advancements in AI technology are " +
                                        "transforming industries at an unprecedented rate.",
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
                                    "NASA announces its ambitious plans for a new lunar base, aiming" +
                                        " to support long-term exploration missions.",
                                description =
                                    "NASA's latest project focuses on building a sustainable lunar " +
                                        "base to advance space exploration.",
                                publishedAt = "2024.12.01",
                                source = Source(id = "nasa", name = "NASA"),
                                title = "NASA Unveils Plans for Lunar Base",
                                url = "https://www.nasa.gov/articles/lunar-base-announcement",
                                urlToImage = "https://www.nasa.gov/images/lunar-base.jpg"),
                            Article(
                                author = "Michael Brown",
                                content =
                                    "Global markets respond positively to new economic policies " +
                                        "introduced at the G20 summit, sparking optimism among investors.",
                                description =
                                    "The G20 summit brings promising economic reforms, boosting " +
                                        "global market confidence.",
                                publishedAt = "2024.11.28",
                                source = Source(id = "financial-times", name = "Financial Times"),
                                title = "G20 Summit: Economic Policies Drive Market Surge",
                                url = "https://www.ft.com/content/g20-summit-markets",
                                urlToImage = "https://www.ft.com/images/g20-summit.jpg")),
                ),
            onAction = {})
      })
}
