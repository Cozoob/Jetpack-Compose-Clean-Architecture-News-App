package com.loc.newsapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.loc.newsapp.core.data.local.LocalDatabase
import com.loc.newsapp.core.data.local.NewsTypeConvertor
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source
import com.loc.newsapp.util.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class IArticleDaoTest {
  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var database: LocalDatabase
  private lateinit var dao: IArticleDao
  private lateinit var article: Article

  @Before
  fun setup() {
    article = getSampleArticle()
    database =
        Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(), LocalDatabase::class.java)
            .addTypeConverter(NewsTypeConvertor())
            .allowMainThreadQueries()
            .build()
    dao = database.articleDao()
  }

  @After
  fun teardown() {
    database.close()
  }

  @Test
  fun deleteArticle() = runTest {
    dao.upsert(article)

    dao.delete(article)

    val allArticles = dao.observeAllArticles().getOrAwaitValue()
    assertThat(allArticles).doesNotContain(article)
  }

  @Test
  fun getAllArticle() = runTest {
    val article1 = getSampleArticle()
    val article2 = getSampleArticle()
    dao.upsert(article)
    dao.upsert(article1)
    dao.upsert(article2)

    val articles = dao.getAll().asLiveData().getOrAwaitValue()

    assertThat(articles).contains(article)
    assertThat(articles).contains(article1)
    assertThat(articles).contains(article2)
  }

  @Test
  fun foundByUrl() = runTest {
    dao.upsert(article)

    val foundArticle = dao.findByUrl(article.url)

    assertThat(foundArticle).isNotNull()
  }

  @Test
  fun notFoundByUrl() = runTest {
    val foundArticle = dao.findByUrl(article.url)

    assertThat(foundArticle).isNull()
  }

  @Test
  fun foundByUrls() = runTest {
    dao.upsert(article)

    val foundArticles = dao.findByUrls(listOf(article.url)).asLiveData().getOrAwaitValue()

    assertThat(foundArticles).hasSize(1)
  }

  @Test
  fun notFoundByUrls() = runTest {
    val foundArticles = dao.findByUrls(listOf(article.url)).asLiveData().getOrAwaitValue()

    assertThat(foundArticles).hasSize(0)
  }

  @Test
  fun foundByTitles() = runTest {
    dao.upsert(article)

    val foundArticles = dao.findByTitles(listOf(article.title)).asLiveData().getOrAwaitValue()

    assertThat(foundArticles).hasSize(1)
  }

  @Test
  fun notFoundByTitles() = runTest {
    val foundArticles = dao.findByTitles(listOf(article.title)).asLiveData().getOrAwaitValue()

    assertThat(foundArticles).hasSize(0)
  }

  @Test
  fun upsertArticle() = runTest {
    dao.upsert(article)

    val allArticles = dao.observeAllArticles().getOrAwaitValue()
    assertThat(allArticles).contains(article)
  }

  private fun getSampleArticle(): Article {
    return Article(
        author = "John Smith",
        content =
            "Recent studies reveal that advancements in AI technology are " +
                "transforming industries at an unprecedented rate.",
        description = "An in-depth look at how AI is reshaping the future of work and daily life.",
        publishedAt = "2024.11.20",
        source = Source(id = "techcrunch", name = "TechCrunch"),
        title = "The Rise of AI: Opportunities and Challenges",
        url = "https://www.techcrunch.com/articles/rise-of-ai",
        urlToImage = "https://www.techcrunch.com/images/ai-article.jpg")
  }
}
