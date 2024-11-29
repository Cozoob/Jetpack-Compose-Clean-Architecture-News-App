package com.loc.newsapp.boarding.domain.useCase.page

import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.InvalidPageException
import com.loc.newsapp.boarding.domain.model.Page

class AddPage(private val repository: IPageRepository) {
  @Throws(InvalidPageException::class)
  suspend operator fun invoke(page: Page) {
    titleCannotBeBlank(page)
    descriptionCannotBeBlank(page)
    repository.insertPage(page)
  }

  private fun titleCannotBeBlank(page: Page) {
    if (page.title.isBlank()) {
      throw InvalidPageException("The title of the page can't be empty.")
    }
  }

  private fun descriptionCannotBeBlank(page: Page) {
    if (page.description.isBlank()) {
      throw InvalidPageException("The description of the page can't be empty.")
    }
  }
}
