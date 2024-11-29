package com.loc.newsapp.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.JsonParseException
import com.loc.newsapp.core.domain.model.Article
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

class SearchNewsPagingSource(
    private val api: INewsApi,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {

  override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return state.anchorPosition?.let { anchorPage ->
      val page = state.closestPageToPosition(anchorPage)
      page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
    }
  }

  private var totalNewsCount = 0

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    val page = params.key ?: 1
    return try {
      val newsResponse = api.searchNews(searchQuery = searchQuery, sources = sources, page = page)
      totalNewsCount += newsResponse.articles.size
      val articles = newsResponse.articles.distinctBy { it.title }

      LoadResult.Page(
          data = articles,
          nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
          prevKey = null)
    } catch (e: HttpException) {
      LoadResult.Error(throwable = Throwable("HTTP error occurred: ${e.message}"))
    } catch (e: JsonParseException) {
      LoadResult.Error(throwable = Throwable("Failed to parse JSON response: ${e.message}"))
    } catch (e: SerializationException) {
      LoadResult.Error(throwable = Throwable("Serialization error: ${e.message}"))
    } catch (e: IllegalArgumentException) {
      LoadResult.Error(throwable = Throwable("Invalid arguments: ${e.message}"))
    } catch (e: IllegalStateException) {
      LoadResult.Error(throwable = Throwable("Illegal state: ${e.message}"))
    }
  }
}
