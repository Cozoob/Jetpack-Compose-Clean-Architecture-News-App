package com.loc.newsapp.core.presentation

import com.loc.newsapp.core.domain.routes.HomeScreenRoute
import com.loc.newsapp.core.domain.routes.IRoute

data class InitialLoadingState(
    val startRoute: IRoute = HomeScreenRoute,
    val isLoading: Boolean = true
)
