package com.loc.newsapp.core.presentation.components.articles_list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.presentation.components.ArticleCard
import com.loc.newsapp.core.presentation.components.ArticleCardShimmerEffect
import com.loc.newsapp.core.presentation.constants.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constants.Dimensions.MediumPadding1
import com.loc.newsapp.core.presentation.error_screen_bundle.ErrorScreenRoot
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticlesListRoot(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>? = null,
    onAction: (ArticlesListAction) -> Unit
) {
    val viewModel = ArticlesListViewModel(
        initArticles = articles
    )
    ArticlesList(
        modifier = modifier,
        state = viewModel.state,
        onAction = onAction
    )
}

@Composable
private fun ArticlesList(
    modifier: Modifier = Modifier,
    state: ArticlesListState,
    onAction: (ArticlesListAction) -> Unit
) {
    if(state.isLoading) {
        LoadingShimmerEffect()
    } else if(state.isError) {
        ErrorScreenRoot(error = state.error)
    } else if(state.articles != null) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(
                count = state.articles.itemCount
            ) {
                state.articles[it]?.let { article ->
                    ArticleCard(
                        article = article,
                        onClick = {
                            onAction(ArticlesListAction.ClickArticle(article))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10){
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}

@Preview(name = "Articles List, loading, light mode", group="Loading", showBackground = true)
@Preview(name = "Articles List, loading, dark mode", group="Loading", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ArticlesListLoadingPreview() {
    NewsAppTheme {
        Surface {
            ArticlesListRoot(
                onAction = {}
            )
        }
    }
}