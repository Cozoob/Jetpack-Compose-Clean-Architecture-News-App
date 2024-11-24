package com.loc.newsapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loc.newsapp.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface IArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getAll(): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE url IN (:urls)")
    fun findByUrls(urls: List<String>): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE title IN (:titles)")
    fun findByTitles(titles: List<String>): Flow<List<Article>>
}