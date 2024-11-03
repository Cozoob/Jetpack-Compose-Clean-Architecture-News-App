package com.loc.newsapp.core.domain.repository

import androidx.paging.PagingData
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>>
}