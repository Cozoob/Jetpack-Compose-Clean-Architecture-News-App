package com.loc.newsapp.boarding.domain.model

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}