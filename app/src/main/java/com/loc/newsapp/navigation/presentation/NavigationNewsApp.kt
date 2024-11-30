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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.loc.newsapp.R
import com.loc.newsapp.articleDetails.presentation.ArticleDetailsScreenView
import com.loc.newsapp.boarding.presentation.OnBoardingScreenView
import com.loc.newsapp.bookmark.presentation.BookmarkScreenView
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.route.ArticleDetailsRoute
import com.loc.newsapp.core.domain.route.BookmarkScreenRoute
import com.loc.newsapp.core.domain.route.HomeScreenRoute
import com.loc.newsapp.core.domain.route.IRoute
import com.loc.newsapp.core.domain.route.OnBoardingScreenRoute
import com.loc.newsapp.core.domain.route.SearchScreenRoute
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.presentation.util.UiText
import com.loc.newsapp.home.presentation.HomeScreenView
import com.loc.newsapp.navigation.domain.BottomNavigationItem
import com.loc.newsapp.navigation.domain.CustomNavType
import com.loc.newsapp.navigation.presentation.component.BottomNavigation
import com.loc.newsapp.search.presentation.SearchScreenView
import kotlin.reflect.typeOf

@Composable
fun NavigationNewsApp(startRoute: IRoute) {
  val context = LocalContext.current

  val bottomNavigationItems = remember {
    listOf(
        BottomNavigationItem(
            icon = R.drawable.icon_home,
            text =
                UiText.StringResource(resId = R.string.navigation_bottomNavigationItem_home_text)
                    .asString(context)),
        BottomNavigationItem(
            icon = R.drawable.icon_search,
            text =
                UiText.StringResource(resId = R.string.navigation_bottomNavigationItem_search_text)
                    .asString(context)),
        BottomNavigationItem(
            icon = R.drawable.icon_bookmark,
            text =
                UiText.StringResource(
                        resId = R.string.navigation_bottomNavigationItem_bookmarks_text)
                    .asString(context)))
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
              composable<OnBoardingScreenRoute> { OnBoardingScreenView() }
              composable<HomeScreenRoute> { HomeScreenView(navController = navController) }
              composable<SearchScreenRoute> { SearchScreenView(navController = navController) }
              composable<ArticleDetailsRoute>(
                  typeMap = mapOf(typeOf<Article>() to CustomNavType.ArticleType)) { article ->
                    val args = article.toRoute<ArticleDetailsRoute>()
                    ArticleDetailsScreenView(navController = navController, article = args.article)
                  }
              composable<BookmarkScreenRoute> { BookmarkScreenView(navController = navController) }
            }
      }
}

@DayNightPreviews
@Composable
private fun NavigationNewsApp_StartHomeScreen_Preview() {
  NewsAppPreviewSurface(content = { NavigationNewsApp(startRoute = HomeScreenRoute) })
}
