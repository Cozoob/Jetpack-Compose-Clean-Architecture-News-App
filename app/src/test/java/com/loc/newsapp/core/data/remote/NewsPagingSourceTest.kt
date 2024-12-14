package com.loc.newsapp.core.data.remote

import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.loc.newsapp.core.data.remote.dto.NewsResponse
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.test.runTest
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
  fun `Return single article`() = runTest {
    givenNewsPagingSourceWithNArticles(numberOfArticles = 1)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 1)
  }

  @Test
  fun `Return single article when two articles have the same title`() = runTest {
    givenNewsPagingSourceWithNArticlesOfTheSameTitle(numberOfArticles = 2)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 1)
  }

  @Test
  fun `Return two articles when two articles do not have the same title`() = runTest {
    givenNewsPagingSourceWithNArticlesOfDifferentTitle(numberOfArticles = 2)
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsNPages(numberOfPages = 2)
  }

  @Test
  fun `Throw HTTP exception`() = runTest {
    givenNewsPagingSourceWithException(
        ex =
            HttpException(
                Response.error<ResponseBody>(
                    500, "some content".toResponseBody("plain/text".toMediaTypeOrNull()))))
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsError()
  }

  @Test
  fun `Throw IO exception`() = runTest {
    givenNewsPagingSourceWithException(ex = IOException("error"))
    whenNewsPagingSourceLoadNElements(loadSize = 10)
    thenResultIsError()
  }

  // TODO maybe i can make it more readable?
  private fun givenNewsPagingSourceWithNArticles(numberOfArticles: Int) {
    val articles = mutableListOf<Article>()

    repeat(numberOfArticles) { articles.add(getSampleArticle()) }

    coEvery { newsApi.getNews(page = any(), sources = any(), apiKey = any()) } returns
        NewsResponse(articles = articles, status = "ok", totalResults = numberOfArticles)

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

  private fun givenNewsPagingSourceWithNArticlesOfTheSameTitle(numberOfArticles: Int) {
    val articles = mutableListOf<Article>()

    repeat(numberOfArticles) { articles.add(getSampleArticle(title = "Wiedźmin")) }

    coEvery { newsApi.getNews(page = any(), sources = any(), apiKey = any()) } returns
        NewsResponse(articles = articles, status = "ok", totalResults = numberOfArticles)

    newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "sourceTest")
  }

  private fun givenNewsPagingSourceWithNArticlesOfDifferentTitle(numberOfArticles: Int) {
    val articles = mutableListOf<Article>()

    repeat(numberOfArticles) { articles.add(getSampleArticle(title = "Wiedźmin$it")) }

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

  private fun thenResultIsError() {
    assertTrue(result is PagingSource.LoadResult.Error)
  }

  // TODO to data (randomization) factory?
  private fun setNewsApiGetNewsResponse(
      articles: List<Article>,
      status: String,
      totalResults: Int
  ) {
    coEvery {
      newsApi.getNews(
          page = capture(slot<Int>()),
          sources = capture(slot<String>()),
          apiKey = capture(slot<String>()))
    } returns NewsResponse(articles = articles, status = status, totalResults = totalResults)
  }

  // TODO to data (randomization) factory?
  private fun getSampleArticle(
      title: String = "The Rise of AI: Opportunities and Challenges"
  ): Article {
    return Article(
        author = "John Smith",
        content =
            "Recent studies reveal that advancements in AI technology are " +
                "transforming industries at an unprecedented rate.",
        description = "An in-depth look at how AI is reshaping the future of work and daily life.",
        publishedAt = "2024.11.20",
        source = Source(id = "techcrunch", name = "TechCrunch"),
        title = title,
        url = "https://www.techcrunch.com/articles/rise-of-ai",
        urlToImage = "https://www.techcrunch.com/images/ai-article.jpg")
  }
}
