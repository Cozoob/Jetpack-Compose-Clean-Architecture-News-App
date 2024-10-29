package com.loc.newsapp.home.presentation

sealed interface HomeAction {
    data object SomeAction: HomeAction
}