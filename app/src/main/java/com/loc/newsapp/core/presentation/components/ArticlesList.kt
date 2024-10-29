package com.loc.newsapp.core.presentation.components

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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.presentation.constants.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constants.Dimensions.MediumPadding1
import com.loc.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)

    if(handlePagingResult) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onClick(article) }
                    )
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(articles: LazyPagingItems<Article>) : Boolean {
    val loadState = articles.loadState

    val error = when {
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
        } else -> {
            return true
        }
    }
}

@Composable
private fun ShimmerEffect(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}

@Preview(name = "Article Card Shimmer Effect, light mode", showBackground = true)
@Preview(name = "Article Card Shimmer Effect, dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ArticlesListPreview() {
    NewsAppTheme {
        Surface {
//            ArticlesList()
        }
    }
}