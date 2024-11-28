package com.loc.newsapp.boarding.domain.model

sealed class PageOrder(val orderType: OrderType) {
  class Title(orderType: OrderType) : PageOrder(orderType)

  class Description(orderType: OrderType) : PageOrder(orderType)

  fun copy(orderType: OrderType): PageOrder {
    return when (this) {
      is Title -> Title(orderType)
      is Description -> Title(orderType)
    }
  }
}
