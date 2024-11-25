package com.loc.newsapp.article_details.presentation

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.R
import com.loc.newsapp.article_details.presentation.component.ArticleDetailsTopBar
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.presentation.constants.Dimensions.ArticleImageHeight
import com.loc.newsapp.core.presentation.constants.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constants.Dimensions.MediumPadding1

@Composable
fun ArticleDetailsScreenRoot(
    navController: NavController,
    article: Article
) {
    val viewModel = hiltViewModel(
        creationCallback = { factory: ArticleDetailsScreenViewModel.ArticleDetailsScreenViewModelFactory ->
            factory.create(article)
        }
    )

    ArticleDetailsScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is ArticleDetailsScreenAction.NavigateUp -> navController.navigateUp()
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ArticleDetailsScreen(
    state: ArticleDetailsScreenState,
    onAction: (ArticleDetailsScreenAction) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        ArticleDetailsTopBar(
            onBrowsingClick = {
                onAction.invoke(ArticleDetailsScreenAction.BrowseArticle(context = context))
            },
            onShareClick = {
                onAction.invoke(ArticleDetailsScreenAction.ShareArticle(context = context))
            },
            onBookMarkClick = {
                onAction.invoke(ArticleDetailsScreenAction.SaveArticle)
            },
            onBackClick = {
                onAction.invoke(ArticleDetailsScreenAction.NavigateUp)
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                top = MediumPadding1,
                start = ExtraSmallPadding2,
                end = ExtraSmallPadding2
            )
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(context = context).data(state.article.urlToImage).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = state.article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )
                Text(
                    text = state.article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }
}