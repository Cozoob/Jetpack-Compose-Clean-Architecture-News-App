package com.loc.newsapp.bookmark.domain

import com.loc.newsapp.core.data.local.dao.IArticleDao
import com.loc.newsapp.core.domain.model.Article

class FindByUrlArticle(private val articleDao: IArticleDao) {
  suspend operator fun invoke(url: String): Article? {
    return articleDao.findByUrl(url)
  }
}
