package com.loc.newsapp.core.domain.use_case.news

import androidx.paging.PagingData
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(private val newsRepository: INewsRepository) {
  operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
    return newsRepository.searchNews(searchQuery = searchQuery, sources = sources)
  }
}
