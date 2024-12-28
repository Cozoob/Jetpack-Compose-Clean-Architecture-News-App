package com.loc.newsapp.articleDetails.presentation

sealed interface ArticleDetailsScreenAction {
  data object SaveArticle : ArticleDetailsScreenAction

  data object ShareArticle : ArticleDetailsScreenAction

  data object BrowseArticle : ArticleDetailsScreenAction

  data object NavigateUp : ArticleDetailsScreenAction
}
