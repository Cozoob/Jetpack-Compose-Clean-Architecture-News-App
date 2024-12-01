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
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val newsUseCases: NewsUseCases) :
    ViewModel() {
  var state by mutableStateOf(HomeScreenState())
    private set

  init {
    HomeScreenLogger.logScreenView()
    loadNews()
  }

  private fun loadNews() {
    viewModelScope.launch {
      val sources = "bbc-news,abc-news,tvp"
      HomeScreenLogger.logLoadNewsStart(newsSources = sources)
      state = state.copy(isLoading = true)

      val news = newsUseCases.getNews(sources = sources.split(",")).cachedIn(viewModelScope)

      state = state.copy(isLoading = false, news = news)
      HomeScreenLogger.logLoadNewsComplete(sources, news.count().toLong())
    }
  }
}
