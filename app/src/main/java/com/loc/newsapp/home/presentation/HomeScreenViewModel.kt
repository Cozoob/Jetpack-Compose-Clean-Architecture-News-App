package com.loc.newsapp.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase
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
    AnalyticsLogger.logScreenView()
    loadNews()
  }

  private fun loadNews() {
    viewModelScope.launch {
      val sources = "bbc-news,abc-news,tvp"
      AnalyticsLogger.logLoadNewsStart(newsSources = sources)
      state = state.copy(isLoading = true)

      val news = newsUseCases.getNews(sources = sources.split(",")).cachedIn(viewModelScope)

      state = state.copy(isLoading = false, news = news)
      AnalyticsLogger.logLoadNewsComplete(sources, news.count().toLong())
    }
  }
}

private object AnalyticsLogger {
  fun logScreenView() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, "HomeScreen")
      param(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeScreenViewModel")
    }
  }

  fun logLoadNewsStart(newsSources: String) {
    Firebase.analytics.logEvent("load_news_start") {
      param("news_sources", newsSources)
      param("is_loading", 1)
    }
  }

  fun logLoadNewsComplete(newsSources: String, newsCount: Long) {
    Firebase.analytics.logEvent("load_news_complete") {
      param("news_sources", newsSources)
      param("news_count", newsCount)
      param("is_loading", 0)
    }
  }
}
