package com.loc.newsapp.articleDetails.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.R
import com.loc.newsapp.bookmark.domain.ArticlesUseCases
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.presentation.util.UiText
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(
    assistedFactory = ArticleDetailsScreenViewModel.ArticleDetailsScreenViewModelFactory::class)
class ArticleDetailsScreenViewModel
@AssistedInject
constructor(
    @Assisted private val article: Article,
    @Assisted private val articleDetailsScreenIntent: ArticleDetailsScreenIntent,
    private val articlesUseCases: ArticlesUseCases
) : ViewModel() {

  @AssistedFactory
  interface ArticleDetailsScreenViewModelFactory {
    fun create(
        article: Article,
        articleDetailsScreenIntent: ArticleDetailsScreenIntent
    ): ArticleDetailsScreenViewModel
  }

  var state by mutableStateOf(ArticleDetailsScreenState(article = article))
    private set

  init {
    ArticleDetailsScreenLogger.logScreenView()
    checkIsArticleBookmarked()
  }

  fun onAction(action: ArticleDetailsScreenAction) {
    when (action) {
      is ArticleDetailsScreenAction.SaveArticle -> saveArticle()
      is ArticleDetailsScreenAction.ShareArticle -> shareArticle()
      is ArticleDetailsScreenAction.BrowseArticle -> browseArticle()
      else -> Unit
    }
  }

  private fun saveArticle() {
    viewModelScope.launch {
      val article = articlesUseCases.findByUrlArticle(url = article.url)
      val isNotFoundArticleInLocalDatabase = article == null

      ArticleDetailsScreenLogger.logBookmark(isBookmarked = !isNotFoundArticleInLocalDatabase)
      if (isNotFoundArticleInLocalDatabase) {
        upsertArticle()
      } else {
        deleteArticle()
      }
    }
  }

  private suspend fun upsertArticle() {
    articlesUseCases.upsertArticle(article = article)
    state =
        state.copy(
            isArticleBookmarked = true,
            toastMessage =
                UiText.StringResource(resId = R.string.articleDetails_toastMessage_articleSaved))
  }

  private suspend fun deleteArticle() {
    articlesUseCases.deleteArticle(article = article)
    state =
        state.copy(
            isArticleBookmarked = false,
            toastMessage =
                UiText.StringResource(resId = R.string.articleDetails_toastMessage_articleRemoved))
  }

  private fun shareArticle() {
    ArticleDetailsScreenLogger.logShare()
    articleDetailsScreenIntent.actionSend(state.article.url)
  }

  private fun browseArticle() {
    ArticleDetailsScreenLogger.logOpenArticleInBrowser()
    articleDetailsScreenIntent.actionView(state.article.url)
  }

  private fun checkIsArticleBookmarked() {
    viewModelScope.launch {
      val article = articlesUseCases.findByUrlArticle(url = article.url)
      val isFoundArticleInLocalDatabase = article != null

      state = state.copy(isArticleBookmarked = isFoundArticleInLocalDatabase)
    }
  }
}
