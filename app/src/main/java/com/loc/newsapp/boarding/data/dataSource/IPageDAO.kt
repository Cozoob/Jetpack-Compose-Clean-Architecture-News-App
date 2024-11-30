package com.loc.newsapp.boarding.data.dataSource

import com.loc.newsapp.boarding.domain.model.Page

interface IPageDAO {
  fun getPages(): List<Page>
}
