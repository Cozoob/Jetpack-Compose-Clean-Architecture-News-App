package com.loc.newsapp.article_details.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.bookmark.domain.ArticlesUseCases
import com.loc.newsapp.core.domain.model.Article
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ArticleDetailsScreenViewModel.ArticleDetailsScreenViewModelFactory::class)
class ArticleDetailsScreenViewModel @AssistedInject constructor(
    @Assisted private val article: Article,
    private val articlesUseCases: ArticlesUseCases
) : ViewModel() {

    @AssistedFactory
    interface ArticleDetailsScreenViewModelFactory {
        fun create(article: Article) : ArticleDetailsScreenViewModel
    }

    var state by mutableStateOf(ArticleDetailsScreenState(article = article))
        private set

    init {
        checkIsArticleBookmarked()
    }

    fun onAction(action: ArticleDetailsScreenAction) {
        when(action) {
            is ArticleDetailsScreenAction.SaveArticle -> saveArticle()
            is ArticleDetailsScreenAction.ShareArticle -> shareArticle(action.context)
            is ArticleDetailsScreenAction.BrowseArticle -> browseArticle(action.context)
            else -> Unit
        }
    }

    private fun saveArticle() {
        viewModelScope.launch {
            val article = articlesUseCases.findByUrlArticle(url = article.url)
            val isNotFoundArticleInLocalDatabase = article == null

            if(isNotFoundArticleInLocalDatabase) {
                upsertArticle()
            } else {
                deleteArticle()
            }
        }
    }

    private suspend fun upsertArticle() {
        articlesUseCases.upsertArticle(article = article)
        state = state.copy(
            isArticleBookmarked = true,
            toastMessage = "Article saved!"
        )
    }

    private suspend fun deleteArticle() {
        articlesUseCases.deleteArticle(article = article)
        state = state.copy(
            isArticleBookmarked = false,
            toastMessage = "Article removed!"
        )
    }

    private fun shareArticle(context: Context) {
        Intent(Intent.ACTION_SEND).also {
            it.putExtra(Intent.EXTRA_TEXT, state.article.url)
            it.type = "text/plain"
            if (it.resolveActivity(context.packageManager) != null) {
                context.startActivity(it)
            }
        }
    }

    private fun browseArticle(context: Context) {
        Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse(state.article.url)
            if(it.resolveActivity(context.packageManager) != null) {
                context.startActivity(it)
            }
        }
    }

    private fun checkIsArticleBookmarked() {
        viewModelScope.launch {
            val article = articlesUseCases.findByUrlArticle(url = article.url)
            val isFoundArticleInLocalDatabase = article != null

            state = state.copy(
                isArticleBookmarked = isFoundArticleInLocalDatabase
            )
        }
    }
}