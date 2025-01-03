package com.loc.newsapp.bookmark.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.bookmark.domain.ArticlesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class BookmarkScreenViewModel @Inject constructor(private val articlesUseCases: ArticlesUseCases) :
    ViewModel() {
  var state by mutableStateOf(BookmarkScreenState())
    private set

  init {
    BookmarkScreenLogger.logScreenView()
    loadArticles()
  }

  private fun loadArticles() {
    articlesUseCases
        .getAllArticles()
        .onEach { state = state.copy(articles = it.asReversed()) }
        .launchIn(viewModelScope)
  }
}
