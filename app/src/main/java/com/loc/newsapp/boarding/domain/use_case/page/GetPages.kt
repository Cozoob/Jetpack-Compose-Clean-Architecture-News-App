package com.loc.newsapp.boarding.domain.use_case.page

import com.loc.newsapp.boarding.domain.IPageRepository
import com.loc.newsapp.boarding.domain.model.OrderType
import com.loc.newsapp.boarding.domain.model.Page
import com.loc.newsapp.boarding.domain.model.PageOrder

class GetPages(private val repository: IPageRepository) {
  operator fun invoke(pageOrder: PageOrder = PageOrder.Title(OrderType.Descending)): List<Page> {
    val pages = repository.getPages()

    return when (pageOrder.orderType) {
      is OrderType.Ascending -> {
        sortPagesAscending(pageOrder, pages)
      }

      is OrderType.Descending -> {
        sortPagesDescending(pageOrder, pages)
      }
    }
  }

  private fun sortPagesAscending(pageOrder: PageOrder, pages: List<Page>): List<Page> {
    return when (pageOrder) {
      is PageOrder.Title -> pages.sortedBy { it.title.lowercase() }
      is PageOrder.Description -> pages.sortedBy { it.description.lowercase() }
    }
  }

  private fun sortPagesDescending(pageOrder: PageOrder, pages: List<Page>): List<Page> {
    return when (pageOrder) {
      is PageOrder.Title -> pages.sortedByDescending { it.title.lowercase() }
      is PageOrder.Description -> pages.sortedByDescending { it.description.lowercase() }
    }
  }
}
