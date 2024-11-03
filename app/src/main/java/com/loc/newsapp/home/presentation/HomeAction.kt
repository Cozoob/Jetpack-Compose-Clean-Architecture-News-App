package com.loc.newsapp.home.presentation

import com.loc.newsapp.core.domain.model.Article

sealed interface HomeAction {
    data object NavigateToSearchScreen: HomeAction
    data class NavigateToArticleDetails(val article: Article): HomeAction
}