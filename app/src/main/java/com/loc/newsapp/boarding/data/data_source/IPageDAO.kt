package com.loc.newsapp.boarding.data.data_source

import com.loc.newsapp.boarding.domain.model.Page

interface IPageDAO {
    fun getPages(): List<Page>

    suspend fun getPageById(id: Int): Page?

    suspend fun insertPage(page: Page)

    suspend fun deletePage(page: Page)
}