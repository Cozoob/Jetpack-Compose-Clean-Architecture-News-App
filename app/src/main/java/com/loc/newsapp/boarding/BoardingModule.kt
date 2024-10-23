package com.loc.newsapp.boarding

import android.app.Application
import com.loc.newsapp.boarding.data.PageRepository
import com.loc.newsapp.boarding.data.data_source.IPageDAO
import com.loc.newsapp.boarding.data.data_source.PageService
import com.loc.newsapp.boarding.data.data_source.PageStaticService
import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.use_case.AddPage
import com.loc.newsapp.boarding.domain.use_case.DeletePage
import com.loc.newsapp.boarding.domain.use_case.GetPage
import com.loc.newsapp.boarding.domain.use_case.GetPages
import com.loc.newsapp.boarding.domain.use_case.PageUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BoardingModule {
    @Provides
    @Singleton
    @Named("PageStaticService")
    fun providePageStaticService(app: Application): IPageDAO {
        return PageStaticService()
    }

    @Provides
    @Singleton
    @Named("PageService")
    fun providePageService(app: Application): IPageDAO {
        return PageService()
    }

    @Provides
    @Singleton
    fun providePageRepository(@Named("PageStaticService") dao: IPageDAO): IPageRepository {
        return PageRepository(dao)
    }

    @Provides
    @Singleton
    fun providePageUseCases(repository: IPageRepository): PageUseCases {
        return PageUseCases(
            getPages = GetPages(repository),
            getPage = GetPage(repository),
            deletePage = DeletePage(repository),
            addPage = AddPage(repository)
        )
    }
}