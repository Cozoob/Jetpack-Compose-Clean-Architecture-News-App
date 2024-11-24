package com.loc.newsapp.bookmark

import android.app.Application
import androidx.room.Room
import com.loc.newsapp.bookmark.domain.ArticlesUseCases
import com.loc.newsapp.bookmark.domain.DeleteArticle
import com.loc.newsapp.bookmark.domain.FindByTitlesArticles
import com.loc.newsapp.bookmark.domain.FindByUrlsArticles
import com.loc.newsapp.bookmark.domain.GetAllArticles
import com.loc.newsapp.bookmark.domain.UpsertArticle
import com.loc.newsapp.core.data.local.LocalDatabase
import com.loc.newsapp.core.data.local.NewsTypeConvertor
import com.loc.newsapp.core.data.local.dao.IArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookmarkModule {
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
    ): IArticleDao = newsDatabase.articleDao()

    @Provides
    @Singleton
    fun provideArticlesUseCases(articleDao: IArticleDao): ArticlesUseCases {
        return ArticlesUseCases(
            upsertArticle = UpsertArticle(articleDao),
            deleteArticle = DeleteArticle(articleDao),
            findByUrlsArticles = FindByUrlsArticles(articleDao),
            findByTitlesArticles = FindByTitlesArticles(articleDao),
            getAllArticles = GetAllArticles(articleDao)
        )
    }
}