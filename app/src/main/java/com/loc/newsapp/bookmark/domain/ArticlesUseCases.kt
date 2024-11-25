package com.loc.newsapp.bookmark.domain

data class ArticlesUseCases(
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val findByTitlesArticles: FindByTitlesArticles,
    val findByUrlArticle: FindByUrlArticle,
    val findByUrlsArticles: FindByUrlsArticles,
    val getAllArticles: GetAllArticles,
)