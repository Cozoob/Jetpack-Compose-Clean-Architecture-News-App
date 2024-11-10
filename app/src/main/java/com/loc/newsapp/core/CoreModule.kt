package com.loc.newsapp.core

import android.app.Application
import androidx.room.Room
import com.loc.newsapp.core.data.local.LocalDatabase
import com.loc.newsapp.core.data.local.LocalLiteDataRepository
import com.loc.newsapp.core.data.local.NewsTypeConvertor
import com.loc.newsapp.core.data.local.dao.ArticleDao
import com.loc.newsapp.core.data.remote.INewsApi
import com.loc.newsapp.core.data.remote.NewsRepository
import com.loc.newsapp.core.domain.Constants.BASE_URL
import com.loc.newsapp.core.domain.repository.ILocalDataRepository
import com.loc.newsapp.core.domain.repository.INewsRepository
import com.loc.newsapp.core.domain.use_case.news.GetNews
import com.loc.newsapp.core.domain.use_case.news.NewsUseCases
import com.loc.newsapp.core.domain.use_case.news.SearchNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideLocalDataService(app: Application): ILocalDataRepository {
        return LocalLiteDataRepository(app)
    }

    @Provides
    @Singleton
    fun provideNewsApi() : INewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(INewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: INewsApi) : INewsRepository {
        return NewsRepository(newsApi)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: INewsRepository) : NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(repository),
            searchNews = SearchNews(repository)
        )
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(
        application: Application
    ): LocalDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = LocalDatabase::class.java,
            name = "local_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(
        newsDatabase: LocalDatabase
    ): ArticleDao = newsDatabase.articleDao()
}