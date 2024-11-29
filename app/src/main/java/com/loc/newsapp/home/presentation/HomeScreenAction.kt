package com.loc.newsapp.home.presentation

import com.loc.newsapp.core.domain.model.Article

sealed interface HomeScreenAction {
  data object NavigateToSearchScreen : HomeScreenAction

  data class NavigateToArticleDetails(val article: Article) : HomeScreenAction
}
