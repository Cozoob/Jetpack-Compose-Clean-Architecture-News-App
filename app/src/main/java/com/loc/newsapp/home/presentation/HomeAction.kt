package com.loc.newsapp.home.presentation

sealed interface HomeAction {
    data object NavigateToSearchScreen: HomeAction
    data object NavigateToArticleDetails: HomeAction
}