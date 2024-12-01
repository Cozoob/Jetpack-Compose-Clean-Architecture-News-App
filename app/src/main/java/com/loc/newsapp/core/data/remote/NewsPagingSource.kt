package com.loc.newsapp.core.data.remote

import androidx.datastore.core.IOException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonParseException
import com.loc.newsapp.core.data.remote.dto.NewsResponse
import com.loc.newsapp.core.domain.model.Article
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

class NewsPagingSource(private val newsApi: INewsApi, private val sources: String) :
    PagingSource<Int, Article>() {
  private var totalNewsCount = 0

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    val page = params.key ?: 1
    return try {
      val newsResponse = newsApi.getNews(sources = sources, page = page)

      totalNewsCount += newsResponse.articles.size
      val articlesData = getDistinctArticles(newsResponse)
      val nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1

      LoadResult.Page(data = articlesData, nextKey = nextKey, prevKey = null)
    } catch (e: HttpException) {
      Firebase.crashlytics.recordException(e)
      LoadResult.Error(throwable = Throwable("HTTP error occurred: ${e.message}"))
    } catch (e: IOException) {
      Firebase.crashlytics.recordException(e)
      LoadResult.Error(throwable = Throwable("Network error: ${e.message}"))
    } catch (e: JsonParseException) {
      Firebase.crashlytics.recordException(e)
      LoadResult.Error(throwable = Throwable("Failed to parse response: ${e.message}"))
    } catch (e: SerializationException) {
      Firebase.crashlytics.recordException(e)
      LoadResult.Error(throwable = Throwable("Serialization error: ${e.message}"))
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }

  private fun getDistinctArticles(newsResponse: NewsResponse): List<Article> {
    return newsResponse.articles.distinctBy { it.title }
  }
}
