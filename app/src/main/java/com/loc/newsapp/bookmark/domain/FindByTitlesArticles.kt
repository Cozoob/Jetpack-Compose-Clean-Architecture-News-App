package com.loc.newsapp.bookmark.domain

import com.loc.newsapp.core.data.local.dao.IArticleDao
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

class FindByTitlesArticles(
    private val articleDao: IArticleDao
) {
    operator fun invoke(titles: List<String>) : Flow<List<Article>> {
        return articleDao.findByTitles(titles)
    }
}