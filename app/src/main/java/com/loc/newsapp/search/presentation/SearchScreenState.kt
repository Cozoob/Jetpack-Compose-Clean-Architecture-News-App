package com.loc.newsapp.search.presentation

import androidx.paging.PagingData
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchScreenState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>> = emptyFlow(),
    val isEmptyArticles: Boolean = true
)
