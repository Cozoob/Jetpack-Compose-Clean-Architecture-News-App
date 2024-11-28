package com.loc.newsapp.home.presentation

import androidx.paging.PagingData
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val isLoading: Boolean = true,
    val news: Flow<PagingData<Article>> = emptyFlow()
)
