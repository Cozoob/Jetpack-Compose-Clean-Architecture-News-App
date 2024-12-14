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

  @Test
  fun `Return single article`() = runTest {
    // GIVEN
    coEvery {
      newsApi.getNews(
          page = capture(slot<Int>()),
          sources = capture(slot<String>()),
          apiKey = capture(slot<String>()))
    } returns NewsResponse(listOf(getSampleArticle()), status = "ok", totalResults = 1)
    val newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "source1")

    // WHEN
    val result =
        newsPagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

    // THEN
    assertTrue(result is PagingSource.LoadResult.Page)
    val page = result as PagingSource.LoadResult.Page
    assertEquals(1, page.data.size)
  }

  @Test
  fun `Return single article when two articles have the same title`() = runTest {
    // GIVEN
    setNewsApiGetNewsResponse(
        articles = listOf(getSampleArticle(), getSampleArticle()), status = "ok", totalResults = 2)
    val newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "source1")

    // WHEN
    val result =
        newsPagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

    // THEN
    assertTrue(result is PagingSource.LoadResult.Page)
    val page = result as PagingSource.LoadResult.Page
    assertEquals(1, page.data.size)
  }

  @Test
  fun `Return two articles when two articles do not have the same title`() = runTest {
    // GIVEN
    setNewsApiGetNewsResponse(
        articles = listOf(getSampleArticle(title = "title1"), getSampleArticle(title = "title2")),
        status = "ok",
        totalResults = 2)
    val newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "source1")

    // WHEN
    val result =
        newsPagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

    // THEN
    assertTrue(result is PagingSource.LoadResult.Page)
    val page = result as PagingSource.LoadResult.Page
    assertEquals(2, page.data.size)
  }

  @Test
  fun `Throw HTTP exception`() = runTest {
    // GIVEN
    // setup mockk for firebase crashlytics
    mockkStatic(FirebaseCrashlytics::class)
    val firebaseCrashlytics = mockk<FirebaseCrashlytics>()
    every { FirebaseCrashlytics.getInstance() } returns firebaseCrashlytics
    every { firebaseCrashlytics.recordException(capture(slot<Throwable>())) } just Runs
    // setup the rest...
    coEvery {
      newsApi.getNews(
          page = capture(slot<Int>()),
          sources = capture(slot<String>()),
          apiKey = capture(slot<String>()))
    } throws
        HttpException(
            Response.error<ResponseBody>(
                500, "some content".toResponseBody("plain/text".toMediaTypeOrNull())))
    val newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "source1")

    // WHEN
    val result =
        newsPagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

    // THEN
    assertTrue(result is PagingSource.LoadResult.Error)
  }

  @Test
  fun `Throw IO exception`() = runTest {
    // GIVEN
    // setup mockk for firebase crashlytics
    mockkStatic(FirebaseCrashlytics::class)
    val firebaseCrashlytics = mockk<FirebaseCrashlytics>()
    every { FirebaseCrashlytics.getInstance() } returns firebaseCrashlytics
    every { firebaseCrashlytics.recordException(capture(slot<Throwable>())) } just Runs
    // setup the rest...
    coEvery {
      newsApi.getNews(
          page = capture(slot<Int>()),
          sources = capture(slot<String>()),
          apiKey = capture(slot<String>()))
    } throws IOException("error")
    val newsPagingSource = NewsPagingSource(newsApi = newsApi, sources = "source1")

    // WHEN
    val result =
        newsPagingSource.load(
            PagingSource.LoadParams.Refresh(key = 1, loadSize = 10, placeholdersEnabled = false))

    // THEN
    assertTrue(result is PagingSource.LoadResult.Error)
  }

  // TODO to data factory?
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

  // TODO to data factory?
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
