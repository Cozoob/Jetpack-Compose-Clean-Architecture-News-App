package com.loc.newsapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loc.newsapp.core.domain.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM Article")
    fun getAll(): List<Article>

    @Query("SELECT * FROM Article WHERE url IN (:urls)")
    fun findByUrls(urls: List<String>): List<Article>

    @Query("SELECT * FROM Article WHERE title IN (:titles)")
    fun findByTitles(titles: List<String>): List<Article>
}