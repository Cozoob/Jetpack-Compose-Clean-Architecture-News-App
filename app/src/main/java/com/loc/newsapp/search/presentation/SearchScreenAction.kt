package com.loc.newsapp.search.presentation

sealed interface SearchScreenAction {
    data class UpdateSearchQuery(val searchQuery: String): SearchScreenAction
    data object SearchNews: SearchScreenAction
    data object NavigateToArticleDetailsScreen: SearchScreenAction
}