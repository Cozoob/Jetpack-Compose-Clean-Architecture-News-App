package com.loc.newsapp.core.data.remote

import com.loc.newsapp.BuildConfig
import com.loc.newsapp.core.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponse
}