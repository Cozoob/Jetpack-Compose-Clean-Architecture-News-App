package com.loc.newsapp.core.domain.route

import com.loc.newsapp.core.domain.model.Article
import kotlinx.serialization.Serializable

@Serializable data class ArticleDetailsRoute(val article: Article) : IRoute
