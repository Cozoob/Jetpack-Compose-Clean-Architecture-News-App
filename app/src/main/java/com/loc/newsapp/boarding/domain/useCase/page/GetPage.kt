package com.loc.newsapp.boarding.domain.useCase.page

import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.model.Page

class GetPage(private val repository: IPageRepository) {
  suspend operator fun invoke(id: Int): Page? {
    return repository.getPageById(id)
  }
}
