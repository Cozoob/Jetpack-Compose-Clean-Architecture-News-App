package com.loc.newsapp.boarding.data.data_source

import androidx.datastore.core.Closeable
import com.loc.newsapp.boarding.domain.model.Page

class PageService(
    //    private val channel: ManagedChannel
    // NEED TO ADD GRPC TO PROJECT !!! ->
    // the exmaple
    // https://github.com/grpc/grpc-kotlin/blob/master/examples/client/src/main/kotlin/io/grpc/examples/animals/AnimalsClient.kt
) : IPageDAO, Closeable {
  //    private val pageStub: PageGrpcKt.PageCoroutineStub by lazy {
  // PageGrpcKt.PigCoroutineStub(channel) }

  override fun getPages(): List<Page> {
    TODO("Not yet implemented")
    //        val request = pageRequest {}
    //        val response = pageStub.getAll(request)
    //        val List<Page> pages = TODO PROCESS PAGES
    //        return pages

    //        emit(emptyList<Page>())
  }

  override suspend fun getPageById(id: Int): Page? {
    TODO("Not yet implemented")
  }

  override suspend fun insertPage(page: Page) {
    TODO("Not yet implemented")
  }

  override suspend fun deletePage(page: Page) {
    TODO("Not yet implemented")
  }

  override fun close() {
    //        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
  }
}
