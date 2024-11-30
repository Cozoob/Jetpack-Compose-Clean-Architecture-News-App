package com.loc.newsapp.core

import android.app.Application
import com.loc.newsapp.core.data.local.LocalDataRepository
import com.loc.newsapp.core.data.remote.INewsApi
import com.loc.newsapp.core.data.remote.NewsRepository
import com.loc.newsapp.core.domain.Constants.BASE_URL
import com.loc.newsapp.core.domain.repository.ILocalDataRepository
import com.loc.newsapp.core.domain.repository.INewsRepository
import com.loc.newsapp.core.domain.useCase.news.GetNews
import com.loc.newsapp.core.domain.useCase.news.NewsUseCases
import com.loc.newsapp.core.domain.useCase.news.SearchNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
  @Provides
  @Singleton
  fun provideLocalDataService(app: Application): ILocalDataRepository {
    return LocalDataRepository(app)
  }

  @Provides
  @Singleton
  fun provideNewsApi(): INewsApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(INewsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideNewsRepository(newsApi: INewsApi): INewsRepository {
    return NewsRepository(newsApi)
  }

  @Provides
  @Singleton
  fun provideNewsUseCases(repository: INewsRepository): NewsUseCases {
    return NewsUseCases(getNews = GetNews(repository), searchNews = SearchNews(repository))
  }
}
