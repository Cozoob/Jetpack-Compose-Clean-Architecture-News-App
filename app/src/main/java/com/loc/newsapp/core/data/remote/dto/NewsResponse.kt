package com.loc.newsapp.core.data.remote.dto

import com.loc.newsapp.core.domain.model.Article

data class NewsResponse(val articles: List<Article>, val status: String, val totalResults: Int)
