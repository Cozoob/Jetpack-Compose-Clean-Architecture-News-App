package com.loc.newsapp.bookmark.domain

import com.loc.newsapp.core.data.local.dao.IArticleDao
import com.loc.newsapp.core.domain.model.Article

class UpsertArticle(private val articleDao: IArticleDao) {
  suspend operator fun invoke(article: Article) {
    articleDao.upsert(article = article)
  }
}
