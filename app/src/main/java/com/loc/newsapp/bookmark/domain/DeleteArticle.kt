package com.loc.newsapp.bookmark.domain

import com.loc.newsapp.core.data.local.dao.IArticleDao
import com.loc.newsapp.core.domain.model.Article

class DeleteArticle(private val articleDao: IArticleDao) {
  suspend operator fun invoke(article: Article) {
    articleDao.delete(article = article)
  }
}
