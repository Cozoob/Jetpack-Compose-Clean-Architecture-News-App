package com.loc.newsapp.core.presentation.components.articles_list

import com.loc.newsapp.core.domain.model.Article

sealed interface ArticlesListAction {
    data class ClickArticle(
        val article: Article
    ): ArticlesListAction
}