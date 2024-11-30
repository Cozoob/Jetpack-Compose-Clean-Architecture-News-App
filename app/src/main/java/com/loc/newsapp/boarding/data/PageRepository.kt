package com.loc.newsapp.boarding.data

import com.loc.newsapp.boarding.data.dataSource.IPageDAO
import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.model.Page

class PageRepository(private val dao: IPageDAO) : IPageRepository {
  override fun getPages(): List<Page> {
    return dao.getPages()
  }
}
