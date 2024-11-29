package com.loc.newsapp.navigation.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.loc.newsapp.R
import com.loc.newsapp.articleDetails.presentation.ArticleDetailsScreenRoot
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
import com.loc.newsapp.navigation.domain.BottomNavigationItem
import com.loc.newsapp.navigation.domain.CustomNavType
import com.loc.newsapp.navigation.presentation.components.BottomNavigation
import com.loc.newsapp.search.presentation.SearchScreenRoot
import kotlin.reflect.typeOf

@Composable
fun NavigationNewsApp(startRoute: IRoute) {

  val bottomNavigationItems = remember {
    listOf(
        BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
        BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
        BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"))
  }
  val navController = rememberNavController()
  val backStackState = navController.currentBackStackEntryAsState().value
  var selectedItem by rememberSaveable { mutableIntStateOf(0) }

  selectedItem =
      when (backStackState?.destination?.route) {
        HomeScreenRoute.javaClass.name -> 0
        SearchScreenRoute.javaClass.name -> 1
        BookmarkScreenRoute.javaClass.name -> 2
        else -> -1
      }
  val isBottomBarVisible =
      remember(key1 = backStackState) {
        backStackState?.destination?.route == HomeScreenRoute.javaClass.name ||
            backStackState?.destination?.route == SearchScreenRoute.javaClass.name ||
            backStackState?.destination?.route == BookmarkScreenRoute.javaClass.name
      }

  Scaffold(
      modifier = Modifier.fillMaxSize(),
      bottomBar = {
        if (isBottomBarVisible) {
          BottomNavigation(
              items = bottomNavigationItems,
              selectedItem = selectedItem,
              onItemClick = { index ->
                when (index) {
                  0 -> navController.navigate(HomeScreenRoute)
                  1 -> navController.navigate(SearchScreenRoute)
                  2 -> navController.navigate(BookmarkScreenRoute)
                }
              })
        }
      }) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = startRoute,
            modifier = Modifier.padding(bottom = bottomPadding)) {
              composable<OnBoardingScreenRoute> { OnBoardingScreenRoot() }
              composable<HomeScreenRoute> { HomeScreenRoot(navController = navController) }
              composable<SearchScreenRoute> { SearchScreenRoot(navController = navController) }
              composable<ArticleDetailsRoute>(
                  typeMap = mapOf(typeOf<Article>() to CustomNavType.ArticleType)) {
                    val args = it.toRoute<ArticleDetailsRoute>()
                    ArticleDetailsScreenRoot(navController = navController, article = args.article)
                  }
              composable<BookmarkScreenRoute> { BookmarkScreenRoot(navController = navController) }
            }
      }
}
