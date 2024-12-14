package com.loc.newsapp.core.data.remote

import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.JsonParseException
import com.loc.newsapp.core.data.remote.dto.NewsResponse
import com.loc.newsapp.core.domain.factory.ArticleTestFactory
import com.loc.newsapp.core.domain.model.Article
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class NewsPagingSourceTest {
  private val newsApi = mockk<INewsApi>()
  private lateinit var newsPagingSource: NewsPagingSource
  private lateinit var result: PagingSource.LoadResult<Int, Article>

  @Test
  fun `Return 1 article`() = runTest {
    givenNewsPagingSourceWithNArticles(numberOfArticles = 1)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 1)
  }

  @Test
  fun `Return 0 articles`() = runTest {
    givenNewsPagingSourceWithNArticles(numberOfArticles = 0)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 0)
  }

  @Test
  fun `Return 1 article WHEN 2 articles have the same title`() = runTest {
    givenNewsPagingSourceWithNArticlesOfTheSameTitle(numberOfArticles = 2)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 1)
  }

  @Test
  fun `Return 1 article WHEN 10 articles have the same title`() = runTest {
    givenNewsPagingSourceWithNArticlesOfTheSameTitle(numberOfArticles = 10)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 1)
  }

  @Test
  fun `Return 2 articles WHEN 2 articles do not have the same title`() = runTest {
    givenNewsPagingSourceWithNArticlesOfDifferentTitle(numberOfArticles = 2)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 2)
  }

  @Test
  fun `Return 10 articles WHEN 10 articles do not have the same title`() = runTest {
    givenNewsPagingSourceWithNArticlesOfDifferentTitle(numberOfArticles = 10)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 10)
  }

  @Test
  fun `Return 10 articles WHEN 15 articles do not have the same title AND load size is 10`() =
      runTest {
        givenNewsPagingSourceWithNArticlesOfDifferentTitle(numberOfArticles = 10)
        whenNewsPagingSourceLoadNElements(loadSize = 10)
        thenResultIsNPages(numberOfPages = 10)
      }

  @Test
  fun `Throw HTTPException`() = runTest {
    givenNewsPagingSourceWithException(
        ex =
            HttpException(
                Response.error<ResponseBody>(
                    500, "some content".toResponseBody("plain/text".toMediaTypeOrNull()))))
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsError()
  }

  @Test
  fun `Throw IOException`() = runTest {
    givenNewsPagingSourceWithException(ex = IOException("error"))
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsError()
  }

  @Test
  fun `Throw JsonParseException`() = runTest {
    givenNewsPagingSourceWithException(ex = JsonParseException("error"))
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsError()
  }

  @Test
  fun `Throw SerializationException`() = runTest {
    givenNewsPagingSourceWithException(ex = SerializationException("error"))
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsError()
  }

  private fun givenNewsPagingSourceWithNArticles(numberOfArticles: Int) {
    val articles = mutableListOf<Article>()

    repeat(numberOfArticles) { articles.add(ArticleTestFactory.createArticle()) }

    coEvery { newsApi.getNews(page = any(), sources = any(), apiKey = any()) } returns
        NewsResponse(articles = articles, status = "ok", totalResults = numberOfArticles)

    newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "sourceTest")
  }

  private fun givenNewsPagingSourceWithNArticlesOfTheSameTitle(numberOfArticles: Int) {
    val articles = mutableListOf<Article>()

    repeat(numberOfArticles) {
      articles.add(ArticleTestFactory.createArticleWithTitle(title = "Wiedźmin"))
    }

    coEvery { newsApi.getNews(page = any(), sources = any(), apiKey = any()) } returns
        NewsResponse(articles = articles, status = "ok", totalResults = numberOfArticles)

    newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "sourceTest")
  }

  private fun givenNewsPagingSourceWithNArticlesOfDifferentTitle(numberOfArticles: Int) {
    val articles = mutableListOf<Article>()

    repeat(numberOfArticles) {
      articles.add(ArticleTestFactory.createArticleWithTitle(title = "Wiedźmin$it"))
    }

    coEvery { newsApi.getNews(page = any(), sources = any(), apiKey = any()) } returns
        NewsResponse(articles = articles, status = "ok", totalResults = numberOfArticles)

    newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "sourceTest")
  }

  private fun givenNewsPagingSourceWithException(ex: Throwable) {
    mockkStatic(FirebaseCrashlytics::class)
    val firebaseCrashlytics = mockk<FirebaseCrashlytics>()
    every { FirebaseCrashlytics.getInstance() } returns firebaseCrashlytics
    every { firebaseCrashlytics.recordException(any()) } just Runs

    coEvery { newsApi.getNews(page = any(), sources = any(), apiKey = any()) } throws ex

    newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "sourceTest")
  }

  private suspend fun whenNewsPagingSourceLoadNElements(loadSize: Int) {
    result =
        newsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1, loadSize = loadSize, placeholdersEnabled = false))
  }

  private fun thenResultIsNPages(numberOfPages: Int) {
    assertTrue(result is PagingSource.LoadResult.Page)
    val page = result as PagingSource.LoadResult.Page
    assertEquals(numberOfPages, page.data.size)
  }

  private fun thenResultIsError() {
    assertTrue(result is PagingSource.LoadResult.Error)
  }
}
