package com.loc.newsapp.boarding.domain

import com.loc.newsapp.boarding.domain.model.Page

interface IPageRepository {
  fun getPages(): List<Page>
}
