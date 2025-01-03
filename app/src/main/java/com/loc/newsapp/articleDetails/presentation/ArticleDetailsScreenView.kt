package com.loc.newsapp.articleDetails.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.R
import com.loc.newsapp.articleDetails.presentation.component.ArticleDetailsTopBar
import com.loc.newsapp.core.constant.DimensionConstant.BIG_PADDING
import com.loc.newsapp.core.constant.DimensionConstant.SMALL_PADDING
import com.loc.newsapp.core.domain.annotation.DayNightInSystemUiPreviews
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.presentation.util.debugPlaceholder

@Composable
fun ArticleDetailsScreenView(navController: NavController, article: Article) {
  val context = LocalContext.current
  val viewModel =
      hiltViewModel(
          creationCallback = {
              factory: ArticleDetailsScreenViewModel.ArticleDetailsScreenViewModelFactory ->
            factory.create(
                article = article, articleDetailsScreenIntent = ArticleDetailsScreenIntent(context))
          })

  ArticleDetailsScreenViewContent(
      state = viewModel.state,
      onAction = { action ->
        when (action) {
          is ArticleDetailsScreenAction.NavigateUp -> navController.navigateUp()
          else -> Unit
        }

        viewModel.onAction(action)
      })
}

@Composable
private fun ArticleDetailsScreenViewContent(
    state: ArticleDetailsScreenState,
    onAction: (ArticleDetailsScreenAction) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
    val context = LocalContext.current
    val toastMessage = state.toastMessage.asString()

    if (toastMessage.isNotBlank()) {
      Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }
    ArticleDetailsTopBar(
        isBookmarked = state.isArticleBookmarked,
        onBrowsingClick = { onAction.invoke(ArticleDetailsScreenAction.BrowseArticle) },
        onShareClick = { onAction.invoke(ArticleDetailsScreenAction.ShareArticle) },
        onBookMarkClick = { onAction.invoke(ArticleDetailsScreenAction.SaveArticle) },
        onBackClick = { onAction.invoke(ArticleDetailsScreenAction.NavigateUp) })
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding =
            PaddingValues(top = BIG_PADDING, start = SMALL_PADDING, end = SMALL_PADDING)) {
          item {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(248.dp).clip(MaterialTheme.shapes.medium),
                model =
                    ImageRequest.Builder(context = context).data(state.article.urlToImage).build(),
                contentDescription = state.article.title,
                contentScale = ContentScale.Crop,
                placeholder = debugPlaceholder(R.drawable.image_placeholder_horizontal),
                error = painterResource(id = R.drawable.image_no_image_available))

            Spacer(modifier = Modifier.height(BIG_PADDING))
            Text(
                text = state.article.title,
                style = MaterialTheme.typography.displaySmall,
                color = colorResource(id = R.color.text_title))
            Text(
                text = state.article.content,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.body))
          }
        }
  }
}

@DayNightInSystemUiPreviews
@Composable
private fun ArticleDetailsScreenView_SampleArticle_Preview() {
  NewsAppPreviewSurface(
      content = {
        ArticleDetailsScreenViewContent(
            state =
                ArticleDetailsScreenState(
                    article =
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
                            urlToImage = "https://www.techcrunch.com/images/ai-article.jpg")),
            onAction = {})
      })
}
