package com.loc.newsapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.loc.newsapp.article_details.presentation.ArticleDetailsScreenRoot
import com.loc.newsapp.boarding.presentation.OnBoardingScreenRoot
import com.loc.newsapp.bookmark.presentation.BookmarkScreenRoot
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.routes.ArticleDetailsRoute
import com.loc.newsapp.core.domain.routes.BookmarkScreenRoute
import com.loc.newsapp.core.domain.routes.HomeScreenRoute
import com.loc.newsapp.core.domain.routes.IRoute
import com.loc.newsapp.core.domain.routes.OnBoardingScreenRoute
import com.loc.newsapp.core.domain.routes.SearchScreenRoute
import com.loc.newsapp.home.presentation.HomeScreenRoot
import com.loc.newsapp.search.presentation.SearchScreenRoot
import kotlin.reflect.typeOf

@Composable
fun NavigationNewsApp(startRoute: IRoute) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable<OnBoardingScreenRoute> {
            OnBoardingScreenRoot()
        }
        composable<HomeScreenRoute> {
            HomeScreenRoot(navController = navController)
        }
        composable<SearchScreenRoute> {
            SearchScreenRoot(navController = navController)
        }
        composable<ArticleDetailsRoute>(
            typeMap = mapOf(
                typeOf<Article>() to CustomNavType.ArticleType
            )
        ) {
            val args = it.toRoute<ArticleDetailsRoute>()
            ArticleDetailsScreenRoot(
                navController = navController,
                article =  args.article
            )
        }
        composable<BookmarkScreenRoute> {
            BookmarkScreenRoot(navController = navController)
        }
    }
}