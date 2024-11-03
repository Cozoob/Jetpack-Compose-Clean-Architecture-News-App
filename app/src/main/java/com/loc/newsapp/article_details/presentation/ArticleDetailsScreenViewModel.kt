package com.loc.newsapp.article_details.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.loc.newsapp.core.domain.model.Article

class ArticleDetailsScreenViewModel (
    article: Article
){
    var state by mutableStateOf(ArticleDetailsScreenState(article = article))
        private set

    fun onAction(action: ArticleDetailsScreenAction) {
        when(action) {
            is ArticleDetailsScreenAction.SaveArticle -> saveArticle()
            is ArticleDetailsScreenAction.ShareArticle -> shareArticle(action.context)
            is ArticleDetailsScreenAction.BrowseArticle -> browseArticle(action.context)
            else -> Unit
        }
    }

    private fun saveArticle() {

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
}