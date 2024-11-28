package com.loc.newsapp.core.domain.routes

import com.loc.newsapp.core.domain.model.Article
import kotlinx.serialization.Serializable

@Serializable data class ArticleDetailsRoute(val article: Article) : IRoute
