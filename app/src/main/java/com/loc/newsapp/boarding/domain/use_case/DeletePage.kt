package com.loc.newsapp.boarding.domain.use_case

import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.model.Page

class DeletePage(
    private val repository: IPageRepository
) {
    suspend operator fun invoke(page: Page) {
        repository.deletePage(page)
    }
}