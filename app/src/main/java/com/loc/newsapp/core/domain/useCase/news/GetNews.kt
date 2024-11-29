package com.loc.newsapp.core.domain.useCase.news

import androidx.paging.PagingData
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(private val newsRepository: INewsRepository) {
  operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
    return newsRepository.getNews(sources = sources)
  }
}
