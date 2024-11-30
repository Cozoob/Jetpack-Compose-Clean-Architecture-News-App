package com.loc.newsapp.articleDetails.presentation

import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.presentation.util.UiText

data class ArticleDetailsScreenState(
    val article: Article,
    val isArticleBookmarked: Boolean = false,
    val toastMessage: UiText = UiText.DynamicString()
)
