package com.loc.newsapp.bookmark.presentation

import com.loc.newsapp.core.domain.model.Article

data class BookmarkScreenState(
    val articles: List<Article> = emptyList()
)
