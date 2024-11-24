package com.loc.newsapp.bookmark.domain

data class ArticlesUseCases(
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val findByTitlesArticles: FindByTitlesArticles,
    val findByUrlsArticles: FindByUrlsArticles,
    val getAllArticles: GetAllArticles,
)