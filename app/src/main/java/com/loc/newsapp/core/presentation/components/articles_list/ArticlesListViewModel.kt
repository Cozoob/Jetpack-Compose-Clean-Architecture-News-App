package com.loc.newsapp.core.presentation.components.articles_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.launch

class ArticlesListViewModel(
    initArticles: LazyPagingItems<Article>? = null,
) : ViewModel() {
    var state by mutableStateOf(ArticlesListState(
        articles = initArticles
    ))
        private set

    init {
        handleArticlesList()
    }

    private fun handleArticlesList() {
        if(state.articles != null) {
            setState()
        }
    }

    private fun setState() {
        val loadState = state.articles!!.loadState
        val error = prepareError(loadState)

        if(loadState.refresh is LoadState.Loading) {
            setLoadingState()
        } else if(error != null) {
            setErrorState(error)
        } else {
            setArticlesListState()
        }
    }

    private fun prepareError(loadState: CombinedLoadStates): LoadState.Error?  {
        return when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
    }

    private fun setLoadingState() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = false
            )
        }
    }

    private fun setErrorState(error: LoadState.Error) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = false,
                isError = true,
                error = error
            )
        }
    }

    private fun setArticlesListState() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = false,
                isError = false,
                articles = state.articles
            )
        }
    }
}