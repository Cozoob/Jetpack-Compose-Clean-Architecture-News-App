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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.core.domain.routes.ArticleDetailsRoute
import com.loc.newsapp.core.domain.routes.SearchScreenRoute
import com.loc.newsapp.core.presentation.components.ArticlesList
import com.loc.newsapp.core.presentation.components.NewsCircularProgressIndicator
import com.loc.newsapp.core.presentation.components.NewsSearchBar
import com.loc.newsapp.core.presentation.constants.Dimensions.ExtraSmallPadding2
import com.loc.newsapp.core.presentation.constants.Dimensions.MediumPadding1

@Composable
fun HomeScreenRoot(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is HomeAction.NavigateToSearchScreen -> navController.navigate(SearchScreenRoute)
                is HomeAction.NavigateToArticleDetails -> navController.navigate(ArticleDetailsRoute(article = action.article))
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    if(state.isLoading) {
        NewsCircularProgressIndicator()
    } else {
        val articles = state.news.collectAsLazyPagingItems()

        val titles by remember {
            derivedStateOf {
                if (articles.itemCount > 10) {
                    articles.itemSnapshotList.items
                        .slice(IntRange(start = 0, endInclusive = 9))
                        .joinToString(separator = " \uD83D\uDFE5 ") { it.title }
                } else {
                    ""
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = MediumPadding1)
                .statusBarsPadding()
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_logo
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(150.dp)
                    .height(30.dp)
                    .padding(horizontal = MediumPadding1)
            )
            Spacer(
                modifier = Modifier
                    .height(MediumPadding1)
            )
            NewsSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ExtraSmallPadding2),
                text = "",
                readOnly = true,
                onValueChange = {},
                onSearch = {},
                onClick = {
                    onAction.invoke(HomeAction.NavigateToSearchScreen)
                }
            )
            Spacer(modifier = Modifier.height(MediumPadding1))
            Text(
                text = titles,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                fontSize = 12.sp,
                color = colorResource(id = R.color.placeholder)
            )
            Spacer(modifier = Modifier.height(MediumPadding1))
            ArticlesList(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                articles = articles,
                onClick = {
                    onAction.invoke(HomeAction.NavigateToArticleDetails(article = it))
                }
            )
        }
    }
}