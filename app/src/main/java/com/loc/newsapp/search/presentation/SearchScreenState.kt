package com.loc.newsapp.search.presentation

import androidx.paging.PagingData
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchScreenState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)
