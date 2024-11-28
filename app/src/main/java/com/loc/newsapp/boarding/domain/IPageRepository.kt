package com.loc.newsapp.boarding.domain

import com.loc.newsapp.boarding.domain.model.Page

interface IPageRepository {
  fun getPages(): List<Page>

  suspend fun getPageById(id: Int): Page?

  suspend fun insertPage(page: Page)

  suspend fun deletePage(page: Page)
}
