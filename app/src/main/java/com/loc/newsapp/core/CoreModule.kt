package com.loc.newsapp.core

import android.app.Application
import com.loc.newsapp.core.data.LocalLiteDataRepository
import com.loc.newsapp.core.domain.ILocalDataRepository
import com.loc.newsapp.core.domain.use_case.LocalDataUseCases
import com.loc.newsapp.core.domain.use_case.ReadAppEntry
import com.loc.newsapp.core.domain.use_case.WriteAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun providePagesService(app: Application): ILocalDataRepository {
        return LocalLiteDataRepository(app)
    }

    @Provides
    @Singleton
    fun provideLocalDataUseCases(repository: ILocalDataRepository) : LocalDataUseCases {
        return LocalDataUseCases(
            readAppEntry = ReadAppEntry(repository),
            writeAppEntry = WriteAppEntry(repository)
        )
    }
}