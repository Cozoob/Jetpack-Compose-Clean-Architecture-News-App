package com.loc.newsapp.boarding.data

import com.loc.newsapp.boarding.data.data_source.IPageDAO
import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.model.Page

class PageRepository(private val dao: IPageDAO) : IPageRepository {
  override fun getPages(): List<Page> {
    return dao.getPages()
  }

  override suspend fun getPageById(id: Int): Page? {
    return dao.getPageById(id)
  }

  override suspend fun insertPage(page: Page) {
    dao.insertPage(page)
  }

  override suspend fun deletePage(page: Page) {
    dao.deletePage(page)
  }
}
