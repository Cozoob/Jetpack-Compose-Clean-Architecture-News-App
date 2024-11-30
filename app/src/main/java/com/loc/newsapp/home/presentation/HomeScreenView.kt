package com.loc.newsapp.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.DimensionConstants.BIG_PADDING
import com.loc.newsapp.core.constant.DimensionConstants.SMALL_PADDING
import com.loc.newsapp.core.domain.annotation.DayNightInSystemUiPreviews
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source
import com.loc.newsapp.core.domain.route.ArticleDetailsRoute
import com.loc.newsapp.core.domain.route.SearchScreenRoute
import com.loc.newsapp.core.presentation.component.ArticlesList
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.presentation.component.NewsCircularProgressIndicator
import com.loc.newsapp.core.presentation.component.NewsSearchBar
import kotlinx.coroutines.flow.flowOf

const val MAX_ARTICLES_PER_SCREEN = 10

@Composable
fun HomeScreenView(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
  HomeScreenViewContent(
      state = viewModel.state,
      onAction = { action ->
        when (action) {
          is HomeScreenAction.NavigateToSearchScreen -> navController.navigate(SearchScreenRoute)
          is HomeScreenAction.NavigateToArticleDetails ->
              navController.navigate(ArticleDetailsRoute(article = action.article))
        }
      })
}

@Composable
private fun HomeScreenViewContent(state: HomeScreenState, onAction: (HomeScreenAction) -> Unit) {
  if (state.isLoading) {
    NewsCircularProgressIndicator()
  } else {
    val articles = state.news.collectAsLazyPagingItems()

    val titles by remember {
      derivedStateOf {
        if (articles.itemCount > MAX_ARTICLES_PER_SCREEN) {
          articles.itemSnapshotList.items.slice(IntRange(start = 0, endInclusive = 9)).joinToString(
              separator = " \uD83D\uDFE5 ") {
                it.title
              }
        } else {
          ""
        }
      }
    }

    Column(modifier = Modifier.fillMaxSize().padding(top = BIG_PADDING).statusBarsPadding()) {
      Image(
          painter = painterResource(id = R.drawable.icon_logo),
          contentDescription = stringResource(id = R.string.common_icon_logo_contentDescription),
          modifier = Modifier.width(150.dp).height(30.dp).padding(horizontal = BIG_PADDING))
      Spacer(modifier = Modifier.height(BIG_PADDING))
      NewsSearchBar(
          modifier = Modifier.fillMaxWidth().padding(horizontal = SMALL_PADDING),
          text = "",
          readOnly = true,
          onValueChange = {},
          onSearch = {},
          onClick = { onAction.invoke(HomeScreenAction.NavigateToSearchScreen) })
      Spacer(modifier = Modifier.height(BIG_PADDING))
      Text(
          text = titles,
          modifier = Modifier.fillMaxWidth().basicMarquee(),
          fontSize = 12.sp,
          color = colorResource(id = R.color.placeholder))
      Spacer(modifier = Modifier.height(BIG_PADDING))
      ArticlesList(
          modifier = Modifier.padding(horizontal = BIG_PADDING),
          articles = articles,
          onClick = { onAction.invoke(HomeScreenAction.NavigateToArticleDetails(article = it)) })
    }
  }
}

@DayNightInSystemUiPreviews
@Composable
private fun HomeScreenView_Loading_Preview() {
  NewsAppPreviewSurface(
      content = { HomeScreenViewContent(state = HomeScreenState(), onAction = {}) })
}

@DayNightInSystemUiPreviews
@Composable
private fun HomeScreenView_ShimmerArticles_Preview() {
  val news =
      flowOf(
          PagingData.from(
              listOf(
                  Article(
                      author = "John Smith",
                      content =
                          "Recent studies reveal that advancements in AI technology are transforming " +
                              "industries at an unprecedented rate.",
                      description =
                          "An in-depth look at how AI is reshaping the future of work and daily life.",
                      publishedAt = "2024.11.20",
                      source = Source(id = "techcrunch", name = "TechCrunch"),
                      title = "The Rise of AI: Opportunities and Challenges",
                      url = "https://www.techcrunch.com/articles/rise-of-ai",
                      urlToImage = "https://www.techcrunch.com/images/ai-article.jpg"))))

  NewsAppPreviewSurface(
      content = {
        HomeScreenViewContent(
            state = HomeScreenState(isLoading = false, news = news), onAction = {})
      })
}
