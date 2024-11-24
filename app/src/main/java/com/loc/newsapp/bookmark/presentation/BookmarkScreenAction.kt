package com.loc.newsapp.bookmark.presentation

import com.loc.newsapp.core.domain.model.Article

sealed interface BookmarkScreenAction {
    data class NavigateToArticleDetails(val article: Article): BookmarkScreenAction
}