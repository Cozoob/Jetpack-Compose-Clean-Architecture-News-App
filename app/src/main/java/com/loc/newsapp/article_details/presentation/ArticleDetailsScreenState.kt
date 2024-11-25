package com.loc.newsapp.article_details.presentation

import com.loc.newsapp.core.domain.model.Article

data class ArticleDetailsScreenState(
    val article: Article,
    val isArticleBookmarked: Boolean = false,
    val toastMessage: String = ""
)