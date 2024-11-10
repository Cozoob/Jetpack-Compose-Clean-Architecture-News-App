package com.loc.newsapp.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Article(
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @ColumnInfo(name = "source") val source: Source,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey @ColumnInfo(name = "url", index = true) val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String
)