package com.loc.newsapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.loc.newsapp.core.data.local.dao.IArticleDao
import com.loc.newsapp.core.domain.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(NewsTypeConvertor::class)
abstract class LocalDatabase : RoomDatabase() {
  abstract fun articleDao(): IArticleDao
}
