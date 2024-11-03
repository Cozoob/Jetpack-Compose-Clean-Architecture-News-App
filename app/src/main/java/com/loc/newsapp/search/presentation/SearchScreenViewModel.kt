package com.loc.newsapp.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.loc.newsapp.core.domain.use_case.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val newsUseCase: NewsUseCases
) : ViewModel() {
    var state by mutableStateOf(SearchScreenState())
        private set

    fun onAction(action: SearchScreenAction) {
        when(action) {
            is SearchScreenAction.UpdateSearchQuery -> updateSearchQuery(action)
            is SearchScreenAction.SearchNews -> searchNews()
            else -> Unit
        }
    }

    private fun updateSearchQuery(action : SearchScreenAction.UpdateSearchQuery) {
        viewModelScope.launch {
            state = state.copy(
                searchQuery = action.searchQuery
            )
        }
    }

    private fun searchNews() {
        val articles = newsUseCase.searchNews(
            searchQuery = state.searchQuery,
            sources = listOf("bbc-news", "abc-news")
        ).cachedIn(viewModelScope)

        viewModelScope.launch {
            state = state.copy(
                articles = articles
            )
        }
    }
}