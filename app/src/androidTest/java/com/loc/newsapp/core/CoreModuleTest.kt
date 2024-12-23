package com.loc.newsapp.core

import android.content.Context
import androidx.room.Room
import com.loc.newsapp.core.data.local.LocalDatabase
import com.loc.newsapp.core.data.local.NewsTypeConvertor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object CoreModuleTest {

  @Provides
  @Named("TestDatabase")
  fun provideInMemoryDb(@ApplicationContext context: Context) =
      Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java)
          .addTypeConverter(NewsTypeConvertor())
          .allowMainThreadQueries()
          .build()
}
