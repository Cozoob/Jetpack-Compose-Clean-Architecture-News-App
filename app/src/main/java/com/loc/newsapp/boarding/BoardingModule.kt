package com.loc.newsapp.boarding

import android.app.Application
import com.loc.newsapp.boarding.data.PageRepository
import com.loc.newsapp.boarding.data.dataSource.IPageDAO
import com.loc.newsapp.boarding.data.dataSource.PageStaticService
import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.useCase.appEntry.AppEntryUseCases
import com.loc.newsapp.boarding.domain.useCase.appEntry.ReadAppEntry
import com.loc.newsapp.boarding.domain.useCase.appEntry.WriteAppEntry
import com.loc.newsapp.boarding.domain.useCase.page.AddPage
import com.loc.newsapp.boarding.domain.useCase.page.DeletePage
import com.loc.newsapp.boarding.domain.useCase.page.GetPage
import com.loc.newsapp.boarding.domain.useCase.page.GetPages
import com.loc.newsapp.boarding.domain.useCase.page.PageUseCases
import com.loc.newsapp.core.domain.repository.ILocalDataRepository
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
        addPage = AddPage(repository))
  }

  @Provides
  @Singleton
  fun provideAppEntryUseCases(repository: ILocalDataRepository): AppEntryUseCases {
    return AppEntryUseCases(
        readAppEntry = ReadAppEntry(repository), writeAppEntry = WriteAppEntry(repository))
  }
}
