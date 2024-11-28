package com.loc.newsapp.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.core.domain.routes.ArticleDetailsRoute
import com.loc.newsapp.core.presentation.components.ArticlesList
import com.loc.newsapp.core.presentation.components.NewsSearchBar
import com.loc.newsapp.core.presentation.constants.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constants.Dimensions.MediumPadding1

@Composable
fun SearchScreenRoot(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    SearchScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is SearchScreenAction.NavigateToArticleDetailsScreen -> navController.navigate(
                    ArticleDetailsRoute(article = action.article)
                )

                else -> Unit
            }

            viewModel.onAction(action)
        }
    )

}


@Composable
private fun SearchScreen(
    state: SearchScreenState,
    onAction: (SearchScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        NewsSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ExtraSmallPadding2),
            text = state.searchQuery,
            readOnly = false,
            focusSearchOnStart = true,
            onValueChange = {
                onAction.invoke(SearchScreenAction.UpdateSearchQuery(it))
            },
            onSearch = {
                onAction.invoke(SearchScreenAction.SearchNews)
            }
        )
        Spacer(modifier = Modifier.height(MediumPadding1))

        state.articles.let { it ->
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = {
                    onAction.invoke(SearchScreenAction.NavigateToArticleDetailsScreen(article = it))
                }
            )
        }
    }
}