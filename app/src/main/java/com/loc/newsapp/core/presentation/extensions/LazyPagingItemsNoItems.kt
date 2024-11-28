package com.loc.newsapp.core.presentation.extensions

import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.noItems
  get() = loadState.append.endOfPaginationReached && itemCount == 0
