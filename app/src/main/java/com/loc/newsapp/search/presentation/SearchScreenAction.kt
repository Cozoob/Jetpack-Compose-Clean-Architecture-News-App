package com.loc.newsapp.search.presentation

import com.loc.newsapp.core.domain.model.Article

sealed interface SearchScreenAction {
    data class UpdateSearchQuery(val searchQuery: String): SearchScreenAction
    data object SearchNews: SearchScreenAction
    data class NavigateToArticleDetailsScreen(val article: Article): SearchScreenAction
}