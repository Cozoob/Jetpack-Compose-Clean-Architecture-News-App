package com.loc.newsapp.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.loc.newsapp.core.domain.useCase.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val newsUseCases: NewsUseCases) :
    ViewModel() {
  var state by mutableStateOf(HomeScreenState())
    private set

  init {
    loadNews()
  }

  private fun loadNews() {
    viewModelScope.launch { state = state.copy(isLoading = true) }

    val news =
        newsUseCases
            .getNews(sources = listOf("bbc-news", "abc-news", "tvp"))
            .cachedIn(viewModelScope)

    viewModelScope.launch { state = state.copy(isLoading = false, news = news) }
  }
}
