package com.loc.newsapp.core.presentation.components.articles_list

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.core.domain.model.Article

data class ArticlesListState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: LoadState.Error? = null,
    val articles: LazyPagingItems<Article>? = null
)
