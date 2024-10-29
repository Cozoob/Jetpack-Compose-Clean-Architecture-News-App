package com.loc.newsapp.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.loc.newsapp.core.domain.use_case.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        loadNews()
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.SomeAction -> { }
        }
    }

    private fun loadNews() {
        val news = newsUseCases.getNews(
            sources = listOf("bbc-news", "abc-news")
        ).cachedIn(viewModelScope)

        // TODO
//        viewModelScope.launch {
//            state = state.copy(
//                isLoading = false,
//                news = news
//            )
//        }
    }
}