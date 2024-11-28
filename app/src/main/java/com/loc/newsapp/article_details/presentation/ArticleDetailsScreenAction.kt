package com.loc.newsapp.article_details.presentation

import android.content.Context

sealed interface ArticleDetailsScreenAction {
  data object SaveArticle : ArticleDetailsScreenAction

  data class ShareArticle(val context: Context) : ArticleDetailsScreenAction

  data class BrowseArticle(val context: Context) : ArticleDetailsScreenAction

  data object NavigateUp : ArticleDetailsScreenAction
}
